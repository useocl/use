#!/usr/bin/env python3
"""Generate Mermaid diagrams from ArchUnit failure reports.

Reads the committed `target_archunit_temp/archunit-reports/failure_report_*.txt`
files and rewrites the contents of `<!-- BEGIN MERMAID:bug-N -->` ...
`<!-- END MERMAID:bug-N -->` regions in the PR notes README.

Re-running the script overwrites only the marker regions; surrounding prose
is preserved byte-for-byte. The script has no external dependencies (stdlib
only) and is intended to be run from anywhere — paths are resolved relative
to this file's location in the repo.
"""

from __future__ import annotations

import re
import sys
from dataclasses import dataclass, field
from pathlib import Path

REPO = Path(__file__).resolve().parent.parent.parent
README = REPO / "README_nghiabt_notes_on_this_pr" / "nghiabt_notes_on_this_pr.md"

CORE_REPORTS = REPO / "use-core" / "target_archunit_temp" / "archunit-reports"
GUI_REPORTS = REPO / "use-gui" / "target_archunit_temp" / "archunit-reports"

# (marker, [(report-path, kind, optional title)])
SOURCES: list[tuple[str, list[tuple[Path, str, str]]]] = [
    ("bug-1", [(CORE_REPORTS / "failure_report_maven_cycles_uml.txt", "cycles", "uml.* triangle")]),
    ("bug-2", [
        (GUI_REPORTS / "failure_report_maven_cycles_gui_main.txt", "cycles", "gui.main"),
        (GUI_REPORTS / "failure_report_maven_cycles_gui_views.txt", "cycles", "gui.views"),
    ]),
    ("bug-3", [(GUI_REPORTS / "failure_report_maven_cycles_runtime.txt", "cycles", "runtime")]),
    # bug-4 resolved: api↔api.impl cycle fixed, inline before/after diagrams in README
    # ("bug-4", [(CORE_REPORTS / "failure_report_maven_cycles_api.txt", "cycles", "api / api.impl")]),
    ("bug-5", [(CORE_REPORTS / "failure_report_maven_cycles_gen.txt", "cycles", "gen.assl / gen.tool")]),
    ("bug-6", [(CORE_REPORTS / "failure_report_maven_cycles_parser.txt", "cycles", "parser.*")]),
    ("bug-7", [(GUI_REPORTS / "failure_report_maven_layers.txt", "layers", "GUI launcher layer violations")]),
]

CYCLE_HEADER = re.compile(r"^Cycle detected:\s*Slice (\S+)\s*(?:->)?\s*$")
SLICE_LINE = re.compile(r"^\s+Slice (\S+)\s*(?:->)?\s*$")
DEPS_HEADER = re.compile(r"^\s+\d+\.\s+Dependencies of Slice (\S+)\s*$")
LAYER_LINE = re.compile(r"Method <(.+?)>\s+calls\s+\S+\s+<(.+?)>\s+in \(([^)]+)\)")


# ---- cycle parsing ---------------------------------------------------------


@dataclass
class Cycle:
    slices: list[str] = field(default_factory=list)


def parse_cycles(text: str) -> list[Cycle]:
    cycles: list[Cycle] = []
    cur: Cycle | None = None
    in_deps = False
    for line in text.splitlines():
        m = CYCLE_HEADER.match(line)
        if m:
            if cur is not None:
                cycles.append(cur)
            cur = Cycle(slices=[m.group(1)])
            in_deps = False
            continue
        if cur is None:
            continue
        if DEPS_HEADER.match(line):
            in_deps = True
            continue
        if not in_deps:
            m = SLICE_LINE.match(line)
            if m:
                cur.slices.append(m.group(1))
    if cur is not None:
        cycles.append(cur)
    for c in cycles:
        if len(c.slices) >= 2 and c.slices[0] == c.slices[-1]:
            c.slices = c.slices[:-1]
    return cycles


def cycles_to_mermaid(cycles: list[Cycle], title: str) -> str:
    if not cycles:
        return f"_(no cycles detected in {title})_"
    nodes: list[str] = []
    edges: list[tuple[str, str]] = []
    for c in cycles:
        for s in c.slices:
            if s not in nodes:
                nodes.append(s)
        for i, s in enumerate(c.slices):
            nxt = c.slices[(i + 1) % len(c.slices)]
            pair = (s, nxt)
            if pair not in edges:
                edges.append(pair)
    lines = [f"**{title}** — {len(cycles)} cycle(s), {len(edges)} edge(s) across {len(nodes)} package(s)", "", "```mermaid", "flowchart LR"]
    for n in nodes:
        lines.append(f'    {n}["{n}"]')
    for a, b in edges:
        lines.append(f"    {a} --> {b}")
    if edges:
        indices = ",".join(str(i) for i in range(len(edges)))
        lines.append(f"    linkStyle {indices} stroke:#d33,stroke-width:2px")
    lines.append("```")
    return "\n".join(lines)


# ---- layer-violation parsing ----------------------------------------------


@dataclass
class LayerEdge:
    caller: str
    callee: str
    locations: list[str] = field(default_factory=list)


def _strip_member(qualified: str) -> str:
    """Drop the trailing `.method(...)` or `.<init>(...)` portion of a qualified ref."""
    paren = qualified.find("(")
    if paren >= 0:
        qualified = qualified[:paren]
    last_dot = qualified.rfind(".")
    return qualified[:last_dot] if last_dot > 0 else qualified


def parse_layer_violations(text: str) -> list[LayerEdge]:
    edges: dict[tuple[str, str], LayerEdge] = {}
    for line in text.splitlines():
        m = LAYER_LINE.search(line)
        if not m:
            continue
        caller = _strip_member(m.group(1))
        callee = _strip_member(m.group(2))
        loc = m.group(3)
        key = (caller, callee)
        if key not in edges:
            edges[key] = LayerEdge(caller=caller, callee=callee)
        edges[key].locations.append(loc)
    return list(edges.values())


def _short_label(fqcn: str) -> tuple[str, str]:
    parts = fqcn.split(".")
    cls = parts[-1]
    pkg = ".".join(parts[3:-1]) if len(parts) > 4 else ".".join(parts[:-1])
    return pkg or "(root)", cls


def layers_to_mermaid(edges: list[LayerEdge], title: str) -> str:
    if not edges:
        return f"_(no layer violations in {title})_"
    lines = [f"**{title}** — {sum(len(e.locations) for e in edges)} violation(s) across {len(edges)} caller→callee pair(s)", "", "```mermaid", "flowchart LR"]
    node_id: dict[str, str] = {}

    def node(fqcn: str) -> str:
        if fqcn not in node_id:
            node_id[fqcn] = f"n{len(node_id)}"
            pkg, cls = _short_label(fqcn)
            lines.append(f'    {node_id[fqcn]}["{pkg}<br/>{cls}"]')
        return node_id[fqcn]

    edge_lines: list[str] = []
    for e in edges:
        a = node(e.caller)
        b = node(e.callee)
        edge_lines.append(f'    {a} -. "{len(e.locations)} call(s)" .-> {b}')
    lines.extend(edge_lines)
    if edge_lines:
        indices = ",".join(str(i) for i in range(len(edge_lines)))
        lines.append(f"    linkStyle {indices} stroke:#d33,stroke-width:2px")
    lines.append("```")
    return "\n".join(lines)


# ---- README rewriting ------------------------------------------------------


def render_marker_block(files: list[tuple[Path, str, str]]) -> str:
    parts: list[str] = []
    for path, kind, title in files:
        if not path.exists():
            parts.append(f"_(report `{path.name}` not present — run the ArchUnit tests to generate it)_")
            continue
        text = path.read_text()
        if kind == "cycles":
            parts.append(cycles_to_mermaid(parse_cycles(text), title))
        elif kind == "layers":
            parts.append(layers_to_mermaid(parse_layer_violations(text), title))
        else:
            raise ValueError(f"unknown kind: {kind}")
    return "\n\n".join(parts)


def update_readme(blocks: dict[str, str]) -> int:
    text = README.read_text()
    missing = 0
    for marker, block in blocks.items():
        replacement = (
            f"<!-- BEGIN MERMAID:{marker} -->\n{block}\n<!-- END MERMAID:{marker} -->"
        )
        pattern = re.compile(
            rf"<!-- BEGIN MERMAID:{re.escape(marker)} -->.*?<!-- END MERMAID:{re.escape(marker)} -->",
            re.DOTALL,
        )
        new_text, n = pattern.subn(lambda _m: replacement, text)
        if n == 0:
            print(f"warning: marker {marker} not found in README", file=sys.stderr)
            missing += 1
        text = new_text
    README.write_text(text)
    return missing


def main() -> int:
    blocks = {marker: render_marker_block(files) for marker, files in SOURCES}
    missing = update_readme(blocks)
    return 1 if missing else 0


if __name__ == "__main__":
    sys.exit(main())

/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

// $Id$

package org.tzi.use.main.shell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tzi.use.analysis.coverage.AttributeAccessInfo;
import org.tzi.use.analysis.coverage.CoverageAnalyzer;
import org.tzi.use.analysis.coverage.CoverageData;
import org.tzi.use.main.Session;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.util.Log;
import org.tzi.use.util.StringUtil;

/**
 * TODO
 * 
 * @author ms
 *
 */
public class ShellCoverageCommandProcessor {

	private Session fSession;
	private MModel model;
	private String line;
	private boolean displaySum = false;

	private String fSpace = " ";
	private String fSpaceTwo = fSpace + fSpace;
	private String fSpaceColon = ": ";
	private String fCommaSpace = ", ";
	private String fDot = ".";
	private String fTab = "\t";

	public ShellCoverageCommandProcessor(Session fSession, String line) {
		this.fSession = fSession;
		this.line = line;
	}

	public void run() {
		if (checkModelAvailable()) {
			dispatch();
		} else {
			Log.error("No model loaded");
			return;
		}
	}

	private boolean checkModelAvailable() {
		model = fSession.system().model();
		return model != null;
	}

	private void dispatch() {
		String[] args = line.split(" ");

		for (int i = 1; i < args.length; ++i) {

			if ("-sum".equals(args[i])) {
				displaySum = true;
			}

			else if ("-invariants".equals(args[i])) {
				_invariantsCoverage();
				return;
			}

			else if ("-preconditions".equals(args[i])) {
				_preConditionsCoverage();
				return;
			}

			else if ("-postconditions".equals(args[i])) {
				_postConditionsCoverage();
				return;

			} else {
				Log.error("Invalid argument " + args[i]);
				return;
			}
		}
	}

	private void _invariantsCoverage() {
		Map<MModelElement, CoverageData> completeData = CoverageAnalyzer
				.calculateInvariantCoverage(model, true);

		_displayCoverage(completeData, "invariants");

		CoverageData data = completeData.get(model);

		Log.println("Coverage by Invariant:");

		List<MClassInvariant> sortedInvariants = new ArrayList<MClassInvariant>(
				model.classInvariants());

		Collections.sort(sortedInvariants, new Comparator<MClassInvariant>() {
			@Override
			public int compare(MClassInvariant o1, MClassInvariant o2) {
				int clsCmp = o1.cls().compareTo(o2);
				if (clsCmp == 0) {
					return o1.name().compareTo(o2.name());
				} else {
					return clsCmp;
				}
			}
		});

		String total, classes, classesComplete, associations, attributes, properties;

		for (MClassInvariant invariant : sortedInvariants) {
			data = completeData.get(invariant);

			if (displaySum) {
				total = fSpace
						+ Integer.toString(data.getCompleteCoveredClasses()
								.size()
								+ data.getAssociationCoverage().keySet().size()
								+ data.getAttributeAccessCoverage().keySet()
										.size()
								+ data.getPropertyCoverage().keySet().size());

				classes = Integer.toString(data.getCoveredClasses().size());
				classesComplete = Integer.toString(data
						.getCompleteCoveredClasses().size());
				associations = Integer.toString(data.getAssociationCoverage()
						.keySet().size());
				attributes = Integer.toString(data.getAttributeAccessCoverage()
						.keySet().size());
				properties = Integer.toString(data.getPropertyCoverage()
						.keySet().size());
			} else {
				total = new String();
				classes = StringUtil.fmtSeq(data.getCoveredClasses(),
						fCommaSpace);
				classesComplete = StringUtil.fmtSeq(
						data.getCompleteCoveredClasses(), fCommaSpace);
				associations = StringUtil.fmtSeq(data.getAssociationCoverage()
						.keySet(), fCommaSpace);
				attributes = StringUtil
						.fmtSeq(data.getAttributeAccessCoverage().keySet(),
								fCommaSpace,
								new StringUtil.IElementFormatter<AttributeAccessInfo>() {
									@Override
									public String format(
											AttributeAccessInfo element) {
										String inherited = new String();
										if (element.isInherited()) {
											inherited = fSpace + "(inherited)";
										}

										return element.getSourceClass().name()
												+ fDot
												+ element.getAttribute().name()
												+ inherited;
									}
								});
				properties = StringUtil.fmtSeq(data.getPropertyCoverage()
						.keySet(), fCommaSpace,
						new StringUtil.IElementFormatter<MModelElement>() {
							@Override
							public String format(MModelElement element) {
								return element.name();
							}
						});
			}

			Log.println();
			Log.print(fSpaceTwo);
			Log.print(invariant.cls().name());
			Log.print("::");
			Log.print(invariant.name());
			Log.print(":");
			Log.println(total);

			Log.print(fSpaceTwo + "-Classes:" + fTab + fTab);
			Log.println(classes);

			Log.print(fSpaceTwo + "-Classes (complete):" + fTab);
			Log.println(classesComplete);

			Log.print(fSpaceTwo + "-Associations:" + fTab);
			Log.println(associations);

			Log.print(fSpaceTwo + "-Attributes:" + fTab + fTab);
			Log.println(attributes);

			Log.print(fSpaceTwo + "-Properties:" + fTab + fTab);
			Log.println(properties);
		}

	}

	private void _preConditionsCoverage() {
		Map<MModelElement, CoverageData> completeData = CoverageAnalyzer
				.calculatePreConditionCoverage(model, true);

		_displayCoverage(completeData, "pre-conditions");

		CoverageData data = completeData.get(model);
	}
	
	private void _postConditionsCoverage() {
		Map<MModelElement, CoverageData> completeData = CoverageAnalyzer
				.calculatePostConditionCoverage(model, true);

		_displayCoverage(completeData, "post-conditions");

		CoverageData data = completeData.get(model);
	}

	private void _displayCoverage(
			Map<MModelElement, CoverageData> completeData, String target) {

		CoverageData data = completeData.get(model);

		Log.println("Classes covered by" + fSpace + target + fSpaceColon
				+ data.getCoveredClasses().size() + "/"
				+ model.classes().size());

		List<Map.Entry<MClass, Integer>> entries = new ArrayList<Map.Entry<MClass, Integer>>(
				data.getClassCoverage().entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<MClass, Integer>>() {
			@Override
			public int compare(Entry<MClass, Integer> o1,
					Entry<MClass, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		for (Map.Entry<MClass, Integer> entry : entries) {
			Log.println(fSpaceTwo + entry.getKey().name() + fSpaceColon
					+ entry.getValue().toString());
		}

		Set<MClass> notCovered = new HashSet<MClass>(model.classes());
		notCovered.removeAll(data.getClassCoverage().keySet());

		for (MClass entry : notCovered) {
			Log.println(fSpaceTwo + entry.name() + fSpaceColon + 0);
		}

		Log.println("Classes covered completely by" + fSpace + target
				+ fSpaceColon + data.getCompleteCoveredClasses().size() + "/"
				+ model.classes().size());

		entries = new ArrayList<Map.Entry<MClass, Integer>>(data
				.getCompleteClassCoverage().entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<MClass, Integer>>() {
			@Override
			public int compare(Entry<MClass, Integer> o1,
					Entry<MClass, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		for (Map.Entry<MClass, Integer> entry : entries) {
			Log.println(fSpaceTwo + entry.getKey().name() + fSpaceColon
					+ entry.getValue().toString());
		}

		notCovered = new HashSet<MClass>(model.classes());
		notCovered.removeAll(data.getCompleteClassCoverage().keySet());

		for (MClass entry : notCovered) {
			Log.println(fSpaceTwo + entry.name() + fSpaceColon + 0);
		}

		Log.println("Associations covered by" + fSpace + target + fSpaceColon
				+ data.getAssociationCoverage().size() + "/"
				+ model.associations().size());

		int attributeCount = 0;
		for (MClass cls : model.classes()) {
			attributeCount += cls.attributes().size();
		}

		Log.println("Attributes covered by" + fSpace + target + fSpaceColon
				+ data.getAttributeAccessCoverage().size() + "/"
				+ attributeCount);
	}

	/**
	 * 
	 */

	/**
	 * private String line; private String result;
	 * 
	 * public ShellCoverageCommandProcessor(String line) { this.line = line; }
	 * 
	 * 
	 * 
	 * public boolean evaluate() {
	 * 
	 * if(check_syntax()) { //derive_tasks(); result = _evaluate(); return true;
	 * } return false; }
	 * 
	 * 
	 * 
	 * private boolean check_syntax() {
	 * 
	 * String[] valid_modifier_switches = {"sum"}; String[] valid_task_switches
	 * = {"invariants", "contracts", "preconditions", "postconditions"};
	 * 
	 * // scan line String[] args = line.split(" ");
	 * 
	 * for (int i = 1; i < args.length; ++i) { if ("-sum".equals(args[i])) {
	 * printSums = true; } else { Log.error("Invalid argument " + args[i]);
	 * return; } }
	 * 
	 * MModel model = fSession.system().model(); if (model == null) {
	 * Log.error("No model loaded"); return; }
	 * 
	 * }
	 * 
	 * private String _evaluate() {
	 * 
	 * boolean printSums = false;
	 * 
	 * 
	 * 
	 * Map<MModelElement, CoverageData> completeData =
	 * CoverageAnalyzer.calculateModelCoverage(model, true); CoverageData data =
	 * completeData.get(model);
	 * 
	 * Log.println("Covered classes by invariants:      " +
	 * data.getCoveredClasses().size() + "/" + model.classes().size());
	 * 
	 * List<Map.Entry<MClass, Integer>> entries = new
	 * ArrayList<Map.Entry<MClass, Integer>>(
	 * data.getClassCoverage().entrySet());
	 * 
	 * Collections.sort(entries, new Comparator<Map.Entry<MClass, Integer>>() {
	 * 
	 * @Override public int compare(Entry<MClass, Integer> o1, Entry<MClass,
	 *           Integer> o2) { return o2.getValue().compareTo(o1.getValue()); }
	 *           });
	 * 
	 *           for (Map.Entry<MClass, Integer> entry : entries) {
	 *           Log.println("  " + entry.getKey().name() + ": " +
	 *           entry.getValue().toString()); }
	 * 
	 *           Set<MClass> notCovered = new HashSet<MClass>(model.classes());
	 *           notCovered.removeAll(data.getClassCoverage().keySet());
	 * 
	 *           for (MClass entry : notCovered) { Log.println("  " +
	 *           entry.name() + ": " + 0); }
	 * 
	 *           Log.println("Covered classes (complete) by invariants: " +
	 *           data.getCompleteCoveredClasses().size() + "/" +
	 *           model.classes().size());
	 * 
	 *           entries = new ArrayList<Map.Entry<MClass,
	 *           Integer>>(data.getCompleteClassCoverage().entrySet());
	 * 
	 *           Collections.sort(entries, new Comparator<Map.Entry<MClass,
	 *           Integer>>() {
	 * @Override public int compare(Entry<MClass, Integer> o1, Entry<MClass,
	 *           Integer> o2) { return o2.getValue().compareTo(o1.getValue()); }
	 *           });
	 * 
	 *           for (Map.Entry<MClass, Integer> entry : entries) {
	 *           Log.println("  " + entry.getKey().name() + ": " +
	 *           entry.getValue().toString()); }
	 * 
	 *           notCovered = new HashSet<MClass>(model.classes());
	 *           notCovered.removeAll(data.getCompleteClassCoverage().keySet());
	 * 
	 *           for (MClass entry : notCovered) { Log.println("  " +
	 *           entry.name() + ": " + 0); }
	 * 
	 *           Log.println("Covered associations by invariants: " +
	 *           data.getAssociationCoverage().size() + "/" +
	 *           model.associations().size());
	 * 
	 *           int attCount = 0; for (MClass cls : model.classes()) { attCount
	 *           += cls.attributes().size(); }
	 * 
	 *           Log.println("Covered attributes by invariants:   " +
	 *           data.getAttributeAccessCoverage().size() + "/" + attCount);
	 * 
	 *           Log.println(); Log.println("Coverage by Invariant:");
	 * 
	 *           List<MClassInvariant> sortedInvs = new
	 *           ArrayList<MClassInvariant>(model.classInvariants());
	 *           Collections.sort(sortedInvs, new Comparator<MClassInvariant>()
	 *           {
	 * @Override public int compare(MClassInvariant o1, MClassInvariant o2) {
	 *           int clsCmp = o1.cls().compareTo(o2); if (clsCmp == 0) { return
	 *           o1.name().compareTo(o2.name()); } else { return clsCmp; } } });
	 * 
	 *           String total, classes, classesComp, assocs, attr, props;
	 * 
	 *           for (MClassInvariant inv : sortedInvs) { data =
	 *           completeData.get(inv);
	 * 
	 *           if (printSums) { total = " " + Integer.toString(
	 *           data.getCompleteCoveredClasses().size() +
	 *           data.getAssociationCoverage().keySet().size() +
	 *           data.getAttributeAccessCoverage().keySet().size() +
	 *           data.getPropertyCoverage().keySet().size() );
	 * 
	 *           classes = Integer.toString(data.getCoveredClasses().size());
	 *           classesComp =
	 *           Integer.toString(data.getCompleteCoveredClasses().size());
	 *           assocs =
	 *           Integer.toString(data.getAssociationCoverage().keySet()
	 *           .size()); attr =
	 *           Integer.toString(data.getAttributeAccessCoverage
	 *           ().keySet().size()); props =
	 *           Integer.toString(data.getPropertyCoverage().keySet().size()); }
	 *           else { total = ""; classes =
	 *           StringUtil.fmtSeq(data.getCoveredClasses(), ", "); classesComp
	 *           = StringUtil.fmtSeq(data.getCompleteCoveredClasses(), ", ");
	 *           assocs =
	 *           StringUtil.fmtSeq(data.getAssociationCoverage().keySet(),
	 *           ", "); attr =
	 *           StringUtil.fmtSeq(data.getAttributeAccessCoverage().keySet(),
	 *           ", ", new StringUtil.IElementFormatter<AttributeAccessInfo>() {
	 * @Override public String format(AttributeAccessInfo element) { String
	 *           inherited = ""; if (element.isInherited()) { inherited =
	 *           " (inherited)"; }
	 * 
	 *           return element.getSourceClass().name() + "." +
	 *           element.getAttribute().name() + inherited; } }); props =
	 *           StringUtil.fmtSeq(data.getPropertyCoverage().keySet(), ", ",
	 *           new StringUtil.IElementFormatter<MModelElement>() {
	 * @Override public String format(MModelElement element) { return
	 *           element.name(); } }); }
	 * 
	 *           Log.println(); Log.print("  "); Log.print(inv.cls().name());
	 *           Log.print("::"); Log.print(inv.name()); Log.print(":");
	 *           Log.println(total);
	 * 
	 *           Log.print("   -Classes:            "); Log.println(classes);
	 * 
	 *           Log.print("   -Classes (complete): ");
	 *           Log.println(classesComp);
	 * 
	 *           Log.print("   -Associations:       "); Log.println(assocs);
	 * 
	 *           Log.print("   -Attributes:         "); Log.println(attr);
	 * 
	 *           Log.print("   -Properties:         "); Log.println(props); }
	 * 
	 *           }
	 **/

}

# USE - UML-based Specification Environment

(Note that this is a release of a research prototype. There is no
warranty of any kind.)

## Overview

USE is a system for the specification of information systems. It is
based on a subset of the Unified Modeling Language (UML) [1]. A USE
specification contains a textual description of a model using features
found in UML class diagrams (classes, associations, etc.). Expressions
written in the Object Constraint Language (OCL) are used to specify
additional integrity constraints on the model. A model can be animated
to validate the specification against non-formal requirements. System
states (snapshots of a running system) can be created and manipulated
during an animation. For each snapshot the OCL constraints are
automatically checked. Information about a system state is given by
graphical views.  OCL expressions can be entered and evaluated to
query detailed information about a system state.

The USE specification language is based on UML and OCL. Due to the
semi-formal definition of OCL there are some language constructs whose
interpretation is ambiguous or unclear [2]. In [3] and [4] we have
presented a formalization of OCL which attempts to provide a solution
for most of the problems. The USE approach to validation is described
in [5] and [6].

## Installation

To install USE, read the instructions in the [INSTALL](INSTALL) file.

## Getting started

After successful installation, the following command can be used to
invoke USE on an example specification. Change the current directory
to the top level directory of the distribution and enter the following
(the exact commands may depend on your platform):

```bash
cd examples
../bin/use -v Documentation/Demo/Demo.use
```

The last command will compile and check the file Demo.use in the
examples directory. It contains a USE specification for a simple model
of a company. The -v switch is used to increase verbosity of
output. The main interface to the tool is a command line interface
where you enter commands at a prompt. The output should therefore be
similar to the following.

```
loading properties from: C:\Dev\USE\use-6.0.0\etc\use.properties
USE version X.X.X, Copyright (C) 1999-2021 University of Bremen
Plugin path: [C:\Dev\USE\use-6.0.0\lib\plugins]
Plugin filename(s) [AssociationExtend.jar,ModelValidatorPlugin-5.2.0-r1.jar,ObjectToClassPlugin-2.0.jar,OCLComplexityPlugin5.1.0.jar,use-filmstrip.jar]
compiling specification...
Model Company (3 classes, 3 associations, 4 invariants, 0 operations, 0 pre-/postconditions, 0 state machines)
Enter `help' for a list of available commands.
Enter `plugins' for a list of available plugin commands.
use>
```

At this point you can enter commands at the prompt (try `help' for a
list of available commands). You can enter OCL-like expressions by
starting the input with a question mark. The expression will be
evaluated and its result will be shown, e.g.:

```ocl
use> ? Set{1,2,3}->select(e | e > 1)
-> Set{2,3} : Set(Integer)
```

The file test/queries.cmd contains a large number of examples for
valid expressions.

Commands can also be read from a separate file with the "read"
command. Look at the files Demo*.cmd in the same directory. For
example, starting with Demo0.cmd, an object is created and the new
system state will be visualized in the system state window.

```use
use> open Demo0.cmd
Demo0.cmd> !create d0:Department;
```

For more information about the graphical user interface please refer
to the [quick tour](http://www.db.informatik.uni-bremen.de/projects/USE/).

## Documentation

Documentation is available in the [manual](manual/main.md) directory.
It contains a quick tour demonstrating the central features of USE.
The tool is heavily based on ideas published in [3], [4] and [6].
See the references at the end of the file.

Some information about issues related to OCL can be found in the file
README.OCL.

Note, that the documentation was automatically translated from LaTex to Markdown 
and has still many issues. Feel free to submit changes to the documentation.

## Contact

Comments and bug reports are welcome and should be addressed on [GitHub](https://github.com/useocl/use/issues).

The project's web site is <https://github.com/useocl/use/>

## Credits

The parser for USE specifications is implemented with the ANTLR parser
generator which is in the public domain. We have included the source
code of ANTLR in this distribution, so that the USE parser can be
easily regenerated. We would like to thank Terence Parr and the other
developers of [ANTLR](http://www.antlr.org) for making this great tool freely available.

## Copying

USE is released under the GNU public license, see the file COPYING for
details. The distribution contains the following libraries from
external parties. Source code for these libraries is available from
the web.

- The [ANTLR parser generator tool](http://www.antlr.org)
- The [JUnit library](http://www.junit.org)

## Reporting bugs

Bug reports can submitted on [GitHub](https://github.com/useocl/use/issues)

When submitting bug reports, use the available bug template and always include:

- a complete description of the problem encountered
- the output of `use -V'
- the operating system and version
- the architecture.

If possible, include:

- a stack trace, if an exception occurred

These steps will help diagnose the problem.

## Acknowledgments

The following people made very helpful contributions to the USE
project. A big "thank you" to all of you.

* Hanna Bauerdick
* Joern Bohling
* Jens Brüning
* Fabian Büttner
* Duc-Hanh Dang
* Heino Gärtner
* Daniel Gent
* Martin Gogolla
* Fabian Gutsche
* Lars Hamann
* Frank Hilken
* Andreas Kästner
* Ralf Kollmann
* Mirco Kuhlmann
* Arne Lindow
* Oliver Radfelder
* Mark Richters
* Antje Werner
* Paul Ziemann

There are many other people who provided comments and input on
USE. Although we cannot list them all by name here, their feedback was
very helpful and is highly appreciated.

## References

[1] OMG Unified Modeling Language Specification, Version 2.5.1, December 2017.
    Object Management Group, Inc., Framingham, Mass., Internet: http://www.omg.org/spec/UML, 2021.

[2] Martin Gogolla and Mark Richters. On constraints and queries in
    UML. In Martin Schader and Axel Korthaus, editors, The Unified
    Modeling Language -- Technical Aspects and Applications, pages
    109--121. Physica-Verlag, Heidelberg, 1998.

[3] Mark Richters and Martin Gogolla. On formalizing the UML object
    constraint language OCL. In Tok Wang Ling, Sudha Ram, and Mong Li
    Lee, editors, Proc. 17th Int. Conf. Conceptual Modeling (ER'98),
    pages 449--464. Springer, Berlin, LNCS 1507, 1998.

[4] Mark Richters and Martin Gogolla. A metamodel for OCL. In Robert
    France and Bernhard Rumpe, editors, Proceedings of the Second
    International Conference on the Unified Modeling Language: UML'99,
    LNCS 1723. Springer, 1999.

[5] Mark Richters and Martin Gogolla. Validating UML models and OCL
    constraints. Accepted paper for the Third International Conference
    on the Unified Modeling Language, UML'2000, York.

[6] Mark Richters. A Precise Approach to Validating UML Models and OCL
    Constraints. Phd thesis. Universitaet Bremen. Logos Verlag,
    Berlin, BISS Monographs, No. 14. 2002.

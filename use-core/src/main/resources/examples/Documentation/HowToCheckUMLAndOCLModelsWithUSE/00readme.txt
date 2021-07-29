		How to Check UML and OCL Models with USE
			     Martin Gogolla
			       July 2010

Abstract: This note contains practical hints on how to define UML and
OCL models and how to check such models with the tool USE (UML-based
Specification Environment) developed at the University of Bremen. One
should obtain a link to the tool USE by searching Google with the terms
'use ocl bremen'. This note explains most of the USE functionality by a
simple example describing civil status properties of persons.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

1. WHAT IS CONTAINED IN THE FILES?

In order to work with the civil status example easily, all files should
be placed in the directory 'c:/use/civstat/'. In your directory
'c:/use/civstat/' there should be 56 files which are partitioned into 9
groups.

----------------------------------------- files documenting this note --
- 00readme.pdf
- 00readme.sxw
- 00readme.txt
----------------------------------------- files for the abcd scenario --
- abcd_assl.cmd
- abcd_assl.olt
+ abcd_assl.pro
- abcd_cmd.cmd
- abcd_cmd.olt
+ abcd_cmd.pro
+ abcd_seqDiaDetail1.gif
+ abcd_seqDiaDetail2.gif
+ abcd_useScreenshot.gif
--------------------------------------- files for the bigamy scenario --
- bigamy.cmd
- bigamy.invs
+ bigamy.pro
------------------------------------- central files showing the model --
+ civstat.assl
+ civstat.use
+ civstat_classDia.gif
---------------------------------------- files for the crowd scenario --
- crowd.cmd
- crowd.olt
+ crowd.pro
+ crowd_classExtent.gif
+ crowd_useScreenshot.gif
---------------------------------- papers explaining underlying ideas --
- Gogolla_2005_SOSYM.pdf
- Gogolla_2005_SOSYM.ps
- Gogolla_2007_SCP.pdf
- Gogolla_2007_SCP.ps
- Gogolla_2009_TAP.pdf
- Gogolla_2009_TAP.ps
--------------------------------- files for the independence scenario --
- independence_allInvariants.gif
- independence_attributesDefined.cmd
+ independence_attributesDefined.gif
- independence_attributesDefined.pro
- independence_femaleHasNoWife.cmd
+ independence_femaleHasNoWife.gif
- independence_femaleHasNoWife.pro
- independence_maleHasNoHusband.cmd
+ independence_maleHasNoHusband.gif
- independence_maleHasNoHusband.pro
- independence_nameCapitalThenSmallLetters.cmd
+ independence_nameCapitalThenSmallLetters.gif
- independence_nameCapitalThenSmallLetters.pro
- independence_nameIsUnique.cmd
+ independence_nameIsUnique.gif
- independence_nameIsUnique.pro
--------------------------------- cmd files defining model operations --
+ Person_birth.cmd
+ Person_death_married.cmd
+ Person_death_unmarried.cmd
+ Person_divorce.cmd
+ Person_marry.cmd
---------------------------------------- files for the world scenario --
- world.cmd
- world.olt
+ world.pro
+ world_objDia.gif
- world_query.cmd
+ world_query.pro
------------------------------------------------------------------------

Files ending with 'use', 'assl', 'cmd', 'invs', or 'olt' can be
processed by the USE tool. All files with other suffixes ('pdf',
'sxw', 'txt', 'pro', 'gif', or 'ps') are the result of documenting the
work ('gif', 'pro', ...) with USE or explain basic ideas ('ps',
'pdf'). The file suffixes indicate the file content as follows.

use  - definition of the UML and OCL model (classes, invariants, etc.)
assl - definition of parametrized scripts for manipulating the model
cmd  - scripts or script calls realizing scenarios
invs - additional invariants to be loaded dynamically
olt  - object diagram layout

pdf  - pdf file for documentation
txt  - plain text files for documentation
pro  - protocol file (plain text) documenting execution of a cmd file
gif  - graphic file containing a UML diagram or something similar
ps   - ps file for documentation

All files enumerated above with '+' are shown in the file
'00readme.pdf'.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

2. HOW CAN I RUN USE AND EXECUTE SCENARIOS?

Download, install and start USE. Consult the USE documentation if
needed. USE will show up with a GUI window and a COMMAND LINE
window. Open the file 'c:/use/civstat/abcd_cmd.cmd' with a text
editor. The first line of that file looks as follows:

-- read c:/use/civstat/abcd_cmd.cmd
   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

A cmd file contains a sequence of commands which can be processed by
USE. The characters '--' indicate that the rest of the line is a
comment. Copy that part of this first line into your clipboard which
is marked above with the character '^'. Paste the command which you
have copied from your clipboard into the COMMAND LINE window and press
return. USE will execute all commands in the file
'c:/use/civstat/abcd_cmd.cmd' and will build up an object diagram. USE
has constructed several object diagrams before reaching the final
one. All executed operations can be traced in the sequence diagram
view.

Execution of all above mentioned scenarios can be done in the same way
as for the first scenario, i.e., by loading the respective cmd file into
a text editor, by copying from the first line the read command together
with the respective file name and by pasting this command into the
COMMAND LINE window. This will work for the following cmd files:

abcd_assl.{cmd|pro}
abcd_cmd.{cmd|pro}
bigamy.{cmd|pro}
crowd.{cmd|pro}
independence_attributesDefined.{cmd|pro}
independence_femaleHasNoWife.{cmd|pro}
independence_maleHasNoHusband.{cmd|pro}
independence_nameCapitalThenSmallLetters.{cmd|pro}
independence_nameIsUnique.{cmd|pro}
world.{cmd|pro}
world_query.{cmd|pro}

These scenarios are documented with a protocol file having the same name
as the cmd file but possessing the suffix pro. These pro files are
ordinary text files. They are edited versions (long files names are
shortened) of the output given by USE in the COMMAND LINE window.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

3. WHAT DO THE DIFFERENT SCENARIOS SHOW?

Scenario 'abcd': A simple object diagram with four persons (ada, bob,
   cyd, dan) is built up. The persons are created, partly get married or
   divorced or die. This scenario is expressed in two versions, namely
   one version realizing the operations with cmd files (abcd_cmd.cmd)
   and one version realizing the operations using the assl file
   (abcd_assl.cmd).

Scenario 'bigamy': This scenario (bigamy.cmd) checks whether a bigamy
   situation can be constructed under the stated invariants. A large
   number of possible bigamy situations is tried with the script
   attemptBigamy() from the civstat.assl file, but none is found. This
   proves that at least in the enumerated search space, the UML and OCL
   model is bigamy free.

Scenario 'crowd': The cmd file crowd.cmd builds up an invalid object
   diagram where one model-inherent multiplicity constraint and two
   explicit invariants are violated. This scenario may be regarded as a
   negative test case which has to lead to a constraint violation.

Scenario 'independence': This scenario considers the relationship
   between the five invariants and consists of five cmd files
   (independence_attributesDefined.cmd,
   independence_femaleHasNoWife.cmd, independence_maleHasNoHusband.cmd,
   independence_nameCapitalThenSmallLetters.cmd,
   independence_nameIsUnique.cmd). It builds up five object diagrams
   where each single object diagram violates exactly one invariant and
   satisfies the other four invariants. The construction of these five
   object diagrams proves that the five invariants are independent from
   each other, i.e., no single invariant is a consequence of the other
   invariants. In other words, the five invariants are non-redundant and
   express independent requirements.

Scenario 'world': Here a larger object diagram is constructed with the
   cmd file world.cmd that shows that the invariants are consistent,
   i.e., the invariants are not contradictory to each other. The object
   diagram is constructed by the script world() which is parametrized
   with the number of expected female and male persons and the number of
   expected mariages. Person attributes like name or gender are filled
   with values representing real-world situations.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

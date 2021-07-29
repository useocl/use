-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-3d.cmd

-- generate many companies and a three-level boss-worker hierarchy
-- in at least one company and a two-role employee (being boss 
-- and worker)

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(26)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateCompanies(7)
gen result
gen result accept

gen start -b examples/Papers/2005/GogollaBohlingRichters/percom.assl generateJobs(182)
-- 182=26*7
gen result
gen result accept

-- gen load examples/Papers/2005/GogollaBohlingRichters/threeLevelHierarchy.invs

-- gen load examples/Papers/2005/GogollaBohlingRichters/twoRoleEmployee.invs

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateBossWorker(175)
-- 175=25*7
gen result
gen result accept

------------------------------------------------------------------------

-- !insert (Company7,Job84) into CompanyJob
-- !insert (Person15,Job85) into PersonJob
-- !insert (Company1,Job85) into CompanyJob
-- !iError: Evaluation aborted because of a stack overflow error. Maybe there were too many eleme
-- nts in a sequence of a for-loop.
-- Error: The system state may be changed in use.
-- nsert (Person16,Job86) into PersonJob
-- !insert (Company2,Job86) into CompanyJob
-- !insert (Person17,Job87) into PersonJob
-- !insert (Company3,Job87) into CompanyJob

-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-14.cmd

-- 12 4 16 12 - originally requested from Joern
-- 24 8 32 24 - also works
--  8 3 10  7 - and also a little bit smaller

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(8)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateCompanies(3)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateJobs(10)
gen result
gen result accept

gen load examples/Papers/2005/GogollaBohlingRichters/twoRoleEmployee.invs
gen load examples/Papers/2005/GogollaBohlingRichters/threeLevelHierarchy.invs
gen load examples/Papers/2005/GogollaBohlingRichters/completeHierarchy.invs

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateBossWorker(7)
gen result
gen result accept

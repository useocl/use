-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-3c.cmd

-- generate many companies and a three-level boss-worker hierarchy
-- in at least one company and a two-role employee (being boss 
-- and worker)
-- enforce existence of one company having only one job
-- this shows neccessity of additionally generating salaries

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(2)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateCompanies(3)
gen result
gen result accept

gen start -b examples/Papers/2005/GogollaBohlingRichters/percom.assl generateJobs(5)
gen result
gen result accept

-- gen load examples/Papers/2005/GogollaBohlingRichters/threeLevelHierarchy.invs

gen load examples/Papers/2005/GogollaBohlingRichters/twoRoleEmployee.invs

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateBossWorker(2)
gen result
gen result accept

-- gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateMissingSalaries()
-- gen result
-- gen result accept

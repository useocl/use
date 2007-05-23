-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-3.cmd

-- generate many companies and a three-level boss-worker hierarchy
-- in at least one company and a two-role employee (being boss 
-- and worker)

-- works for generatePersons(12) generateCompanies(4) generateJobs(16) generateBossWorker(12)

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateCompanies(2)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(3)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateJobs(6)
gen result
gen result accept

gen load examples/Papers/2005/GogollaBohlingRichters/threeLevelHierarchy.invs
gen load examples/Papers/2005/GogollaBohlingRichters/twoRoleEmployee.invs

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateBossWorker(4)
gen result
gen result accept

-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-3e.cmd

-- generate many companies and a three-level boss-worker hierarchy
-- in at least one company and a two-role employee (being boss 
-- and worker)

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(26)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateCompanies(4)
gen result
gen result accept

gen start -b examples/Papers/2005/GogollaBohlingRichters/percom.assl generateJobs(28)
gen result
gen result accept

?Company.allInstances->collect(c|c.job->size)

gen load examples/Papers/2005/GogollaBohlingRichters/threeLevelHierarchy.invs

gen load examples/Papers/2005/GogollaBohlingRichters/twoRoleEmployee.invs

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateBossWorker(24)
gen result
gen result accept

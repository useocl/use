-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-2.cmd

-- generate a three-level boss-worker hierarchy in one company
gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(4)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateCompanies(1)
gen result
gen result accept

gen start -b examples/Papers/2005/GogollaBohlingRichters/percom.assl generateJobs(4)
gen result
gen result accept

gen load examples/Papers/2005/GogollaBohlingRichters/threeLevelHierarchy.invs

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateBossWorker(3)
gen result
gen result accept

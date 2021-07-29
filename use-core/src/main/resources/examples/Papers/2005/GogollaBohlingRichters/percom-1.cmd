-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-1.cmd

-- generate a simple boss-worker hierarchy in one company

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(7)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateCompanies(1)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateJobs(7)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateBossWorker(6)
gen result
gen result accept

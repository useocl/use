-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-10.cmd

-- generate a binary tree boss-worker hierarchy
-- generatePersons(3) generateCompanies(1) generateJobs(3) generateBossWorker(2)
-- generatePersons(5) generateCompanies(1) generateJobs(5) generateBossWorker(4)
-- generatePersons(7) generateCompanies(1) generateJobs(7) generateBossWorker(6)
-- generatePersons(9) generateCompanies(1) generateJobs(9) generateBossWorker(8)
-- generatePersons(11) generateCompanies(1) generateJobs(11) generateBossWorker(10)
-- generatePersons(13) generateCompanies(1) generateJobs(13) generateBossWorker(12)

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(7)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateCompanies(1)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateJobs(7)
gen result
gen result accept

gen load examples/Papers/2005/GogollaBohlingRichters/hierarchyBinTree.invs

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateBossWorker(6)
gen result
gen result accept

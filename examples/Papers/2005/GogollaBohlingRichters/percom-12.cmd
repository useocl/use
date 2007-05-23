-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-12.cmd

-- 15P 3C 18J 15BW works fine
-- 26P 6C 32J 26BW works fine

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(26)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateCompanies(6)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateJobs(32)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateBossWorker(26)
gen result
gen result accept

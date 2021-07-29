-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-130.cmd

-- 26 persons, 10 companies, 130 jobs, 120 bossWorker - about 2mins
-- 26 persons, 10 companies, 164 jobs,  80 bossWorker

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateCompanies(10)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(26)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateJobs(164)
-- 260 leads to error
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateBossWorker(80)
gen result
gen result accept

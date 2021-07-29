-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-4.cmd

-- generate many jobs, many companies, but restrict the number of jobs
-- a single person can have and give employees to all companies

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(4)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateCompanies(3)
gen result
gen result accept

-- gen load examples/Papers/2005/GogollaBohlingRichters/companyHasEmployee.invs -- inv due to changes not needed any more

-- gen load examples/Papers/2005/GogollaBohlingRichters/jobsAtMost.invs         -- inv due to changes not needed any more

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateJobs(8)
gen result
gen result accept

-- following parameter 5 concluded from previous experiments

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateBossWorker(5)
gen result
gen result accept

-- neccessity for following procedure detected from previous experiments

-- gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateMissingSalaries()
-- gen result
-- gen result accept

-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-7.cmd

-- generate a snapshot showing that proofs are possible
-- show that the bossBetterPaidThanWorker property is transitive

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateCompanies(1)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(3)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateJobs(3)
gen result
gen result accept

gen load examples/Papers/2005/GogollaBohlingRichters/threeLevelHierarchy.invs

gen load examples/Papers/2005/GogollaBohlingRichters/bossBossBetterPaidThanWorker.invs

gen flags Job::bossBossBetterPaidThanWorker +n

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateTopMidLow()
gen result
gen result accept

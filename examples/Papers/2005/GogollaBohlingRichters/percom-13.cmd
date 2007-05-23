-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-13.cmd

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(8)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateCompanies(1)
gen result
gen result accept

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateJobs(8)
gen result
gen result accept

gen load examples/Papers/2005/GogollaBohlingRichters/bossWorkerChain.invs

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generateBossWorker(7)
-- improve generateBossWorker by adding into reject: or top.worker->notEmpty
gen result
gen result accept

-- Persons   2 3  4   5    6      7             not valid any more
-- Snapshots 1 5 11 225 5958 366190 (ca. 28min) was for old version V0
--               12 238 5742 314167
--               18 279 5746
--               20 613 6370
--               20 194 5668
--               12 314 5746
--               26 274 6036
--               15 183 5812

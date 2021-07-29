-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-8.cmd

-- generate a snapshot showing that proofs are possible
-- show that if a person has 2 jobs, there must exist at least 2 companies

open examples/Papers/2005/GogollaBohlingRichters/percom.use

gen load examples/Papers/2005/GogollaBohlingRichters/personWith2JobsIn0Or1Company.invs

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersonWith2Jobs0Companies()
gen result
-- gen result accept

reset

gen load examples/Papers/2005/GogollaBohlingRichters/personWith2JobsIn0Or1Company.invs

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersonWith2Jobs1Company()
gen result
-- gen result accept

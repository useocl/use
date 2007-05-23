-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-9.cmd

-- generate a snapshot showing that proofs are possible
-- show that if a person has 3 jobs, there must exist at least 3 companies

open examples/Papers/2005/GogollaBohlingRichters/percom.use

gen load examples/Papers/2005/GogollaBohlingRichters/personWith3JobsIn0Or1Or2Companies.invs

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersonWith3Jobs0Companies()
gen result

reset

gen load examples/Papers/2005/GogollaBohlingRichters/personWith3JobsIn0Or1Or2Companies.invs

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersonWith3Jobs1Company()
gen result

reset

gen load examples/Papers/2005/GogollaBohlingRichters/personWith3JobsIn0Or1Or2Companies.invs

gen start -df yyy.tex examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersonWith3Jobs2Companies()
gen result

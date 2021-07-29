-- Opens the class diagram:
-- open examples/generator/english/train.use

-- Creates the object diagram:
-- read examples/generator/english/waggonchain/run.cmd

gen flags +d

--!create t:Train
! t := new Train
gen start examples/generator/english/train.assl waggonchain(t, 10)
gen result accept

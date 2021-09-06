-- Opens the class diagram:
-- open examples/generator/english/train.use

-- Creates the object diagram:
-- read examples/generator/english/cycles/run2.cmd

reset
gen load examples/generator/english/cycles/invariants.txt
gen flags -d -n

-- Replacing `Train::Chain' with `Train::Chain_tooWeak'.
-- Therefore deactivating `Train:Chain'
-- `Train::Chain_tooWeak' is already activated
gen flags Train::Chain +d

gen flags Waggon::NoCycles +n
gen start examples/generator/english/train.assl testTrainBuildupAndWaggonOrder(2, 3)

gen result accept

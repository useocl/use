-- Opens the class diagram:
-- open examples/generator/english/train.use

-- Creates the object diagram:
-- read examples/generator/english/predecessors/run4.cmd

reset
gen load examples/generator/english/predecessors/invariants.txt
gen flags -d -n

-- Replacing `Train::Chain' with `Train::Chain_tooWeak'.
-- Therefore deactivating `Train:Chain'
-- `Train::Chain_tooWeak' is already activated
gen flags Train::Chain +d

gen flags Waggon::PredecessorInSameTrain +n
gen start examples/generator/english/train.assl testTrainBuildupAndWaggonOrder(3, 3)
--quit

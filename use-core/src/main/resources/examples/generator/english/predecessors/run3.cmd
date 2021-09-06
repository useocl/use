-- Opens the class diagram:
-- open examples/generator/english/train.use

-- Creates the object diagram:
-- read examples/generator/english/predecessors/run3.cmd

reset
gen load examples/generator/english/predecessors/invariants.txt
gen flags -d -n

-- the loaded invariant Train::Chain_tooWeak is deactivated in this example
gen flags Train::Chain_tooWeak +d

gen flags Waggon::PredecessorInSameTrain +n
gen start examples/generator/english/train.assl testTrainBuildupAndWaggonOrder(3, 3)
--quit

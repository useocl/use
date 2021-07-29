-- Opens the class diagram:
-- open examples/generator/german/zug.use

-- Creates the object diagram:
-- read examples/generator/german/waggonkette/run.cmd

gen flags +d

!create z:Zug
gen start examples/generator/german/zug.assl waggonkette(z, 10)
gen result accept

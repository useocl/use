-- Opens the class diagram:
-- open examples/generator/german/zug.use

-- Creates the object diagram:
-- read examples/generator/german/schleifen/run.cmd

reset
gen load examples/generator/german/schleifen/invarianten.txt
gen flags -d -n

-- the loaded invariant Zug::Kette_zuWeich is deactivated in this example
gen flags Zug::Kette_zuWeich +d

gen flags Waggon::KeineSchleife +n
gen start examples/generator/german/zug.assl testeZugaufbauUndWaggonreihung(1, 2)

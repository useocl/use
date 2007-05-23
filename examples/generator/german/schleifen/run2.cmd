-- Opens the class diagram:
-- open examples/generator/german/zug.use

-- Creates the object diagram:
-- read examples/generator/german/schleifen/run2.cmd

reset
gen load examples/generator/german/schleifen/invarianten.txt
gen flags -d -n

-- Replacing `Zug::Kette' with `Zug::Kette_zuWeich'.
-- Therfore deactivating `Zug:Kette'
-- `Zug::Kette_zuWeich' is already activated
gen flags Zug::Kette +d

gen flags Waggon::KeineSchleife +n
gen start examples/generator/german/zug.assl testeZugaufbauUndWaggonreihung(2, 3)

-- gen result accept

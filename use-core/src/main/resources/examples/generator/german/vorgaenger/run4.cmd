-- Opens the class diagram:
-- open examples/generator/german/zug.use

-- Creates the object diagram:
-- read examples/generator/german/vorgaenger/run4.cmd

reset
gen load examples/generator/german/vorgaenger/invarianten.txt
gen flags -d -n

-- Replacing `Zug::Kette' with `Zug::Kette_zuWeich'.
-- Therefore deactivating `Zug:Kette'
-- `Zug::Kette_zuWeich' is already activated
gen flags Zug::Kette +d

gen flags Waggon::VorgaengerImGleichenZug +n
gen start examples/generator/german/zug.assl testeZugaufbauUndWaggonreihungBis(3, 3)
--quit

-- Opens the class diagram:
-- open examples/generator/german/zug.use

-- Creates the object diagram:
-- read examples/generator/german/reservierung/run.cmd

reset
gen flags -d -n
gen flags Bahnhof::NameIstSchluesselattribut Zug::Kette +d 

!create Strecke1 : Strecke
!create Bahnhof1 : Bahnhof
!create Bahnhof2 : Bahnhof
!insert (Strecke1,Bahnhof2) into Halt
!insert (Strecke1,Bahnhof1) into Halt
!create Fahrt1 : Fahrt
!insert (Fahrt1,Strecke1) into FahrtStrecke
!create Zug1 : Zug
!insert (Fahrt1,Zug1) into Einsatz
!create Waggon1 : Waggon
!insert (Zug1,Waggon1) into Zugaufbau
!set Waggon1.anzahlPlaetze := 2
!create Waggon2 : Waggon
!insert (Zug1,Waggon2) into Zugaufbau
!set Waggon2.anzahlPlaetze := 2

gen start examples/generator/german/zug.assl reservierung( Fahrt1, Fahrt1.strecke.bahnhof->at(1), Fahrt1.strecke.bahnhof->at(2) )

gen result accept

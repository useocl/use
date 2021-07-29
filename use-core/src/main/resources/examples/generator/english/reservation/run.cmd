-- Opens the class diagram:
-- open examples/generator/english/train.use

-- Creates the object diagram:
-- read examples/generator/english/reservation/run.cmd

reset
gen flags -d -n
gen flags Station::NameIsKeyAttribute Train::Chain +d 

!create Way1 : Way
!create Station1 : Station
!create Station2 : Station
!insert (Way1,Station2) into Stops
!insert (Way1,Station1) into Stops
!create Journey1 : Journey
!insert (Journey1,Way1) into JourneyWay
!create Train1 : Train
!insert (Journey1,Train1) into Assignment
!create Waggon1 : Waggon
!insert (Train1,Waggon1) into TrainBuildup
!set Waggon1.numberOfSeats := 2
!create Waggon2 : Waggon
!insert (Train1,Waggon2) into TrainBuildup
!set Waggon2.numberOfSeats := 2

gen start examples/generator/english/train.assl reservation( Journey1, Journey1.way.station->at(1), Journey1.way.station->at(2) )

gen result accept

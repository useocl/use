-- Creates an invalid state
reset
!create car:Car

!create w1:Wheel
!create w2:Wheel
!create w3:Wheel
!create w4:Wheel

!create e1:Engine

!insert (car, e1) into CarPoweredBy

!insert (car, w1) into DrivesOn
!insert (car, w2) into DrivesOn
!insert (car, w3) into DrivesOn
!insert (car, w4) into DrivesOn

!insert (e1, w1) into PowersWheel
!insert (e1, w2) into PowersWheel

!create boat:Boat

!create e2:Engine

!create p1:Propeller
!create p2:Propeller

!insert (boat, e2) into BoatPoweredBy

!insert (e2, w3) into PowersWheel
!insert (e2, w4) into PowersWheel

!insert (boat, p1) into DrivesWith
!insert (e1, p1) into PowersPropeller

!insert (boat, p2) into DrivesWith
!insert (e2, p2) into PowersPropeller



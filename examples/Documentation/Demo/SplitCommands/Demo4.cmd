-- Creates the object diagram:
-- read examples/Documentation/Demo/SplitCommands/Demo4.cmd

-- Project research
!create research : Project
!set research.name := 'Research'
!set research.budget := 12000

-- Project teaching
!create teaching : Project
!set teaching.name := 'Validating UML'
!set teaching.budget := 3000

-- Controls
!insert (cs,research) into Controls
!insert (cs,teaching) into Controls

-- WorksOn
!insert (frank,research) into WorksOn
!insert (frank,teaching) into WorksOn
!insert (john,research) into WorksOn

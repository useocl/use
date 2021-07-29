-- Opens the class diagram:
-- open examples/Others/ReflexiveAssociation/ReflexiveAssociation.use

-- Creates the object diagram:
-- read examples/Others/ReflexiveAssociation/ReflexiveAssociation.cmd

!create n1:Node
!create n2:Node
!insert (n1,n2) into Connects
!insert (n2,n1) into Connects
!insert (n1,n1) into Connects

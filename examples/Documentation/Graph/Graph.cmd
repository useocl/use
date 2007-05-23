-- Opens the class diagram:
-- open examples/Documentation/Graph/Graph.use

-- Creates the object diagram:
-- read examples/Documentation/Graph/Graph.cmd

!create n1 : Node

-- this call satisfies both postconditions
!openter n1 newTarget()
!create n2 : Node
!insert (n1,n2) into Edge
!opexit

-- postcondition oneNewTarget fails, because n3 is not linked to n1
!openter n1 newTarget()
!create n3 : Node
!opexit

-- postcondition targetNodeIsNew fails, because n3 has already been create above
!openter n1 newTarget()
!insert (n1,n3) into Edge
!opexit

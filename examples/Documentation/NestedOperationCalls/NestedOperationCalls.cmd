-- Opens the class diagram:
-- open examples/Documentation/NestedOperationCalls/NestedOperationCalls.use

-- Creates the object diagram:
-- read examples/Documentation/NestedOperationCalls/NestedOperationCalls.cmd

!create r : Rec

!openter r fac(3)
!openter r fac(2)
!openter r fac(1)
!opexit 1
!opexit 2
!opexit 6

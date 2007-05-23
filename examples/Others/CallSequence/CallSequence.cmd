-- Opens the class diagram:
-- open examples/Others/CallSequence/CallSequence.use

-- Creates the object diagram:
-- read examples/Others/CallSequence/CallSequence.cmd

!create c1 : C1
!create c2 : C2

!openter c1 op1()
!openter c2 op2()
!openter c1 op1()
!openter c2 op2()
!opexit
!opexit
!opexit
!opexit

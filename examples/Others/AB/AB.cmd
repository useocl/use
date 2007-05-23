-- Opens the class diagram:
-- open examples/Others/AB/AB.use

-- Creates the object diagram:
-- read examples/Others/AB/AB.cmd

-- create initial state:
!create a : A
!create b1 : B
!set b1.c := 1
!insert (a, b1) into R
info vars

-- enter operation, check preconditions, save current state
!openter a op(2)
info vars

-- effect of op()
!set b1.c := 2
!delete (a,b1) from R
!create b2 : B
!set b2.c := 0
!insert (a, b2) into R

-- exit operation, check postconditions with saved and current state
!opexit
info vars

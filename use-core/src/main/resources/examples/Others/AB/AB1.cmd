-- Opens the class diagram:
-- open examples/Others/AB/AB.use

-- Creates the object diagram:
-- read examples/Others/AB/AB1.cmd

-- create initial state:
!create a : A
!create b1 : B
!set b1.c := 1
!insert (a, b1) into R

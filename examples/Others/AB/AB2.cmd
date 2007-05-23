-- Creates the object diagram:
-- read examples/Others/AB/AB2.cmd

-- effect of op()
!set b1.c := 2
!delete (a,b1) from R
!create b2 : B
!set b2.c := 0
!insert (a, b2) into R

-- Opens the class diagram:
-- open examples/Others/RecursiveOperations/RecursiveOperations.use

-- Creates the object diagram:
-- read examples/Others/RecursiveOperations/RecursiveOperations.cmd

!create r : Rec

?r.fac(4)

??r.fac(2)

-- the following triggers a core dump in the blackdown jdk1.3
-- ?r.recurse()

-- Opens the class diagram:
-- open examples/Others/Job/Job.use

-- Creates the object diagram:
-- read examples/Others/Job/Job.cmd

!create j:Job
!create p:Person
!create s:Student
!create e:Employee
!insert (s,j) into Works
!destroy p
!destroy e
!delete (s,j) from Works

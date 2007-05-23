-- Creates the object diagram:
-- read examples/Papers/2001/RichtersPhDThesis/EmployeeExtended/SplitCommands/EmployeeExtended2.cmd

-- Employee john
!create john : Employee
!set john.name := 'John'
!set john.salary := 4000

-- Employee frank
!create frank : Employee
!set frank.name := 'Frank'
!set frank.salary := 4500

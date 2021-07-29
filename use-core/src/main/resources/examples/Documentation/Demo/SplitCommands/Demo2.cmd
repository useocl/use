-- Creates the object diagram:
-- read examples/Documentation/Demo/SplitCommands/Demo2.cmd

-- Employee john
!create john : Employee
!set john.name := 'john'
!set john.salary := 4000

-- Employee frank
!create frank : Employee
!set frank.name := 'frank'
!set frank.salary := 4500

-- Creates the object diagram:
-- read examples/Papers/2001/RichtersPhDThesis/EmployeeExtended/SplitCommands/EmployeeExtendedRaiseSalary.cmd

-- call operation raiseSalary
!openter frank raiseSalary(200)
!set self.salary := self.salary + amount
-- provide result value on exit
!opexit 4700

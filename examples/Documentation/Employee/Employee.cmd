-- Opens the class diagram:
-- open examples/Documentation/Employee/Employee.use

-- Creates the object diagram:
-- read examples/Documentation/Employee/Employee.cmd

-- create a company and a person
!create ibm : Company
!create joe : Person
!set joe.name := 'Joe'
!set joe.age := 23

-- enter operation, check preconditions, save current state
!openter ibm hire(joe)

-- effect of hire
!insert (p, ibm) into WorksFor
!set p.salary := 2000

-- exit operation, check postconditions with state saved at operation
-- entry time and current state 
!opexit

!openter joe raiseSalary(0.1)
!set self.salary := self.salary + self.salary * rate
-- provide result value on exit
!opexit 2200

!openter ibm fire(joe)
!delete (p, ibm) from WorksFor
!opexit

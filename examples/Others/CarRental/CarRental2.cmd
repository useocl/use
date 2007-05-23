-- Opens the class diagram:
-- open examples/Others/CarRental/CarRental2.use

-- Creates the object diagram:
-- read examples/Others/CarRental/CarRental2.cmd

-- Branch
!create branch : Branch
!set branch.location := 'Bremen'

-- Car
!create car1, car2, car3, car4 : Car
!set car1.id := 'Red car'
!set car2.id := 'Blue car'
!set car3.id := 'Silver car'
!set car4.id := 'Gold car'
!insert (branch, car1) into Fleet
!insert (branch, car2) into Fleet
!insert (branch, car3) into Fleet
!insert (branch, car4) into Fleet

-- CarGroup
!create cargroup1, cargroup2, cargroup3 : CarGroup
!set cargroup1.kind := 'compact'
!set cargroup2.kind := 'intermediate'
!set cargroup3.kind := 'luxury'

-- Quality
!insert(cargroup1, cargroup2) into Quality
!insert(cargroup2, cargroup3) into Quality

-- Classification
!insert(cargroup1, car1) into Classification
!insert(cargroup1, car2) into Classification
!insert(cargroup2, car3) into Classification
!insert(cargroup3, car4) into Classification

-- Customer
!create customer1 : Customer
!set customer1.firstname := 'Frank'
!set customer1.lastname := 'Black'
!set customer1.age := 36
!set customer1.address := 'Hamburg'

-- Employee (manager)
!create manager : Employee
!set manager.firstname := 'Joe'
!set manager.lastname := 'White'
!set manager.age := 48

!insert(manager, branch) into Management

-- Rental
!create rental1 : Rental
!insert (rental1, branch) into Provider
!insert (rental1, customer1) into Booking
!insert (rental1, cargroup1) into Reservation

-- ServiceDepot
!create servicedepot1 : ServiceDepot

-- Check
!create check1 : Check
!set check1.description := 'Brakes'
!insert (servicedepot1, check1, car1) into Maintenance

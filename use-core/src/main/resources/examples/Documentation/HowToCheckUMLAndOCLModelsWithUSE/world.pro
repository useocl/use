use> open civstat.use

use> gen start -r 2960 civstat.assl world(7,9,6)
use> gen result
     Random number generator was initialized with 2960.
     Checked 1 snapshots.
     Result: Valid state found.
     Commands to produce the valid state:
     !create Person1,Person2,Person3,Person4,Person5,Person6,Person7 : Person
     !create Person8,Person9,Person10,Person11,Person12,Person13,Person14,Person15,Person16 : Person
     !set Person1.name := 'Flo'
     !set Person1.civstat := #single
     !set Person1.gender := #female
     !set Person1.alive := true
     !set Person2.name := 'Cam'
     !set Person2.civstat := #single
     !set Person2.gender := #female
     !set Person2.alive := true
     !set Person3.name := 'Hao'
     !set Person3.civstat := #single
     !set Person3.gender := #female
     !set Person3.alive := true
     !set Person4.name := 'Yan'
     !set Person4.civstat := #single
     !set Person4.gender := #female
     !set Person4.alive := true
     !set Person5.name := 'Gen'
     !set Person5.civstat := #single
     !set Person5.gender := #female
     !set Person5.alive := true
     !set Person6.name := 'Tip'
     !set Person6.civstat := #single
     !set Person6.gender := #female
     !set Person6.alive := true
     !set Person7.name := 'Pam'
     !set Person7.civstat := #single
     !set Person7.gender := #female
     !set Person7.alive := true
     !set Person8.name := 'Pat'
     !set Person8.civstat := #single
     !set Person8.gender := #male
     !set Person8.alive := true
     !set Person9.name := 'Ole'
     !set Person9.civstat := #single
     !set Person9.gender := #male
     !set Person9.alive := true
     !set Person10.name := 'Jan'
     !set Person10.civstat := #single
     !set Person10.gender := #male
     !set Person10.alive := true
     !set Person11.name := 'Max'
     !set Person11.civstat := #single
     !set Person11.gender := #male
     !set Person11.alive := true
     !set Person12.name := 'Wei'
     !set Person12.civstat := #single
     !set Person12.gender := #male
     !set Person12.alive := true
     !set Person13.name := 'Dan'
     !set Person13.civstat := #single
     !set Person13.gender := #male
     !set Person13.alive := true
     !set Person14.name := 'Hal'
     !set Person14.civstat := #single
     !set Person14.gender := #male
     !set Person14.alive := true
     !set Person15.name := 'Eli'
     !set Person15.civstat := #single
     !set Person15.gender := #male
     !set Person15.alive := true
     !set Person16.name := 'Vic'
     !set Person16.civstat := #single
     !set Person16.gender := #male
     !set Person16.alive := true
     !set Person7.civstat := #married
     !set Person12.civstat := #married
     !insert (Person7,Person12) into Marriage
     !set Person6.civstat := #married
     !set Person15.civstat := #married
     !insert (Person6,Person15) into Marriage
     !set Person5.civstat := #married
     !set Person9.civstat := #married
     !insert (Person5,Person9) into Marriage
     !set Person4.civstat := #married
     !set Person13.civstat := #married
     !insert (Person4,Person13) into Marriage
     !set Person2.civstat := #married
     !set Person8.civstat := #married
     !insert (Person2,Person8) into Marriage
     !set Person1.civstat := #married
     !set Person10.civstat := #married
     !insert (Person1,Person10) into Marriage
use> gen result accept
     Generated result (system state) accepted.

use> check
     checking structure...
     checking invariants...
     checking invariant (1) `Person::attributesDefined': OK.
     checking invariant (2) `Person::femaleHasNoWife': OK.
     checking invariant (3) `Person::maleHasNoHusband': OK.
     checking invariant (4) `Person::nameCapitalThenSmallLetters': OK.
     checking invariant (5) `Person::nameIsUnique': OK.
     checked 5 invariants in 0.016s, 0 failures.

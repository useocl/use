use> open civstat.use

use> gen flags Person::attributesDefined +d
use> gen flags Person::femaleHasNoWife +d
use> gen flags Person::maleHasNoHusband +d
use> gen flags Person::nameCapitalThenSmallLetters +d
use> gen flags Person::nameIsUnique +d

use> gen start -s -r 2115 civstat.assl crowd(3,4,2)
use> gen result
     Random number generator was initialized with 2115.
     Checked 1 snapshots.
     Result: Valid state found.
     Commands to produce the valid state:
     !create Person1,Person2,Person3 : Person
     !create Person4,Person5,Person6,Person7 : Person
     !set @Person1.name := 'Day'
     !set @Person1.civstat := #widowed
     !set @Person1.gender := #male
     !set @Person1.alive := false
     !set @Person2.name := 'Jen'
     !set @Person2.civstat := #widowed
     !set @Person2.gender := #male
     !set @Person2.alive := true
     !set @Person3.name := 'Ina'
     !set @Person3.civstat := #married
     !set @Person3.gender := #male
     !set @Person3.alive := true
     !set @Person4.name := 'Jan'
     !set @Person4.civstat := #divorced
     !set @Person4.gender := #female
     !set @Person4.alive := false
     !set @Person5.name := 'Hal'
     !set @Person5.civstat := #divorced
     !set @Person5.gender := #female
     !set @Person5.alive := true
     !set @Person6.name := 'Hal'
     !set @Person6.civstat := #married
     !set @Person6.gender := #female
     !set @Person6.alive := true
     !set @Person7.name := 'Bob'
     !set @Person7.civstat := #single
     !set @Person7.gender := #male
     !set @Person7.alive := true
     !insert (Person2,Person7) into Marriage
     !insert (Person2,Person4) into Marriage
use> gen result accept
     Generated result (system state) accepted.
use> check
     checking structure...
     Multiplicity constraint violation in association `Marriage': Object
       `Person2' of class `Person' is connected to 2 objects of class 
       `Person' but the multiplicity is specified as `0..1'.
     checking invariants...
     checking invariant (1) `Person::attributesDefined': OK.
     checking invariant (2) `Person::femaleHasNoWife': FAILED.
     checking invariant (3) `Person::maleHasNoHusband': N/A
     checking invariant (4) `Person::nameCapitalThenSmallLetters': OK.
     checking invariant (5) `Person::nameIsUnique': FAILED.
     checked 5 invariants in 0.047s, 2 failures.

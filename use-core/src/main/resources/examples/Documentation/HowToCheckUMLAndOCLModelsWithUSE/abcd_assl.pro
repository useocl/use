use> open civstat.use

use> !create ada:Person

use> !openter ada birth('Ada',#female)
     precondition `freshUnlinkedPerson' is true
use> gen start civstat.assl Person_birth(self,aName,aGender)
use> gen result
     Random number generator was initialized with 5871.
     Checked 1 snapshots.
     Result: Valid state found.
     Commands to produce the valid state:
     !set @ada.name := 'Ada'
     !set @ada.civstat := #single
     !set @ada.gender := #female
     !set @ada.alive := true
use> gen result accept
     Generated result (system state) accepted.
use> !opexit
     postcondition `nameAssigned' is true
     postcondition `civstatAssigned' is true
     postcondition `genderAssigned' is true
     postcondition `isAliveAssigned' is true

use> !create bob:Person

use> !openter bob birth('Bob',#male)
     precondition `freshUnlinkedPerson' is true
use> gen start civstat.assl Person_birth(self,aName,aGender)
use> gen result
     Random number generator was initialized with 1308.
     Checked 1 snapshots.
     Result: Valid state found.
     Commands to produce the valid state:
     !set @bob.name := 'Bob'
     !set @bob.civstat := #single
     !set @bob.gender := #male
     !set @bob.alive := true
use> gen result accept
     Generated result (system state) accepted.
use> !opexit
     postcondition `nameAssigned' is true
     postcondition `civstatAssigned' is true
     postcondition `genderAssigned' is true
     postcondition `isAliveAssigned' is true

use> !openter ada marry(bob)
     precondition `aSpouseDefined' is true
     precondition `isAlive' is true
     precondition `aSpouseAlive' is true
     precondition `isUnmarried' is true
     precondition `aSpouseUnmarried' is true
     precondition `differentGenders' is true
use> gen start civstat.assl Person_marry(self,aSpouse)
use> gen result
     Random number generator was initialized with 8047.
     Checked 1 snapshots.
     Result: Valid state found.
     Commands to produce the valid state:
     !set @ada.civstat := #married
     !set @bob.civstat := #married
     !insert (ada,bob) into Marriage
use> gen result accept
     Generated result (system state) accepted.
use> !opexit
     postcondition `isMarried' is true
     postcondition `femaleHasMarriedHusband' is true
     postcondition `maleHasMarriedWife' is true

use> !create cyd:Person

use> !openter cyd birth('Cyd',#male)
     precondition `freshUnlinkedPerson' is true
use> gen start civstat.assl Person_birth(self,aName,aGender)
use> gen result
     Random number generator was initialized with 2430.
     Checked 1 snapshots.
     Result: Valid state found.
     Commands to produce the valid state:
     !set @cyd.name := 'Cyd'
     !set @cyd.civstat := #single
     !set @cyd.gender := #male
     !set @cyd.alive := true
use> gen result accept
     Generated result (system state) accepted.
use> !opexit
     postcondition `nameAssigned' is true
     postcondition `civstatAssigned' is true
     postcondition `genderAssigned' is true
     postcondition `isAliveAssigned' is true

use> !openter ada divorce()
     precondition `isMarried' is true
     precondition `isAlive' is true
     precondition `husbandAlive' is true
     precondition `wifeAlive' is true
use> gen start civstat.assl Person_divorce(self)
use> gen result
     Random number generator was initialized with 3210.
     Checked 1 snapshots.
     Result: Valid state found.
     Commands to produce the valid state:
     !set @ada.civstat := #divorced
     !set @bob.civstat := #divorced
     !delete (ada,bob) from Marriage
use> gen result accept
     Generated result (system state) accepted.
use> !opexit
     postcondition `isDivorced' is true
     postcondition `husbandDivorced' is true
     postcondition `wifeDivorced' is true

use> !openter cyd marry(ada)
     precondition `aSpouseDefined' is true
     precondition `isAlive' is true
     precondition `aSpouseAlive' is true
     precondition `isUnmarried' is true
     precondition `aSpouseUnmarried' is true
     precondition `differentGenders' is true
use> gen start civstat.assl Person_marry(self,aSpouse)
use> gen result
     Random number generator was initialized with 7593.
     Checked 1 snapshots.
     Result: Valid state found.
     Commands to produce the valid state:
     !set @cyd.civstat := #married
     !set @ada.civstat := #married
     !insert (ada,cyd) into Marriage
use> gen result accept
     Generated result (system state) accepted.
use> !opexit
     postcondition `isMarried' is true
     postcondition `femaleHasMarriedHusband' is true
     postcondition `maleHasMarriedWife' is true

use> !create dan:Person

use> !openter dan birth('Dan',#male)
     precondition `freshUnlinkedPerson' is true
use> gen start civstat.assl Person_birth(self,aName,aGender)
use> gen result
     Random number generator was initialized with 3483.
     Checked 1 snapshots.
     Result: Valid state found.
     Commands to produce the valid state:
     !set @dan.name := 'Dan'
     !set @dan.civstat := #single
     !set @dan.gender := #male
     !set @dan.alive := true
use> gen result accept
     Generated result (system state) accepted.
use> !opexit
     postcondition `nameAssigned' is true
     postcondition `civstatAssigned' is true
     postcondition `genderAssigned' is true
     postcondition `isAliveAssigned' is true

use> !openter cyd death()
     precondition `isAlive' is true
use> gen start civstat.assl Person_death(self)
use> gen result
     Random number generator was initialized with 3761.
     Checked 1 snapshots.
     Result: Valid state found.
     Commands to produce the valid state:
     !set @cyd.alive := false
     !set @ada.civstat := #widowed
     !delete (ada,cyd) from Marriage
use> gen result accept
     Generated result (system state) accepted.
use> !opexit
     postcondition `notAlive' is true
     postcondition `husbandWidowed' is true
     postcondition `wifeWidowed' is true

use> open civstat.use

use> !create ali:Person
use> !set ali.name:='Ali'
use> !set ali.civstat:=#married
use> !set ali.gender:=#male
use> !set ali.alive:=true

use> !create bob:Person
use> !set bob.name:='Bob'
use> !set bob.civstat:=#married
use> !set bob.gender:=#male
use> !set bob.alive:=true

use> !insert (ali,bob) into Marriage

use> check
     checking structure...
     checking invariants...
     checking invariant (1) `Person::attributesDefined': OK.
     checking invariant (2) `Person::femaleHasNoWife': OK.
     checking invariant (3) `Person::maleHasNoHusband': FAILED.
       -> false : Boolean
     checking invariant (4) `Person::nameCapitalThenSmallLetters': OK.
     checking invariant (5) `Person::nameIsUnique': OK.
     checked 5 invariants in 0.016s, 1 failure.

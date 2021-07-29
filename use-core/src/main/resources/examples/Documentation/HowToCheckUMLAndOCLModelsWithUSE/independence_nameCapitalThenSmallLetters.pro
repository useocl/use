use> open civstat.use

use> !create ada:Person
use> !set ada.name:='ADA'
use> !set ada.civstat:=#single
use> !set ada.gender:=#female
use> !set ada.alive:=true

use> check
     checking structure...
     checking invariants...
     checking invariant (1) `Person::attributesDefined': OK.
     checking invariant (2) `Person::femaleHasNoWife': OK.
     checking invariant (3) `Person::maleHasNoHusband': OK.
     checking invariant (4) `Person::nameCapitalThenSmallLetters': FAILED.
       -> false : Boolean
     checking invariant (5) `Person::nameIsUnique': OK.
     checked 5 invariants in 0.063s, 1 failure.

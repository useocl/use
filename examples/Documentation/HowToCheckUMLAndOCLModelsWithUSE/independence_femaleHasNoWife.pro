use> open civstat.use

use> !create ada:Person
use> !set ada.name:='Ada'
use> !set ada.civstat:=#married
use> !set ada.gender:=#female
use> !set ada.alive:=true

use> !create bel:Person
use> !set bel.name:='Bel'
use> !set bel.civstat:=#married
use> !set bel.gender:=#female
use> !set bel.alive:=true

use> !insert (ada,bel) into Marriage

use> check
     checking structure...
     checking invariants...
     checking invariant (1) `Person::attributesDefined': OK.
     checking invariant (2) `Person::femaleHasNoWife': FAILED.
       -> false : Boolean
     checking invariant (3) `Person::maleHasNoHusband': OK.
     checking invariant (4) `Person::nameCapitalThenSmallLetters': OK.
     checking invariant (5) `Person::nameIsUnique': OK.
     checked 5 invariants in 0.015s, 1 failure.

use> open civstat.use

use> !create ada1:Person
use> !set ada1.name:='Ada'
use> !set ada1.civstat:=#single
use> !set ada1.gender:=#female
use> !set ada1.alive:=true

use> !create ada2:Person
use> !set ada2.name:='Ada'
use> !set ada2.civstat:=#single
use> !set ada2.gender:=#female
use> !set ada2.alive:=true

use> check
     checking structure...
     checking invariants...
     checking invariant (1) `Person::attributesDefined': OK.
     checking invariant (2) `Person::femaleHasNoWife': OK.
     checking invariant (3) `Person::maleHasNoHusband': OK.
     checking invariant (4) `Person::nameCapitalThenSmallLetters': OK.
     checking invariant (5) `Person::nameIsUnique': FAILED.
       -> false : Boolean
     checked 5 invariants in 0.016s, 1 failure.

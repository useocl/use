use> open civstat.use

use> !create ada:Person

use> !openter ada birth('Ada',#female)
     precondition `freshUnlinkedPerson' is true
use> read Person_birth.cmd
     Person_birth.cmd> -- Person::birth(aName:String,aGender:Gender)
     Person_birth.cmd> !set self.name:=aName
     Person_birth.cmd> !set self.civstat:=#single
     Person_birth.cmd> !set self.gender:=aGender
     Person_birth.cmd> !set self.alive:=true

use> !opexit
     postcondition `nameAssigned' is true
     postcondition `civstatAssigned' is true
     postcondition `genderAssigned' is true
     postcondition `isAliveAssigned' is true

use> !create bob:Person

use> !openter bob birth('Bob',#male)
     precondition `freshUnlinkedPerson' is true
use> read Person_birth.cmd
     Person_birth.cmd> -- Person::birth(aName:String,aGender:Gender)
     Person_birth.cmd> !set self.name:=aName
     Person_birth.cmd> !set self.civstat:=#single
     Person_birth.cmd> !set self.gender:=aGender
     Person_birth.cmd> !set self.alive:=true

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
use> read Person_marry.cmd
     Person_marry.cmd> -- Person::marry(aSpouse:Person)
     Person_marry.cmd> !set self.civstat:=#married
     Person_marry.cmd> !set aSpouse.civstat:=#married
     Person_marry.cmd> !insert
     (if self.gender=#female then self else aSpouse endif,
      if self.gender=#female then aSpouse else self endif) into Marriage

use> !opexit
     postcondition `isMarried' is true
     postcondition `femaleHasMarriedHusband' is true
     postcondition `maleHasMarriedWife' is true

use> !create cyd:Person

use> !openter cyd birth('Cyd',#male)
     precondition `freshUnlinkedPerson' is true
use> read Person_birth.cmd
     Person_birth.cmd> -- Person::birth(aName:String,aGender:Gender)
     Person_birth.cmd> !set self.name:=aName
     Person_birth.cmd> !set self.civstat:=#single
     Person_birth.cmd> !set self.gender:=aGender
     Person_birth.cmd> !set self.alive:=true

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
use> read Person_divorce.cmd
     Person_divorce.cmd> -- Person::divorce()
     Person_divorce.cmd> !set self.civstat:=#divorced
     Person_divorce.cmd> !set self.spouse().civstat:=#divorced
     Person_divorce.cmd> !delete
     (if self.gender=#female then self else self.wife endif,
      if self.gender=#female then self.husband else self endif) from Marriage

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
use> read Person_marry.cmd
     Person_marry.cmd> -- Person::marry(aSpouse:Person)
     Person_marry.cmd> !set self.civstat:=#married
     Person_marry.cmd> !set aSpouse.civstat:=#married
     Person_marry.cmd> !insert
     (if self.gender=#female then self else aSpouse endif,
      if self.gender=#female then aSpouse else self endif) into Marriage

use> !opexit
     postcondition `isMarried' is true
     postcondition `femaleHasMarriedHusband' is true
     postcondition `maleHasMarriedWife' is true

use> !create dan:Person

use> !openter dan birth('Dan',#male)
     precondition `freshUnlinkedPerson' is true
use> read Person_birth.cmd
     Person_birth.cmd> -- Person::birth(aName:String,aGender:Gender)
     Person_birth.cmd> !set self.name:=aName
     Person_birth.cmd> !set self.civstat:=#single
     Person_birth.cmd> !set self.gender:=aGender
     Person_birth.cmd> !set self.alive:=true

use> !opexit
     postcondition `nameAssigned' is true
     postcondition `civstatAssigned' is true
     postcondition `genderAssigned' is true
     postcondition `isAliveAssigned' is true

use> !openter cyd death()
     precondition `isAlive' is true
use> read Person_death_married.cmd
     Person_death_married.cmd> -- Person::death() -- for married Person objects
     Person_death_married.cmd> !set self.alive:=false
     Person_death_married.cmd> !set self.spouse().civstat:=#widowed
     Person_death_married.cmd> !delete 
     (if self.gender=#female then self else self.wife endif,
      if self.gender=#female then self.husband else self endif) from Marriage

use> !opexit
     postcondition `notAlive' is true
     postcondition `husbandWidowed' is true
     postcondition `wifeWidowed' is true

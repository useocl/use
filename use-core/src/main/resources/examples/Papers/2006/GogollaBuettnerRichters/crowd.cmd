-- Opens the class diagram:
-- open civstat.use

-- Creates the object diagram:
-- read crowd.cmd

gen flags Person::attributesDefined +d
gen flags Person::femaleHasNoWife +d
gen flags Person::maleHasNoHusband +d
gen flags Person::nameCapitalThenSmallLetters +d
gen flags Person::nameIsUnique +d

gen start -s -r 2115 civstat.assl crowd(3,4,2)
gen result
gen result accept

?Person.allInstances->exists(p|p.husband->notEmpty and p.wife->notEmpty)

check

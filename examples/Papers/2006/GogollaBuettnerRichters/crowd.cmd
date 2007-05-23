-- Opens the class diagram:
-- open examples/Papers/2006/GogollaBuettnerRichters/civstat.use

-- Creates the object diagram:
-- read examples/Papers/2006/GogollaBuettnerRichters/crowd.cmd

open examples/Papers/2006/GogollaBuettnerRichters/civstat.use

gen flags Person::attributesDefined +d
gen flags Person::femaleHasNoWife +d
gen flags Person::maleHasNoHusband +d
gen flags Person::nameCapitalThenSmallLetters +d
gen flags Person::nameIsUnique +d

gen start -s -r 2115 examples/Papers/2006/GogollaBuettnerRichters/civstat.assl crowd(3,4,2)
gen result
gen result accept

?Person.allInstances->exists(p|p.husband->notEmpty and p.wife->notEmpty)

check

-- read c:/use/civstat/crowd.cmd

open c:/use/civstat/civstat.use

gen flags Person::attributesDefined +d
gen flags Person::femaleHasNoWife +d
gen flags Person::maleHasNoHusband +d
gen flags Person::nameCapitalThenSmallLetters +d
gen flags Person::nameIsUnique +d

gen start -s -r 2115 c:/use/civstat/civstat.assl crowd(3,4,2)
gen result
gen result accept

check

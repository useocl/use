-- read c:/use/civstat/world.cmd

open c:/use/civstat/civstat.use

gen start -r 2960 c:/use/civstat/civstat.assl world(7,9,6)
gen result
gen result accept

check

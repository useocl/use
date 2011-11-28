-- read c:/use/civstat/bigamy.cmd

open c:/use/civstat/civstat.use

gen load c:/use/civstat/bigamy.invs
gen start c:/use/civstat/civstat.assl attemptBigamy()
gen result

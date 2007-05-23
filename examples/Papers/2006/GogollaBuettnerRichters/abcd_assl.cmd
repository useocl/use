-- Opens the class diagram:
-- open examples/Papers/2006/GogollaBuettnerRichters/civstat.use

-- Creates the object diagram:
-- read examples/Papers/2006/GogollaBuettnerRichters/abcd_assl.cmd

!create ada:Person

!openter ada birth('Ada',#female)
gen start examples/Papers/2006/GogollaBuettnerRichters/civstat.assl Person_birth(ada,'Ada',#female)
gen result
gen result accept
!opexit

!create bob:Person

!openter bob birth('Bob',#male)
gen start examples/Papers/2006/GogollaBuettnerRichters/civstat.assl Person_birth(bob,'Bob',#male)
gen result
gen result accept
!opexit

!openter ada marry(bob)
gen start examples/Papers/2006/GogollaBuettnerRichters/civstat.assl Person_marry(ada,bob)
gen result
gen result accept
!opexit

!create cyd:Person

!openter cyd birth('Cyd',#male)
gen start examples/Papers/2006/GogollaBuettnerRichters/civstat.assl Person_birth(cyd,'Cyd',#male)
gen result
gen result accept
!opexit

!openter ada divorce()
gen start examples/Papers/2006/GogollaBuettnerRichters/civstat.assl Person_divorce(ada)
gen result
gen result accept
!opexit

!openter cyd marry(ada)
gen start examples/Papers/2006/GogollaBuettnerRichters/civstat.assl Person_marry(cyd,ada)
gen result
gen result accept
!opexit

!create dan:Person

!openter dan birth('Dan',#male)
gen start examples/Papers/2006/GogollaBuettnerRichters/civstat.assl Person_birth(dan,'Dan',#male)
gen result
gen result accept
!opexit

!openter cyd death()
gen start examples/Papers/2006/GogollaBuettnerRichters/civstat.assl Person_death(cyd)
gen result
gen result accept
!opexit

-- read c:/use/civstat/abcd_assl.cmd

open c:/use/civstat/civstat.use

!create ada:Person

!openter ada birth('Ada',#female)
gen start c:/use/civstat/civstat.assl Person_birth(self,aName,aGender)
gen result
gen result accept
!opexit

!create bob:Person

!openter bob birth('Bob',#male)
gen start c:/use/civstat/civstat.assl Person_birth(self,aName,aGender)
gen result
gen result accept
!opexit

!openter ada marry(bob)
gen start c:/use/civstat/civstat.assl Person_marry(self,aSpouse)
gen result
gen result accept
!opexit

!create cyd:Person

!openter cyd birth('Cyd',#male)
gen start c:/use/civstat/civstat.assl Person_birth(self,aName,aGender)
gen result
gen result accept
!opexit

!openter ada divorce()
gen start c:/use/civstat/civstat.assl Person_divorce(self)
gen result
gen result accept
!opexit

!openter cyd marry(ada)
gen start c:/use/civstat/civstat.assl Person_marry(self,aSpouse)
gen result
gen result accept
!opexit

!create dan:Person

!openter dan birth('Dan',#male)
gen start c:/use/civstat/civstat.assl Person_birth(self,aName,aGender)
gen result
gen result accept
!opexit

!openter cyd death()
gen start c:/use/civstat/civstat.assl Person_death(self)
gen result
gen result accept
!opexit

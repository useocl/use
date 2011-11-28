-- read c:/use/civstat/abcd_cmd.cmd

open c:/use/civstat/civstat.use

!create ada:Person

!openter ada birth('Ada',#female)
read c:/use/civstat/Person_birth.cmd
!opexit

!create bob:Person

!openter bob birth('Bob',#male)
read c:/use/civstat/Person_birth.cmd
!opexit

!openter ada marry(bob)
read c:/use/civstat/Person_marry.cmd
!opexit

!create cyd:Person

!openter cyd birth('Cyd',#male)
read c:/use/civstat/Person_birth.cmd
!opexit

!openter ada divorce()
read c:/use/civstat/Person_divorce.cmd
!opexit

!openter cyd marry(ada)
read c:/use/civstat/Person_marry.cmd
!opexit

!create dan:Person

!openter dan birth('Dan',#male)
read c:/use/civstat/Person_birth.cmd
!opexit

!openter cyd death()
read c:/use/civstat/Person_death_married.cmd
!opexit

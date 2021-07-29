-- Opens the class diagram:
open civstat.use

-- Creates the object diagram:
-- read abcd_cmd.cmd

!create ada:Person

!openter ada birth('Ada',#female)
read Person_birth.cmd
!opexit

!create bob:Person

!openter bob birth('Bob',#male)
read Person_birth.cmd
!opexit

!openter ada marry(bob)
read Person_marry.cmd
!opexit

!create cyd:Person

!openter cyd birth('Cyd',#male)
read Person_birth.cmd
!opexit

!openter ada divorce()
read Person_divorce.cmd
!opexit

!openter cyd marry(ada)
read Person_marry.cmd
!opexit

!create dan:Person

!openter dan birth('Dan',#male)
read Person_birth.cmd
!opexit

!openter cyd death()
read Person_death_married.cmd
!opexit

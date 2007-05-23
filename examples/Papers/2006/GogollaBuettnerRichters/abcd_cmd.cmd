-- Opens the class diagram:
-- open examples/Papers/2006/GogollaBuettnerRichters/civstat.use

-- Creates the object diagram:
-- read examples/Papers/2006/GogollaBuettnerRichters/abcd_cmd.cmd

!create ada:Person

!openter ada birth('Ada',#female)
read examples/Papers/2006/GogollaBuettnerRichters/Person_birth.cmd
!opexit

!create bob:Person

!openter bob birth('Bob',#male)
read examples/Papers/2006/GogollaBuettnerRichters/Person_birth.cmd
!opexit

!openter ada marry(bob)
read examples/Papers/2006/GogollaBuettnerRichters/Person_marry.cmd
!opexit

!create cyd:Person

!openter cyd birth('Cyd',#male)
read examples/Papers/2006/GogollaBuettnerRichters/Person_birth.cmd
!opexit

!openter ada divorce()
read examples/Papers/2006/GogollaBuettnerRichters/Person_divorce.cmd
!opexit

!openter cyd marry(ada)
read examples/Papers/2006/GogollaBuettnerRichters/Person_marry.cmd
!opexit

!create dan:Person

!openter dan birth('Dan',#male)
read examples/Papers/2006/GogollaBuettnerRichters/Person_birth.cmd
!opexit

!openter cyd death()
read examples/Papers/2006/GogollaBuettnerRichters/Person_death_married.cmd
!opexit

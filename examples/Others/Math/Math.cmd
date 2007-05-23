-- Opens the class diagram:
-- open examples/Others/Math/Math.use

-- Creates the object diagram:
-- read examples/Others/Math/Math.cmd

!create m : Math

!openter m gcd(1,1)
!opexit 1
!openter m gcd(2,3)
!opexit 1
!openter m gcd(6,4)
!opexit 2
!openter m gcd(6,3)
!opexit 3
!openter m gcd(12,16)
!opexit 4

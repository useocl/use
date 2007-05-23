-- Opens the class diagram:
-- open examples/Others/SwapLinks/SwapLinks.use

-- Creates the object diagram:
-- read examples/Others/SwapLinks/SwapLinks.cmd

-- $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $

!create a:A
!create b1:B
!create b2:B
!insert (a,b1) into R1
!insert (a,b2) into R2

-- swap links

!openter a swap_b()
!let tmp_b1 : B = self.rb1
!delete (self,self.rb1) from R1
!insert (self,self.rb2) into R1
!delete (self,self.rb2) from R2
!insert (self,tmp_b1) into R2
!opexit

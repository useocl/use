-- - - - - - - - - - - - - - - - - - - - - - - - - - - -  Copy::return()
!set self.numReturns:=self.numReturns+1
!delete (self.user,self) from Borrows

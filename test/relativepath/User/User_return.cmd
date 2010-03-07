-- - - - - - - - - - - - - - - - - - - - - - -  User::return(aCopy:Copy)
!set aCopy.numReturns:=aCopy.numReturns+1
!delete (self,aCopy) from Borrows

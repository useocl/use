-- - - - - - - - - - - - - - - Copy::init(aSignature:String, aBook:Book)
!set self.signature:=aSignature
!set self.numReturns:=0
!insert (self,aBook) into BelongsTo

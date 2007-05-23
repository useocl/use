-- Truck::move(target:Point)
!set self.trips:=self.trips->including(self.point)
!set self.debt:=self.debt+1
!delete (self,self.point) from Current
!insert (self,target) into Current

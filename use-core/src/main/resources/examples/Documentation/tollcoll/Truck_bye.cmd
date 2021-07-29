-- Truck::bye():Integer
!set self.trips:=self.trips->including(self.point)
!set self.trips:=self.trips->including(oclUndefined(Point))
!delete (self,self.point) from Current


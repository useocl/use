-- Person::death() -- for married Person objects
!set self.alive:=false
!set self.spouse().civstat:=#widowed
!delete (if self.gender=#female then self else self.wife endif,if self.gender=#female then self.husband else self endif) from Marriage

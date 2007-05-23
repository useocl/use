-- Person::divorce()
!set self.civstat:=#divorced
!set self.spouse().civstat:=#divorced
!delete (if self.gender=#female then self else self.wife endif, if self.gender=#female then self.husband else self endif) from Marriage

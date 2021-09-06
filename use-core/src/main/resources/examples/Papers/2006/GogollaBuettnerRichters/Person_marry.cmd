-- Person::marry(aSpouse:Person)
!set self.civstat:=#married
!set aSpouse.civstat:=#married
!insert (if self.gender=#female then self else aSpouse endif, if self.gender=#female then aSpouse else self endif) into Marriage

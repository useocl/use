-- read c:/use/civstat/independence_attributesDefined.cmd

open c:/use/civstat/civstat.use

!create ada:Person
!set ada.name:='Ada'
!set ada.civstat:=#single
!set ada.gender:=#female
!set ada.alive:=oclUndefined(Boolean)

check

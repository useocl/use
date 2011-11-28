-- read c:/use/civstat/independence_femaleHasNoWife.cmd

open c:/use/civstat/civstat.use

!create ada:Person
!set ada.name:='Ada'
!set ada.civstat:=#married
!set ada.gender:=#female
!set ada.alive:=true

!create bel:Person
!set bel.name:='Bel'
!set bel.civstat:=#married
!set bel.gender:=#female
!set bel.alive:=true

!insert (ada,bel) into Marriage

check

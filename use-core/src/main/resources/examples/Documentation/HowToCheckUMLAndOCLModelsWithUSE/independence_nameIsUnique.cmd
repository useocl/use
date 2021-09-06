-- read c:/use/civstat/independence_nameIsUnique.cmd

open c:/use/civstat/civstat.use

!create ada1:Person
!set ada1.name:='Ada'
!set ada1.civstat:=#single
!set ada1.gender:=#female
!set ada1.alive:=true

!create ada2:Person
!set ada2.name:='Ada'
!set ada2.civstat:=#single
!set ada2.gender:=#female
!set ada2.alive:=true

check

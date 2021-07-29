-- read c:/use/civstat/independence_maleHasNoHusband.cmd

open c:/use/civstat/civstat.use

!create ali:Person
!set ali.name:='Ali'
!set ali.civstat:=#married
!set ali.gender:=#male
!set ali.alive:=true

!create bob:Person
!set bob.name:='Bob'
!set bob.civstat:=#married
!set bob.gender:=#male
!set bob.alive:=true

!insert (ali,bob) into Marriage

check

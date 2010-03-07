!create ada:User
!openter ada init('Ada','NY')
read ../User/User_init.cmd
!opexit

------------------------------------------------------------------------

!create date:Book
!openter date init('Intro to DBS',Sequence{'Date'},1983)
open ../Book/Book_init.cmd
!opexit

!create elNa:Book
!openter elNa init('Funds of DBS',Sequence{'Elmasri','Navathe'},1994)
read ../Book/Book_init.cmd
!opexit

------------------------------------------------------------------------

!create dbs42:Copy
!openter dbs42 init('DBS42',date)
open ../Copy/Copy_init.cmd
!opexit

!create dbs43:Copy
!openter dbs43 init('DBS43',date)
read ../Copy/Copy_init.cmd
!opexit

!create dbs52:Copy
!openter dbs52 init('DBS52',elNa)
read ../Copy/Copy_init.cmd
!opexit

!create dbs53:Copy
!openter dbs53 init('DBS53',elNa)
read ../Copy/Copy_init.cmd
!opexit

------------------------------------------------------------------------

!openter dbs42 borrow(ada)
read ../Copy/Copy_borrow.cmd
!opexit

!openter dbs42 return()
read ../Copy/Copy_return.cmd
!opexit

!openter dbs42 borrow(ada)
read ../Copy/Copy_borrow.cmd
!opexit

!openter dbs42 return()
read ../Copy/Copy_return.cmd
!opexit

!openter dbs43 borrow(ada)
read ../Copy/Copy_borrow.cmd
!opexit

!openter dbs43 return()
read ../Copy/Copy_return.cmd
!opexit

!openter dbs43 borrow(ada)
read ../Copy/Copy_borrow.cmd
!opexit

!openter dbs43 return()
read ../Copy/Copy_return.cmd
!opexit

!openter dbs43 borrow(ada)
read ../Copy/Copy_borrow.cmd
!opexit

!openter dbs52 borrow(ada)
read ../Copy/Copy_borrow.cmd
!opexit

!openter dbs52 return()
read ../Copy/Copy_return.cmd
!opexit

!openter dbs52 borrow(ada)
read ../Copy/Copy_borrow.cmd
!opexit

!openter dbs52 return()
read ../Copy/Copy_return.cmd
!opexit

!openter dbs52 borrow(ada)
read ../Copy/Copy_borrow.cmd
!opexit

------------------------------------------------------------------------

!create c:Clean
!set c.threshold:=2
!openter c exec()
!set c.signaturesOfDeletedCopies:=Set{'DBS42'}
!destroy dbs42
!set c.copiesToBeDeleted:=Set{dbs43,dbs52}
!opexit

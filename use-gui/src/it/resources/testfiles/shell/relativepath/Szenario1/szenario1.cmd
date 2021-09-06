open ../library.use

!create ada:User
!openter ada init('Ada','NY')
open ../User/User_init.cmd
!opexit

!create bob:User
!openter bob init('Bob','CA')
open ../User/User_init.cmd
!opexit

!create date:Book
!openter date init('Intro to DBS',Sequence{'Date'},1983)
open ../Book/Book_init.cmd
!opexit

!create dbs42:Copy
!openter dbs42 init('DBS42',date)
open ../Copy/Copy_init.cmd
!opexit

!openter dbs42 borrow(ada)
open ../Copy/Copy_borrow.cmd
!opexit

!create elNa:Book
!openter elNa init('Funds of DBS',Sequence{'Elmasri','Navathe'},1994)
open ../Book/Book_init.cmd
!opexit

!create dbs43:Copy
!openter dbs43 init('DBS43',date)
open ../Copy/Copy_init.cmd
!opexit

!create dbs52:Copy
!openter dbs52 init('DBS52',elNa)
open ../Copy/Copy_init.cmd
!opexit

!openter bob borrow(dbs43)
open ../User/User_borrow.cmd
!opexit

!openter dbs52 borrow(ada)
open ../Copy/Copy_borrow.cmd
!opexit

!openter dbs52 return()
open ../Copy/Copy_return.cmd
!opexit

!openter dbs43.user return(dbs43)
open ../User/User_return.cmd
!opexit

!create ok:OnlyKeyed
!create of:OnlyFormatted

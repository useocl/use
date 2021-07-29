-- getField(iRow, iCol)
open Sudoku.use
open CreateBoard_cmds.cmd

!set board.getField(1, 1).value := null
!set board.getField(1, 2).value := null
!set board.getField(1, 3).value := null
!set board.getField(1, 4).value := null
!set board.getField(1, 5).value := null
!set board.getField(1, 6).value := 5
!set board.getField(1, 7).value := 7
!set board.getField(1, 8).value := null
!set board.getField(1, 9).value := null

!set board.getField(2, 1).value := 2
!set board.getField(2, 2).value := null
!set board.getField(2, 3).value := 7
!set board.getField(2, 4).value := 6
!set board.getField(2, 5).value := 1
!set board.getField(2, 6).value := null
!set board.getField(2, 7).value := null
!set board.getField(2, 8).value := 8
!set board.getField(3, 9).value := null

!set board.getField(3, 1).value := null
!set board.getField(3, 2).value := null
!set board.getField(3, 3).value := 8
!set board.getField(3, 4).value := null
!set board.getField(3, 5).value := 4
!set board.getField(3, 6).value := 9
!set board.getField(3, 7).value := null
!set board.getField(3, 8).value := null
!set board.getField(4, 9).value := null
 
!set board.getField(4, 1).value := null
!set board.getField(4, 2).value := null
!set board.getField(4, 3).value := null
!set board.getField(4, 4).value := null
!set board.getField(4, 5).value := null
!set board.getField(4, 6).value := null
!set board.getField(4, 7).value := 6
!set board.getField(4, 8).value := null
!set board.getField(4, 9).value := 8

!set board.getField(5, 1).value := 7
!set board.getField(5, 2).value := 9
!set board.getField(5, 3).value := null
!set board.getField(5, 4).value := null
!set board.getField(5, 5).value := 8
!set board.getField(5, 6).value := null
!set board.getField(5, 7).value := null
!set board.getField(5, 8).value := 5
!set board.getField(5, 9).value := 3

!set board.getField(6, 1).value := 5
!set board.getField(6, 2).value := 8
!set board.getField(6, 3).value := 6
!set board.getField(6, 4).value := null
!set board.getField(6, 5).value := null
!set board.getField(6, 6).value := null
!set board.getField(6, 7).value := 9
!set board.getField(6, 8).value := 7
!set board.getField(6, 9).value := null

!set board.getField(7, 1).value := null
!set board.getField(7, 2).value := 4
!set board.getField(7, 3).value := 3
!set board.getField(7, 4).value := null
!set board.getField(7, 5).value := null
!set board.getField(7, 6).value := 8
!set board.getField(7, 7).value := null
!set board.getField(7, 8).value := 2
!set board.getField(7, 9).value := null

!set board.getField(8, 1).value := null
!set board.getField(8, 2).value := null
!set board.getField(8, 3).value := null
!set board.getField(8, 4).value := 9
!set board.getField(8, 5).value := null
!set board.getField(8, 6).value := null
!set board.getField(8, 7).value := 3
!set board.getField(8, 8).value := null
!set board.getField(8, 9).value := 1
 
!set board.getField(9, 1).value := null
!set board.getField(9, 2).value := 1
!set board.getField(9, 3).value := null
!set board.getField(9, 4).value := null
!set board.getField(9, 5).value := null
!set board.getField(9, 6).value := 2
!set board.getField(9, 7).value := null
!set board.getField(9, 8).value := 9
!set board.getField(9, 9).value := null
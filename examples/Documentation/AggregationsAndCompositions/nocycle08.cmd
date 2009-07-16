open nocycle08.use

!create x:A
!create y:A
!insert (x,y) into AC1
!insert (y,x) into AC2

-- below comes a positive test case for 'noshare'; according the test
-- case classification it should be not here but in a separate
-- use- and cmd-file

!create z:A
!insert (z,y) into AC2

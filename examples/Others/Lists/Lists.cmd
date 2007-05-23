-- Opens the class diagram:
-- open examples/Others/Lists/Lists.use

-- Creates the object diagram:
-- read examples/Others/Lists/Lists.cmd

-- create list
!create l1 : NonEmptyList
!create l2 : NonEmptyList
!create l3 : NonEmptyList
!create el : EmptyList
!insert (l1, l2) into Next_List
!insert (l2, l3) into Next_List
!insert (l3, el) into Next_List

-- create a list for a map
!create lm : NonEmptyList
!insert (lm, el) into Next_List

-- create pair
!create p : Pair
!create o1 : Object
!create o2 : Object
!insert (p, o1) into Pair_Key
!insert (p, o2) into Pair_Value

-- create map
!create m1 : Map
!insert (m1, lm) into Map_List

-- set list element of lm to p 
!insert (lm, p) into List_Element

-- Opens the class diagram:
-- open examples/Others/Tree/Tree.use

-- Creates the object diagram:
-- read examples/Others/Tree/Tree2.cmd

!create one : TreeNode
!set one.name := 'Uno'

!create two : TreeNode
!set two.name := 'Due'

!insert (one,two) into Parentship

!insert (two,one) into Parentship

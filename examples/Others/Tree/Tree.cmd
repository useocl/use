-- Opens the class diagram:
-- open examples/Others/Tree/Tree.use

-- Creates the object diagram:
-- read examples/Others/Tree/Tree.cmd

!create root : TreeNode
!set root.name := 'Eve'

!create child1 : TreeNode
!set child1.name := 'Ada'

!create child2 : TreeNode
!set child2.name := 'Ben'

!create grandchild1 : TreeNode
!set grandchild1.name := 'Cher'

!create grandchild2 : TreeNode
!set grandchild2.name := 'Dan'

!create grandchild3 : TreeNode
!set grandchild3.name := 'Frank'

!create grandchild4 : TreeNode
!set grandchild4.name := 'Gus'

!insert (root,child1) into Parentship

!insert (root,child2) into Parentship

!insert (child1,grandchild1) into Parentship

!insert (child1,grandchild2) into Parentship

!insert (child2,grandchild3) into Parentship

!insert (child2,grandchild4) into Parentship

!create grandgrandchild1 : TreeNode
!set grandgrandchild1.name := 'Hamid'

!insert (grandchild1,grandgrandchild1) into Parentship

!insert (grandgrandchild1,root) into Parentship

!create grandgrandchild2 : TreeNode
!set grandgrandchild2.name := 'Isa'

!insert (grandchild4,grandgrandchild2) into Parentship

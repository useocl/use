-- Opens the class diagram:
-- open examples/Others/Grammar/Grammar.use

-- Creates the object diagram:
-- read examples/Others/Grammar/Grammar.cmd

-- <expr> -> <expr> <operator> <expr>
-- <expr> -> '0'
-- <expr> -> '1'
-- <operator> -> '+'
-- <operator> -> '-'

!create g : Grammar

-- nonterminals
!create expr : NonTerminal
!set expr.name := 'expression'
!insert (g, expr) into Symbols
!insert (g, expr) into StartSymbol

!create operator : NonTerminal
!set operator.name := 'operator'
!insert (g, operator) into Symbols

-- terminals
!create d0 : Terminal
!set d0.name := '0'
!insert (g, d0) into Symbols

!create d1 : Terminal
!set d1.name := '1'
!insert (g, d1) into Symbols

!create op_plus : Terminal
!set op_plus.name := '+'
!insert (g, op_plus) into Symbols

!create op_minus : Terminal
!set op_minus.name := '-'
!insert (g, op_minus) into Symbols

-- production 1
!create p1 : Production
!insert (g, p1) into Productions
!insert (p1, expr) into Production_LHS

!create p1_rhs1 : RhsElement
!set p1_rhs1.position := 1
!insert (p1, p1_rhs1) into Production_RHS
!insert (p1_rhs1, expr) into RhsElement_Symbol

!create p1_rhs2 : RhsElement
!set p1_rhs2.position := 2
!insert (p1, p1_rhs2) into Production_RHS
!insert (p1_rhs2, operator) into RhsElement_Symbol

!create p1_rhs3 : RhsElement
!set p1_rhs3.position := 3
!insert (p1, p1_rhs3) into Production_RHS
!insert (p1_rhs3, expr) into RhsElement_Symbol

-- production 2
!create p2 : Production
!insert (g, p2) into Productions
!insert (p2, expr) into Production_LHS

!create p2_rhs1 : RhsElement
!set p2_rhs1.position := 1
!insert (p2, p2_rhs1) into Production_RHS
!insert (p2_rhs1, d0) into RhsElement_Symbol

-- production 3
!create p3 : Production
!insert (g, p3) into Productions
!insert (p3, expr) into Production_LHS

!create p3_rhs1 : RhsElement
!set p3_rhs1.position := 1
!insert (p3, p3_rhs1) into Production_RHS
!insert (p3_rhs1, d1) into RhsElement_Symbol

-- production 4
!create p4 : Production
!insert (g, p4) into Productions
!insert (p4, operator) into Production_LHS

!create p4_rhs1 : RhsElement
!set p4_rhs1.position := 1
!insert (p4, p4_rhs1) into Production_RHS
!insert (p4_rhs1, op_plus) into RhsElement_Symbol

-- production 5
!create p5 : Production
!insert (g, p5) into Productions
!insert (p5, operator) into Production_LHS

!create p5_rhs1 : RhsElement
!set p5_rhs1.position := 1
!insert (p5, p5_rhs1) into Production_RHS
!insert (p5_rhs1, op_minus) into RhsElement_Symbol

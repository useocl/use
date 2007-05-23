-- Opens the class diagram:
-- open examples/Metamodels/actionsemantics/actionsemantics.use

-- Creates the object diagram:
-- read examples/Metamodels/actionsemantics/actionsemantics.cmd

--------------------------------------------------------------
-- Example of history of group action ( x:=0; (x:=x+1|x:=x+1))
-- Modeled after definition in Response to OMG RFP ad/98-11-01
-- U. of York. October 2000
-- Jose Alvarez
--------------------------------------------------------------

--------------------------------------------------------------
-- Static Model
--------------------------------------------------------------

--------------------------------------------------------------
-- Instances involved
--------------------------------------------------------------

!create Procedure1 : Procedure
!create GroupAction1 : GroupAction
!create x : Variable
!create Integer_0 : DataType

--------------------------------------------------------------
-- x := 0
--------------------------------------------------------------

!create SetXto0 : WriteVariableAction
!set SetXto0.isRemove := false
!create IP_Xto0 : InputPin
!create DF_SX0LvZero : DataFlow
!create OP_LVZero : OutputPin
!create LVZero : LiteralValueAction
!create Zero : DataValue

--------------------------------------------------------------
-- First x := x + 1
--------------------------------------------------------------

!create ApplyIncVar_1 : ApplyFunctionAction
!create IncVar_1 : PrimitiveFunction
!create Integer_1_1 : DataType
!create Integer_1_2 : DataType
!create Integer_1_3 : DataType
!create IPIncVar_1_1 : InputPin
!create IPIncVar_1_2 : InputPin
!create DFAIncV_1RV_1 : DataFlow
!create DFAIncVL_1VOne_1 : DataFlow
!create OPReadVar_1 : OutputPin
!create OPLVaOne_1 : OutputPin
!create ReadVarX_1 : ReadVariableAction
!create LVOne_1 : LiteralValueAction
!create One_1 : DataValue
!create OPIncVar_1 : OutputPin
!create DFAIV_1SetX_1 : DataFlow
!create IPSetX_1 : InputPin
!create SetX_1 : WriteVariableAction

--------------------------------------------------------------
-- Second x := x + 1
--------------------------------------------------------------

!create ApplyIncVar_2 : ApplyFunctionAction
!create IncVar_2 : PrimitiveFunction
!create Integer_2_1 : DataType
!create Integer_2_2 : DataType
!create Integer_2_3 : DataType
!create IPIncVar_2_1 : InputPin
!create IPIncVar_2_2 : InputPin
!create DFAIncV_2RV_2 : DataFlow
!create DFAIncVL_2VOne_2 : DataFlow
!create OPReadVar_2 : OutputPin
!create OPLVaOne_2 : OutputPin
!create ReadVarX_2 : ReadVariableAction
!create LVOne_2 : LiteralValueAction
!create One_2 : DataValue
!create OPIncVar_2 : OutputPin
!create DFAIV_1SetX_2 : DataFlow
!create IPSetX_2 : InputPin
!create SetX_2 : WriteVariableAction

--------------------------------------------------------------
-- Control flows
--------------------------------------------------------------

!create Xto0ApplyIncVar_1 : ControlFlow
!create Xto0ApplyIncVar_2 : ControlFlow


--------------------------------------------------------------
-- Associations
--------------------------------------------------------------

!insert (Procedure1,GroupAction1) into Procedure_Action
!insert (GroupAction1,x) into GroupAction_Variable
!insert (x,Integer_0) into Variable_Classifier
!insert (GroupAction1,SetXto0) into GroupAction_Action
!insert (GroupAction1,ApplyIncVar_1) into GroupAction_Action
!insert (GroupAction1,ApplyIncVar_2) into GroupAction_Action

--------------------------------------------------------------
-- x := 0
--------------------------------------------------------------

!insert (SetXto0,x) into VariableAction_Variable
!insert (SetXto0,IP_Xto0) into WriteVariableAction_InputPin1
!insert (DF_SX0LvZero,IP_Xto0) into DataFlow_InputPin
!insert (DF_SX0LvZero,OP_LVZero) into DataFlow_OutputPin
!insert (LVZero,OP_LVZero) into LiteralValueAction_OutputPin
!insert (LVZero,Zero) into LiteralValueAction_DataValue

--------------------------------------------------------------
-- First x := x + 1
--------------------------------------------------------------

!insert (ApplyIncVar_1,IncVar_1) into ApplyFunctionAction_PrimitiveFunction
!insert (IncVar_1,Integer_1_1) into PrimitiveFunction_DataType2
!insert (IncVar_1,Integer_1_2) into PrimitiveFunction_DataType1
!insert (IncVar_1,Integer_1_3) into PrimitiveFunction_DataType1
!insert (ApplyIncVar_1,IPIncVar_1_1) into ApplyFunctionAction_InputPin
!insert (ApplyIncVar_1,IPIncVar_1_2) into ApplyFunctionAction_InputPin
!insert (DFAIncV_1RV_1,IPIncVar_1_1) into DataFlow_InputPin
!insert (DFAIncVL_1VOne_1,IPIncVar_1_2) into DataFlow_InputPin
!insert (DFAIncV_1RV_1,OPReadVar_1) into DataFlow_OutputPin
!insert (DFAIncVL_1VOne_1,OPReadVar_1) into DataFlow_OutputPin
!insert (ReadVarX_1,OPReadVar_1) into ReadVariableAction_OutputPin
!insert (ReadVarX_1,x) into VariableAction_Variable
!insert (LVOne_1,OPLVaOne_1) into LiteralValueAction_OutputPin
!insert (LVOne_1,One_1) into LiteralValueAction_DataValue
!insert (ApplyIncVar_1,OPIncVar_1) into ApplyFunctionAction_OutputPin
!insert (DFAIV_1SetX_1, OPIncVar_1) into DataFlow_OutputPin
!insert (DFAIV_1SetX_1, IPSetX_1) into DataFlow_InputPin
!insert (SetX_1, IPSetX_1) into WriteVariableAction_InputPin1
!insert (SetX_1, x) into VariableAction_Variable

--------------------------------------------------------------
-- Second x := x + 1
--------------------------------------------------------------

!insert (ApplyIncVar_2,IncVar_2) into ApplyFunctionAction_PrimitiveFunction
!insert (IncVar_2,Integer_2_1) into PrimitiveFunction_DataType2
!insert (IncVar_2,Integer_2_2) into PrimitiveFunction_DataType1
!insert (IncVar_2,Integer_2_3) into PrimitiveFunction_DataType1
!insert (ApplyIncVar_2,IPIncVar_2_1) into ApplyFunctionAction_InputPin
!insert (ApplyIncVar_2,IPIncVar_2_2) into ApplyFunctionAction_InputPin
!insert (DFAIncV_2RV_2,IPIncVar_2_1) into DataFlow_InputPin
!insert (DFAIncVL_2VOne_2,IPIncVar_2_2) into DataFlow_InputPin
!insert (DFAIncV_2RV_2,OPReadVar_2) into DataFlow_OutputPin
!insert (DFAIncVL_2VOne_2,OPReadVar_2) into DataFlow_OutputPin
!insert (ReadVarX_2,OPReadVar_2) into ReadVariableAction_OutputPin
!insert (ReadVarX_2,x) into VariableAction_Variable
!insert (LVOne_2,OPLVaOne_2) into LiteralValueAction_OutputPin
!insert (LVOne_2,One_2) into LiteralValueAction_DataValue
!insert (ApplyIncVar_2,OPIncVar_2) into ApplyFunctionAction_OutputPin
!insert (DFAIV_1SetX_2, OPIncVar_2) into DataFlow_OutputPin
!insert (DFAIV_1SetX_2, IPSetX_2) into DataFlow_InputPin
!insert (SetX_2, IPSetX_2) into WriteVariableAction_InputPin1
!insert (SetX_2, x) into VariableAction_Variable

--------------------------------------------------------------
-- control flows
--------------------------------------------------------------

!insert (SetXto0,Xto0ApplyIncVar_1) into Action_ControlFlow
!insert (SetXto0,Xto0ApplyIncVar_2) into Action_ControlFlow
!insert (ApplyIncVar_1,Xto0ApplyIncVar_1) into Action_ControlFlow2
!insert (ApplyIncVar_2,Xto0ApplyIncVar_2) into Action_ControlFlow2

--------------------------------------------------------------
-- Dynamic Model (History)
--------------------------------------------------------------

-------------------------------------------------------------
-- procedure history
--------------------------------------------------------------

-------------------------------------------------------------
-- instances
--------------------------------------------------------------

!create proc_history:History
!create pei:ProcedureExecutionIdentity
!create proc_id:ObjectIdentity
!create pes_exec:ProcedureExecutionSnapshot
!set pes_exec.status := #proc_executing
!create pes_comp:ProcedureExecutionSnapshot
!set pes_comp.status := #proc_complete
!create proc_creation:Change
!create proc_exec_comp:Change
!create proc_destruction:Change

-------------------------------------------------------------
-- links
--------------------------------------------------------------

!insert (pei, proc_id) into ProcedureExecutionIdentity_InstanceIdentity
!insert (pei, Procedure1) into ProcedureExecutionIdentity_Procedure
!insert (pes_exec, pei) into ProcedureExecutionSnapshot_ProcedureExecutionIdentity
!insert (pes_comp, pei) into ProcedureExecutionSnapshot_ProcedureExecutionIdentity
!insert (proc_creation, proc_history) into Change_History
!insert (proc_exec_comp, proc_history) into Change_History
!insert (proc_destruction, proc_history) into Change_History
!insert (pes_exec, proc_creation) into Snapshot_Change2
!insert (pes_exec, proc_exec_comp) into Snapshot_Change1
!insert (pes_comp, proc_exec_comp) into Snapshot_Change2
!insert (pes_comp, proc_destruction) into Snapshot_Change1

-------------------------------------------------------------
-- group history
--------------------------------------------------------------

-------------------------------------------------------------
-- instances
--------------------------------------------------------------

!create group_history:History
!create gei:GroupExecutionIdentity
!create ges_wait:GroupExecutionSnapshot
!set ges_wait.status := #waiting
!create ges_ready:GroupExecutionSnapshot
!set ges_ready.status := #ready
!create ges_exec:GroupExecutionSnapshot
!set ges_exec.status := #executing
!create ges_comp:GroupExecutionSnapshot
!set ges_comp.status := #complete
!create group_creation:Step
!set group_creation.time := 0
!create group_wait_ready:Step
!set group_wait_ready.time := 0
!create group_ready_exec:Step
!set group_ready_exec.time := 0
!create group_exec_comp:Step
!set group_exec_comp.time := 0
!create group_destruction: Step
!set group_destruction.time := 0

-------------------------------------------------------------
-- links
--------------------------------------------------------------

!insert (gei, GroupAction1) into ActionExecutionIdentity_Action
!insert (ges_wait, gei) into GroupExecutionSnapshot_GroupExecutionIdentity
!insert (ges_ready, gei) into GroupExecutionSnapshot_GroupExecutionIdentity
!insert (ges_exec, gei) into GroupExecutionSnapshot_GroupExecutionIdentity
!insert (ges_comp, gei) into GroupExecutionSnapshot_GroupExecutionIdentity
!insert (group_creation, group_history) into Change_History
!insert (group_wait_ready, group_history) into Change_History
!insert (group_ready_exec, group_history) into Change_History
!insert (group_exec_comp, group_history) into Change_History
!insert (group_destruction, group_history) into Change_History
!insert (ges_wait, group_creation) into Snapshot_Change2
!insert (ges_wait, group_wait_ready) into Snapshot_Change1
!insert (ges_ready, group_wait_ready) into Snapshot_Change2
!insert (ges_ready, group_ready_exec) into Snapshot_Change1
!insert (ges_exec, group_ready_exec) into Snapshot_Change2
!insert (ges_exec, group_exec_comp) into Snapshot_Change1
!insert (ges_comp, group_exec_comp) into Snapshot_Change2
!insert (ges_comp, group_destruction) into Snapshot_Change1
!insert (gei, pei) into ActionExecutionIdentity_ProcedureExecutionIdentity

!insert (group_exec_comp, proc_exec_comp) into Step_Change
!insert (group_destruction, proc_destruction) into Step_Change

--------------------------------------------------------------
-- x := 0 history
--------------------------------------------------------------

-------------------------------------------------------------
-- instances
--------------------------------------------------------------

!create sx0_history:History
!create sx0ei:ActionExecutionIdentity
!create sx0_wait:ActionExecutionSnapshot
!set sx0_wait.status := #waiting
!create sx0_ready:ActionExecutionSnapshot
!set sx0_ready.status := #ready
!create sx0_exec:ActionExecutionSnapshot
!set sx0_exec.status := #executing
!create sx0_comp:ActionExecutionSnapshot
!set sx0_comp.status := #complete
!create sx0_creation:Step
!set sx0_creation.time := 0
!create sx0_wait_ready:Step
!set sx0_wait_ready.time := 0
!create sx0_ready_exec:Step
!set sx0_ready_exec.time := 0
!create sx0_exec_comp:Step
!set sx0_exec_comp.time := 0
!create sx0_destruction: Step
!set sx0_destruction.time := 0
!create pv1:PinValue
!create pv2:PinValue
!create pv3:PinValue
!create dvid_1: DataValueIdentity
!create dvtype_1: DataType

!create lv0_history:History
!create lv0ei:ActionExecutionIdentity
!create lv0_wait:ActionExecutionSnapshot
!set lv0_wait.status := #waiting
!create lv0_ready:ActionExecutionSnapshot
!set lv0_ready.status := #ready
!create lv0_exec:ActionExecutionSnapshot
!set lv0_exec.status := #executing
!create lv0_comp:ActionExecutionSnapshot
!set lv0_comp.status := #complete
!create lv0_creation:Step
!set lv0_creation.time := 0
!create lv0_wait_ready:Step
!set lv0_wait_ready.time := 0
!create lv0_ready_exec:Step
!set lv0_ready_exec.time := 0
!create lv0_exec_comp:Step
!set lv0_exec_comp.time := 0
!create lv0_destruction: Step
!set lv0_destruction.time := 0
!create pv4:PinValue
!create pv5:PinValue
!create pv6:PinValue
!create dvid_2: DataValueIdentity
!create dvtype_2: DataType

-------------------------------------------------------------
-- links
--------------------------------------------------------------

!insert (sx0ei, SetXto0) into ActionExecutionIdentity_Action
!insert (sx0_wait, sx0ei) into ActionExecutionSnapshot_ActionExecutionIdentity
!insert (sx0_ready, sx0ei) into ActionExecutionSnapshot_ActionExecutionIdentity
!insert (sx0_exec, sx0ei) into ActionExecutionSnapshot_ActionExecutionIdentity
!insert (sx0_comp, sx0ei) into ActionExecutionSnapshot_ActionExecutionIdentity
!insert (sx0_creation, sx0_history) into Change_History
!insert (sx0_wait_ready, sx0_history) into Change_History
!insert (sx0_ready_exec, sx0_history) into Change_History
!insert (sx0_exec_comp, sx0_history) into Change_History
!insert (sx0_destruction, sx0_history) into Change_History
!insert (sx0_wait, sx0_creation) into Snapshot_Change2
!insert (sx0_wait, sx0_wait_ready) into Snapshot_Change1
!insert (sx0_ready, sx0_wait_ready) into Snapshot_Change2
!insert (sx0_ready, sx0_ready_exec) into Snapshot_Change1
!insert (sx0_exec, sx0_ready_exec) into Snapshot_Change2
!insert (sx0_exec, sx0_exec_comp) into Snapshot_Change1
!insert (sx0_comp, sx0_exec_comp) into Snapshot_Change2
!insert (sx0_comp, sx0_destruction) into Snapshot_Change1
!insert (sx0ei, gei) into ActionExecutionIdentity_self
!insert (sx0_ready,pv1) into ActionExecutionSnapshot_PinValue
!insert (sx0_exec,pv2) into ActionExecutionSnapshot_PinValue
!insert (sx0_comp,pv3) into ActionExecutionSnapshot_PinValue
!insert (pv1, IP_Xto0) into PinValue_Pin
!insert (pv2, IP_Xto0) into PinValue_Pin
!insert (pv3, IP_Xto0) into PinValue_Pin
!insert (pv1, dvid_1) into PinValue_InstanceIdentity
!insert (pv2, dvid_1) into PinValue_InstanceIdentity
!insert (pv3, dvid_1) into PinValue_InstanceIdentity
!insert (dvid_1, dvtype_1) into DataValueIdentity_DataType

!insert (lv0ei, LVZero) into ActionExecutionIdentity_Action
!insert (lv0_wait, lv0ei) into ActionExecutionSnapshot_ActionExecutionIdentity
!insert (lv0_ready, lv0ei) into ActionExecutionSnapshot_ActionExecutionIdentity
!insert (lv0_exec, lv0ei) into ActionExecutionSnapshot_ActionExecutionIdentity
!insert (lv0_comp, lv0ei) into ActionExecutionSnapshot_ActionExecutionIdentity
!insert (lv0_creation, lv0_history) into Change_History
!insert (lv0_wait_ready, lv0_history) into Change_History
!insert (lv0_ready_exec, lv0_history) into Change_History
!insert (lv0_exec_comp, lv0_history) into Change_History
!insert (lv0_destruction, lv0_history) into Change_History
!insert (lv0_wait, lv0_creation) into Snapshot_Change2
!insert (lv0_wait, lv0_wait_ready) into Snapshot_Change1
!insert (lv0_ready, lv0_wait_ready) into Snapshot_Change2
!insert (lv0_ready, lv0_ready_exec) into Snapshot_Change1
!insert (lv0_exec, lv0_ready_exec) into Snapshot_Change2
!insert (lv0_exec, lv0_exec_comp) into Snapshot_Change1
!insert (lv0_comp, lv0_exec_comp) into Snapshot_Change2
!insert (lv0_comp, lv0_destruction) into Snapshot_Change1
!insert (lv0ei, sx0ei) into ActionExecutionIdentity_self
!insert (lv0_ready,pv4) into ActionExecutionSnapshot_PinValue
!insert (lv0_exec,pv5) into ActionExecutionSnapshot_PinValue
!insert (lv0_comp,pv6) into ActionExecutionSnapshot_PinValue
!insert (pv4, IP_Xto0) into PinValue_Pin
!insert (pv5, IP_Xto0) into PinValue_Pin
!insert (pv6, IP_Xto0) into PinValue_Pin
!insert (pv4, dvid_2) into PinValue_InstanceIdentity
!insert (pv5, dvid_2) into PinValue_InstanceIdentity
!insert (pv6, dvid_2) into PinValue_InstanceIdentity
!insert (dvid_2, dvtype_2) into DataValueIdentity_DataType


-- ??
!insert (lv0_exec_comp, sx0_exec_comp) into Step_Change

!create sm : StateMachine
!create top : CompositeState
!create t1 : Transition
!create NormalMode : SimpleState
!create BlinkMode : SimpleState
!create se1 : SignalEvent
!create testPower : Operation
!create testPowerMethod : Method
!create Controller : Class
!set Controller.isAbstract = false
!create TrafficLight : Class
!set TrafficLight.isAbstract = false
!create r1 : Reception
!create powerProblem : Signal

!insert (TrafficLight, sm) into ModelElement_StateMachine
!insert (TrafficLight, r1) into Classifier_Feature
!insert (Controller, testPower) into Classifier_Feature
!insert (Controller, testPowerMethod) into Classifier_Feature
!insert (testPower, testPowerMethod) into Operation_Method
!insert (powerProblem, testPower) into Signal_BehavioralFeature
!insert (powerProblem, r1) into Signal_Reception
!insert (se1, powerProblem) into SignalEvent_Signal

!insert (sm, top) into StateMachine_State
!insert (top, NormalMode) into CompositeState_StateVertex
!insert (top, BlinkMode) into CompositeState_StateVertex
!insert (sm, t1) into StateMachine_Transition

!insert (t1, NormalMode) into Transition_StateVertex1
!insert (t1, BlinkMode) into Transition_StateVertex2
!insert (t1, se1) into Transition_Event


check

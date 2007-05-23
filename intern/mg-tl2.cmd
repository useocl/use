!create Controller : Class
!set Controller.isAbstract = false
!set Controller.name = 'Controller'

!create TrafficLight : Class
!set TrafficLight.isAbstract = false
!set TrafficLight.name = 'TrafficLight'

!create MainController : ClassifierRole
!set MainController.name = 'MainController'

!create North : ClassifierRole
!set North.name = 'North'

!create switchLight : Operation
!create italianSwitchLight : Method
!create msg : Message
!create ca : CallAction
!create inter : Interaction
!create coll : Collaboration
!create pkg : Package

!create TRALI248 : Object
!create CONTRO123 : Object

!create st1 : Stimulus

!insert (MainController, Controller) into ClassifierRole_Classifier
!insert (North, TrafficLight) into ClassifierRole_Classifier
!insert (msg, MainController) into Message_ClassifierRole1
!insert (msg, North) into Message_ClassifierRole2

!insert (TrafficLight, switchLight) into Classifier_Feature
!insert (TrafficLight, italianSwitchLight) into Classifier_Feature
!insert (switchLight, italianSwitchLight) into Operation_Method

!insert (msg, ca) into Message_Action
!insert (ca, switchLight) into CallAction_Operation
!insert (inter, msg) into Interaction_Message
!insert (coll, inter) into Collaboration_Interaction

!insert (coll, MainController) into Namespace_ModelElement
!insert (coll, North) into Namespace_ModelElement

!insert (pkg, coll) into Namespace_ModelElement
!insert (pkg, Controller) into Namespace_ModelElement
!insert (pkg, TrafficLight) into Namespace_ModelElement

!insert (TrafficLight, TRALI248) into Classifier_Instance
!insert (Controller, CONTRO123) into Classifier_Instance

!insert (st1, TRALI248) into Stimulus_Instance2
!insert (st1, CONTRO123) into Stimulus_Instance3
!insert (ca, st1) into Action_Stimulus

check

use> --------------------------------------------------------------------
use> !create hh:Point
use> !openter hh init('HH')
     precondition `freshPoint' is true
use> read Point_init.cmd
     Point_init.cmd> -- Point::init(aName:String)
     Point_init.cmd> !set self.name:=aName
use> !opexit
     postcondition `nameAssigned' is true
     postcondition `allPointInvs' is true

use> !create k:Point
use> !openter k init('K')
     precondition `freshPoint' is true
use> read Point_init.cmd
     Point_init.cmd> -- Point::init(aName:String)
     Point_init.cmd> !set self.name:=aName
use> !opexit
     postcondition `nameAssigned' is true
     postcondition `allPointInvs' is true

use> !create h:Point
use> !openter h init('H')
     precondition `freshPoint' is true
use> read Point_init.cmd
     Point_init.cmd> -- Point::init(aName:String)
     Point_init.cmd> !set self.name:=aName
use> !opexit
     postcondition `nameAssigned' is true
     postcondition `allPointInvs' is true

use> !create b:Point
use> !openter b init('B')
     precondition `freshPoint' is true
use> read Point_init.cmd
     Point_init.cmd> -- Point::init(aName:String)
     Point_init.cmd> !set self.name:=aName
use> !opexit
     postcondition `nameAssigned' is true
     postcondition `allPointInvs' is true

use> !create m:Point
use> !openter m init('M')
     precondition `freshPoint' is true
use> read Point_init.cmd
     Point_init.cmd> -- Point::init(aName:String)
     Point_init.cmd> !set self.name:=aName
use> !opexit
     postcondition `nameAssigned' is true
     postcondition `allPointInvs' is true
use> --------------------------------------------------------------------
use> !openter hh southConnect(k)
     precondition `aSouthDefined' is true
     precondition `freshConnection' is true
     precondition `notSelfLink' is true
     precondition `insertionOk' is true
use> read Point_southConnect.cmd
     Point_southConnect.cmd> -- Point::southConnect(aSouth:Point)
     Point_southConnect.cmd> !insert (self,aSouth) into Connection
use> !opexit
     postcondition `connectionAssigned' is true
     postcondition `allPointInvs' is true

use> !openter hh southConnect(h)
     precondition `aSouthDefined' is true
     precondition `freshConnection' is true
     precondition `notSelfLink' is true
     precondition `insertionOk' is true
use> read Point_southConnect.cmd
     Point_southConnect.cmd> -- Point::southConnect(aSouth:Point)
     Point_southConnect.cmd> !insert (self,aSouth) into Connection
use> !opexit
     postcondition `connectionAssigned' is true
     postcondition `allPointInvs' is true

use> !openter hh southConnect(b)
     precondition `aSouthDefined' is true
     precondition `freshConnection' is true
     precondition `notSelfLink' is true
     precondition `insertionOk' is true
use> read Point_southConnect.cmd
     Point_southConnect.cmd> -- Point::southConnect(aSouth:Point)
     Point_southConnect.cmd> !insert (self,aSouth) into Connection
use> !opexit
     postcondition `connectionAssigned' is true
     postcondition `allPointInvs' is true

use> !openter b southConnect(h)
     precondition `aSouthDefined' is true
     precondition `freshConnection' is true
     precondition `notSelfLink' is true
     precondition `insertionOk' is true
use> read Point_southConnect.cmd
     Point_southConnect.cmd> -- Point::southConnect(aSouth:Point)
     Point_southConnect.cmd> !insert (self,aSouth) into Connection
use> !opexit
     postcondition `connectionAssigned' is true
     postcondition `allPointInvs' is true

use> !openter h southConnect(k)
     precondition `aSouthDefined' is true
     precondition `freshConnection' is true
     precondition `notSelfLink' is true
     precondition `insertionOk' is true
use> read Point_southConnect.cmd
     Point_southConnect.cmd> -- Point::southConnect(aSouth:Point)
     Point_southConnect.cmd> !insert (self,aSouth) into Connection
use> !opexit
     postcondition `connectionAssigned' is true
     postcondition `allPointInvs' is true

use> !openter h southConnect(m)
     precondition `aSouthDefined' is true
     precondition `freshConnection' is true
     precondition `notSelfLink' is true
     precondition `insertionOk' is true
use> read Point_southConnect.cmd
     Point_southConnect.cmd> -- Point::southConnect(aSouth:Point)
     Point_southConnect.cmd> !insert (self,aSouth) into Connection
     Point_southConnect.cmd>
use> !opexit
     postcondition `connectionAssigned' is true
     postcondition `allPointInvs' is true

use> !openter k southConnect(m)
     precondition `aSouthDefined' is true
     precondition `freshConnection' is true
     precondition `notSelfLink' is true
     precondition `insertionOk' is true
use> read Point_southConnect.cmd
     Point_southConnect.cmd> -- Point::southConnect(aSouth:Point)
     Point_southConnect.cmd> !insert (self,aSouth) into Connection
use> !opexit
     postcondition `connectionAssigned' is true
     postcondition `allPointInvs' is true

use> !openter b southConnect(m)
     precondition `aSouthDefined' is true
     precondition `freshConnection' is true
     precondition `notSelfLink' is true
     precondition `insertionOk' is true
use> read Point_southConnect.cmd
     Point_southConnect.cmd> -- Point::southConnect(aSouth:Point)
     Point_southConnect.cmd> !insert (self,aSouth) into Connection
use> !opexit
     postcondition `connectionAssigned' is true
     postcondition `allPointInvs' is true
use> --------------------------------------------------------------------
use> !create freds_scania:Truck
use> !openter freds_scania init('HH-MS 42')
     precondition `freshTruck' is true
use> read Truck_init.cmd
     Truck_init.cmd> -- Truck::init(aNum:String)
     Truck_init.cmd> !set self.num:=aNum
     Truck_init.cmd> !set self.trips:=oclEmpty(Sequence(Point))
     Truck_init.cmd> !set self.debt:=0
use> !opexit
     postcondition `numTripsDebtAssigned' is true
     postcondition `allTruckInvs' is true

use> !openter freds_scania enter(hh)
     precondition `tripsOk' is true
     precondition `currentEmpty' is true
use> read Truck_enter.cmd
     Truck_enter.cmd> -- Truck::enter(entry:Point)
     Truck_enter.cmd> !insert (self,entry) into Current
     Truck_enter.cmd> !set self.debt:=1
use> !opexit
     postcondition `debtAssigned' is true
     postcondition `currentAssigned' is true
     postcondition `allTruckInvs' is true

use> !openter freds_scania move(b)
     precondition `currentExists' is true
     precondition `targetReachable' is true
use> read Truck_move.cmd
     Truck_move.cmd> -- Truck::move(target:Point)
     Truck_move.cmd> !set self.trips:=self.trips->including(self.point)
     Truck_move.cmd> !set self.debt:=self.debt+1
     Truck_move.cmd> !delete (self,self.point) from Current
     Truck_move.cmd> !insert (self,target) into Current
use> !opexit
     postcondition `currentAssigned' is true
     postcondition `allTruckInvs' is true

use> !openter freds_scania move(m)
     precondition `currentExists' is true
     precondition `targetReachable' is true
use> read Truck_move.cmd
     Truck_move.cmd> -- Truck::move(target:Point)
     Truck_move.cmd> !set self.trips:=self.trips->including(self.point)
     Truck_move.cmd> !set self.debt:=self.debt+1
     Truck_move.cmd> !delete (self,self.point) from Current
     Truck_move.cmd> !insert (self,target) into Current
use> !opexit
     postcondition `currentAssigned' is true
     postcondition `allTruckInvs' is true

use> !openter freds_scania pay(3)
     precondition `amountPositive' is true
     precondition `currentExists' is true
use> read Truck_pay.cmd
     Truck_pay.cmd> -- Truck::pay(amount:Integer)
     Truck_pay.cmd> !set self.debt:=self.debt-amount
use> !opexit
     postcondition `debtReduced' is true
     postcondition `allTruckInvs' is true

use> !openter freds_scania bye()
     precondition `currentExists' is true
     precondition `noDebt' is true
use> read Truck_bye.cmd
     Truck_bye.cmd> -- Truck::bye():Integer
     Truck_bye.cmd> !set self.trips:=self.trips->including(self.point)
     Truck_bye.cmd> !set self.trips:=
     Truck_bye.cmd>   self.trips->including(oclUndefined(Point))
     Truck_bye.cmd> !delete (self,self.point) from Current
use> !opexit self.debt.abs
     postcondition `returnEqualsOverPayment' is true
     postcondition `currentEmpty' is true
     postcondition `allTruckInvs' is true
use> --------------------------------------------------------------------
use> !openter freds_scania enter(m)
     precondition `tripsOk' is true
     precondition `currentEmpty' is true
use> read Truck_enter.cmd
     Truck_enter.cmd> -- Truck::enter(entry:Point)
     Truck_enter.cmd> !insert (self,entry) into Current
     Truck_enter.cmd> !set self.debt:=1
use> !opexit
     postcondition `debtAssigned' is true
     postcondition `currentAssigned' is true
     postcondition `allTruckInvs' is true

use> !openter freds_scania move(k)
     precondition `currentExists' is true
     precondition `targetReachable' is true
use> read Truck_move.cmd
     Truck_move.cmd> -- Truck::move(target:Point)
     Truck_move.cmd> !set self.trips:=self.trips->including(self.point)
     Truck_move.cmd> !set self.debt:=self.debt+1
     Truck_move.cmd> !delete (self,self.point) from Current
     Truck_move.cmd> !insert (self,target) into Current
use> !opexit
     postcondition `currentAssigned' is true
     postcondition `allTruckInvs' is true

use> !openter freds_scania pay(5)
     precondition `amountPositive' is true
     precondition `currentExists' is true
use> read Truck_pay.cmd
     Truck_pay.cmd> -- Truck::pay(amount:Integer)
     Truck_pay.cmd> !set self.debt:=self.debt-amount
use> !opexit
     postcondition `debtReduced' is true
     postcondition `allTruckInvs' is true

use> !openter freds_scania move(hh)
     precondition `currentExists' is true
     precondition `targetReachable' is true
use> read Truck_move.cmd
     Truck_move.cmd> -- Truck::move(target:Point)
     Truck_move.cmd> !set self.trips:=self.trips->including(self.point)
     Truck_move.cmd> !set self.debt:=self.debt+1
     Truck_move.cmd> !delete (self,self.point) from Current
     Truck_move.cmd> !insert (self,target) into Current
use> !opexit
     postcondition `currentAssigned' is true
     postcondition `allTruckInvs' is true

use> !openter freds_scania bye()
     precondition `currentExists' is true
     precondition `noDebt' is true
     read Truck_bye.cmd
     Truck_bye.cmd> -- Truck::bye():Integer
     Truck_bye.cmd> !set self.trips:=self.trips->including(self.point)
     Truck_bye.cmd> !set self.trips:=
     Truck_bye.cmd>   self.trips->including(oclUndefined(Point))
     Truck_bye.cmd> !delete (self,self.point) from Current
     Truck_bye.cmd>
use> !opexit self.debt.abs
     postcondition `returnEqualsOverPayment' is true
     postcondition `currentEmpty' is true
     postcondition `allTruckInvs' is true
use> --------------------------------------------------------------------

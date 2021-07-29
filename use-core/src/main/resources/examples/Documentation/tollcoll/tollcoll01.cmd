-- Opens the class diagram:
-- open examples/Documentation/tollcoll/tollcoll.use

-- Creates the object diagram:
-- read examples/Documentation/tollcoll/tollcoll01.cmd

------------------------------------------------------------------------
!create hh:Point
!openter hh init('HH')
read examples/Documentation/tollcoll/Point_init.cmd
!opexit
------------------------------------------------------------------------
!create k:Point
!openter k init('K')
read examples/Documentation/tollcoll/Point_init.cmd
!opexit
------------------------------------------------------------------------
!create h:Point
!openter h init('H')
read examples/Documentation/tollcoll/Point_init.cmd
!opexit
------------------------------------------------------------------------
!create b:Point
!openter b init('B')
read examples/Documentation/tollcoll/Point_init.cmd
!opexit
------------------------------------------------------------------------
!create m:Point
!openter m init('M')
read examples/Documentation/tollcoll/Point_init.cmd
!opexit
------------------------------------------------------------------------
------------------------------------------------------------------------
!openter hh southConnect(k)
read examples/Documentation/tollcoll/Point_southConnect.cmd
!opexit
------------------------------------------------------------------------
!openter hh southConnect(h)
read examples/Documentation/tollcoll/Point_southConnect.cmd
!opexit
------------------------------------------------------------------------
!openter hh southConnect(b)
read examples/Documentation/tollcoll/Point_southConnect.cmd
!opexit
------------------------------------------------------------------------
!openter b southConnect(h)
read examples/Documentation/tollcoll/Point_southConnect.cmd
!opexit
------------------------------------------------------------------------
!openter h southConnect(k)
read examples/Documentation/tollcoll/Point_southConnect.cmd
!opexit
------------------------------------------------------------------------
!openter h southConnect(m)
read examples/Documentation/tollcoll/Point_southConnect.cmd
!opexit
------------------------------------------------------------------------
!openter k southConnect(m)
read examples/Documentation/tollcoll/Point_southConnect.cmd
!opexit
------------------------------------------------------------------------
!openter b southConnect(m)
read examples/Documentation/tollcoll/Point_southConnect.cmd
!opexit
------------------------------------------------------------------------
------------------------------------------------------------------------
!create freds_scania:Truck
!openter freds_scania init('HH-MS 42')
read examples/Documentation/tollcoll/Truck_init.cmd
!opexit
------------------------------------------------------------------------
------------------------------------------------------------------------
!openter freds_scania enter(hh)
read examples/Documentation/tollcoll/Truck_enter.cmd
!opexit
------------------------------------------------------------------------
!openter freds_scania move(b)
read examples/Documentation/tollcoll/Truck_move.cmd
!opexit
------------------------------------------------------------------------
!openter freds_scania move(m)
read examples/Documentation/tollcoll/Truck_move.cmd
!opexit
------------------------------------------------------------------------
!openter freds_scania pay(3)
read examples/Documentation/tollcoll/Truck_pay.cmd
!opexit
------------------------------------------------------------------------
!openter freds_scania bye()
read examples/Documentation/tollcoll/Truck_bye.cmd
!opexit self.debt.abs
------------------------------------------------------------------------
------------------------------------------------------------------------
!openter freds_scania enter(m)
read examples/Documentation/tollcoll/Truck_enter.cmd
!opexit
------------------------------------------------------------------------
!openter freds_scania move(k)
read examples/Documentation/tollcoll/Truck_move.cmd
!opexit
------------------------------------------------------------------------
!openter freds_scania pay(5)
read examples/Documentation/tollcoll/Truck_pay.cmd
!opexit
------------------------------------------------------------------------
!openter freds_scania move(hh)
read examples/Documentation/tollcoll/Truck_move.cmd
!opexit
------------------------------------------------------------------------
!openter freds_scania bye()
read examples/Documentation/tollcoll/Truck_bye.cmd
!opexit self.debt.abs
------------------------------------------------------------------------

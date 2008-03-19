use> read c:/mg/carrent/carrent01.cmd
c:/mg/carrent/carrent01.cmd> -- read c:/mg/carrent/carrent01.cmd
c:/mg/carrent/carrent01.cmd>
c:/mg/carrent/carrent01.cmd> open c:/mg/carrent/carrent.use
c:/mg/carrent/carrent01.cmd>
c:/mg/carrent/carrent01.cmd> !create main:Main
c:/mg/carrent/carrent01.cmd>
c:/mg/carrent/carrent01.cmd> !openter main newCustomer('Ada')
c:/mg/carrent/carrent01.cmd> read c:/mg/carrent/Main_newCustomer.cmd
c:/mg/carrent/Main_newCustomer.cmd> -- Main::newCustomer(aName:String)
c:/mg/carrent/Main_newCustomer.cmd> !assign newCustomer := create Customer
c:/mg/carrent/Main_newCustomer.cmd> !set newCustomer.name := aName
c:/mg/carrent/Main_newCustomer.cmd>
c:/mg/carrent/carrent01.cmd> !opexit
c:/mg/carrent/carrent01.cmd>
c:/mg/carrent/carrent01.cmd> !openter main newCar('B-CD42',#standard)
c:/mg/carrent/carrent01.cmd> read c:/mg/carrent/Main_newCar.cmd
c:/mg/carrent/Main_newCar.cmd> -- Main::newCar(aLicenseNo:String,aCat:CatET)
c:/mg/carrent/Main_newCar.cmd> !assign newCar := create Car
c:/mg/carrent/Main_newCar.cmd> !set newCar.licenseNo := aLicenseNo
c:/mg/carrent/Main_newCar.cmd> !set newCar.cat := aCat
c:/mg/carrent/Main_newCar.cmd>
c:/mg/carrent/carrent01.cmd> !opexit
c:/mg/carrent/carrent01.cmd>
c:/mg/carrent/carrent01.cmd> -- !openter main newBooking(main.getCustomer('Ada'),'2008-01-23','2008-
01-27',#economy)
c:/mg/carrent/carrent01.cmd> -- read c:/mg/carrent/Main_newBooking.cmd
c:/mg/carrent/carrent01.cmd> -- !opexit
c:/mg/carrent/carrent01.cmd>
c:/mg/carrent/carrent01.cmd> !openter main.getCustomer('Ada') book('2008-02-23','2008-02-27',#standa
rd)
c:/mg/carrent/carrent01.cmd> read c:/mg/carrent/Customer_book.cmd
c:/mg/carrent/Customer_book.cmd> -- Customer::book(aStartDay:String,anEndDay:String,aCat:CatET)
c:/mg/carrent/Customer_book.cmd> !assign newBooking := create Booking
c:/mg/carrent/Customer_book.cmd> !insert (self,newBooking) into Registration
c:/mg/carrent/Customer_book.cmd> !set newBooking.startDay := aStartDay
c:/mg/carrent/Customer_book.cmd> !set newBooking.endDay := anEndDay
c:/mg/carrent/Customer_book.cmd> !set newBooking.cat := aCat
c:/mg/carrent/Customer_book.cmd> !set newBooking.pickedUp := false
c:/mg/carrent/Customer_book.cmd> !set newBooking.returnDay := oclUndefined(String)
c:/mg/carrent/Customer_book.cmd>
c:/mg/carrent/carrent01.cmd> !opexit
c:/mg/carrent/carrent01.cmd>
c:/mg/carrent/carrent01.cmd> ?Booking.allInstances
-> Set{@Booking1} : Set(Booking)
c:/mg/carrent/carrent01.cmd>
c:/mg/carrent/carrent01.cmd> !openter main.getCustomer('Ada') cancel(Booking.allInstances->any(true)
)
c:/mg/carrent/carrent01.cmd> read c:/mg/carrent/Customer_cancel.cmd
c:/mg/carrent/Customer_cancel.cmd> -- Customer::cancel(aBooking:Booking)
c:/mg/carrent/Customer_cancel.cmd> !delete (self,aBooking) from Registration
c:/mg/carrent/Customer_cancel.cmd> ?aBooking
-> @Booking1 : Booking
c:/mg/carrent/Customer_cancel.cmd> !destroy aBooking

INTERNAL ERROR: An unexpected exception occured. This happened most probably
due to an error in the program. The program will try to continue, but may
not be able to recover from the error. Please send a bug report to mr@tzi.org
with a description of your last input and include the following output:
Program version: 2.3.1
Project version: use 2-3-1-release.3 Wed, 02 Aug 2006 17:53:29 +0200 green
Stack trace: java.lang.RuntimeException: unbound variable `aBooking'.
        at org.tzi.use.uml.ocl.expr.ExpVariable.eval(ExpVariable.java:48)
        at org.tzi.use.uml.ocl.expr.Evaluator.evaluate(Evaluator.java:112)
        at org.tzi.use.uml.ocl.expr.Evaluator.eval(Evaluator.java:70)
        at org.tzi.use.uml.ocl.expr.Evaluator.eval(Evaluator.java:81)
        at org.tzi.use.uml.ocl.expr.Evaluator.eval(Evaluator.java:92)
        at org.tzi.use.uml.sys.MCmdDestroyObjects.execute(MCmdDestroyObjects.java:121)
        at org.tzi.use.util.cmd.CommandProcessor.execute(CommandProcessor.java:49)
        at org.tzi.use.uml.sys.MSystem.executeCmd(MSystem.java:155)
        at org.tzi.use.main.shell.Shell.cmdExec(Shell.java:451)
        at org.tzi.use.main.shell.Shell.processLine(Shell.java:322)
        at org.tzi.use.main.shell.Shell.processLineSafely(Shell.java:254)
        at org.tzi.use.main.shell.Shell.run(Shell.java:205)
        at java.lang.Thread.run(Unknown Source)
c:/mg/carrent/Customer_cancel.cmd>
c:/mg/carrent/carrent01.cmd> !opexit
c:/mg/carrent/carrent01.cmd>
c:/mg/carrent/carrent01.cmd> ?Booking.allInstances
-> Set{@Booking1} : Set(Booking)
c:/mg/carrent/carrent01.cmd>
c:/mg/carrent/carrent01.cmd> !let bb=Booking.allInstances->any(true)
c:/mg/carrent/carrent01.cmd> ?bb
-> @Booking1 : Booking
c:/mg/carrent/carrent01.cmd> !destroy bb
c:/mg/carrent/carrent01.cmd>
c:/mg/carrent/carrent01.cmd> ?Booking.allInstances
-> Set{} : Set(Booking)

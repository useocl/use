use> ------------------------------------------------------------------------

use> ?Person.allInstances
     Set{@Person1,@Person10,@Person11,@Person12,@Person13,@Person14,
         @Person15,@Person16,@Person2,@Person3,@Person4,@Person5,@Person6,
         @Person7,@Person8,@Person9} : Set(Person)

use> ?Person.allInstances.name
     Bag{'Cam','Dan','Eli','Flo','Gen','Hal','Hao','Jan','Max','Ole','Pam',
         'Pat','Tip','Vic','Wei','Yan'} : Bag(String)

use> ?Person.allInstances->collect(p|Tuple{cs:p.civstat,gd:p.gender})
     Bag{Tuple{cs=#married,gd=#female},
         Tuple{cs=#married,gd=#female},
         Tuple{cs=#married,gd=#female},
         Tuple{cs=#married,gd=#female},
         Tuple{cs=#married,gd=#female},
         Tuple{cs=#married,gd=#female},
         Tuple{cs=#married,gd=#male},
         Tuple{cs=#married,gd=#male},
         Tuple{cs=#married,gd=#male},
         Tuple{cs=#married,gd=#male},
         Tuple{cs=#married,gd=#male},
         Tuple{cs=#married,gd=#male},
         Tuple{cs=#single,gd=#female},
         Tuple{cs=#single,gd=#male},
         Tuple{cs=#single,gd=#male},
         Tuple{cs=#single,gd=#male}} : Bag(Tuple(cs:CivilStatus,gd:Gender))

use> ?Person.allInstances->
use>   select(p|p.name.substring(1,1)<='M')->
use>     collectNested(p|
use>       Sequence{p.name,
use>                if p.gender=#female then 'F' else 'M' endif,
use>                p.civstat,
use>                p.spouse().name})
     Bag{Sequence{'Cam','F',#married,'Pat'},
         Sequence{'Dan','M',#married,'Yan'},
         Sequence{'Eli','M',#married,'Tip'},
         Sequence{'Flo','F',#married,'Jan'},
         Sequence{'Gen','F',#married,'Ole'},
         Sequence{'Hal','M',#single,Undefined},
         Sequence{'Hao','F',#single,Undefined},
         Sequence{'Jan','M',#married,'Flo'},
         Sequence{'Max','M',#single,Undefined}} : Bag(Sequence(OclAny))

use> ?Person.allInstances->
use>   select(p|p.name.substring(1,1)<='M')->
use>     collect(p|
use>       Sequence{p.name,
use>                if p.gender=#female then 'F' else 'M' endif,
use>                p.civstat,
use>                p.spouse().name})
     Bag{Undefined,Undefined,Undefined,#married,#married,#married,#married,
         #married,#married,#single,#single,#single,'Cam','Dan','Eli','F',
         'F','F','F','Flo','Flo','Gen','Hal','Hao','Jan','Jan','M','M','M',
         'M','M','Max','Ole','Pat','Tip','Yan'} : Bag(OclAny)

use> ------------------------------------------------------------------------

use> ?Person.allInstances->forAll(p1,p2|p1<>p2 implies p1.name<>p2.name)
     true : Boolean

use> ?Person.allInstances->forAll(p1,p2|p1.name=p2.name implies p1=p2)
     true : Boolean

use> ?Person.allInstances->isUnique(name)
     true : Boolean

use> ?Person.allInstances->isUnique(gender)
     false : Boolean

use> ?Person.allInstances->one(p|p.name='Pam')
     true : Boolean

use> ?Person.allInstances->any(name='Pam').husband.name
     'Wei' : String

use> ?Person.allInstances.husband.name
     Bag{Undefined,Undefined,Undefined,Undefined,Undefined,Undefined,
         Undefined,Undefined,Undefined,Undefined,'Dan','Eli','Jan','Ole',
         'Pat','Wei'} : Bag(String)

use> ?Person.allInstances.husband.name->select(s|s.isUndefined())->size()
     10 : Integer

use> ?Person.allInstances->select(p|p.husband.isUndefined)->size()
     10 : Integer

use> ------------------------------------------------------------------------

use> ?Person.allInstances->sortedBy(p|p.name)
     Sequence{@Person2,@Person13,@Person15,@Person1,@Person5,@Person14,
              @Person3,@Person10,@Person11,@Person9,@Person7,@Person8,
              @Person6,@Person16,@Person12,@Person4} : Sequence(Person)

use> ?Person.allInstances->
use>   sortedBy(p|p.name)->
use>     iterate(p:Person;
use>             res:String=''|
use>             res.concat(if res='' then '' else ' ' endif).concat(p.name))
     'Cam Dan Eli Flo Gen Hal Hao Jan Max Ole Pam Pat Tip Vic Wei Yan' : String

use> ?Person.allInstances->
use>   iterate(p1,p2:Person;
use>           res:Boolean=true|
use>           res and (p1<>p2 implies p1.name<>p2.name))
     true : Boolean

use> ?Person.allInstances->
use>   iterate(p; res:Set(Person)=Set{}|
use>     if p.name.substring(1,1)<='M'
use>       then res->including(p) else res endif).name
     Bag{'Cam','Dan','Eli','Flo','Gen','Hal','Hao','Jan','Max'} : Bag(String)

use> ------------------------------------------------------------------------

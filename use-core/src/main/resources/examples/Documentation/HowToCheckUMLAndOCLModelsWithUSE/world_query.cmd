-- read c:/use/civstat/world_query.cmd

open c:/use/civstat/civstat.use

gen start -r 2960 c:/use/civstat/civstat.assl world(7,9,6)
gen result
gen result accept

------------------------------------------------------------------------

?Person.allInstances

?Person.allInstances.name

?Person.allInstances->collect(p|Tuple{cs:p.civstat,gd:p.gender})

\
?Person.allInstances->
  select(p|p.name.substring(1,1)<='M')->
    collectNested(p|
      Sequence{p.name,
               if p.gender=#female then 'F' else 'M' endif,
               p.civstat,
               p.spouse().name})
.

\
?Person.allInstances->
  select(p|p.name.substring(1,1)<='M')->
    collect(p|
      Sequence{p.name,
               if p.gender=#female then 'F' else 'M' endif,
               p.civstat,
               p.spouse().name})
.

------------------------------------------------------------------------

?Person.allInstances->forAll(p1,p2|p1<>p2 implies p1.name<>p2.name)

?Person.allInstances->forAll(p1,p2|p1.name=p2.name implies p1=p2)

?Person.allInstances->isUnique(name)

?Person.allInstances->isUnique(gender)

?Person.allInstances->one(p|p.name='Pam')

?Person.allInstances->any(name='Pam').husband.name

?Person.allInstances.husband.name

?Person.allInstances.husband.name->select(s|s.isUndefined())->size()

?Person.allInstances->select(p|p.husband.isUndefined)->size()

------------------------------------------------------------------------

?Person.allInstances->sortedBy(p|p.name)

\
?Person.allInstances->
  sortedBy(p|p.name)->
    iterate(p:Person;
            res:String=''|
            res.concat(if res='' then '' else ' ' endif).concat(p.name))
.

\
?Person.allInstances->
  iterate(p1,p2:Person;
          res:Boolean=true|
          res and (p1<>p2 implies p1.name<>p2.name))
.

\
?Person.allInstances->
  iterate(p; res:Set(Person)=Set{}|
    if p.name.substring(1,1)<='M'
      then res->including(p) else res endif).name
.

------------------------------------------------------------------------

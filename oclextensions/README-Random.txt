%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
RANDOM Numbers

<!--- If x.oclIsOfType(Real) then x.rand() returns a random real number between 0 and x.
  --- For example 1.rand() returns a random number in the interval [0..1]
  --- If you need a number in the interval [a..b] you can use the expression "a + (b-a).rand()"
-->

  <operation source="Real" name="rand" returnType="Real">
	<body><![CDATA[
		$self * Random.rand
	]]>
	</body>
  </operation>

<!--- If n>0 then n.srand() starts a new random sequence with n as new seed (the seed is integer). 
  --- It returns the current seed. Given that Ruby's initial seed is a huge integer number 
  --- that cannot be handled by USE, this operation takes the modulo with 10^7.
  --- In n<=0 then it uses the default Ruby srand() operation that generates a seed automatically
  --- using the time and other system values.
-->

  <operation source="Integer" name="srand" returnType="Integer">
	<body><![CDATA[
		if $self > 0
                   return Random.srand($self) %1000000
                else 
                   return Random.srand() % 10000000
                end
	]]>
	</body>
  </operation>

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
EXAMPLES OF USE

use> ?1.rand()
-> 0.09152860811512553 : Real
use> ?2.rand()
-> 1.8371397364794912 : Real
use> ?2.rand()
-> 0.6646401472302712 : Real
use> ?2.rand()
-> 1.5417649510780334 : Real
use> ?2.rand()
-> 0.990212333639167 : Real
use> ?2.rand()
-> 1.676070756281957 : Real
use> ?2.rand()
-> 1.835645464118648 : Real
use> ?1.srand()
-> 32783 : Integer
use> ?1.srand()
-> 1 : Integer
use> ?0.srand()
-> 1 : Integer
use> ?0.srand()
-> 2297549 : Integer
use> ?0.srand()
-> 3220924 : Integer
use> ?0.srand()
-> 3666729 : Integer
use>
use> <b>!create cs:Department</b>

use> <b>!set cs.name := 'Computer Science'</b>
use> <b>!set cs.location := 'Bremen'</b>
use> <b>!set cs.budget := 10000</b>

use> <b>!create john : Employee</b>
use> <b>!set john.name := 'John'</b>
use> <b>!set john.salary := 4000</b>
use> <b>!create frank : Employee</b>
use> <b>!set frank.name := 'Frank'</b>
use> <b>!set frank.salary := 4500</b>

use> <b>!insert (john,cs) into WorksIn</b>
use> <b>!insert (frank,cs) into WorksIn</b>

!create research : Project
!set research.name := 'Research'
!set research.budget := 12000

!create teaching : Project
!set teaching.name := 'Validating UML'
!set teaching.budget := 3000

!insert (cs,research) into Controls
!insert (cs,teaching) into Controls

!insert (frank,research) into WorksOn
!insert (frank,teaching) into WorksOn
!insert (john,research) into WorksOn


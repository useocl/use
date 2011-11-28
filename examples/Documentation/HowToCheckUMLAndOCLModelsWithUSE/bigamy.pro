use> open civstat.use

-- File bigamy.invs contains:
-- context Person inv bigamy: Person.allInstances->exists(p|
--   p.wife.isDefined and p.husband.isDefined)

use> gen load bigamy.invs
     Added invariants: Person::bigamy
use> gen start civstat.assl attemptBigamy()
use> gen result
     Random number generator was initialized with 5649.
     Checked 663552 snapshots. Result: No valid state found.

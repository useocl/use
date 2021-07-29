-- Opens the class diagram:
-- open civstat.use

-- Creates the object diagram:
-- read attemptBigamy.cmd

gen load bigamy.invs

gen start civstat.assl attemptBigamy()

------------------------------------------------------------------------

-- use> open civstat.use
-- 
-- use> gen load bigamy.invs
--      Added invariants: Person::bigamy
-- 
-- use> gen start civstat.assl attemptBigamy()
-- use> gen result
--      Random number generator was initialized with 5649.
--      Checked 663552 snapshots.
--      Result: No valid state found.

-- single person: 3 name values (A B C), 4 civstat values (s m d w),
--                2 gender values (f m), 2 alive values (f t)
--                3*4*2*2 = 48
-- 3 persons: 48*48*48 = 110.592
-- 3*2*1 = 6 marriage link configurations: 6*110.592 = 663.552

------------------------------------------------------------------------

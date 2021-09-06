-- Opens the class diagram:
-- open examples/Papers/2005/GogollaBohlingRichters/percom.use

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/percom-6.cmd

-- compare assl procedure without hints and assl procedure with
-- construction hints

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersonsNaive(5)
gen result
gen result accept

open examples/Papers/2005/GogollaBohlingRichters/percom.use

gen start examples/Papers/2005/GogollaBohlingRichters/percom.assl generatePersons(5)
gen result
gen result accept

-- X        Naive   Constructed (NumberOfSnapshotsChecked)
-- 1            1             1
-- 2            2             1
-- 3           29             1
-- 4          732             1
-- 5       19.011             1
-- 6      494.266             1
-- 7   12.850.897             1

-- X   Naive Constructed (CPU-Time) Seconds Snapshots per Second
-- 1 0:00:00        0:00                  -                    -
-- 2 0:00:00        0:00                  -                    -
-- 3 0:00:00        0:00                  -                    -
-- 4 0:00:02        0:00                  -                    -
-- 5 0:00:11        0:00                 11      19011/11 = 1728
-- 6 0:04:47        0:00                287    494266/287 = 1722
-- 7 2:12:32        0:00               7952 12850897/7952 = 1616

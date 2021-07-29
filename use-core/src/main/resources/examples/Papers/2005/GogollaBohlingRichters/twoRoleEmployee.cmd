-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/twoRoleEmployee.cmd

?Person.allInstances->exists(p | p.job->exists(low, top | low.boss.isDefined and top.worker->notEmpty and low.employer<>top.employer))

?Person.allInstances->select(p | p.job->exists(low, top | low.boss.isDefined and top.worker->notEmpty and low.employer<>top.employer)).name

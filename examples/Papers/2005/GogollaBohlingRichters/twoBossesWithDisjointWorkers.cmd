-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/twoBossesWithDisjointWorkers.cmd

?Person.allInstances->exists(top1, top2 | let top1Worker:Set(Job)=top1.job.workerPlus()->asSet in let top2Worker:Set(Job)=top2.job.workerPlus()->asSet in top1Worker->notEmpty and top2Worker->notEmpty and top1Worker->intersection(top2Worker)->isEmpty)

?Person.allInstances->select(top1 | Person.allInstances->exists(top2 | let top1Worker:Set(Job)=top1.job.workerPlus()->asSet in let top2Worker:Set(Job)=top2.job.workerPlus()->asSet in top1Worker->notEmpty and top2Worker->notEmpty and top1Worker->intersection(top2Worker)->isEmpty)).name

-- Creates the object diagram:
-- read examples/Papers/2005/GogollaBohlingRichters/threeLevelHierarchy.cmd

?Job.allInstances->exists(top, mid, low | top.worker->includes(mid) and mid.worker->includes(low))

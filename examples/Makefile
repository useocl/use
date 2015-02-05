# $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $

TOPDIR = ..
LOCALE = en_US.UTF-8

USE = LANG=$(LOCALE) $(TOPDIR)/bin/use

USE_FILES := $(shell find -name "*.use")
DONE_USE_FILES := $(USE_FILES:.use=.use.done) 

CMD_FILES := $(USE_FILES:.use=.cmd) 
DONE_CMD_FILES := $(CMD_FILES:.cmd=.cmd.done) 

# command files for producing a UML metamodel instance of a model
# usage: use -v -nogui UML13All.use <model>-mm.cmd
MM_CMD_FILES := $(USE_FILES:.use=-mm.cmd) 
MM_DONE_FILES := $(filter-out Test%, $(MM_CMD_FILES:.cmd=.done))

# files for the vcg graph visualization tool
VCG_FILES := $(USE_FILES:.use=.vcg) 

all: 

mm: $(MM_CMD_FILES)

vcg: $(VCG_FILES)

test: $(DONE_USE_FILES) $(DONE_CMD_FILES)

# just execute all cmd files once, and ignore the results of
# invariant checking
test-cmd: $(DONE_CMD_FILES)

test-mm: $(MM_DONE_FILES)

%-mm.done: %-mm.cmd
	-rm -f $@
	$(USE) -qv UML13All.use $^
	touch $@

%.cmd.done: %.cmd
	-rm -f $@
	-$(USE) -q $*.use $^
	touch $@

%.use.done: %.use
	-rm -f $@
	$(USE) -c -v -nogui $^
	touch $@

%-mm.cmd: %.use
	-rm -f $@
	echo "genmm $@" > .genmm.cmd
	echo "quit" >> .genmm.cmd
	$(USE) -nogui $^ .genmm.cmd
	-rm -f .genmm.cmd

%.vcg: %.use
	-rm -f $@
	echo "genvcg $@" > .genvcg.cmd
	echo "quit" >> .genvcg.cmd
	$(USE) -nogui $^ .genvcg.cmd
	-rm -f .genvcg.cmd

clean:
	rm -f $(DONE_USE_FILES) $(MM_DONE_FILES) $(DONE_CMD_FILES) *~ core

maintainer-clean: clean
	rm -f $(MM_CMD_FILES) $(VCG_FILES)


# $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $

TOPDIR = ../..

# change switch to `-qv' to get output in case of test failures
ifeq ($(OS), Windows_NT)
	USE = $(TOPDIR)/bin/use.bat -q
else
	USE = $(TOPDIR)/bin/use -q
endif

GOOD_CMD_FILES := $(wildcard *-good*.cmd)
GOOD_DONE_FILES := $(GOOD_CMD_FILES:.cmd=.done)

BAD_CMD_FILES := $(wildcard *-bad*.cmd)
BAD_DONE_FILES := $(BAD_CMD_FILES:.cmd=.done)

all:

test: test-good test-bad

test-good: $(GOOD_DONE_FILES)

test-bad: $(BAD_DONE_FILES)

$(GOOD_DONE_FILES): %.done : %.cmd
	@-rm -f $@
	@spec=`echo $@ | sed 's/-.*//g'`.use; \
	echo "checking $$spec with $*.cmd"; \
	$(USE) $$spec $*.cmd
	@touch $@

$(BAD_DONE_FILES): %.done : %.cmd
	@-rm -f $@
	@spec=`echo $@ | sed 's/-.*//g'`.use; \
	echo "checking $$spec with $*.cmd"; \
	($(USE) $$spec $*.cmd || fail=yes ; test -n "$$fail")
	@touch $@

clean:
	rm -f $(GOOD_DONE_FILES) $(BAD_DONE_FILES) *~ core

maintainer-clean: clean

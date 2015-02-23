# $Id$

TOPDIR = ..
LOCALE = en_US.UTF-8
DIFFLINECOUNT = 50

ifeq ($(OS), Windows_NT)
	USE = $(TOPDIR)/bin/use.bat -nogui -nr -t -oclAnyCollectionsChecks:E -extendedTypeSystemChecks:E
else
	USE = LANG=$(LOCALE) $(TOPDIR)/bin/use -nogui -nr -t -oclAnyCollectionsChecks:E -extendedTypeSystemChecks:E
endif

INFILES := $(wildcard *.in)
OUTFILES := $(INFILES:.in=.out)
CMDFILES := $(INFILES:.in=.cmd)
RESULTFILES := $(INFILES:.in=.result)

all: $(OUTFILES) $(CMDFILES)

%.cmd: %.in mkcmd.pl
	./mkcmd.pl < $< > $@

%.out: %.in mkout.pl
	./mkout.pl $(basename $<) < $< | dos2unix > $@

test: all $(RESULTFILES)

test-out1: t1.out
	$(USE) $(basename $<).use $(basename $<).cmd

test-out2: t2.out
	$(USE) $(basename $<).use $(basename $<).cmd 

# Die Zeit, die der Generator benötigt, wird aus dem Ergebnis herausgenommen.
%.result: %.in %.use %.cmd
	@-rm -f $@
	@$(USE) $(basename $<).use $(basename $<).cmd 2>&1 | sed 's/in [0-9][0-9]*.[0-9][0-9]*s//' | tail --lines=+2 | dos2unix | diff -U 1 --strip-trailing-cr --ignore-blank-lines --ignore-all-space  $(basename $<).out - > $(basename $<).result || (cat $(basename $<).result | head -n $(DIFFLINECOUNT); [ $$( wc -l < $(basename $<).result ) -gt $(DIFFLINECOUNT) ] && echo "[... (truncated all but first $(DIFFLINECOUNT) lines)]" && echo "http://ci.db.informatik.uni-bremen.de:8080/job/USE Trunk/ws/test/$(basename $<).result/*view*/"; echo "Test $(basename $<) FAILED!"; false)
	@echo "Test $(basename $<) passed."

clean:
	rm -f $(RESULTFILES) *~ core

maintainer-clean: clean
	rm -f $(OUTFILES) $(CMDFILES)

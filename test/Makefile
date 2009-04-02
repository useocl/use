# $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $

TOPDIR = ..

ifeq ($(OS), Windows_NT)
	USE = $(TOPDIR)/bin/use.bat -nogui -nr
else
	USE = $(TOPDIR)/bin/use -nogui -nr
endif

INFILES := $(wildcard *.in)
OUTFILES := $(INFILES:.in=.out)
CMDFILES := $(INFILES:.in=.cmd)
RESULTFILES := $(INFILES:.in=.result)

all: $(OUTFILES) $(CMDFILES)

%.cmd: %.in mkcmd.pl
	./mkcmd.pl < $< > $@

%.out: %.in mkout.pl
	./mkout.pl $(basename $<) < $< > $@

test: all $(RESULTFILES)

test-out1: t1.out
	$(USE) $(basename $<).use $(basename $<).cmd

test-out2: t2.out
	$(USE) $(basename $<).use $(basename $<).cmd

# Die Zeit, die der Generator benötigt, wird aus dem Ergebnis herausgenommen.
%.result: %.in %.use %.cmd
	-rm -f $@
	dos2unix $(basename $<).out
	$(USE) $(basename $<).use $(basename $<).cmd 2>&1 | sed 's/in [0-9][0-9]*.[0-9][0-9]*s//' | tail --lines=+2 | diff -u $(basename $<).out - > $(basename $<).result

clean:
	rm -f $(RESULTFILES) *~ core

maintainer-clean: clean
	rm -f $(OUTFILES) $(CMDFILES)

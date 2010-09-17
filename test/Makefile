# $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $

TOPDIR = ..

ifeq ($(OS), Windows_NT)
	USE = $(TOPDIR)/bin/use.bat -nogui -nr -t
else
	USE = $(TOPDIR)/bin/use -nogui -nr -t
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
	-rm -f $@
	$(USE) $(basename $<).use $(basename $<).cmd 2>&1 | sed 's/in [0-9][0-9]*.[0-9][0-9]*s//' | tail --lines=+2 | dos2unix | diff -u --strip-trailing-cr --ignore-blank-lines --ignore-all-space  $(basename $<).out - > $(basename $<).result

clean:
	rm -f $(RESULTFILES) *~ core

maintainer-clean: clean
	rm -f $(OUTFILES) $(CMDFILES)

# $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $

DVIPS = dvips
LATEX = latex
BIBTEX = bibtex
MAKEINDEX = makeindex

all: 

doc:	use.ps

use.ps: use.dvi
	$(DVIPS) $< -o $@

use.dvi: use.tex
	$(LATEX) $<
	-$(BIBTEX) $(basename $<)
	$(MAKEINDEX) $<
	$(LATEX) $<
	$(LATEX) $<

clean:
	rm -f *.log *.aux *.bbl *.blg *.toc *.idx *.ilg *.ind *~ core

maintainer-clean: clean
	rm -f use.dvi


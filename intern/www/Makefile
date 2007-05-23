# $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $

INSTALL_PATH = ../../build/doc

PERL = perl
FIG2DEV = fig2dev
USE2HTML = ./use2html.pl

SRCFILES := $(wildcard *.html.in)
HTMLFILES := $(SRCFILES:.html.in=.html) 

FIGFILES := $(wildcard *.fig)
FIG_PNGFILES := $(addprefix gen-img/,$(FIGFILES:.fig=.png))

SCREEN_FILES := $(wildcard qt*.png screenshot1.png pp*.png)
SCREEN_THUMBNAILS := $(addprefix gen-img/,$(SCREEN_FILES:.png=-small.png))

USEFILES := Demo1.use Demo2.use \
	Employee1.use Employee2.use Employee3.use \
	Graph.use \
	NestedOperationCalls.use \
	AssociationClass.use

USEHTMLFILES := $(USEFILES:.use=.use.html) 

CHDATE := $(shell date "+%d.%m.%Y")
LOGIN := $(shell whoami)
# AUTHOR := $(shell ypcat passwd | grep '^$(LOGIN):' | gawk 'BEGIN {FS=":"}{print $$5}')
AUTHOR := $(shell finger $(LOGIN) | grep '^Login:' | gawk 'BEGIN {FS=": "}{print $$3}')
TIDY := tidy -e -q 

# $Format: "release_version = $ReleaseVersion$"$
release_version = 2.3.1

all: $(USEHTMLFILES) $(HTMLFILES) $(FIG_PNGFILES) $(SCREEN_THUMBNAILS)

# install data into doc directory
install: all
	cp -rf *.html *.png *.gif *.use *.ps gen-img $(INSTALL_PATH)
	cp ../../examples/README.examples $(INSTALL_PATH)/README.examples

$(HTMLFILES): epilog1 epilog2

%.html:%.html.in
	rm -f $@
	cpp -P -traditional -undef $< | sed -e 's/@release_version@/$(release_version)/g' > $@ 
#	cat $< | sed -e 's/@release_version@/$(release_version)/g' | sed -e '/FOO (.*)/r \1' > $@ 
	cat epilog1 >> $@
	echo "$(CHDATE) by $(AUTHOR) ($(LOGIN)@informatik.uni-bremen.de)" >> $@
	cat epilog2 >> $@
	-$(TIDY) $@

gen-img/%.png:%.fig
	$(FIG2DEV) -L png -b 5 -S 2 $< $@

gen-img/%-small.png:%.png
	convert -geometry 20% $< $@

%.use.html: %.use
	rm -f $@
	$(PERL) $(USE2HTML) $< > $@

clean:
	rm -f $(HTMLFILES) $(USEHTMLFILES) core *~

maintainer-clean: clean
	rm -f $(FIG_PNGFILES) $(SCREEN_THUMBNAILS)

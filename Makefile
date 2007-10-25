## -*- Makefile -*-
## $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $

# In previous versions this Makefile was used to build the USE system.
# Apache Ant is now used for this task. See the file build.xml for details.
# This Makefile is now only used for some maintenance tasks.

package = use

src_dir = src/main/org

all:
	@echo "Please use Ant for building."

# rules below are for maintainance

count-lines:
	find $(src_dir) -name "*.java" | xargs -n 1 wc | awk '{ s += $$1 } END { print s}'

find-fixme:
	find $(src_dir) -name "*.java" | xargs grep -n FIXME

find-todo:
	find $(src_dir) -name "*.java" | xargs grep -n TODO

# dirty.. generates buglist 
update-buglist: 
	cd intern; make-buglist.pl bug???? > buglist.html

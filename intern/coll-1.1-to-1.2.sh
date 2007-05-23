#!/bin/sh

mv -f $1 $1.bak
sed -e 's/com.sun.java.util.collections/java.util/g;' < $1.bak > $1

#!/bin/sh
# change copyright header 

# set -x

files=`find -type f`
for i in $files; do 
    echo $i 
    mv -f $i $i.orig
    cat $i.orig | sed 's/Copyright (C) 1999,2000,2001 Mark/Copyright (C) 1999-2004 Mark/g' > $i
    rm -f $i.orig
done

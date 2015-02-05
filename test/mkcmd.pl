#!/usr/bin/perl -w

### Read test file and strip comments, empty lines and result lines.
### $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $

use strict;

while (<>) {
    chomp;
    next if /^$/;		# skip empty lines
    next if /^#/;		# skip comments
    next if /^\*/;		# skip results

    print "$_\n";
}
print "exit\n";

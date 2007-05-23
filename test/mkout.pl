#!/usr/bin/perl -w

### Read test file and generate file with expected output.
### $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $

use strict;

my $basename = shift;

while (<>) {
    chomp;
    next if /^$/;		# skip empty lines
    next if /^#/;		# skip comments
    
    if (/^\*(.*)/) {
	print "$1\n";
    } else {
	print "$basename.cmd> $_\n";
    }
}

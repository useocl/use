#!/usr/local/bin/perl -w
# $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $

use strict;

my $code;
my $comment;
my $kw = "\@pre|abstract|and|association|associationclass|aggregation|attributes|between|class|composition|constraints|context|else|end|endif|enum|if|implies|inv|let|model|not|operations|or|pre|post|role|then|xor";

while (<>) {
    if (/(.*)--(.*)$/) {	# don't substitute keywords in eol comments
	$code = $1;
	$comment = $2;
	$code =~ s/\b($kw)\b/\<b\>$1\<\/b\>/g; # keywords
	$_ = "$code--<font color=\"red\">$comment</font>\n";
    } else {
	s/\b($kw)\b/\<b\>$1\<\/b\>/g; # substitute all keywords
    }

#    s/$/\\\\\n/g;		# line end --> \\
    print;
}


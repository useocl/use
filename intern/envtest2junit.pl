#!/usr/bin/perl -w
# Translate old test code to junit test.
# usage: pipe test class through script, env.test(...) lines will be replaced
# caveat: env.test statements must be on a single line.

use strict;

while ( <STDIN> ) {
    if (not /\s*env\.test/) {
        print;
        next;
    }
    chomp;
    (my $actual = $_) =~ s/\s*env\.test\(\".*?[^\\]\",\s*(.*),\s*.*\);/$1/g;
    (my $expected = $_) =~ s/\s*env\.test\(.*,.*,\s*(.*)\);/$1/g;
    # print "// $_\n";
    print "        assertEquals($expected, $actual);\n";
}



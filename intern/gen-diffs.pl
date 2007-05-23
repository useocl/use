#!/usr/bin/perl -w

my $rev_from = $ARGV[0];
my $rev_to = $ARGV[1];

print "$rev_from, $rev_to\n";

for ($rev = $rev_from; $rev < $rev_to; $rev++) {
    my $r1 = "0." . $rev;
    my $r2 = "0." . ($rev+1);
    my $file = "use-$r1-$r2.diff";
    my @args = ("/bin/sh", "-c", 
		"prcs diff -r $r1 -r $r2 -- -u > $file 2>&1");
    print "@args\n";
    system(@args);
}


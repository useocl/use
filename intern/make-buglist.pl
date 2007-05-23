#!/usr/local/bin/perl
use warnings;

# generates a html table summarizing all bugs in intern
# looks for $Bug: my bug description$, $State: Open$ and so on..

# dirty hack right now ;)

@tags = ('Bug', 'Category', 'State', 'BugAuthor', 'Tests');

@bugfiles = @ARGV;

# print headers
print "<html><body>\n";
$date = `date`;
print "<h3>USE buglist $date</h3>\n";
print "<table style=\"border-style:solid; border:1\"  cellspacing=\"0\"  cellpadding=\"5\">\n";
print "<tr><td><strong>Bug#</strong></td>";
for $tag (@tags) { print "<td><strong>$tag</strong></td>"; }
print "</tr>";
$highlight=1;
for $bugfile (@bugfiles ) {
    open FH, $bugfile;
    if ($highlight) {
        $color='#CCCCCC';
        $highlight=0;
    } else {
        $color='#FFFFFF';
        $highlight=1;
    }
    for $tag (@tags) { $found{$tag} = ''; }
    while(<FH>) {
        for $tag (@tags) {
            if (/\$$tag: (.*?)\$/) {$found{$tag} = $1;}
        }
    }
    if ($found{"State"} ne "Closed" ) {
        ($strong1, $strong2) = ("<strong>", "</strong>");
    } else {
        ($strong1, $strong2) = ("", "");
    }
    print "<tr><td bgcolor=\"$color\"><a href=\"$bugfile\">$strong1$bugfile$strong2</a></td>";
    for $tag (@tags) { 
        print "<td bgcolor=\"$color\">"; 
        print "$strong1$found{$tag}$strong2";
        print "</td>"; 
    }
    print "</tr>\n";
    close FH;
}
print "</html></body></table>\n";


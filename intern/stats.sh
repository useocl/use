#!/bin/sh

# set -x

in=$1
grep=grep

echo -e "Total:\t" `$grep -c 'STATE:' $in`
echo -e "Ok:\t" `$grep -c 'STATE: Ok' $in`
echo -e "E1:\t" `$grep -c 'STATE:.*E1' $in`
echo -e "E2:\t" `$grep -c 'STATE:.*E2' $in`
echo -e "E3:\t" `$grep -c 'STATE:.*E3' $in`
echo -e "E4:\t" `$grep -c 'STATE:.*E4' $in`


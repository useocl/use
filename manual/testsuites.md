# Using model unit tests in USE

USE has build-in support for running tests.
As with XUnit tests, developers can define assertions at several points in time.

## Testsuite syntax

```ebnf
testSuite := 'testsuite' testSuite-name 'for' 'model' filename
              ['setup'  cmdList 'end']
              testCase*;

testCase  := 'testcase' testCase-name
             (commandStatement | assertStatement | beginVariation | endVariation)*
             'end';

assertStatement := 'assert' ('valid' | 'invalid')
                   (
                     expression | 
                     'invs' (class-name)? | 
                     'inv' class-name '::' invariant-name | 
                     assertionStatementPre | 
                     assertionStatementPost
                   )
                   (',' failure-message)?
                   
assertionStatementPre := 'pre' object-expression operation-name 
                         ('(' expression? (',' expression)* ')' | '('')')
                         ('::' condition-name)?

assertionStatementPost := 'post' condition-name?

testSuite-name := IDENT

filename := IDENT '.' IDENT

testCase-name := IDENT

invariant-name := IDENT

class-name := IDENT

operation-name := IDENT

condition-name := IDENT

failure-message := STRING

object-expression := expression
```

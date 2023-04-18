grammar Grammar2;
start: package* initiations* automations* EOF;
package: 'Use ' packagename ';';
packagename: Name;
initiations: Type Name identifier ';';
Type: 'Light ' | 'Sensor ';
Name: ([A-Z]|[a-z]|[0-9]|'_'|'-')+;
identifier: '"' Name '"'
| Name
| '"' Name '"' ',' identifier
| Name ',' identifier;
automations: 'Begin(Automation)' triggers* actions* 'End(Automation);' ;
triggers: 'Trigger(' boolExpr ');' ;
boolExpr: bool 'and' boolExpr
| bool 'or' boolExpr
| bool
| '(' boolExpr ')';
bool: expr | expr '=' expr | expr '<' expr | expr '>' expr;
expr: 'TIME' | TimeValue | Value | attribute | Key;
TimeValue: ('0'[0-9]|'1'[0-9]|'2'[0-3])':'[0-5][0-9];
Value: [0-9]+
| [0-9]+'.'[0-9]+ ;
Key: ([A-Z]|[a-z]|[0-9])+ ;
attribute: Name '.' Key;
actions: 'Action(' changes ');' ;
changes: attribute operator;
operator: '+' expr | '−' expr | '∗' expr | expr;
NEWLINE : (' '|[\r\n\t])+ -> skip;


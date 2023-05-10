grammar Grammar2;
start: package* initiations* automations* EOF;
package: 'Use ' packagename EOL;
packagename: Name;
initiations: Type Name identifier EOL;
Type: 'Light ' | 'Sensor ';
Name: [A-Z]([A-Z]|[a-z]|[0-9]|'_'|'-')*;
identifier: IDValue
| Name
| IDValue ',' identifier
| Name ',' identifier;
IDValue: '"' ([A-Z]|[a-z]|[0-9]|'_'|'-')+ '"';
automations: scopeStart triggers* actions* scopeEnd EOL ;
scopeStart: 'Begin(Automation)';
scopeEnd: 'End(Automation)';
triggers: 'Trigger(' boolExpr ')' EOL;
actions: 'Action(' changes ')' EOL ;
boolExpr: bool 'and' boolExpr
| bool 'or' boolExpr
| bool
| '(' boolExpr ')';
bool: expr | expr '=' expr | expr '<' expr | expr '>' expr;
expr: 'TIME' | TimeValue | Value | attribute | Key;
TimeValue: ('0'[0-9]|'1'[0-9]|'2'[0-3])':'[0-5][0-9];
Value: [0-9]+
| [0-9]+'.'[0-9]+ ;
attribute: Name'.'Key;
Key: ([A-Z]|[a-z]|[0-9])+ ;
changes: attribute operator expr;
operator: '+' | '−' | '∗';
EOL: ';';
NEWLINE : (' '|[\r\n\t])+ -> skip;


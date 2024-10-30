grammar JavaScenario;

/*
 * Parser Rules
 */

start
    : ClassDeclaration test RCURLY EOF
    ;

test
    : (TestAnnotation TestDeclaration scenario)+
    ;

scenario
    :  (asmDeclaration | variableDeclaration | stepFunction| assertEquals | ~RCURLY)* RCURLY
    ;

asmDeclaration
    : ASMID ID EQ NEW ASMID LPAREN RPAREN SEMI
    ;

variableDeclaration
    : variableType variableName EQ variableValue SEMI
    ;

variableType
    : Identifier
    ;

variableName
    : ID
    ;

variableValue
    : Identifier
    ;

assertEquals
    : ASSERT_EQUALS LPAREN actual COMMA expected RPAREN SEMI
    ;

actual
    : (Identifier | INT+ | STRING)
    ;

expected
    : Getter
    ;

stepFunction
    : StepFunc LPAREN argument? (COMMA argument)* RPAREN SEMI
    ;

argument
    : (ID | STRING | INT+)
    ;

/*
 * Lexer Rules
 */

fragment TEXT
    : (ESC_SEQ | ~["\\])*
    ;

fragment ESC_SEQ
    : '\\' [btnfr"'\\]
    ;

COMMENT
    : '/*' .*? '*/' -> skip
    ;

ClassDeclaration
    : 'package' (.)*? LCURLY
    ;

TestAnnotation
    : '@Test' LPAREN (.)*? RPAREN
    ;

TestDeclaration
    : 'public' (.)*? LCURLY
    ;

StepFunc
    : ID DOT STEP
    ;

Getter
    : ID DOT GET ID LPAREN RPAREN
    ;

Identifier
    : ID (DOT ID)+
    ;

STRING
    : DOUBLE_QUOTES TEXT DOUBLE_QUOTES
    ;

ASMID
    : [a-zA-Z_][a-zA-Z_0-9]*'_ASM'
    ;

ASSERT_EQUALS
    : 'assertEquals'
    ;

AND : '&&' ;
OR : '||' ;
NOT : '!' ;
EQ : '=' ;
COMMA : ',' ;
COLONS : ':' ;
SEMI : ';' ;
LPAREN : '(' ;
RPAREN : ')' ;
LCURLY : '{' ;
RCURLY : '}' ;
DOT : '.' ;
START : '*' ;
AT : '@' ;
DOUBLE_QUOTES : '"' ;
NEW : 'new' ;
STEP : 'step' ;
GET : 'get_' ;

INT : [0-9]+ ;
ID: [a-zA-Z_][a-zA-Z_0-9]* ;
WS: [ \t\n\r\f]+ -> skip ;
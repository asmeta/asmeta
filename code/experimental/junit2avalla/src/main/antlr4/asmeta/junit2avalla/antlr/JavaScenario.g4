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
    :  (asmDeclaration | variableDeclaration | setFunction | stepFunction| assertEquals | trycatchblock | ~RCURLY)* RCURLY
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
    
setFunction
<<<<<<< Updated upstream
    : SetFunc variableName LPAREN argument RPAREN SEMI
=======
    : SetFunc LPAREN setVariableValue RPAREN SEMI
>>>>>>> Stashed changes
    ;

stepFunction
    : StepFunc LPAREN RPAREN SEMI
    ;
    
setVariableValue
    : (ID | STRING | INT+)
    ;

trycatchblock
    : TRY LCURLY (.)*? RCURLY CATCH LPAREN (.)*? LCURLY (.)*? RCURLY
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

LINE_COMMENT
    : '//' ~[\r\n]* -> skip
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

SetFunc
    : ID DOT SET
    ;

StepFunc
    : ID DOT STEP
    ;

Getter
    : ID DOT GET ID LPAREN RPAREN
    ;

TryCatch
    : 'try' RCURLY (.)*? LCURLY 'catch' (.)? RCURLY (.)? LCURLY
    ;

Identifier
    : ID (DOT ID)+
    ;

STRING
    : DOUBLE_QUOTES TEXT DOUBLE_QUOTES
    ;

ASMID
    : [a-zA-Z_][a-zA-Z_0-9]*'_ATG'
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
SET : 'set_' ;
STEP : 'step' ;
GET : 'get_' ;
TRY : 'try' ;
CATCH : 'catch' ;

INT : [0-9]+ ;
ID: [a-zA-Z_][a-zA-Z_0-9]* ;
WS: [ \t\n\r\f]+ -> skip ;
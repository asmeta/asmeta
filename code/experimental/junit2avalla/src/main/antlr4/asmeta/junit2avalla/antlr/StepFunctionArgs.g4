grammar StepFunctionArgs;

/*
 * Parser Rules
 */

argumentList
  : (argument (',' argument)*)?
  ;

argument
  : type name
  ;

type
  : (PrimitiveType | ComplexType )
  ;

name
  : Identifier
  ;

/*
 * Lexer Rules
 */

PrimitiveType
  : 'int' | 'double' | 'float' | 'boolean' | 'char' | 'String'
  ;

ComplexType
  : Identifier '.' Identifier ('.' Identifier)*
  ;

Identifier
  : [a-zA-Z_] [a-zA-Z_0-9]*
  ;

WS
  :  [ \t\r\n\u000C]+ -> skip
  ;
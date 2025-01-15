grammar JavaScenario;

 /*
 * Parser Rules
 */

start
	// Entry point: Defines the structure of a valid junit file.
	// package name.of.the.package;	--> ClassDeclaration
	//... 							--> ClassDeclaration
	// public class ClassName {		--> ClassDeclaration
	// class 						--> class
	// } 							--> RCURLY EOF
    : ClassDeclaration testClass RCURLY EOF 
    ;

testClass
	// The body of the junit test class, it consists of one ore more test methods
	// consists of one or more test methods formed by the structure
	// @Test(...) 				--> TestAnnotation
	// public void testName() { --> TestDeclaration
	// scenario } 				--> scenario
    : (TestAnnotation TestDeclaration scenario)+
    ;

scenario
 	// A scenario is defined by a sequence of actions (set step check), variable declarations, assertions, or try-catch blocks.
    // Ends with a }.
    :  (asmDeclaration | variableDeclaration | setFunction | stepFunction| assertEquals | assertBoolean | trycatchblock | ~RCURLY)* RCURLY
    ;

asmDeclaration
	// Represents an asmeta spec object instantiation: "ASMID ID = new ASMID();".
	// example: 
	// RegistroDiCassav4_ATG	--> ASMID
	// registroDiCassav4_ATG0	--> ID
	// = 						--> EQ
	// new 						--> NEW
	// RegistroDiCassav4_ATG	--> ASMID
	// (						--> LPAREN
	// )						--> RPAREN
	// ;						--> SEMI
    : ASMID ID EQ NEW ASMID LPAREN RPAREN SEMI
    ;

variableDeclaration
	// Represents a variable declaration and initialization: "variableType variableName = variableValue;".
    // example: 
    // RegistroDiCassav4.Servizio			--> variableType
    // registroDiCassav4_Servizio0 			--> variableName
    // = 									--> EQ
    // RegistroDiCassav4.Servizio.NEWORDINE	--> variableValue
    // ;									--> SEMI
    : variableType variableName EQ variableValue SEMI
    ;

variableType
	// A variable type can be an Identifier (if the type is not primitive) or a generic ID (for a primitive type).
	// example:
	// RegistroDiCassav4.Servizio	--> Identifier
	// String 						--> ID
    : (Identifier | ID)
    ;

variableName
 	// Represents the name of a variable.
 	// registroDiCassav4_Servizio0 --> ID
    : ID
    ;

variableValue
	// Defines the value assigned to a variable, which can be an Identifier (if the type is not primitive) 
	// or a Getter function (if the type is primitive).
    // example:
    // RegistroDiCassav4.Servizio.NEWORDINE	--> Identifier
    // registroDiCassav4_ATG0.get_outMess()	--> Getter
    // "card2"								--> String
    // (-125)								--> number
    // 'a'									--> char
    : (Identifier| Getter | STRING | (LPAREN? number RPAREN?) | CHARACTER)
    ;

assertEquals
	// Represents the assertEquals instruction block: "assertEquals(actual, expected);".
	// (COMMA number)? represents the delta: assertEquals(2.2, (double)double0, 0.01);
    : ASSERT_EQUALS LPAREN actual COMMA cast? expected (COMMA number)? RPAREN SEMI
    ;

assertBoolean
	// Represents the assertTrue or assertFalse instruction blocks: "assertTrue(expected)" or "assertFalse(expected)".
    : booleanAssertion LPAREN booleanExpected RPAREN SEMI
    ;

actual
	// The actual value to be tested, which can be an Identifier, an integer, or a string.
	// example:
	// RegistroDiCassav4.Stati.ATTENDI_ORDINAZIONI	--> Identifier
	// 20 											--> number
	// "margherita"									--> STRING
	// 'a'											--> char
    : (Identifier | number | STRING | CHARACTER)
    ;

expected
	// The expected value in an assertion, which can be a Getter (if the value is retrieved from the asm specification) 
	// or an ID (if the value is retrieved from a variable in the junit file).
	// example:
	// registroDiCassav4_ATG0.get_totale()	--> Getter
	// string0 								--> ID
    : (Getter | ID)
    ;

booleanAssertion
	// Defines boolean assertion keywords: "assertTrue" or "assertFalse".
    : (ASSERT_TRUE | ASSERT_FALSE)
    ;
    
booleanExpected
 	// Represents the expected boolean value in a boolean assertion.
 	// example:
	// pillbox1_ATG0.get_requestSatisfied()	--> Getter
    : Getter
    ;

setFunction
	// Represents a function to set a value: "SetFunc(setVariableValue);".
	// example:
	// registroDiCassav4_ATG0.set_sceltaDiAggiuntaPizza --> SetFunc
	// ( 												--> LPAREN
	// registroDiCassav4_AggiungiPizza0					--> setVariableValue
	// ) 												--> RPAREN
	// ;												--> SEMI
    : SetFunc LPAREN setVariableValue RPAREN SEMI
    ;

stepFunction
	// Represents a step function with no arguments: "StepFunc();".
	// example:
	// registroDiCassav4_ATG0.step	--> StepFunc
	// (							--> LPAREN
	// )							--> RPAREN
	// ;							--> SEMI
    : StepFunc LPAREN RPAREN SEMI
    ;
    
setVariableValue
	// Defines the value used in a setFunction, which can be a boolean, ID, string, or number.
	// example:
	// true							--> Boolean
	// int4 						--> ID
	// "margherita"					--> STRING
	// 20							--> number
	// 'a'							--> char
    : (Boolean | ID | STRING | number | CHARACTER )
    ;

trycatchblock
	// Represents a try-catch block: "try { ... } catch (...) { ... }".
    : TRY LCURLY (.)*? RCURLY CATCH LPAREN (.)*? LCURLY (.)*? RCURLY
    ;
    
cast
	// Represent an explicit type casting operation
	// example:
	// (	--> LPAREN
	// int 	--> ID
	// )	--> RPAREN
	: LPAREN ID RPAREN
	;

number
	// defines a number, a number can be positive, negative, integer or decimal.
	: MINUS? (FLOAT | INT)
	; 

/*
 * Lexer Rules
 */

fragment TEXT
	// Defines any text that is not a double quote or a backslash, or an escape sequence.
    : (ESC_SEQ | ~["\\])*
    ;

fragment ESC_SEQ
	// Represents escape sequences for common characters like \n, \t, etc.
    : '\\' [btnfr"'\\]
    ;

COMMENT
	// Multi-line comments: "/* comment */".
    : '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
	// Single-line comments: "// comment".
    : '//' ~[\r\n]* -> skip
    ;

ClassDeclaration
	// Represents a class declaration starting with "package" or "import", ending with an opening brace.
    // example: package org.evoservice.wrapper; ... {
    // or, if the class doesn't have a package: import org.junit.Test; ... {
    : ('package'|'import') (.)*? LCURLY
    ;

TestAnnotation
	// Represents a test annotation like "@Test(parameters)".
	// example: @Test(timeout = 4000)
    : '@Test' LPAREN (.)*? RPAREN
    ;

TestDeclaration
	// Represents a test method declaration starting with "public" and ending with an opening brace: "public ... {".
	// example: public void test00() throws Throwable {
    : 'public' (.)*? LCURLY
    ;

SetFunc
	// Represents a "set" function: "ID.set_ID".
	// example: registroDiCassav4_ATG0.set_sceltaDiAggiuntaPizza
	// registroDiCassav4_ATG0	--> ID
	// . 						--> DOT
	// set_						--> SET
	// sceltaDiAggiuntaPizza	--> ID
    : ID DOT SET ID
    ;

StepFunc
	// Represents a "step" function: "ID.step".
	// example: registroDiCassav4_ATG0.step
	// registroDiCassav4_ATG0	--> ID
	// .						--> DOT
	// step						--> STEP
    : ID DOT STEP
    ;

Getter
	// Represents a "get" function: "ID.get_ID()".
	// example: registroDiCassav4_ATG0.get_statoCassa()
	// registroDiCassav4_ATG0	--> ID
	// .						--> DOT
	// get_						--> GET
	// statoCassa				--> ID
	// (						--> LPAREN
	// )						--> RPAREN
    : ID DOT GET ID LPAREN RPAREN
    ;

Boolean
	// Represents boolean values: "true" or "false".
    :  (TRUE | FALSE)
    ;

Identifier
	// Represents a qualified identifier with dots
	// example: RegistroDiCassav4.Stati.SCEGLI_TIPO_DI_PIZZA
	// RegistroDiCassav4	 	--> ID
	// .Stati				 	--> (DOT ID) (required)
	// .SCEGLI_TIPO_DI_PIZZA	--> (DOT ID)
    : ID (DOT ID)+
    ;

STRING
	// Represents a string literal enclosed in double quotes.
	// example: "margherita"
    : DOUBLE_QUOTES TEXT DOUBLE_QUOTES
    ;
    
CHARACTER
	// Represents a char literal enclosed in single quote.
	// example: 'a'
	// ' 		--> SINGLE_QUOTE
	// '\\' -	-> for escape sequences like \n,\t, ...
	// ~[\\'] 	--> any character that isn't a \ or '
	// ' 		--> SINGLE_QUOTE
	: SINGLE_QUOTE ( '\\' . | ~[\\'] ) SINGLE_QUOTE
	;

ASMID
	// Represents an identifier for an asmeta specification object, ending with "_ATG".
	// example: RegistroDiCassav4_ATG
	// R 				--> [a-zA-Z_]
	// egistroDiCassav4	--> [a-zA-Z_0-9]*
	// _ATG 			--> _ATG
    : [a-zA-Z_][a-zA-Z_0-9]*'_ATG'
    ;

ASSERT_EQUALS : 'assertEquals' ;
ASSERT_TRUE : 'assertTrue' ;    
ASSERT_FALSE : 'assertFalse' ;
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
SINGLE_QUOTE : '\'';
NEW : 'new' ;
SET : 'set_' ;
STEP : 'step' ;
GET : 'get_' ;
TRY : 'try' ;
CATCH : 'catch' ;
TRUE : 'true' ;
FALSE : 'false' ;
MINUS : '-' ;
FLOAT : [0-9]+ '.' [0-9]+ ;
INT : [0-9]+ ;
ID: [a-zA-Z_][a-zA-Z_0-9]* ;
WS: [ \t\n\r\f]+ -> skip ;

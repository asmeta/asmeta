asm Bare

import ../STDL/StandardLibrary
//DOMAINS

//FUNCTIONS

//RULES
signature:
enum domain DomainEnumerative={DD|CC|EE}
domain D1 subsetof Integer
domain D2 subsetof Prod(String,Seq(Integer),D1)
domain D3 subsetof Seq(Prod(String,Integer,String))
domain D4 subsetof Prod(String,D1)
domain D5 subsetof String
domain D6 subsetof Prod(String,String)
domain D7 subsetof Seq(Seq(Integer))
dynamic domain D8 subsetof Integer
static staticFunction: D1
static staticFunction2: Prod(Integer,Integer)
static staticFunction4: Prod(Integer,D1)
static staticFunction3: Integer -> Integer
static staticFunction5: Seq(Prod(Integer,Integer)) -> Integer
controlled controlledFunction1: Integer
controlled controlledFunction2: Integer -> Integer
controlled controlledFunction3: Prod(D1,D1,D1) -> Integer
controlled controlledFunction4: String
controlled controlledFunction5: D1
derived derivedFunction: Integer
//static staticFunction2: Prod(Integer,Integer)-> Prod(Integer,Integer)
//anydomain D4

definitions:
domain D1={1,2,3}
domain D2={("ciao",[1 : 20],1),("prova",[2],3)}
domain D5={"ciao","prova","casa"}
domain D4={("ciao",2),("prova",6)}
domain D6={("ciao","ciao"),("prova","prova")}
domain D3={[("prova", 4, "prova2"),("provax", 5, "provax2")],[("prova1", 4, "prova12"),("provax1", 5, "provax12")]}
domain D7 = {[[1,5,2],[5,9,8],[5,8]]}
function staticFunction = 5
function staticFunction2 = (2,5)
function staticFunction3 ($a in Integer) = 10+$a
function staticFunction5 ($x in Seq(Prod(Integer,Integer))) = 10 + 5
function derivedFunction = controlledFunction1 + staticFunction3(5)

//function staticFunction2($a in Integer, $b in Integer) = (2,5)
rule r_Parameter ($param in Integer, $param2 in D5)=
		par 
			if (controlledFunction1<5) then
				controlledFunction3(1,1,1):=5
			endif
			if (staticFunction<5) then
				skip
			endif
			//if (controlledFunction1=5) then
			//	skip
			//endif
			if (controlledFunction4!="prova") then
				skip
			endif
			if (controlledFunction5=1) then
				skip
			endif
			if (controlledFunction3(1,1,1) = 5) then
				skip
			endif
		endpar

rule r_skipprova=
	skip	
	
main rule r_Main =
	r_skipprova[] 

	
default init s0:
	domain D8={1,2,5}
	function controlledFunction1=5
	function controlledFunction4="provafunzione"
	function controlledFunction3($x in D1, $y in D1, $z in D1)= $x+$y+$z

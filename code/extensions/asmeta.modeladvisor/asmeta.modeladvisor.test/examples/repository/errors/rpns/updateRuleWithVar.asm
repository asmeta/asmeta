//errore in una update rule se viene utilizzata una variabile come valore
//la versione di ASMETA 1129 non presenta questo errore.
asm updateRuleWithVar

import ../../STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled fooA : MyDomain -> MyDomain
	dynamic controlled fooB : MyDomain -> MyDomain
	dynamic controlled fooC : MyDomain -> MyDomain

definitions:
	domain MyDomain = {1 : 3}

	main rule r_Main =
		//errore ArrayIndexOutOfBoundsException in RuleEvaluator.visitForall
		forall $x in MyDomain with true do
			fooA($x) := $x
		
		//errore ArrayIndexOutOfBoundsException in RuleEvaluator.visitForall
		// forall $y in MyDomain with $y=1 do
		// 	fooB(1) := $y
			
		//errore ArrayIndexOutOfBoundsException in RuleEvaluator.visitChoose
		// choose $x in MyDomain with true do
		// 	fooB(1) := $x
			
		//nessun errore
		// forall $z in MyDomain with true do
		// 	fooC($z) := 1

default init s0:
	function fooA($x in  MyDomain) = $x
	function fooB($y in  MyDomain) = $y
	function fooC($z in  MyDomain) = $z

asm arity2and3
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain SubDom subsetof Integer
	enum domain EnumDom = {AA | BB}
	dynamic controlled foo2: Prod(Boolean, EnumDom) -> SubDom
	dynamic controlled foo3: Prod(SubDom, EnumDom, SubDom) -> Boolean

definitions:
	domain SubDom = {1..2}
	
	main  rule r_Main =
		par
		forall $b  in Boolean, $e in EnumDom do foo2($b,$e):=1
		forall $q in SubDom,$w in EnumDom, $i in SubDom do foo3($q,$w,$i):=false
		endpar

default init s0:
	//function foo2($b in Boolean, $e in EnumDom) = 2
	//function foo3($s in SubDom, $e in EnumDom, $i in SubDom) = true

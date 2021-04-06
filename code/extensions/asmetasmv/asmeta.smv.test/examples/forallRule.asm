asm forallRule
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom -> ConcrDom
	
definitions:
	domain ConcrDom = {1:4}
	
	CTLSPEC ax(ag(foo(1)=3 and foo(2)=2))
	CTLSPEC ag(foo(4)=4)
	CTLSPEC ax(ag(foo(3)=2))
	
	main rule r_Main = 
		par
			forall $x in ConcrDom with $x < 3 do
				foo($x) := 4 - $x
			forall $y in ConcrDom with foo(4) - 1 = $y do
				foo($y) := $y - 1
		endpar

default init s0:
	function foo($x in ConcrDom) = 4
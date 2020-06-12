asm untilEwAw
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom
	dynamic controlled fooA: ConcrDom

definitions:
	domain ConcrDom = {0 .. 10}

	CTLSPEC a(foo < 10, ax(fooA = 1))
	
	//ew(p, q) = E[p U q] | EGp
	CTLSPEC ew(foo <= 10, ag(fooA = 1))

	main rule r_main =
		if(foo < 10) then
			foo := foo +1
		else
			if(fooA=0) then
				fooA := 1
			else
				choose $x in ConcrDom with $x > 0 do
					fooA := $x
			endif
		endif

default init s0:
	function foo = 0
	function fooA = 0
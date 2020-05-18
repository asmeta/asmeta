asm minusUnary
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain ConcrDom subsetof Integer
	domain ConcrDomA subsetof Integer
	domain ConcrDomB subsetof Integer
	dynamic controlled foo: ConcrDom
	dynamic controlled fooA: ConcrDomA
	dynamic controlled fooB: ConcrDomB

definitions:
	domain ConcrDom = {-2, -1, 0, 1, 2}
	domain ConcrDomA = {-2 .. 2}
	domain ConcrDomB = {-10 .. 10, 2}

	CTLSPEC foo = -2 and ax(foo=-1 and ax(foo=0 and ax(foo=1 and ax(foo=2))))
	CTLSPEC fooA = -2 and ax(fooA=-1 and ax(fooA=0 and ax(fooA=1 and ax(fooA=2))))
	
	CTLSPEC fooB = -10 and ax(fooB=-8 and ax(fooB=-6 and ax(fooB=-4 and ax(fooB=-2 and ag(fooB=-2)))))
	
	main rule r_main =
		par
			if (foo < 2) then
				foo := foo + 1
			endif
			if (fooA < 2) then
				fooA := fooA + 1
			endif
			if (fooB < -2) then
				fooB := fooB + 2
			endif
		endpar

default init s0:
	function foo = -2
	function fooA = -2
	function fooB = -10

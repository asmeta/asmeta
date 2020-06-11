asm abs
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom
	dynamic monitored mon: ConcrDom
	dynamic monitored monA: ConcrDom
	dynamic controlled fooA: ConcrDom
	dynamic controlled fooB: ConcrDom
	dynamic controlled fooC: ConcrDom
	dynamic controlled fooD: ConcrDom
	dynamic controlled fooE: ConcrDom
	dynamic controlled fooF: ConcrDom
	dynamic controlled fooG: ConcrDom
	dynamic controlled fooH: ConcrDom
	dynamic controlled fooI: ConcrDom
	dynamic controlled fooL: ConcrDom

definitions:
	domain ConcrDom = {-10 .. 10}

	CTLSPEC ctlSpec_f: ag(foo >= 0)
	CTLSPEC ag(fooA >= 0)
	CTLSPEC ag(fooB > 0)
	CTLSPEC fooC=0 and ax(ag(fooC = 1))
	CTLSPEC fooD=0 and ax(ag(fooD = 3))
	CTLSPEC fooE=1 and ax(ag(fooE = 0))
	CTLSPEC fooF=0 and ax(ag(fooF = 4))
	CTLSPEC ag(ef(mon >= 0))
	CTLSPEC ag(ef(mon < 0))
	CTLSPEC ag(fooG=5)
	CTLSPEC ag(fooH=2)
	CTLSPEC ag(fooI=8)

	main rule r_main =
		par
			foo := abs(mon)
			choose $x in ConcrDom with true do
				fooA := abs($x)
			choose $y in ConcrDom with $y < 0 do
				fooB := abs($y)
			choose $z in ConcrDom with $z = abs(-1) do
				fooC := $z
			fooD := abs(-3)
			fooE := abs(0)
			fooF := abs(4)
			fooG := abs(4 + 1)
			fooH := abs(4 - 6)
			fooI := abs(4 * -2)
			fooL := abs(idiv(mon,2) + abs(idiv(monA,2)))
		endpar

default init s0:
	function foo = 1
	function fooA = 2
	function fooB = 3
	function fooC = 0
	function fooD = 0
	function fooE = 1
	function fooF = 0
	function fooG = 5
	function fooH = 2
	function fooI = 8
	function fooL = abs(idiv(mon,2) + abs(idiv(monA,2)))

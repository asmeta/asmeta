asm max
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain ConcrDom subsetof Integer
	domain ConcrDomN subsetof Natural
	dynamic controlled foo: ConcrDom
	dynamic controlled fooX: ConcrDom
	dynamic controlled fooY: ConcrDom
	dynamic controlled fooN: ConcrDomN
	dynamic controlled fooK: ConcrDomN
	dynamic controlled fooZ: ConcrDomN
	dynamic controlled fooA: ConcrDom
	dynamic controlled fooB: ConcrDom
	dynamic controlled fooC: ConcrDom
	dynamic controlled fooD: ConcrDom
	dynamic controlled fooE: ConcrDom
	dynamic controlled fooF: ConcrDom
	dynamic controlled fooG: ConcrDom
	dynamic controlled fooFN: ConcrDomN
	dynamic controlled fooGN: ConcrDomN
	
definitions:
	domain ConcrDom = {-10 .. 10}
	domain ConcrDomN = {1n .. 20n}

	CTLSPEC ag(foo >= fooX and foo >= fooY)
	CTLSPEC ag(foo = fooX or foo = fooY)
	CTLSPEC ag(fooN >= fooK and fooN >= fooZ)
	CTLSPEC ag(fooN = fooK or fooN = fooZ)
	CTLSPEC ag(fooA = -1)
	CTLSPEC ag(fooB = -2)
	CTLSPEC ag(fooC = 3)
	CTLSPEC ag(fooD = 4)
	CTLSPEC ag(fooE = 0)
	CTLSPEC ag(fooF = 5)
	CTLSPEC ag(fooG = 3)
	CTLSPEC ag(fooFN = 5n)
	CTLSPEC ag(fooGN = 3n)

	main rule r_main =
		par
			choose $x in ConcrDom, $y in ConcrDom with true do
				par
					foo := max($x, $y)
					fooX := $x
					fooY := $y
				endpar
			choose $k in ConcrDomN, $z in ConcrDomN with true do
				par
					fooN := max($k, $z)
					fooK := $k
					fooZ := $z
				endpar
			fooA := max(-1, -1)
			fooB := max(-2, -3)
			fooC := max(-1, 3)
			fooD := max(4, -2)
			fooE := max(0, 0)
			fooF := max(2, 5)
			fooG := max(3, 2)
			fooFN := max(2n, 5n)
			fooGN := max(3n, 2n)
		endpar

default init s0:
	function foo = 3
	function fooX = 3
	function fooY = 2
	function fooN = 3n
	function fooK = 3n
	function fooZ = 2n
	function fooA = -1
	function fooB = -2
	function fooC = 3
	function fooD = 4
	function fooE = 0
	function fooF = 5
	function fooG = 3
	function fooFN = 5n
	function fooGN = 3n

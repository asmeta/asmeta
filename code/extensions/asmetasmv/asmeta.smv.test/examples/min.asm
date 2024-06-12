asm min
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain ConcrDom subsetof Integer
	domain ConcrDomN subsetof Natural
	dynamic controlled foo: ConcrDom
	dynamic controlled fooX: ConcrDom
	dynamic controlled fooY: ConcrDom
	dynamic controlled fooN: ConcrDomN
	dynamic controlled fooK: ConcrDomN
	dynamic controlled fooZ: ConcrDomN
	dynamic controlled fooT: ConcrDomN
	
definitions:
	domain ConcrDom = {-10 : 10}
	domain ConcrDomN = {1n : 20n}

	CTLSPEC ag(foo <= fooX and foo <= fooY)
	CTLSPEC ag(foo = fooX or foo = fooY)
	CTLSPEC ag(fooN <= fooK and fooN <= fooZ)
	CTLSPEC ag(fooN = fooK or fooN = fooZ)
	
	main rule r_main =
		par
			choose $x in ConcrDom, $y in ConcrDom with true do
				par
					foo := min($x, $y)
					fooX := $x
					fooY := $y
				endpar
			choose $k in ConcrDomN, $z in ConcrDomN with true do
				par
					fooN := min($k, $z)
					fooK := $k
					fooZ := $z
				endpar
			fooT := min(fooK, fooZ)
		endpar

default init s0:
	function foo = 2
	function fooX = 3
	function fooY = 2
	function fooN = 2n
	function fooK = 3n
	function fooZ = 2n

asm overload

import ../../STDL/StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom -> Boolean
	dynamic controlled foo: Boolean -> Boolean
	dynamic controlled foo: ConcrDom -> ConcrDom

definitions:
	domain ConcrDom = {1 : 10}

	main rule r_Main =
		par
			choose $a in EnumDom, $b in Boolean with true do
				foo($a) := $b
			choose $c in Boolean, $d in Boolean with true do
				foo($c) := $d
			choose $e in ConcrDom, $f in ConcrDom with true do
				foo($e) := $f
		endpar

default init s0:
	function foo($c in EnumDom) = false
	function foo($c in Boolean) = true
	function foo($c in ConcrDom) = 1

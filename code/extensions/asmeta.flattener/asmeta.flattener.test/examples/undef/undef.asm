asm undef

import ../../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain ConcrDomain subsetof Integer
	domain ConcrDomain2 subsetof Integer
	enum domain EnumDom = {AA | BB | CC}
	enum domain EnumDom2 = {AA2 | BB2 | CC2}
	dynamic controlled fooI: ConcrDomain
	dynamic controlled fooE: EnumDom
	dynamic controlled fooEa: EnumDom
	dynamic controlled fooEb: EnumDom
	dynamic controlled fooB: ConcrDomain
	dynamic controlled fooEc: EnumDom
	dynamic controlled fooEd: ConcrDomain -> EnumDom

	dynamic controlled fooI2: ConcrDomain2
	dynamic controlled fooI2a: ConcrDomain2 -> Boolean

definitions:
	domain ConcrDomain = {1..3}
	domain ConcrDomain2 = {1..3}

	main rule r_Main =
		par
			fooI := undef // cbu
			fooE := undef //cbu
			fooEa := undef // cbu
			fooEb := fooEa // cbu
			fooB := undef //cbu
			fooEc := fooEd(fooB) // CBU
			fooI2 := undef // cbu
			fooI2a(fooI2) := true //CBU
		endpar

default init s0:
	function fooI = 1
	function fooE = AA
	function fooEa = BB
	function fooB = 1
	function fooEd($b in ConcrDomain) = AA // cbu $b undef, undef
	function fooI2 = 1
	function fooI2a($a in ConcrDomain2) = false // CBU if $a undef, undef

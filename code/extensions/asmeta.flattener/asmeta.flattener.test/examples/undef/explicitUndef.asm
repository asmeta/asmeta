asm explicitUndef

import ../../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain ConcrDomain subsetof Integer
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled fooI: ConcrDomain //undef in an update
	dynamic controlled fooE: EnumDom //undef in an update
	dynamic controlled fooB: Boolean //undef in an update
	dynamic controlled fooIis: ConcrDomain //undef in the initial state
	dynamic controlled fooEis: EnumDom //undef in the initial state
	dynamic controlled fooBis: Boolean //undef in the initial state

definitions:
	domain ConcrDomain = {1:3}

	main rule r_Main =
		par
			fooI := undef
			fooE := undef
			fooB := undef
			fooIis := 2
			fooEis := BB
			fooBis := true
		endpar

default init s0:
	function fooI = 1
	function fooE = AA
	function fooB = false

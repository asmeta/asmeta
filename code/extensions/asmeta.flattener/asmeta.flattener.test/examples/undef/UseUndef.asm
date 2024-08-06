asm UseUndef

import ../../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain ConcrDomain subsetof Integer
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled fooI: ConcrDomain
	dynamic controlled fooE: EnumDom

definitions:
	domain ConcrDomain = {1:3}

	main rule r_Main =
		par
			if fooI = undef then fooI:=1 endif
			if fooE = undef then fooE:=AA endif
		endpar

default init s0:
	function fooI = 1
	function fooE = AA

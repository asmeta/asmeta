// directly use undef in the specification
// in comparison (instea do isUndef
// in assignemnts
asm UseUndef

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain ConcrDomain subsetof Integer
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled fooI: ConcrDomain
	dynamic controlled fooE: EnumDom
	dynamic controlled fooE2: EnumDom

definitions:
	domain ConcrDomain = {1:3}

	ctlspec ag(fooI = 1)
	ctlspec ef(fooE2 = undef)


	main rule r_Main =
		par
			if fooI = undef then fooI:= 1 endif
			if fooE = undef then fooE:= AA endif
			if fooE = AA then fooE2:= undef endif
		endpar

default init s0:
	function fooI = 1
	function fooE = AA

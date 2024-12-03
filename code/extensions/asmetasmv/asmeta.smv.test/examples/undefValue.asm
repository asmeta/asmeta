asm undefValue
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain ConcrDom subsetof Integer
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled fooA: EnumDom
	dynamic controlled fooB: EnumDom
	dynamic controlled fooC: EnumDom
	dynamic controlled fooD: EnumDom
	dynamic controlled fooE: EnumDom
	dynamic controlled fooF: EnumDom

	dynamic controlled fooG: ConcrDom
	dynamic controlled fooH: ConcrDom
	dynamic controlled fooI: ConcrDom
	dynamic controlled fooJ: ConcrDom
	dynamic controlled fooK: ConcrDom
	dynamic controlled fooL: ConcrDom
	dynamic controlled fooM: ConcrDom
	dynamic monitored mon: Boolean
	dynamic monitored mon2: Boolean
definitions:
	domain ConcrDom = {1:2}

	CTLSPEC isDef(fooB) and ax(ag(isUndef(fooB)))
	CTLSPEC isUndef(fooC) and ax(ag(isDef(fooC)))
	CTLSPEC ag(isUndef(fooD))
	CTLSPEC isUndef(fooE) and ax(ag(isDef(fooE)))
	CTLSPEC ag(isUndef(fooF))
	CTLSPEC ag(isDef(fooG))
	CTLSPEC isDef(fooH) and ax(ag(isUndef(fooH)))
	CTLSPEC isUndef(fooI) and ax(ag(isDef(fooI)))
	CTLSPEC isUndef(fooK) and ax(ag(isDef(fooK)))
	CTLSPEC ag(isUndef(fooL))
	CTLSPEC isUndef(fooM) and ax(ag(fooM=2))
	CTLSPEC ag(isDef(fooA))
	
	main  rule r_Main =
		par
			fooA := BB
			fooB := undef
			fooC := AA
			fooE := BB
			fooF := undef

			fooG := 2
			fooH := undef
			fooI := 1
			fooK := 2
			fooL := undef
			if(isUndef(fooG)) then
				fooM := 1
			else
				fooM := 2
			endif
		endpar

default init s0:
	function fooA = AA
	function fooB = CC
	function fooC = undef
	function fooG = 1
	function fooH = 2
	function fooI = undef
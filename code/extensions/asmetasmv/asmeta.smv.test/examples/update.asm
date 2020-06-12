asm update
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic monitored mon: Boolean
	dynamic controlled foo: EnumDom
	dynamic controlled foo1: EnumDom

definitions:

	CTLSPEC ag(ef(mon))
	CTLSPEC ag(ef(not(mon)))
	CTLSPEC ag(mon implies ax(foo = AA and foo1 = CC))
	CTLSPEC ag(not(mon) implies ax(foo = BB and foo1 = AA))

	main  rule r_Main = 
		if(mon) then
			par
				foo := AA
				foo1 := CC
			endpar
		else
			par
				foo := BB
				foo1 := AA
			endpar
		endif
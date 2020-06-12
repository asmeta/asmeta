asm args
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic monitored mon: EnumDom
	dynamic controlled foo: EnumDom -> Boolean

definitions:
	main  rule r_Main =
		foo(mon) := true

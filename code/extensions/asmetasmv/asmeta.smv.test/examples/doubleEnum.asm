asm doubleEnum
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | AA}
	dynamic controlled foo: EnumDom

definitions:
	main rule r_Main =
		foo := AA

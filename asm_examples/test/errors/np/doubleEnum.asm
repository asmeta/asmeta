asm doubleEnum

import ../../../STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | AA}
	dynamic controlled foo: EnumDom

definitions:
	main rule r_Main =
		foo := AA

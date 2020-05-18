asm funcs

import StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom -> EnumDom
	dynamic controlled fooA: Boolean -> EnumDom
	dynamic controlled fooB: Boolean -> Integer
	dynamic controlled fooC: Prod(Boolean, EnumDom) -> Integer

definitions:

	main rule r_Main =
		skip

default init s0:
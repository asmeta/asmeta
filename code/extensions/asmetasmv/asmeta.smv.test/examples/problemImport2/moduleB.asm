module moduleB
export r_b

signature:
	enum domain EnumDom = {AA | BB}
	dynamic controlled foo: EnumDom

definitions:
	rule r_b =
		foo := BB

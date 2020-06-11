module moduleB
export foo, EnumDom

signature:
	enum domain EnumDom = {AA | BB}
	dynamic controlled foo: EnumDom

definitions:

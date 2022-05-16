module moduloC

export *

signature:
	domain ImportedDomain subsetof Integer
	enum domain EnumDomain = {ZZ | RR}
	dynamic controlled fooEnum: EnumDomain -> Boolean
		
definitions:
	domain ImportedDomain = {1: 3}

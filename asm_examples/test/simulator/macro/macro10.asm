module macro10

	import ../../../STDL/StandardLibrary
	export Chan, ChanBool, EnumD, func, punc, r_macro
	
signature:

	abstract domain Chan
	domain ChanBool subsetof Chan
	enum domain EnumD = {ABC | DEF}

	controlled func: String -> String
	controlled punc: Integer
	
definitions:

	macro rule r_macro = skip
	

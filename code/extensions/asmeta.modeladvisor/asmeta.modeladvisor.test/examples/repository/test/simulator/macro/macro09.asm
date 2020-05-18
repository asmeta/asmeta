asm macro09

	import ../../../STDL/StandardLibrary
	import macro10
	
signature:

definitions:

	macro rule r_fool($p in Rule) =
		skip
		
	macro rule r_odd($l in String, $m in Integer, $n in Boolean) =
		let ($var = 0) in
			r_fool[<<r_macro>>]
		endlet
		
	macro  rule r_bar($y in String, $z in Integer) =
		par
			skip
			r_odd[func("hello"), $z, true]
		endpar
		
	macro rule r_foo($x in String) =
		seq
			skip
			r_bar[$x, punc]
		endseq
		
	main rule r_main =
		r_foo["hello"]
		

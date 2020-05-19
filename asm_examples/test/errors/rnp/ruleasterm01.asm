asm ruleasterm01

	import ../../STDL/StandardLibrary
	
signature:

	controlled count: Integer
	controlled foo: Rule
	
definitions:

	macro rule r_run =
		count := count + 1
		
	macro rule r_run2($exec in Rule) =
		$exec
				
	main rule r_main =
		r_run2[<<r_run>>]
		
default init s0:

	function count = 0
	function foo = <<r_run>>
	

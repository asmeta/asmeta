asm rule01

	import ../../STDL/StandardLibrary
	
signature:

	controlled count: Integer
	controlled foo: Rule(Integer)
	
definitions:

	macro rule r_run($x in Integer) =
		count := count + $x
		
//	macro rule r_run2($exec in Rule) =
//		$exec
				
	main rule r_main =
		foo[5]
		
default init s0:

	function count = 0
	function foo = <<r_run>>
	

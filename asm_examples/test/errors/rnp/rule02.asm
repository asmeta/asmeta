asm rule02

	import ../../STDL/StandardLibrary
	
signature:

	abstract domain Process
	domain Clock subsetof Process

	controlled foo: Rule(Process)
	
	static clock: Clock
	
definitions:

	macro rule r_run($c in Clock) =
		skip
	
	main rule r_main =
		foo := <<r_run>>
		

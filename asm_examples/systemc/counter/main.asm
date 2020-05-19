asm main

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanint
	import ../sched/sched
	import top
	
signature:

definitions:

	main rule r_main =
		r_start[1000, <<r_initTop>>]
		

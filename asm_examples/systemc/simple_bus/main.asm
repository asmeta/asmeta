asm main

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/sched
	import request
	import simple_bus
	import top
	
signature:

definitions:

	main rule r_main =
		r_start[3000, <<r_initTop>>]
		

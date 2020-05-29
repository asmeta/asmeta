asm main

	import ../../../STDL/StandardLibrary
	import ../../../systemc/sched/common
	import ../../../systemc/sched/sched
	import ../../../systemc/simple_bus/request
	import simple_bus
	import top
	
signature:

definitions:

	main rule r_main =
		r_start[3000, <<r_initTop>>]
		

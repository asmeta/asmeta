asm main

	import ../../STDL/StandardLibrary
	import ../sched/clock
	import ../sched/chanbool
	import ../sched/common
	import ../sched/sched
	import top
	
signature:

definitions:

	main rule r_main =
		r_start[100, <<r_initTop>>]
		

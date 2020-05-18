module top

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/clock
	import monitor
	export r_initTop, clock_top
	
signature:

	static clock_top: Clock
	
definitions:

	macro rule r_initTop =
		seq
			r_initClock[clock_top, 20, 0.75, 30, true]
			extend Monitor with $m do
				r_initMonitor[$m, clockOutput(clock_top)]
		endseq
	

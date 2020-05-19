module top

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	import ../sched/chanint
	import ../sched/clock
	import driver
	import monitor
	import counter
	export r_initTop, out_top

signature:

static out_top: ChanInteger

definitions:

	macro rule r_initTop =
	//extend ChanInteger with $out_top do
		extend Clock with $clock do
			extend ChanBoolean with $load do
				extend ChanInteger with $din do
					extend Driver with $driver do
						extend Monitor with $monitor do
							extend Counter with $counter do seq
								r_initClock[$clock, 20, 0.5, 10, true]
								r_init[$load]
								r_initInt[$din]
								r_initInt[out_top]
								r_initDriver[$driver, 8, $load, $clock, $din, out_top]
								r_initMonitor[$monitor, out_top]
								r_initCounter[$counter, $load, $clock, $din, out_top]
							endseq
							

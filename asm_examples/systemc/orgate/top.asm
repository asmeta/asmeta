module top

	import ../../STDL/StandardLibrary
	import ../sched/chanbool
	import driver
	import orgate
	import monitor
	export r_initTop, in1_top, in2_top, out_top

signature:

	static in1_top: ChanBoolean
	static in2_top: ChanBoolean
	static out_top: ChanBoolean
	
definitions:

	macro rule r_initTop =
	//extend ChanBoolean with $in1_top, $in2_top, $out_top do
		seq
			r_init[in1_top]
			r_init[in2_top]
			r_init[out_top]			
			extend Driver with $d do
				r_initDriver[$d, in1_top, in2_top]
			extend OrGate with $g do
				r_initOrGate[$g, in1_top, in2_top, out_top]
			extend Monitor with $m do
				r_initMonitor[$m, in1_top, in2_top, out_top]
		endseq
		
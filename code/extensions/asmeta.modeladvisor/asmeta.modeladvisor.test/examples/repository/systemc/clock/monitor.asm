module monitor

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	export Monitor, r_initMonitor, r_runMonitor

signature:

	dynamic abstract domain Monitor
	
	controlled input1: Monitor -> ChanBoolean
	controlled owner: Process -> Monitor
	
definitions:

	macro rule r_runMonitor =
		let ($p = current_exec,
			$m = owner($p)) in seq
			result := print("monitor", time, read(input1($m)))
			r_waitStatic[$p]
		endseq endlet

	macro rule r_initMonitor($m in Monitor, $c1 in ChanBoolean) =
		extend Process with $p do seq
			input1($m) := $c1
			owner($p) := $m
			r_thread[$p, <<r_runMonitor>>]
			r_sensitive[$p, defaultEvent($c1)]
			r_dontInitialize[$p]
		endseq
		
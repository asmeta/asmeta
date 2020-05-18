module driver

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	export Driver, r_initDriver, r_runDriver

signature:

	dynamic abstract domain Driver

	// input ports
	controlled output1: Driver -> ChanBoolean
	controlled output2: Driver -> ChanBoolean
	// current step
	controlled step: Driver -> Integer
	controlled owner: Process -> Driver

definitions:

	macro rule r_runDriver =
		let ($p = current_exec,
			$d = owner($p)) in
			if step($d) = 0 then seq
				r_write[output1($d), true]
				r_write[output2($d), true]
				step($d) := 1
				r_waitTimeOut[$p, 5]
			endseq else if step($d) = 1 then seq
				r_write[output2($d), false]
				step($d) := 2
				r_waitTimeOut[$p, 5]
			endseq else if step($d) = 2 then seq
				r_write[output1($d), false]
				step($d) := 3
				r_waitTimeOut[$p, 5]
			endseq else if step($d) = 3 then seq
				r_write[output2($d), true]
				step($d) := 4
				r_waitTimeOut[$p, 5]
			endseq else if step($d) = 4 then seq
				skip
				r_stop[$p]
			endseq endif endif endif endif endif
		endlet
		
	macro rule r_initDriver(
	$d in Driver, $c1 in ChanBoolean, $c2 in ChanBoolean) =
		extend Process with $p do seq
			step($d) := 0
			output1($d) := $c1
			output2($d) := $c2
			owner($p) := $d
			r_thread[$p, <<r_runDriver>>]
		endseq
		

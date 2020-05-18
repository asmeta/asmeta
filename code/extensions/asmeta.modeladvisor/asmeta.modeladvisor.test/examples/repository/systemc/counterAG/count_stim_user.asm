module count_stim_user

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	import ../sched/chanint
	import ../sched/clock
	export Count_stim_user, r_initCount_stim, r_stimgen

signature:

	dynamic abstract domain Count_stim_user

	// ports
	controlled load: Count_stim_user -> ChanBoolean
	controlled clock: Count_stim_user -> Clock
	controlled din: Count_stim_user -> ChanInteger
	controlled dout: Count_stim_user -> ChanInteger
	// fields
	controlled maxcount: Count_stim_user -> Integer
	// asm only fields
	controlled owner: Process -> Count_stim_user

	// user inputs
	monitored load_cmd: Count_stim_user -> Boolean
	monitored din_value: Count_stim_user -> Integer


definitions:

	macro rule r_stimgen =
	// retrive process' context
	// $p is the running process, $d is the Count_stim's instance
	let ($p = current_exec,
		$d = owner($p)) in
		// body
		seq
			// indside the main loop
			if load_cmd($d) then seq
				r_write[load($d), true]
				r_writeInt[din($d), din_value($d)]
			endseq else
				r_write[load($d), false]
			endif
			r_waitStatic[$p]
		endseq 
	endlet

	// ctor
	macro rule r_initCount_stim(
	$d in Count_stim_user,
	$maxcount in Integer,
	$load in ChanBoolean,
	$clock in Clock,
	$din in ChanInteger,
	$dout in ChanInteger) =
		extend Process with $p do seq
			// init ports and fields
			maxcount($d) := $maxcount
			load($d) := $load
			clock($d) := $clock
			din($d) := $din
			dout($d) := $dout
			owner($p) := $d
			// set up process
			r_thread[$p, <<r_stimgen>>]
			r_dontInitialize[$p]
			r_sensitive[$p, posEdgeEvent($clock)]
		endseq


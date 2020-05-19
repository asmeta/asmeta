module count_stim

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	import ../sched/chanint
	import ../sched/clock
	export Count_stim, r_initCount_stim, r_stimgen

signature:

	dynamic abstract domain Count_stim

	// ports
	controlled load: Count_stim -> ChanBoolean
	controlled clock: Count_stim -> Clock
	controlled din: Count_stim -> ChanInteger
	controlled dout: Count_stim -> ChanInteger
	// fields
	controlled maxcount: Count_stim -> Integer
	// asm only fields
	controlled step: Count_stim -> Integer
	controlled owner: Process -> Count_stim

definitions:

	macro rule r_stimgen =
	// retrive process' context
	// $p is the running process, $d is the Count_stim's instance
	let ($p = current_exec,
		$d = owner($p)) in
		// body
		if step($d) = 0 then seq
			// outside the main loop: executed only once
			r_write[load($d), true]
			r_writeInt[din($d), 0]
			step($d) := 1
			// dont' forget to suspend the process!
			r_waitStatic[$p]
		endseq else seq
			// indside the main loop
			if readInt(dout($d)) = maxcount($d) - 1 then seq
				r_write[load($d), true]
				r_writeInt[din($d), 0]
			endseq else
				r_write[load($d), false]
			endif
			r_waitStatic[$p]
		endseq endif
	endlet

	// ctor
	macro rule r_initCount_stim(
	$d in Count_stim,
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
			step($d) := 0
			owner($p) := $d
			// set up process
			r_thread[$p, <<r_stimgen>>]
			r_dontInitialize[$p]
			r_sensitive[$p, posEdgeEvent($clock)]
		endseq

// invariants - inseriti da AG
//        invariant inv_max over dout: let ($out = readInt(dout(owner(current_exec)))) in (isDef($out) implies $out < maxcount(owner(current_exec))) endlet

// questo è falso:        invariant inv_max over dout: (forall $c in Count_stim with let  ($out = readInt(dout($c))) in ($out != undef implies $out < maxcount($c)) endlet)


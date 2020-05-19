module count

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	import ../sched/chanint
	import ../sched/clock
	export Count, r_initCount, r_count_up

signature:

	dynamic abstract domain Count

	// ports
	controlled load: Count -> ChanBoolean
	controlled clock: Count -> Clock
	controlled din: Count -> ChanInteger
	controlled dout: Count -> ChanInteger
	// fields
	controlled count_val: Count -> Integer
	// asm only fields
	controlled owner: Process -> Count

definitions:

	macro rule r_count_up =
	// retrive process' context
	// $p is the running process, $c is the Count's instance
	let ($p = current_exec,
		$c = owner($p)) in
		// process' body
		seq
			if read(load($c)) then
				count_val($c) := readInt(din($c))
			else
				count_val($c) := count_val($c) + 1
			endif
			r_writeInt[dout($c), count_val($c)]
			// asm only: when done, suspend the process (thread or method)
			r_waitStatic[$p]
		endseq
	endlet

	// ctor
	macro rule r_initCount(
	$c in Count,
	$load in ChanBoolean,
	$clock in Clock,
	$din in ChanInteger,
	$dout in ChanInteger) =
		extend Process with $p do seq
			// init ports and fields
			load($c) := $load
			clock($c) := $clock
			din($c) := $din
			dout($c) := $dout
			count_val($c) := 0
			owner($p) := $c
			// set up process
			// in asm there are only threads
			r_thread[$p, <<r_count_up>>]
			r_dontInitialize[$p]
			r_sensitive[$p, posEdgeEvent($clock)]
		endseq


// invariant (aggiunti da AG)
 invariant inv_count_val over count_val : (forall $c in Count with count_val($c) != undef implies count_val($c) >= 0 )

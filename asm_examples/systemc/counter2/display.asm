module display

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanint
	export Display, r_initDisplay, r_display_count

signature:

	dynamic abstract domain Display
	
	// ports
	controlled dout: Display -> ChanInteger
	// asm only fields
	controlled owner: Process -> Display
	
definitions:

	macro rule r_display_count =
	// retrive process' context
	// $p is the running process, $m is the Display's instance
	let ($p = current_exec,
		$m = owner($p)) in
		// body
		seq
			result := print("Monitor", time, readInt(dout($m)))
			// asm only: when done, suspend the process (thread or method)
			r_waitStatic[$p]
		endseq
	endlet
	
	// ctor
	macro rule r_initDisplay($m in Display, $c1 in ChanInteger) =
		extend Process with $p do seq
			// init ports and fields
			dout($m) := $c1
			owner($p) := $m
			// set up process
			// in asm there are only threads
			r_thread[$p, <<r_display_count>>]
			r_sensitive[$p, defaultEvent($c1)]
			r_dontInitialize[$p]
		endseq
		
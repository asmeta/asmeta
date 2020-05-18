module display

	import StandardLibrary
	import chan
	//import common
	//import chanint
	export Display, r_initDisplay, r_display_count, u_display

signature:

	dynamic abstract domain Display
	
	// ports
	controlled dout: Display -> ChanInteger
	// asm only fields
	//Process
	controlled owner: Process -> Display
	
	static d: Sc_Thread
	static u_display: Display
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
	

	macro rule r_initDisplay($m in Display, $c1 in ChanInteger) =
		//extend Sc_Thread with $p do 
		seq
			// init ports and fields
			dout($m) := $c1
			owner(d) := $m
			// set up process
			// in asm there are only threads
			r_thread[d, <<r_display_count>>]
			r_sensitive[d, defaultEvent($c1)]
			r_dontInitialize[d]
		endseq
		
module orgate

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	export OrGate, r_initOrGate, r_runOrGate
	
signature:

	dynamic abstract domain OrGate
	
	// input ports
	controlled input1: OrGate -> ChanBoolean
	controlled input2: OrGate -> ChanBoolean
	// output port
	controlled output: OrGate -> ChanBoolean
	
	controlled owner: Process -> OrGate
	
definitions:

	macro rule r_runOrGate =
	let ($p = current_exec,
		$g = owner($p)) in
		seq
			let ($i1 = read(input1($g)),
				 $i2 = read(input2($g))) in
				r_write[output($g), $i1 or $i2]
			endlet
			r_waitStatic[$p]
		endseq
	endlet
		
	macro rule r_initOrGate(
	$g in OrGate, $c1 in ChanBoolean, $c2 in ChanBoolean, $c3 in ChanBoolean) =
		extend Process with $p do seq
			input1($g) := $c1
			input2($g) := $c2
			output($g) := $c3
			owner($p) := $g
			r_thread[$p, <<r_runOrGate>>]
			r_sensitive[$p, defaultEvent($c1)]
			r_sensitive[$p, defaultEvent($c2)]
			r_dontInitialize[$p]
		endseq
		

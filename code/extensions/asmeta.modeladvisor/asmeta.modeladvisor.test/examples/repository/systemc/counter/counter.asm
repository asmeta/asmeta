module counter

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	import ../sched/chanint
	import ../sched/clock
	export Counter, r_initCounter, r_runCounter

signature:

	dynamic abstract domain Counter
	
	// ports
	controlled load: Counter -> ChanBoolean
	controlled clock: Counter -> Clock
	controlled din: Counter -> ChanInteger
	controlled dout: Counter -> ChanInteger
	
	controlled count: Counter -> Integer
	controlled owner: Process -> Counter

definitions:
		
	macro rule r_runCounter =
	let ($p = current_exec,
		$c = owner($p)) in
		seq
			if read(load($c)) then
				count($c) := readInt(din($c))
			else
				count($c) := count($c) + 1
			endif
			r_writeInt[dout($c), count($c)]
			r_waitStatic[$p]
		endseq
	endlet
		
	macro rule r_initCounter(
	$c in Counter, 
	$load in ChanBoolean, 
	$clock in Clock, 
	$din in ChanInteger, 
	$dout in ChanInteger) =
		extend Process with $p do seq
			load($c) := $load
			clock($c) := $clock
			din($c) := $din
			dout($c) := $dout
			count($c) := 0
			owner($p) := $c
			r_thread[$p, <<r_runCounter>>]
			r_sensitive[$p, posEdgeEvent($clock)]
			r_dontInitialize[$p]
		endseq
		
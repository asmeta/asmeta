module driver

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	import ../sched/chanint
	import ../sched/clock
	export Driver, r_initDriver, r_runDriver

signature:

	dynamic abstract domain Driver
	
	// ports
	controlled load: Driver -> ChanBoolean
	controlled clock: Driver -> Clock
	controlled din: Driver -> ChanInteger
	controlled dout: Driver -> ChanInteger
	
	controlled maxCount: Driver -> Integer
	controlled step: Driver -> Integer
	controlled owner: Process -> Driver

definitions:
		
	macro rule r_runDriver =
	let ($p = current_exec,
		$d = owner($p)) in
		if step($d) = 0 then seq
			r_write[load($d), true]
			r_writeInt[din($d), 0]
			step($d) := 1
			r_waitStatic[$p]
		endseq else seq
			if readInt(dout($d)) = maxCount($d) - 1 then seq
				r_write[load($d), true]
				r_writeInt[din($d), 0]
			endseq else
				r_write[load($d), false]
			endif
			r_waitStatic[$p]
		endseq endif
	endlet

	macro rule r_initDriver(
	$d in Driver,
	$maxCount in Integer,
	$load in ChanBoolean,
	$clock in Clock,
	$din in ChanInteger,
	$dout in ChanInteger) =
		extend Process with $p do seq
			maxCount($d) := $maxCount
			load($d) := $load
			clock($d) := $clock
			din($d) := $din
			dout($d) := $dout
			step($d) := 0
			owner($p) := $d
			r_thread[$p, <<r_runDriver>>]
			r_sensitive[$p, posEdgeEvent($clock)]
			r_dontInitialize[$p]
		endseq
		

/*
 * A primitive boolean channel
 */
module chanbool

	import ../../STDL/StandardLibrary
	import common
	export ChanBoolean, read, r_write, r_init, posEdgeEvent, negEdgeEvent

signature:
	/*
	 * Boolean channel domain
	 */
	dynamic domain ChanBoolean subsetof PrimChannel
	
	/*
	 * Given a boolean channel, returns the current value
	 */
	controlled value: ChanBoolean -> Boolean
	/*
	 * Given a boolean channel, returns the next delta cycle value
	 */
	controlled newValue: ChanBoolean -> Boolean
	/*
	 * Given a boolean channel, returns the event associated with the transition
	 * from a false value to a true value
	 */
	controlled posEdgeEvent: ChanBoolean -> Event
	/*
	 * Given a boolean channel, returns the event associated with the transition
	 * from a true value to a false value
	 */
	controlled negEdgeEvent: ChanBoolean -> Event
	/*
	 * Given a primitive channel, returns a boolean channel (used by the
	 * update rule to retrive the channel instance to update)
	 */
	controlled whoAmI: PrimChannel -> ChanBoolean
	/*
	 * Given a boolean channel, returns the current value
	 */
	derived read: ChanBoolean -> Boolean
                          
definitions:
	/*
	 * Given a boolean channel, returns the current value
	 */
	function read($c in ChanBoolean) = value($c)
	/*
	 * Writes the given boolean value in the given boolean channel
	 */
	macro rule r_write($c in ChanBoolean, $exp in Boolean) =
		if $exp != value($c) then seq
			newValue($c) := $exp
			r_requestUpdate[$c]
		endseq endif
	/*
	 * Updates the given primitive channel 
	 */
	macro rule r_update($pc in PrimChannel) =
		let ($c = whoAmI($pc)) in
			if newValue($c) != value($c) then seq
				value($c) := newValue($c)
				r_notifyNow[defaultEvent($c)]
				if newValue($c) then
					r_notifyNow[posEdgeEvent($c)]
				else
					r_notifyNow[negEdgeEvent($c)]
				endif
			endseq endif
		endlet
	/*
	 * Initializes the given boolean channel
	 */
	macro rule r_init($c in ChanBoolean) =
		seq
			whoAmI($c) := $c
			r_initPrimChannel[$c, <<r_update(PrimChannel)>>]
			extend Event with $e1, $e2 do seq
				r_initEvent[$e1]
				r_initEvent[$e2]
				posEdgeEvent($c) := $e1
				negEdgeEvent($c) := $e2
			endseq
			// initial values
			value($c) := false
			newValue($c) := false
		endseq
		

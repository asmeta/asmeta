/*
 * A primitive integer channel
 */
module chanint

	import ../../STDL/StandardLibrary
	import common
	export ChanInteger, readInt, r_writeInt, r_updateInt, r_initInt

signature:
	/*
	 * Integer channel domain
	 */
	dynamic domain ChanInteger subsetof PrimChannel
	
	/*
	 * Given an integer channel, returns the current value
	 */
	controlled value: ChanInteger -> Integer
	/*
	 * Given an integer channel, returns the next delta cycle value
	 */
	controlled newValue: ChanInteger -> Integer
	/*
	 * Given a primitive channel, returns an integer channel (used by the
	 * update rule to retrive the channel instance to update)
	 */
	controlled whoAmI: PrimChannel -> ChanInteger
	/*
	 * Given an integer channel, returns the current value
	 */
	derived readInt: ChanInteger -> Integer

definitions:
	/*
	 * Given an integer channel, returns the current value
	 */
	function readInt($c in ChanInteger) = value($c)
	/*
	 * Writes the given integer value in the given integer channel
	 */
	macro rule r_writeInt($c in ChanInteger, $exp in Integer) =
		if $exp != value($c) then seq
			newValue($c) := $exp
			r_requestUpdate[$c]
		endseq endif
	/*
	 * Updates the given primitive channel 
	 */
	macro rule r_updateInt($pc in PrimChannel) =
		let ($c = whoAmI($pc)) in
			if newValue($c) != value($c) then seq
				value($c) := newValue($c)
				r_notifyNow[defaultEvent($c)]
			endseq endif
		endlet
	/*
	 * Initializes the given integer channel
	 */
	macro rule r_initInt($c in ChanInteger) =
		seq
			whoAmI($c) := $c
			r_initPrimChannel[$c, <<r_updateInt(PrimChannel)>>]
			// initial values
			value($c) := 0
			newValue($c) := 0
		endseq
		

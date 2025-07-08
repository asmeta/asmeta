//
// a simple example where a sequence of inputs must be given to reach a state
//
// the right combination is a sequence of 7
//
asm SafeCombination

import StandardLibrary


signature:
	// DOMAINS
	domain Digit subsetof Integer
	domain Locks subsetof Integer
	monitored digitPad: Digit
	controlled currentLock: Locks
	controlled openSafe: Boolean
	
	

definitions:
	// DOMAIN DEFINITIONS
	// it can be inserted by the user
	domain Digit = {0 : 9}
	// the plates locks - there are 5
	domain Locks = {1 : 5}

	// MAIN RULE
	main rule r_Main =
		par 
			// if the digit is right, go to the next lock
			if digitPad = 7 and currentLock < 5 then  currentLock := currentLock +1 endif
			// if the digit is right, and it is the last lock, open the safe
			if digitPad = 7 and currentLock = 5 then openSafe := true endif
			// go back to the first lock
			if digitPad != 7 then currentLock := 1 endif			
		endpar


// INITIAL STATE
default init s0:
	// intially closed
	function openSafe = false
	// try to open the first lock
	function currentLock = 1
	

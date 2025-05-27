/* A simple game in which the player has to guess a hidden number.
 * The player has 10 guesses at his disposal and on each attempt the game indicates
 * whether the number entered is higher or lower than the hidden number.
 */

asm GuessBigNumber

import STDL/StandardLibrary

signature:
	// DOMAINS
	domain Number subsetof Integer
	domain Attempt subsetof Integer
	monitored guess: Number
	controlled attempt: Attempt
	controlled win: Boolean
	controlled smaller: Boolean

definitions:
	// DOMAIN DEFINITIONS
	domain Number = {0 : 2100}
	domain Attempt = {0 : 10}

	// MAIN RULE
	main rule r_Main =
		if attempt > 0 then
			if guess = 752 then
				par
					attempt := 0
					win := true
				endpar
			else
				par
					if guess > 752 then
						smaller := true
					else
						smaller := false
					endif
					attempt := attempt - 1
				endpar
			endif
		endif

// INITIAL STATE
default init s0:
	function guess = 0
	function attempt = 10
	function win = false

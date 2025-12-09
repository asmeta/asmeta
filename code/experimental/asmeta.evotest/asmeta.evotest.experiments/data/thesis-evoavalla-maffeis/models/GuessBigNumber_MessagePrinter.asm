/* For printing messages, imported by GuessBigNumber.asm
 */

module GuessBigNumber_MessagePrinter

import STDL/StandardLibrary
export *

signature:
	controlled outMessage: String

definitions:
	rule r_printGuess($b in Boolean) = 
		if $b then
			outMessage := "hidden is smaller"
		else
			outMessage := "hidden is bigger"
		endif
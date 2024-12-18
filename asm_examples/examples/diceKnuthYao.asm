asm diceKnuthYao

import ../STDL/StandardLibrary
import ../STDL/CTLLibrary

signature:
	domain DiceValue subsetof Integer
	dynamic controlled dice: DiceValue
	dynamic controlled phase: DiceValue
	dynamic monitored coin: Boolean

definitions:
	domain DiceValue = {0 : 6}

	main rule r_Main =
		if dice = 0 then
			if phase <= 2 then
				if coin then
					phase := phase*2 + 2
				else
					phase := phase*2 + 1
				endif
			else
				switch phase
					case 3:
						if coin then
							dice := 1
						else
							phase := 1
						endif
					case 4:
						if coin then
							dice := 3
						else
							dice := 4
						endif
					case 5:
						if coin then
							dice := 5
						else
							dice := 4
						endif
					case 6:
						if coin then
							dice := 6
						else
							phase := 2
						endif
				endswitch
			endif
		endif

default init s0:
	function phase = 0
	function dice = 0

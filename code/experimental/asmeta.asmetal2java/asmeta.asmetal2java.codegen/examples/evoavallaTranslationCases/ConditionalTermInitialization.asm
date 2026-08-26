asm ConditionalTermInitialization

import ../STDL/StandardLibrary

signature:
	enum domain Switch = {ON | OFF | STANDBY}
	controlled selector: Switch
	controlled value: Switch -> Integer
	controlled label: Switch -> String
	controlled enabled: Switch -> Boolean
	controlled selectedValue: Integer
	controlled selectedLabel: String

definitions:
	main rule r_Main =
		par
			selector :=
				if selector = ON then
					OFF
				else
					ON
				endif
			selectedValue :=
				if enabled(selector) then
					value(selector)
				else
					-1
				endif
			selectedLabel :=
				if selector = STANDBY then
					label(STANDBY)
				else
					if selector = ON then
						label(ON)
					else
						label(OFF)
					endif
				endif
		endpar

default init s0:
	function selector = STANDBY
	function selectedValue = 0
	function selectedLabel = "none"
	function value($switch in Switch) =
		if $switch = ON then
			1
		else
			if $switch = OFF then
				0
			else
				-1
			endif
		endif
	function label($switch in Switch) =
		if $switch = ON then
			"on"
		else
			if $switch = OFF then
				"off"
			else
				"standby"
			endif
		endif
	function enabled($switch in Switch) =
		if $switch = STANDBY then
			false
		else
			true
		endif

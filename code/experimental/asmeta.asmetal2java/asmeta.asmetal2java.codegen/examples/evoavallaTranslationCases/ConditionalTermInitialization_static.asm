asm ConditionalTermInitialization_static

import ../STDL/StandardLibrary

signature:
	enum domain Switch = {ON | OFF | STANDBY}
	controlled selector: Switch
	static value: Switch -> Integer
	static label: Switch -> String
	static enabled: Switch -> Boolean
	controlled selectedValue: Integer
	controlled selectedLabel: String

definitions:
		function value($sw in Switch) =
		if $sw = ON then
			1
		else
			if $sw = OFF then
				0
			else
				-1
			endif
		endif
	function label($sw in Switch) =
		if $sw = ON then
			"on"
		else
			if $sw = OFF then
				"off"
			else
				"standby"
			endif
		endif
	function enabled($sw in Switch) =
		if $sw = STANDBY then
			false
		else
			true
		endif


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

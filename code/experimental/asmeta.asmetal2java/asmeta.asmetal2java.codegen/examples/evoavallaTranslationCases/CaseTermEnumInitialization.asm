asm CaseTermEnumInitialization

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
				switch(selector)
					case ON: OFF
					case OFF: STANDBY
					otherwise ON
				endswitch
			selectedValue :=
				switch(selector)
					case ON: value(ON)
					case OFF: value(OFF)
					otherwise value(STANDBY)
				endswitch
			selectedLabel :=
				switch(selector)
					case ON: label(ON)
					case OFF: label(OFF)
					otherwise label(STANDBY)
				endswitch
		endpar

default init s0:
	function selector = STANDBY
	function selectedValue = 0
	function selectedLabel = "none"
	function value($switch in Switch) =
		switch($switch)
			case ON: 1
			case OFF: 0
			otherwise -1
		endswitch
	function label($switch in Switch) =
		switch($switch)
			case ON: "on"
			case OFF: "off"
			otherwise "standby"
		endswitch
	function enabled($switch in Switch) =
		switch($switch)
			case ON: true
			case OFF: true
			otherwise false
		endswitch

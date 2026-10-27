asm CaseTermEnumInitialization_static

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
		switch($sw)
			case ON: 1
			case OFF: 0
			otherwise -1
		endswitch
	function label($sw in Switch) =
		switch($sw)
			case ON: "on"
			case OFF: "off"
			otherwise "standby"
		endswitch
	function enabled($sw in Switch) =
		switch($sw)
			case ON: true
			case OFF: true
			otherwise false
		endswitch

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

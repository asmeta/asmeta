asm derivedCtlCheck
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain MyDomain subsetof Integer
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo : MyDomain
	dynamic monitored mon1: Boolean
	dynamic monitored mon2: Boolean
	derived der_func: Boolean
	derived der_Switch: Boolean
	derived der_doubleif: Boolean
	derived der_ifswitch: Boolean
	derived der_switchif: Boolean
	derived der_doubleswitch: Boolean
	derived der_der: Boolean
	derived der_NotEx: MyDomain
	derived der_NotExEnumDom: Boolean -> EnumDom
	
	
	static statFunc: MyDomain

definitions:
	domain MyDomain = {1..4}

	function statFunc = 3
	
	function der_der = der_func

	function der_func =
			if(mon1) then
				true
			else
				false
			endif
			
	function der_doubleif =
			if(mon1) then
				if(mon2) then
					false
				else
					true
				endif
			else
				if(mon2) then
					true
				else
					false
				endif
			endif
			
	function der_ifswitch =
			if(mon1) then
				switch(mon2)
					case true:
						false
					case false:
						true
				endswitch
			else
				switch(mon2)
					case true:
						true
					case false:
						false
				endswitch
			endif
	
	function der_switchif =
			switch(mon1)
			case true:
				if(mon2) then
					false
				else
					true
				endif
			case false:
				if(mon2) then
					true
				else
					false
				endif
			endswitch
	
	function der_doubleswitch =
			switch(mon1)
			case true:
				switch(mon2)
					case true:
						false
					case false:
						true
				endswitch
			case false:
				switch(mon2)
					case true:
						true
					case false:
						false
				endswitch
			endswitch
	
	function der_Switch =
		switch(mon1)
			case true:
				false
			case false:
				true
		endswitch
	
	function der_NotEx =
		if(mon1) then
			1
		endif

	function der_NotExEnumDom($b in Boolean) =
		if($b) then
			if(mon1) then
				AA
			endif
		else
			if(mon1) then
				BB
			else
				CC
			endif
		endif
		
	
	CTLSPEC ag(der_func = mon1)
	CTLSPEC ag(statFunc = 3)
	CTLSPEC ag(der_Switch = not(mon1))
	CTLSPEC ag(der_doubleif = mon1 xor mon2)
	CTLSPEC ag(der_ifswitch = mon1 xor mon2)
	CTLSPEC ag(der_switchif = mon1 xor mon2)
	CTLSPEC ag(der_doubleswitch = mon1 xor mon2)
	CTLSPEC ag(der_der = der_func)
	CTLSPEC ag(der_NotEx = 1 iff mon1)
	CTLSPEC ag(der_NotEx = -2147483647 iff not(mon1))
	CTLSPEC ag(der_NotExEnumDom(true) = AA iff mon1)
	CTLSPEC ag(isUndef(der_NotExEnumDom(true)) iff not(mon1))
	CTLSPEC ag(der_NotExEnumDom(false) = BB iff mon1)
	CTLSPEC ag(der_NotExEnumDom(false) = CC iff not(mon1))

	main rule r_Main =
		if(der_func) then
			foo := 1
		endif
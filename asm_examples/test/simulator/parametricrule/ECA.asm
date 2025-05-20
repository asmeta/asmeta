asm ECA

import ../../../STDL/StandardLibrary

signature:
enum domain WindowStatus = {OPEN | CLOSED}

//Sensors
dynamic monitored badAir: Boolean
dynamic monitored sgnOpenWindow: Boolean
dynamic monitored sgnCloseWindow: Boolean

//Obbligations
dynamic out closeWindow: Boolean
dynamic out openWindow: Boolean

//Knowledge
dynamic controlled windowStatus: WindowStatus

definitions:

macro rule r_ECA($event in Boolean, $cond in Boolean, $rule in Rule, $otherwiseR in Rule) =
	if $event then
		if $cond then $rule else $otherwiseR endif
	endif

macro rule r_skip = skip

rule r_closeWindow =
		par
			windowStatus := CLOSED
			closeWindow := true
			openWindow := false
		endpar
	
rule r_openWindow =
		par
			windowStatus := OPEN
			openWindow := true
			closeWindow := false
		endpar
	
invariant inv_neverBoth over sgnOpenWindow, sgnCloseWindow: not(sgnOpenWindow and sgnCloseWindow)

main rule r_Main =  
	par
		r_ECA[sgnOpenWindow, true, <<r_openWindow>>, <<r_skip>>]
		r_ECA[sgnCloseWindow, not badAir, <<r_closeWindow>>, <<r_openWindow>>]
	endpar

default init s0:
function windowStatus = CLOSED
function openWindow = false
function closeWindow = false
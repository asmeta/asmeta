/* example of ASM specification */

asm LoginController

import ../../../../../asm_examples/STDL/StandardLibrary

//declare universes and functions

signature:

// domains

	enum domain States = {
		IDLE | TRYING_TO_LOGIN | ERROR | CANCELLED | LOGINERROR | LOGINTIMEOUT | LOGGEDIN | ACTIVE
	}
    
	enum domain Events = {
		INTENT_LOGIN | CANCEL_LOGIN | LOGIN_TIMEOUT | LOGIN_COMPLETE | INTENT_CONTINUE | INTENT_LOGOUT
	}

// functions

    controlled current_state: States
	
	dynamic monitored event : Events

	controlled network : Boolean
	controlled gps : Boolean
	controlled cpumemory : Boolean
	controlled audio : Boolean
	controlled camera : Boolean
	controlled display : Boolean
	controlled displaytouch : Boolean
	controlled keyboard : Boolean
	controlled keyboardbacklight : Boolean
	
	

// static functions

//	static odd : Natural -> Boolean

// definitions

definitions:

// 

//	function odd ($x in Natural) = true

//Rules

	
		rule r_IDLE =
		if current_state = IDLE then
		
		    if event = INTENT_LOGIN then
		    par
		    	current_state := TRYING_TO_LOGIN
				network := true
				gps := true
				cpumemory := true
				display := true
			
		    endpar
		    endif
		
		endif
	
		rule r_TRYING_TO_LOGIN =
		if current_state = TRYING_TO_LOGIN then
		
		switch event
		    case CANCEL_LOGIN :
		    par
		    	current_state := IDLE
				network := false
				gps := false
				cpumemory := false
				display := false
			
		    endpar
		
		    case LOGIN_TIMEOUT :
		    par
		    	current_state := LOGINTIMEOUT
				network := false
				gps := false
				cpumemory := false
				display := false
			
		    endpar
		
		    case LOGIN_COMPLETE :
		    par
		    	current_state := LOGGEDIN
				network := false
				gps := false
				cpumemory := false
				display := false
			
		    endpar
		endswitch
		endif
	
		rule r_ERROR =
		if current_state = ERROR then
		
		    if event = INTENT_CONTINUE then
		    	current_state := IDLE
		    endif
		
		endif
	
		rule r_CANCELLED =
		if current_state = CANCELLED then
			skip
		endif
	
		rule r_LOGINERROR =
		if current_state = LOGINERROR then
			skip		
		endif
	
		rule r_LOGINTIMEOUT =
		if current_state = LOGINTIMEOUT then
			skip
		endif
	
		rule r_LOGGEDIN =
		if current_state = LOGGEDIN then
		
		    if event = INTENT_CONTINUE then
		    	current_state := ACTIVE
		    endif
		
		endif
	
		rule r_ACTIVE =
		if current_state = ACTIVE then
		
		    if event = INTENT_LOGOUT then
		    	current_state := IDLE
		    endif
		
		endif
	
	    

//Main Rule

	main rule r_Spec = 
		par 
			r_IDLE[]
			r_TRYING_TO_LOGIN[]
			r_ERROR[]
			r_CANCELLED[]
			r_LOGINERROR[]
			r_LOGINTIMEOUT[]
			r_LOGGEDIN[]
			r_ACTIVE[]
			
		endpar

//define the initial states

default init initial_state:

	function current_state = IDLE
	function network = false
	function gps = false
	function cpumemory = false
	function audio = false
	function camera = false
	function display = false
	function displaytouch = false
	function keyboard = false
	function keyboardbacklight = false

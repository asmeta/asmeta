asm wiper_abs2

import  ../../STDL/StandardLibrary

signature:

   enum domain Chart1 = {CA2_PUMPING | CA3_SPEED1 | CA4_PUMPING_2 | CA5_PUMPING_1 | CA6_WAIT_IDLE | CA7_IDLE | CA8_SPEED2 | CA9_WAIT_WIPE | CA10_WAIT_WIPES2 | CA11_WAIT_WIPES3}
   enum domain Chart2 = {CA13_ST1 | CA14_ST2 | CA15_ST0}
   enum domain Wipes  = {WIPES0 | WIPES1 | WIPES2 | WIPES3}
   enum domain Temperature = {LT_0 | EQ_0 | LT_10 | EQ_10 | GT_10 }
   enum domain Heating = {OFF | ON1 | ON2}
	
   monitored in_speed1 : Boolean
   monitored in_speed2 : Boolean
   monitored in_water  : Boolean
   monitored endsw     : Boolean
   monitored in_temp   : Temperature
	
   controlled out_heating: Heating
   controlled out_pump   : Boolean
   controlled out_speed1 : Boolean
   controlled out_speed2 : Boolean


   controlled ca1_wipes    : Wipes
   controlled ca1_chart_ns : Chart1
   controlled ca12_chart_ns: Chart2
   controlled n_speed      : Boolean

definitions:


macro rule r_ca2_pumping =
  if ca1_chart_ns = CA2_PUMPING then
      if in_speed2 then
        par
          ca1_chart_ns := CA4_PUMPING_2
          out_speed1 := false
          out_speed2 := true
        endpar
      else
        par
          ca1_chart_ns := CA5_PUMPING_1
          out_speed1 := true
          out_speed2 := false
        endpar
      endif
  endif

macro rule r_ca3_speed1 =
  if not in_water and (not in_speed1 or in_speed2) then
      ca1_chart_ns := CA6_WAIT_IDLE
    else
      if in_water then
        par
          ca1_chart_ns := CA2_PUMPING
          out_pump := true
          ca1_wipes := WIPES3
        endpar
      endif
  endif

macro rule r_ca4_pumping_2 =
  if ca1_chart_ns = CA4_PUMPING_2 then
    if in_water and in_speed2 then
      par
        ca1_chart_ns := CA2_PUMPING
        out_pump := true
        ca1_wipes := WIPES3
      endpar
    else
      if not in_water then
        par
          ca1_chart_ns := CA9_WAIT_WIPE
          ca1_wipes := switch(ca1_wipes)
            case WIPES3:	 WIPES2
            case WIPES2:	 WIPES1
            case WIPES1:	 WIPES0
          endswitch
          out_pump := false
        endpar
      else
        if not in_speed2 then
          par
            ca1_chart_ns := CA5_PUMPING_1
            out_speed1 := true
            out_speed2 := false
          endpar
        endif
      endif
    endif    
  endif

macro rule r_ca5_pumping_1 =
  if ca1_chart_ns = CA5_PUMPING_1 then
    if in_water and not in_speed2 then
      par
        ca1_chart_ns := CA2_PUMPING
        out_pump := true
        ca1_wipes := WIPES3
      endpar
    else
      if in_water and in_speed2 then
        par
          ca1_chart_ns := CA4_PUMPING_2
          out_speed1 := false
          out_speed2 := true
        endpar
      else
        if not in_water then
          par
            ca1_chart_ns := CA9_WAIT_WIPE
            ca1_wipes := switch(ca1_wipes)
              case WIPES3:	 WIPES2
              case WIPES2:	 WIPES1
              case WIPES1:	 WIPES0
            endswitch
            out_pump := false
          endpar
        endif
      endif
    endif
  endif

macro rule r_ca6_wait_idle =
  if ca1_chart_ns = CA6_WAIT_IDLE then
    if in_water then
      par
        ca1_chart_ns := CA2_PUMPING
        out_pump := true
        ca1_wipes := WIPES3
      endpar
    else
      if endsw then
        par
          ca1_chart_ns := CA7_IDLE
          out_speed1 := false
          out_speed2 := false
          out_pump   := false
        endpar
      endif
    endif
  endif

macro rule r_ca7_idle =
  if ca1_chart_ns = CA7_IDLE then
    if in_speed1 and not in_speed2 and not in_water then
      par
        ca1_chart_ns := CA3_SPEED1
        out_speed1 := true
        out_speed2 := false
      endpar
    else
      if in_water then
        par
          ca1_chart_ns := CA2_PUMPING
          out_pump := true
          ca1_wipes := WIPES3
        endpar
      else
        if in_speed2 then
          par
            ca1_chart_ns := CA8_SPEED2
            out_speed2 := true
            out_speed1 := false
          endpar
        endif
      endif
    endif
  endif

macro rule r_ca8_speed2 =
  if ca1_chart_ns = CA8_SPEED2 then
    if not in_speed2 and not in_water then
      ca1_chart_ns := CA6_WAIT_IDLE
    else
      if in_water then
        par
          ca1_chart_ns := CA2_PUMPING
          out_pump := true
          ca1_wipes := WIPES3
        endpar
      endif
    endif
  endif  

macro rule r_ca9_wait_wipe =
  if ca1_chart_ns = CA9_WAIT_WIPE then
    if in_water then
	  par
	  	ca1_chart_ns := CA2_PUMPING
	  	out_pump := true
	  	ca1_wipes := WIPES3
	  endpar
    else
    	ca1_chart_ns := CA10_WAIT_WIPES2
    endif
  endif  

macro rule r_ca10_wait_wipes2 =
  if ca1_chart_ns = CA10_WAIT_WIPES2 then
    if ca1_wipes = WIPES0 and not in_water and not in_speed1 and not in_speed2 then
    	ca1_chart_ns := CA6_WAIT_IDLE
    else
    	if ca1_wipes = WIPES0 and not in_water and in_speed2 then
    	  par
    		ca1_chart_ns := CA8_SPEED2
    		out_speed2 := true
    		out_speed1 := false
    	  endpar
    	else
    		if ca1_wipes = WIPES0 and not in_water and in_speed1 and not in_speed2 then
    			par
    				ca1_chart_ns := CA3_SPEED1
    				out_speed1 := true
    				out_speed2 := false
    			endpar
    		else
    			if endsw and ca1_wipes != WIPES0 and not in_water then
    				ca1_chart_ns := CA11_WAIT_WIPES3
    			else
    				if in_water then
    					par
    						ca1_chart_ns := CA2_PUMPING
    						out_pump := true
    						ca1_wipes := WIPES3
    					endpar
    				endif
    			endif
    		endif
    	endif
    endif
  endif  

macro rule r_ca11_wait_wipes3 =
  if ca1_chart_ns = CA11_WAIT_WIPES3 then
    if in_speed2 and not in_water then
    	par
    		ca1_chart_ns := CA4_PUMPING_2
    		out_speed1 := false
    		out_speed2 := true
    	endpar
    else
    	if in_water then
    		par
    			ca1_chart_ns := CA2_PUMPING
    			out_pump := true
    			ca1_wipes := WIPES3
    		endpar
    	else
    		if not in_speed2 then
    			par
    				ca1_chart_ns := CA5_PUMPING_1
    				out_speed1 := true
    				out_speed2 := false
    			endpar
    		endif
    	endif
    endif
  endif  


macro rule r_ca13_st1 =
  if ca12_chart_ns = CA13_ST1 then
  	if in_temp = LT_0 and (in_speed1 or in_speed2) then // TODO
  		par
  			ca12_chart_ns := CA14_ST2
  			out_heating := ON2
  		endpar
  	else
  		if in_temp = GT_10 or not (in_speed1 or in_speed2) then // TODO
  			par
  				ca12_chart_ns := CA13_ST1
  				out_heating := OFF
  			endpar
  		endif
  	endif
  endif

macro rule r_ca14_st2 =
  if ca12_chart_ns = CA14_ST2 then
  	if in_temp = LT_10 or in_temp = EQ_10 or in_temp = GT_10 then
  		par
  			ca12_chart_ns := CA13_ST1
  			out_heating := ON1
  		endpar
  	endif
  endif

macro rule r_ca15_st0 =
  if ca12_chart_ns = CA15_ST0 then
  	if in_temp = LT_0 and (in_speed1 or in_speed2) then // TODO
  		par
  			ca12_chart_ns := CA14_ST2
  			out_heating := ON2
  		endpar
  	else
  		if (in_temp = LT_10 or in_temp = EQ_0 or in_temp = LT_0) and (in_speed1 or in_speed2) then // TODO
  			par
  				ca12_chart_ns := CA13_ST1
  				out_heating := ON1
  			endpar
  		endif
  	endif
  endif
  
	
// macro rule r_nspeed = wiper_on := (out_speed1 or out_speed2)

main rule r_Main = 
  	par
  	  r_ca2_pumping[]
	  r_ca3_speed1[]
	  r_ca4_pumping_2[]
	  r_ca5_pumping_1[]
	  r_ca6_wait_idle[]
	  r_ca7_idle[]
	  r_ca8_speed2[]
	  r_ca9_wait_wipe[]
	  r_ca10_wait_wipes2[]
	  r_ca11_wait_wipes3[]
	  r_ca13_st1[]
	  r_ca14_st2[]
	  r_ca15_st0[]
	endpar

	
default init s1:
  function out_speed1    = false
  function out_speed2    = false
  function out_pump      = false
  function ca1_chart_ns  = CA7_IDLE
  function out_heating   = OFF
  function ca12_chart_ns = CA15_ST0
  function ca1_wipes     = WIPES0








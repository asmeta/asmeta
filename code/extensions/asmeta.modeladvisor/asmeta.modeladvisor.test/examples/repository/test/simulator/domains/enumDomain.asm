asm enumDomain

import  ../../../STDL/StandardLibrary
		
signature:

enum domain Pressure_type = {TOO_LOW | NORMAL |HIGH }

controlled pressure :  Pressure_type

monitored pressure_in : Pressure_type
		
definitions:	
      	
main rule r_test_enum = 
	
	    if  pressure_in = TOO_LOW then  pressure := NORMAL endif
   

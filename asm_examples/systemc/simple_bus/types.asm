module types

	export BusStatus

signature:
	
	enum domain BusStatus = {SIMPLE_BUS_OK | SIMPLE_BUS_REQUEST |
		SIMPLE_BUS_WAIT | SIMPLE_BUS_ERROR}
		
definitions:


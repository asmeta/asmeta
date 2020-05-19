asm turboReturnRuleWithExtend

import ../../STDL/StandardLibrary

signature:
	dynamic abstract domain Message
	dynamic controlled newMessage: Message
	
definitions:

	turbo rule r_createNewMessage in Message =
		extend Message with $m do
			result := $m
			
	main rule r_Main =
		newMessage <- r_createNewMessage()

default init s0:

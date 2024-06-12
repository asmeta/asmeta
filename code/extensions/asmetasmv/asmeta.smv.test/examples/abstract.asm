asm abstract

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	abstract domain Contact
	abstract domain Noelements
	dynamic controlled foo : Contact -> Boolean
	static contact1 : Contact
	static contact2 : Contact
	static contact3 : Contact
	
definitions:
	
	CTLSPEC ag(foo(contact1))
	CTLSPEC ax(ag(not(foo(contact2)) and not(foo(contact3))))
	
	main rule r_main =
		forall $c in Contact with $c!=contact1 do
			foo($c) := false

default init s0:
	function foo($c in Contact) = true

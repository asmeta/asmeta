// test for lazy evaluation with a derived
// thederive dis defined by a monitored, it shouldbe easked
asm monitoredLazyDer

import ../../STDL/StandardLibrary

signature:
	monitored f1: Boolean
	monitored f2: Boolean
	controlled g1: Boolean
	derived d1: Boolean

definitions:

	function d1 = f1

// f1 --> TRUE
main rule r_main =	g1 := d1

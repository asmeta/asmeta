//
// this is correct
//
asm staticVSDerived

import ../../STDL/StandardLibrary

signature:

	monitored m: Integer
	static fs: Integer -> Integer
	derived fd: Integer -> Integer
	derived fd2: Integer -> Integer

definitions:
// simple case no indirection
	function fd($i in Integer) = m + $i
	function fs($i in Integer) = 2 * $i
	
// indirection	- still derived since it is defined with a derived
	function fd2($i in Integer) = fd($i)
	
	
default init s0:

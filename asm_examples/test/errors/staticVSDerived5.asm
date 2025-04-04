//
// this is not correct
//
asm staticVSDerived5

import ../../STDL/StandardLibrary

signature:

	monitored m: Integer
	static fs: Integer -> Integer
	derived fd: Integer -> Integer

definitions:
// simple case no indirection
	function fd($i in Integer) = m + $i
// indirection
	function fs($i in Integer) = fd($i)
	
	
	
default init s0:

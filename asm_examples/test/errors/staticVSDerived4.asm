//
// this is not correct
//
asm staticVSDerived4

import ../../STDL/StandardLibrary

signature:

	static fs: Integer -> Integer
	derived fd: Integer -> Integer

definitions:
// simple case no indirection
	function fs($i in Integer) = 2 * $i
	
// indirection	- it is wrong since it should b static
	function fd($i in Integer) = fs($i) + 2
	
	
default init s0:

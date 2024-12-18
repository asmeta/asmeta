asm staticVSDerived

import ../../STDL/StandardLibrary

signature:

	monitored m: Integer
	static fs: Integer -> Integer

definitions:
	function fs($i in Integer) = m + $i
	
default init s0:

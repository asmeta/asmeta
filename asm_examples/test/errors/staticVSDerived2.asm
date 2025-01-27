//
// error: a derived function fs is defined using only a static function - it must be static
//
asm staticVSDerived2

import ../../STDL/StandardLibrary

signature:

	derived fs: Integer -> Integer
		

definitions:
	function fs($i in Integer) = 2 *  $i
	
default init s0:

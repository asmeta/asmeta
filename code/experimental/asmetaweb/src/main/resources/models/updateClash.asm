/** example of Inconsistent Update. When count become 2, inconsistentUpdate occur **/

asm updateClash

import ../libraries/StandardLibrary

signature:

	abstract domain Orders
	enum domain Status = {PENDING | INVOICED | CANCELLED}
	domain Counter subsetof Integer

	static o1: Orders
	static o2: Orders
	static o3: Orders

	controlled orderStatus: Orders -> Status
	controlled count: Counter

definitions:
	domain Counter = {0 : 3}
	
	main rule r_main =
	
		if count < 2 then
			count := count + 1
		else 
			par
				choose $o in Orders with true do
					orderStatus($o) := INVOICED
				choose $oo in Orders with true do
					orderStatus($oo) := CANCELLED
			endpar
		endif
		

default init s0:

	function orderStatus($o in Orders) = PENDING
	function count = 0

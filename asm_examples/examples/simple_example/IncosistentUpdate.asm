asm IncosistentUpdate

import ../../STDL/StandardLibrary

signature:

	abstract domain Orders
	enum domain Status = {PENDING | INVOICED | CANCELLED}

	static o1: Orders
	static o2: Orders
	static o3: Orders

	controlled orderStatus: Orders -> Status

definitions:

	main rule r_main =
		par
			choose $o in Orders with true do
				orderStatus($o) := INVOICED
			choose $oo in Orders with true do
				orderStatus($oo) := CANCELLED
		endpar

default init s0:

	function orderStatus($o in Orders) = PENDING

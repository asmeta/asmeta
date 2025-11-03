/*
 Given a set of orders, update the status randomly to PENDING, INVOINCED or CANCELLED.
 This is done twice in parallel by selecting different order at a time.
 */

asm IncosistentUpdate

import ../../STDL/StandardLibrary

signature:

	abstract domain Orders //domain for orders
	enum domain Status = {PENDING | INVOICED | CANCELLED} // orders status

	// three orders
	static o1: Orders
	static o2: Orders
	static o3: Orders

	controlled orderStatus: Orders -> Status //function for the order status

definitions:

	main rule r_main =
		par
			choose $o in Orders with true do //select one order randomly and assign the status to INVOICED
				orderStatus($o) := INVOICED
			choose $oo in Orders with true do
				orderStatus($oo) := CANCELLED //select one order randomly and assign the status to CANCELLED
		endpar

default init s0:

	function orderStatus($o in Orders) = PENDING //initialize all orders to pending

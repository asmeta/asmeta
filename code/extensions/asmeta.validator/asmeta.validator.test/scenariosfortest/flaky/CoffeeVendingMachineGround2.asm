// same as CoffeeVendingMachineGround but with two variables
// inthe choose rule
asm CoffeeVendingMachineGround2
import StandardLibrary

signature:
	enum domain Product = {COFFEE | TEA | MILK}
	controlled dispensed : Product

definitions:

	main rule r_Main =
			choose $p in Product, $p1 in Product with true do
					dispensed := $p

default init s0:
	function dispensed = undef

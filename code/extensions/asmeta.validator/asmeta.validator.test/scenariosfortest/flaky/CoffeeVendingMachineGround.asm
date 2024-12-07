asm CoffeeVendingMachineGround
import StandardLibrary

signature:
	enum domain Product = {COFFEE | TEA | MILK}
	controlled dispensed : Product

definitions:

	main rule r_Main =
			choose $p in Product with true do
					dispensed := $p

default init s0:
	function dispensed = undef

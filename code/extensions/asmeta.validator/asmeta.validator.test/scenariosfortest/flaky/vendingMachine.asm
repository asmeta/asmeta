// non deterministic vending machine
asm vendingMachine

import StandardLibrary

signature:
	enum domain Product = {TEA, COFFEE}
	controlled dispensed : Product
	
	
definitions:

	main rule r_Main =  choose $p in Product with true do dispensed := $p

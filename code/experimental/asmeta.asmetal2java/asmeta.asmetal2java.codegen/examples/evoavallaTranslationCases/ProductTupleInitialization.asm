asm ProductTupleInitialization

import ../STDL/StandardLibrary

signature:
	controlled coordinates: Prod(Integer, Integer)
	controlled mixedTuple: Prod(Integer, Boolean, String)
	controlled tenTuple: Prod(Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer)
	controlled productUnary: Prod(Integer, Integer) -> Integer
	controlled productToTuple: Prod(Integer, Boolean) -> Prod(String, Real)
	monitored productMon: Prod(Integer, Integer) -> Integer
	monitored mixedProductMon: Prod(Integer, Boolean, String) -> Real
	controlled conInteger: Integer
	controlled conReal: Real

definitions:
	main rule r_Main =
		par
			coordinates := (3, 4)
			mixedTuple := (2, false, "updated")
			tenTuple := (10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
			productUnary(1, 2) := productUnary(0, 1) + productMon(3, 4)
			productToTuple(7, true) := ("updated", mixedProductMon(5, false, "key"))
			conInteger := productMon(3, 4)
			conReal := mixedProductMon(5, false, "key")
		endpar

default init s0:
	function coordinates = (1, 2)
	function mixedTuple = (1, true, "initial")
	function tenTuple = (1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
	function conInteger = 0
	function conReal = 0.0

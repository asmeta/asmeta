asm test_Count

import ../../../STDL/StandardLibrary

signature:
	dynamic controlled list: Seq(Integer)
	dynamic controlled list1: Seq(Integer)
	dynamic controlled list2: Seq(Integer)
	dynamic controlled list3: Seq(Integer)
	dynamic controlled countA: Natural
	dynamic controlled countB: Natural
	dynamic controlled count1: Natural
	dynamic controlled count2: Natural
	dynamic controlled count3: Natural

definitions:

	main rule r_Main =
		par
			countA := count(list, 3) // 1
			countB := count(list, 7) // 0
			count1 := count(list1, 1) // 2
			count2 := count(list2, 1) // 3
			count3 := count(list3, 2) // 3
		endpar

default init s0:
	function list = [1, 2, 3, 4]
	function list1 = [1, 1, 3, 4]
	function list2 = [1, 2, 1, 1]
	function list3 = [1, 2, 3, 4, 2, 7, 2]
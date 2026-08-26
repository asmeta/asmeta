asm ExtendRule

import ../STDL/StandardLibrary

signature:
	dynamic abstract domain Item
	dynamic abstract domain Group
	dynamic controlled active: Item -> Boolean
	dynamic controlled owner: Item -> Group
	dynamic controlled itemCount: Natural
	dynamic controlled lastItem: Item

definitions:
	main rule r_Main =
		extend Group with $group do
			extend Item with $first, $second do
				par
					active($first) := true
					active($second) := false
					owner($first) := $group
					owner($second) := $group
					itemCount := itemCount + 2n
					lastItem := $second
				endpar

default init s0:
	function active($item in Item) = false
	function itemCount = 0n

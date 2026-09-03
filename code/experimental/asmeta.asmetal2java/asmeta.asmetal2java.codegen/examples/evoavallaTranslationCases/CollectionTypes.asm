asm CollectionTypes

import ../STDL/StandardLibrary

signature:
	controlled values: Seq(Integer)
	static withoutFirst: Seq(Integer) -> Seq(Integer)

definitions:
	function withoutFirst($sequence in Seq(Integer)) =
		tail($sequence)

	main rule r_Main =
		values := withoutFirst(values)

default init s0:
	function values = [1, 2, 3]

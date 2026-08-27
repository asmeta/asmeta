asm SequenceInitialization

import ../STDL/StandardLibrary

signature:
	controlled integerSequence: Seq(Integer)
	controlled booleanSequence: Seq(Boolean)
	controlled stringSequence: Seq(String)
	controlled matrix: Seq(Seq(Integer))
	controlled sequenceLengthResult: Integer
	controlled firstElementResult: Integer
	controlled containsResult: Boolean

definitions:
	main rule r_Main =
		par
			integerSequence := append(integerSequence, 4)
			booleanSequence := prepend(true, booleanSequence)
			stringSequence := append(tail(stringSequence), "gamma")
			matrix := append(matrix, [5, 6])
			sequenceLengthResult := length(integerSequence)
			firstElementResult := first(integerSequence)
			containsResult := contains(integerSequence, 2)
		endpar

default init s0:
	function integerSequence = [1, 2, 3]
	function booleanSequence = [false, true]
	function stringSequence = ["alpha", "beta"]
	function matrix = [[1, 2], [3, 4]]
	function sequenceLengthResult = 0
	function firstElementResult = 0
	function containsResult = false

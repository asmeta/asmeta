asm SequenceInitialization

import ../STDL/StandardLibrary

signature:
	controlled integerSequence: Seq(Integer)
	controlled booleanSequence: Seq(Boolean)
	controlled stringSequence: Seq(String)
	controlled emptySequence: Seq(Integer)
	controlled singletonSequence: Seq(Integer)
	controlled matrix: Seq(Seq(Integer))
	controlled cube: Seq(Seq(Seq(Integer)))
	controlled sequenceLengthResult: Integer
	controlled firstElementResult: Integer
	controlled containsResult: Boolean

definitions:
	main rule r_Main =
		par
			integerSequence := append(integerSequence, 4)
			booleanSequence := prepend(true, booleanSequence)
			stringSequence := append(tail(stringSequence), "gamma")
			emptySequence := []
			singletonSequence := [99]
			matrix := [[7, 8], [9, 10]]
			cube := append(cube, [[5, 6], [7, 8]])
			sequenceLengthResult := length(integerSequence)
			firstElementResult := first(integerSequence)
			containsResult := contains(integerSequence, 2)
		endpar

default init s0:
	function integerSequence = [1, 2, 3]
	function booleanSequence = [false, true]
	function stringSequence = ["alpha", "beta"]
	function emptySequence = []
	function singletonSequence = [42]
	function matrix = [[1, 2], [3, 4]]
	function cube = [[[1, 2]], [[3, 4]]]
	function sequenceLengthResult = 0
	function firstElementResult = 0
	function containsResult = false

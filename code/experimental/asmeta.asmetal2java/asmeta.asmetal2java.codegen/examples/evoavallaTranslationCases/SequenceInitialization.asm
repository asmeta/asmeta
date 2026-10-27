asm SequenceInitialization

import ../STDL/StandardLibrary

signature:
	domain IntegerSequence subsetof Seq(Integer)
	controlled integerSequence: Seq(Integer)
	controlled concreteSequence: IntegerSequence
	controlled booleanSequence: Seq(Boolean)
	controlled stringSequence: Seq(String)
	controlled emptySequence: Seq(Integer)
	controlled singletonSequence: Seq(Integer)
	controlled combinedSequence: Seq(Integer)
	controlled replacedSequence: Seq(Integer)
	controlled insertedSequence: Seq(Integer)
	controlled slicedSequence: Seq(Integer)
	controlled sequenceAsSet: Powerset(Integer)
	controlled setAsSequence: Seq(Integer)
	controlled sourceSet: Powerset(Integer)
	controlled matrix: Seq(Seq(Integer))
	controlled cube: Seq(Seq(Seq(Integer)))
	controlled sequenceLengthResult: Integer
	controlled firstElementResult: Integer
	controlled containsResult: Boolean
	controlled emptyResult: Boolean
	controlled lastResult: Integer
	controlled countResult: Natural

definitions:
	domain IntegerSequence = {[], [10, 11]}

	main rule r_Main =
		par
			integerSequence := append(integerSequence, 4)
			booleanSequence := prepend(true, booleanSequence)
			stringSequence := append(tail(stringSequence), "gamma")
			emptySequence := []
			singletonSequence := [99]
			combinedSequence := union(integerSequence, [3, 3])
			replacedSequence := replaceAt(integerSequence, 1n, 8)
			insertedSequence := insertAt(integerSequence, 1n, 8)
			slicedSequence := subSequence(integerSequence, 1n, 3n)
			sequenceAsSet := asSet(integerSequence)
			setAsSequence := asSequence(sourceSet)
			matrix := [[7, 8], [9, 10]]
			cube := append(cube, [[5, 6], [7, 8]])
			sequenceLengthResult := length(integerSequence)
			firstElementResult := first(integerSequence)
			containsResult := contains(integerSequence, 2)
			emptyResult := isEmpty(emptySequence)
			lastResult := last(integerSequence)
			countResult := count(integerSequence, 2)
			concreteSequence := [10, 11]
		endpar

default init s0:
	function integerSequence = [1, 2, 3]
	function concreteSequence = [10, 11]
	function booleanSequence = [false, true]
	function stringSequence = ["alpha", "beta"]
	function emptySequence = []
	function singletonSequence = [42]
	function combinedSequence = [1, 1]
	function replacedSequence = []
	function insertedSequence = []
	function slicedSequence = []
	function sequenceAsSet = {}
	function setAsSequence = []
	function sourceSet = {4, 5}
	function matrix = [[1, 2], [3, 4]]
	function cube = [[[1, 2]], [[3, 4]]]
	function sequenceLengthResult = 0
	function firstElementResult = 0
	function containsResult = false
	function emptyResult = false
	function lastResult = 0
	function countResult = 0n

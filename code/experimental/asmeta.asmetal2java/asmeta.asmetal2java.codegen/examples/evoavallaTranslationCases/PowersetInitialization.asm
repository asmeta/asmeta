asm PowersetInitialization

import ../STDL/StandardLibrary

signature:
	enum domain Color = {RED | GREEN | BLUE}
	domain IntegerSet subsetof Powerset(Integer)
	controlled values: Powerset(Integer)
	controlled concreteSet: IntegerSet
	controlled flags: Powerset(Boolean)
	controlled colors: Powerset(Color)
	controlled combinedValues: Powerset(Integer)
	controlled commonValues: Powerset(Integer)
	controlled remainingValues: Powerset(Integer)
	controlled distinctValues: Powerset(Integer)
	controlled containsTwo: Boolean

definitions:
	domain IntegerSet = {{}, {10,11}}

	main rule r_Main =
		par
			values := including(excluding(values, 1), 4)
			flags := including(flags, true)
			colors := excluding(colors, RED)
			combinedValues := union(values, {7, 8})
			commonValues := intersection(values, {2, 3, 7})
			remainingValues := difference(values, {2, 7})
			distinctValues := symmetricDifference(values, {3, 4})
			containsTwo := contains(values, 2)
			concreteSet := {10,11}
		endpar

default init s0:
	function values = {1, 2, 3}
	function concreteSet = {10, 11}
	function flags = {false}
	function colors = {RED, GREEN}
	function combinedValues = {}
	function commonValues = {}
	function remainingValues = {}
	function distinctValues = {}
	function containsTwo = false

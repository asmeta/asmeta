asm STDL_funcs
	
import ../../../STDL/StandardLibrary
	
signature:
	controlled seq1: Seq(Integer)
	controlled seq2: Seq(Integer)
	controlled seq3: Seq(Integer)
	controlled seq4: Seq(Integer)
	controlled seq5: Seq(Integer)
	controlled seq6: Seq(Integer)
	controlled seq7: Seq(Integer)
	controlled seq8: Seq(Integer)
	controlled numOfOnes: Natural
	controlled numOfElements: Integer
	controlled emptyCheck: Boolean
	controlled containsOne: Boolean
	controlled value: Integer
	controlled index: Integer
	controlled firstEl: Integer
	controlled lastEl: Integer
	controlled bag: Bag(Integer)
	controlled set: Powerset(Integer)

definitions:
                       
	main rule r_main =
		par
			numOfOnes := count(seq1, 1)
			numOfElements := length(seq1)
			emptyCheck := isEmpty(seq1)
			containsOne := contains(seq1, 1)
			seq3 := union(seq1, seq2)
			seq1 := append(seq1, 9)
			seq2 := prepend(12, seq2)
			seq4 := insertAt(seq4, 2n, 1)
			seq5 := subSequence(seq1, 1n, 3n)
			value := at(seq1, 2n)
			index := indexOf(seq1, 3)
			firstEl := first(seq1)
			lastEl := last(seq1)
			bag := asBag(seq1)
			set := asSet(seq1)
			seq6 := excluding(seq1, 2)
			seq7 := tail(seq1)
			seq8 :=  replaceAt(seq1, 3n, 4)
		endpar 

default init s0:
	function seq1 = [1, 2, 3, 4, 5]
	function seq2 = [64324]
	function seq3 = [6, 7]
	function seq4 = [6, 343, 45353, 343]
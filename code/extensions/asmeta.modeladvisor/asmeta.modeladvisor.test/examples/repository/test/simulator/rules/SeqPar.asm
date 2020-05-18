asm SeqPar
import ../../../STDL/StandardLibrary

signature:

dynamic controlled x : Integer
dynamic controlled y : Integer
dynamic controlled z : Integer

definitions:

main rule r_main =
			seq
				par
				x := x +1
				z := x
				endpar
				par
				x := x +1
				y := x
				endpar
			endseq

default init initial_state:
function x = 0
function y = 0
function z = 0
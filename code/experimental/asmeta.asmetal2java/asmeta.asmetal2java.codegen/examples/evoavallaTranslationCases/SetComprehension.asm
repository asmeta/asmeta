asm SetComprehension

import ../STDL/StandardLibrary

signature:
	enum domain Color = {RED | GREEN | BLUE}
	domain Small subsetof Integer
	controlled selected: Powerset(Integer)
	controlled selectedPairs: Powerset(Prod(Integer, Integer))
	controlled selectedColors: Powerset(Color)
	controlled selectedSmall: Powerset(Small)

definitions:
	domain Small = {1 : 3}
	main rule r_Main =
		par
			selected := {$value in {1 : 5} | $value > 2 : $value}
			selectedPairs := {$left in {1 : 2}, $right in {$left, $left + 1} |
				$right > $left : ($left, $right)}
			selectedColors := {$color in Color | $color != BLUE : $color}
			selectedSmall := {$small in Small | $small > 1 : $small}
		endpar

default init s0:
	function selected = {}
	function selectedPairs = {}
	function selectedColors = {}
	function selectedSmall = {}

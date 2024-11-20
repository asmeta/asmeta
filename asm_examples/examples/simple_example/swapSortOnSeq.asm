asm swapSortOnSeq

import ../../STDL/StandardLibrary

signature:
	dynamic controlled list: Seq(Integer)
	static subseq: Prod(Seq(Integer), Natural, Natural) -> Seq(Integer)

definitions:

	function subseq($list in Seq(Integer), $x in Natural, $y in Natural) =
		if($y >= $x) then
			subSequence($list, $x, $y + 1n)
		else
			[]
		endif

	main rule r_Main =
		choose $x in {0n : 6n}, $y in {0n : 7n} with
				$x < $y and at(list, $x) > at(list, $y) do
			list := union(
						union(
							append(subseq(list, 0n, iton($x - 1n)), at(list, $y)),
							append(subseq(list, $x + 1n, iton( $y - 1n)), at(list, $x))
						),
						subseq(list, $y + 1n, iton(length(list)-1))
					)

default init s0:
	function list = [3, 1, 7, 4, 2, 5, 12, 8]

asm QuickSort
import  STDL/StandardLibrary
	
signature:
	controlled f: Seq(Integer)
	static qsort: Seq(Integer) -> Seq(Integer)

definitions:

function qsort($seq in Seq(Integer)) =
	if length($seq) = 0 then
		[]
	else
		let ($pivot = first($seq)) in
			union(union(
					qsort([$x in $seq | $x < $pivot : $x]), 
					[$y in $seq | $y = $pivot: $y  ]),
				qsort([$z in $seq | $z > $pivot: $z ]))
		endlet
	endif

	main rule r_main =
		f := qsort([5, 1, 8, 10, 2, 4, 7, 3, 6, 9])
// DANIELA CUGLIETTA, PATRIZIA SCANDURRA
// Esercizio pag. 173
// merge sort with turbo

asm MERGE

import ../../STDL/StandardLibrary

//declare universes and functions

	
signature:
	
	domain List subsetof Seq (Integer)
    domain List2 subsetof Seq (Integer)

	dynamic monitored input: List
    dynamic out output: List

	dynamic monitored lefthalf: List -> List
	dynamic monitored righthalf: List -> List

definitions:


	turbo rule r_Merge ($l in List, $m in List) in List =
                local x: List [skip]
		if length ($l)=0n or length ($m)=0n then
			result := union ($l,$m)
		else if first($l) <= first ($m) 
                     then
                             seq
                                x <- r_Merge(last($l), $m)
                                result := prepend (first($l),x)
                             endseq

	             else
	                     seq
                                x <- r_Merge($l, last($m))
                                result := prepend (first($m), x)
                             endseq                
		     endif
		endif		

	turbo rule r_MergeSort ($l in List) in List =
		local x: List [x:=[]]
                local y: List [y:=[]]
                if length($l) <= 1n 
                then
			result := $l
		else 
                  seq
                    x <- r_MergeSort(lefthalf($l))
                    y <- r_MergeSort (righthalf($l))
                    result <- r_Merge(x,y) 
                  endseq
		endif 


main rule r_MainMerge  = output <- r_MergeSort (input)

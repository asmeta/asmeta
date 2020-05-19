// DANIELA CUGLIETTA, PATRIZIA SCANDURRA
// Esercizio pag. 172
// with turbo rules 

asm QUICKSORT

import ../../../../STDL/StandardLibrary

//declare universes and functions

	
signature:
	domain Number subsetof Natural
	domain List subsetof Seq (Natural)
	
	
	// given a list L returns the list of the elements in the tail of L which are 
	// < of the head of the list L
	
	static tail_lt_head:	 List -> List
	
	// given a list L returns the list of the elements in the tail of L which are 
	// >= of the head of the list L

	static tail_ge_head:	 List -> List

    // given a list L returns the tail of L without the first element
       
    static tail: List->List  
	

	
definitions:
	
	
	function tail_lt_head ($l in List) =
	  if length($l) = 1n then []
	  else let ($tail = tail($l), 
	            $head = first($l),
	            $x = first($tail)) in
	       if ($x < $head) then append(tail_lt_head($tail),$x)
	       else tail_lt_head($tail)
               endif
               endlet  
	  endif

        function tail_ge_head ($l in List) =
	  if length($l) = 1n then []
	  else let ($tail = tail($l), 
	            $head = first($l),
	            $x = first($tail)) in
	       if ($x >= $head) then append(tail_lt_head($tail),$x)
	       else tail_lt_head($tail)
               endif
               endlet  
	  endif	

	  
	turbo rule r_Quicksort ($l in List) in List =
		local x:List [x:=[]]
                local y:List [y:=[]]

                if length ($l) <= 1n then result := $l 
		else
                seq
		 x <- r_Quicksort(tail_lt_head($l)) 
	         y <- r_Quicksort(tail_ge_head($l))
		result := union(x, prepend(first($l),y))   
		endseq
		endif

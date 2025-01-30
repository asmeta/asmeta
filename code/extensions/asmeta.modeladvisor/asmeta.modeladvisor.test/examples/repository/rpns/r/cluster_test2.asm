asm cluster_test2

import ../../STDL/StandardLibrary
import cluster

signature:
	
	dynamic controlled c3: Cluster
	dynamic controlled c4: Cluster

// dynamic functions

// definitions 

definitions:
	      
/*   invariant inv_checkcenter over c3,c4:
   		let ($cx3 = first(center(c3)), $cy3 = second(center(c3)), 
   		     $cx4 = first(center(c4)), $cy4 = second(center(c4)),
   		     $sum_ps3 = sumPoints(c3),$sum_ps4 = sumPoints(c4)) in
    	 isUndef(center(c3)) or 
   			$cx3 = first($sum_ps3) / itor(size(c3)) and
   			$cy3 = second($sum_ps3) / itor(size(c3)) and
   			$cx4 = first($sum_ps4) / itor(size(c4)) and
   			$cy4 = second($sum_ps4) / itor(size(c4))
   		endlet   
*/   
   main rule r_test =
     
		if isUndef(center(c3)) or isUndef(center(c4)) then 
			seq
        		r_setMean[c3]
        		r_setMean[c4]
			endseq
		else 
        	// move (6.0,10.0) from c3 to c4
       		r_move[(6.0,10.0),c3,c4]
       	endif

default init initial_state:
       
	function c3 = {(0.0,0.0),(6.0,10.0)}
 
    function c4 = {(4.0,8.0)}
       
asm cluster_movePoints

import ../../STDL/StandardLibrary
import cluster

signature:
	
	dynamic controlled c3: Cluster

	dynamic controlled c4: Cluster

    controlled firstStep: Boolean // centers and ssrs are iniializated

   	// initial input - front end
   	monitored  cluster_in : Cluster

// definitions 

definitions:

	invariant inv_Center_x_c3 over center : 
	  (not firstStep implies ( abs( first(center(c3)) - ( first(sumPoints(c3)) / ntor(size(c3)))) < 0.2 ))
	      
	invariant inv_Center_x_c4 over center : 
	  (not firstStep implies ( abs( first(center(c4)) - ( first(sumPoints(c4)) / ntor(size(c4)))) < 0.2 ))

   main rule r_test =
     	if firstStep then
			seq
				r_setCenter[c3]
				r_setCenter[c4]
          		firstStep := false
			endseq
		else  
		    choose $c in {3,4} with true do
		    if ($c = 3 and size(c3) > 1n) or size(c4) = 1n then 
		    	choose $p in c3 with true do r_move[$p,c3,c4]
		    else 	
		    	choose $p2 in c4 with true do r_move[$p2,c4,c3]
			endif
		endif

default init initial_state:
        
    function c3 = {(0.0,0.3),(1.9,1.6),(100.1,0.3),(100.1,0.6),(100.3,1.7),(100.4,0.1),(100.4,1.1),(100.9,0.5),(100.9,0.8),(100.9,0.9),(100.9,1.3),(101.0,0.5),(101.0,1.9),(101.1,0.1),(101.1,1.3),(101.2,1.0),(101.2,1.5),(101.3,0.4),(101.5,0.5),(101.7,1.0),(101.8,1.4),(101.9,0.8)}
    
    function c4 = {(0.0,0.5),(0.0,0.9),(0.2,0.0),(0.3,1.1),(0.3,1.7),(0.9,1.8),(1.1,0.2),(1.1,0.3),(1.4,1.7),(100.7,0.2),(101.6,1.8)}
   
   	function firstStep = true
       
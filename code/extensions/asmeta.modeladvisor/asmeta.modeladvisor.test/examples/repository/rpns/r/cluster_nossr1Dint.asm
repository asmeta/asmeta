// cluster senza ssr 1 dimensione con uso di interi 

asm cluster_nossr1Dint

import ../../STDL/StandardLibrary
		
signature:

// domains
				
   	domain Cluster subsetof Powerset(Integer)		    
	
// static functions 

    // sum of all the points in a cluster
    static sumPoints: Cluster -> Integer
              

// dynamic functions

    controlled firstStep: Boolean // centers and ssrs are iniializated

   	controlled mean : Cluster -> Real

   	controlled pointToMove : Integer // useful for debugging - not necessary

   	controlled clusters : Powerset(Cluster)   
   
   	// initial input - front end
   	monitored  clusters_in : Powerset(Cluster)
   
// definitions 

definitions:
	
   	// sum of the Points in a Cluster --> Point   	
   	function sumPoints($c in Cluster) =
      if isEmpty($c) then 0
      else  let ($c_seq =  asSequence($c), $one = first($c_seq)) in      			
//      	add($one, sumPoints(excluding($c,$one))) //--> questo da errori NON USARE EXCLUDING ....
      		$one + sumPoints(asSet(tail($c_seq)))
      		endlet
      endif
	      
// RULES

// compute the initial centers

    rule r_setCenter($c in Cluster) = 
    	let ($sum_ps = sumPoints($c)) in
    	mean($c) :=  itor($sum_ps) / ntor(size($c))
    	endlet

	// move point p from d_i to d_j
	// update center and the sets of points
	
    rule r_move($p in Integer, $d_i in Cluster,$d_j in Cluster) =      
    	// store the current values of centers
	    let ($old_ci = mean($d_i), $old_cj = mean($d_j), 
	         $n_i = ntor(size($d_i)), $n_j = ntor(size($d_j))) in
	   		seq
	   		// add $p to $c2
	   		$d_j := including($d_j,$p) // $d_j($p):= true
	   		// remove $p from $c
	   		$d_i := excluding($d_i,$p) // $d_i($p):= false
	   		// update the center
			mean($d_j) := $old_cj + ((itor($p) - $old_cj) / ($n_j + 1.0))  				
			mean($d_i) := $old_ci - ((itor($p) - $old_ci) / ($n_i - 1.0))  				
	   		endseq
	   	endlet
   

	rule r_step = 
		// take a cluster c1 with at least 2 points 
		choose $c1 in clusters with size($c1) > 1n do
			// take a point $p in $c1
			// take a second cluster such that ... 		
		   choose $p in $c1, $c2 in clusters with $c1 != $c2 do 
	   						seq 
	   						pointToMove := $p
	   						// move $p from $c1 to $c2
	   					    r_move[$p,$c1,$c2]
	   						endseq	 

// invariants

// uso di abs perch� altrimenti basta piccolo errore di arrotondamento

	invariant inv_mean over mean: 
	  not firstStep implies (forall $c in clusters with 
    	( abs( mean($c) - ( itor(sumPoints($c)) / ntor(size($c))))) < 0.2 )

	

//Main Rule
 
    main rule r_clustering = 
    
	//if (exist $c in clusters with isUndef(center($c))) then 
	if firstStep then
			seq
			    clusters :=  clusters_in 
				forall $c1 in clusters do r_setCenter[$c1]
          		firstStep := false
			endseq
	else r_step[]
	endif
          
 
//define the initial states 

default init initial_state:

	function firstStep = true
	

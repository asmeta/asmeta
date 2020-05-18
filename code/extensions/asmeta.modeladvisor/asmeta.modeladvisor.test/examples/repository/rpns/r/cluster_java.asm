// FIRST version of the ASM presented by Andreas Pritz at ASM 2007
// It models the clustering algorithm

// July 07: it does not work beacuse locationva are not supporte din choose

asm cluster_java

import ../../STDL/StandardLibrary

export Cluster,center,r_setMean,sumPoints,r_move
		
signature:

// domains
		
   	domain Point subsetof Prod(Real,Real)
		
   	domain Cluster subsetof Powerset(Point)		    
	
// static functions 

   	static x : Point -> Real

   	static y : Point -> Real
   
   	static distance2 : Prod(Point,Point) -> Real // --> in Java

   	static square : Real -> Real

	// compute the new ssr when a point is moved
    static newSSR: Prod(Cluster,Point) -> Real
  
    static newSSRC: Prod(Cluster,Point,Point) -> Real
  
    // sum the ssr of cluster, with center c
    static initSSR: Prod(Cluster,Point) -> Real
    
    // sum of all the points in a cluster
    static sumPoints: Cluster -> Point
              
	// replace including and excluding
	static adding: Prod(Cluster,Point) -> Cluster
	
	static removing: Prod(Cluster,Point) -> Cluster 


// dynamic functions

    controlled firstStep: Boolean // centers and ssrs are iniializated

   	controlled center : Cluster -> Point

   	controlled clusters : Powerset(Cluster)   

   	controlled ssr : Cluster ->  Real
   
   	// initial input - front end
   	
   	monitored  clusters_in : Powerset(Cluster)
   
// definitions 

definitions:
	
   	function x ($p in Point) = first($p)

   	function y ($p in Point) = second($p)
	
/*	function newSSRC($c in Cluster, $p in Point, $cp in Point) =
   	 let ($tmp = if contains($c,$p) then - 1.0 else 1.0 endif) in    
       ntor(size($c)) / ( ntor(size($c)) + $tmp ) * distance2($p,$cp) 
     endlet
*/	
   	function newSSR($c in Cluster, $p in Point) = newSSRC($c, $p , center($c))

   	// square of a real number
   	function square($r in Real) = $r * $r


   function initSSR($c in Cluster, $center in Point) =
      if isEmpty($c) then 0.0      
      else  let ($c_seq = asSequence($c), $one = first($c_seq)) in 
            distance2($one,$center) + initSSR(excluding($c,$one),$center)
            endlet
      endif
      	      
// RULES

// compute the initial centers

    rule r_setMean($c in Cluster) = 
    	let ($sum_ps = sumPoints($c)) in
    	center($c) := ( x($sum_ps) / ntor(size($c)) , y($sum_ps) / ntor(size($c)))
    	endlet

	// move point p from d_i to d_j
	// update center and the sets of points
	
    rule r_move($p in Point, $d_i in Cluster,$d_j in Cluster) =      
    	// store the current values of centers
	    let ($old_ci = center($d_i), $old_cj = center($d_j), $n_i = ntor(size($d_i)), $n_j = ntor(size($d_j))) in
	   		seq
	   		// add $p to $c2
	   		$d_j := adding($d_j,$p) // $d_j($p):= true
	   		// remove $p from $c
	   		$d_i := removing($d_i,$p) // $d_i($p):= false
	   		// update the center
			let ($x_j = x($old_cj), $y_j = y($old_cj)) in 		
//				center($d_j) := (($x_j + ((x($p) - $x_j) / ($n_j + 1.0))), $y_j + ((y($p) - $y_j) / ($n_j + 1.0)))  				
				center($d_j) := (( ($n_j * $x_j + x($p) ) / ($n_j + 1.0)), $y_j + ((y($p) - $y_j) / ($n_j + 1.0)))
			endlet
			let ($x_i = x($old_ci), $y_i = y($old_ci)) in 		
//				center($d_i) := (($x_i - ((x($p) - $x_i) / ($n_i - 1.0))), $y_i - ((y($p) - $y_i) / ($n_i - 1.0)))  				
				center($d_i) := (( ($n_i * $x_i - x($p) ) / ($n_i - 1.0)), $y_i - ((y($p) - $y_i) / ($n_i - 1.0)))  				
			endlet
	   		endseq
	   	endlet

   // compute the initial ssr	
   rule r_setSSR($c in Cluster) =	ssr($c) := initSSR($c,center($c))
   

	rule r_step = 
	
		// take a cluster c1 with at least 2 points 
		// take a point $p in it
		// take a second cluster such that 		
		choose $c1 in clusters with size($c1) > 1n do
		   choose $p in $c1, $c2 in clusters with  
					(forall $cc in clusters with newSSR($c2,$p) <= newSSR($cc,$p) )do
					// move p from 
	   				if ( $c1 != $c2) then
	   				let ($increase = newSSR($c2,$p), $decrease = newSSR($c1,$p)) in
	   					if ($decrease > $increase) then
	   						let ($old_ssr1 = ssr($c1),$old_ssr2 = ssr($c2)) in
	   						seq 
	   							// move $p from $c to $c2
	   							r_move[$p,$c1,$c2]
	   							// update ssr 
	   							ssr($c2) := $old_ssr2 + $increase
	   							ssr($c1) := $old_ssr1 - $decrease
	   						endseq	 
	   						endlet  					
	   					endif
	   				endlet endif


	invariant inv_Center_x over center : 
	  not firstStep implies (forall $c in clusters with 
    	( abs( x(center($c)) - ( x(sumPoints($c)) / ntor(size($c))))) < 0.2 )

/*	invariant inv_Center_y over center :
	  not firstStep implies (forall $c in clusters with 
       ( abs( y(center($c)) - ( y(sumPoints($c)) / ntor(size($c))))) < 0.2 )
*/


//Main Rule
 
    main rule r_clustering = 

	//if (exist $c in clusters with isUndef(center($c))) then 
	if firstStep then
			seq
			    clusters :=  clusters_in 
				forall $c1 in clusters do r_setMean[$c1]
          		forall $c2 in clusters do r_setSSR[$c2]
          		firstStep := false
			endseq
	else r_step[]
	endif
  
//define the initial states 

default init initial_state:

	//{c1,c2} //function center($c in Cluster) = (0.0,0.0)	
	
	function firstStep = true
	
	/*function clusters =  
	// read from file: 
	clusters_in*/ 

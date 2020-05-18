// version of the ASM presented by Andreas Pritz at ASM 2007
// It models the clustering algorithm
// it does not use the location variables 

asm cluster_nolocvar

import ../../STDL/StandardLibrary
		
signature:

// domains
		
   	domain Point subsetof Prod(Real,Real)
		
   	domain Cluster subsetof Powerset(Point)		    
   	//domain Cluster subsetof Powerset(Prod(Real,Real))	
	
// static functions 

   	static x : Point -> Real // the x of a Point

   	static y : Point -> Real // the y of a Point
   
   	static distance2 : Prod(Point,Point) -> Real // the square of the distance between two points

   	static square : Real -> Real

	// add one point to another
	static add: Prod(Point,Point) -> Point

	// compute the new ssr when a point i moved
    static newSSR: Prod(Cluster,Point) -> Real
  
    // sum the ssr of cluster, with center c
    static initSSR: Prod(Cluster,Point) -> Real
    
    // sum of all the points in a cluster
    static sumPoints: Cluster -> Point
              

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

   	// || p1 -p2 ||^2 
   	function distance2($p1 in Point, $p2 in Point) =
		square(x($p1) - x($p2)) + square( y($p1) - y($p2 ))


	// increase or decrease by adding or deleting $p to $c
   	function newSSR($c in Cluster, $p in Point) =
   	 let ($tmp = if contains($c,$p) then - 1.0 else 1.0 endif) in    
       (ntor(size($c)) / ( ntor(size($c)) + $tmp )) * distance2($p,center($c)) 
     endlet 

   	// square of a real number
   	function square($r in Real) = $r * $r

   	// sum of the Points in a Cluster --> Point   	
   	function sumPoints($c in Cluster) =
      if isEmpty($c) then (0.0,0.0)
      else  let ($c_seq =  asSequence($c), $one = first($c_seq)) in      			
//      	add($one, sumPoints(excluding($c,$one))) //--> questo da errori ....
      		add($one, sumPoints(asSet(tail($c_seq))))
      		endlet
      endif

   	function initSSR($c in Cluster, $center in Point) =
      if isEmpty($c) then 0.0      
      else  let ($c_seq = asSequence($c), $one = first($c_seq)) in 
//            distance2($one,$center) + initSSR(excluding($c,$one),$center)
			distance2($one,$center) + initSSR(asSet(tail($c_seq)),$center)
            endlet
      endif
      
	//add two Points
	function add($p1 in Point, $p2 in Point) = 
			( x($p1) + x($p2), y($p1) + y($p2))  				
	      
// RULES

// compute the initial centers

    rule r_setCenter($c in Cluster) = 
    	let ($sum_ps = sumPoints($c)) in
    	center($c) := ( x($sum_ps) / ntor(size($c)) , y($sum_ps) / ntor(size($c)))
    	endlet

	// move point p from d_i to d_j
	// update center and the sets of points
	
    rule r_move($p in Point, $d_i in Cluster,$d_j in Cluster) =      
    	// store the current values of centers
	    let ($old_ci = center($d_i), $old_cj = center($d_j), 
	         $n_i = ntor(size($d_i)), $n_j = ntor(size($d_j)),
	         $new_di = excluding($d_i,$p), $new_dj = including($d_j,$p)
	         ) in
	   		seq
	   		// add $p to $d_j 
	   		// remove $dj from clusters and add $d_j + $p
	   		clusters := excluding(clusters,$d_j)
	   		clusters := including(clusters,$new_dj)
	   		// remove $p from $d_i
	   		clusters := excluding(clusters,$d_i)
	   		clusters := including(clusters,$new_di)
	   		// update the center
			let ($x_j = x($old_cj), $y_j = y($old_cj)) in 		
				center($new_dj) := ($x_j + ((x($p) - $x_j) / ($n_j + 1.0)) , $y_j + ((y($p) - $y_j) / ($n_j + 1.0)))  				
			endlet
			let ($x_i = x($old_ci), $y_i = y($old_ci)) in 		
				center($new_di) := ($x_i - ((x($p) - $x_i) / ($n_i - 1.0)) , $y_i - ((y($p) - $y_i) / ($n_i - 1.0)))  				
			endlet
	   		endseq
	   	endlet

		
// compute the initial ssr	
   rule r_setSSR($c in Cluster) =	ssr($c) := initSSR($c,center($c))
   

	rule r_step = 
		// take a cluster c1 with at least 2 points 
		choose $c1 in clusters with size($c1) > 1n do
			// take a point $p in $c1
			// take a second cluster such that ... 		
		   choose $p in $c1, $c2 in clusters with  
					( $c1 != $c2) and (forall $cc in clusters with newSSR($c2,$p) <= newSSR($cc,$p) )do
					// move p if necessary
	   				let ($increase = newSSR($c2,$p), $decrease = newSSR($c1,$p)) in
	   					if ($decrease > $increase) then
	   						let ($old_ssr1 = ssr($c1),$old_ssr2 = ssr($c2)) in
	   						seq 
	   							// move $p from $c1 to $c2
	   							r_move[$p,$c1,$c2]
	   							// update ssr 
	   							ssr($c2) := $old_ssr2 + $increase
	   							ssr($c1) := $old_ssr1 - $decrease
	   						endseq	 
	   						endlet
	   					endif  					
	   				endlet 

// invariants

// uso di abs perch� altrimenti basta piccolo errore di arrotondamento

	invariant inv_Center_x over center : 
	  not firstStep implies (forall $c in clusters with 
    	( abs( x(center($c)) - ( x(sumPoints($c)) / ntor(size($c))))) <= 0.1 )

	invariant inv_Center_y over center :
	  not firstStep implies (forall $c in clusters with 
       ( abs( y(center($c)) - ( y(sumPoints($c)) / ntor(size($c))))) <= 0.1 )


//Main Rule
 
    main rule r_clustering = 
    
	//if (exist $c in clusters with isUndef(center($c))) then 
	if firstStep then
			seq
			    clusters :=  clusters_in 
				forall $c1 in clusters do r_setCenter[$c1]
          		forall $c2 in clusters do r_setSSR[$c2]
          		firstStep := false
			endseq
	else r_step[]
	endif
          
 
//define the initial states 

default init initial_state:
	
	function firstStep = true
	

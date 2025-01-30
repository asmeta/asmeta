// cluster senza ssr 

asm cluster_nossr

import ../../STDL/StandardLibrary
		
signature:

// domains
		
   	domain Point subsetof Prod(Real,Real)
		
   	domain Cluster subsetof Powerset(Point)		    
	
// static functions 

   	static x : Point -> Real // the x of a Point

   	static y : Point -> Real // the y of a Point
   
   	static distance2 : Prod(Point,Point) -> Real // the square of the distance between two points

   	static square : Real -> Real

	// add one point to another
	static add: Prod(Point,Point) -> Point
    
    // sum of all the points in a cluster
    static sumPoints: Cluster -> Point
              

// dynamic functions

    controlled firstStep: Boolean // centers and ssrs are iniializated

   	controlled center : Cluster -> Point

   	controlled pointToMove : Point // useful for debugging - not necessary

   	controlled clusters : Powerset(Cluster)   
   
   	// initial input - front end
   	monitored  clusters_in : Powerset(Cluster)
   
// definitions 

definitions:
	
   	function x ($p in Point) = first($p)

   	function y ($p in Point) = second($p)

   	// || p1 -p2 ||^2 
   	function distance2($p1 in Point, $p2 in Point) =
		square(x($p1) - x($p2)) + square( y($p1) - y($p2 ))

   	// square of a real number
   	function square($r in Real) = $r * $r

   	// sum of the Points in a Cluster --> Point   	
   	function sumPoints($c in Cluster) =
      if isEmpty($c) then (0.0,0.0)
      else  let ($c_seq =  asSequence($c), $one = first($c_seq)) in      			
//      	add($one, sumPoints(excluding($c,$one))) //--> questo da errori NON USARE EXCLUDING ....
      		add($one, sumPoints(asSet(tail($c_seq))))
      		endlet
      endif

	//add two Points
	function add($p1 in Point, $p2 in Point) = 
			( x($p1) + x($p2), y($p1) + y($p2))  				
	      
// RULES

// compute the initial centers

    rule r_setCenter($c in Cluster) = 
    	let ($sum_ps = sumPoints($c)) in
    	center($c) := ( x($sum_ps) / itor(size($c)) , y($sum_ps) / itor(size($c)))
    	endlet

	// move point p from d_i to d_j
	// update center and the sets of points
	
    rule r_move($p in Point, $d_i in Cluster,$d_j in Cluster) =      
    	// store the current values of centers
	    let ($old_ci = center($d_i), $old_cj = center($d_j), 
	         $n_i = itor(size($d_i)), $n_j = itor(size($d_j)),
	         $x_j = x($old_cj), $y_j = y($old_cj),
	         $x_i = x($old_ci), $y_i = y($old_ci)) in
	   		seq
	   		// add $p to $c2
	   		$d_j := including($d_j,$p) // $d_j($p):= true
	   		// remove $p from $c
	   		$d_i := excluding($d_i,$p) // $d_i($p):= false
	   		// update the center
//			let ($x_j = x($old_cj), $y_j = y($old_cj)) in 		
//				center($d_j) := ($x_j + ((x($p) - $x_j) / ($n_j + 1.0)) , $y_j + ((y($p) - $y_j) / ($n_j + 1.0)))  				
				center($d_j) := (( ($n_j * $x_j + x($p) ) / ($n_j + 1.0)), ( ($n_j * $y_j + y($p) ) / ($n_j + 1.0)))
//			endlet
//			let ($x_i = x($old_ci), $y_i = y($old_ci)) in 		
//				center($d_i) := (($x_i - ((x($p) - $x_i) / ($n_i - 1.0))), $y_i - ((y($p) - $y_i) / ($n_i - 1.0)))  				
				center($d_i) := (( ($n_i * $x_i - x($p) ) / ($n_i - 1.0)), ( ($n_i * $y_i - y($p) ) / ($n_i - 1.0)))  				
//			endlet
	   		endseq
	   	endlet
   

	rule r_step = 
		// take a cluster c1 with at least 2 points 
		choose $c1 in clusters with size($c1) > 1 do
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

	invariant inv_Center_x over center : 
	  not firstStep implies (forall $c in clusters with 
    	( abs( x(center($c)) - ( x(sumPoints($c)) / itor(size($c))))) < 0.2 )

	invariant inv_Center_y over center :
	  not firstStep implies (forall $c in clusters with 
       ( abs( y(center($c)) - ( y(sumPoints($c)) / itor(size($c))))) < 0.2 )


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
	

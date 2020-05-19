// Test di unita'
// controlla la somma dei punti e il centro dei punti iniziale

asm cluster_test

import ../../../../STDL/StandardLibrary
import cluster

signature:
	
	static c1: Cluster

	static c2: Cluster
	static c3: Cluster
	static c4: Cluster

// dynamic functions

// definitions 

definitions:
	
    
   function c1 = {(0.0,0.0),(2.0,2.0)}

   function c2 = {(3.0,1.0),(5.0,3.0)}
   
   function c3 = {(-1.0,-1.0),(0.0,0.0),(1.0,0.0),(9.0,11.0)}

   function c4 = {(3.0,1.0)}
      
/*   invariant inv_centerComputation1 over c1: 
   		let ($c = center(c1)) in isDef($c) implies 
   		  false
   		  //( first($c) = 1.0 and second($c) = 1.0 ) 
   		endlet

   invariant inv_centerComputation2 over c2: 
   		let ($c = center(c2)) in isDef($c) implies 
   		  false
   		  //( first($c) = 1.0 and second($c) = 1.0 ) 
   		endlet*/
   
   invariant inv_sumPoints1 over c1:
       let ($s = sumPoints(c1)) in first($s) = 2.0 and second($s) = 2.0 endlet

   invariant inv_sumPoints2 over c2:
       let ($s = sumPoints(c2)) in first($s) = 8.0 and second($s) = 4.0 endlet
   

   main rule r_test =  
   	   par 
       r_setCenter[c1]
       r_setCenter[c2]
       r_setCenter[c3]
       r_setCenter[c4]
       endpar

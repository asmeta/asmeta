// problems with static functions
// questo da problemi !!!
// crea tante funnzioni, una per ognu uso di a

asm static_functions

import ../../../STDL/StandardLibrary


signature:
	
	domain Point subsetof Prod(Integer,Integer)
	
	static a: Powerset(Point)		

// dynamic functions
   dynamic controlled f : Powerset(Point) -> Integer

// definitions 

definitions:
	   
   function a = {(0,0)}

   invariant inv_A over f: 
   		let ($c = f(a)) in true 
   		endlet

   main rule r_test =  
   	seq
   	f(a) := 10
   	f(a) := 20
    endseq

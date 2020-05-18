// use of location vars
// da cencellare perchè ormai ho tanti test già in repository 

asm LocationVarChoose

import ../../STDL/StandardLibrary

		
signature:
		
   abstract domain Processes
      
   domain NumSets subsetof Powerset(Integer)

   domain NumSeqs subsetof Seq(Integer)
   			
   dynamic domain SomeNums subsetof Integer
   
//   controlled numbers : Powerset(SomeNums)

   controlled numbers : Powerset(Integer)
      
   controlled setofnumbers: Powerset(NumSets)
   
   controlled setofproc: Powerset(Processes) 
   
   static p1 : Processes
   
definitions:
	
//   domain NumSets = {[0],[]}	
   
//   domain SomNums = {0, 1, 3}

//Main Rule
 
    rule r_set_1($y in Integer) = $y := 1
 
    main rule r_test =
            
//    	  choose $x in NumSets with true do $x := [1,2,3]
// questa il parser la da sbagliata
//          choose $x in SomeNums with true do r_set_1[$x] 

//       questa tratta un dominimo come una funzione
// 		il parse l'accetta ma è sbagliata?
// 		choose $x in SomeNums with true do $x := 5 

//       choose $x in numbers with $x < 20 do $x := 20 // numbers($x) := false numbers(20):= true
       
//        choose $y in  setofnumbers with true do $y := including($y,0)   
															// $y(0) := true ad esempio {1,2,3}(0):= true
      
// per aggiungere un elemento posso fare:      numbers:= including(numbers,30) 

// scelgo un processo e lo metto ad una costante

        choose $z in setofproc with true do $z := p1 // setofproc(p1) := true setofproc($z) := false

											
  
  default init initial_state:

	 domain SomeNums = {0, 1, 3}

     function setofnumbers = {{},{3,4}}

     function numbers = {0,5,10}	 
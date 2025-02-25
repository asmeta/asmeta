asm euclideMCD

//Algoritmo di Euclide per il calcolo del Massimo Comun Divisore (MCD)
//function MCD(a, b)
//    while a != b
//         if a > b
//             a := a - b
//         else
//             b := b - a
//     return a

//Questo modello ASM implementa l'algoritmo di Euclide nel seguente modo:
//- ogni step della macchina corrisponde ad un'iterazione del while;
//- quando l'update set e' vuoto vuol dire che e' stato ottenuto il risultato che puo'
//  essere letto sia in numA che in numB.
//Il modello deve essere eseguito con l'opzione -ne (run until empty).
//I valori dei due numeri numA e numB devono essere inizializzati nello stato iniziale,

import StandardLibrary

signature:
	dynamic controlled numA: Integer
	dynamic controlled numB: Integer

definitions:

	main rule r_Main =
		if(numA != numB) then
			if(numA > numB) then
				numA := numA - numB
			else
				numB := numB - numA
			endif
		endif

default init s0:
	function numA = 6409 //121
	function numB = 3289 //22

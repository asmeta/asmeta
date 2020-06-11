module util

import ../../../../../../asm_examples/STDL/StandardLibrary
export *

signature:
	domain Coord subsetof Integer
	domain Passo subsetof Integer

definitions:
	domain Coord = {1..8}
	domain Passo ={-1..1}

	//aggiorna la coordinata $i di un taxi o di un cliente che sta
	//andando verso $j  
	rule r_updateCoord($i in Coord, $j in Coord) =	
		par		
			if($i > $j) then 
				$i := $i - 1
			endif
			if($i < $j) then 
				$i := $i + 1
			endif
		endpar

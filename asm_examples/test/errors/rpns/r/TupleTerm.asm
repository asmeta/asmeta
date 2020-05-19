// adesso funziona, valuta un argomento come tupla
asm TupleTerm

import ../../../../STDL/StandardLibrary

signature:

	static dir: Prod(Integer, Integer) -> Boolean

	static a:  Prod(Integer, Integer)
	
		
definitions:

    function dir ($i in Integer, $j in Integer) = true

    function a = (2,3)
	
//	main rule r_Main = if( undir(1) = ((2,3))) skip endif --> errore il parser 
	
//	main rule r_Main = if( undir(1) = a ) then skip endif errore l'interpreter: manca eq for tuple terms

	main rule r_Main = if( dir(a)) then skip endif 
	
default init initial_state:
	
     
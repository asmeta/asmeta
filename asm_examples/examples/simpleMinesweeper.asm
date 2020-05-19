asm simpleMinesweeper

import ../STDL/StandardLibrary
import ../STDL/CTLlibrary

signature:
	enum domain GameStatus = {PLAYING | WON | LOST}
	domain Coord subsetof Integer
	dynamic controlled status: GameStatus
	dynamic controlled selectedCell: Prod(Coord, Coord) -> Boolean
	static hasMine: Prod(Coord, Coord) -> Boolean
	dynamic monitored selectedRow: Coord
	dynamic monitored selectedCol: Coord

definitions:
	domain Coord = {1 : 4}

	function hasMine($r in Coord, $c in Coord) =
		($r = 1 and $c = 2) or ($r = 2 and $c = 4) or ($r = 4 and $c = 3)  

	macro rule r_selectCell =
		//the let rule was needed in the old version of AsmetaSMV
		/*let ($r = selectedRow, $c = selectedCol) in
			if hasMine($r, $c) then
				status := LOST
			else
				selectedCell($r, $c) := true
			endif
		endlet*/
		if hasMine(selectedRow, selectedCol) then
			status := LOST
		else
			selectedCell(selectedRow, selectedCol) := true
		endif

	//scrivere una proprieta' temporale che permetta di ottenere
	//una sequenza di mosse che porta alla vittoria
	CTLSPEC not(ef(status = WON))
	//se la macchina segnala la vittoria del giocatore, non e'
	//possibile che nel futuro segnali uno stato differente 
	CTLSPEC ag(status = WON implies ag(status = WON))
	//esiste un cammino lungo il quale il giocatore non vince mai
	CTLSPEC eg(status != WON)

	main rule r_Main =
		if status=PLAYING then		
			if (exist $r in Coord, $c in Coord with not(selectedCell($r,$c)) and
		                                        not(hasMine($r,$c))) then
				r_selectCell[]
			else
				status := WON
			endif
		endif

default init s0:
	function selectedCell($r in Coord, $c in Coord) = false
	function status = PLAYING

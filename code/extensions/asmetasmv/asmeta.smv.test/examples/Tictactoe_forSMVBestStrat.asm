asm Tictactoe_forSMV

//strategia che permette di non perdere mai

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary


signature:
	//For representing a board  
	domain Square subsetof Integer
	enum domain Skind = {CROSS|NOUGHT|EMPTY}
	enum domain Finalres = {PLAYER|PC|TIE|RUN}
	enum domain Status = {TURNX|CHECKX|TURNPC|CHECKPC|GAMEOVER}
	controlled symbol: Square -> Skind
	controlled status: Status

	//For execution behavior
	monitored playerX: Square  // mossa di X
	controlled whoWon: Finalres
	static noSquareLeft : Boolean

definitions:
	domain Square = {1..9}
 
	function noSquareLeft =  not (exist $s in Square with symbol($s) = EMPTY)
 
	/*rule r_movePlayerX =
		if symbol(playerX)= EMPTY then
			par
				symbol(playerX):= CROSS
				status := CHECKX   
			endpar
		else
			status := TURNX
		endif*/

	//riscritta per eliminare la monitorata come argomento.
	//Al posto della monitorata e' stata usata una choose.
	//puo' essere utilizzato questo trucco perche', in questo caso,
	//la monitorata e' utilizzata solo in questo punto.
	//Se fosse utilizzata anche in altri punti non sarebbe valido usare la
	//choose in ogni punto (diverse choose, in uno stato, potrebbe scegliere valori diversi).
	/*rule r_movePlayerX =
		choose $x in Square with true do
			if symbol($x)= EMPTY then
				par
					symbol($x):= CROSS
					status := CHECKX   
				endpar
			else
				status := TURNX
			endif*/

	//riscritta per risolvere il problema della monitorata come argomento, ma lasciando la monitorata.
	//Dove c'e' la monitorata basta mettere la forall.
	//Si poteva anche mettere la choose (con condizione $x=playerX), pero' la forall e' meno onerosa.
	rule r_movePlayerX =
		forall $x in Square with $x=playerX do
			if symbol($x)= EMPTY then
				par
					symbol($x) := CROSS
					status := CHECKX   
				endpar
			else
				status := TURNX
			endif
  
	//A very naive player
	rule r_movePC =
		if(symbol(5)=CROSS and
		(forall $ i in Square with $i!=5 implies symbol($i)=EMPTY)) then
			symbol($s):=  NOUGHT
		else
			choose $s in Square with symbol($s)=EMPTY do
				par
					symbol($s):=  NOUGHT
					status := CHECKPC
				endpar
		endif 


  
	rule r_checkForAWinner($symbol in Skind) = 
		// GAME OVER WITH A WINNER?
		if (symbol(1) = $symbol and symbol(2) = $symbol and symbol(3) = $symbol) or 
		(symbol(4) = $symbol and symbol(5) = $symbol and symbol(6) = $symbol) or 
		(symbol(7) = $symbol and symbol(8) = $symbol and symbol(9) = $symbol) or 
		(symbol(1) = $symbol and symbol(4) = $symbol and symbol(7) = $symbol) or 
		(symbol(2) = $symbol and symbol(5) = $symbol and symbol(8) = $symbol) or 
		(symbol(3) = $symbol and symbol(6) = $symbol and symbol(9) = $symbol) or 
		(symbol(1) = $symbol and symbol(5) = $symbol and symbol(9) = $symbol) or 
		(symbol(3) = $symbol and symbol(5) = $symbol and symbol(7) = $symbol) then 
			par
				status := GAMEOVER
				if $symbol = CROSS then
					whoWon:= PLAYER
				else
					whoWon:= PC 
				endif
			endpar
			// GAME TIE?
		else
			if ( noSquareLeft ) then
				par
					status := GAMEOVER
					whoWon := TIE
				endpar
			else             
				if $symbol = CROSS then
					status:= TURNPC
				else
					status:= TURNX
				endif
			endif
		endif
  
  	CTLSPEC ef(symbol(1)!=EMPTY)
  	CTLSPEC ef(symbol(2)!=EMPTY)
  	CTLSPEC ef(symbol(3)!=EMPTY)
  	CTLSPEC ef(symbol(4)!=EMPTY)
  	CTLSPEC ef(symbol(5)!=EMPTY)
  	CTLSPEC ef(symbol(6)!=EMPTY)
  	CTLSPEC ef(symbol(7)!=EMPTY)
  	CTLSPEC ef(symbol(8)!=EMPTY)
  	CTLSPEC ef(symbol(9)!=EMPTY)
  	CTLSPEC ag(whoWon = RUN) //deve essere falsa
	CTLSPEC ag(whoWon!=PC)//il PC puo' vincere. Cerco il controesempio
	CTLSPEC ag(whoWon!=PLAYER)//il player puo' vincere. Cerco il controesempio
	CTLSPEC ag(whoWon!=TIE)//puo' finire con una patta. Cerco il controesempio
	CTLSPEC ef(whoWon=PC)//il PC puo' vincere
	CTLSPEC ef(whoWon=PLAYER)//il player puo' vincere
	CTLSPEC ef(whoWon=TIE)//puo' finire con una patta
	CTLSPEC ag((
						(symbol(1)=CROSS and symbol(2)=CROSS and symbol(3)=CROSS) or
						(symbol(4)=CROSS and symbol(5)=CROSS and symbol(6)=CROSS) or
						(symbol(7)=CROSS and symbol(8)=CROSS and symbol(9)=CROSS) or
						(symbol(1)=CROSS and symbol(4)=CROSS and symbol(7)=CROSS) or
						(symbol(2)=CROSS and symbol(5)=CROSS and symbol(8)=CROSS) or
						(symbol(3)=CROSS and symbol(6)=CROSS and symbol(9)=CROSS) or
						(symbol(1)=CROSS and symbol(5)=CROSS and symbol(9)=CROSS) or
						(symbol(3)=CROSS and symbol(5)=CROSS and symbol(7)=CROSS) or
						(symbol(1)=NOUGHT and symbol(2)=NOUGHT and symbol(3)=NOUGHT) or
						(symbol(4)=NOUGHT and symbol(5)=NOUGHT and symbol(6)=NOUGHT) or
						(symbol(7)=NOUGHT and symbol(8)=NOUGHT and symbol(9)=NOUGHT) or
						(symbol(1)=NOUGHT and symbol(4)=NOUGHT and symbol(7)=NOUGHT) or
						(symbol(2)=NOUGHT and symbol(5)=NOUGHT and symbol(8)=NOUGHT) or
						(symbol(3)=NOUGHT and symbol(6)=NOUGHT and symbol(9)=NOUGHT) or
						(symbol(1)=NOUGHT and symbol(5)=NOUGHT and symbol(9)=NOUGHT) or
						(symbol(3)=NOUGHT and symbol(5)=NOUGHT and symbol(7)=NOUGHT)
						)
						implies ax(ag(status = GAMEOVER)))

	main rule r_Main =  
		if status = TURNX then r_movePlayerX[]
		else if status = CHECKX then r_checkForAWinner[CROSS]
		else if status = TURNPC then r_movePC[]
		else if status = CHECKPC then r_checkForAWinner[NOUGHT]
		endif endif endif endif

default init s0:
	function symbol($s in Square) = EMPTY
	//The computer is polite: allows the person (symbol X) to play first 
	function whoWon = RUN
	function status = TURNX
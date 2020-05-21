asm Tictactoe_v2

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

//versione con il dominio Coord.
//La board e' un Prod(Coord, Coord)

signature:
	domain Coord subsetof Integer
	enum domain Sign = {CROSS | NOUGHT | EMPTY}
	enum domain ResultDomain = {PLAYER | PC | TIE | RUN}
	enum domain Status = {TURN_USER | CHECK_USER | TURN_PC | CHECK_PC | GAMEOVER}
	controlled board: Prod(Coord, Coord) -> Sign //rappresenta la scacchiera
	controlled status: Status //lo stato della macchina

	monitored userChoiceX: Coord //scelta coordinata x dell'utente
	monitored userChoiceY: Coord //scelta coordinata y dell'utente
	controlled winner: ResultDomain // indica il vincitore
	derived noSquareLeft: Boolean

definitions:
	domain Coord = {1:3}
 
 	//indica se tutte le caselle sono occupate
	function noSquareLeft =
		not (exist $x in Coord, $y in Coord with board($x, $y) = EMPTY)

	rule r_moveUser =
		//bisogna usare il forall perche' poi deve essere tradotto in NuSMV.
		forall $x in Coord, $y in Coord with $x=userChoiceX and $y=userChoiceY
											and board($x, $y) = EMPTY
												do
			//if(board($x, $y) = EMPTY) then
				par
					board($x, $y) := CROSS
					status := CHECK_USER
				endpar
			//endif

	//la strategia del PC e' banale: sceglie una cella vuota
	rule r_movePC =
		choose $x in Coord, $y in Coord with board($x, $y)=EMPTY do
			par
				board($x, $y):=  NOUGHT
				status := CHECK_PC
			endpar

	//controlla che ci sia una combinazione vincente
	rule r_checkBoard($sign in Sign) =
		if(		(board(1, 1) = $sign and board(1, 2) = $sign and board(1, 3) = $sign) or
				(board(2, 1) = $sign and board(2, 2) = $sign and board(2, 3) = $sign) or
				(board(3, 1) = $sign and board(3, 2) = $sign and board(3, 3) = $sign) or
				(board(1, 1) = $sign and board(2, 1) = $sign and board(3, 1) = $sign) or
				(board(1, 2) = $sign and board(2, 2) = $sign and board(3, 2) = $sign) or
				(board(1, 3) = $sign and board(2, 3) = $sign and board(3, 3) = $sign) or
				(board(1, 1) = $sign and board(2, 2) = $sign and board(3, 3) = $sign) or
				(board(1, 3) = $sign and board(2, 2) = $sign and board(3, 1) = $sign) ) then
			par
				status := GAMEOVER
				if($sign = CROSS) then
					winner := PLAYER
				else
					winner := PC
				endif
			endpar
		else
			//non c'e' una combinazione vincente e non ci sono celle vuote
			if(noSquareLeft) then
				par
					winner := TIE
					status := GAMEOVER
				endpar
			else
				if($sign = CROSS) then
					status := TURN_PC
				else
					status := TURN_USER
				endif
			endif
		endif

	//AsmetaL invariant
	invariant over winner: winner=TIE implies noSquareLeft

	//CTL properties
 	CTLSPEC ef(noSquareLeft) // esiste uno stato in cui tutte le caselle sono occupate
 	CTLSPEC ag(noSquareLeft implies ag(noSquareLeft))

 	CTLSPEC ef(winner=PC)  // il pc puo' vincere
 	CTLSPEC ef(winner=PLAYER) //l'utente puo' vincere
 	CTLSPEC ef(winner=TIE) //ci puo' essere patta
 	CTLSPEC ag(winner=TIE implies noSquareLeft)
 	//CTLSPEC ag(noSquareLeft implies winner=TIE) //falso. uno puo' vincere all'ultima mossa
  	CTLSPEC ag( (
						(board(1, 1)!=EMPTY and board(1, 1)=board(1, 2) and board(1, 2)=board(1, 3)) or
						(board(2, 1)!=EMPTY and board(2, 1)=board(2, 2) and board(2, 2)=board(2, 3)) or
						(board(3, 1)!=EMPTY and board(3, 1)=board(3, 2) and board(3, 2)=board(3, 3)) or
						(board(1, 1)!=EMPTY and board(1, 1)=board(1, 2) and board(1, 2)=board(1, 3)) or
						(board(2, 1)!=EMPTY and board(2, 1)=board(2, 2) and board(2, 2)=board(2, 3)) or
						(board(3, 1)!=EMPTY and board(3, 1)=board(3, 2) and board(3, 2)=board(3, 3)) or
						(board(1, 1)!=EMPTY and board(1, 1)=board(2, 2) and board(2, 2)=board(3, 3)) or
						(board(1, 3)!=EMPTY and board(1, 3)=board(2, 2) and board(2, 2)=board(3, 1))
						)
						implies ax(ag(status = GAMEOVER))
						)

	main rule r_Main =
		switch(status)
			case TURN_USER: r_moveUser[]
			case CHECK_USER: r_checkBoard[CROSS]
			case TURN_PC: r_movePC[]
			case CHECK_PC: r_checkBoard[NOUGHT]
		endswitch

default init s0:
	function board($x in Coord, $y in Coord) = EMPTY
	function winner = RUN
	function status = TURN_USER

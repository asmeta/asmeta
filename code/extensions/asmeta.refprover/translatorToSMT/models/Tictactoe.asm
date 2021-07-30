asm Tictactoe

import StandardLibrary

//versione con il dominio Coord.
//La board e' un Prod(Coord, Coord)

signature:
	domain Coord subsetof Integer
	enum domain Sign = {CROSS | NOUGHT | EMPTY}
	enum domain ResultDomain = {PLAYER | PC | TIE | RUN}
	enum domain Status = {TURN_USER | CHECK_USER | TURN_PC | CHECK_PC | GAMEOVER}
	controlled board: Prod(Coord, Coord) -> Sign //rappresenta la scacchiera
	controlled status: Status //lo stato della macchina

	controlled winner: ResultDomain // indica il vincitore
	derived noSquareLeft: Boolean

definitions:
	domain Coord = {1 : 3}
 
 	//indica se tutte le caselle sono occupate
	function noSquareLeft =
		not (exist $x in Coord, $y in Coord with board($x, $y) = EMPTY)

	rule r_moveUser =
		choose $x in Coord, $y in Coord with board($x, $y)=EMPTY do
			par
				board($x, $y):=  CROSS
				status := CHECK_USER
			endpar

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

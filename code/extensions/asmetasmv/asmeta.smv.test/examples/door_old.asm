asm door_old

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

//vecchia versione con switch nella main.
//piu' comprensibile? non credo...

signature:
	enum domain ButtonDomain = {IN_OPEN | IN_CLOSE | OUT_OPEN | OUT_CLOSE | NONE}
	enum domain RoomStatusDomain = {OPENED | CLOSED_WITH_PERSON | CLOSED_EMPTY}

	controlled roomStatus: RoomStatusDomain // stato della stanza
	controlled personInRoom: Boolean //indica se qualcuno e' presente nella stanza

	monitored buttons: ButtonDomain //pulsante che e' stato premuto
	monitored personInRoomMon: Boolean //segnala variazioni della presenza di qualcuno nella stanza
	monitored timeout: Boolean // indica se e' scaduto il timeout

definitions:

	rule r_InOpen =
		if(roomStatus = CLOSED_WITH_PERSON) then
			roomStatus := OPENED
		endif

	rule r_close =
		if(roomStatus = OPENED) then
			if(personInRoomMon) then
				roomStatus := CLOSED_WITH_PERSON
			else
				roomStatus := CLOSED_EMPTY
			endif
		endif

	rule r_OutOpen =
		if(roomStatus = CLOSED_EMPTY) then
			roomStatus := OPENED
		endif

	rule r_timeout =
		if(roomStatus = OPENED and buttons = NONE) then
			if(timeout) then
				if(personInRoomMon) then
					roomStatus := CLOSED_WITH_PERSON
				else
					roomStatus := CLOSED_EMPTY
				endif
			endif
		endif

	rule r_updatePersonInRoom =
		if(roomStatus = OPENED) then
			personInRoom := personInRoomMon
		endif

	//Asmetal invariants
	invariant over roomStatus: roomStatus = CLOSED_WITH_PERSON implies personInRoom


	//CTL properties

	//per capire se il modello funziona
	//la stanza puo' sempre raggiungere uno dei tre stati
	CTLSPEC ag(ef(roomStatus=CLOSED_EMPTY))
	CTLSPEC ag(ef(roomStatus=CLOSED_WITH_PERSON))
	CTLSPEC ag(ef(roomStatus=OPENED))

	//se lo stato e' CLOSED_WITH_PERSON vuol dire che c'e' una persona dentro
	CTLSPEC ag(roomStatus = CLOSED_WITH_PERSON implies personInRoom)

	//reachability property: esiste uno stato in cui c'e' una persona nella stanza
	//con la porta aperta
	CTLSPEC ef(roomStatus = OPENED and personInRoom)

	//safety property: assenza di un comportamento sbagliato.
	//Facciamo il modello di una situazione che vogliamo non si verifichi mai.
	//Modelliamo la situazione dentro un ef e poi lo neghiamo per verificare che la
	//situazione non si verifica mai.
	//Situazione che vogliamo evitare:
	//la porta e' chiusa con una persona dentro la stanza e qualcuno preme il bottone
	//di apertura da fuori; nello stato successivo la porta potrebbe aprirsi
	CTLSPEC not(ef(roomStatus = CLOSED_WITH_PERSON and ex(buttons = OUT_OPEN and roomStatus = OPENED)))

	//Attenzione: non abbiamo alcun problema se la porta e' chiusa con una persona
	//dentro la stanza e qualcuno preme il bottone di apertura da fuori
	CTLSPEC ef(roomStatus = CLOSED_WITH_PERSON and buttons = OUT_OPEN)

	//se la porta e' chiusa con una persona dentro la stanza, lo stato successivo
	//o e' lo stesso (CLOSED_WITH_PERSON) o e' OPENED
	CTLSPEC ag(roomStatus = CLOSED_WITH_PERSON implies ax(roomStatus != CLOSED_EMPTY))

	//se la porta e' chiusa e la stanza e' vuota, lo stato successivo o e' lo stesso
	//(CLOSED_EMPTY) o e' OPENED
	CTLSPEC ag(roomStatus = CLOSED_EMPTY implies ax(roomStatus != CLOSED_WITH_PERSON))

	main rule r_Main =
		par
			if(buttons != NONE and not(roomStatus = CLOSED_EMPTY and
										(buttons=IN_OPEN or buttons=IN_CLOSE))) then
				switch(buttons)
					case IN_OPEN: r_InOpen[]
					case IN_CLOSE: r_close[]
					case OUT_OPEN: r_OutOpen[]
					case OUT_CLOSE: r_close[]
				endswitch
			endif
			r_timeout[]
			r_updatePersonInRoom[]
		endpar

default init s0:
	function roomStatus = CLOSED_EMPTY
	function buttons = NONE
	function personInRoom = false

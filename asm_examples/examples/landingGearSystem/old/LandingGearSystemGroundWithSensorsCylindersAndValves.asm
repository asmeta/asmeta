//http://www.irit.fr/ABZ2014/landing_system.pdf

asm LandingGearSystemGroundWithSensorsCylindersAndValves

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary

//i sensori non bastano per sapere se i gear sono retracting o extending:
//bisogna sapere anche lo stato dei cilindri dei gear

//con cilindri (viene modellato il fatto che un cilindro
//puo' essere mezzo esteso)
//con valvole: le valvole si spengono quando la porta è chiusa o la ruota è retratta?
//Sì. Si spengono anche quando la porta è aperta e la ruota è estesa
//in questo modello, le valvole e i cilindri si attivano insieme:
//quando si accende la valvola di apertura delle porte, anche il cilindro delle porte si attiva.
//Bisognerebbe inserire uno stato di passaggio?

//senza funzioni "doors" e "gears" (non previste nel testo)

signature:
	enum domain HandleStatus = {UP | DOWN}
	enum domain CylinderStatus = {CYLINDER_RETRACTED | CYLINDER_EXTENDING | CYLINDER_EXTENDED | CYLINDER_RETRACTING}
	dynamic monitored handle: HandleStatus
	//gearsExtended is true if the corresponding gear is locked in the extended position and false in the other case
	dynamic controlled gearsExtended: Boolean
	//gearsRetracted is true if the corresponding gear is locked in the retracted position and false in the other case
	dynamic controlled gearsRetracted: Boolean
	//doorsClosed is true if and only if the corresponding door is locked closed
	dynamic controlled doorsClosed: Boolean
	//doorsOpen is true if and only if the corresponding door is locked open
	dynamic controlled doorsOpen: Boolean

	dynamic controlled cylinderDoorStatus: CylinderStatus
	dynamic controlled cylinderGearStatus: CylinderStatus

	dynamic controlled generalElectroValve: Boolean
	dynamic controlled openDoorsElectroValve: Boolean
	dynamic controlled closeDoorsElectroValve: Boolean
	dynamic controlled retractGearsElectroValve: Boolean
	dynamic controlled extendGearsElectroValve: Boolean

definitions:

	rule r_closeDoor =
		switch cylinderDoorStatus
			case CYLINDER_EXTENDED:
				par
					closeDoorsElectroValve := true
					cylinderDoorStatus := CYLINDER_RETRACTING
					doorsOpen := false
				endpar
			case CYLINDER_RETRACTING:
				par
					generalElectroValve := false //quando si chiude la porta vuol dire che una manovra è terminata. quindi si può spegnere la valvola generale
					closeDoorsElectroValve := false
					cylinderDoorStatus := CYLINDER_RETRACTED //il cilindro rimane esteso anche se la valvola è spenta? sembra di sì
					doorsClosed := true
				endpar
			case CYLINDER_EXTENDING:
				par
					closeDoorsElectroValve := true
					openDoorsElectroValve := false
					cylinderDoorStatus := CYLINDER_RETRACTING
				endpar
		endswitch

	rule r_retractingLandingSequence =
		if cylinderGearStatus != CYLINDER_RETRACTED then
			switch cylinderDoorStatus
				case CYLINDER_RETRACTED:
					par
						generalElectroValve := true //si apre la valvola generale quando la porta è chiusa con le ruote estese (e si riceve il segnale UP)
						openDoorsElectroValve := true
						cylinderDoorStatus := CYLINDER_EXTENDING
						doorsClosed := false
					endpar
				case CYLINDER_RETRACTING:
					par
						closeDoorsElectroValve := false
						openDoorsElectroValve := true
						cylinderDoorStatus := CYLINDER_EXTENDING
					endpar
				case CYLINDER_EXTENDING:
					par
						openDoorsElectroValve := false
						cylinderDoorStatus := CYLINDER_EXTENDED //il cilindro rimane esteso anche se la valvola è spenta? sembra di sì
						doorsOpen := true
					endpar
				case CYLINDER_EXTENDED:
					switch cylinderGearStatus
						case CYLINDER_EXTENDED:
							par
								retractGearsElectroValve := true
								cylinderGearStatus := CYLINDER_RETRACTING
								gearsExtended := false
							endpar
						case CYLINDER_RETRACTING:
							par
								retractGearsElectroValve := false
								cylinderGearStatus := CYLINDER_RETRACTED
								gearsRetracted := true
							endpar
						case CYLINDER_EXTENDING:
							par
								extendGearsElectroValve := false
								retractGearsElectroValve := true
								cylinderGearStatus := CYLINDER_RETRACTING
							endpar
					endswitch
			endswitch
		else
			r_closeDoor[]
		endif

	rule r_extendingLandingSequence =
		if cylinderDoorStatus != CYLINDER_EXTENDED then
			switch cylinderDoorStatus
				case CYLINDER_RETRACTED:
					par
						generalElectroValve := true //si apre la valvola generale quando la porta è chiusa con le ruote retratte (e si riceve il segnale DOWN)
						openDoorsElectroValve := true
						cylinderDoorStatus := CYLINDER_EXTENDING
						doorsClosed := false
					endpar
				case CYLINDER_EXTENDING:
					par
						openDoorsElectroValve := false
						cylinderDoorStatus := CYLINDER_EXTENDED
						doorsOpen := true
					endpar
				case CYLINDER_EXTENDED:
					switch cylinderGearStatus
						case CYLINDER_RETRACTED:
							par
								extendGearsElectroValve := true
								cylinderGearStatus := CYLINDER_EXTENDING
								gearsRetracted := false
							endpar
						case CYLINDER_EXTENDING:
							par
								extendGearsElectroValve := false
								cylinderDoorStatus := CYLINDER_EXTENDED
								gearsExtended := true
							endpar
						case CYLINDER_RETRACTING:
							par
								extendGearsElectroValve := true
								retractGearsElectroValve := false
								cylinderGearStatus := CYLINDER_EXTENDING
							endpar
					endswitch
			endswitch
		else
			r_closeDoor[]
		endif

	//for sensors
	invariant over gearsExtended,gearsRetracted: not(gearsExtended and gearsRetracted)
	invariant over doorsOpen,doorsClosed: not(doorsOpen and doorsClosed)

	//for sensors
	CTLSPEC ag(not(gearsExtended and gearsRetracted))
	CTLSPEC ag(not(doorsOpen and doorsClosed))
	CTLSPEC ag(doorsOpen implies not(doorsClosed))
	CTLSPEC ag(doorsClosed implies not(doorsOpen))

	main rule r_Main =
		if handle = UP then
			r_retractingLandingSequence[]
		else
			r_extendingLandingSequence[]
		endif

default init s0:
	function gearsExtended = true
	function gearsRetracted = false
	function doorsClosed = true
	function doorsOpen = false
	function cylinderDoorStatus = CYLINDER_RETRACTED
	function cylinderGearStatus = CYLINDER_EXTENDED
	function openDoorsElectroValve = false
	function closeDoorsElectroValve = false
	function retractGearsElectroValve = false
	function extendGearsElectroValve = false
	function generalElectroValve = false
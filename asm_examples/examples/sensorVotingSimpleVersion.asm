asm sensorVotingSimpleVersion

//versione semplificata del sensor voting descritta in
//http://www.irit.fr/ABZ2014/landing_system.pdf

import ../STDL/StandardLibrary
import ../STDL/CTLlibrary

signature:
	domain NumOfInvalidDom subsetof Integer
	dynamic controlled sensor: Boolean
	dynamic controlled numOfInvalid: NumOfInvalidDom
	derived valid: Boolean
	dynamic monitored sensorMon1: Boolean
	dynamic monitored sensorMon2: Boolean
	dynamic monitored sensorMon3: Boolean

definitions:
	domain NumOfInvalidDom = {0 : 10}

	function valid = numOfInvalid < 10 

	rule r_sensorVoting =
		if sensorMon1 = sensorMon2 then
			par
				sensor := sensorMon1 //(1 = 2, 2 != 3), (1 = 2 = 3)
				if sensorMon1 != sensorMon3 then
					numOfInvalid := numOfInvalid + 1 
				endif
			endpar
		else
			par
				if sensorMon2 = sensorMon3 then
					sensor := sensorMon2 //(1 != 2, 2 = 3)
				else
					sensor := sensorMon1 //(1 = 3, 1 != 2)
				endif
				numOfInvalid := numOfInvalid + 1
			endpar
		endif

	//esiste un cammino in cui il sensore e' sempre valido
	CTLSPEC eg(valid)
	//se il sensore diventa non valido, non puo' diventare valido in seguito
	CTLSPEC ag(not(valid) implies ag(not(valid)))
	//esiste uno stato in cui il sensore vale true e in uno
	//stato successivo vale false
	CTLSPEC ef(sensor and ex(not(sensor)))

	main rule r_Main =
		if valid then
			r_sensorVoting[]
		endif

default init s0:
	function sensor = true
	function numOfInvalid = 0

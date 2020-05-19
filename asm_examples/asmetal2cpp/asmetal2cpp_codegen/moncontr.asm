asm moncontr

import ../../STDL/StandardLibrary
signature:
	dynamic controlled controlledfunction: Integer//numero della carta presente nel bancomat
	dynamic controlled controlledfunction2: Integer//numero della carta presente nel bancomat
	dynamic monitored monitoredfunction: Integer

definitions:
	main rule r_Main =
		controlledfunction2:=controlledfunction

default init s0:
	function controlledfunction = monitoredfunction+1

// a spec that used a monitored which cna be undef
// during simulationmust be possible to put indef
asm UseUndefMon
import ../../STDL/StandardLibrary
	
signature:
monitored m: Integer
controlled c: Integer
		
definitions:	                 	
main rule r_main = 
	if m = undef then c:= c+1 else c:=c endif

default init s0:
	function c = 0

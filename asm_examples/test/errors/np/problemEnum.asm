asm problemEnum
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain ClientState = {IDLE | WAIT}
	enum domain TaxiState = {IDLE | TRAVEL}
	dynamic controlled clState: ClientState
	dynamic controlled txState: TaxiState

definitions:
	main rule r_Main =
		if(clState = IDLE and txState = IDLE) then
			skip		
		endif

default init s0:
	function clState = IDLE
	function txState = IDLE

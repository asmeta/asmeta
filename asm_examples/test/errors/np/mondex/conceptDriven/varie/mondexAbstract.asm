//mondex abstract da articolo:
//A concept-driven construction of the mondex protocol using three refinements
//versione fedele all'articolo
//non funziona perche' la choose non supporta la scelta su un basic type domain (es. $value in Natural)

asm mondexAbstract

import ../../../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain Name = {AA | BB | CC | DD}
	dynamic controlled balance: Name -> Natural
	dynamic controlled lost: Name -> Natural
	static authentic: Name -> Boolean
	dynamic monitored fail: Boolean
	
definitions:
	function authentic($n in Name) = if($n = AA or $n = BB or $n=CC) then
						true
					else
						false
					endif
	
	rule r_transferOk =
		choose $from in Name, $to in Name, $value in Natural with 
		authentic($from) and authentic($to) and $from!=$to and $value <= balance($from) do
			par
				balance($from) := balance($from) - $value
				balance($to) := balance($to) + $value
			endpar

	rule r_transferFail =
		choose $from in Name, $to in Name, $value in Natural with 
		authentic($from) and authentic($to) and $from!=$to and $value <= balance($from) do
			par
				balance($from) := balance($from) - $value
				lost($from) := lost($from) + $value
			endpar
	
	//i soldi girano solo tra quelli autenticati
	invariant over balance, lost: (balance(AA) + balance(BB) + balance(CC) + lost(AA) + lost(BB)
										+ lost(CC) = 12n)
	//il credito falso di DD non cambia mai
	invariant over balance, lost: (balance(DD) = 4n and lost(DD) = 0n)

	main rule r_Main =
		if(not(fail)) then
			r_transferOk[]
		else
			r_transferFail[]
		endif

default init s0:
	function balance($n in Name) = at({AA->10n,BB->0n, CC->2n, DD->4n},$n)
	function lost($n in Name) = 0n

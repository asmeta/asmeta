//mondex abstract da articolo:
//The mondex challenge: machine checked proofs for an electronic purse

asm mondexAbstract

import ../../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain Name = {AA | BB | CC | DD}
	domain Money subsetof Natural
	dynamic controlled balance: Name -> Money
	dynamic controlled lost: Name -> Money
	static authentic: Name -> Boolean
	dynamic monitored fail: Boolean
	
definitions:
	domain Money = {0n..13n}

	function authentic($n in Name) = if($n = AA or $n = BB or $n=CC) then
						true
					else
						false
					endif
	
	rule r_abTransfer =
		choose $from in Name, $to in Name, $value in Money with 
		authentic($from) and authentic($to) and $from!=$to and $value <= balance($from) do
			if(not(fail)) then
				par
					balance($from) := balance($from) - $value
					balance($to) := balance($to) + $value
				endpar
			else
				par
					balance($from) := balance($from) - $value
					lost($from) := lost($from) + $value
				endpar
			endif
	
	//i soldi girano solo tra quelli autenticati
	CTLSPEC ag(balance(AA) + balance(BB) + balance(CC) + lost(AA) + lost(BB) + lost(CC) = 12n)

	//il credito falso di DD non cambia mai
	CTLSPEC ag(balance(DD) = 4n and lost(DD) = 0)
	
	main rule r_Main =
		r_abTransfer[]


default init s0:
	function balance($n in Name) = at({AA->10n,BB->0n, CC->2n, DD->4n},$n)
	function lost($n in Name) = 0n

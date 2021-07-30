asm prova

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain Name = {AA | BB | CC | DD}
	//nostro per identificare il tipo di messagio
	enum domain MessageType = {REQ | VAL | ACK}
	//nostro per fare la choose nella main rule
	domain TidDomain subsetof Natural
	domain MoneyDomain subsetof Natural

	dynamic controlled exLogFrom: Prod(Name, Name, MoneyDomain, TidDomain) -> Boolean
	dynamic controlled exLogTo: Prod(Name, Name, MoneyDomain, TidDomain) -> Boolean
	dynamic controlled inbox: Prod(Name, MessageType, Name, MoneyDomain, TidDomain) -> Boolean
	dynamic controlled outbox: Prod(Name, MessageType, Name, MoneyDomain, TidDomain) -> Boolean

	
definitions:
	domain TidDomain = {1n:3n}
	domain MoneyDomain = {0n, 5n, 10n}

	main rule r_irule =
	skip

default init s0:
	function inbox($n in Name, $t in MessageType, $na in Name, $value in MoneyDomain,
								$tid in TidDomain) = false
	function outbox($n in Name, $t in MessageType, $na in Name, $value in MoneyDomain,
								$tid in TidDomain) = false
	function exLogFrom($n in Name, $na in Name, $value in MoneyDomain,
								$tid in TidDomain) = false
	function exLogTo($n in Name, $na in Name, $value in MoneyDomain,
								$tid in TidDomain) = false

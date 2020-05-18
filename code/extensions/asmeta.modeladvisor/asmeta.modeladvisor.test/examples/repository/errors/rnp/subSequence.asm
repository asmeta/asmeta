// il parser fallisce perchè:
// la standard library restituisce con length un intero
// però sunSequence vuole un natural
// la conversione da intero a natural o vicevera sembra l'unica soluzione, però da considerare
// anche la promozione automatica
// giugno 09 AG

asm subSequence

import ../../../STDL/StandardLibrary

signature:
	abstract domain PkgLabel
	static label1: PkgLabel
	static label2: PkgLabel
	static label3: PkgLabel
	static label4: PkgLabel
	static label5: PkgLabel
	static label6: PkgLabel
	dynamic controlled d: Any
	dynamic controlled queue_conveyor: Seq(PkgLabel)

definitions:

	main rule r_Main =par
		let ($x = length(queue_conveyor)) in
			if $x >= 2 then
		      queue_conveyor := subSequence(queue_conveyor, 2, $x)
		    endif
		endlet
		if(contains(queue_conveyor, label2)) then skip endif
		endpar

default init s0:
	function queue_conveyor = [label2, label6, label1, label3, label4, label5]

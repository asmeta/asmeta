asm bitCounter

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	abstract domain Bit
	dynamic controlled value: Bit -> Boolean
	derived carryIn: Bit -> Boolean
	derived carryOut: Bit -> Boolean

	static bit0: Bit
	static bit1: Bit
	static bit2: Bit

definitions:
	function carryIn($b in Bit) =
		switch($b)
			case bit0: true
			case bit1: carryOut(bit0)
			case bit2: carryOut(bit1)
		endswitch

	function carryOut($b in Bit) =
		value($b) and carryIn($b)

	//se in uno stato bit0 e' true, nel successivo e' false (e viceversa)
	CTLSPEC ag(value(bit0) iff ax(not(value(bit0))))

	//se per due stati bit1 e' true, nei due successivi e' false (e viceversa)
	CTLSPEC ag( (value(bit1) and ax(value(bit1)))
						iff
						( ax(ax(not(value(bit1)))) and ax(ax(ax(not(value(bit1))))))
					)


	//se per quattro stati bit2 e' true, nei quattro successivi e' false (e viceversa)				
	CTLSPEC ag( (value(bit2) and ax(value(bit2)) and ax(ax(value(bit2)))) and ax(ax(ax(not(value(bit2))))))
						iff
						(ax(ax(ax(ax(not(value(bit2)))))) and ax(ax(ax(ax(ax(not(value(bit2))))))) and ax(ax(ax(ax(ax(ax(not(value(bit2))))))))
					)

	//tutte le combinazioni di bit sono sempre ottenibili
	CTLSPEC (forall $b0 in Boolean, $b1 in Boolean, $b2 in Boolean
										with ag(af(value(bit0) = $b0 and
												value(bit1) = $b1 and
												value(bit2) = $b2)))

	main rule r_Main =
		forall $b in Bit with true do
			value($b) := value($b) xor carryIn($b)

default init s0:
	function value($b in Bit) = false

asm UnaryMinus
import STDL/StandardLibrary

signature:
	controlled negatedMonitored: Integer
	controlled negatedLiteral: Integer
	controlled negatedExpression: Integer
	monitored mon: Integer

definitions:
	main rule r_Main =
		par
			negatedMonitored := -mon
			negatedLiteral := -5
			negatedExpression := -(mon + 1)
		endpar

default init s0:
	function negatedMonitored = 0
	function negatedLiteral = 0
	function negatedExpression = 0

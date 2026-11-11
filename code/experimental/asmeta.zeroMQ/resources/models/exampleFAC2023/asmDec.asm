/** at every step decrease the sum of monitored functions by 1 
*/
asm asmDec

import StandardLibrary

signature:
	monitored funcInc: Integer
	monitored funcMulti: Integer
	out funcDec: Integer
	static sumOfFunctions: Prod(Integer,Integer)-> Integer

definitions:

	function sumOfFunctions($f1 in Integer, $f2 in Integer) = $f1 + $f2

	main rule r_Main = 
		funcDec := sumOfFunctions(funcInc, funcMulti) - 1

default init s0:
	function funcInc = 0
	function funcMulti = 0

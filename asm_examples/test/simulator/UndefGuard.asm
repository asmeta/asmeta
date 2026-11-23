// in this example, we test what happens if the guard is undef
// according to the semantics of asm book, it should be false
// see https://github.com/asmeta/asmeta/issues/203
asm UndefGuard
import ../../STDL/StandardLibrary

signature:
controlled f: Integer
		
definitions:

main rule r_main = 
	if f = 2 then f:= 1
	else f:= 5 endif

default init s0:

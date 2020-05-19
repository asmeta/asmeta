asm SeqRule01
import ../../STDL/StandardLibrary
	
signature:
controlled f : Integer 
controlled g : Integer 
controlled z : Integer 
		
definitions:

main rule r_main = 
	seq
		f := 10
		g := 5 + f
		z := f * g
		f := 33
	endseq

asm fibonacci
import ../../../STDL/StandardLibrary
	
signature:
controlled f: Integer
		
definitions:

turbo rule r_fibo($x in Integer) =
	if $x < 2 then
		result := $x
	else let ($f1 = 0, $f2 = 0) in seq
		$f1 <- r_fibo($x - 1)
		$f2 <- r_fibo($x - 2)
		result :=  $f1 + $f2
	endseq endlet endif
	
main rule r_main =
	// expected f = 55
	f <- r_fibo(10)
	

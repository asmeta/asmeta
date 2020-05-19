// questo dovrebbe essere giusta oppure no?

asm foverload2

	import ../../STDL/StandardLibrary
	
signature:

	abstract domain Mammiferi

	domain Gatti subsetof Mammiferi
	
	static gatto: Gatti
	
	controlled f: Mammiferi -> Boolean
	controlled f: Gatti -> Boolean

definitions:

	main rule r_main = 	f(gatto):= true
		
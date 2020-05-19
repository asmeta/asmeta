// questo dovrebbe essere giusdto, perchè f viene definita due volte

asm foverload1

	import ../../STDL/StandardLibrary
	
signature:

	enum domain RazzeCani = {BASSOTTO | LUPO}
	enum domain RazzeGatti = {MICIO | GRIGIO}
	abstract domain Mammiferi
	domain Cani subsetof Mammiferi
	domain Gatti subsetof Mammiferi
	
	static cane: Cani
	static gatto: Gatti
	
	controlled f: Cani -> RazzeCani
	controlled f: Gatti -> RazzeGatti

definitions:

	main rule r_main = par
		f(gatto):= MICIO
		f(cane):= LUPO
		endpar 
		
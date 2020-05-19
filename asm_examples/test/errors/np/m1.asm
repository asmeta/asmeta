asm m1

	import ../../../STDL/StandardLibrary

signature:

	enum domain RazzeCani = {BASSOTTO | LUPO}
	abstract domain Mammiferi
	domain Cani subsetof Mammiferi
	domain Gatti subsetof Mammiferi

	static cavallo: Mammiferi
	static cane: Cani
	static gatto: Gatti

	controlled f: Cani -> RazzeCani

definitions:

	main rule r_main =
		forall $d in Mammiferi with true do
			f($d) := LUPO


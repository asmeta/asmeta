// assignemnt to a value out of domain
// it should raise an exception
// ieth any domain
asm concreteAssgnAny
import  ../../STDL/StandardLibrary

signature:
	domain PayloadType subsetof Any
	enum domain Identity = {ID_C,ID_S,ID_A}
	controlled c:PayloadType

definitions:
	domain PayloadType={ID_C,ID_S}

	main rule r_Main = c:= ID_A

default init s0:

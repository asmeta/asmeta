// assignemnt to a value out of domain
// it should raise an exception
// ieth any domain
asm concreteAssgnAnyunion
import  ../../STDL/StandardLibrary

signature:
	domain PayloadType subsetof Any

	enum domain Identity = {ID_C}
	enum domain Nounce = {ID_N}
	enum domain IdentityA = {ID_A}

	controlled c:PayloadType

definitions:
	domain PayloadType= union(Identity,Nounce)

	main rule r_Main = c:= ID_A

default init s0:

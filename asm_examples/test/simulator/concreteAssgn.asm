// assignemnt to a value out of domain
// it should raise an exception
asm concreteAssgn
import  ../../STDL/StandardLibrary

signature:
	enum domain Mydomain={RED | GREEN | YELLOW}

	domain SubMydomain subsetof Mydomain

	//monitored firstvar: Mydomain1
	controlled secondvar: SubMydomain

definitions:
	domain SubMydomain={RED,YELLOW}

	main rule r_Main = secondvar:= GREEN
/*		par
			if firstvar=RED then
				secondvar:= RED
			endif
			if firstvar=GREEN then
				secondvar:= GREEN
			endif
			if firstvar=YELLOW then
				secondvar:= YELLOW
			endif
		endpar*/

default init s0:
	function secondvar = RED

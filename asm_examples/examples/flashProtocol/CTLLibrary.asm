module CTLLibrary

import ../../STDL/StandardLibrary

export *

signature:
	/*-----------CTL formulas------------*/ 
	static eg: Boolean -> Boolean	//exists globally
	static ex: Boolean -> Boolean	//exists next state
	static ef: Boolean -> Boolean	//exists finally
	static ag: Boolean -> Boolean	//forall globally
	static ax: Boolean -> Boolean	//forall next state
	static af: Boolean -> Boolean	//forall finally
	static eu: Prod(Boolean, Boolean) -> Boolean	//exists until
	static au: Prod(Boolean, Boolean) -> Boolean //forall until

	static ctl: Boolean

definitions:	
	

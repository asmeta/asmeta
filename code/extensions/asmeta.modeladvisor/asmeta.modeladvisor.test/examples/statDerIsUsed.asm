asm statDerIsUsed

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled foo: MyDomain
	dynamic controlled fooA: MyDomain
	dynamic controlled fooB: Boolean
	
	static fooC_Der: Boolean
	
	
	dynamic monitored mon1: Boolean
	dynamic monitored mon2: Boolean
	derived der_func: Boolean //used in another derived function body and in the main rule
	derived der_Switch: Boolean //used in another derived function body
	derived der_doubleif: Boolean //not used
	derived der_ifswitch: Boolean //not used
	derived der_switchif: Boolean //not used
	derived der_doubleswitch: Boolean //not used
	derived der_der: Boolean //not used
	derived fooDer: Boolean -> Boolean //location fooDer(false) not used

	static statFunc: MyDomain //not used

	static fooStatA: Boolean -> Boolean //fooStatA(true) is used only in unreachable code fragments
	static fooStatB: Boolean -> Boolean //used only in unreachable code fragments

	/*derived*/ static fooDerA: Boolean -> Boolean
	/*derived*/ static fooDerB: Boolean -> Boolean

	/*derived*/ static fooDerC: Boolean //usata
	/*derived*/ static fooDerD: Boolean//usata nel body di fooDerC

	//derived fooDerE: Boolean //non usata
	//derived fooDerF: Boolean //usata nel body di fooDerE

	derived fooDerG: Boolean
	controlled fooContrInDerG: Boolean

definitions:
	domain MyDomain = {1 : 4}

	function statFunc = 3
	
	function der_der = der_func

	function der_func =
		if(mon1) then
			true
		else
			false
		endif
			
	function der_doubleif =
		if mon1 then
			if mon2 then
				false
			else
				true
			endif
		else
			if mon2 then
				true
			else
				false
			endif
		endif
			
	function der_ifswitch =
		if(mon1) then
			switch(mon2)
				case true:
					false
				case false:
					true
			endswitch
		else
			switch(mon2)
				case true:
					true
				case false:
					false
			endswitch
		endif
	
	function der_switchif =
		switch(mon1)
			case true:
				if(mon2) then
					false
				else
					true
				endif
			case false:
				if(mon2) then
					true
				else
					false
				endif
		endswitch
	
	function der_doubleswitch =
		switch(mon1)
			case true:
				switch(mon2)
					case true:
						false
					case false:
						true
				endswitch
			case false:
				switch(mon2)
					case true:
						true
					case false:
						false
				endswitch
		endswitch
	
	function der_Switch =
		switch(mon1)
			case true:
				false
			case false:
				true
		endswitch

	function fooDer($b in Boolean) =
		not(der_Switch) and $b
		
	function fooC_Der = false
	
	function fooStatA($b in Boolean) = $b
	function fooStatB($b in Boolean) = $b
	
	function fooDerA($b in Boolean) = $b
	function fooDerB($b in Boolean) = $b
	
	function fooDerC = true
	function fooDerD = fooDerC

	//function fooDerE = false
	//function fooDerF = fooDerE

	function fooDerG = fooContrInDerG

	main rule r_Main =
		par
			if(der_func and fooDer(true)) then
				foo := 1
			endif
			choose $q in MyDomain with fooA - $q >=1 do
				fooA := fooA -$q
			if(fooA=2) then
				if(not(fooC_Der)) then
					fooB := false
				endif
			endif
			if false then
				if fooStatA(true) then
					skip
				endif
			endif
			if true then
				if fooStatA(false) then
					skip
				endif
			endif
			if false then
				if(fooStatB(true) and fooStatB(false)) then
					skip
				endif
			endif
			
			if false then
				if fooDerA(true) then
					skip
				endif
			endif
			if true then
				if fooDerA(false) then
					skip
				endif
			endif
			if false then
				if fooDerB(true) and fooDerB(false) then
					skip
				endif
			endif
			if fooDerD then
				skip
			endif
		endpar

default init s0:
	function fooA = 3
	function fooB = true

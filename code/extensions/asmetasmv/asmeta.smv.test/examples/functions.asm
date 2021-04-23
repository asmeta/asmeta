asm functions

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain SubDom subsetof Integer
	domain SubDomOfInt subsetof Integer
	domain SubDomOfInt2 subsetof Integer
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled fooA: SubDom
	dynamic controlled fooB: SubDom
	dynamic controlled fooC: SubDom
	dynamic controlled fooD: SubDom
	dynamic controlled fooE: SubDom
	dynamic controlled fooF: SubDom
	dynamic controlled fooG: SubDom
	dynamic controlled fooH: SubDom
	dynamic controlled fooI: SubDom
	dynamic controlled fooL: Boolean
	dynamic controlled fooM: Boolean
	dynamic controlled fooN: Boolean
	dynamic controlled fooO: Boolean
	dynamic controlled fooLL: Boolean
	dynamic controlled fooLLL: Boolean
	dynamic controlled fooLLLL: Boolean
	dynamic controlled fooLLLLL: Boolean
	dynamic controlled fooLLLLLL: Boolean
	dynamic controlled fooP: Boolean
	dynamic controlled fooQ: Boolean
	dynamic controlled fooR: Boolean
	dynamic controlled fooS: Boolean
	dynamic controlled fooT: Boolean
	dynamic controlled fooU: Boolean
	dynamic controlled fooV: Boolean
	dynamic controlled fooZ: Boolean
	dynamic controlled fooJ: Boolean
	dynamic controlled fooK: Boolean
	dynamic controlled fooX: Boolean
	dynamic controlled fooW: Boolean
	dynamic controlled fooNotEqualsEnum: Boolean
	dynamic controlled fooEqualsEnum: Boolean
	dynamic controlled fooNotEqualsInteger: Boolean
	dynamic controlled fooEqualsInteger: Boolean
	dynamic controlled fooNotTrue: Boolean
	dynamic controlled fooNotFalse: Boolean
	dynamic controlled fooNotNotTrue: Boolean
	dynamic controlled fooNotNotFalse: Boolean
	dynamic controlled fooMinusUnary: SubDomOfInt -> SubDomOfInt
	dynamic controlled fooMinusUnary2: SubDomOfInt -> SubDomOfInt
	dynamic controlled fooLT1: Boolean
	dynamic controlled fooLT2: Boolean
	dynamic controlled fooLT3: Boolean
	dynamic controlled fooLT4: Boolean
	dynamic controlled fooGT1: Boolean
	dynamic controlled fooGT2: Boolean
	dynamic controlled fooGT3: Boolean
	dynamic controlled fooGT4: Boolean
	dynamic controlled fooLE1: Boolean
	dynamic controlled fooLE2: Boolean
	dynamic controlled fooLE3: Boolean
	dynamic controlled fooLE4: Boolean
	dynamic controlled fooLE5: Boolean
	dynamic controlled fooLE6: Boolean
	dynamic controlled fooGE1: Boolean
	dynamic controlled fooGE2: Boolean
	dynamic controlled fooGE3: Boolean
	dynamic controlled fooGE4: Boolean
	dynamic controlled fooGE5: Boolean
	dynamic controlled fooGE6: Boolean
	dynamic controlled fooMod1: Boolean
	dynamic controlled fooMod2: Boolean
	dynamic controlled fooMod3: Boolean
	dynamic controlled fooMod4: Boolean
	dynamic controlled fooMod5: Boolean
	dynamic controlled fooMod6: Boolean
	dynamic controlled fooMod7: Boolean
	dynamic controlled fooMod8: Boolean
	dynamic controlled fooMod9: Boolean
	dynamic controlled fooMod10: Boolean
	dynamic controlled fooMod11: Boolean
	dynamic controlled fooMod12: Boolean
	
	dynamic controlled fooXor1: Boolean
	dynamic controlled fooXor2: Boolean
	dynamic controlled fooXor3: Boolean
	dynamic controlled fooXor4: Boolean

	dynamic controlled funcA: SubDomOfInt2
	dynamic controlled funcB: SubDomOfInt2
	dynamic controlled funcC: SubDomOfInt2
	dynamic controlled funcD: SubDomOfInt2

	dynamic controlled funcDiv: SubDom
	dynamic controlled funcMult: SubDom
	dynamic controlled funcAdd: SubDom
	dynamic controlled funcAdd2: SubDom

	static fooTrue: Boolean
	static fooFalse: Boolean

definitions:
	domain SubDom = {1:10}
	domain SubDomOfInt = {-1..1}
	domain SubDomOfInt2 = {-2..2}
	function fooTrue = true
	function fooFalse = false

	CTLSPEC ag(fooA = 2 and fooB = 3 and fooC = 3 and
			fooD = 1 and fooE = 2 and fooF = 1 and fooG = 6
			and fooH = 4 and fooI = 4)

	CTLSPEC ag(fooL and fooM and not(fooN) and fooO and fooLL and not(fooLLL)
				and fooLLLL and fooLLLLL and fooLLLLLL)
	CTLSPEC ag(fooP and not(fooQ) and not(fooR) and fooS)
	CTLSPEC ag(not(fooT) and not(fooU) and not(fooV) and fooZ)
	CTLSPEC ag(not(fooJ) and fooK and fooX and fooW)
	CTLSPEC ag(fooNotEqualsEnum and fooEqualsEnum and fooNotEqualsInteger
					and fooEqualsInteger and not(fooNotTrue) and fooNotFalse
					and fooNotNotTrue and not(fooNotNotFalse))
	CTLSPEC ag(fooMinusUnary(-1)=1 and fooMinusUnary(0)=0 and fooMinusUnary(1)=-1)
	//CTLSPEC ag(fooMinusUnary2(-1)=-1 and fooMinusUnary2(0)=0 and fooMinusUnary2(1)=1)//versione errata
	CTLSPEC ag(fooMinusUnary2(-1)!=0 and fooMinusUnary2(0)=0 and fooMinusUnary2(1)!=0)
	CTLSPEC ag(fooLT1 and not(fooLT2) and fooLT3 and not(fooLT4))
	CTLSPEC ag(fooGT1 and not(fooGT2) and fooGT3 and not(fooGT4))
	CTLSPEC ag(fooLE1 and not(fooLE2) and fooLE3 and not(fooLE4) and fooLE5 and fooLE6)
	CTLSPEC ag(fooGE1 and not(fooGE2) and fooGE3 and not(fooGE4) and fooGE5 and fooGE6)

	CTLSPEC ag(fooMod1 and fooMod2 and fooMod3 and fooMod4 and fooMod5 and 
		fooMod6 and fooMod7 and fooMod8 /*and fooMod9*/ and fooMod10 and fooMod11 and fooMod12)

	CTLSPEC ag(not(fooXor1) and fooXor2 and fooXor3 and not(fooXor4))

	CTLSPEC funcA = 2 and ax(ag(funcA = -1))
	CTLSPEC funcB = 2 and ax(ag(funcB = 1))
	CTLSPEC funcC = 2 and ax(ag(funcC = -1))
	CTLSPEC funcD = 2 and ax(ag(funcD = 1))

	CTLSPEC funcDiv = 3 and ax(ag(funcDiv = 2))
	CTLSPEC funcMult = 8 and ax(ag(funcMult = 6))
	CTLSPEC funcAdd = 3 and ax(ag(funcAdd = 5))

	main rule r_main =
		par
			fooA := 1 + 1
			fooB := fooA + 1
			fooC := 1 + fooA
			fooD := 2 - 1
			fooE := fooB - 1
			fooF := 3 - fooA
			fooG := 2*3
			fooH := fooA*2
			fooI := 2*fooA
			fooL := false implies false
			fooM := false implies true
			fooN := true implies false
			fooO := true implies true
			fooLL := fooT implies false
			fooLLL := fooZ implies false
			fooLLLL := fooZ implies fooZ
			fooLLLLL := false implies fooP
			fooLLLLLL := fooFalse implies fooTrue
			fooP := false iff false
			fooQ := false iff true
			fooR := true iff false
			fooS := true iff true
			fooT := false and false
			fooU := false and true
			fooV := true and false
			fooZ := true and true
			fooJ := false or false
			fooK := false or true
			fooX := true or false
			fooW := true or true
			fooNotEqualsEnum := AA != BB
			fooEqualsEnum := AA = AA
			fooNotEqualsInteger := 1!=2
			fooEqualsInteger := 2=2
			fooNotTrue := not(fooTrue)
			fooNotFalse := not(fooFalse)
			fooNotNotTrue := not(not(fooTrue))
			fooNotNotFalse := not(not(fooFalse))
			forall $i in SubDomOfInt with true do
				par
					fooMinusUnary($i) := +(-$i)
					fooMinusUnary2($i) := +(-fooMinusUnary($i))
				endpar
			fooLT1 := 1 < 2
			fooLT2 := 2 < 1
			//fooLT3 := 0 < fooMinusUnary2(1)
			fooLT3 := 0 < fooMinusUnary2(1) or fooMinusUnary2(1) < 0
			//fooLT4 := 2 < fooMinusUnary2(1)
			fooLT4 := 2 < fooMinusUnary2(1)
			fooGT1 := 2 > 1
			fooGT2 := 1 > 2
			//fooGT3 := fooMinusUnary2(1) > 0
			fooGT3 := fooMinusUnary2(1) > 0 or 0 > fooMinusUnary2(1)
			fooGT4 := fooMinusUnary2(1) > 2
			fooLE1 := 1 <= 2
			fooLE2 := 2 <= 1
			//fooLE3 := 0 <= fooMinusUnary2(1) //errato
			fooLE3 := 0 != fooMinusUnary2(1)
			fooLE4 := 2 <= fooMinusUnary2(1)
			fooLE5 := 1 <= 1
			fooLE6 := 0 <= fooMinusUnary2(0)
			fooGE1 := 2 >= 1
			fooGE2 := 1 >= 2
			//fooGE3 := fooMinusUnary2(1) >= 0
			fooGE3 := (fooMinusUnary2(1) != 0)
			fooGE4 := fooMinusUnary2(1) >= 2
			fooGE5 := 2 >= 2
			fooGE6 := fooMinusUnary2(0) >= 0
			fooMod1 := (2 mod 2 = 0)
			fooMod2 := (2 mod 1 = 0)
			fooMod3 := (2 mod 2 = 0)
			fooMod4 := (1 mod 2 = 1)
			fooMod5 := (0 mod 2 = 0)
			fooMod6 := (5 mod -2 = 1)
			fooMod7 := (-5 mod 2 = -1)
			fooMod8 := (-5 mod -2 = -1)
			//fooMod9 := (2 mod fooMinusUnary2(1) = 0)
			fooMod10 := (fooMinusUnary2(0) mod 2 = 0)
			//fooMod11:= (fooMinusUnary2(-1) mod 2 = -1)
			fooMod11:= (fooMinusUnary2(-1) mod 2 = -1) or (fooMinusUnary2(-1) mod 2 = 1)
			//fooMod12 := (fooMinusUnary2(-1) mod -2 = -1)
			fooMod12 := (fooMinusUnary2(-1) mod -2 = -1) or (fooMinusUnary2(-1) mod -2 = 1)
			
			fooXor1 := false xor false
			fooXor2 := false xor true
			fooXor3 := true xor false
			fooXor4 := true xor true
			funcA := -1
			funcB := --1
			funcC := ---1
			funcD := ----1
			funcDiv := idiv(5, 2)
			funcMult := mult(3, 2)
			funcAdd := plus(2, 3)
		endpar
	

default init s0:
	function fooA = 2
	function fooB = 3
	function fooC = 3
	function fooD = 1
	function fooE = 2
	function fooF = 1
	function fooG = 6
	function fooH = 4
	function fooI = 4
	function fooL = true
	function fooM = true
	function fooN = false
	function fooO = true
	function fooLL = true
	function fooLLL = false
	function fooLLLL = true
	function fooLLLLL = true
	function fooLLLLLL = true
	function fooP = true
	function fooQ = false
	function fooR = false
	function fooS = true
	function fooT = false
	function fooU = false
	function fooV = false
	function fooZ = true
	function fooJ = false
	function fooK = true
	function fooX = true
	function fooW = true
	function fooNotEqualsEnum = true
	function fooEqualsEnum = true
	function fooNotEqualsInteger = true
	function fooEqualsInteger = true
	function fooNotTrue = false
	function fooNotFalse = true
	function fooNotNotTrue = true
	function fooNotNotFalse = false
	function fooMinusUnary($i in SubDomOfInt) = -$i
	function fooMinusUnary2($j in SubDomOfInt) = $j
	function fooLT1 = true
	function fooLT2 = false
	function fooLT3 = true
	function fooLT4 = false
	function fooGT1 = true
	function fooGT2 = false
	function fooGT3 = true
	function fooGT4 = false
	function fooLE1 = true
	function fooLE2 = false
	function fooLE3 = true
	function fooLE4 = false
	function fooLE5 = true
	function fooLE6 = true
	function fooGE1 = true
	function fooGE2 = false
	function fooGE3 = true
	function fooGE4 = false
	function fooGE5 = true
	function fooGE6 = true
	function fooMod1 = true
	function fooMod2 = true
	function fooMod3 = true
	function fooMod4 = true
	function fooMod5 = true
	function fooMod6 = true
	function fooMod7 = true
	function fooMod8 = true
	//function fooMod9 = true
	function fooMod10 = true
	function fooMod11 = true
	function fooMod12 = true
	function fooXor1 = false
	function fooXor2 = true
	function fooXor3 = true
	function fooXor4 = false
	function funcA = 2
	function funcB = 2
	function funcC = 2
	function funcD = 2
	function funcDiv = idiv(7, 2)
	function funcMult = mult(4, 2)
	function funcAdd = plus(1, 2)
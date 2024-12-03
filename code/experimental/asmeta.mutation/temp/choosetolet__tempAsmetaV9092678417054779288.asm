// translation of the asm (for avalla) file:///C:/Users/angel/codicefromrepos/ricerca/asmeta/asmeta/code/experimental/asmeta.mutation/examples/choosetolet.asm
asm choosetolet__tempAsmetaV9092678417054779288
import C:\\Users\\angel\\codicefromrepos\\ricerca\\asmeta\\asmeta\\asm_examples\\STDL\\StandardLibrary

signature:

    // added by validator
    controlled step__: Integer
    controlled foo1: Integer

definitions:

    macro rule r_main =
        let ($s = 1) in
            foo1 := $s
        endlet


    // new main added by validator
    main rule r_main__ =
        switch step__
			case 0:
				seq
					r_main[]
					step__ := step__ + 1
				endseq
			case 1:
				seq
				if foo1 = 5 then
					result := print("check succeeded: foo1 = 5")
				else
					seq
						result := print("CHECK FAILED: foo1 = 5 at step 1")
						step__ := -2
					endseq
				endif
				r_main[]
				step__ := step__ + 1
			endseq
			case 2:
				seq
			if foo1 = 5 then
				result := print("check succeeded: foo1 = 5")
			else
				seq
					result := print("CHECK FAILED: foo1 = 5 at step 2")
					step__ := -2
				endseq
			endif
			r_main[]
			step__ := step__ + 1
		endseq
			case 3:
				seq
		if foo1 = 5 then
			result := print("check succeeded: foo1 = 5")
		else
			seq
				result := print("CHECK FAILED: foo1 = 5 at step 3")
				step__ := -2
			endseq
		endif
		r_main[]
		step__ := step__ + 1
	endseq
			case 4:
				seq
	if foo1 = 5 then
		result := print("check succeeded: foo1 = 5")
	else
		seq
			result := print("CHECK FAILED: foo1 = 5 at step 4")
			step__ := -2
		endseq
	endif
	r_main[]
	step__ := step__ + 1
endseq
		endswitch
    // added by validator (Initialization)
    default init s0:
        // added by validator (visitFuncInits)
        function step__ = 0
        // this function does not belong to this asm, but it can be initialized 
        function foo1 = 1

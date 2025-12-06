/* translation of the asm (for avalla) 
   file:   asm_examples/examples/simple_example/population.asm
   created on 26 Nov 2025, 18:47:48
 */
asm population__tempAsmetaV6005497458607192670
import C:\\Users\\angel\\Documents\\codefromrepos\\asmeta\\asmeta\\asm_examples\\STDL\\StandardLibrary

signature:
    dynamic abstract domain Person
    enum domain GenderDomain = {MALE | FEMALE}

    // added by validator
    controlled step__: Integer
    controlled gender: Person -> GenderDomain
    controlled age: Person -> Natural
    controlled alive: Person -> Boolean
    controlled mother: Person -> Person
    controlled father: Person -> Person
    static male1: Person
    static female1: Person
    static person!1: Person // added by me - the validator does not add it
    static person!2: Person // added by me - the validator does not add it

definitions:

    macro rule r_reproduce($p in Person) =
        if and(and(eq(gender($p),FEMALE),ge(age($p),13n)),le(age($p),50n)) then
            choose $x in {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100} with true do
                if le($x,30) then
                    choose $father in Person with and(and(alive($father),eq(gender($father),MALE)),ge(age($father),13n)) do
                        extend Person with $child do
                            choose $g in GenderDomain with true do
                                par
                                    age($child) := 0n
                                    alive($child) := true
                                    gender($child) := $g
                                    mother($child) := $p
                                    father($child) := $father
                                endpar
                endif
        endif

    macro rule r_dead($p in Person) =
        choose $x in {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100} with true do
            if gt($x,95) then
                alive($p) := false
            endif

    macro rule r_Main =
        forall $p in Person with alive($p) do
            par
                age($p) := plus(age($p),1n)
                r_reproduce[$p]
                r_dead[$p]
            endpar


    //model invariants
    invariant over Person: (forall $p in Person with implies(and(neq($p,male1),neq($p,female1)),and(implies(alive(mother($p)),lt(age($p),age(mother($p)))),implies(alive(father($p)),lt(age($p),age(father($p)))))))
    invariant over Person: (forall $p in Person with implies(eq(age($p),0),alive($p)))
    invariant over Person: (forall $p in Person with implies(and(neq($p,male1),neq($p,female1)),neq(mother($p),father($p))))
    // new main added by validator
    main rule r_main__ =
        switch step__
			case 0:
				seq
					if mother(female1) = female1 then
						result := print("check succeeded: mother(female1) = female1")
					else
						seq
							result := print("CHECK FAILED: mother(female1) = female1 at step 0")
							step__ := -2
						endseq
					endif
					if father(male1) = male1 then
						result := print("check succeeded: father(male1) = male1")
					else
						seq
							result := print("CHECK FAILED: father(male1) = male1 at step 0")
							step__ := -2
						endseq
					endif
					if alive(female1) = true then
						result := print("check succeeded: alive(female1) = true")
					else
						seq
							result := print("CHECK FAILED: alive(female1) = true at step 0")
							step__ := -2
						endseq
					endif
					if father(female1) = female1 then
						result := print("check succeeded: father(female1) = female1")
					else
						seq
							result := print("CHECK FAILED: father(female1) = female1 at step 0")
							step__ := -2
						endseq
					endif
					if gender(male1) = MALE then
						result := print("check succeeded: gender(male1) = MALE")
					else
						seq
							result := print("CHECK FAILED: gender(male1) = MALE at step 0")
							step__ := -2
						endseq
					endif
					if age(female1) = 16 then
						result := print("check succeeded: age(female1) = 16")
					else
						seq
							result := print("CHECK FAILED: age(female1) = 16 at step 0")
							step__ := -2
						endseq
					endif
					if age(male1) = 19 then
						result := print("check succeeded: age(male1) = 19")
					else
						seq
							result := print("CHECK FAILED: age(male1) = 19 at step 0")
							step__ := -2
						endseq
					endif
					if mother(male1) = male1 then
						result := print("check succeeded: mother(male1) = male1")
					else
						seq
							result := print("CHECK FAILED: mother(male1) = male1 at step 0")
							step__ := -2
						endseq
					endif
					if gender(female1) = FEMALE then
						result := print("check succeeded: gender(female1) = FEMALE")
					else
						seq
							result := print("CHECK FAILED: gender(female1) = FEMALE at step 0")
							step__ := -2
						endseq
					endif
					if alive(male1) = true then
						result := print("check succeeded: alive(male1) = true")
					else
						seq
							result := print("CHECK FAILED: alive(male1) = true at step 0")
							step__ := -2
						endseq
					endif
					r_Main[]
					step__ := step__ + 1
				endseq
			case 1:
				seq
					if gender(person!1) = MALE then
						result := print("check succeeded: gender(person!1) = MALE")
					else
						seq
							result := print("CHECK FAILED: gender(person!1) = MALE at step 1")
							step__ := -2
						endseq
					endif
					if mother(person!1) = female1 then
						result := print("check succeeded: mother(person!1) = female1")
					else
						seq
							result := print("CHECK FAILED: mother(person!1) = female1 at step 1")
							step__ := -2
						endseq
					endif
					if age(person!1) = 0 then
						result := print("check succeeded: age(person!1) = 0")
					else
						seq
							result := print("CHECK FAILED: age(person!1) = 0 at step 1")
							step__ := -2
						endseq
					endif
					if father(person!1) = male1 then
						result := print("check succeeded: father(person!1) = male1")
					else
						seq
							result := print("CHECK FAILED: father(person!1) = male1 at step 1")
							step__ := -2
						endseq
					endif
					if age(male1) = 20 then
						result := print("check succeeded: age(male1) = 20")
					else
						seq
							result := print("CHECK FAILED: age(male1) = 20 at step 1")
							step__ := -2
						endseq
					endif
					if age(female1) = 17 then
						result := print("check succeeded: age(female1) = 17")
					else
						seq
							result := print("CHECK FAILED: age(female1) = 17 at step 1")
							step__ := -2
						endseq
					endif
					if alive(person!1) = true then
						result := print("check succeeded: alive(person!1) = true")
					else
						seq
							result := print("CHECK FAILED: alive(person!1) = true at step 1")
							step__ := -2
						endseq
					endif
					r_Main[]
					step__ := step__ + 1
				endseq
			case 2:
				seq
					if gender(person!2) = MALE then
						result := print("check succeeded: gender(person!2) = MALE")
					else
						seq
							result := print("CHECK FAILED: gender(person!2) = MALE at step 2")
							step__ := -2
						endseq
					endif
					if age(person!2) = 0 then
						result := print("check succeeded: age(person!2) = 0")
					else
						seq
							result := print("CHECK FAILED: age(person!2) = 0 at step 2")
							step__ := -2
						endseq
					endif
					if age(person!1) = 1 then
						result := print("check succeeded: age(person!1) = 1")
					else
						seq
							result := print("CHECK FAILED: age(person!1) = 1 at step 2")
							step__ := -2
						endseq
					endif
					if mother(person!2) = female1 then
						result := print("check succeeded: mother(person!2) = female1")
					else
						seq
							result := print("CHECK FAILED: mother(person!2) = female1 at step 2")
							step__ := -2
						endseq
					endif
					if age(male1) = 21 then
						result := print("check succeeded: age(male1) = 21")
					else
						seq
							result := print("CHECK FAILED: age(male1) = 21 at step 2")
							step__ := -2
						endseq
					endif
					if age(female1) = 18 then
						result := print("check succeeded: age(female1) = 18")
					else
						seq
							result := print("CHECK FAILED: age(female1) = 18 at step 2")
							step__ := -2
						endseq
					endif
					if father(person!2) = male1 then
						result := print("check succeeded: father(person!2) = male1")
					else
						seq
							result := print("CHECK FAILED: father(person!2) = male1 at step 2")
							step__ := -2
						endseq
					endif
					if alive(person!2) = true then
						result := print("check succeeded: alive(person!2) = true")
					else
						seq
							result := print("CHECK FAILED: alive(person!2) = true at step 2")
							step__ := -2
						endseq
					endif
					step__ := step__ + 1
				endseq
		endswitch

    // added by validator (Initialization)
    default init s0:
        // added by validator (visitFuncInits)
        function step__ = 0
        // this function does not belong to this asm, but it can be initialized 
        function mother($p in Person) = $p
        // this function does not belong to this asm, but it can be initialized 
        function father($p in Person) = $p
        // this function does not belong to this asm, but it can be initialized 
        function alive($p in Person) = true
        // this function does not belong to this asm, but it can be initialized 
        function gender($p in Person) = if eq($p,female1) then FEMALE else MALE endif
        // this function does not belong to this asm, but it can be initialized 
        function age($p in Person) = if eq($p,female1) then 16n else 19n endif

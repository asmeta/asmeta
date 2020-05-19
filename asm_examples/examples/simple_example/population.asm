asm population

import ../../STDL/StandardLibrary

signature:
	dynamic abstract domain Person
	enum domain GenderDomain = {MALE | FEMALE}
	dynamic controlled gender: Person -> GenderDomain
	dynamic controlled age: Person -> Natural
	dynamic controlled alive: Person -> Boolean

	dynamic controlled mother: Person -> Person
	dynamic controlled father: Person -> Person

	static male1: Person
	static female1: Person

definitions:
	rule r_reproduce($p in Person) =
		if( gender($p) = FEMALE and age($p) >= 13n and age($p) <= 50n ) then
			choose $x in {1 : 100} with true do
				if($x <= 30) then //probabilita' di riprodursi
					choose $father in Person with alive($father) and gender($father) = MALE and age($father) >= 13n do
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

	rule r_dead($p in Person) =
		choose $x in {1 : 100} with true do
			if($x > 95) then
				alive($p) := false
			endif

	//l'eta' dei figli e' inferiore di quella dei genitori
	//E' corretto il seguente invariante? No, perche' un genitore potrebbe essere morto: in tal
	//caso la sua eta' (di quando e' morto) puo' essere inferiore a quella dei figli.
	/*invariant over Person: (forall $p in Person with ($p != male1 and $p != female1) implies
													(age($p) < age(mother($p)) and age($p) < age(father($p)))
												
							)*/
	//versione corretta
	invariant over Person: (forall $p in Person with ($p != male1 and $p != female1) implies
														(
															(alive(mother($p)) implies age($p) < age(mother($p))) and
															(alive(father($p)) implies age($p) < age(father($p)))
															//(not(alive(mother($p))) or age($p) < age(mother($p))) and
															//(not(alive(father($p))) or age($p) < age(father($p)))
														)
							)

	//i bambini appena nati sono vivi
	invariant over Person: (forall $p in Person with age($p) = 0 implies alive($p))
	//il padre e' diverso dalla madre
	invariant over Person: (forall $p in Person with ($p != male1 and $p != female1) implies mother($p) != father($p))

	main rule r_Main =
		forall $p in Person with alive($p) do
			par
				age($p) := age($p) + 1n
				r_reproduce[$p]
				r_dead[$p]
			endpar

default init s0:
	function mother($p in Person) = $p //Who are the mothers of the first individuals? Themselves 
	function father($p in Person) = $p //Who are the fathers of the first individuals? Themselves
	function alive($p in Person) = true
	function gender($p in Person) = if ($p = female1) then
										FEMALE
									else
										MALE
									endif

	function age($p in Person) = if ($p = female1) then
									16n
								else
									19n
								endif

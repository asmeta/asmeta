asm populationAgent

import ../../STDL/StandardLibrary

signature:
	dynamic domain Person subsetof Agent
	enum domain GenderDomain = {MALE | FEMALE}
	dynamic controlled gender: Person -> GenderDomain
	dynamic controlled age: Person -> Natural
	dynamic controlled alive: Person -> Boolean

	dynamic controlled mother: Person -> Person
	dynamic controlled father: Person -> Person

	static male1: Person
	static female1: Person

definitions:
	rule r_reproduce =
		if( gender(self) = FEMALE and 
			//(age(self) in {13n : 50n})
			age(self) >= 13n and age(self) <= 50n 
			) then
			choose $x in {1 : 100} with true do
				if($x <= 30) then //probabilita' di riprodursi
					choose $father in Person with alive($father) and gender($father) = MALE and age($father) >= 13n do
						extend Person with $child do
							choose $g in GenderDomain with true do
								par
									age($child) := 0n
									alive($child) := true
									gender($child) := $g
									mother($child) := self
									father($child) := $father
								endpar
				endif
		endif

	rule r_dead =
		choose $x in {1 : 100} with true do
			if($x > 95) then
				alive(self) := false
			endif

	rule r_lifeRule =
		par
			age(self) := age(self) + 1n
			r_reproduce[]
			r_dead[]
		endpar

	main rule r_Main =
		forall $p in Person with alive($p) do
			program($p)

default init s0:
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

	agent Person:
		r_lifeRule[]

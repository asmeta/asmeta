/*
Refinement of the model population.asm:
prevents reproduction between first-degree relatives or between siblings.
Model with an initial population of four individuals.
*/

asm population_raff1_fourPersons

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

	static male2: Person
	static female2: Person

	derived areSiblings: Prod(Person, Person) -> Boolean
	derived siblings: Person -> Powerset(Person)
	
	derived isFirstDegreeRelationship: Prod(Person, Person) -> Boolean 

definitions:
	function areSiblings($x in Person, $y in Person) =
		mother($x) = mother($y) and father($x) = father($y)

	function siblings($x in Person) =
		{$y in Person | $x != $y and areSiblings($x, $y) : $y}

	function isFirstDegreeRelationship($x in Person, $y in Person) =
		mother($x) = $y or mother($y) = $x or father($x) = $y or father($y) = $x

	rule r_reproduce($p in Person) =
		if( gender($p) = FEMALE and 
			age($p) >= 13n and age($p) <= 50n 
			) then
			choose $x in {1 : 100} with true do
				if($x <= 20) then //probability of reproduction
					choose $father in Person with alive($father) and gender($father) = MALE and age($father) >= 13n and
													not(areSiblings($p, $father)) and $father!=father($p) and
													mother($father)!=$p do
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
			if($x > 99) then
				alive($p) := false
			endif

	rule r_lifeRule($p in Person) =
		par
			age($p) := age($p) + 1n
			r_reproduce[$p]
			r_dead[$p]
		endpar
// the age of the children is lower than that of their parents
invariant over Person: (forall $p in Person with ($p != male1 and $p != female1 and $p != male2 and $p != female2) implies
														(
															(alive(mother($p)) implies age($p) < age(mother($p))) and
															(alive(father($p)) implies age($p) < age(father($p)))
														)
							)
	//Newborn babies are alive.
	invariant over Person: (forall $p in Person with age($p) = 0 implies alive($p))
	//The father is different from the mother.
	invariant over Person: (forall $p in Person with ($p != male1 and $p != female1 and $p != male2 and $p != female2) implies mother($p) != father($p))

	//The parents of each person cannot be siblings.
	invariant over Person: (forall $p in Person with ($p != male1 and $p != female1 and $p != male2 and $p != female2) implies not(areSiblings(mother($p), father($p))))

	main rule r_Main =
		forall $p in Person with alive($p) do
			r_lifeRule[$p]

default init s0:
	function mother($p in Person) = $p  //who is the mother of the first individuals?
	function father($p in Person) = $p  //who is the father of the first individuals?
	function alive($p in Person) = true
	function age($p in Person) = 16n

	function gender($p in Person) = if ($p = female1 or $p = female2) then
						FEMALE
					else
						MALE
					endif

/*
We want to model the evolution of a population in AsmetaL.
For each individual in the population, we want to know:
- their sex;
- their age;
- their mother and father.

At each step of the machine (each year of the simulation), an individual:
- ages by one year;
- may reproduce;
- may die.

Reproduction:
- a woman can reproduce from age 13 to 50; a man from age 13 onward;
- a woman of reproductive age decides with a 20% probability to reproduce; in that case, she chooses a man of reproductive age;
- the sex of the newborn is equally probable.

Death:
Each year, the probability of death for each individual is 10%.

Modeling requirements:
- People must be modeled with an abstract domain;
- the `extend` rule must be used to create new individuals;
- initialization with two individuals.

Invariants:
- the age of children is less than that of their parents;
- newborn children are alive;
- the father is different from the mother.
*/
 
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
				if($x <= 30) then //probability of reproduction
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

	//the age of the children is lower than that of their parents
	invariant over Person: (forall $p in Person with ($p != male1 and $p != female1) implies
														(
															(alive(mother($p)) implies age($p) < age(mother($p))) and
															(alive(father($p)) implies age($p) < age(father($p)))
														)
							)

	//Newborn babies are alive.
	invariant over Person: (forall $p in Person with age($p) = 0 implies alive($p))
	//The father is different from the mother.
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

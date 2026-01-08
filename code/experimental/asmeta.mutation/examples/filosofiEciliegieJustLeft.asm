asm filosofiEciliegieJustLeft

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	abstract domain Philosopher
	domain NumOfCherries subsetof Integer
	controlled cherriesInPlate: Philosopher -> NumOfCherries

	static leftNeighbour: Philosopher -> Philosopher
	
	static philo1: Philosopher
	static philo2: Philosopher
	static philo3: Philosopher
	static philo4: Philosopher
	static philo5: Philosopher

definitions:
	domain NumOfCherries = {0 : 3}

	function leftNeighbour($p in Philosopher) =
		switch($p)
			case philo1: philo5
			case philo2: philo1
			case philo3: philo2
			case philo4: philo3
			case philo5: philo4
		endswitch

	rule r_philoRule($p in Philosopher) =
		if(cherriesInPlate($p) > 0) then
			cherriesInPlate($p) := cherriesInPlate($p) - 1
		else
			if(cherriesInPlate(leftNeighbour($p)) > 0) then
				par
					cherriesInPlate(leftNeighbour($p)) := cherriesInPlate(leftNeighbour($p)) - 1
					cherriesInPlate($p) := cherriesInPlate($p) + 1
				endpar
			endif
		endif

	main rule r_Main =
		choose $p in Philosopher with true do
			r_philoRule[$p]

default init s0:
	function cherriesInPlate($p in Philosopher) = 3

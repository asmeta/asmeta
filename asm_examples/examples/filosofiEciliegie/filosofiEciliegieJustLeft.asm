asm filosofiEciliegieJustLeft

import ../../STDL/StandardLibrary
import ../../STDL/CTLLibrary

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
			//the let rule was needed in the old version of AsmetaSMV
			/*let($left=leftNeighbour($p)) in
				if(cherriesInPlate($left) > 0) then
					par
						cherriesInPlate($left) := cherriesInPlate($left) - 1
						cherriesInPlate($p) := cherriesInPlate($p) + 1
					endpar
				endif
			endlet*/
			if(cherriesInPlate(leftNeighbour($p)) > 0) then
				par
					cherriesInPlate(leftNeighbour($p)) := cherriesInPlate(leftNeighbour($p)) - 1
					cherriesInPlate($p) := cherriesInPlate($p) + 1
				endpar
			endif
		endif

	//nel sistema ci sono sempre al massimo 15 ciliegie
	CTLSPEC ag(cherriesInPlate(philo1) + cherriesInPlate(philo2) + cherriesInPlate(philo3) +
				cherriesInPlate(philo4) + cherriesInPlate(philo5) <= 15)

	//ogni filosofo non ha mai piu' di 3 ciliegie nel piatto
	CTLSPEC (forall $p in Philosopher with ag(cherriesInPlate($p) <= 3))

	//prima o poi nel sistema non ci sono piu' ciliegie.
	CTLSPEC ef(cherriesInPlate(philo1) + cherriesInPlate(philo2) + cherriesInPlate(philo3) +
				cherriesInPlate(philo4) + cherriesInPlate(philo5) = 0)

	main rule r_Main =
		choose $p in Philosopher with true do
			r_philoRule[$p]

default init s0:
	function cherriesInPlate($p in Philosopher) = 3

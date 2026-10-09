asm evo_pop

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain Eta subsetof Natural
	domain EtaFertDonna subsetof Natural 
	domain EtaFertUomo subsetof Natural
	dynamic abstract domain Persona
	enum domain Sesso = { MAS | FEM }
	
	dynamic controlled padre: Persona -> Persona
	dynamic controlled madre: Persona -> Persona
	dynamic controlled eta: Persona -> Natural
	dynamic controlled sesso: Persona -> Sesso
	dynamic controlled vivo: Persona -> Boolean
	
	static eva: Persona
	static adamo: Persona
	
	dynamic controlled ee: Integer
	
definitions:
	domain Eta = {0n:100n}
	domain EtaFertDonna = {13n:50n}
	domain EtaFertUomo = {13n:100n}
	
	
rule r_genera ($figlio in Persona, $madre in Persona, $padre in Persona) =
		choose $genere in Sesso with true do
			par
				eta($figlio) := 0n
				sesso($figlio) := $genere
				padre($figlio) := $padre
				madre($figlio) := $madre
				vivo($figlio) := true
			endpar	
	
rule r_riproduci ($madre in Persona) =
//if eta($madre) < 50n and eta($madre) >= 13n and sesso($madre) = FEM then
			if (exist $etamadre in EtaFertDonna with $etamadre = eta($madre)) and sesso($madre) = FEM then
				choose $volonta in {1:5} with $volonta = 1 do //20%
//choose $padre in Persona with eta($padre) >=13n and sesso($padre) = MAS and vivo($padre) do
					choose $padre in Persona with (exist $a in EtaFertUomo with $a = eta($padre)) and sesso($padre) = MAS and vivo($padre) do
						extend Persona with $figlio do
							r_genera[$figlio,$madre,$padre]
			endif
			
rule r_muori ($persona in Persona) =
	choose $prob in {1:10} with true do //10%
		if $prob = 1 then
			par
			vivo($persona) := false
			ee := $prob
			endpar
		endif
	
rule r_invecchia ($persona in Persona) =
	eta($persona) := eta($persona) + 1n	
	

// eta minore dei genitori
invariant over Persona: (forall $persona in Persona with ($persona != eva and $persona != adamo 
	implies
	(vivo(madre($persona)) implies (eta($persona) < eta(madre($persona))))
	and
	(vivo(padre($persona)) implies (eta($persona) < eta(padre($persona))))
	))

// i nati sono vivi
invariant over Persona: (forall $persona in Persona with (eta($persona) = 0n implies vivo($persona)))

// genitori diversi
invariant over Persona: (forall $persona in Persona with ($persona != eva and $persona != adamo) implies
	madre($persona) != padre ($persona))
	

main rule r_main =
	forall $persona in Persona with vivo($persona) do
	par
		r_riproduci[$persona]
		r_invecchia[$persona]
		r_muori[$persona]
	endpar
	
	
default init s0:
function padre ($persona in Persona) = $persona
function madre ($persona in Persona) = $persona
function sesso ($persona in Persona) = if $persona = eva then FEM else MAS endif
function eta ($persona in Persona) = 13n
function vivo ($persona in Persona) = true



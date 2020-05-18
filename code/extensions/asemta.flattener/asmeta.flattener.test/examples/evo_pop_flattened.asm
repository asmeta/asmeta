asm evo_pop_flattened
import ../../../../asm_examples/STDL/StandardLibrary

signature:
    domain Eta subsetof Natural
    domain EtaFertDonna subsetof Natural
    domain EtaFertUomo subsetof Natural
    abstract domain Persona
    enum domain Sesso = {MAS | FEM}

    controlled padre: Persona -> Persona
    controlled madre: Persona -> Persona
    controlled eta: Persona -> Natural
    controlled sesso: Persona -> Sesso
    controlled vivo: Persona -> Boolean
    static eva: Persona
    static adamo: Persona
    controlled ee: Integer

definitions:

    domain Eta = {0n,1n,2n,3n,4n,5n,6n,7n,8n,9n,10n,11n,12n,13n,14n,15n,16n,17n,18n,19n,20n,21n,22n,23n,24n,25n,26n,27n,28n,29n,30n,31n,32n,33n,34n,35n,36n,37n,38n,39n,40n,41n,42n,43n,44n,45n,46n,47n,48n,49n,50n,51n,52n,53n,54n,55n,56n,57n,58n,59n,60n,61n,62n,63n,64n,65n,66n,67n,68n,69n,70n,71n,72n,73n,74n,75n,76n,77n,78n,79n,80n,81n,82n,83n,84n,85n,86n,87n,88n,89n,90n,91n,92n,93n,94n,95n,96n,97n,98n,99n,100n}
    domain EtaFertDonna = {13n,14n,15n,16n,17n,18n,19n,20n,21n,22n,23n,24n,25n,26n,27n,28n,29n,30n,31n,32n,33n,34n,35n,36n,37n,38n,39n,40n,41n,42n,43n,44n,45n,46n,47n,48n,49n,50n}
    domain EtaFertUomo = {13n,14n,15n,16n,17n,18n,19n,20n,21n,22n,23n,24n,25n,26n,27n,28n,29n,30n,31n,32n,33n,34n,35n,36n,37n,38n,39n,40n,41n,42n,43n,44n,45n,46n,47n,48n,49n,50n,51n,52n,53n,54n,55n,56n,57n,58n,59n,60n,61n,62n,63n,64n,65n,66n,67n,68n,69n,70n,71n,72n,73n,74n,75n,76n,77n,78n,79n,80n,81n,82n,83n,84n,85n,86n,87n,88n,89n,90n,91n,92n,93n,94n,95n,96n,97n,98n,99n,100n}

    macro rule r_genera($figlio in Persona, $madre in Persona, $padre in Persona) =
        choose $genere in Sesso with true do
            par
                eta($figlio) := 0n
                sesso($figlio) := $genere
                padre($figlio) := $padre
                madre($figlio) := $madre
                vivo($figlio) := true
            endpar

    macro rule r_riproduci($madre in Persona) =
        if and((exist $etamadre in EtaFertDonna with eq($etamadre,eta($madre))),eq(sesso($madre),FEM)) then
            choose $volonta in {1,2,3,4,5} with eq($volonta,1) do
                choose $padre in Persona with and(and((exist $a in EtaFertUomo with eq($a,eta($padre))),eq(sesso($padre),MAS)),vivo($padre)) do
                    extend Persona with $figlio do
                        r_genera[$figlio,$madre,$padre]
        endif

    macro rule r_muori($persona in Persona) =
        choose $prob in {1,2,3,4,5,6,7,8,9,10} with true do
            if eq($prob,1) then
                par
                    vivo($persona) := false
                    ee := $prob
                endpar
            endif

    macro rule r_invecchia($persona in Persona) =
        eta($persona) := plus(eta($persona),1n)


    invariant over Persona: (forall $persona in Persona with implies(and(neq($persona,eva),neq($persona,adamo)),and(implies(vivo(madre($persona)),lt(eta($persona),eta(madre($persona)))),implies(vivo(padre($persona)),lt(eta($persona),eta(padre($persona)))))))
    invariant over Persona: (forall $persona in Persona with implies(eq(eta($persona),0n),vivo($persona)))
    invariant over Persona: (forall $persona in Persona with implies(and(neq($persona,eva),neq($persona,adamo)),neq(madre($persona),padre($persona))))
    main rule r_main =
        forall $persona in Persona with vivo($persona) do
            par
                r_riproduci[$persona]
                r_invecchia[$persona]
                r_muori[$persona]
            endpar

default init s0:
    function padre($persona in Persona) = $persona
    function madre($persona in Persona) = $persona
    function sesso($persona in Persona) = if eq($persona,eva) then FEM else MAS endif
    function eta($persona in Persona) = 13n
    function vivo($persona in Persona) = true

// a simple example with a tic tac toe game

asm testSignature

import STDL/StandardLibrary

signature:
	// DOMAINS

	//Astratti
 	abstract domain NumCard
    abstract domain Sfortuna

    dynamic abstract domain Dinam



    domain Numeri subsetof Integer
    domain Parole subsetof String
    domain Naturali subsetof Natural
    domain Oggetto subsetof Any

    dynamic domain NumeriD subsetof Integer
    //domain TF subsetof Boolean
    //non funziona un sottotipo di boolean, perchè non esiste un insieme ristretto di true/false

    //Enumerativi
    enum domain Color={RED | GREEN | BLUE}
    enum domain Type={LATTE | THE | CAFFE | ACQUA}


    //Domini derivati dalle funzioni, statici

    static dominioS1: Seq(Integer)
    static dominioS2: Seq(NumCard)
    static dominioS3: Seq(Naturali)
    static dominioS4: Seq(Color)

    static dominioS6: Prod(Integer,String)
    static dominioS7: Prod(NumCard,Boolean)
    static dominioS8: Prod(Naturali,NumCard)
    static dominioS9: Prod(Color,String)

    static dominioS10: Prod(String,Integer)
    static dominioS11: Prod(String,Integer,String)
    static dominioS12: Prod(String,Integer,String,Integer)
    static dominioS13: Prod(String,Integer,String,Integer,String)
    static dominioS14: Prod(String,Integer,String,Integer,String,Integer)
    static dominioS15: Prod(String,Integer,String,Integer,String,Integer,String)
    static dominioS16: Prod(String,Integer,String,Integer,String,Integer,String,Integer)
    static dominioS17: Prod(String,Integer,String,Integer,String,Integer,String,Integer,String)
    static dominioS18: Prod(String,Integer,String,Integer,String,Integer,String,Integer,String,Integer)

    static dominioS19: Powerset(Integer)
    static dominioS20: Powerset(NumCard)
    static dominioS21: Powerset(Naturali)
    static dominioS22: Powerset(Color)

    static dominioS23: Bag(Integer)
    static dominioS24: Bag(NumCard)
    static dominioS25: Bag(Naturali)
    static dominioS26: Bag(Color)

    static dominioS27: Map(Integer,String)
    static dominioS28: Map(NumCard,Boolean)
    static dominioS29: Map(Naturali,NumCard)
    static dominioS30: Map(Color,String)

    //Controllo funzionamento dei domini con funzioni controlled

    controlled dominioC1: Seq(Integer)

    controlled dominioC2: Prod(Integer,String)

    controlled dominioC3: Prod(String,Integer,String)

    controlled dominioC4: Powerset(Integer)

    controlled dominioC5: Bag(Integer)

    controlled dominioC6: Map(Integer,String)


    //Controllo funzionamento dei domini con funzioni monitored

    monitored dominioM1: Seq(Integer)

    monitored dominioM2: Prod(Integer,String)

    monitored dominioM3: Prod(String,Integer,String)

    monitored dominioM4: Powerset(Integer)

    monitored dominioM5: Bag(Integer)

    monitored dominioM6: Map(Integer,String)


    //Controllo funzionamento dei domini con funzioni out

    out dominioO1: Seq(Integer)

    out dominioO2: Prod(Integer,String)

    out dominioO3: Prod(String,Integer,String)

    out dominioO4: Powerset(Integer)

    out dominioO5: Bag(Integer)

    out dominioO6: Map(Integer,String)


    //Controllo funzionamento dei domini con funzioni derived

    derived dominioD1: Seq(Integer)

    derived dominioD2: Prod(Integer,String)

    derived dominioD3: Prod(String,Integer,String)

    derived dominioD4: Powerset(Integer)

    derived dominioD5: Bag(Integer)

    derived dominioD6: Map(Integer,String)



	// FUNCTIONS
    //Studio di tutti gli altri tipi di funzione disponibile

    //Statiche

    //Tipo astratto
    //static-> aggiungo al dominio
    static funAbS: NumCard

    //Altri casi creo una variabile
    controlled funAbC: NumCard
    monitored funAbM: NumCard
    out funAbO: NumCard

    //Creo un metodo che restituisce un tipo Astratto senza variabili in ingresso
    derived funAbD: NumCard


    //Studio casi statici

    static funS1: Integer
    static funS2: Color
    static funS3: Oggetto

    static funS4: Integer -> Color
    static funS5: Color -> Oggetto
    static funS6: Oggetto -> NumCard
    static funS7: NumCard -> Integer

    static funS8: Prod(Oggetto,NumCard,Integer,Color) -> Integer

    //Studio casi controlled

    controlled funC1: Integer
    controlled funC2: Color
    controlled funC3: Oggetto
    controlled funC4: NumCard

    controlled funC5: Integer -> Color
    controlled funC6: Color -> Oggetto
    controlled funC7: Oggetto -> NumCard
    controlled funC8: NumCard -> Integer

    controlled funC9: Prod(Oggetto,NumCard,Integer,Color) -> Integer

    //Studio casi monitored

    monitored funM1: Integer
    monitored funM2: Color
    monitored funM3: Oggetto
    monitored funM4: NumCard

    monitored funM5: Integer -> Color
    monitored funM6: Color -> Oggetto
    monitored funM7: Oggetto -> NumCard
    monitored funM8: NumCard -> Integer

    monitored funM9: Prod(Oggetto,NumCard,Integer,Color) -> Integer

    //Studio  casi out

    out funO1: Integer
    out funO2: Color
    out funO3: Oggetto
    out funO4: NumCard

    out funO5: Integer -> Color
    out funO6: Color -> Oggetto
    out funO7: Oggetto -> NumCard
    out funO8: NumCard -> Integer

    out funO9: Prod(Oggetto,NumCard,Integer,Color) -> Integer

    //Studio casi derived

    derived funD1: Integer
    derived funD2: Color
    derived funD3: Oggetto

    derived funD4: Integer -> Color
    derived funD5: Color -> Oggetto
    derived funD6: Oggetto -> NumCard
    derived funD7: NumCard -> Integer

    derived funD8: Prod(Oggetto,NumCard,Integer,Color) -> Integer

    controlled bool: Boolean

definitions:
	// DOMAIN DEFINITIONS


     domain Parole = { "sos", "sas"}


	// FUNCTION DEFINITIONS





	// RULE DEFINITIONS

	macro rule r_prova=
	skip

	rule r_prova2=
	skip

	rule r_prova3($a in Integer)=
	skip

	rule r_prova3($b in Integer, $c in NumCard)=
	skip

	rule r_prova3($d in Integer, $e in NumCard, $f in Color)=
	skip

	rule r_prova3($g in Integer, $h in NumCard, $i in Color, $l in Naturali)=
	skip

	rule r_prova3($m in Numeri, $n in Numeri)=
	skip

    main rule r_Main =

       skip







	// INVARIANTS





	// MAIN RULE




// INITIAL STATE




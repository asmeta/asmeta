asm fraioli

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary

signature:
// Dichiaro il dominio dell'agente Gru
domain Gru subsetof Agent
// Dichiaro il dominio dell'agente Nastro
domain Nastro subsetof Agent
// Dichiaro il dominio dei Bidoni, che mi servir� per poter selezionare il bidone, che conterr� numeri da 1 a 3 in quanto si tratta di 3 bidoni
domain Bidoni subsetof Integer
// Dichiaro il dominio del Versamento, che mi servir� per dare un range alla quantit� di grano versato, che conterr� numeri da 1 a 5
domain Versamento subsetof Integer
// Dichiaro il dominio del contenuto dei bidoni cilindrici, che conterr� numeri da 0 a 12
domain Contenuto subsetof Integer
/* Dichiaro il dominio del prelievo, ovvero il range di quantit� che pu� essere prelevato dal nastro. 
 * Andr� da 0 a 2 poich� in caso di meno di 2 quintali, il nastro ne prelever� meno di 2. Quindi 0 o 1. 
 */
domain Prelievo subsetof Integer

/*Dichiaro una locuzione dinamica e controllata, quindi gestita dalla macchina. 
 * Questa mi servir� per verificare il contenuto dei singoli bidoni. In input accetta in numero appartenente al dominio
 * Bidoni e in output resistuisce un numero appartenente al dominio Contenuto.
 */
dynamic controlled howMany : Bidoni -> Contenuto
/* Creo una locuzione controllata dall'ASM che accetta in input un Agent e in output restituisce un booleano.
 * La utilizzer� per verificare se gli agenti gru o nastro sono in attesa (o meno).
 */
dynamic controlled inAttesa : Agent -> Boolean
// Per comodit� dichiaro una locuzione derivata. Che sar� calcolata appunto, dinamicamente, sulla base di altri valori di altre locuzioni.
derived moreThanMaxContent : Boolean
// Stessa cosa per questa derivata che invece utilizzer� all'interno della CTLSPEC che verificher� la propriet� di Safety
derived overload : Boolean
// Queste invece sono delle locuzioni monitorate che verranno fornite dall'utente (oracolo/ambiente esterno).
// La prima riguarda la scelta relativa al bidone, che andr� da 1 a 3.
monitored bidone : Bidoni
// La seconda � relativa alla quantit� di grano da versare all'interno del sopracitato bidone
monitored versamento : Versamento

// Dichiaro i due agenti e gli assegno il rispettivo dominio
static gru : Gru
static nastro : Nastro

definitions: 
// Qui, come gi� anticipato, assegno i range di valori dei domini sopracitati. I due punti stanno a significare che i numeri presenti tra i due estremi sono compresi all'interno del dominio. Es. 1:3 = 1,2,3
domain Bidoni = {1:3}
domain Versamento = {1:5}
domain Contenuto = {0:12}
domain Prelievo = {0:2}

/* Qui dichiaro la modalit� con cui andr� a calcolare dinamicamente il valore di questa derivata.
 * In questo caso specifico il mio intento � quello di verificare tramite l'espressione booleana dichiarata a destra dell'uguale
 * se a causa del versamento che l'utente sta cercando di fare, il bidone andr� a riempirsi pi� di quanto consentito dalle specifiche (quindi pi� di 10 quintali).
 * In tal caso, quindi in caso di previsto superamento, il versamento verr� annullato. In tutti gli altri casi ovviamente si proceder� con quanto richiesto dall'utente.
 */
function moreThanMaxContent = versamento <= (10 - howMany(bidone))

// Queste espressioni booleane in sequenza(and) mi permettono di verificare che non ci siano mai pi� di 10 quintali per bidone. Ne vedremo l'utilizzo nelle CTLSPEC.
function overload = howMany(1) > 10 or howMany(2)> 10 or howMany(3) > 10

/* Questa � la regola del versamento. Viene utilizzata dalla gru e ha come scopo quello di controllare che con il versamento il bidone non oltrepassi
 * la capienza massima consentita. In caso in cui il versamento sia possibile senza violare questa condizione, procede con l'aggiornamento
 * del contenuto del bidone aggiungendo il quantitativo richiesto dall'utente.
*/
macro rule r_versamento =  
				if(not(moreThanMaxContent)) then 
				howMany(bidone):= howMany(bidone) + versamento
				endif
				
/* Per quanto riguarda il prelievo invece la regola da seguire � semplice: se ci sono almeno due quintali si rimuovono 2 quintali.
 * Se ce n'� uno si svuota il bidone.
 * In caso in cui ce ne fossero zero non si compie nessun azione per quanto riguarda quel bidone (la condizione del forall mi consente di escludere dall'update
 * i bidoni che non hanno contenuto)
 */		
macro rule r_prelievo = 
            forall $b in Bidoni with howMany($b)>0 do 
           		if(howMany($b)=1) then
           			howMany($b):=howMany($b)-1
           		else 
           			howMany($b):=howMany($b)-2
           		endif 
           		
/* Questa � la regola dedicata agli agenti di tipo Gru (in questo caso uno) e si occupa di disciplinarne il comportamento.
 * In caso in cui l'agente non si trovi in stato di attesa, in parallelo eseguir� la regola dedicata al versamento e aggiorner�
 * il suo stato di attesa e quello del nastro, invertendoli.
 */ 		
macro rule r_gru = 
			if(not(inAttesa(self))) then 
				par
				r_versamento[]
				inAttesa(self):=true
				inAttesa(nastro):=false
				endpar
			endif
			
/* Questa � la regola dedicata agli agenti di tipo Nastro (in questo caso uno) e si occupa di disciplinarne il comportamento.
 * In caso in cui l'agente non si trovi in stato di attesa, in parallelo eseguir� la regola dedicata al prelievo e aggiorner�
 * il suo stato di attesa e quello della gru, invertendoli.
 */ 	
macro rule r_nastro = 
			if(not(inAttesa(self))) then 
				par
				r_prelievo[]
				inAttesa(self):=true
				inAttesa(gru):=false
				endpar
			endif

// Propriet� P1: Tutti i cilindri non hanno mai pi� di 10 quintali.
CTLSPEC(ag(not(overload)))
// Propriet� P2: Se attivo il nastro, allora nello stato successivo tutti i cilindri avranno al massimo 8 quintali
CTLSPEC(ag(inAttesa(nastro)=false implies ax(howMany(1) <= 8 and howMany(2) <= 8 and howMany(3) <= 8)))
// Propriet� P3: Se il primo cilindro � vuoto, finch� non si attiva la gru, esso rimane vuoto
CTLSPEC(ag(howMany(1)=0 implies ew(not(howMany(1)>0), inAttesa(gru)=false)))
// Propriet� falsa, di cui il model checker trover� il controesempio: in tutti gli stati successivi la gru sar� in attesa.
CTLSPEC(ag(inAttesa(gru)=true implies ax(inAttesa(gru)=true)))
// Propriet� di safety vera
CTLSPEC(ag(versamento<5))
// Propriet� di liveness vera
CTLSPEC(ag(af(inAttesa(gru)=false)))


// La main rule, in cui eseguo in parallelo i programmi dei due agenti
main rule r_Main = 
			par
			program(nastro)
			program(gru)
			endpar

// Inizializzo le locuzioni controllate nello stato s0
default init s0:

// Tutti i bidoni con 1 quintale al loro interno
function howMany($bidone in Bidoni)=1

// L'agente nastro in attesa e l'agente gru NON in attesa
function inAttesa($agente in Agent)= 
						if($agente=nastro) then
							true 
						else 
							false 
						endif
// Dichiaro i programmi dei singoli agenti					
agent Nastro : r_nastro[]
agent Gru : r_gru[]

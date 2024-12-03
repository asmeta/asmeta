asm fraioli

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary

signature:
// Dichiaro il dominio dell'agente Gru
domain Gru subsetof Agent
// Dichiaro il dominio dell'agente Nastro
domain Nastro subsetof Agent
// Dichiaro il dominio dei Bidoni, che mi servirà per poter selezionare il bidone, che conterrà numeri da 1 a 3 in quanto si tratta di 3 bidoni
domain Bidoni subsetof Integer
// Dichiaro il dominio del Versamento, che mi servirà per dare un range alla quantità di grano versato, che conterrà numeri da 1 a 5
domain Versamento subsetof Integer
// Dichiaro il dominio del contenuto dei bidoni cilindrici, che conterrà numeri da 0 a 12
domain Contenuto subsetof Integer
/* Dichiaro il dominio del prelievo, ovvero il range di quantità che puà essere prelevato dal nastro.
 * Andrà da 0 a 2 poichà in caso di meno di 2 quintali, il nastro ne preleverà meno di 2. Quindi 0 o 1.
 */
domain Prelievo subsetof Integer

/*Dichiaro una locuzione dinamica e controllata, quindi gestita dalla macchina. 
 * Questa mi servirà per verificare il contenuto dei singoli bidoni. In input accetta in numero appartenente al dominio
 * Bidoni e in output resistuisce un numero appartenente al dominio Contenuto.
 */
dynamic controlled howMany : Bidoni -> Contenuto
/* Creo una locuzione controllata dall'ASM che accetta in input un Agent e in output restituisce un booleano.
 * La utilizzerà per verificare se gli agenti gru o nastro sono in attesa (o meno).
 */
dynamic controlled inAttesa : Agent -> Boolean
// Per comodità dichiaro una locuzione derivata. Che sarà calcolata appunto, dinamicamente, sulla base di altri valori di altre locuzioni.
derived moreThanMaxContent : Boolean
// Stessa cosa per questa derivata che invece utilizzerà all'interno della CTLSPEC che verificherà la proprietà di Safety
derived overload : Boolean
// Queste invece sono delle locuzioni monitorate che verranno fornite dall'utente (oracolo/ambiente esterno).
// La prima riguarda la scelta relativa al bidone, che andrà da 1 a 3.
monitored bidone : Bidoni
// La seconda à relativa alla quantità di grano da versare all'interno del sopracitato bidone
monitored versamento : Versamento

// Dichiaro i due agenti e gli assegno il rispettivo dominio
static gru : Gru
static nastro : Nastro

definitions: 
// Qui, come già anticipato, assegno i range di valori dei domini sopracitati. I due punti stanno a significare che i numeri presenti tra i due estremi sono compresi all'interno del dominio. Es. 1:3 = 1,2,3
domain Bidoni = {1:3}
domain Versamento = {1:5}
domain Contenuto = {0:12}
domain Prelievo = {0:2}

/* Qui dichiaro la modalità con cui andrà a calcolare dinamicamente il valore di questa derivata.
 * In questo caso specifico il mio intento à quello di verificare tramite l'espressione booleana dichiarata a destra dell'uguale
 * se a causa del versamento che l'utente sta cercando di fare, il bidone andrà a riempirsi pià di quanto consentito dalle specifiche (quindi pià di 10 quintali).
 * In tal caso, quindi in caso di previsto superamento, il versamento verrà annullato. In tutti gli altri casi ovviamente si procederà con quanto richiesto dall'utente.
 */
function moreThanMaxContent = versamento <= (10 - howMany(bidone))

// Queste espressioni booleane in sequenza(and) mi permettono di verificare che non ci siano mai pià di 10 quintali per bidone. Ne vedremo l'utilizzo nelle CTLSPEC.
function overload = howMany(1) > 10 or howMany(2)> 10 or howMany(3) > 10

/* Questa à la regola del versamento. Viene utilizzata dalla gru e ha come scopo quello di controllare che con il versamento il bidone non oltrepassi
 * la capienza massima consentita. In caso in cui il versamento sia possibile senza violare questa condizione, procede con l'aggiornamento
 * del contenuto del bidone aggiungendo il quantitativo richiesto dall'utente.
*/
macro rule r_versamento =  
				if(not(moreThanMaxContent)) then 
				howMany(bidone):= howMany(bidone) + versamento
				endif
				
/* Per quanto riguarda il prelievo invece la regola da seguire à semplice: se ci sono almeno due quintali si rimuovono 2 quintali.
 * Se ce n'à uno si svuota il bidone.
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
           		
/* Questa à la regola dedicata agli agenti di tipo Gru (in questo caso uno) e si occupa di disciplinarne il comportamento.
 * In caso in cui l'agente non si trovi in stato di attesa, in parallelo eseguirà la regola dedicata al versamento e aggiornerà
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
			
/* Questa à la regola dedicata agli agenti di tipo Nastro (in questo caso uno) e si occupa di disciplinarne il comportamento.
 * In caso in cui l'agente non si trovi in stato di attesa, in parallelo eseguirà la regola dedicata al prelievo e aggiornerà
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

// Proprietà P1: Tutti i cilindri non hanno mai pià di 10 quintali.
CTLSPEC(ag(not(overload)))
// Proprietà P2: Se attivo il nastro, allora nello stato successivo tutti i cilindri avranno al massimo 8 quintali
CTLSPEC(ag(inAttesa(nastro)=false implies ax(howMany(1) <= 8 and howMany(2) <= 8 and howMany(3) <= 8)))
// Proprietà P3: Se il primo cilindro à vuoto, finchà non si attiva la gru, esso rimane vuoto
CTLSPEC(ag(howMany(1)=0 implies ew(not(howMany(1)>0), inAttesa(gru)=false)))
// Proprietà falsa, di cui il model checker troverà il controesempio: in tutti gli stati successivi la gru sarà in attesa.
CTLSPEC(ag(inAttesa(gru)=true implies ax(inAttesa(gru)=true)))
// Proprietà di safety vera
CTLSPEC(ag(versamento<5))
// Proprietà di liveness vera
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

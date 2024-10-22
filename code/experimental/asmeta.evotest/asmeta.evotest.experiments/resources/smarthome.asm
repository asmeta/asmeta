asm smarthome

import StandardLibrary
import CTLlibrary
import LTLlibrary

signature:
	// DOMAINS
	//Domini rappresentanti gli elementi della smarthome e lo stato degli elementi
	domain Luci subsetof Integer
	domain Tapparelle subsetof Integer
	domain LivelloTapparella subsetof Integer
	domain Casualita subsetof Integer
	enum domain Porte = {PRINCIPALE | RETRO | GARAGE}
	enum domain StatoPorta = {APERTA | CHIUSA}
	enum domain StatoLuce = {ACCESA | SOFFUSA | SPENTA}
	
	//Domini per le funzioni monitorate (input dall'environment)
	enum domain Elementi = {CASA | LUCI | TAPPARELLE | PORTE | ANTIFURTO}
	enum domain AzioniCasa = {CHIUDI_TUTTO | APRI_TUTTO}
	enum domain AzioniLuci = {ACCENDI_LUCE | SPEGNI_LUCE | LUCE_SOFFUSA}
	enum domain AzioniTapparelle = {IMPOSTA_TAPPARELLA | APRI_TAPPARELLE | CHIUDI_TAPPARELLE}
	enum domain AzioniPorte = {APRI_PORTA | CHIUDI_PORTA}
	
	// FUNCTIONS
	controlled statoLuce: Luci -> StatoLuce
	controlled statoTapparella: Tapparelle -> LivelloTapparella
	controlled statoPorta: Porte -> StatoPorta
	controlled statoAntifurto: Boolean

	monitored elemento: Elementi
	monitored azioneCasa: AzioniCasa
	monitored azioneLuci: AzioniLuci
	monitored azioneTapparelle: AzioniTapparelle
	monitored azionePorte: AzioniPorte
	monitored luce: Luci
	monitored tapparella: Tapparelle
	monitored porta: Porte
	monitored livello_tapparella: LivelloTapparella

	derived casaChiusa: Boolean //True se la casa ha luci spente, porte e tapparelle chiuse

definitions:
	// DOMAIN DEFINITIONS
	domain Luci = {1 : 5}
	domain Tapparelle = {1 : 3}
	domain LivelloTapparella = {0 : 100} //0% = completamente aperta; 100% = completamente chiusa
	domain Casualita = {1:100} //per modellare la casualità con cui scatta l'antifurto
	
	// FUNCTION DEFINITIONS	
	function casaChiusa = 	(forall $l in Luci with statoLuce($l) = SPENTA) and
							(forall $t in Tapparelle with statoTapparella($t) = 100) and
							(forall $p in Porte with statoPorta($p) = CHIUSA)
	
	// RULE DEFINITIONS
	// Quando scatta l'allarme, chiudi porte e tapparelle e accendi le luci
	rule r_allarme = 
		if (statoAntifurto = true)
		then
			par
				forall $l in Luci do statoLuce($l) := ACCESA
				forall $t in Tapparelle do statoTapparella($t) := 100
				forall $p in Porte do statoPorta($p) := CHIUSA
			endpar
		else skip
		endif
		
	
	// Quando attivo l'antifurto, la porta principale si chiude
	// e non posso disattivarlo se la porta principale è aperta
	rule r_antifurto = 	if (statoAntifurto = true)
						then 	if (statoPorta(PRINCIPALE) = APERTA)
								then statoAntifurto := false
								else skip
								endif
						else 
							par	
								statoAntifurto := true
								statoPorta(PRINCIPALE) := CHIUSA
							endpar
						endif
	
	// Le azioni totali sulla casa aprono (o chiudono) tutte le porte e tapparelle
	// se chiudo tutto attivo anche l'antifurto
	rule r_casa($azione in AzioniCasa) =
		if ($azione = APRI_TUTTO)
		then		
			par
				forall $ta in Tapparelle do statoTapparella($ta) := 0
				forall $pa in Porte do statoPorta($pa) := APERTA
			endpar
		else 
			par
				forall $tc in Tapparelle do statoTapparella($tc) := 100
				forall $pc in Porte do statoPorta($pc) := CHIUSA
				statoAntifurto := true
			endpar
		endif
	
	// Le azioni sulle luci ne modificano solo una (accesa, spenta o soffusa)
	rule r_luci($azione in AzioniLuci) = 
		if ($azione = ACCENDI_LUCE)	
		then statoLuce(luce) := ACCESA
		else 	if ($azione = SPEGNI_LUCE)
				then statoLuce(luce) := SPENTA
				else statoLuce(luce) := SOFFUSA
				endif
		endif
	
	// Le azioni sulle tapparelle le aprono o chiudono tutte
	// o impostano la percentuale di chiusura di una
	rule r_tapparelle($azione in AzioniTapparelle) =
		if ($azione = APRI_TAPPARELLE)	
		then forall $ta in Tapparelle do statoTapparella($ta) := 0
		else 	if ($azione = CHIUDI_TAPPARELLE)
				then forall $tp in Tapparelle do statoTapparella($tp) := 100
				else statoTapparella(tapparella) := livello_tapparella
				endif
		endif
	
	// Le azioni sulle porte ne modificano solo una (aperta o chiusa)
	// Quando apro la porta principale, se nessuna luce è accesa, 
	// tutte le tapparelle chiuse più del 50%, si aprono al 50%
	// La porta si può chiudere solo se l'antifurto è attivo
	rule r_porte($azione in AzioniPorte) =
		if ($azione = APRI_PORTA)	
		then 
			par
				statoPorta(porta) := APERTA
				if (porta = PRINCIPALE and (not (exist $l in Luci with statoLuce($l) = ACCESA)))
				then forall $t in Tapparelle with statoTapparella($t) > 50
											 do statoTapparella($t) := 50
				else skip
				endif
			endpar
		else 	if (porta = PRINCIPALE and statoAntifurto = false)
				then skip
				else statoPorta(porta) := CHIUSA
				endif
		endif
	
	// INVARIANTS
	// Se la casa è completamente chiusa, allora l'antifurto deve essere attivo
	invariant inv_antifurto over statoAntifurto: casaChiusa implies statoAntifurto
	
	// PROPERTIES
	// In qualsiasi momento, è possibile avere una luce accesa in un qualche stato futuro (Liveness)
	CTLSPEC ag (ef ((exist $l in Luci with statoLuce($l) = ACCESA)))
	// Non è mai possibile avere la porta principale chiusa e l'antifurto disattivato (Safety)
	CTLSPEC ag (not (statoPorta(PRINCIPALE) = CHIUSA and not statoAntifurto))
	// Se l'antifurto non è attivo, non è possibile che si accenda più di una luce alla volta (Safety)
	CTLSPEC ag (not statoAntifurto implies (forall $l1 in Luci, $l2 in Luci 
					with ((statoLuce($l1)!=ACCESA and statoLuce($l2)!=ACCESA and $l1 != $l2) 
							implies ax (statoLuce($l1)!=ACCESA or statoLuce($l2)!=ACCESA))))
	// Ogni luce rimane accesa finchè non si opera direttamente sulla luce
	LTLSPEC g ((forall $l in Luci with (statoLuce($l) = ACCESA implies 
				u(statoLuce($l) = ACCESA, elemento = LUCI and luce = $l and azioneLuci != ACCENDI_LUCE) 
				or g(statoLuce($l) = ACCESA))))
	
	// MAIN RULE
	main rule r_Main =
		// Con probabilità 15% si modella il tentativo di furto,
		// altrimenti si modella un'azione dell'utente
		choose $x in Casualita with true
		do 	if ($x <= 15)
			then r_allarme[]
			else 	if (elemento = ANTIFURTO)
					then r_antifurto[]
					else	if (elemento = CASA)
							then r_casa[azioneCasa]
							else 	if (elemento = LUCI)
									then r_luci[azioneLuci]
									else 	if (elemento = TAPPARELLE)
											then r_tapparelle[azioneTapparelle]
											else r_porte[azionePorte]
											endif
									endif
							endif
					endif
			endif				

// INITIAL STATE
default init s0:
	// Luci spente, porte e tapparelle chiuse, antifurto attivo
	function statoLuce($e in Luci) =  SPENTA
	function statoTapparella($t in Tapparelle) =  100
	function statoPorta($p in Porte) =  CHIUSA
	function statoAntifurto = true
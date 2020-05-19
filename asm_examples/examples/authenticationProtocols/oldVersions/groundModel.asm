asm groundModel

//dall'articolo "A Runtime-Based Verification Method for Stepwise Refined Concurrent Programs"

//vecchia versione senza intruder

import ../../../STDL/StandardLibrary

signature:
	domain Actors subsetof Agent
	dynamic abstract domain SignalId
	dynamic abstract domain Signal
	enum domain TypeOfSignal = {CHALLENGE | RESPONSE | COMMIT}
	dynamic controlled signalType: Signal -> TypeOfSignal
	dynamic controlled challengeMsg: Signal -> Prod(Actors, Actors, SignalId)
	dynamic controlled responseMsg: Signal -> Prod(Actors, Actors, SignalId, SignalId)
	dynamic controlled commitMsg: Signal -> Prod(Actors, Actors, SignalId, SignalId)

	dynamic controlled isGood: Actors -> Boolean
	derived isBad: Actors -> Boolean

	controlled output : String

	dynamic monitored wishToInitiate: Actors -> Boolean
	dynamic controlled readSignal: Signal -> Boolean

	static actorA: Actors
	static actorB: Actors

definitions:
	function isBad($a in Actors) =
		not(isGood($a))

	//Challenge(A;B) =
	//	let Na = new SignalId
	//		Send(challengeMsg(A;B;Na ))
	//where
	//	Send(m) = Insert(m; Signal)
	rule r_challenge($a in Actors, $b in Actors) =
		extend SignalId with $na do
			extend Signal with $signal do
				par
					readSignal($signal) := false
					signalType($signal) := CHALLENGE
					challengeMsg($signal) := ($a, $b, $na)
				endpar

	//Response(A;B;Na ) =
	//	if Received(challengeMsg(A;B;Na )) then
	//		let Nb = new SignalId
	//			Send(responseMsg(A;B;Na ;Nb))
	//where
	//	Received(m) = m \in Signal
	rule r_response($a in Actors, $b in Actors, $na in SignalId) =
		if((exist $s in Signal with isDef(challengeMsg($s)) and eq(challengeMsg($s), ($a, $b, $na)))) then
			extend SignalId with $nb do
				extend Signal with $signal do
					par
						readSignal($signal) := false
						signalType($signal) := RESPONSE
						responseMsg($signal) := ($a, $b, $na, $nb)
					endpar
		endif

	//Commit(A;B;Na ;Nb) =
	//	if A, B \in Good and Received(responseMsg(A;B;Na ;Nb)) then
	//		Send(commitMsg(A;B;Na ;Nb))
	rule r_commit($a in Actors, $b in Actors, $na in SignalId, $nb in SignalId) =
		if(isGood($a) and isGood($b) and
			(exist $s in Signal with isDef(responseMsg($s)) and
										eq(responseMsg($s), ($a, $b, $na, $nb)) ) ) then
			extend Signal with $signal do
				par
					readSignal($signal) := false
					signalType($signal) := COMMIT
					commitMsg($signal) := ($a, $b, $na, $nb)
				endpar
		endif

	//IntruderCommit(A;B;Na ;Nb) =
	//	if A \in Bad or B \in Bad then Send(commitMsg(A;B;Na ;Nb))
	rule r_intruderCommit($a in Actors, $b in Actors, $na in SignalId, $nb in SignalId) =
		if(isBad($a) or isBad($b)) then
			extend Signal with $signal do
				commitMsg($signal) := ($a, $b, $na, $nb)
		endif

	rule r_actorRule =
		par
			if(wishToInitiate(self)) then
				choose $b in Actors with $b != self do
					r_challenge[self, $b]
			endif
			forall $s1 in Signal with not(readSignal($s1)) and signalType($s1) = CHALLENGE do
				if(second(challengeMsg($s1)) = self) then
					par
						r_response[first(challengeMsg($s1)), self, third(challengeMsg($s1))]
						readSignal($s1) := true
					endpar
				endif
			forall $s2 in Signal with not(readSignal($s2)) and signalType($s2) = RESPONSE do
				if(first(responseMsg($s2)) = self) then
					par
						r_commit[self, second(responseMsg($s2)), third(responseMsg($s2)), fourth(responseMsg($s2))]
						readSignal($s2) := true
					endpar
				endif
		endpar

	main rule r_Main =
		forall $actor in Actors with true do
			program($actor)

default init s0:
	function isGood($actor in Actors) = true

	agent Actors:
		r_actorRule[]

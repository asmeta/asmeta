asm groundModel

//dall'articolo "A Runtime-Based Verification Method for Stepwise Refined Concurrent Programs"

//non e' necessario che vengano creati due messaggi affinche' possa essere creata una commit dall'intruder

import ../../STDL/StandardLibrary

signature:
	domain Actors subsetof Agent
	dynamic abstract domain SignalId
	dynamic abstract domain Signal
	enum domain TypeOfSignal = {CHALLENGE | RESPONSE | COMMIT}
	dynamic controlled signalType: Signal -> TypeOfSignal
	dynamic controlled challengeMsg: Signal -> Prod(Actors, Actors, SignalId)
	dynamic controlled responseMsg: Signal -> Prod(Actors, Actors, SignalId, SignalId)
	dynamic controlled commitMsg: Signal -> Prod(Actors, Actors, SignalId, SignalId)

	dynamic controlled isGood: Agent -> Boolean
	derived isBad: Agent -> Boolean

	controlled output : String

	dynamic monitored wishToInitiate: Actors -> Boolean
	dynamic controlled readSignal: Signal -> Boolean

	dynamic monitored sendFakeCommit: Actors -> Boolean

	static actorA: Actors
	static actorB: Actors
	static intruder: Actors

definitions:
	function isBad($a in Agent) =
		not(isGood($a))

	//Challenge(A;B) =
	//	let Na = new SignalId
	//		Send(challengeMsg(A;B;Na))
	//where
	//	Send(m) = Insert(m; Signal)
	//rule r_challenge($a in Actors, $b in Actors) =
	rule r_challenge =
		if(wishToInitiate(self)) then
			choose $b in Actors with $b != self do
				extend SignalId with $na do
					extend Signal with $signal do
						par
							readSignal($signal) := false
							signalType($signal) := CHALLENGE
							challengeMsg($signal) := (self, $b, $na)
						endpar
		endif

	//Response(A;B;Na ) =
	//	if Received(challengeMsg(A; B; Na)) then
	//		let Nb = new SignalId
	//			Send(responseMsg(A; B; Na ;Nb))
	//where
	//	Received(m) = m \in Signal
	//rule r_response($a in Actors, $b in Actors, $na in SignalId) =
	rule r_response =
		forall $s in Signal with not(readSignal($s)) and signalType($s) = CHALLENGE do
			if(second(challengeMsg($s)) = self) then
				par
					let($dest = first(challengeMsg($s)), $na = third(challengeMsg($s))) in
						extend SignalId with $nb do
							extend Signal with $signal do
								par
									readSignal($signal) := false
									signalType($signal) := RESPONSE
									responseMsg($signal) := (self, $dest, $na, $nb)
								endpar
					endlet
					readSignal($s) := true
				endpar
			endif

	//Commit(A;B;Na ;Nb) =
	//	if A, B \in Good and Received(responseMsg(A;B;Na ;Nb)) then
	//		Send(commitMsg(A;B;Na ;Nb))
	//rule r_commit($a in Actors, $b in Actors, $na in SignalId, $nb in SignalId) =
	rule r_commit =
		forall $s in Signal with not(readSignal($s)) and signalType($s) = RESPONSE do
			if(second(responseMsg($s)) = self) then
				if(isGood(first(responseMsg($s)))) then
					par
						let($dest = first(responseMsg($s)), $na=third(responseMsg($s)), $nb=fourth(responseMsg($s))) in
							extend Signal with $signal do
								par
									readSignal($signal) := false
									signalType($signal) := COMMIT
									commitMsg($signal) := (self, $dest, $na, $nb)
								endpar
						endlet
						readSignal($s) := true
					endpar
				endif
			endif

	//IntruderCommit(A;B;Na ;Nb) =
	//	if A \in Bad or B \in Bad then Send(commitMsg(A;B;Na ;Nb))
	//rule r_intruderCommit($a in Actors, $b in Actors, $na in SignalId, $nb in SignalId) =
	rule r_intruderCommit =
		choose $b in Agent with $b!=self and (isBad(self) or isBad($b)) do
			//choose $na in SignalId, $nb in SignalId with $na != $nb do
			extend SignalId with $na, $nb do
				extend Signal with $signal do
					par
						commitMsg($signal) := (self, $b, $na, $nb)
						signalType($signal) := COMMIT
						readSignal($signal) := false
					endpar

	rule r_goodActorRule =
		par
			r_challenge[]
			r_response[]
			r_commit[]
		endpar

	rule r_badActorRule =
		par
			r_challenge[]
			r_response[]
			if(sendFakeCommit(self)) then
				r_intruderCommit[]
			endif
		endpar

	rule r_actorRule =
		if(isGood(self)) then
			r_goodActorRule[]
		else
			r_badActorRule[]
		endif

	main rule r_Main =
		forall $actor in Actors with true do
			program($actor)

default init s0:
	function isGood($actor in Agent) = at({actorA -> true, actorB -> true, intruder -> false}, $actor)

	agent Actors:
		r_actorRule[]

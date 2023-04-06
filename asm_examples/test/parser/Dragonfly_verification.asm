asm Dragonfly_verification

import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary

signature:
	domain Initiator subsetof Agent
	domain Receiver subsetof Agent
	domain Intruder subsetof Agent
	
	
	/*------------------------------------------------------------------- */
	//               Custom domain for DragonFly
	/*------------------------------------------------------------------- */
	 
	/*------------------------------------------------------------------- */
	//                    CryptoLibrary domain
	/*------------------------------------------------------------------- */
	enum domain StateInit = { INIT_A | WAIT_COMMITB | WAIT_CONFIRMB | END_A } 
	
	enum domain StateRec = { WAIT_COMMITA | WAIT_CONFIRMA | END_B}
	
	enum domain Message = { CONFIRM_A |  CONFIRM_B  | COMMIT_A | COMMIT_B |EMPTY}
	
	
	enum domain KnowledgeBitStringExponent={MA | RA | SA | MB | RB | SB | MEA | REA | SEA 
	   | MEB | REB | SEB |SERR| OPP_MA | OPP_MB | OPP_MEA | OPP_MEB}
	enum domain KnowledgeBitStringBase={PE}
	enum domain KnowledgeBitStringResult={PE_OPP_MA | PE_OPP_MB | PE_OPP_MEA | PE_OPP_MEB | PE_SA | PE_SB 
	   | PE_RA | PE_RB | PE_SEA | PE_SEB | PE_REA | PE_REB |PE_ERR
	   | PE_RA_RB | PE_REA_RB | PE_REB_RA}				   
	
	//Attacker Mode
	enum domain Modality = {ACTIVE | PASSIVE} 					
	
	
	controlled initiatorState: Initiator -> StateInit
	controlled receiverState: Receiver -> StateRec
	
	controlled protocolMessage: Prod(Initiator,Intruder)-> Message
	controlled protocolMessage: Prod(Intruder,Initiator)-> Message
	controlled protocolMessage: Prod(Receiver,Intruder)-> Message
	controlled protocolMessage: Prod(Intruder,Receiver)-> Message
	
	monitored chosenMode: Modality
	controlled mode: Modality
	
	//controlled messageField: Prod(Agent,Agent,FieldPosition,Message)->Knowledge
	//controlled messageField: Prod(Agent,Agent,FieldPosition,Message)->Knowledge
	/* 
	controlled messageField_nodeA_nodeE_1_COMMIT_A:KnowledgeBitStringExponent
	controlled messageField_nodeE_nodeB_1_COMMIT_A:KnowledgeBitStringExponent
	controlled messageField_nodeB_nodeE_1_COMMIT_B:KnowledgeBitStringExponent
	controlled messageField_nodeE_nodeA_1_COMMIT_B:KnowledgeBitStringExponent
	
	controlled messageField_nodeA_nodeE_2_COMMIT_A:KnowledgeBitStringResult
	controlled messageField_nodeE_nodeB_2_COMMIT_A:KnowledgeBitStringResult
	controlled messageField_nodeB_nodeE_2_COMMIT_B:KnowledgeBitStringResult
	controlled messageField_nodeE_nodeA_2_COMMIT_B:KnowledgeBitStringResult
	
	controlled messageField_nodeA_nodeE_1_CONFIRM_A:KnowledgeBitStringResult
	controlled messageField_nodeE_nodeB_1_CONFIRM_A:KnowledgeBitStringResult
	controlled messageField_nodeB_nodeE_1_CONFIRM_B:KnowledgeBitStringResult
	controlled messageField_nodeE_nodeA_1_CONFIRM_B:KnowledgeBitStringResult
	
	controlled messageField_nodeA_nodeE_2_CONFIRM_A:KnowledgeBitStringExponent
	controlled messageField_nodeE_nodeB_2_CONFIRM_A:KnowledgeBitStringExponent
	controlled messageField_nodeB_nodeE_2_CONFIRM_B:KnowledgeBitStringExponent
	controlled messageField_nodeE_nodeA_2_CONFIRM_B:KnowledgeBitStringExponent
	
	controlled messageField_nodeA_nodeE_3_CONFIRM_A:KnowledgeBitStringResult
	controlled messageField_nodeE_nodeB_3_CONFIRM_A:KnowledgeBitStringResult
	controlled messageField_nodeB_nodeE_3_CONFIRM_B:KnowledgeBitStringResult
	controlled messageField_nodeE_nodeA_3_CONFIRM_B:KnowledgeBitStringResult
	
	controlled messageField_nodeA_nodeE_4_CONFIRM_A:KnowledgeBitStringExponent
	controlled messageField_nodeE_nodeB_4_CONFIRM_A:KnowledgeBitStringExponent
	controlled messageField_nodeB_nodeE_4_CONFIRM_B:KnowledgeBitStringExponent
	controlled messageField_nodeE_nodeA_4_CONFIRM_B:KnowledgeBitStringExponent
	
	controlled messageField_nodeA_nodeE_5_CONFIRM_A:KnowledgeBitStringResult
	controlled messageField_nodeE_nodeB_5_CONFIRM_A:KnowledgeBitStringResult
	controlled messageField_nodeB_nodeE_5_CONFIRM_B:KnowledgeBitStringResult
	controlled messageField_nodeE_nodeA_5_CONFIRM_B:KnowledgeBitStringResult
	*/
	
	//static sumMod:Prod(KnowledgeBitStringExponent,KnowledgeBitStringExponent)->KnowledgeBitStringExponent
	
	//static exp:Prod(KnowledgeBitStringBase,KnowledgeBitStringExponent)->KnowledgeBitStringResult
	
	//static prodExp:Prod(KnowledgeBitStringResult,KnowledgeBitStringResult)->KnowledgeBitStringResult
	//static diffieHellman:Prod(KnowledgeAsymPubKey,KnowledgeAsymPrivKey)->KnowledgeSymKey
	/*------------------------------------------------------------------- */
	//            Knowledge  management of the principals 
	/*------------------------------------------------------------------- */
	/* 
	controlled knowsBitStringExponent:Prod(Initiator,KnowledgeBitStringExponent)->Boolean
	controlled knowsBitStringExponent:Prod(Receiver,KnowledgeBitStringExponent)->Boolean
	controlled knowsBitStringExponent:Prod(Intruder,KnowledgeBitStringExponent)->Boolean
	//controlled knowsSymKey:Prod(Agent,KnowledgeSymKey)->Boolean
	controlled knowsBitStringBase:Prod(Initiator,KnowledgeBitStringBase)->Boolean
	controlled knowsBitStringBase:Prod(Receiver,KnowledgeBitStringBase)->Boolean
	controlled knowsBitStringBase:Prod(Intruder,KnowledgeBitStringBase)->Boolean
	//controlled knowsAsymPubKey:Prod(Agent,KnowledgeAsymPubKey)->Boolean
	controlled knowsBitStringResult:Prod(Initiator,KnowledgeBitStringResult)->Boolean
	controlled knowsBitStringResult:Prod(Receiver,KnowledgeBitStringResult)->Boolean
	controlled knowsBitStringResult:Prod(Intruder,KnowledgeBitStringResult)->Boolean
	* 
	*/
	//controlled knowsAsymPrivKey:Prod(Agent,KnowledgeAsymPrivKey)->Boolean
	
	
	
	//static readKnowledge:Prod(Initiator,Knowledge)->Knowledge
	//static readKnowledge:Prod(Receiver,Knowledge)->Knowledge
	//static readKnowledge:Prod(Intruder,Knowledge)->Knowledge
	/*------------------------------------------------------------------- */
	//               Z-wave protocol specific function
	/*------------------------------------------------------------------- */
	
	
		
	static nodeA: Initiator
	static nodeB: Receiver
	static nodeE: Intruder
	

definitions:

	
/*	 	
	
	function sumMod($x in KnowledgeBitStringExponent,$y in KnowledgeBitStringExponent)=
		if(($x=RA and $y=MA) or ($y=RA and $x=MA) )then
			SA
		else
			if(($x=RB and $y=MB) or ($y=RB and $x=MB))then
				SB
			else
				if(($x=REB and $y=MEB)or($y=REB and $x=MEB))then
					SEB
				else
					if(($x=REA and $y=MEA)or($y=REA and $x=MEA))then
						SEA
					else
						SERR
					endif
				endif
			endif
		endif
		
	function exp($b in KnowledgeBitStringBase,$e in KnowledgeBitStringExponent)=
		if($b=PE and $e=OPP_MA)then
			PE_OPP_MA
		else
			if($b=PE and $e=OPP_MB)then
				PE_OPP_MB
			else
				if($b=PE and $e=SA)then
					PE_SA
				else
					if($b=PE and $e=SB)then
						PE_SB
					else
						if($b=PE and $e=OPP_MEA)then
							PE_OPP_MEA
						else
							if($b=PE and $e=OPP_MEB)then
								PE_OPP_MEA
							else
								if($b=PE and $e=SEA)then
									PE_SEA
								else
									if($b=PE and $e=SEB)then
										PE_SEB
									else
										if($b=PE and $e=RB)then
											PE_RB
										else
											if($b=PE and $e=RA)then
												PE_RA
											else
												if($b=PE and $e=REA)then
													PE_REA
												else
													if($b=PE and $e=REB)then
														PE_REB
													else
														PE_ERR
													endif
												
												endif
											endif
										endif
										
									endif
								endif
							endif
						endif
					endif
				endif
			endif
		endif
	
	function prodExp($x in KnowledgeBitStringResult,$y in KnowledgeBitStringResult)=
		if(($x=PE_OPP_MB and $y=PE_SB)or($y=PE_OPP_MB and $x=PE_SB))then
			PE_RB
		else
			if(($x=PE_OPP_MA and $y=PE_SA) or ($y=PE_OPP_MA and $x=PE_SA))then
				PE_RA
			else
				if(($x=PE_OPP_MEA and $y=PE_SEA) or ($y=PE_OPP_MEA and $x=PE_SEA))then
					PE_REA
				else
					if(($x=PE_OPP_MEB and $y=PE_SEB) or ($y=PE_OPP_MEB and $x=PE_SEB))then
						PE_REB
					else
						if(($x=PE_RB and $y=PE_RA)or($y=PE_RB and $x=PE_RA))then
							PE_RA_RB
						else
							if(($x=PE_REA and $y=PE_RB)or($y=PE_REA and $x=PE_RB))then
								PE_REA_RB
							else
								if(($x=PE_REB and $y=PE_RA)or($y=PE_REB and $x=PE_RA))then
									PE_REB_RA
								else
								 	PE_ERR
								endif
							endif
						endif
						
					endif
				
				endif
			endif
		endif
		
	
	

	rule r_commitA =
		if(initiatorState(self) = INIT_A)then
			let($sa =sumMod(MA,RA),$pe_opp=exp(PE,OPP_MA),$e=nodeE) in
				par
					protocolMessage(self,$e):=COMMIT_A
					messageField_nodeA_nodeE_1_COMMIT_A:= $sa
					messageField_nodeA_nodeE_2_COMMIT_A:= $pe_opp
					initiatorState(self) := WAIT_COMMITB
				endpar
			endlet			
		endif

	rule r_commitB =
		let($e=nodeE)in
			if(receiverState(self) = WAIT_COMMITA and protocolMessage($e,self)=COMMIT_A)then
				let($sb =sumMod(MB,RB),$pe_opp=exp(PE,OPP_MB)) in
					par
						knowsBitStringExponent(self,messageField_nodeE_nodeB_1_COMMIT_A):=true
						knowsBitStringResult(self,messageField_nodeE_nodeB_2_COMMIT_A):=true
						protocolMessage(self,$e):=COMMIT_B
						messageField_nodeB_nodeE_1_COMMIT_B:= $sb
						messageField_nodeB_nodeE_2_COMMIT_B:= $pe_opp
						receiverState(self):=WAIT_CONFIRMA
					endpar
				endlet			
			endif
		endlet
 		
	
	rule r_confirmA=
		let($e=nodeE)in
			if(initiatorState(self) = WAIT_COMMITB and protocolMessage($e, self)=COMMIT_B)then
				let ($pesb=exp(PE,messageField_nodeE_nodeA_1_COMMIT_B))in
					let ($perb=prodExp($pesb,messageField_nodeE_nodeA_2_COMMIT_B),$pera=exp(PE,RA))in
						let ($ssab=prodExp($perb,$pera))in
							par
								knowsBitStringExponent(self,messageField_nodeE_nodeA_1_COMMIT_B):=true
								knowsBitStringResult(self,messageField_nodeE_nodeA_2_COMMIT_B):=true
								protocolMessage(self,$e):=CONFIRM_A
								messageField_nodeA_nodeE_1_CONFIRM_A:= $ssab
								messageField_nodeA_nodeE_2_CONFIRM_A:= messageField_nodeA_nodeE_1_COMMIT_A
								messageField_nodeA_nodeE_3_CONFIRM_A:= messageField_nodeA_nodeE_2_COMMIT_A
								messageField_nodeA_nodeE_4_CONFIRM_A:= messageField_nodeE_nodeA_1_COMMIT_B
								messageField_nodeA_nodeE_5_CONFIRM_A:= messageField_nodeE_nodeA_2_COMMIT_B
								initiatorState(self):=WAIT_CONFIRMB
								//hash(CONFIRM_A,1,1,4):=HASH_CONFIRMA
							endpar
						endlet
					endlet
					
				endlet
			endif
		endlet
		
	rule r_confirmB=
		let($e=nodeE)in
			if(receiverState(self) = WAIT_CONFIRMA and protocolMessage($e, self)=CONFIRM_A)then
				let ($pesa=exp(PE,messageField_nodeE_nodeB_1_COMMIT_A))in
					let ($pera=prodExp($pesa,messageField_nodeE_nodeB_2_COMMIT_A),$perb=exp(PE,RB))in
						let ($ssba=prodExp($perb,$pera),$sa=messageField_nodeE_nodeB_1_COMMIT_A, $sb=messageField_nodeB_nodeE_1_COMMIT_B
							,$ea=messageField_nodeE_nodeB_2_COMMIT_A,$eb=messageField_nodeB_nodeE_2_COMMIT_B)in
							if($ssba=messageField_nodeE_nodeB_1_CONFIRM_A and $sa=messageField_nodeE_nodeB_2_CONFIRM_A and
								$ea=messageField_nodeE_nodeB_3_CONFIRM_A and $sb=messageField_nodeE_nodeB_4_CONFIRM_A and
								$eb=messageField_nodeE_nodeB_5_CONFIRM_A)then
								par
									protocolMessage(self,$e):=CONFIRM_B
									messageField_nodeB_nodeE_1_CONFIRM_B:= $ssba
									messageField_nodeB_nodeE_2_CONFIRM_B:= $sa
									messageField_nodeB_nodeE_3_CONFIRM_B:= $ea
									messageField_nodeB_nodeE_4_CONFIRM_B:= $sb
									messageField_nodeB_nodeE_5_CONFIRM_B:= $eb
									receiverState(self):=END_B
									//hash(CONFIRM_A,1,1,4):=HASH_CONFIRMB
								endpar
							endif
						endlet
					endlet
					
				endlet
			endif
		endlet
		
	rule r_endA=
		let($e=nodeE)in
			if(initiatorState(self) = WAIT_CONFIRMB and protocolMessage($e, self)=CONFIRM_B)then
				let ($pesb=exp(PE,messageField_nodeE_nodeA_1_COMMIT_B))in
					let ($perb=prodExp($pesb,messageField_nodeE_nodeA_2_COMMIT_B),$pera=exp(PE,RA))in
						let ($ssab=prodExp($perb,$pera),$sa=messageField_nodeA_nodeE_1_COMMIT_A, $sb=messageField_nodeE_nodeA_1_COMMIT_B
							,$ea=messageField_nodeA_nodeE_2_COMMIT_A,$eb=messageField_nodeE_nodeA_2_COMMIT_B)in
							if($ssab=messageField_nodeE_nodeA_1_CONFIRM_B and $sa=messageField_nodeE_nodeA_2_CONFIRM_B and
								$ea=messageField_nodeE_nodeA_3_CONFIRM_B and $sb=messageField_nodeE_nodeA_4_CONFIRM_B and
								$eb=messageField_nodeE_nodeA_5_CONFIRM_B)then

									initiatorState(self):=END_A
								
							endif
						endlet
					endlet
				endlet
			endif
		endlet

	// fare si che l'attacante  invi un SA a caso e se non conosce PE il PE ricevuto
	rule r_sendCommitA=
		let ($a=nodeA, $b=nodeB) in
			if(protocolMessage($a, self)=COMMIT_A and protocolMessage(self, $b)!=COMMIT_A)then
				choose $sa1 in KnowledgeBitStringExponent with ($sa1=SA or $sa1=SEA) do 
					if(knowsBitStringBase(self,PE)=true)then
						let ($peop1=exp(PE,OPP_MEA)) in
							par
								protocolMessage(self,$b):=COMMIT_A
								messageField_nodeE_nodeB_1_COMMIT_A:= $sa1
								messageField_nodeE_nodeB_2_COMMIT_A:= $peop1
							endpar
						endlet
					else
						par
							protocolMessage(self,$b):=COMMIT_A
							messageField_nodeE_nodeB_1_COMMIT_A:= $sa1
							messageField_nodeE_nodeB_2_COMMIT_A:= messageField_nodeA_nodeE_2_COMMIT_A
						endpar
					endif
			endif	
		endlet
		
	rule r_sendCommitB=
		let ($a=nodeA, $b=nodeB) in
			if(protocolMessage($b, self)=COMMIT_B and protocolMessage(self, $a)!=COMMIT_B)then
				choose $sb1 in KnowledgeBitStringExponent with ($sb1=SB or $sb1=SEB) do 
					if(knowsBitStringBase(self,PE)=true)then
						let ($peop1=exp(PE,OPP_MEB)) in
							par
								protocolMessage(self,$a):=COMMIT_B
								messageField_nodeE_nodeA_1_COMMIT_B:= $sb1
								messageField_nodeE_nodeA_2_COMMIT_B:= $peop1
							endpar
						endlet
					else
						par
							protocolMessage(self,$a):=COMMIT_B
							messageField_nodeE_nodeA_1_COMMIT_B:= $sb1
							messageField_nodeE_nodeA_2_COMMIT_B:= messageField_nodeB_nodeE_2_COMMIT_B
						endpar
					endif
			endif	
		endlet
		
	rule r_sendConfirmA=
		let ($a=nodeA, $b=nodeB) in
			if(protocolMessage($a, self)=CONFIRM_A and protocolMessage(self, $b)!=CONFIRM_A)then
				if(knowsBitStringBase(self,PE)=true)then
					choose $pe_oppa in KnowledgeBitStringResult with ($pe_oppa=PE_OPP_MEA or $pe_oppa=messageField_nodeA_nodeE_2_COMMIT_A) do
						let ($pesb=exp(PE,messageField_nodeB_nodeE_1_COMMIT_B))in
							let ($perb=prodExp($pesb,messageField_nodeB_nodeE_2_COMMIT_B),$perea=exp(PE,REA)) in
								let($sseb=prodExp($perb,$perea))in
									par
										protocolMessage(self,$b):=CONFIRM_A
										messageField_nodeE_nodeB_1_CONFIRM_A:= $sseb
										messageField_nodeE_nodeB_2_CONFIRM_A:= messageField_nodeE_nodeB_1_COMMIT_A
										messageField_nodeE_nodeB_3_CONFIRM_A:= messageField_nodeE_nodeB_2_COMMIT_A
										messageField_nodeE_nodeB_4_CONFIRM_A:= messageField_nodeB_nodeE_1_COMMIT_B
										messageField_nodeE_nodeB_5_CONFIRM_A:= messageField_nodeB_nodeE_2_COMMIT_B
										
									endpar
								endlet
							endlet
						endlet
				else
					par
						protocolMessage(self,$b):=CONFIRM_A
						messageField_nodeE_nodeB_1_CONFIRM_A:= messageField_nodeA_nodeE_1_CONFIRM_A
						messageField_nodeE_nodeB_2_CONFIRM_A:= messageField_nodeA_nodeE_2_CONFIRM_A
						messageField_nodeE_nodeB_3_CONFIRM_A:= messageField_nodeA_nodeE_3_CONFIRM_A
						messageField_nodeE_nodeB_4_CONFIRM_A:= messageField_nodeA_nodeE_4_CONFIRM_A
						messageField_nodeE_nodeB_5_CONFIRM_A:= messageField_nodeA_nodeE_5_CONFIRM_A
						
					endpar
				
				endif
			endif
		endlet
		
	rule r_sendConfirmB=
		let ($a=nodeA, $b=nodeB) in
			if(protocolMessage($b, self)=CONFIRM_B and protocolMessage(self, $a)!=CONFIRM_B)then
				if(knowsBitStringBase(self,PE)=true)then
					choose $pe_oppb in KnowledgeBitStringResult with ($pe_oppb=PE_OPP_MEB or $pe_oppb=messageField_nodeB_nodeE_2_COMMIT_B) do
						let ($pesa=exp(PE,messageField_nodeA_nodeE_1_COMMIT_A))in
							let ($pera=prodExp($pesa,messageField_nodeA_nodeE_2_COMMIT_A),$pereb=exp(PE,REB)) in
								let($ssea=prodExp($pera,$pereb))in
									par
										protocolMessage(self,$a):=CONFIRM_B
										messageField_nodeE_nodeA_1_CONFIRM_B:= $ssea
										messageField_nodeE_nodeA_2_CONFIRM_B:= messageField_nodeA_nodeE_1_COMMIT_A
										messageField_nodeE_nodeA_3_CONFIRM_B:= messageField_nodeA_nodeE_2_COMMIT_A
										messageField_nodeE_nodeA_4_CONFIRM_B:= messageField_nodeE_nodeA_1_COMMIT_B
										messageField_nodeE_nodeA_5_CONFIRM_B:= messageField_nodeE_nodeA_2_COMMIT_B
										
									endpar
								endlet
							endlet
						endlet
				else
					par
						protocolMessage(self,$a):=CONFIRM_B
						messageField_nodeE_nodeA_1_CONFIRM_B:= messageField_nodeB_nodeE_1_CONFIRM_B
						messageField_nodeE_nodeA_2_CONFIRM_B:= messageField_nodeB_nodeE_2_CONFIRM_B
						messageField_nodeE_nodeA_3_CONFIRM_B:= messageField_nodeB_nodeE_3_CONFIRM_B
						messageField_nodeE_nodeA_4_CONFIRM_B:= messageField_nodeB_nodeE_4_CONFIRM_B
						messageField_nodeE_nodeA_5_CONFIRM_B:= messageField_nodeB_nodeE_5_CONFIRM_B
						
					endpar
				
				endif
			endif
		endlet
		
	rule r_replayCommitA=
		let ($a=nodeA, $b=nodeB) in
			if(protocolMessage($a, self)=COMMIT_A and protocolMessage(self, $b)!=COMMIT_A)then
				par
					protocolMessage(self,$b):=COMMIT_A
					messageField_nodeE_nodeB_1_COMMIT_A:= messageField_nodeA_nodeE_1_COMMIT_A
					messageField_nodeE_nodeB_2_COMMIT_A:= messageField_nodeA_nodeE_2_COMMIT_A
				endpar
			endif	
		endlet
	
	rule r_replayCommitB=
		let ($a=nodeA, $b=nodeB) in
			if(protocolMessage($b, self)=COMMIT_B and protocolMessage(self, $a)!=COMMIT_B)then
				
				par
					protocolMessage(self,$a):=COMMIT_B
					messageField_nodeE_nodeA_1_COMMIT_B:= messageField_nodeB_nodeE_1_COMMIT_B
					messageField_nodeE_nodeA_2_COMMIT_B:= messageField_nodeB_nodeE_2_COMMIT_B
				endpar
			endif
		endlet
		
	rule r_replayConfirmA=
		let ($a=nodeA, $b=nodeB) in
			if(protocolMessage($a, self)=CONFIRM_A and protocolMessage(self, $b)!=CONFIRM_A)then
				par
					protocolMessage(self,$b):=CONFIRM_A
					
					messageField_nodeE_nodeB_1_CONFIRM_A:= messageField_nodeA_nodeE_1_CONFIRM_A
					messageField_nodeE_nodeB_2_CONFIRM_A:= messageField_nodeA_nodeE_2_CONFIRM_A
					messageField_nodeE_nodeB_3_CONFIRM_A:= messageField_nodeA_nodeE_3_CONFIRM_A
					messageField_nodeE_nodeB_4_CONFIRM_A:= messageField_nodeA_nodeE_4_CONFIRM_A
					messageField_nodeE_nodeB_5_CONFIRM_A:= messageField_nodeA_nodeE_5_CONFIRM_A
					
				endpar
				
			endif
		endlet
		
	rule r_replayConfirmB=
		let ($a=nodeA, $b=nodeB) in
			if(protocolMessage($b, self)=CONFIRM_B and protocolMessage(self, $a)!=CONFIRM_B)then
				par
					protocolMessage(self,$a):=CONFIRM_B
					
					messageField_nodeE_nodeA_1_CONFIRM_B:= messageField_nodeB_nodeE_1_CONFIRM_B
					messageField_nodeE_nodeA_2_CONFIRM_B:= messageField_nodeB_nodeE_2_CONFIRM_B
					messageField_nodeE_nodeA_3_CONFIRM_B:= messageField_nodeB_nodeE_3_CONFIRM_B
					messageField_nodeE_nodeA_4_CONFIRM_B:= messageField_nodeB_nodeE_4_CONFIRM_B
					messageField_nodeE_nodeA_5_CONFIRM_B:= messageField_nodeB_nodeE_5_CONFIRM_B
					
				endpar
			endif
		endlet
*/
	rule r_intiatorRule =
		skip
		//par
			//r_commitA[]
			//r_confirmA[]
			//r_endA[]
			
		//endpar
	
	
	rule r_receiverRule =
		skip
		//par
			//r_commitB[]
			//r_confirmB[]
						
		//endpar
		

	rule r_mitmRule =
	
		if(mode=ACTIVE)then
			skip/*par
				r_sendCommitA[]
				r_sendCommitB[]
				r_sendConfirmA[]
				r_sendConfirmB[]			
			endpar*/
		else
			skip/*par
				r_replayCommitA[]
				r_replayCommitB[]
				r_replayConfirmA[]
				r_replayConfirmB[]
			endpar*/
		endif
		

//CTLSPEC not(ef(initiatorState(nodeA)=END_A and receiverState(nodeB)=END_B))	

		
main rule r_Main =
		par
			program(nodeA)
			program(nodeE)
			program(nodeB)		
		endpar	
		
default init s0:
	//function receiverState($r in Receiver) = if($r = nodeB )then  WAIT_COMMITA endif
	//function initiatorState($i in Initiator) = if($i = nodeA )then INIT_A endif

	
	//function knowsBitStringExponent($a in Initiator, $b in KnowledgeBitStringExponent)=if($a = nodeA and ($b=RA or $b=MA or $b=OPP_MA or $b=PE)) then true else false endif
	//function knowsBitStringExponent($a in Receiver, $b in KnowledgeBitStringExponent)=if($a = nodeB and ($b=RB or $b=MB or $b=OPP_MB or $b=PE))then true else false endif
	//function knowsBitStringExponent($a in Intruder, $b in KnowledgeBitStringExponent)=if($a = nodeE and ($b=REA or $b=MEA or $b=OPP_MEA or $b=REB or $b=MEB or $b=OPP_MEB))then true else false endif
	
	//function  protocolMessage($i in Initiator,$e in Intruder)= EMPTY
	//function  protocolMessage($e in Intruder, $i in Initiator)= EMPTY
	//function  protocolMessage($r in Receiver,$e in Intruder)= EMPTY
	//function  protocolMessage($e in Intruder,$r in Receiver)= EMPTY
	
	function mode=chosenMode
	
	
	agent Initiator:		
			r_intiatorRule[]
			
	agent Intruder:		
			r_mitmRule[]
				
	agent Receiver:
			r_receiverRule[]
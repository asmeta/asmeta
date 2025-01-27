/** FLASH Cache Coherence Protocol dalla tesi di George Ma */
//modificato per poter essere tradotto in NuSMV
//aggiunte funzioni per gestire gli undef
//modificate le locazioni con funzione come argomento
//N: number of nodes
//L: numers of lines
//Q: the size of the message queue

//N=2, L=2, Q=1
asm flashProtocol_N2_L2_Q1_C
import ../../STDL/StandardLibrary
import ../../STDL/LTLLibrary
import ../../STDL/CTLLibrary

signature:
	enum domain MsgType = {NO_MESS | GET_MSG | GETX_MSG | INV_MSG | WB_MSG |
							RPL_MSG | FWD_ACK_MSG | SWB_MSG | INV_ACK_MSG |
							NACK_MSG | NACKC_MSG | FWD_GET_MSG | FWD_GETX_MSG |
							PUT_MSG | PUTX_MSG | NACK_C2_MSG | PUT_SWB_MSG |
							PUTX_FWD_ACK_MSG}
	enum domain CcType = { CCGET| CCGETX| CCRPL| CCWB }
	domain Line subsetof Integer /* enum domain Line = {l1} */
	enum domain Phase = {READY | WAIT | INVALID_PHASE}
	enum domain State = { EXCLUSIVE | SHARED | INVALID}
	domain AgentDomain subsetof Agent
	domain AgentDomainEnv subsetof Agent

	controlled messInTr: AgentDomain -> MsgType
	controlled senderInTr: AgentDomain -> AgentDomain
	controlled sourceInTr: AgentDomain -> AgentDomain
	controlled senderInTrR: AgentDomain -> AgentDomain
	controlled sourceInTrR: AgentDomain -> AgentDomain
	controlled messInTrR: AgentDomain -> MsgType
	controlled inSender: AgentDomain -> AgentDomain
	controlled inSource: AgentDomain -> AgentDomain
	controlled inMess: AgentDomain -> MsgType
	controlled curPhase: Prod(AgentDomain, Line) -> Phase
	controlled ccState: Prod(AgentDomain, Line) -> State
	controlled pending: Line -> Boolean
	controlled owner: Line -> AgentDomain
	controlled ownerIsUndef: Line -> Boolean //aggiunto
	controlled sharer: Prod(Line, AgentDomain) -> Boolean

	monitored produceCCType : AgentDomain -> CcType

	static home : Line -> AgentDomain
	static lineInTr: AgentDomain -> Line
	derived lineInTrR : AgentDomain -> Line
	derived inLine : AgentDomain -> Line

	static l1: Line
	static l2: Line
	static a1: AgentDomain
	static a2: AgentDomain
	static ee: AgentDomainEnv

definitions:
	domain Line = {1, 2}
	domain AgentDomain = {a1, a2}
	domain AgentDomainEnv = {ee}
	function a1 = a1
	function a2 = a2
	function ee = ee
	function l1 = 1
	function l2 = 2
	function home($l in Line) = at({1 -> a1, 2 -> a2}, $l)
	function inLine($a in AgentDomain) = at({a1 -> l1, a2 -> l1}, $a)
	function lineInTr($a in AgentDomain) = at({ a1 -> l1 , a2 -> l1}, $a)
	function lineInTrR($a in AgentDomain) = at({ a1 -> l1 , a2 -> l1}, $a)

	rule r_AppendToTransit($agentU in AgentDomain, $senderU in AgentDomain, $messU in MsgType, $sourceU in AgentDomain, $lineU in Line) =
		if(messInTr($agentU)=NO_MESS) then
			par
				senderInTr($agentU) := $senderU
				messInTr($agentU) := $messU
				sourceInTr($agentU) := $sourceU
			endpar
		endif

	rule r_AppendRequestToTransit($agentU in AgentDomain, $senderU in AgentDomain, $messU in MsgType, $sourceU in AgentDomain, $lineU in Line) =
		if(messInTrR($agentU) = NO_MESS) then
			par
				senderInTrR($agentU) := $senderU
				messInTrR($agentU) := $messU
				sourceInTrR($agentU) := $sourceU
			endpar
		endif

	rule r_R1UR2UR3UR4 =
		if(messInTrR(a1) = NO_MESS) then
			/*par
				if (produceCCType(self)=CCGET) and (curPhase(self, l1)=READY) then
					r_AppendRequestToTransit[home(l1), self, GET_MSG, self, l1]
				endif
				if (produceCCType(self)=CCGETX) and (curPhase(self,l1)=READY) then
					r_AppendRequestToTransit[home(l1), self, GETX_MSG, self, l1]
				endif
				if ((produceCCType(self)=CCRPL) and (curPhase (self, l1)=READY) and (ccState(self, l1)=SHARED)) then
					r_AppendRequestToTransit[home(l1), self, RPL_MSG, self, l1]
				endif
				if ((produceCCType(self)=CCWB) and (curPhase(self, l1)=READY) and (ccState (self, l1)= EXCLUSIVE)) then
					r_AppendRequestToTransit[home(l1), self, WB_MSG, self, l1]
				endif
			endpar*/
			par
				forall $j in Line, $k in AgentDomain with $j=l1 and $k = home($j) do
					par
						if (produceCCType(self)=CCGET) and (curPhase(self, $j)=READY) then
							r_AppendRequestToTransit[$k, self, GET_MSG, self, $j]
						endif
						if (produceCCType(self)=CCGETX) and (curPhase(self, $j)=READY) then
							r_AppendRequestToTransit[$k, self, GETX_MSG, self, $j]
						endif
						if ((produceCCType(self)=CCRPL) and (curPhase (self, $j)=READY) and (ccState(self, $j)=SHARED)) then
							r_AppendRequestToTransit[$k, self, RPL_MSG, self, $j]
						endif
						if ((produceCCType(self)=CCWB) and (curPhase(self, $j)=READY) and (ccState(self, $j)= EXCLUSIVE)) then
							r_AppendRequestToTransit[$k, self, WB_MSG, self, $j]
						endif					
					endpar
					
				forall $j2 in Line, $k2 in AgentDomain with $j2=l2 and $k2 = home($j2) do
					par
						if (produceCCType(self)=CCGET) and (curPhase(self, $j2)=READY) then
							r_AppendRequestToTransit[$k2, self, GET_MSG, self, $j2]
						endif
						if (produceCCType(self)=CCGETX) and (curPhase(self, $j2)=READY) then
							r_AppendRequestToTransit[$k2, self, GETX_MSG, self, $j2]
						endif
						if ((produceCCType(self)=CCRPL) and (curPhase (self, $j2)=READY) and (ccState(self, $j2)=SHARED)) then
							r_AppendRequestToTransit[$k2, self, RPL_MSG, self, $j2]
						endif
						if ((produceCCType(self)=CCWB) and (curPhase(self, $j2)=READY) and (ccState(self, $j2)= EXCLUSIVE)) then
							r_AppendRequestToTransit[$k2, self, WB_MSG, self, $j2]
						endif					
					endpar
			endpar
				
		endif

	rule r_R5 =
		forall $i in AgentDomain, $j in Line, $k in AgentDomain with $i=inSource(self) and $j=inLine(self) and $k=owner($j) do
		 	if((inMess(self)=GET_MSG) and (home($j)=self)) then
				if(pending($j)) then
					if(messInTr($i) = NO_MESS) then
						par
							r_AppendToTransit[$i, self, NACK_MSG, $i, $j]
							inMess(self) := NO_MESS
						endpar
					endif
				else
					//if(owner(inLine(self)) != undef) then
					if(ownerIsUndef($j) != true) then
						if(messInTr($k)=NO_MESS) then
							par
								r_AppendToTransit[$k, self, FWD_GET_MSG, $i, $j]
								pending($j) := true
								inMess(self) := NO_MESS
							endpar
						endif
					else
						if(messInTr($i) = NO_MESS) then
							par
								r_AppendToTransit[$i, self, PUT_MSG, $i, $j]
								inMess(self) := NO_MESS
								sharer($j, $i) := true
							endpar
						endif
					endif
				endif
			endif

	rule r_R6 =
		forall $i in AgentDomain, $j in Line, $k in AgentDomain with $i=inSource(self) and $j=inLine(self) and $k=home($j) do
			if(inMess(self) = FWD_GET_MSG) then
				if(ccState(self, $j) = EXCLUSIVE) then
					if($k=$i) then
						if(messInTr($k) = NO_MESS) then
							par
								r_AppendToTransit[$k, self, PUT_SWB_MSG, $i, $j]
								ccState(self, $j) := SHARED
								inMess(self) := NO_MESS
							endpar
						endif
					else
						if((messInTr($i) = NO_MESS) and (messInTr($k) = NO_MESS)) then
							par
								r_AppendToTransit[$i, self, PUT_MSG, $i, $j]
								r_AppendToTransit[$k, self, SWB_MSG, $i, $j]
								ccState(self, $j) := SHARED
								inMess(self) := NO_MESS
							endpar
						endif
					endif
				else
					if($k = $i) then
						if(messInTr($k) = NO_MESS) then
							par
								r_AppendToTransit[$k, self, NACK_C2_MSG, $i, $j]
								inMess(self) := NO_MESS
				 			endpar
			 			endif
			 		else
			 			if((messInTr($i) = NO_MESS) and (messInTr($k)= NO_MESS)) then
							par
								r_AppendToTransit[$i, self, NACK_MSG, $i, $j]
								r_AppendToTransit[$k, self, NACKC_MSG, $i, $j]
								inMess(self) := NO_MESS
							endpar
						endif
					endif
				endif
			endif

	rule r_R7 =
		forall $j in Line with $j=inLine(self) do
			if(inMess(self) = PUT_MSG) then
				par
					if(curPhase(self, $j) != INVALID_PHASE) then
						ccState(self, $j) := SHARED
					endif
					curPhase(self, $j) := READY
					inMess(self) := NO_MESS
				endpar
			endif

	rule r_R8 =
		forall $i in AgentDomain, $j in Line, $k in AgentDomain with $i=inSource(self) and $j=inLine(self) and $k=owner($j) do
			if((inMess(self)=SWB_MSG) and (home($j)=self)) then
				par
					sharer($j, $i) := true
					//if(owner(inLine(self)) != undef) then
					if(ownerIsUndef($j) != true) then
						sharer($j, $k) := true
					endif
					//owner(inLine(self)) := undef
					ownerIsUndef($j) := true
					pending($j) := false
					inMess(self) := NO_MESS
				endpar
			endif

	rule r_R7UR8 =
		forall $i in AgentDomain, $j in Line, $k in AgentDomain with $i=inSource(self) and $j=inLine(self) and $k=owner($j) do
			if(inMess(self) = PUT_SWB_MSG) then
				par
					if curPhase(self, $j) != INVALID_PHASE then
						ccState(self, $j) := SHARED
					endif
					curPhase(self, $j) := READY
					sharer($j, $i) := true
					//if(owner(inLine(self)) != undef) then
					if(ownerIsUndef($j) != true) then
						sharer($j, $k) := true
					endif
					//owner(inLine(self)) := undef
					ownerIsUndef($j) := true
					pending($j) := false
					inMess(self) := NO_MESS
				endpar
			endif

	rule r_R9 =
		forall $j in Line with $j=inLine(self) do
			if(inMess(self) = NACK_MSG) then
				par
					curPhase(self, $j) := READY
					inMess(self) := NO_MESS
				endpar
			endif

	rule r_R10 =
		forall $j in Line with $j=inLine(self) do
			if ((inMess(self)=NACKC_MSG) and (home($j)=self)) then
				par
					pending($j) := false
					inMess(self) := NO_MESS
				endpar
			endif

	rule r_R9UR10 =
		forall $j in Line with $j=inLine(self) do
			if(inMess(self) = NACK_C2_MSG) then
				par
					curPhase(self, $j) := READY
					pending($j) := false
					inMess(self) := NO_MESS
				endpar
			endif

	rule r_R11 =
		forall $i in AgentDomain, $j in Line, $k in AgentDomain with $i=inSource(self) and $j=inLine(self) and $k=owner($j) do
			if((inMess(self)=GETX_MSG) and (home($j)=self)) then
				if(pending($j)=true) then
					if(messInTr($i)=NO_MESS) then
						par
							r_AppendToTransit[$i, self, NACK_MSG, $i, $j]
							inMess(self) := NO_MESS
						endpar
					endif
				else
					//if(owner(inLine(self)) != undef) then
					if(ownerIsUndef($j) != true) then
						if(messInTr($k)=NO_MESS) then
							par
								r_AppendToTransit[$k, self, FWD_GETX_MSG, $i, $j]
								pending($j) := true
								inMess(self) := NO_MESS
							endpar
						endif
					else
						//if(forall $agentU in {a1, a2} holds (not(sharer($j, $agentU)))) then
						if( forall $agentU in AgentDomain with (not(sharer($j, $agentU)))) then
							if(messInTr($i) = NO_MESS) then
								par
									r_AppendToTransit[$i, self, PUTX_MSG, $i, $j]
									owner($j) := $i
									ownerIsUndef($j) := false
									inMess(self) := NO_MESS
								endpar
							endif
						else
							//if(forall agentU in {al, a2} holds (not(sharer($j, $agentU)) or (messInTr($agentU)=NO_MESS))) then
							if(forall $agentU2 in AgentDomain with (not(sharer($j, $agentU2)) or (messInTr($agentU)=NO_MESS))) then
								par
									forall $agentU3 in AgentDomain with (sharer($j, $agentU3)) do
										par
											r_AppendToTransit[$agentU3, self, INV_MSG, $i, $j]
											pending($j) := true
										endpar
									inMess(self) := NO_MESS
								endpar
							endif
						endif
					endif
				endif
			endif

	rule r_R12 =
		forall $i in AgentDomain, $j in Line, $k in AgentDomain with $i=inSource(self) and $j=inLine(self) and $k=home($j) do
			if(inMess(self) = FWD_GETX_MSG) then
				if(ccState(self, $j) = EXCLUSIVE) then
					if($k=$i) then
						if(messInTr($k) = NO_MESS) then
							par
								r_AppendToTransit[$k, self, PUTX_FWD_ACK_MSG, $i, $j]
								ccState(self, $j) := INVALID
								inMess(self) := NO_MESS
							endpar
						endif
					else
						if (messInTr($i) = NO_MESS) and (messInTr($k) = NO_MESS) then
							par
								r_AppendToTransit[$i, self, PUTX_MSG, $i, $j]
								r_AppendToTransit[$k, self, FWD_ACK_MSG, $i, $j]
								ccState(self, $j) := INVALID
								inMess(self) := NO_MESS
							endpar
						endif
					endif
				else
					if($k=$i) then
						if (messInTr($k) = NO_MESS) then
							par
								r_AppendToTransit[$i, self, NACK_C2_MSG, $i, $j]
								inMess(self) := NO_MESS
							endpar
						endif
					else
						if((messInTr($i)= NO_MESS) and (messInTr($k) = NO_MESS)) then
							par
								r_AppendToTransit[$i, self, NACK_MSG, $i, $j]
								r_AppendToTransit[$k, self, NACKC_MSG, $i, $j]
								inMess(self) := NO_MESS
							endpar
						endif
					endif
				endif
			endif

	rule r_R13 =
		forall $j in Line with $j=inLine(self) do
			if (inMess(self) = PUTX_MSG) then
				par
					ccState(self, $j) := EXCLUSIVE
					curPhase(self, $j) := READY
					inMess(self) := NO_MESS
				endpar
			endif

	rule r_R14 =
		forall $j in Line with $j=inLine(self) do
			if((inMess(self) = FWD_ACK_MSG) and (home($j) = self)) then
				par
					owner($j) := inSource(self)
					ownerIsUndef($j) := false
					pending($j) := false
					inMess(self) := NO_MESS
				endpar
			endif

	rule r_R13UR14 =
		forall $j in Line with $j=inLine(self) do
			if(inMess(self) = PUTX_FWD_ACK_MSG)then
				par
					ccState(self, $j) := EXCLUSIVE
					curPhase(self, $j) := READY
					owner($j) := inSource(self)
					ownerIsUndef($j) := false
					pending($j) := false
					inMess(self) := NO_MESS
				endpar
			endif

	rule r_R15 =
		forall $i in AgentDomain, $j in Line, $k in AgentDomain with $i=inSource(self) and $j=inLine(self) and $k=home($j) do
			if(inMess(self)=INV_MSG) then
				if(messInTr($k)=NO_MESS) then
					par
						r_AppendToTransit[$k, self, INV_ACK_MSG, $i, $j]
						inMess(self) := NO_MESS
						if(ccState(self, $j)=SHARED) then
							ccState(self, $j) := INVALID
						else
							if(curPhase(self, $j)=WAIT) then
								curPhase(self, $j) := INVALID_PHASE
							endif
						endif
					endpar
				endif
			endif

	rule r_R16 =
		forall $i in AgentDomain, $j in Line with $i=inSource(self) and $j=inLine(self) do
			if((inMess(self)=INV_ACK_MSG) and (home($j)=self)) then
				forall $agentU in AgentDomain with true do
					if(inSender(self)=$agentU) then
						par
							sharer($j, $agentU) := false
							if(forall $otherUagentU in AgentDomain with (($otherUagentU=$agentU or sharer($j, $otherUagentU)=false))) then
								if(messInTr($i) = NO_MESS) then
									par
										r_AppendToTransit[$i, self, PUTX_MSG, $i, $j]
										pending($j) := false
										inMess(self) := NO_MESS
									endpar
								endif
							else
								inMess(self) := NO_MESS
							endif
						endpar
					endif
				endif

	rule r_R17 =
		forall $i in AgentDomain, $j in Line with $i=inSender(self) and $j=inLine(self) do
			if((inMess(self)=RPL_MSG) and (home($j)=self)) then
				par
					if((sharer($j, $i) = true) and (pending($j)= false)) then
						par
							sharer($j, $i) := false
							ccState(self, $j) := INVALID
						endpar
					endif
					inMess(self) := NO_MESS
				endpar
			endif

	rule r_R18 =
		forall $j in Line with $j=inLine(self) do
			if((inMess(self)=WB_MSG) and (home($j)=self)) then
				par
					//if(owner(inLine(self))!= undef) then
					if(ownerIsUndef($j)!= true) then
						par
							//owner(inLine(self)) := undef
							ownerIsUndef($j) := true
							ccState(self, $j) := INVALID
						endpar
					endif
					inMess(self) := NO_MESS
				endpar
			endif

	rule r_behavior =
		par
			r_R1UR2UR3UR4[]
			r_R5[]
			r_R6[]
			r_R7[]
			r_R8[]
			r_R7UR8[]
			r_R9[]
			r_R10[]
			r_R9UR10[]
			r_R11[]
			r_R12[]
			r_R13[]
			r_R14[]
			r_R13UR14[]
			r_R15[]
			r_R16[]
			r_R17[]
			r_R18[]
		endpar

	rule r_ClearMessInTr($agentU in AgentDomain) =
		messInTr($agentU) := NO_MESS

	rule r_SendMess($agentU in AgentDomain) =
		par
			inSender($agentU) := senderInTr($agentU)
			inMess($agentU) := messInTr($agentU)
			inSource($agentU) := sourceInTr($agentU)
			r_ClearMessInTr[$agentU]
		endpar

	rule r_SendR($agentU in AgentDomain) =
		par
			inSender($agentU) := senderInTrR($agentU)
			inMess($agentU) := messInTrR($agentU)
			inSource($agentU) := sourceInTrR($agentU)
			messInTrR($agentU) := NO_MESS
		endpar

	rule r_SendRequest($agentU in AgentDomain) =
		forall $i in AgentDomain, $j in Line with $i=senderInTrR($agentU) and $j=lineInTrR($agentU) do
			if((messInTrR($agentU)=GET_MSG) and (curPhase($i, $j)=READY) and (ccState($i, $j)=INVALID)) then
				par
					r_SendR[$agentU]
					curPhase($i, $j) := WAIT
				endpar
			else
				if((messInTrR($agentU)=GETX_MSG) and (curPhase($i, $j)=READY)) then
					par
						r_SendR[$agentU]
						curPhase($i, $j) := WAIT
					endpar
				else
					if((messInTrR($agentU)=RPL_MSG) and (curPhase($i, $j)=READY) and (ccState($i, $j)=SHARED)) then
						par
							r_SendR[$agentU]
							ccState($i, $j) := INVALID
						endpar
					else
						if((messInTrR($agentU) = WB_MSG) and (curPhase($i, $j) = READY) and (ccState($i, $j)=EXCLUSIVE)) then
							par
								r_SendR[$agentU]
								ccState($i, $j) := INVALID
							endpar
						endif
					endif
				endif
			endif

	//rule r_env =
	rule r_env =
		forall $a in AgentDomain with true do
			if(inMess($a) = NO_MESS) then
				if(messInTr($a) != NO_MESS) then
					r_SendMess[$a]
				else
					if((messInTrR($a) != NO_MESS) and (inMess($a)=NO_MESS)) then
						r_SendRequest[$a]
					endif
				endif
			endif

	/*rule r_Skip =
		skip*/



	//proprieta' LTL
	LTLSPEC g(not( (ccState(a1, 1) = EXCLUSIVE) and (ccState(a2, 1)=EXCLUSIVE) ))
	/*LTLSPEC (g(((curPhase(a1, 1) =WAIT) implies f(curPhase(a1, 1)=READY)))) and (g(((curPhase(a2, 1) =WAIT) implies f(curPhase(a2, 1)=READY))))
	LTLSPEC g( ((ccState(a1, 1)=SHARED) implies x(sharer(1, a1)=true)) or ((sharer(1, a1)=true) implies x(ccState(a1, 1)=SHARED)) ) and g( ((ccState(a2, 1)=SHARED) implies x(sharer(1, a2)=true)) or ((sharer(1, a2)=true) implies x(ccState(a2, 1)=SHARED)) )

	//proprieta' CTL
	CTLSPEC ag(not(ccState(a1, 1) = EXCLUSIVE and ccState(a2, 1)=EXCLUSIVE))
	CTLSPEC ag(curPhase(a1, 1) = WAIT implies af(curPhase(a1, 1) = READY)) and ag(curPhase(a2, 1) = WAIT implies af(curPhase(a2, 1) = READY))
	CTLSPEC ag((ccState(a1, 1)=SHARED implies ax(sharer(1, a1)=true)) or (sharer(1, a1)=true implies ax(ccState(a1, 1)=SHARED))) and ag((ccState(a2, 1)=SHARED implies ax(sharer(1, a2)=true)) or (sharer(1, a2)=true implies ax(ccState(a2, 1)=SHARED)))

	CTLSPEC ag(ccState(a1, 1)=INVALID and ccState(a2, 1)=INVALID)
	CTLSPEC ag(curPhase(a1, 1) = READY and curPhase(a2, 1) = READY)
	*/

	//mio
	main rule r_main =
		par
			choose $a in AgentDomain with true do program ($a)
			program(ee)
		endpar

default init s0:
	function messInTr($a in AgentDomain) = at({ a1 -> NO_MESS, a2 -> NO_MESS}, $a)
	function senderInTr($a in AgentDomain) = at({ a1 -> a1 , a2 -> a1 }, $a)
	function sourceInTr($a in AgentDomain) = at({ a1 -> a1 , a2 -> a1}, $a)
	function senderInTrR($a in AgentDomain ) = at({ a1 -> a2 , a2 -> a2}, $a)
	function sourceInTrR($a in AgentDomain) = at({ a1 -> a1 , a2 -> a1}, $a)
	function messInTrR($a in AgentDomain) = at({a1 -> NO_MESS, a2 -> NO_MESS}, $a)
	function inSender($a in AgentDomain) = at({a1 -> a2 , a2 -> a2}, $a)
	function inSource($a in AgentDomain) = at({a1 -> a2, a2 -> a2}, $a)
	function inMess($a in AgentDomain) = at({ a1 -> NO_MESS, a2 -> NO_MESS}, $a)
	function curPhase($a in AgentDomain, $l in Line) = at({(a1, l1) -> READY, (a2, l1) -> READY, (a1, l2) -> READY, (a2, l2) -> READY}, ($a, $l))
	function ccState($a in AgentDomain, $l in Line) = at({(a1, l1) -> INVALID, (a2,l1) -> INVALID, (a1, l2) -> INVALID, (a2,l2) -> INVALID}, ($a, $l))
	function pending($l in Line) = at({l1  -> false, l2 -> false}, $l)
	function sharer($l in Line, $a in AgentDomain) = at({(l1, a1) -> false, (l1, a2) -> false, (l2, a1) -> false, (l2, a2) -> false}, ($l, $a))

	function ownerIsUndef($l in Line) = true
	function owner($l in Line) = a1

	agent AgentDomain:
		r_behavior[]

	agent AgentDomainEnv:
		r_env[]
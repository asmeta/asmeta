/* 
  versione presa dal sito della Winter
  Version with  do_for_all
   2 Agents and with Inter-Node-Communication,
   i.e. Home(l)=agent(1) also requests on l! 
   MessInTrQ with parametric length 
   clear InMess, as far as appending to MessInTr is successful
*/
asm flashProtocol

import ../../../STDL/StandardLibrary

signature:


enum domain  MsgType  = { NO_MESS | GET_MSG| GETX_MSG| INV_MSG|  WB_MSG| RPL_MSG| FWD_ACK_MSG| SWB_MSG| 
                      INV_ACK_MSG|  NACK_MSG| NACKC_MSG| FWD_GET_MSG| FWD_GETX_MSG| PUT_MSG| PUTX_MSG|
                      NACK_C2_MSG| PUT_SWB_MSG| PUTX_FWD_ACK_MSG}

enum domain  CcType  == { CCGET| CCGETX| CCRPL| CCWB }

domain  AgentDomain subsetof Integer  /*== { agent :INT| none }*/

domain  Line subsetof Integer /*    == { lines :INT }*/

domain Message :  Prod(AgentDomain,MsgType,AgentDomain,Line)

enum domain PHASE  == { ready| wait| invalidPhase }
enum domain STATE   == { exclusive| shared| invalid }
freetype LENGTH == { n :INT }
(*------------------------------------------*)
static function max_agent == 2
static function max_line  == 2
static function maxQ  == 2

static function Agent == {agent(i)| i in {1..max_agent}} union {none}
static function Agent_without_none == {agent(i)| i in {1..max_agent}}
static function Line  == { lines(i) | i in {1..max_line} }
static function QLength  == { n(i) | i in {1..maxQ} }
(*------------------------------------------*)
static function Type  == { NO_MESS| GET_MSG| GETX_MSG| INV_MSG|  WB_MSG| RPL_MSG| FWD_ACK_MSG| SWB_MSG| 
                           INV_ACK_MSG|  NACK_MSG| NACKC_MSG| FWD_GET_MSG| FWD_GETX_MSG| PUT_MSG| PUTX_MSG|
                           NACK_C2_MSG| PUT_SWB_MSG| PUTX_FWD_ACK_MSG                      (*for intra-node-comm*)
                           }

static function CCType == { CCGET| CCGETX| CCRPL| CCWB }
static function ReqType == { NO_MESS| GET_MSG| GETX_MSG| RPL_MSG| WB_MSG }


static function Home ==
  MAP_TO_FUN { lines(1) -> agent(1), lines(4) -> agent(1),
               lines(2) -> agent(1), lines(5) -> agent(2),
               lines(3) -> agent(3), lines(6) -> agent(3) }

(*------------------------------------------*)
(* --- NEW: Messages in Transit ------------*)

(*-- NEW: in order to make undef uneccassary initialize -- *)

dynamic function MessInTr : LENGTH * AgentDomain -> TYPE
  with MessInTr(i,a) in Type
  initially MAP_TO_FUN {(i,a) -> NO_MESS|(i,a) in Union({ {(i,a) | i in QLength } | a in Agent\{none}}) }

dynamic function SenderInTr : LENGTH * AgentDomain -> AgentDomain
   with SenderInTr(i,a) in Agent \ { none }
   initially MAP_TO_FUN { (i,a) -> agent(1) | (i,a) in Union 
                           ({ { (i,a) | i in QLength } | a in Agent \ { none }}) }

dynamic function SourceInTr : LENGTH * AgentDomain -> AgentDomain
   with SourceInTr(i,a) in Agent \ { none }
  initially MAP_TO_FUN { (i,a) -> agent(1) | (i,a) in Union 
                           ({ { (i,a) | i in QLength } | a in Agent \ { none }}) }


dynamic function LineInTr : LENGTH * AgentDomain -> Line
  with LineInTr(i,a) in Line
  initially MAP_TO_FUN { (i,a) -> lines(1) | (i,a) in Union 
                           ({ { (i,a) | i in QLength } | a in Agent \ { none }}) }

(*------------------------------------------*)
(*-- NEW: in order to make undef uneccassary -- *)
dynamic function SenderInTrR : AgentDomain -> AgentDomain
   with SenderInTrR(a) in Agent \ { none }
  initially MAP_TO_FUN { a -> agent(2) | a in Agent \ { none } }

(*-- NEW: in order to make undef uneccassary -- *)
dynamic function SourceInTrR : AgentDomain -> AgentDomain
   with SourceInTrR(a) in Agent \ { none }
  initially MAP_TO_FUN { a -> agent(1) | a in Agent \ { none } }

(*-- NEW: in order to make undef uneccassary -- *)
dynamic function MessInTrR : AgentDomain -> TYPE
  with MessInTrR(a) in ReqType
  initially MAP_TO_FUN { a -> NO_MESS | a in Agent \ { none } }

(*-- NEW: initial value changed from undef to line(1) --*)
dynamic function LineInTrR : AgentDomain -> Line
  with LineInTrR(a) in Line
  initially MAP_TO_FUN { a -> lines(1) | a in Agent \ { none } }


(*------------------------------------------*)
(* --- New: Mess after Transit -------------*)


(*-- NEW: in order to make undef uneccassary -- *)
dynamic function InSender : AgentDomain -> AgentDomain
   with InSender(a) in Agent \ { none }
  initially MAP_TO_FUN { a -> agent(2) | a in Agent \ { none } }

(*-- NEW: in order to make undef uneccassary -- *)
dynamic function InSource : AgentDomain -> AgentDomain
   with InSource(a) in Agent \ { none }
  initially MAP_TO_FUN { a -> agent(2) | a in Agent \ { none } }

(*-- NEW: in order to make undef uneccassary -- *)
dynamic function InMess : AgentDomain -> TYPE
  with InMess(a) in Type
  initially MAP_TO_FUN { a -> NO_MESS | a in Agent \ { none } }

(*-- NEW: initial value changed from undef to line(1) --*)
dynamic function InLine : AgentDomain -> Line
  with InLine(a) in Line
  initially MAP_TO_FUN { a -> lines(1) | a in Agent \ { none } }

  
(*------------------------------------------*)
(*---- other state and line Variables ----*)

dynamic function CurPhase :AgentDomain * Line -> PHASE
   initially MAP_TO_FUN
    { (a,l) -> ready | (a,l) in Union ({ { (a,l) | 
                     a in Agent \ { none }} | l in Line} )}

(*-- NEW: initial value changed from shared to invalid --*)
dynamic function CCState :AgentDomain * Line -> STATE
   initially MAP_TO_FUN 
    { (a,l) -> invalid | (a,l) in Union ({ { (a,l) | a in 
                                      Agent \ { none }} | l in Line} )}

dynamic relation Pending  :Line
   initially SET_TO_REL {}

dynamic function Owner    :Line -> AgentDomain
   with Owner (l) in Agent
   initially MAP_TO_FUN { l -> none | l in Line }

dynamic relation Sharer   :Line * AgentDomain
   initially SET_TO_REL {}


(*------------------------------------------*)
(*-- synchronization -----------------------*)

enum domain MODE   == { behave, sync }

dynamic function toggle : MODE
   initially behave

(*------------------------------------------*)

external function Self    :AgentDomain
  with Self in Agent \ { none }

external function produce :AgentDomain -> (CcType * Line)
    with produce(a) in Union ({ { (cctype_, line_) | cctype_ in CCType } | line_ in Line }) 
(*   initially MAP_TO_FUN { a -> (CCGET,lines(1)) | a in Agent \ { none } } *)




(*-------------------------------------------------------------*)


transition AppendToTransit (agent_, (sender_,mess_,source_,line_)) ==
   if MessInTr(n(1),agent_)=NO_MESS
   then  SenderInTr(n(1),agent_) := sender_ 
         MessInTr(n(1),agent_)  := mess_ 
         SourceInTr(n(1),agent_):= source_ 
         LineInTr(n(1),agent_)  := line_
   else do forall i in { 2..maxQ }                           (* the first empty Queue-entry *)
         if MessInTr(n(i-1),agent_)!=NO_MESS and MessInTr(n(i),agent_)=NO_MESS 
         then SenderInTr(n(i),agent_) := sender_ 
              MessInTr(n(i),agent_)  := mess_ 
              SourceInTr(n(i),agent_):= source_ 
              LineInTr(n(i),agent_)  := line_
         endif
        enddo
   endif


transition AppendRequestToTransit (agent_, (sender_,mess_,source_,line_)) ==
  if MessInTrR (agent_)=NO_MESS
  then SenderInTrR(agent_) := sender_ 
       MessInTrR (agent_)  := mess_ 
       SourceInTrR (agent_):= source_ 
       LineInTrR (agent_)  := line_
  endif


(*-------------------------------------------------------------*)
(*-- not excluding intra-node-comm add:  and Self != Home(l)   *)
(*-- restricting requests: if line is shared or owned dont 
     make a new request!                                     --*)
(* The guard for sending a request have to be satisfied when
   the request is passing through also!! Not only when the request is PUT_MSG into 
   Transit-Queue! *)


transition R1_R2_R3_R4 ==
  if MessInTrR(agent(1)) = NO_MESS 
  then 
    case produce(Self) of
      (CCGET, l) :
         if CurPhase(Self, l)=ready
         then AppendRequestToTransit (Home(l),(Self,GET_MSG,Self,l))
         endif;
      (CCGETX,l) :
         if CurPhase(Self, l)=ready
         then AppendRequestToTransit (Home(l), (Self,GETX_MSG,Self,l))
         endif;
      (CCRPL,l) :
          if CurPhase(Self, l)=ready and CCState(Self,l)=shared
          then AppendRequestToTransit (Home(l),(Self,RPL_MSG,Self,l))
          endif;
       (CCWB,l) :
          if CurPhase(Self, l)=ready and CCState(Self,l)=exclusive
          then AppendRequestToTransit (Home(l),(Self,WB_MSG,Self,l))
          endif;
     _ : skip
    endcase
  endif 


(*------------------------------------------------------------ *)


(*--------------------------------------------------------*)

transition R5 ==
  if (InMess(Self)=GET_MSG and Home(InLine(Self)) = Self)
  then
     if Pending (InLine(Self))
      then if  MessInTr(n(maxQ),InSource(Self))=NO_MESS
           then AppendToTransit (InSource(Self),
                         (Self,NACK_MSG,InSource(Self),InLine(Self)))
                InMess(Self):=NO_MESS
           endif
      else if Owner (InLine(Self)) != none
	     then if MessInTr(n(maxQ),Owner(InLine(Self)))=NO_MESS
		  then AppendToTransit (Owner (InLine(Self)),
                          (Self,FWD_GET_MSG,InSource(Self),InLine(Self)))
		       Pending (InLine(Self)) := true
                       InMess(Self):=NO_MESS
		  endif
           else if MessInTr(n(maxQ),InSource(Self))=NO_MESS
		  then AppendToTransit (InSource(Self),
                          (Self,PUT_MSG,InSource(Self),InLine(Self)))
                       InMess(Self):=NO_MESS
		       Sharer (InLine(Self), InSource(Self)) := true
		  endif
           endif
      endif
  endif


transition R6 ==
  if InMess(Self) = FWD_GET_MSG 
  then if CCState (Self,InLine(Self)) = exclusive
       then if (Home(InLine(Self))=InSource(Self))
            then if MessInTr(n(maxQ),Home(InLine(Self))) = NO_MESS
                 then AppendToTransit ( Home(InLine(Self)), (Self,PUT_SWB_MSG,InSource(Self),InLine(Self)))
                      CCState(Self,InLine(Self)):=shared
                      InMess(Self):=NO_MESS
                 endif
            else if MessInTr(n(maxQ),InSource(Self)) = NO_MESS and MessInTr(n(maxQ),Home(InLine(Self))) = NO_MESS
                 then AppendToTransit (InSource(Self), (Self,PUT_MSG,InSource(Self),InLine(Self)))
                      AppendToTransit ( Home(InLine(Self)), (Self,SWB_MSG,InSource(Self),InLine(Self)))
                      CCState(Self,InLine(Self)):=shared
                      InMess(Self):=NO_MESS
                 endif
            endif
       else if (Home(InLine(Self))=InSource(Self))
            then if MessInTr(n(maxQ),Home(InLine(Self))) = NO_MESS
                 then AppendToTransit (Home(InLine(Self)),(Self,NACK_C2_MSG,InSource(Self),InLine(Self)))
                      InMess(Self):=NO_MESS
                 endif
            else if MessInTr(n(maxQ),InSource(Self)) = NO_MESS and MessInTr(n(maxQ),Home(InLine(Self))) = NO_MESS
                 then AppendToTransit (InSource(Self), (Self,NACK_MSG,InSource(Self),InLine(Self)))
                      AppendToTransit (Home(InLine(Self)), (Self,NACKC_MSG,InSource(Self),InLine(Self)))
                      InMess(Self):=NO_MESS
                 endif
            endif
      endif
  endif

transition R7 ==
 if InMess(Self) = PUT_MSG
 then if CurPhase(Self,InLine(Self)) != invalidPhase
      then CCState(Self,InLine(Self)) := shared
      endif
      CurPhase(Self,InLine(Self)) := ready
      InMess(Self) := NO_MESS
  endif

transition R8 ==
  if (InMess(Self) = SWB_MSG and Home(InLine(Self)) = Self)
  then  
      Sharer(InLine(Self), InSource(Self)) := true
      if Owner(InLine(Self)) != none
      then Sharer(InLine(Self), Owner(InLine(Self))) := true
      endif
      Owner(InLine(Self)) := none
      Pending(InLine(Self)) := false
      InMess(Self) := NO_MESS
  endif

(*-------------------------------------------------------------*)
(* if intra-node-comm and Home ask for getting then do PUT_MSG and SWB_MSG in one step  *)

transition R7_R8 ==
 if InMess(Self) = PUT_SWB_MSG
 then if CurPhase(Self,InLine(Self)) != invalidPhase
        then CCState(Self,InLine(Self)) := shared
      endif
      CurPhase(Self,InLine(Self)) := ready
      Sharer(InLine(Self), InSource(Self)) := true
      if Owner(InLine(Self)) != none
        then Sharer(InLine(Self), Owner(InLine(Self))) := true
      endif
      Owner(InLine(Self)) := none
      Pending(InLine(Self)) := false
      InMess(Self) := NO_MESS
  endif
 


(*-------------------------------------------------------------*)

transition R9 ==
  if InMess(Self) = NACK_MSG
  then
      CurPhase(Self, InLine(Self)) := ready
      InMess(Self) := NO_MESS
  endif

transition R10 ==
 if (InMess(Self) = NACKC_MSG and Home(InLine(Self)) = Self)
 then 
      Pending(InLine(Self)) := false
      InMess(Self) := NO_MESS
  endif

(*-------------------------------------------------------------*)
(* if intra-node-comm and Home gets negativ request then do NACK_MSG and NACKC_MSG in one step  *)

transition R9_R10 ==
  if InMess(Self) = NACK_C2_MSG
  then
      CurPhase(Self, InLine(Self)) := ready
      Pending(InLine(Self)) := false
      InMess(Self) := NO_MESS
  endif


(*-------------------------------------------------------------*)

transition R11 == 
  if (InMess(Self) = GETX_MSG and Home(InLine(Self)) = Self)
  then
      if Pending(InLine(Self)) = true
      then if MessInTr(n(maxQ),InSource(Self)) = NO_MESS
           then AppendToTransit (InSource(Self), (Self,NACK_MSG,InSource(Self),InLine(Self)))
                InMess(Self):=NO_MESS
           endif
      else if Owner(InLine(Self)) != none
           then if MessInTr(n(maxQ),Owner(InLine(Self))) = NO_MESS
                then AppendToTransit (Owner(InLine(Self)),(Self,FWD_GETX_MSG,InSource(Self),InLine(Self)))
                     Pending(InLine(Self)) := true
                     InMess(Self):=NO_MESS
                endif
           else if ( forall agent_ in Agent_without_none : not(Sharer(InLine(Self),agent_)) )
                  then if MessInTr(n(maxQ),InSource(Self)) = NO_MESS
                       then AppendToTransit (InSource(Self), (Self,PUTX_MSG,InSource(Self),InLine(Self)))
                          Owner(InLine(Self)) := InSource(Self)
                          InMess(Self):=NO_MESS
                     endif
                else if (forall agent_ in Agent_without_none 
                                : not(Sharer(InLine(Self),agent_)) or MessInTr(n(maxQ),agent_) = NO_MESS )
                     then do forall agent_ in Agent_without_none with Sharer(InLine(Self),agent_)
                                 AppendToTransit (agent_,(Self,INV_MSG,InSource(Self),InLine(Self)))
                                 Pending(InLine(Self)):=true
                          enddo
                          InMess(Self):=NO_MESS
                     endif
                endif
           endif
      endif
  endif


(*--------------------------------------------------------------*)

transition R12 == 
  if InMess(Self) = FWD_GETX_MSG
  then
      if CCState(Self,InLine(Self)) = exclusive
      then if (Home(InLine(Self))=InSource(Self))
           then if MessInTr(n(maxQ),Home(InLine(Self))) = NO_MESS
                then AppendToTransit (Home(InLine(Self)),(Self,PUTX_FWD_ACK_MSG,InSource(Self),InLine(Self)))
                     CCState(Self,InLine(Self)):=invalid
                     InMess(Self):=NO_MESS
                endif
           else if MessInTr(n(maxQ),InSource(Self)) = NO_MESS and MessInTr(n(maxQ),Home(InLine(Self))) = NO_MESS
                then AppendToTransit (InSource(Self), (Self,PUTX_MSG,InSource(Self),InLine(Self)))
                     AppendToTransit (Home(InLine(Self)), (Self,FWD_ACK_MSG,InSource(Self),InLine(Self)))
                     CCState(Self,InLine(Self)):=invalid
                     InMess(Self):=NO_MESS
                endif
           endif
      else if (Home(InLine(Self))=InSource(Self))
           then if MessInTr(n(maxQ),Home(InLine(Self))) = NO_MESS
                then AppendToTransit (InSource(Self), (Self,NACK_C2_MSG,InSource(Self),InLine(Self)))
                     InMess(Self):=NO_MESS
                endif
           else if MessInTr(n(maxQ),InSource(Self)) = NO_MESS and MessInTr(n(maxQ),Home(InLine(Self))) = NO_MESS
                then AppendToTransit (InSource(Self), (Self,NACK_MSG,InSource(Self),InLine(Self)))
                     AppendToTransit (Home(InLine(Self)), (Self,NACKC_MSG,InSource(Self),InLine(Self)))
                     InMess(Self):=NO_MESS
                endif
           endif
      endif
  endif



transition R13 == 
  if InMess(Self) = PUTX_MSG
  then
      CCState(Self,InLine(Self)) := exclusive
      CurPhase(Self, InLine(Self)) := ready
      InMess(Self) := NO_MESS
  endif

transition R14 == 
  if (InMess(Self) = FWD_ACK_MSG and Home(InLine(Self)) = Self)
  then
      Owner(InLine(Self)) := InSource(Self)
      Pending(InLine(Self)) := false
      InMess(Self) := NO_MESS
  endif

(*----------------------------------------------------------------------*)
(* if intra-node-comm and Home requests for exclusive mem then do PUTX_MSG and FWD_ACK_MSG in one step  *)

transition R13_R14 == 
  if InMess(Self) = PUTX_FWD_ACK_MSG
  then
      CCState(Self,InLine(Self)) := exclusive
      CurPhase(Self, InLine(Self)) := ready
      Owner(InLine(Self)) := InSource(Self)
      Pending(InLine(Self)) := false
      InMess(Self) := NO_MESS
  endif


(*----------------------------------------------------------------------*)

(* for testing *)
transition R15a == 
      if MessInTr(n(maxQ),Home(InLine(Self))) = NO_MESS 
      then  CCState(Self,InLine(Self)) := shared
      endif

(* original *)

transition R15 == 
  if InMess(Self) = INV_MSG
  then
      if MessInTr(n(maxQ),Home(InLine(Self))) = NO_MESS 
      then  AppendToTransit (Home(InLine(Self)),(Self,INV_ACK_MSG,InSource(Self),InLine(Self)))  
            InMess(Self):=NO_MESS
            if CCState(Self,InLine(Self)) = shared
            then CCState(Self,InLine(Self)) := invalid
            else if CurPhase(Self, InLine(Self)) = wait
                 then CurPhase(Self, InLine(Self)) := invalidPhase
                 endif
            endif
      endif
 endif


transition R16 == 
 if (InMess(Self) = INV_ACK_MSG and Home(InLine(Self)) = Self)
 then do forall agent_ in Agent_without_none with true
       if InSender(Self)=agent_
       then Sharer(InLine(Self),agent_) := false     
            if ( forall other_agent_ in (Agent_without_none\{agent_})
                 : Sharer(InLine(Self), other_agent_)=false )
            then if MessInTr(n(maxQ),InSource(Self)) = NO_MESS
                 then AppendToTransit (InSource(Self), (Self,PUTX_MSG,InSource(Self),InLine(Self)))
                      Pending(InLine(Self)):=false
                      Owner(InLine(Self)) := InSource(Self) 
                      InMess(Self):=NO_MESS
                 endif
            else InMess(Self):=NO_MESS
            endif
        endif
       enddo
 endif


(*-------------------------------------------------------------*)

transition R17 == 
 if (InMess(Self) = RPL_MSG and Home(InLine(Self)) = Self)
 then if Sharer(InLine(Self),InSender(Self)) = true and Pending(InLine(Self)) = false       
      then Sharer(InLine(Self),InSender(Self)) := false
           CCState(Self,InLine(Self)) := invalid
      endif
      InMess(Self) := NO_MESS
  endif

transition R18 == 
 if (InMess(Self) = WB_MSG and Home(InLine(Self)) = Self)
 then if Owner(InLine(Self)) != none
      then Owner(InLine(Self)) := none
           CCState(Self,InLine(Self)) := invalid
      endif
      InMess(Self) := NO_MESS
  endif

(*-------------------------------------------------------------*)

transition behavior ==
  block
    R1_R2_R3_R4
    R5
    R6
    R7
    R8
    R7_R8
    R9
    R10
    R9_R10
    R11
    R12
    R13
    R14
    R13_R14
    R15
    R16 
    R17
    R18
  endblock

(*-------------------------------------------------------------*)

(* sending message means to write mess from transit into queue
   the message from transit has to be clear to indicate that 
   the sending is process (and has not failed!)
   if it is the case that sender is equal to destiny (Owner=sharedRequester)
   then InMess of sender should not be clear
   otherwise, it has to be cleared to indicate message has been send *)   

transition  ClearMessInTr(agent_) ==
block
  MessInTr(n(maxQ),agent_):=NO_MESS    (* clear last entry in any case *)
  if MessInTr(n(2),agent_)=NO_MESS        (* next message in transit after first *)
  then MessInTr(n(1),agent_):=NO_MESS
  else do forall i in {2..maxQ}
        if MessInTr(n(i), agent_)!=NO_MESS
        then MessInTr (n(i-1), agent_):=MessInTr(n(i), agent_)
             SenderInTr (n(i-1), agent_):=SenderInTr(n(i), agent_) 
             SourceInTr (n(i-1), agent_):=SourceInTr(n(i), agent_)
             LineInTr (n(i-1), agent_):=LineInTr(n(i), agent_)
(*             MessInTr (n(i), agent_):=NO_MESS *) (* RACING MessInTr2 wird ueberschrieben!! *)
        else MessInTr(n(i-1), agent_):=NO_MESS  (* wenn n(i) == NO_MESS, dann loesche auch n(i-1) *)
        endif
       enddo
  endif
endblock

transition SendMess(agent_) ==
  block
   InSender(agent_):= SenderInTr(n(1), agent_) 
   InMess(agent_)  := MessInTr(n(1), agent_)  
   InSource(agent_):= SourceInTr(n(1), agent_)
   InLine(agent_)  := LineInTr(n(1), agent_) 
   ClearMessInTr(agent_)                   (* clear MessInTransit *)
  endblock


(* if a Request is send the MessInTransitRequest Variable is cleared
   and a new request can be adopted       *)
(* The guard for sending a request have to be satisfied when
   the request is passing through!! Not when the request is PUT_MSG into 
   Transit-Queue! *)


transition SendR(agent_) ==
  block
   InSender(agent_)  := SenderInTrR(agent_) 
   InMess(agent_)    := MessInTrR (agent_)  
   InSource(agent_)  := SourceInTrR (agent_)
   InLine(agent_)    := LineInTrR (agent_) 
   MessInTrR(agent_) := NO_MESS            (* clear RequestInTransit *)
  endblock


transition SendRequest (agent_) ==
  if MessInTrR (agent_) = GET_MSG 
     and CurPhase(SenderInTrR(agent_), LineInTrR (agent_)) = ready 
     and CCState(SenderInTrR(agent_), LineInTrR (agent_)) = invalid
  then SendR(agent_) 
       CurPhase(SenderInTrR(agent_), LineInTrR (agent_)) := wait
  else if MessInTrR (agent_) = GETX_MSG
          and CurPhase(SenderInTrR(agent_), LineInTrR (agent_)) = ready 
          and CCState(SenderInTrR(agent_), LineInTrR (agent_)) = invalid
       then SendR(agent_) 
            CurPhase(SenderInTrR(agent_), LineInTrR (agent_)) := wait
       else if MessInTrR (agent_) = RPL_MSG
               and CurPhase(SenderInTrR(agent_), LineInTrR (agent_)) = ready 
               and CCState(SenderInTrR(agent_), LineInTrR (agent_)) = shared
            then SendR(agent_)
                 CCState(SenderInTrR(agent_), LineInTrR (agent_)) := invalid
            else if MessInTrR (agent_) = WB_MSG
                    and CurPhase(SenderInTrR(agent_), LineInTrR (agent_)) = ready 
                    and CCState(SenderInTrR(agent_), LineInTrR (agent_)) = exclusive
                 then SendR(agent_)
                      CCState(SenderInTrR(agent_), LineInTrR (agent_)) := invalid
                 endif
            endif
       endif
  endif


(*-- if there is a mess in transit then send
     only if there is no message in transit then send request 
     (this probably works only for non-intra-node comm!)  *)
(*-------------------------------------------------------------*)
(* do_forall version: problems in ki-opt-intermediate -> term2expr and simplify -> simplify_statics... *)

transition synchronize ==
 block
  do forall agent_ in Agent_without_none with true
    if InMess(agent_)=NO_MESS
    then if MessInTr(n(1),agent_) != NO_MESS
         then SendMess(agent_)
         else if MessInTrR(agent_) != NO_MESS and InMess(agent_)=NO_MESS  (* MessInTrR ohnehin nur an Owner! *)
              then SendRequest(agent_)
              endif
         endif
    endif
  enddo
 endblock

(*-------------------------------------------------------------*)

transition main ==
   if toggle=behave
    then 
       behavior
       toggle:=sync
   else 
       synchronize
       toggle:=behave
   endif

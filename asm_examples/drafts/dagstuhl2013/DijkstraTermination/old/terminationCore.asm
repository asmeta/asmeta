/* from CoreASM specification */

asm terminationCore

import ../../../STDL/StandardLibrary

signature:

  enum domain Colors = { BLACK | WHITE }
  enum domain States = { ACTIVE | PASSIVE }

  domain Machine subsetof Integer 
  
  
  
  controlled token : Machine
  controlled colorT : Colors
  controlled colorM : Machine -> Colors
  controlled hasMessage : Machine -> Boolean
  controlled state : Machine -> States
  
derived hasToken : Machine -> Boolean
derived tokenIndicatesFinished : Boolean
derived unsuccesfull :Machine -> Boolean 
derived pred : Machine -> Machine
//derived gt(a,b) = index(a)>index(b)
derived isMaster : Machine -> Boolean

/* 
 Problems with state(.): can become ACTIVE and PASSIVE in the same step.
 Problems with hasMessage(.): if we don't consume messages, they will
 stay forever. If we DO consume messages (say in E), we might receive
 another message in the same step (in C, from the same or another machine),
 hence inconsistent update.
*/

definitions:

domain Machine = {0 : 3}
  
function tokenIndicatesFinished = (colorT=WHITE)
function unsuccesfull($m in Machine) = (isMaster($m) and hasToken($m) and (colorT=BLACK or colorM($m)=BLACK))
//function machines = {a | a in Agents with index(a)!=undef}
//function pred(m) = pick p in machines with index(m)=(index(p)+1)%N
function pred($m in Machine) =  (($m + 1) mod 4)
//function gt(a,b) = index(a)>index(b)
//function isMaster(m) = (index(m)=0)
function isMaster($m in Machine) = ($m = 0)

main rule r_machine =
    forall $m in Machine with true do 
    par
	if hasMessage($m) then						// E: could clear msg
		state($m):=ACTIVE							// A: clash with B
	endif
	
	choose $spontaneous in { true, false } with true do
		if state($m)=ACTIVE and $spontaneous then
			state($m):=PASSIVE					// B: clash with A
		endif
		
	if isMaster($m) and tokenIndicatesFinished then
		done:=true
	if hasToken($m) then
		if state($m)=ACTIVE then
			skip
		else
			PASSTOKEN
	choose spontaneous in { true, false } do
		if state($m)=ACTIVE and spontaneous then
			choose dest in machines do
				SENDMESSAGE(dest)				// C: clash with D
	if isMaster($m) and unsuccessful then
		colorM($m):=WHITE
		colorT:=WHITE
		PASSTOKEN								// D: clash with C
     endpar
     
rule PASSTOKEN($m in Machine) =
	//hasToken($m):=false
	//hasToken(pred($m)):=true
	par
	token := $m
	if colorM($m)=BLACK then
		colorT:=BLACK
	colorM($m):=WHITE
	endif
	endpar

rule SENDMESSAGE(dest) =
	hasMessage(dest):=true
	if (gt(dest,$m)) then
		colorM($m):=BLACK

rule InitialState =
	forall i in [0..(N-1)] do
		extend Agents with m do
			program(m):=@MACHINE
			index(m):=i
			choose s in { ACTIVE, PASSIVE } do					
				state(m):=s
			if i=0 then
				hasToken(m):=true
				colorM(m):=WHITE
			else
				hasToken(m):=false
				colorM(m):=BLACK
	colorT:=WHITE
	program($m):=undef


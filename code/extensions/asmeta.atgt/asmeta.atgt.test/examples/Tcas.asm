// boolean conditions/decision  
// from the paper 

asm Tcas

import StandardLibrary

signature:

	controlled condition: Boolean

	monitored a: Boolean
	monitored b: Boolean
	monitored c: Boolean
	monitored d: Boolean
	monitored e: Boolean
	monitored f0: Boolean 
	monitored g0: Boolean
	monitored h0: Boolean 
	// i is not working neither i0
	monitored o0: Boolean
	monitored k: Boolean
	monitored j: Boolean
	monitored l: Boolean
	monitored m: Boolean
	monitored n: Boolean
	
	
definitions:
	main rule r_Main =
		par
			// tacas1 
			if not (a and b) and ((d and not e and not f0) or (not d and e and not f0) or (not d  and not e and not f0)) and (a and c and (d or e) and h0 or a and (d or e) and not h0 or b and (e or f0 )) then
				condition := true
			endif
			// tacas2 
			if (a and 
					((c or d or e) and g0 or a and f0 or c and (f0 or g0 or h0 or o0)) or 
					(a or b) and (c or d or e) and o0) and 
				not (a and b) and not (c and d) and not (c and e) and not (d and e) and 
				not ( f0  and g0) and not ( f0 and  h0) and not  ( f0  and o0) and not  (g0 and h0) and not  (h0 and o0) then
				condition := true
			endif
			// tacas3
			if (a and (not d or not e or d and e and( not f0 and g0 and h0 and not o0 or not g0 and h0 and o0) and ( not f0 and g0 and l and k or not g0 and not o0 and k)) or ( not f0 and g0 and h0 and not o0 or not g0 and h0 and o0) and ( not f0 and g0 and l and k or not g0 and not o0 and k) and (b or c and not m or f0)) and (a and not b and not c or not a and b and not c or not a and not b and c) then
				condition := true
			endif
			// tacas4 
			if a and (not b or not c) and d or e then
				condition := true
			endif
			// tacas5 
			if a and (not b or not c or b and c and not (not f0 and g0 and h0 and not o0 or not g0 and h0 and o0) and not (not f0 and g0 and l and k or not g0 and not o0 and k)) or f0 then
				condition := true
			endif
			// tacas6 
			if (not a and b or a and not b) and not (c and d) and (f0 and not g0 and not h0 or not f0 and g0 and not h0 or not f0 and not g0 and not h0) and not (j and k) and ((a and c or b and d) and e and (f0 or (o0 and(g0 and j or h0 and k)))) then
				condition := true
			endif
			// tacas7 
			if (not a and b or a and not b) and not (c and d) and not (g0 and h0) and (j and k) and ((a and c or b and d) and e and (not o0 or not g0 and not k or not j and (not h0 or not k))) then
				condition := true
			endif
			// tacas8 
			if (not a and b or a and not b) and not (c and d) and not (g0 and h0) and ((a and c or b and d) and e and (f0 and g0 or not f0 and h0)) then
				condition := true
			endif
			// tacas9 
			if not (c and d) and (not e and f0 and not g0 and not a and (b and c or not b and d)) then
				condition := true
			endif
			// tacas10 
			if a and not b and not c and d and not e and f0 and (g0 or not g0 and (h0 or o0)) and not (j and k or not j and l or m) then
				condition := true
			endif
			// tacas11 
			if a and not b and not c and ( not (f0 and (g0 or not g0 and (h0 or o0))) or f0 and (g0 or not g0 and (h0 or o0)) and not d and not e) and not (j and k or not j and l and not m) then
				condition := true
			endif
			// tacas12 
			if a and b and not c and (f0 and (g0 or g0 and (h0 or o0)) and (((not e) and not n) or d) or not n) and ((j and k) or (not j and l and not m)) then
				condition := true
			endif
			// tacas13 
			if a or b or c or not c and not d and e and f0 and not g0 and not h0 or o0 and(j or k) and not l then
				condition := true
			endif
			// tacas14 
			if a and c and (d or e) and h0 or a and (d or e) and not h0 or b and (e or f0) then
				condition := true
			endif
			// tacas15 
			if a and ((c or d or e) and g0 or a and f0 or c and (f0 or g0 or h0 or o0)) or (a or b) and (c or d or e) and o0 then
				condition := true
			endif
			// tacas16 
			if a and (not d or not e or d and e and not (not f0 and g0 and h0 and not o0 or not g0 and h0 and o0) and not (not f0 and g0 and l and k or not g0 and not o0 and k)) or not (not f0 and g0 and h0 and not o0 or not g0 and h0 and o0) and not (not f0 and g0 and l and k or not g0 and not o0 and k) and (b or c and not m or f0) then
				condition := true
			endif
			// tacas17 
			if (a and c or b and d) and e and (f0 or (o0 and (g0 and j or h0 and k))) then
				condition := true
			endif
			// tacas18 
			if (a and c or b and d) and e and (not o0 or not g0 and not k or not j and (not h0 or not k)) then
				condition := true
			endif
			// tacas19 
			if (a and c or b and d) and e and (f0 and g0 or not f0 and h0) then
				condition := true
			endif
			// tacas20 
			if not e and f0 and not g0 and not a and (b and c or not b and d) then		 		
				condition := true
			endif
		endpar

default init s0:

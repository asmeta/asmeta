asm setTerm
	
import ../../../STDL/StandardLibrary
	
signature:
	


definitions:

rule r_r1 = choose $delta in {-3.0 : -1.0} with true do skip
	
rule r_r2 = choose $delta in {-3.0 : 1.0} with true do skip

rule r_r3 = choose $delta in {3.0 : 5.0} with true do skip

rule r_r4 = choose $delta in {3.0 : +5.0} with true do skip

rule r_r11 = choose $delta in {-3 : -1} with true do skip
	
rule r_r21 = choose $delta in {-3 : 1} with true do skip

rule r_r31 = choose $delta in {3 : 5} with true do skip

rule r_r42 = choose $delta in {3n : 6n} with true do skip

rule r_r5 = choose $delta in {} with true do skip

rule r_r12 = choose $delta in {-3.0 : -1.0,1.2} with true do skip
	
rule r_r22 = choose $delta in {-3.0 : 1.0,+2.0} with true do skip

//rule r_r32 = choose $delta in {-3.0 : +5.0,0.0} with true do skip Error! The step must be a positive number.

rule r_r112 = choose $delta in {-3 : -1,2} with true do skip
	
rule r_r212 = choose $delta in {-3 : 1,+2} with true do skip

rule r_r312 = choose $delta in {3 : 5,2n} with true do skip

rule r_r422 = choose $delta in {3n : 6n,2n} with true do skip

rule r_r423 = choose $delta in {3n : 6n,2} with true do skip

rule r_r424 = choose $delta in {3n : 6n,+2} with true do skip

main rule r_wp = choose $delta in {3n : 6n,2} with true do skip

package org.asmeta.nusmv.main;

import org.asmeta.parser.util.RuleCounter;

/**
 * Non viene istanziata da nessuno. Usata per l'articolo smv4val per contare
 * le regole.
 *
 */
//class RuleCounter extends ReflectiveVisitor {
class RuleCounterTest extends org.asmeta.parser.util.ReflectiveVisitor {

	public static void main(String[] args) throws Exception {
		RuleCounter rc = new RuleCounter();
		
		String FILE_BASE_UNIBG = "./examples/smv4val/unibgStudents/";
		System.out.println("Contatore_U_DA_H " + rc.getNumOfRules(FILE_BASE_UNIBG+"Contatore_U_DA_H.asm"));
		System.out.println("Prova "+rc.getNumOfRules(FILE_BASE_UNIBG+"Prova.asm"));
		System.out.println("RegistroDiCassa "+rc.getNumOfRules(FILE_BASE_UNIBG+"RegistroDiCassa.asm"));
		System.out.println("Rubrica "+rc.getNumOfRules(FILE_BASE_UNIBG+"Rubrica.asm"));
		System.out.println("stufa "+rc.getNumOfRules(FILE_BASE_UNIBG+"stufa.asm"));
		System.out.println("VideotecaASM "+rc.getNumOfRules(FILE_BASE_UNIBG+"VideotecaASM.asm"));
		
		String FILE_BASE_REP = "./examples/smv4val/repository/";
		System.out.println("AdvancedClock "+rc.getNumOfRules(FILE_BASE_REP + "examples/simple_ex/AdvancedClock.asm"));
		System.out.println("AdvancedClock2 "+rc.getNumOfRules(FILE_BASE_REP + "examples/simple_ex/AdvancedClock2.asm"));
		System.out.println("ATM "+rc.getNumOfRules(FILE_BASE_REP + "examples/simple_ex/ATM.asm"));
		System.out.println("Axioms "+rc.getNumOfRules(FILE_BASE_REP + "examples/simple_ex/Axioms.asm"));
		System.out.println("fattoriale "+rc.getNumOfRules(FILE_BASE_REP + "examples/simple_ex/fattoriale.asm"));
		System.out.println("FLIP_FLOP_0 "+rc.getNumOfRules(FILE_BASE_REP + "examples/simple_ex/FLIP_FLOP_0.asm"));
		System.out.println("IncosistentUpdate "+rc.getNumOfRules(FILE_BASE_REP + "examples/simple_ex/IncosistentUpdate.asm"));
		System.out.println("lift2 "+rc.getNumOfRules(FILE_BASE_REP + "examples/models/lift2.asm"));
		System.out.println("SIS "+rc.getNumOfRules(FILE_BASE_REP + "examples/models/SIS.asm"));
		System.out.println("FSM_hooking2 "+rc.getNumOfRules(FILE_BASE_REP + "examples/fsmsemantics/FSM_hooking2.asm"));
		System.out.println("FSM_map0_2 "+rc.getNumOfRules(FILE_BASE_REP + "examples/fsmsemantics/FSM_map0_2.asm"));
		System.out.println("philosophers1 "+rc.getNumOfRules(FILE_BASE_REP + "examples/philosophers/philosophers1.asm"));
		System.out.println("sluiceGateGround "+rc.getNumOfRules(FILE_BASE_REP + "examples/sluicegate/sluiceGateGround.asm"));
		System.out.println("sluiceGateMotorCtl "+rc.getNumOfRules(FILE_BASE_REP + "examples/sluicegate/sluiceGateMotorCtl.asm"));
		System.out.println("oneWayTrafficLight "+rc.getNumOfRules(FILE_BASE_REP + "examples/traffic_light/oneWayTrafficLight.asm"));
		System.out.println("oneWayTrafficLight2 "+rc.getNumOfRules(FILE_BASE_REP + "examples/traffic_light/oneWayTrafficLight2.asm"));
		System.out.println("oneWayTrafficLight_refined "+rc.getNumOfRules(FILE_BASE_REP + "examples/traffic_light/oneWayTrafficLight_refined.asm"));
		System.out.println("oneWayTrafficLight_refined_with_agents "+rc.getNumOfRules(FILE_BASE_REP + "examples/traffic_light/oneWayTrafficLight_refined_with_agents.asm"));

		String FILE_BASE_BENCH = "./examples/smv4val/";
		System.out.println("caseIsComplete "+rc.getNumOfRules(FILE_BASE_BENCH + "caseIsComplete.asm"));
		System.out.println("chooseEmpty "+rc.getNumOfRules(FILE_BASE_BENCH + "chooseEmpty.asm"));
		System.out.println("condIsComplete "+rc.getNumOfRules(FILE_BASE_BENCH + "caseIsComplete.asm"));
		System.out.println("condIsEvalToTrue "+rc.getNumOfRules(FILE_BASE_BENCH + "condIsEvalToTrue.asm"));
		System.out.println("contrLocAllValues "+rc.getNumOfRules(FILE_BASE_BENCH + "contrLocAllValues.asm"));
		System.out.println("contrLocIsUpdated "+rc.getNumOfRules(FILE_BASE_BENCH + "contrLocIsUpdated.asm"));
		System.out.println("controlled1 "+rc.getNumOfRules(FILE_BASE_BENCH + "controlled1.asm"));
		System.out.println("controlledLocationCouldBeStatic "+rc.getNumOfRules(FILE_BASE_BENCH + "controlledLocationCouldBeStatic.asm"));
		System.out.println("domain1 "+rc.getNumOfRules(FILE_BASE_BENCH + "domain1.asm"));
		System.out.println("forallEmpty "+rc.getNumOfRules(FILE_BASE_BENCH + "forallEmpty.asm"));
		System.out.println("inconsistentUpdates "+rc.getNumOfRules(FILE_BASE_BENCH + "inconsistentUpdates.asm"));
		System.out.println("locationCouldBeRemoved "+rc.getNumOfRules(FILE_BASE_BENCH + "locationCouldBeRemoved.asm"));
		System.out.println("macroRuleCalled "+rc.getNumOfRules(FILE_BASE_BENCH + "macroRuleCalled.asm"));
		System.out.println("macroRuleReached "+rc.getNumOfRules(FILE_BASE_BENCH + "macroRuleReached.asm"));
		System.out.println("oneWayTrafficLight_refined_with_agents_InconUpdates "+rc.getNumOfRules(FILE_BASE_BENCH + "oneWayTrafficLight_refined_with_agents_InconUpdates.asm"));
		System.out.println("ruleIsReached "+rc.getNumOfRules(FILE_BASE_BENCH + "ruleIsReached.asm"));
		System.out.println("statDerIsUsed "+rc.getNumOfRules(FILE_BASE_BENCH + "statDerIsUsed.asm"));
		System.out.println("taxiInconsistentUpate "+rc.getNumOfRules(FILE_BASE_BENCH + "taxiInconsistentUpate.asm"));
		System.out.println("trivialUpdate "+rc.getNumOfRules(FILE_BASE_BENCH + "trivialUpdate.asm"));
		System.out.println("usedDomain "+rc.getNumOfRules(FILE_BASE_BENCH + "usedDomain.asm"));
		System.out.println("usedDomain2 "+rc.getNumOfRules(FILE_BASE_BENCH + "usedDomain2.asm"));

	}	
}

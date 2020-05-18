package org.asmeta.modeladvisor.metaproperties;

import static java.lang.System.out;

import java.util.Map;

import asmeta.transitionrules.basictransitionrules.MacroDeclaration;

public class MacroRuleCalled {
	public Map<MacroDeclaration, Integer> numOfCalls;
	
	public MacroRuleCalled(Map<MacroDeclaration, Integer> numOfCalls) {
		this.numOfCalls = numOfCalls;
	}

	/**
	 * Stampa le macro rule che sono dichiarate ma che non sono chiamate in
	 * nessuna porzione di codice.
	 * Attenzione: la macro rule potrebbe anche essere chiamata in una porzione
	 * di codice irraggiungibile. Non e' questa metaproprieta' che indica tali
	 * situazioni. Vedere, invece, macroCallRuleIsReached.
	 */
	public void evaluation(){
		out.println("MP: Macro rule has been called.");
		boolean noViolation = true;
		for(MacroDeclaration md: numOfCalls.keySet()) {
			if(numOfCalls.get(md) == 0) {
				out.println("Macro rule " + md.getName() + " has never been called.");
				noViolation = false;
			}
		}
		if(noViolation) {
			out.println("NONE.");
		}
		out.println();
	}
}
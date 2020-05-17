package asmeta;

import asmeta.definitions.DefinitionsFactory;
import asmeta.structure.StructureFactory;
import asmeta.terms.TermsFactory;
import asmeta.transitionrules.TransitionRulesFactory;

/**
 * questa classe serve solamente perche' EMF non genera le istanze dei package
 * senza classi non servirebbe perche' poi richiama le eInstance corrette nei
 * package contenuti
 * 
 * @author pscandurra
 *
 */
public class AsmetaFactory {
	public static AsmetaFactory eINSTANCE = init();

	private static AsmetaFactory init() {
		return new AsmetaFactory();
	}

	/**
	 * Returns nested package Terms.
	 * 
	 * @return Proxy object related to nested package Terms.
	 */
	public asmeta.terms.TermsFactory getTerms() {
		return TermsFactory.eINSTANCE;
	}

	/**
	 * Returns nested package Structure.
	 * 
	 * @return Proxy object related to nested package Structure.
	 */
	public asmeta.structure.StructureFactory getStructure() {
		return StructureFactory.eINSTANCE;
	}

	/**
	 * Returns nested package TransitionRules.
	 * 
	 * @return Proxy object related to nested package TransitionRules.
	 */
	public asmeta.transitionrules.TransitionRulesFactory getTransitionRules() {
		return TransitionRulesFactory.eINSTANCE;
	}

	/**
	 * Returns nested package Definitions.
	 * 
	 * @return Proxy object related to nested package Definitions.
	 */
	public asmeta.definitions.DefinitionsFactory getDefinitions() {
		return DefinitionsFactory.eINSTANCE;
	}
}

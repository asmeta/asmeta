package asmeta.transitionrules;

import asmeta.transitionrules.basictransitionrules.BasictransitionrulesFactory;
import asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesFactory;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesFactory;

public class TransitionRulesFactory {

	public static TransitionRulesFactory eINSTANCE = init();

	private static TransitionRulesFactory init() {
		return new TransitionRulesFactory();
	}
	
    public asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesFactory getTurboTransitionRules(){
		return TurbotransitionrulesFactory.eINSTANCE;
	}
    public asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesFactory getDerivedTransitionRules(){
		return DerivedtransitionrulesFactory.eINSTANCE;
	}
    public asmeta.transitionrules.basictransitionrules.BasictransitionrulesFactory getBasicTransitionRules(){
		return BasictransitionrulesFactory.eINSTANCE;
	}
}

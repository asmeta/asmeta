package org.asmeta.flattener.rule;

import org.asmeta.flattener.RemoveArgumentsFlattener;
import org.asmeta.flattener.nesting.RemoveNestingFlattener;

import asmeta.structure.Asm;

/**
 * 
 * An Asmeta flattener transform a given ASM to another (the original is givne in the constructor (TODO)
 *
 */
public interface AsmetaFlattener {
	


	// TODO this should be an abstract class not an interface - use composition and noy inheriatnce AG
	//protected Asm asm;
	
	//protected AsmetaFlattener(Asm asm) {
//		this.asm = asm;
//	}
	
	
	public abstract Asm flattenASM();	
	
	/** return the id of this flattener (two letetrs normally */
	public abstract String getCode();
	
	// TODO altro costruttore flatten che prende una lista di flattener
	// istanziati(oggetti) e non class
	public static String getFlattenerCode(Class<? extends AsmetaFlattener> flattener) {
		if (flattener == MacroCallRuleFlattener.class) {
			return "MCR";
		} else if (flattener == ForallRuleFlattener.class) {
			return "FR";
		} else if (flattener == ChooseRuleFlattener.class) {
			return "ChR";
		} else if (flattener == RemoveArgumentsFlattener.class) {
			return "AR";
		} else if (flattener == LetRuleFlattener.class) {
			return "LR";
		} else if (flattener == CaseRuleFlattener.class) {
			return "CaR";
		} else if (flattener == RemoveNestingFlattener.class) {
			return "NR";
		} else if (flattener == RuleSimplifier.class) {
			return "RS";
		} else {
			throw new Error("Flattener " + flattener.getSimpleName() + " not supported.");
		}
	}
}

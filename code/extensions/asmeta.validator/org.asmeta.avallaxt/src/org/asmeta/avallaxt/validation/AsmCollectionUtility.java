package org.asmeta.avallaxt.validation;

import java.util.HashMap;
import java.util.Map;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;

public class AsmCollectionUtility {

	/**
	 * returns all the choose rules in the main ASM of the collection with the name
	 * of the macro rule declaration in which they are defined
	 * 
	 * @param asmCollection the ASM collection
	 * @return a map with the choose rule as key and the name of the macro rule
	 *         declaration as value
	 */
	public static Map<ChooseRule, String> getChooseRules(AsmCollection asmCollection) {
		Map<ChooseRule, String> allChooseRules = new HashMap<>();
		for (RuleDeclaration rd : asmCollection.getMain().getBodySection().getRuleDeclaration()) {
			if (rd instanceof MacroDeclaration)
				for (Rule r : RuleExtractorFromMacroDecl.getAllContainedRules((MacroDeclaration) rd))
					if (r instanceof ChooseRule)
						allChooseRules.put((ChooseRule) r, rd.getName());
		}
		return allChooseRules;
	}

}

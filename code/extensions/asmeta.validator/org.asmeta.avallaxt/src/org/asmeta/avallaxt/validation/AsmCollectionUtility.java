package org.asmeta.avallaxt.validation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;

public class AsmCollectionUtility {

	/**
	 * returns all the choose rules in the main ASM of the collection with the
	 * signature of the macro rule declaration in which they are defined
	 * 
	 * @param asmCollection the ASM collection
	 * @return a map with the choose rule as key and the signature of the macro rule
	 *         declaration as value
	 */
	public static Map<ChooseRule, String> getChooseRules(AsmCollection asmCollection) {
		Map<ChooseRule, String> allChooseRules = new LinkedHashMap<>(); // Keep the ordering
		for (RuleDeclaration rd : asmCollection.getMain().getBodySection().getRuleDeclaration()) {
			// Get the String representing the signature (e.g. r_MyRule_Boolean_Integer)
			String signature = getSignature(rd);
			// Add the choose rules contained in the rule declaration
			if (rd instanceof MacroDeclaration)
				for (Rule r : RuleExtractorFromMacroDecl.getAllContainedRules((MacroDeclaration) rd))
					if (r instanceof ChooseRule)
						allChooseRules.put((ChooseRule) r, signature);
		}
		return allChooseRules;
	}

	/**
	 * Obtain the signature of a rule declaration (e.g. r_myRule(Integer, Boolean))
	 * 
	 * @param rd the rule declaration
	 * @return the signature
	 */
	public static String getSignature(RuleDeclaration rd) {
		String name = rd.getName();
		List<String> params = new ArrayList<>();
		for (VariableTerm variable : rd.getVariable()) {
			params.add(variable.getDomain().getName());
		}
		String signature = name + (params.size() > 0 ? ("_" + String.join("_", params)) : "");
		return signature;
	}

}

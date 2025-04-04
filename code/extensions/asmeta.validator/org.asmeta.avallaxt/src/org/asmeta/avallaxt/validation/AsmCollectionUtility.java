package org.asmeta.avallaxt.validation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.asmeta.avallaxt.avalla.Pick;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;

public class AsmCollectionUtility {

	/**
	 * returns all the choose rules in the main ASM along with the signature of the
	 * macro rule declaration in which they are defined
	 * 
	 * @param asmCollection the ASM collection
	 * @return a map with the choose rule as key and the signature of the macro rule
	 *         declaration as value
	 */
	public static Map<ChooseRule, String> getAllChooseRules(AsmCollection asmCollection) {
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
	 * returns all the choose rules in the main ASM with at least a variable that
	 * have been picked at least once along with the signature of the macro rule
	 * declaration in which they are defined
	 * 
	 * @param asmCollection     the ASM collection
	 * @param allPickStatements all pick statements used in the scenario
	 * @return a map with the choose rule as key and the signature of the macro rule
	 *         declaration as value
	 */
	public static Map<ChooseRule, String> getPickedChooseRules(AsmCollection asmCollection,
			List<Pick> allPickStatements) {
		Map<ChooseRule, String> allChooseRules = getAllChooseRules(asmCollection);
		Map<ChooseRule, String> pickedChooseRules = new LinkedHashMap<>();
		for (Entry<ChooseRule, String> r : allChooseRules.entrySet()) {
			ChooseRule chooseRule = r.getKey();
			String signature = r.getValue();
			// Check if at least one variable has been picked at least one time
			List<String> definedVars = chooseRule.getVariable().stream().map(var -> var.getName())
					.collect(Collectors.toList());
			for (Pick pick : allPickStatements) {
				String pickVar = pick.getVar();
				String pickRule = pick.getRule();
				if (definedVars.contains(pickVar) && (pickRule == null
						|| pickRule.replaceAll("\\(|\\,", "_").replace(")", "").equals(signature))) {
					pickedChooseRules.put(chooseRule, signature);
					break;
				}
			}
		}
		return pickedChooseRules;
	}

	/**
	 * Obtain the signature of a rule declaration (e.g. r_MyRule_Boolean_Integer)
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

package org.asmeta.xt.validator;

import asmeta.definitions.RuleDeclaration;
import asmeta.terms.basicterms.VariableTerm;

/**
 * This class contains useful methods when working with RuleDeclarations during
 * validation
 *
 */
public class RuleDeclarationUtils {

	/**
	 * Compute the complete name of a rule declaration.
	 *
	 * @param rd the rule declaration
	 * @return the complete name as asn_name::rule_signature
	 */
	public static String getCompleteName(RuleDeclaration rd) {
		String asmName = rd.getAsmBody().getAsm().getName();
		if (asmName.contains(AsmetaFromAvallaBuilder.TEMP_ASMETA_V)) { // For asm built from avalla
			asmName = asmName.substring(0, asmName.indexOf(AsmetaFromAvallaBuilder.TEMP_ASMETA_V));
		} else if (asmName.startsWith("_")) { // For imported asm
			asmName = asmName.substring(1, asmName.lastIndexOf('_'));
		}
		String signature = rd.getName() + "(";
		for (VariableTerm variable : rd.getVariable()) {
			signature += variable.getDomain().getName() + ",";
		}
		if (signature.endsWith(","))
			signature = signature.substring(0, signature.length() - 1);
		signature += ")";
		return asmName + "::" + signature;
	}

}

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules.validation;

import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * A sample validator interface for {@link asmeta.transitionrules.turbotransitionrules.IterateRule}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface IterateRuleValidator {
	boolean validate();

	boolean validateRule(Rule value);
}

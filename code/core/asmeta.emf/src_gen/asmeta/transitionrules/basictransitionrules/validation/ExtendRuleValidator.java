/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules.validation;

import org.eclipse.emf.common.util.EList;

import asmeta.definitions.domains.Domain;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * A sample validator interface for {@link asmeta.transitionrules.basictransitionrules.ExtendRule}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ExtendRuleValidator {
	boolean validate();

	boolean validateExtendedDomain(Domain value);
	boolean validateBoundVar(EList<VariableTerm> value);
	boolean validateDoRule(Rule value);
}

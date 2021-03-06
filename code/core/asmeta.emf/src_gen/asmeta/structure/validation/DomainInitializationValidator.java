/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure.validation;

import asmeta.definitions.domains.ConcreteDomain;
import asmeta.structure.Initialization;
import asmeta.terms.basicterms.Term;

/**
 * A sample validator interface for {@link asmeta.structure.DomainInitialization}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface DomainInitializationValidator {
	boolean validate();

	boolean validateInitializedDomain(ConcreteDomain value);
	boolean validateBody(Term value);
	boolean validateInitialState(Initialization value);
}

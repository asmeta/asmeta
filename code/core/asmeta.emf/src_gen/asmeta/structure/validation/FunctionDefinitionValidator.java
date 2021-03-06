/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure.validation;

import org.eclipse.emf.common.util.EList;

import asmeta.definitions.Function;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;

/**
 * A sample validator interface for {@link asmeta.structure.FunctionDefinition}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface FunctionDefinitionValidator {
	boolean validate();

	boolean validateBody(Term value);
	boolean validateVariable(EList<VariableTerm> value);
	boolean validateDefinedFunction(Function value);
}

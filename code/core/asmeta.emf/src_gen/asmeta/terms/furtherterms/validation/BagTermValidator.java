/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms.validation;

import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.Term;

/**
 * A sample validator interface for {@link asmeta.terms.furtherterms.BagTerm}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface BagTermValidator {
	boolean validate();

	boolean validateTerm(EList<Term> value);
}

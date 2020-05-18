/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains.validation;

import asmeta.definitions.domains.Domain;

/**
 * A sample validator interface for {@link asmeta.definitions.domains.MapDomain}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface MapDomainValidator {
	boolean validate();

	boolean validateSourceDomain(Domain value);
	boolean validateTargetDomain(Domain value);
}
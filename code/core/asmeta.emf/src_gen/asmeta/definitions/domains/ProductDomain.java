/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Product Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.ProductDomain#getDomains <em>Domains</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.domains.DomainsPackage#getProductDomain()
 * @model
 * @generated
 */
public interface ProductDomain extends StructuredTd {
	/**
	 * Returns the value of the '<em><b>Domains</b></em>' attribute list.
	 * The list contents are of type {@link asmeta.definitions.domains.Domain}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domains</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domains</em>' attribute list.
	 * @see asmeta.definitions.domains.DomainsPackage#getProductDomain_Domains()
	 * @model unique="false" dataType="asmeta.definitions.domains.DomainDT"
	 * @generated
	 */
	EList<Domain> getDomains();

} // ProductDomain

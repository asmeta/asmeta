/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bag Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.BagDomain#getDomain <em>Domain</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.domains.DomainsPackage#getBagDomain()
 * @model
 * @generated
 */
public interface BagDomain extends StructuredTd {
	/**
	 * Returns the value of the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' reference.
	 * @see #setDomain(Domain)
	 * @see asmeta.definitions.domains.DomainsPackage#getBagDomain_Domain()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Domain getDomain();

	/**
	 * Sets the value of the '{@link asmeta.definitions.domains.BagDomain#getDomain <em>Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' reference.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(Domain value);

} // BagDomain

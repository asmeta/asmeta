/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Powerset Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.PowersetDomain#getBaseDomain <em>Base Domain</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.domains.DomainsPackage#getPowersetDomain()
 * @model
 * @generated
 */
public interface PowersetDomain extends StructuredTd {
	/**
	 * Returns the value of the '<em><b>Base Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Domain</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Domain</em>' reference.
	 * @see #setBaseDomain(Domain)
	 * @see asmeta.definitions.domains.DomainsPackage#getPowersetDomain_BaseDomain()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Domain getBaseDomain();

	/**
	 * Sets the value of the '{@link asmeta.definitions.domains.PowersetDomain#getBaseDomain <em>Base Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Domain</em>' reference.
	 * @see #getBaseDomain()
	 * @generated
	 */
	void setBaseDomain(Domain value);

} // PowersetDomain

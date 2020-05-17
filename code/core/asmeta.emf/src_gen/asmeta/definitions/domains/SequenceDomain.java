/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sequence Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.SequenceDomain#getDomain <em>Domain</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.domains.DomainsPackage#getSequenceDomain()
 * @model
 * @generated
 */
public interface SequenceDomain extends StructuredTd {
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
	 * @see asmeta.definitions.domains.DomainsPackage#getSequenceDomain_Domain()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Domain getDomain();

	/**
	 * Sets the value of the '{@link asmeta.definitions.domains.SequenceDomain#getDomain <em>Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' reference.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(Domain value);

} // SequenceDomain

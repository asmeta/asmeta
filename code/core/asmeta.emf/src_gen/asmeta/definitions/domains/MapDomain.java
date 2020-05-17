/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Map Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.MapDomain#getSourceDomain <em>Source Domain</em>}</li>
 *   <li>{@link asmeta.definitions.domains.MapDomain#getTargetDomain <em>Target Domain</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.domains.DomainsPackage#getMapDomain()
 * @model
 * @generated
 */
public interface MapDomain extends StructuredTd {
	/**
	 * Returns the value of the '<em><b>Source Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Domain</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Domain</em>' reference.
	 * @see #setSourceDomain(Domain)
	 * @see asmeta.definitions.domains.DomainsPackage#getMapDomain_SourceDomain()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Domain getSourceDomain();

	/**
	 * Sets the value of the '{@link asmeta.definitions.domains.MapDomain#getSourceDomain <em>Source Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Domain</em>' reference.
	 * @see #getSourceDomain()
	 * @generated
	 */
	void setSourceDomain(Domain value);

	/**
	 * Returns the value of the '<em><b>Target Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Domain</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Domain</em>' reference.
	 * @see #setTargetDomain(Domain)
	 * @see asmeta.definitions.domains.DomainsPackage#getMapDomain_TargetDomain()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Domain getTargetDomain();

	/**
	 * Sets the value of the '{@link asmeta.definitions.domains.MapDomain#getTargetDomain <em>Target Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Domain</em>' reference.
	 * @see #getTargetDomain()
	 * @generated
	 */
	void setTargetDomain(Domain value);

} // MapDomain

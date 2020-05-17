/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Td</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.AbstractTd#getIsDynamic <em>Is Dynamic</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.domains.DomainsPackage#getAbstractTd()
 * @model
 * @generated
 */
public interface AbstractTd extends TypeDomain {
	/**
	 * Returns the value of the '<em><b>Is Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Dynamic</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Dynamic</em>' attribute.
	 * @see #setIsDynamic(Boolean)
	 * @see asmeta.definitions.domains.DomainsPackage#getAbstractTd_IsDynamic()
	 * @model unique="false" dataType="primitivetypes.Boolean" required="true" ordered="false"
	 * @generated
	 */
	Boolean getIsDynamic();

	/**
	 * Sets the value of the '{@link asmeta.definitions.domains.AbstractTd#getIsDynamic <em>Is Dynamic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Dynamic</em>' attribute.
	 * @see #getIsDynamic()
	 * @generated
	 */
	void setIsDynamic(Boolean value);

} // AbstractTd

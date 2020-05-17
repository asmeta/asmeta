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
 * A representation of the model object '<em><b>Enum Td</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.EnumTd#getElement <em>Element</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.domains.DomainsPackage#getEnumTd()
 * @model
 * @generated
 */
public interface EnumTd extends TypeDomain {
	/**
	 * Returns the value of the '<em><b>Element</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.definitions.domains.EnumElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element</em>' containment reference list.
	 * @see asmeta.definitions.domains.DomainsPackage#getEnumTd_Element()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	EList<EnumElement> getElement();

} // EnumTd

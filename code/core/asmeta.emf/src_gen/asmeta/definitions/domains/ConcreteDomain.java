/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains;

import org.eclipse.emf.common.util.EList;

import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Concrete Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.ConcreteDomain#getInitialization <em>Initialization</em>}</li>
 *   <li>{@link asmeta.definitions.domains.ConcreteDomain#getDefinition <em>Definition</em>}</li>
 *   <li>{@link asmeta.definitions.domains.ConcreteDomain#getTypeDomain <em>Type Domain</em>}</li>
 *   <li>{@link asmeta.definitions.domains.ConcreteDomain#getIsDynamic <em>Is Dynamic</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.domains.DomainsPackage#getConcreteDomain()
 * @model
 * @generated
 */
public interface ConcreteDomain extends Domain {
	/**
	 * Returns the value of the '<em><b>Initialization</b></em>' reference list.
	 * The list contents are of type {@link asmeta.structure.DomainInitialization}.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.DomainInitialization#getInitializedDomain <em>Initialized Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initialization</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initialization</em>' reference list.
	 * @see asmeta.definitions.domains.DomainsPackage#getConcreteDomain_Initialization()
	 * @see asmeta.structure.DomainInitialization#getInitializedDomain
	 * @model opposite="initializedDomain" ordered="false"
	 * @generated
	 */
	EList<DomainInitialization> getInitialization();

	/**
	 * Returns the value of the '<em><b>Definition</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.DomainDefinition#getDefinedDomain <em>Defined Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Definition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Definition</em>' reference.
	 * @see #setDefinition(DomainDefinition)
	 * @see asmeta.definitions.domains.DomainsPackage#getConcreteDomain_Definition()
	 * @see asmeta.structure.DomainDefinition#getDefinedDomain
	 * @model opposite="definedDomain" ordered="false"
	 * @generated
	 */
	DomainDefinition getDefinition();

	/**
	 * Sets the value of the '{@link asmeta.definitions.domains.ConcreteDomain#getDefinition <em>Definition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Definition</em>' reference.
	 * @see #getDefinition()
	 * @generated
	 */
	void setDefinition(DomainDefinition value);

	/**
	 * Returns the value of the '<em><b>Type Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Domain</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Domain</em>' reference.
	 * @see #setTypeDomain(TypeDomain)
	 * @see asmeta.definitions.domains.DomainsPackage#getConcreteDomain_TypeDomain()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	TypeDomain getTypeDomain();

	/**
	 * Sets the value of the '{@link asmeta.definitions.domains.ConcreteDomain#getTypeDomain <em>Type Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Domain</em>' reference.
	 * @see #getTypeDomain()
	 * @generated
	 */
	void setTypeDomain(TypeDomain value);

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
	 * @see asmeta.definitions.domains.DomainsPackage#getConcreteDomain_IsDynamic()
	 * @model unique="false" dataType="primitivetypes.Boolean" required="true" ordered="false"
	 * @generated
	 */
	Boolean getIsDynamic();

	/**
	 * Sets the value of the '{@link asmeta.definitions.domains.ConcreteDomain#getIsDynamic <em>Is Dynamic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Dynamic</em>' attribute.
	 * @see #getIsDynamic()
	 * @generated
	 */
	void setIsDynamic(Boolean value);

} // ConcreteDomain

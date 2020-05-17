/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure;

import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.domains.ConcreteDomain;
import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain Initialization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.DomainInitialization#getInitializedDomain <em>Initialized Domain</em>}</li>
 *   <li>{@link asmeta.structure.DomainInitialization#getBody <em>Body</em>}</li>
 *   <li>{@link asmeta.structure.DomainInitialization#getInitialState <em>Initial State</em>}</li>
 * </ul>
 *
 * @see asmeta.structure.StructurePackage#getDomainInitialization()
 * @model
 * @generated
 */
public interface DomainInitialization extends EObject {
	/**
	 * Returns the value of the '<em><b>Initialized Domain</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link asmeta.definitions.domains.ConcreteDomain#getInitialization <em>Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initialized Domain</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initialized Domain</em>' reference.
	 * @see #setInitializedDomain(ConcreteDomain)
	 * @see asmeta.structure.StructurePackage#getDomainInitialization_InitializedDomain()
	 * @see asmeta.definitions.domains.ConcreteDomain#getInitialization
	 * @model opposite="initialization" required="true" ordered="false"
	 * @generated
	 */
	ConcreteDomain getInitializedDomain();

	/**
	 * Sets the value of the '{@link asmeta.structure.DomainInitialization#getInitializedDomain <em>Initialized Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initialized Domain</em>' reference.
	 * @see #getInitializedDomain()
	 * @generated
	 */
	void setInitializedDomain(ConcreteDomain value);

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(Term)
	 * @see asmeta.structure.StructurePackage#getDomainInitialization_Body()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Term getBody();

	/**
	 * Sets the value of the '{@link asmeta.structure.DomainInitialization#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Term value);

	/**
	 * Returns the value of the '<em><b>Initial State</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Initialization#getDomainInitialization <em>Domain Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial State</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial State</em>' container reference.
	 * @see #setInitialState(Initialization)
	 * @see asmeta.structure.StructurePackage#getDomainInitialization_InitialState()
	 * @see asmeta.structure.Initialization#getDomainInitialization
	 * @model opposite="domainInitialization" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Initialization getInitialState();

	/**
	 * Sets the value of the '{@link asmeta.structure.DomainInitialization#getInitialState <em>Initial State</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial State</em>' container reference.
	 * @see #getInitialState()
	 * @generated
	 */
	void setInitialState(Initialization value);

} // DomainInitialization

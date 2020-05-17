/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Initialization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.Initialization#getDomainInitialization <em>Domain Initialization</em>}</li>
 *   <li>{@link asmeta.structure.Initialization#getFunctionInitialization <em>Function Initialization</em>}</li>
 *   <li>{@link asmeta.structure.Initialization#getAgentInitialization <em>Agent Initialization</em>}</li>
 *   <li>{@link asmeta.structure.Initialization#getAsm <em>Asm</em>}</li>
 * </ul>
 *
 * @see asmeta.structure.StructurePackage#getInitialization()
 * @model
 * @generated
 */
public interface Initialization extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Domain Initialization</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.structure.DomainInitialization}.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.DomainInitialization#getInitialState <em>Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Initialization</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Initialization</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getInitialization_DomainInitialization()
	 * @see asmeta.structure.DomainInitialization#getInitialState
	 * @model opposite="initialState" containment="true" ordered="false"
	 * @generated
	 */
	EList<DomainInitialization> getDomainInitialization();

	/**
	 * Returns the value of the '<em><b>Function Initialization</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.structure.FunctionInitialization}.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.FunctionInitialization#getInitialState <em>Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function Initialization</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function Initialization</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getInitialization_FunctionInitialization()
	 * @see asmeta.structure.FunctionInitialization#getInitialState
	 * @model opposite="initialState" containment="true" ordered="false"
	 * @generated
	 */
	EList<FunctionInitialization> getFunctionInitialization();

	/**
	 * Returns the value of the '<em><b>Agent Initialization</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.structure.AgentInitialization}.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.AgentInitialization#getInitialState <em>Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agent Initialization</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agent Initialization</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getInitialization_AgentInitialization()
	 * @see asmeta.structure.AgentInitialization#getInitialState
	 * @model opposite="initialState" containment="true" ordered="false"
	 * @generated
	 */
	EList<AgentInitialization> getAgentInitialization();

	/**
	 * Returns the value of the '<em><b>Asm</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Asm#getDefaultInitialState <em>Default Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Asm</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Asm</em>' reference.
	 * @see #setAsm(Asm)
	 * @see asmeta.structure.StructurePackage#getInitialization_Asm()
	 * @see asmeta.structure.Asm#getDefaultInitialState
	 * @model opposite="defaultInitialState" required="true" ordered="false"
	 * @generated
	 */
	Asm getAsm();

	/**
	 * Sets the value of the '{@link asmeta.structure.Initialization#getAsm <em>Asm</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Asm</em>' reference.
	 * @see #getAsm()
	 * @generated
	 */
	void setAsm(Asm value);

} // Initialization

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure;

import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.domains.Domain;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Agent Initialization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.AgentInitialization#getProgram <em>Program</em>}</li>
 *   <li>{@link asmeta.structure.AgentInitialization#getDomain <em>Domain</em>}</li>
 *   <li>{@link asmeta.structure.AgentInitialization#getInitialState <em>Initial State</em>}</li>
 * </ul>
 *
 * @see asmeta.structure.StructurePackage#getAgentInitialization()
 * @model
 * @generated
 */
public interface AgentInitialization extends EObject {
	/**
	 * Returns the value of the '<em><b>Program</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Program</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Program</em>' containment reference.
	 * @see #setProgram(MacroCallRule)
	 * @see asmeta.structure.StructurePackage#getAgentInitialization_Program()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	MacroCallRule getProgram();

	/**
	 * Sets the value of the '{@link asmeta.structure.AgentInitialization#getProgram <em>Program</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Program</em>' containment reference.
	 * @see #getProgram()
	 * @generated
	 */
	void setProgram(MacroCallRule value);

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
	 * @see asmeta.structure.StructurePackage#getAgentInitialization_Domain()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Domain getDomain();

	/**
	 * Sets the value of the '{@link asmeta.structure.AgentInitialization#getDomain <em>Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' reference.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(Domain value);

	/**
	 * Returns the value of the '<em><b>Initial State</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Initialization#getAgentInitialization <em>Agent Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial State</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial State</em>' container reference.
	 * @see #setInitialState(Initialization)
	 * @see asmeta.structure.StructurePackage#getAgentInitialization_InitialState()
	 * @see asmeta.structure.Initialization#getAgentInitialization
	 * @model opposite="agentInitialization" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Initialization getInitialState();

	/**
	 * Sets the value of the '{@link asmeta.structure.AgentInitialization#getInitialState <em>Initial State</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial State</em>' container reference.
	 * @see #getInitialState()
	 * @generated
	 */
	void setInitialState(Initialization value);

} // AgentInitialization

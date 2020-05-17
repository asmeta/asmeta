/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.DynamicFunction;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Initialization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.FunctionInitialization#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link asmeta.structure.FunctionInitialization#getBody <em>Body</em>}</li>
 *   <li>{@link asmeta.structure.FunctionInitialization#getInitializedFunction <em>Initialized Function</em>}</li>
 *   <li>{@link asmeta.structure.FunctionInitialization#getVariable <em>Variable</em>}</li>
 * </ul>
 *
 * @see asmeta.structure.StructurePackage#getFunctionInitialization()
 * @model
 * @generated
 */
public interface FunctionInitialization extends EObject {
	/**
	 * Returns the value of the '<em><b>Initial State</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Initialization#getFunctionInitialization <em>Function Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial State</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial State</em>' container reference.
	 * @see #setInitialState(Initialization)
	 * @see asmeta.structure.StructurePackage#getFunctionInitialization_InitialState()
	 * @see asmeta.structure.Initialization#getFunctionInitialization
	 * @model opposite="functionInitialization" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Initialization getInitialState();

	/**
	 * Sets the value of the '{@link asmeta.structure.FunctionInitialization#getInitialState <em>Initial State</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial State</em>' container reference.
	 * @see #getInitialState()
	 * @generated
	 */
	void setInitialState(Initialization value);

	/**
	 * Returns the value of the '<em><b>Body</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' reference.
	 * @see #setBody(Term)
	 * @see asmeta.structure.StructurePackage#getFunctionInitialization_Body()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Term getBody();

	/**
	 * Sets the value of the '{@link asmeta.structure.FunctionInitialization#getBody <em>Body</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Term value);

	/**
	 * Returns the value of the '<em><b>Initialized Function</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link asmeta.definitions.DynamicFunction#getInitialization <em>Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initialized Function</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initialized Function</em>' reference.
	 * @see #setInitializedFunction(DynamicFunction)
	 * @see asmeta.structure.StructurePackage#getFunctionInitialization_InitializedFunction()
	 * @see asmeta.definitions.DynamicFunction#getInitialization
	 * @model opposite="initialization" required="true" ordered="false"
	 * @generated
	 */
	DynamicFunction getInitializedFunction();

	/**
	 * Sets the value of the '{@link asmeta.structure.FunctionInitialization#getInitializedFunction <em>Initialized Function</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initialized Function</em>' reference.
	 * @see #getInitializedFunction()
	 * @generated
	 */
	void setInitializedFunction(DynamicFunction value);

	/**
	 * Returns the value of the '<em><b>Variable</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.terms.basicterms.VariableTerm}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variable</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getFunctionInitialization_Variable()
	 * @model containment="true"
	 * @generated
	 */
	EList<VariableTerm> getVariable();

} // FunctionInitialization

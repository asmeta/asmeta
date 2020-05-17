/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions;

import org.eclipse.emf.common.util.EList;

import asmeta.structure.FunctionInitialization;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dynamic Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.DynamicFunction#getInitialization <em>Initialization</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.DefinitionsPackage#getDynamicFunction()
 * @model abstract="true"
 * @generated
 */
public interface DynamicFunction extends BasicFunction {
	/**
	 * Returns the value of the '<em><b>Initialization</b></em>' reference list.
	 * The list contents are of type {@link asmeta.structure.FunctionInitialization}.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.FunctionInitialization#getInitializedFunction <em>Initialized Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initialization</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initialization</em>' reference list.
	 * @see asmeta.definitions.DefinitionsPackage#getDynamicFunction_Initialization()
	 * @see asmeta.structure.FunctionInitialization#getInitializedFunction
	 * @model opposite="initializedFunction" ordered="false"
	 * @generated
	 */
	EList<FunctionInitialization> getInitialization();

} // DynamicFunction

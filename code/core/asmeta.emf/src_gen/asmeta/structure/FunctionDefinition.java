/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.Function;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.FunctionDefinition#getBody <em>Body</em>}</li>
 *   <li>{@link asmeta.structure.FunctionDefinition#getVariable <em>Variable</em>}</li>
 *   <li>{@link asmeta.structure.FunctionDefinition#getDefinedFunction <em>Defined Function</em>}</li>
 * </ul>
 *
 * @see asmeta.structure.StructurePackage#getFunctionDefinition()
 * @model
 * @generated
 */
public interface FunctionDefinition extends EObject {
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
	 * @see asmeta.structure.StructurePackage#getFunctionDefinition_Body()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Term getBody();

	/**
	 * Sets the value of the '{@link asmeta.structure.FunctionDefinition#getBody <em>Body</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Term value);

	/**
	 * Returns the value of the '<em><b>Variable</b></em>' reference list.
	 * The list contents are of type {@link asmeta.terms.basicterms.VariableTerm}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variable</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' reference list.
	 * @see asmeta.structure.StructurePackage#getFunctionDefinition_Variable()
	 * @model
	 * @generated
	 */
	EList<VariableTerm> getVariable();

	/**
	 * Returns the value of the '<em><b>Defined Function</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link asmeta.definitions.Function#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Defined Function</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Defined Function</em>' reference.
	 * @see #setDefinedFunction(Function)
	 * @see asmeta.structure.StructurePackage#getFunctionDefinition_DefinedFunction()
	 * @see asmeta.definitions.Function#getDefinition
	 * @model opposite="definition" required="true" ordered="false"
	 * @generated
	 */
	Function getDefinedFunction();

	/**
	 * Sets the value of the '{@link asmeta.structure.FunctionDefinition#getDefinedFunction <em>Defined Function</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defined Function</em>' reference.
	 * @see #getDefinedFunction()
	 * @generated
	 */
	void setDefinedFunction(Function value);

} // FunctionDefinition

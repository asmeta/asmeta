/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms;

import asmeta.definitions.Function;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.basicterms.FunctionTerm#getArguments <em>Arguments</em>}</li>
 *   <li>{@link asmeta.terms.basicterms.FunctionTerm#getFunction <em>Function</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.basicterms.BasictermsPackage#getFunctionTerm()
 * @model
 * @generated
 */
public interface FunctionTerm extends BasicTerm {
	/**
	 * Returns the value of the '<em><b>Arguments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arguments</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arguments</em>' containment reference.
	 * @see #setArguments(TupleTerm)
	 * @see asmeta.terms.basicterms.BasictermsPackage#getFunctionTerm_Arguments()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	TupleTerm getArguments();

	/**
	 * Sets the value of the '{@link asmeta.terms.basicterms.FunctionTerm#getArguments <em>Arguments</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arguments</em>' containment reference.
	 * @see #getArguments()
	 * @generated
	 */
	void setArguments(TupleTerm value);

	/**
	 * Returns the value of the '<em><b>Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function</em>' reference.
	 * @see #setFunction(Function)
	 * @see asmeta.terms.basicterms.BasictermsPackage#getFunctionTerm_Function()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Function getFunction();

	/**
	 * Sets the value of the '{@link asmeta.terms.basicterms.FunctionTerm#getFunction <em>Function</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function</em>' reference.
	 * @see #getFunction()
	 * @generated
	 */
	void setFunction(Function value);

} // FunctionTerm

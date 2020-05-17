/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms;

import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Let Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.furtherterms.LetTerm#getVariable <em>Variable</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.LetTerm#getAssignmentTerm <em>Assignment Term</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.LetTerm#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.furtherterms.FurthertermsPackage#getLetTerm()
 * @model
 * @generated
 */
public interface LetTerm extends VariableBindingTerm {
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
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getLetTerm_Variable()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<VariableTerm> getVariable();

	/**
	 * Returns the value of the '<em><b>Assignment Term</b></em>' reference list.
	 * The list contents are of type {@link asmeta.terms.basicterms.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assignment Term</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assignment Term</em>' reference list.
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getLetTerm_AssignmentTerm()
	 * @model required="true"
	 * @generated
	 */
	EList<Term> getAssignmentTerm();

	/**
	 * Returns the value of the '<em><b>Body</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' reference.
	 * @see #setBody(Term)
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getLetTerm_Body()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Term getBody();

	/**
	 * Sets the value of the '{@link asmeta.terms.furtherterms.LetTerm#getBody <em>Body</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Term value);

} // LetTerm

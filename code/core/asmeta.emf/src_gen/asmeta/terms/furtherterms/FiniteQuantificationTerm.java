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
 * A representation of the model object '<em><b>Finite Quantification Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.furtherterms.FiniteQuantificationTerm#getVariable <em>Variable</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.FiniteQuantificationTerm#getGuard <em>Guard</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.FiniteQuantificationTerm#getRanges <em>Ranges</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.furtherterms.FurthertermsPackage#getFiniteQuantificationTerm()
 * @model abstract="true"
 * @generated
 */
public interface FiniteQuantificationTerm extends VariableBindingTerm {
	/**
	 * Returns the value of the '<em><b>Variable</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.terms.basicterms.VariableTerm}.
	 * It is bidirectional and its opposite is '{@link asmeta.terms.basicterms.VariableTerm#getFiniteQuantificationTerm <em>Finite Quantification Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variable</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' containment reference list.
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getFiniteQuantificationTerm_Variable()
	 * @see asmeta.terms.basicterms.VariableTerm#getFiniteQuantificationTerm
	 * @model opposite="finiteQuantificationTerm" containment="true" required="true"
	 * @generated
	 */
	EList<VariableTerm> getVariable();

	/**
	 * Returns the value of the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guard</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guard</em>' reference.
	 * @see #setGuard(Term)
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getFiniteQuantificationTerm_Guard()
	 * @model ordered="false"
	 * @generated
	 */
	Term getGuard();

	/**
	 * Sets the value of the '{@link asmeta.terms.furtherterms.FiniteQuantificationTerm#getGuard <em>Guard</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard</em>' reference.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(Term value);

	/**
	 * Returns the value of the '<em><b>Ranges</b></em>' attribute list.
	 * The list contents are of type {@link asmeta.terms.basicterms.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ranges</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ranges</em>' attribute list.
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getFiniteQuantificationTerm_Ranges()
	 * @model unique="false" dataType="asmeta.terms.basicterms.TermDT" required="true"
	 * @generated
	 */
	EList<Term> getRanges();

} // FiniteQuantificationTerm

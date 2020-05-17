/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms;

import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.ExtendedTerm;
import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Case Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.furtherterms.CaseTerm#getComparingTerm <em>Comparing Term</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.CaseTerm#getComparedTerm <em>Compared Term</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.CaseTerm#getOtherwiseTerm <em>Otherwise Term</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.CaseTerm#getResultTerms <em>Result Terms</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.furtherterms.FurthertermsPackage#getCaseTerm()
 * @model
 * @generated
 */
public interface CaseTerm extends ExtendedTerm {
	/**
	 * Returns the value of the '<em><b>Comparing Term</b></em>' reference list.
	 * The list contents are of type {@link asmeta.terms.basicterms.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comparing Term</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comparing Term</em>' reference list.
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getCaseTerm_ComparingTerm()
	 * @model required="true"
	 * @generated
	 */
	EList<Term> getComparingTerm();

	/**
	 * Returns the value of the '<em><b>Compared Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compared Term</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compared Term</em>' reference.
	 * @see #setComparedTerm(Term)
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getCaseTerm_ComparedTerm()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Term getComparedTerm();

	/**
	 * Sets the value of the '{@link asmeta.terms.furtherterms.CaseTerm#getComparedTerm <em>Compared Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compared Term</em>' reference.
	 * @see #getComparedTerm()
	 * @generated
	 */
	void setComparedTerm(Term value);

	/**
	 * Returns the value of the '<em><b>Otherwise Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Otherwise Term</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Otherwise Term</em>' reference.
	 * @see #setOtherwiseTerm(Term)
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getCaseTerm_OtherwiseTerm()
	 * @model ordered="false"
	 * @generated
	 */
	Term getOtherwiseTerm();

	/**
	 * Sets the value of the '{@link asmeta.terms.furtherterms.CaseTerm#getOtherwiseTerm <em>Otherwise Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Otherwise Term</em>' reference.
	 * @see #getOtherwiseTerm()
	 * @generated
	 */
	void setOtherwiseTerm(Term value);

	/**
	 * Returns the value of the '<em><b>Result Terms</b></em>' attribute list.
	 * The list contents are of type {@link asmeta.terms.basicterms.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result Terms</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Terms</em>' attribute list.
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getCaseTerm_ResultTerms()
	 * @model unique="false" dataType="asmeta.terms.basicterms.TermDT" required="true"
	 * @generated
	 */
	EList<Term> getResultTerms();

} // CaseTerm

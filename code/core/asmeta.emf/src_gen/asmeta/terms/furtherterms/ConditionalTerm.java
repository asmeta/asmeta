/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms;

import asmeta.terms.basicterms.ExtendedTerm;
import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conditional Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.furtherterms.ConditionalTerm#getElseTerm <em>Else Term</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.ConditionalTerm#getGuard <em>Guard</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.ConditionalTerm#getThenTerm <em>Then Term</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.furtherterms.FurthertermsPackage#getConditionalTerm()
 * @model
 * @generated
 */
public interface ConditionalTerm extends ExtendedTerm {
	/**
	 * Returns the value of the '<em><b>Else Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Else Term</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Else Term</em>' reference.
	 * @see #setElseTerm(Term)
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getConditionalTerm_ElseTerm()
	 * @model ordered="false"
	 * @generated
	 */
	Term getElseTerm();

	/**
	 * Sets the value of the '{@link asmeta.terms.furtherterms.ConditionalTerm#getElseTerm <em>Else Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Else Term</em>' reference.
	 * @see #getElseTerm()
	 * @generated
	 */
	void setElseTerm(Term value);

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
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getConditionalTerm_Guard()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Term getGuard();

	/**
	 * Sets the value of the '{@link asmeta.terms.furtherterms.ConditionalTerm#getGuard <em>Guard</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard</em>' reference.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(Term value);

	/**
	 * Returns the value of the '<em><b>Then Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Then Term</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Then Term</em>' reference.
	 * @see #setThenTerm(Term)
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getConditionalTerm_ThenTerm()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Term getThenTerm();

	/**
	 * Sets the value of the '{@link asmeta.terms.furtherterms.ConditionalTerm#getThenTerm <em>Then Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Then Term</em>' reference.
	 * @see #getThenTerm()
	 * @generated
	 */
	void setThenTerm(Term value);

} // ConditionalTerm

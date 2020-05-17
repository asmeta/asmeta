/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.derivedtransitionrules;

import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Iterative While Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule#getGuard <em>Guard</em>}</li>
 *   <li>{@link asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule#getRule <em>Rule</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage#getIterativeWhileRule()
 * @model
 * @generated
 */
public interface IterativeWhileRule extends TurboDerivedRule {
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
	 * @see asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage#getIterativeWhileRule_Guard()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Term getGuard();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule#getGuard <em>Guard</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard</em>' reference.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(Term value);

	/**
	 * Returns the value of the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' containment reference.
	 * @see #setRule(Rule)
	 * @see asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage#getIterativeWhileRule_Rule()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Rule getRule();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule#getRule <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' containment reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(Rule value);

} // IterativeWhileRule

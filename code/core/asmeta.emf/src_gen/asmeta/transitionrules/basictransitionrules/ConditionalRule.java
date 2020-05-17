/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules;

import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conditional Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.ConditionalRule#getGuard <em>Guard</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.ConditionalRule#getElseRule <em>Else Rule</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.ConditionalRule#getThenRule <em>Then Rule</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getConditionalRule()
 * @model
 * @generated
 */
public interface ConditionalRule extends BasicRule {
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
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getConditionalRule_Guard()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Term getGuard();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.basictransitionrules.ConditionalRule#getGuard <em>Guard</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard</em>' reference.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(Term value);

	/**
	 * Returns the value of the '<em><b>Else Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Else Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Else Rule</em>' containment reference.
	 * @see #setElseRule(Rule)
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getConditionalRule_ElseRule()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Rule getElseRule();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.basictransitionrules.ConditionalRule#getElseRule <em>Else Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Else Rule</em>' containment reference.
	 * @see #getElseRule()
	 * @generated
	 */
	void setElseRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Then Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Then Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Then Rule</em>' containment reference.
	 * @see #setThenRule(Rule)
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getConditionalRule_ThenRule()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Rule getThenRule();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.basictransitionrules.ConditionalRule#getThenRule <em>Then Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Then Rule</em>' containment reference.
	 * @see #getThenRule()
	 * @generated
	 */
	void setThenRule(Rule value);

} // ConditionalRule

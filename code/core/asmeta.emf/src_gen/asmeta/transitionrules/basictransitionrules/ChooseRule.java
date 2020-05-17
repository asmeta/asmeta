/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules;

import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Choose Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.ChooseRule#getIfnone <em>Ifnone</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.ChooseRule#getDoRule <em>Do Rule</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.ChooseRule#getGuard <em>Guard</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.ChooseRule#getVariable <em>Variable</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.ChooseRule#getRanges <em>Ranges</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getChooseRule()
 * @model
 * @generated
 */
public interface ChooseRule extends BasicRule {
	/**
	 * Returns the value of the '<em><b>Ifnone</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ifnone</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ifnone</em>' containment reference.
	 * @see #setIfnone(Rule)
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getChooseRule_Ifnone()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Rule getIfnone();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.basictransitionrules.ChooseRule#getIfnone <em>Ifnone</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ifnone</em>' containment reference.
	 * @see #getIfnone()
	 * @generated
	 */
	void setIfnone(Rule value);

	/**
	 * Returns the value of the '<em><b>Do Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Do Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Do Rule</em>' containment reference.
	 * @see #setDoRule(Rule)
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getChooseRule_DoRule()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Rule getDoRule();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.basictransitionrules.ChooseRule#getDoRule <em>Do Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Do Rule</em>' containment reference.
	 * @see #getDoRule()
	 * @generated
	 */
	void setDoRule(Rule value);

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
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getChooseRule_Guard()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Term getGuard();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.basictransitionrules.ChooseRule#getGuard <em>Guard</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard</em>' reference.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(Term value);

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
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getChooseRule_Variable()
	 * @model required="true"
	 * @generated
	 */
	EList<VariableTerm> getVariable();

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
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getChooseRule_Ranges()
	 * @model unique="false" dataType="asmeta.terms.basicterms.TermDT" required="true"
	 * @generated
	 */
	EList<Term> getRanges();

} // ChooseRule

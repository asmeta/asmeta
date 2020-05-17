/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.derivedtransitionrules;

import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Case Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.derivedtransitionrules.CaseRule#getTerm <em>Term</em>}</li>
 *   <li>{@link asmeta.transitionrules.derivedtransitionrules.CaseRule#getCaseTerm <em>Case Term</em>}</li>
 *   <li>{@link asmeta.transitionrules.derivedtransitionrules.CaseRule#getOtherwiseBranch <em>Otherwise Branch</em>}</li>
 *   <li>{@link asmeta.transitionrules.derivedtransitionrules.CaseRule#getCaseBranches <em>Case Branches</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage#getCaseRule()
 * @model
 * @generated
 */
public interface CaseRule extends BasicDerivedRule {
	/**
	 * Returns the value of the '<em><b>Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Term</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Term</em>' reference.
	 * @see #setTerm(Term)
	 * @see asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage#getCaseRule_Term()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Term getTerm();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.derivedtransitionrules.CaseRule#getTerm <em>Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Term</em>' reference.
	 * @see #getTerm()
	 * @generated
	 */
	void setTerm(Term value);

	/**
	 * Returns the value of the '<em><b>Case Term</b></em>' reference list.
	 * The list contents are of type {@link asmeta.terms.basicterms.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Case Term</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Case Term</em>' reference list.
	 * @see asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage#getCaseRule_CaseTerm()
	 * @model required="true"
	 * @generated
	 */
	EList<Term> getCaseTerm();

	/**
	 * Returns the value of the '<em><b>Otherwise Branch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Otherwise Branch</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Otherwise Branch</em>' containment reference.
	 * @see #setOtherwiseBranch(Rule)
	 * @see asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage#getCaseRule_OtherwiseBranch()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	Rule getOtherwiseBranch();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.derivedtransitionrules.CaseRule#getOtherwiseBranch <em>Otherwise Branch</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Otherwise Branch</em>' containment reference.
	 * @see #getOtherwiseBranch()
	 * @generated
	 */
	void setOtherwiseBranch(Rule value);

	/**
	 * Returns the value of the '<em><b>Case Branches</b></em>' attribute list.
	 * The list contents are of type {@link asmeta.transitionrules.basictransitionrules.Rule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Case Branches</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Case Branches</em>' attribute list.
	 * @see asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage#getCaseRule_CaseBranches()
	 * @model unique="false" dataType="asmeta.transitionrules.basictransitionrules.RuleDT" required="true"
	 * @generated
	 */
	EList<Rule> getCaseBranches();

} // CaseRule

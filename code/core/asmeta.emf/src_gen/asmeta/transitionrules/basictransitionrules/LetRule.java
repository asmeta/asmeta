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
 * A representation of the model object '<em><b>Let Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.LetRule#getInRule <em>In Rule</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.LetRule#getInitExpression <em>Init Expression</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.LetRule#getVariable <em>Variable</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getLetRule()
 * @model
 * @generated
 */
public interface LetRule extends BasicRule {
	/**
	 * Returns the value of the '<em><b>In Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Rule</em>' containment reference.
	 * @see #setInRule(Rule)
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getLetRule_InRule()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Rule getInRule();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.basictransitionrules.LetRule#getInRule <em>In Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>In Rule</em>' containment reference.
	 * @see #getInRule()
	 * @generated
	 */
	void setInRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Init Expression</b></em>' reference list.
	 * The list contents are of type {@link asmeta.terms.basicterms.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init Expression</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Expression</em>' reference list.
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getLetRule_InitExpression()
	 * @model required="true"
	 * @generated
	 */
	EList<Term> getInitExpression();

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
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getLetRule_Variable()
	 * @model required="true"
	 * @generated
	 */
	EList<VariableTerm> getVariable();

} // LetRule

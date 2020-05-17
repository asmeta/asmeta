/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms;

import asmeta.definitions.RuleDeclaration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule As Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.basicterms.RuleAsTerm#getRule <em>Rule</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.basicterms.BasictermsPackage#getRuleAsTerm()
 * @model
 * @generated
 */
public interface RuleAsTerm extends ExtendedTerm {
	/**
	 * Returns the value of the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' reference.
	 * @see #setRule(RuleDeclaration)
	 * @see asmeta.terms.basicterms.BasictermsPackage#getRuleAsTerm_Rule()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	RuleDeclaration getRule();

	/**
	 * Sets the value of the '{@link asmeta.terms.basicterms.RuleAsTerm#getRule <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(RuleDeclaration value);

} // RuleAsTerm

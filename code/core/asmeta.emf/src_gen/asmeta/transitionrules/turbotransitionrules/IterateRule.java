/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules;

import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Iterate Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.IterateRule#getRule <em>Rule</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getIterateRule()
 * @model
 * @generated
 */
public interface IterateRule extends TurboRule {
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
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getIterateRule_Rule()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Rule getRule();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.turbotransitionrules.IterateRule#getRule <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' containment reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(Rule value);

} // IterateRule

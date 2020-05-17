/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Block Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.BlockRule#getRules <em>Rules</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getBlockRule()
 * @model
 * @generated
 */
public interface BlockRule extends BasicRule {
	/**
	 * Returns the value of the '<em><b>Rules</b></em>' attribute list.
	 * The list contents are of type {@link asmeta.transitionrules.basictransitionrules.Rule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' attribute list.
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getBlockRule_Rules()
	 * @model unique="false" dataType="asmeta.transitionrules.basictransitionrules.RuleDT" lower="2"
	 * @generated
	 */
	EList<Rule> getRules();

} // BlockRule

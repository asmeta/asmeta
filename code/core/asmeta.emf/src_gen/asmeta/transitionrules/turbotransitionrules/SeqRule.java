/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules;

import org.eclipse.emf.common.util.EList;

import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Seq Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.SeqRule#getRules <em>Rules</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getSeqRule()
 * @model
 * @generated
 */
public interface SeqRule extends TurboRule {
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
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getSeqRule_Rules()
	 * @model unique="false" dataType="asmeta.transitionrules.basictransitionrules.RuleDT" lower="2"
	 * @generated
	 */
	EList<Rule> getRules();

} // SeqRule

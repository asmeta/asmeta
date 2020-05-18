/**
 * generated by Xtext 2.21.0
 */
package org.asmeta.xt.asmetal;

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
 *   <li>{@link org.asmeta.xt.asmetal.BlockRule#getRules <em>Rules</em>}</li>
 * </ul>
 *
 * @see org.asmeta.xt.asmetal.AsmetalPackage#getBlockRule()
 * @model
 * @generated
 */
public interface BlockRule extends BasicRule
{
  /**
   * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
   * The list contents are of type {@link org.asmeta.xt.asmetal.Rule}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Rules</em>' containment reference list.
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getBlockRule_Rules()
   * @model containment="true"
   * @generated
   */
  EList<Rule> getRules();

} // BlockRule
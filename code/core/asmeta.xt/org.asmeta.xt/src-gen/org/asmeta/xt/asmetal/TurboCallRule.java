/**
 * generated by Xtext 2.36.0
 */
package org.asmeta.xt.asmetal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Turbo Call Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.xt.asmetal.TurboCallRule#getCalledRuleName <em>Called Rule Name</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.TurboCallRule#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @see org.asmeta.xt.asmetal.AsmetalPackage#getTurboCallRule()
 * @model
 * @generated
 */
public interface TurboCallRule extends TurboRule
{
  /**
   * Returns the value of the '<em><b>Called Rule Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Called Rule Name</em>' attribute.
   * @see #setCalledRuleName(String)
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getTurboCallRule_CalledRuleName()
   * @model
   * @generated
   */
  String getCalledRuleName();

  /**
   * Sets the value of the '{@link org.asmeta.xt.asmetal.TurboCallRule#getCalledRuleName <em>Called Rule Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Called Rule Name</em>' attribute.
   * @see #getCalledRuleName()
   * @generated
   */
  void setCalledRuleName(String value);

  /**
   * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
   * The list contents are of type {@link org.asmeta.xt.asmetal.Term}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameters</em>' containment reference list.
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getTurboCallRule_Parameters()
   * @model containment="true"
   * @generated
   */
  EList<Term> getParameters();

} // TurboCallRule

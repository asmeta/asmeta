/**
 * generated by Xtext 2.21.0
 */
package org.asmeta.xt.asmetal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Forall Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.xt.asmetal.ForallRule#getVariable <em>Variable</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.ForallRule#getRanges <em>Ranges</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.ForallRule#getGuard <em>Guard</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.ForallRule#getDoRule <em>Do Rule</em>}</li>
 * </ul>
 *
 * @see org.asmeta.xt.asmetal.AsmetalPackage#getForallRule()
 * @model
 * @generated
 */
public interface ForallRule extends BasicRule
{
  /**
   * Returns the value of the '<em><b>Variable</b></em>' containment reference list.
   * The list contents are of type {@link org.asmeta.xt.asmetal.VariableTerm}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variable</em>' containment reference list.
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getForallRule_Variable()
   * @model containment="true"
   * @generated
   */
  EList<VariableTerm> getVariable();

  /**
   * Returns the value of the '<em><b>Ranges</b></em>' containment reference list.
   * The list contents are of type {@link org.asmeta.xt.asmetal.Term}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Ranges</em>' containment reference list.
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getForallRule_Ranges()
   * @model containment="true"
   * @generated
   */
  EList<Term> getRanges();

  /**
   * Returns the value of the '<em><b>Guard</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Guard</em>' containment reference.
   * @see #setGuard(Term)
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getForallRule_Guard()
   * @model containment="true"
   * @generated
   */
  Term getGuard();

  /**
   * Sets the value of the '{@link org.asmeta.xt.asmetal.ForallRule#getGuard <em>Guard</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Guard</em>' containment reference.
   * @see #getGuard()
   * @generated
   */
  void setGuard(Term value);

  /**
   * Returns the value of the '<em><b>Do Rule</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Do Rule</em>' containment reference.
   * @see #setDoRule(Rule)
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getForallRule_DoRule()
   * @model containment="true"
   * @generated
   */
  Rule getDoRule();

  /**
   * Sets the value of the '{@link org.asmeta.xt.asmetal.ForallRule#getDoRule <em>Do Rule</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Do Rule</em>' containment reference.
   * @see #getDoRule()
   * @generated
   */
  void setDoRule(Rule value);

} // ForallRule
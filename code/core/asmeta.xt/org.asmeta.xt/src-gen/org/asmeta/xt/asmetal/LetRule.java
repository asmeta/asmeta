/**
 * generated by Xtext 2.36.0
 */
package org.asmeta.xt.asmetal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Let Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.xt.asmetal.LetRule#getVariable <em>Variable</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.LetRule#getInitExpression <em>Init Expression</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.LetRule#getInRule <em>In Rule</em>}</li>
 * </ul>
 *
 * @see org.asmeta.xt.asmetal.AsmetalPackage#getLetRule()
 * @model
 * @generated
 */
public interface LetRule extends BasicRule
{
  /**
   * Returns the value of the '<em><b>Variable</b></em>' containment reference list.
   * The list contents are of type {@link org.asmeta.xt.asmetal.VariableTerm}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Variable</em>' containment reference list.
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getLetRule_Variable()
   * @model containment="true"
   * @generated
   */
  EList<VariableTerm> getVariable();

  /**
   * Returns the value of the '<em><b>Init Expression</b></em>' containment reference list.
   * The list contents are of type {@link org.asmeta.xt.asmetal.Term}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Init Expression</em>' containment reference list.
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getLetRule_InitExpression()
   * @model containment="true"
   * @generated
   */
  EList<Term> getInitExpression();

  /**
   * Returns the value of the '<em><b>In Rule</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>In Rule</em>' containment reference.
   * @see #setInRule(Rule)
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getLetRule_InRule()
   * @model containment="true"
   * @generated
   */
  Rule getInRule();

  /**
   * Sets the value of the '{@link org.asmeta.xt.asmetal.LetRule#getInRule <em>In Rule</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>In Rule</em>' containment reference.
   * @see #getInRule()
   * @generated
   */
  void setInRule(Rule value);

} // LetRule

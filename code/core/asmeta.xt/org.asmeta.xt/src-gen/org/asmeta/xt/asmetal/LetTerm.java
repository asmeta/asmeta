/**
 * generated by Xtext 2.36.0
 */
package org.asmeta.xt.asmetal;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Let Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.xt.asmetal.LetTerm#getAssignmentTerm <em>Assignment Term</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.LetTerm#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see org.asmeta.xt.asmetal.AsmetalPackage#getLetTerm()
 * @model
 * @generated
 */
public interface LetTerm extends VariableBindingTerm
{
  /**
   * Returns the value of the '<em><b>Assignment Term</b></em>' containment reference list.
   * The list contents are of type {@link org.asmeta.xt.asmetal.Term}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Assignment Term</em>' containment reference list.
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getLetTerm_AssignmentTerm()
   * @model containment="true"
   * @generated
   */
  EList<Term> getAssignmentTerm();

  /**
   * Returns the value of the '<em><b>Body</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Body</em>' containment reference.
   * @see #setBody(Term)
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getLetTerm_Body()
   * @model containment="true"
   * @generated
   */
  Term getBody();

  /**
   * Sets the value of the '{@link org.asmeta.xt.asmetal.LetTerm#getBody <em>Body</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Body</em>' containment reference.
   * @see #getBody()
   * @generated
   */
  void setBody(Term value);

} // LetTerm

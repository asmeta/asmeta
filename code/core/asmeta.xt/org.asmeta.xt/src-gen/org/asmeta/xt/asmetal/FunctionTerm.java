/**
 * generated by Xtext 2.21.0
 */
package org.asmeta.xt.asmetal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.xt.asmetal.FunctionTerm#getAgent <em>Agent</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.FunctionTerm#getFunctionName <em>Function Name</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.FunctionTerm#getArguments <em>Arguments</em>}</li>
 * </ul>
 *
 * @see org.asmeta.xt.asmetal.AsmetalPackage#getFunctionTerm()
 * @model
 * @generated
 */
public interface FunctionTerm extends BasicTerm
{
  /**
   * Returns the value of the '<em><b>Agent</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Agent</em>' attribute.
   * @see #setAgent(String)
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getFunctionTerm_Agent()
   * @model
   * @generated
   */
  String getAgent();

  /**
   * Sets the value of the '{@link org.asmeta.xt.asmetal.FunctionTerm#getAgent <em>Agent</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Agent</em>' attribute.
   * @see #getAgent()
   * @generated
   */
  void setAgent(String value);

  /**
   * Returns the value of the '<em><b>Function Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Function Name</em>' attribute.
   * @see #setFunctionName(String)
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getFunctionTerm_FunctionName()
   * @model
   * @generated
   */
  String getFunctionName();

  /**
   * Sets the value of the '{@link org.asmeta.xt.asmetal.FunctionTerm#getFunctionName <em>Function Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Function Name</em>' attribute.
   * @see #getFunctionName()
   * @generated
   */
  void setFunctionName(String value);

  /**
   * Returns the value of the '<em><b>Arguments</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Arguments</em>' containment reference.
   * @see #setArguments(TupleTerm)
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getFunctionTerm_Arguments()
   * @model containment="true"
   * @generated
   */
  TupleTerm getArguments();

  /**
   * Sets the value of the '{@link org.asmeta.xt.asmetal.FunctionTerm#getArguments <em>Arguments</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Arguments</em>' containment reference.
   * @see #getArguments()
   * @generated
   */
  void setArguments(TupleTerm value);

  org.asmeta.xt.asmetal.Function getFunction();
  
  void setFunction(org.asmeta.xt.asmetal.Function f);
} // FunctionTerm
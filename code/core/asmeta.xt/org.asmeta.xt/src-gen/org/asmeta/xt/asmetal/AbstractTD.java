/**
 * generated by Xtext 2.36.0
 */
package org.asmeta.xt.asmetal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract TD</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.xt.asmetal.AbstractTD#isDynamic <em>Dynamic</em>}</li>
 * </ul>
 *
 * @see org.asmeta.xt.asmetal.AsmetalPackage#getAbstractTD()
 * @model
 * @generated
 */
public interface AbstractTD extends TypeDomain
{
  /**
   * Returns the value of the '<em><b>Dynamic</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Dynamic</em>' attribute.
   * @see #setDynamic(boolean)
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getAbstractTD_Dynamic()
   * @model
   * @generated
   */
  boolean isDynamic();

  /**
   * Sets the value of the '{@link org.asmeta.xt.asmetal.AbstractTD#isDynamic <em>Dynamic</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Dynamic</em>' attribute.
   * @see #isDynamic()
   * @generated
   */
  void setDynamic(boolean value);

  public boolean getIsDynamic();
} // AbstractTD

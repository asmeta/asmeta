/**
 * generated by Xtext 2.36.0
 */
package org.asmeta.xt.asmetal;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Powerset Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.xt.asmetal.PowersetDomain#getBaseDomain <em>Base Domain</em>}</li>
 * </ul>
 *
 * @see org.asmeta.xt.asmetal.AsmetalPackage#getPowersetDomain()
 * @model
 * @generated
 */
public interface PowersetDomain extends StructuredTD
{
  /**
   * Returns the value of the '<em><b>Base Domain</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Base Domain</em>' containment reference.
   * @see #setBaseDomain(Domain)
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getPowersetDomain_BaseDomain()
   * @model containment="true"
   * @generated
   */
  Domain getBaseDomain();

  /**
   * Sets the value of the '{@link org.asmeta.xt.asmetal.PowersetDomain#getBaseDomain <em>Base Domain</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Base Domain</em>' containment reference.
   * @see #getBaseDomain()
   * @generated
   */
  void setBaseDomain(Domain value);

} // PowersetDomain

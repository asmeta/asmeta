/**
 * generated by Xtext 2.36.0
 */
package org.asmeta.xt.asmetal.impl;

import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.asmetal.Domain;
import org.asmeta.xt.asmetal.PowersetDomain;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Powerset Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.xt.asmetal.impl.PowersetDomainImpl#getBaseDomain <em>Base Domain</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PowersetDomainImpl extends StructuredTDImpl implements PowersetDomain
{
  /**
   * The cached value of the '{@link #getBaseDomain() <em>Base Domain</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBaseDomain()
   * @generated
   * @ordered
   */
  protected Domain baseDomain;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PowersetDomainImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return AsmetalPackage.Literals.POWERSET_DOMAIN;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Domain getBaseDomain()
  {
    return baseDomain;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetBaseDomain(Domain newBaseDomain, NotificationChain msgs)
  {
    Domain oldBaseDomain = baseDomain;
    baseDomain = newBaseDomain;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AsmetalPackage.POWERSET_DOMAIN__BASE_DOMAIN, oldBaseDomain, newBaseDomain);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setBaseDomain(Domain newBaseDomain)
  {
    if (newBaseDomain != baseDomain)
    {
      NotificationChain msgs = null;
      if (baseDomain != null)
        msgs = ((InternalEObject)baseDomain).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AsmetalPackage.POWERSET_DOMAIN__BASE_DOMAIN, null, msgs);
      if (newBaseDomain != null)
        msgs = ((InternalEObject)newBaseDomain).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AsmetalPackage.POWERSET_DOMAIN__BASE_DOMAIN, null, msgs);
      msgs = basicSetBaseDomain(newBaseDomain, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, AsmetalPackage.POWERSET_DOMAIN__BASE_DOMAIN, newBaseDomain, newBaseDomain));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case AsmetalPackage.POWERSET_DOMAIN__BASE_DOMAIN:
        return basicSetBaseDomain(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case AsmetalPackage.POWERSET_DOMAIN__BASE_DOMAIN:
        return getBaseDomain();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case AsmetalPackage.POWERSET_DOMAIN__BASE_DOMAIN:
        setBaseDomain((Domain)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case AsmetalPackage.POWERSET_DOMAIN__BASE_DOMAIN:
        setBaseDomain((Domain)null);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case AsmetalPackage.POWERSET_DOMAIN__BASE_DOMAIN:
        return baseDomain != null;
    }
    return super.eIsSet(featureID);
  }

} //PowersetDomainImpl

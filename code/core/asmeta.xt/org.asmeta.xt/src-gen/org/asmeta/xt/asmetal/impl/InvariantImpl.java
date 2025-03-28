/**
 * generated by Xtext 2.36.0
 */
package org.asmeta.xt.asmetal.impl;

import java.util.Collection;

import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.asmetal.Invariant;
import org.asmeta.xt.asmetal.InvariantElement;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Invariant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.xt.asmetal.impl.InvariantImpl#getInvar_list <em>Invar list</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvariantImpl extends PropertyImpl implements Invariant
{
  /**
   * The cached value of the '{@link #getInvar_list() <em>Invar list</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvar_list()
   * @generated
   * @ordered
   */
  protected EList<InvariantElement> invar_list;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected InvariantImpl()
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
    return AsmetalPackage.Literals.INVARIANT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<InvariantElement> getInvar_list()
  {
    if (invar_list == null)
    {
      invar_list = new EObjectContainmentEList<InvariantElement>(InvariantElement.class, this, AsmetalPackage.INVARIANT__INVAR_LIST);
    }
    return invar_list;
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
      case AsmetalPackage.INVARIANT__INVAR_LIST:
        return ((InternalEList<?>)getInvar_list()).basicRemove(otherEnd, msgs);
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
      case AsmetalPackage.INVARIANT__INVAR_LIST:
        return getInvar_list();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case AsmetalPackage.INVARIANT__INVAR_LIST:
        getInvar_list().clear();
        getInvar_list().addAll((Collection<? extends InvariantElement>)newValue);
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
      case AsmetalPackage.INVARIANT__INVAR_LIST:
        getInvar_list().clear();
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
      case AsmetalPackage.INVARIANT__INVAR_LIST:
        return invar_list != null && !invar_list.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  public java.util.List<org.asmeta.xt.asmetal.RuleDeclaration> getConstrainedRule() {
  	// TODO
  	return null;
  }
  
  public java.util.List<org.asmeta.xt.asmetal.Function> getConstrainedFunction() {
  	// TODO
  	return null;
  }
  
  public java.util.List<org.asmeta.xt.asmetal.Domain> getConstrainedDomain() {
  	// TODO
  	return null;
  }
} //InvariantImpl

/**
 * generated by Xtext 2.36.0
 */
package org.asmeta.xt.asmetal.impl;

import java.util.Collection;

import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.asmetal.ExtendedTerm;
import org.asmeta.xt.asmetal.FiniteQuantificationTerm;
import org.asmeta.xt.asmetal.Term;
import org.asmeta.xt.asmetal.VariableBindingTerm;
import org.asmeta.xt.asmetal.VariableTerm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Finite Quantification Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.xt.asmetal.impl.FiniteQuantificationTermImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.impl.FiniteQuantificationTermImpl#getRanges <em>Ranges</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.impl.FiniteQuantificationTermImpl#getGuard <em>Guard</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FiniteQuantificationTermImpl extends basicExprImpl implements FiniteQuantificationTerm
{
  /**
   * The cached value of the '{@link #getVariable() <em>Variable</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getVariable()
   * @generated
   * @ordered
   */
  protected EList<VariableTerm> variable;

  /**
   * The cached value of the '{@link #getRanges() <em>Ranges</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRanges()
   * @generated
   * @ordered
   */
  protected EList<Term> ranges;

  /**
   * The cached value of the '{@link #getGuard() <em>Guard</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGuard()
   * @generated
   * @ordered
   */
  protected Term guard;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FiniteQuantificationTermImpl()
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
    return AsmetalPackage.Literals.FINITE_QUANTIFICATION_TERM;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<VariableTerm> getVariable()
  {
    if (variable == null)
    {
      variable = new EObjectContainmentEList<VariableTerm>(VariableTerm.class, this, AsmetalPackage.FINITE_QUANTIFICATION_TERM__VARIABLE);
    }
    return variable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Term> getRanges()
  {
    if (ranges == null)
    {
      ranges = new EObjectContainmentEList<Term>(Term.class, this, AsmetalPackage.FINITE_QUANTIFICATION_TERM__RANGES);
    }
    return ranges;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Term getGuard()
  {
    return guard;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetGuard(Term newGuard, NotificationChain msgs)
  {
    Term oldGuard = guard;
    guard = newGuard;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AsmetalPackage.FINITE_QUANTIFICATION_TERM__GUARD, oldGuard, newGuard);
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
  public void setGuard(Term newGuard)
  {
    if (newGuard != guard)
    {
      NotificationChain msgs = null;
      if (guard != null)
        msgs = ((InternalEObject)guard).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AsmetalPackage.FINITE_QUANTIFICATION_TERM__GUARD, null, msgs);
      if (newGuard != null)
        msgs = ((InternalEObject)newGuard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AsmetalPackage.FINITE_QUANTIFICATION_TERM__GUARD, null, msgs);
      msgs = basicSetGuard(newGuard, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, AsmetalPackage.FINITE_QUANTIFICATION_TERM__GUARD, newGuard, newGuard));
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
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__VARIABLE:
        return ((InternalEList<?>)getVariable()).basicRemove(otherEnd, msgs);
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__RANGES:
        return ((InternalEList<?>)getRanges()).basicRemove(otherEnd, msgs);
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__GUARD:
        return basicSetGuard(null, msgs);
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
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__VARIABLE:
        return getVariable();
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__RANGES:
        return getRanges();
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__GUARD:
        return getGuard();
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
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__VARIABLE:
        getVariable().clear();
        getVariable().addAll((Collection<? extends VariableTerm>)newValue);
        return;
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__RANGES:
        getRanges().clear();
        getRanges().addAll((Collection<? extends Term>)newValue);
        return;
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__GUARD:
        setGuard((Term)newValue);
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
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__VARIABLE:
        getVariable().clear();
        return;
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__RANGES:
        getRanges().clear();
        return;
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__GUARD:
        setGuard((Term)null);
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
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__VARIABLE:
        return variable != null && !variable.isEmpty();
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__RANGES:
        return ranges != null && !ranges.isEmpty();
      case AsmetalPackage.FINITE_QUANTIFICATION_TERM__GUARD:
        return guard != null;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
  {
    if (baseClass == ExtendedTerm.class)
    {
      switch (derivedFeatureID)
      {
        default: return -1;
      }
    }
    if (baseClass == VariableBindingTerm.class)
    {
      switch (derivedFeatureID)
      {
        case AsmetalPackage.FINITE_QUANTIFICATION_TERM__VARIABLE: return AsmetalPackage.VARIABLE_BINDING_TERM__VARIABLE;
        default: return -1;
      }
    }
    return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
  {
    if (baseClass == ExtendedTerm.class)
    {
      switch (baseFeatureID)
      {
        default: return -1;
      }
    }
    if (baseClass == VariableBindingTerm.class)
    {
      switch (baseFeatureID)
      {
        case AsmetalPackage.VARIABLE_BINDING_TERM__VARIABLE: return AsmetalPackage.FINITE_QUANTIFICATION_TERM__VARIABLE;
        default: return -1;
      }
    }
    return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
  }

  @Override
  public void setRanges(EList<Term> list) {
	ranges = list;
  }
} //FiniteQuantificationTermImpl

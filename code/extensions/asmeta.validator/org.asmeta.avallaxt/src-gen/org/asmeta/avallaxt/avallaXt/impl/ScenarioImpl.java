/**
 * generated by Xtext 2.22.0
 */
package org.asmeta.avallaxt.avallaXt.impl;

import java.util.Collection;

import org.asmeta.avallaxt.avallaXt.AvallaXtPackage;
import org.asmeta.avallaxt.avallaXt.Element;
import org.asmeta.avallaxt.avallaXt.Invariant;
import org.asmeta.avallaxt.avallaXt.Scenario;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Scenario</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.avallaxt.avallaXt.impl.ScenarioImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.asmeta.avallaxt.avallaXt.impl.ScenarioImpl#getSpec <em>Spec</em>}</li>
 *   <li>{@link org.asmeta.avallaxt.avallaXt.impl.ScenarioImpl#getInvariants <em>Invariants</em>}</li>
 *   <li>{@link org.asmeta.avallaxt.avallaXt.impl.ScenarioImpl#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ScenarioImpl extends MinimalEObjectImpl.Container implements Scenario
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getSpec() <em>Spec</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSpec()
   * @generated
   * @ordered
   */
  protected static final String SPEC_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSpec() <em>Spec</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSpec()
   * @generated
   * @ordered
   */
  protected String spec = SPEC_EDEFAULT;

  /**
   * The cached value of the '{@link #getInvariants() <em>Invariants</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvariants()
   * @generated
   * @ordered
   */
  protected EList<Invariant> invariants;

  /**
   * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getElements()
   * @generated
   * @ordered
   */
  protected EList<Element> elements;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ScenarioImpl()
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
    return AvallaXtPackage.Literals.SCENARIO;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, AvallaXtPackage.SCENARIO__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getSpec()
  {
    return spec;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setSpec(String newSpec)
  {
    String oldSpec = spec;
    spec = newSpec;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, AvallaXtPackage.SCENARIO__SPEC, oldSpec, spec));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Invariant> getInvariants()
  {
    if (invariants == null)
    {
      invariants = new EObjectContainmentEList<Invariant>(Invariant.class, this, AvallaXtPackage.SCENARIO__INVARIANTS);
    }
    return invariants;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Element> getElements()
  {
    if (elements == null)
    {
      elements = new EObjectContainmentEList<Element>(Element.class, this, AvallaXtPackage.SCENARIO__ELEMENTS);
    }
    return elements;
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
      case AvallaXtPackage.SCENARIO__INVARIANTS:
        return ((InternalEList<?>)getInvariants()).basicRemove(otherEnd, msgs);
      case AvallaXtPackage.SCENARIO__ELEMENTS:
        return ((InternalEList<?>)getElements()).basicRemove(otherEnd, msgs);
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
      case AvallaXtPackage.SCENARIO__NAME:
        return getName();
      case AvallaXtPackage.SCENARIO__SPEC:
        return getSpec();
      case AvallaXtPackage.SCENARIO__INVARIANTS:
        return getInvariants();
      case AvallaXtPackage.SCENARIO__ELEMENTS:
        return getElements();
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
      case AvallaXtPackage.SCENARIO__NAME:
        setName((String)newValue);
        return;
      case AvallaXtPackage.SCENARIO__SPEC:
        setSpec((String)newValue);
        return;
      case AvallaXtPackage.SCENARIO__INVARIANTS:
        getInvariants().clear();
        getInvariants().addAll((Collection<? extends Invariant>)newValue);
        return;
      case AvallaXtPackage.SCENARIO__ELEMENTS:
        getElements().clear();
        getElements().addAll((Collection<? extends Element>)newValue);
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
      case AvallaXtPackage.SCENARIO__NAME:
        setName(NAME_EDEFAULT);
        return;
      case AvallaXtPackage.SCENARIO__SPEC:
        setSpec(SPEC_EDEFAULT);
        return;
      case AvallaXtPackage.SCENARIO__INVARIANTS:
        getInvariants().clear();
        return;
      case AvallaXtPackage.SCENARIO__ELEMENTS:
        getElements().clear();
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
      case AvallaXtPackage.SCENARIO__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case AvallaXtPackage.SCENARIO__SPEC:
        return SPEC_EDEFAULT == null ? spec != null : !SPEC_EDEFAULT.equals(spec);
      case AvallaXtPackage.SCENARIO__INVARIANTS:
        return invariants != null && !invariants.isEmpty();
      case AvallaXtPackage.SCENARIO__ELEMENTS:
        return elements != null && !elements.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(", spec: ");
    result.append(spec);
    result.append(')');
    return result.toString();
  }

} //ScenarioImpl
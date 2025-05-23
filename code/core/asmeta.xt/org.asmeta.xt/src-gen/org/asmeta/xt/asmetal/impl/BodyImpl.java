/**
 * generated by Xtext 2.36.0
 */
package org.asmeta.xt.asmetal.impl;

import java.util.Collection;

import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.asmetal.Body;
import org.asmeta.xt.asmetal.DomainDefinition;
import org.asmeta.xt.asmetal.FairnessConstraint;
import org.asmeta.xt.asmetal.FunctionDefinition;
import org.asmeta.xt.asmetal.InvariantConstraint;
import org.asmeta.xt.asmetal.Property;
import org.asmeta.xt.asmetal.RuleDeclaration;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Body</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.xt.asmetal.impl.BodyImpl#getDomainDefinition <em>Domain Definition</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.impl.BodyImpl#getFunctionDefinition <em>Function Definition</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.impl.BodyImpl#getRuleDeclaration <em>Rule Declaration</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.impl.BodyImpl#getInvariantConstraint <em>Invariant Constraint</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.impl.BodyImpl#getFairnessConstraint <em>Fairness Constraint</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.impl.BodyImpl#getProperty <em>Property</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BodyImpl extends MinimalEObjectImpl.Container implements Body
{
  /**
   * The cached value of the '{@link #getDomainDefinition() <em>Domain Definition</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDomainDefinition()
   * @generated
   * @ordered
   */
  protected EList<DomainDefinition> domainDefinition;

  /**
   * The cached value of the '{@link #getFunctionDefinition() <em>Function Definition</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFunctionDefinition()
   * @generated
   * @ordered
   */
  protected EList<FunctionDefinition> functionDefinition;

  /**
   * The cached value of the '{@link #getRuleDeclaration() <em>Rule Declaration</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRuleDeclaration()
   * @generated
   * @ordered
   */
  protected EList<RuleDeclaration> ruleDeclaration;

  /**
   * The cached value of the '{@link #getInvariantConstraint() <em>Invariant Constraint</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInvariantConstraint()
   * @generated
   * @ordered
   */
  protected EList<InvariantConstraint> invariantConstraint;

  /**
   * The cached value of the '{@link #getFairnessConstraint() <em>Fairness Constraint</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFairnessConstraint()
   * @generated
   * @ordered
   */
  protected EList<FairnessConstraint> fairnessConstraint;

  /**
   * The cached value of the '{@link #getProperty() <em>Property</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProperty()
   * @generated
   * @ordered
   */
  protected EList<Property> property;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected BodyImpl()
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
    return AsmetalPackage.Literals.BODY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<DomainDefinition> getDomainDefinition()
  {
    if (domainDefinition == null)
    {
      domainDefinition = new EObjectContainmentEList<DomainDefinition>(DomainDefinition.class, this, AsmetalPackage.BODY__DOMAIN_DEFINITION);
    }
    return domainDefinition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<FunctionDefinition> getFunctionDefinition()
  {
    if (functionDefinition == null)
    {
      functionDefinition = new EObjectContainmentEList<FunctionDefinition>(FunctionDefinition.class, this, AsmetalPackage.BODY__FUNCTION_DEFINITION);
    }
    return functionDefinition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<RuleDeclaration> getRuleDeclaration()
  {
    if (ruleDeclaration == null)
    {
      ruleDeclaration = new EObjectContainmentEList<RuleDeclaration>(RuleDeclaration.class, this, AsmetalPackage.BODY__RULE_DECLARATION);
    }
    return ruleDeclaration;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<InvariantConstraint> getInvariantConstraint()
  {
    if (invariantConstraint == null)
    {
      invariantConstraint = new EObjectContainmentEList<InvariantConstraint>(InvariantConstraint.class, this, AsmetalPackage.BODY__INVARIANT_CONSTRAINT);
    }
    return invariantConstraint;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<FairnessConstraint> getFairnessConstraint()
  {
    if (fairnessConstraint == null)
    {
      fairnessConstraint = new EObjectContainmentEList<FairnessConstraint>(FairnessConstraint.class, this, AsmetalPackage.BODY__FAIRNESS_CONSTRAINT);
    }
    return fairnessConstraint;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<Property> getProperty()
  {
    if (property == null)
    {
      property = new EObjectContainmentEList<Property>(Property.class, this, AsmetalPackage.BODY__PROPERTY);
    }
    return property;
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
      case AsmetalPackage.BODY__DOMAIN_DEFINITION:
        return ((InternalEList<?>)getDomainDefinition()).basicRemove(otherEnd, msgs);
      case AsmetalPackage.BODY__FUNCTION_DEFINITION:
        return ((InternalEList<?>)getFunctionDefinition()).basicRemove(otherEnd, msgs);
      case AsmetalPackage.BODY__RULE_DECLARATION:
        return ((InternalEList<?>)getRuleDeclaration()).basicRemove(otherEnd, msgs);
      case AsmetalPackage.BODY__INVARIANT_CONSTRAINT:
        return ((InternalEList<?>)getInvariantConstraint()).basicRemove(otherEnd, msgs);
      case AsmetalPackage.BODY__FAIRNESS_CONSTRAINT:
        return ((InternalEList<?>)getFairnessConstraint()).basicRemove(otherEnd, msgs);
      case AsmetalPackage.BODY__PROPERTY:
        return ((InternalEList<?>)getProperty()).basicRemove(otherEnd, msgs);
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
      case AsmetalPackage.BODY__DOMAIN_DEFINITION:
        return getDomainDefinition();
      case AsmetalPackage.BODY__FUNCTION_DEFINITION:
        return getFunctionDefinition();
      case AsmetalPackage.BODY__RULE_DECLARATION:
        return getRuleDeclaration();
      case AsmetalPackage.BODY__INVARIANT_CONSTRAINT:
        return getInvariantConstraint();
      case AsmetalPackage.BODY__FAIRNESS_CONSTRAINT:
        return getFairnessConstraint();
      case AsmetalPackage.BODY__PROPERTY:
        return getProperty();
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
      case AsmetalPackage.BODY__DOMAIN_DEFINITION:
        getDomainDefinition().clear();
        getDomainDefinition().addAll((Collection<? extends DomainDefinition>)newValue);
        return;
      case AsmetalPackage.BODY__FUNCTION_DEFINITION:
        getFunctionDefinition().clear();
        getFunctionDefinition().addAll((Collection<? extends FunctionDefinition>)newValue);
        return;
      case AsmetalPackage.BODY__RULE_DECLARATION:
        getRuleDeclaration().clear();
        getRuleDeclaration().addAll((Collection<? extends RuleDeclaration>)newValue);
        return;
      case AsmetalPackage.BODY__INVARIANT_CONSTRAINT:
        getInvariantConstraint().clear();
        getInvariantConstraint().addAll((Collection<? extends InvariantConstraint>)newValue);
        return;
      case AsmetalPackage.BODY__FAIRNESS_CONSTRAINT:
        getFairnessConstraint().clear();
        getFairnessConstraint().addAll((Collection<? extends FairnessConstraint>)newValue);
        return;
      case AsmetalPackage.BODY__PROPERTY:
        getProperty().clear();
        getProperty().addAll((Collection<? extends Property>)newValue);
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
      case AsmetalPackage.BODY__DOMAIN_DEFINITION:
        getDomainDefinition().clear();
        return;
      case AsmetalPackage.BODY__FUNCTION_DEFINITION:
        getFunctionDefinition().clear();
        return;
      case AsmetalPackage.BODY__RULE_DECLARATION:
        getRuleDeclaration().clear();
        return;
      case AsmetalPackage.BODY__INVARIANT_CONSTRAINT:
        getInvariantConstraint().clear();
        return;
      case AsmetalPackage.BODY__FAIRNESS_CONSTRAINT:
        getFairnessConstraint().clear();
        return;
      case AsmetalPackage.BODY__PROPERTY:
        getProperty().clear();
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
      case AsmetalPackage.BODY__DOMAIN_DEFINITION:
        return domainDefinition != null && !domainDefinition.isEmpty();
      case AsmetalPackage.BODY__FUNCTION_DEFINITION:
        return functionDefinition != null && !functionDefinition.isEmpty();
      case AsmetalPackage.BODY__RULE_DECLARATION:
        return ruleDeclaration != null && !ruleDeclaration.isEmpty();
      case AsmetalPackage.BODY__INVARIANT_CONSTRAINT:
        return invariantConstraint != null && !invariantConstraint.isEmpty();
      case AsmetalPackage.BODY__FAIRNESS_CONSTRAINT:
        return fairnessConstraint != null && !fairnessConstraint.isEmpty();
      case AsmetalPackage.BODY__PROPERTY:
        return property != null && !property.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  public org.asmeta.xt.asmetal.Asm getAsm()
  {
  	// Return the container of the Body, i.e. the ASM
  	return (org.asmeta.xt.asmetal.Asm) this.eContainer;
  }
} //BodyImpl

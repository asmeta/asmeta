/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.FairnessConstraint;
import asmeta.definitions.InvarConstraint;
import asmeta.definitions.Property;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.structure.Body;
import asmeta.structure.DomainDefinition;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.StructurePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Body</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.impl.BodyImpl#getFunctionDefinition <em>Function Definition</em>}</li>
 *   <li>{@link asmeta.structure.impl.BodyImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link asmeta.structure.impl.BodyImpl#getDomainDefinition <em>Domain Definition</em>}</li>
 *   <li>{@link asmeta.structure.impl.BodyImpl#getRuleDeclaration <em>Rule Declaration</em>}</li>
 *   <li>{@link asmeta.structure.impl.BodyImpl#getAsm <em>Asm</em>}</li>
 *   <li>{@link asmeta.structure.impl.BodyImpl#getFairnessConstraint <em>Fairness Constraint</em>}</li>
 *   <li>{@link asmeta.structure.impl.BodyImpl#getInvariantConstraint <em>Invariant Constraint</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BodyImpl extends EObjectImpl implements Body {
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
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> property;

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
	 * The cached value of the '{@link #getRuleDeclaration() <em>Rule Declaration</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleDeclaration()
	 * @generated
	 * @ordered
	 */
	protected EList<RuleDeclaration> ruleDeclaration;

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
	 * The cached value of the '{@link #getInvariantConstraint() <em>Invariant Constraint</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvariantConstraint()
	 * @generated
	 * @ordered
	 */
	protected EList<InvarConstraint> invariantConstraint;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BodyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StructurePackage.Literals.BODY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FunctionDefinition> getFunctionDefinition() {
		if (functionDefinition == null) {
			functionDefinition = new EObjectContainmentEList<FunctionDefinition>(FunctionDefinition.class, this, StructurePackage.BODY__FUNCTION_DEFINITION);
		}
		return functionDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Property> getProperty() {
		if (property == null) {
			property = new EObjectContainmentEList<Property>(Property.class, this, StructurePackage.BODY__PROPERTY);
		}
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DomainDefinition> getDomainDefinition() {
		if (domainDefinition == null) {
			domainDefinition = new EObjectContainmentEList<DomainDefinition>(DomainDefinition.class, this, StructurePackage.BODY__DOMAIN_DEFINITION);
		}
		return domainDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RuleDeclaration> getRuleDeclaration() {
		if (ruleDeclaration == null) {
			ruleDeclaration = new EObjectContainmentWithInverseEList<RuleDeclaration>(RuleDeclaration.class, this, StructurePackage.BODY__RULE_DECLARATION, DefinitionsPackage.RULE_DECLARATION__ASM_BODY);
		}
		return ruleDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Asm getAsm() {
		if (eContainerFeatureID() != StructurePackage.BODY__ASM) return null;
		return (Asm)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAsm(Asm newAsm, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAsm, StructurePackage.BODY__ASM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAsm(Asm newAsm) {
		if (newAsm != eInternalContainer() || (eContainerFeatureID() != StructurePackage.BODY__ASM && newAsm != null)) {
			if (EcoreUtil.isAncestor(this, newAsm))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAsm != null)
				msgs = ((InternalEObject)newAsm).eInverseAdd(this, StructurePackage.ASM__BODY_SECTION, Asm.class, msgs);
			msgs = basicSetAsm(newAsm, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.BODY__ASM, newAsm, newAsm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FairnessConstraint> getFairnessConstraint() {
		if (fairnessConstraint == null) {
			fairnessConstraint = new EObjectContainmentEList<FairnessConstraint>(FairnessConstraint.class, this, StructurePackage.BODY__FAIRNESS_CONSTRAINT);
		}
		return fairnessConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<InvarConstraint> getInvariantConstraint() {
		if (invariantConstraint == null) {
			invariantConstraint = new EObjectContainmentEList<InvarConstraint>(InvarConstraint.class, this, StructurePackage.BODY__INVARIANT_CONSTRAINT);
		}
		return invariantConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StructurePackage.BODY__RULE_DECLARATION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRuleDeclaration()).basicAdd(otherEnd, msgs);
			case StructurePackage.BODY__ASM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAsm((Asm)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StructurePackage.BODY__FUNCTION_DEFINITION:
				return ((InternalEList<?>)getFunctionDefinition()).basicRemove(otherEnd, msgs);
			case StructurePackage.BODY__PROPERTY:
				return ((InternalEList<?>)getProperty()).basicRemove(otherEnd, msgs);
			case StructurePackage.BODY__DOMAIN_DEFINITION:
				return ((InternalEList<?>)getDomainDefinition()).basicRemove(otherEnd, msgs);
			case StructurePackage.BODY__RULE_DECLARATION:
				return ((InternalEList<?>)getRuleDeclaration()).basicRemove(otherEnd, msgs);
			case StructurePackage.BODY__ASM:
				return basicSetAsm(null, msgs);
			case StructurePackage.BODY__FAIRNESS_CONSTRAINT:
				return ((InternalEList<?>)getFairnessConstraint()).basicRemove(otherEnd, msgs);
			case StructurePackage.BODY__INVARIANT_CONSTRAINT:
				return ((InternalEList<?>)getInvariantConstraint()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case StructurePackage.BODY__ASM:
				return eInternalContainer().eInverseRemove(this, StructurePackage.ASM__BODY_SECTION, Asm.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StructurePackage.BODY__FUNCTION_DEFINITION:
				return getFunctionDefinition();
			case StructurePackage.BODY__PROPERTY:
				return getProperty();
			case StructurePackage.BODY__DOMAIN_DEFINITION:
				return getDomainDefinition();
			case StructurePackage.BODY__RULE_DECLARATION:
				return getRuleDeclaration();
			case StructurePackage.BODY__ASM:
				return getAsm();
			case StructurePackage.BODY__FAIRNESS_CONSTRAINT:
				return getFairnessConstraint();
			case StructurePackage.BODY__INVARIANT_CONSTRAINT:
				return getInvariantConstraint();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StructurePackage.BODY__FUNCTION_DEFINITION:
				getFunctionDefinition().clear();
				getFunctionDefinition().addAll((Collection<? extends FunctionDefinition>)newValue);
				return;
			case StructurePackage.BODY__PROPERTY:
				getProperty().clear();
				getProperty().addAll((Collection<? extends Property>)newValue);
				return;
			case StructurePackage.BODY__DOMAIN_DEFINITION:
				getDomainDefinition().clear();
				getDomainDefinition().addAll((Collection<? extends DomainDefinition>)newValue);
				return;
			case StructurePackage.BODY__RULE_DECLARATION:
				getRuleDeclaration().clear();
				getRuleDeclaration().addAll((Collection<? extends RuleDeclaration>)newValue);
				return;
			case StructurePackage.BODY__ASM:
				setAsm((Asm)newValue);
				return;
			case StructurePackage.BODY__FAIRNESS_CONSTRAINT:
				getFairnessConstraint().clear();
				getFairnessConstraint().addAll((Collection<? extends FairnessConstraint>)newValue);
				return;
			case StructurePackage.BODY__INVARIANT_CONSTRAINT:
				getInvariantConstraint().clear();
				getInvariantConstraint().addAll((Collection<? extends InvarConstraint>)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case StructurePackage.BODY__FUNCTION_DEFINITION:
				getFunctionDefinition().clear();
				return;
			case StructurePackage.BODY__PROPERTY:
				getProperty().clear();
				return;
			case StructurePackage.BODY__DOMAIN_DEFINITION:
				getDomainDefinition().clear();
				return;
			case StructurePackage.BODY__RULE_DECLARATION:
				getRuleDeclaration().clear();
				return;
			case StructurePackage.BODY__ASM:
				setAsm((Asm)null);
				return;
			case StructurePackage.BODY__FAIRNESS_CONSTRAINT:
				getFairnessConstraint().clear();
				return;
			case StructurePackage.BODY__INVARIANT_CONSTRAINT:
				getInvariantConstraint().clear();
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case StructurePackage.BODY__FUNCTION_DEFINITION:
				return functionDefinition != null && !functionDefinition.isEmpty();
			case StructurePackage.BODY__PROPERTY:
				return property != null && !property.isEmpty();
			case StructurePackage.BODY__DOMAIN_DEFINITION:
				return domainDefinition != null && !domainDefinition.isEmpty();
			case StructurePackage.BODY__RULE_DECLARATION:
				return ruleDeclaration != null && !ruleDeclaration.isEmpty();
			case StructurePackage.BODY__ASM:
				return getAsm() != null;
			case StructurePackage.BODY__FAIRNESS_CONSTRAINT:
				return fairnessConstraint != null && !fairnessConstraint.isEmpty();
			case StructurePackage.BODY__INVARIANT_CONSTRAINT:
				return invariantConstraint != null && !invariantConstraint.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //BodyImpl

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.Invariant;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Body;
import asmeta.structure.StructurePackage;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.impl.RuleDeclarationImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link asmeta.definitions.impl.RuleDeclarationImpl#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link asmeta.definitions.impl.RuleDeclarationImpl#getRuleBody <em>Rule Body</em>}</li>
 *   <li>{@link asmeta.definitions.impl.RuleDeclarationImpl#getAsmBody <em>Asm Body</em>}</li>
 *   <li>{@link asmeta.definitions.impl.RuleDeclarationImpl#getArity <em>Arity</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class RuleDeclarationImpl extends ClassifierImpl implements RuleDeclaration {
	/**
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected EList<VariableTerm> variable;

	/**
	 * The cached value of the '{@link #getConstraint() <em>Constraint</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraint()
	 * @generated
	 * @ordered
	 */
	protected EList<Invariant> constraint;

	/**
	 * The cached value of the '{@link #getRuleBody() <em>Rule Body</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleBody()
	 * @generated
	 * @ordered
	 */
	protected Rule ruleBody;

	/**
	 * The default value of the '{@link #getArity() <em>Arity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArity()
	 * @generated
	 * @ordered
	 */
	protected static final Integer ARITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getArity() <em>Arity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArity()
	 * @generated
	 * @ordered
	 */
	protected Integer arity = ARITY_EDEFAULT;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DefinitionsPackage.Literals.RULE_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariableTerm> getVariable() {
		if (variable == null) {
			variable = new EObjectResolvingEList<VariableTerm>(VariableTerm.class, this, DefinitionsPackage.RULE_DECLARATION__VARIABLE);
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Invariant> getConstraint() {
		if (constraint == null) {
			constraint = new EObjectWithInverseResolvingEList.ManyInverse<Invariant>(Invariant.class, this, DefinitionsPackage.RULE_DECLARATION__CONSTRAINT, DefinitionsPackage.INVARIANT__CONSTRAINED_RULE);
		}
		return constraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rule getRuleBody() {
		if (ruleBody != null && ruleBody.eIsProxy()) {
			InternalEObject oldRuleBody = (InternalEObject)ruleBody;
			ruleBody = (Rule)eResolveProxy(oldRuleBody);
			if (ruleBody != oldRuleBody) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DefinitionsPackage.RULE_DECLARATION__RULE_BODY, oldRuleBody, ruleBody));
			}
		}
		return ruleBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule basicGetRuleBody() {
		return ruleBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRuleBody(Rule newRuleBody) {
		Rule oldRuleBody = ruleBody;
		ruleBody = newRuleBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DefinitionsPackage.RULE_DECLARATION__RULE_BODY, oldRuleBody, ruleBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Body getAsmBody() {
		if (eContainerFeatureID() != DefinitionsPackage.RULE_DECLARATION__ASM_BODY) return null;
		return (Body)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAsmBody(Body newAsmBody, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAsmBody, DefinitionsPackage.RULE_DECLARATION__ASM_BODY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAsmBody(Body newAsmBody) {
		if (newAsmBody != eInternalContainer() || (eContainerFeatureID() != DefinitionsPackage.RULE_DECLARATION__ASM_BODY && newAsmBody != null)) {
			if (EcoreUtil.isAncestor(this, newAsmBody))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAsmBody != null)
				msgs = ((InternalEObject)newAsmBody).eInverseAdd(this, StructurePackage.BODY__RULE_DECLARATION, Body.class, msgs);
			msgs = basicSetAsmBody(newAsmBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DefinitionsPackage.RULE_DECLARATION__ASM_BODY, newAsmBody, newAsmBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Integer getArity() {
		return arity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setArity(Integer newArity) {
		Integer oldArity = arity;
		arity = newArity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DefinitionsPackage.RULE_DECLARATION__ARITY, oldArity, arity));
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
			case DefinitionsPackage.RULE_DECLARATION__CONSTRAINT:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getConstraint()).basicAdd(otherEnd, msgs);
			case DefinitionsPackage.RULE_DECLARATION__ASM_BODY:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAsmBody((Body)otherEnd, msgs);
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
			case DefinitionsPackage.RULE_DECLARATION__CONSTRAINT:
				return ((InternalEList<?>)getConstraint()).basicRemove(otherEnd, msgs);
			case DefinitionsPackage.RULE_DECLARATION__ASM_BODY:
				return basicSetAsmBody(null, msgs);
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
			case DefinitionsPackage.RULE_DECLARATION__ASM_BODY:
				return eInternalContainer().eInverseRemove(this, StructurePackage.BODY__RULE_DECLARATION, Body.class, msgs);
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
			case DefinitionsPackage.RULE_DECLARATION__VARIABLE:
				return getVariable();
			case DefinitionsPackage.RULE_DECLARATION__CONSTRAINT:
				return getConstraint();
			case DefinitionsPackage.RULE_DECLARATION__RULE_BODY:
				if (resolve) return getRuleBody();
				return basicGetRuleBody();
			case DefinitionsPackage.RULE_DECLARATION__ASM_BODY:
				return getAsmBody();
			case DefinitionsPackage.RULE_DECLARATION__ARITY:
				return getArity();
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
			case DefinitionsPackage.RULE_DECLARATION__VARIABLE:
				getVariable().clear();
				getVariable().addAll((Collection<? extends VariableTerm>)newValue);
				return;
			case DefinitionsPackage.RULE_DECLARATION__CONSTRAINT:
				getConstraint().clear();
				getConstraint().addAll((Collection<? extends Invariant>)newValue);
				return;
			case DefinitionsPackage.RULE_DECLARATION__RULE_BODY:
				setRuleBody((Rule)newValue);
				return;
			case DefinitionsPackage.RULE_DECLARATION__ASM_BODY:
				setAsmBody((Body)newValue);
				return;
			case DefinitionsPackage.RULE_DECLARATION__ARITY:
				setArity((Integer)newValue);
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
			case DefinitionsPackage.RULE_DECLARATION__VARIABLE:
				getVariable().clear();
				return;
			case DefinitionsPackage.RULE_DECLARATION__CONSTRAINT:
				getConstraint().clear();
				return;
			case DefinitionsPackage.RULE_DECLARATION__RULE_BODY:
				setRuleBody((Rule)null);
				return;
			case DefinitionsPackage.RULE_DECLARATION__ASM_BODY:
				setAsmBody((Body)null);
				return;
			case DefinitionsPackage.RULE_DECLARATION__ARITY:
				setArity(ARITY_EDEFAULT);
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
			case DefinitionsPackage.RULE_DECLARATION__VARIABLE:
				return variable != null && !variable.isEmpty();
			case DefinitionsPackage.RULE_DECLARATION__CONSTRAINT:
				return constraint != null && !constraint.isEmpty();
			case DefinitionsPackage.RULE_DECLARATION__RULE_BODY:
				return ruleBody != null;
			case DefinitionsPackage.RULE_DECLARATION__ASM_BODY:
				return getAsmBody() != null;
			case DefinitionsPackage.RULE_DECLARATION__ARITY:
				return ARITY_EDEFAULT == null ? arity != null : !ARITY_EDEFAULT.equals(arity);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (arity: ");
		result.append(arity);
		result.append(')');
		return result.toString();
	}

} //RuleDeclarationImpl

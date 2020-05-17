/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Forall Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.ForallRuleImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.ForallRuleImpl#getGuard <em>Guard</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.ForallRuleImpl#getDoRule <em>Do Rule</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.ForallRuleImpl#getRanges <em>Ranges</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ForallRuleImpl extends BasicRuleImpl implements ForallRule {
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
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected Term guard;

	/**
	 * The cached value of the '{@link #getDoRule() <em>Do Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoRule()
	 * @generated
	 * @ordered
	 */
	protected Rule doRule;

	/**
	 * The cached value of the '{@link #getRanges() <em>Ranges</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRanges()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> ranges;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ForallRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasictransitionrulesPackage.Literals.FORALL_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariableTerm> getVariable() {
		if (variable == null) {
			variable = new EObjectResolvingEList<VariableTerm>(VariableTerm.class, this, BasictransitionrulesPackage.FORALL_RULE__VARIABLE);
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getGuard() {
		if (guard != null && guard.eIsProxy()) {
			InternalEObject oldGuard = (InternalEObject)guard;
			guard = (Term)eResolveProxy(oldGuard);
			if (guard != oldGuard) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BasictransitionrulesPackage.FORALL_RULE__GUARD, oldGuard, guard));
			}
		}
		return guard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term basicGetGuard() {
		return guard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGuard(Term newGuard) {
		Term oldGuard = guard;
		guard = newGuard;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.FORALL_RULE__GUARD, oldGuard, guard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rule getDoRule() {
		return doRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDoRule(Rule newDoRule, NotificationChain msgs) {
		Rule oldDoRule = doRule;
		doRule = newDoRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.FORALL_RULE__DO_RULE, oldDoRule, newDoRule);
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
	public void setDoRule(Rule newDoRule) {
		if (newDoRule != doRule) {
			NotificationChain msgs = null;
			if (doRule != null)
				msgs = ((InternalEObject)doRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BasictransitionrulesPackage.FORALL_RULE__DO_RULE, null, msgs);
			if (newDoRule != null)
				msgs = ((InternalEObject)newDoRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BasictransitionrulesPackage.FORALL_RULE__DO_RULE, null, msgs);
			msgs = basicSetDoRule(newDoRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.FORALL_RULE__DO_RULE, newDoRule, newDoRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Term> getRanges() {
		if (ranges == null) {
			ranges = new EDataTypeEList<Term>(Term.class, this, BasictransitionrulesPackage.FORALL_RULE__RANGES);
		}
		return ranges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasictransitionrulesPackage.FORALL_RULE__DO_RULE:
				return basicSetDoRule(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BasictransitionrulesPackage.FORALL_RULE__VARIABLE:
				return getVariable();
			case BasictransitionrulesPackage.FORALL_RULE__GUARD:
				if (resolve) return getGuard();
				return basicGetGuard();
			case BasictransitionrulesPackage.FORALL_RULE__DO_RULE:
				return getDoRule();
			case BasictransitionrulesPackage.FORALL_RULE__RANGES:
				return getRanges();
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
			case BasictransitionrulesPackage.FORALL_RULE__VARIABLE:
				getVariable().clear();
				getVariable().addAll((Collection<? extends VariableTerm>)newValue);
				return;
			case BasictransitionrulesPackage.FORALL_RULE__GUARD:
				setGuard((Term)newValue);
				return;
			case BasictransitionrulesPackage.FORALL_RULE__DO_RULE:
				setDoRule((Rule)newValue);
				return;
			case BasictransitionrulesPackage.FORALL_RULE__RANGES:
				getRanges().clear();
				getRanges().addAll((Collection<? extends Term>)newValue);
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
			case BasictransitionrulesPackage.FORALL_RULE__VARIABLE:
				getVariable().clear();
				return;
			case BasictransitionrulesPackage.FORALL_RULE__GUARD:
				setGuard((Term)null);
				return;
			case BasictransitionrulesPackage.FORALL_RULE__DO_RULE:
				setDoRule((Rule)null);
				return;
			case BasictransitionrulesPackage.FORALL_RULE__RANGES:
				getRanges().clear();
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
			case BasictransitionrulesPackage.FORALL_RULE__VARIABLE:
				return variable != null && !variable.isEmpty();
			case BasictransitionrulesPackage.FORALL_RULE__GUARD:
				return guard != null;
			case BasictransitionrulesPackage.FORALL_RULE__DO_RULE:
				return doRule != null;
			case BasictransitionrulesPackage.FORALL_RULE__RANGES:
				return ranges != null && !ranges.isEmpty();
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
		result.append(" (ranges: ");
		result.append(ranges);
		result.append(')');
		return result.toString();
	}

} //ForallRuleImpl

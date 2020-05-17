/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conditional Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.ConditionalRuleImpl#getGuard <em>Guard</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.ConditionalRuleImpl#getElseRule <em>Else Rule</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.ConditionalRuleImpl#getThenRule <em>Then Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionalRuleImpl extends BasicRuleImpl implements ConditionalRule {
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
	 * The cached value of the '{@link #getElseRule() <em>Else Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElseRule()
	 * @generated
	 * @ordered
	 */
	protected Rule elseRule;

	/**
	 * The cached value of the '{@link #getThenRule() <em>Then Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThenRule()
	 * @generated
	 * @ordered
	 */
	protected Rule thenRule;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionalRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasictransitionrulesPackage.Literals.CONDITIONAL_RULE;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BasictransitionrulesPackage.CONDITIONAL_RULE__GUARD, oldGuard, guard));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.CONDITIONAL_RULE__GUARD, oldGuard, guard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rule getElseRule() {
		return elseRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetElseRule(Rule newElseRule, NotificationChain msgs) {
		Rule oldElseRule = elseRule;
		elseRule = newElseRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.CONDITIONAL_RULE__ELSE_RULE, oldElseRule, newElseRule);
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
	public void setElseRule(Rule newElseRule) {
		if (newElseRule != elseRule) {
			NotificationChain msgs = null;
			if (elseRule != null)
				msgs = ((InternalEObject)elseRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BasictransitionrulesPackage.CONDITIONAL_RULE__ELSE_RULE, null, msgs);
			if (newElseRule != null)
				msgs = ((InternalEObject)newElseRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BasictransitionrulesPackage.CONDITIONAL_RULE__ELSE_RULE, null, msgs);
			msgs = basicSetElseRule(newElseRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.CONDITIONAL_RULE__ELSE_RULE, newElseRule, newElseRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rule getThenRule() {
		return thenRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetThenRule(Rule newThenRule, NotificationChain msgs) {
		Rule oldThenRule = thenRule;
		thenRule = newThenRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.CONDITIONAL_RULE__THEN_RULE, oldThenRule, newThenRule);
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
	public void setThenRule(Rule newThenRule) {
		if (newThenRule != thenRule) {
			NotificationChain msgs = null;
			if (thenRule != null)
				msgs = ((InternalEObject)thenRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BasictransitionrulesPackage.CONDITIONAL_RULE__THEN_RULE, null, msgs);
			if (newThenRule != null)
				msgs = ((InternalEObject)newThenRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BasictransitionrulesPackage.CONDITIONAL_RULE__THEN_RULE, null, msgs);
			msgs = basicSetThenRule(newThenRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.CONDITIONAL_RULE__THEN_RULE, newThenRule, newThenRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasictransitionrulesPackage.CONDITIONAL_RULE__ELSE_RULE:
				return basicSetElseRule(null, msgs);
			case BasictransitionrulesPackage.CONDITIONAL_RULE__THEN_RULE:
				return basicSetThenRule(null, msgs);
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
			case BasictransitionrulesPackage.CONDITIONAL_RULE__GUARD:
				if (resolve) return getGuard();
				return basicGetGuard();
			case BasictransitionrulesPackage.CONDITIONAL_RULE__ELSE_RULE:
				return getElseRule();
			case BasictransitionrulesPackage.CONDITIONAL_RULE__THEN_RULE:
				return getThenRule();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BasictransitionrulesPackage.CONDITIONAL_RULE__GUARD:
				setGuard((Term)newValue);
				return;
			case BasictransitionrulesPackage.CONDITIONAL_RULE__ELSE_RULE:
				setElseRule((Rule)newValue);
				return;
			case BasictransitionrulesPackage.CONDITIONAL_RULE__THEN_RULE:
				setThenRule((Rule)newValue);
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
			case BasictransitionrulesPackage.CONDITIONAL_RULE__GUARD:
				setGuard((Term)null);
				return;
			case BasictransitionrulesPackage.CONDITIONAL_RULE__ELSE_RULE:
				setElseRule((Rule)null);
				return;
			case BasictransitionrulesPackage.CONDITIONAL_RULE__THEN_RULE:
				setThenRule((Rule)null);
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
			case BasictransitionrulesPackage.CONDITIONAL_RULE__GUARD:
				return guard != null;
			case BasictransitionrulesPackage.CONDITIONAL_RULE__ELSE_RULE:
				return elseRule != null;
			case BasictransitionrulesPackage.CONDITIONAL_RULE__THEN_RULE:
				return thenRule != null;
		}
		return super.eIsSet(featureID);
	}

} //ConditionalRuleImpl

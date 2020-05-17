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
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Choose Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.ChooseRuleImpl#getIfnone <em>Ifnone</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.ChooseRuleImpl#getDoRule <em>Do Rule</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.ChooseRuleImpl#getGuard <em>Guard</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.ChooseRuleImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.ChooseRuleImpl#getRanges <em>Ranges</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ChooseRuleImpl extends BasicRuleImpl implements ChooseRule {
	/**
	 * The cached value of the '{@link #getIfnone() <em>Ifnone</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIfnone()
	 * @generated
	 * @ordered
	 */
	protected Rule ifnone;

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
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected Term guard;

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
	protected ChooseRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasictransitionrulesPackage.Literals.CHOOSE_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rule getIfnone() {
		return ifnone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIfnone(Rule newIfnone, NotificationChain msgs) {
		Rule oldIfnone = ifnone;
		ifnone = newIfnone;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.CHOOSE_RULE__IFNONE, oldIfnone, newIfnone);
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
	public void setIfnone(Rule newIfnone) {
		if (newIfnone != ifnone) {
			NotificationChain msgs = null;
			if (ifnone != null)
				msgs = ((InternalEObject)ifnone).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BasictransitionrulesPackage.CHOOSE_RULE__IFNONE, null, msgs);
			if (newIfnone != null)
				msgs = ((InternalEObject)newIfnone).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BasictransitionrulesPackage.CHOOSE_RULE__IFNONE, null, msgs);
			msgs = basicSetIfnone(newIfnone, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.CHOOSE_RULE__IFNONE, newIfnone, newIfnone));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.CHOOSE_RULE__DO_RULE, oldDoRule, newDoRule);
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
				msgs = ((InternalEObject)doRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BasictransitionrulesPackage.CHOOSE_RULE__DO_RULE, null, msgs);
			if (newDoRule != null)
				msgs = ((InternalEObject)newDoRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BasictransitionrulesPackage.CHOOSE_RULE__DO_RULE, null, msgs);
			msgs = basicSetDoRule(newDoRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.CHOOSE_RULE__DO_RULE, newDoRule, newDoRule));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BasictransitionrulesPackage.CHOOSE_RULE__GUARD, oldGuard, guard));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.CHOOSE_RULE__GUARD, oldGuard, guard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariableTerm> getVariable() {
		if (variable == null) {
			variable = new EObjectResolvingEList<VariableTerm>(VariableTerm.class, this, BasictransitionrulesPackage.CHOOSE_RULE__VARIABLE);
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Term> getRanges() {
		if (ranges == null) {
			ranges = new EDataTypeEList<Term>(Term.class, this, BasictransitionrulesPackage.CHOOSE_RULE__RANGES);
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
			case BasictransitionrulesPackage.CHOOSE_RULE__IFNONE:
				return basicSetIfnone(null, msgs);
			case BasictransitionrulesPackage.CHOOSE_RULE__DO_RULE:
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
			case BasictransitionrulesPackage.CHOOSE_RULE__IFNONE:
				return getIfnone();
			case BasictransitionrulesPackage.CHOOSE_RULE__DO_RULE:
				return getDoRule();
			case BasictransitionrulesPackage.CHOOSE_RULE__GUARD:
				if (resolve) return getGuard();
				return basicGetGuard();
			case BasictransitionrulesPackage.CHOOSE_RULE__VARIABLE:
				return getVariable();
			case BasictransitionrulesPackage.CHOOSE_RULE__RANGES:
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
			case BasictransitionrulesPackage.CHOOSE_RULE__IFNONE:
				setIfnone((Rule)newValue);
				return;
			case BasictransitionrulesPackage.CHOOSE_RULE__DO_RULE:
				setDoRule((Rule)newValue);
				return;
			case BasictransitionrulesPackage.CHOOSE_RULE__GUARD:
				setGuard((Term)newValue);
				return;
			case BasictransitionrulesPackage.CHOOSE_RULE__VARIABLE:
				getVariable().clear();
				getVariable().addAll((Collection<? extends VariableTerm>)newValue);
				return;
			case BasictransitionrulesPackage.CHOOSE_RULE__RANGES:
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
			case BasictransitionrulesPackage.CHOOSE_RULE__IFNONE:
				setIfnone((Rule)null);
				return;
			case BasictransitionrulesPackage.CHOOSE_RULE__DO_RULE:
				setDoRule((Rule)null);
				return;
			case BasictransitionrulesPackage.CHOOSE_RULE__GUARD:
				setGuard((Term)null);
				return;
			case BasictransitionrulesPackage.CHOOSE_RULE__VARIABLE:
				getVariable().clear();
				return;
			case BasictransitionrulesPackage.CHOOSE_RULE__RANGES:
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
			case BasictransitionrulesPackage.CHOOSE_RULE__IFNONE:
				return ifnone != null;
			case BasictransitionrulesPackage.CHOOSE_RULE__DO_RULE:
				return doRule != null;
			case BasictransitionrulesPackage.CHOOSE_RULE__GUARD:
				return guard != null;
			case BasictransitionrulesPackage.CHOOSE_RULE__VARIABLE:
				return variable != null && !variable.isEmpty();
			case BasictransitionrulesPackage.CHOOSE_RULE__RANGES:
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

} //ChooseRuleImpl

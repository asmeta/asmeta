/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.turbotransitionrules.TryCatchRule;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Try Catch Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.impl.TryCatchRuleImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.impl.TryCatchRuleImpl#getCatchRule <em>Catch Rule</em>}</li>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.impl.TryCatchRuleImpl#getTryRule <em>Try Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TryCatchRuleImpl extends TurboRuleImpl implements TryCatchRule {
	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> location;

	/**
	 * The cached value of the '{@link #getCatchRule() <em>Catch Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatchRule()
	 * @generated
	 * @ordered
	 */
	protected Rule catchRule;

	/**
	 * The cached value of the '{@link #getTryRule() <em>Try Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTryRule()
	 * @generated
	 * @ordered
	 */
	protected Rule tryRule;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TryCatchRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TurbotransitionrulesPackage.Literals.TRY_CATCH_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Term> getLocation() {
		if (location == null) {
			location = new EObjectResolvingEList<Term>(Term.class, this, TurbotransitionrulesPackage.TRY_CATCH_RULE__LOCATION);
		}
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rule getCatchRule() {
		return catchRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCatchRule(Rule newCatchRule, NotificationChain msgs) {
		Rule oldCatchRule = catchRule;
		catchRule = newCatchRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TurbotransitionrulesPackage.TRY_CATCH_RULE__CATCH_RULE, oldCatchRule, newCatchRule);
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
	public void setCatchRule(Rule newCatchRule) {
		if (newCatchRule != catchRule) {
			NotificationChain msgs = null;
			if (catchRule != null)
				msgs = ((InternalEObject)catchRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TurbotransitionrulesPackage.TRY_CATCH_RULE__CATCH_RULE, null, msgs);
			if (newCatchRule != null)
				msgs = ((InternalEObject)newCatchRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TurbotransitionrulesPackage.TRY_CATCH_RULE__CATCH_RULE, null, msgs);
			msgs = basicSetCatchRule(newCatchRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TurbotransitionrulesPackage.TRY_CATCH_RULE__CATCH_RULE, newCatchRule, newCatchRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rule getTryRule() {
		return tryRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTryRule(Rule newTryRule, NotificationChain msgs) {
		Rule oldTryRule = tryRule;
		tryRule = newTryRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TurbotransitionrulesPackage.TRY_CATCH_RULE__TRY_RULE, oldTryRule, newTryRule);
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
	public void setTryRule(Rule newTryRule) {
		if (newTryRule != tryRule) {
			NotificationChain msgs = null;
			if (tryRule != null)
				msgs = ((InternalEObject)tryRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TurbotransitionrulesPackage.TRY_CATCH_RULE__TRY_RULE, null, msgs);
			if (newTryRule != null)
				msgs = ((InternalEObject)newTryRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TurbotransitionrulesPackage.TRY_CATCH_RULE__TRY_RULE, null, msgs);
			msgs = basicSetTryRule(newTryRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TurbotransitionrulesPackage.TRY_CATCH_RULE__TRY_RULE, newTryRule, newTryRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__CATCH_RULE:
				return basicSetCatchRule(null, msgs);
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__TRY_RULE:
				return basicSetTryRule(null, msgs);
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
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__LOCATION:
				return getLocation();
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__CATCH_RULE:
				return getCatchRule();
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__TRY_RULE:
				return getTryRule();
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
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__LOCATION:
				getLocation().clear();
				getLocation().addAll((Collection<? extends Term>)newValue);
				return;
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__CATCH_RULE:
				setCatchRule((Rule)newValue);
				return;
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__TRY_RULE:
				setTryRule((Rule)newValue);
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
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__LOCATION:
				getLocation().clear();
				return;
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__CATCH_RULE:
				setCatchRule((Rule)null);
				return;
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__TRY_RULE:
				setTryRule((Rule)null);
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
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__LOCATION:
				return location != null && !location.isEmpty();
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__CATCH_RULE:
				return catchRule != null;
			case TurbotransitionrulesPackage.TRY_CATCH_RULE__TRY_RULE:
				return tryRule != null;
		}
		return super.eIsSet(featureID);
	}

} //TryCatchRuleImpl

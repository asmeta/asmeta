/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.turbotransitionrules.TurboCallRule;
import asmeta.transitionrules.turbotransitionrules.TurboReturnRule;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Turbo Return Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.impl.TurboReturnRuleImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.impl.TurboReturnRuleImpl#getUpdateRule <em>Update Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TurboReturnRuleImpl extends TurboRuleImpl implements TurboReturnRule {
	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected Term location;

	/**
	 * The cached value of the '{@link #getUpdateRule() <em>Update Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdateRule()
	 * @generated
	 * @ordered
	 */
	protected TurboCallRule updateRule;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TurboReturnRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TurbotransitionrulesPackage.Literals.TURBO_RETURN_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getLocation() {
		if (location != null && location.eIsProxy()) {
			InternalEObject oldLocation = (InternalEObject)location;
			location = (Term)eResolveProxy(oldLocation);
			if (location != oldLocation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TurbotransitionrulesPackage.TURBO_RETURN_RULE__LOCATION, oldLocation, location));
			}
		}
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term basicGetLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLocation(Term newLocation) {
		Term oldLocation = location;
		location = newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TurbotransitionrulesPackage.TURBO_RETURN_RULE__LOCATION, oldLocation, location));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TurboCallRule getUpdateRule() {
		if (updateRule != null && updateRule.eIsProxy()) {
			InternalEObject oldUpdateRule = (InternalEObject)updateRule;
			updateRule = (TurboCallRule)eResolveProxy(oldUpdateRule);
			if (updateRule != oldUpdateRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TurbotransitionrulesPackage.TURBO_RETURN_RULE__UPDATE_RULE, oldUpdateRule, updateRule));
			}
		}
		return updateRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TurboCallRule basicGetUpdateRule() {
		return updateRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUpdateRule(TurboCallRule newUpdateRule) {
		TurboCallRule oldUpdateRule = updateRule;
		updateRule = newUpdateRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TurbotransitionrulesPackage.TURBO_RETURN_RULE__UPDATE_RULE, oldUpdateRule, updateRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TurbotransitionrulesPackage.TURBO_RETURN_RULE__LOCATION:
				if (resolve) return getLocation();
				return basicGetLocation();
			case TurbotransitionrulesPackage.TURBO_RETURN_RULE__UPDATE_RULE:
				if (resolve) return getUpdateRule();
				return basicGetUpdateRule();
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
			case TurbotransitionrulesPackage.TURBO_RETURN_RULE__LOCATION:
				setLocation((Term)newValue);
				return;
			case TurbotransitionrulesPackage.TURBO_RETURN_RULE__UPDATE_RULE:
				setUpdateRule((TurboCallRule)newValue);
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
			case TurbotransitionrulesPackage.TURBO_RETURN_RULE__LOCATION:
				setLocation((Term)null);
				return;
			case TurbotransitionrulesPackage.TURBO_RETURN_RULE__UPDATE_RULE:
				setUpdateRule((TurboCallRule)null);
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
			case TurbotransitionrulesPackage.TURBO_RETURN_RULE__LOCATION:
				return location != null;
			case TurbotransitionrulesPackage.TURBO_RETURN_RULE__UPDATE_RULE:
				return updateRule != null;
		}
		return super.eIsSet(featureID);
	}

} //TurboReturnRuleImpl

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage;
import asmeta.transitionrules.basictransitionrules.UpdateRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Update Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.UpdateRuleImpl#getUpdatingTerm <em>Updating Term</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.UpdateRuleImpl#getLocation <em>Location</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UpdateRuleImpl extends BasicRuleImpl implements UpdateRule {
	/**
	 * The cached value of the '{@link #getUpdatingTerm() <em>Updating Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdatingTerm()
	 * @generated
	 * @ordered
	 */
	protected Term updatingTerm;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UpdateRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasictransitionrulesPackage.Literals.UPDATE_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getUpdatingTerm() {
		if (updatingTerm != null && updatingTerm.eIsProxy()) {
			InternalEObject oldUpdatingTerm = (InternalEObject)updatingTerm;
			updatingTerm = (Term)eResolveProxy(oldUpdatingTerm);
			if (updatingTerm != oldUpdatingTerm) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BasictransitionrulesPackage.UPDATE_RULE__UPDATING_TERM, oldUpdatingTerm, updatingTerm));
			}
		}
		return updatingTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term basicGetUpdatingTerm() {
		return updatingTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUpdatingTerm(Term newUpdatingTerm) {
		Term oldUpdatingTerm = updatingTerm;
		updatingTerm = newUpdatingTerm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.UPDATE_RULE__UPDATING_TERM, oldUpdatingTerm, updatingTerm));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BasictransitionrulesPackage.UPDATE_RULE__LOCATION, oldLocation, location));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.UPDATE_RULE__LOCATION, oldLocation, location));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BasictransitionrulesPackage.UPDATE_RULE__UPDATING_TERM:
				if (resolve) return getUpdatingTerm();
				return basicGetUpdatingTerm();
			case BasictransitionrulesPackage.UPDATE_RULE__LOCATION:
				if (resolve) return getLocation();
				return basicGetLocation();
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
			case BasictransitionrulesPackage.UPDATE_RULE__UPDATING_TERM:
				setUpdatingTerm((Term)newValue);
				return;
			case BasictransitionrulesPackage.UPDATE_RULE__LOCATION:
				setLocation((Term)newValue);
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
			case BasictransitionrulesPackage.UPDATE_RULE__UPDATING_TERM:
				setUpdatingTerm((Term)null);
				return;
			case BasictransitionrulesPackage.UPDATE_RULE__LOCATION:
				setLocation((Term)null);
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
			case BasictransitionrulesPackage.UPDATE_RULE__UPDATING_TERM:
				return updatingTerm != null;
			case BasictransitionrulesPackage.UPDATE_RULE__LOCATION:
				return location != null;
		}
		return super.eIsSet(featureID);
	}

} //UpdateRuleImpl

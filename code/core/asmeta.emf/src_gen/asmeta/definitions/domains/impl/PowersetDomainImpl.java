/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.DomainsPackage;
import asmeta.definitions.domains.PowersetDomain;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Powerset Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.impl.PowersetDomainImpl#getBaseDomain <em>Base Domain</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PowersetDomainImpl extends StructuredTdImpl implements PowersetDomain {
	/**
	 * The cached value of the '{@link #getBaseDomain() <em>Base Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseDomain()
	 * @generated
	 * @ordered
	 */
	protected Domain baseDomain;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PowersetDomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomainsPackage.Literals.POWERSET_DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Domain getBaseDomain() {
		if (baseDomain != null && baseDomain.eIsProxy()) {
			InternalEObject oldBaseDomain = (InternalEObject)baseDomain;
			baseDomain = (Domain)eResolveProxy(oldBaseDomain);
			if (baseDomain != oldBaseDomain) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomainsPackage.POWERSET_DOMAIN__BASE_DOMAIN, oldBaseDomain, baseDomain));
			}
		}
		return baseDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain basicGetBaseDomain() {
		return baseDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBaseDomain(Domain newBaseDomain) {
		Domain oldBaseDomain = baseDomain;
		baseDomain = newBaseDomain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainsPackage.POWERSET_DOMAIN__BASE_DOMAIN, oldBaseDomain, baseDomain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomainsPackage.POWERSET_DOMAIN__BASE_DOMAIN:
				if (resolve) return getBaseDomain();
				return basicGetBaseDomain();
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
			case DomainsPackage.POWERSET_DOMAIN__BASE_DOMAIN:
				setBaseDomain((Domain)newValue);
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
			case DomainsPackage.POWERSET_DOMAIN__BASE_DOMAIN:
				setBaseDomain((Domain)null);
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
			case DomainsPackage.POWERSET_DOMAIN__BASE_DOMAIN:
				return baseDomain != null;
		}
		return super.eIsSet(featureID);
	}

} //PowersetDomainImpl

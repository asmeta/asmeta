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
import asmeta.definitions.domains.MapDomain;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Map Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.impl.MapDomainImpl#getSourceDomain <em>Source Domain</em>}</li>
 *   <li>{@link asmeta.definitions.domains.impl.MapDomainImpl#getTargetDomain <em>Target Domain</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MapDomainImpl extends StructuredTdImpl implements MapDomain {
	/**
	 * The cached value of the '{@link #getSourceDomain() <em>Source Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceDomain()
	 * @generated
	 * @ordered
	 */
	protected Domain sourceDomain;

	/**
	 * The cached value of the '{@link #getTargetDomain() <em>Target Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetDomain()
	 * @generated
	 * @ordered
	 */
	protected Domain targetDomain;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MapDomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomainsPackage.Literals.MAP_DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Domain getSourceDomain() {
		if (sourceDomain != null && sourceDomain.eIsProxy()) {
			InternalEObject oldSourceDomain = (InternalEObject)sourceDomain;
			sourceDomain = (Domain)eResolveProxy(oldSourceDomain);
			if (sourceDomain != oldSourceDomain) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomainsPackage.MAP_DOMAIN__SOURCE_DOMAIN, oldSourceDomain, sourceDomain));
			}
		}
		return sourceDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain basicGetSourceDomain() {
		return sourceDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceDomain(Domain newSourceDomain) {
		Domain oldSourceDomain = sourceDomain;
		sourceDomain = newSourceDomain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainsPackage.MAP_DOMAIN__SOURCE_DOMAIN, oldSourceDomain, sourceDomain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Domain getTargetDomain() {
		if (targetDomain != null && targetDomain.eIsProxy()) {
			InternalEObject oldTargetDomain = (InternalEObject)targetDomain;
			targetDomain = (Domain)eResolveProxy(oldTargetDomain);
			if (targetDomain != oldTargetDomain) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomainsPackage.MAP_DOMAIN__TARGET_DOMAIN, oldTargetDomain, targetDomain));
			}
		}
		return targetDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain basicGetTargetDomain() {
		return targetDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetDomain(Domain newTargetDomain) {
		Domain oldTargetDomain = targetDomain;
		targetDomain = newTargetDomain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainsPackage.MAP_DOMAIN__TARGET_DOMAIN, oldTargetDomain, targetDomain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomainsPackage.MAP_DOMAIN__SOURCE_DOMAIN:
				if (resolve) return getSourceDomain();
				return basicGetSourceDomain();
			case DomainsPackage.MAP_DOMAIN__TARGET_DOMAIN:
				if (resolve) return getTargetDomain();
				return basicGetTargetDomain();
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
			case DomainsPackage.MAP_DOMAIN__SOURCE_DOMAIN:
				setSourceDomain((Domain)newValue);
				return;
			case DomainsPackage.MAP_DOMAIN__TARGET_DOMAIN:
				setTargetDomain((Domain)newValue);
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
			case DomainsPackage.MAP_DOMAIN__SOURCE_DOMAIN:
				setSourceDomain((Domain)null);
				return;
			case DomainsPackage.MAP_DOMAIN__TARGET_DOMAIN:
				setTargetDomain((Domain)null);
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
			case DomainsPackage.MAP_DOMAIN__SOURCE_DOMAIN:
				return sourceDomain != null;
			case DomainsPackage.MAP_DOMAIN__TARGET_DOMAIN:
				return targetDomain != null;
		}
		return super.eIsSet(featureID);
	}

} //MapDomainImpl

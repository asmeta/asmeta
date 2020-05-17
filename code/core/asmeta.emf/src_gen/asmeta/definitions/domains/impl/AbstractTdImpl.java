/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.DomainsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Td</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.impl.AbstractTdImpl#getIsDynamic <em>Is Dynamic</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AbstractTdImpl extends TypeDomainImpl implements AbstractTd {
	/**
	 * The default value of the '{@link #getIsDynamic() <em>Is Dynamic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsDynamic()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean IS_DYNAMIC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIsDynamic() <em>Is Dynamic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsDynamic()
	 * @generated
	 * @ordered
	 */
	protected Boolean isDynamic = IS_DYNAMIC_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractTdImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomainsPackage.Literals.ABSTRACT_TD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getIsDynamic() {
		return isDynamic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsDynamic(Boolean newIsDynamic) {
		Boolean oldIsDynamic = isDynamic;
		isDynamic = newIsDynamic;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainsPackage.ABSTRACT_TD__IS_DYNAMIC, oldIsDynamic, isDynamic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomainsPackage.ABSTRACT_TD__IS_DYNAMIC:
				return getIsDynamic();
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
			case DomainsPackage.ABSTRACT_TD__IS_DYNAMIC:
				setIsDynamic((Boolean)newValue);
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
			case DomainsPackage.ABSTRACT_TD__IS_DYNAMIC:
				setIsDynamic(IS_DYNAMIC_EDEFAULT);
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
			case DomainsPackage.ABSTRACT_TD__IS_DYNAMIC:
				return IS_DYNAMIC_EDEFAULT == null ? isDynamic != null : !IS_DYNAMIC_EDEFAULT.equals(isDynamic);
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
		result.append(" (isDynamic: ");
		result.append(isDynamic);
		result.append(')');
		return result.toString();
	}

} //AbstractTdImpl

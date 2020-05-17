/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EDataTypeEList;

import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.DomainsPackage;
import asmeta.definitions.domains.ProductDomain;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Product Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.impl.ProductDomainImpl#getDomains <em>Domains</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProductDomainImpl extends StructuredTdImpl implements ProductDomain {
	/**
	 * The cached value of the '{@link #getDomains() <em>Domains</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomains()
	 * @generated
	 * @ordered
	 */
	protected EList<Domain> domains;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProductDomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomainsPackage.Literals.PRODUCT_DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Domain> getDomains() {
		if (domains == null) {
			domains = new EDataTypeEList<Domain>(Domain.class, this, DomainsPackage.PRODUCT_DOMAIN__DOMAINS);
		}
		return domains;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomainsPackage.PRODUCT_DOMAIN__DOMAINS:
				return getDomains();
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
			case DomainsPackage.PRODUCT_DOMAIN__DOMAINS:
				getDomains().clear();
				getDomains().addAll((Collection<? extends Domain>)newValue);
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
			case DomainsPackage.PRODUCT_DOMAIN__DOMAINS:
				getDomains().clear();
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
			case DomainsPackage.PRODUCT_DOMAIN__DOMAINS:
				return domains != null && !domains.isEmpty();
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
		result.append(" (domains: ");
		result.append(domains);
		result.append(')');
		return result.toString();
	}

} //ProductDomainImpl

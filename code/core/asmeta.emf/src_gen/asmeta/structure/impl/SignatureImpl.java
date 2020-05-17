/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.Function;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.DomainsPackage;
import asmeta.definitions.domains.StructuredTd;
import asmeta.structure.Header;
import asmeta.structure.Signature;
import asmeta.structure.StructurePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Signature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.impl.SignatureImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link asmeta.structure.impl.SignatureImpl#getFunction <em>Function</em>}</li>
 *   <li>{@link asmeta.structure.impl.SignatureImpl#getHeaderSection <em>Header Section</em>}</li>
 *   <li>{@link asmeta.structure.impl.SignatureImpl#getStructuredDomain <em>Structured Domain</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SignatureImpl extends EObjectImpl implements Signature {
	/**
	 * The cached value of the '{@link #getDomain() <em>Domain</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
	protected EList<Domain> domain;

	/**
	 * The cached value of the '{@link #getFunction() <em>Function</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunction()
	 * @generated
	 * @ordered
	 */
	protected EList<Function> function;

	/**
	 * The cached value of the '{@link #getStructuredDomain() <em>Structured Domain</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStructuredDomain()
	 * @generated
	 * @ordered
	 */
	protected EList<StructuredTd> structuredDomain;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SignatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StructurePackage.Literals.SIGNATURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Domain> getDomain() {
		if (domain == null) {
			domain = new EObjectContainmentWithInverseEList<Domain>(Domain.class, this, StructurePackage.SIGNATURE__DOMAIN, DomainsPackage.DOMAIN__SIGNATURE);
		}
		return domain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Function> getFunction() {
		if (function == null) {
			function = new EObjectContainmentWithInverseEList<Function>(Function.class, this, StructurePackage.SIGNATURE__FUNCTION, DefinitionsPackage.FUNCTION__SIGNATURE);
		}
		return function;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Header getHeaderSection() {
		if (eContainerFeatureID() != StructurePackage.SIGNATURE__HEADER_SECTION) return null;
		return (Header)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHeaderSection(Header newHeaderSection, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newHeaderSection, StructurePackage.SIGNATURE__HEADER_SECTION, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHeaderSection(Header newHeaderSection) {
		if (newHeaderSection != eInternalContainer() || (eContainerFeatureID() != StructurePackage.SIGNATURE__HEADER_SECTION && newHeaderSection != null)) {
			if (EcoreUtil.isAncestor(this, newHeaderSection))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newHeaderSection != null)
				msgs = ((InternalEObject)newHeaderSection).eInverseAdd(this, StructurePackage.HEADER__SIGNATURE, Header.class, msgs);
			msgs = basicSetHeaderSection(newHeaderSection, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.SIGNATURE__HEADER_SECTION, newHeaderSection, newHeaderSection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<StructuredTd> getStructuredDomain() {
		if (structuredDomain == null) {
			structuredDomain = new EObjectContainmentEList<StructuredTd>(StructuredTd.class, this, StructurePackage.SIGNATURE__STRUCTURED_DOMAIN);
		}
		return structuredDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StructurePackage.SIGNATURE__DOMAIN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDomain()).basicAdd(otherEnd, msgs);
			case StructurePackage.SIGNATURE__FUNCTION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getFunction()).basicAdd(otherEnd, msgs);
			case StructurePackage.SIGNATURE__HEADER_SECTION:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetHeaderSection((Header)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StructurePackage.SIGNATURE__DOMAIN:
				return ((InternalEList<?>)getDomain()).basicRemove(otherEnd, msgs);
			case StructurePackage.SIGNATURE__FUNCTION:
				return ((InternalEList<?>)getFunction()).basicRemove(otherEnd, msgs);
			case StructurePackage.SIGNATURE__HEADER_SECTION:
				return basicSetHeaderSection(null, msgs);
			case StructurePackage.SIGNATURE__STRUCTURED_DOMAIN:
				return ((InternalEList<?>)getStructuredDomain()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case StructurePackage.SIGNATURE__HEADER_SECTION:
				return eInternalContainer().eInverseRemove(this, StructurePackage.HEADER__SIGNATURE, Header.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StructurePackage.SIGNATURE__DOMAIN:
				return getDomain();
			case StructurePackage.SIGNATURE__FUNCTION:
				return getFunction();
			case StructurePackage.SIGNATURE__HEADER_SECTION:
				return getHeaderSection();
			case StructurePackage.SIGNATURE__STRUCTURED_DOMAIN:
				return getStructuredDomain();
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
			case StructurePackage.SIGNATURE__DOMAIN:
				getDomain().clear();
				getDomain().addAll((Collection<? extends Domain>)newValue);
				return;
			case StructurePackage.SIGNATURE__FUNCTION:
				getFunction().clear();
				getFunction().addAll((Collection<? extends Function>)newValue);
				return;
			case StructurePackage.SIGNATURE__HEADER_SECTION:
				setHeaderSection((Header)newValue);
				return;
			case StructurePackage.SIGNATURE__STRUCTURED_DOMAIN:
				getStructuredDomain().clear();
				getStructuredDomain().addAll((Collection<? extends StructuredTd>)newValue);
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
			case StructurePackage.SIGNATURE__DOMAIN:
				getDomain().clear();
				return;
			case StructurePackage.SIGNATURE__FUNCTION:
				getFunction().clear();
				return;
			case StructurePackage.SIGNATURE__HEADER_SECTION:
				setHeaderSection((Header)null);
				return;
			case StructurePackage.SIGNATURE__STRUCTURED_DOMAIN:
				getStructuredDomain().clear();
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
			case StructurePackage.SIGNATURE__DOMAIN:
				return domain != null && !domain.isEmpty();
			case StructurePackage.SIGNATURE__FUNCTION:
				return function != null && !function.isEmpty();
			case StructurePackage.SIGNATURE__HEADER_SECTION:
				return getHeaderSection() != null;
			case StructurePackage.SIGNATURE__STRUCTURED_DOMAIN:
				return structuredDomain != null && !structuredDomain.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SignatureImpl

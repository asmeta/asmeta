/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.DomainsPackage;
import asmeta.definitions.domains.TypeDomain;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.structure.StructurePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Concrete Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.impl.ConcreteDomainImpl#getInitialization <em>Initialization</em>}</li>
 *   <li>{@link asmeta.definitions.domains.impl.ConcreteDomainImpl#getDefinition <em>Definition</em>}</li>
 *   <li>{@link asmeta.definitions.domains.impl.ConcreteDomainImpl#getTypeDomain <em>Type Domain</em>}</li>
 *   <li>{@link asmeta.definitions.domains.impl.ConcreteDomainImpl#getIsDynamic <em>Is Dynamic</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConcreteDomainImpl extends DomainImpl implements ConcreteDomain {
	/**
	 * The cached value of the '{@link #getInitialization() <em>Initialization</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialization()
	 * @generated
	 * @ordered
	 */
	protected EList<DomainInitialization> initialization;

	/**
	 * The cached value of the '{@link #getDefinition() <em>Definition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinition()
	 * @generated
	 * @ordered
	 */
	protected DomainDefinition definition;

	/**
	 * The cached value of the '{@link #getTypeDomain() <em>Type Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeDomain()
	 * @generated
	 * @ordered
	 */
	protected TypeDomain typeDomain;

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
	protected ConcreteDomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomainsPackage.Literals.CONCRETE_DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DomainInitialization> getInitialization() {
		if (initialization == null) {
			initialization = new EObjectWithInverseResolvingEList<DomainInitialization>(DomainInitialization.class, this, DomainsPackage.CONCRETE_DOMAIN__INITIALIZATION, StructurePackage.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN);
		}
		return initialization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DomainDefinition getDefinition() {
		if (definition != null && definition.eIsProxy()) {
			InternalEObject oldDefinition = (InternalEObject)definition;
			definition = (DomainDefinition)eResolveProxy(oldDefinition);
			if (definition != oldDefinition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomainsPackage.CONCRETE_DOMAIN__DEFINITION, oldDefinition, definition));
			}
		}
		return definition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainDefinition basicGetDefinition() {
		return definition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefinition(DomainDefinition newDefinition, NotificationChain msgs) {
		DomainDefinition oldDefinition = definition;
		definition = newDefinition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DomainsPackage.CONCRETE_DOMAIN__DEFINITION, oldDefinition, newDefinition);
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
	public void setDefinition(DomainDefinition newDefinition) {
		if (newDefinition != definition) {
			NotificationChain msgs = null;
			if (definition != null)
				msgs = ((InternalEObject)definition).eInverseRemove(this, StructurePackage.DOMAIN_DEFINITION__DEFINED_DOMAIN, DomainDefinition.class, msgs);
			if (newDefinition != null)
				msgs = ((InternalEObject)newDefinition).eInverseAdd(this, StructurePackage.DOMAIN_DEFINITION__DEFINED_DOMAIN, DomainDefinition.class, msgs);
			msgs = basicSetDefinition(newDefinition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainsPackage.CONCRETE_DOMAIN__DEFINITION, newDefinition, newDefinition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeDomain getTypeDomain() {
		if (typeDomain != null && typeDomain.eIsProxy()) {
			InternalEObject oldTypeDomain = (InternalEObject)typeDomain;
			typeDomain = (TypeDomain)eResolveProxy(oldTypeDomain);
			if (typeDomain != oldTypeDomain) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomainsPackage.CONCRETE_DOMAIN__TYPE_DOMAIN, oldTypeDomain, typeDomain));
			}
		}
		return typeDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeDomain basicGetTypeDomain() {
		return typeDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypeDomain(TypeDomain newTypeDomain) {
		TypeDomain oldTypeDomain = typeDomain;
		typeDomain = newTypeDomain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainsPackage.CONCRETE_DOMAIN__TYPE_DOMAIN, oldTypeDomain, typeDomain));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DomainsPackage.CONCRETE_DOMAIN__IS_DYNAMIC, oldIsDynamic, isDynamic));
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
			case DomainsPackage.CONCRETE_DOMAIN__INITIALIZATION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInitialization()).basicAdd(otherEnd, msgs);
			case DomainsPackage.CONCRETE_DOMAIN__DEFINITION:
				if (definition != null)
					msgs = ((InternalEObject)definition).eInverseRemove(this, StructurePackage.DOMAIN_DEFINITION__DEFINED_DOMAIN, DomainDefinition.class, msgs);
				return basicSetDefinition((DomainDefinition)otherEnd, msgs);
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
			case DomainsPackage.CONCRETE_DOMAIN__INITIALIZATION:
				return ((InternalEList<?>)getInitialization()).basicRemove(otherEnd, msgs);
			case DomainsPackage.CONCRETE_DOMAIN__DEFINITION:
				return basicSetDefinition(null, msgs);
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
			case DomainsPackage.CONCRETE_DOMAIN__INITIALIZATION:
				return getInitialization();
			case DomainsPackage.CONCRETE_DOMAIN__DEFINITION:
				if (resolve) return getDefinition();
				return basicGetDefinition();
			case DomainsPackage.CONCRETE_DOMAIN__TYPE_DOMAIN:
				if (resolve) return getTypeDomain();
				return basicGetTypeDomain();
			case DomainsPackage.CONCRETE_DOMAIN__IS_DYNAMIC:
				return getIsDynamic();
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
			case DomainsPackage.CONCRETE_DOMAIN__INITIALIZATION:
				getInitialization().clear();
				getInitialization().addAll((Collection<? extends DomainInitialization>)newValue);
				return;
			case DomainsPackage.CONCRETE_DOMAIN__DEFINITION:
				setDefinition((DomainDefinition)newValue);
				return;
			case DomainsPackage.CONCRETE_DOMAIN__TYPE_DOMAIN:
				setTypeDomain((TypeDomain)newValue);
				return;
			case DomainsPackage.CONCRETE_DOMAIN__IS_DYNAMIC:
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
			case DomainsPackage.CONCRETE_DOMAIN__INITIALIZATION:
				getInitialization().clear();
				return;
			case DomainsPackage.CONCRETE_DOMAIN__DEFINITION:
				setDefinition((DomainDefinition)null);
				return;
			case DomainsPackage.CONCRETE_DOMAIN__TYPE_DOMAIN:
				setTypeDomain((TypeDomain)null);
				return;
			case DomainsPackage.CONCRETE_DOMAIN__IS_DYNAMIC:
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
			case DomainsPackage.CONCRETE_DOMAIN__INITIALIZATION:
				return initialization != null && !initialization.isEmpty();
			case DomainsPackage.CONCRETE_DOMAIN__DEFINITION:
				return definition != null;
			case DomainsPackage.CONCRETE_DOMAIN__TYPE_DOMAIN:
				return typeDomain != null;
			case DomainsPackage.CONCRETE_DOMAIN__IS_DYNAMIC:
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

} //ConcreteDomainImpl

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.DomainsPackage;
import asmeta.structure.DomainDefinition;
import asmeta.structure.StructurePackage;
import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.impl.DomainDefinitionImpl#getBody <em>Body</em>}</li>
 *   <li>{@link asmeta.structure.impl.DomainDefinitionImpl#getDefinedDomain <em>Defined Domain</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DomainDefinitionImpl extends EObjectImpl implements DomainDefinition {
	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Term body;

	/**
	 * The cached value of the '{@link #getDefinedDomain() <em>Defined Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedDomain()
	 * @generated
	 * @ordered
	 */
	protected ConcreteDomain definedDomain;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DomainDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StructurePackage.Literals.DOMAIN_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBody(Term newBody, NotificationChain msgs) {
		Term oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StructurePackage.DOMAIN_DEFINITION__BODY, oldBody, newBody);
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
	public void setBody(Term newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StructurePackage.DOMAIN_DEFINITION__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StructurePackage.DOMAIN_DEFINITION__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.DOMAIN_DEFINITION__BODY, newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConcreteDomain getDefinedDomain() {
		if (definedDomain != null && definedDomain.eIsProxy()) {
			InternalEObject oldDefinedDomain = (InternalEObject)definedDomain;
			definedDomain = (ConcreteDomain)eResolveProxy(oldDefinedDomain);
			if (definedDomain != oldDefinedDomain) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StructurePackage.DOMAIN_DEFINITION__DEFINED_DOMAIN, oldDefinedDomain, definedDomain));
			}
		}
		return definedDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConcreteDomain basicGetDefinedDomain() {
		return definedDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefinedDomain(ConcreteDomain newDefinedDomain, NotificationChain msgs) {
		ConcreteDomain oldDefinedDomain = definedDomain;
		definedDomain = newDefinedDomain;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StructurePackage.DOMAIN_DEFINITION__DEFINED_DOMAIN, oldDefinedDomain, newDefinedDomain);
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
	public void setDefinedDomain(ConcreteDomain newDefinedDomain) {
		if (newDefinedDomain != definedDomain) {
			NotificationChain msgs = null;
			if (definedDomain != null)
				msgs = ((InternalEObject)definedDomain).eInverseRemove(this, DomainsPackage.CONCRETE_DOMAIN__DEFINITION, ConcreteDomain.class, msgs);
			if (newDefinedDomain != null)
				msgs = ((InternalEObject)newDefinedDomain).eInverseAdd(this, DomainsPackage.CONCRETE_DOMAIN__DEFINITION, ConcreteDomain.class, msgs);
			msgs = basicSetDefinedDomain(newDefinedDomain, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.DOMAIN_DEFINITION__DEFINED_DOMAIN, newDefinedDomain, newDefinedDomain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StructurePackage.DOMAIN_DEFINITION__DEFINED_DOMAIN:
				if (definedDomain != null)
					msgs = ((InternalEObject)definedDomain).eInverseRemove(this, DomainsPackage.CONCRETE_DOMAIN__DEFINITION, ConcreteDomain.class, msgs);
				return basicSetDefinedDomain((ConcreteDomain)otherEnd, msgs);
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
			case StructurePackage.DOMAIN_DEFINITION__BODY:
				return basicSetBody(null, msgs);
			case StructurePackage.DOMAIN_DEFINITION__DEFINED_DOMAIN:
				return basicSetDefinedDomain(null, msgs);
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
			case StructurePackage.DOMAIN_DEFINITION__BODY:
				return getBody();
			case StructurePackage.DOMAIN_DEFINITION__DEFINED_DOMAIN:
				if (resolve) return getDefinedDomain();
				return basicGetDefinedDomain();
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
			case StructurePackage.DOMAIN_DEFINITION__BODY:
				setBody((Term)newValue);
				return;
			case StructurePackage.DOMAIN_DEFINITION__DEFINED_DOMAIN:
				setDefinedDomain((ConcreteDomain)newValue);
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
			case StructurePackage.DOMAIN_DEFINITION__BODY:
				setBody((Term)null);
				return;
			case StructurePackage.DOMAIN_DEFINITION__DEFINED_DOMAIN:
				setDefinedDomain((ConcreteDomain)null);
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
			case StructurePackage.DOMAIN_DEFINITION__BODY:
				return body != null;
			case StructurePackage.DOMAIN_DEFINITION__DEFINED_DOMAIN:
				return definedDomain != null;
		}
		return super.eIsSet(featureID);
	}

} //DomainDefinitionImpl

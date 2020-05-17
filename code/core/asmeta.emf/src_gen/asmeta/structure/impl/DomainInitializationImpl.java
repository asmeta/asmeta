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
import org.eclipse.emf.ecore.util.EcoreUtil;

import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.DomainsPackage;
import asmeta.structure.DomainInitialization;
import asmeta.structure.Initialization;
import asmeta.structure.StructurePackage;
import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain Initialization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.impl.DomainInitializationImpl#getInitializedDomain <em>Initialized Domain</em>}</li>
 *   <li>{@link asmeta.structure.impl.DomainInitializationImpl#getBody <em>Body</em>}</li>
 *   <li>{@link asmeta.structure.impl.DomainInitializationImpl#getInitialState <em>Initial State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DomainInitializationImpl extends EObjectImpl implements DomainInitialization {
	/**
	 * The cached value of the '{@link #getInitializedDomain() <em>Initialized Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitializedDomain()
	 * @generated
	 * @ordered
	 */
	protected ConcreteDomain initializedDomain;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DomainInitializationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StructurePackage.Literals.DOMAIN_INITIALIZATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConcreteDomain getInitializedDomain() {
		if (initializedDomain != null && initializedDomain.eIsProxy()) {
			InternalEObject oldInitializedDomain = (InternalEObject)initializedDomain;
			initializedDomain = (ConcreteDomain)eResolveProxy(oldInitializedDomain);
			if (initializedDomain != oldInitializedDomain) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StructurePackage.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN, oldInitializedDomain, initializedDomain));
			}
		}
		return initializedDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConcreteDomain basicGetInitializedDomain() {
		return initializedDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitializedDomain(ConcreteDomain newInitializedDomain, NotificationChain msgs) {
		ConcreteDomain oldInitializedDomain = initializedDomain;
		initializedDomain = newInitializedDomain;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StructurePackage.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN, oldInitializedDomain, newInitializedDomain);
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
	public void setInitializedDomain(ConcreteDomain newInitializedDomain) {
		if (newInitializedDomain != initializedDomain) {
			NotificationChain msgs = null;
			if (initializedDomain != null)
				msgs = ((InternalEObject)initializedDomain).eInverseRemove(this, DomainsPackage.CONCRETE_DOMAIN__INITIALIZATION, ConcreteDomain.class, msgs);
			if (newInitializedDomain != null)
				msgs = ((InternalEObject)newInitializedDomain).eInverseAdd(this, DomainsPackage.CONCRETE_DOMAIN__INITIALIZATION, ConcreteDomain.class, msgs);
			msgs = basicSetInitializedDomain(newInitializedDomain, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN, newInitializedDomain, newInitializedDomain));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StructurePackage.DOMAIN_INITIALIZATION__BODY, oldBody, newBody);
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
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StructurePackage.DOMAIN_INITIALIZATION__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StructurePackage.DOMAIN_INITIALIZATION__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.DOMAIN_INITIALIZATION__BODY, newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Initialization getInitialState() {
		if (eContainerFeatureID() != StructurePackage.DOMAIN_INITIALIZATION__INITIAL_STATE) return null;
		return (Initialization)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitialState(Initialization newInitialState, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newInitialState, StructurePackage.DOMAIN_INITIALIZATION__INITIAL_STATE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInitialState(Initialization newInitialState) {
		if (newInitialState != eInternalContainer() || (eContainerFeatureID() != StructurePackage.DOMAIN_INITIALIZATION__INITIAL_STATE && newInitialState != null)) {
			if (EcoreUtil.isAncestor(this, newInitialState))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newInitialState != null)
				msgs = ((InternalEObject)newInitialState).eInverseAdd(this, StructurePackage.INITIALIZATION__DOMAIN_INITIALIZATION, Initialization.class, msgs);
			msgs = basicSetInitialState(newInitialState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.DOMAIN_INITIALIZATION__INITIAL_STATE, newInitialState, newInitialState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StructurePackage.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN:
				if (initializedDomain != null)
					msgs = ((InternalEObject)initializedDomain).eInverseRemove(this, DomainsPackage.CONCRETE_DOMAIN__INITIALIZATION, ConcreteDomain.class, msgs);
				return basicSetInitializedDomain((ConcreteDomain)otherEnd, msgs);
			case StructurePackage.DOMAIN_INITIALIZATION__INITIAL_STATE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetInitialState((Initialization)otherEnd, msgs);
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
			case StructurePackage.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN:
				return basicSetInitializedDomain(null, msgs);
			case StructurePackage.DOMAIN_INITIALIZATION__BODY:
				return basicSetBody(null, msgs);
			case StructurePackage.DOMAIN_INITIALIZATION__INITIAL_STATE:
				return basicSetInitialState(null, msgs);
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
			case StructurePackage.DOMAIN_INITIALIZATION__INITIAL_STATE:
				return eInternalContainer().eInverseRemove(this, StructurePackage.INITIALIZATION__DOMAIN_INITIALIZATION, Initialization.class, msgs);
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
			case StructurePackage.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN:
				if (resolve) return getInitializedDomain();
				return basicGetInitializedDomain();
			case StructurePackage.DOMAIN_INITIALIZATION__BODY:
				return getBody();
			case StructurePackage.DOMAIN_INITIALIZATION__INITIAL_STATE:
				return getInitialState();
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
			case StructurePackage.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN:
				setInitializedDomain((ConcreteDomain)newValue);
				return;
			case StructurePackage.DOMAIN_INITIALIZATION__BODY:
				setBody((Term)newValue);
				return;
			case StructurePackage.DOMAIN_INITIALIZATION__INITIAL_STATE:
				setInitialState((Initialization)newValue);
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
			case StructurePackage.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN:
				setInitializedDomain((ConcreteDomain)null);
				return;
			case StructurePackage.DOMAIN_INITIALIZATION__BODY:
				setBody((Term)null);
				return;
			case StructurePackage.DOMAIN_INITIALIZATION__INITIAL_STATE:
				setInitialState((Initialization)null);
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
			case StructurePackage.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN:
				return initializedDomain != null;
			case StructurePackage.DOMAIN_INITIALIZATION__BODY:
				return body != null;
			case StructurePackage.DOMAIN_INITIALIZATION__INITIAL_STATE:
				return getInitialState() != null;
		}
		return super.eIsSet(featureID);
	}

} //DomainInitializationImpl

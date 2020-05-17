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

import asmeta.definitions.domains.Domain;
import asmeta.structure.AgentInitialization;
import asmeta.structure.Initialization;
import asmeta.structure.StructurePackage;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Agent Initialization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.impl.AgentInitializationImpl#getProgram <em>Program</em>}</li>
 *   <li>{@link asmeta.structure.impl.AgentInitializationImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link asmeta.structure.impl.AgentInitializationImpl#getInitialState <em>Initial State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AgentInitializationImpl extends EObjectImpl implements AgentInitialization {
	/**
	 * The cached value of the '{@link #getProgram() <em>Program</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProgram()
	 * @generated
	 * @ordered
	 */
	protected MacroCallRule program;

	/**
	 * The cached value of the '{@link #getDomain() <em>Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
	protected Domain domain;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AgentInitializationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StructurePackage.Literals.AGENT_INITIALIZATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MacroCallRule getProgram() {
		return program;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProgram(MacroCallRule newProgram, NotificationChain msgs) {
		MacroCallRule oldProgram = program;
		program = newProgram;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StructurePackage.AGENT_INITIALIZATION__PROGRAM, oldProgram, newProgram);
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
	public void setProgram(MacroCallRule newProgram) {
		if (newProgram != program) {
			NotificationChain msgs = null;
			if (program != null)
				msgs = ((InternalEObject)program).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StructurePackage.AGENT_INITIALIZATION__PROGRAM, null, msgs);
			if (newProgram != null)
				msgs = ((InternalEObject)newProgram).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StructurePackage.AGENT_INITIALIZATION__PROGRAM, null, msgs);
			msgs = basicSetProgram(newProgram, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.AGENT_INITIALIZATION__PROGRAM, newProgram, newProgram));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Domain getDomain() {
		if (domain != null && domain.eIsProxy()) {
			InternalEObject oldDomain = (InternalEObject)domain;
			domain = (Domain)eResolveProxy(oldDomain);
			if (domain != oldDomain) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StructurePackage.AGENT_INITIALIZATION__DOMAIN, oldDomain, domain));
			}
		}
		return domain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain basicGetDomain() {
		return domain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDomain(Domain newDomain) {
		Domain oldDomain = domain;
		domain = newDomain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.AGENT_INITIALIZATION__DOMAIN, oldDomain, domain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Initialization getInitialState() {
		if (eContainerFeatureID() != StructurePackage.AGENT_INITIALIZATION__INITIAL_STATE) return null;
		return (Initialization)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitialState(Initialization newInitialState, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newInitialState, StructurePackage.AGENT_INITIALIZATION__INITIAL_STATE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInitialState(Initialization newInitialState) {
		if (newInitialState != eInternalContainer() || (eContainerFeatureID() != StructurePackage.AGENT_INITIALIZATION__INITIAL_STATE && newInitialState != null)) {
			if (EcoreUtil.isAncestor(this, newInitialState))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newInitialState != null)
				msgs = ((InternalEObject)newInitialState).eInverseAdd(this, StructurePackage.INITIALIZATION__AGENT_INITIALIZATION, Initialization.class, msgs);
			msgs = basicSetInitialState(newInitialState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.AGENT_INITIALIZATION__INITIAL_STATE, newInitialState, newInitialState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StructurePackage.AGENT_INITIALIZATION__INITIAL_STATE:
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
			case StructurePackage.AGENT_INITIALIZATION__PROGRAM:
				return basicSetProgram(null, msgs);
			case StructurePackage.AGENT_INITIALIZATION__INITIAL_STATE:
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
			case StructurePackage.AGENT_INITIALIZATION__INITIAL_STATE:
				return eInternalContainer().eInverseRemove(this, StructurePackage.INITIALIZATION__AGENT_INITIALIZATION, Initialization.class, msgs);
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
			case StructurePackage.AGENT_INITIALIZATION__PROGRAM:
				return getProgram();
			case StructurePackage.AGENT_INITIALIZATION__DOMAIN:
				if (resolve) return getDomain();
				return basicGetDomain();
			case StructurePackage.AGENT_INITIALIZATION__INITIAL_STATE:
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
			case StructurePackage.AGENT_INITIALIZATION__PROGRAM:
				setProgram((MacroCallRule)newValue);
				return;
			case StructurePackage.AGENT_INITIALIZATION__DOMAIN:
				setDomain((Domain)newValue);
				return;
			case StructurePackage.AGENT_INITIALIZATION__INITIAL_STATE:
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
			case StructurePackage.AGENT_INITIALIZATION__PROGRAM:
				setProgram((MacroCallRule)null);
				return;
			case StructurePackage.AGENT_INITIALIZATION__DOMAIN:
				setDomain((Domain)null);
				return;
			case StructurePackage.AGENT_INITIALIZATION__INITIAL_STATE:
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
			case StructurePackage.AGENT_INITIALIZATION__PROGRAM:
				return program != null;
			case StructurePackage.AGENT_INITIALIZATION__DOMAIN:
				return domain != null;
			case StructurePackage.AGENT_INITIALIZATION__INITIAL_STATE:
				return getInitialState() != null;
		}
		return super.eIsSet(featureID);
	}

} //AgentInitializationImpl

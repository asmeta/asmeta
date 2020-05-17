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
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
import asmeta.structure.DomainInitialization;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Initialization;
import asmeta.structure.StructurePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Initialization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.impl.InitializationImpl#getDomainInitialization <em>Domain Initialization</em>}</li>
 *   <li>{@link asmeta.structure.impl.InitializationImpl#getFunctionInitialization <em>Function Initialization</em>}</li>
 *   <li>{@link asmeta.structure.impl.InitializationImpl#getAgentInitialization <em>Agent Initialization</em>}</li>
 *   <li>{@link asmeta.structure.impl.InitializationImpl#getAsm <em>Asm</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InitializationImpl extends NamedElementImpl implements Initialization {
	/**
	 * The cached value of the '{@link #getDomainInitialization() <em>Domain Initialization</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainInitialization()
	 * @generated
	 * @ordered
	 */
	protected EList<DomainInitialization> domainInitialization;

	/**
	 * The cached value of the '{@link #getFunctionInitialization() <em>Function Initialization</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionInitialization()
	 * @generated
	 * @ordered
	 */
	protected EList<FunctionInitialization> functionInitialization;

	/**
	 * The cached value of the '{@link #getAgentInitialization() <em>Agent Initialization</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAgentInitialization()
	 * @generated
	 * @ordered
	 */
	protected EList<AgentInitialization> agentInitialization;

	/**
	 * The cached value of the '{@link #getAsm() <em>Asm</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAsm()
	 * @generated
	 * @ordered
	 */
	protected Asm asm;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InitializationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StructurePackage.Literals.INITIALIZATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DomainInitialization> getDomainInitialization() {
		if (domainInitialization == null) {
			domainInitialization = new EObjectContainmentWithInverseEList<DomainInitialization>(DomainInitialization.class, this, StructurePackage.INITIALIZATION__DOMAIN_INITIALIZATION, StructurePackage.DOMAIN_INITIALIZATION__INITIAL_STATE);
		}
		return domainInitialization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FunctionInitialization> getFunctionInitialization() {
		if (functionInitialization == null) {
			functionInitialization = new EObjectContainmentWithInverseEList<FunctionInitialization>(FunctionInitialization.class, this, StructurePackage.INITIALIZATION__FUNCTION_INITIALIZATION, StructurePackage.FUNCTION_INITIALIZATION__INITIAL_STATE);
		}
		return functionInitialization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AgentInitialization> getAgentInitialization() {
		if (agentInitialization == null) {
			agentInitialization = new EObjectContainmentWithInverseEList<AgentInitialization>(AgentInitialization.class, this, StructurePackage.INITIALIZATION__AGENT_INITIALIZATION, StructurePackage.AGENT_INITIALIZATION__INITIAL_STATE);
		}
		return agentInitialization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Asm getAsm() {
		if (asm != null && asm.eIsProxy()) {
			InternalEObject oldAsm = (InternalEObject)asm;
			asm = (Asm)eResolveProxy(oldAsm);
			if (asm != oldAsm) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StructurePackage.INITIALIZATION__ASM, oldAsm, asm));
			}
		}
		return asm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Asm basicGetAsm() {
		return asm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAsm(Asm newAsm, NotificationChain msgs) {
		Asm oldAsm = asm;
		asm = newAsm;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StructurePackage.INITIALIZATION__ASM, oldAsm, newAsm);
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
	public void setAsm(Asm newAsm) {
		if (newAsm != asm) {
			NotificationChain msgs = null;
			if (asm != null)
				msgs = ((InternalEObject)asm).eInverseRemove(this, StructurePackage.ASM__DEFAULT_INITIAL_STATE, Asm.class, msgs);
			if (newAsm != null)
				msgs = ((InternalEObject)newAsm).eInverseAdd(this, StructurePackage.ASM__DEFAULT_INITIAL_STATE, Asm.class, msgs);
			msgs = basicSetAsm(newAsm, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.INITIALIZATION__ASM, newAsm, newAsm));
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
			case StructurePackage.INITIALIZATION__DOMAIN_INITIALIZATION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getDomainInitialization()).basicAdd(otherEnd, msgs);
			case StructurePackage.INITIALIZATION__FUNCTION_INITIALIZATION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getFunctionInitialization()).basicAdd(otherEnd, msgs);
			case StructurePackage.INITIALIZATION__AGENT_INITIALIZATION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAgentInitialization()).basicAdd(otherEnd, msgs);
			case StructurePackage.INITIALIZATION__ASM:
				if (asm != null)
					msgs = ((InternalEObject)asm).eInverseRemove(this, StructurePackage.ASM__DEFAULT_INITIAL_STATE, Asm.class, msgs);
				return basicSetAsm((Asm)otherEnd, msgs);
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
			case StructurePackage.INITIALIZATION__DOMAIN_INITIALIZATION:
				return ((InternalEList<?>)getDomainInitialization()).basicRemove(otherEnd, msgs);
			case StructurePackage.INITIALIZATION__FUNCTION_INITIALIZATION:
				return ((InternalEList<?>)getFunctionInitialization()).basicRemove(otherEnd, msgs);
			case StructurePackage.INITIALIZATION__AGENT_INITIALIZATION:
				return ((InternalEList<?>)getAgentInitialization()).basicRemove(otherEnd, msgs);
			case StructurePackage.INITIALIZATION__ASM:
				return basicSetAsm(null, msgs);
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
			case StructurePackage.INITIALIZATION__DOMAIN_INITIALIZATION:
				return getDomainInitialization();
			case StructurePackage.INITIALIZATION__FUNCTION_INITIALIZATION:
				return getFunctionInitialization();
			case StructurePackage.INITIALIZATION__AGENT_INITIALIZATION:
				return getAgentInitialization();
			case StructurePackage.INITIALIZATION__ASM:
				if (resolve) return getAsm();
				return basicGetAsm();
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
			case StructurePackage.INITIALIZATION__DOMAIN_INITIALIZATION:
				getDomainInitialization().clear();
				getDomainInitialization().addAll((Collection<? extends DomainInitialization>)newValue);
				return;
			case StructurePackage.INITIALIZATION__FUNCTION_INITIALIZATION:
				getFunctionInitialization().clear();
				getFunctionInitialization().addAll((Collection<? extends FunctionInitialization>)newValue);
				return;
			case StructurePackage.INITIALIZATION__AGENT_INITIALIZATION:
				getAgentInitialization().clear();
				getAgentInitialization().addAll((Collection<? extends AgentInitialization>)newValue);
				return;
			case StructurePackage.INITIALIZATION__ASM:
				setAsm((Asm)newValue);
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
			case StructurePackage.INITIALIZATION__DOMAIN_INITIALIZATION:
				getDomainInitialization().clear();
				return;
			case StructurePackage.INITIALIZATION__FUNCTION_INITIALIZATION:
				getFunctionInitialization().clear();
				return;
			case StructurePackage.INITIALIZATION__AGENT_INITIALIZATION:
				getAgentInitialization().clear();
				return;
			case StructurePackage.INITIALIZATION__ASM:
				setAsm((Asm)null);
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
			case StructurePackage.INITIALIZATION__DOMAIN_INITIALIZATION:
				return domainInitialization != null && !domainInitialization.isEmpty();
			case StructurePackage.INITIALIZATION__FUNCTION_INITIALIZATION:
				return functionInitialization != null && !functionInitialization.isEmpty();
			case StructurePackage.INITIALIZATION__AGENT_INITIALIZATION:
				return agentInitialization != null && !agentInitialization.isEmpty();
			case StructurePackage.INITIALIZATION__ASM:
				return asm != null;
		}
		return super.eIsSet(featureID);
	}

} //InitializationImpl

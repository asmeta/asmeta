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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.DynamicFunction;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Initialization;
import asmeta.structure.StructurePackage;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Initialization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.impl.FunctionInitializationImpl#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link asmeta.structure.impl.FunctionInitializationImpl#getBody <em>Body</em>}</li>
 *   <li>{@link asmeta.structure.impl.FunctionInitializationImpl#getInitializedFunction <em>Initialized Function</em>}</li>
 *   <li>{@link asmeta.structure.impl.FunctionInitializationImpl#getVariable <em>Variable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FunctionInitializationImpl extends EObjectImpl implements FunctionInitialization {
	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Term body;

	/**
	 * The cached value of the '{@link #getInitializedFunction() <em>Initialized Function</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitializedFunction()
	 * @generated
	 * @ordered
	 */
	protected DynamicFunction initializedFunction;

	/**
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected EList<VariableTerm> variable;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionInitializationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StructurePackage.Literals.FUNCTION_INITIALIZATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Initialization getInitialState() {
		if (eContainerFeatureID() != StructurePackage.FUNCTION_INITIALIZATION__INITIAL_STATE) return null;
		return (Initialization)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitialState(Initialization newInitialState, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newInitialState, StructurePackage.FUNCTION_INITIALIZATION__INITIAL_STATE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInitialState(Initialization newInitialState) {
		if (newInitialState != eInternalContainer() || (eContainerFeatureID() != StructurePackage.FUNCTION_INITIALIZATION__INITIAL_STATE && newInitialState != null)) {
			if (EcoreUtil.isAncestor(this, newInitialState))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newInitialState != null)
				msgs = ((InternalEObject)newInitialState).eInverseAdd(this, StructurePackage.INITIALIZATION__FUNCTION_INITIALIZATION, Initialization.class, msgs);
			msgs = basicSetInitialState(newInitialState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.FUNCTION_INITIALIZATION__INITIAL_STATE, newInitialState, newInitialState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getBody() {
		if (body != null && body.eIsProxy()) {
			InternalEObject oldBody = (InternalEObject)body;
			body = (Term)eResolveProxy(oldBody);
			if (body != oldBody) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StructurePackage.FUNCTION_INITIALIZATION__BODY, oldBody, body));
			}
		}
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term basicGetBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBody(Term newBody) {
		Term oldBody = body;
		body = newBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.FUNCTION_INITIALIZATION__BODY, oldBody, body));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DynamicFunction getInitializedFunction() {
		if (initializedFunction != null && initializedFunction.eIsProxy()) {
			InternalEObject oldInitializedFunction = (InternalEObject)initializedFunction;
			initializedFunction = (DynamicFunction)eResolveProxy(oldInitializedFunction);
			if (initializedFunction != oldInitializedFunction) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StructurePackage.FUNCTION_INITIALIZATION__INITIALIZED_FUNCTION, oldInitializedFunction, initializedFunction));
			}
		}
		return initializedFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DynamicFunction basicGetInitializedFunction() {
		return initializedFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitializedFunction(DynamicFunction newInitializedFunction, NotificationChain msgs) {
		DynamicFunction oldInitializedFunction = initializedFunction;
		initializedFunction = newInitializedFunction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StructurePackage.FUNCTION_INITIALIZATION__INITIALIZED_FUNCTION, oldInitializedFunction, newInitializedFunction);
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
	public void setInitializedFunction(DynamicFunction newInitializedFunction) {
		if (newInitializedFunction != initializedFunction) {
			NotificationChain msgs = null;
			if (initializedFunction != null)
				msgs = ((InternalEObject)initializedFunction).eInverseRemove(this, DefinitionsPackage.DYNAMIC_FUNCTION__INITIALIZATION, DynamicFunction.class, msgs);
			if (newInitializedFunction != null)
				msgs = ((InternalEObject)newInitializedFunction).eInverseAdd(this, DefinitionsPackage.DYNAMIC_FUNCTION__INITIALIZATION, DynamicFunction.class, msgs);
			msgs = basicSetInitializedFunction(newInitializedFunction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.FUNCTION_INITIALIZATION__INITIALIZED_FUNCTION, newInitializedFunction, newInitializedFunction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariableTerm> getVariable() {
		if (variable == null) {
			variable = new EObjectContainmentEList<VariableTerm>(VariableTerm.class, this, StructurePackage.FUNCTION_INITIALIZATION__VARIABLE);
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StructurePackage.FUNCTION_INITIALIZATION__INITIAL_STATE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetInitialState((Initialization)otherEnd, msgs);
			case StructurePackage.FUNCTION_INITIALIZATION__INITIALIZED_FUNCTION:
				if (initializedFunction != null)
					msgs = ((InternalEObject)initializedFunction).eInverseRemove(this, DefinitionsPackage.DYNAMIC_FUNCTION__INITIALIZATION, DynamicFunction.class, msgs);
				return basicSetInitializedFunction((DynamicFunction)otherEnd, msgs);
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
			case StructurePackage.FUNCTION_INITIALIZATION__INITIAL_STATE:
				return basicSetInitialState(null, msgs);
			case StructurePackage.FUNCTION_INITIALIZATION__INITIALIZED_FUNCTION:
				return basicSetInitializedFunction(null, msgs);
			case StructurePackage.FUNCTION_INITIALIZATION__VARIABLE:
				return ((InternalEList<?>)getVariable()).basicRemove(otherEnd, msgs);
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
			case StructurePackage.FUNCTION_INITIALIZATION__INITIAL_STATE:
				return eInternalContainer().eInverseRemove(this, StructurePackage.INITIALIZATION__FUNCTION_INITIALIZATION, Initialization.class, msgs);
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
			case StructurePackage.FUNCTION_INITIALIZATION__INITIAL_STATE:
				return getInitialState();
			case StructurePackage.FUNCTION_INITIALIZATION__BODY:
				if (resolve) return getBody();
				return basicGetBody();
			case StructurePackage.FUNCTION_INITIALIZATION__INITIALIZED_FUNCTION:
				if (resolve) return getInitializedFunction();
				return basicGetInitializedFunction();
			case StructurePackage.FUNCTION_INITIALIZATION__VARIABLE:
				return getVariable();
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
			case StructurePackage.FUNCTION_INITIALIZATION__INITIAL_STATE:
				setInitialState((Initialization)newValue);
				return;
			case StructurePackage.FUNCTION_INITIALIZATION__BODY:
				setBody((Term)newValue);
				return;
			case StructurePackage.FUNCTION_INITIALIZATION__INITIALIZED_FUNCTION:
				setInitializedFunction((DynamicFunction)newValue);
				return;
			case StructurePackage.FUNCTION_INITIALIZATION__VARIABLE:
				getVariable().clear();
				getVariable().addAll((Collection<? extends VariableTerm>)newValue);
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
			case StructurePackage.FUNCTION_INITIALIZATION__INITIAL_STATE:
				setInitialState((Initialization)null);
				return;
			case StructurePackage.FUNCTION_INITIALIZATION__BODY:
				setBody((Term)null);
				return;
			case StructurePackage.FUNCTION_INITIALIZATION__INITIALIZED_FUNCTION:
				setInitializedFunction((DynamicFunction)null);
				return;
			case StructurePackage.FUNCTION_INITIALIZATION__VARIABLE:
				getVariable().clear();
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
			case StructurePackage.FUNCTION_INITIALIZATION__INITIAL_STATE:
				return getInitialState() != null;
			case StructurePackage.FUNCTION_INITIALIZATION__BODY:
				return body != null;
			case StructurePackage.FUNCTION_INITIALIZATION__INITIALIZED_FUNCTION:
				return initializedFunction != null;
			case StructurePackage.FUNCTION_INITIALIZATION__VARIABLE:
				return variable != null && !variable.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //FunctionInitializationImpl

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.DynamicFunction;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.StructurePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dynamic Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.impl.DynamicFunctionImpl#getInitialization <em>Initialization</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DynamicFunctionImpl extends BasicFunctionImpl implements DynamicFunction {
	/**
	 * The cached value of the '{@link #getInitialization() <em>Initialization</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialization()
	 * @generated
	 * @ordered
	 */
	protected EList<FunctionInitialization> initialization;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DynamicFunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DefinitionsPackage.Literals.DYNAMIC_FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FunctionInitialization> getInitialization() {
		if (initialization == null) {
			initialization = new EObjectWithInverseResolvingEList<FunctionInitialization>(FunctionInitialization.class, this, DefinitionsPackage.DYNAMIC_FUNCTION__INITIALIZATION, StructurePackage.FUNCTION_INITIALIZATION__INITIALIZED_FUNCTION);
		}
		return initialization;
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
			case DefinitionsPackage.DYNAMIC_FUNCTION__INITIALIZATION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInitialization()).basicAdd(otherEnd, msgs);
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
			case DefinitionsPackage.DYNAMIC_FUNCTION__INITIALIZATION:
				return ((InternalEList<?>)getInitialization()).basicRemove(otherEnd, msgs);
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
			case DefinitionsPackage.DYNAMIC_FUNCTION__INITIALIZATION:
				return getInitialization();
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
			case DefinitionsPackage.DYNAMIC_FUNCTION__INITIALIZATION:
				getInitialization().clear();
				getInitialization().addAll((Collection<? extends FunctionInitialization>)newValue);
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
			case DefinitionsPackage.DYNAMIC_FUNCTION__INITIALIZATION:
				getInitialization().clear();
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
			case DefinitionsPackage.DYNAMIC_FUNCTION__INITIALIZATION:
				return initialization != null && !initialization.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DynamicFunctionImpl

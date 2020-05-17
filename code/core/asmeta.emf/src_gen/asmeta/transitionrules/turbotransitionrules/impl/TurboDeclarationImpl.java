/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import asmeta.definitions.domains.Domain;
import asmeta.definitions.impl.RuleDeclarationImpl;
import asmeta.transitionrules.turbotransitionrules.TurboDeclaration;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Turbo Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.impl.TurboDeclarationImpl#getResultType <em>Result Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TurboDeclarationImpl extends RuleDeclarationImpl implements TurboDeclaration {
	/**
	 * The cached value of the '{@link #getResultType() <em>Result Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultType()
	 * @generated
	 * @ordered
	 */
	protected Domain resultType;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TurboDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TurbotransitionrulesPackage.Literals.TURBO_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Domain getResultType() {
		if (resultType != null && resultType.eIsProxy()) {
			InternalEObject oldResultType = (InternalEObject)resultType;
			resultType = (Domain)eResolveProxy(oldResultType);
			if (resultType != oldResultType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TurbotransitionrulesPackage.TURBO_DECLARATION__RESULT_TYPE, oldResultType, resultType));
			}
		}
		return resultType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain basicGetResultType() {
		return resultType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResultType(Domain newResultType) {
		Domain oldResultType = resultType;
		resultType = newResultType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TurbotransitionrulesPackage.TURBO_DECLARATION__RESULT_TYPE, oldResultType, resultType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TurbotransitionrulesPackage.TURBO_DECLARATION__RESULT_TYPE:
				if (resolve) return getResultType();
				return basicGetResultType();
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
			case TurbotransitionrulesPackage.TURBO_DECLARATION__RESULT_TYPE:
				setResultType((Domain)newValue);
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
			case TurbotransitionrulesPackage.TURBO_DECLARATION__RESULT_TYPE:
				setResultType((Domain)null);
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
			case TurbotransitionrulesPackage.TURBO_DECLARATION__RESULT_TYPE:
				return resultType != null;
		}
		return super.eIsSet(featureID);
	}

} //TurboDeclarationImpl

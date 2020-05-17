/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;

import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.turbotransitionrules.TurboCallRule;
import asmeta.transitionrules.turbotransitionrules.TurboDeclaration;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Turbo Call Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.impl.TurboCallRuleImpl#getCalledRule <em>Called Rule</em>}</li>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.impl.TurboCallRuleImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TurboCallRuleImpl extends TurboRuleImpl implements TurboCallRule {
	/**
	 * The cached value of the '{@link #getCalledRule() <em>Called Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCalledRule()
	 * @generated
	 * @ordered
	 */
	protected TurboDeclaration calledRule;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> parameters;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TurboCallRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TurbotransitionrulesPackage.Literals.TURBO_CALL_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TurboDeclaration getCalledRule() {
		if (calledRule != null && calledRule.eIsProxy()) {
			InternalEObject oldCalledRule = (InternalEObject)calledRule;
			calledRule = (TurboDeclaration)eResolveProxy(oldCalledRule);
			if (calledRule != oldCalledRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TurbotransitionrulesPackage.TURBO_CALL_RULE__CALLED_RULE, oldCalledRule, calledRule));
			}
		}
		return calledRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TurboDeclaration basicGetCalledRule() {
		return calledRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCalledRule(TurboDeclaration newCalledRule) {
		TurboDeclaration oldCalledRule = calledRule;
		calledRule = newCalledRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TurbotransitionrulesPackage.TURBO_CALL_RULE__CALLED_RULE, oldCalledRule, calledRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Term> getParameters() {
		if (parameters == null) {
			parameters = new EDataTypeEList<Term>(Term.class, this, TurbotransitionrulesPackage.TURBO_CALL_RULE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TurbotransitionrulesPackage.TURBO_CALL_RULE__CALLED_RULE:
				if (resolve) return getCalledRule();
				return basicGetCalledRule();
			case TurbotransitionrulesPackage.TURBO_CALL_RULE__PARAMETERS:
				return getParameters();
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
			case TurbotransitionrulesPackage.TURBO_CALL_RULE__CALLED_RULE:
				setCalledRule((TurboDeclaration)newValue);
				return;
			case TurbotransitionrulesPackage.TURBO_CALL_RULE__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Term>)newValue);
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
			case TurbotransitionrulesPackage.TURBO_CALL_RULE__CALLED_RULE:
				setCalledRule((TurboDeclaration)null);
				return;
			case TurbotransitionrulesPackage.TURBO_CALL_RULE__PARAMETERS:
				getParameters().clear();
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
			case TurbotransitionrulesPackage.TURBO_CALL_RULE__CALLED_RULE:
				return calledRule != null;
			case TurbotransitionrulesPackage.TURBO_CALL_RULE__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
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
		result.append(" (parameters: ");
		result.append(parameters);
		result.append(')');
		return result.toString();
	}

} //TurboCallRuleImpl

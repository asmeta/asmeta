/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.definitions.LocalFunction;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Turbo Local State Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.impl.TurboLocalStateRuleImpl#getInit <em>Init</em>}</li>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.impl.TurboLocalStateRuleImpl#getBody <em>Body</em>}</li>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.impl.TurboLocalStateRuleImpl#getLocalFunction <em>Local Function</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TurboLocalStateRuleImpl extends TurboRuleImpl implements TurboLocalStateRule {
	/**
	 * The cached value of the '{@link #getInit() <em>Init</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInit()
	 * @generated
	 * @ordered
	 */
	protected EList<Rule> init;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Rule body;

	/**
	 * The cached value of the '{@link #getLocalFunction() <em>Local Function</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalFunction()
	 * @generated
	 * @ordered
	 */
	protected EList<LocalFunction> localFunction;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TurboLocalStateRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TurbotransitionrulesPackage.Literals.TURBO_LOCAL_STATE_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Rule> getInit() {
		if (init == null) {
			init = new EObjectContainmentEList<Rule>(Rule.class, this, TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__INIT);
		}
		return init;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rule getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBody(Rule newBody, NotificationChain msgs) {
		Rule oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__BODY, oldBody, newBody);
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
	public void setBody(Rule newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__BODY, newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<LocalFunction> getLocalFunction() {
		if (localFunction == null) {
			localFunction = new EObjectContainmentEList<LocalFunction>(LocalFunction.class, this, TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__LOCAL_FUNCTION);
		}
		return localFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__INIT:
				return ((InternalEList<?>)getInit()).basicRemove(otherEnd, msgs);
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__BODY:
				return basicSetBody(null, msgs);
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__LOCAL_FUNCTION:
				return ((InternalEList<?>)getLocalFunction()).basicRemove(otherEnd, msgs);
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
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__INIT:
				return getInit();
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__BODY:
				return getBody();
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__LOCAL_FUNCTION:
				return getLocalFunction();
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
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__INIT:
				getInit().clear();
				getInit().addAll((Collection<? extends Rule>)newValue);
				return;
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__BODY:
				setBody((Rule)newValue);
				return;
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__LOCAL_FUNCTION:
				getLocalFunction().clear();
				getLocalFunction().addAll((Collection<? extends LocalFunction>)newValue);
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
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__INIT:
				getInit().clear();
				return;
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__BODY:
				setBody((Rule)null);
				return;
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__LOCAL_FUNCTION:
				getLocalFunction().clear();
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
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__INIT:
				return init != null && !init.isEmpty();
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__BODY:
				return body != null;
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE__LOCAL_FUNCTION:
				return localFunction != null && !localFunction.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TurboLocalStateRuleImpl

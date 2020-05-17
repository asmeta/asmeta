/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Let Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.LetRuleImpl#getInRule <em>In Rule</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.LetRuleImpl#getInitExpression <em>Init Expression</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.LetRuleImpl#getVariable <em>Variable</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LetRuleImpl extends BasicRuleImpl implements LetRule {
	/**
	 * The cached value of the '{@link #getInRule() <em>In Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInRule()
	 * @generated
	 * @ordered
	 */
	protected Rule inRule;

	/**
	 * The cached value of the '{@link #getInitExpression() <em>Init Expression</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitExpression()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> initExpression;

	/**
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' reference list.
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
	protected LetRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasictransitionrulesPackage.Literals.LET_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rule getInRule() {
		return inRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInRule(Rule newInRule, NotificationChain msgs) {
		Rule oldInRule = inRule;
		inRule = newInRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.LET_RULE__IN_RULE, oldInRule, newInRule);
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
	public void setInRule(Rule newInRule) {
		if (newInRule != inRule) {
			NotificationChain msgs = null;
			if (inRule != null)
				msgs = ((InternalEObject)inRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BasictransitionrulesPackage.LET_RULE__IN_RULE, null, msgs);
			if (newInRule != null)
				msgs = ((InternalEObject)newInRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BasictransitionrulesPackage.LET_RULE__IN_RULE, null, msgs);
			msgs = basicSetInRule(newInRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.LET_RULE__IN_RULE, newInRule, newInRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Term> getInitExpression() {
		if (initExpression == null) {
			initExpression = new EObjectResolvingEList<Term>(Term.class, this, BasictransitionrulesPackage.LET_RULE__INIT_EXPRESSION);
		}
		return initExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariableTerm> getVariable() {
		if (variable == null) {
			variable = new EObjectResolvingEList<VariableTerm>(VariableTerm.class, this, BasictransitionrulesPackage.LET_RULE__VARIABLE);
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasictransitionrulesPackage.LET_RULE__IN_RULE:
				return basicSetInRule(null, msgs);
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
			case BasictransitionrulesPackage.LET_RULE__IN_RULE:
				return getInRule();
			case BasictransitionrulesPackage.LET_RULE__INIT_EXPRESSION:
				return getInitExpression();
			case BasictransitionrulesPackage.LET_RULE__VARIABLE:
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
			case BasictransitionrulesPackage.LET_RULE__IN_RULE:
				setInRule((Rule)newValue);
				return;
			case BasictransitionrulesPackage.LET_RULE__INIT_EXPRESSION:
				getInitExpression().clear();
				getInitExpression().addAll((Collection<? extends Term>)newValue);
				return;
			case BasictransitionrulesPackage.LET_RULE__VARIABLE:
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
			case BasictransitionrulesPackage.LET_RULE__IN_RULE:
				setInRule((Rule)null);
				return;
			case BasictransitionrulesPackage.LET_RULE__INIT_EXPRESSION:
				getInitExpression().clear();
				return;
			case BasictransitionrulesPackage.LET_RULE__VARIABLE:
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
			case BasictransitionrulesPackage.LET_RULE__IN_RULE:
				return inRule != null;
			case BasictransitionrulesPackage.LET_RULE__INIT_EXPRESSION:
				return initExpression != null && !initExpression.isEmpty();
			case BasictransitionrulesPackage.LET_RULE__VARIABLE:
				return variable != null && !variable.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //LetRuleImpl

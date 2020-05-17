/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import asmeta.definitions.RuleDeclaration;
import asmeta.terms.basicterms.BasictermsPackage;
import asmeta.terms.basicterms.RuleAsTerm;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule As Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.basicterms.impl.RuleAsTermImpl#getRule <em>Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RuleAsTermImpl extends ExtendedTermImpl implements RuleAsTerm {
	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected RuleDeclaration rule;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleAsTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasictermsPackage.Literals.RULE_AS_TERM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuleDeclaration getRule() {
		if (rule != null && rule.eIsProxy()) {
			InternalEObject oldRule = (InternalEObject)rule;
			rule = (RuleDeclaration)eResolveProxy(oldRule);
			if (rule != oldRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BasictermsPackage.RULE_AS_TERM__RULE, oldRule, rule));
			}
		}
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleDeclaration basicGetRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRule(RuleDeclaration newRule) {
		RuleDeclaration oldRule = rule;
		rule = newRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictermsPackage.RULE_AS_TERM__RULE, oldRule, rule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BasictermsPackage.RULE_AS_TERM__RULE:
				if (resolve) return getRule();
				return basicGetRule();
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
			case BasictermsPackage.RULE_AS_TERM__RULE:
				setRule((RuleDeclaration)newValue);
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
			case BasictermsPackage.RULE_AS_TERM__RULE:
				setRule((RuleDeclaration)null);
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
			case BasictermsPackage.RULE_AS_TERM__RULE:
				return rule != null;
		}
		return super.eIsSet(featureID);
	}

} //RuleAsTermImpl

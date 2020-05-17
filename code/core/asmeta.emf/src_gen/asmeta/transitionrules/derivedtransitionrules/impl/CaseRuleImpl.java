/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.derivedtransitionrules.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Case Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.derivedtransitionrules.impl.CaseRuleImpl#getTerm <em>Term</em>}</li>
 *   <li>{@link asmeta.transitionrules.derivedtransitionrules.impl.CaseRuleImpl#getCaseTerm <em>Case Term</em>}</li>
 *   <li>{@link asmeta.transitionrules.derivedtransitionrules.impl.CaseRuleImpl#getOtherwiseBranch <em>Otherwise Branch</em>}</li>
 *   <li>{@link asmeta.transitionrules.derivedtransitionrules.impl.CaseRuleImpl#getCaseBranches <em>Case Branches</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CaseRuleImpl extends BasicDerivedRuleImpl implements CaseRule {
	/**
	 * The cached value of the '{@link #getTerm() <em>Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTerm()
	 * @generated
	 * @ordered
	 */
	protected Term term;

	/**
	 * The cached value of the '{@link #getCaseTerm() <em>Case Term</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCaseTerm()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> caseTerm;

	/**
	 * The cached value of the '{@link #getOtherwiseBranch() <em>Otherwise Branch</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherwiseBranch()
	 * @generated
	 * @ordered
	 */
	protected Rule otherwiseBranch;

	/**
	 * The cached value of the '{@link #getCaseBranches() <em>Case Branches</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCaseBranches()
	 * @generated
	 * @ordered
	 */
	protected EList<Rule> caseBranches;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CaseRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DerivedtransitionrulesPackage.Literals.CASE_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getTerm() {
		if (term != null && term.eIsProxy()) {
			InternalEObject oldTerm = (InternalEObject)term;
			term = (Term)eResolveProxy(oldTerm);
			if (term != oldTerm) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DerivedtransitionrulesPackage.CASE_RULE__TERM, oldTerm, term));
			}
		}
		return term;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term basicGetTerm() {
		return term;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTerm(Term newTerm) {
		Term oldTerm = term;
		term = newTerm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DerivedtransitionrulesPackage.CASE_RULE__TERM, oldTerm, term));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Term> getCaseTerm() {
		if (caseTerm == null) {
			caseTerm = new EObjectResolvingEList<Term>(Term.class, this, DerivedtransitionrulesPackage.CASE_RULE__CASE_TERM);
		}
		return caseTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Rule getOtherwiseBranch() {
		return otherwiseBranch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOtherwiseBranch(Rule newOtherwiseBranch, NotificationChain msgs) {
		Rule oldOtherwiseBranch = otherwiseBranch;
		otherwiseBranch = newOtherwiseBranch;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DerivedtransitionrulesPackage.CASE_RULE__OTHERWISE_BRANCH, oldOtherwiseBranch, newOtherwiseBranch);
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
	public void setOtherwiseBranch(Rule newOtherwiseBranch) {
		if (newOtherwiseBranch != otherwiseBranch) {
			NotificationChain msgs = null;
			if (otherwiseBranch != null)
				msgs = ((InternalEObject)otherwiseBranch).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DerivedtransitionrulesPackage.CASE_RULE__OTHERWISE_BRANCH, null, msgs);
			if (newOtherwiseBranch != null)
				msgs = ((InternalEObject)newOtherwiseBranch).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DerivedtransitionrulesPackage.CASE_RULE__OTHERWISE_BRANCH, null, msgs);
			msgs = basicSetOtherwiseBranch(newOtherwiseBranch, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DerivedtransitionrulesPackage.CASE_RULE__OTHERWISE_BRANCH, newOtherwiseBranch, newOtherwiseBranch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Rule> getCaseBranches() {
		if (caseBranches == null) {
			caseBranches = new EDataTypeEList<Rule>(Rule.class, this, DerivedtransitionrulesPackage.CASE_RULE__CASE_BRANCHES);
		}
		return caseBranches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DerivedtransitionrulesPackage.CASE_RULE__OTHERWISE_BRANCH:
				return basicSetOtherwiseBranch(null, msgs);
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
			case DerivedtransitionrulesPackage.CASE_RULE__TERM:
				if (resolve) return getTerm();
				return basicGetTerm();
			case DerivedtransitionrulesPackage.CASE_RULE__CASE_TERM:
				return getCaseTerm();
			case DerivedtransitionrulesPackage.CASE_RULE__OTHERWISE_BRANCH:
				return getOtherwiseBranch();
			case DerivedtransitionrulesPackage.CASE_RULE__CASE_BRANCHES:
				return getCaseBranches();
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
			case DerivedtransitionrulesPackage.CASE_RULE__TERM:
				setTerm((Term)newValue);
				return;
			case DerivedtransitionrulesPackage.CASE_RULE__CASE_TERM:
				getCaseTerm().clear();
				getCaseTerm().addAll((Collection<? extends Term>)newValue);
				return;
			case DerivedtransitionrulesPackage.CASE_RULE__OTHERWISE_BRANCH:
				setOtherwiseBranch((Rule)newValue);
				return;
			case DerivedtransitionrulesPackage.CASE_RULE__CASE_BRANCHES:
				getCaseBranches().clear();
				getCaseBranches().addAll((Collection<? extends Rule>)newValue);
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
			case DerivedtransitionrulesPackage.CASE_RULE__TERM:
				setTerm((Term)null);
				return;
			case DerivedtransitionrulesPackage.CASE_RULE__CASE_TERM:
				getCaseTerm().clear();
				return;
			case DerivedtransitionrulesPackage.CASE_RULE__OTHERWISE_BRANCH:
				setOtherwiseBranch((Rule)null);
				return;
			case DerivedtransitionrulesPackage.CASE_RULE__CASE_BRANCHES:
				getCaseBranches().clear();
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
			case DerivedtransitionrulesPackage.CASE_RULE__TERM:
				return term != null;
			case DerivedtransitionrulesPackage.CASE_RULE__CASE_TERM:
				return caseTerm != null && !caseTerm.isEmpty();
			case DerivedtransitionrulesPackage.CASE_RULE__OTHERWISE_BRANCH:
				return otherwiseBranch != null;
			case DerivedtransitionrulesPackage.CASE_RULE__CASE_BRANCHES:
				return caseBranches != null && !caseBranches.isEmpty();
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
		result.append(" (caseBranches: ");
		result.append(caseBranches);
		result.append(')');
		return result.toString();
	}

} //CaseRuleImpl

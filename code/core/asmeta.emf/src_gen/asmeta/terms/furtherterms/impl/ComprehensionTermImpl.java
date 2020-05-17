/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.ComprehensionTerm;
import asmeta.terms.furtherterms.FurthertermsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Comprehension Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.furtherterms.impl.ComprehensionTermImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.impl.ComprehensionTermImpl#getGuard <em>Guard</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.impl.ComprehensionTermImpl#getTerm <em>Term</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.impl.ComprehensionTermImpl#getRanges <em>Ranges</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ComprehensionTermImpl extends VariableBindingTermImpl implements ComprehensionTerm {
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
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected Term guard;

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
	 * The cached value of the '{@link #getRanges() <em>Ranges</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRanges()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> ranges;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComprehensionTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FurthertermsPackage.Literals.COMPREHENSION_TERM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariableTerm> getVariable() {
		if (variable == null) {
			variable = new EObjectContainmentEList<VariableTerm>(VariableTerm.class, this, FurthertermsPackage.COMPREHENSION_TERM__VARIABLE);
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getGuard() {
		if (guard != null && guard.eIsProxy()) {
			InternalEObject oldGuard = (InternalEObject)guard;
			guard = (Term)eResolveProxy(oldGuard);
			if (guard != oldGuard) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FurthertermsPackage.COMPREHENSION_TERM__GUARD, oldGuard, guard));
			}
		}
		return guard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term basicGetGuard() {
		return guard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGuard(Term newGuard) {
		Term oldGuard = guard;
		guard = newGuard;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FurthertermsPackage.COMPREHENSION_TERM__GUARD, oldGuard, guard));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FurthertermsPackage.COMPREHENSION_TERM__TERM, oldTerm, term));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FurthertermsPackage.COMPREHENSION_TERM__TERM, oldTerm, term));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Term> getRanges() {
		if (ranges == null) {
			ranges = new EDataTypeEList<Term>(Term.class, this, FurthertermsPackage.COMPREHENSION_TERM__RANGES);
		}
		return ranges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FurthertermsPackage.COMPREHENSION_TERM__VARIABLE:
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FurthertermsPackage.COMPREHENSION_TERM__VARIABLE:
				return getVariable();
			case FurthertermsPackage.COMPREHENSION_TERM__GUARD:
				if (resolve) return getGuard();
				return basicGetGuard();
			case FurthertermsPackage.COMPREHENSION_TERM__TERM:
				if (resolve) return getTerm();
				return basicGetTerm();
			case FurthertermsPackage.COMPREHENSION_TERM__RANGES:
				return getRanges();
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
			case FurthertermsPackage.COMPREHENSION_TERM__VARIABLE:
				getVariable().clear();
				getVariable().addAll((Collection<? extends VariableTerm>)newValue);
				return;
			case FurthertermsPackage.COMPREHENSION_TERM__GUARD:
				setGuard((Term)newValue);
				return;
			case FurthertermsPackage.COMPREHENSION_TERM__TERM:
				setTerm((Term)newValue);
				return;
			case FurthertermsPackage.COMPREHENSION_TERM__RANGES:
				getRanges().clear();
				getRanges().addAll((Collection<? extends Term>)newValue);
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
			case FurthertermsPackage.COMPREHENSION_TERM__VARIABLE:
				getVariable().clear();
				return;
			case FurthertermsPackage.COMPREHENSION_TERM__GUARD:
				setGuard((Term)null);
				return;
			case FurthertermsPackage.COMPREHENSION_TERM__TERM:
				setTerm((Term)null);
				return;
			case FurthertermsPackage.COMPREHENSION_TERM__RANGES:
				getRanges().clear();
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
			case FurthertermsPackage.COMPREHENSION_TERM__VARIABLE:
				return variable != null && !variable.isEmpty();
			case FurthertermsPackage.COMPREHENSION_TERM__GUARD:
				return guard != null;
			case FurthertermsPackage.COMPREHENSION_TERM__TERM:
				return term != null;
			case FurthertermsPackage.COMPREHENSION_TERM__RANGES:
				return ranges != null && !ranges.isEmpty();
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
		result.append(" (ranges: ");
		result.append(ranges);
		result.append(')');
		return result.toString();
	}

} //ComprehensionTermImpl

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.impl.ExtendedTermImpl;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.FurthertermsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conditional Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.furtherterms.impl.ConditionalTermImpl#getElseTerm <em>Else Term</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.impl.ConditionalTermImpl#getGuard <em>Guard</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.impl.ConditionalTermImpl#getThenTerm <em>Then Term</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionalTermImpl extends ExtendedTermImpl implements ConditionalTerm {
	/**
	 * The cached value of the '{@link #getElseTerm() <em>Else Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElseTerm()
	 * @generated
	 * @ordered
	 */
	protected Term elseTerm;

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
	 * The cached value of the '{@link #getThenTerm() <em>Then Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThenTerm()
	 * @generated
	 * @ordered
	 */
	protected Term thenTerm;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionalTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FurthertermsPackage.Literals.CONDITIONAL_TERM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getElseTerm() {
		if (elseTerm != null && elseTerm.eIsProxy()) {
			InternalEObject oldElseTerm = (InternalEObject)elseTerm;
			elseTerm = (Term)eResolveProxy(oldElseTerm);
			if (elseTerm != oldElseTerm) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FurthertermsPackage.CONDITIONAL_TERM__ELSE_TERM, oldElseTerm, elseTerm));
			}
		}
		return elseTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term basicGetElseTerm() {
		return elseTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setElseTerm(Term newElseTerm) {
		Term oldElseTerm = elseTerm;
		elseTerm = newElseTerm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FurthertermsPackage.CONDITIONAL_TERM__ELSE_TERM, oldElseTerm, elseTerm));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FurthertermsPackage.CONDITIONAL_TERM__GUARD, oldGuard, guard));
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
			eNotify(new ENotificationImpl(this, Notification.SET, FurthertermsPackage.CONDITIONAL_TERM__GUARD, oldGuard, guard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getThenTerm() {
		if (thenTerm != null && thenTerm.eIsProxy()) {
			InternalEObject oldThenTerm = (InternalEObject)thenTerm;
			thenTerm = (Term)eResolveProxy(oldThenTerm);
			if (thenTerm != oldThenTerm) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FurthertermsPackage.CONDITIONAL_TERM__THEN_TERM, oldThenTerm, thenTerm));
			}
		}
		return thenTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term basicGetThenTerm() {
		return thenTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setThenTerm(Term newThenTerm) {
		Term oldThenTerm = thenTerm;
		thenTerm = newThenTerm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FurthertermsPackage.CONDITIONAL_TERM__THEN_TERM, oldThenTerm, thenTerm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FurthertermsPackage.CONDITIONAL_TERM__ELSE_TERM:
				if (resolve) return getElseTerm();
				return basicGetElseTerm();
			case FurthertermsPackage.CONDITIONAL_TERM__GUARD:
				if (resolve) return getGuard();
				return basicGetGuard();
			case FurthertermsPackage.CONDITIONAL_TERM__THEN_TERM:
				if (resolve) return getThenTerm();
				return basicGetThenTerm();
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
			case FurthertermsPackage.CONDITIONAL_TERM__ELSE_TERM:
				setElseTerm((Term)newValue);
				return;
			case FurthertermsPackage.CONDITIONAL_TERM__GUARD:
				setGuard((Term)newValue);
				return;
			case FurthertermsPackage.CONDITIONAL_TERM__THEN_TERM:
				setThenTerm((Term)newValue);
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
			case FurthertermsPackage.CONDITIONAL_TERM__ELSE_TERM:
				setElseTerm((Term)null);
				return;
			case FurthertermsPackage.CONDITIONAL_TERM__GUARD:
				setGuard((Term)null);
				return;
			case FurthertermsPackage.CONDITIONAL_TERM__THEN_TERM:
				setThenTerm((Term)null);
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
			case FurthertermsPackage.CONDITIONAL_TERM__ELSE_TERM:
				return elseTerm != null;
			case FurthertermsPackage.CONDITIONAL_TERM__GUARD:
				return guard != null;
			case FurthertermsPackage.CONDITIONAL_TERM__THEN_TERM:
				return thenTerm != null;
		}
		return super.eIsSet(featureID);
	}

} //ConditionalTermImpl

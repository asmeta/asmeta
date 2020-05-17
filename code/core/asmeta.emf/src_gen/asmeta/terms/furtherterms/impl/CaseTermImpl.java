/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.impl.ExtendedTermImpl;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.FurthertermsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Case Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.furtherterms.impl.CaseTermImpl#getComparingTerm <em>Comparing Term</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.impl.CaseTermImpl#getComparedTerm <em>Compared Term</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.impl.CaseTermImpl#getOtherwiseTerm <em>Otherwise Term</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.impl.CaseTermImpl#getResultTerms <em>Result Terms</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CaseTermImpl extends ExtendedTermImpl implements CaseTerm {
	/**
	 * The cached value of the '{@link #getComparingTerm() <em>Comparing Term</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComparingTerm()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> comparingTerm;

	/**
	 * The cached value of the '{@link #getComparedTerm() <em>Compared Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComparedTerm()
	 * @generated
	 * @ordered
	 */
	protected Term comparedTerm;

	/**
	 * The cached value of the '{@link #getOtherwiseTerm() <em>Otherwise Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherwiseTerm()
	 * @generated
	 * @ordered
	 */
	protected Term otherwiseTerm;

	/**
	 * The cached value of the '{@link #getResultTerms() <em>Result Terms</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultTerms()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> resultTerms;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CaseTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FurthertermsPackage.Literals.CASE_TERM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Term> getComparingTerm() {
		if (comparingTerm == null) {
			comparingTerm = new EObjectResolvingEList<Term>(Term.class, this, FurthertermsPackage.CASE_TERM__COMPARING_TERM);
		}
		return comparingTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getComparedTerm() {
		if (comparedTerm != null && comparedTerm.eIsProxy()) {
			InternalEObject oldComparedTerm = (InternalEObject)comparedTerm;
			comparedTerm = (Term)eResolveProxy(oldComparedTerm);
			if (comparedTerm != oldComparedTerm) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FurthertermsPackage.CASE_TERM__COMPARED_TERM, oldComparedTerm, comparedTerm));
			}
		}
		return comparedTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term basicGetComparedTerm() {
		return comparedTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComparedTerm(Term newComparedTerm) {
		Term oldComparedTerm = comparedTerm;
		comparedTerm = newComparedTerm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FurthertermsPackage.CASE_TERM__COMPARED_TERM, oldComparedTerm, comparedTerm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getOtherwiseTerm() {
		if (otherwiseTerm != null && otherwiseTerm.eIsProxy()) {
			InternalEObject oldOtherwiseTerm = (InternalEObject)otherwiseTerm;
			otherwiseTerm = (Term)eResolveProxy(oldOtherwiseTerm);
			if (otherwiseTerm != oldOtherwiseTerm) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FurthertermsPackage.CASE_TERM__OTHERWISE_TERM, oldOtherwiseTerm, otherwiseTerm));
			}
		}
		return otherwiseTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term basicGetOtherwiseTerm() {
		return otherwiseTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOtherwiseTerm(Term newOtherwiseTerm) {
		Term oldOtherwiseTerm = otherwiseTerm;
		otherwiseTerm = newOtherwiseTerm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FurthertermsPackage.CASE_TERM__OTHERWISE_TERM, oldOtherwiseTerm, otherwiseTerm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Term> getResultTerms() {
		if (resultTerms == null) {
			resultTerms = new EDataTypeEList<Term>(Term.class, this, FurthertermsPackage.CASE_TERM__RESULT_TERMS);
		}
		return resultTerms;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FurthertermsPackage.CASE_TERM__COMPARING_TERM:
				return getComparingTerm();
			case FurthertermsPackage.CASE_TERM__COMPARED_TERM:
				if (resolve) return getComparedTerm();
				return basicGetComparedTerm();
			case FurthertermsPackage.CASE_TERM__OTHERWISE_TERM:
				if (resolve) return getOtherwiseTerm();
				return basicGetOtherwiseTerm();
			case FurthertermsPackage.CASE_TERM__RESULT_TERMS:
				return getResultTerms();
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
			case FurthertermsPackage.CASE_TERM__COMPARING_TERM:
				getComparingTerm().clear();
				getComparingTerm().addAll((Collection<? extends Term>)newValue);
				return;
			case FurthertermsPackage.CASE_TERM__COMPARED_TERM:
				setComparedTerm((Term)newValue);
				return;
			case FurthertermsPackage.CASE_TERM__OTHERWISE_TERM:
				setOtherwiseTerm((Term)newValue);
				return;
			case FurthertermsPackage.CASE_TERM__RESULT_TERMS:
				getResultTerms().clear();
				getResultTerms().addAll((Collection<? extends Term>)newValue);
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
			case FurthertermsPackage.CASE_TERM__COMPARING_TERM:
				getComparingTerm().clear();
				return;
			case FurthertermsPackage.CASE_TERM__COMPARED_TERM:
				setComparedTerm((Term)null);
				return;
			case FurthertermsPackage.CASE_TERM__OTHERWISE_TERM:
				setOtherwiseTerm((Term)null);
				return;
			case FurthertermsPackage.CASE_TERM__RESULT_TERMS:
				getResultTerms().clear();
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
			case FurthertermsPackage.CASE_TERM__COMPARING_TERM:
				return comparingTerm != null && !comparingTerm.isEmpty();
			case FurthertermsPackage.CASE_TERM__COMPARED_TERM:
				return comparedTerm != null;
			case FurthertermsPackage.CASE_TERM__OTHERWISE_TERM:
				return otherwiseTerm != null;
			case FurthertermsPackage.CASE_TERM__RESULT_TERMS:
				return resultTerms != null && !resultTerms.isEmpty();
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
		result.append(" (resultTerms: ");
		result.append(resultTerms);
		result.append(')');
		return result.toString();
	}

} //CaseTermImpl

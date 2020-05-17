/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;

import asmeta.terms.basicterms.BasictermsPackage;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tuple Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.basicterms.impl.TupleTermImpl#getArity <em>Arity</em>}</li>
 *   <li>{@link asmeta.terms.basicterms.impl.TupleTermImpl#getTerms <em>Terms</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TupleTermImpl extends ExtendedTermImpl implements TupleTerm {
	/**
	 * The default value of the '{@link #getArity() <em>Arity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArity()
	 * @generated
	 * @ordered
	 */
	protected static final Integer ARITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getArity() <em>Arity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArity()
	 * @generated
	 * @ordered
	 */
	protected Integer arity = ARITY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTerms() <em>Terms</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTerms()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> terms;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TupleTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasictermsPackage.Literals.TUPLE_TERM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Integer getArity() {
		return arity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setArity(Integer newArity) {
		Integer oldArity = arity;
		arity = newArity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictermsPackage.TUPLE_TERM__ARITY, oldArity, arity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Term> getTerms() {
		if (terms == null) {
			terms = new EDataTypeEList<Term>(Term.class, this, BasictermsPackage.TUPLE_TERM__TERMS);
		}
		return terms;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BasictermsPackage.TUPLE_TERM__ARITY:
				return getArity();
			case BasictermsPackage.TUPLE_TERM__TERMS:
				return getTerms();
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
			case BasictermsPackage.TUPLE_TERM__ARITY:
				setArity((Integer)newValue);
				return;
			case BasictermsPackage.TUPLE_TERM__TERMS:
				getTerms().clear();
				getTerms().addAll((Collection<? extends Term>)newValue);
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
			case BasictermsPackage.TUPLE_TERM__ARITY:
				setArity(ARITY_EDEFAULT);
				return;
			case BasictermsPackage.TUPLE_TERM__TERMS:
				getTerms().clear();
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
			case BasictermsPackage.TUPLE_TERM__ARITY:
				return ARITY_EDEFAULT == null ? arity != null : !ARITY_EDEFAULT.equals(arity);
			case BasictermsPackage.TUPLE_TERM__TERMS:
				return terms != null && !terms.isEmpty();
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
		result.append(" (arity: ");
		result.append(arity);
		result.append(", terms: ");
		result.append(terms);
		result.append(')');
		return result.toString();
	}

} //TupleTermImpl

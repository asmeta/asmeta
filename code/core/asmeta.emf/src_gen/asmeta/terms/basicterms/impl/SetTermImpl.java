/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import asmeta.terms.basicterms.BasictermsPackage;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Set Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.basicterms.impl.SetTermImpl#getTerm <em>Term</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SetTermImpl extends CollectionTermImpl implements SetTerm {
	/**
	 * The cached value of the '{@link #getTerm() <em>Term</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTerm()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> term;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SetTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasictermsPackage.Literals.SET_TERM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Term> getTerm() {
		if (term == null) {
			term = new EObjectResolvingEList<Term>(Term.class, this, BasictermsPackage.SET_TERM__TERM);
		}
		return term;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BasictermsPackage.SET_TERM__TERM:
				return getTerm();
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
			case BasictermsPackage.SET_TERM__TERM:
				getTerm().clear();
				getTerm().addAll((Collection<? extends Term>)newValue);
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
			case BasictermsPackage.SET_TERM__TERM:
				getTerm().clear();
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
			case BasictermsPackage.SET_TERM__TERM:
				return term != null && !term.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SetTermImpl

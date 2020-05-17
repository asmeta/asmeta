/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.impl.CollectionTermImpl;
import asmeta.terms.furtherterms.FurthertermsPackage;
import asmeta.terms.furtherterms.MapTerm;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Map Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.furtherterms.impl.MapTermImpl#getPair <em>Pair</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MapTermImpl extends CollectionTermImpl implements MapTerm {
	/**
	 * The cached value of the '{@link #getPair() <em>Pair</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPair()
	 * @generated
	 * @ordered
	 */
	protected EList<TupleTerm> pair;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MapTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FurthertermsPackage.Literals.MAP_TERM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TupleTerm> getPair() {
		if (pair == null) {
			pair = new EObjectResolvingEList<TupleTerm>(TupleTerm.class, this, FurthertermsPackage.MAP_TERM__PAIR);
		}
		return pair;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FurthertermsPackage.MAP_TERM__PAIR:
				return getPair();
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
			case FurthertermsPackage.MAP_TERM__PAIR:
				getPair().clear();
				getPair().addAll((Collection<? extends TupleTerm>)newValue);
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
			case FurthertermsPackage.MAP_TERM__PAIR:
				getPair().clear();
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
			case FurthertermsPackage.MAP_TERM__PAIR:
				return pair != null && !pair.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MapTermImpl

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.definitions.domains.Domain;
import asmeta.terms.basicterms.BasictermsPackage;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage;
import asmeta.transitionrules.basictransitionrules.TermAsRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.basicterms.impl.TermImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link asmeta.terms.basicterms.impl.TermImpl#getTermAsRule <em>Term As Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TermImpl extends EObjectImpl implements Term {
	/**
	 * The cached value of the '{@link #getDomain() <em>Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
	protected Domain domain;

	/**
	 * The cached value of the '{@link #getTermAsRule() <em>Term As Rule</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTermAsRule()
	 * @generated
	 * @ordered
	 */
	protected EList<TermAsRule> termAsRule;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasictermsPackage.Literals.TERM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Domain getDomain() {
		if (domain != null && domain.eIsProxy()) {
			InternalEObject oldDomain = (InternalEObject)domain;
			domain = (Domain)eResolveProxy(oldDomain);
			if (domain != oldDomain) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BasictermsPackage.TERM__DOMAIN, oldDomain, domain));
			}
		}
		return domain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain basicGetDomain() {
		return domain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDomain(Domain newDomain) {
		Domain oldDomain = domain;
		domain = newDomain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictermsPackage.TERM__DOMAIN, oldDomain, domain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TermAsRule> getTermAsRule() {
		if (termAsRule == null) {
			termAsRule = new EObjectWithInverseResolvingEList<TermAsRule>(TermAsRule.class, this, BasictermsPackage.TERM__TERM_AS_RULE, BasictransitionrulesPackage.TERM_AS_RULE__TERM);
		}
		return termAsRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void compatible() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasictermsPackage.TERM__TERM_AS_RULE:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getTermAsRule()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasictermsPackage.TERM__TERM_AS_RULE:
				return ((InternalEList<?>)getTermAsRule()).basicRemove(otherEnd, msgs);
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
			case BasictermsPackage.TERM__DOMAIN:
				if (resolve) return getDomain();
				return basicGetDomain();
			case BasictermsPackage.TERM__TERM_AS_RULE:
				return getTermAsRule();
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
			case BasictermsPackage.TERM__DOMAIN:
				setDomain((Domain)newValue);
				return;
			case BasictermsPackage.TERM__TERM_AS_RULE:
				getTermAsRule().clear();
				getTermAsRule().addAll((Collection<? extends TermAsRule>)newValue);
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
			case BasictermsPackage.TERM__DOMAIN:
				setDomain((Domain)null);
				return;
			case BasictermsPackage.TERM__TERM_AS_RULE:
				getTermAsRule().clear();
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
			case BasictermsPackage.TERM__DOMAIN:
				return domain != null;
			case BasictermsPackage.TERM__TERM_AS_RULE:
				return termAsRule != null && !termAsRule.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TermImpl

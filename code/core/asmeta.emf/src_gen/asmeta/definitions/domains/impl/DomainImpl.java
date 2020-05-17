/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.Invariant;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.DomainsPackage;
import asmeta.definitions.impl.ClassifierImpl;
import asmeta.structure.Signature;
import asmeta.structure.StructurePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.impl.DomainImpl#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link asmeta.definitions.domains.impl.DomainImpl#getSignature <em>Signature</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DomainImpl extends ClassifierImpl implements Domain {
	/**
	 * The cached value of the '{@link #getConstraint() <em>Constraint</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraint()
	 * @generated
	 * @ordered
	 */
	protected EList<Invariant> constraint;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomainsPackage.Literals.DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Invariant> getConstraint() {
		if (constraint == null) {
			constraint = new EObjectWithInverseResolvingEList.ManyInverse<Invariant>(Invariant.class, this, DomainsPackage.DOMAIN__CONSTRAINT, DefinitionsPackage.INVARIANT__CONSTRAINED_DOMAIN);
		}
		return constraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Signature getSignature() {
		if (eContainerFeatureID() != DomainsPackage.DOMAIN__SIGNATURE) return null;
		return (Signature)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSignature(Signature newSignature, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSignature, DomainsPackage.DOMAIN__SIGNATURE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSignature(Signature newSignature) {
		if (newSignature != eInternalContainer() || (eContainerFeatureID() != DomainsPackage.DOMAIN__SIGNATURE && newSignature != null)) {
			if (EcoreUtil.isAncestor(this, newSignature))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSignature != null)
				msgs = ((InternalEObject)newSignature).eInverseAdd(this, StructurePackage.SIGNATURE__DOMAIN, Signature.class, msgs);
			msgs = basicSetSignature(newSignature, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainsPackage.DOMAIN__SIGNATURE, newSignature, newSignature));
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
			case DomainsPackage.DOMAIN__CONSTRAINT:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getConstraint()).basicAdd(otherEnd, msgs);
			case DomainsPackage.DOMAIN__SIGNATURE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSignature((Signature)otherEnd, msgs);
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
			case DomainsPackage.DOMAIN__CONSTRAINT:
				return ((InternalEList<?>)getConstraint()).basicRemove(otherEnd, msgs);
			case DomainsPackage.DOMAIN__SIGNATURE:
				return basicSetSignature(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case DomainsPackage.DOMAIN__SIGNATURE:
				return eInternalContainer().eInverseRemove(this, StructurePackage.SIGNATURE__DOMAIN, Signature.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DomainsPackage.DOMAIN__CONSTRAINT:
				return getConstraint();
			case DomainsPackage.DOMAIN__SIGNATURE:
				return getSignature();
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
			case DomainsPackage.DOMAIN__CONSTRAINT:
				getConstraint().clear();
				getConstraint().addAll((Collection<? extends Invariant>)newValue);
				return;
			case DomainsPackage.DOMAIN__SIGNATURE:
				setSignature((Signature)newValue);
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
			case DomainsPackage.DOMAIN__CONSTRAINT:
				getConstraint().clear();
				return;
			case DomainsPackage.DOMAIN__SIGNATURE:
				setSignature((Signature)null);
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
			case DomainsPackage.DOMAIN__CONSTRAINT:
				return constraint != null && !constraint.isEmpty();
			case DomainsPackage.DOMAIN__SIGNATURE:
				return getSignature() != null;
		}
		return super.eIsSet(featureID);
	}

} //DomainImpl

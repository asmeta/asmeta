/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import asmeta.terms.basicterms.BasictermsPackage;
import asmeta.terms.basicterms.VariableKind;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.FiniteQuantificationTerm;
import asmeta.terms.furtherterms.FurthertermsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.basicterms.impl.VariableTermImpl#getFiniteQuantificationTerm <em>Finite Quantification Term</em>}</li>
 *   <li>{@link asmeta.terms.basicterms.impl.VariableTermImpl#getName <em>Name</em>}</li>
 *   <li>{@link asmeta.terms.basicterms.impl.VariableTermImpl#getKind <em>Kind</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VariableTermImpl extends BasicTermImpl implements VariableTerm {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final VariableKind KIND_EDEFAULT = VariableKind.LOGICAL_VAR;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected VariableKind kind = KIND_EDEFAULT;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasictermsPackage.Literals.VARIABLE_TERM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FiniteQuantificationTerm getFiniteQuantificationTerm() {
		if (eContainerFeatureID() != BasictermsPackage.VARIABLE_TERM__FINITE_QUANTIFICATION_TERM) return null;
		return (FiniteQuantificationTerm)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFiniteQuantificationTerm(FiniteQuantificationTerm newFiniteQuantificationTerm, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newFiniteQuantificationTerm, BasictermsPackage.VARIABLE_TERM__FINITE_QUANTIFICATION_TERM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFiniteQuantificationTerm(FiniteQuantificationTerm newFiniteQuantificationTerm) {
		if (newFiniteQuantificationTerm != eInternalContainer() || (eContainerFeatureID() != BasictermsPackage.VARIABLE_TERM__FINITE_QUANTIFICATION_TERM && newFiniteQuantificationTerm != null)) {
			if (EcoreUtil.isAncestor(this, newFiniteQuantificationTerm))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newFiniteQuantificationTerm != null)
				msgs = ((InternalEObject)newFiniteQuantificationTerm).eInverseAdd(this, FurthertermsPackage.FINITE_QUANTIFICATION_TERM__VARIABLE, FiniteQuantificationTerm.class, msgs);
			msgs = basicSetFiniteQuantificationTerm(newFiniteQuantificationTerm, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictermsPackage.VARIABLE_TERM__FINITE_QUANTIFICATION_TERM, newFiniteQuantificationTerm, newFiniteQuantificationTerm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictermsPackage.VARIABLE_TERM__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VariableKind getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKind(VariableKind newKind) {
		VariableKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictermsPackage.VARIABLE_TERM__KIND, oldKind, kind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasictermsPackage.VARIABLE_TERM__FINITE_QUANTIFICATION_TERM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetFiniteQuantificationTerm((FiniteQuantificationTerm)otherEnd, msgs);
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
			case BasictermsPackage.VARIABLE_TERM__FINITE_QUANTIFICATION_TERM:
				return basicSetFiniteQuantificationTerm(null, msgs);
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
			case BasictermsPackage.VARIABLE_TERM__FINITE_QUANTIFICATION_TERM:
				return eInternalContainer().eInverseRemove(this, FurthertermsPackage.FINITE_QUANTIFICATION_TERM__VARIABLE, FiniteQuantificationTerm.class, msgs);
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
			case BasictermsPackage.VARIABLE_TERM__FINITE_QUANTIFICATION_TERM:
				return getFiniteQuantificationTerm();
			case BasictermsPackage.VARIABLE_TERM__NAME:
				return getName();
			case BasictermsPackage.VARIABLE_TERM__KIND:
				return getKind();
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
			case BasictermsPackage.VARIABLE_TERM__FINITE_QUANTIFICATION_TERM:
				setFiniteQuantificationTerm((FiniteQuantificationTerm)newValue);
				return;
			case BasictermsPackage.VARIABLE_TERM__NAME:
				setName((String)newValue);
				return;
			case BasictermsPackage.VARIABLE_TERM__KIND:
				setKind((VariableKind)newValue);
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
			case BasictermsPackage.VARIABLE_TERM__FINITE_QUANTIFICATION_TERM:
				setFiniteQuantificationTerm((FiniteQuantificationTerm)null);
				return;
			case BasictermsPackage.VARIABLE_TERM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BasictermsPackage.VARIABLE_TERM__KIND:
				setKind(KIND_EDEFAULT);
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
			case BasictermsPackage.VARIABLE_TERM__FINITE_QUANTIFICATION_TERM:
				return getFiniteQuantificationTerm() != null;
			case BasictermsPackage.VARIABLE_TERM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BasictermsPackage.VARIABLE_TERM__KIND:
				return kind != KIND_EDEFAULT;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", kind: ");
		result.append(kind);
		result.append(')');
		return result.toString();
	}

} //VariableTermImpl

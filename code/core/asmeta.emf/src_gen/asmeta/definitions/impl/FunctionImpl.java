/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.impl;

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
import asmeta.definitions.Function;
import asmeta.definitions.Invariant;
import asmeta.definitions.domains.Domain;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.Signature;
import asmeta.structure.StructurePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.impl.FunctionImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link asmeta.definitions.impl.FunctionImpl#getCodomain <em>Codomain</em>}</li>
 *   <li>{@link asmeta.definitions.impl.FunctionImpl#getDefinition <em>Definition</em>}</li>
 *   <li>{@link asmeta.definitions.impl.FunctionImpl#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link asmeta.definitions.impl.FunctionImpl#getArity <em>Arity</em>}</li>
 *   <li>{@link asmeta.definitions.impl.FunctionImpl#getSignature <em>Signature</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class FunctionImpl extends ClassifierImpl implements Function {
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
	 * The cached value of the '{@link #getCodomain() <em>Codomain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCodomain()
	 * @generated
	 * @ordered
	 */
	protected Domain codomain;

	/**
	 * The cached value of the '{@link #getDefinition() <em>Definition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinition()
	 * @generated
	 * @ordered
	 */
	protected FunctionDefinition definition;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DefinitionsPackage.Literals.FUNCTION;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DefinitionsPackage.FUNCTION__DOMAIN, oldDomain, domain));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DefinitionsPackage.FUNCTION__DOMAIN, oldDomain, domain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Domain getCodomain() {
		if (codomain != null && codomain.eIsProxy()) {
			InternalEObject oldCodomain = (InternalEObject)codomain;
			codomain = (Domain)eResolveProxy(oldCodomain);
			if (codomain != oldCodomain) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DefinitionsPackage.FUNCTION__CODOMAIN, oldCodomain, codomain));
			}
		}
		return codomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain basicGetCodomain() {
		return codomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCodomain(Domain newCodomain) {
		Domain oldCodomain = codomain;
		codomain = newCodomain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DefinitionsPackage.FUNCTION__CODOMAIN, oldCodomain, codomain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FunctionDefinition getDefinition() {
		if (definition != null && definition.eIsProxy()) {
			InternalEObject oldDefinition = (InternalEObject)definition;
			definition = (FunctionDefinition)eResolveProxy(oldDefinition);
			if (definition != oldDefinition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DefinitionsPackage.FUNCTION__DEFINITION, oldDefinition, definition));
			}
		}
		return definition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionDefinition basicGetDefinition() {
		return definition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefinition(FunctionDefinition newDefinition, NotificationChain msgs) {
		FunctionDefinition oldDefinition = definition;
		definition = newDefinition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DefinitionsPackage.FUNCTION__DEFINITION, oldDefinition, newDefinition);
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
	public void setDefinition(FunctionDefinition newDefinition) {
		if (newDefinition != definition) {
			NotificationChain msgs = null;
			if (definition != null)
				msgs = ((InternalEObject)definition).eInverseRemove(this, StructurePackage.FUNCTION_DEFINITION__DEFINED_FUNCTION, FunctionDefinition.class, msgs);
			if (newDefinition != null)
				msgs = ((InternalEObject)newDefinition).eInverseAdd(this, StructurePackage.FUNCTION_DEFINITION__DEFINED_FUNCTION, FunctionDefinition.class, msgs);
			msgs = basicSetDefinition(newDefinition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DefinitionsPackage.FUNCTION__DEFINITION, newDefinition, newDefinition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Invariant> getConstraint() {
		if (constraint == null) {
			constraint = new EObjectWithInverseResolvingEList.ManyInverse<Invariant>(Invariant.class, this, DefinitionsPackage.FUNCTION__CONSTRAINT, DefinitionsPackage.INVARIANT__CONSTRAINED_FUNCTION);
		}
		return constraint;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DefinitionsPackage.FUNCTION__ARITY, oldArity, arity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Signature getSignature() {
		if (eContainerFeatureID() != DefinitionsPackage.FUNCTION__SIGNATURE) return null;
		return (Signature)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSignature(Signature newSignature, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSignature, DefinitionsPackage.FUNCTION__SIGNATURE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSignature(Signature newSignature) {
		if (newSignature != eInternalContainer() || (eContainerFeatureID() != DefinitionsPackage.FUNCTION__SIGNATURE && newSignature != null)) {
			if (EcoreUtil.isAncestor(this, newSignature))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSignature != null)
				msgs = ((InternalEObject)newSignature).eInverseAdd(this, StructurePackage.SIGNATURE__FUNCTION, Signature.class, msgs);
			msgs = basicSetSignature(newSignature, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DefinitionsPackage.FUNCTION__SIGNATURE, newSignature, newSignature));
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
			case DefinitionsPackage.FUNCTION__DEFINITION:
				if (definition != null)
					msgs = ((InternalEObject)definition).eInverseRemove(this, StructurePackage.FUNCTION_DEFINITION__DEFINED_FUNCTION, FunctionDefinition.class, msgs);
				return basicSetDefinition((FunctionDefinition)otherEnd, msgs);
			case DefinitionsPackage.FUNCTION__CONSTRAINT:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getConstraint()).basicAdd(otherEnd, msgs);
			case DefinitionsPackage.FUNCTION__SIGNATURE:
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
			case DefinitionsPackage.FUNCTION__DEFINITION:
				return basicSetDefinition(null, msgs);
			case DefinitionsPackage.FUNCTION__CONSTRAINT:
				return ((InternalEList<?>)getConstraint()).basicRemove(otherEnd, msgs);
			case DefinitionsPackage.FUNCTION__SIGNATURE:
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
			case DefinitionsPackage.FUNCTION__SIGNATURE:
				return eInternalContainer().eInverseRemove(this, StructurePackage.SIGNATURE__FUNCTION, Signature.class, msgs);
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
			case DefinitionsPackage.FUNCTION__DOMAIN:
				if (resolve) return getDomain();
				return basicGetDomain();
			case DefinitionsPackage.FUNCTION__CODOMAIN:
				if (resolve) return getCodomain();
				return basicGetCodomain();
			case DefinitionsPackage.FUNCTION__DEFINITION:
				if (resolve) return getDefinition();
				return basicGetDefinition();
			case DefinitionsPackage.FUNCTION__CONSTRAINT:
				return getConstraint();
			case DefinitionsPackage.FUNCTION__ARITY:
				return getArity();
			case DefinitionsPackage.FUNCTION__SIGNATURE:
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
			case DefinitionsPackage.FUNCTION__DOMAIN:
				setDomain((Domain)newValue);
				return;
			case DefinitionsPackage.FUNCTION__CODOMAIN:
				setCodomain((Domain)newValue);
				return;
			case DefinitionsPackage.FUNCTION__DEFINITION:
				setDefinition((FunctionDefinition)newValue);
				return;
			case DefinitionsPackage.FUNCTION__CONSTRAINT:
				getConstraint().clear();
				getConstraint().addAll((Collection<? extends Invariant>)newValue);
				return;
			case DefinitionsPackage.FUNCTION__ARITY:
				setArity((Integer)newValue);
				return;
			case DefinitionsPackage.FUNCTION__SIGNATURE:
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
			case DefinitionsPackage.FUNCTION__DOMAIN:
				setDomain((Domain)null);
				return;
			case DefinitionsPackage.FUNCTION__CODOMAIN:
				setCodomain((Domain)null);
				return;
			case DefinitionsPackage.FUNCTION__DEFINITION:
				setDefinition((FunctionDefinition)null);
				return;
			case DefinitionsPackage.FUNCTION__CONSTRAINT:
				getConstraint().clear();
				return;
			case DefinitionsPackage.FUNCTION__ARITY:
				setArity(ARITY_EDEFAULT);
				return;
			case DefinitionsPackage.FUNCTION__SIGNATURE:
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
			case DefinitionsPackage.FUNCTION__DOMAIN:
				return domain != null;
			case DefinitionsPackage.FUNCTION__CODOMAIN:
				return codomain != null;
			case DefinitionsPackage.FUNCTION__DEFINITION:
				return definition != null;
			case DefinitionsPackage.FUNCTION__CONSTRAINT:
				return constraint != null && !constraint.isEmpty();
			case DefinitionsPackage.FUNCTION__ARITY:
				return ARITY_EDEFAULT == null ? arity != null : !ARITY_EDEFAULT.equals(arity);
			case DefinitionsPackage.FUNCTION__SIGNATURE:
				return getSignature() != null;
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
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Function) {
			Function otherFunc = (Function)o;
			return otherFunc.getName().equals(name) &&
					otherFunc.getArity().equals(arity) &&
					((domain == null && otherFunc.getDomain() == null) || otherFunc.getDomain().equals(domain)) &&
					otherFunc.getCodomain().equals(codomain);
		}
		else {
			return false;
		}
	}

} //FunctionImpl

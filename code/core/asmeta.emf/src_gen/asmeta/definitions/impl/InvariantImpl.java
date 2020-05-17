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
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.Function;
import asmeta.definitions.Invariant;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.DomainsPackage;
import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Invariant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.impl.InvariantImpl#getConstrainedDomain <em>Constrained Domain</em>}</li>
 *   <li>{@link asmeta.definitions.impl.InvariantImpl#getConstrainedRule <em>Constrained Rule</em>}</li>
 *   <li>{@link asmeta.definitions.impl.InvariantImpl#getConstrainedFunction <em>Constrained Function</em>}</li>
 *   <li>{@link asmeta.definitions.impl.InvariantImpl#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvariantImpl extends PropertyImpl implements Invariant {
	/**
	 * The cached value of the '{@link #getConstrainedDomain() <em>Constrained Domain</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstrainedDomain()
	 * @generated
	 * @ordered
	 */
	protected EList<Domain> constrainedDomain;

	/**
	 * The cached value of the '{@link #getConstrainedRule() <em>Constrained Rule</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstrainedRule()
	 * @generated
	 * @ordered
	 */
	protected EList<RuleDeclaration> constrainedRule;

	/**
	 * The cached value of the '{@link #getConstrainedFunction() <em>Constrained Function</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstrainedFunction()
	 * @generated
	 * @ordered
	 */
	protected EList<Function> constrainedFunction;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Term body;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InvariantImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DefinitionsPackage.Literals.INVARIANT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Domain> getConstrainedDomain() {
		if (constrainedDomain == null) {
			constrainedDomain = new EObjectWithInverseResolvingEList.ManyInverse<Domain>(Domain.class, this, DefinitionsPackage.INVARIANT__CONSTRAINED_DOMAIN, DomainsPackage.DOMAIN__CONSTRAINT);
		}
		return constrainedDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RuleDeclaration> getConstrainedRule() {
		if (constrainedRule == null) {
			constrainedRule = new EObjectWithInverseResolvingEList.ManyInverse<RuleDeclaration>(RuleDeclaration.class, this, DefinitionsPackage.INVARIANT__CONSTRAINED_RULE, DefinitionsPackage.RULE_DECLARATION__CONSTRAINT);
		}
		return constrainedRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Function> getConstrainedFunction() {
		if (constrainedFunction == null) {
			constrainedFunction = new EObjectWithInverseResolvingEList.ManyInverse<Function>(Function.class, this, DefinitionsPackage.INVARIANT__CONSTRAINED_FUNCTION, DefinitionsPackage.FUNCTION__CONSTRAINT);
		}
		return constrainedFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBody(Term newBody, NotificationChain msgs) {
		Term oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DefinitionsPackage.INVARIANT__BODY, oldBody, newBody);
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
	public void setBody(Term newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DefinitionsPackage.INVARIANT__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DefinitionsPackage.INVARIANT__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DefinitionsPackage.INVARIANT__BODY, newBody, newBody));
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
			case DefinitionsPackage.INVARIANT__CONSTRAINED_DOMAIN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getConstrainedDomain()).basicAdd(otherEnd, msgs);
			case DefinitionsPackage.INVARIANT__CONSTRAINED_RULE:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getConstrainedRule()).basicAdd(otherEnd, msgs);
			case DefinitionsPackage.INVARIANT__CONSTRAINED_FUNCTION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getConstrainedFunction()).basicAdd(otherEnd, msgs);
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
			case DefinitionsPackage.INVARIANT__CONSTRAINED_DOMAIN:
				return ((InternalEList<?>)getConstrainedDomain()).basicRemove(otherEnd, msgs);
			case DefinitionsPackage.INVARIANT__CONSTRAINED_RULE:
				return ((InternalEList<?>)getConstrainedRule()).basicRemove(otherEnd, msgs);
			case DefinitionsPackage.INVARIANT__CONSTRAINED_FUNCTION:
				return ((InternalEList<?>)getConstrainedFunction()).basicRemove(otherEnd, msgs);
			case DefinitionsPackage.INVARIANT__BODY:
				return basicSetBody(null, msgs);
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
			case DefinitionsPackage.INVARIANT__CONSTRAINED_DOMAIN:
				return getConstrainedDomain();
			case DefinitionsPackage.INVARIANT__CONSTRAINED_RULE:
				return getConstrainedRule();
			case DefinitionsPackage.INVARIANT__CONSTRAINED_FUNCTION:
				return getConstrainedFunction();
			case DefinitionsPackage.INVARIANT__BODY:
				return getBody();
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
			case DefinitionsPackage.INVARIANT__CONSTRAINED_DOMAIN:
				getConstrainedDomain().clear();
				getConstrainedDomain().addAll((Collection<? extends Domain>)newValue);
				return;
			case DefinitionsPackage.INVARIANT__CONSTRAINED_RULE:
				getConstrainedRule().clear();
				getConstrainedRule().addAll((Collection<? extends RuleDeclaration>)newValue);
				return;
			case DefinitionsPackage.INVARIANT__CONSTRAINED_FUNCTION:
				getConstrainedFunction().clear();
				getConstrainedFunction().addAll((Collection<? extends Function>)newValue);
				return;
			case DefinitionsPackage.INVARIANT__BODY:
				setBody((Term)newValue);
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
			case DefinitionsPackage.INVARIANT__CONSTRAINED_DOMAIN:
				getConstrainedDomain().clear();
				return;
			case DefinitionsPackage.INVARIANT__CONSTRAINED_RULE:
				getConstrainedRule().clear();
				return;
			case DefinitionsPackage.INVARIANT__CONSTRAINED_FUNCTION:
				getConstrainedFunction().clear();
				return;
			case DefinitionsPackage.INVARIANT__BODY:
				setBody((Term)null);
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
			case DefinitionsPackage.INVARIANT__CONSTRAINED_DOMAIN:
				return constrainedDomain != null && !constrainedDomain.isEmpty();
			case DefinitionsPackage.INVARIANT__CONSTRAINED_RULE:
				return constrainedRule != null && !constrainedRule.isEmpty();
			case DefinitionsPackage.INVARIANT__CONSTRAINED_FUNCTION:
				return constrainedFunction != null && !constrainedFunction.isEmpty();
			case DefinitionsPackage.INVARIANT__BODY:
				return body != null;
		}
		return super.eIsSet(featureID);
	}

} //InvariantImpl

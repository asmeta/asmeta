/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.FurthertermsPackage;
import asmeta.terms.furtherterms.LetTerm;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Let Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.furtherterms.impl.LetTermImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.impl.LetTermImpl#getAssignmentTerm <em>Assignment Term</em>}</li>
 *   <li>{@link asmeta.terms.furtherterms.impl.LetTermImpl#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LetTermImpl extends VariableBindingTermImpl implements LetTerm {
	/**
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected EList<VariableTerm> variable;

	/**
	 * The cached value of the '{@link #getAssignmentTerm() <em>Assignment Term</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssignmentTerm()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> assignmentTerm;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' reference.
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
	protected LetTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FurthertermsPackage.Literals.LET_TERM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariableTerm> getVariable() {
		if (variable == null) {
			variable = new EObjectContainmentEList<VariableTerm>(VariableTerm.class, this, FurthertermsPackage.LET_TERM__VARIABLE);
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Term> getAssignmentTerm() {
		if (assignmentTerm == null) {
			assignmentTerm = new EObjectResolvingEList<Term>(Term.class, this, FurthertermsPackage.LET_TERM__ASSIGNMENT_TERM);
		}
		return assignmentTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Term getBody() {
		if (body != null && body.eIsProxy()) {
			InternalEObject oldBody = (InternalEObject)body;
			body = (Term)eResolveProxy(oldBody);
			if (body != oldBody) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FurthertermsPackage.LET_TERM__BODY, oldBody, body));
			}
		}
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term basicGetBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBody(Term newBody) {
		Term oldBody = body;
		body = newBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FurthertermsPackage.LET_TERM__BODY, oldBody, body));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FurthertermsPackage.LET_TERM__VARIABLE:
				return ((InternalEList<?>)getVariable()).basicRemove(otherEnd, msgs);
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
			case FurthertermsPackage.LET_TERM__VARIABLE:
				return getVariable();
			case FurthertermsPackage.LET_TERM__ASSIGNMENT_TERM:
				return getAssignmentTerm();
			case FurthertermsPackage.LET_TERM__BODY:
				if (resolve) return getBody();
				return basicGetBody();
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
			case FurthertermsPackage.LET_TERM__VARIABLE:
				getVariable().clear();
				getVariable().addAll((Collection<? extends VariableTerm>)newValue);
				return;
			case FurthertermsPackage.LET_TERM__ASSIGNMENT_TERM:
				getAssignmentTerm().clear();
				getAssignmentTerm().addAll((Collection<? extends Term>)newValue);
				return;
			case FurthertermsPackage.LET_TERM__BODY:
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
			case FurthertermsPackage.LET_TERM__VARIABLE:
				getVariable().clear();
				return;
			case FurthertermsPackage.LET_TERM__ASSIGNMENT_TERM:
				getAssignmentTerm().clear();
				return;
			case FurthertermsPackage.LET_TERM__BODY:
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
			case FurthertermsPackage.LET_TERM__VARIABLE:
				return variable != null && !variable.isEmpty();
			case FurthertermsPackage.LET_TERM__ASSIGNMENT_TERM:
				return assignmentTerm != null && !assignmentTerm.isEmpty();
			case FurthertermsPackage.LET_TERM__BODY:
				return body != null;
		}
		return super.eIsSet(featureID);
	}

} //LetTermImpl

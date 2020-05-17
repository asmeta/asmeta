/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.Function;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.StructurePackage;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.impl.FunctionDefinitionImpl#getBody <em>Body</em>}</li>
 *   <li>{@link asmeta.structure.impl.FunctionDefinitionImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link asmeta.structure.impl.FunctionDefinitionImpl#getDefinedFunction <em>Defined Function</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FunctionDefinitionImpl extends EObjectImpl implements FunctionDefinition {
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
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected EList<VariableTerm> variable;

	/**
	 * The cached value of the '{@link #getDefinedFunction() <em>Defined Function</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedFunction()
	 * @generated
	 * @ordered
	 */
	protected Function definedFunction;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StructurePackage.Literals.FUNCTION_DEFINITION;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StructurePackage.FUNCTION_DEFINITION__BODY, oldBody, body));
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
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.FUNCTION_DEFINITION__BODY, oldBody, body));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VariableTerm> getVariable() {
		if (variable == null) {
			variable = new EObjectResolvingEList<VariableTerm>(VariableTerm.class, this, StructurePackage.FUNCTION_DEFINITION__VARIABLE);
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Function getDefinedFunction() {
		if (definedFunction != null && definedFunction.eIsProxy()) {
			InternalEObject oldDefinedFunction = (InternalEObject)definedFunction;
			definedFunction = (Function)eResolveProxy(oldDefinedFunction);
			if (definedFunction != oldDefinedFunction) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StructurePackage.FUNCTION_DEFINITION__DEFINED_FUNCTION, oldDefinedFunction, definedFunction));
			}
		}
		return definedFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Function basicGetDefinedFunction() {
		return definedFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefinedFunction(Function newDefinedFunction, NotificationChain msgs) {
		Function oldDefinedFunction = definedFunction;
		definedFunction = newDefinedFunction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StructurePackage.FUNCTION_DEFINITION__DEFINED_FUNCTION, oldDefinedFunction, newDefinedFunction);
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
	public void setDefinedFunction(Function newDefinedFunction) {
		if (newDefinedFunction != definedFunction) {
			NotificationChain msgs = null;
			if (definedFunction != null)
				msgs = ((InternalEObject)definedFunction).eInverseRemove(this, DefinitionsPackage.FUNCTION__DEFINITION, Function.class, msgs);
			if (newDefinedFunction != null)
				msgs = ((InternalEObject)newDefinedFunction).eInverseAdd(this, DefinitionsPackage.FUNCTION__DEFINITION, Function.class, msgs);
			msgs = basicSetDefinedFunction(newDefinedFunction, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.FUNCTION_DEFINITION__DEFINED_FUNCTION, newDefinedFunction, newDefinedFunction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StructurePackage.FUNCTION_DEFINITION__DEFINED_FUNCTION:
				if (definedFunction != null)
					msgs = ((InternalEObject)definedFunction).eInverseRemove(this, DefinitionsPackage.FUNCTION__DEFINITION, Function.class, msgs);
				return basicSetDefinedFunction((Function)otherEnd, msgs);
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
			case StructurePackage.FUNCTION_DEFINITION__DEFINED_FUNCTION:
				return basicSetDefinedFunction(null, msgs);
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
			case StructurePackage.FUNCTION_DEFINITION__BODY:
				if (resolve) return getBody();
				return basicGetBody();
			case StructurePackage.FUNCTION_DEFINITION__VARIABLE:
				return getVariable();
			case StructurePackage.FUNCTION_DEFINITION__DEFINED_FUNCTION:
				if (resolve) return getDefinedFunction();
				return basicGetDefinedFunction();
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
			case StructurePackage.FUNCTION_DEFINITION__BODY:
				setBody((Term)newValue);
				return;
			case StructurePackage.FUNCTION_DEFINITION__VARIABLE:
				getVariable().clear();
				getVariable().addAll((Collection<? extends VariableTerm>)newValue);
				return;
			case StructurePackage.FUNCTION_DEFINITION__DEFINED_FUNCTION:
				setDefinedFunction((Function)newValue);
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
			case StructurePackage.FUNCTION_DEFINITION__BODY:
				setBody((Term)null);
				return;
			case StructurePackage.FUNCTION_DEFINITION__VARIABLE:
				getVariable().clear();
				return;
			case StructurePackage.FUNCTION_DEFINITION__DEFINED_FUNCTION:
				setDefinedFunction((Function)null);
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
			case StructurePackage.FUNCTION_DEFINITION__BODY:
				return body != null;
			case StructurePackage.FUNCTION_DEFINITION__VARIABLE:
				return variable != null && !variable.isEmpty();
			case StructurePackage.FUNCTION_DEFINITION__DEFINED_FUNCTION:
				return definedFunction != null;
		}
		return super.eIsSet(featureID);
	}

} //FunctionDefinitionImpl

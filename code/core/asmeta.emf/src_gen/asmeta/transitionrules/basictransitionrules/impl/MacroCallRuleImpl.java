/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;

import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Macro Call Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.MacroCallRuleImpl#getCalledMacro <em>Called Macro</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.impl.MacroCallRuleImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MacroCallRuleImpl extends BasicRuleImpl implements MacroCallRule {
	/**
	 * The cached value of the '{@link #getCalledMacro() <em>Called Macro</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCalledMacro()
	 * @generated
	 * @ordered
	 */
	protected MacroDeclaration calledMacro;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Term> parameters;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MacroCallRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasictransitionrulesPackage.Literals.MACRO_CALL_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MacroDeclaration getCalledMacro() {
		if (calledMacro != null && calledMacro.eIsProxy()) {
			InternalEObject oldCalledMacro = (InternalEObject)calledMacro;
			calledMacro = (MacroDeclaration)eResolveProxy(oldCalledMacro);
			if (calledMacro != oldCalledMacro) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BasictransitionrulesPackage.MACRO_CALL_RULE__CALLED_MACRO, oldCalledMacro, calledMacro));
			}
		}
		return calledMacro;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MacroDeclaration basicGetCalledMacro() {
		return calledMacro;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCalledMacro(MacroDeclaration newCalledMacro) {
		MacroDeclaration oldCalledMacro = calledMacro;
		calledMacro = newCalledMacro;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BasictransitionrulesPackage.MACRO_CALL_RULE__CALLED_MACRO, oldCalledMacro, calledMacro));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Term> getParameters() {
		if (parameters == null) {
			parameters = new EDataTypeEList<Term>(Term.class, this, BasictransitionrulesPackage.MACRO_CALL_RULE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BasictransitionrulesPackage.MACRO_CALL_RULE__CALLED_MACRO:
				if (resolve) return getCalledMacro();
				return basicGetCalledMacro();
			case BasictransitionrulesPackage.MACRO_CALL_RULE__PARAMETERS:
				return getParameters();
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
			case BasictransitionrulesPackage.MACRO_CALL_RULE__CALLED_MACRO:
				setCalledMacro((MacroDeclaration)newValue);
				return;
			case BasictransitionrulesPackage.MACRO_CALL_RULE__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Term>)newValue);
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
			case BasictransitionrulesPackage.MACRO_CALL_RULE__CALLED_MACRO:
				setCalledMacro((MacroDeclaration)null);
				return;
			case BasictransitionrulesPackage.MACRO_CALL_RULE__PARAMETERS:
				getParameters().clear();
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
			case BasictransitionrulesPackage.MACRO_CALL_RULE__CALLED_MACRO:
				return calledMacro != null;
			case BasictransitionrulesPackage.MACRO_CALL_RULE__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
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
		result.append(" (parameters: ");
		result.append(parameters);
		result.append(')');
		return result.toString();
	}

} //MacroCallRuleImpl

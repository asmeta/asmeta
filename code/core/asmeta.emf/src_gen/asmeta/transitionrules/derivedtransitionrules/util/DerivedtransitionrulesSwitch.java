/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.derivedtransitionrules.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.derivedtransitionrules.*;
import asmeta.transitionrules.derivedtransitionrules.BasicDerivedRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.derivedtransitionrules.DerivedRule;
import asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage;
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule;
import asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule;
import asmeta.transitionrules.derivedtransitionrules.TurboDerivedRule;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage
 * @generated
 */
public class DerivedtransitionrulesSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DerivedtransitionrulesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DerivedtransitionrulesSwitch() {
		if (modelPackage == null) {
			modelPackage = DerivedtransitionrulesPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case DerivedtransitionrulesPackage.RECURSIVE_WHILE_RULE: {
				RecursiveWhileRule recursiveWhileRule = (RecursiveWhileRule)theEObject;
				T result = caseRecursiveWhileRule(recursiveWhileRule);
				if (result == null) result = caseTurboDerivedRule(recursiveWhileRule);
				if (result == null) result = caseDerivedRule(recursiveWhileRule);
				if (result == null) result = caseRule(recursiveWhileRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DerivedtransitionrulesPackage.ITERATIVE_WHILE_RULE: {
				IterativeWhileRule iterativeWhileRule = (IterativeWhileRule)theEObject;
				T result = caseIterativeWhileRule(iterativeWhileRule);
				if (result == null) result = caseTurboDerivedRule(iterativeWhileRule);
				if (result == null) result = caseDerivedRule(iterativeWhileRule);
				if (result == null) result = caseRule(iterativeWhileRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DerivedtransitionrulesPackage.DERIVED_RULE: {
				DerivedRule derivedRule = (DerivedRule)theEObject;
				T result = caseDerivedRule(derivedRule);
				if (result == null) result = caseRule(derivedRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DerivedtransitionrulesPackage.CASE_RULE: {
				CaseRule caseRule = (CaseRule)theEObject;
				T result = caseCaseRule(caseRule);
				if (result == null) result = caseBasicDerivedRule(caseRule);
				if (result == null) result = caseDerivedRule(caseRule);
				if (result == null) result = caseRule(caseRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DerivedtransitionrulesPackage.BASIC_DERIVED_RULE: {
				BasicDerivedRule basicDerivedRule = (BasicDerivedRule)theEObject;
				T result = caseBasicDerivedRule(basicDerivedRule);
				if (result == null) result = caseDerivedRule(basicDerivedRule);
				if (result == null) result = caseRule(basicDerivedRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DerivedtransitionrulesPackage.TURBO_DERIVED_RULE: {
				TurboDerivedRule turboDerivedRule = (TurboDerivedRule)theEObject;
				T result = caseTurboDerivedRule(turboDerivedRule);
				if (result == null) result = caseDerivedRule(turboDerivedRule);
				if (result == null) result = caseRule(turboDerivedRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Recursive While Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Recursive While Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRecursiveWhileRule(RecursiveWhileRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Iterative While Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iterative While Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIterativeWhileRule(IterativeWhileRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Derived Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Derived Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDerivedRule(DerivedRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Case Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Case Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCaseRule(CaseRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Basic Derived Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Basic Derived Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBasicDerivedRule(BasicDerivedRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Turbo Derived Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Turbo Derived Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTurboDerivedRule(TurboDerivedRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRule(Rule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //DerivedtransitionrulesSwitch

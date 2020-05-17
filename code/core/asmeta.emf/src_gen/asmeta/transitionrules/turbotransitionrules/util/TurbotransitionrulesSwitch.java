/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import asmeta.definitions.Classifier;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.NamedElement;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.turbotransitionrules.*;
import asmeta.transitionrules.turbotransitionrules.IterateRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.transitionrules.turbotransitionrules.TryCatchRule;
import asmeta.transitionrules.turbotransitionrules.TurboCallRule;
import asmeta.transitionrules.turbotransitionrules.TurboDeclaration;
import asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule;
import asmeta.transitionrules.turbotransitionrules.TurboReturnRule;
import asmeta.transitionrules.turbotransitionrules.TurboRule;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage;

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
 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage
 * @generated
 */
public class TurbotransitionrulesSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TurbotransitionrulesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TurbotransitionrulesSwitch() {
		if (modelPackage == null) {
			modelPackage = TurbotransitionrulesPackage.eINSTANCE;
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
			case TurbotransitionrulesPackage.TURBO_RULE: {
				TurboRule turboRule = (TurboRule)theEObject;
				T result = caseTurboRule(turboRule);
				if (result == null) result = caseRule(turboRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TurbotransitionrulesPackage.TURBO_DECLARATION: {
				TurboDeclaration turboDeclaration = (TurboDeclaration)theEObject;
				T result = caseTurboDeclaration(turboDeclaration);
				if (result == null) result = caseRuleDeclaration(turboDeclaration);
				if (result == null) result = caseClassifier(turboDeclaration);
				if (result == null) result = caseNamedElement(turboDeclaration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TurbotransitionrulesPackage.SEQ_RULE: {
				SeqRule seqRule = (SeqRule)theEObject;
				T result = caseSeqRule(seqRule);
				if (result == null) result = caseTurboRule(seqRule);
				if (result == null) result = caseRule(seqRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE: {
				TurboLocalStateRule turboLocalStateRule = (TurboLocalStateRule)theEObject;
				T result = caseTurboLocalStateRule(turboLocalStateRule);
				if (result == null) result = caseTurboRule(turboLocalStateRule);
				if (result == null) result = caseRule(turboLocalStateRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TurbotransitionrulesPackage.TURBO_CALL_RULE: {
				TurboCallRule turboCallRule = (TurboCallRule)theEObject;
				T result = caseTurboCallRule(turboCallRule);
				if (result == null) result = caseTurboRule(turboCallRule);
				if (result == null) result = caseRule(turboCallRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TurbotransitionrulesPackage.TURBO_RETURN_RULE: {
				TurboReturnRule turboReturnRule = (TurboReturnRule)theEObject;
				T result = caseTurboReturnRule(turboReturnRule);
				if (result == null) result = caseTurboRule(turboReturnRule);
				if (result == null) result = caseRule(turboReturnRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TurbotransitionrulesPackage.TRY_CATCH_RULE: {
				TryCatchRule tryCatchRule = (TryCatchRule)theEObject;
				T result = caseTryCatchRule(tryCatchRule);
				if (result == null) result = caseTurboRule(tryCatchRule);
				if (result == null) result = caseRule(tryCatchRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TurbotransitionrulesPackage.ITERATE_RULE: {
				IterateRule iterateRule = (IterateRule)theEObject;
				T result = caseIterateRule(iterateRule);
				if (result == null) result = caseTurboRule(iterateRule);
				if (result == null) result = caseRule(iterateRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Turbo Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Turbo Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTurboRule(TurboRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Turbo Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Turbo Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTurboDeclaration(TurboDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Seq Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Seq Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSeqRule(SeqRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Turbo Local State Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Turbo Local State Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTurboLocalStateRule(TurboLocalStateRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Turbo Call Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Turbo Call Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTurboCallRule(TurboCallRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Turbo Return Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Turbo Return Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTurboReturnRule(TurboReturnRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Try Catch Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Try Catch Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTryCatchRule(TryCatchRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Iterate Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iterate Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIterateRule(IterateRule object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedElement(NamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClassifier(Classifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleDeclaration(RuleDeclaration object) {
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

} //TurbotransitionrulesSwitch

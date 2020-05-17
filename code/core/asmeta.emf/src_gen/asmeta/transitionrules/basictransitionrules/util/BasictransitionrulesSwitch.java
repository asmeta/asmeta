/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import asmeta.definitions.Classifier;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.NamedElement;
import asmeta.transitionrules.basictransitionrules.*;
import asmeta.transitionrules.basictransitionrules.BasicRule;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;

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
 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage
 * @generated
 */
public class BasictransitionrulesSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BasictransitionrulesPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasictransitionrulesSwitch() {
		if (modelPackage == null) {
			modelPackage = BasictransitionrulesPackage.eINSTANCE;
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
			case BasictransitionrulesPackage.TERM_AS_RULE: {
				TermAsRule termAsRule = (TermAsRule)theEObject;
				T result = caseTermAsRule(termAsRule);
				if (result == null) result = caseRule(termAsRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictransitionrulesPackage.BASIC_RULE: {
				BasicRule basicRule = (BasicRule)theEObject;
				T result = caseBasicRule(basicRule);
				if (result == null) result = caseRule(basicRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictransitionrulesPackage.RULE: {
				Rule rule = (Rule)theEObject;
				T result = caseRule(rule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictransitionrulesPackage.CHOOSE_RULE: {
				ChooseRule chooseRule = (ChooseRule)theEObject;
				T result = caseChooseRule(chooseRule);
				if (result == null) result = caseBasicRule(chooseRule);
				if (result == null) result = caseRule(chooseRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictransitionrulesPackage.MACRO_CALL_RULE: {
				MacroCallRule macroCallRule = (MacroCallRule)theEObject;
				T result = caseMacroCallRule(macroCallRule);
				if (result == null) result = caseBasicRule(macroCallRule);
				if (result == null) result = caseRule(macroCallRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictransitionrulesPackage.BLOCK_RULE: {
				BlockRule blockRule = (BlockRule)theEObject;
				T result = caseBlockRule(blockRule);
				if (result == null) result = caseBasicRule(blockRule);
				if (result == null) result = caseRule(blockRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictransitionrulesPackage.CONDITIONAL_RULE: {
				ConditionalRule conditionalRule = (ConditionalRule)theEObject;
				T result = caseConditionalRule(conditionalRule);
				if (result == null) result = caseBasicRule(conditionalRule);
				if (result == null) result = caseRule(conditionalRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictransitionrulesPackage.FORALL_RULE: {
				ForallRule forallRule = (ForallRule)theEObject;
				T result = caseForallRule(forallRule);
				if (result == null) result = caseBasicRule(forallRule);
				if (result == null) result = caseRule(forallRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictransitionrulesPackage.LET_RULE: {
				LetRule letRule = (LetRule)theEObject;
				T result = caseLetRule(letRule);
				if (result == null) result = caseBasicRule(letRule);
				if (result == null) result = caseRule(letRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictransitionrulesPackage.EXTEND_RULE: {
				ExtendRule extendRule = (ExtendRule)theEObject;
				T result = caseExtendRule(extendRule);
				if (result == null) result = caseBasicRule(extendRule);
				if (result == null) result = caseRule(extendRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictransitionrulesPackage.UPDATE_RULE: {
				UpdateRule updateRule = (UpdateRule)theEObject;
				T result = caseUpdateRule(updateRule);
				if (result == null) result = caseBasicRule(updateRule);
				if (result == null) result = caseRule(updateRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictransitionrulesPackage.SKIP_RULE: {
				SkipRule skipRule = (SkipRule)theEObject;
				T result = caseSkipRule(skipRule);
				if (result == null) result = caseBasicRule(skipRule);
				if (result == null) result = caseRule(skipRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictransitionrulesPackage.MACRO_DECLARATION: {
				MacroDeclaration macroDeclaration = (MacroDeclaration)theEObject;
				T result = caseMacroDeclaration(macroDeclaration);
				if (result == null) result = caseRuleDeclaration(macroDeclaration);
				if (result == null) result = caseClassifier(macroDeclaration);
				if (result == null) result = caseNamedElement(macroDeclaration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Term As Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Term As Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTermAsRule(TermAsRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Basic Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Basic Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBasicRule(BasicRule object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Choose Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Choose Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChooseRule(ChooseRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Macro Call Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Macro Call Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMacroCallRule(MacroCallRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Block Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Block Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlockRule(BlockRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Conditional Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Conditional Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConditionalRule(ConditionalRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Forall Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Forall Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseForallRule(ForallRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Let Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Let Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLetRule(LetRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Extend Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Extend Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtendRule(ExtendRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Update Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Update Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUpdateRule(UpdateRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Skip Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Skip Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSkipRule(SkipRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Macro Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Macro Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMacroDeclaration(MacroDeclaration object) {
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

} //BasictransitionrulesSwitch

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms.util;

import asmeta.terms.basicterms.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import asmeta.terms.basicterms.BasicTerm;
import asmeta.terms.basicterms.BasictermsPackage;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.CollectionTerm;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.ExtendedTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.RuleAsTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;

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
 * @see asmeta.terms.basicterms.BasictermsPackage
 * @generated
 */
public class BasictermsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BasictermsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasictermsSwitch() {
		if (modelPackage == null) {
			modelPackage = BasictermsPackage.eINSTANCE;
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
			case BasictermsPackage.VARIABLE_TERM: {
				VariableTerm variableTerm = (VariableTerm)theEObject;
				T result = caseVariableTerm(variableTerm);
				if (result == null) result = caseBasicTerm(variableTerm);
				if (result == null) result = caseTerm(variableTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictermsPackage.UNDEF_TERM: {
				UndefTerm undefTerm = (UndefTerm)theEObject;
				T result = caseUndefTerm(undefTerm);
				if (result == null) result = caseConstantTerm(undefTerm);
				if (result == null) result = caseBasicTerm(undefTerm);
				if (result == null) result = caseTerm(undefTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictermsPackage.TUPLE_TERM: {
				TupleTerm tupleTerm = (TupleTerm)theEObject;
				T result = caseTupleTerm(tupleTerm);
				if (result == null) result = caseExtendedTerm(tupleTerm);
				if (result == null) result = caseTerm(tupleTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictermsPackage.SET_TERM: {
				SetTerm setTerm = (SetTerm)theEObject;
				T result = caseSetTerm(setTerm);
				if (result == null) result = caseCollectionTerm(setTerm);
				if (result == null) result = caseExtendedTerm(setTerm);
				if (result == null) result = caseTerm(setTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictermsPackage.RULE_AS_TERM: {
				RuleAsTerm ruleAsTerm = (RuleAsTerm)theEObject;
				T result = caseRuleAsTerm(ruleAsTerm);
				if (result == null) result = caseExtendedTerm(ruleAsTerm);
				if (result == null) result = caseTerm(ruleAsTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictermsPackage.LOCATION_TERM: {
				LocationTerm locationTerm = (LocationTerm)theEObject;
				T result = caseLocationTerm(locationTerm);
				if (result == null) result = caseFunctionTerm(locationTerm);
				if (result == null) result = caseBasicTerm(locationTerm);
				if (result == null) result = caseTerm(locationTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictermsPackage.FUNCTION_TERM: {
				FunctionTerm functionTerm = (FunctionTerm)theEObject;
				T result = caseFunctionTerm(functionTerm);
				if (result == null) result = caseBasicTerm(functionTerm);
				if (result == null) result = caseTerm(functionTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictermsPackage.EXTENDED_TERM: {
				ExtendedTerm extendedTerm = (ExtendedTerm)theEObject;
				T result = caseExtendedTerm(extendedTerm);
				if (result == null) result = caseTerm(extendedTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictermsPackage.DOMAIN_TERM: {
				DomainTerm domainTerm = (DomainTerm)theEObject;
				T result = caseDomainTerm(domainTerm);
				if (result == null) result = caseExtendedTerm(domainTerm);
				if (result == null) result = caseTerm(domainTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictermsPackage.CONSTANT_TERM: {
				ConstantTerm constantTerm = (ConstantTerm)theEObject;
				T result = caseConstantTerm(constantTerm);
				if (result == null) result = caseBasicTerm(constantTerm);
				if (result == null) result = caseTerm(constantTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictermsPackage.COLLECTION_TERM: {
				CollectionTerm collectionTerm = (CollectionTerm)theEObject;
				T result = caseCollectionTerm(collectionTerm);
				if (result == null) result = caseExtendedTerm(collectionTerm);
				if (result == null) result = caseTerm(collectionTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictermsPackage.BOOLEAN_TERM: {
				BooleanTerm booleanTerm = (BooleanTerm)theEObject;
				T result = caseBooleanTerm(booleanTerm);
				if (result == null) result = caseConstantTerm(booleanTerm);
				if (result == null) result = caseBasicTerm(booleanTerm);
				if (result == null) result = caseTerm(booleanTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictermsPackage.BASIC_TERM: {
				BasicTerm basicTerm = (BasicTerm)theEObject;
				T result = caseBasicTerm(basicTerm);
				if (result == null) result = caseTerm(basicTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasictermsPackage.TERM: {
				Term term = (Term)theEObject;
				T result = caseTerm(term);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariableTerm(VariableTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Undef Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Undef Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUndefTerm(UndefTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tuple Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tuple Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTupleTerm(TupleTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Set Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Set Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSetTerm(SetTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule As Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule As Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleAsTerm(RuleAsTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Location Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Location Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocationTerm(LocationTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionTerm(FunctionTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Extended Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Extended Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtendedTerm(ExtendedTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Domain Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Domain Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDomainTerm(DomainTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Constant Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Constant Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConstantTerm(ConstantTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Collection Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Collection Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCollectionTerm(CollectionTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanTerm(BooleanTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Basic Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Basic Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBasicTerm(BasicTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTerm(Term object) {
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

} //BasictermsSwitch

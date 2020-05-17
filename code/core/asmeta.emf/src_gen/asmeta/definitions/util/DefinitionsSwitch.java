/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.util;

import asmeta.definitions.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import asmeta.definitions.BasicFunction;
import asmeta.definitions.Classifier;
import asmeta.definitions.CompassionConstraint;
import asmeta.definitions.ControlledFunction;
import asmeta.definitions.CtlSpec;
import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.FairnessConstraint;
import asmeta.definitions.Function;
import asmeta.definitions.InvarConstraint;
import asmeta.definitions.Invariant;
import asmeta.definitions.JusticeConstraint;
import asmeta.definitions.LocalFunction;
import asmeta.definitions.LtlSpec;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.Property;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.SharedFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.TemporalProperty;
import asmeta.structure.NamedElement;

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
 * @see asmeta.definitions.DefinitionsPackage
 * @generated
 */
public class DefinitionsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DefinitionsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefinitionsSwitch() {
		if (modelPackage == null) {
			modelPackage = DefinitionsPackage.eINSTANCE;
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
			case DefinitionsPackage.RULE_DECLARATION: {
				RuleDeclaration ruleDeclaration = (RuleDeclaration)theEObject;
				T result = caseRuleDeclaration(ruleDeclaration);
				if (result == null) result = caseClassifier(ruleDeclaration);
				if (result == null) result = caseNamedElement(ruleDeclaration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.LOCAL_FUNCTION: {
				LocalFunction localFunction = (LocalFunction)theEObject;
				T result = caseLocalFunction(localFunction);
				if (result == null) result = caseDynamicFunction(localFunction);
				if (result == null) result = caseBasicFunction(localFunction);
				if (result == null) result = caseFunction(localFunction);
				if (result == null) result = caseClassifier(localFunction);
				if (result == null) result = caseNamedElement(localFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.CONTROLLED_FUNCTION: {
				ControlledFunction controlledFunction = (ControlledFunction)theEObject;
				T result = caseControlledFunction(controlledFunction);
				if (result == null) result = caseDynamicFunction(controlledFunction);
				if (result == null) result = caseBasicFunction(controlledFunction);
				if (result == null) result = caseFunction(controlledFunction);
				if (result == null) result = caseClassifier(controlledFunction);
				if (result == null) result = caseNamedElement(controlledFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.SHARED_FUNCTION: {
				SharedFunction sharedFunction = (SharedFunction)theEObject;
				T result = caseSharedFunction(sharedFunction);
				if (result == null) result = caseDynamicFunction(sharedFunction);
				if (result == null) result = caseBasicFunction(sharedFunction);
				if (result == null) result = caseFunction(sharedFunction);
				if (result == null) result = caseClassifier(sharedFunction);
				if (result == null) result = caseNamedElement(sharedFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.MONITORED_FUNCTION: {
				MonitoredFunction monitoredFunction = (MonitoredFunction)theEObject;
				T result = caseMonitoredFunction(monitoredFunction);
				if (result == null) result = caseDynamicFunction(monitoredFunction);
				if (result == null) result = caseBasicFunction(monitoredFunction);
				if (result == null) result = caseFunction(monitoredFunction);
				if (result == null) result = caseClassifier(monitoredFunction);
				if (result == null) result = caseNamedElement(monitoredFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.OUT_FUNCTION: {
				OutFunction outFunction = (OutFunction)theEObject;
				T result = caseOutFunction(outFunction);
				if (result == null) result = caseDynamicFunction(outFunction);
				if (result == null) result = caseBasicFunction(outFunction);
				if (result == null) result = caseFunction(outFunction);
				if (result == null) result = caseClassifier(outFunction);
				if (result == null) result = caseNamedElement(outFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.DYNAMIC_FUNCTION: {
				DynamicFunction dynamicFunction = (DynamicFunction)theEObject;
				T result = caseDynamicFunction(dynamicFunction);
				if (result == null) result = caseBasicFunction(dynamicFunction);
				if (result == null) result = caseFunction(dynamicFunction);
				if (result == null) result = caseClassifier(dynamicFunction);
				if (result == null) result = caseNamedElement(dynamicFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.STATIC_FUNCTION: {
				StaticFunction staticFunction = (StaticFunction)theEObject;
				T result = caseStaticFunction(staticFunction);
				if (result == null) result = caseBasicFunction(staticFunction);
				if (result == null) result = caseFunction(staticFunction);
				if (result == null) result = caseClassifier(staticFunction);
				if (result == null) result = caseNamedElement(staticFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.DERIVED_FUNCTION: {
				DerivedFunction derivedFunction = (DerivedFunction)theEObject;
				T result = caseDerivedFunction(derivedFunction);
				if (result == null) result = caseFunction(derivedFunction);
				if (result == null) result = caseClassifier(derivedFunction);
				if (result == null) result = caseNamedElement(derivedFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.BASIC_FUNCTION: {
				BasicFunction basicFunction = (BasicFunction)theEObject;
				T result = caseBasicFunction(basicFunction);
				if (result == null) result = caseFunction(basicFunction);
				if (result == null) result = caseClassifier(basicFunction);
				if (result == null) result = caseNamedElement(basicFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.INVARIANT: {
				Invariant invariant = (Invariant)theEObject;
				T result = caseInvariant(invariant);
				if (result == null) result = caseProperty(invariant);
				if (result == null) result = caseClassifier(invariant);
				if (result == null) result = caseNamedElement(invariant);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.FUNCTION: {
				Function function = (Function)theEObject;
				T result = caseFunction(function);
				if (result == null) result = caseClassifier(function);
				if (result == null) result = caseNamedElement(function);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.CLASSIFIER: {
				Classifier classifier = (Classifier)theEObject;
				T result = caseClassifier(classifier);
				if (result == null) result = caseNamedElement(classifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.PROPERTY: {
				Property property = (Property)theEObject;
				T result = caseProperty(property);
				if (result == null) result = caseClassifier(property);
				if (result == null) result = caseNamedElement(property);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.TEMPORAL_PROPERTY: {
				TemporalProperty temporalProperty = (TemporalProperty)theEObject;
				T result = caseTemporalProperty(temporalProperty);
				if (result == null) result = caseProperty(temporalProperty);
				if (result == null) result = caseClassifier(temporalProperty);
				if (result == null) result = caseNamedElement(temporalProperty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.CTL_SPEC: {
				CtlSpec ctlSpec = (CtlSpec)theEObject;
				T result = caseCtlSpec(ctlSpec);
				if (result == null) result = caseTemporalProperty(ctlSpec);
				if (result == null) result = caseProperty(ctlSpec);
				if (result == null) result = caseClassifier(ctlSpec);
				if (result == null) result = caseNamedElement(ctlSpec);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.LTL_SPEC: {
				LtlSpec ltlSpec = (LtlSpec)theEObject;
				T result = caseLtlSpec(ltlSpec);
				if (result == null) result = caseTemporalProperty(ltlSpec);
				if (result == null) result = caseProperty(ltlSpec);
				if (result == null) result = caseClassifier(ltlSpec);
				if (result == null) result = caseNamedElement(ltlSpec);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.INVAR_CONSTRAINT: {
				InvarConstraint invarConstraint = (InvarConstraint)theEObject;
				T result = caseInvarConstraint(invarConstraint);
				if (result == null) result = caseClassifier(invarConstraint);
				if (result == null) result = caseNamedElement(invarConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.FAIRNESS_CONSTRAINT: {
				FairnessConstraint fairnessConstraint = (FairnessConstraint)theEObject;
				T result = caseFairnessConstraint(fairnessConstraint);
				if (result == null) result = caseClassifier(fairnessConstraint);
				if (result == null) result = caseNamedElement(fairnessConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.JUSTICE_CONSTRAINT: {
				JusticeConstraint justiceConstraint = (JusticeConstraint)theEObject;
				T result = caseJusticeConstraint(justiceConstraint);
				if (result == null) result = caseFairnessConstraint(justiceConstraint);
				if (result == null) result = caseClassifier(justiceConstraint);
				if (result == null) result = caseNamedElement(justiceConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DefinitionsPackage.COMPASSION_CONSTRAINT: {
				CompassionConstraint compassionConstraint = (CompassionConstraint)theEObject;
				T result = caseCompassionConstraint(compassionConstraint);
				if (result == null) result = caseFairnessConstraint(compassionConstraint);
				if (result == null) result = caseClassifier(compassionConstraint);
				if (result == null) result = caseNamedElement(compassionConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
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
	 * Returns the result of interpreting the object as an instance of '<em>Local Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Local Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocalFunction(LocalFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Controlled Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Controlled Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseControlledFunction(ControlledFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shared Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shared Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSharedFunction(SharedFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Monitored Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Monitored Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMonitoredFunction(MonitoredFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Out Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Out Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOutFunction(OutFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dynamic Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dynamic Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDynamicFunction(DynamicFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Static Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Static Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStaticFunction(StaticFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Derived Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Derived Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDerivedFunction(DerivedFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Basic Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Basic Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBasicFunction(BasicFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Invariant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Invariant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInvariant(Invariant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunction(Function object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProperty(Property object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Temporal Property</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Temporal Property</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTemporalProperty(TemporalProperty object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ctl Spec</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ctl Spec</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCtlSpec(CtlSpec object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ltl Spec</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ltl Spec</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLtlSpec(LtlSpec object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Invar Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Invar Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInvarConstraint(InvarConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Fairness Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Fairness Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFairnessConstraint(FairnessConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Justice Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Justice Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJusticeConstraint(JusticeConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Compassion Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Compassion Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompassionConstraint(CompassionConstraint object) {
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

} //DefinitionsSwitch

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.util;

import asmeta.definitions.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.definitions.DefinitionsPackage
 * @generated
 */
public class DefinitionsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DefinitionsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefinitionsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DefinitionsPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DefinitionsSwitch<Adapter> modelSwitch =
		new DefinitionsSwitch<Adapter>() {
			@Override
			public Adapter caseRuleDeclaration(RuleDeclaration object) {
				return createRuleDeclarationAdapter();
			}
			@Override
			public Adapter caseLocalFunction(LocalFunction object) {
				return createLocalFunctionAdapter();
			}
			@Override
			public Adapter caseControlledFunction(ControlledFunction object) {
				return createControlledFunctionAdapter();
			}
			@Override
			public Adapter caseSharedFunction(SharedFunction object) {
				return createSharedFunctionAdapter();
			}
			@Override
			public Adapter caseMonitoredFunction(MonitoredFunction object) {
				return createMonitoredFunctionAdapter();
			}
			@Override
			public Adapter caseOutFunction(OutFunction object) {
				return createOutFunctionAdapter();
			}
			@Override
			public Adapter caseDynamicFunction(DynamicFunction object) {
				return createDynamicFunctionAdapter();
			}
			@Override
			public Adapter caseStaticFunction(StaticFunction object) {
				return createStaticFunctionAdapter();
			}
			@Override
			public Adapter caseDerivedFunction(DerivedFunction object) {
				return createDerivedFunctionAdapter();
			}
			@Override
			public Adapter caseBasicFunction(BasicFunction object) {
				return createBasicFunctionAdapter();
			}
			@Override
			public Adapter caseInvariant(Invariant object) {
				return createInvariantAdapter();
			}
			@Override
			public Adapter caseFunction(Function object) {
				return createFunctionAdapter();
			}
			@Override
			public Adapter caseClassifier(Classifier object) {
				return createClassifierAdapter();
			}
			@Override
			public Adapter caseProperty(Property object) {
				return createPropertyAdapter();
			}
			@Override
			public Adapter caseTemporalProperty(TemporalProperty object) {
				return createTemporalPropertyAdapter();
			}
			@Override
			public Adapter caseCtlSpec(CtlSpec object) {
				return createCtlSpecAdapter();
			}
			@Override
			public Adapter caseLtlSpec(LtlSpec object) {
				return createLtlSpecAdapter();
			}
			@Override
			public Adapter caseInvarConstraint(InvarConstraint object) {
				return createInvarConstraintAdapter();
			}
			@Override
			public Adapter caseFairnessConstraint(FairnessConstraint object) {
				return createFairnessConstraintAdapter();
			}
			@Override
			public Adapter caseJusticeConstraint(JusticeConstraint object) {
				return createJusticeConstraintAdapter();
			}
			@Override
			public Adapter caseCompassionConstraint(CompassionConstraint object) {
				return createCompassionConstraintAdapter();
			}
			@Override
			public Adapter caseNamedElement(NamedElement object) {
				return createNamedElementAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.RuleDeclaration <em>Rule Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.RuleDeclaration
	 * @generated
	 */
	public Adapter createRuleDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.LocalFunction <em>Local Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.LocalFunction
	 * @generated
	 */
	public Adapter createLocalFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.ControlledFunction <em>Controlled Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.ControlledFunction
	 * @generated
	 */
	public Adapter createControlledFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.SharedFunction <em>Shared Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.SharedFunction
	 * @generated
	 */
	public Adapter createSharedFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.MonitoredFunction <em>Monitored Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.MonitoredFunction
	 * @generated
	 */
	public Adapter createMonitoredFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.OutFunction <em>Out Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.OutFunction
	 * @generated
	 */
	public Adapter createOutFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.DynamicFunction <em>Dynamic Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.DynamicFunction
	 * @generated
	 */
	public Adapter createDynamicFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.StaticFunction <em>Static Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.StaticFunction
	 * @generated
	 */
	public Adapter createStaticFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.DerivedFunction <em>Derived Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.DerivedFunction
	 * @generated
	 */
	public Adapter createDerivedFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.BasicFunction <em>Basic Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.BasicFunction
	 * @generated
	 */
	public Adapter createBasicFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.Invariant <em>Invariant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.Invariant
	 * @generated
	 */
	public Adapter createInvariantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.Function
	 * @generated
	 */
	public Adapter createFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.Classifier <em>Classifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.Classifier
	 * @generated
	 */
	public Adapter createClassifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.Property
	 * @generated
	 */
	public Adapter createPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.TemporalProperty <em>Temporal Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.TemporalProperty
	 * @generated
	 */
	public Adapter createTemporalPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.CtlSpec <em>Ctl Spec</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.CtlSpec
	 * @generated
	 */
	public Adapter createCtlSpecAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.LtlSpec <em>Ltl Spec</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.LtlSpec
	 * @generated
	 */
	public Adapter createLtlSpecAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.InvarConstraint <em>Invar Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.InvarConstraint
	 * @generated
	 */
	public Adapter createInvarConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.FairnessConstraint <em>Fairness Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.FairnessConstraint
	 * @generated
	 */
	public Adapter createFairnessConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.JusticeConstraint <em>Justice Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.JusticeConstraint
	 * @generated
	 */
	public Adapter createJusticeConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.CompassionConstraint <em>Compassion Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.CompassionConstraint
	 * @generated
	 */
	public Adapter createCompassionConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.structure.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.NamedElement
	 * @generated
	 */
	public Adapter createNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //DefinitionsAdapterFactory

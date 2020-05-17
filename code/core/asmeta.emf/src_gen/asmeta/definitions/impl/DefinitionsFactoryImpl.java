/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.impl;

import asmeta.definitions.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import asmeta.definitions.CompassionConstraint;
import asmeta.definitions.ControlledFunction;
import asmeta.definitions.CtlSpec;
import asmeta.definitions.DefinitionsFactory;
import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.InvarConstraint;
import asmeta.definitions.Invariant;
import asmeta.definitions.JusticeConstraint;
import asmeta.definitions.LocalFunction;
import asmeta.definitions.LtlSpec;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.Property;
import asmeta.definitions.SharedFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.DomainsFactory;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DefinitionsFactoryImpl extends EFactoryImpl implements DefinitionsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DefinitionsFactory init() {
		try {
			DefinitionsFactory theDefinitionsFactory = (DefinitionsFactory)EPackage.Registry.INSTANCE.getEFactory(DefinitionsPackage.eNS_URI);
			if (theDefinitionsFactory != null) {
				return theDefinitionsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DefinitionsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefinitionsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DefinitionsPackage.LOCAL_FUNCTION: return createLocalFunction();
			case DefinitionsPackage.CONTROLLED_FUNCTION: return createControlledFunction();
			case DefinitionsPackage.SHARED_FUNCTION: return createSharedFunction();
			case DefinitionsPackage.MONITORED_FUNCTION: return createMonitoredFunction();
			case DefinitionsPackage.OUT_FUNCTION: return createOutFunction();
			case DefinitionsPackage.STATIC_FUNCTION: return createStaticFunction();
			case DefinitionsPackage.DERIVED_FUNCTION: return createDerivedFunction();
			case DefinitionsPackage.INVARIANT: return createInvariant();
			case DefinitionsPackage.PROPERTY: return createProperty();
			case DefinitionsPackage.CTL_SPEC: return createCtlSpec();
			case DefinitionsPackage.LTL_SPEC: return createLtlSpec();
			case DefinitionsPackage.INVAR_CONSTRAINT: return createInvarConstraint();
			case DefinitionsPackage.JUSTICE_CONSTRAINT: return createJusticeConstraint();
			case DefinitionsPackage.COMPASSION_CONSTRAINT: return createCompassionConstraint();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LocalFunction createLocalFunction() {
		LocalFunctionImpl localFunction = new LocalFunctionImpl();
		return localFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ControlledFunction createControlledFunction() {
		ControlledFunctionImpl controlledFunction = new ControlledFunctionImpl();
		return controlledFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SharedFunction createSharedFunction() {
		SharedFunctionImpl sharedFunction = new SharedFunctionImpl();
		return sharedFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MonitoredFunction createMonitoredFunction() {
		MonitoredFunctionImpl monitoredFunction = new MonitoredFunctionImpl();
		return monitoredFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OutFunction createOutFunction() {
		OutFunctionImpl outFunction = new OutFunctionImpl();
		return outFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StaticFunction createStaticFunction() {
		StaticFunctionImpl staticFunction = new StaticFunctionImpl();
		return staticFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DerivedFunction createDerivedFunction() {
		DerivedFunctionImpl derivedFunction = new DerivedFunctionImpl();
		return derivedFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Invariant createInvariant() {
		InvariantImpl invariant = new InvariantImpl();
		return invariant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Property createProperty() {
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CtlSpec createCtlSpec() {
		CtlSpecImpl ctlSpec = new CtlSpecImpl();
		return ctlSpec;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LtlSpec createLtlSpec() {
		LtlSpecImpl ltlSpec = new LtlSpecImpl();
		return ltlSpec;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InvarConstraint createInvarConstraint() {
		InvarConstraintImpl invarConstraint = new InvarConstraintImpl();
		return invarConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public JusticeConstraint createJusticeConstraint() {
		JusticeConstraintImpl justiceConstraint = new JusticeConstraintImpl();
		return justiceConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompassionConstraint createCompassionConstraint() {
		CompassionConstraintImpl compassionConstraint = new CompassionConstraintImpl();
		return compassionConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DefinitionsPackage getDefinitionsPackage() {
		return (DefinitionsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DefinitionsPackage getPackage() {
		return DefinitionsPackage.eINSTANCE;
	}

	/** scritto da AG */
	@Override
	public DomainsFactory getDomains() {
		return DomainsFactory.eINSTANCE;	
	}

} //DefinitionsFactoryImpl

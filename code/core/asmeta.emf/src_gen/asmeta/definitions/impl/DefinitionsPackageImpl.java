/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import asmeta.definitions.BasicFunction;
import asmeta.definitions.Classifier;
import asmeta.definitions.CompassionConstraint;
import asmeta.definitions.ControlledFunction;
import asmeta.definitions.CtlSpec;
import asmeta.definitions.DefinitionsFactory;
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
import asmeta.definitions.domains.DomainsPackage;
import asmeta.definitions.domains.impl.DomainsPackageImpl;
import asmeta.structure.StructurePackage;
import asmeta.structure.impl.StructurePackageImpl;
import asmeta.terms.basicterms.BasictermsPackage;
import asmeta.terms.basicterms.impl.BasictermsPackageImpl;
import asmeta.terms.furtherterms.FurthertermsPackage;
import asmeta.terms.furtherterms.impl.FurthertermsPackageImpl;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage;
import asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl;
import asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage;
import asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage;
import asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl;
import primitivetypes.PrimitivetypesPackage;
import primitivetypes.impl.PrimitivetypesPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DefinitionsPackageImpl extends EPackageImpl implements DefinitionsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass localFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass controlledFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass monitoredFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass outFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dynamicFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass staticFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass derivedFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass basicFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass invariantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass temporalPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ctlSpecEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ltlSpecEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass invarConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fairnessConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass justiceConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compassionConstraintEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see asmeta.definitions.DefinitionsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DefinitionsPackageImpl() {
		super(eNS_URI, DefinitionsFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link DefinitionsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DefinitionsPackage init() {
		if (isInited) return (DefinitionsPackage)EPackage.Registry.INSTANCE.getEPackage(DefinitionsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredDefinitionsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		DefinitionsPackageImpl theDefinitionsPackage = registeredDefinitionsPackage instanceof DefinitionsPackageImpl ? (DefinitionsPackageImpl)registeredDefinitionsPackage : new DefinitionsPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(FurthertermsPackage.eNS_URI);
		FurthertermsPackageImpl theFurthertermsPackage = (FurthertermsPackageImpl)(registeredPackage instanceof FurthertermsPackageImpl ? registeredPackage : FurthertermsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(BasictermsPackage.eNS_URI);
		BasictermsPackageImpl theBasictermsPackage = (BasictermsPackageImpl)(registeredPackage instanceof BasictermsPackageImpl ? registeredPackage : BasictermsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(StructurePackage.eNS_URI);
		StructurePackageImpl theStructurePackage = (StructurePackageImpl)(registeredPackage instanceof StructurePackageImpl ? registeredPackage : StructurePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(TurbotransitionrulesPackage.eNS_URI);
		TurbotransitionrulesPackageImpl theTurbotransitionrulesPackage = (TurbotransitionrulesPackageImpl)(registeredPackage instanceof TurbotransitionrulesPackageImpl ? registeredPackage : TurbotransitionrulesPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DerivedtransitionrulesPackage.eNS_URI);
		DerivedtransitionrulesPackageImpl theDerivedtransitionrulesPackage = (DerivedtransitionrulesPackageImpl)(registeredPackage instanceof DerivedtransitionrulesPackageImpl ? registeredPackage : DerivedtransitionrulesPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(BasictransitionrulesPackage.eNS_URI);
		BasictransitionrulesPackageImpl theBasictransitionrulesPackage = (BasictransitionrulesPackageImpl)(registeredPackage instanceof BasictransitionrulesPackageImpl ? registeredPackage : BasictransitionrulesPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DomainsPackage.eNS_URI);
		DomainsPackageImpl theDomainsPackage = (DomainsPackageImpl)(registeredPackage instanceof DomainsPackageImpl ? registeredPackage : DomainsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(PrimitivetypesPackage.eNS_URI);
		PrimitivetypesPackageImpl thePrimitivetypesPackage = (PrimitivetypesPackageImpl)(registeredPackage instanceof PrimitivetypesPackageImpl ? registeredPackage : PrimitivetypesPackage.eINSTANCE);

		// Create package meta-data objects
		theDefinitionsPackage.createPackageContents();
		theFurthertermsPackage.createPackageContents();
		theBasictermsPackage.createPackageContents();
		theStructurePackage.createPackageContents();
		theTurbotransitionrulesPackage.createPackageContents();
		theDerivedtransitionrulesPackage.createPackageContents();
		theBasictransitionrulesPackage.createPackageContents();
		theDomainsPackage.createPackageContents();
		thePrimitivetypesPackage.createPackageContents();

		// Initialize created meta-data
		theDefinitionsPackage.initializePackageContents();
		theFurthertermsPackage.initializePackageContents();
		theBasictermsPackage.initializePackageContents();
		theStructurePackage.initializePackageContents();
		theTurbotransitionrulesPackage.initializePackageContents();
		theDerivedtransitionrulesPackage.initializePackageContents();
		theBasictransitionrulesPackage.initializePackageContents();
		theDomainsPackage.initializePackageContents();
		thePrimitivetypesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDefinitionsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DefinitionsPackage.eNS_URI, theDefinitionsPackage);
		return theDefinitionsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRuleDeclaration() {
		return ruleDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleDeclaration_Variable() {
		return (EReference)ruleDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleDeclaration_Constraint() {
		return (EReference)ruleDeclarationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleDeclaration_RuleBody() {
		return (EReference)ruleDeclarationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleDeclaration_AsmBody() {
		return (EReference)ruleDeclarationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRuleDeclaration_Arity() {
		return (EAttribute)ruleDeclarationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLocalFunction() {
		return localFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getControlledFunction() {
		return controlledFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSharedFunction() {
		return sharedFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMonitoredFunction() {
		return monitoredFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOutFunction() {
		return outFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDynamicFunction() {
		return dynamicFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDynamicFunction_Initialization() {
		return (EReference)dynamicFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStaticFunction() {
		return staticFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDerivedFunction() {
		return derivedFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBasicFunction() {
		return basicFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInvariant() {
		return invariantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInvariant_ConstrainedDomain() {
		return (EReference)invariantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInvariant_ConstrainedRule() {
		return (EReference)invariantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInvariant_ConstrainedFunction() {
		return (EReference)invariantEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInvariant_Body() {
		return (EReference)invariantEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFunction() {
		return functionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunction_Domain() {
		return (EReference)functionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunction_Codomain() {
		return (EReference)functionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunction_Definition() {
		return (EReference)functionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunction_Constraint() {
		return (EReference)functionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFunction_Arity() {
		return (EAttribute)functionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunction_Signature() {
		return (EReference)functionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getClassifier() {
		return classifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getProperty() {
		return propertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTemporalProperty() {
		return temporalPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTemporalProperty_Body() {
		return (EReference)temporalPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCtlSpec() {
		return ctlSpecEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLtlSpec() {
		return ltlSpecEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInvarConstraint() {
		return invarConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInvarConstraint_Body() {
		return (EReference)invarConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFairnessConstraint() {
		return fairnessConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getJusticeConstraint() {
		return justiceConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getJusticeConstraint_Body() {
		return (EReference)justiceConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCompassionConstraint() {
		return compassionConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompassionConstraint_P() {
		return (EReference)compassionConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompassionConstraint_Q() {
		return (EReference)compassionConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DefinitionsFactory getDefinitionsFactory() {
		return (DefinitionsFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		ruleDeclarationEClass = createEClass(RULE_DECLARATION);
		createEReference(ruleDeclarationEClass, RULE_DECLARATION__VARIABLE);
		createEReference(ruleDeclarationEClass, RULE_DECLARATION__CONSTRAINT);
		createEReference(ruleDeclarationEClass, RULE_DECLARATION__RULE_BODY);
		createEReference(ruleDeclarationEClass, RULE_DECLARATION__ASM_BODY);
		createEAttribute(ruleDeclarationEClass, RULE_DECLARATION__ARITY);

		localFunctionEClass = createEClass(LOCAL_FUNCTION);

		controlledFunctionEClass = createEClass(CONTROLLED_FUNCTION);

		sharedFunctionEClass = createEClass(SHARED_FUNCTION);

		monitoredFunctionEClass = createEClass(MONITORED_FUNCTION);

		outFunctionEClass = createEClass(OUT_FUNCTION);

		dynamicFunctionEClass = createEClass(DYNAMIC_FUNCTION);
		createEReference(dynamicFunctionEClass, DYNAMIC_FUNCTION__INITIALIZATION);

		staticFunctionEClass = createEClass(STATIC_FUNCTION);

		derivedFunctionEClass = createEClass(DERIVED_FUNCTION);

		basicFunctionEClass = createEClass(BASIC_FUNCTION);

		invariantEClass = createEClass(INVARIANT);
		createEReference(invariantEClass, INVARIANT__CONSTRAINED_DOMAIN);
		createEReference(invariantEClass, INVARIANT__CONSTRAINED_RULE);
		createEReference(invariantEClass, INVARIANT__CONSTRAINED_FUNCTION);
		createEReference(invariantEClass, INVARIANT__BODY);

		functionEClass = createEClass(FUNCTION);
		createEReference(functionEClass, FUNCTION__DOMAIN);
		createEReference(functionEClass, FUNCTION__CODOMAIN);
		createEReference(functionEClass, FUNCTION__DEFINITION);
		createEReference(functionEClass, FUNCTION__CONSTRAINT);
		createEAttribute(functionEClass, FUNCTION__ARITY);
		createEReference(functionEClass, FUNCTION__SIGNATURE);

		classifierEClass = createEClass(CLASSIFIER);

		propertyEClass = createEClass(PROPERTY);

		temporalPropertyEClass = createEClass(TEMPORAL_PROPERTY);
		createEReference(temporalPropertyEClass, TEMPORAL_PROPERTY__BODY);

		ctlSpecEClass = createEClass(CTL_SPEC);

		ltlSpecEClass = createEClass(LTL_SPEC);

		invarConstraintEClass = createEClass(INVAR_CONSTRAINT);
		createEReference(invarConstraintEClass, INVAR_CONSTRAINT__BODY);

		fairnessConstraintEClass = createEClass(FAIRNESS_CONSTRAINT);

		justiceConstraintEClass = createEClass(JUSTICE_CONSTRAINT);
		createEReference(justiceConstraintEClass, JUSTICE_CONSTRAINT__BODY);

		compassionConstraintEClass = createEClass(COMPASSION_CONSTRAINT);
		createEReference(compassionConstraintEClass, COMPASSION_CONSTRAINT__P);
		createEReference(compassionConstraintEClass, COMPASSION_CONSTRAINT__Q);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		DomainsPackage theDomainsPackage = (DomainsPackage)EPackage.Registry.INSTANCE.getEPackage(DomainsPackage.eNS_URI);
		BasictermsPackage theBasictermsPackage = (BasictermsPackage)EPackage.Registry.INSTANCE.getEPackage(BasictermsPackage.eNS_URI);
		BasictransitionrulesPackage theBasictransitionrulesPackage = (BasictransitionrulesPackage)EPackage.Registry.INSTANCE.getEPackage(BasictransitionrulesPackage.eNS_URI);
		StructurePackage theStructurePackage = (StructurePackage)EPackage.Registry.INSTANCE.getEPackage(StructurePackage.eNS_URI);
		PrimitivetypesPackage thePrimitivetypesPackage = (PrimitivetypesPackage)EPackage.Registry.INSTANCE.getEPackage(PrimitivetypesPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theDomainsPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		ruleDeclarationEClass.getESuperTypes().add(this.getClassifier());
		localFunctionEClass.getESuperTypes().add(this.getDynamicFunction());
		controlledFunctionEClass.getESuperTypes().add(this.getDynamicFunction());
		sharedFunctionEClass.getESuperTypes().add(this.getDynamicFunction());
		monitoredFunctionEClass.getESuperTypes().add(this.getDynamicFunction());
		outFunctionEClass.getESuperTypes().add(this.getDynamicFunction());
		dynamicFunctionEClass.getESuperTypes().add(this.getBasicFunction());
		staticFunctionEClass.getESuperTypes().add(this.getBasicFunction());
		derivedFunctionEClass.getESuperTypes().add(this.getFunction());
		basicFunctionEClass.getESuperTypes().add(this.getFunction());
		invariantEClass.getESuperTypes().add(this.getProperty());
		functionEClass.getESuperTypes().add(this.getClassifier());
		classifierEClass.getESuperTypes().add(theStructurePackage.getNamedElement());
		propertyEClass.getESuperTypes().add(this.getClassifier());
		temporalPropertyEClass.getESuperTypes().add(this.getProperty());
		ctlSpecEClass.getESuperTypes().add(this.getTemporalProperty());
		ltlSpecEClass.getESuperTypes().add(this.getTemporalProperty());
		invarConstraintEClass.getESuperTypes().add(this.getClassifier());
		fairnessConstraintEClass.getESuperTypes().add(this.getClassifier());
		justiceConstraintEClass.getESuperTypes().add(this.getFairnessConstraint());
		compassionConstraintEClass.getESuperTypes().add(this.getFairnessConstraint());

		// Initialize classes and features; add operations and parameters
		initEClass(ruleDeclarationEClass, RuleDeclaration.class, "RuleDeclaration", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleDeclaration_Variable(), theBasictermsPackage.getVariableTerm(), null, "variable", null, 0, -1, RuleDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleDeclaration_Constraint(), this.getInvariant(), this.getInvariant_ConstrainedRule(), "constraint", null, 0, -1, RuleDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getRuleDeclaration_RuleBody(), theBasictransitionrulesPackage.getRule(), null, "ruleBody", null, 1, 1, RuleDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getRuleDeclaration_AsmBody(), theStructurePackage.getBody(), theStructurePackage.getBody_RuleDeclaration(), "asmBody", null, 1, 1, RuleDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getRuleDeclaration_Arity(), thePrimitivetypesPackage.getInteger(), "arity", null, 1, 1, RuleDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(localFunctionEClass, LocalFunction.class, "LocalFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(controlledFunctionEClass, ControlledFunction.class, "ControlledFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(sharedFunctionEClass, SharedFunction.class, "SharedFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(monitoredFunctionEClass, MonitoredFunction.class, "MonitoredFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(outFunctionEClass, OutFunction.class, "OutFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(dynamicFunctionEClass, DynamicFunction.class, "DynamicFunction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDynamicFunction_Initialization(), theStructurePackage.getFunctionInitialization(), theStructurePackage.getFunctionInitialization_InitializedFunction(), "initialization", null, 0, -1, DynamicFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(staticFunctionEClass, StaticFunction.class, "StaticFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(derivedFunctionEClass, DerivedFunction.class, "DerivedFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(basicFunctionEClass, BasicFunction.class, "BasicFunction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(invariantEClass, Invariant.class, "Invariant", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInvariant_ConstrainedDomain(), theDomainsPackage.getDomain(), theDomainsPackage.getDomain_Constraint(), "constrainedDomain", null, 0, -1, Invariant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getInvariant_ConstrainedRule(), this.getRuleDeclaration(), this.getRuleDeclaration_Constraint(), "constrainedRule", null, 0, -1, Invariant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getInvariant_ConstrainedFunction(), this.getFunction(), this.getFunction_Constraint(), "constrainedFunction", null, 0, -1, Invariant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getInvariant_Body(), theBasictermsPackage.getTerm(), null, "body", null, 1, 1, Invariant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(functionEClass, Function.class, "Function", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFunction_Domain(), theDomainsPackage.getDomain(), null, "domain", null, 0, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getFunction_Codomain(), theDomainsPackage.getDomain(), null, "codomain", null, 1, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getFunction_Definition(), theStructurePackage.getFunctionDefinition(), theStructurePackage.getFunctionDefinition_DefinedFunction(), "definition", null, 0, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getFunction_Constraint(), this.getInvariant(), this.getInvariant_ConstrainedFunction(), "constraint", null, 0, -1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getFunction_Arity(), thePrimitivetypesPackage.getInteger(), "arity", null, 1, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getFunction_Signature(), theStructurePackage.getSignature(), theStructurePackage.getSignature_Function(), "signature", null, 0, 1, Function.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(classifierEClass, Classifier.class, "Classifier", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(temporalPropertyEClass, TemporalProperty.class, "TemporalProperty", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTemporalProperty_Body(), theBasictermsPackage.getTerm(), null, "body", null, 1, 1, TemporalProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(ctlSpecEClass, CtlSpec.class, "CtlSpec", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(ltlSpecEClass, LtlSpec.class, "LtlSpec", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(invarConstraintEClass, InvarConstraint.class, "InvarConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInvarConstraint_Body(), theBasictermsPackage.getTerm(), null, "body", null, 1, 1, InvarConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(fairnessConstraintEClass, FairnessConstraint.class, "FairnessConstraint", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(justiceConstraintEClass, JusticeConstraint.class, "JusticeConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJusticeConstraint_Body(), theBasictermsPackage.getTerm(), null, "body", null, 1, 1, JusticeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(compassionConstraintEClass, CompassionConstraint.class, "CompassionConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompassionConstraint_P(), theBasictermsPackage.getTerm(), null, "p", null, 1, 1, CompassionConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCompassionConstraint_Q(), theBasictermsPackage.getTerm(), null, "q", null, 1, 1, CompassionConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //DefinitionsPackageImpl

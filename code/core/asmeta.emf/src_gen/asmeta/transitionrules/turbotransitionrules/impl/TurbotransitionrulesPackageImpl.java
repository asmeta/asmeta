/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.domains.DomainsPackage;
import asmeta.definitions.domains.impl.DomainsPackageImpl;
import asmeta.definitions.impl.DefinitionsPackageImpl;
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
import asmeta.transitionrules.turbotransitionrules.IterateRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.transitionrules.turbotransitionrules.TryCatchRule;
import asmeta.transitionrules.turbotransitionrules.TurboCallRule;
import asmeta.transitionrules.turbotransitionrules.TurboDeclaration;
import asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule;
import asmeta.transitionrules.turbotransitionrules.TurboReturnRule;
import asmeta.transitionrules.turbotransitionrules.TurboRule;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesFactory;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage;
import primitivetypes.PrimitivetypesPackage;
import primitivetypes.impl.PrimitivetypesPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TurbotransitionrulesPackageImpl extends EPackageImpl implements TurbotransitionrulesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass turboRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass turboDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass seqRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass turboLocalStateRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass turboCallRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass turboReturnRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tryCatchRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iterateRuleEClass = null;

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
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TurbotransitionrulesPackageImpl() {
		super(eNS_URI, TurbotransitionrulesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link TurbotransitionrulesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TurbotransitionrulesPackage init() {
		if (isInited) return (TurbotransitionrulesPackage)EPackage.Registry.INSTANCE.getEPackage(TurbotransitionrulesPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTurbotransitionrulesPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		TurbotransitionrulesPackageImpl theTurbotransitionrulesPackage = registeredTurbotransitionrulesPackage instanceof TurbotransitionrulesPackageImpl ? (TurbotransitionrulesPackageImpl)registeredTurbotransitionrulesPackage : new TurbotransitionrulesPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(FurthertermsPackage.eNS_URI);
		FurthertermsPackageImpl theFurthertermsPackage = (FurthertermsPackageImpl)(registeredPackage instanceof FurthertermsPackageImpl ? registeredPackage : FurthertermsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(BasictermsPackage.eNS_URI);
		BasictermsPackageImpl theBasictermsPackage = (BasictermsPackageImpl)(registeredPackage instanceof BasictermsPackageImpl ? registeredPackage : BasictermsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(StructurePackage.eNS_URI);
		StructurePackageImpl theStructurePackage = (StructurePackageImpl)(registeredPackage instanceof StructurePackageImpl ? registeredPackage : StructurePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DerivedtransitionrulesPackage.eNS_URI);
		DerivedtransitionrulesPackageImpl theDerivedtransitionrulesPackage = (DerivedtransitionrulesPackageImpl)(registeredPackage instanceof DerivedtransitionrulesPackageImpl ? registeredPackage : DerivedtransitionrulesPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(BasictransitionrulesPackage.eNS_URI);
		BasictransitionrulesPackageImpl theBasictransitionrulesPackage = (BasictransitionrulesPackageImpl)(registeredPackage instanceof BasictransitionrulesPackageImpl ? registeredPackage : BasictransitionrulesPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DefinitionsPackage.eNS_URI);
		DefinitionsPackageImpl theDefinitionsPackage = (DefinitionsPackageImpl)(registeredPackage instanceof DefinitionsPackageImpl ? registeredPackage : DefinitionsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DomainsPackage.eNS_URI);
		DomainsPackageImpl theDomainsPackage = (DomainsPackageImpl)(registeredPackage instanceof DomainsPackageImpl ? registeredPackage : DomainsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(PrimitivetypesPackage.eNS_URI);
		PrimitivetypesPackageImpl thePrimitivetypesPackage = (PrimitivetypesPackageImpl)(registeredPackage instanceof PrimitivetypesPackageImpl ? registeredPackage : PrimitivetypesPackage.eINSTANCE);

		// Create package meta-data objects
		theTurbotransitionrulesPackage.createPackageContents();
		theFurthertermsPackage.createPackageContents();
		theBasictermsPackage.createPackageContents();
		theStructurePackage.createPackageContents();
		theDerivedtransitionrulesPackage.createPackageContents();
		theBasictransitionrulesPackage.createPackageContents();
		theDefinitionsPackage.createPackageContents();
		theDomainsPackage.createPackageContents();
		thePrimitivetypesPackage.createPackageContents();

		// Initialize created meta-data
		theTurbotransitionrulesPackage.initializePackageContents();
		theFurthertermsPackage.initializePackageContents();
		theBasictermsPackage.initializePackageContents();
		theStructurePackage.initializePackageContents();
		theDerivedtransitionrulesPackage.initializePackageContents();
		theBasictransitionrulesPackage.initializePackageContents();
		theDefinitionsPackage.initializePackageContents();
		theDomainsPackage.initializePackageContents();
		thePrimitivetypesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTurbotransitionrulesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TurbotransitionrulesPackage.eNS_URI, theTurbotransitionrulesPackage);
		return theTurbotransitionrulesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTurboRule() {
		return turboRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTurboDeclaration() {
		return turboDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTurboDeclaration_ResultType() {
		return (EReference)turboDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSeqRule() {
		return seqRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSeqRule_Rules() {
		return (EAttribute)seqRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTurboLocalStateRule() {
		return turboLocalStateRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTurboLocalStateRule_Init() {
		return (EReference)turboLocalStateRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTurboLocalStateRule_Body() {
		return (EReference)turboLocalStateRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTurboLocalStateRule_LocalFunction() {
		return (EReference)turboLocalStateRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTurboCallRule() {
		return turboCallRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTurboCallRule_CalledRule() {
		return (EReference)turboCallRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTurboCallRule_Parameters() {
		return (EAttribute)turboCallRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTurboReturnRule() {
		return turboReturnRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTurboReturnRule_Location() {
		return (EReference)turboReturnRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTurboReturnRule_UpdateRule() {
		return (EReference)turboReturnRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTryCatchRule() {
		return tryCatchRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTryCatchRule_Location() {
		return (EReference)tryCatchRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTryCatchRule_CatchRule() {
		return (EReference)tryCatchRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTryCatchRule_TryRule() {
		return (EReference)tryCatchRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIterateRule() {
		return iterateRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIterateRule_Rule() {
		return (EReference)iterateRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TurbotransitionrulesFactory getTurbotransitionrulesFactory() {
		return (TurbotransitionrulesFactory)getEFactoryInstance();
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
		turboRuleEClass = createEClass(TURBO_RULE);

		turboDeclarationEClass = createEClass(TURBO_DECLARATION);
		createEReference(turboDeclarationEClass, TURBO_DECLARATION__RESULT_TYPE);

		seqRuleEClass = createEClass(SEQ_RULE);
		createEAttribute(seqRuleEClass, SEQ_RULE__RULES);

		turboLocalStateRuleEClass = createEClass(TURBO_LOCAL_STATE_RULE);
		createEReference(turboLocalStateRuleEClass, TURBO_LOCAL_STATE_RULE__INIT);
		createEReference(turboLocalStateRuleEClass, TURBO_LOCAL_STATE_RULE__BODY);
		createEReference(turboLocalStateRuleEClass, TURBO_LOCAL_STATE_RULE__LOCAL_FUNCTION);

		turboCallRuleEClass = createEClass(TURBO_CALL_RULE);
		createEReference(turboCallRuleEClass, TURBO_CALL_RULE__CALLED_RULE);
		createEAttribute(turboCallRuleEClass, TURBO_CALL_RULE__PARAMETERS);

		turboReturnRuleEClass = createEClass(TURBO_RETURN_RULE);
		createEReference(turboReturnRuleEClass, TURBO_RETURN_RULE__LOCATION);
		createEReference(turboReturnRuleEClass, TURBO_RETURN_RULE__UPDATE_RULE);

		tryCatchRuleEClass = createEClass(TRY_CATCH_RULE);
		createEReference(tryCatchRuleEClass, TRY_CATCH_RULE__LOCATION);
		createEReference(tryCatchRuleEClass, TRY_CATCH_RULE__CATCH_RULE);
		createEReference(tryCatchRuleEClass, TRY_CATCH_RULE__TRY_RULE);

		iterateRuleEClass = createEClass(ITERATE_RULE);
		createEReference(iterateRuleEClass, ITERATE_RULE__RULE);
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
		BasictransitionrulesPackage theBasictransitionrulesPackage = (BasictransitionrulesPackage)EPackage.Registry.INSTANCE.getEPackage(BasictransitionrulesPackage.eNS_URI);
		DefinitionsPackage theDefinitionsPackage = (DefinitionsPackage)EPackage.Registry.INSTANCE.getEPackage(DefinitionsPackage.eNS_URI);
		DomainsPackage theDomainsPackage = (DomainsPackage)EPackage.Registry.INSTANCE.getEPackage(DomainsPackage.eNS_URI);
		BasictermsPackage theBasictermsPackage = (BasictermsPackage)EPackage.Registry.INSTANCE.getEPackage(BasictermsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		turboRuleEClass.getESuperTypes().add(theBasictransitionrulesPackage.getRule());
		turboDeclarationEClass.getESuperTypes().add(theDefinitionsPackage.getRuleDeclaration());
		seqRuleEClass.getESuperTypes().add(this.getTurboRule());
		turboLocalStateRuleEClass.getESuperTypes().add(this.getTurboRule());
		turboCallRuleEClass.getESuperTypes().add(this.getTurboRule());
		turboReturnRuleEClass.getESuperTypes().add(this.getTurboRule());
		tryCatchRuleEClass.getESuperTypes().add(this.getTurboRule());
		iterateRuleEClass.getESuperTypes().add(this.getTurboRule());

		// Initialize classes and features; add operations and parameters
		initEClass(turboRuleEClass, TurboRule.class, "TurboRule", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(turboDeclarationEClass, TurboDeclaration.class, "TurboDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTurboDeclaration_ResultType(), theDomainsPackage.getDomain(), null, "resultType", null, 0, 1, TurboDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(seqRuleEClass, SeqRule.class, "SeqRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSeqRule_Rules(), theBasictransitionrulesPackage.getRuleDT(), "rules", null, 2, -1, SeqRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(turboLocalStateRuleEClass, TurboLocalStateRule.class, "TurboLocalStateRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTurboLocalStateRule_Init(), theBasictransitionrulesPackage.getRule(), null, "init", null, 1, -1, TurboLocalStateRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTurboLocalStateRule_Body(), theBasictransitionrulesPackage.getRule(), null, "body", null, 1, 1, TurboLocalStateRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getTurboLocalStateRule_LocalFunction(), theDefinitionsPackage.getLocalFunction(), null, "localFunction", null, 1, -1, TurboLocalStateRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(turboCallRuleEClass, TurboCallRule.class, "TurboCallRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTurboCallRule_CalledRule(), this.getTurboDeclaration(), null, "calledRule", null, 1, 1, TurboCallRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getTurboCallRule_Parameters(), theBasictermsPackage.getTermDT(), "parameters", null, 0, -1, TurboCallRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(turboReturnRuleEClass, TurboReturnRule.class, "TurboReturnRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTurboReturnRule_Location(), theBasictermsPackage.getTerm(), null, "location", null, 1, 1, TurboReturnRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getTurboReturnRule_UpdateRule(), this.getTurboCallRule(), null, "updateRule", null, 1, 1, TurboReturnRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(tryCatchRuleEClass, TryCatchRule.class, "TryCatchRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTryCatchRule_Location(), theBasictermsPackage.getTerm(), null, "location", null, 1, -1, TryCatchRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getTryCatchRule_CatchRule(), theBasictransitionrulesPackage.getRule(), null, "catchRule", null, 1, 1, TryCatchRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getTryCatchRule_TryRule(), theBasictransitionrulesPackage.getRule(), null, "tryRule", null, 1, 1, TryCatchRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(iterateRuleEClass, IterateRule.class, "IterateRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIterateRule_Rule(), theBasictransitionrulesPackage.getRule(), null, "rule", null, 1, 1, IterateRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //TurbotransitionrulesPackageImpl

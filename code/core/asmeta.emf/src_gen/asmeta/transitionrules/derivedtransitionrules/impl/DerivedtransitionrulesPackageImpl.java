/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.derivedtransitionrules.impl;

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
import asmeta.transitionrules.derivedtransitionrules.BasicDerivedRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.derivedtransitionrules.DerivedRule;
import asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesFactory;
import asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage;
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule;
import asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule;
import asmeta.transitionrules.derivedtransitionrules.TurboDerivedRule;
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
public class DerivedtransitionrulesPackageImpl extends EPackageImpl implements DerivedtransitionrulesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass recursiveWhileRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iterativeWhileRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass derivedRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass caseRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass basicDerivedRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass turboDerivedRuleEClass = null;

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
	 * @see asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DerivedtransitionrulesPackageImpl() {
		super(eNS_URI, DerivedtransitionrulesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link DerivedtransitionrulesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DerivedtransitionrulesPackage init() {
		if (isInited) return (DerivedtransitionrulesPackage)EPackage.Registry.INSTANCE.getEPackage(DerivedtransitionrulesPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredDerivedtransitionrulesPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		DerivedtransitionrulesPackageImpl theDerivedtransitionrulesPackage = registeredDerivedtransitionrulesPackage instanceof DerivedtransitionrulesPackageImpl ? (DerivedtransitionrulesPackageImpl)registeredDerivedtransitionrulesPackage : new DerivedtransitionrulesPackageImpl();

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
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(BasictransitionrulesPackage.eNS_URI);
		BasictransitionrulesPackageImpl theBasictransitionrulesPackage = (BasictransitionrulesPackageImpl)(registeredPackage instanceof BasictransitionrulesPackageImpl ? registeredPackage : BasictransitionrulesPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DefinitionsPackage.eNS_URI);
		DefinitionsPackageImpl theDefinitionsPackage = (DefinitionsPackageImpl)(registeredPackage instanceof DefinitionsPackageImpl ? registeredPackage : DefinitionsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DomainsPackage.eNS_URI);
		DomainsPackageImpl theDomainsPackage = (DomainsPackageImpl)(registeredPackage instanceof DomainsPackageImpl ? registeredPackage : DomainsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(PrimitivetypesPackage.eNS_URI);
		PrimitivetypesPackageImpl thePrimitivetypesPackage = (PrimitivetypesPackageImpl)(registeredPackage instanceof PrimitivetypesPackageImpl ? registeredPackage : PrimitivetypesPackage.eINSTANCE);

		// Create package meta-data objects
		theDerivedtransitionrulesPackage.createPackageContents();
		theFurthertermsPackage.createPackageContents();
		theBasictermsPackage.createPackageContents();
		theStructurePackage.createPackageContents();
		theTurbotransitionrulesPackage.createPackageContents();
		theBasictransitionrulesPackage.createPackageContents();
		theDefinitionsPackage.createPackageContents();
		theDomainsPackage.createPackageContents();
		thePrimitivetypesPackage.createPackageContents();

		// Initialize created meta-data
		theDerivedtransitionrulesPackage.initializePackageContents();
		theFurthertermsPackage.initializePackageContents();
		theBasictermsPackage.initializePackageContents();
		theStructurePackage.initializePackageContents();
		theTurbotransitionrulesPackage.initializePackageContents();
		theBasictransitionrulesPackage.initializePackageContents();
		theDefinitionsPackage.initializePackageContents();
		theDomainsPackage.initializePackageContents();
		thePrimitivetypesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDerivedtransitionrulesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DerivedtransitionrulesPackage.eNS_URI, theDerivedtransitionrulesPackage);
		return theDerivedtransitionrulesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRecursiveWhileRule() {
		return recursiveWhileRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRecursiveWhileRule_Rule() {
		return (EReference)recursiveWhileRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRecursiveWhileRule_Guard() {
		return (EReference)recursiveWhileRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIterativeWhileRule() {
		return iterativeWhileRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIterativeWhileRule_Guard() {
		return (EReference)iterativeWhileRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIterativeWhileRule_Rule() {
		return (EReference)iterativeWhileRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDerivedRule() {
		return derivedRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCaseRule() {
		return caseRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCaseRule_Term() {
		return (EReference)caseRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCaseRule_CaseTerm() {
		return (EReference)caseRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCaseRule_OtherwiseBranch() {
		return (EReference)caseRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCaseRule_CaseBranches() {
		return (EAttribute)caseRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBasicDerivedRule() {
		return basicDerivedRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTurboDerivedRule() {
		return turboDerivedRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DerivedtransitionrulesFactory getDerivedtransitionrulesFactory() {
		return (DerivedtransitionrulesFactory)getEFactoryInstance();
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
		recursiveWhileRuleEClass = createEClass(RECURSIVE_WHILE_RULE);
		createEReference(recursiveWhileRuleEClass, RECURSIVE_WHILE_RULE__RULE);
		createEReference(recursiveWhileRuleEClass, RECURSIVE_WHILE_RULE__GUARD);

		iterativeWhileRuleEClass = createEClass(ITERATIVE_WHILE_RULE);
		createEReference(iterativeWhileRuleEClass, ITERATIVE_WHILE_RULE__GUARD);
		createEReference(iterativeWhileRuleEClass, ITERATIVE_WHILE_RULE__RULE);

		derivedRuleEClass = createEClass(DERIVED_RULE);

		caseRuleEClass = createEClass(CASE_RULE);
		createEReference(caseRuleEClass, CASE_RULE__TERM);
		createEReference(caseRuleEClass, CASE_RULE__CASE_TERM);
		createEReference(caseRuleEClass, CASE_RULE__OTHERWISE_BRANCH);
		createEAttribute(caseRuleEClass, CASE_RULE__CASE_BRANCHES);

		basicDerivedRuleEClass = createEClass(BASIC_DERIVED_RULE);

		turboDerivedRuleEClass = createEClass(TURBO_DERIVED_RULE);
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
		BasictermsPackage theBasictermsPackage = (BasictermsPackage)EPackage.Registry.INSTANCE.getEPackage(BasictermsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		recursiveWhileRuleEClass.getESuperTypes().add(this.getTurboDerivedRule());
		iterativeWhileRuleEClass.getESuperTypes().add(this.getTurboDerivedRule());
		derivedRuleEClass.getESuperTypes().add(theBasictransitionrulesPackage.getRule());
		caseRuleEClass.getESuperTypes().add(this.getBasicDerivedRule());
		basicDerivedRuleEClass.getESuperTypes().add(this.getDerivedRule());
		turboDerivedRuleEClass.getESuperTypes().add(this.getDerivedRule());

		// Initialize classes and features; add operations and parameters
		initEClass(recursiveWhileRuleEClass, RecursiveWhileRule.class, "RecursiveWhileRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRecursiveWhileRule_Rule(), theBasictransitionrulesPackage.getRule(), null, "rule", null, 1, 1, RecursiveWhileRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getRecursiveWhileRule_Guard(), theBasictermsPackage.getTerm(), null, "guard", null, 1, 1, RecursiveWhileRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(iterativeWhileRuleEClass, IterativeWhileRule.class, "IterativeWhileRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIterativeWhileRule_Guard(), theBasictermsPackage.getTerm(), null, "guard", null, 1, 1, IterativeWhileRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getIterativeWhileRule_Rule(), theBasictransitionrulesPackage.getRule(), null, "rule", null, 1, 1, IterativeWhileRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(derivedRuleEClass, DerivedRule.class, "DerivedRule", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(caseRuleEClass, CaseRule.class, "CaseRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCaseRule_Term(), theBasictermsPackage.getTerm(), null, "term", null, 1, 1, CaseRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCaseRule_CaseTerm(), theBasictermsPackage.getTerm(), null, "caseTerm", null, 1, -1, CaseRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCaseRule_OtherwiseBranch(), theBasictransitionrulesPackage.getRule(), null, "otherwiseBranch", null, 0, 1, CaseRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getCaseRule_CaseBranches(), theBasictransitionrulesPackage.getRuleDT(), "caseBranches", null, 1, -1, CaseRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(basicDerivedRuleEClass, BasicDerivedRule.class, "BasicDerivedRule", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(turboDerivedRuleEClass, TurboDerivedRule.class, "TurboDerivedRule", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //DerivedtransitionrulesPackageImpl

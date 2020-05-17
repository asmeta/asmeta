/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
import asmeta.transitionrules.basictransitionrules.BasicRule;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesFactory;
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
public class BasictransitionrulesPackageImpl extends EPackageImpl implements BasictransitionrulesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass termAsRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass basicRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass chooseRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass macroCallRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass blockRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionalRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass forallRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass letRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extendRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass updateRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass skipRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass macroDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType ruleDTEDataType = null;

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
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BasictransitionrulesPackageImpl() {
		super(eNS_URI, BasictransitionrulesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link BasictransitionrulesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BasictransitionrulesPackage init() {
		if (isInited) return (BasictransitionrulesPackage)EPackage.Registry.INSTANCE.getEPackage(BasictransitionrulesPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredBasictransitionrulesPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		BasictransitionrulesPackageImpl theBasictransitionrulesPackage = registeredBasictransitionrulesPackage instanceof BasictransitionrulesPackageImpl ? (BasictransitionrulesPackageImpl)registeredBasictransitionrulesPackage : new BasictransitionrulesPackageImpl();

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
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DefinitionsPackage.eNS_URI);
		DefinitionsPackageImpl theDefinitionsPackage = (DefinitionsPackageImpl)(registeredPackage instanceof DefinitionsPackageImpl ? registeredPackage : DefinitionsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DomainsPackage.eNS_URI);
		DomainsPackageImpl theDomainsPackage = (DomainsPackageImpl)(registeredPackage instanceof DomainsPackageImpl ? registeredPackage : DomainsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(PrimitivetypesPackage.eNS_URI);
		PrimitivetypesPackageImpl thePrimitivetypesPackage = (PrimitivetypesPackageImpl)(registeredPackage instanceof PrimitivetypesPackageImpl ? registeredPackage : PrimitivetypesPackage.eINSTANCE);

		// Create package meta-data objects
		theBasictransitionrulesPackage.createPackageContents();
		theFurthertermsPackage.createPackageContents();
		theBasictermsPackage.createPackageContents();
		theStructurePackage.createPackageContents();
		theTurbotransitionrulesPackage.createPackageContents();
		theDerivedtransitionrulesPackage.createPackageContents();
		theDefinitionsPackage.createPackageContents();
		theDomainsPackage.createPackageContents();
		thePrimitivetypesPackage.createPackageContents();

		// Initialize created meta-data
		theBasictransitionrulesPackage.initializePackageContents();
		theFurthertermsPackage.initializePackageContents();
		theBasictermsPackage.initializePackageContents();
		theStructurePackage.initializePackageContents();
		theTurbotransitionrulesPackage.initializePackageContents();
		theDerivedtransitionrulesPackage.initializePackageContents();
		theDefinitionsPackage.initializePackageContents();
		theDomainsPackage.initializePackageContents();
		thePrimitivetypesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBasictransitionrulesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BasictransitionrulesPackage.eNS_URI, theBasictransitionrulesPackage);
		return theBasictransitionrulesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTermAsRule() {
		return termAsRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTermAsRule_Term() {
		return (EReference)termAsRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTermAsRule_Parameters() {
		return (EAttribute)termAsRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBasicRule() {
		return basicRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRule() {
		return ruleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getChooseRule() {
		return chooseRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChooseRule_Ifnone() {
		return (EReference)chooseRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChooseRule_DoRule() {
		return (EReference)chooseRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChooseRule_Guard() {
		return (EReference)chooseRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getChooseRule_Variable() {
		return (EReference)chooseRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getChooseRule_Ranges() {
		return (EAttribute)chooseRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMacroCallRule() {
		return macroCallRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMacroCallRule_CalledMacro() {
		return (EReference)macroCallRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMacroCallRule_Parameters() {
		return (EAttribute)macroCallRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBlockRule() {
		return blockRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getBlockRule_Rules() {
		return (EAttribute)blockRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConditionalRule() {
		return conditionalRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConditionalRule_Guard() {
		return (EReference)conditionalRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConditionalRule_ElseRule() {
		return (EReference)conditionalRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConditionalRule_ThenRule() {
		return (EReference)conditionalRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getForallRule() {
		return forallRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getForallRule_Variable() {
		return (EReference)forallRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getForallRule_Guard() {
		return (EReference)forallRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getForallRule_DoRule() {
		return (EReference)forallRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getForallRule_Ranges() {
		return (EAttribute)forallRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLetRule() {
		return letRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLetRule_InRule() {
		return (EReference)letRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLetRule_InitExpression() {
		return (EReference)letRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLetRule_Variable() {
		return (EReference)letRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExtendRule() {
		return extendRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExtendRule_ExtendedDomain() {
		return (EReference)extendRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExtendRule_BoundVar() {
		return (EReference)extendRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExtendRule_DoRule() {
		return (EReference)extendRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUpdateRule() {
		return updateRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUpdateRule_UpdatingTerm() {
		return (EReference)updateRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUpdateRule_Location() {
		return (EReference)updateRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSkipRule() {
		return skipRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMacroDeclaration() {
		return macroDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getRuleDT() {
		return ruleDTEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BasictransitionrulesFactory getBasictransitionrulesFactory() {
		return (BasictransitionrulesFactory)getEFactoryInstance();
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
		termAsRuleEClass = createEClass(TERM_AS_RULE);
		createEReference(termAsRuleEClass, TERM_AS_RULE__TERM);
		createEAttribute(termAsRuleEClass, TERM_AS_RULE__PARAMETERS);

		basicRuleEClass = createEClass(BASIC_RULE);

		ruleEClass = createEClass(RULE);

		chooseRuleEClass = createEClass(CHOOSE_RULE);
		createEReference(chooseRuleEClass, CHOOSE_RULE__IFNONE);
		createEReference(chooseRuleEClass, CHOOSE_RULE__DO_RULE);
		createEReference(chooseRuleEClass, CHOOSE_RULE__GUARD);
		createEReference(chooseRuleEClass, CHOOSE_RULE__VARIABLE);
		createEAttribute(chooseRuleEClass, CHOOSE_RULE__RANGES);

		macroCallRuleEClass = createEClass(MACRO_CALL_RULE);
		createEReference(macroCallRuleEClass, MACRO_CALL_RULE__CALLED_MACRO);
		createEAttribute(macroCallRuleEClass, MACRO_CALL_RULE__PARAMETERS);

		blockRuleEClass = createEClass(BLOCK_RULE);
		createEAttribute(blockRuleEClass, BLOCK_RULE__RULES);

		conditionalRuleEClass = createEClass(CONDITIONAL_RULE);
		createEReference(conditionalRuleEClass, CONDITIONAL_RULE__GUARD);
		createEReference(conditionalRuleEClass, CONDITIONAL_RULE__ELSE_RULE);
		createEReference(conditionalRuleEClass, CONDITIONAL_RULE__THEN_RULE);

		forallRuleEClass = createEClass(FORALL_RULE);
		createEReference(forallRuleEClass, FORALL_RULE__VARIABLE);
		createEReference(forallRuleEClass, FORALL_RULE__GUARD);
		createEReference(forallRuleEClass, FORALL_RULE__DO_RULE);
		createEAttribute(forallRuleEClass, FORALL_RULE__RANGES);

		letRuleEClass = createEClass(LET_RULE);
		createEReference(letRuleEClass, LET_RULE__IN_RULE);
		createEReference(letRuleEClass, LET_RULE__INIT_EXPRESSION);
		createEReference(letRuleEClass, LET_RULE__VARIABLE);

		extendRuleEClass = createEClass(EXTEND_RULE);
		createEReference(extendRuleEClass, EXTEND_RULE__EXTENDED_DOMAIN);
		createEReference(extendRuleEClass, EXTEND_RULE__BOUND_VAR);
		createEReference(extendRuleEClass, EXTEND_RULE__DO_RULE);

		updateRuleEClass = createEClass(UPDATE_RULE);
		createEReference(updateRuleEClass, UPDATE_RULE__UPDATING_TERM);
		createEReference(updateRuleEClass, UPDATE_RULE__LOCATION);

		skipRuleEClass = createEClass(SKIP_RULE);

		macroDeclarationEClass = createEClass(MACRO_DECLARATION);

		// Create data types
		ruleDTEDataType = createEDataType(RULE_DT);
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
		BasictermsPackage theBasictermsPackage = (BasictermsPackage)EPackage.Registry.INSTANCE.getEPackage(BasictermsPackage.eNS_URI);
		DomainsPackage theDomainsPackage = (DomainsPackage)EPackage.Registry.INSTANCE.getEPackage(DomainsPackage.eNS_URI);
		DefinitionsPackage theDefinitionsPackage = (DefinitionsPackage)EPackage.Registry.INSTANCE.getEPackage(DefinitionsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		termAsRuleEClass.getESuperTypes().add(this.getRule());
		basicRuleEClass.getESuperTypes().add(this.getRule());
		chooseRuleEClass.getESuperTypes().add(this.getBasicRule());
		macroCallRuleEClass.getESuperTypes().add(this.getBasicRule());
		blockRuleEClass.getESuperTypes().add(this.getBasicRule());
		conditionalRuleEClass.getESuperTypes().add(this.getBasicRule());
		forallRuleEClass.getESuperTypes().add(this.getBasicRule());
		letRuleEClass.getESuperTypes().add(this.getBasicRule());
		extendRuleEClass.getESuperTypes().add(this.getBasicRule());
		updateRuleEClass.getESuperTypes().add(this.getBasicRule());
		skipRuleEClass.getESuperTypes().add(this.getBasicRule());
		macroDeclarationEClass.getESuperTypes().add(theDefinitionsPackage.getRuleDeclaration());

		// Initialize classes and features; add operations and parameters
		initEClass(termAsRuleEClass, TermAsRule.class, "TermAsRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTermAsRule_Term(), theBasictermsPackage.getTerm(), theBasictermsPackage.getTerm_TermAsRule(), "term", null, 1, 1, TermAsRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getTermAsRule_Parameters(), theBasictermsPackage.getTermDT(), "parameters", null, 0, -1, TermAsRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(basicRuleEClass, BasicRule.class, "BasicRule", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(ruleEClass, Rule.class, "Rule", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(chooseRuleEClass, ChooseRule.class, "ChooseRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChooseRule_Ifnone(), this.getRule(), null, "ifnone", null, 0, 1, ChooseRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getChooseRule_DoRule(), this.getRule(), null, "doRule", null, 1, 1, ChooseRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getChooseRule_Guard(), theBasictermsPackage.getTerm(), null, "guard", null, 1, 1, ChooseRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getChooseRule_Variable(), theBasictermsPackage.getVariableTerm(), null, "variable", null, 1, -1, ChooseRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getChooseRule_Ranges(), theBasictermsPackage.getTermDT(), "ranges", null, 1, -1, ChooseRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(macroCallRuleEClass, MacroCallRule.class, "MacroCallRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMacroCallRule_CalledMacro(), this.getMacroDeclaration(), null, "calledMacro", null, 1, 1, MacroCallRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getMacroCallRule_Parameters(), theBasictermsPackage.getTermDT(), "parameters", null, 0, -1, MacroCallRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(blockRuleEClass, BlockRule.class, "BlockRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBlockRule_Rules(), this.getRuleDT(), "rules", null, 2, -1, BlockRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionalRuleEClass, ConditionalRule.class, "ConditionalRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConditionalRule_Guard(), theBasictermsPackage.getTerm(), null, "guard", null, 1, 1, ConditionalRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getConditionalRule_ElseRule(), this.getRule(), null, "elseRule", null, 0, 1, ConditionalRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getConditionalRule_ThenRule(), this.getRule(), null, "thenRule", null, 1, 1, ConditionalRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(forallRuleEClass, ForallRule.class, "ForallRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getForallRule_Variable(), theBasictermsPackage.getVariableTerm(), null, "variable", null, 1, -1, ForallRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getForallRule_Guard(), theBasictermsPackage.getTerm(), null, "guard", null, 1, 1, ForallRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getForallRule_DoRule(), this.getRule(), null, "doRule", null, 1, 1, ForallRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getForallRule_Ranges(), theBasictermsPackage.getTermDT(), "ranges", null, 1, -1, ForallRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(letRuleEClass, LetRule.class, "LetRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLetRule_InRule(), this.getRule(), null, "inRule", null, 1, 1, LetRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getLetRule_InitExpression(), theBasictermsPackage.getTerm(), null, "initExpression", null, 1, -1, LetRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLetRule_Variable(), theBasictermsPackage.getVariableTerm(), null, "variable", null, 1, -1, LetRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extendRuleEClass, ExtendRule.class, "ExtendRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExtendRule_ExtendedDomain(), theDomainsPackage.getDomain(), null, "extendedDomain", null, 1, 1, ExtendRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getExtendRule_BoundVar(), theBasictermsPackage.getVariableTerm(), null, "boundVar", null, 1, -1, ExtendRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getExtendRule_DoRule(), this.getRule(), null, "doRule", null, 1, 1, ExtendRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(updateRuleEClass, UpdateRule.class, "UpdateRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUpdateRule_UpdatingTerm(), theBasictermsPackage.getTerm(), null, "updatingTerm", null, 1, 1, UpdateRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getUpdateRule_Location(), theBasictermsPackage.getTerm(), null, "location", null, 0, 1, UpdateRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(skipRuleEClass, SkipRule.class, "SkipRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(macroDeclarationEClass, MacroDeclaration.class, "MacroDeclaration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize data types
		initEDataType(ruleDTEDataType, Rule.class, "RuleDT", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //BasictransitionrulesPackageImpl

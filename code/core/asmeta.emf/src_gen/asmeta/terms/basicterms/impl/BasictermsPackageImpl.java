/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.domains.DomainsPackage;
import asmeta.definitions.domains.impl.DomainsPackageImpl;
import asmeta.definitions.impl.DefinitionsPackageImpl;
import asmeta.structure.StructurePackage;
import asmeta.structure.impl.StructurePackageImpl;
import asmeta.terms.basicterms.BasicTerm;
import asmeta.terms.basicterms.BasictermsFactory;
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
import asmeta.terms.basicterms.VariableKind;
import asmeta.terms.basicterms.VariableTerm;
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
public class BasictermsPackageImpl extends EPackageImpl implements BasictermsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass undefTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tupleTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass setTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleAsTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass locationTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extendedTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass domainTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constantTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass collectionTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass basicTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass termEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum variableKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType termDTEDataType = null;

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
	 * @see asmeta.terms.basicterms.BasictermsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BasictermsPackageImpl() {
		super(eNS_URI, BasictermsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link BasictermsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BasictermsPackage init() {
		if (isInited) return (BasictermsPackage)EPackage.Registry.INSTANCE.getEPackage(BasictermsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredBasictermsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		BasictermsPackageImpl theBasictermsPackage = registeredBasictermsPackage instanceof BasictermsPackageImpl ? (BasictermsPackageImpl)registeredBasictermsPackage : new BasictermsPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(FurthertermsPackage.eNS_URI);
		FurthertermsPackageImpl theFurthertermsPackage = (FurthertermsPackageImpl)(registeredPackage instanceof FurthertermsPackageImpl ? registeredPackage : FurthertermsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(StructurePackage.eNS_URI);
		StructurePackageImpl theStructurePackage = (StructurePackageImpl)(registeredPackage instanceof StructurePackageImpl ? registeredPackage : StructurePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(TurbotransitionrulesPackage.eNS_URI);
		TurbotransitionrulesPackageImpl theTurbotransitionrulesPackage = (TurbotransitionrulesPackageImpl)(registeredPackage instanceof TurbotransitionrulesPackageImpl ? registeredPackage : TurbotransitionrulesPackage.eINSTANCE);
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
		theBasictermsPackage.createPackageContents();
		theFurthertermsPackage.createPackageContents();
		theStructurePackage.createPackageContents();
		theTurbotransitionrulesPackage.createPackageContents();
		theDerivedtransitionrulesPackage.createPackageContents();
		theBasictransitionrulesPackage.createPackageContents();
		theDefinitionsPackage.createPackageContents();
		theDomainsPackage.createPackageContents();
		thePrimitivetypesPackage.createPackageContents();

		// Initialize created meta-data
		theBasictermsPackage.initializePackageContents();
		theFurthertermsPackage.initializePackageContents();
		theStructurePackage.initializePackageContents();
		theTurbotransitionrulesPackage.initializePackageContents();
		theDerivedtransitionrulesPackage.initializePackageContents();
		theBasictransitionrulesPackage.initializePackageContents();
		theDefinitionsPackage.initializePackageContents();
		theDomainsPackage.initializePackageContents();
		thePrimitivetypesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBasictermsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BasictermsPackage.eNS_URI, theBasictermsPackage);
		return theBasictermsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariableTerm() {
		return variableTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVariableTerm_FiniteQuantificationTerm() {
		return (EReference)variableTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVariableTerm_Name() {
		return (EAttribute)variableTermEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVariableTerm_Kind() {
		return (EAttribute)variableTermEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUndefTerm() {
		return undefTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTupleTerm() {
		return tupleTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTupleTerm_Arity() {
		return (EAttribute)tupleTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTupleTerm_Terms() {
		return (EAttribute)tupleTermEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSetTerm() {
		return setTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSetTerm_Term() {
		return (EReference)setTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRuleAsTerm() {
		return ruleAsTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleAsTerm_Rule() {
		return (EReference)ruleAsTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLocationTerm() {
		return locationTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFunctionTerm() {
		return functionTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionTerm_Arguments() {
		return (EReference)functionTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionTerm_Function() {
		return (EReference)functionTermEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExtendedTerm() {
		return extendedTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDomainTerm() {
		return domainTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConstantTerm() {
		return constantTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConstantTerm_Symbol() {
		return (EAttribute)constantTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCollectionTerm() {
		return collectionTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCollectionTerm_Size() {
		return (EAttribute)collectionTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBooleanTerm() {
		return booleanTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBasicTerm() {
		return basicTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTerm() {
		return termEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTerm_Domain() {
		return (EReference)termEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTerm_TermAsRule() {
		return (EReference)termEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getVariableKind() {
		return variableKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getTermDT() {
		return termDTEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BasictermsFactory getBasictermsFactory() {
		return (BasictermsFactory)getEFactoryInstance();
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
		variableTermEClass = createEClass(VARIABLE_TERM);
		createEReference(variableTermEClass, VARIABLE_TERM__FINITE_QUANTIFICATION_TERM);
		createEAttribute(variableTermEClass, VARIABLE_TERM__NAME);
		createEAttribute(variableTermEClass, VARIABLE_TERM__KIND);

		undefTermEClass = createEClass(UNDEF_TERM);

		tupleTermEClass = createEClass(TUPLE_TERM);
		createEAttribute(tupleTermEClass, TUPLE_TERM__ARITY);
		createEAttribute(tupleTermEClass, TUPLE_TERM__TERMS);

		setTermEClass = createEClass(SET_TERM);
		createEReference(setTermEClass, SET_TERM__TERM);

		ruleAsTermEClass = createEClass(RULE_AS_TERM);
		createEReference(ruleAsTermEClass, RULE_AS_TERM__RULE);

		locationTermEClass = createEClass(LOCATION_TERM);

		functionTermEClass = createEClass(FUNCTION_TERM);
		createEReference(functionTermEClass, FUNCTION_TERM__ARGUMENTS);
		createEReference(functionTermEClass, FUNCTION_TERM__FUNCTION);

		extendedTermEClass = createEClass(EXTENDED_TERM);

		domainTermEClass = createEClass(DOMAIN_TERM);

		constantTermEClass = createEClass(CONSTANT_TERM);
		createEAttribute(constantTermEClass, CONSTANT_TERM__SYMBOL);

		collectionTermEClass = createEClass(COLLECTION_TERM);
		createEAttribute(collectionTermEClass, COLLECTION_TERM__SIZE);

		booleanTermEClass = createEClass(BOOLEAN_TERM);

		basicTermEClass = createEClass(BASIC_TERM);

		termEClass = createEClass(TERM);
		createEReference(termEClass, TERM__DOMAIN);
		createEReference(termEClass, TERM__TERM_AS_RULE);

		// Create enums
		variableKindEEnum = createEEnum(VARIABLE_KIND);

		// Create data types
		termDTEDataType = createEDataType(TERM_DT);
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
		FurthertermsPackage theFurthertermsPackage = (FurthertermsPackage)EPackage.Registry.INSTANCE.getEPackage(FurthertermsPackage.eNS_URI);
		PrimitivetypesPackage thePrimitivetypesPackage = (PrimitivetypesPackage)EPackage.Registry.INSTANCE.getEPackage(PrimitivetypesPackage.eNS_URI);
		DefinitionsPackage theDefinitionsPackage = (DefinitionsPackage)EPackage.Registry.INSTANCE.getEPackage(DefinitionsPackage.eNS_URI);
		DomainsPackage theDomainsPackage = (DomainsPackage)EPackage.Registry.INSTANCE.getEPackage(DomainsPackage.eNS_URI);
		BasictransitionrulesPackage theBasictransitionrulesPackage = (BasictransitionrulesPackage)EPackage.Registry.INSTANCE.getEPackage(BasictransitionrulesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		variableTermEClass.getESuperTypes().add(this.getBasicTerm());
		undefTermEClass.getESuperTypes().add(this.getConstantTerm());
		tupleTermEClass.getESuperTypes().add(this.getExtendedTerm());
		setTermEClass.getESuperTypes().add(this.getCollectionTerm());
		ruleAsTermEClass.getESuperTypes().add(this.getExtendedTerm());
		locationTermEClass.getESuperTypes().add(this.getFunctionTerm());
		functionTermEClass.getESuperTypes().add(this.getBasicTerm());
		extendedTermEClass.getESuperTypes().add(this.getTerm());
		domainTermEClass.getESuperTypes().add(this.getExtendedTerm());
		constantTermEClass.getESuperTypes().add(this.getBasicTerm());
		collectionTermEClass.getESuperTypes().add(this.getExtendedTerm());
		booleanTermEClass.getESuperTypes().add(this.getConstantTerm());
		basicTermEClass.getESuperTypes().add(this.getTerm());

		// Initialize classes and features; add operations and parameters
		initEClass(variableTermEClass, VariableTerm.class, "VariableTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVariableTerm_FiniteQuantificationTerm(), theFurthertermsPackage.getFiniteQuantificationTerm(), theFurthertermsPackage.getFiniteQuantificationTerm_Variable(), "finiteQuantificationTerm", null, 0, 1, VariableTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getVariableTerm_Name(), thePrimitivetypesPackage.getString(), "name", null, 1, 1, VariableTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getVariableTerm_Kind(), this.getVariableKind(), "kind", null, 1, 1, VariableTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(undefTermEClass, UndefTerm.class, "UndefTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(tupleTermEClass, TupleTerm.class, "TupleTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTupleTerm_Arity(), thePrimitivetypesPackage.getInteger(), "arity", null, 1, 1, TupleTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getTupleTerm_Terms(), this.getTermDT(), "terms", null, 2, -1, TupleTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(setTermEClass, SetTerm.class, "SetTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSetTerm_Term(), this.getTerm(), null, "term", null, 0, -1, SetTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(ruleAsTermEClass, RuleAsTerm.class, "RuleAsTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleAsTerm_Rule(), theDefinitionsPackage.getRuleDeclaration(), null, "rule", null, 1, 1, RuleAsTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(locationTermEClass, LocationTerm.class, "LocationTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(functionTermEClass, FunctionTerm.class, "FunctionTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFunctionTerm_Arguments(), this.getTupleTerm(), null, "arguments", null, 0, 1, FunctionTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getFunctionTerm_Function(), theDefinitionsPackage.getFunction(), null, "function", null, 1, 1, FunctionTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(extendedTermEClass, ExtendedTerm.class, "ExtendedTerm", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(domainTermEClass, DomainTerm.class, "DomainTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(constantTermEClass, ConstantTerm.class, "ConstantTerm", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConstantTerm_Symbol(), thePrimitivetypesPackage.getString(), "symbol", null, 1, 1, ConstantTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(collectionTermEClass, CollectionTerm.class, "CollectionTerm", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCollectionTerm_Size(), thePrimitivetypesPackage.getInteger(), "size", null, 1, 1, CollectionTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(booleanTermEClass, BooleanTerm.class, "BooleanTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(basicTermEClass, BasicTerm.class, "BasicTerm", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(termEClass, Term.class, "Term", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTerm_Domain(), theDomainsPackage.getDomain(), null, "domain", null, 1, 1, Term.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getTerm_TermAsRule(), theBasictransitionrulesPackage.getTermAsRule(), theBasictransitionrulesPackage.getTermAsRule_Term(), "termAsRule", null, 0, -1, Term.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		addEOperation(termEClass, null, "compatible", 0, 1, IS_UNIQUE, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(variableKindEEnum, VariableKind.class, "VariableKind");
		addEEnumLiteral(variableKindEEnum, VariableKind.LOGICAL_VAR);
		addEEnumLiteral(variableKindEEnum, VariableKind.LOCATION_VAR);
		addEEnumLiteral(variableKindEEnum, VariableKind.RULE_VAR);

		// Initialize data types
		initEDataType(termDTEDataType, Term.class, "TermDT", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //BasictermsPackageImpl

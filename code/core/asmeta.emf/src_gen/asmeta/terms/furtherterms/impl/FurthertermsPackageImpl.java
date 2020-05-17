/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms.impl;

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
import asmeta.terms.furtherterms.BagCt;
import asmeta.terms.furtherterms.BagTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.CharTerm;
import asmeta.terms.furtherterms.ComplexTerm;
import asmeta.terms.furtherterms.ComprehensionTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.ExistUniqueTerm;
import asmeta.terms.furtherterms.FiniteQuantificationTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.FurthertermsFactory;
import asmeta.terms.furtherterms.FurthertermsPackage;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.MapCt;
import asmeta.terms.furtherterms.MapTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.SequenceCt;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.SetCt;
import asmeta.terms.furtherterms.StringTerm;
import asmeta.terms.furtherterms.VariableBindingTerm;
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
public class FurthertermsPackageImpl extends EPackageImpl implements FurthertermsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass naturalTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableBindingTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass setCtEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sequenceTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sequenceCtEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass realTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapCtEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass letTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass forallTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass finiteQuantificationTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass existUniqueTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass existTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionalTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass comprehensionTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass complexTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass charTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass caseTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bagTermEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bagCtEClass = null;

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
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private FurthertermsPackageImpl() {
		super(eNS_URI, FurthertermsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link FurthertermsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static FurthertermsPackage init() {
		if (isInited) return (FurthertermsPackage)EPackage.Registry.INSTANCE.getEPackage(FurthertermsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredFurthertermsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		FurthertermsPackageImpl theFurthertermsPackage = registeredFurthertermsPackage instanceof FurthertermsPackageImpl ? (FurthertermsPackageImpl)registeredFurthertermsPackage : new FurthertermsPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(BasictermsPackage.eNS_URI);
		BasictermsPackageImpl theBasictermsPackage = (BasictermsPackageImpl)(registeredPackage instanceof BasictermsPackageImpl ? registeredPackage : BasictermsPackage.eINSTANCE);
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
		theFurthertermsPackage.createPackageContents();
		theBasictermsPackage.createPackageContents();
		theStructurePackage.createPackageContents();
		theTurbotransitionrulesPackage.createPackageContents();
		theDerivedtransitionrulesPackage.createPackageContents();
		theBasictransitionrulesPackage.createPackageContents();
		theDefinitionsPackage.createPackageContents();
		theDomainsPackage.createPackageContents();
		thePrimitivetypesPackage.createPackageContents();

		// Initialize created meta-data
		theFurthertermsPackage.initializePackageContents();
		theBasictermsPackage.initializePackageContents();
		theStructurePackage.initializePackageContents();
		theTurbotransitionrulesPackage.initializePackageContents();
		theDerivedtransitionrulesPackage.initializePackageContents();
		theBasictransitionrulesPackage.initializePackageContents();
		theDefinitionsPackage.initializePackageContents();
		theDomainsPackage.initializePackageContents();
		thePrimitivetypesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theFurthertermsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(FurthertermsPackage.eNS_URI, theFurthertermsPackage);
		return theFurthertermsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIntegerTerm() {
		return integerTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNaturalTerm() {
		return naturalTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVariableBindingTerm() {
		return variableBindingTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStringTerm() {
		return stringTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSetCt() {
		return setCtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSequenceTerm() {
		return sequenceTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSequenceTerm_Terms() {
		return (EAttribute)sequenceTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSequenceCt() {
		return sequenceCtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRealTerm() {
		return realTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMapTerm() {
		return mapTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMapTerm_Pair() {
		return (EReference)mapTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMapCt() {
		return mapCtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLetTerm() {
		return letTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLetTerm_Variable() {
		return (EReference)letTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLetTerm_AssignmentTerm() {
		return (EReference)letTermEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLetTerm_Body() {
		return (EReference)letTermEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getForallTerm() {
		return forallTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFiniteQuantificationTerm() {
		return finiteQuantificationTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFiniteQuantificationTerm_Variable() {
		return (EReference)finiteQuantificationTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFiniteQuantificationTerm_Guard() {
		return (EReference)finiteQuantificationTermEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFiniteQuantificationTerm_Ranges() {
		return (EAttribute)finiteQuantificationTermEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExistUniqueTerm() {
		return existUniqueTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExistTerm() {
		return existTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEnumTerm() {
		return enumTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConditionalTerm() {
		return conditionalTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConditionalTerm_ElseTerm() {
		return (EReference)conditionalTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConditionalTerm_Guard() {
		return (EReference)conditionalTermEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConditionalTerm_ThenTerm() {
		return (EReference)conditionalTermEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getComprehensionTerm() {
		return comprehensionTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComprehensionTerm_Variable() {
		return (EReference)comprehensionTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComprehensionTerm_Guard() {
		return (EReference)comprehensionTermEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getComprehensionTerm_Term() {
		return (EReference)comprehensionTermEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getComprehensionTerm_Ranges() {
		return (EAttribute)comprehensionTermEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getComplexTerm() {
		return complexTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCharTerm() {
		return charTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCaseTerm() {
		return caseTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCaseTerm_ComparingTerm() {
		return (EReference)caseTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCaseTerm_ComparedTerm() {
		return (EReference)caseTermEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCaseTerm_OtherwiseTerm() {
		return (EReference)caseTermEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCaseTerm_ResultTerms() {
		return (EAttribute)caseTermEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBagTerm() {
		return bagTermEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBagTerm_Term() {
		return (EReference)bagTermEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBagCt() {
		return bagCtEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FurthertermsFactory getFurthertermsFactory() {
		return (FurthertermsFactory)getEFactoryInstance();
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
		integerTermEClass = createEClass(INTEGER_TERM);

		naturalTermEClass = createEClass(NATURAL_TERM);

		variableBindingTermEClass = createEClass(VARIABLE_BINDING_TERM);

		stringTermEClass = createEClass(STRING_TERM);

		setCtEClass = createEClass(SET_CT);

		sequenceTermEClass = createEClass(SEQUENCE_TERM);
		createEAttribute(sequenceTermEClass, SEQUENCE_TERM__TERMS);

		sequenceCtEClass = createEClass(SEQUENCE_CT);

		realTermEClass = createEClass(REAL_TERM);

		mapTermEClass = createEClass(MAP_TERM);
		createEReference(mapTermEClass, MAP_TERM__PAIR);

		mapCtEClass = createEClass(MAP_CT);

		letTermEClass = createEClass(LET_TERM);
		createEReference(letTermEClass, LET_TERM__VARIABLE);
		createEReference(letTermEClass, LET_TERM__ASSIGNMENT_TERM);
		createEReference(letTermEClass, LET_TERM__BODY);

		forallTermEClass = createEClass(FORALL_TERM);

		finiteQuantificationTermEClass = createEClass(FINITE_QUANTIFICATION_TERM);
		createEReference(finiteQuantificationTermEClass, FINITE_QUANTIFICATION_TERM__VARIABLE);
		createEReference(finiteQuantificationTermEClass, FINITE_QUANTIFICATION_TERM__GUARD);
		createEAttribute(finiteQuantificationTermEClass, FINITE_QUANTIFICATION_TERM__RANGES);

		existUniqueTermEClass = createEClass(EXIST_UNIQUE_TERM);

		existTermEClass = createEClass(EXIST_TERM);

		enumTermEClass = createEClass(ENUM_TERM);

		conditionalTermEClass = createEClass(CONDITIONAL_TERM);
		createEReference(conditionalTermEClass, CONDITIONAL_TERM__ELSE_TERM);
		createEReference(conditionalTermEClass, CONDITIONAL_TERM__GUARD);
		createEReference(conditionalTermEClass, CONDITIONAL_TERM__THEN_TERM);

		comprehensionTermEClass = createEClass(COMPREHENSION_TERM);
		createEReference(comprehensionTermEClass, COMPREHENSION_TERM__VARIABLE);
		createEReference(comprehensionTermEClass, COMPREHENSION_TERM__GUARD);
		createEReference(comprehensionTermEClass, COMPREHENSION_TERM__TERM);
		createEAttribute(comprehensionTermEClass, COMPREHENSION_TERM__RANGES);

		complexTermEClass = createEClass(COMPLEX_TERM);

		charTermEClass = createEClass(CHAR_TERM);

		caseTermEClass = createEClass(CASE_TERM);
		createEReference(caseTermEClass, CASE_TERM__COMPARING_TERM);
		createEReference(caseTermEClass, CASE_TERM__COMPARED_TERM);
		createEReference(caseTermEClass, CASE_TERM__OTHERWISE_TERM);
		createEAttribute(caseTermEClass, CASE_TERM__RESULT_TERMS);

		bagTermEClass = createEClass(BAG_TERM);
		createEReference(bagTermEClass, BAG_TERM__TERM);

		bagCtEClass = createEClass(BAG_CT);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		integerTermEClass.getESuperTypes().add(theBasictermsPackage.getConstantTerm());
		naturalTermEClass.getESuperTypes().add(theBasictermsPackage.getConstantTerm());
		variableBindingTermEClass.getESuperTypes().add(theBasictermsPackage.getExtendedTerm());
		stringTermEClass.getESuperTypes().add(theBasictermsPackage.getConstantTerm());
		setCtEClass.getESuperTypes().add(this.getComprehensionTerm());
		sequenceTermEClass.getESuperTypes().add(theBasictermsPackage.getCollectionTerm());
		sequenceCtEClass.getESuperTypes().add(this.getComprehensionTerm());
		realTermEClass.getESuperTypes().add(theBasictermsPackage.getConstantTerm());
		mapTermEClass.getESuperTypes().add(theBasictermsPackage.getCollectionTerm());
		mapCtEClass.getESuperTypes().add(this.getComprehensionTerm());
		letTermEClass.getESuperTypes().add(this.getVariableBindingTerm());
		forallTermEClass.getESuperTypes().add(this.getFiniteQuantificationTerm());
		finiteQuantificationTermEClass.getESuperTypes().add(this.getVariableBindingTerm());
		existUniqueTermEClass.getESuperTypes().add(this.getFiniteQuantificationTerm());
		existTermEClass.getESuperTypes().add(this.getFiniteQuantificationTerm());
		enumTermEClass.getESuperTypes().add(theBasictermsPackage.getConstantTerm());
		conditionalTermEClass.getESuperTypes().add(theBasictermsPackage.getExtendedTerm());
		comprehensionTermEClass.getESuperTypes().add(this.getVariableBindingTerm());
		complexTermEClass.getESuperTypes().add(theBasictermsPackage.getConstantTerm());
		charTermEClass.getESuperTypes().add(theBasictermsPackage.getConstantTerm());
		caseTermEClass.getESuperTypes().add(theBasictermsPackage.getExtendedTerm());
		bagTermEClass.getESuperTypes().add(theBasictermsPackage.getCollectionTerm());
		bagCtEClass.getESuperTypes().add(this.getComprehensionTerm());

		// Initialize classes and features; add operations and parameters
		initEClass(integerTermEClass, IntegerTerm.class, "IntegerTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(naturalTermEClass, NaturalTerm.class, "NaturalTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(variableBindingTermEClass, VariableBindingTerm.class, "VariableBindingTerm", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(stringTermEClass, StringTerm.class, "StringTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(setCtEClass, SetCt.class, "SetCt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(sequenceTermEClass, SequenceTerm.class, "SequenceTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSequenceTerm_Terms(), theBasictermsPackage.getTermDT(), "terms", null, 0, -1, SequenceTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sequenceCtEClass, SequenceCt.class, "SequenceCt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(realTermEClass, RealTerm.class, "RealTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mapTermEClass, MapTerm.class, "MapTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMapTerm_Pair(), theBasictermsPackage.getTupleTerm(), null, "pair", null, 0, -1, MapTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(mapCtEClass, MapCt.class, "MapCt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(letTermEClass, LetTerm.class, "LetTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLetTerm_Variable(), theBasictermsPackage.getVariableTerm(), null, "variable", null, 1, -1, LetTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLetTerm_AssignmentTerm(), theBasictermsPackage.getTerm(), null, "assignmentTerm", null, 1, -1, LetTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLetTerm_Body(), theBasictermsPackage.getTerm(), null, "body", null, 1, 1, LetTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(forallTermEClass, ForallTerm.class, "ForallTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(finiteQuantificationTermEClass, FiniteQuantificationTerm.class, "FiniteQuantificationTerm", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFiniteQuantificationTerm_Variable(), theBasictermsPackage.getVariableTerm(), theBasictermsPackage.getVariableTerm_FiniteQuantificationTerm(), "variable", null, 1, -1, FiniteQuantificationTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFiniteQuantificationTerm_Guard(), theBasictermsPackage.getTerm(), null, "guard", null, 0, 1, FiniteQuantificationTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getFiniteQuantificationTerm_Ranges(), theBasictermsPackage.getTermDT(), "ranges", null, 1, -1, FiniteQuantificationTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(existUniqueTermEClass, ExistUniqueTerm.class, "ExistUniqueTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(existTermEClass, ExistTerm.class, "ExistTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(enumTermEClass, EnumTerm.class, "EnumTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(conditionalTermEClass, ConditionalTerm.class, "ConditionalTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConditionalTerm_ElseTerm(), theBasictermsPackage.getTerm(), null, "elseTerm", null, 0, 1, ConditionalTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getConditionalTerm_Guard(), theBasictermsPackage.getTerm(), null, "guard", null, 1, 1, ConditionalTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getConditionalTerm_ThenTerm(), theBasictermsPackage.getTerm(), null, "thenTerm", null, 1, 1, ConditionalTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(comprehensionTermEClass, ComprehensionTerm.class, "ComprehensionTerm", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComprehensionTerm_Variable(), theBasictermsPackage.getVariableTerm(), null, "variable", null, 1, -1, ComprehensionTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getComprehensionTerm_Guard(), theBasictermsPackage.getTerm(), null, "guard", null, 0, 1, ComprehensionTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getComprehensionTerm_Term(), theBasictermsPackage.getTerm(), null, "term", null, 1, 1, ComprehensionTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getComprehensionTerm_Ranges(), theBasictermsPackage.getTermDT(), "ranges", null, 1, -1, ComprehensionTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(complexTermEClass, ComplexTerm.class, "ComplexTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(charTermEClass, CharTerm.class, "CharTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(caseTermEClass, CaseTerm.class, "CaseTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCaseTerm_ComparingTerm(), theBasictermsPackage.getTerm(), null, "comparingTerm", null, 1, -1, CaseTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCaseTerm_ComparedTerm(), theBasictermsPackage.getTerm(), null, "comparedTerm", null, 1, 1, CaseTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCaseTerm_OtherwiseTerm(), theBasictermsPackage.getTerm(), null, "otherwiseTerm", null, 0, 1, CaseTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getCaseTerm_ResultTerms(), theBasictermsPackage.getTermDT(), "resultTerms", null, 1, -1, CaseTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bagTermEClass, BagTerm.class, "BagTerm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBagTerm_Term(), theBasictermsPackage.getTerm(), null, "term", null, 0, -1, BagTerm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(bagCtEClass, BagCt.class, "BagCt", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //FurthertermsPackageImpl

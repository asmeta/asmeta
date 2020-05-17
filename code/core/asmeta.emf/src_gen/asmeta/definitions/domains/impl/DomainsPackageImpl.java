/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.BasicTd;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.CharDomain;
import asmeta.definitions.domains.ComplexDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.DomainsFactory;
import asmeta.definitions.domains.DomainsPackage;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.ReserveDomain;
import asmeta.definitions.domains.RuleDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StringDomain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.definitions.domains.TypeDomain;
import asmeta.definitions.domains.UndefDomain;
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
public class DomainsPackageImpl extends EPackageImpl implements DomainsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass naturalDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass undefDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structuredTdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sequenceDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass reserveDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass realDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass productDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass powersetDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumTdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass domainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass concreteDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass complexDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass charDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass basicTdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bagDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anyDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agentDomainEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractTdEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType domainDTEDataType = null;

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
	 * @see asmeta.definitions.domains.DomainsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DomainsPackageImpl() {
		super(eNS_URI, DomainsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link DomainsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DomainsPackage init() {
		if (isInited) return (DomainsPackage)EPackage.Registry.INSTANCE.getEPackage(DomainsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredDomainsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		DomainsPackageImpl theDomainsPackage = registeredDomainsPackage instanceof DomainsPackageImpl ? (DomainsPackageImpl)registeredDomainsPackage : new DomainsPackageImpl();

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
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(DefinitionsPackage.eNS_URI);
		DefinitionsPackageImpl theDefinitionsPackage = (DefinitionsPackageImpl)(registeredPackage instanceof DefinitionsPackageImpl ? registeredPackage : DefinitionsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(PrimitivetypesPackage.eNS_URI);
		PrimitivetypesPackageImpl thePrimitivetypesPackage = (PrimitivetypesPackageImpl)(registeredPackage instanceof PrimitivetypesPackageImpl ? registeredPackage : PrimitivetypesPackage.eINSTANCE);

		// Create package meta-data objects
		theDomainsPackage.createPackageContents();
		theFurthertermsPackage.createPackageContents();
		theBasictermsPackage.createPackageContents();
		theStructurePackage.createPackageContents();
		theTurbotransitionrulesPackage.createPackageContents();
		theDerivedtransitionrulesPackage.createPackageContents();
		theBasictransitionrulesPackage.createPackageContents();
		theDefinitionsPackage.createPackageContents();
		thePrimitivetypesPackage.createPackageContents();

		// Initialize created meta-data
		theDomainsPackage.initializePackageContents();
		theFurthertermsPackage.initializePackageContents();
		theBasictermsPackage.initializePackageContents();
		theStructurePackage.initializePackageContents();
		theTurbotransitionrulesPackage.initializePackageContents();
		theDerivedtransitionrulesPackage.initializePackageContents();
		theBasictransitionrulesPackage.initializePackageContents();
		theDefinitionsPackage.initializePackageContents();
		thePrimitivetypesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDomainsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DomainsPackage.eNS_URI, theDomainsPackage);
		return theDomainsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNaturalDomain() {
		return naturalDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUndefDomain() {
		return undefDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTypeDomain() {
		return typeDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStructuredTd() {
		return structuredTdEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStringDomain() {
		return stringDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSequenceDomain() {
		return sequenceDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSequenceDomain_Domain() {
		return (EReference)sequenceDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRuleDomain() {
		return ruleDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRuleDomain_Domains() {
		return (EAttribute)ruleDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReserveDomain() {
		return reserveDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRealDomain() {
		return realDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getProductDomain() {
		return productDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getProductDomain_Domains() {
		return (EAttribute)productDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPowersetDomain() {
		return powersetDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPowersetDomain_BaseDomain() {
		return (EReference)powersetDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMapDomain() {
		return mapDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMapDomain_SourceDomain() {
		return (EReference)mapDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMapDomain_TargetDomain() {
		return (EReference)mapDomainEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIntegerDomain() {
		return integerDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEnumTd() {
		return enumTdEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEnumTd_Element() {
		return (EReference)enumTdEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEnumElement() {
		return enumElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEnumElement_Symbol() {
		return (EAttribute)enumElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDomain() {
		return domainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDomain_Constraint() {
		return (EReference)domainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDomain_Signature() {
		return (EReference)domainEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConcreteDomain() {
		return concreteDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConcreteDomain_Initialization() {
		return (EReference)concreteDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConcreteDomain_Definition() {
		return (EReference)concreteDomainEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConcreteDomain_TypeDomain() {
		return (EReference)concreteDomainEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getConcreteDomain_IsDynamic() {
		return (EAttribute)concreteDomainEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getComplexDomain() {
		return complexDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCharDomain() {
		return charDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBooleanDomain() {
		return booleanDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBasicTd() {
		return basicTdEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBagDomain() {
		return bagDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBagDomain_Domain() {
		return (EReference)bagDomainEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnyDomain() {
		return anyDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAgentDomain() {
		return agentDomainEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractTd() {
		return abstractTdEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractTd_IsDynamic() {
		return (EAttribute)abstractTdEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EDataType getDomainDT() {
		return domainDTEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DomainsFactory getDomainsFactory() {
		return (DomainsFactory)getEFactoryInstance();
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
		naturalDomainEClass = createEClass(NATURAL_DOMAIN);

		undefDomainEClass = createEClass(UNDEF_DOMAIN);

		typeDomainEClass = createEClass(TYPE_DOMAIN);

		structuredTdEClass = createEClass(STRUCTURED_TD);

		stringDomainEClass = createEClass(STRING_DOMAIN);

		sequenceDomainEClass = createEClass(SEQUENCE_DOMAIN);
		createEReference(sequenceDomainEClass, SEQUENCE_DOMAIN__DOMAIN);

		ruleDomainEClass = createEClass(RULE_DOMAIN);
		createEAttribute(ruleDomainEClass, RULE_DOMAIN__DOMAINS);

		reserveDomainEClass = createEClass(RESERVE_DOMAIN);

		realDomainEClass = createEClass(REAL_DOMAIN);

		productDomainEClass = createEClass(PRODUCT_DOMAIN);
		createEAttribute(productDomainEClass, PRODUCT_DOMAIN__DOMAINS);

		powersetDomainEClass = createEClass(POWERSET_DOMAIN);
		createEReference(powersetDomainEClass, POWERSET_DOMAIN__BASE_DOMAIN);

		mapDomainEClass = createEClass(MAP_DOMAIN);
		createEReference(mapDomainEClass, MAP_DOMAIN__SOURCE_DOMAIN);
		createEReference(mapDomainEClass, MAP_DOMAIN__TARGET_DOMAIN);

		integerDomainEClass = createEClass(INTEGER_DOMAIN);

		enumTdEClass = createEClass(ENUM_TD);
		createEReference(enumTdEClass, ENUM_TD__ELEMENT);

		enumElementEClass = createEClass(ENUM_ELEMENT);
		createEAttribute(enumElementEClass, ENUM_ELEMENT__SYMBOL);

		domainEClass = createEClass(DOMAIN);
		createEReference(domainEClass, DOMAIN__CONSTRAINT);
		createEReference(domainEClass, DOMAIN__SIGNATURE);

		concreteDomainEClass = createEClass(CONCRETE_DOMAIN);
		createEReference(concreteDomainEClass, CONCRETE_DOMAIN__INITIALIZATION);
		createEReference(concreteDomainEClass, CONCRETE_DOMAIN__DEFINITION);
		createEReference(concreteDomainEClass, CONCRETE_DOMAIN__TYPE_DOMAIN);
		createEAttribute(concreteDomainEClass, CONCRETE_DOMAIN__IS_DYNAMIC);

		complexDomainEClass = createEClass(COMPLEX_DOMAIN);

		charDomainEClass = createEClass(CHAR_DOMAIN);

		booleanDomainEClass = createEClass(BOOLEAN_DOMAIN);

		basicTdEClass = createEClass(BASIC_TD);

		bagDomainEClass = createEClass(BAG_DOMAIN);
		createEReference(bagDomainEClass, BAG_DOMAIN__DOMAIN);

		anyDomainEClass = createEClass(ANY_DOMAIN);

		agentDomainEClass = createEClass(AGENT_DOMAIN);

		abstractTdEClass = createEClass(ABSTRACT_TD);
		createEAttribute(abstractTdEClass, ABSTRACT_TD__IS_DYNAMIC);

		// Create data types
		domainDTEDataType = createEDataType(DOMAIN_DT);
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
		PrimitivetypesPackage thePrimitivetypesPackage = (PrimitivetypesPackage)EPackage.Registry.INSTANCE.getEPackage(PrimitivetypesPackage.eNS_URI);
		DefinitionsPackage theDefinitionsPackage = (DefinitionsPackage)EPackage.Registry.INSTANCE.getEPackage(DefinitionsPackage.eNS_URI);
		StructurePackage theStructurePackage = (StructurePackage)EPackage.Registry.INSTANCE.getEPackage(StructurePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		naturalDomainEClass.getESuperTypes().add(this.getIntegerDomain());
		undefDomainEClass.getESuperTypes().add(this.getBasicTd());
		typeDomainEClass.getESuperTypes().add(this.getDomain());
		structuredTdEClass.getESuperTypes().add(this.getTypeDomain());
		stringDomainEClass.getESuperTypes().add(this.getBasicTd());
		sequenceDomainEClass.getESuperTypes().add(this.getStructuredTd());
		ruleDomainEClass.getESuperTypes().add(this.getStructuredTd());
		reserveDomainEClass.getESuperTypes().add(this.getAbstractTd());
		realDomainEClass.getESuperTypes().add(this.getComplexDomain());
		productDomainEClass.getESuperTypes().add(this.getStructuredTd());
		powersetDomainEClass.getESuperTypes().add(this.getStructuredTd());
		mapDomainEClass.getESuperTypes().add(this.getStructuredTd());
		integerDomainEClass.getESuperTypes().add(this.getRealDomain());
		enumTdEClass.getESuperTypes().add(this.getTypeDomain());
		domainEClass.getESuperTypes().add(theDefinitionsPackage.getClassifier());
		concreteDomainEClass.getESuperTypes().add(this.getDomain());
		complexDomainEClass.getESuperTypes().add(this.getBasicTd());
		charDomainEClass.getESuperTypes().add(this.getBasicTd());
		booleanDomainEClass.getESuperTypes().add(this.getBasicTd());
		basicTdEClass.getESuperTypes().add(this.getTypeDomain());
		bagDomainEClass.getESuperTypes().add(this.getStructuredTd());
		anyDomainEClass.getESuperTypes().add(this.getTypeDomain());
		agentDomainEClass.getESuperTypes().add(this.getAbstractTd());
		abstractTdEClass.getESuperTypes().add(this.getTypeDomain());

		// Initialize classes and features; add operations and parameters
		initEClass(naturalDomainEClass, NaturalDomain.class, "NaturalDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(undefDomainEClass, UndefDomain.class, "UndefDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(typeDomainEClass, TypeDomain.class, "TypeDomain", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(structuredTdEClass, StructuredTd.class, "StructuredTd", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(stringDomainEClass, StringDomain.class, "StringDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(sequenceDomainEClass, SequenceDomain.class, "SequenceDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSequenceDomain_Domain(), this.getDomain(), null, "domain", null, 1, 1, SequenceDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(ruleDomainEClass, RuleDomain.class, "RuleDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleDomain_Domains(), this.getDomainDT(), "domains", null, 0, -1, RuleDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(reserveDomainEClass, ReserveDomain.class, "ReserveDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(realDomainEClass, RealDomain.class, "RealDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(productDomainEClass, ProductDomain.class, "ProductDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProductDomain_Domains(), this.getDomainDT(), "domains", null, 0, -1, ProductDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(powersetDomainEClass, PowersetDomain.class, "PowersetDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPowersetDomain_BaseDomain(), this.getDomain(), null, "baseDomain", null, 1, 1, PowersetDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(mapDomainEClass, MapDomain.class, "MapDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMapDomain_SourceDomain(), this.getDomain(), null, "sourceDomain", null, 1, 1, MapDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getMapDomain_TargetDomain(), this.getDomain(), null, "targetDomain", null, 1, 1, MapDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(integerDomainEClass, IntegerDomain.class, "IntegerDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(enumTdEClass, EnumTd.class, "EnumTd", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEnumTd_Element(), this.getEnumElement(), null, "element", null, 1, -1, EnumTd.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(enumElementEClass, EnumElement.class, "EnumElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnumElement_Symbol(), thePrimitivetypesPackage.getString(), "symbol", null, 1, 1, EnumElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(domainEClass, Domain.class, "Domain", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDomain_Constraint(), theDefinitionsPackage.getInvariant(), theDefinitionsPackage.getInvariant_ConstrainedDomain(), "constraint", null, 0, -1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getDomain_Signature(), theStructurePackage.getSignature(), theStructurePackage.getSignature_Domain(), "signature", null, 0, 1, Domain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(domainEClass, null, "compatible", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(concreteDomainEClass, ConcreteDomain.class, "ConcreteDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConcreteDomain_Initialization(), theStructurePackage.getDomainInitialization(), theStructurePackage.getDomainInitialization_InitializedDomain(), "initialization", null, 0, -1, ConcreteDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getConcreteDomain_Definition(), theStructurePackage.getDomainDefinition(), theStructurePackage.getDomainDefinition_DefinedDomain(), "definition", null, 0, 1, ConcreteDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getConcreteDomain_TypeDomain(), this.getTypeDomain(), null, "typeDomain", null, 1, 1, ConcreteDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getConcreteDomain_IsDynamic(), thePrimitivetypesPackage.getBoolean(), "isDynamic", null, 1, 1, ConcreteDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(complexDomainEClass, ComplexDomain.class, "ComplexDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(charDomainEClass, CharDomain.class, "CharDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(booleanDomainEClass, BooleanDomain.class, "BooleanDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(basicTdEClass, BasicTd.class, "BasicTd", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(bagDomainEClass, BagDomain.class, "BagDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBagDomain_Domain(), this.getDomain(), null, "domain", null, 1, 1, BagDomain.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(anyDomainEClass, AnyDomain.class, "AnyDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(agentDomainEClass, AgentDomain.class, "AgentDomain", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(abstractTdEClass, AbstractTd.class, "AbstractTd", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractTd_IsDynamic(), thePrimitivetypesPackage.getBoolean(), "isDynamic", null, 1, 1, AbstractTd.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		// Initialize data types
		initEDataType(domainDTEDataType, Domain.class, "DomainDT", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
	}

} //DomainsPackageImpl

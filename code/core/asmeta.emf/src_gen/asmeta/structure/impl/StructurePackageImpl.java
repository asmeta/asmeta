/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import asmeta.definitions.DefinitionsPackage;
import asmeta.definitions.domains.DomainsPackage;
import asmeta.definitions.domains.impl.DomainsPackageImpl;
import asmeta.definitions.impl.DefinitionsPackageImpl;
import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
import asmeta.structure.Body;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.structure.ExportClause;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Header;
import asmeta.structure.ImportClause;
import asmeta.structure.Initialization;
import asmeta.structure.NamedElement;
import asmeta.structure.Signature;
import asmeta.structure.StructureFactory;
import asmeta.structure.StructurePackage;
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
public class StructurePackageImpl extends EPackageImpl implements StructurePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agentInitializationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bodyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionInitializationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass domainInitializationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass signatureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass exportClauseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importClauseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass functionDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass domainDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass initializationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass headerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass asmEClass = null;

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
	 * @see asmeta.structure.StructurePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private StructurePackageImpl() {
		super(eNS_URI, StructureFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link StructurePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static StructurePackage init() {
		if (isInited) return (StructurePackage)EPackage.Registry.INSTANCE.getEPackage(StructurePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredStructurePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		StructurePackageImpl theStructurePackage = registeredStructurePackage instanceof StructurePackageImpl ? (StructurePackageImpl)registeredStructurePackage : new StructurePackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(FurthertermsPackage.eNS_URI);
		FurthertermsPackageImpl theFurthertermsPackage = (FurthertermsPackageImpl)(registeredPackage instanceof FurthertermsPackageImpl ? registeredPackage : FurthertermsPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(BasictermsPackage.eNS_URI);
		BasictermsPackageImpl theBasictermsPackage = (BasictermsPackageImpl)(registeredPackage instanceof BasictermsPackageImpl ? registeredPackage : BasictermsPackage.eINSTANCE);
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
		theStructurePackage.createPackageContents();
		theFurthertermsPackage.createPackageContents();
		theBasictermsPackage.createPackageContents();
		theTurbotransitionrulesPackage.createPackageContents();
		theDerivedtransitionrulesPackage.createPackageContents();
		theBasictransitionrulesPackage.createPackageContents();
		theDefinitionsPackage.createPackageContents();
		theDomainsPackage.createPackageContents();
		thePrimitivetypesPackage.createPackageContents();

		// Initialize created meta-data
		theStructurePackage.initializePackageContents();
		theFurthertermsPackage.initializePackageContents();
		theBasictermsPackage.initializePackageContents();
		theTurbotransitionrulesPackage.initializePackageContents();
		theDerivedtransitionrulesPackage.initializePackageContents();
		theBasictransitionrulesPackage.initializePackageContents();
		theDefinitionsPackage.initializePackageContents();
		theDomainsPackage.initializePackageContents();
		thePrimitivetypesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theStructurePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(StructurePackage.eNS_URI, theStructurePackage);
		return theStructurePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNamedElement() {
		return namedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getNamedElement_Name() {
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAgentInitialization() {
		return agentInitializationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAgentInitialization_Program() {
		return (EReference)agentInitializationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAgentInitialization_Domain() {
		return (EReference)agentInitializationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAgentInitialization_InitialState() {
		return (EReference)agentInitializationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBody() {
		return bodyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBody_FunctionDefinition() {
		return (EReference)bodyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBody_Property() {
		return (EReference)bodyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBody_DomainDefinition() {
		return (EReference)bodyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBody_RuleDeclaration() {
		return (EReference)bodyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBody_Asm() {
		return (EReference)bodyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBody_FairnessConstraint() {
		return (EReference)bodyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getBody_InvariantConstraint() {
		return (EReference)bodyEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFunctionInitialization() {
		return functionInitializationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionInitialization_InitialState() {
		return (EReference)functionInitializationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionInitialization_Body() {
		return (EReference)functionInitializationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionInitialization_InitializedFunction() {
		return (EReference)functionInitializationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionInitialization_Variable() {
		return (EReference)functionInitializationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDomainInitialization() {
		return domainInitializationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDomainInitialization_InitializedDomain() {
		return (EReference)domainInitializationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDomainInitialization_Body() {
		return (EReference)domainInitializationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDomainInitialization_InitialState() {
		return (EReference)domainInitializationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSignature() {
		return signatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSignature_Domain() {
		return (EReference)signatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSignature_Function() {
		return (EReference)signatureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSignature_HeaderSection() {
		return (EReference)signatureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSignature_StructuredDomain() {
		return (EReference)signatureEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExportClause() {
		return exportClauseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExportClause_ExportedFunction() {
		return (EReference)exportClauseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExportClause_ExportedDomain() {
		return (EReference)exportClauseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExportClause_ExportedRule() {
		return (EReference)exportClauseEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getImportClause() {
		return importClauseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getImportClause_ImportedDomain() {
		return (EReference)importClauseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getImportClause_ImportedFunction() {
		return (EReference)importClauseEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getImportClause_ImportedRule() {
		return (EReference)importClauseEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getImportClause_ModuleName() {
		return (EAttribute)importClauseEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFunctionDefinition() {
		return functionDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionDefinition_Body() {
		return (EReference)functionDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionDefinition_Variable() {
		return (EReference)functionDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFunctionDefinition_DefinedFunction() {
		return (EReference)functionDefinitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDomainDefinition() {
		return domainDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDomainDefinition_Body() {
		return (EReference)domainDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDomainDefinition_DefinedDomain() {
		return (EReference)domainDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInitialization() {
		return initializationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInitialization_DomainInitialization() {
		return (EReference)initializationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInitialization_FunctionInitialization() {
		return (EReference)initializationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInitialization_AgentInitialization() {
		return (EReference)initializationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInitialization_Asm() {
		return (EReference)initializationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getHeader() {
		return headerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHeader_ImportClause() {
		return (EReference)headerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHeader_Signature() {
		return (EReference)headerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHeader_ExportClause() {
		return (EReference)headerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getHeader_Asm() {
		return (EReference)headerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAsm() {
		return asmEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAsm_InitialState() {
		return (EReference)asmEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAsm_DefaultInitialState() {
		return (EReference)asmEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAsm_BodySection() {
		return (EReference)asmEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAsm_HeaderSection() {
		return (EReference)asmEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAsm_Mainrule() {
		return (EReference)asmEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAsm_IsAsynchr() {
		return (EAttribute)asmEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StructureFactory getStructureFactory() {
		return (StructureFactory)getEFactoryInstance();
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
		namedElementEClass = createEClass(NAMED_ELEMENT);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);

		agentInitializationEClass = createEClass(AGENT_INITIALIZATION);
		createEReference(agentInitializationEClass, AGENT_INITIALIZATION__PROGRAM);
		createEReference(agentInitializationEClass, AGENT_INITIALIZATION__DOMAIN);
		createEReference(agentInitializationEClass, AGENT_INITIALIZATION__INITIAL_STATE);

		bodyEClass = createEClass(BODY);
		createEReference(bodyEClass, BODY__FUNCTION_DEFINITION);
		createEReference(bodyEClass, BODY__PROPERTY);
		createEReference(bodyEClass, BODY__DOMAIN_DEFINITION);
		createEReference(bodyEClass, BODY__RULE_DECLARATION);
		createEReference(bodyEClass, BODY__ASM);
		createEReference(bodyEClass, BODY__FAIRNESS_CONSTRAINT);
		createEReference(bodyEClass, BODY__INVARIANT_CONSTRAINT);

		functionInitializationEClass = createEClass(FUNCTION_INITIALIZATION);
		createEReference(functionInitializationEClass, FUNCTION_INITIALIZATION__INITIAL_STATE);
		createEReference(functionInitializationEClass, FUNCTION_INITIALIZATION__BODY);
		createEReference(functionInitializationEClass, FUNCTION_INITIALIZATION__INITIALIZED_FUNCTION);
		createEReference(functionInitializationEClass, FUNCTION_INITIALIZATION__VARIABLE);

		domainInitializationEClass = createEClass(DOMAIN_INITIALIZATION);
		createEReference(domainInitializationEClass, DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN);
		createEReference(domainInitializationEClass, DOMAIN_INITIALIZATION__BODY);
		createEReference(domainInitializationEClass, DOMAIN_INITIALIZATION__INITIAL_STATE);

		signatureEClass = createEClass(SIGNATURE);
		createEReference(signatureEClass, SIGNATURE__DOMAIN);
		createEReference(signatureEClass, SIGNATURE__FUNCTION);
		createEReference(signatureEClass, SIGNATURE__HEADER_SECTION);
		createEReference(signatureEClass, SIGNATURE__STRUCTURED_DOMAIN);

		exportClauseEClass = createEClass(EXPORT_CLAUSE);
		createEReference(exportClauseEClass, EXPORT_CLAUSE__EXPORTED_FUNCTION);
		createEReference(exportClauseEClass, EXPORT_CLAUSE__EXPORTED_DOMAIN);
		createEReference(exportClauseEClass, EXPORT_CLAUSE__EXPORTED_RULE);

		importClauseEClass = createEClass(IMPORT_CLAUSE);
		createEReference(importClauseEClass, IMPORT_CLAUSE__IMPORTED_DOMAIN);
		createEReference(importClauseEClass, IMPORT_CLAUSE__IMPORTED_FUNCTION);
		createEReference(importClauseEClass, IMPORT_CLAUSE__IMPORTED_RULE);
		createEAttribute(importClauseEClass, IMPORT_CLAUSE__MODULE_NAME);

		functionDefinitionEClass = createEClass(FUNCTION_DEFINITION);
		createEReference(functionDefinitionEClass, FUNCTION_DEFINITION__BODY);
		createEReference(functionDefinitionEClass, FUNCTION_DEFINITION__VARIABLE);
		createEReference(functionDefinitionEClass, FUNCTION_DEFINITION__DEFINED_FUNCTION);

		domainDefinitionEClass = createEClass(DOMAIN_DEFINITION);
		createEReference(domainDefinitionEClass, DOMAIN_DEFINITION__BODY);
		createEReference(domainDefinitionEClass, DOMAIN_DEFINITION__DEFINED_DOMAIN);

		initializationEClass = createEClass(INITIALIZATION);
		createEReference(initializationEClass, INITIALIZATION__DOMAIN_INITIALIZATION);
		createEReference(initializationEClass, INITIALIZATION__FUNCTION_INITIALIZATION);
		createEReference(initializationEClass, INITIALIZATION__AGENT_INITIALIZATION);
		createEReference(initializationEClass, INITIALIZATION__ASM);

		headerEClass = createEClass(HEADER);
		createEReference(headerEClass, HEADER__IMPORT_CLAUSE);
		createEReference(headerEClass, HEADER__SIGNATURE);
		createEReference(headerEClass, HEADER__EXPORT_CLAUSE);
		createEReference(headerEClass, HEADER__ASM);

		asmEClass = createEClass(ASM);
		createEReference(asmEClass, ASM__INITIAL_STATE);
		createEReference(asmEClass, ASM__DEFAULT_INITIAL_STATE);
		createEReference(asmEClass, ASM__BODY_SECTION);
		createEReference(asmEClass, ASM__HEADER_SECTION);
		createEReference(asmEClass, ASM__MAINRULE);
		createEAttribute(asmEClass, ASM__IS_ASYNCHR);
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
		BasictransitionrulesPackage theBasictransitionrulesPackage = (BasictransitionrulesPackage)EPackage.Registry.INSTANCE.getEPackage(BasictransitionrulesPackage.eNS_URI);
		DomainsPackage theDomainsPackage = (DomainsPackage)EPackage.Registry.INSTANCE.getEPackage(DomainsPackage.eNS_URI);
		DefinitionsPackage theDefinitionsPackage = (DefinitionsPackage)EPackage.Registry.INSTANCE.getEPackage(DefinitionsPackage.eNS_URI);
		BasictermsPackage theBasictermsPackage = (BasictermsPackage)EPackage.Registry.INSTANCE.getEPackage(BasictermsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		initializationEClass.getESuperTypes().add(this.getNamedElement());
		asmEClass.getESuperTypes().add(this.getNamedElement());

		// Initialize classes and features; add operations and parameters
		initEClass(namedElementEClass, NamedElement.class, "NamedElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamedElement_Name(), thePrimitivetypesPackage.getString(), "name", null, 1, 1, NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(agentInitializationEClass, AgentInitialization.class, "AgentInitialization", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAgentInitialization_Program(), theBasictransitionrulesPackage.getMacroCallRule(), null, "program", null, 1, 1, AgentInitialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getAgentInitialization_Domain(), theDomainsPackage.getDomain(), null, "domain", null, 1, 1, AgentInitialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getAgentInitialization_InitialState(), this.getInitialization(), this.getInitialization_AgentInitialization(), "initialState", null, 1, 1, AgentInitialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(bodyEClass, Body.class, "Body", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBody_FunctionDefinition(), this.getFunctionDefinition(), null, "functionDefinition", null, 0, -1, Body.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getBody_Property(), theDefinitionsPackage.getProperty(), null, "property", null, 0, -1, Body.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEReference(getBody_DomainDefinition(), this.getDomainDefinition(), null, "domainDefinition", null, 0, -1, Body.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getBody_RuleDeclaration(), theDefinitionsPackage.getRuleDeclaration(), theDefinitionsPackage.getRuleDeclaration_AsmBody(), "ruleDeclaration", null, 0, -1, Body.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getBody_Asm(), this.getAsm(), this.getAsm_BodySection(), "asm", null, 0, 1, Body.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBody_FairnessConstraint(), theDefinitionsPackage.getFairnessConstraint(), null, "fairnessConstraint", null, 0, -1, Body.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);
		initEReference(getBody_InvariantConstraint(), theDefinitionsPackage.getInvarConstraint(), null, "invariantConstraint", null, 0, -1, Body.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, !IS_ORDERED);

		initEClass(functionInitializationEClass, FunctionInitialization.class, "FunctionInitialization", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFunctionInitialization_InitialState(), this.getInitialization(), this.getInitialization_FunctionInitialization(), "initialState", null, 1, 1, FunctionInitialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getFunctionInitialization_Body(), theBasictermsPackage.getTerm(), null, "body", null, 1, 1, FunctionInitialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getFunctionInitialization_InitializedFunction(), theDefinitionsPackage.getDynamicFunction(), theDefinitionsPackage.getDynamicFunction_Initialization(), "initializedFunction", null, 1, 1, FunctionInitialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getFunctionInitialization_Variable(), theBasictermsPackage.getVariableTerm(), null, "variable", null, 0, -1, FunctionInitialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(domainInitializationEClass, DomainInitialization.class, "DomainInitialization", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDomainInitialization_InitializedDomain(), theDomainsPackage.getConcreteDomain(), theDomainsPackage.getConcreteDomain_Initialization(), "initializedDomain", null, 1, 1, DomainInitialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getDomainInitialization_Body(), theBasictermsPackage.getTerm(), null, "body", null, 1, 1, DomainInitialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getDomainInitialization_InitialState(), this.getInitialization(), this.getInitialization_DomainInitialization(), "initialState", null, 1, 1, DomainInitialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(signatureEClass, Signature.class, "Signature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSignature_Domain(), theDomainsPackage.getDomain(), theDomainsPackage.getDomain_Signature(), "domain", null, 0, -1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getSignature_Function(), theDefinitionsPackage.getFunction(), theDefinitionsPackage.getFunction_Signature(), "function", null, 0, -1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getSignature_HeaderSection(), this.getHeader(), this.getHeader_Signature(), "headerSection", null, 1, 1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSignature_StructuredDomain(), theDomainsPackage.getStructuredTd(), null, "structuredDomain", null, 0, -1, Signature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(exportClauseEClass, ExportClause.class, "ExportClause", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExportClause_ExportedFunction(), theDefinitionsPackage.getFunction(), null, "exportedFunction", null, 0, -1, ExportClause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getExportClause_ExportedDomain(), theDomainsPackage.getDomain(), null, "exportedDomain", null, 0, -1, ExportClause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getExportClause_ExportedRule(), theDefinitionsPackage.getRuleDeclaration(), null, "exportedRule", null, 0, -1, ExportClause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(importClauseEClass, ImportClause.class, "ImportClause", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getImportClause_ImportedDomain(), theDomainsPackage.getDomain(), null, "importedDomain", null, 0, -1, ImportClause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getImportClause_ImportedFunction(), theDefinitionsPackage.getFunction(), null, "importedFunction", null, 0, -1, ImportClause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getImportClause_ImportedRule(), theDefinitionsPackage.getRuleDeclaration(), null, "importedRule", null, 0, -1, ImportClause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getImportClause_ModuleName(), thePrimitivetypesPackage.getString(), "moduleName", null, 1, 1, ImportClause.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(functionDefinitionEClass, FunctionDefinition.class, "FunctionDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFunctionDefinition_Body(), theBasictermsPackage.getTerm(), null, "body", null, 1, 1, FunctionDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getFunctionDefinition_Variable(), theBasictermsPackage.getVariableTerm(), null, "variable", null, 0, -1, FunctionDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFunctionDefinition_DefinedFunction(), theDefinitionsPackage.getFunction(), theDefinitionsPackage.getFunction_Definition(), "definedFunction", null, 1, 1, FunctionDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(domainDefinitionEClass, DomainDefinition.class, "DomainDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDomainDefinition_Body(), theBasictermsPackage.getTerm(), null, "body", null, 1, 1, DomainDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getDomainDefinition_DefinedDomain(), theDomainsPackage.getConcreteDomain(), theDomainsPackage.getConcreteDomain_Definition(), "definedDomain", null, 1, 1, DomainDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(initializationEClass, Initialization.class, "Initialization", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInitialization_DomainInitialization(), this.getDomainInitialization(), this.getDomainInitialization_InitialState(), "domainInitialization", null, 0, -1, Initialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getInitialization_FunctionInitialization(), this.getFunctionInitialization(), this.getFunctionInitialization_InitialState(), "functionInitialization", null, 0, -1, Initialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getInitialization_AgentInitialization(), this.getAgentInitialization(), this.getAgentInitialization_InitialState(), "agentInitialization", null, 0, -1, Initialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getInitialization_Asm(), this.getAsm(), this.getAsm_DefaultInitialState(), "asm", null, 1, 1, Initialization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(headerEClass, Header.class, "Header", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHeader_ImportClause(), this.getImportClause(), null, "importClause", null, 0, -1, Header.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getHeader_Signature(), this.getSignature(), this.getSignature_HeaderSection(), "signature", null, 1, 1, Header.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getHeader_ExportClause(), this.getExportClause(), null, "exportClause", null, 0, 1, Header.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getHeader_Asm(), this.getAsm(), this.getAsm_HeaderSection(), "asm", null, 0, 1, Header.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(asmEClass, Asm.class, "Asm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAsm_InitialState(), this.getInitialization(), null, "initialState", null, 0, -1, Asm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getAsm_DefaultInitialState(), this.getInitialization(), this.getInitialization_Asm(), "defaultInitialState", null, 0, 1, Asm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getAsm_BodySection(), this.getBody(), this.getBody_Asm(), "bodySection", null, 1, 1, Asm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getAsm_HeaderSection(), this.getHeader(), this.getHeader_Asm(), "headerSection", null, 1, 1, Asm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getAsm_Mainrule(), theBasictransitionrulesPackage.getMacroDeclaration(), null, "mainrule", null, 0, 1, Asm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getAsm_IsAsynchr(), thePrimitivetypesPackage.getBoolean(), "isAsynchr", null, 1, 1, Asm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //StructurePackageImpl

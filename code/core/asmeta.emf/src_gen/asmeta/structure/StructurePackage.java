/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see asmeta.structure.StructureFactory
 * @model kind="package"
 * @generated
 */
public interface StructurePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "structure";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.asmeta/asmm#Structure";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "AsmM";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StructurePackage eINSTANCE = asmeta.structure.impl.StructurePackageImpl.init();

	/**
	 * The meta object id for the '{@link asmeta.structure.impl.NamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.structure.impl.NamedElementImpl
	 * @see asmeta.structure.impl.StructurePackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link asmeta.structure.impl.AgentInitializationImpl <em>Agent Initialization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.structure.impl.AgentInitializationImpl
	 * @see asmeta.structure.impl.StructurePackageImpl#getAgentInitialization()
	 * @generated
	 */
	int AGENT_INITIALIZATION = 1;

	/**
	 * The feature id for the '<em><b>Program</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_INITIALIZATION__PROGRAM = 0;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_INITIALIZATION__DOMAIN = 1;

	/**
	 * The feature id for the '<em><b>Initial State</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_INITIALIZATION__INITIAL_STATE = 2;

	/**
	 * The number of structural features of the '<em>Agent Initialization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_INITIALIZATION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link asmeta.structure.impl.BodyImpl <em>Body</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.structure.impl.BodyImpl
	 * @see asmeta.structure.impl.StructurePackageImpl#getBody()
	 * @generated
	 */
	int BODY = 2;

	/**
	 * The feature id for the '<em><b>Function Definition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BODY__FUNCTION_DEFINITION = 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BODY__PROPERTY = 1;

	/**
	 * The feature id for the '<em><b>Domain Definition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BODY__DOMAIN_DEFINITION = 2;

	/**
	 * The feature id for the '<em><b>Rule Declaration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BODY__RULE_DECLARATION = 3;

	/**
	 * The feature id for the '<em><b>Asm</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BODY__ASM = 4;

	/**
	 * The feature id for the '<em><b>Fairness Constraint</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BODY__FAIRNESS_CONSTRAINT = 5;

	/**
	 * The feature id for the '<em><b>Invariant Constraint</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BODY__INVARIANT_CONSTRAINT = 6;

	/**
	 * The number of structural features of the '<em>Body</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BODY_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link asmeta.structure.impl.FunctionInitializationImpl <em>Function Initialization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.structure.impl.FunctionInitializationImpl
	 * @see asmeta.structure.impl.StructurePackageImpl#getFunctionInitialization()
	 * @generated
	 */
	int FUNCTION_INITIALIZATION = 3;

	/**
	 * The feature id for the '<em><b>Initial State</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_INITIALIZATION__INITIAL_STATE = 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_INITIALIZATION__BODY = 1;

	/**
	 * The feature id for the '<em><b>Initialized Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_INITIALIZATION__INITIALIZED_FUNCTION = 2;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_INITIALIZATION__VARIABLE = 3;

	/**
	 * The number of structural features of the '<em>Function Initialization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_INITIALIZATION_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link asmeta.structure.impl.DomainInitializationImpl <em>Domain Initialization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.structure.impl.DomainInitializationImpl
	 * @see asmeta.structure.impl.StructurePackageImpl#getDomainInitialization()
	 * @generated
	 */
	int DOMAIN_INITIALIZATION = 4;

	/**
	 * The feature id for the '<em><b>Initialized Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN = 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_INITIALIZATION__BODY = 1;

	/**
	 * The feature id for the '<em><b>Initial State</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_INITIALIZATION__INITIAL_STATE = 2;

	/**
	 * The number of structural features of the '<em>Domain Initialization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_INITIALIZATION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link asmeta.structure.impl.SignatureImpl <em>Signature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.structure.impl.SignatureImpl
	 * @see asmeta.structure.impl.StructurePackageImpl#getSignature()
	 * @generated
	 */
	int SIGNATURE = 5;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE__DOMAIN = 0;

	/**
	 * The feature id for the '<em><b>Function</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE__FUNCTION = 1;

	/**
	 * The feature id for the '<em><b>Header Section</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE__HEADER_SECTION = 2;

	/**
	 * The feature id for the '<em><b>Structured Domain</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE__STRUCTURED_DOMAIN = 3;

	/**
	 * The number of structural features of the '<em>Signature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIGNATURE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link asmeta.structure.impl.ExportClauseImpl <em>Export Clause</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.structure.impl.ExportClauseImpl
	 * @see asmeta.structure.impl.StructurePackageImpl#getExportClause()
	 * @generated
	 */
	int EXPORT_CLAUSE = 6;

	/**
	 * The feature id for the '<em><b>Exported Function</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_CLAUSE__EXPORTED_FUNCTION = 0;

	/**
	 * The feature id for the '<em><b>Exported Domain</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_CLAUSE__EXPORTED_DOMAIN = 1;

	/**
	 * The feature id for the '<em><b>Exported Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_CLAUSE__EXPORTED_RULE = 2;

	/**
	 * The number of structural features of the '<em>Export Clause</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_CLAUSE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link asmeta.structure.impl.ImportClauseImpl <em>Import Clause</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.structure.impl.ImportClauseImpl
	 * @see asmeta.structure.impl.StructurePackageImpl#getImportClause()
	 * @generated
	 */
	int IMPORT_CLAUSE = 7;

	/**
	 * The feature id for the '<em><b>Imported Domain</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_CLAUSE__IMPORTED_DOMAIN = 0;

	/**
	 * The feature id for the '<em><b>Imported Function</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_CLAUSE__IMPORTED_FUNCTION = 1;

	/**
	 * The feature id for the '<em><b>Imported Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_CLAUSE__IMPORTED_RULE = 2;

	/**
	 * The feature id for the '<em><b>Module Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_CLAUSE__MODULE_NAME = 3;

	/**
	 * The number of structural features of the '<em>Import Clause</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_CLAUSE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link asmeta.structure.impl.FunctionDefinitionImpl <em>Function Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.structure.impl.FunctionDefinitionImpl
	 * @see asmeta.structure.impl.StructurePackageImpl#getFunctionDefinition()
	 * @generated
	 */
	int FUNCTION_DEFINITION = 8;

	/**
	 * The feature id for the '<em><b>Body</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION__BODY = 0;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION__VARIABLE = 1;

	/**
	 * The feature id for the '<em><b>Defined Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION__DEFINED_FUNCTION = 2;

	/**
	 * The number of structural features of the '<em>Function Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link asmeta.structure.impl.DomainDefinitionImpl <em>Domain Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.structure.impl.DomainDefinitionImpl
	 * @see asmeta.structure.impl.StructurePackageImpl#getDomainDefinition()
	 * @generated
	 */
	int DOMAIN_DEFINITION = 9;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_DEFINITION__BODY = 0;

	/**
	 * The feature id for the '<em><b>Defined Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_DEFINITION__DEFINED_DOMAIN = 1;

	/**
	 * The number of structural features of the '<em>Domain Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_DEFINITION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link asmeta.structure.impl.InitializationImpl <em>Initialization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.structure.impl.InitializationImpl
	 * @see asmeta.structure.impl.StructurePackageImpl#getInitialization()
	 * @generated
	 */
	int INITIALIZATION = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZATION__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Domain Initialization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZATION__DOMAIN_INITIALIZATION = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Function Initialization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZATION__FUNCTION_INITIALIZATION = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Agent Initialization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZATION__AGENT_INITIALIZATION = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Asm</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZATION__ASM = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Initialization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INITIALIZATION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link asmeta.structure.impl.HeaderImpl <em>Header</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.structure.impl.HeaderImpl
	 * @see asmeta.structure.impl.StructurePackageImpl#getHeader()
	 * @generated
	 */
	int HEADER = 11;

	/**
	 * The feature id for the '<em><b>Import Clause</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__IMPORT_CLAUSE = 0;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__SIGNATURE = 1;

	/**
	 * The feature id for the '<em><b>Export Clause</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__EXPORT_CLAUSE = 2;

	/**
	 * The feature id for the '<em><b>Asm</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER__ASM = 3;

	/**
	 * The number of structural features of the '<em>Header</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEADER_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link asmeta.structure.impl.AsmImpl <em>Asm</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.structure.impl.AsmImpl
	 * @see asmeta.structure.impl.StructurePackageImpl#getAsm()
	 * @generated
	 */
	int ASM = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASM__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Initial State</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASM__INITIAL_STATE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default Initial State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASM__DEFAULT_INITIAL_STATE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Body Section</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASM__BODY_SECTION = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Header Section</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASM__HEADER_SECTION = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Mainrule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASM__MAINRULE = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Is Asynchr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASM__IS_ASYNCHR = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Asm</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASM_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 6;


	/**
	 * Returns the meta object for class '{@link asmeta.structure.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see asmeta.structure.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link asmeta.structure.NamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see asmeta.structure.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link asmeta.structure.AgentInitialization <em>Agent Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Agent Initialization</em>'.
	 * @see asmeta.structure.AgentInitialization
	 * @generated
	 */
	EClass getAgentInitialization();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.structure.AgentInitialization#getProgram <em>Program</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Program</em>'.
	 * @see asmeta.structure.AgentInitialization#getProgram()
	 * @see #getAgentInitialization()
	 * @generated
	 */
	EReference getAgentInitialization_Program();

	/**
	 * Returns the meta object for the reference '{@link asmeta.structure.AgentInitialization#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Domain</em>'.
	 * @see asmeta.structure.AgentInitialization#getDomain()
	 * @see #getAgentInitialization()
	 * @generated
	 */
	EReference getAgentInitialization_Domain();

	/**
	 * Returns the meta object for the container reference '{@link asmeta.structure.AgentInitialization#getInitialState <em>Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Initial State</em>'.
	 * @see asmeta.structure.AgentInitialization#getInitialState()
	 * @see #getAgentInitialization()
	 * @generated
	 */
	EReference getAgentInitialization_InitialState();

	/**
	 * Returns the meta object for class '{@link asmeta.structure.Body <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Body</em>'.
	 * @see asmeta.structure.Body
	 * @generated
	 */
	EClass getBody();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Body#getFunctionDefinition <em>Function Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Function Definition</em>'.
	 * @see asmeta.structure.Body#getFunctionDefinition()
	 * @see #getBody()
	 * @generated
	 */
	EReference getBody_FunctionDefinition();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Body#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see asmeta.structure.Body#getProperty()
	 * @see #getBody()
	 * @generated
	 */
	EReference getBody_Property();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Body#getDomainDefinition <em>Domain Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Domain Definition</em>'.
	 * @see asmeta.structure.Body#getDomainDefinition()
	 * @see #getBody()
	 * @generated
	 */
	EReference getBody_DomainDefinition();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Body#getRuleDeclaration <em>Rule Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rule Declaration</em>'.
	 * @see asmeta.structure.Body#getRuleDeclaration()
	 * @see #getBody()
	 * @generated
	 */
	EReference getBody_RuleDeclaration();

	/**
	 * Returns the meta object for the container reference '{@link asmeta.structure.Body#getAsm <em>Asm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Asm</em>'.
	 * @see asmeta.structure.Body#getAsm()
	 * @see #getBody()
	 * @generated
	 */
	EReference getBody_Asm();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Body#getFairnessConstraint <em>Fairness Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Fairness Constraint</em>'.
	 * @see asmeta.structure.Body#getFairnessConstraint()
	 * @see #getBody()
	 * @generated
	 */
	EReference getBody_FairnessConstraint();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Body#getInvariantConstraint <em>Invariant Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Invariant Constraint</em>'.
	 * @see asmeta.structure.Body#getInvariantConstraint()
	 * @see #getBody()
	 * @generated
	 */
	EReference getBody_InvariantConstraint();

	/**
	 * Returns the meta object for class '{@link asmeta.structure.FunctionInitialization <em>Function Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Initialization</em>'.
	 * @see asmeta.structure.FunctionInitialization
	 * @generated
	 */
	EClass getFunctionInitialization();

	/**
	 * Returns the meta object for the container reference '{@link asmeta.structure.FunctionInitialization#getInitialState <em>Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Initial State</em>'.
	 * @see asmeta.structure.FunctionInitialization#getInitialState()
	 * @see #getFunctionInitialization()
	 * @generated
	 */
	EReference getFunctionInitialization_InitialState();

	/**
	 * Returns the meta object for the reference '{@link asmeta.structure.FunctionInitialization#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Body</em>'.
	 * @see asmeta.structure.FunctionInitialization#getBody()
	 * @see #getFunctionInitialization()
	 * @generated
	 */
	EReference getFunctionInitialization_Body();

	/**
	 * Returns the meta object for the reference '{@link asmeta.structure.FunctionInitialization#getInitializedFunction <em>Initialized Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Initialized Function</em>'.
	 * @see asmeta.structure.FunctionInitialization#getInitializedFunction()
	 * @see #getFunctionInitialization()
	 * @generated
	 */
	EReference getFunctionInitialization_InitializedFunction();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.FunctionInitialization#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variable</em>'.
	 * @see asmeta.structure.FunctionInitialization#getVariable()
	 * @see #getFunctionInitialization()
	 * @generated
	 */
	EReference getFunctionInitialization_Variable();

	/**
	 * Returns the meta object for class '{@link asmeta.structure.DomainInitialization <em>Domain Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain Initialization</em>'.
	 * @see asmeta.structure.DomainInitialization
	 * @generated
	 */
	EClass getDomainInitialization();

	/**
	 * Returns the meta object for the reference '{@link asmeta.structure.DomainInitialization#getInitializedDomain <em>Initialized Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Initialized Domain</em>'.
	 * @see asmeta.structure.DomainInitialization#getInitializedDomain()
	 * @see #getDomainInitialization()
	 * @generated
	 */
	EReference getDomainInitialization_InitializedDomain();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.structure.DomainInitialization#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see asmeta.structure.DomainInitialization#getBody()
	 * @see #getDomainInitialization()
	 * @generated
	 */
	EReference getDomainInitialization_Body();

	/**
	 * Returns the meta object for the container reference '{@link asmeta.structure.DomainInitialization#getInitialState <em>Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Initial State</em>'.
	 * @see asmeta.structure.DomainInitialization#getInitialState()
	 * @see #getDomainInitialization()
	 * @generated
	 */
	EReference getDomainInitialization_InitialState();

	/**
	 * Returns the meta object for class '{@link asmeta.structure.Signature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Signature</em>'.
	 * @see asmeta.structure.Signature
	 * @generated
	 */
	EClass getSignature();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Signature#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Domain</em>'.
	 * @see asmeta.structure.Signature#getDomain()
	 * @see #getSignature()
	 * @generated
	 */
	EReference getSignature_Domain();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Signature#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Function</em>'.
	 * @see asmeta.structure.Signature#getFunction()
	 * @see #getSignature()
	 * @generated
	 */
	EReference getSignature_Function();

	/**
	 * Returns the meta object for the container reference '{@link asmeta.structure.Signature#getHeaderSection <em>Header Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Header Section</em>'.
	 * @see asmeta.structure.Signature#getHeaderSection()
	 * @see #getSignature()
	 * @generated
	 */
	EReference getSignature_HeaderSection();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Signature#getStructuredDomain <em>Structured Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Structured Domain</em>'.
	 * @see asmeta.structure.Signature#getStructuredDomain()
	 * @see #getSignature()
	 * @generated
	 */
	EReference getSignature_StructuredDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.structure.ExportClause <em>Export Clause</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Export Clause</em>'.
	 * @see asmeta.structure.ExportClause
	 * @generated
	 */
	EClass getExportClause();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.structure.ExportClause#getExportedFunction <em>Exported Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Exported Function</em>'.
	 * @see asmeta.structure.ExportClause#getExportedFunction()
	 * @see #getExportClause()
	 * @generated
	 */
	EReference getExportClause_ExportedFunction();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.structure.ExportClause#getExportedDomain <em>Exported Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Exported Domain</em>'.
	 * @see asmeta.structure.ExportClause#getExportedDomain()
	 * @see #getExportClause()
	 * @generated
	 */
	EReference getExportClause_ExportedDomain();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.structure.ExportClause#getExportedRule <em>Exported Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Exported Rule</em>'.
	 * @see asmeta.structure.ExportClause#getExportedRule()
	 * @see #getExportClause()
	 * @generated
	 */
	EReference getExportClause_ExportedRule();

	/**
	 * Returns the meta object for class '{@link asmeta.structure.ImportClause <em>Import Clause</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import Clause</em>'.
	 * @see asmeta.structure.ImportClause
	 * @generated
	 */
	EClass getImportClause();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.structure.ImportClause#getImportedDomain <em>Imported Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Imported Domain</em>'.
	 * @see asmeta.structure.ImportClause#getImportedDomain()
	 * @see #getImportClause()
	 * @generated
	 */
	EReference getImportClause_ImportedDomain();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.structure.ImportClause#getImportedFunction <em>Imported Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Imported Function</em>'.
	 * @see asmeta.structure.ImportClause#getImportedFunction()
	 * @see #getImportClause()
	 * @generated
	 */
	EReference getImportClause_ImportedFunction();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.structure.ImportClause#getImportedRule <em>Imported Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Imported Rule</em>'.
	 * @see asmeta.structure.ImportClause#getImportedRule()
	 * @see #getImportClause()
	 * @generated
	 */
	EReference getImportClause_ImportedRule();

	/**
	 * Returns the meta object for the attribute '{@link asmeta.structure.ImportClause#getModuleName <em>Module Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Name</em>'.
	 * @see asmeta.structure.ImportClause#getModuleName()
	 * @see #getImportClause()
	 * @generated
	 */
	EAttribute getImportClause_ModuleName();

	/**
	 * Returns the meta object for class '{@link asmeta.structure.FunctionDefinition <em>Function Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Definition</em>'.
	 * @see asmeta.structure.FunctionDefinition
	 * @generated
	 */
	EClass getFunctionDefinition();

	/**
	 * Returns the meta object for the reference '{@link asmeta.structure.FunctionDefinition#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Body</em>'.
	 * @see asmeta.structure.FunctionDefinition#getBody()
	 * @see #getFunctionDefinition()
	 * @generated
	 */
	EReference getFunctionDefinition_Body();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.structure.FunctionDefinition#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Variable</em>'.
	 * @see asmeta.structure.FunctionDefinition#getVariable()
	 * @see #getFunctionDefinition()
	 * @generated
	 */
	EReference getFunctionDefinition_Variable();

	/**
	 * Returns the meta object for the reference '{@link asmeta.structure.FunctionDefinition#getDefinedFunction <em>Defined Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Defined Function</em>'.
	 * @see asmeta.structure.FunctionDefinition#getDefinedFunction()
	 * @see #getFunctionDefinition()
	 * @generated
	 */
	EReference getFunctionDefinition_DefinedFunction();

	/**
	 * Returns the meta object for class '{@link asmeta.structure.DomainDefinition <em>Domain Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain Definition</em>'.
	 * @see asmeta.structure.DomainDefinition
	 * @generated
	 */
	EClass getDomainDefinition();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.structure.DomainDefinition#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see asmeta.structure.DomainDefinition#getBody()
	 * @see #getDomainDefinition()
	 * @generated
	 */
	EReference getDomainDefinition_Body();

	/**
	 * Returns the meta object for the reference '{@link asmeta.structure.DomainDefinition#getDefinedDomain <em>Defined Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Defined Domain</em>'.
	 * @see asmeta.structure.DomainDefinition#getDefinedDomain()
	 * @see #getDomainDefinition()
	 * @generated
	 */
	EReference getDomainDefinition_DefinedDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.structure.Initialization <em>Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Initialization</em>'.
	 * @see asmeta.structure.Initialization
	 * @generated
	 */
	EClass getInitialization();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Initialization#getDomainInitialization <em>Domain Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Domain Initialization</em>'.
	 * @see asmeta.structure.Initialization#getDomainInitialization()
	 * @see #getInitialization()
	 * @generated
	 */
	EReference getInitialization_DomainInitialization();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Initialization#getFunctionInitialization <em>Function Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Function Initialization</em>'.
	 * @see asmeta.structure.Initialization#getFunctionInitialization()
	 * @see #getInitialization()
	 * @generated
	 */
	EReference getInitialization_FunctionInitialization();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Initialization#getAgentInitialization <em>Agent Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Agent Initialization</em>'.
	 * @see asmeta.structure.Initialization#getAgentInitialization()
	 * @see #getInitialization()
	 * @generated
	 */
	EReference getInitialization_AgentInitialization();

	/**
	 * Returns the meta object for the reference '{@link asmeta.structure.Initialization#getAsm <em>Asm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Asm</em>'.
	 * @see asmeta.structure.Initialization#getAsm()
	 * @see #getInitialization()
	 * @generated
	 */
	EReference getInitialization_Asm();

	/**
	 * Returns the meta object for class '{@link asmeta.structure.Header <em>Header</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Header</em>'.
	 * @see asmeta.structure.Header
	 * @generated
	 */
	EClass getHeader();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Header#getImportClause <em>Import Clause</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Import Clause</em>'.
	 * @see asmeta.structure.Header#getImportClause()
	 * @see #getHeader()
	 * @generated
	 */
	EReference getHeader_ImportClause();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.structure.Header#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Signature</em>'.
	 * @see asmeta.structure.Header#getSignature()
	 * @see #getHeader()
	 * @generated
	 */
	EReference getHeader_Signature();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.structure.Header#getExportClause <em>Export Clause</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Export Clause</em>'.
	 * @see asmeta.structure.Header#getExportClause()
	 * @see #getHeader()
	 * @generated
	 */
	EReference getHeader_ExportClause();

	/**
	 * Returns the meta object for the container reference '{@link asmeta.structure.Header#getAsm <em>Asm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Asm</em>'.
	 * @see asmeta.structure.Header#getAsm()
	 * @see #getHeader()
	 * @generated
	 */
	EReference getHeader_Asm();

	/**
	 * Returns the meta object for class '{@link asmeta.structure.Asm <em>Asm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Asm</em>'.
	 * @see asmeta.structure.Asm
	 * @generated
	 */
	EClass getAsm();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.structure.Asm#getInitialState <em>Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Initial State</em>'.
	 * @see asmeta.structure.Asm#getInitialState()
	 * @see #getAsm()
	 * @generated
	 */
	EReference getAsm_InitialState();

	/**
	 * Returns the meta object for the reference '{@link asmeta.structure.Asm#getDefaultInitialState <em>Default Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Default Initial State</em>'.
	 * @see asmeta.structure.Asm#getDefaultInitialState()
	 * @see #getAsm()
	 * @generated
	 */
	EReference getAsm_DefaultInitialState();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.structure.Asm#getBodySection <em>Body Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body Section</em>'.
	 * @see asmeta.structure.Asm#getBodySection()
	 * @see #getAsm()
	 * @generated
	 */
	EReference getAsm_BodySection();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.structure.Asm#getHeaderSection <em>Header Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Header Section</em>'.
	 * @see asmeta.structure.Asm#getHeaderSection()
	 * @see #getAsm()
	 * @generated
	 */
	EReference getAsm_HeaderSection();

	/**
	 * Returns the meta object for the reference '{@link asmeta.structure.Asm#getMainrule <em>Mainrule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mainrule</em>'.
	 * @see asmeta.structure.Asm#getMainrule()
	 * @see #getAsm()
	 * @generated
	 */
	EReference getAsm_Mainrule();

	/**
	 * Returns the meta object for the attribute '{@link asmeta.structure.Asm#getIsAsynchr <em>Is Asynchr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Asynchr</em>'.
	 * @see asmeta.structure.Asm#getIsAsynchr()
	 * @see #getAsm()
	 * @generated
	 */
	EAttribute getAsm_IsAsynchr();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	StructureFactory getStructureFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link asmeta.structure.impl.NamedElementImpl <em>Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.structure.impl.NamedElementImpl
		 * @see asmeta.structure.impl.StructurePackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

		/**
		 * The meta object literal for the '{@link asmeta.structure.impl.AgentInitializationImpl <em>Agent Initialization</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.structure.impl.AgentInitializationImpl
		 * @see asmeta.structure.impl.StructurePackageImpl#getAgentInitialization()
		 * @generated
		 */
		EClass AGENT_INITIALIZATION = eINSTANCE.getAgentInitialization();

		/**
		 * The meta object literal for the '<em><b>Program</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGENT_INITIALIZATION__PROGRAM = eINSTANCE.getAgentInitialization_Program();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGENT_INITIALIZATION__DOMAIN = eINSTANCE.getAgentInitialization_Domain();

		/**
		 * The meta object literal for the '<em><b>Initial State</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGENT_INITIALIZATION__INITIAL_STATE = eINSTANCE.getAgentInitialization_InitialState();

		/**
		 * The meta object literal for the '{@link asmeta.structure.impl.BodyImpl <em>Body</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.structure.impl.BodyImpl
		 * @see asmeta.structure.impl.StructurePackageImpl#getBody()
		 * @generated
		 */
		EClass BODY = eINSTANCE.getBody();

		/**
		 * The meta object literal for the '<em><b>Function Definition</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BODY__FUNCTION_DEFINITION = eINSTANCE.getBody_FunctionDefinition();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BODY__PROPERTY = eINSTANCE.getBody_Property();

		/**
		 * The meta object literal for the '<em><b>Domain Definition</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BODY__DOMAIN_DEFINITION = eINSTANCE.getBody_DomainDefinition();

		/**
		 * The meta object literal for the '<em><b>Rule Declaration</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BODY__RULE_DECLARATION = eINSTANCE.getBody_RuleDeclaration();

		/**
		 * The meta object literal for the '<em><b>Asm</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BODY__ASM = eINSTANCE.getBody_Asm();

		/**
		 * The meta object literal for the '<em><b>Fairness Constraint</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BODY__FAIRNESS_CONSTRAINT = eINSTANCE.getBody_FairnessConstraint();

		/**
		 * The meta object literal for the '<em><b>Invariant Constraint</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BODY__INVARIANT_CONSTRAINT = eINSTANCE.getBody_InvariantConstraint();

		/**
		 * The meta object literal for the '{@link asmeta.structure.impl.FunctionInitializationImpl <em>Function Initialization</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.structure.impl.FunctionInitializationImpl
		 * @see asmeta.structure.impl.StructurePackageImpl#getFunctionInitialization()
		 * @generated
		 */
		EClass FUNCTION_INITIALIZATION = eINSTANCE.getFunctionInitialization();

		/**
		 * The meta object literal for the '<em><b>Initial State</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_INITIALIZATION__INITIAL_STATE = eINSTANCE.getFunctionInitialization_InitialState();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_INITIALIZATION__BODY = eINSTANCE.getFunctionInitialization_Body();

		/**
		 * The meta object literal for the '<em><b>Initialized Function</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_INITIALIZATION__INITIALIZED_FUNCTION = eINSTANCE.getFunctionInitialization_InitializedFunction();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_INITIALIZATION__VARIABLE = eINSTANCE.getFunctionInitialization_Variable();

		/**
		 * The meta object literal for the '{@link asmeta.structure.impl.DomainInitializationImpl <em>Domain Initialization</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.structure.impl.DomainInitializationImpl
		 * @see asmeta.structure.impl.StructurePackageImpl#getDomainInitialization()
		 * @generated
		 */
		EClass DOMAIN_INITIALIZATION = eINSTANCE.getDomainInitialization();

		/**
		 * The meta object literal for the '<em><b>Initialized Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN = eINSTANCE.getDomainInitialization_InitializedDomain();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN_INITIALIZATION__BODY = eINSTANCE.getDomainInitialization_Body();

		/**
		 * The meta object literal for the '<em><b>Initial State</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN_INITIALIZATION__INITIAL_STATE = eINSTANCE.getDomainInitialization_InitialState();

		/**
		 * The meta object literal for the '{@link asmeta.structure.impl.SignatureImpl <em>Signature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.structure.impl.SignatureImpl
		 * @see asmeta.structure.impl.StructurePackageImpl#getSignature()
		 * @generated
		 */
		EClass SIGNATURE = eINSTANCE.getSignature();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIGNATURE__DOMAIN = eINSTANCE.getSignature_Domain();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIGNATURE__FUNCTION = eINSTANCE.getSignature_Function();

		/**
		 * The meta object literal for the '<em><b>Header Section</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIGNATURE__HEADER_SECTION = eINSTANCE.getSignature_HeaderSection();

		/**
		 * The meta object literal for the '<em><b>Structured Domain</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIGNATURE__STRUCTURED_DOMAIN = eINSTANCE.getSignature_StructuredDomain();

		/**
		 * The meta object literal for the '{@link asmeta.structure.impl.ExportClauseImpl <em>Export Clause</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.structure.impl.ExportClauseImpl
		 * @see asmeta.structure.impl.StructurePackageImpl#getExportClause()
		 * @generated
		 */
		EClass EXPORT_CLAUSE = eINSTANCE.getExportClause();

		/**
		 * The meta object literal for the '<em><b>Exported Function</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPORT_CLAUSE__EXPORTED_FUNCTION = eINSTANCE.getExportClause_ExportedFunction();

		/**
		 * The meta object literal for the '<em><b>Exported Domain</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPORT_CLAUSE__EXPORTED_DOMAIN = eINSTANCE.getExportClause_ExportedDomain();

		/**
		 * The meta object literal for the '<em><b>Exported Rule</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPORT_CLAUSE__EXPORTED_RULE = eINSTANCE.getExportClause_ExportedRule();

		/**
		 * The meta object literal for the '{@link asmeta.structure.impl.ImportClauseImpl <em>Import Clause</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.structure.impl.ImportClauseImpl
		 * @see asmeta.structure.impl.StructurePackageImpl#getImportClause()
		 * @generated
		 */
		EClass IMPORT_CLAUSE = eINSTANCE.getImportClause();

		/**
		 * The meta object literal for the '<em><b>Imported Domain</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMPORT_CLAUSE__IMPORTED_DOMAIN = eINSTANCE.getImportClause_ImportedDomain();

		/**
		 * The meta object literal for the '<em><b>Imported Function</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMPORT_CLAUSE__IMPORTED_FUNCTION = eINSTANCE.getImportClause_ImportedFunction();

		/**
		 * The meta object literal for the '<em><b>Imported Rule</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMPORT_CLAUSE__IMPORTED_RULE = eINSTANCE.getImportClause_ImportedRule();

		/**
		 * The meta object literal for the '<em><b>Module Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_CLAUSE__MODULE_NAME = eINSTANCE.getImportClause_ModuleName();

		/**
		 * The meta object literal for the '{@link asmeta.structure.impl.FunctionDefinitionImpl <em>Function Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.structure.impl.FunctionDefinitionImpl
		 * @see asmeta.structure.impl.StructurePackageImpl#getFunctionDefinition()
		 * @generated
		 */
		EClass FUNCTION_DEFINITION = eINSTANCE.getFunctionDefinition();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_DEFINITION__BODY = eINSTANCE.getFunctionDefinition_Body();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_DEFINITION__VARIABLE = eINSTANCE.getFunctionDefinition_Variable();

		/**
		 * The meta object literal for the '<em><b>Defined Function</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_DEFINITION__DEFINED_FUNCTION = eINSTANCE.getFunctionDefinition_DefinedFunction();

		/**
		 * The meta object literal for the '{@link asmeta.structure.impl.DomainDefinitionImpl <em>Domain Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.structure.impl.DomainDefinitionImpl
		 * @see asmeta.structure.impl.StructurePackageImpl#getDomainDefinition()
		 * @generated
		 */
		EClass DOMAIN_DEFINITION = eINSTANCE.getDomainDefinition();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN_DEFINITION__BODY = eINSTANCE.getDomainDefinition_Body();

		/**
		 * The meta object literal for the '<em><b>Defined Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN_DEFINITION__DEFINED_DOMAIN = eINSTANCE.getDomainDefinition_DefinedDomain();

		/**
		 * The meta object literal for the '{@link asmeta.structure.impl.InitializationImpl <em>Initialization</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.structure.impl.InitializationImpl
		 * @see asmeta.structure.impl.StructurePackageImpl#getInitialization()
		 * @generated
		 */
		EClass INITIALIZATION = eINSTANCE.getInitialization();

		/**
		 * The meta object literal for the '<em><b>Domain Initialization</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INITIALIZATION__DOMAIN_INITIALIZATION = eINSTANCE.getInitialization_DomainInitialization();

		/**
		 * The meta object literal for the '<em><b>Function Initialization</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INITIALIZATION__FUNCTION_INITIALIZATION = eINSTANCE.getInitialization_FunctionInitialization();

		/**
		 * The meta object literal for the '<em><b>Agent Initialization</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INITIALIZATION__AGENT_INITIALIZATION = eINSTANCE.getInitialization_AgentInitialization();

		/**
		 * The meta object literal for the '<em><b>Asm</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INITIALIZATION__ASM = eINSTANCE.getInitialization_Asm();

		/**
		 * The meta object literal for the '{@link asmeta.structure.impl.HeaderImpl <em>Header</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.structure.impl.HeaderImpl
		 * @see asmeta.structure.impl.StructurePackageImpl#getHeader()
		 * @generated
		 */
		EClass HEADER = eINSTANCE.getHeader();

		/**
		 * The meta object literal for the '<em><b>Import Clause</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEADER__IMPORT_CLAUSE = eINSTANCE.getHeader_ImportClause();

		/**
		 * The meta object literal for the '<em><b>Signature</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEADER__SIGNATURE = eINSTANCE.getHeader_Signature();

		/**
		 * The meta object literal for the '<em><b>Export Clause</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEADER__EXPORT_CLAUSE = eINSTANCE.getHeader_ExportClause();

		/**
		 * The meta object literal for the '<em><b>Asm</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEADER__ASM = eINSTANCE.getHeader_Asm();

		/**
		 * The meta object literal for the '{@link asmeta.structure.impl.AsmImpl <em>Asm</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.structure.impl.AsmImpl
		 * @see asmeta.structure.impl.StructurePackageImpl#getAsm()
		 * @generated
		 */
		EClass ASM = eINSTANCE.getAsm();

		/**
		 * The meta object literal for the '<em><b>Initial State</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASM__INITIAL_STATE = eINSTANCE.getAsm_InitialState();

		/**
		 * The meta object literal for the '<em><b>Default Initial State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASM__DEFAULT_INITIAL_STATE = eINSTANCE.getAsm_DefaultInitialState();

		/**
		 * The meta object literal for the '<em><b>Body Section</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASM__BODY_SECTION = eINSTANCE.getAsm_BodySection();

		/**
		 * The meta object literal for the '<em><b>Header Section</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASM__HEADER_SECTION = eINSTANCE.getAsm_HeaderSection();

		/**
		 * The meta object literal for the '<em><b>Mainrule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASM__MAINRULE = eINSTANCE.getAsm_Mainrule();

		/**
		 * The meta object literal for the '<em><b>Is Asynchr</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASM__IS_ASYNCHR = eINSTANCE.getAsm_IsAsynchr();

	}

} //StructurePackage

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import asmeta.structure.StructurePackage;

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
 * @see asmeta.definitions.DefinitionsFactory
 * @model kind="package"
 * @generated
 */
public interface DefinitionsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "definitions";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.asmeta/asmm#Definitions";

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
	DefinitionsPackage eINSTANCE = asmeta.definitions.impl.DefinitionsPackageImpl.init();

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.ClassifierImpl <em>Classifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.ClassifierImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getClassifier()
	 * @generated
	 */
	int CLASSIFIER = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__NAME = StructurePackage.NAMED_ELEMENT__NAME;

	/**
	 * The number of structural features of the '<em>Classifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_FEATURE_COUNT = StructurePackage.NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.RuleDeclarationImpl <em>Rule Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.RuleDeclarationImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getRuleDeclaration()
	 * @generated
	 */
	int RULE_DECLARATION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_DECLARATION__NAME = CLASSIFIER__NAME;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_DECLARATION__VARIABLE = CLASSIFIER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_DECLARATION__CONSTRAINT = CLASSIFIER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rule Body</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_DECLARATION__RULE_BODY = CLASSIFIER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Asm Body</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_DECLARATION__ASM_BODY = CLASSIFIER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_DECLARATION__ARITY = CLASSIFIER_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Rule Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_DECLARATION_FEATURE_COUNT = CLASSIFIER_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.FunctionImpl <em>Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.FunctionImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getFunction()
	 * @generated
	 */
	int FUNCTION = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__NAME = CLASSIFIER__NAME;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__DOMAIN = CLASSIFIER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Codomain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__CODOMAIN = CLASSIFIER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__DEFINITION = CLASSIFIER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__CONSTRAINT = CLASSIFIER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__ARITY = CLASSIFIER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__SIGNATURE = CLASSIFIER_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FEATURE_COUNT = CLASSIFIER_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.BasicFunctionImpl <em>Basic Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.BasicFunctionImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getBasicFunction()
	 * @generated
	 */
	int BASIC_FUNCTION = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FUNCTION__NAME = FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FUNCTION__DOMAIN = FUNCTION__DOMAIN;

	/**
	 * The feature id for the '<em><b>Codomain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FUNCTION__CODOMAIN = FUNCTION__CODOMAIN;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FUNCTION__DEFINITION = FUNCTION__DEFINITION;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FUNCTION__CONSTRAINT = FUNCTION__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FUNCTION__ARITY = FUNCTION__ARITY;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FUNCTION__SIGNATURE = FUNCTION__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Basic Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FUNCTION_FEATURE_COUNT = FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.DynamicFunctionImpl <em>Dynamic Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.DynamicFunctionImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getDynamicFunction()
	 * @generated
	 */
	int DYNAMIC_FUNCTION = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_FUNCTION__NAME = BASIC_FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_FUNCTION__DOMAIN = BASIC_FUNCTION__DOMAIN;

	/**
	 * The feature id for the '<em><b>Codomain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_FUNCTION__CODOMAIN = BASIC_FUNCTION__CODOMAIN;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_FUNCTION__DEFINITION = BASIC_FUNCTION__DEFINITION;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_FUNCTION__CONSTRAINT = BASIC_FUNCTION__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_FUNCTION__ARITY = BASIC_FUNCTION__ARITY;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_FUNCTION__SIGNATURE = BASIC_FUNCTION__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Initialization</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_FUNCTION__INITIALIZATION = BASIC_FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Dynamic Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_FUNCTION_FEATURE_COUNT = BASIC_FUNCTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.LocalFunctionImpl <em>Local Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.LocalFunctionImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getLocalFunction()
	 * @generated
	 */
	int LOCAL_FUNCTION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FUNCTION__NAME = DYNAMIC_FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FUNCTION__DOMAIN = DYNAMIC_FUNCTION__DOMAIN;

	/**
	 * The feature id for the '<em><b>Codomain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FUNCTION__CODOMAIN = DYNAMIC_FUNCTION__CODOMAIN;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FUNCTION__DEFINITION = DYNAMIC_FUNCTION__DEFINITION;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FUNCTION__CONSTRAINT = DYNAMIC_FUNCTION__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FUNCTION__ARITY = DYNAMIC_FUNCTION__ARITY;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FUNCTION__SIGNATURE = DYNAMIC_FUNCTION__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Initialization</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FUNCTION__INITIALIZATION = DYNAMIC_FUNCTION__INITIALIZATION;

	/**
	 * The number of structural features of the '<em>Local Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_FUNCTION_FEATURE_COUNT = DYNAMIC_FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.ControlledFunctionImpl <em>Controlled Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.ControlledFunctionImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getControlledFunction()
	 * @generated
	 */
	int CONTROLLED_FUNCTION = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROLLED_FUNCTION__NAME = DYNAMIC_FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROLLED_FUNCTION__DOMAIN = DYNAMIC_FUNCTION__DOMAIN;

	/**
	 * The feature id for the '<em><b>Codomain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROLLED_FUNCTION__CODOMAIN = DYNAMIC_FUNCTION__CODOMAIN;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROLLED_FUNCTION__DEFINITION = DYNAMIC_FUNCTION__DEFINITION;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROLLED_FUNCTION__CONSTRAINT = DYNAMIC_FUNCTION__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROLLED_FUNCTION__ARITY = DYNAMIC_FUNCTION__ARITY;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROLLED_FUNCTION__SIGNATURE = DYNAMIC_FUNCTION__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Initialization</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROLLED_FUNCTION__INITIALIZATION = DYNAMIC_FUNCTION__INITIALIZATION;

	/**
	 * The number of structural features of the '<em>Controlled Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROLLED_FUNCTION_FEATURE_COUNT = DYNAMIC_FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.SharedFunctionImpl <em>Shared Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.SharedFunctionImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getSharedFunction()
	 * @generated
	 */
	int SHARED_FUNCTION = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_FUNCTION__NAME = DYNAMIC_FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_FUNCTION__DOMAIN = DYNAMIC_FUNCTION__DOMAIN;

	/**
	 * The feature id for the '<em><b>Codomain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_FUNCTION__CODOMAIN = DYNAMIC_FUNCTION__CODOMAIN;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_FUNCTION__DEFINITION = DYNAMIC_FUNCTION__DEFINITION;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_FUNCTION__CONSTRAINT = DYNAMIC_FUNCTION__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_FUNCTION__ARITY = DYNAMIC_FUNCTION__ARITY;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_FUNCTION__SIGNATURE = DYNAMIC_FUNCTION__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Initialization</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_FUNCTION__INITIALIZATION = DYNAMIC_FUNCTION__INITIALIZATION;

	/**
	 * The number of structural features of the '<em>Shared Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_FUNCTION_FEATURE_COUNT = DYNAMIC_FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.MonitoredFunctionImpl <em>Monitored Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.MonitoredFunctionImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getMonitoredFunction()
	 * @generated
	 */
	int MONITORED_FUNCTION = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORED_FUNCTION__NAME = DYNAMIC_FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORED_FUNCTION__DOMAIN = DYNAMIC_FUNCTION__DOMAIN;

	/**
	 * The feature id for the '<em><b>Codomain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORED_FUNCTION__CODOMAIN = DYNAMIC_FUNCTION__CODOMAIN;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORED_FUNCTION__DEFINITION = DYNAMIC_FUNCTION__DEFINITION;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORED_FUNCTION__CONSTRAINT = DYNAMIC_FUNCTION__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORED_FUNCTION__ARITY = DYNAMIC_FUNCTION__ARITY;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORED_FUNCTION__SIGNATURE = DYNAMIC_FUNCTION__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Initialization</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORED_FUNCTION__INITIALIZATION = DYNAMIC_FUNCTION__INITIALIZATION;

	/**
	 * The number of structural features of the '<em>Monitored Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORED_FUNCTION_FEATURE_COUNT = DYNAMIC_FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.OutFunctionImpl <em>Out Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.OutFunctionImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getOutFunction()
	 * @generated
	 */
	int OUT_FUNCTION = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_FUNCTION__NAME = DYNAMIC_FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_FUNCTION__DOMAIN = DYNAMIC_FUNCTION__DOMAIN;

	/**
	 * The feature id for the '<em><b>Codomain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_FUNCTION__CODOMAIN = DYNAMIC_FUNCTION__CODOMAIN;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_FUNCTION__DEFINITION = DYNAMIC_FUNCTION__DEFINITION;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_FUNCTION__CONSTRAINT = DYNAMIC_FUNCTION__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_FUNCTION__ARITY = DYNAMIC_FUNCTION__ARITY;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_FUNCTION__SIGNATURE = DYNAMIC_FUNCTION__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Initialization</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_FUNCTION__INITIALIZATION = DYNAMIC_FUNCTION__INITIALIZATION;

	/**
	 * The number of structural features of the '<em>Out Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUT_FUNCTION_FEATURE_COUNT = DYNAMIC_FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.StaticFunctionImpl <em>Static Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.StaticFunctionImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getStaticFunction()
	 * @generated
	 */
	int STATIC_FUNCTION = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_FUNCTION__NAME = BASIC_FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_FUNCTION__DOMAIN = BASIC_FUNCTION__DOMAIN;

	/**
	 * The feature id for the '<em><b>Codomain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_FUNCTION__CODOMAIN = BASIC_FUNCTION__CODOMAIN;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_FUNCTION__DEFINITION = BASIC_FUNCTION__DEFINITION;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_FUNCTION__CONSTRAINT = BASIC_FUNCTION__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_FUNCTION__ARITY = BASIC_FUNCTION__ARITY;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_FUNCTION__SIGNATURE = BASIC_FUNCTION__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Static Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_FUNCTION_FEATURE_COUNT = BASIC_FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.DerivedFunctionImpl <em>Derived Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.DerivedFunctionImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getDerivedFunction()
	 * @generated
	 */
	int DERIVED_FUNCTION = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_FUNCTION__NAME = FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_FUNCTION__DOMAIN = FUNCTION__DOMAIN;

	/**
	 * The feature id for the '<em><b>Codomain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_FUNCTION__CODOMAIN = FUNCTION__CODOMAIN;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_FUNCTION__DEFINITION = FUNCTION__DEFINITION;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_FUNCTION__CONSTRAINT = FUNCTION__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_FUNCTION__ARITY = FUNCTION__ARITY;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_FUNCTION__SIGNATURE = FUNCTION__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Derived Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_FUNCTION_FEATURE_COUNT = FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.PropertyImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = CLASSIFIER__NAME;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = CLASSIFIER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.InvariantImpl <em>Invariant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.InvariantImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getInvariant()
	 * @generated
	 */
	int INVARIANT = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT__NAME = PROPERTY__NAME;

	/**
	 * The feature id for the '<em><b>Constrained Domain</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT__CONSTRAINED_DOMAIN = PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Constrained Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT__CONSTRAINED_RULE = PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Constrained Function</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT__CONSTRAINED_FUNCTION = PROPERTY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT__BODY = PROPERTY_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Invariant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT_FEATURE_COUNT = PROPERTY_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.TemporalPropertyImpl <em>Temporal Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.TemporalPropertyImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getTemporalProperty()
	 * @generated
	 */
	int TEMPORAL_PROPERTY = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORAL_PROPERTY__NAME = PROPERTY__NAME;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORAL_PROPERTY__BODY = PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Temporal Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPORAL_PROPERTY_FEATURE_COUNT = PROPERTY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.CtlSpecImpl <em>Ctl Spec</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.CtlSpecImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getCtlSpec()
	 * @generated
	 */
	int CTL_SPEC = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CTL_SPEC__NAME = TEMPORAL_PROPERTY__NAME;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CTL_SPEC__BODY = TEMPORAL_PROPERTY__BODY;

	/**
	 * The number of structural features of the '<em>Ctl Spec</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CTL_SPEC_FEATURE_COUNT = TEMPORAL_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.LtlSpecImpl <em>Ltl Spec</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.LtlSpecImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getLtlSpec()
	 * @generated
	 */
	int LTL_SPEC = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTL_SPEC__NAME = TEMPORAL_PROPERTY__NAME;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTL_SPEC__BODY = TEMPORAL_PROPERTY__BODY;

	/**
	 * The number of structural features of the '<em>Ltl Spec</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTL_SPEC_FEATURE_COUNT = TEMPORAL_PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.InvarConstraintImpl <em>Invar Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.InvarConstraintImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getInvarConstraint()
	 * @generated
	 */
	int INVAR_CONSTRAINT = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVAR_CONSTRAINT__NAME = CLASSIFIER__NAME;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVAR_CONSTRAINT__BODY = CLASSIFIER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Invar Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVAR_CONSTRAINT_FEATURE_COUNT = CLASSIFIER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.FairnessConstraintImpl <em>Fairness Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.FairnessConstraintImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getFairnessConstraint()
	 * @generated
	 */
	int FAIRNESS_CONSTRAINT = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAIRNESS_CONSTRAINT__NAME = CLASSIFIER__NAME;

	/**
	 * The number of structural features of the '<em>Fairness Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAIRNESS_CONSTRAINT_FEATURE_COUNT = CLASSIFIER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.JusticeConstraintImpl <em>Justice Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.JusticeConstraintImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getJusticeConstraint()
	 * @generated
	 */
	int JUSTICE_CONSTRAINT = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JUSTICE_CONSTRAINT__NAME = FAIRNESS_CONSTRAINT__NAME;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JUSTICE_CONSTRAINT__BODY = FAIRNESS_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Justice Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JUSTICE_CONSTRAINT_FEATURE_COUNT = FAIRNESS_CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.definitions.impl.CompassionConstraintImpl <em>Compassion Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.impl.CompassionConstraintImpl
	 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getCompassionConstraint()
	 * @generated
	 */
	int COMPASSION_CONSTRAINT = 20;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPASSION_CONSTRAINT__NAME = FAIRNESS_CONSTRAINT__NAME;

	/**
	 * The feature id for the '<em><b>P</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPASSION_CONSTRAINT__P = FAIRNESS_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Q</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPASSION_CONSTRAINT__Q = FAIRNESS_CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Compassion Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPASSION_CONSTRAINT_FEATURE_COUNT = FAIRNESS_CONSTRAINT_FEATURE_COUNT + 2;

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.RuleDeclaration <em>Rule Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Declaration</em>'.
	 * @see asmeta.definitions.RuleDeclaration
	 * @generated
	 */
	EClass getRuleDeclaration();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.definitions.RuleDeclaration#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Variable</em>'.
	 * @see asmeta.definitions.RuleDeclaration#getVariable()
	 * @see #getRuleDeclaration()
	 * @generated
	 */
	EReference getRuleDeclaration_Variable();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.definitions.RuleDeclaration#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Constraint</em>'.
	 * @see asmeta.definitions.RuleDeclaration#getConstraint()
	 * @see #getRuleDeclaration()
	 * @generated
	 */
	EReference getRuleDeclaration_Constraint();

	/**
	 * Returns the meta object for the reference '{@link asmeta.definitions.RuleDeclaration#getRuleBody <em>Rule Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule Body</em>'.
	 * @see asmeta.definitions.RuleDeclaration#getRuleBody()
	 * @see #getRuleDeclaration()
	 * @generated
	 */
	EReference getRuleDeclaration_RuleBody();

	/**
	 * Returns the meta object for the container reference '{@link asmeta.definitions.RuleDeclaration#getAsmBody <em>Asm Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Asm Body</em>'.
	 * @see asmeta.definitions.RuleDeclaration#getAsmBody()
	 * @see #getRuleDeclaration()
	 * @generated
	 */
	EReference getRuleDeclaration_AsmBody();

	/**
	 * Returns the meta object for the attribute '{@link asmeta.definitions.RuleDeclaration#getArity <em>Arity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Arity</em>'.
	 * @see asmeta.definitions.RuleDeclaration#getArity()
	 * @see #getRuleDeclaration()
	 * @generated
	 */
	EAttribute getRuleDeclaration_Arity();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.LocalFunction <em>Local Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Function</em>'.
	 * @see asmeta.definitions.LocalFunction
	 * @generated
	 */
	EClass getLocalFunction();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.ControlledFunction <em>Controlled Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Controlled Function</em>'.
	 * @see asmeta.definitions.ControlledFunction
	 * @generated
	 */
	EClass getControlledFunction();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.SharedFunction <em>Shared Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared Function</em>'.
	 * @see asmeta.definitions.SharedFunction
	 * @generated
	 */
	EClass getSharedFunction();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.MonitoredFunction <em>Monitored Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Monitored Function</em>'.
	 * @see asmeta.definitions.MonitoredFunction
	 * @generated
	 */
	EClass getMonitoredFunction();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.OutFunction <em>Out Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Out Function</em>'.
	 * @see asmeta.definitions.OutFunction
	 * @generated
	 */
	EClass getOutFunction();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.DynamicFunction <em>Dynamic Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dynamic Function</em>'.
	 * @see asmeta.definitions.DynamicFunction
	 * @generated
	 */
	EClass getDynamicFunction();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.definitions.DynamicFunction#getInitialization <em>Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Initialization</em>'.
	 * @see asmeta.definitions.DynamicFunction#getInitialization()
	 * @see #getDynamicFunction()
	 * @generated
	 */
	EReference getDynamicFunction_Initialization();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.StaticFunction <em>Static Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Static Function</em>'.
	 * @see asmeta.definitions.StaticFunction
	 * @generated
	 */
	EClass getStaticFunction();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.DerivedFunction <em>Derived Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Derived Function</em>'.
	 * @see asmeta.definitions.DerivedFunction
	 * @generated
	 */
	EClass getDerivedFunction();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.BasicFunction <em>Basic Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic Function</em>'.
	 * @see asmeta.definitions.BasicFunction
	 * @generated
	 */
	EClass getBasicFunction();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.Invariant <em>Invariant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Invariant</em>'.
	 * @see asmeta.definitions.Invariant
	 * @generated
	 */
	EClass getInvariant();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.definitions.Invariant#getConstrainedDomain <em>Constrained Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Constrained Domain</em>'.
	 * @see asmeta.definitions.Invariant#getConstrainedDomain()
	 * @see #getInvariant()
	 * @generated
	 */
	EReference getInvariant_ConstrainedDomain();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.definitions.Invariant#getConstrainedRule <em>Constrained Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Constrained Rule</em>'.
	 * @see asmeta.definitions.Invariant#getConstrainedRule()
	 * @see #getInvariant()
	 * @generated
	 */
	EReference getInvariant_ConstrainedRule();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.definitions.Invariant#getConstrainedFunction <em>Constrained Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Constrained Function</em>'.
	 * @see asmeta.definitions.Invariant#getConstrainedFunction()
	 * @see #getInvariant()
	 * @generated
	 */
	EReference getInvariant_ConstrainedFunction();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.definitions.Invariant#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see asmeta.definitions.Invariant#getBody()
	 * @see #getInvariant()
	 * @generated
	 */
	EReference getInvariant_Body();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function</em>'.
	 * @see asmeta.definitions.Function
	 * @generated
	 */
	EClass getFunction();

	/**
	 * Returns the meta object for the reference '{@link asmeta.definitions.Function#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Domain</em>'.
	 * @see asmeta.definitions.Function#getDomain()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Domain();

	/**
	 * Returns the meta object for the reference '{@link asmeta.definitions.Function#getCodomain <em>Codomain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Codomain</em>'.
	 * @see asmeta.definitions.Function#getCodomain()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Codomain();

	/**
	 * Returns the meta object for the reference '{@link asmeta.definitions.Function#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Definition</em>'.
	 * @see asmeta.definitions.Function#getDefinition()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Definition();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.definitions.Function#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Constraint</em>'.
	 * @see asmeta.definitions.Function#getConstraint()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Constraint();

	/**
	 * Returns the meta object for the attribute '{@link asmeta.definitions.Function#getArity <em>Arity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Arity</em>'.
	 * @see asmeta.definitions.Function#getArity()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Arity();

	/**
	 * Returns the meta object for the container reference '{@link asmeta.definitions.Function#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Signature</em>'.
	 * @see asmeta.definitions.Function#getSignature()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Signature();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.Classifier <em>Classifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Classifier</em>'.
	 * @see asmeta.definitions.Classifier
	 * @generated
	 */
	EClass getClassifier();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see asmeta.definitions.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.TemporalProperty <em>Temporal Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Temporal Property</em>'.
	 * @see asmeta.definitions.TemporalProperty
	 * @generated
	 */
	EClass getTemporalProperty();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.definitions.TemporalProperty#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see asmeta.definitions.TemporalProperty#getBody()
	 * @see #getTemporalProperty()
	 * @generated
	 */
	EReference getTemporalProperty_Body();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.CtlSpec <em>Ctl Spec</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ctl Spec</em>'.
	 * @see asmeta.definitions.CtlSpec
	 * @generated
	 */
	EClass getCtlSpec();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.LtlSpec <em>Ltl Spec</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ltl Spec</em>'.
	 * @see asmeta.definitions.LtlSpec
	 * @generated
	 */
	EClass getLtlSpec();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.InvarConstraint <em>Invar Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Invar Constraint</em>'.
	 * @see asmeta.definitions.InvarConstraint
	 * @generated
	 */
	EClass getInvarConstraint();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.definitions.InvarConstraint#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see asmeta.definitions.InvarConstraint#getBody()
	 * @see #getInvarConstraint()
	 * @generated
	 */
	EReference getInvarConstraint_Body();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.FairnessConstraint <em>Fairness Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fairness Constraint</em>'.
	 * @see asmeta.definitions.FairnessConstraint
	 * @generated
	 */
	EClass getFairnessConstraint();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.JusticeConstraint <em>Justice Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Justice Constraint</em>'.
	 * @see asmeta.definitions.JusticeConstraint
	 * @generated
	 */
	EClass getJusticeConstraint();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.definitions.JusticeConstraint#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see asmeta.definitions.JusticeConstraint#getBody()
	 * @see #getJusticeConstraint()
	 * @generated
	 */
	EReference getJusticeConstraint_Body();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.CompassionConstraint <em>Compassion Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compassion Constraint</em>'.
	 * @see asmeta.definitions.CompassionConstraint
	 * @generated
	 */
	EClass getCompassionConstraint();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.definitions.CompassionConstraint#getP <em>P</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>P</em>'.
	 * @see asmeta.definitions.CompassionConstraint#getP()
	 * @see #getCompassionConstraint()
	 * @generated
	 */
	EReference getCompassionConstraint_P();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.definitions.CompassionConstraint#getQ <em>Q</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Q</em>'.
	 * @see asmeta.definitions.CompassionConstraint#getQ()
	 * @see #getCompassionConstraint()
	 * @generated
	 */
	EReference getCompassionConstraint_Q();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DefinitionsFactory getDefinitionsFactory();

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
		 * The meta object literal for the '{@link asmeta.definitions.impl.RuleDeclarationImpl <em>Rule Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.RuleDeclarationImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getRuleDeclaration()
		 * @generated
		 */
		EClass RULE_DECLARATION = eINSTANCE.getRuleDeclaration();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_DECLARATION__VARIABLE = eINSTANCE.getRuleDeclaration_Variable();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_DECLARATION__CONSTRAINT = eINSTANCE.getRuleDeclaration_Constraint();

		/**
		 * The meta object literal for the '<em><b>Rule Body</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_DECLARATION__RULE_BODY = eINSTANCE.getRuleDeclaration_RuleBody();

		/**
		 * The meta object literal for the '<em><b>Asm Body</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_DECLARATION__ASM_BODY = eINSTANCE.getRuleDeclaration_AsmBody();

		/**
		 * The meta object literal for the '<em><b>Arity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_DECLARATION__ARITY = eINSTANCE.getRuleDeclaration_Arity();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.LocalFunctionImpl <em>Local Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.LocalFunctionImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getLocalFunction()
		 * @generated
		 */
		EClass LOCAL_FUNCTION = eINSTANCE.getLocalFunction();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.ControlledFunctionImpl <em>Controlled Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.ControlledFunctionImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getControlledFunction()
		 * @generated
		 */
		EClass CONTROLLED_FUNCTION = eINSTANCE.getControlledFunction();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.SharedFunctionImpl <em>Shared Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.SharedFunctionImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getSharedFunction()
		 * @generated
		 */
		EClass SHARED_FUNCTION = eINSTANCE.getSharedFunction();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.MonitoredFunctionImpl <em>Monitored Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.MonitoredFunctionImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getMonitoredFunction()
		 * @generated
		 */
		EClass MONITORED_FUNCTION = eINSTANCE.getMonitoredFunction();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.OutFunctionImpl <em>Out Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.OutFunctionImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getOutFunction()
		 * @generated
		 */
		EClass OUT_FUNCTION = eINSTANCE.getOutFunction();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.DynamicFunctionImpl <em>Dynamic Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.DynamicFunctionImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getDynamicFunction()
		 * @generated
		 */
		EClass DYNAMIC_FUNCTION = eINSTANCE.getDynamicFunction();

		/**
		 * The meta object literal for the '<em><b>Initialization</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DYNAMIC_FUNCTION__INITIALIZATION = eINSTANCE.getDynamicFunction_Initialization();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.StaticFunctionImpl <em>Static Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.StaticFunctionImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getStaticFunction()
		 * @generated
		 */
		EClass STATIC_FUNCTION = eINSTANCE.getStaticFunction();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.DerivedFunctionImpl <em>Derived Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.DerivedFunctionImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getDerivedFunction()
		 * @generated
		 */
		EClass DERIVED_FUNCTION = eINSTANCE.getDerivedFunction();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.BasicFunctionImpl <em>Basic Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.BasicFunctionImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getBasicFunction()
		 * @generated
		 */
		EClass BASIC_FUNCTION = eINSTANCE.getBasicFunction();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.InvariantImpl <em>Invariant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.InvariantImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getInvariant()
		 * @generated
		 */
		EClass INVARIANT = eINSTANCE.getInvariant();

		/**
		 * The meta object literal for the '<em><b>Constrained Domain</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INVARIANT__CONSTRAINED_DOMAIN = eINSTANCE.getInvariant_ConstrainedDomain();

		/**
		 * The meta object literal for the '<em><b>Constrained Rule</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INVARIANT__CONSTRAINED_RULE = eINSTANCE.getInvariant_ConstrainedRule();

		/**
		 * The meta object literal for the '<em><b>Constrained Function</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INVARIANT__CONSTRAINED_FUNCTION = eINSTANCE.getInvariant_ConstrainedFunction();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INVARIANT__BODY = eINSTANCE.getInvariant_Body();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.FunctionImpl <em>Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.FunctionImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getFunction()
		 * @generated
		 */
		EClass FUNCTION = eINSTANCE.getFunction();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__DOMAIN = eINSTANCE.getFunction_Domain();

		/**
		 * The meta object literal for the '<em><b>Codomain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__CODOMAIN = eINSTANCE.getFunction_Codomain();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__DEFINITION = eINSTANCE.getFunction_Definition();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__CONSTRAINT = eINSTANCE.getFunction_Constraint();

		/**
		 * The meta object literal for the '<em><b>Arity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__ARITY = eINSTANCE.getFunction_Arity();

		/**
		 * The meta object literal for the '<em><b>Signature</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__SIGNATURE = eINSTANCE.getFunction_Signature();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.ClassifierImpl <em>Classifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.ClassifierImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getClassifier()
		 * @generated
		 */
		EClass CLASSIFIER = eINSTANCE.getClassifier();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.PropertyImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.TemporalPropertyImpl <em>Temporal Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.TemporalPropertyImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getTemporalProperty()
		 * @generated
		 */
		EClass TEMPORAL_PROPERTY = eINSTANCE.getTemporalProperty();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPORAL_PROPERTY__BODY = eINSTANCE.getTemporalProperty_Body();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.CtlSpecImpl <em>Ctl Spec</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.CtlSpecImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getCtlSpec()
		 * @generated
		 */
		EClass CTL_SPEC = eINSTANCE.getCtlSpec();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.LtlSpecImpl <em>Ltl Spec</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.LtlSpecImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getLtlSpec()
		 * @generated
		 */
		EClass LTL_SPEC = eINSTANCE.getLtlSpec();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.InvarConstraintImpl <em>Invar Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.InvarConstraintImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getInvarConstraint()
		 * @generated
		 */
		EClass INVAR_CONSTRAINT = eINSTANCE.getInvarConstraint();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INVAR_CONSTRAINT__BODY = eINSTANCE.getInvarConstraint_Body();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.FairnessConstraintImpl <em>Fairness Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.FairnessConstraintImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getFairnessConstraint()
		 * @generated
		 */
		EClass FAIRNESS_CONSTRAINT = eINSTANCE.getFairnessConstraint();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.JusticeConstraintImpl <em>Justice Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.JusticeConstraintImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getJusticeConstraint()
		 * @generated
		 */
		EClass JUSTICE_CONSTRAINT = eINSTANCE.getJusticeConstraint();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JUSTICE_CONSTRAINT__BODY = eINSTANCE.getJusticeConstraint_Body();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.impl.CompassionConstraintImpl <em>Compassion Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.impl.CompassionConstraintImpl
		 * @see asmeta.definitions.impl.DefinitionsPackageImpl#getCompassionConstraint()
		 * @generated
		 */
		EClass COMPASSION_CONSTRAINT = eINSTANCE.getCompassionConstraint();

		/**
		 * The meta object literal for the '<em><b>P</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPASSION_CONSTRAINT__P = eINSTANCE.getCompassionConstraint_P();

		/**
		 * The meta object literal for the '<em><b>Q</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPASSION_CONSTRAINT__Q = eINSTANCE.getCompassionConstraint_Q();

	}

} //DefinitionsPackage

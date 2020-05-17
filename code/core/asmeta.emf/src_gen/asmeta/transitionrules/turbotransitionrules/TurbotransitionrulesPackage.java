/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import asmeta.definitions.DefinitionsPackage;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage;

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
 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesFactory
 * @model kind="package"
 * @generated
 */
public interface TurbotransitionrulesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "turbotransitionrules";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.asmeta/asmm#TurboTransitionRules";

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
	TurbotransitionrulesPackage eINSTANCE = asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl.init();

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.turbotransitionrules.impl.TurboRuleImpl <em>Turbo Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TurboRuleImpl
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getTurboRule()
	 * @generated
	 */
	int TURBO_RULE = 0;

	/**
	 * The number of structural features of the '<em>Turbo Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_RULE_FEATURE_COUNT = BasictransitionrulesPackage.RULE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.turbotransitionrules.impl.TurboDeclarationImpl <em>Turbo Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TurboDeclarationImpl
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getTurboDeclaration()
	 * @generated
	 */
	int TURBO_DECLARATION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_DECLARATION__NAME = DefinitionsPackage.RULE_DECLARATION__NAME;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_DECLARATION__VARIABLE = DefinitionsPackage.RULE_DECLARATION__VARIABLE;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_DECLARATION__CONSTRAINT = DefinitionsPackage.RULE_DECLARATION__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Rule Body</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_DECLARATION__RULE_BODY = DefinitionsPackage.RULE_DECLARATION__RULE_BODY;

	/**
	 * The feature id for the '<em><b>Asm Body</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_DECLARATION__ASM_BODY = DefinitionsPackage.RULE_DECLARATION__ASM_BODY;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_DECLARATION__ARITY = DefinitionsPackage.RULE_DECLARATION__ARITY;

	/**
	 * The feature id for the '<em><b>Result Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_DECLARATION__RESULT_TYPE = DefinitionsPackage.RULE_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Turbo Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_DECLARATION_FEATURE_COUNT = DefinitionsPackage.RULE_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.turbotransitionrules.impl.SeqRuleImpl <em>Seq Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.turbotransitionrules.impl.SeqRuleImpl
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getSeqRule()
	 * @generated
	 */
	int SEQ_RULE = 2;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQ_RULE__RULES = TURBO_RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Seq Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQ_RULE_FEATURE_COUNT = TURBO_RULE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.turbotransitionrules.impl.TurboLocalStateRuleImpl <em>Turbo Local State Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TurboLocalStateRuleImpl
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getTurboLocalStateRule()
	 * @generated
	 */
	int TURBO_LOCAL_STATE_RULE = 3;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_LOCAL_STATE_RULE__INIT = TURBO_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_LOCAL_STATE_RULE__BODY = TURBO_RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Local Function</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_LOCAL_STATE_RULE__LOCAL_FUNCTION = TURBO_RULE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Turbo Local State Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_LOCAL_STATE_RULE_FEATURE_COUNT = TURBO_RULE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.turbotransitionrules.impl.TurboCallRuleImpl <em>Turbo Call Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TurboCallRuleImpl
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getTurboCallRule()
	 * @generated
	 */
	int TURBO_CALL_RULE = 4;

	/**
	 * The feature id for the '<em><b>Called Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_CALL_RULE__CALLED_RULE = TURBO_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_CALL_RULE__PARAMETERS = TURBO_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Turbo Call Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_CALL_RULE_FEATURE_COUNT = TURBO_RULE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.turbotransitionrules.impl.TurboReturnRuleImpl <em>Turbo Return Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TurboReturnRuleImpl
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getTurboReturnRule()
	 * @generated
	 */
	int TURBO_RETURN_RULE = 5;

	/**
	 * The feature id for the '<em><b>Location</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_RETURN_RULE__LOCATION = TURBO_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Update Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_RETURN_RULE__UPDATE_RULE = TURBO_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Turbo Return Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_RETURN_RULE_FEATURE_COUNT = TURBO_RULE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.turbotransitionrules.impl.TryCatchRuleImpl <em>Try Catch Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TryCatchRuleImpl
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getTryCatchRule()
	 * @generated
	 */
	int TRY_CATCH_RULE = 6;

	/**
	 * The feature id for the '<em><b>Location</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRY_CATCH_RULE__LOCATION = TURBO_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Catch Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRY_CATCH_RULE__CATCH_RULE = TURBO_RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Try Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRY_CATCH_RULE__TRY_RULE = TURBO_RULE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Try Catch Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRY_CATCH_RULE_FEATURE_COUNT = TURBO_RULE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.turbotransitionrules.impl.IterateRuleImpl <em>Iterate Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.turbotransitionrules.impl.IterateRuleImpl
	 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getIterateRule()
	 * @generated
	 */
	int ITERATE_RULE = 7;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATE_RULE__RULE = TURBO_RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Iterate Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATE_RULE_FEATURE_COUNT = TURBO_RULE_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.turbotransitionrules.TurboRule <em>Turbo Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Turbo Rule</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboRule
	 * @generated
	 */
	EClass getTurboRule();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.turbotransitionrules.TurboDeclaration <em>Turbo Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Turbo Declaration</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboDeclaration
	 * @generated
	 */
	EClass getTurboDeclaration();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.turbotransitionrules.TurboDeclaration#getResultType <em>Result Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Result Type</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboDeclaration#getResultType()
	 * @see #getTurboDeclaration()
	 * @generated
	 */
	EReference getTurboDeclaration_ResultType();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.turbotransitionrules.SeqRule <em>Seq Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Seq Rule</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.SeqRule
	 * @generated
	 */
	EClass getSeqRule();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.transitionrules.turbotransitionrules.SeqRule#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Rules</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.SeqRule#getRules()
	 * @see #getSeqRule()
	 * @generated
	 */
	EAttribute getSeqRule_Rules();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule <em>Turbo Local State Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Turbo Local State Rule</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule
	 * @generated
	 */
	EClass getTurboLocalStateRule();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule#getInit <em>Init</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Init</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule#getInit()
	 * @see #getTurboLocalStateRule()
	 * @generated
	 */
	EReference getTurboLocalStateRule_Init();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule#getBody()
	 * @see #getTurboLocalStateRule()
	 * @generated
	 */
	EReference getTurboLocalStateRule_Body();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule#getLocalFunction <em>Local Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Local Function</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule#getLocalFunction()
	 * @see #getTurboLocalStateRule()
	 * @generated
	 */
	EReference getTurboLocalStateRule_LocalFunction();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.turbotransitionrules.TurboCallRule <em>Turbo Call Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Turbo Call Rule</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboCallRule
	 * @generated
	 */
	EClass getTurboCallRule();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.turbotransitionrules.TurboCallRule#getCalledRule <em>Called Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Called Rule</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboCallRule#getCalledRule()
	 * @see #getTurboCallRule()
	 * @generated
	 */
	EReference getTurboCallRule_CalledRule();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.transitionrules.turbotransitionrules.TurboCallRule#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parameters</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboCallRule#getParameters()
	 * @see #getTurboCallRule()
	 * @generated
	 */
	EAttribute getTurboCallRule_Parameters();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.turbotransitionrules.TurboReturnRule <em>Turbo Return Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Turbo Return Rule</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboReturnRule
	 * @generated
	 */
	EClass getTurboReturnRule();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.turbotransitionrules.TurboReturnRule#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Location</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboReturnRule#getLocation()
	 * @see #getTurboReturnRule()
	 * @generated
	 */
	EReference getTurboReturnRule_Location();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.turbotransitionrules.TurboReturnRule#getUpdateRule <em>Update Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Update Rule</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboReturnRule#getUpdateRule()
	 * @see #getTurboReturnRule()
	 * @generated
	 */
	EReference getTurboReturnRule_UpdateRule();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.turbotransitionrules.TryCatchRule <em>Try Catch Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Try Catch Rule</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TryCatchRule
	 * @generated
	 */
	EClass getTryCatchRule();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.transitionrules.turbotransitionrules.TryCatchRule#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Location</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TryCatchRule#getLocation()
	 * @see #getTryCatchRule()
	 * @generated
	 */
	EReference getTryCatchRule_Location();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.turbotransitionrules.TryCatchRule#getCatchRule <em>Catch Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Catch Rule</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TryCatchRule#getCatchRule()
	 * @see #getTryCatchRule()
	 * @generated
	 */
	EReference getTryCatchRule_CatchRule();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.turbotransitionrules.TryCatchRule#getTryRule <em>Try Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Try Rule</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.TryCatchRule#getTryRule()
	 * @see #getTryCatchRule()
	 * @generated
	 */
	EReference getTryCatchRule_TryRule();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.turbotransitionrules.IterateRule <em>Iterate Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Iterate Rule</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.IterateRule
	 * @generated
	 */
	EClass getIterateRule();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.turbotransitionrules.IterateRule#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule</em>'.
	 * @see asmeta.transitionrules.turbotransitionrules.IterateRule#getRule()
	 * @see #getIterateRule()
	 * @generated
	 */
	EReference getIterateRule_Rule();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TurbotransitionrulesFactory getTurbotransitionrulesFactory();

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
		 * The meta object literal for the '{@link asmeta.transitionrules.turbotransitionrules.impl.TurboRuleImpl <em>Turbo Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TurboRuleImpl
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getTurboRule()
		 * @generated
		 */
		EClass TURBO_RULE = eINSTANCE.getTurboRule();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.turbotransitionrules.impl.TurboDeclarationImpl <em>Turbo Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TurboDeclarationImpl
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getTurboDeclaration()
		 * @generated
		 */
		EClass TURBO_DECLARATION = eINSTANCE.getTurboDeclaration();

		/**
		 * The meta object literal for the '<em><b>Result Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TURBO_DECLARATION__RESULT_TYPE = eINSTANCE.getTurboDeclaration_ResultType();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.turbotransitionrules.impl.SeqRuleImpl <em>Seq Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.turbotransitionrules.impl.SeqRuleImpl
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getSeqRule()
		 * @generated
		 */
		EClass SEQ_RULE = eINSTANCE.getSeqRule();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEQ_RULE__RULES = eINSTANCE.getSeqRule_Rules();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.turbotransitionrules.impl.TurboLocalStateRuleImpl <em>Turbo Local State Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TurboLocalStateRuleImpl
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getTurboLocalStateRule()
		 * @generated
		 */
		EClass TURBO_LOCAL_STATE_RULE = eINSTANCE.getTurboLocalStateRule();

		/**
		 * The meta object literal for the '<em><b>Init</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TURBO_LOCAL_STATE_RULE__INIT = eINSTANCE.getTurboLocalStateRule_Init();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TURBO_LOCAL_STATE_RULE__BODY = eINSTANCE.getTurboLocalStateRule_Body();

		/**
		 * The meta object literal for the '<em><b>Local Function</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TURBO_LOCAL_STATE_RULE__LOCAL_FUNCTION = eINSTANCE.getTurboLocalStateRule_LocalFunction();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.turbotransitionrules.impl.TurboCallRuleImpl <em>Turbo Call Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TurboCallRuleImpl
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getTurboCallRule()
		 * @generated
		 */
		EClass TURBO_CALL_RULE = eINSTANCE.getTurboCallRule();

		/**
		 * The meta object literal for the '<em><b>Called Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TURBO_CALL_RULE__CALLED_RULE = eINSTANCE.getTurboCallRule_CalledRule();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TURBO_CALL_RULE__PARAMETERS = eINSTANCE.getTurboCallRule_Parameters();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.turbotransitionrules.impl.TurboReturnRuleImpl <em>Turbo Return Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TurboReturnRuleImpl
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getTurboReturnRule()
		 * @generated
		 */
		EClass TURBO_RETURN_RULE = eINSTANCE.getTurboReturnRule();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TURBO_RETURN_RULE__LOCATION = eINSTANCE.getTurboReturnRule_Location();

		/**
		 * The meta object literal for the '<em><b>Update Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TURBO_RETURN_RULE__UPDATE_RULE = eINSTANCE.getTurboReturnRule_UpdateRule();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.turbotransitionrules.impl.TryCatchRuleImpl <em>Try Catch Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TryCatchRuleImpl
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getTryCatchRule()
		 * @generated
		 */
		EClass TRY_CATCH_RULE = eINSTANCE.getTryCatchRule();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRY_CATCH_RULE__LOCATION = eINSTANCE.getTryCatchRule_Location();

		/**
		 * The meta object literal for the '<em><b>Catch Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRY_CATCH_RULE__CATCH_RULE = eINSTANCE.getTryCatchRule_CatchRule();

		/**
		 * The meta object literal for the '<em><b>Try Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRY_CATCH_RULE__TRY_RULE = eINSTANCE.getTryCatchRule_TryRule();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.turbotransitionrules.impl.IterateRuleImpl <em>Iterate Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.turbotransitionrules.impl.IterateRuleImpl
		 * @see asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesPackageImpl#getIterateRule()
		 * @generated
		 */
		EClass ITERATE_RULE = eINSTANCE.getIterateRule();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATE_RULE__RULE = eINSTANCE.getIterateRule_Rule();

	}

} //TurbotransitionrulesPackage

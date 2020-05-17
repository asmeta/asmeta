/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import asmeta.definitions.DefinitionsPackage;

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
 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesFactory
 * @model kind="package"
 * @generated
 */
public interface BasictransitionrulesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "basictransitionrules";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.asmeta/asmm#BasicTransitionRules";

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
	BasictransitionrulesPackage eINSTANCE = asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl.init();

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.basictransitionrules.impl.RuleImpl <em>Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.impl.RuleImpl
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getRule()
	 * @generated
	 */
	int RULE = 2;

	/**
	 * The number of structural features of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.basictransitionrules.impl.TermAsRuleImpl <em>Term As Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.impl.TermAsRuleImpl
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getTermAsRule()
	 * @generated
	 */
	int TERM_AS_RULE = 0;

	/**
	 * The feature id for the '<em><b>Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_AS_RULE__TERM = RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_AS_RULE__PARAMETERS = RULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Term As Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_AS_RULE_FEATURE_COUNT = RULE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.basictransitionrules.impl.BasicRuleImpl <em>Basic Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasicRuleImpl
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getBasicRule()
	 * @generated
	 */
	int BASIC_RULE = 1;

	/**
	 * The number of structural features of the '<em>Basic Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_RULE_FEATURE_COUNT = RULE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.basictransitionrules.impl.ChooseRuleImpl <em>Choose Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.impl.ChooseRuleImpl
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getChooseRule()
	 * @generated
	 */
	int CHOOSE_RULE = 3;

	/**
	 * The feature id for the '<em><b>Ifnone</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOOSE_RULE__IFNONE = BASIC_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Do Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOOSE_RULE__DO_RULE = BASIC_RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOOSE_RULE__GUARD = BASIC_RULE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOOSE_RULE__VARIABLE = BASIC_RULE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOOSE_RULE__RANGES = BASIC_RULE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Choose Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOOSE_RULE_FEATURE_COUNT = BASIC_RULE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.basictransitionrules.impl.MacroCallRuleImpl <em>Macro Call Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.impl.MacroCallRuleImpl
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getMacroCallRule()
	 * @generated
	 */
	int MACRO_CALL_RULE = 4;

	/**
	 * The feature id for the '<em><b>Called Macro</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_CALL_RULE__CALLED_MACRO = BASIC_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_CALL_RULE__PARAMETERS = BASIC_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Macro Call Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_CALL_RULE_FEATURE_COUNT = BASIC_RULE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.basictransitionrules.impl.BlockRuleImpl <em>Block Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.impl.BlockRuleImpl
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getBlockRule()
	 * @generated
	 */
	int BLOCK_RULE = 5;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_RULE__RULES = BASIC_RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Block Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_RULE_FEATURE_COUNT = BASIC_RULE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.basictransitionrules.impl.ConditionalRuleImpl <em>Conditional Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.impl.ConditionalRuleImpl
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getConditionalRule()
	 * @generated
	 */
	int CONDITIONAL_RULE = 6;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_RULE__GUARD = BASIC_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Else Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_RULE__ELSE_RULE = BASIC_RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Then Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_RULE__THEN_RULE = BASIC_RULE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Conditional Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_RULE_FEATURE_COUNT = BASIC_RULE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.basictransitionrules.impl.ForallRuleImpl <em>Forall Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.impl.ForallRuleImpl
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getForallRule()
	 * @generated
	 */
	int FORALL_RULE = 7;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_RULE__VARIABLE = BASIC_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_RULE__GUARD = BASIC_RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Do Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_RULE__DO_RULE = BASIC_RULE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_RULE__RANGES = BASIC_RULE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Forall Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_RULE_FEATURE_COUNT = BASIC_RULE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.basictransitionrules.impl.LetRuleImpl <em>Let Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.impl.LetRuleImpl
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getLetRule()
	 * @generated
	 */
	int LET_RULE = 8;

	/**
	 * The feature id for the '<em><b>In Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LET_RULE__IN_RULE = BASIC_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Init Expression</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LET_RULE__INIT_EXPRESSION = BASIC_RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LET_RULE__VARIABLE = BASIC_RULE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Let Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LET_RULE_FEATURE_COUNT = BASIC_RULE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.basictransitionrules.impl.ExtendRuleImpl <em>Extend Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.impl.ExtendRuleImpl
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getExtendRule()
	 * @generated
	 */
	int EXTEND_RULE = 9;

	/**
	 * The feature id for the '<em><b>Extended Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTEND_RULE__EXTENDED_DOMAIN = BASIC_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Bound Var</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTEND_RULE__BOUND_VAR = BASIC_RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Do Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTEND_RULE__DO_RULE = BASIC_RULE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Extend Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTEND_RULE_FEATURE_COUNT = BASIC_RULE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.basictransitionrules.impl.UpdateRuleImpl <em>Update Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.impl.UpdateRuleImpl
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getUpdateRule()
	 * @generated
	 */
	int UPDATE_RULE = 10;

	/**
	 * The feature id for the '<em><b>Updating Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_RULE__UPDATING_TERM = BASIC_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Location</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_RULE__LOCATION = BASIC_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Update Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UPDATE_RULE_FEATURE_COUNT = BASIC_RULE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.basictransitionrules.impl.SkipRuleImpl <em>Skip Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.impl.SkipRuleImpl
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getSkipRule()
	 * @generated
	 */
	int SKIP_RULE = 11;

	/**
	 * The number of structural features of the '<em>Skip Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SKIP_RULE_FEATURE_COUNT = BASIC_RULE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.basictransitionrules.impl.MacroDeclarationImpl <em>Macro Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.impl.MacroDeclarationImpl
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getMacroDeclaration()
	 * @generated
	 */
	int MACRO_DECLARATION = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_DECLARATION__NAME = DefinitionsPackage.RULE_DECLARATION__NAME;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_DECLARATION__VARIABLE = DefinitionsPackage.RULE_DECLARATION__VARIABLE;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_DECLARATION__CONSTRAINT = DefinitionsPackage.RULE_DECLARATION__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Rule Body</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_DECLARATION__RULE_BODY = DefinitionsPackage.RULE_DECLARATION__RULE_BODY;

	/**
	 * The feature id for the '<em><b>Asm Body</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_DECLARATION__ASM_BODY = DefinitionsPackage.RULE_DECLARATION__ASM_BODY;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_DECLARATION__ARITY = DefinitionsPackage.RULE_DECLARATION__ARITY;

	/**
	 * The number of structural features of the '<em>Macro Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MACRO_DECLARATION_FEATURE_COUNT = DefinitionsPackage.RULE_DECLARATION_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '<em>Rule DT</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.basictransitionrules.Rule
	 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getRuleDT()
	 * @generated
	 */
	int RULE_DT = 13;


	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.basictransitionrules.TermAsRule <em>Term As Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Term As Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.TermAsRule
	 * @generated
	 */
	EClass getTermAsRule();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.basictransitionrules.TermAsRule#getTerm <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Term</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.TermAsRule#getTerm()
	 * @see #getTermAsRule()
	 * @generated
	 */
	EReference getTermAsRule_Term();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.transitionrules.basictransitionrules.TermAsRule#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parameters</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.TermAsRule#getParameters()
	 * @see #getTermAsRule()
	 * @generated
	 */
	EAttribute getTermAsRule_Parameters();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.basictransitionrules.BasicRule <em>Basic Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.BasicRule
	 * @generated
	 */
	EClass getBasicRule();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.basictransitionrules.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.Rule
	 * @generated
	 */
	EClass getRule();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.basictransitionrules.ChooseRule <em>Choose Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Choose Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ChooseRule
	 * @generated
	 */
	EClass getChooseRule();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.basictransitionrules.ChooseRule#getIfnone <em>Ifnone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ifnone</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ChooseRule#getIfnone()
	 * @see #getChooseRule()
	 * @generated
	 */
	EReference getChooseRule_Ifnone();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.basictransitionrules.ChooseRule#getDoRule <em>Do Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Do Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ChooseRule#getDoRule()
	 * @see #getChooseRule()
	 * @generated
	 */
	EReference getChooseRule_DoRule();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.basictransitionrules.ChooseRule#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Guard</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ChooseRule#getGuard()
	 * @see #getChooseRule()
	 * @generated
	 */
	EReference getChooseRule_Guard();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.transitionrules.basictransitionrules.ChooseRule#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Variable</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ChooseRule#getVariable()
	 * @see #getChooseRule()
	 * @generated
	 */
	EReference getChooseRule_Variable();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.transitionrules.basictransitionrules.ChooseRule#getRanges <em>Ranges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Ranges</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ChooseRule#getRanges()
	 * @see #getChooseRule()
	 * @generated
	 */
	EAttribute getChooseRule_Ranges();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.basictransitionrules.MacroCallRule <em>Macro Call Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Macro Call Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.MacroCallRule
	 * @generated
	 */
	EClass getMacroCallRule();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.basictransitionrules.MacroCallRule#getCalledMacro <em>Called Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Called Macro</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.MacroCallRule#getCalledMacro()
	 * @see #getMacroCallRule()
	 * @generated
	 */
	EReference getMacroCallRule_CalledMacro();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.transitionrules.basictransitionrules.MacroCallRule#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parameters</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.MacroCallRule#getParameters()
	 * @see #getMacroCallRule()
	 * @generated
	 */
	EAttribute getMacroCallRule_Parameters();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.basictransitionrules.BlockRule <em>Block Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.BlockRule
	 * @generated
	 */
	EClass getBlockRule();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.transitionrules.basictransitionrules.BlockRule#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Rules</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.BlockRule#getRules()
	 * @see #getBlockRule()
	 * @generated
	 */
	EAttribute getBlockRule_Rules();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.basictransitionrules.ConditionalRule <em>Conditional Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conditional Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ConditionalRule
	 * @generated
	 */
	EClass getConditionalRule();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.basictransitionrules.ConditionalRule#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Guard</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ConditionalRule#getGuard()
	 * @see #getConditionalRule()
	 * @generated
	 */
	EReference getConditionalRule_Guard();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.basictransitionrules.ConditionalRule#getElseRule <em>Else Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Else Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ConditionalRule#getElseRule()
	 * @see #getConditionalRule()
	 * @generated
	 */
	EReference getConditionalRule_ElseRule();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.basictransitionrules.ConditionalRule#getThenRule <em>Then Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Then Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ConditionalRule#getThenRule()
	 * @see #getConditionalRule()
	 * @generated
	 */
	EReference getConditionalRule_ThenRule();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.basictransitionrules.ForallRule <em>Forall Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Forall Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ForallRule
	 * @generated
	 */
	EClass getForallRule();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.transitionrules.basictransitionrules.ForallRule#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Variable</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ForallRule#getVariable()
	 * @see #getForallRule()
	 * @generated
	 */
	EReference getForallRule_Variable();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.basictransitionrules.ForallRule#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Guard</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ForallRule#getGuard()
	 * @see #getForallRule()
	 * @generated
	 */
	EReference getForallRule_Guard();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.basictransitionrules.ForallRule#getDoRule <em>Do Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Do Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ForallRule#getDoRule()
	 * @see #getForallRule()
	 * @generated
	 */
	EReference getForallRule_DoRule();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.transitionrules.basictransitionrules.ForallRule#getRanges <em>Ranges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Ranges</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ForallRule#getRanges()
	 * @see #getForallRule()
	 * @generated
	 */
	EAttribute getForallRule_Ranges();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.basictransitionrules.LetRule <em>Let Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Let Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.LetRule
	 * @generated
	 */
	EClass getLetRule();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.basictransitionrules.LetRule#getInRule <em>In Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>In Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.LetRule#getInRule()
	 * @see #getLetRule()
	 * @generated
	 */
	EReference getLetRule_InRule();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.transitionrules.basictransitionrules.LetRule#getInitExpression <em>Init Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Init Expression</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.LetRule#getInitExpression()
	 * @see #getLetRule()
	 * @generated
	 */
	EReference getLetRule_InitExpression();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.transitionrules.basictransitionrules.LetRule#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Variable</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.LetRule#getVariable()
	 * @see #getLetRule()
	 * @generated
	 */
	EReference getLetRule_Variable();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.basictransitionrules.ExtendRule <em>Extend Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extend Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ExtendRule
	 * @generated
	 */
	EClass getExtendRule();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.basictransitionrules.ExtendRule#getExtendedDomain <em>Extended Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Extended Domain</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ExtendRule#getExtendedDomain()
	 * @see #getExtendRule()
	 * @generated
	 */
	EReference getExtendRule_ExtendedDomain();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.transitionrules.basictransitionrules.ExtendRule#getBoundVar <em>Bound Var</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Bound Var</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ExtendRule#getBoundVar()
	 * @see #getExtendRule()
	 * @generated
	 */
	EReference getExtendRule_BoundVar();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.basictransitionrules.ExtendRule#getDoRule <em>Do Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Do Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.ExtendRule#getDoRule()
	 * @see #getExtendRule()
	 * @generated
	 */
	EReference getExtendRule_DoRule();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.basictransitionrules.UpdateRule <em>Update Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Update Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.UpdateRule
	 * @generated
	 */
	EClass getUpdateRule();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.basictransitionrules.UpdateRule#getUpdatingTerm <em>Updating Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Updating Term</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.UpdateRule#getUpdatingTerm()
	 * @see #getUpdateRule()
	 * @generated
	 */
	EReference getUpdateRule_UpdatingTerm();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.basictransitionrules.UpdateRule#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Location</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.UpdateRule#getLocation()
	 * @see #getUpdateRule()
	 * @generated
	 */
	EReference getUpdateRule_Location();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.basictransitionrules.SkipRule <em>Skip Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Skip Rule</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.SkipRule
	 * @generated
	 */
	EClass getSkipRule();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.basictransitionrules.MacroDeclaration <em>Macro Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Macro Declaration</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.MacroDeclaration
	 * @generated
	 */
	EClass getMacroDeclaration();

	/**
	 * Returns the meta object for data type '{@link asmeta.transitionrules.basictransitionrules.Rule <em>Rule DT</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Rule DT</em>'.
	 * @see asmeta.transitionrules.basictransitionrules.Rule
	 * @model instanceClass="asmeta.transitionrules.basictransitionrules.Rule"
	 * @generated
	 */
	EDataType getRuleDT();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BasictransitionrulesFactory getBasictransitionrulesFactory();

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
		 * The meta object literal for the '{@link asmeta.transitionrules.basictransitionrules.impl.TermAsRuleImpl <em>Term As Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.impl.TermAsRuleImpl
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getTermAsRule()
		 * @generated
		 */
		EClass TERM_AS_RULE = eINSTANCE.getTermAsRule();

		/**
		 * The meta object literal for the '<em><b>Term</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERM_AS_RULE__TERM = eINSTANCE.getTermAsRule_Term();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TERM_AS_RULE__PARAMETERS = eINSTANCE.getTermAsRule_Parameters();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.basictransitionrules.impl.BasicRuleImpl <em>Basic Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasicRuleImpl
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getBasicRule()
		 * @generated
		 */
		EClass BASIC_RULE = eINSTANCE.getBasicRule();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.basictransitionrules.impl.RuleImpl <em>Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.impl.RuleImpl
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getRule()
		 * @generated
		 */
		EClass RULE = eINSTANCE.getRule();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.basictransitionrules.impl.ChooseRuleImpl <em>Choose Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.impl.ChooseRuleImpl
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getChooseRule()
		 * @generated
		 */
		EClass CHOOSE_RULE = eINSTANCE.getChooseRule();

		/**
		 * The meta object literal for the '<em><b>Ifnone</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOOSE_RULE__IFNONE = eINSTANCE.getChooseRule_Ifnone();

		/**
		 * The meta object literal for the '<em><b>Do Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOOSE_RULE__DO_RULE = eINSTANCE.getChooseRule_DoRule();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOOSE_RULE__GUARD = eINSTANCE.getChooseRule_Guard();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOOSE_RULE__VARIABLE = eINSTANCE.getChooseRule_Variable();

		/**
		 * The meta object literal for the '<em><b>Ranges</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHOOSE_RULE__RANGES = eINSTANCE.getChooseRule_Ranges();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.basictransitionrules.impl.MacroCallRuleImpl <em>Macro Call Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.impl.MacroCallRuleImpl
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getMacroCallRule()
		 * @generated
		 */
		EClass MACRO_CALL_RULE = eINSTANCE.getMacroCallRule();

		/**
		 * The meta object literal for the '<em><b>Called Macro</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MACRO_CALL_RULE__CALLED_MACRO = eINSTANCE.getMacroCallRule_CalledMacro();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MACRO_CALL_RULE__PARAMETERS = eINSTANCE.getMacroCallRule_Parameters();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.basictransitionrules.impl.BlockRuleImpl <em>Block Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.impl.BlockRuleImpl
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getBlockRule()
		 * @generated
		 */
		EClass BLOCK_RULE = eINSTANCE.getBlockRule();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLOCK_RULE__RULES = eINSTANCE.getBlockRule_Rules();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.basictransitionrules.impl.ConditionalRuleImpl <em>Conditional Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.impl.ConditionalRuleImpl
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getConditionalRule()
		 * @generated
		 */
		EClass CONDITIONAL_RULE = eINSTANCE.getConditionalRule();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_RULE__GUARD = eINSTANCE.getConditionalRule_Guard();

		/**
		 * The meta object literal for the '<em><b>Else Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_RULE__ELSE_RULE = eINSTANCE.getConditionalRule_ElseRule();

		/**
		 * The meta object literal for the '<em><b>Then Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_RULE__THEN_RULE = eINSTANCE.getConditionalRule_ThenRule();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.basictransitionrules.impl.ForallRuleImpl <em>Forall Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.impl.ForallRuleImpl
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getForallRule()
		 * @generated
		 */
		EClass FORALL_RULE = eINSTANCE.getForallRule();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FORALL_RULE__VARIABLE = eINSTANCE.getForallRule_Variable();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FORALL_RULE__GUARD = eINSTANCE.getForallRule_Guard();

		/**
		 * The meta object literal for the '<em><b>Do Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FORALL_RULE__DO_RULE = eINSTANCE.getForallRule_DoRule();

		/**
		 * The meta object literal for the '<em><b>Ranges</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FORALL_RULE__RANGES = eINSTANCE.getForallRule_Ranges();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.basictransitionrules.impl.LetRuleImpl <em>Let Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.impl.LetRuleImpl
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getLetRule()
		 * @generated
		 */
		EClass LET_RULE = eINSTANCE.getLetRule();

		/**
		 * The meta object literal for the '<em><b>In Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LET_RULE__IN_RULE = eINSTANCE.getLetRule_InRule();

		/**
		 * The meta object literal for the '<em><b>Init Expression</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LET_RULE__INIT_EXPRESSION = eINSTANCE.getLetRule_InitExpression();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LET_RULE__VARIABLE = eINSTANCE.getLetRule_Variable();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.basictransitionrules.impl.ExtendRuleImpl <em>Extend Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.impl.ExtendRuleImpl
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getExtendRule()
		 * @generated
		 */
		EClass EXTEND_RULE = eINSTANCE.getExtendRule();

		/**
		 * The meta object literal for the '<em><b>Extended Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTEND_RULE__EXTENDED_DOMAIN = eINSTANCE.getExtendRule_ExtendedDomain();

		/**
		 * The meta object literal for the '<em><b>Bound Var</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTEND_RULE__BOUND_VAR = eINSTANCE.getExtendRule_BoundVar();

		/**
		 * The meta object literal for the '<em><b>Do Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTEND_RULE__DO_RULE = eINSTANCE.getExtendRule_DoRule();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.basictransitionrules.impl.UpdateRuleImpl <em>Update Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.impl.UpdateRuleImpl
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getUpdateRule()
		 * @generated
		 */
		EClass UPDATE_RULE = eINSTANCE.getUpdateRule();

		/**
		 * The meta object literal for the '<em><b>Updating Term</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UPDATE_RULE__UPDATING_TERM = eINSTANCE.getUpdateRule_UpdatingTerm();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UPDATE_RULE__LOCATION = eINSTANCE.getUpdateRule_Location();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.basictransitionrules.impl.SkipRuleImpl <em>Skip Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.impl.SkipRuleImpl
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getSkipRule()
		 * @generated
		 */
		EClass SKIP_RULE = eINSTANCE.getSkipRule();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.basictransitionrules.impl.MacroDeclarationImpl <em>Macro Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.impl.MacroDeclarationImpl
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getMacroDeclaration()
		 * @generated
		 */
		EClass MACRO_DECLARATION = eINSTANCE.getMacroDeclaration();

		/**
		 * The meta object literal for the '<em>Rule DT</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.basictransitionrules.Rule
		 * @see asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesPackageImpl#getRuleDT()
		 * @generated
		 */
		EDataType RULE_DT = eINSTANCE.getRuleDT();

	}

} //BasictransitionrulesPackage

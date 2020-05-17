/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.derivedtransitionrules;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesFactory
 * @model kind="package"
 * @generated
 */
public interface DerivedtransitionrulesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "derivedtransitionrules";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.asmeta/asmm#DerivedTransitionRules";

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
	DerivedtransitionrulesPackage eINSTANCE = asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl.init();

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.derivedtransitionrules.impl.DerivedRuleImpl <em>Derived Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedRuleImpl
	 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl#getDerivedRule()
	 * @generated
	 */
	int DERIVED_RULE = 2;

	/**
	 * The number of structural features of the '<em>Derived Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_RULE_FEATURE_COUNT = BasictransitionrulesPackage.RULE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.derivedtransitionrules.impl.TurboDerivedRuleImpl <em>Turbo Derived Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.derivedtransitionrules.impl.TurboDerivedRuleImpl
	 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl#getTurboDerivedRule()
	 * @generated
	 */
	int TURBO_DERIVED_RULE = 5;

	/**
	 * The number of structural features of the '<em>Turbo Derived Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TURBO_DERIVED_RULE_FEATURE_COUNT = DERIVED_RULE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.derivedtransitionrules.impl.RecursiveWhileRuleImpl <em>Recursive While Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.derivedtransitionrules.impl.RecursiveWhileRuleImpl
	 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl#getRecursiveWhileRule()
	 * @generated
	 */
	int RECURSIVE_WHILE_RULE = 0;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECURSIVE_WHILE_RULE__RULE = TURBO_DERIVED_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECURSIVE_WHILE_RULE__GUARD = TURBO_DERIVED_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Recursive While Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECURSIVE_WHILE_RULE_FEATURE_COUNT = TURBO_DERIVED_RULE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.derivedtransitionrules.impl.IterativeWhileRuleImpl <em>Iterative While Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.derivedtransitionrules.impl.IterativeWhileRuleImpl
	 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl#getIterativeWhileRule()
	 * @generated
	 */
	int ITERATIVE_WHILE_RULE = 1;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATIVE_WHILE_RULE__GUARD = TURBO_DERIVED_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATIVE_WHILE_RULE__RULE = TURBO_DERIVED_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Iterative While Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATIVE_WHILE_RULE_FEATURE_COUNT = TURBO_DERIVED_RULE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.derivedtransitionrules.impl.BasicDerivedRuleImpl <em>Basic Derived Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.derivedtransitionrules.impl.BasicDerivedRuleImpl
	 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl#getBasicDerivedRule()
	 * @generated
	 */
	int BASIC_DERIVED_RULE = 4;

	/**
	 * The number of structural features of the '<em>Basic Derived Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_DERIVED_RULE_FEATURE_COUNT = DERIVED_RULE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.transitionrules.derivedtransitionrules.impl.CaseRuleImpl <em>Case Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.transitionrules.derivedtransitionrules.impl.CaseRuleImpl
	 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl#getCaseRule()
	 * @generated
	 */
	int CASE_RULE = 3;

	/**
	 * The feature id for the '<em><b>Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_RULE__TERM = BASIC_DERIVED_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Case Term</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_RULE__CASE_TERM = BASIC_DERIVED_RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Otherwise Branch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_RULE__OTHERWISE_BRANCH = BASIC_DERIVED_RULE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Case Branches</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_RULE__CASE_BRANCHES = BASIC_DERIVED_RULE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Case Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_RULE_FEATURE_COUNT = BASIC_DERIVED_RULE_FEATURE_COUNT + 4;


	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule <em>Recursive While Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Recursive While Rule</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule
	 * @generated
	 */
	EClass getRecursiveWhileRule();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule#getRule()
	 * @see #getRecursiveWhileRule()
	 * @generated
	 */
	EReference getRecursiveWhileRule_Rule();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Guard</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule#getGuard()
	 * @see #getRecursiveWhileRule()
	 * @generated
	 */
	EReference getRecursiveWhileRule_Guard();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule <em>Iterative While Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Iterative While Rule</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule
	 * @generated
	 */
	EClass getIterativeWhileRule();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Guard</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule#getGuard()
	 * @see #getIterativeWhileRule()
	 * @generated
	 */
	EReference getIterativeWhileRule_Guard();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule#getRule()
	 * @see #getIterativeWhileRule()
	 * @generated
	 */
	EReference getIterativeWhileRule_Rule();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.derivedtransitionrules.DerivedRule <em>Derived Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Derived Rule</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.DerivedRule
	 * @generated
	 */
	EClass getDerivedRule();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.derivedtransitionrules.CaseRule <em>Case Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Case Rule</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.CaseRule
	 * @generated
	 */
	EClass getCaseRule();

	/**
	 * Returns the meta object for the reference '{@link asmeta.transitionrules.derivedtransitionrules.CaseRule#getTerm <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Term</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.CaseRule#getTerm()
	 * @see #getCaseRule()
	 * @generated
	 */
	EReference getCaseRule_Term();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.transitionrules.derivedtransitionrules.CaseRule#getCaseTerm <em>Case Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Case Term</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.CaseRule#getCaseTerm()
	 * @see #getCaseRule()
	 * @generated
	 */
	EReference getCaseRule_CaseTerm();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.transitionrules.derivedtransitionrules.CaseRule#getOtherwiseBranch <em>Otherwise Branch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Otherwise Branch</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.CaseRule#getOtherwiseBranch()
	 * @see #getCaseRule()
	 * @generated
	 */
	EReference getCaseRule_OtherwiseBranch();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.transitionrules.derivedtransitionrules.CaseRule#getCaseBranches <em>Case Branches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Case Branches</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.CaseRule#getCaseBranches()
	 * @see #getCaseRule()
	 * @generated
	 */
	EAttribute getCaseRule_CaseBranches();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.derivedtransitionrules.BasicDerivedRule <em>Basic Derived Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic Derived Rule</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.BasicDerivedRule
	 * @generated
	 */
	EClass getBasicDerivedRule();

	/**
	 * Returns the meta object for class '{@link asmeta.transitionrules.derivedtransitionrules.TurboDerivedRule <em>Turbo Derived Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Turbo Derived Rule</em>'.
	 * @see asmeta.transitionrules.derivedtransitionrules.TurboDerivedRule
	 * @generated
	 */
	EClass getTurboDerivedRule();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DerivedtransitionrulesFactory getDerivedtransitionrulesFactory();

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
		 * The meta object literal for the '{@link asmeta.transitionrules.derivedtransitionrules.impl.RecursiveWhileRuleImpl <em>Recursive While Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.derivedtransitionrules.impl.RecursiveWhileRuleImpl
		 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl#getRecursiveWhileRule()
		 * @generated
		 */
		EClass RECURSIVE_WHILE_RULE = eINSTANCE.getRecursiveWhileRule();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RECURSIVE_WHILE_RULE__RULE = eINSTANCE.getRecursiveWhileRule_Rule();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RECURSIVE_WHILE_RULE__GUARD = eINSTANCE.getRecursiveWhileRule_Guard();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.derivedtransitionrules.impl.IterativeWhileRuleImpl <em>Iterative While Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.derivedtransitionrules.impl.IterativeWhileRuleImpl
		 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl#getIterativeWhileRule()
		 * @generated
		 */
		EClass ITERATIVE_WHILE_RULE = eINSTANCE.getIterativeWhileRule();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATIVE_WHILE_RULE__GUARD = eINSTANCE.getIterativeWhileRule_Guard();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATIVE_WHILE_RULE__RULE = eINSTANCE.getIterativeWhileRule_Rule();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.derivedtransitionrules.impl.DerivedRuleImpl <em>Derived Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedRuleImpl
		 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl#getDerivedRule()
		 * @generated
		 */
		EClass DERIVED_RULE = eINSTANCE.getDerivedRule();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.derivedtransitionrules.impl.CaseRuleImpl <em>Case Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.derivedtransitionrules.impl.CaseRuleImpl
		 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl#getCaseRule()
		 * @generated
		 */
		EClass CASE_RULE = eINSTANCE.getCaseRule();

		/**
		 * The meta object literal for the '<em><b>Term</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASE_RULE__TERM = eINSTANCE.getCaseRule_Term();

		/**
		 * The meta object literal for the '<em><b>Case Term</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASE_RULE__CASE_TERM = eINSTANCE.getCaseRule_CaseTerm();

		/**
		 * The meta object literal for the '<em><b>Otherwise Branch</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASE_RULE__OTHERWISE_BRANCH = eINSTANCE.getCaseRule_OtherwiseBranch();

		/**
		 * The meta object literal for the '<em><b>Case Branches</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CASE_RULE__CASE_BRANCHES = eINSTANCE.getCaseRule_CaseBranches();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.derivedtransitionrules.impl.BasicDerivedRuleImpl <em>Basic Derived Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.derivedtransitionrules.impl.BasicDerivedRuleImpl
		 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl#getBasicDerivedRule()
		 * @generated
		 */
		EClass BASIC_DERIVED_RULE = eINSTANCE.getBasicDerivedRule();

		/**
		 * The meta object literal for the '{@link asmeta.transitionrules.derivedtransitionrules.impl.TurboDerivedRuleImpl <em>Turbo Derived Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.transitionrules.derivedtransitionrules.impl.TurboDerivedRuleImpl
		 * @see asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesPackageImpl#getTurboDerivedRule()
		 * @generated
		 */
		EClass TURBO_DERIVED_RULE = eINSTANCE.getTurboDerivedRule();

	}

} //DerivedtransitionrulesPackage

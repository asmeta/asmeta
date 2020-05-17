/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
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
 * @see asmeta.terms.basicterms.BasictermsFactory
 * @model kind="package"
 * @generated
 */
public interface BasictermsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "basicterms";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.asmeta/asmm#BasicTerms";

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
	BasictermsPackage eINSTANCE = asmeta.terms.basicterms.impl.BasictermsPackageImpl.init();

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.TermImpl <em>Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.TermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getTerm()
	 * @generated
	 */
	int TERM = 13;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM__DOMAIN = 0;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM__TERM_AS_RULE = 1;

	/**
	 * The number of structural features of the '<em>Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.BasicTermImpl <em>Basic Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.BasicTermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getBasicTerm()
	 * @generated
	 */
	int BASIC_TERM = 12;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_TERM__DOMAIN = TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_TERM__TERM_AS_RULE = TERM__TERM_AS_RULE;

	/**
	 * The number of structural features of the '<em>Basic Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_TERM_FEATURE_COUNT = TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.VariableTermImpl <em>Variable Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.VariableTermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getVariableTerm()
	 * @generated
	 */
	int VARIABLE_TERM = 0;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_TERM__DOMAIN = BASIC_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_TERM__TERM_AS_RULE = BASIC_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Finite Quantification Term</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_TERM__FINITE_QUANTIFICATION_TERM = BASIC_TERM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_TERM__NAME = BASIC_TERM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_TERM__KIND = BASIC_TERM_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Variable Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_TERM_FEATURE_COUNT = BASIC_TERM_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.ConstantTermImpl <em>Constant Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.ConstantTermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getConstantTerm()
	 * @generated
	 */
	int CONSTANT_TERM = 9;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_TERM__DOMAIN = BASIC_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_TERM__TERM_AS_RULE = BASIC_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_TERM__SYMBOL = BASIC_TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Constant Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_TERM_FEATURE_COUNT = BASIC_TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.UndefTermImpl <em>Undef Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.UndefTermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getUndefTerm()
	 * @generated
	 */
	int UNDEF_TERM = 1;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNDEF_TERM__DOMAIN = CONSTANT_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNDEF_TERM__TERM_AS_RULE = CONSTANT_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNDEF_TERM__SYMBOL = CONSTANT_TERM__SYMBOL;

	/**
	 * The number of structural features of the '<em>Undef Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNDEF_TERM_FEATURE_COUNT = CONSTANT_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.ExtendedTermImpl <em>Extended Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.ExtendedTermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getExtendedTerm()
	 * @generated
	 */
	int EXTENDED_TERM = 7;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENDED_TERM__DOMAIN = TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENDED_TERM__TERM_AS_RULE = TERM__TERM_AS_RULE;

	/**
	 * The number of structural features of the '<em>Extended Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTENDED_TERM_FEATURE_COUNT = TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.TupleTermImpl <em>Tuple Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.TupleTermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getTupleTerm()
	 * @generated
	 */
	int TUPLE_TERM = 2;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TUPLE_TERM__DOMAIN = EXTENDED_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TUPLE_TERM__TERM_AS_RULE = EXTENDED_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TUPLE_TERM__ARITY = EXTENDED_TERM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Terms</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TUPLE_TERM__TERMS = EXTENDED_TERM_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Tuple Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TUPLE_TERM_FEATURE_COUNT = EXTENDED_TERM_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.CollectionTermImpl <em>Collection Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.CollectionTermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getCollectionTerm()
	 * @generated
	 */
	int COLLECTION_TERM = 10;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLLECTION_TERM__DOMAIN = EXTENDED_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLLECTION_TERM__TERM_AS_RULE = EXTENDED_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLLECTION_TERM__SIZE = EXTENDED_TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Collection Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLLECTION_TERM_FEATURE_COUNT = EXTENDED_TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.SetTermImpl <em>Set Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.SetTermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getSetTerm()
	 * @generated
	 */
	int SET_TERM = 3;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_TERM__DOMAIN = COLLECTION_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_TERM__TERM_AS_RULE = COLLECTION_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_TERM__SIZE = COLLECTION_TERM__SIZE;

	/**
	 * The feature id for the '<em><b>Term</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_TERM__TERM = COLLECTION_TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Set Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_TERM_FEATURE_COUNT = COLLECTION_TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.RuleAsTermImpl <em>Rule As Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.RuleAsTermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getRuleAsTerm()
	 * @generated
	 */
	int RULE_AS_TERM = 4;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_AS_TERM__DOMAIN = EXTENDED_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_AS_TERM__TERM_AS_RULE = EXTENDED_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_AS_TERM__RULE = EXTENDED_TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Rule As Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_AS_TERM_FEATURE_COUNT = EXTENDED_TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.FunctionTermImpl <em>Function Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.FunctionTermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getFunctionTerm()
	 * @generated
	 */
	int FUNCTION_TERM = 6;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TERM__DOMAIN = BASIC_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TERM__TERM_AS_RULE = BASIC_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TERM__ARGUMENTS = BASIC_TERM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TERM__FUNCTION = BASIC_TERM_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Function Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TERM_FEATURE_COUNT = BASIC_TERM_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.LocationTermImpl <em>Location Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.LocationTermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getLocationTerm()
	 * @generated
	 */
	int LOCATION_TERM = 5;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_TERM__DOMAIN = FUNCTION_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_TERM__TERM_AS_RULE = FUNCTION_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_TERM__ARGUMENTS = FUNCTION_TERM__ARGUMENTS;

	/**
	 * The feature id for the '<em><b>Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_TERM__FUNCTION = FUNCTION_TERM__FUNCTION;

	/**
	 * The number of structural features of the '<em>Location Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_TERM_FEATURE_COUNT = FUNCTION_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.DomainTermImpl <em>Domain Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.DomainTermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getDomainTerm()
	 * @generated
	 */
	int DOMAIN_TERM = 8;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_TERM__DOMAIN = EXTENDED_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_TERM__TERM_AS_RULE = EXTENDED_TERM__TERM_AS_RULE;

	/**
	 * The number of structural features of the '<em>Domain Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_TERM_FEATURE_COUNT = EXTENDED_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.impl.BooleanTermImpl <em>Boolean Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.impl.BooleanTermImpl
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getBooleanTerm()
	 * @generated
	 */
	int BOOLEAN_TERM = 11;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TERM__DOMAIN = CONSTANT_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TERM__TERM_AS_RULE = CONSTANT_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TERM__SYMBOL = CONSTANT_TERM__SYMBOL;

	/**
	 * The number of structural features of the '<em>Boolean Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TERM_FEATURE_COUNT = CONSTANT_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.basicterms.VariableKind <em>Variable Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.VariableKind
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getVariableKind()
	 * @generated
	 */
	int VARIABLE_KIND = 14;


	/**
	 * The meta object id for the '<em>Term DT</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.basicterms.Term
	 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getTermDT()
	 * @generated
	 */
	int TERM_DT = 15;


	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.VariableTerm <em>Variable Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Term</em>'.
	 * @see asmeta.terms.basicterms.VariableTerm
	 * @generated
	 */
	EClass getVariableTerm();

	/**
	 * Returns the meta object for the container reference '{@link asmeta.terms.basicterms.VariableTerm#getFiniteQuantificationTerm <em>Finite Quantification Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Finite Quantification Term</em>'.
	 * @see asmeta.terms.basicterms.VariableTerm#getFiniteQuantificationTerm()
	 * @see #getVariableTerm()
	 * @generated
	 */
	EReference getVariableTerm_FiniteQuantificationTerm();

	/**
	 * Returns the meta object for the attribute '{@link asmeta.terms.basicterms.VariableTerm#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see asmeta.terms.basicterms.VariableTerm#getName()
	 * @see #getVariableTerm()
	 * @generated
	 */
	EAttribute getVariableTerm_Name();

	/**
	 * Returns the meta object for the attribute '{@link asmeta.terms.basicterms.VariableTerm#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see asmeta.terms.basicterms.VariableTerm#getKind()
	 * @see #getVariableTerm()
	 * @generated
	 */
	EAttribute getVariableTerm_Kind();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.UndefTerm <em>Undef Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Undef Term</em>'.
	 * @see asmeta.terms.basicterms.UndefTerm
	 * @generated
	 */
	EClass getUndefTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.TupleTerm <em>Tuple Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tuple Term</em>'.
	 * @see asmeta.terms.basicterms.TupleTerm
	 * @generated
	 */
	EClass getTupleTerm();

	/**
	 * Returns the meta object for the attribute '{@link asmeta.terms.basicterms.TupleTerm#getArity <em>Arity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Arity</em>'.
	 * @see asmeta.terms.basicterms.TupleTerm#getArity()
	 * @see #getTupleTerm()
	 * @generated
	 */
	EAttribute getTupleTerm_Arity();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.terms.basicterms.TupleTerm#getTerms <em>Terms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Terms</em>'.
	 * @see asmeta.terms.basicterms.TupleTerm#getTerms()
	 * @see #getTupleTerm()
	 * @generated
	 */
	EAttribute getTupleTerm_Terms();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.SetTerm <em>Set Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Set Term</em>'.
	 * @see asmeta.terms.basicterms.SetTerm
	 * @generated
	 */
	EClass getSetTerm();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.terms.basicterms.SetTerm#getTerm <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Term</em>'.
	 * @see asmeta.terms.basicterms.SetTerm#getTerm()
	 * @see #getSetTerm()
	 * @generated
	 */
	EReference getSetTerm_Term();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.RuleAsTerm <em>Rule As Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule As Term</em>'.
	 * @see asmeta.terms.basicterms.RuleAsTerm
	 * @generated
	 */
	EClass getRuleAsTerm();

	/**
	 * Returns the meta object for the reference '{@link asmeta.terms.basicterms.RuleAsTerm#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule</em>'.
	 * @see asmeta.terms.basicterms.RuleAsTerm#getRule()
	 * @see #getRuleAsTerm()
	 * @generated
	 */
	EReference getRuleAsTerm_Rule();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.LocationTerm <em>Location Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Location Term</em>'.
	 * @see asmeta.terms.basicterms.LocationTerm
	 * @generated
	 */
	EClass getLocationTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.FunctionTerm <em>Function Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Term</em>'.
	 * @see asmeta.terms.basicterms.FunctionTerm
	 * @generated
	 */
	EClass getFunctionTerm();

	/**
	 * Returns the meta object for the containment reference '{@link asmeta.terms.basicterms.FunctionTerm#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Arguments</em>'.
	 * @see asmeta.terms.basicterms.FunctionTerm#getArguments()
	 * @see #getFunctionTerm()
	 * @generated
	 */
	EReference getFunctionTerm_Arguments();

	/**
	 * Returns the meta object for the reference '{@link asmeta.terms.basicterms.FunctionTerm#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Function</em>'.
	 * @see asmeta.terms.basicterms.FunctionTerm#getFunction()
	 * @see #getFunctionTerm()
	 * @generated
	 */
	EReference getFunctionTerm_Function();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.ExtendedTerm <em>Extended Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Extended Term</em>'.
	 * @see asmeta.terms.basicterms.ExtendedTerm
	 * @generated
	 */
	EClass getExtendedTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.DomainTerm <em>Domain Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain Term</em>'.
	 * @see asmeta.terms.basicterms.DomainTerm
	 * @generated
	 */
	EClass getDomainTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.ConstantTerm <em>Constant Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constant Term</em>'.
	 * @see asmeta.terms.basicterms.ConstantTerm
	 * @generated
	 */
	EClass getConstantTerm();

	/**
	 * Returns the meta object for the attribute '{@link asmeta.terms.basicterms.ConstantTerm#getSymbol <em>Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Symbol</em>'.
	 * @see asmeta.terms.basicterms.ConstantTerm#getSymbol()
	 * @see #getConstantTerm()
	 * @generated
	 */
	EAttribute getConstantTerm_Symbol();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.CollectionTerm <em>Collection Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Collection Term</em>'.
	 * @see asmeta.terms.basicterms.CollectionTerm
	 * @generated
	 */
	EClass getCollectionTerm();

	/**
	 * Returns the meta object for the attribute '{@link asmeta.terms.basicterms.CollectionTerm#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see asmeta.terms.basicterms.CollectionTerm#getSize()
	 * @see #getCollectionTerm()
	 * @generated
	 */
	EAttribute getCollectionTerm_Size();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.BooleanTerm <em>Boolean Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Term</em>'.
	 * @see asmeta.terms.basicterms.BooleanTerm
	 * @generated
	 */
	EClass getBooleanTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.BasicTerm <em>Basic Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic Term</em>'.
	 * @see asmeta.terms.basicterms.BasicTerm
	 * @generated
	 */
	EClass getBasicTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.basicterms.Term <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Term</em>'.
	 * @see asmeta.terms.basicterms.Term
	 * @generated
	 */
	EClass getTerm();

	/**
	 * Returns the meta object for the reference '{@link asmeta.terms.basicterms.Term#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Domain</em>'.
	 * @see asmeta.terms.basicterms.Term#getDomain()
	 * @see #getTerm()
	 * @generated
	 */
	EReference getTerm_Domain();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.terms.basicterms.Term#getTermAsRule <em>Term As Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Term As Rule</em>'.
	 * @see asmeta.terms.basicterms.Term#getTermAsRule()
	 * @see #getTerm()
	 * @generated
	 */
	EReference getTerm_TermAsRule();

	/**
	 * Returns the meta object for enum '{@link asmeta.terms.basicterms.VariableKind <em>Variable Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Variable Kind</em>'.
	 * @see asmeta.terms.basicterms.VariableKind
	 * @generated
	 */
	EEnum getVariableKind();

	/**
	 * Returns the meta object for data type '{@link asmeta.terms.basicterms.Term <em>Term DT</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Term DT</em>'.
	 * @see asmeta.terms.basicterms.Term
	 * @model instanceClass="asmeta.terms.basicterms.Term"
	 * @generated
	 */
	EDataType getTermDT();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BasictermsFactory getBasictermsFactory();

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
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.VariableTermImpl <em>Variable Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.VariableTermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getVariableTerm()
		 * @generated
		 */
		EClass VARIABLE_TERM = eINSTANCE.getVariableTerm();

		/**
		 * The meta object literal for the '<em><b>Finite Quantification Term</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_TERM__FINITE_QUANTIFICATION_TERM = eINSTANCE.getVariableTerm_FiniteQuantificationTerm();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE_TERM__NAME = eINSTANCE.getVariableTerm_Name();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE_TERM__KIND = eINSTANCE.getVariableTerm_Kind();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.UndefTermImpl <em>Undef Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.UndefTermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getUndefTerm()
		 * @generated
		 */
		EClass UNDEF_TERM = eINSTANCE.getUndefTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.TupleTermImpl <em>Tuple Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.TupleTermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getTupleTerm()
		 * @generated
		 */
		EClass TUPLE_TERM = eINSTANCE.getTupleTerm();

		/**
		 * The meta object literal for the '<em><b>Arity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TUPLE_TERM__ARITY = eINSTANCE.getTupleTerm_Arity();

		/**
		 * The meta object literal for the '<em><b>Terms</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TUPLE_TERM__TERMS = eINSTANCE.getTupleTerm_Terms();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.SetTermImpl <em>Set Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.SetTermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getSetTerm()
		 * @generated
		 */
		EClass SET_TERM = eINSTANCE.getSetTerm();

		/**
		 * The meta object literal for the '<em><b>Term</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SET_TERM__TERM = eINSTANCE.getSetTerm_Term();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.RuleAsTermImpl <em>Rule As Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.RuleAsTermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getRuleAsTerm()
		 * @generated
		 */
		EClass RULE_AS_TERM = eINSTANCE.getRuleAsTerm();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_AS_TERM__RULE = eINSTANCE.getRuleAsTerm_Rule();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.LocationTermImpl <em>Location Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.LocationTermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getLocationTerm()
		 * @generated
		 */
		EClass LOCATION_TERM = eINSTANCE.getLocationTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.FunctionTermImpl <em>Function Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.FunctionTermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getFunctionTerm()
		 * @generated
		 */
		EClass FUNCTION_TERM = eINSTANCE.getFunctionTerm();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_TERM__ARGUMENTS = eINSTANCE.getFunctionTerm_Arguments();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_TERM__FUNCTION = eINSTANCE.getFunctionTerm_Function();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.ExtendedTermImpl <em>Extended Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.ExtendedTermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getExtendedTerm()
		 * @generated
		 */
		EClass EXTENDED_TERM = eINSTANCE.getExtendedTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.DomainTermImpl <em>Domain Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.DomainTermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getDomainTerm()
		 * @generated
		 */
		EClass DOMAIN_TERM = eINSTANCE.getDomainTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.ConstantTermImpl <em>Constant Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.ConstantTermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getConstantTerm()
		 * @generated
		 */
		EClass CONSTANT_TERM = eINSTANCE.getConstantTerm();

		/**
		 * The meta object literal for the '<em><b>Symbol</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTANT_TERM__SYMBOL = eINSTANCE.getConstantTerm_Symbol();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.CollectionTermImpl <em>Collection Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.CollectionTermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getCollectionTerm()
		 * @generated
		 */
		EClass COLLECTION_TERM = eINSTANCE.getCollectionTerm();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLLECTION_TERM__SIZE = eINSTANCE.getCollectionTerm_Size();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.BooleanTermImpl <em>Boolean Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.BooleanTermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getBooleanTerm()
		 * @generated
		 */
		EClass BOOLEAN_TERM = eINSTANCE.getBooleanTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.BasicTermImpl <em>Basic Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.BasicTermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getBasicTerm()
		 * @generated
		 */
		EClass BASIC_TERM = eINSTANCE.getBasicTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.impl.TermImpl <em>Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.impl.TermImpl
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getTerm()
		 * @generated
		 */
		EClass TERM = eINSTANCE.getTerm();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERM__DOMAIN = eINSTANCE.getTerm_Domain();

		/**
		 * The meta object literal for the '<em><b>Term As Rule</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERM__TERM_AS_RULE = eINSTANCE.getTerm_TermAsRule();

		/**
		 * The meta object literal for the '{@link asmeta.terms.basicterms.VariableKind <em>Variable Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.VariableKind
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getVariableKind()
		 * @generated
		 */
		EEnum VARIABLE_KIND = eINSTANCE.getVariableKind();

		/**
		 * The meta object literal for the '<em>Term DT</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.basicterms.Term
		 * @see asmeta.terms.basicterms.impl.BasictermsPackageImpl#getTermDT()
		 * @generated
		 */
		EDataType TERM_DT = eINSTANCE.getTermDT();

	}

} //BasictermsPackage

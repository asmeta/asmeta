/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import asmeta.terms.basicterms.BasictermsPackage;

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
 * @see asmeta.terms.furtherterms.FurthertermsFactory
 * @model kind="package"
 * @generated
 */
public interface FurthertermsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "furtherterms";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.asmeta/asmm#FurtherTerms";

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
	FurthertermsPackage eINSTANCE = asmeta.terms.furtherterms.impl.FurthertermsPackageImpl.init();

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.IntegerTermImpl <em>Integer Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.IntegerTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getIntegerTerm()
	 * @generated
	 */
	int INTEGER_TERM = 0;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TERM__DOMAIN = BasictermsPackage.CONSTANT_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TERM__TERM_AS_RULE = BasictermsPackage.CONSTANT_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TERM__SYMBOL = BasictermsPackage.CONSTANT_TERM__SYMBOL;

	/**
	 * The number of structural features of the '<em>Integer Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TERM_FEATURE_COUNT = BasictermsPackage.CONSTANT_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.NaturalTermImpl <em>Natural Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.NaturalTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getNaturalTerm()
	 * @generated
	 */
	int NATURAL_TERM = 1;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATURAL_TERM__DOMAIN = BasictermsPackage.CONSTANT_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATURAL_TERM__TERM_AS_RULE = BasictermsPackage.CONSTANT_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATURAL_TERM__SYMBOL = BasictermsPackage.CONSTANT_TERM__SYMBOL;

	/**
	 * The number of structural features of the '<em>Natural Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATURAL_TERM_FEATURE_COUNT = BasictermsPackage.CONSTANT_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.VariableBindingTermImpl <em>Variable Binding Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.VariableBindingTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getVariableBindingTerm()
	 * @generated
	 */
	int VARIABLE_BINDING_TERM = 2;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_BINDING_TERM__DOMAIN = BasictermsPackage.EXTENDED_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_BINDING_TERM__TERM_AS_RULE = BasictermsPackage.EXTENDED_TERM__TERM_AS_RULE;

	/**
	 * The number of structural features of the '<em>Variable Binding Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_BINDING_TERM_FEATURE_COUNT = BasictermsPackage.EXTENDED_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.StringTermImpl <em>String Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.StringTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getStringTerm()
	 * @generated
	 */
	int STRING_TERM = 3;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TERM__DOMAIN = BasictermsPackage.CONSTANT_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TERM__TERM_AS_RULE = BasictermsPackage.CONSTANT_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TERM__SYMBOL = BasictermsPackage.CONSTANT_TERM__SYMBOL;

	/**
	 * The number of structural features of the '<em>String Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TERM_FEATURE_COUNT = BasictermsPackage.CONSTANT_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.ComprehensionTermImpl <em>Comprehension Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.ComprehensionTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getComprehensionTerm()
	 * @generated
	 */
	int COMPREHENSION_TERM = 17;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPREHENSION_TERM__DOMAIN = VARIABLE_BINDING_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPREHENSION_TERM__TERM_AS_RULE = VARIABLE_BINDING_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPREHENSION_TERM__VARIABLE = VARIABLE_BINDING_TERM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPREHENSION_TERM__GUARD = VARIABLE_BINDING_TERM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPREHENSION_TERM__TERM = VARIABLE_BINDING_TERM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPREHENSION_TERM__RANGES = VARIABLE_BINDING_TERM_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Comprehension Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPREHENSION_TERM_FEATURE_COUNT = VARIABLE_BINDING_TERM_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.SetCtImpl <em>Set Ct</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.SetCtImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getSetCt()
	 * @generated
	 */
	int SET_CT = 4;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_CT__DOMAIN = COMPREHENSION_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_CT__TERM_AS_RULE = COMPREHENSION_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_CT__VARIABLE = COMPREHENSION_TERM__VARIABLE;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_CT__GUARD = COMPREHENSION_TERM__GUARD;

	/**
	 * The feature id for the '<em><b>Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_CT__TERM = COMPREHENSION_TERM__TERM;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_CT__RANGES = COMPREHENSION_TERM__RANGES;

	/**
	 * The number of structural features of the '<em>Set Ct</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_CT_FEATURE_COUNT = COMPREHENSION_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.SequenceTermImpl <em>Sequence Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.SequenceTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getSequenceTerm()
	 * @generated
	 */
	int SEQUENCE_TERM = 5;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_TERM__DOMAIN = BasictermsPackage.COLLECTION_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_TERM__TERM_AS_RULE = BasictermsPackage.COLLECTION_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_TERM__SIZE = BasictermsPackage.COLLECTION_TERM__SIZE;

	/**
	 * The feature id for the '<em><b>Terms</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_TERM__TERMS = BasictermsPackage.COLLECTION_TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sequence Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_TERM_FEATURE_COUNT = BasictermsPackage.COLLECTION_TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.SequenceCtImpl <em>Sequence Ct</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.SequenceCtImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getSequenceCt()
	 * @generated
	 */
	int SEQUENCE_CT = 6;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_CT__DOMAIN = COMPREHENSION_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_CT__TERM_AS_RULE = COMPREHENSION_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_CT__VARIABLE = COMPREHENSION_TERM__VARIABLE;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_CT__GUARD = COMPREHENSION_TERM__GUARD;

	/**
	 * The feature id for the '<em><b>Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_CT__TERM = COMPREHENSION_TERM__TERM;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_CT__RANGES = COMPREHENSION_TERM__RANGES;

	/**
	 * The number of structural features of the '<em>Sequence Ct</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_CT_FEATURE_COUNT = COMPREHENSION_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.RealTermImpl <em>Real Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.RealTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getRealTerm()
	 * @generated
	 */
	int REAL_TERM = 7;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TERM__DOMAIN = BasictermsPackage.CONSTANT_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TERM__TERM_AS_RULE = BasictermsPackage.CONSTANT_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TERM__SYMBOL = BasictermsPackage.CONSTANT_TERM__SYMBOL;

	/**
	 * The number of structural features of the '<em>Real Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TERM_FEATURE_COUNT = BasictermsPackage.CONSTANT_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.MapTermImpl <em>Map Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.MapTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getMapTerm()
	 * @generated
	 */
	int MAP_TERM = 8;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TERM__DOMAIN = BasictermsPackage.COLLECTION_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TERM__TERM_AS_RULE = BasictermsPackage.COLLECTION_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TERM__SIZE = BasictermsPackage.COLLECTION_TERM__SIZE;

	/**
	 * The feature id for the '<em><b>Pair</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TERM__PAIR = BasictermsPackage.COLLECTION_TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Map Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TERM_FEATURE_COUNT = BasictermsPackage.COLLECTION_TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.MapCtImpl <em>Map Ct</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.MapCtImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getMapCt()
	 * @generated
	 */
	int MAP_CT = 9;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_CT__DOMAIN = COMPREHENSION_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_CT__TERM_AS_RULE = COMPREHENSION_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_CT__VARIABLE = COMPREHENSION_TERM__VARIABLE;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_CT__GUARD = COMPREHENSION_TERM__GUARD;

	/**
	 * The feature id for the '<em><b>Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_CT__TERM = COMPREHENSION_TERM__TERM;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_CT__RANGES = COMPREHENSION_TERM__RANGES;

	/**
	 * The number of structural features of the '<em>Map Ct</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_CT_FEATURE_COUNT = COMPREHENSION_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.LetTermImpl <em>Let Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.LetTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getLetTerm()
	 * @generated
	 */
	int LET_TERM = 10;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LET_TERM__DOMAIN = VARIABLE_BINDING_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LET_TERM__TERM_AS_RULE = VARIABLE_BINDING_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LET_TERM__VARIABLE = VARIABLE_BINDING_TERM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Assignment Term</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LET_TERM__ASSIGNMENT_TERM = VARIABLE_BINDING_TERM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Body</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LET_TERM__BODY = VARIABLE_BINDING_TERM_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Let Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LET_TERM_FEATURE_COUNT = VARIABLE_BINDING_TERM_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.FiniteQuantificationTermImpl <em>Finite Quantification Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.FiniteQuantificationTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getFiniteQuantificationTerm()
	 * @generated
	 */
	int FINITE_QUANTIFICATION_TERM = 12;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINITE_QUANTIFICATION_TERM__DOMAIN = VARIABLE_BINDING_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINITE_QUANTIFICATION_TERM__TERM_AS_RULE = VARIABLE_BINDING_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINITE_QUANTIFICATION_TERM__VARIABLE = VARIABLE_BINDING_TERM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINITE_QUANTIFICATION_TERM__GUARD = VARIABLE_BINDING_TERM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINITE_QUANTIFICATION_TERM__RANGES = VARIABLE_BINDING_TERM_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Finite Quantification Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINITE_QUANTIFICATION_TERM_FEATURE_COUNT = VARIABLE_BINDING_TERM_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.ForallTermImpl <em>Forall Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.ForallTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getForallTerm()
	 * @generated
	 */
	int FORALL_TERM = 11;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_TERM__DOMAIN = FINITE_QUANTIFICATION_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_TERM__TERM_AS_RULE = FINITE_QUANTIFICATION_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_TERM__VARIABLE = FINITE_QUANTIFICATION_TERM__VARIABLE;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_TERM__GUARD = FINITE_QUANTIFICATION_TERM__GUARD;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_TERM__RANGES = FINITE_QUANTIFICATION_TERM__RANGES;

	/**
	 * The number of structural features of the '<em>Forall Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_TERM_FEATURE_COUNT = FINITE_QUANTIFICATION_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.ExistUniqueTermImpl <em>Exist Unique Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.ExistUniqueTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getExistUniqueTerm()
	 * @generated
	 */
	int EXIST_UNIQUE_TERM = 13;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXIST_UNIQUE_TERM__DOMAIN = FINITE_QUANTIFICATION_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXIST_UNIQUE_TERM__TERM_AS_RULE = FINITE_QUANTIFICATION_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXIST_UNIQUE_TERM__VARIABLE = FINITE_QUANTIFICATION_TERM__VARIABLE;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXIST_UNIQUE_TERM__GUARD = FINITE_QUANTIFICATION_TERM__GUARD;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXIST_UNIQUE_TERM__RANGES = FINITE_QUANTIFICATION_TERM__RANGES;

	/**
	 * The number of structural features of the '<em>Exist Unique Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXIST_UNIQUE_TERM_FEATURE_COUNT = FINITE_QUANTIFICATION_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.ExistTermImpl <em>Exist Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.ExistTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getExistTerm()
	 * @generated
	 */
	int EXIST_TERM = 14;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXIST_TERM__DOMAIN = FINITE_QUANTIFICATION_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXIST_TERM__TERM_AS_RULE = FINITE_QUANTIFICATION_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXIST_TERM__VARIABLE = FINITE_QUANTIFICATION_TERM__VARIABLE;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXIST_TERM__GUARD = FINITE_QUANTIFICATION_TERM__GUARD;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXIST_TERM__RANGES = FINITE_QUANTIFICATION_TERM__RANGES;

	/**
	 * The number of structural features of the '<em>Exist Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXIST_TERM_FEATURE_COUNT = FINITE_QUANTIFICATION_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.EnumTermImpl <em>Enum Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.EnumTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getEnumTerm()
	 * @generated
	 */
	int ENUM_TERM = 15;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_TERM__DOMAIN = BasictermsPackage.CONSTANT_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_TERM__TERM_AS_RULE = BasictermsPackage.CONSTANT_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_TERM__SYMBOL = BasictermsPackage.CONSTANT_TERM__SYMBOL;

	/**
	 * The number of structural features of the '<em>Enum Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_TERM_FEATURE_COUNT = BasictermsPackage.CONSTANT_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.ConditionalTermImpl <em>Conditional Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.ConditionalTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getConditionalTerm()
	 * @generated
	 */
	int CONDITIONAL_TERM = 16;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_TERM__DOMAIN = BasictermsPackage.EXTENDED_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_TERM__TERM_AS_RULE = BasictermsPackage.EXTENDED_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Else Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_TERM__ELSE_TERM = BasictermsPackage.EXTENDED_TERM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_TERM__GUARD = BasictermsPackage.EXTENDED_TERM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Then Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_TERM__THEN_TERM = BasictermsPackage.EXTENDED_TERM_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Conditional Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_TERM_FEATURE_COUNT = BasictermsPackage.EXTENDED_TERM_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.ComplexTermImpl <em>Complex Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.ComplexTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getComplexTerm()
	 * @generated
	 */
	int COMPLEX_TERM = 18;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_TERM__DOMAIN = BasictermsPackage.CONSTANT_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_TERM__TERM_AS_RULE = BasictermsPackage.CONSTANT_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_TERM__SYMBOL = BasictermsPackage.CONSTANT_TERM__SYMBOL;

	/**
	 * The number of structural features of the '<em>Complex Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_TERM_FEATURE_COUNT = BasictermsPackage.CONSTANT_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.CharTermImpl <em>Char Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.CharTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getCharTerm()
	 * @generated
	 */
	int CHAR_TERM = 19;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_TERM__DOMAIN = BasictermsPackage.CONSTANT_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_TERM__TERM_AS_RULE = BasictermsPackage.CONSTANT_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_TERM__SYMBOL = BasictermsPackage.CONSTANT_TERM__SYMBOL;

	/**
	 * The number of structural features of the '<em>Char Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_TERM_FEATURE_COUNT = BasictermsPackage.CONSTANT_TERM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.CaseTermImpl <em>Case Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.CaseTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getCaseTerm()
	 * @generated
	 */
	int CASE_TERM = 20;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_TERM__DOMAIN = BasictermsPackage.EXTENDED_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_TERM__TERM_AS_RULE = BasictermsPackage.EXTENDED_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Comparing Term</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_TERM__COMPARING_TERM = BasictermsPackage.EXTENDED_TERM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Compared Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_TERM__COMPARED_TERM = BasictermsPackage.EXTENDED_TERM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Otherwise Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_TERM__OTHERWISE_TERM = BasictermsPackage.EXTENDED_TERM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Result Terms</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_TERM__RESULT_TERMS = BasictermsPackage.EXTENDED_TERM_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Case Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_TERM_FEATURE_COUNT = BasictermsPackage.EXTENDED_TERM_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.BagTermImpl <em>Bag Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.BagTermImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getBagTerm()
	 * @generated
	 */
	int BAG_TERM = 21;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_TERM__DOMAIN = BasictermsPackage.COLLECTION_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_TERM__TERM_AS_RULE = BasictermsPackage.COLLECTION_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_TERM__SIZE = BasictermsPackage.COLLECTION_TERM__SIZE;

	/**
	 * The feature id for the '<em><b>Term</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_TERM__TERM = BasictermsPackage.COLLECTION_TERM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bag Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_TERM_FEATURE_COUNT = BasictermsPackage.COLLECTION_TERM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.terms.furtherterms.impl.BagCtImpl <em>Bag Ct</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.terms.furtherterms.impl.BagCtImpl
	 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getBagCt()
	 * @generated
	 */
	int BAG_CT = 22;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_CT__DOMAIN = COMPREHENSION_TERM__DOMAIN;

	/**
	 * The feature id for the '<em><b>Term As Rule</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_CT__TERM_AS_RULE = COMPREHENSION_TERM__TERM_AS_RULE;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_CT__VARIABLE = COMPREHENSION_TERM__VARIABLE;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_CT__GUARD = COMPREHENSION_TERM__GUARD;

	/**
	 * The feature id for the '<em><b>Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_CT__TERM = COMPREHENSION_TERM__TERM;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_CT__RANGES = COMPREHENSION_TERM__RANGES;

	/**
	 * The number of structural features of the '<em>Bag Ct</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_CT_FEATURE_COUNT = COMPREHENSION_TERM_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.IntegerTerm <em>Integer Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Term</em>'.
	 * @see asmeta.terms.furtherterms.IntegerTerm
	 * @generated
	 */
	EClass getIntegerTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.NaturalTerm <em>Natural Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Natural Term</em>'.
	 * @see asmeta.terms.furtherterms.NaturalTerm
	 * @generated
	 */
	EClass getNaturalTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.VariableBindingTerm <em>Variable Binding Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Binding Term</em>'.
	 * @see asmeta.terms.furtherterms.VariableBindingTerm
	 * @generated
	 */
	EClass getVariableBindingTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.StringTerm <em>String Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Term</em>'.
	 * @see asmeta.terms.furtherterms.StringTerm
	 * @generated
	 */
	EClass getStringTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.SetCt <em>Set Ct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Set Ct</em>'.
	 * @see asmeta.terms.furtherterms.SetCt
	 * @generated
	 */
	EClass getSetCt();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.SequenceTerm <em>Sequence Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sequence Term</em>'.
	 * @see asmeta.terms.furtherterms.SequenceTerm
	 * @generated
	 */
	EClass getSequenceTerm();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.terms.furtherterms.SequenceTerm#getTerms <em>Terms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Terms</em>'.
	 * @see asmeta.terms.furtherterms.SequenceTerm#getTerms()
	 * @see #getSequenceTerm()
	 * @generated
	 */
	EAttribute getSequenceTerm_Terms();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.SequenceCt <em>Sequence Ct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sequence Ct</em>'.
	 * @see asmeta.terms.furtherterms.SequenceCt
	 * @generated
	 */
	EClass getSequenceCt();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.RealTerm <em>Real Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Real Term</em>'.
	 * @see asmeta.terms.furtherterms.RealTerm
	 * @generated
	 */
	EClass getRealTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.MapTerm <em>Map Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Term</em>'.
	 * @see asmeta.terms.furtherterms.MapTerm
	 * @generated
	 */
	EClass getMapTerm();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.terms.furtherterms.MapTerm#getPair <em>Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Pair</em>'.
	 * @see asmeta.terms.furtherterms.MapTerm#getPair()
	 * @see #getMapTerm()
	 * @generated
	 */
	EReference getMapTerm_Pair();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.MapCt <em>Map Ct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Ct</em>'.
	 * @see asmeta.terms.furtherterms.MapCt
	 * @generated
	 */
	EClass getMapCt();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.LetTerm <em>Let Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Let Term</em>'.
	 * @see asmeta.terms.furtherterms.LetTerm
	 * @generated
	 */
	EClass getLetTerm();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.terms.furtherterms.LetTerm#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variable</em>'.
	 * @see asmeta.terms.furtherterms.LetTerm#getVariable()
	 * @see #getLetTerm()
	 * @generated
	 */
	EReference getLetTerm_Variable();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.terms.furtherterms.LetTerm#getAssignmentTerm <em>Assignment Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Assignment Term</em>'.
	 * @see asmeta.terms.furtherterms.LetTerm#getAssignmentTerm()
	 * @see #getLetTerm()
	 * @generated
	 */
	EReference getLetTerm_AssignmentTerm();

	/**
	 * Returns the meta object for the reference '{@link asmeta.terms.furtherterms.LetTerm#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Body</em>'.
	 * @see asmeta.terms.furtherterms.LetTerm#getBody()
	 * @see #getLetTerm()
	 * @generated
	 */
	EReference getLetTerm_Body();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.ForallTerm <em>Forall Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Forall Term</em>'.
	 * @see asmeta.terms.furtherterms.ForallTerm
	 * @generated
	 */
	EClass getForallTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.FiniteQuantificationTerm <em>Finite Quantification Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Finite Quantification Term</em>'.
	 * @see asmeta.terms.furtherterms.FiniteQuantificationTerm
	 * @generated
	 */
	EClass getFiniteQuantificationTerm();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.terms.furtherterms.FiniteQuantificationTerm#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variable</em>'.
	 * @see asmeta.terms.furtherterms.FiniteQuantificationTerm#getVariable()
	 * @see #getFiniteQuantificationTerm()
	 * @generated
	 */
	EReference getFiniteQuantificationTerm_Variable();

	/**
	 * Returns the meta object for the reference '{@link asmeta.terms.furtherterms.FiniteQuantificationTerm#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Guard</em>'.
	 * @see asmeta.terms.furtherterms.FiniteQuantificationTerm#getGuard()
	 * @see #getFiniteQuantificationTerm()
	 * @generated
	 */
	EReference getFiniteQuantificationTerm_Guard();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.terms.furtherterms.FiniteQuantificationTerm#getRanges <em>Ranges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Ranges</em>'.
	 * @see asmeta.terms.furtherterms.FiniteQuantificationTerm#getRanges()
	 * @see #getFiniteQuantificationTerm()
	 * @generated
	 */
	EAttribute getFiniteQuantificationTerm_Ranges();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.ExistUniqueTerm <em>Exist Unique Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exist Unique Term</em>'.
	 * @see asmeta.terms.furtherterms.ExistUniqueTerm
	 * @generated
	 */
	EClass getExistUniqueTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.ExistTerm <em>Exist Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exist Term</em>'.
	 * @see asmeta.terms.furtherterms.ExistTerm
	 * @generated
	 */
	EClass getExistTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.EnumTerm <em>Enum Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Term</em>'.
	 * @see asmeta.terms.furtherterms.EnumTerm
	 * @generated
	 */
	EClass getEnumTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.ConditionalTerm <em>Conditional Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conditional Term</em>'.
	 * @see asmeta.terms.furtherterms.ConditionalTerm
	 * @generated
	 */
	EClass getConditionalTerm();

	/**
	 * Returns the meta object for the reference '{@link asmeta.terms.furtherterms.ConditionalTerm#getElseTerm <em>Else Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Else Term</em>'.
	 * @see asmeta.terms.furtherterms.ConditionalTerm#getElseTerm()
	 * @see #getConditionalTerm()
	 * @generated
	 */
	EReference getConditionalTerm_ElseTerm();

	/**
	 * Returns the meta object for the reference '{@link asmeta.terms.furtherterms.ConditionalTerm#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Guard</em>'.
	 * @see asmeta.terms.furtherterms.ConditionalTerm#getGuard()
	 * @see #getConditionalTerm()
	 * @generated
	 */
	EReference getConditionalTerm_Guard();

	/**
	 * Returns the meta object for the reference '{@link asmeta.terms.furtherterms.ConditionalTerm#getThenTerm <em>Then Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Then Term</em>'.
	 * @see asmeta.terms.furtherterms.ConditionalTerm#getThenTerm()
	 * @see #getConditionalTerm()
	 * @generated
	 */
	EReference getConditionalTerm_ThenTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.ComprehensionTerm <em>Comprehension Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comprehension Term</em>'.
	 * @see asmeta.terms.furtherterms.ComprehensionTerm
	 * @generated
	 */
	EClass getComprehensionTerm();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.terms.furtherterms.ComprehensionTerm#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variable</em>'.
	 * @see asmeta.terms.furtherterms.ComprehensionTerm#getVariable()
	 * @see #getComprehensionTerm()
	 * @generated
	 */
	EReference getComprehensionTerm_Variable();

	/**
	 * Returns the meta object for the reference '{@link asmeta.terms.furtherterms.ComprehensionTerm#getGuard <em>Guard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Guard</em>'.
	 * @see asmeta.terms.furtherterms.ComprehensionTerm#getGuard()
	 * @see #getComprehensionTerm()
	 * @generated
	 */
	EReference getComprehensionTerm_Guard();

	/**
	 * Returns the meta object for the reference '{@link asmeta.terms.furtherterms.ComprehensionTerm#getTerm <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Term</em>'.
	 * @see asmeta.terms.furtherterms.ComprehensionTerm#getTerm()
	 * @see #getComprehensionTerm()
	 * @generated
	 */
	EReference getComprehensionTerm_Term();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.terms.furtherterms.ComprehensionTerm#getRanges <em>Ranges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Ranges</em>'.
	 * @see asmeta.terms.furtherterms.ComprehensionTerm#getRanges()
	 * @see #getComprehensionTerm()
	 * @generated
	 */
	EAttribute getComprehensionTerm_Ranges();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.ComplexTerm <em>Complex Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Complex Term</em>'.
	 * @see asmeta.terms.furtherterms.ComplexTerm
	 * @generated
	 */
	EClass getComplexTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.CharTerm <em>Char Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Char Term</em>'.
	 * @see asmeta.terms.furtherterms.CharTerm
	 * @generated
	 */
	EClass getCharTerm();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.CaseTerm <em>Case Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Case Term</em>'.
	 * @see asmeta.terms.furtherterms.CaseTerm
	 * @generated
	 */
	EClass getCaseTerm();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.terms.furtherterms.CaseTerm#getComparingTerm <em>Comparing Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Comparing Term</em>'.
	 * @see asmeta.terms.furtherterms.CaseTerm#getComparingTerm()
	 * @see #getCaseTerm()
	 * @generated
	 */
	EReference getCaseTerm_ComparingTerm();

	/**
	 * Returns the meta object for the reference '{@link asmeta.terms.furtherterms.CaseTerm#getComparedTerm <em>Compared Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Compared Term</em>'.
	 * @see asmeta.terms.furtherterms.CaseTerm#getComparedTerm()
	 * @see #getCaseTerm()
	 * @generated
	 */
	EReference getCaseTerm_ComparedTerm();

	/**
	 * Returns the meta object for the reference '{@link asmeta.terms.furtherterms.CaseTerm#getOtherwiseTerm <em>Otherwise Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Otherwise Term</em>'.
	 * @see asmeta.terms.furtherterms.CaseTerm#getOtherwiseTerm()
	 * @see #getCaseTerm()
	 * @generated
	 */
	EReference getCaseTerm_OtherwiseTerm();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.terms.furtherterms.CaseTerm#getResultTerms <em>Result Terms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Result Terms</em>'.
	 * @see asmeta.terms.furtherterms.CaseTerm#getResultTerms()
	 * @see #getCaseTerm()
	 * @generated
	 */
	EAttribute getCaseTerm_ResultTerms();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.BagTerm <em>Bag Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bag Term</em>'.
	 * @see asmeta.terms.furtherterms.BagTerm
	 * @generated
	 */
	EClass getBagTerm();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.terms.furtherterms.BagTerm#getTerm <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Term</em>'.
	 * @see asmeta.terms.furtherterms.BagTerm#getTerm()
	 * @see #getBagTerm()
	 * @generated
	 */
	EReference getBagTerm_Term();

	/**
	 * Returns the meta object for class '{@link asmeta.terms.furtherterms.BagCt <em>Bag Ct</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bag Ct</em>'.
	 * @see asmeta.terms.furtherterms.BagCt
	 * @generated
	 */
	EClass getBagCt();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FurthertermsFactory getFurthertermsFactory();

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
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.IntegerTermImpl <em>Integer Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.IntegerTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getIntegerTerm()
		 * @generated
		 */
		EClass INTEGER_TERM = eINSTANCE.getIntegerTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.NaturalTermImpl <em>Natural Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.NaturalTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getNaturalTerm()
		 * @generated
		 */
		EClass NATURAL_TERM = eINSTANCE.getNaturalTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.VariableBindingTermImpl <em>Variable Binding Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.VariableBindingTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getVariableBindingTerm()
		 * @generated
		 */
		EClass VARIABLE_BINDING_TERM = eINSTANCE.getVariableBindingTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.StringTermImpl <em>String Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.StringTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getStringTerm()
		 * @generated
		 */
		EClass STRING_TERM = eINSTANCE.getStringTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.SetCtImpl <em>Set Ct</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.SetCtImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getSetCt()
		 * @generated
		 */
		EClass SET_CT = eINSTANCE.getSetCt();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.SequenceTermImpl <em>Sequence Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.SequenceTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getSequenceTerm()
		 * @generated
		 */
		EClass SEQUENCE_TERM = eINSTANCE.getSequenceTerm();

		/**
		 * The meta object literal for the '<em><b>Terms</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEQUENCE_TERM__TERMS = eINSTANCE.getSequenceTerm_Terms();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.SequenceCtImpl <em>Sequence Ct</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.SequenceCtImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getSequenceCt()
		 * @generated
		 */
		EClass SEQUENCE_CT = eINSTANCE.getSequenceCt();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.RealTermImpl <em>Real Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.RealTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getRealTerm()
		 * @generated
		 */
		EClass REAL_TERM = eINSTANCE.getRealTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.MapTermImpl <em>Map Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.MapTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getMapTerm()
		 * @generated
		 */
		EClass MAP_TERM = eINSTANCE.getMapTerm();

		/**
		 * The meta object literal for the '<em><b>Pair</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAP_TERM__PAIR = eINSTANCE.getMapTerm_Pair();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.MapCtImpl <em>Map Ct</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.MapCtImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getMapCt()
		 * @generated
		 */
		EClass MAP_CT = eINSTANCE.getMapCt();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.LetTermImpl <em>Let Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.LetTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getLetTerm()
		 * @generated
		 */
		EClass LET_TERM = eINSTANCE.getLetTerm();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LET_TERM__VARIABLE = eINSTANCE.getLetTerm_Variable();

		/**
		 * The meta object literal for the '<em><b>Assignment Term</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LET_TERM__ASSIGNMENT_TERM = eINSTANCE.getLetTerm_AssignmentTerm();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LET_TERM__BODY = eINSTANCE.getLetTerm_Body();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.ForallTermImpl <em>Forall Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.ForallTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getForallTerm()
		 * @generated
		 */
		EClass FORALL_TERM = eINSTANCE.getForallTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.FiniteQuantificationTermImpl <em>Finite Quantification Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.FiniteQuantificationTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getFiniteQuantificationTerm()
		 * @generated
		 */
		EClass FINITE_QUANTIFICATION_TERM = eINSTANCE.getFiniteQuantificationTerm();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FINITE_QUANTIFICATION_TERM__VARIABLE = eINSTANCE.getFiniteQuantificationTerm_Variable();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FINITE_QUANTIFICATION_TERM__GUARD = eINSTANCE.getFiniteQuantificationTerm_Guard();

		/**
		 * The meta object literal for the '<em><b>Ranges</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FINITE_QUANTIFICATION_TERM__RANGES = eINSTANCE.getFiniteQuantificationTerm_Ranges();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.ExistUniqueTermImpl <em>Exist Unique Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.ExistUniqueTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getExistUniqueTerm()
		 * @generated
		 */
		EClass EXIST_UNIQUE_TERM = eINSTANCE.getExistUniqueTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.ExistTermImpl <em>Exist Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.ExistTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getExistTerm()
		 * @generated
		 */
		EClass EXIST_TERM = eINSTANCE.getExistTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.EnumTermImpl <em>Enum Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.EnumTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getEnumTerm()
		 * @generated
		 */
		EClass ENUM_TERM = eINSTANCE.getEnumTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.ConditionalTermImpl <em>Conditional Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.ConditionalTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getConditionalTerm()
		 * @generated
		 */
		EClass CONDITIONAL_TERM = eINSTANCE.getConditionalTerm();

		/**
		 * The meta object literal for the '<em><b>Else Term</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_TERM__ELSE_TERM = eINSTANCE.getConditionalTerm_ElseTerm();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_TERM__GUARD = eINSTANCE.getConditionalTerm_Guard();

		/**
		 * The meta object literal for the '<em><b>Then Term</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_TERM__THEN_TERM = eINSTANCE.getConditionalTerm_ThenTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.ComprehensionTermImpl <em>Comprehension Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.ComprehensionTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getComprehensionTerm()
		 * @generated
		 */
		EClass COMPREHENSION_TERM = eINSTANCE.getComprehensionTerm();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPREHENSION_TERM__VARIABLE = eINSTANCE.getComprehensionTerm_Variable();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPREHENSION_TERM__GUARD = eINSTANCE.getComprehensionTerm_Guard();

		/**
		 * The meta object literal for the '<em><b>Term</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPREHENSION_TERM__TERM = eINSTANCE.getComprehensionTerm_Term();

		/**
		 * The meta object literal for the '<em><b>Ranges</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPREHENSION_TERM__RANGES = eINSTANCE.getComprehensionTerm_Ranges();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.ComplexTermImpl <em>Complex Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.ComplexTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getComplexTerm()
		 * @generated
		 */
		EClass COMPLEX_TERM = eINSTANCE.getComplexTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.CharTermImpl <em>Char Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.CharTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getCharTerm()
		 * @generated
		 */
		EClass CHAR_TERM = eINSTANCE.getCharTerm();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.CaseTermImpl <em>Case Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.CaseTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getCaseTerm()
		 * @generated
		 */
		EClass CASE_TERM = eINSTANCE.getCaseTerm();

		/**
		 * The meta object literal for the '<em><b>Comparing Term</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASE_TERM__COMPARING_TERM = eINSTANCE.getCaseTerm_ComparingTerm();

		/**
		 * The meta object literal for the '<em><b>Compared Term</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASE_TERM__COMPARED_TERM = eINSTANCE.getCaseTerm_ComparedTerm();

		/**
		 * The meta object literal for the '<em><b>Otherwise Term</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASE_TERM__OTHERWISE_TERM = eINSTANCE.getCaseTerm_OtherwiseTerm();

		/**
		 * The meta object literal for the '<em><b>Result Terms</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CASE_TERM__RESULT_TERMS = eINSTANCE.getCaseTerm_ResultTerms();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.BagTermImpl <em>Bag Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.BagTermImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getBagTerm()
		 * @generated
		 */
		EClass BAG_TERM = eINSTANCE.getBagTerm();

		/**
		 * The meta object literal for the '<em><b>Term</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BAG_TERM__TERM = eINSTANCE.getBagTerm_Term();

		/**
		 * The meta object literal for the '{@link asmeta.terms.furtherterms.impl.BagCtImpl <em>Bag Ct</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.terms.furtherterms.impl.BagCtImpl
		 * @see asmeta.terms.furtherterms.impl.FurthertermsPackageImpl#getBagCt()
		 * @generated
		 */
		EClass BAG_CT = eINSTANCE.getBagCt();

	}

} //FurthertermsPackage

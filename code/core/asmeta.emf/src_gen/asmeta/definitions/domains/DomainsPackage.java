/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains;

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
 * @see asmeta.definitions.domains.DomainsFactory
 * @model kind="package"
 * @generated
 */
public interface DomainsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "domains";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.asmeta/asmm#Domains";

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
	DomainsPackage eINSTANCE = asmeta.definitions.domains.impl.DomainsPackageImpl.init();

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.DomainImpl <em>Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.DomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getDomain()
	 * @generated
	 */
	int DOMAIN = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__NAME = DefinitionsPackage.CLASSIFIER__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__CONSTRAINT = DefinitionsPackage.CLASSIFIER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__SIGNATURE = DefinitionsPackage.CLASSIFIER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_FEATURE_COUNT = DefinitionsPackage.CLASSIFIER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.TypeDomainImpl <em>Type Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.TypeDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getTypeDomain()
	 * @generated
	 */
	int TYPE_DOMAIN = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DOMAIN__NAME = DOMAIN__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DOMAIN__CONSTRAINT = DOMAIN__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DOMAIN__SIGNATURE = DOMAIN__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Type Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DOMAIN_FEATURE_COUNT = DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.BasicTdImpl <em>Basic Td</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.BasicTdImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getBasicTd()
	 * @generated
	 */
	int BASIC_TD = 20;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_TD__NAME = TYPE_DOMAIN__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_TD__CONSTRAINT = TYPE_DOMAIN__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_TD__SIGNATURE = TYPE_DOMAIN__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Basic Td</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_TD_FEATURE_COUNT = TYPE_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.ComplexDomainImpl <em>Complex Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.ComplexDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getComplexDomain()
	 * @generated
	 */
	int COMPLEX_DOMAIN = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_DOMAIN__NAME = BASIC_TD__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_DOMAIN__CONSTRAINT = BASIC_TD__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_DOMAIN__SIGNATURE = BASIC_TD__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Complex Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_DOMAIN_FEATURE_COUNT = BASIC_TD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.RealDomainImpl <em>Real Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.RealDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getRealDomain()
	 * @generated
	 */
	int REAL_DOMAIN = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_DOMAIN__NAME = COMPLEX_DOMAIN__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_DOMAIN__CONSTRAINT = COMPLEX_DOMAIN__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_DOMAIN__SIGNATURE = COMPLEX_DOMAIN__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Real Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_DOMAIN_FEATURE_COUNT = COMPLEX_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.IntegerDomainImpl <em>Integer Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.IntegerDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getIntegerDomain()
	 * @generated
	 */
	int INTEGER_DOMAIN = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_DOMAIN__NAME = REAL_DOMAIN__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_DOMAIN__CONSTRAINT = REAL_DOMAIN__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_DOMAIN__SIGNATURE = REAL_DOMAIN__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Integer Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_DOMAIN_FEATURE_COUNT = REAL_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.NaturalDomainImpl <em>Natural Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.NaturalDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getNaturalDomain()
	 * @generated
	 */
	int NATURAL_DOMAIN = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATURAL_DOMAIN__NAME = INTEGER_DOMAIN__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATURAL_DOMAIN__CONSTRAINT = INTEGER_DOMAIN__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATURAL_DOMAIN__SIGNATURE = INTEGER_DOMAIN__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Natural Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NATURAL_DOMAIN_FEATURE_COUNT = INTEGER_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.UndefDomainImpl <em>Undef Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.UndefDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getUndefDomain()
	 * @generated
	 */
	int UNDEF_DOMAIN = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNDEF_DOMAIN__NAME = BASIC_TD__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNDEF_DOMAIN__CONSTRAINT = BASIC_TD__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNDEF_DOMAIN__SIGNATURE = BASIC_TD__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Undef Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNDEF_DOMAIN_FEATURE_COUNT = BASIC_TD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.StructuredTdImpl <em>Structured Td</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.StructuredTdImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getStructuredTd()
	 * @generated
	 */
	int STRUCTURED_TD = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TD__NAME = TYPE_DOMAIN__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TD__CONSTRAINT = TYPE_DOMAIN__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TD__SIGNATURE = TYPE_DOMAIN__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Structured Td</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TD_FEATURE_COUNT = TYPE_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.StringDomainImpl <em>String Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.StringDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getStringDomain()
	 * @generated
	 */
	int STRING_DOMAIN = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_DOMAIN__NAME = BASIC_TD__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_DOMAIN__CONSTRAINT = BASIC_TD__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_DOMAIN__SIGNATURE = BASIC_TD__SIGNATURE;

	/**
	 * The number of structural features of the '<em>String Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_DOMAIN_FEATURE_COUNT = BASIC_TD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.SequenceDomainImpl <em>Sequence Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.SequenceDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getSequenceDomain()
	 * @generated
	 */
	int SEQUENCE_DOMAIN = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DOMAIN__NAME = STRUCTURED_TD__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DOMAIN__CONSTRAINT = STRUCTURED_TD__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DOMAIN__SIGNATURE = STRUCTURED_TD__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DOMAIN__DOMAIN = STRUCTURED_TD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sequence Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_DOMAIN_FEATURE_COUNT = STRUCTURED_TD_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.RuleDomainImpl <em>Rule Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.RuleDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getRuleDomain()
	 * @generated
	 */
	int RULE_DOMAIN = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_DOMAIN__NAME = STRUCTURED_TD__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_DOMAIN__CONSTRAINT = STRUCTURED_TD__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_DOMAIN__SIGNATURE = STRUCTURED_TD__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Domains</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_DOMAIN__DOMAINS = STRUCTURED_TD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Rule Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_DOMAIN_FEATURE_COUNT = STRUCTURED_TD_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.AbstractTdImpl <em>Abstract Td</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.AbstractTdImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getAbstractTd()
	 * @generated
	 */
	int ABSTRACT_TD = 24;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TD__NAME = TYPE_DOMAIN__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TD__CONSTRAINT = TYPE_DOMAIN__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TD__SIGNATURE = TYPE_DOMAIN__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Is Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TD__IS_DYNAMIC = TYPE_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Td</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_TD_FEATURE_COUNT = TYPE_DOMAIN_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.ReserveDomainImpl <em>Reserve Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.ReserveDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getReserveDomain()
	 * @generated
	 */
	int RESERVE_DOMAIN = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESERVE_DOMAIN__NAME = ABSTRACT_TD__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESERVE_DOMAIN__CONSTRAINT = ABSTRACT_TD__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESERVE_DOMAIN__SIGNATURE = ABSTRACT_TD__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Is Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESERVE_DOMAIN__IS_DYNAMIC = ABSTRACT_TD__IS_DYNAMIC;

	/**
	 * The number of structural features of the '<em>Reserve Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESERVE_DOMAIN_FEATURE_COUNT = ABSTRACT_TD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.ProductDomainImpl <em>Product Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.ProductDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getProductDomain()
	 * @generated
	 */
	int PRODUCT_DOMAIN = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_DOMAIN__NAME = STRUCTURED_TD__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_DOMAIN__CONSTRAINT = STRUCTURED_TD__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_DOMAIN__SIGNATURE = STRUCTURED_TD__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Domains</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_DOMAIN__DOMAINS = STRUCTURED_TD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Product Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_DOMAIN_FEATURE_COUNT = STRUCTURED_TD_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.PowersetDomainImpl <em>Powerset Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.PowersetDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getPowersetDomain()
	 * @generated
	 */
	int POWERSET_DOMAIN = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POWERSET_DOMAIN__NAME = STRUCTURED_TD__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POWERSET_DOMAIN__CONSTRAINT = STRUCTURED_TD__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POWERSET_DOMAIN__SIGNATURE = STRUCTURED_TD__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Base Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POWERSET_DOMAIN__BASE_DOMAIN = STRUCTURED_TD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Powerset Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POWERSET_DOMAIN_FEATURE_COUNT = STRUCTURED_TD_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.MapDomainImpl <em>Map Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.MapDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getMapDomain()
	 * @generated
	 */
	int MAP_DOMAIN = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_DOMAIN__NAME = STRUCTURED_TD__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_DOMAIN__CONSTRAINT = STRUCTURED_TD__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_DOMAIN__SIGNATURE = STRUCTURED_TD__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Source Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_DOMAIN__SOURCE_DOMAIN = STRUCTURED_TD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_DOMAIN__TARGET_DOMAIN = STRUCTURED_TD_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Map Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_DOMAIN_FEATURE_COUNT = STRUCTURED_TD_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.EnumTdImpl <em>Enum Td</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.EnumTdImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getEnumTd()
	 * @generated
	 */
	int ENUM_TD = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_TD__NAME = TYPE_DOMAIN__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_TD__CONSTRAINT = TYPE_DOMAIN__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_TD__SIGNATURE = TYPE_DOMAIN__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Element</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_TD__ELEMENT = TYPE_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enum Td</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_TD_FEATURE_COUNT = TYPE_DOMAIN_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.EnumElementImpl <em>Enum Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.EnumElementImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getEnumElement()
	 * @generated
	 */
	int ENUM_ELEMENT = 14;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT__SYMBOL = 0;

	/**
	 * The number of structural features of the '<em>Enum Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.ConcreteDomainImpl <em>Concrete Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.ConcreteDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getConcreteDomain()
	 * @generated
	 */
	int CONCRETE_DOMAIN = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_DOMAIN__NAME = DOMAIN__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_DOMAIN__CONSTRAINT = DOMAIN__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_DOMAIN__SIGNATURE = DOMAIN__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Initialization</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_DOMAIN__INITIALIZATION = DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_DOMAIN__DEFINITION = DOMAIN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_DOMAIN__TYPE_DOMAIN = DOMAIN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Is Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_DOMAIN__IS_DYNAMIC = DOMAIN_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Concrete Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONCRETE_DOMAIN_FEATURE_COUNT = DOMAIN_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.CharDomainImpl <em>Char Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.CharDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getCharDomain()
	 * @generated
	 */
	int CHAR_DOMAIN = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_DOMAIN__NAME = BASIC_TD__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_DOMAIN__CONSTRAINT = BASIC_TD__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_DOMAIN__SIGNATURE = BASIC_TD__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Char Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_DOMAIN_FEATURE_COUNT = BASIC_TD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.BooleanDomainImpl <em>Boolean Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.BooleanDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getBooleanDomain()
	 * @generated
	 */
	int BOOLEAN_DOMAIN = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_DOMAIN__NAME = BASIC_TD__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_DOMAIN__CONSTRAINT = BASIC_TD__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_DOMAIN__SIGNATURE = BASIC_TD__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Boolean Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_DOMAIN_FEATURE_COUNT = BASIC_TD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.BagDomainImpl <em>Bag Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.BagDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getBagDomain()
	 * @generated
	 */
	int BAG_DOMAIN = 21;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_DOMAIN__NAME = STRUCTURED_TD__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_DOMAIN__CONSTRAINT = STRUCTURED_TD__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_DOMAIN__SIGNATURE = STRUCTURED_TD__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_DOMAIN__DOMAIN = STRUCTURED_TD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bag Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BAG_DOMAIN_FEATURE_COUNT = STRUCTURED_TD_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.AnyDomainImpl <em>Any Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.AnyDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getAnyDomain()
	 * @generated
	 */
	int ANY_DOMAIN = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DOMAIN__NAME = TYPE_DOMAIN__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DOMAIN__CONSTRAINT = TYPE_DOMAIN__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DOMAIN__SIGNATURE = TYPE_DOMAIN__SIGNATURE;

	/**
	 * The number of structural features of the '<em>Any Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DOMAIN_FEATURE_COUNT = TYPE_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link asmeta.definitions.domains.impl.AgentDomainImpl <em>Agent Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.impl.AgentDomainImpl
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getAgentDomain()
	 * @generated
	 */
	int AGENT_DOMAIN = 23;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_DOMAIN__NAME = ABSTRACT_TD__NAME;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_DOMAIN__CONSTRAINT = ABSTRACT_TD__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_DOMAIN__SIGNATURE = ABSTRACT_TD__SIGNATURE;

	/**
	 * The feature id for the '<em><b>Is Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_DOMAIN__IS_DYNAMIC = ABSTRACT_TD__IS_DYNAMIC;

	/**
	 * The number of structural features of the '<em>Agent Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGENT_DOMAIN_FEATURE_COUNT = ABSTRACT_TD_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '<em>Domain DT</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see asmeta.definitions.domains.Domain
	 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getDomainDT()
	 * @generated
	 */
	int DOMAIN_DT = 25;


	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.NaturalDomain <em>Natural Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Natural Domain</em>'.
	 * @see asmeta.definitions.domains.NaturalDomain
	 * @generated
	 */
	EClass getNaturalDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.UndefDomain <em>Undef Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Undef Domain</em>'.
	 * @see asmeta.definitions.domains.UndefDomain
	 * @generated
	 */
	EClass getUndefDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.TypeDomain <em>Type Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Domain</em>'.
	 * @see asmeta.definitions.domains.TypeDomain
	 * @generated
	 */
	EClass getTypeDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.StructuredTd <em>Structured Td</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Structured Td</em>'.
	 * @see asmeta.definitions.domains.StructuredTd
	 * @generated
	 */
	EClass getStructuredTd();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.StringDomain <em>String Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Domain</em>'.
	 * @see asmeta.definitions.domains.StringDomain
	 * @generated
	 */
	EClass getStringDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.SequenceDomain <em>Sequence Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sequence Domain</em>'.
	 * @see asmeta.definitions.domains.SequenceDomain
	 * @generated
	 */
	EClass getSequenceDomain();

	/**
	 * Returns the meta object for the reference '{@link asmeta.definitions.domains.SequenceDomain#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Domain</em>'.
	 * @see asmeta.definitions.domains.SequenceDomain#getDomain()
	 * @see #getSequenceDomain()
	 * @generated
	 */
	EReference getSequenceDomain_Domain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.RuleDomain <em>Rule Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Domain</em>'.
	 * @see asmeta.definitions.domains.RuleDomain
	 * @generated
	 */
	EClass getRuleDomain();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.definitions.domains.RuleDomain#getDomains <em>Domains</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Domains</em>'.
	 * @see asmeta.definitions.domains.RuleDomain#getDomains()
	 * @see #getRuleDomain()
	 * @generated
	 */
	EAttribute getRuleDomain_Domains();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.ReserveDomain <em>Reserve Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reserve Domain</em>'.
	 * @see asmeta.definitions.domains.ReserveDomain
	 * @generated
	 */
	EClass getReserveDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.RealDomain <em>Real Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Real Domain</em>'.
	 * @see asmeta.definitions.domains.RealDomain
	 * @generated
	 */
	EClass getRealDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.ProductDomain <em>Product Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Product Domain</em>'.
	 * @see asmeta.definitions.domains.ProductDomain
	 * @generated
	 */
	EClass getProductDomain();

	/**
	 * Returns the meta object for the attribute list '{@link asmeta.definitions.domains.ProductDomain#getDomains <em>Domains</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Domains</em>'.
	 * @see asmeta.definitions.domains.ProductDomain#getDomains()
	 * @see #getProductDomain()
	 * @generated
	 */
	EAttribute getProductDomain_Domains();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.PowersetDomain <em>Powerset Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Powerset Domain</em>'.
	 * @see asmeta.definitions.domains.PowersetDomain
	 * @generated
	 */
	EClass getPowersetDomain();

	/**
	 * Returns the meta object for the reference '{@link asmeta.definitions.domains.PowersetDomain#getBaseDomain <em>Base Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Domain</em>'.
	 * @see asmeta.definitions.domains.PowersetDomain#getBaseDomain()
	 * @see #getPowersetDomain()
	 * @generated
	 */
	EReference getPowersetDomain_BaseDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.MapDomain <em>Map Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Domain</em>'.
	 * @see asmeta.definitions.domains.MapDomain
	 * @generated
	 */
	EClass getMapDomain();

	/**
	 * Returns the meta object for the reference '{@link asmeta.definitions.domains.MapDomain#getSourceDomain <em>Source Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Domain</em>'.
	 * @see asmeta.definitions.domains.MapDomain#getSourceDomain()
	 * @see #getMapDomain()
	 * @generated
	 */
	EReference getMapDomain_SourceDomain();

	/**
	 * Returns the meta object for the reference '{@link asmeta.definitions.domains.MapDomain#getTargetDomain <em>Target Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Domain</em>'.
	 * @see asmeta.definitions.domains.MapDomain#getTargetDomain()
	 * @see #getMapDomain()
	 * @generated
	 */
	EReference getMapDomain_TargetDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.IntegerDomain <em>Integer Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Domain</em>'.
	 * @see asmeta.definitions.domains.IntegerDomain
	 * @generated
	 */
	EClass getIntegerDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.EnumTd <em>Enum Td</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Td</em>'.
	 * @see asmeta.definitions.domains.EnumTd
	 * @generated
	 */
	EClass getEnumTd();

	/**
	 * Returns the meta object for the containment reference list '{@link asmeta.definitions.domains.EnumTd#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Element</em>'.
	 * @see asmeta.definitions.domains.EnumTd#getElement()
	 * @see #getEnumTd()
	 * @generated
	 */
	EReference getEnumTd_Element();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.EnumElement <em>Enum Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Element</em>'.
	 * @see asmeta.definitions.domains.EnumElement
	 * @generated
	 */
	EClass getEnumElement();

	/**
	 * Returns the meta object for the attribute '{@link asmeta.definitions.domains.EnumElement#getSymbol <em>Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Symbol</em>'.
	 * @see asmeta.definitions.domains.EnumElement#getSymbol()
	 * @see #getEnumElement()
	 * @generated
	 */
	EAttribute getEnumElement_Symbol();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.Domain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain</em>'.
	 * @see asmeta.definitions.domains.Domain
	 * @generated
	 */
	EClass getDomain();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.definitions.domains.Domain#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Constraint</em>'.
	 * @see asmeta.definitions.domains.Domain#getConstraint()
	 * @see #getDomain()
	 * @generated
	 */
	EReference getDomain_Constraint();

	/**
	 * Returns the meta object for the container reference '{@link asmeta.definitions.domains.Domain#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Signature</em>'.
	 * @see asmeta.definitions.domains.Domain#getSignature()
	 * @see #getDomain()
	 * @generated
	 */
	EReference getDomain_Signature();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.ConcreteDomain <em>Concrete Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Concrete Domain</em>'.
	 * @see asmeta.definitions.domains.ConcreteDomain
	 * @generated
	 */
	EClass getConcreteDomain();

	/**
	 * Returns the meta object for the reference list '{@link asmeta.definitions.domains.ConcreteDomain#getInitialization <em>Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Initialization</em>'.
	 * @see asmeta.definitions.domains.ConcreteDomain#getInitialization()
	 * @see #getConcreteDomain()
	 * @generated
	 */
	EReference getConcreteDomain_Initialization();

	/**
	 * Returns the meta object for the reference '{@link asmeta.definitions.domains.ConcreteDomain#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Definition</em>'.
	 * @see asmeta.definitions.domains.ConcreteDomain#getDefinition()
	 * @see #getConcreteDomain()
	 * @generated
	 */
	EReference getConcreteDomain_Definition();

	/**
	 * Returns the meta object for the reference '{@link asmeta.definitions.domains.ConcreteDomain#getTypeDomain <em>Type Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type Domain</em>'.
	 * @see asmeta.definitions.domains.ConcreteDomain#getTypeDomain()
	 * @see #getConcreteDomain()
	 * @generated
	 */
	EReference getConcreteDomain_TypeDomain();

	/**
	 * Returns the meta object for the attribute '{@link asmeta.definitions.domains.ConcreteDomain#getIsDynamic <em>Is Dynamic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Dynamic</em>'.
	 * @see asmeta.definitions.domains.ConcreteDomain#getIsDynamic()
	 * @see #getConcreteDomain()
	 * @generated
	 */
	EAttribute getConcreteDomain_IsDynamic();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.ComplexDomain <em>Complex Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Complex Domain</em>'.
	 * @see asmeta.definitions.domains.ComplexDomain
	 * @generated
	 */
	EClass getComplexDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.CharDomain <em>Char Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Char Domain</em>'.
	 * @see asmeta.definitions.domains.CharDomain
	 * @generated
	 */
	EClass getCharDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.BooleanDomain <em>Boolean Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Domain</em>'.
	 * @see asmeta.definitions.domains.BooleanDomain
	 * @generated
	 */
	EClass getBooleanDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.BasicTd <em>Basic Td</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic Td</em>'.
	 * @see asmeta.definitions.domains.BasicTd
	 * @generated
	 */
	EClass getBasicTd();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.BagDomain <em>Bag Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bag Domain</em>'.
	 * @see asmeta.definitions.domains.BagDomain
	 * @generated
	 */
	EClass getBagDomain();

	/**
	 * Returns the meta object for the reference '{@link asmeta.definitions.domains.BagDomain#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Domain</em>'.
	 * @see asmeta.definitions.domains.BagDomain#getDomain()
	 * @see #getBagDomain()
	 * @generated
	 */
	EReference getBagDomain_Domain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.AnyDomain <em>Any Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Domain</em>'.
	 * @see asmeta.definitions.domains.AnyDomain
	 * @generated
	 */
	EClass getAnyDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.AgentDomain <em>Agent Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Agent Domain</em>'.
	 * @see asmeta.definitions.domains.AgentDomain
	 * @generated
	 */
	EClass getAgentDomain();

	/**
	 * Returns the meta object for class '{@link asmeta.definitions.domains.AbstractTd <em>Abstract Td</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Td</em>'.
	 * @see asmeta.definitions.domains.AbstractTd
	 * @generated
	 */
	EClass getAbstractTd();

	/**
	 * Returns the meta object for the attribute '{@link asmeta.definitions.domains.AbstractTd#getIsDynamic <em>Is Dynamic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Dynamic</em>'.
	 * @see asmeta.definitions.domains.AbstractTd#getIsDynamic()
	 * @see #getAbstractTd()
	 * @generated
	 */
	EAttribute getAbstractTd_IsDynamic();

	/**
	 * Returns the meta object for data type '{@link asmeta.definitions.domains.Domain <em>Domain DT</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Domain DT</em>'.
	 * @see asmeta.definitions.domains.Domain
	 * @model instanceClass="asmeta.definitions.domains.Domain"
	 * @generated
	 */
	EDataType getDomainDT();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DomainsFactory getDomainsFactory();

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
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.NaturalDomainImpl <em>Natural Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.NaturalDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getNaturalDomain()
		 * @generated
		 */
		EClass NATURAL_DOMAIN = eINSTANCE.getNaturalDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.UndefDomainImpl <em>Undef Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.UndefDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getUndefDomain()
		 * @generated
		 */
		EClass UNDEF_DOMAIN = eINSTANCE.getUndefDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.TypeDomainImpl <em>Type Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.TypeDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getTypeDomain()
		 * @generated
		 */
		EClass TYPE_DOMAIN = eINSTANCE.getTypeDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.StructuredTdImpl <em>Structured Td</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.StructuredTdImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getStructuredTd()
		 * @generated
		 */
		EClass STRUCTURED_TD = eINSTANCE.getStructuredTd();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.StringDomainImpl <em>String Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.StringDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getStringDomain()
		 * @generated
		 */
		EClass STRING_DOMAIN = eINSTANCE.getStringDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.SequenceDomainImpl <em>Sequence Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.SequenceDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getSequenceDomain()
		 * @generated
		 */
		EClass SEQUENCE_DOMAIN = eINSTANCE.getSequenceDomain();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEQUENCE_DOMAIN__DOMAIN = eINSTANCE.getSequenceDomain_Domain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.RuleDomainImpl <em>Rule Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.RuleDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getRuleDomain()
		 * @generated
		 */
		EClass RULE_DOMAIN = eINSTANCE.getRuleDomain();

		/**
		 * The meta object literal for the '<em><b>Domains</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_DOMAIN__DOMAINS = eINSTANCE.getRuleDomain_Domains();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.ReserveDomainImpl <em>Reserve Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.ReserveDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getReserveDomain()
		 * @generated
		 */
		EClass RESERVE_DOMAIN = eINSTANCE.getReserveDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.RealDomainImpl <em>Real Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.RealDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getRealDomain()
		 * @generated
		 */
		EClass REAL_DOMAIN = eINSTANCE.getRealDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.ProductDomainImpl <em>Product Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.ProductDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getProductDomain()
		 * @generated
		 */
		EClass PRODUCT_DOMAIN = eINSTANCE.getProductDomain();

		/**
		 * The meta object literal for the '<em><b>Domains</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT_DOMAIN__DOMAINS = eINSTANCE.getProductDomain_Domains();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.PowersetDomainImpl <em>Powerset Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.PowersetDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getPowersetDomain()
		 * @generated
		 */
		EClass POWERSET_DOMAIN = eINSTANCE.getPowersetDomain();

		/**
		 * The meta object literal for the '<em><b>Base Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POWERSET_DOMAIN__BASE_DOMAIN = eINSTANCE.getPowersetDomain_BaseDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.MapDomainImpl <em>Map Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.MapDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getMapDomain()
		 * @generated
		 */
		EClass MAP_DOMAIN = eINSTANCE.getMapDomain();

		/**
		 * The meta object literal for the '<em><b>Source Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAP_DOMAIN__SOURCE_DOMAIN = eINSTANCE.getMapDomain_SourceDomain();

		/**
		 * The meta object literal for the '<em><b>Target Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAP_DOMAIN__TARGET_DOMAIN = eINSTANCE.getMapDomain_TargetDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.IntegerDomainImpl <em>Integer Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.IntegerDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getIntegerDomain()
		 * @generated
		 */
		EClass INTEGER_DOMAIN = eINSTANCE.getIntegerDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.EnumTdImpl <em>Enum Td</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.EnumTdImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getEnumTd()
		 * @generated
		 */
		EClass ENUM_TD = eINSTANCE.getEnumTd();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUM_TD__ELEMENT = eINSTANCE.getEnumTd_Element();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.EnumElementImpl <em>Enum Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.EnumElementImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getEnumElement()
		 * @generated
		 */
		EClass ENUM_ELEMENT = eINSTANCE.getEnumElement();

		/**
		 * The meta object literal for the '<em><b>Symbol</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_ELEMENT__SYMBOL = eINSTANCE.getEnumElement_Symbol();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.DomainImpl <em>Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.DomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getDomain()
		 * @generated
		 */
		EClass DOMAIN = eINSTANCE.getDomain();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN__CONSTRAINT = eINSTANCE.getDomain_Constraint();

		/**
		 * The meta object literal for the '<em><b>Signature</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN__SIGNATURE = eINSTANCE.getDomain_Signature();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.ConcreteDomainImpl <em>Concrete Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.ConcreteDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getConcreteDomain()
		 * @generated
		 */
		EClass CONCRETE_DOMAIN = eINSTANCE.getConcreteDomain();

		/**
		 * The meta object literal for the '<em><b>Initialization</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONCRETE_DOMAIN__INITIALIZATION = eINSTANCE.getConcreteDomain_Initialization();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONCRETE_DOMAIN__DEFINITION = eINSTANCE.getConcreteDomain_Definition();

		/**
		 * The meta object literal for the '<em><b>Type Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONCRETE_DOMAIN__TYPE_DOMAIN = eINSTANCE.getConcreteDomain_TypeDomain();

		/**
		 * The meta object literal for the '<em><b>Is Dynamic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONCRETE_DOMAIN__IS_DYNAMIC = eINSTANCE.getConcreteDomain_IsDynamic();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.ComplexDomainImpl <em>Complex Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.ComplexDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getComplexDomain()
		 * @generated
		 */
		EClass COMPLEX_DOMAIN = eINSTANCE.getComplexDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.CharDomainImpl <em>Char Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.CharDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getCharDomain()
		 * @generated
		 */
		EClass CHAR_DOMAIN = eINSTANCE.getCharDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.BooleanDomainImpl <em>Boolean Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.BooleanDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getBooleanDomain()
		 * @generated
		 */
		EClass BOOLEAN_DOMAIN = eINSTANCE.getBooleanDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.BasicTdImpl <em>Basic Td</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.BasicTdImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getBasicTd()
		 * @generated
		 */
		EClass BASIC_TD = eINSTANCE.getBasicTd();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.BagDomainImpl <em>Bag Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.BagDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getBagDomain()
		 * @generated
		 */
		EClass BAG_DOMAIN = eINSTANCE.getBagDomain();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BAG_DOMAIN__DOMAIN = eINSTANCE.getBagDomain_Domain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.AnyDomainImpl <em>Any Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.AnyDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getAnyDomain()
		 * @generated
		 */
		EClass ANY_DOMAIN = eINSTANCE.getAnyDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.AgentDomainImpl <em>Agent Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.AgentDomainImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getAgentDomain()
		 * @generated
		 */
		EClass AGENT_DOMAIN = eINSTANCE.getAgentDomain();

		/**
		 * The meta object literal for the '{@link asmeta.definitions.domains.impl.AbstractTdImpl <em>Abstract Td</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.impl.AbstractTdImpl
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getAbstractTd()
		 * @generated
		 */
		EClass ABSTRACT_TD = eINSTANCE.getAbstractTd();

		/**
		 * The meta object literal for the '<em><b>Is Dynamic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_TD__IS_DYNAMIC = eINSTANCE.getAbstractTd_IsDynamic();

		/**
		 * The meta object literal for the '<em>Domain DT</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see asmeta.definitions.domains.Domain
		 * @see asmeta.definitions.domains.impl.DomainsPackageImpl#getDomainDT()
		 * @generated
		 */
		EDataType DOMAIN_DT = eINSTANCE.getDomainDT();

	}

} //DomainsPackage

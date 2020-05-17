/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains.impl;

import asmeta.definitions.domains.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.BagDomain;
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
import asmeta.definitions.domains.UndefDomain;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DomainsFactoryImpl extends EFactoryImpl implements DomainsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DomainsFactory init() {
		try {
			DomainsFactory theDomainsFactory = (DomainsFactory)EPackage.Registry.INSTANCE.getEFactory(DomainsPackage.eNS_URI);
			if (theDomainsFactory != null) {
				return theDomainsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DomainsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DomainsPackage.NATURAL_DOMAIN: return createNaturalDomain();
			case DomainsPackage.UNDEF_DOMAIN: return createUndefDomain();
			case DomainsPackage.STRING_DOMAIN: return createStringDomain();
			case DomainsPackage.SEQUENCE_DOMAIN: return createSequenceDomain();
			case DomainsPackage.RULE_DOMAIN: return createRuleDomain();
			case DomainsPackage.RESERVE_DOMAIN: return createReserveDomain();
			case DomainsPackage.REAL_DOMAIN: return createRealDomain();
			case DomainsPackage.PRODUCT_DOMAIN: return createProductDomain();
			case DomainsPackage.POWERSET_DOMAIN: return createPowersetDomain();
			case DomainsPackage.MAP_DOMAIN: return createMapDomain();
			case DomainsPackage.INTEGER_DOMAIN: return createIntegerDomain();
			case DomainsPackage.ENUM_TD: return createEnumTd();
			case DomainsPackage.ENUM_ELEMENT: return createEnumElement();
			case DomainsPackage.CONCRETE_DOMAIN: return createConcreteDomain();
			case DomainsPackage.COMPLEX_DOMAIN: return createComplexDomain();
			case DomainsPackage.CHAR_DOMAIN: return createCharDomain();
			case DomainsPackage.BOOLEAN_DOMAIN: return createBooleanDomain();
			case DomainsPackage.BAG_DOMAIN: return createBagDomain();
			case DomainsPackage.ANY_DOMAIN: return createAnyDomain();
			case DomainsPackage.AGENT_DOMAIN: return createAgentDomain();
			case DomainsPackage.ABSTRACT_TD: return createAbstractTd();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case DomainsPackage.DOMAIN_DT:
				return createDomainDTFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case DomainsPackage.DOMAIN_DT:
				return convertDomainDTToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NaturalDomain createNaturalDomain() {
		NaturalDomainImpl naturalDomain = new NaturalDomainImpl();
		return naturalDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UndefDomain createUndefDomain() {
		UndefDomainImpl undefDomain = new UndefDomainImpl();
		return undefDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StringDomain createStringDomain() {
		StringDomainImpl stringDomain = new StringDomainImpl();
		return stringDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@Override
	public SequenceDomain createSequenceDomain() {
		SequenceDomainImpl sequenceDomain = new SequenceDomainImpl();
		// add this domain to the list of all the structured td
		stds.add(sequenceDomain);
		return sequenceDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuleDomain createRuleDomain() {
		RuleDomainImpl ruleDomain = new RuleDomainImpl();
		return ruleDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ReserveDomain createReserveDomain() {
		ReserveDomainImpl reserveDomain = new ReserveDomainImpl();
		return reserveDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RealDomain createRealDomain() {
		RealDomainImpl realDomain = new RealDomainImpl();
		return realDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@Override
	public ProductDomain createProductDomain() {
		ProductDomainImpl productDomain = new ProductDomainImpl();
		// add this domain to the list of all the structured td
		// per risalire a tutte le istanze di StructuredTd
		stds.add(productDomain);
		return productDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@Override
	public PowersetDomain createPowersetDomain() {
		PowersetDomainImpl powersetDomain = new PowersetDomainImpl();
		// add this domain to the list of all the structured td
		stds.add(powersetDomain);
		return powersetDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@Override
	public MapDomain createMapDomain() {
		MapDomainImpl mapDomain = new MapDomainImpl();
		// add this domain to the list of all the structured td
		stds.add(mapDomain);
		return mapDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@Override
	public IntegerDomain createIntegerDomain() {
		
		IntegerDomainImpl integerDomain = new IntegerDomainImpl();
		return integerDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EnumTd createEnumTd() {
		EnumTdImpl enumTd = new EnumTdImpl();
		return enumTd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EnumElement createEnumElement() {
		EnumElementImpl enumElement = new EnumElementImpl();
		return enumElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConcreteDomain createConcreteDomain() {
		ConcreteDomainImpl concreteDomain = new ConcreteDomainImpl();
		return concreteDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComplexDomain createComplexDomain() {
		ComplexDomainImpl complexDomain = new ComplexDomainImpl();
		return complexDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CharDomain createCharDomain() {
		CharDomainImpl charDomain = new CharDomainImpl();
		return charDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BooleanDomain createBooleanDomain() {
		BooleanDomainImpl booleanDomain = new BooleanDomainImpl();
		return booleanDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	@Override
	public BagDomain createBagDomain() {
		BagDomainImpl bagDomain = new BagDomainImpl();
		// add this domain to the list of all the structured td
		stds.add(bagDomain);
		return bagDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * Cannot be called, use the constructor with name instead
	 */
	@Override
	public AnyDomain createAnyDomain() {
		throw new RuntimeException("any domain can be created only with name");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AgentDomain createAgentDomain() {
		AgentDomainImpl agentDomain = new AgentDomainImpl();
		return agentDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractTd createAbstractTd() {
		AbstractTdImpl abstractTd = new AbstractTdImpl();
		return abstractTd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain createDomainDTFromString(EDataType eDataType, String initialValue) {
		return (Domain)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDomainDTToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DomainsPackage getDomainsPackage() {
		return (DomainsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DomainsPackage getPackage() {
		return DomainsPackage.eINSTANCE;
	}

	
	
	private Map<String,AnyDomain> anydomains = new HashMap<String,AnyDomain>();
	
	@Override
	public AnyDomain createAnyDomain(String name) {		
		AnyDomainImpl anyDomain = new AnyDomainImpl();
		anyDomain.setName(name);
		anydomains.put(name,anyDomain);
		return anyDomain;
	}

	@Override
	public AnyDomain getAnyDomain(String name) {
		return anydomains.get(name);
	}

	private Set<StructuredTd> stds = new HashSet<StructuredTd>();
	
	
	@Override
	public StructuredTd getStructuredTd(String name) {
		for(StructuredTd std: stds){
			if (std.getName().equals(name)) return std;
		}
		return null;
	}

	@Override
	public void resetDomains() {
		stds = new HashSet<StructuredTd>();
		anydomains = new HashMap<String,AnyDomain>();
	}

} //DomainsFactoryImpl

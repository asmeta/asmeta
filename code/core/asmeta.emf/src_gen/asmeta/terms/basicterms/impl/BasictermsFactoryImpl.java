/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms.impl;

import asmeta.terms.basicterms.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import asmeta.terms.basicterms.BasictermsFactory;
import asmeta.terms.basicterms.BasictermsPackage;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.RuleAsTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableKind;
import asmeta.terms.basicterms.VariableTerm;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BasictermsFactoryImpl extends EFactoryImpl implements BasictermsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BasictermsFactory init() {
		try {
			BasictermsFactory theBasictermsFactory = (BasictermsFactory)EPackage.Registry.INSTANCE.getEFactory(BasictermsPackage.eNS_URI);
			if (theBasictermsFactory != null) {
				return theBasictermsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BasictermsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasictermsFactoryImpl() {
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
			case BasictermsPackage.VARIABLE_TERM: return createVariableTerm();
			case BasictermsPackage.UNDEF_TERM: return createUndefTerm();
			case BasictermsPackage.TUPLE_TERM: return createTupleTerm();
			case BasictermsPackage.SET_TERM: return createSetTerm();
			case BasictermsPackage.RULE_AS_TERM: return createRuleAsTerm();
			case BasictermsPackage.LOCATION_TERM: return createLocationTerm();
			case BasictermsPackage.FUNCTION_TERM: return createFunctionTerm();
			case BasictermsPackage.DOMAIN_TERM: return createDomainTerm();
			case BasictermsPackage.BOOLEAN_TERM: return createBooleanTerm();
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
			case BasictermsPackage.VARIABLE_KIND:
				return createVariableKindFromString(eDataType, initialValue);
			case BasictermsPackage.TERM_DT:
				return createTermDTFromString(eDataType, initialValue);
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
			case BasictermsPackage.VARIABLE_KIND:
				return convertVariableKindToString(eDataType, instanceValue);
			case BasictermsPackage.TERM_DT:
				return convertTermDTToString(eDataType, instanceValue);
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
	public VariableTerm createVariableTerm() {
		VariableTermImpl variableTerm = new VariableTermImpl();
		return variableTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UndefTerm createUndefTerm() {
		UndefTermImpl undefTerm = new UndefTermImpl();
		return undefTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TupleTerm createTupleTerm() {
		TupleTermImpl tupleTerm = new TupleTermImpl();
		return tupleTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SetTerm createSetTerm() {
		SetTermImpl setTerm = new SetTermImpl();
		return setTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuleAsTerm createRuleAsTerm() {
		RuleAsTermImpl ruleAsTerm = new RuleAsTermImpl();
		return ruleAsTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LocationTerm createLocationTerm() {
		LocationTermImpl locationTerm = new LocationTermImpl();
		return locationTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FunctionTerm createFunctionTerm() {
		FunctionTermImpl functionTerm = new FunctionTermImpl();
		return functionTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DomainTerm createDomainTerm() {
		DomainTermImpl domainTerm = new DomainTermImpl();
		return domainTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BooleanTerm createBooleanTerm() {
		BooleanTermImpl booleanTerm = new BooleanTermImpl();
		return booleanTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariableKind createVariableKindFromString(EDataType eDataType, String initialValue) {
		VariableKind result = VariableKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVariableKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Term createTermDTFromString(EDataType eDataType, String initialValue) {
		return (Term)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTermDTToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BasictermsPackage getBasictermsPackage() {
		return (BasictermsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BasictermsPackage getPackage() {
		return BasictermsPackage.eINSTANCE;
	}

	/** */
	public BooleanTerm TRUE = initBooleanTerm(true);
	
	public BooleanTerm FALSE = initBooleanTerm(false);

	@Override
	public BooleanTerm createBooleanTerm(boolean symbol) {
		return symbol ? TRUE: FALSE;
	}
	
	private BooleanTerm initBooleanTerm(boolean symbol) {
		BooleanTerm bt = createBooleanTerm();
		bt.setSymbol(String.valueOf(symbol));
		return bt;
	}

	@Override @Deprecated
	public VariableTerm createVariableTerm(String name, VariableKind kind) {
		VariableTerm result = createVariableTerm();
		result.setName(name);
		result.setKind(kind);
		return result;
	}

} //BasictermsFactoryImpl

/**
 */
package asmeta.terms.furtherterms.impl;

import asmeta.terms.furtherterms.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FurthertermsFactoryImpl extends EFactoryImpl implements FurthertermsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static FurthertermsFactory init() {
		try {
			FurthertermsFactory theFurthertermsFactory = (FurthertermsFactory)EPackage.Registry.INSTANCE.getEFactory(FurthertermsPackage.eNS_URI);
			if (theFurthertermsFactory != null) {
				return theFurthertermsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new FurthertermsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FurthertermsFactoryImpl() {
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
			case FurthertermsPackage.INTEGER_TERM: return createIntegerTerm();
			case FurthertermsPackage.NATURAL_TERM: return createNaturalTerm();
			case FurthertermsPackage.STRING_TERM: return createStringTerm();
			case FurthertermsPackage.SET_CT: return createSetCt();
			case FurthertermsPackage.SEQUENCE_TERM: return createSequenceTerm();
			case FurthertermsPackage.SEQUENCE_CT: return createSequenceCt();
			case FurthertermsPackage.REAL_TERM: return createRealTerm();
			case FurthertermsPackage.MAP_TERM: return createMapTerm();
			case FurthertermsPackage.MAP_CT: return createMapCt();
			case FurthertermsPackage.LET_TERM: return createLetTerm();
			case FurthertermsPackage.FORALL_TERM: return createForallTerm();
			case FurthertermsPackage.EXISTS_UNIQUE_TERM: return createExistsUniqueTerm();
			case FurthertermsPackage.EXISTS_TERM: return createExistsTerm();
			case FurthertermsPackage.ENUM_TERM: return createEnumTerm();
			case FurthertermsPackage.CONDITIONAL_TERM: return createConditionalTerm();
			case FurthertermsPackage.COMPLEX_TERM: return createComplexTerm();
			case FurthertermsPackage.CHAR_TERM: return createCharTerm();
			case FurthertermsPackage.CASE_TERM: return createCaseTerm();
			case FurthertermsPackage.BAG_TERM: return createBagTerm();
			case FurthertermsPackage.BAG_CT: return createBagCt();
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
	public IntegerTerm createIntegerTerm() {
		IntegerTermImpl integerTerm = new IntegerTermImpl();
		return integerTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NaturalTerm createNaturalTerm() {
		NaturalTermImpl naturalTerm = new NaturalTermImpl();
		return naturalTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StringTerm createStringTerm() {
		StringTermImpl stringTerm = new StringTermImpl();
		return stringTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SetCt createSetCt() {
		SetCtImpl setCt = new SetCtImpl();
		return setCt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SequenceTerm createSequenceTerm() {
		SequenceTermImpl sequenceTerm = new SequenceTermImpl();
		return sequenceTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SequenceCt createSequenceCt() {
		SequenceCtImpl sequenceCt = new SequenceCtImpl();
		return sequenceCt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RealTerm createRealTerm() {
		RealTermImpl realTerm = new RealTermImpl();
		return realTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MapTerm createMapTerm() {
		MapTermImpl mapTerm = new MapTermImpl();
		return mapTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MapCt createMapCt() {
		MapCtImpl mapCt = new MapCtImpl();
		return mapCt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LetTerm createLetTerm() {
		LetTermImpl letTerm = new LetTermImpl();
		return letTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ForallTerm createForallTerm() {
		ForallTermImpl forallTerm = new ForallTermImpl();
		return forallTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExistsUniqueTerm createExistsUniqueTerm() {
		ExistsUniqueTermImpl existsUniqueTerm = new ExistsUniqueTermImpl();
		return existsUniqueTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExistsTerm createExistsTerm() {
		ExistsTermImpl existsTerm = new ExistsTermImpl();
		return existsTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EnumTerm createEnumTerm() {
		EnumTermImpl enumTerm = new EnumTermImpl();
		return enumTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConditionalTerm createConditionalTerm() {
		ConditionalTermImpl conditionalTerm = new ConditionalTermImpl();
		return conditionalTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComplexTerm createComplexTerm() {
		ComplexTermImpl complexTerm = new ComplexTermImpl();
		return complexTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CharTerm createCharTerm() {
		CharTermImpl charTerm = new CharTermImpl();
		return charTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CaseTerm createCaseTerm() {
		CaseTermImpl caseTerm = new CaseTermImpl();
		return caseTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BagTerm createBagTerm() {
		BagTermImpl bagTerm = new BagTermImpl();
		return bagTerm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BagCt createBagCt() {
		BagCtImpl bagCt = new BagCtImpl();
		return bagCt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FurthertermsPackage getFurthertermsPackage() {
		return (FurthertermsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static FurthertermsPackage getPackage() {
		return FurthertermsPackage.eINSTANCE;
	}

} //FurthertermsFactoryImpl

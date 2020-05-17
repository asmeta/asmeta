/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules.impl;

import asmeta.transitionrules.turbotransitionrules.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import asmeta.transitionrules.turbotransitionrules.IterateRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.transitionrules.turbotransitionrules.TryCatchRule;
import asmeta.transitionrules.turbotransitionrules.TurboCallRule;
import asmeta.transitionrules.turbotransitionrules.TurboDeclaration;
import asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule;
import asmeta.transitionrules.turbotransitionrules.TurboReturnRule;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesFactory;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TurbotransitionrulesFactoryImpl extends EFactoryImpl implements TurbotransitionrulesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TurbotransitionrulesFactory init() {
		try {
			TurbotransitionrulesFactory theTurbotransitionrulesFactory = (TurbotransitionrulesFactory)EPackage.Registry.INSTANCE.getEFactory(TurbotransitionrulesPackage.eNS_URI);
			if (theTurbotransitionrulesFactory != null) {
				return theTurbotransitionrulesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TurbotransitionrulesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TurbotransitionrulesFactoryImpl() {
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
			case TurbotransitionrulesPackage.TURBO_DECLARATION: return createTurboDeclaration();
			case TurbotransitionrulesPackage.SEQ_RULE: return createSeqRule();
			case TurbotransitionrulesPackage.TURBO_LOCAL_STATE_RULE: return createTurboLocalStateRule();
			case TurbotransitionrulesPackage.TURBO_CALL_RULE: return createTurboCallRule();
			case TurbotransitionrulesPackage.TURBO_RETURN_RULE: return createTurboReturnRule();
			case TurbotransitionrulesPackage.TRY_CATCH_RULE: return createTryCatchRule();
			case TurbotransitionrulesPackage.ITERATE_RULE: return createIterateRule();
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
	public TurboDeclaration createTurboDeclaration() {
		TurboDeclarationImpl turboDeclaration = new TurboDeclarationImpl();
		return turboDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SeqRule createSeqRule() {
		SeqRuleImpl seqRule = new SeqRuleImpl();
		return seqRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TurboLocalStateRule createTurboLocalStateRule() {
		TurboLocalStateRuleImpl turboLocalStateRule = new TurboLocalStateRuleImpl();
		return turboLocalStateRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TurboCallRule createTurboCallRule() {
		TurboCallRuleImpl turboCallRule = new TurboCallRuleImpl();
		return turboCallRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TurboReturnRule createTurboReturnRule() {
		TurboReturnRuleImpl turboReturnRule = new TurboReturnRuleImpl();
		return turboReturnRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TryCatchRule createTryCatchRule() {
		TryCatchRuleImpl tryCatchRule = new TryCatchRuleImpl();
		return tryCatchRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IterateRule createIterateRule() {
		IterateRuleImpl iterateRule = new IterateRuleImpl();
		return iterateRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TurbotransitionrulesPackage getTurbotransitionrulesPackage() {
		return (TurbotransitionrulesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TurbotransitionrulesPackage getPackage() {
		return TurbotransitionrulesPackage.eINSTANCE;
	}

} //TurbotransitionrulesFactoryImpl

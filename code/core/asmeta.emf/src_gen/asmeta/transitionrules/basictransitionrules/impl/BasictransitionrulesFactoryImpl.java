/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules.impl;

import asmeta.transitionrules.basictransitionrules.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import asmeta.transitionrules.basictransitionrules.BasictransitionrulesFactory;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BasictransitionrulesFactoryImpl extends EFactoryImpl implements BasictransitionrulesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BasictransitionrulesFactory init() {
		try {
			BasictransitionrulesFactory theBasictransitionrulesFactory = (BasictransitionrulesFactory)EPackage.Registry.INSTANCE.getEFactory(BasictransitionrulesPackage.eNS_URI);
			if (theBasictransitionrulesFactory != null) {
				return theBasictransitionrulesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new BasictransitionrulesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasictransitionrulesFactoryImpl() {
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
			case BasictransitionrulesPackage.TERM_AS_RULE: return createTermAsRule();
			case BasictransitionrulesPackage.CHOOSE_RULE: return createChooseRule();
			case BasictransitionrulesPackage.MACRO_CALL_RULE: return createMacroCallRule();
			case BasictransitionrulesPackage.BLOCK_RULE: return createBlockRule();
			case BasictransitionrulesPackage.CONDITIONAL_RULE: return createConditionalRule();
			case BasictransitionrulesPackage.FORALL_RULE: return createForallRule();
			case BasictransitionrulesPackage.LET_RULE: return createLetRule();
			case BasictransitionrulesPackage.EXTEND_RULE: return createExtendRule();
			case BasictransitionrulesPackage.UPDATE_RULE: return createUpdateRule();
			case BasictransitionrulesPackage.SKIP_RULE: return createSkipRule();
			case BasictransitionrulesPackage.MACRO_DECLARATION: return createMacroDeclaration();
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
			case BasictransitionrulesPackage.RULE_DT:
				return createRuleDTFromString(eDataType, initialValue);
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
			case BasictransitionrulesPackage.RULE_DT:
				return convertRuleDTToString(eDataType, instanceValue);
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
	public TermAsRule createTermAsRule() {
		TermAsRuleImpl termAsRule = new TermAsRuleImpl();
		return termAsRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ChooseRule createChooseRule() {
		ChooseRuleImpl chooseRule = new ChooseRuleImpl();
		return chooseRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MacroCallRule createMacroCallRule() {
		MacroCallRuleImpl macroCallRule = new MacroCallRuleImpl();
		return macroCallRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockRule createBlockRule() {
		BlockRuleImpl blockRule = new BlockRuleImpl();
		return blockRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConditionalRule createConditionalRule() {
		ConditionalRuleImpl conditionalRule = new ConditionalRuleImpl();
		return conditionalRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ForallRule createForallRule() {
		ForallRuleImpl forallRule = new ForallRuleImpl();
		return forallRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LetRule createLetRule() {
		LetRuleImpl letRule = new LetRuleImpl();
		return letRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExtendRule createExtendRule() {
		ExtendRuleImpl extendRule = new ExtendRuleImpl();
		return extendRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UpdateRule createUpdateRule() {
		UpdateRuleImpl updateRule = new UpdateRuleImpl();
		return updateRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SkipRule createSkipRule() {
		SkipRuleImpl skipRule = new SkipRuleImpl();
		return skipRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MacroDeclaration createMacroDeclaration() {
		MacroDeclarationImpl macroDeclaration = new MacroDeclarationImpl();
		return macroDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule createRuleDTFromString(EDataType eDataType, String initialValue) {
		return (Rule)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRuleDTToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BasictransitionrulesPackage getBasictransitionrulesPackage() {
		return (BasictransitionrulesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static BasictransitionrulesPackage getPackage() {
		return BasictransitionrulesPackage.eINSTANCE;
	}

} //BasictransitionrulesFactoryImpl

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.Classifier;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.NamedElement;
import asmeta.transitionrules.basictransitionrules.*;
import asmeta.transitionrules.basictransitionrules.BasicRule;
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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage
 * @generated
 */
public class BasictransitionrulesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BasictransitionrulesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasictransitionrulesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = BasictransitionrulesPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BasictransitionrulesSwitch<Adapter> modelSwitch =
		new BasictransitionrulesSwitch<Adapter>() {
			@Override
			public Adapter caseTermAsRule(TermAsRule object) {
				return createTermAsRuleAdapter();
			}
			@Override
			public Adapter caseBasicRule(BasicRule object) {
				return createBasicRuleAdapter();
			}
			@Override
			public Adapter caseRule(Rule object) {
				return createRuleAdapter();
			}
			@Override
			public Adapter caseChooseRule(ChooseRule object) {
				return createChooseRuleAdapter();
			}
			@Override
			public Adapter caseMacroCallRule(MacroCallRule object) {
				return createMacroCallRuleAdapter();
			}
			@Override
			public Adapter caseBlockRule(BlockRule object) {
				return createBlockRuleAdapter();
			}
			@Override
			public Adapter caseConditionalRule(ConditionalRule object) {
				return createConditionalRuleAdapter();
			}
			@Override
			public Adapter caseForallRule(ForallRule object) {
				return createForallRuleAdapter();
			}
			@Override
			public Adapter caseLetRule(LetRule object) {
				return createLetRuleAdapter();
			}
			@Override
			public Adapter caseExtendRule(ExtendRule object) {
				return createExtendRuleAdapter();
			}
			@Override
			public Adapter caseUpdateRule(UpdateRule object) {
				return createUpdateRuleAdapter();
			}
			@Override
			public Adapter caseSkipRule(SkipRule object) {
				return createSkipRuleAdapter();
			}
			@Override
			public Adapter caseMacroDeclaration(MacroDeclaration object) {
				return createMacroDeclarationAdapter();
			}
			@Override
			public Adapter caseNamedElement(NamedElement object) {
				return createNamedElementAdapter();
			}
			@Override
			public Adapter caseClassifier(Classifier object) {
				return createClassifierAdapter();
			}
			@Override
			public Adapter caseRuleDeclaration(RuleDeclaration object) {
				return createRuleDeclarationAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.basictransitionrules.TermAsRule <em>Term As Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.basictransitionrules.TermAsRule
	 * @generated
	 */
	public Adapter createTermAsRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.basictransitionrules.BasicRule <em>Basic Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.basictransitionrules.BasicRule
	 * @generated
	 */
	public Adapter createBasicRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.basictransitionrules.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.basictransitionrules.Rule
	 * @generated
	 */
	public Adapter createRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.basictransitionrules.ChooseRule <em>Choose Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.basictransitionrules.ChooseRule
	 * @generated
	 */
	public Adapter createChooseRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.basictransitionrules.MacroCallRule <em>Macro Call Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.basictransitionrules.MacroCallRule
	 * @generated
	 */
	public Adapter createMacroCallRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.basictransitionrules.BlockRule <em>Block Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.basictransitionrules.BlockRule
	 * @generated
	 */
	public Adapter createBlockRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.basictransitionrules.ConditionalRule <em>Conditional Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.basictransitionrules.ConditionalRule
	 * @generated
	 */
	public Adapter createConditionalRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.basictransitionrules.ForallRule <em>Forall Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.basictransitionrules.ForallRule
	 * @generated
	 */
	public Adapter createForallRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.basictransitionrules.LetRule <em>Let Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.basictransitionrules.LetRule
	 * @generated
	 */
	public Adapter createLetRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.basictransitionrules.ExtendRule <em>Extend Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.basictransitionrules.ExtendRule
	 * @generated
	 */
	public Adapter createExtendRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.basictransitionrules.UpdateRule <em>Update Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.basictransitionrules.UpdateRule
	 * @generated
	 */
	public Adapter createUpdateRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.basictransitionrules.SkipRule <em>Skip Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.basictransitionrules.SkipRule
	 * @generated
	 */
	public Adapter createSkipRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.basictransitionrules.MacroDeclaration <em>Macro Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.basictransitionrules.MacroDeclaration
	 * @generated
	 */
	public Adapter createMacroDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.structure.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.NamedElement
	 * @generated
	 */
	public Adapter createNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.Classifier <em>Classifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.Classifier
	 * @generated
	 */
	public Adapter createClassifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.RuleDeclaration <em>Rule Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.RuleDeclaration
	 * @generated
	 */
	public Adapter createRuleDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //BasictransitionrulesAdapterFactory

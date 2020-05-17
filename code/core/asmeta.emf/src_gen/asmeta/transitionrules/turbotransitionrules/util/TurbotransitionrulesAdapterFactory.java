/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.Classifier;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.NamedElement;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.turbotransitionrules.*;
import asmeta.transitionrules.turbotransitionrules.IterateRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.transitionrules.turbotransitionrules.TryCatchRule;
import asmeta.transitionrules.turbotransitionrules.TurboCallRule;
import asmeta.transitionrules.turbotransitionrules.TurboDeclaration;
import asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule;
import asmeta.transitionrules.turbotransitionrules.TurboReturnRule;
import asmeta.transitionrules.turbotransitionrules.TurboRule;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage
 * @generated
 */
public class TurbotransitionrulesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TurbotransitionrulesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TurbotransitionrulesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TurbotransitionrulesPackage.eINSTANCE;
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
	protected TurbotransitionrulesSwitch<Adapter> modelSwitch =
		new TurbotransitionrulesSwitch<Adapter>() {
			@Override
			public Adapter caseTurboRule(TurboRule object) {
				return createTurboRuleAdapter();
			}
			@Override
			public Adapter caseTurboDeclaration(TurboDeclaration object) {
				return createTurboDeclarationAdapter();
			}
			@Override
			public Adapter caseSeqRule(SeqRule object) {
				return createSeqRuleAdapter();
			}
			@Override
			public Adapter caseTurboLocalStateRule(TurboLocalStateRule object) {
				return createTurboLocalStateRuleAdapter();
			}
			@Override
			public Adapter caseTurboCallRule(TurboCallRule object) {
				return createTurboCallRuleAdapter();
			}
			@Override
			public Adapter caseTurboReturnRule(TurboReturnRule object) {
				return createTurboReturnRuleAdapter();
			}
			@Override
			public Adapter caseTryCatchRule(TryCatchRule object) {
				return createTryCatchRuleAdapter();
			}
			@Override
			public Adapter caseIterateRule(IterateRule object) {
				return createIterateRuleAdapter();
			}
			@Override
			public Adapter caseRule(Rule object) {
				return createRuleAdapter();
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
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.turbotransitionrules.TurboRule <em>Turbo Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboRule
	 * @generated
	 */
	public Adapter createTurboRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.turbotransitionrules.TurboDeclaration <em>Turbo Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboDeclaration
	 * @generated
	 */
	public Adapter createTurboDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.turbotransitionrules.SeqRule <em>Seq Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.turbotransitionrules.SeqRule
	 * @generated
	 */
	public Adapter createSeqRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule <em>Turbo Local State Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule
	 * @generated
	 */
	public Adapter createTurboLocalStateRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.turbotransitionrules.TurboCallRule <em>Turbo Call Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboCallRule
	 * @generated
	 */
	public Adapter createTurboCallRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.turbotransitionrules.TurboReturnRule <em>Turbo Return Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.turbotransitionrules.TurboReturnRule
	 * @generated
	 */
	public Adapter createTurboReturnRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.turbotransitionrules.TryCatchRule <em>Try Catch Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.turbotransitionrules.TryCatchRule
	 * @generated
	 */
	public Adapter createTryCatchRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.turbotransitionrules.IterateRule <em>Iterate Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.turbotransitionrules.IterateRule
	 * @generated
	 */
	public Adapter createIterateRuleAdapter() {
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

} //TurbotransitionrulesAdapterFactory

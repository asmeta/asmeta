/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.derivedtransitionrules.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.derivedtransitionrules.*;
import asmeta.transitionrules.derivedtransitionrules.BasicDerivedRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.derivedtransitionrules.DerivedRule;
import asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage;
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule;
import asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule;
import asmeta.transitionrules.derivedtransitionrules.TurboDerivedRule;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage
 * @generated
 */
public class DerivedtransitionrulesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DerivedtransitionrulesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DerivedtransitionrulesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DerivedtransitionrulesPackage.eINSTANCE;
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
	protected DerivedtransitionrulesSwitch<Adapter> modelSwitch =
		new DerivedtransitionrulesSwitch<Adapter>() {
			@Override
			public Adapter caseRecursiveWhileRule(RecursiveWhileRule object) {
				return createRecursiveWhileRuleAdapter();
			}
			@Override
			public Adapter caseIterativeWhileRule(IterativeWhileRule object) {
				return createIterativeWhileRuleAdapter();
			}
			@Override
			public Adapter caseDerivedRule(DerivedRule object) {
				return createDerivedRuleAdapter();
			}
			@Override
			public Adapter caseCaseRule(CaseRule object) {
				return createCaseRuleAdapter();
			}
			@Override
			public Adapter caseBasicDerivedRule(BasicDerivedRule object) {
				return createBasicDerivedRuleAdapter();
			}
			@Override
			public Adapter caseTurboDerivedRule(TurboDerivedRule object) {
				return createTurboDerivedRuleAdapter();
			}
			@Override
			public Adapter caseRule(Rule object) {
				return createRuleAdapter();
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
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule <em>Recursive While Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule
	 * @generated
	 */
	public Adapter createRecursiveWhileRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule <em>Iterative While Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule
	 * @generated
	 */
	public Adapter createIterativeWhileRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.derivedtransitionrules.DerivedRule <em>Derived Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.derivedtransitionrules.DerivedRule
	 * @generated
	 */
	public Adapter createDerivedRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.derivedtransitionrules.CaseRule <em>Case Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.derivedtransitionrules.CaseRule
	 * @generated
	 */
	public Adapter createCaseRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.derivedtransitionrules.BasicDerivedRule <em>Basic Derived Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.derivedtransitionrules.BasicDerivedRule
	 * @generated
	 */
	public Adapter createBasicDerivedRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.transitionrules.derivedtransitionrules.TurboDerivedRule <em>Turbo Derived Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.transitionrules.derivedtransitionrules.TurboDerivedRule
	 * @generated
	 */
	public Adapter createTurboDerivedRuleAdapter() {
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

} //DerivedtransitionrulesAdapterFactory

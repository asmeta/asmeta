/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms.util;

import asmeta.terms.basicterms.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import asmeta.terms.basicterms.BasicTerm;
import asmeta.terms.basicterms.BasictermsPackage;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.CollectionTerm;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.ExtendedTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.RuleAsTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.terms.basicterms.BasictermsPackage
 * @generated
 */
public class BasictermsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BasictermsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasictermsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = BasictermsPackage.eINSTANCE;
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
	protected BasictermsSwitch<Adapter> modelSwitch =
		new BasictermsSwitch<Adapter>() {
			@Override
			public Adapter caseVariableTerm(VariableTerm object) {
				return createVariableTermAdapter();
			}
			@Override
			public Adapter caseUndefTerm(UndefTerm object) {
				return createUndefTermAdapter();
			}
			@Override
			public Adapter caseTupleTerm(TupleTerm object) {
				return createTupleTermAdapter();
			}
			@Override
			public Adapter caseSetTerm(SetTerm object) {
				return createSetTermAdapter();
			}
			@Override
			public Adapter caseRuleAsTerm(RuleAsTerm object) {
				return createRuleAsTermAdapter();
			}
			@Override
			public Adapter caseLocationTerm(LocationTerm object) {
				return createLocationTermAdapter();
			}
			@Override
			public Adapter caseFunctionTerm(FunctionTerm object) {
				return createFunctionTermAdapter();
			}
			@Override
			public Adapter caseExtendedTerm(ExtendedTerm object) {
				return createExtendedTermAdapter();
			}
			@Override
			public Adapter caseDomainTerm(DomainTerm object) {
				return createDomainTermAdapter();
			}
			@Override
			public Adapter caseConstantTerm(ConstantTerm object) {
				return createConstantTermAdapter();
			}
			@Override
			public Adapter caseCollectionTerm(CollectionTerm object) {
				return createCollectionTermAdapter();
			}
			@Override
			public Adapter caseBooleanTerm(BooleanTerm object) {
				return createBooleanTermAdapter();
			}
			@Override
			public Adapter caseBasicTerm(BasicTerm object) {
				return createBasicTermAdapter();
			}
			@Override
			public Adapter caseTerm(Term object) {
				return createTermAdapter();
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
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.VariableTerm <em>Variable Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.VariableTerm
	 * @generated
	 */
	public Adapter createVariableTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.UndefTerm <em>Undef Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.UndefTerm
	 * @generated
	 */
	public Adapter createUndefTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.TupleTerm <em>Tuple Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.TupleTerm
	 * @generated
	 */
	public Adapter createTupleTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.SetTerm <em>Set Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.SetTerm
	 * @generated
	 */
	public Adapter createSetTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.RuleAsTerm <em>Rule As Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.RuleAsTerm
	 * @generated
	 */
	public Adapter createRuleAsTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.LocationTerm <em>Location Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.LocationTerm
	 * @generated
	 */
	public Adapter createLocationTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.FunctionTerm <em>Function Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.FunctionTerm
	 * @generated
	 */
	public Adapter createFunctionTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.ExtendedTerm <em>Extended Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.ExtendedTerm
	 * @generated
	 */
	public Adapter createExtendedTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.DomainTerm <em>Domain Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.DomainTerm
	 * @generated
	 */
	public Adapter createDomainTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.ConstantTerm <em>Constant Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.ConstantTerm
	 * @generated
	 */
	public Adapter createConstantTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.CollectionTerm <em>Collection Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.CollectionTerm
	 * @generated
	 */
	public Adapter createCollectionTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.BooleanTerm <em>Boolean Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.BooleanTerm
	 * @generated
	 */
	public Adapter createBooleanTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.BasicTerm <em>Basic Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.BasicTerm
	 * @generated
	 */
	public Adapter createBasicTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.basicterms.Term <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.basicterms.Term
	 * @generated
	 */
	public Adapter createTermAdapter() {
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

} //BasictermsAdapterFactory

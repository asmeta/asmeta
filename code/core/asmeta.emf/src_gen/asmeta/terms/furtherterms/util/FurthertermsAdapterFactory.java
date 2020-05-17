/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import asmeta.terms.basicterms.BasicTerm;
import asmeta.terms.basicterms.CollectionTerm;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.ExtendedTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.furtherterms.*;
import asmeta.terms.furtherterms.BagCt;
import asmeta.terms.furtherterms.BagTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.CharTerm;
import asmeta.terms.furtherterms.ComplexTerm;
import asmeta.terms.furtherterms.ComprehensionTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.ExistUniqueTerm;
import asmeta.terms.furtherterms.FiniteQuantificationTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.FurthertermsPackage;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.MapCt;
import asmeta.terms.furtherterms.MapTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.SequenceCt;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.SetCt;
import asmeta.terms.furtherterms.StringTerm;
import asmeta.terms.furtherterms.VariableBindingTerm;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.terms.furtherterms.FurthertermsPackage
 * @generated
 */
public class FurthertermsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static FurthertermsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FurthertermsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = FurthertermsPackage.eINSTANCE;
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
	protected FurthertermsSwitch<Adapter> modelSwitch =
		new FurthertermsSwitch<Adapter>() {
			@Override
			public Adapter caseIntegerTerm(IntegerTerm object) {
				return createIntegerTermAdapter();
			}
			@Override
			public Adapter caseNaturalTerm(NaturalTerm object) {
				return createNaturalTermAdapter();
			}
			@Override
			public Adapter caseVariableBindingTerm(VariableBindingTerm object) {
				return createVariableBindingTermAdapter();
			}
			@Override
			public Adapter caseStringTerm(StringTerm object) {
				return createStringTermAdapter();
			}
			@Override
			public Adapter caseSetCt(SetCt object) {
				return createSetCtAdapter();
			}
			@Override
			public Adapter caseSequenceTerm(SequenceTerm object) {
				return createSequenceTermAdapter();
			}
			@Override
			public Adapter caseSequenceCt(SequenceCt object) {
				return createSequenceCtAdapter();
			}
			@Override
			public Adapter caseRealTerm(RealTerm object) {
				return createRealTermAdapter();
			}
			@Override
			public Adapter caseMapTerm(MapTerm object) {
				return createMapTermAdapter();
			}
			@Override
			public Adapter caseMapCt(MapCt object) {
				return createMapCtAdapter();
			}
			@Override
			public Adapter caseLetTerm(LetTerm object) {
				return createLetTermAdapter();
			}
			@Override
			public Adapter caseForallTerm(ForallTerm object) {
				return createForallTermAdapter();
			}
			@Override
			public Adapter caseFiniteQuantificationTerm(FiniteQuantificationTerm object) {
				return createFiniteQuantificationTermAdapter();
			}
			@Override
			public Adapter caseExistUniqueTerm(ExistUniqueTerm object) {
				return createExistUniqueTermAdapter();
			}
			@Override
			public Adapter caseExistTerm(ExistTerm object) {
				return createExistTermAdapter();
			}
			@Override
			public Adapter caseEnumTerm(EnumTerm object) {
				return createEnumTermAdapter();
			}
			@Override
			public Adapter caseConditionalTerm(ConditionalTerm object) {
				return createConditionalTermAdapter();
			}
			@Override
			public Adapter caseComprehensionTerm(ComprehensionTerm object) {
				return createComprehensionTermAdapter();
			}
			@Override
			public Adapter caseComplexTerm(ComplexTerm object) {
				return createComplexTermAdapter();
			}
			@Override
			public Adapter caseCharTerm(CharTerm object) {
				return createCharTermAdapter();
			}
			@Override
			public Adapter caseCaseTerm(CaseTerm object) {
				return createCaseTermAdapter();
			}
			@Override
			public Adapter caseBagTerm(BagTerm object) {
				return createBagTermAdapter();
			}
			@Override
			public Adapter caseBagCt(BagCt object) {
				return createBagCtAdapter();
			}
			@Override
			public Adapter caseTerm(Term object) {
				return createTermAdapter();
			}
			@Override
			public Adapter caseBasicTerm(BasicTerm object) {
				return createBasicTermAdapter();
			}
			@Override
			public Adapter caseConstantTerm(ConstantTerm object) {
				return createConstantTermAdapter();
			}
			@Override
			public Adapter caseExtendedTerm(ExtendedTerm object) {
				return createExtendedTermAdapter();
			}
			@Override
			public Adapter caseCollectionTerm(CollectionTerm object) {
				return createCollectionTermAdapter();
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
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.IntegerTerm <em>Integer Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.IntegerTerm
	 * @generated
	 */
	public Adapter createIntegerTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.NaturalTerm <em>Natural Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.NaturalTerm
	 * @generated
	 */
	public Adapter createNaturalTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.VariableBindingTerm <em>Variable Binding Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.VariableBindingTerm
	 * @generated
	 */
	public Adapter createVariableBindingTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.StringTerm <em>String Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.StringTerm
	 * @generated
	 */
	public Adapter createStringTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.SetCt <em>Set Ct</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.SetCt
	 * @generated
	 */
	public Adapter createSetCtAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.SequenceTerm <em>Sequence Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.SequenceTerm
	 * @generated
	 */
	public Adapter createSequenceTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.SequenceCt <em>Sequence Ct</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.SequenceCt
	 * @generated
	 */
	public Adapter createSequenceCtAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.RealTerm <em>Real Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.RealTerm
	 * @generated
	 */
	public Adapter createRealTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.MapTerm <em>Map Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.MapTerm
	 * @generated
	 */
	public Adapter createMapTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.MapCt <em>Map Ct</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.MapCt
	 * @generated
	 */
	public Adapter createMapCtAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.LetTerm <em>Let Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.LetTerm
	 * @generated
	 */
	public Adapter createLetTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.ForallTerm <em>Forall Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.ForallTerm
	 * @generated
	 */
	public Adapter createForallTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.FiniteQuantificationTerm <em>Finite Quantification Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.FiniteQuantificationTerm
	 * @generated
	 */
	public Adapter createFiniteQuantificationTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.ExistUniqueTerm <em>Exist Unique Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.ExistUniqueTerm
	 * @generated
	 */
	public Adapter createExistUniqueTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.ExistTerm <em>Exist Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.ExistTerm
	 * @generated
	 */
	public Adapter createExistTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.EnumTerm <em>Enum Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.EnumTerm
	 * @generated
	 */
	public Adapter createEnumTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.ConditionalTerm <em>Conditional Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.ConditionalTerm
	 * @generated
	 */
	public Adapter createConditionalTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.ComprehensionTerm <em>Comprehension Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.ComprehensionTerm
	 * @generated
	 */
	public Adapter createComprehensionTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.ComplexTerm <em>Complex Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.ComplexTerm
	 * @generated
	 */
	public Adapter createComplexTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.CharTerm <em>Char Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.CharTerm
	 * @generated
	 */
	public Adapter createCharTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.CaseTerm <em>Case Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.CaseTerm
	 * @generated
	 */
	public Adapter createCaseTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.BagTerm <em>Bag Term</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.BagTerm
	 * @generated
	 */
	public Adapter createBagTermAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.terms.furtherterms.BagCt <em>Bag Ct</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.terms.furtherterms.BagCt
	 * @generated
	 */
	public Adapter createBagCtAdapter() {
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

} //FurthertermsAdapterFactory

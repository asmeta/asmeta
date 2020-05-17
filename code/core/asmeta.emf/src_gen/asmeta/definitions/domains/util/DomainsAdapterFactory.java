/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.Classifier;
import asmeta.definitions.domains.*;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.BasicTd;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.CharDomain;
import asmeta.definitions.domains.ComplexDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
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
import asmeta.definitions.domains.TypeDomain;
import asmeta.definitions.domains.UndefDomain;
import asmeta.structure.NamedElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.definitions.domains.DomainsPackage
 * @generated
 */
public class DomainsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DomainsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DomainsPackage.eINSTANCE;
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
	protected DomainsSwitch<Adapter> modelSwitch =
		new DomainsSwitch<Adapter>() {
			@Override
			public Adapter caseNaturalDomain(NaturalDomain object) {
				return createNaturalDomainAdapter();
			}
			@Override
			public Adapter caseUndefDomain(UndefDomain object) {
				return createUndefDomainAdapter();
			}
			@Override
			public Adapter caseTypeDomain(TypeDomain object) {
				return createTypeDomainAdapter();
			}
			@Override
			public Adapter caseStructuredTd(StructuredTd object) {
				return createStructuredTdAdapter();
			}
			@Override
			public Adapter caseStringDomain(StringDomain object) {
				return createStringDomainAdapter();
			}
			@Override
			public Adapter caseSequenceDomain(SequenceDomain object) {
				return createSequenceDomainAdapter();
			}
			@Override
			public Adapter caseRuleDomain(RuleDomain object) {
				return createRuleDomainAdapter();
			}
			@Override
			public Adapter caseReserveDomain(ReserveDomain object) {
				return createReserveDomainAdapter();
			}
			@Override
			public Adapter caseRealDomain(RealDomain object) {
				return createRealDomainAdapter();
			}
			@Override
			public Adapter caseProductDomain(ProductDomain object) {
				return createProductDomainAdapter();
			}
			@Override
			public Adapter casePowersetDomain(PowersetDomain object) {
				return createPowersetDomainAdapter();
			}
			@Override
			public Adapter caseMapDomain(MapDomain object) {
				return createMapDomainAdapter();
			}
			@Override
			public Adapter caseIntegerDomain(IntegerDomain object) {
				return createIntegerDomainAdapter();
			}
			@Override
			public Adapter caseEnumTd(EnumTd object) {
				return createEnumTdAdapter();
			}
			@Override
			public Adapter caseEnumElement(EnumElement object) {
				return createEnumElementAdapter();
			}
			@Override
			public Adapter caseDomain(Domain object) {
				return createDomainAdapter();
			}
			@Override
			public Adapter caseConcreteDomain(ConcreteDomain object) {
				return createConcreteDomainAdapter();
			}
			@Override
			public Adapter caseComplexDomain(ComplexDomain object) {
				return createComplexDomainAdapter();
			}
			@Override
			public Adapter caseCharDomain(CharDomain object) {
				return createCharDomainAdapter();
			}
			@Override
			public Adapter caseBooleanDomain(BooleanDomain object) {
				return createBooleanDomainAdapter();
			}
			@Override
			public Adapter caseBasicTd(BasicTd object) {
				return createBasicTdAdapter();
			}
			@Override
			public Adapter caseBagDomain(BagDomain object) {
				return createBagDomainAdapter();
			}
			@Override
			public Adapter caseAnyDomain(AnyDomain object) {
				return createAnyDomainAdapter();
			}
			@Override
			public Adapter caseAgentDomain(AgentDomain object) {
				return createAgentDomainAdapter();
			}
			@Override
			public Adapter caseAbstractTd(AbstractTd object) {
				return createAbstractTdAdapter();
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
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.NaturalDomain <em>Natural Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.NaturalDomain
	 * @generated
	 */
	public Adapter createNaturalDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.UndefDomain <em>Undef Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.UndefDomain
	 * @generated
	 */
	public Adapter createUndefDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.TypeDomain <em>Type Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.TypeDomain
	 * @generated
	 */
	public Adapter createTypeDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.StructuredTd <em>Structured Td</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.StructuredTd
	 * @generated
	 */
	public Adapter createStructuredTdAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.StringDomain <em>String Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.StringDomain
	 * @generated
	 */
	public Adapter createStringDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.SequenceDomain <em>Sequence Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.SequenceDomain
	 * @generated
	 */
	public Adapter createSequenceDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.RuleDomain <em>Rule Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.RuleDomain
	 * @generated
	 */
	public Adapter createRuleDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.ReserveDomain <em>Reserve Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.ReserveDomain
	 * @generated
	 */
	public Adapter createReserveDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.RealDomain <em>Real Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.RealDomain
	 * @generated
	 */
	public Adapter createRealDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.ProductDomain <em>Product Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.ProductDomain
	 * @generated
	 */
	public Adapter createProductDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.PowersetDomain <em>Powerset Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.PowersetDomain
	 * @generated
	 */
	public Adapter createPowersetDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.MapDomain <em>Map Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.MapDomain
	 * @generated
	 */
	public Adapter createMapDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.IntegerDomain <em>Integer Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.IntegerDomain
	 * @generated
	 */
	public Adapter createIntegerDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.EnumTd <em>Enum Td</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.EnumTd
	 * @generated
	 */
	public Adapter createEnumTdAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.EnumElement <em>Enum Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.EnumElement
	 * @generated
	 */
	public Adapter createEnumElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.Domain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.Domain
	 * @generated
	 */
	public Adapter createDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.ConcreteDomain <em>Concrete Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.ConcreteDomain
	 * @generated
	 */
	public Adapter createConcreteDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.ComplexDomain <em>Complex Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.ComplexDomain
	 * @generated
	 */
	public Adapter createComplexDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.CharDomain <em>Char Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.CharDomain
	 * @generated
	 */
	public Adapter createCharDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.BooleanDomain <em>Boolean Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.BooleanDomain
	 * @generated
	 */
	public Adapter createBooleanDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.BasicTd <em>Basic Td</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.BasicTd
	 * @generated
	 */
	public Adapter createBasicTdAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.BagDomain <em>Bag Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.BagDomain
	 * @generated
	 */
	public Adapter createBagDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.AnyDomain <em>Any Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.AnyDomain
	 * @generated
	 */
	public Adapter createAnyDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.AgentDomain <em>Agent Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.AgentDomain
	 * @generated
	 */
	public Adapter createAgentDomainAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.definitions.domains.AbstractTd <em>Abstract Td</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.definitions.domains.AbstractTd
	 * @generated
	 */
	public Adapter createAbstractTdAdapter() {
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

} //DomainsAdapterFactory

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure.util;

import asmeta.structure.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
import asmeta.structure.Body;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.structure.ExportClause;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Header;
import asmeta.structure.ImportClause;
import asmeta.structure.Initialization;
import asmeta.structure.NamedElement;
import asmeta.structure.Signature;
import asmeta.structure.StructurePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.structure.StructurePackage
 * @generated
 */
public class StructureAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static StructurePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructureAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = StructurePackage.eINSTANCE;
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
	protected StructureSwitch<Adapter> modelSwitch =
		new StructureSwitch<Adapter>() {
			@Override
			public Adapter caseNamedElement(NamedElement object) {
				return createNamedElementAdapter();
			}
			@Override
			public Adapter caseAgentInitialization(AgentInitialization object) {
				return createAgentInitializationAdapter();
			}
			@Override
			public Adapter caseBody(Body object) {
				return createBodyAdapter();
			}
			@Override
			public Adapter caseFunctionInitialization(FunctionInitialization object) {
				return createFunctionInitializationAdapter();
			}
			@Override
			public Adapter caseDomainInitialization(DomainInitialization object) {
				return createDomainInitializationAdapter();
			}
			@Override
			public Adapter caseSignature(Signature object) {
				return createSignatureAdapter();
			}
			@Override
			public Adapter caseExportClause(ExportClause object) {
				return createExportClauseAdapter();
			}
			@Override
			public Adapter caseImportClause(ImportClause object) {
				return createImportClauseAdapter();
			}
			@Override
			public Adapter caseFunctionDefinition(FunctionDefinition object) {
				return createFunctionDefinitionAdapter();
			}
			@Override
			public Adapter caseDomainDefinition(DomainDefinition object) {
				return createDomainDefinitionAdapter();
			}
			@Override
			public Adapter caseInitialization(Initialization object) {
				return createInitializationAdapter();
			}
			@Override
			public Adapter caseHeader(Header object) {
				return createHeaderAdapter();
			}
			@Override
			public Adapter caseAsm(Asm object) {
				return createAsmAdapter();
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
	 * Creates a new adapter for an object of class '{@link asmeta.structure.AgentInitialization <em>Agent Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.AgentInitialization
	 * @generated
	 */
	public Adapter createAgentInitializationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.structure.Body <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.Body
	 * @generated
	 */
	public Adapter createBodyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.structure.FunctionInitialization <em>Function Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.FunctionInitialization
	 * @generated
	 */
	public Adapter createFunctionInitializationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.structure.DomainInitialization <em>Domain Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.DomainInitialization
	 * @generated
	 */
	public Adapter createDomainInitializationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.structure.Signature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.Signature
	 * @generated
	 */
	public Adapter createSignatureAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.structure.ExportClause <em>Export Clause</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.ExportClause
	 * @generated
	 */
	public Adapter createExportClauseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.structure.ImportClause <em>Import Clause</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.ImportClause
	 * @generated
	 */
	public Adapter createImportClauseAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.structure.FunctionDefinition <em>Function Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.FunctionDefinition
	 * @generated
	 */
	public Adapter createFunctionDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.structure.DomainDefinition <em>Domain Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.DomainDefinition
	 * @generated
	 */
	public Adapter createDomainDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.structure.Initialization <em>Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.Initialization
	 * @generated
	 */
	public Adapter createInitializationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.structure.Header <em>Header</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.Header
	 * @generated
	 */
	public Adapter createHeaderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link asmeta.structure.Asm <em>Asm</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see asmeta.structure.Asm
	 * @generated
	 */
	public Adapter createAsmAdapter() {
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

} //StructureAdapterFactory

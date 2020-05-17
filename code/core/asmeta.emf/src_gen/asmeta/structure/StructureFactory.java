/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.structure.StructurePackage
 * @generated
 */
public interface StructureFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StructureFactory eINSTANCE = asmeta.structure.impl.StructureFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Agent Initialization</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Agent Initialization</em>'.
	 * @generated
	 */
	AgentInitialization createAgentInitialization();

	/**
	 * Returns a new object of class '<em>Body</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Body</em>'.
	 * @generated
	 */
	Body createBody();

	/**
	 * Returns a new object of class '<em>Function Initialization</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Initialization</em>'.
	 * @generated
	 */
	FunctionInitialization createFunctionInitialization();

	/**
	 * Returns a new object of class '<em>Domain Initialization</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Domain Initialization</em>'.
	 * @generated
	 */
	DomainInitialization createDomainInitialization();

	/**
	 * Returns a new object of class '<em>Signature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Signature</em>'.
	 * @generated
	 */
	Signature createSignature();

	/**
	 * Returns a new object of class '<em>Export Clause</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Export Clause</em>'.
	 * @generated
	 */
	ExportClause createExportClause();

	/**
	 * Returns a new object of class '<em>Import Clause</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Import Clause</em>'.
	 * @generated
	 */
	ImportClause createImportClause();

	/**
	 * Returns a new object of class '<em>Function Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Definition</em>'.
	 * @generated
	 */
	FunctionDefinition createFunctionDefinition();

	/**
	 * Returns a new object of class '<em>Domain Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Domain Definition</em>'.
	 * @generated
	 */
	DomainDefinition createDomainDefinition();

	/**
	 * Returns a new object of class '<em>Initialization</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Initialization</em>'.
	 * @generated
	 */
	Initialization createInitialization();

	/**
	 * Returns a new object of class '<em>Header</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Header</em>'.
	 * @generated
	 */
	Header createHeader();

	/**
	 * Returns a new object of class '<em>Asm</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Asm</em>'.
	 * @generated
	 */
	Asm createAsm();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	StructurePackage getStructurePackage();

	Asm createAsm(String name, boolean isAsyncr);

} //StructureFactory

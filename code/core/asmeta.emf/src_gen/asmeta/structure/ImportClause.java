/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.Domain;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Import Clause</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.ImportClause#getImportedDomain <em>Imported Domain</em>}</li>
 *   <li>{@link asmeta.structure.ImportClause#getImportedFunction <em>Imported Function</em>}</li>
 *   <li>{@link asmeta.structure.ImportClause#getImportedRule <em>Imported Rule</em>}</li>
 *   <li>{@link asmeta.structure.ImportClause#getModuleName <em>Module Name</em>}</li>
 * </ul>
 *
 * @see asmeta.structure.StructurePackage#getImportClause()
 * @model
 * @generated
 */
public interface ImportClause extends EObject {
	/**
	 * Returns the value of the '<em><b>Imported Domain</b></em>' reference list.
	 * The list contents are of type {@link asmeta.definitions.domains.Domain}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imported Domain</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imported Domain</em>' reference list.
	 * @see asmeta.structure.StructurePackage#getImportClause_ImportedDomain()
	 * @model ordered="false"
	 * @generated
	 */
	EList<Domain> getImportedDomain();

	/**
	 * Returns the value of the '<em><b>Imported Function</b></em>' reference list.
	 * The list contents are of type {@link asmeta.definitions.Function}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imported Function</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imported Function</em>' reference list.
	 * @see asmeta.structure.StructurePackage#getImportClause_ImportedFunction()
	 * @model ordered="false"
	 * @generated
	 */
	EList<Function> getImportedFunction();

	/**
	 * Returns the value of the '<em><b>Imported Rule</b></em>' reference list.
	 * The list contents are of type {@link asmeta.definitions.RuleDeclaration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imported Rule</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imported Rule</em>' reference list.
	 * @see asmeta.structure.StructurePackage#getImportClause_ImportedRule()
	 * @model ordered="false"
	 * @generated
	 */
	EList<RuleDeclaration> getImportedRule();

	/**
	 * Returns the value of the '<em><b>Module Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Name</em>' attribute.
	 * @see #setModuleName(String)
	 * @see asmeta.structure.StructurePackage#getImportClause_ModuleName()
	 * @model unique="false" dataType="primitivetypes.String" required="true" ordered="false"
	 * @generated
	 */
	String getModuleName();

	/**
	 * Sets the value of the '{@link asmeta.structure.ImportClause#getModuleName <em>Module Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Name</em>' attribute.
	 * @see #getModuleName()
	 * @generated
	 */
	void setModuleName(String value);

} // ImportClause

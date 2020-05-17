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
 * A representation of the model object '<em><b>Export Clause</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.ExportClause#getExportedFunction <em>Exported Function</em>}</li>
 *   <li>{@link asmeta.structure.ExportClause#getExportedDomain <em>Exported Domain</em>}</li>
 *   <li>{@link asmeta.structure.ExportClause#getExportedRule <em>Exported Rule</em>}</li>
 * </ul>
 *
 * @see asmeta.structure.StructurePackage#getExportClause()
 * @model
 * @generated
 */
public interface ExportClause extends EObject {
	/**
	 * Returns the value of the '<em><b>Exported Function</b></em>' reference list.
	 * The list contents are of type {@link asmeta.definitions.Function}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exported Function</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exported Function</em>' reference list.
	 * @see asmeta.structure.StructurePackage#getExportClause_ExportedFunction()
	 * @model ordered="false"
	 * @generated
	 */
	EList<Function> getExportedFunction();

	/**
	 * Returns the value of the '<em><b>Exported Domain</b></em>' reference list.
	 * The list contents are of type {@link asmeta.definitions.domains.Domain}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exported Domain</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exported Domain</em>' reference list.
	 * @see asmeta.structure.StructurePackage#getExportClause_ExportedDomain()
	 * @model ordered="false"
	 * @generated
	 */
	EList<Domain> getExportedDomain();

	/**
	 * Returns the value of the '<em><b>Exported Rule</b></em>' reference list.
	 * The list contents are of type {@link asmeta.definitions.RuleDeclaration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exported Rule</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exported Rule</em>' reference list.
	 * @see asmeta.structure.StructurePackage#getExportClause_ExportedRule()
	 * @model ordered="false"
	 * @generated
	 */
	EList<RuleDeclaration> getExportedRule();

} // ExportClause

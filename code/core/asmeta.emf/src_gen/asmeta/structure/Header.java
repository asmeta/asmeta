/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Header</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.Header#getImportClause <em>Import Clause</em>}</li>
 *   <li>{@link asmeta.structure.Header#getSignature <em>Signature</em>}</li>
 *   <li>{@link asmeta.structure.Header#getExportClause <em>Export Clause</em>}</li>
 *   <li>{@link asmeta.structure.Header#getAsm <em>Asm</em>}</li>
 * </ul>
 *
 * @see asmeta.structure.StructurePackage#getHeader()
 * @model
 * @generated
 */
public interface Header extends EObject {
	/**
	 * Returns the value of the '<em><b>Import Clause</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.structure.ImportClause}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Import Clause</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Import Clause</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getHeader_ImportClause()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ImportClause> getImportClause();

	/**
	 * Returns the value of the '<em><b>Signature</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Signature#getHeaderSection <em>Header Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signature</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signature</em>' containment reference.
	 * @see #setSignature(Signature)
	 * @see asmeta.structure.StructurePackage#getHeader_Signature()
	 * @see asmeta.structure.Signature#getHeaderSection
	 * @model opposite="headerSection" containment="true" required="true" ordered="false"
	 * @generated
	 */
	Signature getSignature();

	/**
	 * Sets the value of the '{@link asmeta.structure.Header#getSignature <em>Signature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Signature</em>' containment reference.
	 * @see #getSignature()
	 * @generated
	 */
	void setSignature(Signature value);

	/**
	 * Returns the value of the '<em><b>Export Clause</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Export Clause</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Export Clause</em>' containment reference.
	 * @see #setExportClause(ExportClause)
	 * @see asmeta.structure.StructurePackage#getHeader_ExportClause()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	ExportClause getExportClause();

	/**
	 * Sets the value of the '{@link asmeta.structure.Header#getExportClause <em>Export Clause</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Export Clause</em>' containment reference.
	 * @see #getExportClause()
	 * @generated
	 */
	void setExportClause(ExportClause value);

	/**
	 * Returns the value of the '<em><b>Asm</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Asm#getHeaderSection <em>Header Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Asm</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Asm</em>' container reference.
	 * @see #setAsm(Asm)
	 * @see asmeta.structure.StructurePackage#getHeader_Asm()
	 * @see asmeta.structure.Asm#getHeaderSection
	 * @model opposite="headerSection" transient="false"
	 * @generated
	 */
	Asm getAsm();

	/**
	 * Sets the value of the '{@link asmeta.structure.Header#getAsm <em>Asm</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Asm</em>' container reference.
	 * @see #getAsm()
	 * @generated
	 */
	void setAsm(Asm value);

} // Header

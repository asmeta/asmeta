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
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.StructuredTd;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Signature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.Signature#getDomain <em>Domain</em>}</li>
 *   <li>{@link asmeta.structure.Signature#getFunction <em>Function</em>}</li>
 *   <li>{@link asmeta.structure.Signature#getHeaderSection <em>Header Section</em>}</li>
 *   <li>{@link asmeta.structure.Signature#getStructuredDomain <em>Structured Domain</em>}</li>
 * </ul>
 *
 * @see asmeta.structure.StructurePackage#getSignature()
 * @model
 * @generated
 */
public interface Signature extends EObject {
	/**
	 * Returns the value of the '<em><b>Domain</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.definitions.domains.Domain}.
	 * It is bidirectional and its opposite is '{@link asmeta.definitions.domains.Domain#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getSignature_Domain()
	 * @see asmeta.definitions.domains.Domain#getSignature
	 * @model opposite="signature" containment="true" ordered="false"
	 * @generated
	 */
	EList<Domain> getDomain();

	/**
	 * Returns the value of the '<em><b>Function</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.definitions.Function}.
	 * It is bidirectional and its opposite is '{@link asmeta.definitions.Function#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getSignature_Function()
	 * @see asmeta.definitions.Function#getSignature
	 * @model opposite="signature" containment="true" ordered="false"
	 * @generated
	 */
	EList<Function> getFunction();

	/**
	 * Returns the value of the '<em><b>Header Section</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Header#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Header Section</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header Section</em>' container reference.
	 * @see #setHeaderSection(Header)
	 * @see asmeta.structure.StructurePackage#getSignature_HeaderSection()
	 * @see asmeta.structure.Header#getSignature
	 * @model opposite="signature" required="true" transient="false"
	 * @generated
	 */
	Header getHeaderSection();

	/**
	 * Sets the value of the '{@link asmeta.structure.Signature#getHeaderSection <em>Header Section</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Section</em>' container reference.
	 * @see #getHeaderSection()
	 * @generated
	 */
	void setHeaderSection(Header value);

	/**
	 * Returns the value of the '<em><b>Structured Domain</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.definitions.domains.StructuredTd}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Structured Domain</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Structured Domain</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getSignature_StructuredDomain()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<StructuredTd> getStructuredDomain();

} // Signature

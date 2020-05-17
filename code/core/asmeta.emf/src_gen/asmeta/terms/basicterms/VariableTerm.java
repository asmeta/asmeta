/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms;

import asmeta.terms.furtherterms.FiniteQuantificationTerm;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.basicterms.VariableTerm#getFiniteQuantificationTerm <em>Finite Quantification Term</em>}</li>
 *   <li>{@link asmeta.terms.basicterms.VariableTerm#getName <em>Name</em>}</li>
 *   <li>{@link asmeta.terms.basicterms.VariableTerm#getKind <em>Kind</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.basicterms.BasictermsPackage#getVariableTerm()
 * @model
 * @generated
 */
public interface VariableTerm extends BasicTerm {
	/**
	 * Returns the value of the '<em><b>Finite Quantification Term</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link asmeta.terms.furtherterms.FiniteQuantificationTerm#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Finite Quantification Term</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Finite Quantification Term</em>' container reference.
	 * @see #setFiniteQuantificationTerm(FiniteQuantificationTerm)
	 * @see asmeta.terms.basicterms.BasictermsPackage#getVariableTerm_FiniteQuantificationTerm()
	 * @see asmeta.terms.furtherterms.FiniteQuantificationTerm#getVariable
	 * @model opposite="variable" transient="false" ordered="false"
	 * @generated
	 */
	FiniteQuantificationTerm getFiniteQuantificationTerm();

	/**
	 * Sets the value of the '{@link asmeta.terms.basicterms.VariableTerm#getFiniteQuantificationTerm <em>Finite Quantification Term</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Finite Quantification Term</em>' container reference.
	 * @see #getFiniteQuantificationTerm()
	 * @generated
	 */
	void setFiniteQuantificationTerm(FiniteQuantificationTerm value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see asmeta.terms.basicterms.BasictermsPackage#getVariableTerm_Name()
	 * @model unique="false" dataType="primitivetypes.String" required="true" ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link asmeta.terms.basicterms.VariableTerm#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link asmeta.terms.basicterms.VariableKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see asmeta.terms.basicterms.VariableKind
	 * @see #setKind(VariableKind)
	 * @see asmeta.terms.basicterms.BasictermsPackage#getVariableTerm_Kind()
	 * @model unique="false" required="true" ordered="false"
	 * @generated
	 */
	VariableKind getKind();

	/**
	 * Sets the value of the '{@link asmeta.terms.basicterms.VariableTerm#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see asmeta.terms.basicterms.VariableKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(VariableKind value);

} // VariableTerm

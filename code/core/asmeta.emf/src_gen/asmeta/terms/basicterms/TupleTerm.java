/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tuple Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.basicterms.TupleTerm#getArity <em>Arity</em>}</li>
 *   <li>{@link asmeta.terms.basicterms.TupleTerm#getTerms <em>Terms</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.basicterms.BasictermsPackage#getTupleTerm()
 * @model
 * @generated
 */
public interface TupleTerm extends ExtendedTerm {
	/**
	 * Returns the value of the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arity</em>' attribute.
	 * @see #setArity(Integer)
	 * @see asmeta.terms.basicterms.BasictermsPackage#getTupleTerm_Arity()
	 * @model unique="false" dataType="primitivetypes.Integer" required="true" ordered="false"
	 * @generated
	 */
	Integer getArity();

	/**
	 * Sets the value of the '{@link asmeta.terms.basicterms.TupleTerm#getArity <em>Arity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arity</em>' attribute.
	 * @see #getArity()
	 * @generated
	 */
	void setArity(Integer value);

	/**
	 * Returns the value of the '<em><b>Terms</b></em>' attribute list.
	 * The list contents are of type {@link asmeta.terms.basicterms.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Terms</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Terms</em>' attribute list.
	 * @see asmeta.terms.basicterms.BasictermsPackage#getTupleTerm_Terms()
	 * @model unique="false" dataType="asmeta.terms.basicterms.TermDT" lower="2"
	 * @generated
	 */
	EList<Term> getTerms();

} // TupleTerm

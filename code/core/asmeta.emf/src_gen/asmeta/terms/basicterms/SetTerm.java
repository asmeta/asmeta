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
 * A representation of the model object '<em><b>Set Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.basicterms.SetTerm#getTerm <em>Term</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.basicterms.BasictermsPackage#getSetTerm()
 * @model
 * @generated
 */
public interface SetTerm extends CollectionTerm {
	/**
	 * Returns the value of the '<em><b>Term</b></em>' reference list.
	 * The list contents are of type {@link asmeta.terms.basicterms.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Term</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Term</em>' reference list.
	 * @see asmeta.terms.basicterms.BasictermsPackage#getSetTerm_Term()
	 * @model ordered="false"
	 * @generated
	 */
	EList<Term> getTerm();

} // SetTerm

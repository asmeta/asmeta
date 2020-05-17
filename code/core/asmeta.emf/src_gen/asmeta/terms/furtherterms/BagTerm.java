/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms;

import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.CollectionTerm;
import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bag Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.furtherterms.BagTerm#getTerm <em>Term</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.furtherterms.FurthertermsPackage#getBagTerm()
 * @model
 * @generated
 */
public interface BagTerm extends CollectionTerm {
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
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getBagTerm_Term()
	 * @model ordered="false"
	 * @generated
	 */
	EList<Term> getTerm();

} // BagTerm

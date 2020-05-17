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
 * A representation of the model object '<em><b>Sequence Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.furtherterms.SequenceTerm#getTerms <em>Terms</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.furtherterms.FurthertermsPackage#getSequenceTerm()
 * @model
 * @generated
 */
public interface SequenceTerm extends CollectionTerm {
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
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getSequenceTerm_Terms()
	 * @model unique="false" dataType="asmeta.terms.basicterms.TermDT"
	 * @generated
	 */
	EList<Term> getTerms();

} // SequenceTerm

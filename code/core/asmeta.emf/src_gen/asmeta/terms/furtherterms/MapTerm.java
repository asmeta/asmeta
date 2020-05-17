/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms;

import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.CollectionTerm;
import asmeta.terms.basicterms.TupleTerm;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Map Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.furtherterms.MapTerm#getPair <em>Pair</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.furtherterms.FurthertermsPackage#getMapTerm()
 * @model
 * @generated
 */
public interface MapTerm extends CollectionTerm {
	/**
	 * Returns the value of the '<em><b>Pair</b></em>' reference list.
	 * The list contents are of type {@link asmeta.terms.basicterms.TupleTerm}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pair</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pair</em>' reference list.
	 * @see asmeta.terms.furtherterms.FurthertermsPackage#getMapTerm_Pair()
	 * @model ordered="false"
	 * @generated
	 */
	EList<TupleTerm> getPair();

} // MapTerm

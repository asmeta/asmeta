/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Collection Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.basicterms.CollectionTerm#getSize <em>Size</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.basicterms.BasictermsPackage#getCollectionTerm()
 * @model abstract="true"
 * @generated
 */
public interface CollectionTerm extends ExtendedTerm {
	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see #setSize(Integer)
	 * @see asmeta.terms.basicterms.BasictermsPackage#getCollectionTerm_Size()
	 * @model unique="false" dataType="primitivetypes.Integer" required="true" ordered="false"
	 * @generated
	 */
	Integer getSize();

	/**
	 * Sets the value of the '{@link asmeta.terms.basicterms.CollectionTerm#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' attribute.
	 * @see #getSize()
	 * @generated
	 */
	void setSize(Integer value);

} // CollectionTerm

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constant Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.basicterms.ConstantTerm#getSymbol <em>Symbol</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.basicterms.BasictermsPackage#getConstantTerm()
 * @model abstract="true"
 * @generated
 */
public interface ConstantTerm extends BasicTerm {
	/**
	 * Returns the value of the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Symbol</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symbol</em>' attribute.
	 * @see #setSymbol(String)
	 * @see asmeta.terms.basicterms.BasictermsPackage#getConstantTerm_Symbol()
	 * @model unique="false" dataType="primitivetypes.String" required="true" ordered="false"
	 * @generated
	 */
	String getSymbol();

	/**
	 * Sets the value of the '{@link asmeta.terms.basicterms.ConstantTerm#getSymbol <em>Symbol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Symbol</em>' attribute.
	 * @see #getSymbol()
	 * @generated
	 */
	void setSymbol(String value);

} // ConstantTerm

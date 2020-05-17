/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions;

import asmeta.terms.basicterms.Term;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Temporal Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.TemporalProperty#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.DefinitionsPackage#getTemporalProperty()
 * @model abstract="true"
 * @generated
 */
public interface TemporalProperty extends Property {

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(Term)
	 * @see asmeta.definitions.DefinitionsPackage#getTemporalProperty_Body()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Term getBody();

	/**
	 * Sets the value of the '{@link asmeta.definitions.TemporalProperty#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Term value);
} // TemporalProperty

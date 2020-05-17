/**
 */
package asmeta.definitions;

import asmeta.terms.basicterms.Term;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Invar Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.InvarConstraint#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.DefinitionsPackage#getInvarConstraint()
 * @model
 * @generated
 */
public interface InvarConstraint extends Classifier {

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
	 * @see asmeta.definitions.DefinitionsPackage#getInvarConstraint_Body()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Term getBody();

	/**
	 * Sets the value of the '{@link asmeta.definitions.InvarConstraint#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Term value);
} // InvarConstraint

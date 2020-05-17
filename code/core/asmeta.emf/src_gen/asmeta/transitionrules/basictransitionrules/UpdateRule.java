/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules;

import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Update Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.UpdateRule#getUpdatingTerm <em>Updating Term</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.UpdateRule#getLocation <em>Location</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getUpdateRule()
 * @model
 * @generated
 */
public interface UpdateRule extends BasicRule {
	/**
	 * Returns the value of the '<em><b>Updating Term</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Updating Term</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Updating Term</em>' reference.
	 * @see #setUpdatingTerm(Term)
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getUpdateRule_UpdatingTerm()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Term getUpdatingTerm();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.basictransitionrules.UpdateRule#getUpdatingTerm <em>Updating Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Updating Term</em>' reference.
	 * @see #getUpdatingTerm()
	 * @generated
	 */
	void setUpdatingTerm(Term value);

	/**
	 * Returns the value of the '<em><b>Location</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' reference.
	 * @see #setLocation(Term)
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getUpdateRule_Location()
	 * @model ordered="false"
	 * @generated
	 */
	Term getLocation();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.basictransitionrules.UpdateRule#getLocation <em>Location</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' reference.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(Term value);

} // UpdateRule

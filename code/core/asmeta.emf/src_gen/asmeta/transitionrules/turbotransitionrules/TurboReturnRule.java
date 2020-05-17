/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules;

import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Turbo Return Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.TurboReturnRule#getLocation <em>Location</em>}</li>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.TurboReturnRule#getUpdateRule <em>Update Rule</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTurboReturnRule()
 * @model
 * @generated
 */
public interface TurboReturnRule extends TurboRule {
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
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTurboReturnRule_Location()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Term getLocation();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.turbotransitionrules.TurboReturnRule#getLocation <em>Location</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' reference.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(Term value);

	/**
	 * Returns the value of the '<em><b>Update Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Update Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Update Rule</em>' reference.
	 * @see #setUpdateRule(TurboCallRule)
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTurboReturnRule_UpdateRule()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	TurboCallRule getUpdateRule();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.turbotransitionrules.TurboReturnRule#getUpdateRule <em>Update Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Update Rule</em>' reference.
	 * @see #getUpdateRule()
	 * @generated
	 */
	void setUpdateRule(TurboCallRule value);

} // TurboReturnRule

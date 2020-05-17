/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules;

import org.eclipse.emf.common.util.EList;

import asmeta.definitions.LocalFunction;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Turbo Local State Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule#getInit <em>Init</em>}</li>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule#getBody <em>Body</em>}</li>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule#getLocalFunction <em>Local Function</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTurboLocalStateRule()
 * @model
 * @generated
 */
public interface TurboLocalStateRule extends TurboRule {
	/**
	 * Returns the value of the '<em><b>Init</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.transitionrules.basictransitionrules.Rule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init</em>' containment reference list.
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTurboLocalStateRule_Init()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Rule> getInit();

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(Rule)
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTurboLocalStateRule_Body()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Rule getBody();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.turbotransitionrules.TurboLocalStateRule#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Rule value);

	/**
	 * Returns the value of the '<em><b>Local Function</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.definitions.LocalFunction}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local Function</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Function</em>' containment reference list.
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTurboLocalStateRule_LocalFunction()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<LocalFunction> getLocalFunction();

} // TurboLocalStateRule

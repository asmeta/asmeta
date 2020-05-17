/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules;

import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.Domain;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Turbo Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.TurboDeclaration#getResultType <em>Result Type</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTurboDeclaration()
 * @model
 * @generated
 */
public interface TurboDeclaration extends RuleDeclaration {
	/**
	 * Returns the value of the '<em><b>Result Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Type</em>' reference.
	 * @see #setResultType(Domain)
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTurboDeclaration_ResultType()
	 * @model ordered="false"
	 * @generated
	 */
	Domain getResultType();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.turbotransitionrules.TurboDeclaration#getResultType <em>Result Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Type</em>' reference.
	 * @see #getResultType()
	 * @generated
	 */
	void setResultType(Domain value);

} // TurboDeclaration

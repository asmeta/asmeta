/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules;

import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Turbo Call Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.TurboCallRule#getCalledRule <em>Called Rule</em>}</li>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.TurboCallRule#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTurboCallRule()
 * @model
 * @generated
 */
public interface TurboCallRule extends TurboRule {
	/**
	 * Returns the value of the '<em><b>Called Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Called Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Called Rule</em>' reference.
	 * @see #setCalledRule(TurboDeclaration)
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTurboCallRule_CalledRule()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	TurboDeclaration getCalledRule();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.turbotransitionrules.TurboCallRule#getCalledRule <em>Called Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Called Rule</em>' reference.
	 * @see #getCalledRule()
	 * @generated
	 */
	void setCalledRule(TurboDeclaration value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' attribute list.
	 * The list contents are of type {@link asmeta.terms.basicterms.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' attribute list.
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTurboCallRule_Parameters()
	 * @model unique="false" dataType="asmeta.terms.basicterms.TermDT"
	 * @generated
	 */
	EList<Term> getParameters();

} // TurboCallRule

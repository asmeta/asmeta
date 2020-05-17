/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules;

import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Macro Call Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.MacroCallRule#getCalledMacro <em>Called Macro</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.MacroCallRule#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getMacroCallRule()
 * @model
 * @generated
 */
public interface MacroCallRule extends BasicRule {
	/**
	 * Returns the value of the '<em><b>Called Macro</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Called Macro</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Called Macro</em>' reference.
	 * @see #setCalledMacro(MacroDeclaration)
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getMacroCallRule_CalledMacro()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	MacroDeclaration getCalledMacro();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.basictransitionrules.MacroCallRule#getCalledMacro <em>Called Macro</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Called Macro</em>' reference.
	 * @see #getCalledMacro()
	 * @generated
	 */
	void setCalledMacro(MacroDeclaration value);

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
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getMacroCallRule_Parameters()
	 * @model unique="false" dataType="asmeta.terms.basicterms.TermDT"
	 * @generated
	 */
	EList<Term> getParameters();

} // MacroCallRule

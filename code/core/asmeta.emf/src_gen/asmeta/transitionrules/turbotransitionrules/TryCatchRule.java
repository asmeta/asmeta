/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules;

import org.eclipse.emf.common.util.EList;

import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Try Catch Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.TryCatchRule#getLocation <em>Location</em>}</li>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.TryCatchRule#getCatchRule <em>Catch Rule</em>}</li>
 *   <li>{@link asmeta.transitionrules.turbotransitionrules.TryCatchRule#getTryRule <em>Try Rule</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTryCatchRule()
 * @model
 * @generated
 */
public interface TryCatchRule extends TurboRule {
	/**
	 * Returns the value of the '<em><b>Location</b></em>' reference list.
	 * The list contents are of type {@link asmeta.terms.basicterms.Term}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' reference list.
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTryCatchRule_Location()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	EList<Term> getLocation();

	/**
	 * Returns the value of the '<em><b>Catch Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Catch Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catch Rule</em>' containment reference.
	 * @see #setCatchRule(Rule)
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTryCatchRule_CatchRule()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Rule getCatchRule();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.turbotransitionrules.TryCatchRule#getCatchRule <em>Catch Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Catch Rule</em>' containment reference.
	 * @see #getCatchRule()
	 * @generated
	 */
	void setCatchRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Try Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Try Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Try Rule</em>' containment reference.
	 * @see #setTryRule(Rule)
	 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage#getTryCatchRule_TryRule()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Rule getTryRule();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.turbotransitionrules.TryCatchRule#getTryRule <em>Try Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Try Rule</em>' containment reference.
	 * @see #getTryRule()
	 * @generated
	 */
	void setTryRule(Rule value);

} // TryCatchRule

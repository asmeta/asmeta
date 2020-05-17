/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules;

import org.eclipse.emf.common.util.EList;

import asmeta.definitions.domains.Domain;
import asmeta.terms.basicterms.VariableTerm;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extend Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.ExtendRule#getExtendedDomain <em>Extended Domain</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.ExtendRule#getBoundVar <em>Bound Var</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.ExtendRule#getDoRule <em>Do Rule</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getExtendRule()
 * @model
 * @generated
 */
public interface ExtendRule extends BasicRule {
	/**
	 * Returns the value of the '<em><b>Extended Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extended Domain</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended Domain</em>' reference.
	 * @see #setExtendedDomain(Domain)
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getExtendRule_ExtendedDomain()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Domain getExtendedDomain();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.basictransitionrules.ExtendRule#getExtendedDomain <em>Extended Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extended Domain</em>' reference.
	 * @see #getExtendedDomain()
	 * @generated
	 */
	void setExtendedDomain(Domain value);

	/**
	 * Returns the value of the '<em><b>Bound Var</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.terms.basicterms.VariableTerm}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bound Var</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bound Var</em>' containment reference list.
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getExtendRule_BoundVar()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	EList<VariableTerm> getBoundVar();

	/**
	 * Returns the value of the '<em><b>Do Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Do Rule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Do Rule</em>' containment reference.
	 * @see #setDoRule(Rule)
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getExtendRule_DoRule()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Rule getDoRule();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.basictransitionrules.ExtendRule#getDoRule <em>Do Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Do Rule</em>' containment reference.
	 * @see #getDoRule()
	 * @generated
	 */
	void setDoRule(Rule value);

} // ExtendRule

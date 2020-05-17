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
 * A representation of the model object '<em><b>Term As Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.TermAsRule#getTerm <em>Term</em>}</li>
 *   <li>{@link asmeta.transitionrules.basictransitionrules.TermAsRule#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getTermAsRule()
 * @model
 * @generated
 */
public interface TermAsRule extends Rule {
	/**
	 * Returns the value of the '<em><b>Term</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link asmeta.terms.basicterms.Term#getTermAsRule <em>Term As Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Term</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Term</em>' reference.
	 * @see #setTerm(Term)
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getTermAsRule_Term()
	 * @see asmeta.terms.basicterms.Term#getTermAsRule
	 * @model opposite="termAsRule" required="true" ordered="false"
	 * @generated
	 */
	Term getTerm();

	/**
	 * Sets the value of the '{@link asmeta.transitionrules.basictransitionrules.TermAsRule#getTerm <em>Term</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Term</em>' reference.
	 * @see #getTerm()
	 * @generated
	 */
	void setTerm(Term value);

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
	 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage#getTermAsRule_Parameters()
	 * @model unique="false" dataType="asmeta.terms.basicterms.TermDT"
	 * @generated
	 */
	EList<Term> getParameters();

} // TermAsRule

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.domains.Domain;
import asmeta.transitionrules.basictransitionrules.TermAsRule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.terms.basicterms.Term#getDomain <em>Domain</em>}</li>
 *   <li>{@link asmeta.terms.basicterms.Term#getTermAsRule <em>Term As Rule</em>}</li>
 * </ul>
 *
 * @see asmeta.terms.basicterms.BasictermsPackage#getTerm()
 * @model abstract="true"
 * @generated
 */
public interface Term extends EObject {
	/**
	 * Returns the value of the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' reference.
	 * @see #setDomain(Domain)
	 * @see asmeta.terms.basicterms.BasictermsPackage#getTerm_Domain()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Domain getDomain();

	/**
	 * Sets the value of the '{@link asmeta.terms.basicterms.Term#getDomain <em>Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' reference.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(Domain value);

	/**
	 * Returns the value of the '<em><b>Term As Rule</b></em>' reference list.
	 * The list contents are of type {@link asmeta.transitionrules.basictransitionrules.TermAsRule}.
	 * It is bidirectional and its opposite is '{@link asmeta.transitionrules.basictransitionrules.TermAsRule#getTerm <em>Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Term As Rule</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Term As Rule</em>' reference list.
	 * @see asmeta.terms.basicterms.BasictermsPackage#getTerm_TermAsRule()
	 * @see asmeta.transitionrules.basictransitionrules.TermAsRule#getTerm
	 * @model opposite="term" ordered="false"
	 * @generated
	 */
	EList<TermAsRule> getTermAsRule();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void compatible();

} // Term

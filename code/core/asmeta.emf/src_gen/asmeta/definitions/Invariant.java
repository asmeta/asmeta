/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions;

import org.eclipse.emf.common.util.EList;

import asmeta.definitions.domains.Domain;
import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Invariant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.Invariant#getConstrainedDomain <em>Constrained Domain</em>}</li>
 *   <li>{@link asmeta.definitions.Invariant#getConstrainedRule <em>Constrained Rule</em>}</li>
 *   <li>{@link asmeta.definitions.Invariant#getConstrainedFunction <em>Constrained Function</em>}</li>
 *   <li>{@link asmeta.definitions.Invariant#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.DefinitionsPackage#getInvariant()
 * @model
 * @generated
 */
public interface Invariant extends Property {
	/**
	 * Returns the value of the '<em><b>Constrained Domain</b></em>' reference list.
	 * The list contents are of type {@link asmeta.definitions.domains.Domain}.
	 * It is bidirectional and its opposite is '{@link asmeta.definitions.domains.Domain#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constrained Domain</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constrained Domain</em>' reference list.
	 * @see asmeta.definitions.DefinitionsPackage#getInvariant_ConstrainedDomain()
	 * @see asmeta.definitions.domains.Domain#getConstraint
	 * @model opposite="constraint" ordered="false"
	 * @generated
	 */
	EList<Domain> getConstrainedDomain();

	/**
	 * Returns the value of the '<em><b>Constrained Rule</b></em>' reference list.
	 * The list contents are of type {@link asmeta.definitions.RuleDeclaration}.
	 * It is bidirectional and its opposite is '{@link asmeta.definitions.RuleDeclaration#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constrained Rule</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constrained Rule</em>' reference list.
	 * @see asmeta.definitions.DefinitionsPackage#getInvariant_ConstrainedRule()
	 * @see asmeta.definitions.RuleDeclaration#getConstraint
	 * @model opposite="constraint" ordered="false"
	 * @generated
	 */
	EList<RuleDeclaration> getConstrainedRule();

	/**
	 * Returns the value of the '<em><b>Constrained Function</b></em>' reference list.
	 * The list contents are of type {@link asmeta.definitions.Function}.
	 * It is bidirectional and its opposite is '{@link asmeta.definitions.Function#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constrained Function</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constrained Function</em>' reference list.
	 * @see asmeta.definitions.DefinitionsPackage#getInvariant_ConstrainedFunction()
	 * @see asmeta.definitions.Function#getConstraint
	 * @model opposite="constraint" ordered="false"
	 * @generated
	 */
	EList<Function> getConstrainedFunction();

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(Term)
	 * @see asmeta.definitions.DefinitionsPackage#getInvariant_Body()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Term getBody();

	/**
	 * Sets the value of the '{@link asmeta.definitions.Invariant#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Term value);

} // Invariant

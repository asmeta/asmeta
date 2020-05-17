/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains;

import org.eclipse.emf.common.util.EList;

import asmeta.definitions.Classifier;
import asmeta.definitions.Invariant;
import asmeta.structure.Signature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.domains.Domain#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link asmeta.definitions.domains.Domain#getSignature <em>Signature</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.domains.DomainsPackage#getDomain()
 * @model abstract="true"
 * @generated
 */
public interface Domain extends Classifier {
	/**
	 * Returns the value of the '<em><b>Constraint</b></em>' reference list.
	 * The list contents are of type {@link asmeta.definitions.Invariant}.
	 * It is bidirectional and its opposite is '{@link asmeta.definitions.Invariant#getConstrainedDomain <em>Constrained Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint</em>' reference list.
	 * @see asmeta.definitions.domains.DomainsPackage#getDomain_Constraint()
	 * @see asmeta.definitions.Invariant#getConstrainedDomain
	 * @model opposite="constrainedDomain" ordered="false"
	 * @generated
	 */
	EList<Invariant> getConstraint();

	/**
	 * Returns the value of the '<em><b>Signature</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Signature#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signature</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signature</em>' container reference.
	 * @see #setSignature(Signature)
	 * @see asmeta.definitions.domains.DomainsPackage#getDomain_Signature()
	 * @see asmeta.structure.Signature#getDomain
	 * @model opposite="domain" transient="false"
	 * @generated
	 */
	Signature getSignature();

	/**
	 * Sets the value of the '{@link asmeta.definitions.domains.Domain#getSignature <em>Signature</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Signature</em>' container reference.
	 * @see #getSignature()
	 * @generated
	 */
	void setSignature(Signature value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void compatible();

} // Domain

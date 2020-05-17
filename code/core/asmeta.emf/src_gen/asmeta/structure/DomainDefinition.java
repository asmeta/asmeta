/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure;

import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.domains.ConcreteDomain;
import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.DomainDefinition#getBody <em>Body</em>}</li>
 *   <li>{@link asmeta.structure.DomainDefinition#getDefinedDomain <em>Defined Domain</em>}</li>
 * </ul>
 *
 * @see asmeta.structure.StructurePackage#getDomainDefinition()
 * @model
 * @generated
 */
public interface DomainDefinition extends EObject {
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
	 * @see asmeta.structure.StructurePackage#getDomainDefinition_Body()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Term getBody();

	/**
	 * Sets the value of the '{@link asmeta.structure.DomainDefinition#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Term value);

	/**
	 * Returns the value of the '<em><b>Defined Domain</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link asmeta.definitions.domains.ConcreteDomain#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Defined Domain</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Defined Domain</em>' reference.
	 * @see #setDefinedDomain(ConcreteDomain)
	 * @see asmeta.structure.StructurePackage#getDomainDefinition_DefinedDomain()
	 * @see asmeta.definitions.domains.ConcreteDomain#getDefinition
	 * @model opposite="definition" required="true" ordered="false"
	 * @generated
	 */
	ConcreteDomain getDefinedDomain();

	/**
	 * Sets the value of the '{@link asmeta.structure.DomainDefinition#getDefinedDomain <em>Defined Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defined Domain</em>' reference.
	 * @see #getDefinedDomain()
	 * @generated
	 */
	void setDefinedDomain(ConcreteDomain value);

} // DomainDefinition

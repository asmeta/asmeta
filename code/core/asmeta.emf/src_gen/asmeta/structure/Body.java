/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.FairnessConstraint;
import asmeta.definitions.InvarConstraint;
import asmeta.definitions.Property;
import asmeta.definitions.RuleDeclaration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Body</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.Body#getFunctionDefinition <em>Function Definition</em>}</li>
 *   <li>{@link asmeta.structure.Body#getProperty <em>Property</em>}</li>
 *   <li>{@link asmeta.structure.Body#getDomainDefinition <em>Domain Definition</em>}</li>
 *   <li>{@link asmeta.structure.Body#getRuleDeclaration <em>Rule Declaration</em>}</li>
 *   <li>{@link asmeta.structure.Body#getAsm <em>Asm</em>}</li>
 *   <li>{@link asmeta.structure.Body#getFairnessConstraint <em>Fairness Constraint</em>}</li>
 *   <li>{@link asmeta.structure.Body#getInvariantConstraint <em>Invariant Constraint</em>}</li>
 * </ul>
 *
 * @see asmeta.structure.StructurePackage#getBody()
 * @model
 * @generated
 */
public interface Body extends EObject {
	/**
	 * Returns the value of the '<em><b>Function Definition</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.structure.FunctionDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function Definition</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function Definition</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getBody_FunctionDefinition()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<FunctionDefinition> getFunctionDefinition();

	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.definitions.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getBody_Property()
	 * @model containment="true" derived="true" ordered="false"
	 * @generated
	 */
	EList<Property> getProperty();

	/**
	 * Returns the value of the '<em><b>Domain Definition</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.structure.DomainDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Definition</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Definition</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getBody_DomainDefinition()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<DomainDefinition> getDomainDefinition();

	/**
	 * Returns the value of the '<em><b>Rule Declaration</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.definitions.RuleDeclaration}.
	 * It is bidirectional and its opposite is '{@link asmeta.definitions.RuleDeclaration#getAsmBody <em>Asm Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Declaration</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Declaration</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getBody_RuleDeclaration()
	 * @see asmeta.definitions.RuleDeclaration#getAsmBody
	 * @model opposite="asmBody" containment="true" ordered="false"
	 * @generated
	 */
	EList<RuleDeclaration> getRuleDeclaration();

	/**
	 * Returns the value of the '<em><b>Asm</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Asm#getBodySection <em>Body Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Asm</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Asm</em>' container reference.
	 * @see #setAsm(Asm)
	 * @see asmeta.structure.StructurePackage#getBody_Asm()
	 * @see asmeta.structure.Asm#getBodySection
	 * @model opposite="bodySection" transient="false"
	 * @generated
	 */
	Asm getAsm();

	/**
	 * Sets the value of the '{@link asmeta.structure.Body#getAsm <em>Asm</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Asm</em>' container reference.
	 * @see #getAsm()
	 * @generated
	 */
	void setAsm(Asm value);

	/**
	 * Returns the value of the '<em><b>Fairness Constraint</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.definitions.FairnessConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fairness Constraint</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fairness Constraint</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getBody_FairnessConstraint()
	 * @model containment="true" derived="true" ordered="false"
	 * @generated
	 */
	EList<FairnessConstraint> getFairnessConstraint();

	/**
	 * Returns the value of the '<em><b>Invariant Constraint</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.definitions.InvarConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Invariant Constraint</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invariant Constraint</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getBody_InvariantConstraint()
	 * @model containment="true" derived="true" ordered="false"
	 * @generated
	 */
	EList<InvarConstraint> getInvariantConstraint();

} // Body

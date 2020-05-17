/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions;

import org.eclipse.emf.common.util.EList;

import asmeta.structure.Body;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.RuleDeclaration#getVariable <em>Variable</em>}</li>
 *   <li>{@link asmeta.definitions.RuleDeclaration#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link asmeta.definitions.RuleDeclaration#getRuleBody <em>Rule Body</em>}</li>
 *   <li>{@link asmeta.definitions.RuleDeclaration#getAsmBody <em>Asm Body</em>}</li>
 *   <li>{@link asmeta.definitions.RuleDeclaration#getArity <em>Arity</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.DefinitionsPackage#getRuleDeclaration()
 * @model abstract="true"
 * @generated
 */
public interface RuleDeclaration extends Classifier {
	/**
	 * Returns the value of the '<em><b>Variable</b></em>' reference list.
	 * The list contents are of type {@link asmeta.terms.basicterms.VariableTerm}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variable</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' reference list.
	 * @see asmeta.definitions.DefinitionsPackage#getRuleDeclaration_Variable()
	 * @model
	 * @generated
	 */
	EList<VariableTerm> getVariable();

	/**
	 * Returns the value of the '<em><b>Constraint</b></em>' reference list.
	 * The list contents are of type {@link asmeta.definitions.Invariant}.
	 * It is bidirectional and its opposite is '{@link asmeta.definitions.Invariant#getConstrainedRule <em>Constrained Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint</em>' reference list.
	 * @see asmeta.definitions.DefinitionsPackage#getRuleDeclaration_Constraint()
	 * @see asmeta.definitions.Invariant#getConstrainedRule
	 * @model opposite="constrainedRule" ordered="false"
	 * @generated
	 */
	EList<Invariant> getConstraint();

	/**
	 * Returns the value of the '<em><b>Rule Body</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Body</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Body</em>' reference.
	 * @see #setRuleBody(Rule)
	 * @see asmeta.definitions.DefinitionsPackage#getRuleDeclaration_RuleBody()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Rule getRuleBody();

	/**
	 * Sets the value of the '{@link asmeta.definitions.RuleDeclaration#getRuleBody <em>Rule Body</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Body</em>' reference.
	 * @see #getRuleBody()
	 * @generated
	 */
	void setRuleBody(Rule value);

	/**
	 * Returns the value of the '<em><b>Asm Body</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Body#getRuleDeclaration <em>Rule Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Asm Body</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Asm Body</em>' container reference.
	 * @see #setAsmBody(Body)
	 * @see asmeta.definitions.DefinitionsPackage#getRuleDeclaration_AsmBody()
	 * @see asmeta.structure.Body#getRuleDeclaration
	 * @model opposite="ruleDeclaration" required="true" transient="false" ordered="false"
	 * @generated
	 */
	Body getAsmBody();

	/**
	 * Sets the value of the '{@link asmeta.definitions.RuleDeclaration#getAsmBody <em>Asm Body</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Asm Body</em>' container reference.
	 * @see #getAsmBody()
	 * @generated
	 */
	void setAsmBody(Body value);

	/**
	 * Returns the value of the '<em><b>Arity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arity</em>' attribute.
	 * @see #setArity(Integer)
	 * @see asmeta.definitions.DefinitionsPackage#getRuleDeclaration_Arity()
	 * @model unique="false" dataType="primitivetypes.Integer" required="true" ordered="false"
	 * @generated
	 */
	Integer getArity();

	/**
	 * Sets the value of the '{@link asmeta.definitions.RuleDeclaration#getArity <em>Arity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arity</em>' attribute.
	 * @see #getArity()
	 * @generated
	 */
	void setArity(Integer value);

} // RuleDeclaration

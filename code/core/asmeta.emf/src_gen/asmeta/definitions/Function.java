/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions;

import org.eclipse.emf.common.util.EList;

import asmeta.definitions.domains.Domain;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.Signature;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.Function#getDomain <em>Domain</em>}</li>
 *   <li>{@link asmeta.definitions.Function#getCodomain <em>Codomain</em>}</li>
 *   <li>{@link asmeta.definitions.Function#getDefinition <em>Definition</em>}</li>
 *   <li>{@link asmeta.definitions.Function#getConstraint <em>Constraint</em>}</li>
 *   <li>{@link asmeta.definitions.Function#getArity <em>Arity</em>}</li>
 *   <li>{@link asmeta.definitions.Function#getSignature <em>Signature</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.DefinitionsPackage#getFunction()
 * @model abstract="true"
 * @generated
 */
public interface Function extends Classifier {
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
	 * @see asmeta.definitions.DefinitionsPackage#getFunction_Domain()
	 * @model ordered="false"
	 * @generated
	 */
	Domain getDomain();

	/**
	 * Sets the value of the '{@link asmeta.definitions.Function#getDomain <em>Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' reference.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(Domain value);

	/**
	 * Returns the value of the '<em><b>Codomain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Codomain</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Codomain</em>' reference.
	 * @see #setCodomain(Domain)
	 * @see asmeta.definitions.DefinitionsPackage#getFunction_Codomain()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	Domain getCodomain();

	/**
	 * Sets the value of the '{@link asmeta.definitions.Function#getCodomain <em>Codomain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Codomain</em>' reference.
	 * @see #getCodomain()
	 * @generated
	 */
	void setCodomain(Domain value);

	/**
	 * Returns the value of the '<em><b>Definition</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.FunctionDefinition#getDefinedFunction <em>Defined Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Definition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Definition</em>' reference.
	 * @see #setDefinition(FunctionDefinition)
	 * @see asmeta.definitions.DefinitionsPackage#getFunction_Definition()
	 * @see asmeta.structure.FunctionDefinition#getDefinedFunction
	 * @model opposite="definedFunction" ordered="false"
	 * @generated
	 */
	FunctionDefinition getDefinition();

	/**
	 * Sets the value of the '{@link asmeta.definitions.Function#getDefinition <em>Definition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Definition</em>' reference.
	 * @see #getDefinition()
	 * @generated
	 */
	void setDefinition(FunctionDefinition value);

	/**
	 * Returns the value of the '<em><b>Constraint</b></em>' reference list.
	 * The list contents are of type {@link asmeta.definitions.Invariant}.
	 * It is bidirectional and its opposite is '{@link asmeta.definitions.Invariant#getConstrainedFunction <em>Constrained Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint</em>' reference list.
	 * @see asmeta.definitions.DefinitionsPackage#getFunction_Constraint()
	 * @see asmeta.definitions.Invariant#getConstrainedFunction
	 * @model opposite="constrainedFunction" ordered="false"
	 * @generated
	 */
	EList<Invariant> getConstraint();

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
	 * @see asmeta.definitions.DefinitionsPackage#getFunction_Arity()
	 * @model unique="false" dataType="primitivetypes.Integer" required="true" ordered="false"
	 * @generated
	 */
	Integer getArity();

	/**
	 * Sets the value of the '{@link asmeta.definitions.Function#getArity <em>Arity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arity</em>' attribute.
	 * @see #getArity()
	 * @generated
	 */
	void setArity(Integer value);

	/**
	 * Returns the value of the '<em><b>Signature</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Signature#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signature</em>' container reference.
	 * @see #setSignature(Signature)
	 * @see asmeta.definitions.DefinitionsPackage#getFunction_Signature()
	 * @see asmeta.structure.Signature#getFunction
	 * @model opposite="function" transient="false"
	 * @generated
	 */
	Signature getSignature();

	/**
	 * Sets the value of the '{@link asmeta.definitions.Function#getSignature <em>Signature</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Signature</em>' container reference.
	 * @see #getSignature()
	 * @generated
	 */
	void setSignature(Signature value);

} // Function

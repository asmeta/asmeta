/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure;

import org.eclipse.emf.common.util.EList;

import asmeta.transitionrules.basictransitionrules.MacroDeclaration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Asm</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.Asm#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link asmeta.structure.Asm#getDefaultInitialState <em>Default Initial State</em>}</li>
 *   <li>{@link asmeta.structure.Asm#getBodySection <em>Body Section</em>}</li>
 *   <li>{@link asmeta.structure.Asm#getHeaderSection <em>Header Section</em>}</li>
 *   <li>{@link asmeta.structure.Asm#getMainrule <em>Mainrule</em>}</li>
 *   <li>{@link asmeta.structure.Asm#getIsAsynchr <em>Is Asynchr</em>}</li>
 * </ul>
 *
 * @see asmeta.structure.StructurePackage#getAsm()
 * @model
 * @generated
 */
public interface Asm extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Initial State</b></em>' containment reference list.
	 * The list contents are of type {@link asmeta.structure.Initialization}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial State</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial State</em>' containment reference list.
	 * @see asmeta.structure.StructurePackage#getAsm_InitialState()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Initialization> getInitialState();

	/**
	 * Returns the value of the '<em><b>Default Initial State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Initialization#getAsm <em>Asm</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Initial State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Initial State</em>' reference.
	 * @see #setDefaultInitialState(Initialization)
	 * @see asmeta.structure.StructurePackage#getAsm_DefaultInitialState()
	 * @see asmeta.structure.Initialization#getAsm
	 * @model opposite="asm" ordered="false"
	 * @generated
	 */
	Initialization getDefaultInitialState();

	/**
	 * Sets the value of the '{@link asmeta.structure.Asm#getDefaultInitialState <em>Default Initial State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Initial State</em>' reference.
	 * @see #getDefaultInitialState()
	 * @generated
	 */
	void setDefaultInitialState(Initialization value);

	/**
	 * Returns the value of the '<em><b>Body Section</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Body#getAsm <em>Asm</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body Section</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body Section</em>' containment reference.
	 * @see #setBodySection(Body)
	 * @see asmeta.structure.StructurePackage#getAsm_BodySection()
	 * @see asmeta.structure.Body#getAsm
	 * @model opposite="asm" containment="true" required="true" ordered="false"
	 * @generated
	 */
	Body getBodySection();

	/**
	 * Sets the value of the '{@link asmeta.structure.Asm#getBodySection <em>Body Section</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body Section</em>' containment reference.
	 * @see #getBodySection()
	 * @generated
	 */
	void setBodySection(Body value);

	/**
	 * Returns the value of the '<em><b>Header Section</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link asmeta.structure.Header#getAsm <em>Asm</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Header Section</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header Section</em>' containment reference.
	 * @see #setHeaderSection(Header)
	 * @see asmeta.structure.StructurePackage#getAsm_HeaderSection()
	 * @see asmeta.structure.Header#getAsm
	 * @model opposite="asm" containment="true" required="true" ordered="false"
	 * @generated
	 */
	Header getHeaderSection();

	/**
	 * Sets the value of the '{@link asmeta.structure.Asm#getHeaderSection <em>Header Section</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Section</em>' containment reference.
	 * @see #getHeaderSection()
	 * @generated
	 */
	void setHeaderSection(Header value);

	/**
	 * Returns the value of the '<em><b>Mainrule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mainrule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mainrule</em>' reference.
	 * @see #setMainrule(MacroDeclaration)
	 * @see asmeta.structure.StructurePackage#getAsm_Mainrule()
	 * @model ordered="false"
	 * @generated
	 */
	MacroDeclaration getMainrule();

	/**
	 * Sets the value of the '{@link asmeta.structure.Asm#getMainrule <em>Mainrule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mainrule</em>' reference.
	 * @see #getMainrule()
	 * @generated
	 */
	void setMainrule(MacroDeclaration value);

	/**
	 * Returns the value of the '<em><b>Is Asynchr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Asynchr</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Asynchr</em>' attribute.
	 * @see #setIsAsynchr(Boolean)
	 * @see asmeta.structure.StructurePackage#getAsm_IsAsynchr()
	 * @model unique="false" dataType="primitivetypes.Boolean" required="true" ordered="false"
	 * @generated
	 */
	Boolean getIsAsynchr();

	/**
	 * Sets the value of the '{@link asmeta.structure.Asm#getIsAsynchr <em>Is Asynchr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Asynchr</em>' attribute.
	 * @see #getIsAsynchr()
	 * @generated
	 */
	void setIsAsynchr(Boolean value);

} // Asm

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.basictransitionrules;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.transitionrules.basictransitionrules.BasictransitionrulesPackage
 * @generated
 */
public interface BasictransitionrulesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BasictransitionrulesFactory eINSTANCE = asmeta.transitionrules.basictransitionrules.impl.BasictransitionrulesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Term As Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Term As Rule</em>'.
	 * @generated
	 */
	TermAsRule createTermAsRule();

	/**
	 * Returns a new object of class '<em>Choose Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Choose Rule</em>'.
	 * @generated
	 */
	ChooseRule createChooseRule();

	/**
	 * Returns a new object of class '<em>Macro Call Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Macro Call Rule</em>'.
	 * @generated
	 */
	MacroCallRule createMacroCallRule();

	/**
	 * Returns a new object of class '<em>Block Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Block Rule</em>'.
	 * @generated
	 */
	BlockRule createBlockRule();

	/**
	 * Returns a new object of class '<em>Conditional Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Conditional Rule</em>'.
	 * @generated
	 */
	ConditionalRule createConditionalRule();

	/**
	 * Returns a new object of class '<em>Forall Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Forall Rule</em>'.
	 * @generated
	 */
	ForallRule createForallRule();

	/**
	 * Returns a new object of class '<em>Let Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Let Rule</em>'.
	 * @generated
	 */
	LetRule createLetRule();

	/**
	 * Returns a new object of class '<em>Extend Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extend Rule</em>'.
	 * @generated
	 */
	ExtendRule createExtendRule();

	/**
	 * Returns a new object of class '<em>Update Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Update Rule</em>'.
	 * @generated
	 */
	UpdateRule createUpdateRule();

	/**
	 * Returns a new object of class '<em>Skip Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Skip Rule</em>'.
	 * @generated
	 */
	SkipRule createSkipRule();

	/**
	 * Returns a new object of class '<em>Macro Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Macro Declaration</em>'.
	 * @generated
	 */
	MacroDeclaration createMacroDeclaration();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	BasictransitionrulesPackage getBasictransitionrulesPackage();

} //BasictransitionrulesFactory

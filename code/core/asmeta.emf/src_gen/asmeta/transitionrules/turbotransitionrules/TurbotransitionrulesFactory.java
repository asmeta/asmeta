/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.turbotransitionrules;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesPackage
 * @generated
 */
public interface TurbotransitionrulesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TurbotransitionrulesFactory eINSTANCE = asmeta.transitionrules.turbotransitionrules.impl.TurbotransitionrulesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Turbo Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Turbo Declaration</em>'.
	 * @generated
	 */
	TurboDeclaration createTurboDeclaration();

	/**
	 * Returns a new object of class '<em>Seq Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Seq Rule</em>'.
	 * @generated
	 */
	SeqRule createSeqRule();

	/**
	 * Returns a new object of class '<em>Turbo Local State Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Turbo Local State Rule</em>'.
	 * @generated
	 */
	TurboLocalStateRule createTurboLocalStateRule();

	/**
	 * Returns a new object of class '<em>Turbo Call Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Turbo Call Rule</em>'.
	 * @generated
	 */
	TurboCallRule createTurboCallRule();

	/**
	 * Returns a new object of class '<em>Turbo Return Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Turbo Return Rule</em>'.
	 * @generated
	 */
	TurboReturnRule createTurboReturnRule();

	/**
	 * Returns a new object of class '<em>Try Catch Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Try Catch Rule</em>'.
	 * @generated
	 */
	TryCatchRule createTryCatchRule();

	/**
	 * Returns a new object of class '<em>Iterate Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Iterate Rule</em>'.
	 * @generated
	 */
	IterateRule createIterateRule();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TurbotransitionrulesPackage getTurbotransitionrulesPackage();

} //TurbotransitionrulesFactory

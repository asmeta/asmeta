/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.derivedtransitionrules;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage
 * @generated
 */
public interface DerivedtransitionrulesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DerivedtransitionrulesFactory eINSTANCE = asmeta.transitionrules.derivedtransitionrules.impl.DerivedtransitionrulesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Recursive While Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Recursive While Rule</em>'.
	 * @generated
	 */
	RecursiveWhileRule createRecursiveWhileRule();

	/**
	 * Returns a new object of class '<em>Iterative While Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Iterative While Rule</em>'.
	 * @generated
	 */
	IterativeWhileRule createIterativeWhileRule();

	/**
	 * Returns a new object of class '<em>Case Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Case Rule</em>'.
	 * @generated
	 */
	CaseRule createCaseRule();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DerivedtransitionrulesPackage getDerivedtransitionrulesPackage();

} //DerivedtransitionrulesFactory

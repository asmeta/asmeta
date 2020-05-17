/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.basicterms;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.terms.basicterms.BasictermsPackage
 * @generated
 */
public interface BasictermsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BasictermsFactory eINSTANCE = asmeta.terms.basicterms.impl.BasictermsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Variable Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable Term</em>'.
	 * @generated
	 */
	VariableTerm createVariableTerm();

	/**
	 * Returns a new object of class '<em>Undef Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Undef Term</em>'.
	 * @generated
	 */
	UndefTerm createUndefTerm();

	/**
	 * Returns a new object of class '<em>Tuple Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tuple Term</em>'.
	 * @generated
	 */
	TupleTerm createTupleTerm();

	/**
	 * Returns a new object of class '<em>Set Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Set Term</em>'.
	 * @generated
	 */
	SetTerm createSetTerm();

	/**
	 * Returns a new object of class '<em>Rule As Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule As Term</em>'.
	 * @generated
	 */
	RuleAsTerm createRuleAsTerm();

	/**
	 * Returns a new object of class '<em>Location Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Location Term</em>'.
	 * @generated
	 */
	LocationTerm createLocationTerm();

	/**
	 * Returns a new object of class '<em>Function Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Term</em>'.
	 * @generated
	 */
	FunctionTerm createFunctionTerm();

	/**
	 * Returns a new object of class '<em>Domain Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Domain Term</em>'.
	 * @generated
	 */
	DomainTerm createDomainTerm();

	/**
	 * Returns a new object of class '<em>Boolean Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Term</em>'.
	 * @generated
	 */
	BooleanTerm createBooleanTerm();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	BasictermsPackage getBasictermsPackage();

	/** create a boolean term of symbol */
	BooleanTerm createBooleanTerm(boolean symbol);

	/** create a variable term **/
	@Deprecated
	VariableTerm createVariableTerm(String name, VariableKind kind);

} //BasictermsFactory

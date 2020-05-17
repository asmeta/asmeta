/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions;

import org.eclipse.emf.ecore.EFactory;

import asmeta.definitions.domains.DomainsFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.definitions.DefinitionsPackage
 * @generated
 */
public interface DefinitionsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DefinitionsFactory eINSTANCE = asmeta.definitions.impl.DefinitionsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Local Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Local Function</em>'.
	 * @generated
	 */
	LocalFunction createLocalFunction();

	/**
	 * Returns a new object of class '<em>Controlled Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Controlled Function</em>'.
	 * @generated
	 */
	ControlledFunction createControlledFunction();

	/**
	 * Returns a new object of class '<em>Shared Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shared Function</em>'.
	 * @generated
	 */
	SharedFunction createSharedFunction();

	/**
	 * Returns a new object of class '<em>Monitored Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Monitored Function</em>'.
	 * @generated
	 */
	MonitoredFunction createMonitoredFunction();

	/**
	 * Returns a new object of class '<em>Out Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Out Function</em>'.
	 * @generated
	 */
	OutFunction createOutFunction();

	/**
	 * Returns a new object of class '<em>Static Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Static Function</em>'.
	 * @generated
	 */
	StaticFunction createStaticFunction();

	/**
	 * Returns a new object of class '<em>Derived Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Derived Function</em>'.
	 * @generated
	 */
	DerivedFunction createDerivedFunction();

	/**
	 * Returns a new object of class '<em>Invariant</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Invariant</em>'.
	 * @generated
	 */
	Invariant createInvariant();

	/**
	 * Returns a new object of class '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property</em>'.
	 * @generated
	 */
	Property createProperty();

	/**
	 * Returns a new object of class '<em>Ctl Spec</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ctl Spec</em>'.
	 * @generated
	 */
	CtlSpec createCtlSpec();

	/**
	 * Returns a new object of class '<em>Ltl Spec</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Ltl Spec</em>'.
	 * @generated
	 */
	LtlSpec createLtlSpec();

	/**
	 * Returns a new object of class '<em>Invar Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Invar Constraint</em>'.
	 * @generated
	 */
	InvarConstraint createInvarConstraint();

	/**
	 * Returns a new object of class '<em>Justice Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Justice Constraint</em>'.
	 * @generated
	 */
	JusticeConstraint createJusticeConstraint();

	/**
	 * Returns a new object of class '<em>Compassion Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compassion Constraint</em>'.
	 * @generated
	 */
	CompassionConstraint createCompassionConstraint();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DefinitionsPackage getDefinitionsPackage();

	
	/** restituisce una istanza del Pacakge contenuto in questo */
	DomainsFactory getDomains();

} //DefinitionsFactory

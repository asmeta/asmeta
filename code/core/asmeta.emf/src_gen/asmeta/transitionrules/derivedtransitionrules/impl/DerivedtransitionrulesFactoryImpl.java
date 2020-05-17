/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.transitionrules.derivedtransitionrules.impl;

import asmeta.transitionrules.derivedtransitionrules.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesFactory;
import asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesPackage;
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule;
import asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DerivedtransitionrulesFactoryImpl extends EFactoryImpl implements DerivedtransitionrulesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DerivedtransitionrulesFactory init() {
		try {
			DerivedtransitionrulesFactory theDerivedtransitionrulesFactory = (DerivedtransitionrulesFactory)EPackage.Registry.INSTANCE.getEFactory(DerivedtransitionrulesPackage.eNS_URI);
			if (theDerivedtransitionrulesFactory != null) {
				return theDerivedtransitionrulesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DerivedtransitionrulesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DerivedtransitionrulesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DerivedtransitionrulesPackage.RECURSIVE_WHILE_RULE: return createRecursiveWhileRule();
			case DerivedtransitionrulesPackage.ITERATIVE_WHILE_RULE: return createIterativeWhileRule();
			case DerivedtransitionrulesPackage.CASE_RULE: return createCaseRule();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RecursiveWhileRule createRecursiveWhileRule() {
		RecursiveWhileRuleImpl recursiveWhileRule = new RecursiveWhileRuleImpl();
		return recursiveWhileRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IterativeWhileRule createIterativeWhileRule() {
		IterativeWhileRuleImpl iterativeWhileRule = new IterativeWhileRuleImpl();
		return iterativeWhileRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CaseRule createCaseRule() {
		CaseRuleImpl caseRule = new CaseRuleImpl();
		return caseRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DerivedtransitionrulesPackage getDerivedtransitionrulesPackage() {
		return (DerivedtransitionrulesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DerivedtransitionrulesPackage getPackage() {
		return DerivedtransitionrulesPackage.eINSTANCE;
	}

} //DerivedtransitionrulesFactoryImpl

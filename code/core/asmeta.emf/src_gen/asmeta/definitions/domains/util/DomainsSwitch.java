/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.domains.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import asmeta.definitions.Classifier;
import asmeta.definitions.domains.*;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.BasicTd;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.CharDomain;
import asmeta.definitions.domains.ComplexDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.DomainsPackage;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.ReserveDomain;
import asmeta.definitions.domains.RuleDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StringDomain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.definitions.domains.TypeDomain;
import asmeta.definitions.domains.UndefDomain;
import asmeta.structure.NamedElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see asmeta.definitions.domains.DomainsPackage
 * @generated
 */
public class DomainsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DomainsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainsSwitch() {
		if (modelPackage == null) {
			modelPackage = DomainsPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case DomainsPackage.NATURAL_DOMAIN: {
				NaturalDomain naturalDomain = (NaturalDomain)theEObject;
				T result = caseNaturalDomain(naturalDomain);
				if (result == null) result = caseIntegerDomain(naturalDomain);
				if (result == null) result = caseRealDomain(naturalDomain);
				if (result == null) result = caseComplexDomain(naturalDomain);
				if (result == null) result = caseBasicTd(naturalDomain);
				if (result == null) result = caseTypeDomain(naturalDomain);
				if (result == null) result = caseDomain(naturalDomain);
				if (result == null) result = caseClassifier(naturalDomain);
				if (result == null) result = caseNamedElement(naturalDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.UNDEF_DOMAIN: {
				UndefDomain undefDomain = (UndefDomain)theEObject;
				T result = caseUndefDomain(undefDomain);
				if (result == null) result = caseBasicTd(undefDomain);
				if (result == null) result = caseTypeDomain(undefDomain);
				if (result == null) result = caseDomain(undefDomain);
				if (result == null) result = caseClassifier(undefDomain);
				if (result == null) result = caseNamedElement(undefDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.TYPE_DOMAIN: {
				TypeDomain typeDomain = (TypeDomain)theEObject;
				T result = caseTypeDomain(typeDomain);
				if (result == null) result = caseDomain(typeDomain);
				if (result == null) result = caseClassifier(typeDomain);
				if (result == null) result = caseNamedElement(typeDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.STRUCTURED_TD: {
				StructuredTd structuredTd = (StructuredTd)theEObject;
				T result = caseStructuredTd(structuredTd);
				if (result == null) result = caseTypeDomain(structuredTd);
				if (result == null) result = caseDomain(structuredTd);
				if (result == null) result = caseClassifier(structuredTd);
				if (result == null) result = caseNamedElement(structuredTd);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.STRING_DOMAIN: {
				StringDomain stringDomain = (StringDomain)theEObject;
				T result = caseStringDomain(stringDomain);
				if (result == null) result = caseBasicTd(stringDomain);
				if (result == null) result = caseTypeDomain(stringDomain);
				if (result == null) result = caseDomain(stringDomain);
				if (result == null) result = caseClassifier(stringDomain);
				if (result == null) result = caseNamedElement(stringDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.SEQUENCE_DOMAIN: {
				SequenceDomain sequenceDomain = (SequenceDomain)theEObject;
				T result = caseSequenceDomain(sequenceDomain);
				if (result == null) result = caseStructuredTd(sequenceDomain);
				if (result == null) result = caseTypeDomain(sequenceDomain);
				if (result == null) result = caseDomain(sequenceDomain);
				if (result == null) result = caseClassifier(sequenceDomain);
				if (result == null) result = caseNamedElement(sequenceDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.RULE_DOMAIN: {
				RuleDomain ruleDomain = (RuleDomain)theEObject;
				T result = caseRuleDomain(ruleDomain);
				if (result == null) result = caseStructuredTd(ruleDomain);
				if (result == null) result = caseTypeDomain(ruleDomain);
				if (result == null) result = caseDomain(ruleDomain);
				if (result == null) result = caseClassifier(ruleDomain);
				if (result == null) result = caseNamedElement(ruleDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.RESERVE_DOMAIN: {
				ReserveDomain reserveDomain = (ReserveDomain)theEObject;
				T result = caseReserveDomain(reserveDomain);
				if (result == null) result = caseAbstractTd(reserveDomain);
				if (result == null) result = caseTypeDomain(reserveDomain);
				if (result == null) result = caseDomain(reserveDomain);
				if (result == null) result = caseClassifier(reserveDomain);
				if (result == null) result = caseNamedElement(reserveDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.REAL_DOMAIN: {
				RealDomain realDomain = (RealDomain)theEObject;
				T result = caseRealDomain(realDomain);
				if (result == null) result = caseComplexDomain(realDomain);
				if (result == null) result = caseBasicTd(realDomain);
				if (result == null) result = caseTypeDomain(realDomain);
				if (result == null) result = caseDomain(realDomain);
				if (result == null) result = caseClassifier(realDomain);
				if (result == null) result = caseNamedElement(realDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.PRODUCT_DOMAIN: {
				ProductDomain productDomain = (ProductDomain)theEObject;
				T result = caseProductDomain(productDomain);
				if (result == null) result = caseStructuredTd(productDomain);
				if (result == null) result = caseTypeDomain(productDomain);
				if (result == null) result = caseDomain(productDomain);
				if (result == null) result = caseClassifier(productDomain);
				if (result == null) result = caseNamedElement(productDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.POWERSET_DOMAIN: {
				PowersetDomain powersetDomain = (PowersetDomain)theEObject;
				T result = casePowersetDomain(powersetDomain);
				if (result == null) result = caseStructuredTd(powersetDomain);
				if (result == null) result = caseTypeDomain(powersetDomain);
				if (result == null) result = caseDomain(powersetDomain);
				if (result == null) result = caseClassifier(powersetDomain);
				if (result == null) result = caseNamedElement(powersetDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.MAP_DOMAIN: {
				MapDomain mapDomain = (MapDomain)theEObject;
				T result = caseMapDomain(mapDomain);
				if (result == null) result = caseStructuredTd(mapDomain);
				if (result == null) result = caseTypeDomain(mapDomain);
				if (result == null) result = caseDomain(mapDomain);
				if (result == null) result = caseClassifier(mapDomain);
				if (result == null) result = caseNamedElement(mapDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.INTEGER_DOMAIN: {
				IntegerDomain integerDomain = (IntegerDomain)theEObject;
				T result = caseIntegerDomain(integerDomain);
				if (result == null) result = caseRealDomain(integerDomain);
				if (result == null) result = caseComplexDomain(integerDomain);
				if (result == null) result = caseBasicTd(integerDomain);
				if (result == null) result = caseTypeDomain(integerDomain);
				if (result == null) result = caseDomain(integerDomain);
				if (result == null) result = caseClassifier(integerDomain);
				if (result == null) result = caseNamedElement(integerDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.ENUM_TD: {
				EnumTd enumTd = (EnumTd)theEObject;
				T result = caseEnumTd(enumTd);
				if (result == null) result = caseTypeDomain(enumTd);
				if (result == null) result = caseDomain(enumTd);
				if (result == null) result = caseClassifier(enumTd);
				if (result == null) result = caseNamedElement(enumTd);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.ENUM_ELEMENT: {
				EnumElement enumElement = (EnumElement)theEObject;
				T result = caseEnumElement(enumElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.DOMAIN: {
				Domain domain = (Domain)theEObject;
				T result = caseDomain(domain);
				if (result == null) result = caseClassifier(domain);
				if (result == null) result = caseNamedElement(domain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.CONCRETE_DOMAIN: {
				ConcreteDomain concreteDomain = (ConcreteDomain)theEObject;
				T result = caseConcreteDomain(concreteDomain);
				if (result == null) result = caseDomain(concreteDomain);
				if (result == null) result = caseClassifier(concreteDomain);
				if (result == null) result = caseNamedElement(concreteDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.COMPLEX_DOMAIN: {
				ComplexDomain complexDomain = (ComplexDomain)theEObject;
				T result = caseComplexDomain(complexDomain);
				if (result == null) result = caseBasicTd(complexDomain);
				if (result == null) result = caseTypeDomain(complexDomain);
				if (result == null) result = caseDomain(complexDomain);
				if (result == null) result = caseClassifier(complexDomain);
				if (result == null) result = caseNamedElement(complexDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.CHAR_DOMAIN: {
				CharDomain charDomain = (CharDomain)theEObject;
				T result = caseCharDomain(charDomain);
				if (result == null) result = caseBasicTd(charDomain);
				if (result == null) result = caseTypeDomain(charDomain);
				if (result == null) result = caseDomain(charDomain);
				if (result == null) result = caseClassifier(charDomain);
				if (result == null) result = caseNamedElement(charDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.BOOLEAN_DOMAIN: {
				BooleanDomain booleanDomain = (BooleanDomain)theEObject;
				T result = caseBooleanDomain(booleanDomain);
				if (result == null) result = caseBasicTd(booleanDomain);
				if (result == null) result = caseTypeDomain(booleanDomain);
				if (result == null) result = caseDomain(booleanDomain);
				if (result == null) result = caseClassifier(booleanDomain);
				if (result == null) result = caseNamedElement(booleanDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.BASIC_TD: {
				BasicTd basicTd = (BasicTd)theEObject;
				T result = caseBasicTd(basicTd);
				if (result == null) result = caseTypeDomain(basicTd);
				if (result == null) result = caseDomain(basicTd);
				if (result == null) result = caseClassifier(basicTd);
				if (result == null) result = caseNamedElement(basicTd);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.BAG_DOMAIN: {
				BagDomain bagDomain = (BagDomain)theEObject;
				T result = caseBagDomain(bagDomain);
				if (result == null) result = caseStructuredTd(bagDomain);
				if (result == null) result = caseTypeDomain(bagDomain);
				if (result == null) result = caseDomain(bagDomain);
				if (result == null) result = caseClassifier(bagDomain);
				if (result == null) result = caseNamedElement(bagDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.ANY_DOMAIN: {
				AnyDomain anyDomain = (AnyDomain)theEObject;
				T result = caseAnyDomain(anyDomain);
				if (result == null) result = caseTypeDomain(anyDomain);
				if (result == null) result = caseDomain(anyDomain);
				if (result == null) result = caseClassifier(anyDomain);
				if (result == null) result = caseNamedElement(anyDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.AGENT_DOMAIN: {
				AgentDomain agentDomain = (AgentDomain)theEObject;
				T result = caseAgentDomain(agentDomain);
				if (result == null) result = caseAbstractTd(agentDomain);
				if (result == null) result = caseTypeDomain(agentDomain);
				if (result == null) result = caseDomain(agentDomain);
				if (result == null) result = caseClassifier(agentDomain);
				if (result == null) result = caseNamedElement(agentDomain);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomainsPackage.ABSTRACT_TD: {
				AbstractTd abstractTd = (AbstractTd)theEObject;
				T result = caseAbstractTd(abstractTd);
				if (result == null) result = caseTypeDomain(abstractTd);
				if (result == null) result = caseDomain(abstractTd);
				if (result == null) result = caseClassifier(abstractTd);
				if (result == null) result = caseNamedElement(abstractTd);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Natural Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Natural Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNaturalDomain(NaturalDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Undef Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Undef Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUndefDomain(UndefDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeDomain(TypeDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Structured Td</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Structured Td</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStructuredTd(StructuredTd object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringDomain(StringDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sequence Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sequence Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSequenceDomain(SequenceDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleDomain(RuleDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reserve Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reserve Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReserveDomain(ReserveDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Real Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Real Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRealDomain(RealDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Product Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Product Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProductDomain(ProductDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Powerset Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Powerset Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePowersetDomain(PowersetDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Map Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Map Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapDomain(MapDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerDomain(IntegerDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enum Td</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum Td</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumTd(EnumTd object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enum Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumElement(EnumElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDomain(Domain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Concrete Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Concrete Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConcreteDomain(ConcreteDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Complex Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Complex Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComplexDomain(ComplexDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Char Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Char Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCharDomain(CharDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanDomain(BooleanDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Basic Td</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Basic Td</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBasicTd(BasicTd object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bag Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bag Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBagDomain(BagDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Any Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Any Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnyDomain(AnyDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Agent Domain</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Agent Domain</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAgentDomain(AgentDomain object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Td</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Td</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractTd(AbstractTd object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedElement(NamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Classifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClassifier(Classifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //DomainsSwitch

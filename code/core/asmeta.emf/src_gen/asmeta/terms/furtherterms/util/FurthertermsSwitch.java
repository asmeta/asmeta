/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import asmeta.terms.basicterms.BasicTerm;
import asmeta.terms.basicterms.CollectionTerm;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.ExtendedTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.furtherterms.*;
import asmeta.terms.furtherterms.BagCt;
import asmeta.terms.furtherterms.BagTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.CharTerm;
import asmeta.terms.furtherterms.ComplexTerm;
import asmeta.terms.furtherterms.ComprehensionTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.ExistUniqueTerm;
import asmeta.terms.furtherterms.FiniteQuantificationTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.FurthertermsPackage;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.MapCt;
import asmeta.terms.furtherterms.MapTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.SequenceCt;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.SetCt;
import asmeta.terms.furtherterms.StringTerm;
import asmeta.terms.furtherterms.VariableBindingTerm;

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
 * @see asmeta.terms.furtherterms.FurthertermsPackage
 * @generated
 */
public class FurthertermsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static FurthertermsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FurthertermsSwitch() {
		if (modelPackage == null) {
			modelPackage = FurthertermsPackage.eINSTANCE;
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
			case FurthertermsPackage.INTEGER_TERM: {
				IntegerTerm integerTerm = (IntegerTerm)theEObject;
				T result = caseIntegerTerm(integerTerm);
				if (result == null) result = caseConstantTerm(integerTerm);
				if (result == null) result = caseBasicTerm(integerTerm);
				if (result == null) result = caseTerm(integerTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.NATURAL_TERM: {
				NaturalTerm naturalTerm = (NaturalTerm)theEObject;
				T result = caseNaturalTerm(naturalTerm);
				if (result == null) result = caseConstantTerm(naturalTerm);
				if (result == null) result = caseBasicTerm(naturalTerm);
				if (result == null) result = caseTerm(naturalTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.VARIABLE_BINDING_TERM: {
				VariableBindingTerm variableBindingTerm = (VariableBindingTerm)theEObject;
				T result = caseVariableBindingTerm(variableBindingTerm);
				if (result == null) result = caseExtendedTerm(variableBindingTerm);
				if (result == null) result = caseTerm(variableBindingTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.STRING_TERM: {
				StringTerm stringTerm = (StringTerm)theEObject;
				T result = caseStringTerm(stringTerm);
				if (result == null) result = caseConstantTerm(stringTerm);
				if (result == null) result = caseBasicTerm(stringTerm);
				if (result == null) result = caseTerm(stringTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.SET_CT: {
				SetCt setCt = (SetCt)theEObject;
				T result = caseSetCt(setCt);
				if (result == null) result = caseComprehensionTerm(setCt);
				if (result == null) result = caseVariableBindingTerm(setCt);
				if (result == null) result = caseExtendedTerm(setCt);
				if (result == null) result = caseTerm(setCt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.SEQUENCE_TERM: {
				SequenceTerm sequenceTerm = (SequenceTerm)theEObject;
				T result = caseSequenceTerm(sequenceTerm);
				if (result == null) result = caseCollectionTerm(sequenceTerm);
				if (result == null) result = caseExtendedTerm(sequenceTerm);
				if (result == null) result = caseTerm(sequenceTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.SEQUENCE_CT: {
				SequenceCt sequenceCt = (SequenceCt)theEObject;
				T result = caseSequenceCt(sequenceCt);
				if (result == null) result = caseComprehensionTerm(sequenceCt);
				if (result == null) result = caseVariableBindingTerm(sequenceCt);
				if (result == null) result = caseExtendedTerm(sequenceCt);
				if (result == null) result = caseTerm(sequenceCt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.REAL_TERM: {
				RealTerm realTerm = (RealTerm)theEObject;
				T result = caseRealTerm(realTerm);
				if (result == null) result = caseConstantTerm(realTerm);
				if (result == null) result = caseBasicTerm(realTerm);
				if (result == null) result = caseTerm(realTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.MAP_TERM: {
				MapTerm mapTerm = (MapTerm)theEObject;
				T result = caseMapTerm(mapTerm);
				if (result == null) result = caseCollectionTerm(mapTerm);
				if (result == null) result = caseExtendedTerm(mapTerm);
				if (result == null) result = caseTerm(mapTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.MAP_CT: {
				MapCt mapCt = (MapCt)theEObject;
				T result = caseMapCt(mapCt);
				if (result == null) result = caseComprehensionTerm(mapCt);
				if (result == null) result = caseVariableBindingTerm(mapCt);
				if (result == null) result = caseExtendedTerm(mapCt);
				if (result == null) result = caseTerm(mapCt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.LET_TERM: {
				LetTerm letTerm = (LetTerm)theEObject;
				T result = caseLetTerm(letTerm);
				if (result == null) result = caseVariableBindingTerm(letTerm);
				if (result == null) result = caseExtendedTerm(letTerm);
				if (result == null) result = caseTerm(letTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.FORALL_TERM: {
				ForallTerm forallTerm = (ForallTerm)theEObject;
				T result = caseForallTerm(forallTerm);
				if (result == null) result = caseFiniteQuantificationTerm(forallTerm);
				if (result == null) result = caseVariableBindingTerm(forallTerm);
				if (result == null) result = caseExtendedTerm(forallTerm);
				if (result == null) result = caseTerm(forallTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.FINITE_QUANTIFICATION_TERM: {
				FiniteQuantificationTerm finiteQuantificationTerm = (FiniteQuantificationTerm)theEObject;
				T result = caseFiniteQuantificationTerm(finiteQuantificationTerm);
				if (result == null) result = caseVariableBindingTerm(finiteQuantificationTerm);
				if (result == null) result = caseExtendedTerm(finiteQuantificationTerm);
				if (result == null) result = caseTerm(finiteQuantificationTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.EXIST_UNIQUE_TERM: {
				ExistUniqueTerm existUniqueTerm = (ExistUniqueTerm)theEObject;
				T result = caseExistUniqueTerm(existUniqueTerm);
				if (result == null) result = caseFiniteQuantificationTerm(existUniqueTerm);
				if (result == null) result = caseVariableBindingTerm(existUniqueTerm);
				if (result == null) result = caseExtendedTerm(existUniqueTerm);
				if (result == null) result = caseTerm(existUniqueTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.EXIST_TERM: {
				ExistTerm existTerm = (ExistTerm)theEObject;
				T result = caseExistTerm(existTerm);
				if (result == null) result = caseFiniteQuantificationTerm(existTerm);
				if (result == null) result = caseVariableBindingTerm(existTerm);
				if (result == null) result = caseExtendedTerm(existTerm);
				if (result == null) result = caseTerm(existTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.ENUM_TERM: {
				EnumTerm enumTerm = (EnumTerm)theEObject;
				T result = caseEnumTerm(enumTerm);
				if (result == null) result = caseConstantTerm(enumTerm);
				if (result == null) result = caseBasicTerm(enumTerm);
				if (result == null) result = caseTerm(enumTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.CONDITIONAL_TERM: {
				ConditionalTerm conditionalTerm = (ConditionalTerm)theEObject;
				T result = caseConditionalTerm(conditionalTerm);
				if (result == null) result = caseExtendedTerm(conditionalTerm);
				if (result == null) result = caseTerm(conditionalTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.COMPREHENSION_TERM: {
				ComprehensionTerm comprehensionTerm = (ComprehensionTerm)theEObject;
				T result = caseComprehensionTerm(comprehensionTerm);
				if (result == null) result = caseVariableBindingTerm(comprehensionTerm);
				if (result == null) result = caseExtendedTerm(comprehensionTerm);
				if (result == null) result = caseTerm(comprehensionTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.COMPLEX_TERM: {
				ComplexTerm complexTerm = (ComplexTerm)theEObject;
				T result = caseComplexTerm(complexTerm);
				if (result == null) result = caseConstantTerm(complexTerm);
				if (result == null) result = caseBasicTerm(complexTerm);
				if (result == null) result = caseTerm(complexTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.CHAR_TERM: {
				CharTerm charTerm = (CharTerm)theEObject;
				T result = caseCharTerm(charTerm);
				if (result == null) result = caseConstantTerm(charTerm);
				if (result == null) result = caseBasicTerm(charTerm);
				if (result == null) result = caseTerm(charTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.CASE_TERM: {
				CaseTerm caseTerm = (CaseTerm)theEObject;
				T result = caseCaseTerm(caseTerm);
				if (result == null) result = caseExtendedTerm(caseTerm);
				if (result == null) result = caseTerm(caseTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.BAG_TERM: {
				BagTerm bagTerm = (BagTerm)theEObject;
				T result = caseBagTerm(bagTerm);
				if (result == null) result = caseCollectionTerm(bagTerm);
				if (result == null) result = caseExtendedTerm(bagTerm);
				if (result == null) result = caseTerm(bagTerm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case FurthertermsPackage.BAG_CT: {
				BagCt bagCt = (BagCt)theEObject;
				T result = caseBagCt(bagCt);
				if (result == null) result = caseComprehensionTerm(bagCt);
				if (result == null) result = caseVariableBindingTerm(bagCt);
				if (result == null) result = caseExtendedTerm(bagCt);
				if (result == null) result = caseTerm(bagCt);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerTerm(IntegerTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Natural Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Natural Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNaturalTerm(NaturalTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable Binding Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable Binding Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariableBindingTerm(VariableBindingTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringTerm(StringTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Set Ct</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Set Ct</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSetCt(SetCt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sequence Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sequence Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSequenceTerm(SequenceTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sequence Ct</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sequence Ct</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSequenceCt(SequenceCt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Real Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Real Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRealTerm(RealTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Map Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Map Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapTerm(MapTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Map Ct</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Map Ct</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapCt(MapCt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Let Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Let Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLetTerm(LetTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Forall Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Forall Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseForallTerm(ForallTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Finite Quantification Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Finite Quantification Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFiniteQuantificationTerm(FiniteQuantificationTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Exist Unique Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Exist Unique Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExistUniqueTerm(ExistUniqueTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Exist Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Exist Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExistTerm(ExistTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enum Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumTerm(EnumTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Conditional Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Conditional Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConditionalTerm(ConditionalTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comprehension Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comprehension Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComprehensionTerm(ComprehensionTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Complex Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Complex Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComplexTerm(ComplexTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Char Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Char Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCharTerm(CharTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Case Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Case Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCaseTerm(CaseTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bag Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bag Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBagTerm(BagTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bag Ct</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bag Ct</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBagCt(BagCt object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTerm(Term object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Basic Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Basic Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBasicTerm(BasicTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Constant Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Constant Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConstantTerm(ConstantTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Extended Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Extended Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtendedTerm(ExtendedTerm object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Collection Term</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Collection Term</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCollectionTerm(CollectionTerm object) {
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

} //FurthertermsSwitch

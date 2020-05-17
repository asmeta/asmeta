/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.terms.furtherterms;

import java.util.List;

import org.eclipse.emf.ecore.EFactory;

import asmeta.terms.basicterms.Term;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see asmeta.terms.furtherterms.FurthertermsPackage
 * @generated
 */
public interface FurthertermsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FurthertermsFactory eINSTANCE = asmeta.terms.furtherterms.impl.FurthertermsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Integer Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer Term</em>'.
	 * @generated
	 */
	IntegerTerm createIntegerTerm();

	/**
	 * Returns a new object of class '<em>Natural Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Natural Term</em>'.
	 * @generated
	 */
	NaturalTerm createNaturalTerm();

	/**
	 * Returns a new object of class '<em>String Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Term</em>'.
	 * @generated
	 */
	StringTerm createStringTerm();

	/**
	 * Returns a new object of class '<em>Set Ct</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Set Ct</em>'.
	 * @generated
	 */
	SetCt createSetCt();

	/**
	 * Returns a new object of class '<em>Sequence Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sequence Term</em>'.
	 * @generated
	 */
	SequenceTerm createSequenceTerm();

	/**
	 * Returns a new object of class '<em>Sequence Ct</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sequence Ct</em>'.
	 * @generated
	 */
	SequenceCt createSequenceCt();

	/**
	 * Returns a new object of class '<em>Real Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Real Term</em>'.
	 * @generated
	 */
	RealTerm createRealTerm();

	/**
	 * Returns a new object of class '<em>Map Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Term</em>'.
	 * @generated
	 */
	MapTerm createMapTerm();

	/**
	 * Returns a new object of class '<em>Map Ct</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Map Ct</em>'.
	 * @generated
	 */
	MapCt createMapCt();

	/**
	 * Returns a new object of class '<em>Let Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Let Term</em>'.
	 * @generated
	 */
	LetTerm createLetTerm();

	/**
	 * Returns a new object of class '<em>Forall Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Forall Term</em>'.
	 * @generated
	 */
	ForallTerm createForallTerm();

	/**
	 * Returns a new object of class '<em>Exist Unique Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exist Unique Term</em>'.
	 * @generated
	 */
	ExistUniqueTerm createExistUniqueTerm();

	/**
	 * Returns a new object of class '<em>Exist Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exist Term</em>'.
	 * @generated
	 */
	ExistTerm createExistTerm();

	/**
	 * Returns a new object of class '<em>Enum Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enum Term</em>'.
	 * @generated
	 */
	EnumTerm createEnumTerm();

	/**
	 * Returns a new object of class '<em>Conditional Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Conditional Term</em>'.
	 * @generated
	 */
	ConditionalTerm createConditionalTerm();

	/**
	 * Returns a new object of class '<em>Complex Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Complex Term</em>'.
	 * @generated
	 */
	ComplexTerm createComplexTerm();

	/**
	 * Returns a new object of class '<em>Char Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Char Term</em>'.
	 * @generated
	 */
	CharTerm createCharTerm();

	/**
	 * Returns a new object of class '<em>Case Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Case Term</em>'.
	 * @generated
	 */
	CaseTerm createCaseTerm();

	/**
	 * Returns a new object of class '<em>Bag Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bag Term</em>'.
	 * @generated
	 */
	BagTerm createBagTerm();

	/**
	 * Returns a new object of class '<em>Bag Ct</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bag Ct</em>'.
	 * @generated
	 */
	BagCt createBagCt();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	FurthertermsPackage getFurthertermsPackage();

	@Deprecated
	MapCt createMapCt(List<Term> rangeList);
	
	@Deprecated
	BagCt createBagCt(List<Term> rangeList);

} //FurthertermsFactory

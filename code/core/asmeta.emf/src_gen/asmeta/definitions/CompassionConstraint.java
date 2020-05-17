/**
 */
package asmeta.definitions;

import asmeta.terms.basicterms.Term;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compassion Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link asmeta.definitions.CompassionConstraint#getP <em>P</em>}</li>
 *   <li>{@link asmeta.definitions.CompassionConstraint#getQ <em>Q</em>}</li>
 * </ul>
 *
 * @see asmeta.definitions.DefinitionsPackage#getCompassionConstraint()
 * @model
 * @generated
 */
public interface CompassionConstraint extends FairnessConstraint {

	/**
	 * Returns the value of the '<em><b>P</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>P</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>P</em>' containment reference.
	 * @see #setP(Term)
	 * @see asmeta.definitions.DefinitionsPackage#getCompassionConstraint_P()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Term getP();

	/**
	 * Sets the value of the '{@link asmeta.definitions.CompassionConstraint#getP <em>P</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>P</em>' containment reference.
	 * @see #getP()
	 * @generated
	 */
	void setP(Term value);

	/**
	 * Returns the value of the '<em><b>Q</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Q</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Q</em>' containment reference.
	 * @see #setQ(Term)
	 * @see asmeta.definitions.DefinitionsPackage#getCompassionConstraint_Q()
	 * @model containment="true" required="true" ordered="false"
	 * @generated
	 */
	Term getQ();

	/**
	 * Sets the value of the '{@link asmeta.definitions.CompassionConstraint#getQ <em>Q</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Q</em>' containment reference.
	 * @see #getQ()
	 * @generated
	 */
	void setQ(Term value);
} // CompassionConstraint

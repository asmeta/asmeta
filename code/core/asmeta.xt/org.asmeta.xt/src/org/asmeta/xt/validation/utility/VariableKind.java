/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.asmeta.xt.validation.utility;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Variable Kind</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see asmeta.terms.basicterms.BasictermsPackage#getVariableKind()
 * @model
 * @generated
 */
public enum VariableKind implements Enumerator {
	/**
	 * The '<em><b>Logical Var</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOGICAL_VAR_VALUE
	 * @generated
	 * @ordered
	 */
	LOGICAL_VAR(1, "logicalVar", "logicalVar"),

	/**
	 * The '<em><b>Location Var</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOCATION_VAR_VALUE
	 * @generated
	 * @ordered
	 */
	LOCATION_VAR(2, "locationVar", "locationVar"),

	/**
	 * The '<em><b>Rule Var</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_VAR_VALUE
	 * @generated
	 * @ordered
	 */
	RULE_VAR(3, "ruleVar", "ruleVar");

	/**
	 * The '<em><b>Logical Var</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Logical Var</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOGICAL_VAR
	 * @model name="logicalVar"
	 * @generated
	 * @ordered
	 */
	public static final int LOGICAL_VAR_VALUE = 1;

	/**
	 * The '<em><b>Location Var</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Location Var</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOCATION_VAR
	 * @model name="locationVar"
	 * @generated
	 * @ordered
	 */
	public static final int LOCATION_VAR_VALUE = 2;

	/**
	 * The '<em><b>Rule Var</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rule Var</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE_VAR
	 * @model name="ruleVar"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_VAR_VALUE = 3;

	/**
	 * An array of all the '<em><b>Variable Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final VariableKind[] VALUES_ARRAY =
		new VariableKind[] {
			LOGICAL_VAR,
			LOCATION_VAR,
			RULE_VAR,
		};

	/**
	 * A public read-only list of all the '<em><b>Variable Kind</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<VariableKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Variable Kind</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static VariableKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VariableKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Variable Kind</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static VariableKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VariableKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Variable Kind</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static VariableKind get(int value) {
		switch (value) {
			case LOGICAL_VAR_VALUE: return LOGICAL_VAR;
			case LOCATION_VAR_VALUE: return LOCATION_VAR;
			case RULE_VAR_VALUE: return RULE_VAR;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private VariableKind(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //VariableKind

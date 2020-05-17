/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.definitions.impl;

import org.eclipse.emf.ecore.EClass;

import asmeta.definitions.Classifier;
import asmeta.definitions.DefinitionsPackage;
import asmeta.structure.impl.NamedElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Classifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class ClassifierImpl extends NamedElementImpl implements Classifier {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DefinitionsPackage.Literals.CLASSIFIER;
	}

} //ClassifierImpl

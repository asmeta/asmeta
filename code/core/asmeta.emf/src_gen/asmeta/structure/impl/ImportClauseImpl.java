/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.Domain;
import asmeta.structure.ImportClause;
import asmeta.structure.StructurePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Import Clause</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.impl.ImportClauseImpl#getImportedDomain <em>Imported Domain</em>}</li>
 *   <li>{@link asmeta.structure.impl.ImportClauseImpl#getImportedFunction <em>Imported Function</em>}</li>
 *   <li>{@link asmeta.structure.impl.ImportClauseImpl#getImportedRule <em>Imported Rule</em>}</li>
 *   <li>{@link asmeta.structure.impl.ImportClauseImpl#getModuleName <em>Module Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImportClauseImpl extends EObjectImpl implements ImportClause {
	/**
	 * The cached value of the '{@link #getImportedDomain() <em>Imported Domain</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportedDomain()
	 * @generated
	 * @ordered
	 */
	protected EList<Domain> importedDomain;

	/**
	 * The cached value of the '{@link #getImportedFunction() <em>Imported Function</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportedFunction()
	 * @generated
	 * @ordered
	 */
	protected EList<Function> importedFunction;

	/**
	 * The cached value of the '{@link #getImportedRule() <em>Imported Rule</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportedRule()
	 * @generated
	 * @ordered
	 */
	protected EList<RuleDeclaration> importedRule;

	/**
	 * The default value of the '{@link #getModuleName() <em>Module Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleName()
	 * @generated
	 * @ordered
	 */
	protected static final String MODULE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModuleName() <em>Module Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleName()
	 * @generated
	 * @ordered
	 */
	protected String moduleName = MODULE_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImportClauseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StructurePackage.Literals.IMPORT_CLAUSE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Domain> getImportedDomain() {
		if (importedDomain == null) {
			importedDomain = new EObjectResolvingEList<Domain>(Domain.class, this, StructurePackage.IMPORT_CLAUSE__IMPORTED_DOMAIN);
		}
		return importedDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Function> getImportedFunction() {
		if (importedFunction == null) {
			importedFunction = new EObjectResolvingEList<Function>(Function.class, this, StructurePackage.IMPORT_CLAUSE__IMPORTED_FUNCTION);
		}
		return importedFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RuleDeclaration> getImportedRule() {
		if (importedRule == null) {
			importedRule = new EObjectResolvingEList<RuleDeclaration>(RuleDeclaration.class, this, StructurePackage.IMPORT_CLAUSE__IMPORTED_RULE);
		}
		return importedRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModuleName(String newModuleName) {
		String oldModuleName = moduleName;
		moduleName = newModuleName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.IMPORT_CLAUSE__MODULE_NAME, oldModuleName, moduleName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StructurePackage.IMPORT_CLAUSE__IMPORTED_DOMAIN:
				return getImportedDomain();
			case StructurePackage.IMPORT_CLAUSE__IMPORTED_FUNCTION:
				return getImportedFunction();
			case StructurePackage.IMPORT_CLAUSE__IMPORTED_RULE:
				return getImportedRule();
			case StructurePackage.IMPORT_CLAUSE__MODULE_NAME:
				return getModuleName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StructurePackage.IMPORT_CLAUSE__IMPORTED_DOMAIN:
				getImportedDomain().clear();
				getImportedDomain().addAll((Collection<? extends Domain>)newValue);
				return;
			case StructurePackage.IMPORT_CLAUSE__IMPORTED_FUNCTION:
				getImportedFunction().clear();
				getImportedFunction().addAll((Collection<? extends Function>)newValue);
				return;
			case StructurePackage.IMPORT_CLAUSE__IMPORTED_RULE:
				getImportedRule().clear();
				getImportedRule().addAll((Collection<? extends RuleDeclaration>)newValue);
				return;
			case StructurePackage.IMPORT_CLAUSE__MODULE_NAME:
				setModuleName((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case StructurePackage.IMPORT_CLAUSE__IMPORTED_DOMAIN:
				getImportedDomain().clear();
				return;
			case StructurePackage.IMPORT_CLAUSE__IMPORTED_FUNCTION:
				getImportedFunction().clear();
				return;
			case StructurePackage.IMPORT_CLAUSE__IMPORTED_RULE:
				getImportedRule().clear();
				return;
			case StructurePackage.IMPORT_CLAUSE__MODULE_NAME:
				setModuleName(MODULE_NAME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case StructurePackage.IMPORT_CLAUSE__IMPORTED_DOMAIN:
				return importedDomain != null && !importedDomain.isEmpty();
			case StructurePackage.IMPORT_CLAUSE__IMPORTED_FUNCTION:
				return importedFunction != null && !importedFunction.isEmpty();
			case StructurePackage.IMPORT_CLAUSE__IMPORTED_RULE:
				return importedRule != null && !importedRule.isEmpty();
			case StructurePackage.IMPORT_CLAUSE__MODULE_NAME:
				return MODULE_NAME_EDEFAULT == null ? moduleName != null : !MODULE_NAME_EDEFAULT.equals(moduleName);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (moduleName: ");
		result.append(moduleName);
		result.append(')');
		return result.toString();
	}

} //ImportClauseImpl

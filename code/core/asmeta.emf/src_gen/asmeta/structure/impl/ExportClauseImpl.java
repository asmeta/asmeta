/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.Domain;
import asmeta.structure.ExportClause;
import asmeta.structure.StructurePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Export Clause</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.impl.ExportClauseImpl#getExportedFunction <em>Exported Function</em>}</li>
 *   <li>{@link asmeta.structure.impl.ExportClauseImpl#getExportedDomain <em>Exported Domain</em>}</li>
 *   <li>{@link asmeta.structure.impl.ExportClauseImpl#getExportedRule <em>Exported Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExportClauseImpl extends EObjectImpl implements ExportClause {
	/**
	 * The cached value of the '{@link #getExportedFunction() <em>Exported Function</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedFunction()
	 * @generated
	 * @ordered
	 */
	protected EList<Function> exportedFunction;

	/**
	 * The cached value of the '{@link #getExportedDomain() <em>Exported Domain</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedDomain()
	 * @generated
	 * @ordered
	 */
	protected EList<Domain> exportedDomain;

	/**
	 * The cached value of the '{@link #getExportedRule() <em>Exported Rule</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedRule()
	 * @generated
	 * @ordered
	 */
	protected EList<RuleDeclaration> exportedRule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExportClauseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StructurePackage.Literals.EXPORT_CLAUSE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Function> getExportedFunction() {
		if (exportedFunction == null) {
			exportedFunction = new EObjectResolvingEList<Function>(Function.class, this, StructurePackage.EXPORT_CLAUSE__EXPORTED_FUNCTION);
		}
		return exportedFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Domain> getExportedDomain() {
		if (exportedDomain == null) {
			exportedDomain = new EObjectResolvingEList<Domain>(Domain.class, this, StructurePackage.EXPORT_CLAUSE__EXPORTED_DOMAIN);
		}
		return exportedDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RuleDeclaration> getExportedRule() {
		if (exportedRule == null) {
			exportedRule = new EObjectResolvingEList<RuleDeclaration>(RuleDeclaration.class, this, StructurePackage.EXPORT_CLAUSE__EXPORTED_RULE);
		}
		return exportedRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StructurePackage.EXPORT_CLAUSE__EXPORTED_FUNCTION:
				return getExportedFunction();
			case StructurePackage.EXPORT_CLAUSE__EXPORTED_DOMAIN:
				return getExportedDomain();
			case StructurePackage.EXPORT_CLAUSE__EXPORTED_RULE:
				return getExportedRule();
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
			case StructurePackage.EXPORT_CLAUSE__EXPORTED_FUNCTION:
				getExportedFunction().clear();
				getExportedFunction().addAll((Collection<? extends Function>)newValue);
				return;
			case StructurePackage.EXPORT_CLAUSE__EXPORTED_DOMAIN:
				getExportedDomain().clear();
				getExportedDomain().addAll((Collection<? extends Domain>)newValue);
				return;
			case StructurePackage.EXPORT_CLAUSE__EXPORTED_RULE:
				getExportedRule().clear();
				getExportedRule().addAll((Collection<? extends RuleDeclaration>)newValue);
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
			case StructurePackage.EXPORT_CLAUSE__EXPORTED_FUNCTION:
				getExportedFunction().clear();
				return;
			case StructurePackage.EXPORT_CLAUSE__EXPORTED_DOMAIN:
				getExportedDomain().clear();
				return;
			case StructurePackage.EXPORT_CLAUSE__EXPORTED_RULE:
				getExportedRule().clear();
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
			case StructurePackage.EXPORT_CLAUSE__EXPORTED_FUNCTION:
				return exportedFunction != null && !exportedFunction.isEmpty();
			case StructurePackage.EXPORT_CLAUSE__EXPORTED_DOMAIN:
				return exportedDomain != null && !exportedDomain.isEmpty();
			case StructurePackage.EXPORT_CLAUSE__EXPORTED_RULE:
				return exportedRule != null && !exportedRule.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ExportClauseImpl

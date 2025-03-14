/**
 * generated by Xtext 2.36.0
 */
package org.asmeta.xt.asmetal.impl;

import java.util.Collection;

import org.asmeta.xt.asmetal.AsmetalPackage;
import org.asmeta.xt.asmetal.ExportClause;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Export Clause</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.xt.asmetal.impl.ExportClauseImpl#getExportedList <em>Exported List</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.impl.ExportClauseImpl#isExportAll <em>Export All</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ExportClauseImpl extends MinimalEObjectImpl.Container implements ExportClause
{
  /**
   * The cached value of the '{@link #getExportedList() <em>Exported List</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExportedList()
   * @generated
   * @ordered
   */
  protected EList<String> exportedList;

  /**
   * The default value of the '{@link #isExportAll() <em>Export All</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isExportAll()
   * @generated
   * @ordered
   */
  protected static final boolean EXPORT_ALL_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isExportAll() <em>Export All</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isExportAll()
   * @generated
   * @ordered
   */
  protected boolean exportAll = EXPORT_ALL_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ExportClauseImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return AsmetalPackage.Literals.EXPORT_CLAUSE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EList<String> getExportedList()
  {
    if (exportedList == null)
    {
      exportedList = new EDataTypeEList<String>(String.class, this, AsmetalPackage.EXPORT_CLAUSE__EXPORTED_LIST);
    }
    return exportedList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isExportAll()
  {
    return exportAll;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setExportAll(boolean newExportAll)
  {
    boolean oldExportAll = exportAll;
    exportAll = newExportAll;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, AsmetalPackage.EXPORT_CLAUSE__EXPORT_ALL, oldExportAll, exportAll));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case AsmetalPackage.EXPORT_CLAUSE__EXPORTED_LIST:
        return getExportedList();
      case AsmetalPackage.EXPORT_CLAUSE__EXPORT_ALL:
        return isExportAll();
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
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case AsmetalPackage.EXPORT_CLAUSE__EXPORTED_LIST:
        getExportedList().clear();
        getExportedList().addAll((Collection<? extends String>)newValue);
        return;
      case AsmetalPackage.EXPORT_CLAUSE__EXPORT_ALL:
        setExportAll((Boolean)newValue);
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
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case AsmetalPackage.EXPORT_CLAUSE__EXPORTED_LIST:
        getExportedList().clear();
        return;
      case AsmetalPackage.EXPORT_CLAUSE__EXPORT_ALL:
        setExportAll(EXPORT_ALL_EDEFAULT);
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
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case AsmetalPackage.EXPORT_CLAUSE__EXPORTED_LIST:
        return exportedList != null && !exportedList.isEmpty();
      case AsmetalPackage.EXPORT_CLAUSE__EXPORT_ALL:
        return exportAll != EXPORT_ALL_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (exportedList: ");
    result.append(exportedList);
    result.append(", exportAll: ");
    result.append(exportAll);
    result.append(')');
    return result.toString();
  }

} //ExportClauseImpl

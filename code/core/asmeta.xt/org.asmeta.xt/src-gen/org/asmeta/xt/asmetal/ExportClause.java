/**
 * generated by Xtext 2.36.0
 */
package org.asmeta.xt.asmetal;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Export Clause</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.asmeta.xt.asmetal.ExportClause#getExportedList <em>Exported List</em>}</li>
 *   <li>{@link org.asmeta.xt.asmetal.ExportClause#isExportAll <em>Export All</em>}</li>
 * </ul>
 *
 * @see org.asmeta.xt.asmetal.AsmetalPackage#getExportClause()
 * @model
 * @generated
 */
public interface ExportClause extends EObject
{
  /**
   * Returns the value of the '<em><b>Exported List</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Exported List</em>' attribute list.
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getExportClause_ExportedList()
   * @model unique="false"
   * @generated
   */
  EList<String> getExportedList();

  /**
   * Returns the value of the '<em><b>Export All</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Export All</em>' attribute.
   * @see #setExportAll(boolean)
   * @see org.asmeta.xt.asmetal.AsmetalPackage#getExportClause_ExportAll()
   * @model
   * @generated
   */
  boolean isExportAll();

  /**
   * Sets the value of the '{@link org.asmeta.xt.asmetal.ExportClause#isExportAll <em>Export All</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Export All</em>' attribute.
   * @see #isExportAll()
   * @generated
   */
  void setExportAll(boolean value);

} // ExportClause

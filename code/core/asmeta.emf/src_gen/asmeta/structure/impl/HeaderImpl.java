/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.structure.Asm;
import asmeta.structure.ExportClause;
import asmeta.structure.Header;
import asmeta.structure.ImportClause;
import asmeta.structure.Signature;
import asmeta.structure.StructurePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Header</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.impl.HeaderImpl#getImportClause <em>Import Clause</em>}</li>
 *   <li>{@link asmeta.structure.impl.HeaderImpl#getSignature <em>Signature</em>}</li>
 *   <li>{@link asmeta.structure.impl.HeaderImpl#getExportClause <em>Export Clause</em>}</li>
 *   <li>{@link asmeta.structure.impl.HeaderImpl#getAsm <em>Asm</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HeaderImpl extends EObjectImpl implements Header {
	/**
	 * The cached value of the '{@link #getImportClause() <em>Import Clause</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportClause()
	 * @generated
	 * @ordered
	 */
	protected EList<ImportClause> importClause;

	/**
	 * The cached value of the '{@link #getSignature() <em>Signature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignature()
	 * @generated
	 * @ordered
	 */
	protected Signature signature;

	/**
	 * The cached value of the '{@link #getExportClause() <em>Export Clause</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportClause()
	 * @generated
	 * @ordered
	 */
	protected ExportClause exportClause;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HeaderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StructurePackage.Literals.HEADER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ImportClause> getImportClause() {
		if (importClause == null) {
			importClause = new EObjectContainmentEList<ImportClause>(ImportClause.class, this, StructurePackage.HEADER__IMPORT_CLAUSE);
		}
		return importClause;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Signature getSignature() {
		return signature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSignature(Signature newSignature, NotificationChain msgs) {
		Signature oldSignature = signature;
		signature = newSignature;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StructurePackage.HEADER__SIGNATURE, oldSignature, newSignature);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSignature(Signature newSignature) {
		if (newSignature != signature) {
			NotificationChain msgs = null;
			if (signature != null)
				msgs = ((InternalEObject)signature).eInverseRemove(this, StructurePackage.SIGNATURE__HEADER_SECTION, Signature.class, msgs);
			if (newSignature != null)
				msgs = ((InternalEObject)newSignature).eInverseAdd(this, StructurePackage.SIGNATURE__HEADER_SECTION, Signature.class, msgs);
			msgs = basicSetSignature(newSignature, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.HEADER__SIGNATURE, newSignature, newSignature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExportClause getExportClause() {
		return exportClause;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExportClause(ExportClause newExportClause, NotificationChain msgs) {
		ExportClause oldExportClause = exportClause;
		exportClause = newExportClause;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StructurePackage.HEADER__EXPORT_CLAUSE, oldExportClause, newExportClause);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExportClause(ExportClause newExportClause) {
		if (newExportClause != exportClause) {
			NotificationChain msgs = null;
			if (exportClause != null)
				msgs = ((InternalEObject)exportClause).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StructurePackage.HEADER__EXPORT_CLAUSE, null, msgs);
			if (newExportClause != null)
				msgs = ((InternalEObject)newExportClause).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - StructurePackage.HEADER__EXPORT_CLAUSE, null, msgs);
			msgs = basicSetExportClause(newExportClause, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.HEADER__EXPORT_CLAUSE, newExportClause, newExportClause));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Asm getAsm() {
		if (eContainerFeatureID() != StructurePackage.HEADER__ASM) return null;
		return (Asm)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAsm(Asm newAsm, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAsm, StructurePackage.HEADER__ASM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAsm(Asm newAsm) {
		if (newAsm != eInternalContainer() || (eContainerFeatureID() != StructurePackage.HEADER__ASM && newAsm != null)) {
			if (EcoreUtil.isAncestor(this, newAsm))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAsm != null)
				msgs = ((InternalEObject)newAsm).eInverseAdd(this, StructurePackage.ASM__HEADER_SECTION, Asm.class, msgs);
			msgs = basicSetAsm(newAsm, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.HEADER__ASM, newAsm, newAsm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StructurePackage.HEADER__SIGNATURE:
				if (signature != null)
					msgs = ((InternalEObject)signature).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StructurePackage.HEADER__SIGNATURE, null, msgs);
				return basicSetSignature((Signature)otherEnd, msgs);
			case StructurePackage.HEADER__ASM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAsm((Asm)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StructurePackage.HEADER__IMPORT_CLAUSE:
				return ((InternalEList<?>)getImportClause()).basicRemove(otherEnd, msgs);
			case StructurePackage.HEADER__SIGNATURE:
				return basicSetSignature(null, msgs);
			case StructurePackage.HEADER__EXPORT_CLAUSE:
				return basicSetExportClause(null, msgs);
			case StructurePackage.HEADER__ASM:
				return basicSetAsm(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case StructurePackage.HEADER__ASM:
				return eInternalContainer().eInverseRemove(this, StructurePackage.ASM__HEADER_SECTION, Asm.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StructurePackage.HEADER__IMPORT_CLAUSE:
				return getImportClause();
			case StructurePackage.HEADER__SIGNATURE:
				return getSignature();
			case StructurePackage.HEADER__EXPORT_CLAUSE:
				return getExportClause();
			case StructurePackage.HEADER__ASM:
				return getAsm();
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
			case StructurePackage.HEADER__IMPORT_CLAUSE:
				getImportClause().clear();
				getImportClause().addAll((Collection<? extends ImportClause>)newValue);
				return;
			case StructurePackage.HEADER__SIGNATURE:
				setSignature((Signature)newValue);
				return;
			case StructurePackage.HEADER__EXPORT_CLAUSE:
				setExportClause((ExportClause)newValue);
				return;
			case StructurePackage.HEADER__ASM:
				setAsm((Asm)newValue);
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
			case StructurePackage.HEADER__IMPORT_CLAUSE:
				getImportClause().clear();
				return;
			case StructurePackage.HEADER__SIGNATURE:
				setSignature((Signature)null);
				return;
			case StructurePackage.HEADER__EXPORT_CLAUSE:
				setExportClause((ExportClause)null);
				return;
			case StructurePackage.HEADER__ASM:
				setAsm((Asm)null);
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
			case StructurePackage.HEADER__IMPORT_CLAUSE:
				return importClause != null && !importClause.isEmpty();
			case StructurePackage.HEADER__SIGNATURE:
				return signature != null;
			case StructurePackage.HEADER__EXPORT_CLAUSE:
				return exportClause != null;
			case StructurePackage.HEADER__ASM:
				return getAsm() != null;
		}
		return super.eIsSet(featureID);
	}

} //HeaderImpl

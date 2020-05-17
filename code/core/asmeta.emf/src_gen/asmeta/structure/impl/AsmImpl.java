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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import asmeta.structure.Asm;
import asmeta.structure.Body;
import asmeta.structure.Header;
import asmeta.structure.Initialization;
import asmeta.structure.StructurePackage;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Asm</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link asmeta.structure.impl.AsmImpl#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link asmeta.structure.impl.AsmImpl#getDefaultInitialState <em>Default Initial State</em>}</li>
 *   <li>{@link asmeta.structure.impl.AsmImpl#getBodySection <em>Body Section</em>}</li>
 *   <li>{@link asmeta.structure.impl.AsmImpl#getHeaderSection <em>Header Section</em>}</li>
 *   <li>{@link asmeta.structure.impl.AsmImpl#getMainrule <em>Mainrule</em>}</li>
 *   <li>{@link asmeta.structure.impl.AsmImpl#getIsAsynchr <em>Is Asynchr</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AsmImpl extends NamedElementImpl implements Asm {
	/**
	 * The cached value of the '{@link #getInitialState() <em>Initial State</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialState()
	 * @generated
	 * @ordered
	 */
	protected EList<Initialization> initialState;

	/**
	 * The cached value of the '{@link #getDefaultInitialState() <em>Default Initial State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultInitialState()
	 * @generated
	 * @ordered
	 */
	protected Initialization defaultInitialState;

	/**
	 * The cached value of the '{@link #getBodySection() <em>Body Section</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBodySection()
	 * @generated
	 * @ordered
	 */
	protected Body bodySection;

	/**
	 * The cached value of the '{@link #getHeaderSection() <em>Header Section</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderSection()
	 * @generated
	 * @ordered
	 */
	protected Header headerSection;

	/**
	 * The cached value of the '{@link #getMainrule() <em>Mainrule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMainrule()
	 * @generated
	 * @ordered
	 */
	protected MacroDeclaration mainrule;

	/**
	 * The default value of the '{@link #getIsAsynchr() <em>Is Asynchr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsAsynchr()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean IS_ASYNCHR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIsAsynchr() <em>Is Asynchr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsAsynchr()
	 * @generated
	 * @ordered
	 */
	protected Boolean isAsynchr = IS_ASYNCHR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AsmImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StructurePackage.Literals.ASM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Initialization> getInitialState() {
		if (initialState == null) {
			initialState = new EObjectContainmentEList<Initialization>(Initialization.class, this, StructurePackage.ASM__INITIAL_STATE);
		}
		return initialState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Initialization getDefaultInitialState() {
		if (defaultInitialState != null && defaultInitialState.eIsProxy()) {
			InternalEObject oldDefaultInitialState = (InternalEObject)defaultInitialState;
			defaultInitialState = (Initialization)eResolveProxy(oldDefaultInitialState);
			if (defaultInitialState != oldDefaultInitialState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StructurePackage.ASM__DEFAULT_INITIAL_STATE, oldDefaultInitialState, defaultInitialState));
			}
		}
		return defaultInitialState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Initialization basicGetDefaultInitialState() {
		return defaultInitialState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefaultInitialState(Initialization newDefaultInitialState, NotificationChain msgs) {
		Initialization oldDefaultInitialState = defaultInitialState;
		defaultInitialState = newDefaultInitialState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StructurePackage.ASM__DEFAULT_INITIAL_STATE, oldDefaultInitialState, newDefaultInitialState);
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
	public void setDefaultInitialState(Initialization newDefaultInitialState) {
		if (newDefaultInitialState != defaultInitialState) {
			NotificationChain msgs = null;
			if (defaultInitialState != null)
				msgs = ((InternalEObject)defaultInitialState).eInverseRemove(this, StructurePackage.INITIALIZATION__ASM, Initialization.class, msgs);
			if (newDefaultInitialState != null)
				msgs = ((InternalEObject)newDefaultInitialState).eInverseAdd(this, StructurePackage.INITIALIZATION__ASM, Initialization.class, msgs);
			msgs = basicSetDefaultInitialState(newDefaultInitialState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.ASM__DEFAULT_INITIAL_STATE, newDefaultInitialState, newDefaultInitialState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Body getBodySection() {
		return bodySection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBodySection(Body newBodySection, NotificationChain msgs) {
		Body oldBodySection = bodySection;
		bodySection = newBodySection;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StructurePackage.ASM__BODY_SECTION, oldBodySection, newBodySection);
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
	public void setBodySection(Body newBodySection) {
		if (newBodySection != bodySection) {
			NotificationChain msgs = null;
			if (bodySection != null)
				msgs = ((InternalEObject)bodySection).eInverseRemove(this, StructurePackage.BODY__ASM, Body.class, msgs);
			if (newBodySection != null)
				msgs = ((InternalEObject)newBodySection).eInverseAdd(this, StructurePackage.BODY__ASM, Body.class, msgs);
			msgs = basicSetBodySection(newBodySection, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.ASM__BODY_SECTION, newBodySection, newBodySection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Header getHeaderSection() {
		return headerSection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHeaderSection(Header newHeaderSection, NotificationChain msgs) {
		Header oldHeaderSection = headerSection;
		headerSection = newHeaderSection;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StructurePackage.ASM__HEADER_SECTION, oldHeaderSection, newHeaderSection);
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
	public void setHeaderSection(Header newHeaderSection) {
		if (newHeaderSection != headerSection) {
			NotificationChain msgs = null;
			if (headerSection != null)
				msgs = ((InternalEObject)headerSection).eInverseRemove(this, StructurePackage.HEADER__ASM, Header.class, msgs);
			if (newHeaderSection != null)
				msgs = ((InternalEObject)newHeaderSection).eInverseAdd(this, StructurePackage.HEADER__ASM, Header.class, msgs);
			msgs = basicSetHeaderSection(newHeaderSection, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.ASM__HEADER_SECTION, newHeaderSection, newHeaderSection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MacroDeclaration getMainrule() {
		if (mainrule != null && mainrule.eIsProxy()) {
			InternalEObject oldMainrule = (InternalEObject)mainrule;
			mainrule = (MacroDeclaration)eResolveProxy(oldMainrule);
			if (mainrule != oldMainrule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StructurePackage.ASM__MAINRULE, oldMainrule, mainrule));
			}
		}
		return mainrule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MacroDeclaration basicGetMainrule() {
		return mainrule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMainrule(MacroDeclaration newMainrule) {
		MacroDeclaration oldMainrule = mainrule;
		mainrule = newMainrule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.ASM__MAINRULE, oldMainrule, mainrule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getIsAsynchr() {
		return isAsynchr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsAsynchr(Boolean newIsAsynchr) {
		Boolean oldIsAsynchr = isAsynchr;
		isAsynchr = newIsAsynchr;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StructurePackage.ASM__IS_ASYNCHR, oldIsAsynchr, isAsynchr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StructurePackage.ASM__DEFAULT_INITIAL_STATE:
				if (defaultInitialState != null)
					msgs = ((InternalEObject)defaultInitialState).eInverseRemove(this, StructurePackage.INITIALIZATION__ASM, Initialization.class, msgs);
				return basicSetDefaultInitialState((Initialization)otherEnd, msgs);
			case StructurePackage.ASM__BODY_SECTION:
				if (bodySection != null)
					msgs = ((InternalEObject)bodySection).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StructurePackage.ASM__BODY_SECTION, null, msgs);
				return basicSetBodySection((Body)otherEnd, msgs);
			case StructurePackage.ASM__HEADER_SECTION:
				if (headerSection != null)
					msgs = ((InternalEObject)headerSection).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - StructurePackage.ASM__HEADER_SECTION, null, msgs);
				return basicSetHeaderSection((Header)otherEnd, msgs);
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
			case StructurePackage.ASM__INITIAL_STATE:
				return ((InternalEList<?>)getInitialState()).basicRemove(otherEnd, msgs);
			case StructurePackage.ASM__DEFAULT_INITIAL_STATE:
				return basicSetDefaultInitialState(null, msgs);
			case StructurePackage.ASM__BODY_SECTION:
				return basicSetBodySection(null, msgs);
			case StructurePackage.ASM__HEADER_SECTION:
				return basicSetHeaderSection(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StructurePackage.ASM__INITIAL_STATE:
				return getInitialState();
			case StructurePackage.ASM__DEFAULT_INITIAL_STATE:
				if (resolve) return getDefaultInitialState();
				return basicGetDefaultInitialState();
			case StructurePackage.ASM__BODY_SECTION:
				return getBodySection();
			case StructurePackage.ASM__HEADER_SECTION:
				return getHeaderSection();
			case StructurePackage.ASM__MAINRULE:
				if (resolve) return getMainrule();
				return basicGetMainrule();
			case StructurePackage.ASM__IS_ASYNCHR:
				return getIsAsynchr();
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
			case StructurePackage.ASM__INITIAL_STATE:
				getInitialState().clear();
				getInitialState().addAll((Collection<? extends Initialization>)newValue);
				return;
			case StructurePackage.ASM__DEFAULT_INITIAL_STATE:
				setDefaultInitialState((Initialization)newValue);
				return;
			case StructurePackage.ASM__BODY_SECTION:
				setBodySection((Body)newValue);
				return;
			case StructurePackage.ASM__HEADER_SECTION:
				setHeaderSection((Header)newValue);
				return;
			case StructurePackage.ASM__MAINRULE:
				setMainrule((MacroDeclaration)newValue);
				return;
			case StructurePackage.ASM__IS_ASYNCHR:
				setIsAsynchr((Boolean)newValue);
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
			case StructurePackage.ASM__INITIAL_STATE:
				getInitialState().clear();
				return;
			case StructurePackage.ASM__DEFAULT_INITIAL_STATE:
				setDefaultInitialState((Initialization)null);
				return;
			case StructurePackage.ASM__BODY_SECTION:
				setBodySection((Body)null);
				return;
			case StructurePackage.ASM__HEADER_SECTION:
				setHeaderSection((Header)null);
				return;
			case StructurePackage.ASM__MAINRULE:
				setMainrule((MacroDeclaration)null);
				return;
			case StructurePackage.ASM__IS_ASYNCHR:
				setIsAsynchr(IS_ASYNCHR_EDEFAULT);
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
			case StructurePackage.ASM__INITIAL_STATE:
				return initialState != null && !initialState.isEmpty();
			case StructurePackage.ASM__DEFAULT_INITIAL_STATE:
				return defaultInitialState != null;
			case StructurePackage.ASM__BODY_SECTION:
				return bodySection != null;
			case StructurePackage.ASM__HEADER_SECTION:
				return headerSection != null;
			case StructurePackage.ASM__MAINRULE:
				return mainrule != null;
			case StructurePackage.ASM__IS_ASYNCHR:
				return IS_ASYNCHR_EDEFAULT == null ? isAsynchr != null : !IS_ASYNCHR_EDEFAULT.equals(isAsynchr);
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
		result.append(" (isAsynchr: ");
		result.append(isAsynchr);
		result.append(')');
		return result.toString();
	}

} //AsmImpl

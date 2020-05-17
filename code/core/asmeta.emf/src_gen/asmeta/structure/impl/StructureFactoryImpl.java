/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package asmeta.structure.impl;

import asmeta.structure.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
import asmeta.structure.Body;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.structure.ExportClause;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Header;
import asmeta.structure.ImportClause;
import asmeta.structure.Initialization;
import asmeta.structure.Signature;
import asmeta.structure.StructureFactory;
import asmeta.structure.StructurePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StructureFactoryImpl extends EFactoryImpl implements StructureFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StructureFactory init() {
		try {
			StructureFactory theStructureFactory = (StructureFactory)EPackage.Registry.INSTANCE.getEFactory(StructurePackage.eNS_URI);
			if (theStructureFactory != null) {
				return theStructureFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new StructureFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructureFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case StructurePackage.AGENT_INITIALIZATION: return createAgentInitialization();
			case StructurePackage.BODY: return createBody();
			case StructurePackage.FUNCTION_INITIALIZATION: return createFunctionInitialization();
			case StructurePackage.DOMAIN_INITIALIZATION: return createDomainInitialization();
			case StructurePackage.SIGNATURE: return createSignature();
			case StructurePackage.EXPORT_CLAUSE: return createExportClause();
			case StructurePackage.IMPORT_CLAUSE: return createImportClause();
			case StructurePackage.FUNCTION_DEFINITION: return createFunctionDefinition();
			case StructurePackage.DOMAIN_DEFINITION: return createDomainDefinition();
			case StructurePackage.INITIALIZATION: return createInitialization();
			case StructurePackage.HEADER: return createHeader();
			case StructurePackage.ASM: return createAsm();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AgentInitialization createAgentInitialization() {
		AgentInitializationImpl agentInitialization = new AgentInitializationImpl();
		return agentInitialization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Body createBody() {
		BodyImpl body = new BodyImpl();
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FunctionInitialization createFunctionInitialization() {
		FunctionInitializationImpl functionInitialization = new FunctionInitializationImpl();
		return functionInitialization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DomainInitialization createDomainInitialization() {
		DomainInitializationImpl domainInitialization = new DomainInitializationImpl();
		return domainInitialization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Signature createSignature() {
		SignatureImpl signature = new SignatureImpl();
		return signature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExportClause createExportClause() {
		ExportClauseImpl exportClause = new ExportClauseImpl();
		return exportClause;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ImportClause createImportClause() {
		ImportClauseImpl importClause = new ImportClauseImpl();
		return importClause;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FunctionDefinition createFunctionDefinition() {
		FunctionDefinitionImpl functionDefinition = new FunctionDefinitionImpl();
		return functionDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DomainDefinition createDomainDefinition() {
		DomainDefinitionImpl domainDefinition = new DomainDefinitionImpl();
		return domainDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Initialization createInitialization() {
		InitializationImpl initialization = new InitializationImpl();
		return initialization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Header createHeader() {
		HeaderImpl header = new HeaderImpl();
		return header;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Asm createAsm() {
		AsmImpl asm = new AsmImpl();
		return asm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StructurePackage getStructurePackage() {
		return (StructurePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static StructurePackage getPackage() {
		return StructurePackage.eINSTANCE;
	}

	@Override
	@Deprecated
	public Asm createAsm(String name, boolean isAsyncr) {
		Asm result = createAsm();
		result.setName(name);
		result.setIsAsynchr(isAsyncr);
		return result;
	}

} //StructureFactoryImpl

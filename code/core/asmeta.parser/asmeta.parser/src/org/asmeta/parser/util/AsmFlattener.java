package org.asmeta.parser.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.AsmetaParserUtility;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.structure.Asm;
import asmeta.structure.AgentInitialization;
import asmeta.structure.Body;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Header;
import asmeta.structure.ImportClause;
import asmeta.structure.Initialization;
import asmeta.structure.Signature;
import asmeta.structure.StructureFactory;

/**
 * Builds a single ASM for Java generation from modules already parsed together.
 * The original ImportFlattener constructor, visit() and getters remain a collector.
 */
class AsmFlattener {

	private Set<Domain> domains;
	private ArrayList<Function> functions;
	private ArrayList<DomainDefinition> domainDefs;
	private ArrayList<DomainInitialization> domainInits;
	private List<FunctionInitialization> functionInits = new ArrayList<FunctionInitialization>();
	private List<AgentInitialization> agentInits = new ArrayList<AgentInitialization>();
	private List<StructuredTd> structuredDomains = new ArrayList<StructuredTd>();
	private List<FunctionDefinition> functionDefs = new ArrayList<FunctionDefinition>();
	private List<RuleDeclaration> rules = new ArrayList<RuleDeclaration>();
	private List<ImportClause> libraryImports = new ArrayList<ImportClause>();
	private Map<Asm, File> moduleFiles = new HashMap<Asm, File>();
	private AsmCollection parsedAsms;

	// where the asm is located in the file system
	private String folder;
	private Asm asm;

	AsmFlattener(AsmCollection parsedAsms, String folder) {
		this.parsedAsms = parsedAsms;
		initialize(parsedAsms.getMain(), folder);
	}

	private void initialize(Asm asm, String folder) {
		// check that exists and it is a directory
		assert Files.exists(Path.of(folder)) && Files.isDirectory(Path.of(folder));
		this.folder = folder;
		this.asm = asm;

		Signature signature = asm.getHeaderSection().getSignature();
		this.domains = new LinkedHashSet<Domain>(signature.getDomain());
		this.functions = new ArrayList<Function>(signature.getFunction());

		Body body = asm.getBodySection();
		this.domainDefs = new ArrayList<DomainDefinition>(body.getDomainDefinition());
		this.domainInits = new ArrayList<DomainInitialization>();
		
		collectInitializations(asm);
	}
	
	private void collectInitializations(Asm module) {
		Initialization init = module.getDefaultInitialState();
		if (init != null) {
			domainInits.addAll(init.getDomainInitialization());
			restoreInitializedFunctions(module, init);
			functionInits.addAll(init.getFunctionInitialization());
			agentInits.addAll(init.getAgentInitialization());
		}
	}

	/**
	 * The parser keeps imported initialization instructions and their values, but
	 * clears their function references. Read the module alone to recover the names,
	 * then reconnect the instructions to the functions collected for the flattened ASM.
	 */
	private void restoreInitializedFunctions(Asm module, Initialization init) {
		Initialization sourceInit = null;
		List<FunctionInitialization> instructions = init.getFunctionInitialization();
		for (int i = 0; i < instructions.size(); i++) {
			FunctionInitialization instruction = instructions.get(i);
			// Keep instructions that still point to their initialized function (for the main asm)
			if (instruction.getInitializedFunction() != null) {
				continue;
			}
			if (sourceInit == null) {
				// Read this module only once, and only if a function reference is missing
				File source = moduleFiles.get(module);
				if (source == null) {
					throw new IllegalStateException("Cannot locate initialization source for " + module.getName());
				}
				try {
					sourceInit = ASMParser.setUpReadAsm(source).getMain().getDefaultInitialState();
				} catch (Exception e) {
					throw new IllegalStateException("Cannot read initializations from " + source, e);
				}
			}
			// The same instruction has the same position in the freshly parsed init
			String name = sourceInit.getFunctionInitialization().get(i).getInitializedFunction().getName();
			// Use the function from the original model, not the freshly parsed copy
			for (Function function : functions) {
				if (function.getName().equals(name) && function instanceof DynamicFunction) {
					// Restore only the missing reference; keep the instruction and its value.
					instruction.setInitializedFunction((DynamicFunction) function);
					break;
				}
			}
		}
	}

	private void collectModules() {
		Set<Asm> modules = new LinkedHashSet<Asm>();
		collectImportedModules(asm, folder, modules);
		for (Asm module : modules) {
			Signature signature = module.getHeaderSection().getSignature();
			domains.addAll(signature.getDomain());
			domainDefs.addAll(module.getBodySection().getDomainDefinition());
			functions.addAll(signature.getFunction());
			collectInitializations(module);
			collectDefinitions(module);
		}
		collectDefinitions(asm);
	}

	private void collectImportedModules(Asm current, String currentFolder, Set<Asm> modules) {
		// Called macro rules must precede their callers when the flattened ASM is printed
		for (ImportClause clause : current.getHeaderSection().getImportClause()) {
			if (ImportFlattener.isLibrary(clause.getModuleName())) {
				continue;
			}
			String name = clause.getModuleName().replace('\\', '/');
			name = name.substring(name.lastIndexOf('/') + 1);
			if (name.endsWith(AsmetaParserUtility.ASM_EXTENSION)) {
				name = name.substring(0, name.length() - AsmetaParserUtility.ASM_EXTENSION.length());
			}
			for (Asm imported : parsedAsms) {
				if (imported.getName().equals(name) && !modules.contains(imported)) {
					File file = AsmetaParserUtility.importFile(currentFolder, clause);
					moduleFiles.put(imported, file);
					collectImportedModules(imported, file.getParent(), modules);
					modules.add(imported);
				}
			}
		}
	}


	private void collectDefinitions(Asm module) {
		Body body = module.getBodySection();
		structuredDomains.addAll(module.getHeaderSection().getSignature().getStructuredDomain());
		functionDefs.addAll(body.getFunctionDefinition());
		rules.addAll(body.getRuleDeclaration());
		for (ImportClause clause : module.getHeaderSection().getImportClause()) {
			if (ImportFlattener.isLibrary(clause.getModuleName())) {
				libraryImports.add(clause);
			}
		}
	}


	/**
	 * Builds a single ASM from an already parsed collection. 
	 * Declarations are removed from the original ASMs and added to the flattened ASM.
	 * After flattening, use the returned ASM: the original ASMs no longer contain all their declarations.
	 */
	Asm flatten() {
		collectModules();
		StructureFactory factory = StructureFactory.eINSTANCE;
		Asm result = factory.createAsm();
		result.setName(asm.getName());
		result.setIsAsynchr(asm.getIsAsynchr());
		Header header = factory.createHeader();
		Signature signature = factory.createSignature();
		Body body = factory.createBody();
		result.setHeaderSection(header);
		header.setSignature(signature);
		result.setBodySection(body);

		signature.getDomain().addAll(domains);
		signature.getStructuredDomain().addAll(structuredDomains);
		signature.getFunction().addAll(functions);
		body.getDomainDefinition().addAll(domainDefs);
		body.getFunctionDefinition().addAll(functionDefs);
		body.getRuleDeclaration().addAll(rules);
		header.getImportClause().addAll(libraryImports);
		header.setExportClause(asm.getHeaderSection().getExportClause());
		result.setMainrule(asm.getMainrule());

		Initialization defaultInit = asm.getDefaultInitialState();
		result.getInitialState().addAll(new ArrayList<Initialization>(asm.getInitialState()));
		if (defaultInit == null && (!domainInits.isEmpty() || !functionInits.isEmpty() || !agentInits.isEmpty())) {
			defaultInit = factory.createInitialization();
			defaultInit.setName("s0");
			result.getInitialState().add(defaultInit);
		}
		if (defaultInit != null) {
			defaultInit.getDomainInitialization().addAll(domainInits);
			defaultInit.getFunctionInitialization().addAll(functionInits);
			defaultInit.getAgentInitialization().addAll(agentInits);
		}
		result.setDefaultInitialState(defaultInit);
		return result;
	}

}

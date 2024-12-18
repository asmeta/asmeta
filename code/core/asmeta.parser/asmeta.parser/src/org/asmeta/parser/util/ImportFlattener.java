package org.asmeta.parser.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.Utility;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;
import asmeta.structure.Body;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.structure.ImportClause;
import asmeta.structure.Initialization;
import asmeta.structure.Signature;

/**
 * takes an ASM and recursively call the imports and build the entire set of
 * functions and domains
 * 
 * used in model advisor/ model checker - in altre parti non serve perche' ho controllato che
 * se import e poi import, vengono aggiunte a quelle dell'ultimo livello quindi
 * basta un import (almeno per avalla) AG 9/1/20
 */
public class ImportFlattener {

	private HashSet<Domain> domains;
	private ArrayList<Function> functions;
	private ArrayList<DomainDefinition> domainDefs;
	private ArrayList<DomainInitialization> domainInits;

	// where the asm is located in the file system
	private String folder;
	private Asm asm;

	/**
	 * 
	 * @param asm
	 * @param folder
	 */
	public ImportFlattener(Asm asm, String folder) {
		// check that exists and it is a directory
		assert Files.exists(Paths.get(folder)) && Files.isDirectory(Paths.get(folder));
		this.folder = folder;
		this.asm = asm;

		Signature signature = asm.getHeaderSection().getSignature();
		this.domains = new HashSet<Domain>(signature.getDomain());
		this.functions = new ArrayList<Function>(signature.getFunction());

		Body body = asm.getBodySection();
		this.domainDefs = new ArrayList<DomainDefinition>(body.getDomainDefinition());
		this.domainInits = new ArrayList<DomainInitialization>();
		Initialization defaultInit = asm.getDefaultInitialState();
		if (defaultInit != null) {
			domainInits.addAll(defaultInit.getDomainInitialization());
		}

	}

	/** visit the asm and imports and build the data for it */
	public void visit() {
		List<ImportClause> list = asm.getHeaderSection().getImportClause();
		Signature signature;
		Initialization defaultInitialState;
		Set<Asm> asmSet = new HashSet<Asm>();
		getAsms(list, asmSet, this.folder);
		for (Asm asm : asmSet) {
			signature = asm.getHeaderSection().getSignature();
			domains.addAll(signature.getDomain());
			domainDefs.addAll(asm.getBodySection().getDomainDefinition());
			defaultInitialState = asm.getDefaultInitialState();
			if (defaultInitialState != null) {
				domainInits.addAll(defaultInitialState.getDomainInitialization());
			}
			functions.addAll(signature.getFunction());
		}
	}

	/**
	 * It recursively visits the import clauses and memorises in "asmSet" the
	 * involved modules.
	 * 
	 * @param list   the list of imported clauses
	 * @param asmSet the set of imported ASMs
	 * @param folder the current parent folder of the visiting file or library
	 * 
	 */
	private void getAsms(List<ImportClause> list, Set<Asm> asmSet, String folder) {
		String moduleName;// name of the imported module
		File file;
		AsmCollection asms;
		Asm main;
		if (list != null) {
			for (ImportClause ic : list) {
				moduleName = ic.getModuleName();
				if (!moduleName.endsWith(Utility.STANDARD_LIBRARY_NAME) && !moduleName.endsWith(Utility.CTL_LIBRARY_NAME)
						&& !moduleName.endsWith(Utility.LTL_LIBRARY_NAME)) {
					// Import the file
					file = Utility.importFile(folder, ic);
					asms = null;
					try {
						asms = ASMParser.setUpReadAsm(file);
					} catch (Exception e) {
						e.printStackTrace();
						throw new Error(e);
					}
					main = asms.getMain();
					asmSet.add(main);
					getAsms(main.getHeaderSection().getImportClause(), asmSet, file.getParent());
				}
			}
		}
	}

	public Set<Domain> getDomains() {
		return domains;
	}

	public List<Function> getFunctions() {
		return functions;
	}

	public List<DomainDefinition> getDomainDefinitions() {
		return domainDefs;
	}

	public List<DomainInitialization> getDomainInitializations() {
		return domainInits;
	}

}

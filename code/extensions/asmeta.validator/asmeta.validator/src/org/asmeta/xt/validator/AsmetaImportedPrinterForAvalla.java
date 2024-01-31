package org.asmeta.xt.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.asmeta.avallaxt.avalla.Invariant;
import org.asmeta.avallaxt.avalla.Set;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.Defs;
import org.asmeta.parser.Utility;
import org.asmeta.parser.util.AsmPrinter;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.util.MonitoredFinder;
import org.asmeta.simulator.util.StandardLibrary;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.SharedFunction;
import asmeta.definitions.TemporalProperty;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.structure.Asm;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.ImportClause;
import asmeta.structure.Initialization;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;

//
// the asmeta printer for avallla in case of imported ASMETAS
// if it is imported:
// the main rule is not translated by considering the scenario?
// this is done only for the mainasm, not those that are imported
// the initialitaion is not traslated
// the step is not added
//
class AsmetaImportedPrinterForAvalla extends AsmetaPrinterForAvalla {

	AsmetaImportedPrinterForAvalla(File tempAsmPath, Path asmPath, AsmetaFromAvallaBuilder builder)
			throws FileNotFoundException {
		super(tempAsmPath, asmPath, builder);
	}

	public void visitMain(MacroDeclaration main) {
		// main is is not translated
		if (this.model.getMainrule() != null) {
			println("// main is not added - imported ASM");
		}
	}

	@Override
	protected void visitDef(RuleDeclaration dcl) {
		if (model.getMainrule() == dcl) {
			println("// main rule removed in the imported ASM");
			return;
		}
		super.visitDef(dcl);
	}

	@Override
	public void visitDefault(Initialization init) {
		println("// this ASM is imported, FunctionInitialization ignored");
	}

	@Override
	public void visitFunctions(Collection<Function> funcs) {
		// DO NOT ADD STEP
		super.visitDeclaredFunctions(funcs);
	}

	@Override
	public void visitFuncInits(Collection<FunctionInitialization> funcs) {
		println("// this ASM is imported, FunctionInitialization ignored");
		return;
	}
}
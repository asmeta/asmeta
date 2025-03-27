package org.asmeta.xt.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Collection;

import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Initialization;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;

//
// the asmeta printer for avallla in case of imported ASMETAS
// if it is imported:
// the main rule is not translated by considering the scenario?
// this is done only for the mainasm, not those that are imported
// the initialitaion is not traslated
// the step is not added
//
public class AsmetaImportedPrinterForAvalla extends AsmetaPrinterForAvalla {

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

	@Override
	public void visit(ChooseRule chooseRule) {
		// Do NOT translate the choose in a let if the asm is imported
		super.basicChooseVisit(chooseRule);
	}
}
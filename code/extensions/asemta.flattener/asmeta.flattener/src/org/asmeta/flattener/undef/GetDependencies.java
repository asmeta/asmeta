package org.asmeta.flattener.undef;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.structure.Body;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Initialization;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

public class GetDependencies extends ReflectiveVisitor<Void> {
	private Map<Function, Set<Function>> dependencies;
	private GetFunctionsInTerm gft;
	private Asm asm;
	private List<Function> funcs;

	private GetDependencies(Asm asm) {
		this.asm = asm;
		funcs = asm.getHeaderSection().getSignature().getFunction();
		dependencies = new HashMap<>();
		gft = new GetFunctionsInTerm();
	}

	public static Map<Function, Set<Function>> getDependencies(String asm) throws Exception {
		return getDependencies(ASMParser.setUpReadAsm(new File(asm)).getMain());
	}

	public static Map<Function, Set<Function>> getDependencies(Asm asm) {
		GetDependencies gd = new GetDependencies(asm);
		Body body = asm.getBodySection();
		for (FunctionDefinition fd : body.getFunctionDefinition()) {
			gd.getDeps(fd.getDefinedFunction(), fd.getBody());
		}
		for (RuleDeclaration rd : body.getRuleDeclaration()) {
			gd.visit(rd.getRuleBody());
		}
		for(Initialization is: asm.getInitialState()) {
			for (FunctionInitialization fi : is.getFunctionInitialization()) {
				gd.getDeps(fi.getInitializedFunction(), fi.getBody());
			}
		}
		return gd.dependencies;
	}

	public Void visit(BlockRule blockRule) {
		for (Rule b : blockRule.getRules()) {
			visit(b);
		}
		return null;
	}

	public Void visit(SeqRule seqRule) {
		for (Rule b : seqRule.getRules()) {
			visit(b);
		}
		return null;
	}

	public Void visit(CaseRule caseRule) {
		for (Rule branch : caseRule.getCaseBranches()) {
			visit(branch);
		}
		Rule otherwise = caseRule.getOtherwiseBranch();
		if (otherwise != null) {
			visit(otherwise);
		}
		return null;
	}

	public Void visit(ChooseRule chooseRule) {
		visit(chooseRule.getDoRule());
		Rule ifNone = chooseRule.getIfnone();
		if (ifNone != null) {
			visit(ifNone);
		}
		return null;
	}

	public Void visit(ConditionalRule conditionalRule) {
		visit(conditionalRule.getThenRule());
		Rule elseRule = conditionalRule.getElseRule();
		if (elseRule != null) {
			visit(elseRule);
		}
		return null;
	}

	public Void visit(ExtendRule extendRule) {
		visit(extendRule.getDoRule());
		return null;
	}

	public Void visit(ForallRule forallRule) {
		visit(forallRule.getDoRule());
		return null;
	}

	public Void visit(LetRule letRule) {
		visit(letRule.getInRule());
		return null;
	}

	public Void visit(MacroCallRule macroCallRule) {
		return null;
	}

	public Void visit(SkipRule skipRule) {
		return null;
	}

	public Void visit(TermAsRule termAsRule) {
		return null;
	}

	public Void visit(UpdateRule updateRule) {
		Term location = updateRule.getLocation();
		if (location instanceof LocationTerm) {
			Function func = ((LocationTerm) location).getFunction();
			getDeps(func, updateRule.getUpdatingTerm());
		}
		return null;
	}

	private void getDeps(Function func, Term term) {
		Set<Function> deps = null;
		if (!dependencies.containsKey(func)) {
			deps = new HashSet<>();
			dependencies.put(func, deps);
		} else {
			deps = dependencies.get(func);
		}
		List<Function> depFunctions = gft.visit(term);
		for (Function dp : depFunctions) {
			if (funcs.contains(dp) && dp != func) {
				deps.add(dp);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		// String asm =
		// "../asmeta.flattener.test/benchmarksFIDE2018/CoffeeVendingMachine.asm";
		String asm = "../asmeta.flattener.test/benchmarksFIDE2018/StereoacuityRaff5.asm";
		Map<Function, Set<Function>> deps = GetDependencies.getDependencies(asm);
		for (Entry<Function, Set<Function>> entry : deps.entrySet()) {
			System.out.println(entry.getKey().getName() + " " + entry.getValue());
		}
	}
}

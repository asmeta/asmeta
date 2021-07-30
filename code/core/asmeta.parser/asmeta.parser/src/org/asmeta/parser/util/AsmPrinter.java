package org.asmeta.parser.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.asmeta.parser.Defs;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.CompassionConstraint;
import asmeta.definitions.ControlledFunction;
import asmeta.definitions.CtlSpec;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.FairnessConstraint;
import asmeta.definitions.Function;
import asmeta.definitions.Invariant;
import asmeta.definitions.LtlSpec;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.Property;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.SharedFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.TemporalProperty;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.ReserveDomain;
import asmeta.definitions.domains.RuleDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.definitions.domains.TypeDomain;
import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
import asmeta.structure.Body;
import asmeta.structure.DomainDefinition;
import asmeta.structure.ExportClause;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Header;
import asmeta.structure.ImportClause;
import asmeta.structure.Initialization;
import asmeta.structure.Signature;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.transitionrules.turbotransitionrules.TurboCallRule;
import asmeta.transitionrules.turbotransitionrules.TurboDeclaration;
import asmeta.transitionrules.turbotransitionrules.TurboReturnRule;

public class AsmPrinter extends ReflectiveVisitor<Void> {
	AsmetaTermPrinter tp = AsmetaTermPrinter.getAsmetaTermPrinter(false);
	static final private String tabWidth = "    ";
	private int indentation = 0;
	private PrintWriter out;
	protected Asm model;
	boolean expand = true;

	public void close() {
		out.flush();
		out.close();
	}

	public AsmPrinter(String fileName) throws FileNotFoundException {
		this(new File(fileName));
	}

	public AsmPrinter(File file) throws FileNotFoundException {
		out = new PrintWriter(file);
	}

	public AsmPrinter(PrintWriter writer) {
		out = writer;
	}

	public void visitUnknown(Object object) {
		invokeMethod(object);
	}

	public void visitUnknownDcl(Object object) {
		invokeMethod(object, "visitDcl");
	}

	public void visit(Asm asm) {
		model = asm;
		String name = getAsmName();
		Header header = asm.getHeaderSection();
		Body body = asm.getBodySection();
		Initialization init = asm.getDefaultInitialState();
		if (model.getMainrule() == null) 
			println("module " + name);
		else
			println("asm " + name);
		visit(header);
		println();
		visit(body);
		MacroDeclaration main = asm.getMainrule();
		if (main != null) {
			indent();
			visitMain(main);
			unIndent();
		}
		visitDefault(init);
		model = null;
	}

	public String getAsmName() {
		return model.getName();
	}

	public void visit(Term term) {
		tp.visit(term);
	}

	public void visit(Body body) {
		Collection<Property> property = body.getProperty();
		Collection<DomainDefinition> domains = body.getDomainDefinition();
		Collection<FunctionDefinition> funcs = body.getFunctionDefinition();
		Collection<RuleDeclaration> rules = body.getRuleDeclaration();
		println("definitions:");
		println();
		indent();
		if (domains.size() > 0) {
			visitDomDefs(domains);
			println();
		}
		if (funcs.size() > 0) {
			visitFunDefs(funcs);
			println();
		}
		if (rules.size() > 0) {
			visitRuleDefs(rules);
			println();
		}
		visitFairness(body.getFairnessConstraint());// TODO
		visitProperties(property);
		unIndent();
	}

	protected void visitFairness(List<FairnessConstraint> fairness) {
		for (FairnessConstraint f : fairness) {
			if (f instanceof CompassionConstraint) {
				CompassionConstraint c = ((CompassionConstraint) f);
				println("COMPASSION (" + tp.visit(c.getP()) + "," + tp.visit(c.getQ()) + ")");
			}

		}
	}

	protected void visitProperties(Collection<Property> property) {
		if (property.size() > 0) {
			Collection<Invariant> invariants = new ArrayList<Invariant>();
			Collection<LtlSpec> ltlSpecs = new ArrayList<LtlSpec>();
			Collection<CtlSpec> ctlSpecs = new ArrayList<CtlSpec>();
			for (Property p : property) {
				if (p instanceof Invariant) {
					invariants.add((Invariant) p);
				} else if (p instanceof LtlSpec) {
					ltlSpecs.add((LtlSpec) p);
				} else {
					ctlSpecs.add((CtlSpec) p);
				}
			}
			visitInvariants(invariants);
			visitTemporalProperties(ltlSpecs);
			visitTemporalProperties(ctlSpecs);
		}
	}

	protected void visitTemporalProperties(Collection<? extends TemporalProperty> properties) {
		for (TemporalProperty property : properties) {
			if (property instanceof LtlSpec) {
				print("LTLSPEC ");
			} else {
				print("CTLSPEC ");
			}
			String name = property.getName();
			if (name != null) {
				print(name + ": ");
			}
			println(tp.visit(property.getBody()));
		}
	}

	protected void visitInvariants(Collection<Invariant> invariants) {
		for (Invariant invariant : invariants) {
			print("invariant ");
			String name = invariant.getName();
			if (name != null && !name.equals(""))
				print(name + " ");
			print("over ");
			boolean needComma = false;
			List<Function> constrFuncs = invariant.getConstrainedFunction();
			if (!constrFuncs.isEmpty()) {
				Iterator<Function> i = constrFuncs.iterator();
				print(i.next().getName());
				while (i.hasNext()) {
					print(", " + i.next().getName());
				}
				needComma = true;
			}
			List<Domain> constrDoms = invariant.getConstrainedDomain();
			if (!constrDoms.isEmpty()) {
				Iterator<Domain> i = constrDoms.iterator();
				if (needComma)
					print(", ");
				print(i.next().getName());
				while (i.hasNext()) {
					print(", " + i.next().getName());
				}
				needComma = true;
			}
			List<RuleDeclaration> constrRules = invariant.getConstrainedRule();
			if (!constrRules.isEmpty()) {
				Iterator<RuleDeclaration> i = constrRules.iterator();
				if (needComma)
					print(", ");
				print(i.next().getName());
				while (i.hasNext()) {
					print(", " + i.next().getName());
				}
			}
			print(": ");
			visitInvariantBody(invariant);
		}
	}

	protected void visitInvariantBody(Invariant invariant) {
		println(tp.visit(invariant.getBody()));
	}

	public void visitFunDefs(Collection<FunctionDefinition> funcs) {
		if (funcs != null) {
			for (FunctionDefinition def : funcs) {
				// PA 2014/01/31 commentato l'if. Perche' non posso definire una funzione in un
				// Function func = def.getDefinedFunction();
				// Asm asm = Defs.getAsm(func);
				// modulo diverso?
				// if (asm == model) {
				visitDef(def);
				// }
			}
		}
	}

	public void visitDef(FunctionDefinition def) {
		String name = def.getDefinedFunction().getName();
		List<VariableTerm> vars = def.getVariable();
		Term term = def.getBody();
		String termStr = tp.visit(term);
		print("function " + name);
		visitDclVars(vars, "(", ")");
		println(" = " + termStr);
	}

	public void visitDomDefs(Collection<DomainDefinition> domains) {
		if (domains != null) {
			for (DomainDefinition def : domains) {
				ConcreteDomain dom = def.getDefinedDomain();
				Asm asm = Defs.getAsm(dom);
				if (asm == model) {
					visitDef(def);
				} else {
					println("// domain " + def.getDefinedDomain().getName() + " not printed because it does not belong to this asm");
				}
			}
		}
	}

	public void visitDef(DomainDefinition def) {
		String name = def.getDefinedDomain().getName();
		Term term = def.getBody();
		String termStr = tp.visit(term);
		if (def.getDefinedDomain().getIsDynamic()) {
			print("dynamic ");
		}
		println("domain " + name + " = " + termStr);
	}

	public void visit(IterativeWhileRule rule) {
		Term guard = rule.getGuard();
		Rule r = rule.getRule();
		print("while ");
		print(tp.visit(guard));
		print(" do ");
		visit(r);
	}

	public void visitRuleDefs(Collection<RuleDeclaration> defs) {
		if (defs != null) {
			RuleDeclaration[] rules = sortRuleDeclarations(defs);
			for (RuleDeclaration rule : rules) {
				Asm asm = Defs.getAsm(rule);
				if (asm == model) {
					// PA: 207/05/19
					// control added to avoid to print the
					// main rule twice
					if (rule != asm.getMainrule()) {
						visitDef(rule);
					}
				}
			}
		}
	}

	public RuleDeclaration[] sortRuleDeclarations(Collection<RuleDeclaration> ruleDcls) {
		RuleDeclaration[] rules = ruleDcls.toArray(new RuleDeclaration[0]);
		/*
		 * Arrays.sort(rules, new Comparator<RuleDeclaration>() {
		 * 
		 * @Override public int compare(RuleDeclaration o1, RuleDeclaration o2) { String
		 * name1 = o1.getName(); String name2 = o2.getName(); return
		 * name1.compareTo(name2); }
		 * 
		 * });
		 */
		return rules;
	}

	/** print a rule declaration (not main rule) **/
	public void visitDef(RuleDeclaration dcl) {
		// assert model.getMainrule() != dcl;
		if (dcl instanceof MacroDeclaration) {
			print("macro ");
		} else {
			print("turbo ");
		}
		visitDef2(dcl);
	}

	void visitDef2(RuleDeclaration dcl) {
		String name = dcl.getName();
		List<VariableTerm> vars = dcl.getVariable();
		Rule rule = dcl.getRuleBody();
		assert rule != null;
		print("rule " + name);
		visitDclVars(vars, "(", ")");
		println(" =");
		indent();
		visit(rule);
		println();
		unIndent();
	}

	public void visitMain(MacroDeclaration main) {
		print("main ");
		visitDef2(main);
	}

	public void visit(Rule rule) {
		visitUnknown(rule);
	}

	public void visit(SkipRule rule) {
		println("skip");
	}

	public void visit(ConditionalRule rule) {
		Term cond = rule.getGuard();
		Rule thenRule = rule.getThenRule();
		Rule elseRule = rule.getElseRule();
		String condStr = tp.visit(cond);
		println("if " + condStr + " then");
		indent();
		visit(thenRule);
		if (elseRule != null) {
			unIndent();
			println("else ");
			indent();
			visit(elseRule);
		}
		unIndent();
		println("endif");
	}

	public void visit(BlockRule rule) {
		List<Rule> rules = rule.getRules();
		println("par");
		indent();
		visitRules(rules);
		unIndent();
		println("endpar");
	}

	public void visit(SeqRule rule) {
		List<Rule> rules = rule.getRules();
		println("seq");
		indent();
		visitRules(rules);
		unIndent();
		println("endseq");
	}

	public void visitRules(List<Rule> rules) {
		if (rules != null) {
			for (Rule rule : rules) {
				visit(rule);
			}
		}
	}

	public void visit(UpdateRule rule) {
		Term location = rule.getLocation();
		assert (location != null);
		Term term = rule.getUpdatingTerm();
		assert (term != null);
		String locationStr = tp.visit(location);
		String termStr = tp.visit(term);
		println(locationStr + " := " + termStr);
	}

	public void visit(ExtendRule rule) {
		Collection<VariableTerm> vars = rule.getBoundVar();
		Domain domain = rule.getExtendedDomain();
		Rule rule2 = rule.getDoRule();
		String varsStr = tp.visit(vars, "", "");
		print("extend ");
		visit(domain);
		print(" with ");
		print(varsStr);
		println(" do");
		indent();
		visit(rule2);
		unIndent();
	}

	public void visit(LetRule rule) {
		List<VariableTerm> vars = rule.getVariable();
		List<Term> terms = rule.getInitExpression();
		Rule rule2 = rule.getInRule();
		print("let (");
		visit(vars, terms, "=");
		println(") in");
		indent();
		visit(rule2);
		unIndent();
		println("endlet");
	}

	public void visit(ChooseRule rule) {
		List<VariableTerm> vars = rule.getVariable();
		List<Term> domains = rule.getRanges();
		Term cond = rule.getGuard();
		Rule rule2 = rule.getDoRule();
		Rule rule3 = rule.getIfnone();
		print("choose ");
		visit(vars, domains, "in");
		if (cond != null) {
			String condStr = tp.visit(cond);
			print(" with " + condStr);
		}
		println(" do");
		indent();
		visit(rule2);
		unIndent();
		if (rule3 != null) {
			unIndent();
			println("ifnone");
			indent();
			visit(rule3);
			unIndent();
		}
	}

	public void visit(ForallRule rule) {
		List<VariableTerm> vars = rule.getVariable();
		List<Term> domains = rule.getRanges();
		Term cond = rule.getGuard();
		Rule rule2 = rule.getDoRule();
		print("forall ");
		visit(vars, domains, "in");
		if (cond != null) {
			String condStr = tp.visit(cond);
			print(" with " + condStr);
		}
		println(" do");
		indent();
		visit(rule2);
		unIndent();
	}

	public void visit(CaseRule rule) {
		// indent();
		print("switch ");
		println(tp.visit(rule.getTerm()));
		List<Term> cases = rule.getCaseTerm();
		List<Rule> rules = rule.getCaseBranches();
		indent();
		for (int i = 0; i < cases.size(); i++) {
			print("case ");
			println(tp.visit(cases.get(i)) + ":");
			indent();
			visit(rules.get(i));
			unIndent();
		}
		unIndent();
		Rule rOth = rule.getOtherwiseBranch();
		if (rOth != null) {
			println("otherwise");
			indent();
			visit(rOth);
			unIndent();
		}
		println("endswitch");
		// unIndent();
	}

	public void visit(MacroCallRule rule) {
		MacroDeclaration dcl = rule.getCalledMacro();
		String name = dcl.getName();
		List<Term> params = rule.getParameters();
		String paramsStr = tp.visit(params, "[", "]");
		println(name + paramsStr);
	}

	public void visit(TurboCallRule rule) {
		TurboDeclaration dcl = rule.getCalledRule();
		String name = dcl.getName();
		List<Term> params = rule.getParameters();
		String paramsStr = tp.visit(params, "(", ")");
		println(name + paramsStr);
	}

	public void visit(TurboReturnRule rule) {
		Term loc = rule.getLocation();
		tp.visit(loc);
		print("<-");
		visit(rule.getUpdateRule());
	}

	public void visit(TermAsRule rule) {
		Term term = rule.getTerm();
		String termStr = tp.visit(term);
		print(termStr);
		List<Term> params = rule.getParameters();
		if (params.size() > 0) {
			String paramsStr = tp.visit(params, "[", "]");
			println(paramsStr);
		} else {
			println();
		}
	}

	public void visit(List<VariableTerm> vars, List<Term> terms, String delim) {
		if (vars != null) {
			assert vars.size() > 0;
			VariableTerm var = vars.get(0);
			Term term = terms.get(0);
			String varStr = var.getName();
			String termStr = tp.visit(term);
			print(varStr + " " + delim + " " + termStr);
			assert vars.size() == terms.size();
			for (int i = 1; i < vars.size(); i++) {
				var = vars.get(i);
				term = terms.get(i);
				varStr = var.getName();
				termStr = tp.visit(term);
				print(", " + varStr + " " + delim + " " + termStr);
			}
		}
	}

	public void visitDclVars(Collection<VariableTerm> vars, String start, String stop) {
		VariableTerm[] array = vars.toArray(new VariableTerm[0]);
		List<VariableTerm> list = Arrays.asList(array);
		visitDclVars(list, start, stop);
	}

	public void visitDclVars(List<VariableTerm> vars, String start, String stop) {
		if (vars != null && vars.size() > 0) {
			print(start);
			VariableTerm var = vars.get(0);
			String name = var.getName();
			Domain domain = var.getDomain();
			print(name + " in ");
			visit(domain);
			for (int i = 1; i < vars.size(); i++) {
				var = vars.get(i);
				name = var.getName();
				domain = var.getDomain();
				print(", " + name + " in ");
				visit(domain);
			}
			print(stop);
		}
	}

	// print the initialization part in the init regarding the functions and agents
	protected void visitFuntionsAgents(Initialization init) {
		// TODO init.getDomainInitialization()
		assert (init != null);
		// functions
		Collection<FunctionInitialization> funcs = init.getFunctionInitialization();
		visitFuncInits(funcs);
		// agents
		EList<AgentInitialization> agents = init.getAgentInitialization();
		for (AgentInitialization agent : agents) {
			print("agent " + agent.getDomain().getName() + ":\n");
			indent();
			print(agent.getProgram().getCalledMacro().getName() + "[]\n\n");
			unIndent();
		}
		unIndent();
	}

	public void visitDefault(Initialization init) {
		if (init != null) {
			// print the header
			println("default init " + init.getName() + ":");
			indent();
			visitFuntionsAgents(init);
		}
	}

	public void visitFuncInits(Collection<FunctionInitialization> funcs) {
		if (funcs != null) {
			for (FunctionInitialization init : funcs) {
				DynamicFunction func = init.getInitializedFunction();
				Asm asm = Defs.getAsm(func);
				if (asm == model) {
					println("// this function does not belong to this asm, but it can be initialized ");
				}
				visitInit(init);
			}
		}
	}

	public void visitInit(FunctionInitialization init) {
		String name = init.getInitializedFunction().getName();
		List<VariableTerm> vars = init.getVariable();
		Term term = init.getBody();
		String termStr = tp.visit(term);
		print("function " + name);
		visitDclVars(vars, "(", ")");
		println(" = " + termStr);
	}

	public void visit(Header header) {
		Collection<ImportClause> importClause = header.getImportClause();
		ExportClause exportClause = header.getExportClause();
		Signature signature = header.getSignature();
		visit(importClause);
		visit(exportClause);
		println();
		visit(signature);
	}

	public void visit(Collection<ImportClause> imports) {
		if (imports != null) {
			for (ImportClause importClause : imports) {
				String name = importClause.getModuleName();
				println("import " + name);
			}
		}
	}

	public void visit(ExportClause exportClause) {
		if (exportClause != null) {
			// TODO in the future
			println("export *");
		}
	}

	public void visit(Signature signature) {
		Collection<Domain> domains = signature.getDomain();
		Collection<Function> functions = signature.getFunction();
		println("signature:");
		indent();
		visitDomains(domains);
		println();
		visitFunctions(functions);
		unIndent();
	}

	public void visitFunctions(Collection<Function> funcs) {
		if (funcs != null) {
			Function[] functions = sortFunctions(funcs);
			for (Function function : functions) {
				Asm asm = Defs.getAsm(function);
				// check that the function belongs to this model (not those imported)
				if (asm == model) {
					visitDcl(function);
				}
			}
		}
	}

	private Function[] sortFunctions(Collection<Function> funcs) {
		Function[] functions = funcs.toArray(new Function[0]);
		/*
		 * Arrays.sort(functions, new Comparator<Function>() {
		 * 
		 * @Override public int compare(Function arg0, Function arg1) { int[][] compare
		 * = { {+0, -1, -1, -1, -1, -1, -1}, {+1, +0, -1, -1, -1, -1, -1}, {+1, +1, +0,
		 * -1, -1, -1, -1}, {+1, +1, +1, +0, -1, -1, -1}, {+1, +1, +1, +1, +0, -1, -1},
		 * {+1, +1, +1, +1, +1, +0, -1}, {+1, +1, +1, +1, +1, +1, +0} }; int i =
		 * order(arg0); int j = order(arg1); int result = compare[i][j]; return result
		 * == 0 ? compare2(arg0, arg1) : result; }
		 * 
		 * int order(Function func) { if (func instanceof MonitoredFunction) { return 0;
		 * } else if (func instanceof SharedFunction) { return 1; } else if (func
		 * instanceof ControlledFunction) { return 2; } else if (func instanceof
		 * OutFunction) { return 3; } else if (func instanceof StaticFunction) { return
		 * 4; } else if (func instanceof DerivedFunction) { return 5; } else { return 6;
		 * } }
		 * 
		 * int compare2(Function arg0, Function arg1) { String name0 = arg0.getName();
		 * String name1 = arg1.getName(); return name0.compareTo(name1); }
		 * 
		 * });
		 */
		return functions;
	}

	public void visitDcl(Function function) {
		String name = function.getName();
		Domain domain = function.getDomain();
		Domain codomain = function.getCodomain();
		visitUnknownDcl(function);
		print(name + ": ");
		if (domain != null) {
			visit(domain);
			print(" -> ");
		}
		visit(codomain);
		println();
	}

	public void visit(Domain domain) {
		if (domain instanceof StructuredTd) {
			visitUnknown(domain);
		} else {
			String name = domain.getName();
			print(name);
		}
	}

	public void visit(RuleDomain domain) {
		List<Domain> domains = domain.getDomains();
		print("Rule");
		visit(domains);
	}

	public void visit(ProductDomain domain) {
		List<Domain> domains = domain.getDomains();
		print("Prod");
		visit(domains);
	}

	public void visit(SequenceDomain domain) {
		Domain base = domain.getDomain();
		print("Seq(");
		visit(base);
		print(")");
	}

	public void visit(PowersetDomain domain) {
		Domain base = domain.getBaseDomain();
		print("Powerset(");
		visit(base);
		print(")");
	}

	public void visit(BagDomain domain) {
		Domain base = domain.getDomain();
		print("Bag(");
		visit(base);
		print(")");
	}

	public void visit(MapDomain domain) {
		Domain source = domain.getSourceDomain();
		Domain target = domain.getTargetDomain();
		print("Map(");
		visit(source);
		print(", ");
		visit(target);
		print(")");
	}

	/**
	 * visit the domain list, if it is empty do nothing
	 * 
	 * @param domains
	 */
	public void visit(List<Domain> domains) {
		if (domains != null & domains.size() > 0) {
			print("(");
			int i;
			for (i = 0; i < domains.size() - 1; i++) {
				visit(domains.get(i));
				print(", ");
			}
			visit(domains.get(i));
			print(")");
		}
	}

	public void visitDcl(StaticFunction function) {
		print("static ");
	}

	public void visitDcl(OutFunction function) {
		print("out ");
	}

	public void visitDcl(MonitoredFunction function) {
		print("monitored ");
	}

	public void visitDcl(SharedFunction function) {
		print("shared ");
	}

	public void visitDcl(ControlledFunction function) {
		print("controlled ");
	}

	public void visitDcl(DerivedFunction function) {
		print("derived ");
	}

	public void visitDomains(Collection<Domain> doms) {
		if (doms != null) {
			Domain[] domains = sortDomains(doms);
			for (Domain domain : domains) {
				Asm asm = Defs.getAsm(domain);
				if (asm == model) {
					visitDcl(domain);
				}
			}
		}
	}

	private Domain[] sortDomains(Collection<Domain> doms) {
		Domain[] domains = doms.toArray(new Domain[0]);
		/*
		 * Arrays.sort(domains, new Comparator<Domain>() {
		 * 
		 * @Override public int compare(Domain arg0, Domain arg1) { String name0 =
		 * arg0.getName(); String name1 = arg1.getName(); if (arg0 instanceof
		 * AbstractTd) { if (arg1 instanceof AbstractTd) { return
		 * name0.compareTo(name1); } return -1; } else if (arg0 instanceof
		 * ConcreteDomain) { if (arg1 instanceof AbstractTd) { return 1; } if (arg1
		 * instanceof ConcreteDomain) { return name0.compareTo(name1); } return -1; }
		 * else if (arg0 instanceof EnumTd) { if (arg1 instanceof AbstractTd || arg1
		 * instanceof ConcreteDomain) { return 1; } return name0.compareTo(name1); }
		 * else { return name0.compareTo(name1); } }
		 * 
		 * });
		 */
		return domains;
	}

	public void visitDcl(Domain domain) {
		if (domain instanceof AbstractTd || domain instanceof ConcreteDomain || domain instanceof EnumTd) {
			if (!(domain instanceof AgentDomain || domain instanceof ReserveDomain)) {
				visitUnknownDcl(domain);
			}
		}
	}

	public void visitDcl(AbstractTd domain) {
		String name = domain.getName();
		if (domain.getIsDynamic()) {
			print("dynamic ");
		}
		println("abstract domain " + name);
	}

	public void visitDcl(ConcreteDomain domain) {
		String name = domain.getName();
		TypeDomain typeDomain = domain.getTypeDomain();
		if (domain.getIsDynamic()) {
			print("dynamic ");
		}
		print("domain " + name + " subsetof ");
		visit(typeDomain);
		println();
	}

	public void visitDcl(EnumTd enumTd) {
		String name = enumTd.getName();
		Collection<EnumElement> elements = enumTd.getElement();
		print("enum domain " + name + " = ");
		visitDcl(elements);
	}

	public void visitDcl(Collection<EnumElement> enums) {
		if (enums != null) {
			print("{");
			EnumElement[] elements = enums.toArray(new EnumElement[0]);
			EnumElement enumElement = elements[0];
			String name = enumElement.getSymbol();
			print(name);
			for (int i = 1; i < elements.length; i++) {
				enumElement = elements[i];
				name = enumElement.getSymbol();
				print(" | " + name);
			}
			println("}");
		}
	}

	protected void print(String msg) {
		if (expand) {
			expandWhites();
			expand = false;
		}
		out.print(msg);
	}

	protected void println(String msg) {
		print(msg);
		println();
	}

	protected void println() {
		out.println();
		expand = true;
	}

	// increases the indentation
	protected void indent() {
		indentation++;
	}

	protected void unIndent() {
		indentation--;
	}

	void expandWhites() {
		String s = "";
		for (int i = 0; i < indentation; i++) {
			s += tabWidth;
		}
		out.print(s);
	}

	public void visitTerm(Term t) {
		out.print(tp.visit(t));
	}
}

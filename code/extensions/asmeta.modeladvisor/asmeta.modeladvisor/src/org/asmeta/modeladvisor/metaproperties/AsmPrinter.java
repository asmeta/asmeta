package org.asmeta.modeladvisor.metaproperties;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.asmeta.nuxmv.Environment;
import org.asmeta.parser.Defs;
import org.asmeta.parser.util.AsmetaTermPrinter;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.OutFunction;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.SharedFunction;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RuleDomain;
import asmeta.definitions.domains.SequenceDomain;
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
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

//class AsmetaToSMV extends ReflectiveVisitor {
class AsmPrinter extends org.asmeta.parser.util.ReflectiveVisitor {

	AsmetaTermPrinter tp;
	String tabWidth = "    ";
	int indentation = 0;
	Asm model;
	boolean expand = true;
	
	public AsmPrinter(Environment env) {
		//tp = new AsmetaTermPrinter(env);
		tp = AsmetaTermPrinter.getAsmetaTermPrinter(false);
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
		println("asm " + name);
		visit(header);
		println();
		visit(body);
		MacroDeclaration main = asm.getMainrule();
		if (main != null)
			visitMain(main);
		visitDefault(init);
		model = null;
	}

	public String getAsmName() {
		return model.getName();
	}

	public void visit(Body body) {
		// TODO body.getInvariant()
		Collection<DomainDefinition> domains = body.getDomainDefinition();
		Collection<FunctionDefinition> funcs = body.getFunctionDefinition();
		Collection<RuleDeclaration> rules = body.getRuleDeclaration();
		println("definitions:");
		println();
		indent();
		visitDomDefs(domains);
		println();
		visitFunDefs(funcs);
		println();
		visitRuleDefs(rules);
		unIndent();
	}

	public void visitFunDefs(Collection<FunctionDefinition> funcs) {
		if (funcs != null) {
			for (FunctionDefinition def : funcs) {
				Function func = def.getDefinedFunction();
				Asm asm = Defs.getAsm(func);
				if (asm == model) {
					visitDef(def);
				}
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
				}
			}
		}
	}

	public void visitDef(DomainDefinition def) {
		String name = def.getDefinedDomain().getName();
		Term term = def.getBody();
		String termStr = tp.visit(term);
		if ( def.getDefinedDomain().getIsDynamic()) print("dynamic ");
		println("domain " + name + " = " + termStr);
	}

	public void visitRuleDefs(Collection<RuleDeclaration> defs) {
		if (defs != null) {
			RuleDeclaration[] rules = sortRuleDeclarations(defs);
			for (RuleDeclaration rule : rules) {
				Asm asm = Defs.getAsm(rule);
				if (asm == model) {
					visitDef(rule);
				}
			}
		}
	}

	public RuleDeclaration[] sortRuleDeclarations(Collection<RuleDeclaration> ruleDcls) {
		RuleDeclaration[] rules = ruleDcls.toArray(new RuleDeclaration[0]);
		/*
		 * Arrays.sort(rules, new Comparator<RuleDeclaration>() {
		 * 
		 * @Override public int compare(RuleDeclaration o1, RuleDeclaration o2)
		 * { String name1 = o1.getName(); String name2 = o2.getName(); return
		 * name1.compareTo(name2); }
		 * 
		 * });
		 */
		return rules;
	}

	public void visitDef(RuleDeclaration dcl) {
		// if (dcl == model.getMainrule()) {
		// visitMain();
		// } else
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
	
	public String visitCondRule(ConditionalRule condRule) {
		StringBuilder sb = new StringBuilder();
		Term cond = condRule.getGuard();
		sb.append(print("if " + tp.visit(cond) + " then"));
		if (condRule.getElseRule() != null) {
			sb.append(print(" else"));
		}
		sb.append(print(" endif"));
		return sb.toString();
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
	
	public String visitCaseHeader(CaseRule caseRule) {
		return "switch (" + tp.visit(caseRule.getTerm()) + ")";
	}
	
	public String visitCondHeader(ConditionalRule rule) {
		return "if (" + tp.visit(rule.getGuard()) + ")";
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
		//Collection<VariableTerm> vars = rule.getBoundVar();
		Domain domain = rule.getExtendedDomain();
		Rule rule2 = rule.getDoRule();
		//String varsStr = tp.visit(vars, "", "");
		print("extend ");
		visit(domain);
		print(" with ");
		//print(varsStr);
		println(" do");
		indent();
		visit(rule2);
		unIndent();
	}

	public String visit(LetRule rule) {
		StringBuilder str = new StringBuilder();
		List<VariableTerm> vars = rule.getVariable();
		List<Term> terms = rule.getInitExpression();
		//Rule rule2 = rule.getInRule();
		str.append(print("let ("));
		str.append(visit(vars, terms, "="));
		str.append(println(") in"));
		indent();
		//str.append(visit(rule2));
		unIndent();
		str.append(println("endlet"));
		return str.toString();
	}
	
	public String visitChooseRuleHeader(ChooseRule chooseRule){
		StringBuilder str = new StringBuilder();
		List<VariableTerm> vars = chooseRule.getVariable();
		List<Term> domains = chooseRule.getRanges();
		Term cond = chooseRule.getGuard();
		str.append(print("choose "));
		str.append(visit(vars, domains, "in"));
		if (cond != null) {
			String condStr = tp.visit(cond);
			str.append(print(" with " + condStr));
		}
		return str.toString();
	}
	
	public String visitForallRuleHeader(ForallRule forallRule){
		StringBuilder str = new StringBuilder();
		List<VariableTerm> vars = forallRule.getVariable();
		List<Term> domains = forallRule.getRanges();
		Term cond = forallRule.getGuard();
		str.append(print("forall "));
		str.append(visit(vars, domains, "in"));
		if (cond != null) {
			String condStr = tp.visit(cond);
			str.append(print(" with " + condStr));
		}
		return str.toString();
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

//	public String visit(CaseRule rule) {
//		StringBuilder str = new StringBuilder();
//		indent();
//		str.append(print("switch "));
//		tp.visit(rule.getTerm());
//		List<Term> cases = rule.getCaseTerm();
//		List<Rule> rules = rule.getCaseBranches();
//		for (int i = 0; i < cases.size(); i++) {
//			str.append(print("case "));
//			str.append(tp.visit(cases.get(i)));
//			str.append(print(":\n"));
//			str.append(visit(rules.get(i)));
//		}
//		Rule rOth = rule.getOtherwiseBranch();
//		if (rOth != null) {
//			str.append(print("otherwise "));
//			str.append(visit(rOth));
//		}
//		str.append(print("endswitch"));
//		unIndent();
//		return str.toString();
//	}

//	public String visit(MacroCallRule rule) {
//		MacroDeclaration dcl = rule.getCalledMacro();
//		String name = dcl.getName();
//		List<Term> params = rule.getParameters();
//	//	String paramsStr = tp.visit(params, "[", "]");
//		return println(name + paramsStr);
//	}
//
//	
//	public String visit(TurboCallRule rule) {
//		TurboDeclaration dcl = rule.getCalledRule();
//		String name = dcl.getName();
//		List<Term> params = rule.getParameters();
//	//	String paramsStr = tp.visit(params, "(", ")");
//		return println(name + paramsStr);
//	}

//	public String visit(TurboReturnRule rule) {
//		Term loc = rule.getLocation();
//		tp.visit(loc);
//		return print("<-") +
//		visit(rule.getUpdateRule());
//	}

//	public String visit(TermAsRule rule) {
//		StringBuilder str = new StringBuilder();
//		Term term = rule.getTerm();
//		String termStr = tp.visit(term);
//		str.append(print(termStr));
//		List<Term> params = rule.getParameters();
//		if (params.size() > 0) {
//		//	String paramsStr = tp.visit(params, "[", "]");
//			str.append(println(paramsStr));
//		} else {
//			str.append(println());
//		}
//		return str.toString();
//	}

	public String visit(List<VariableTerm> vars, List<Term> terms, String delim) {
		StringBuilder str = new StringBuilder();
		if (vars != null) {
			VariableTerm var = vars.get(0);
			Term term = terms.get(0);
			String varStr = var.getName();
			String termStr = tp.visit(term);
			str.append(print(varStr + " " + delim + " " + termStr));
			for (int i = 1; i < vars.size(); i++) {
				var = vars.get(i);
				term = terms.get(i);
				varStr = var.getName();
				termStr = tp.visit(term);
				str.append(print(", " + varStr + " " + delim + " " + termStr));
			}
		}
		return str.toString();
	}

	public String visitDclVars(Collection<VariableTerm> vars, String start,
			String stop) {
		VariableTerm[] array = vars.toArray(new VariableTerm[0]);
		List<VariableTerm> list = Arrays.asList(array);
		return visitDclVars(list, start, stop);
	}

	public String visitDclVars(List<VariableTerm> vars, String start, String stop) {
		StringBuilder str = new StringBuilder();
		if (vars != null && vars.size() > 0) {
			str.append(print(start));
			VariableTerm var = vars.get(0);
			String name = var.getName();
			Domain domain = var.getDomain();
			str.append(print(name + " in "));
			str.append(visit(domain));
			for (int i = 1; i < vars.size(); i++) {
				var = vars.get(i);
				name = var.getName();
				domain = var.getDomain();
				str.append(print(", " + name + " in "));
				str.append(visit(domain));
			}
			str.append(print(stop));
		}
		return str.toString();
	}

	public String visit(Initialization init) {
		StringBuilder str = new StringBuilder();
		// TODO init.getAgentInitialization()
		// TODO init.getDomainInitialization()
		if (init != null) {
			String name = init.getName();
			str.append(println("init " + name + ":"));
			indent();
			Collection<FunctionInitialization> funcs = init
					.getFunctionInitialization();
			str.append(visitFuncInits(funcs));
			unIndent();
		}
		return str.toString();
	}

	public String visitDefault(Initialization init) {
		StringBuilder str = new StringBuilder();
		if (init != null) {
			str.append(print("default "));
			str.append(visit(init));
		}
		return str.toString();
	}

	public String visitFuncInits(Collection<FunctionInitialization> funcs) {
		StringBuilder str = new StringBuilder();
		if (funcs != null) {
			for (FunctionInitialization init : funcs) {
				DynamicFunction func = init.getInitializedFunction();
				Asm asm = Defs.getAsm(func);
				if (asm == model) {
					str.append(visitInit(init));
				}
			}
		}
		return str.toString();
	}

	public String visitInit(FunctionInitialization init) {
		StringBuilder str = new StringBuilder();
		String name = init.getInitializedFunction().getName();
		List<VariableTerm> vars = init.getVariable();
		Term term = init.getBody();
		String termStr = tp.visit(term);
		str.append(print("function " + name));
		str.append(visitDclVars(vars, "(", ")"));
		str.append(println(" = " + termStr));
		return str.toString();
	}

	public String visit(Header header) {
		StringBuilder str = new StringBuilder();
		Collection<ImportClause> importClause = header.getImportClause();
		//ExportClause exportClause = header.getExportClause();
		Signature signature = header.getSignature();
		str.append(visit(importClause));
		//str.append(visit(exportClause));
		str.append(println());
		str.append(visit(signature));
		return str.toString();
	}

	public String visit(Collection<ImportClause> imports) {
		StringBuilder str = new StringBuilder();
		if (imports != null) {
			for (ImportClause importClause : imports) {
				String name = importClause.getModuleName();
				str.append(println("import " + name));
			}
		}
		return str.toString();
	}

	public void visit(ExportClause exportClause) {
		// TODO
	}

//	public String visit(Signature signature) {
//		StringBuilder str = new StringBuilder();
//		Collection<Domain> domains = signature.getDomain();
//		Collection<Function> functions = signature.getFunction();
//		str.append(println("signature:"));
//		indent();
//		str.append(visitDomains(domains));
//		str.append(println());
//		str.append(visitFunctions(functions));
//		unIndent();
//		return str.toString();
//	}

//	public String visitFunctions(Collection<Function> funcs) {
//		StringBuilder str = new StringBuilder();
//		if (funcs != null) {
//			Function[] functions = sort(funcs);
//			for (Function function : functions) {
//				Asm asm = Defs.getAsm(function);
//				if (asm == model) {
//					str.append(visitDcl(function));
//				}
//			}
//		}
//		return str.toString();
//	}

	private Function[] sortFunctions(Collection<Function> funcs) {
		Function[] functions = funcs.toArray(new Function[0]);
		/*
		 * Arrays.sort(functions, new Comparator<Function>() {
		 * 
		 * @Override public int compare(Function arg0, Function arg1) { int[][]
		 * compare = { {+0, -1, -1, -1, -1, -1, -1}, {+1, +0, -1, -1, -1, -1,
		 * -1}, {+1, +1, +0, -1, -1, -1, -1}, {+1, +1, +1, +0, -1, -1, -1}, {+1,
		 * +1, +1, +1, +0, -1, -1}, {+1, +1, +1, +1, +1, +0, -1}, {+1, +1, +1,
		 * +1, +1, +1, +0} }; int i = order(arg0); int j = order(arg1); int
		 * result = compare[i][j]; return result == 0 ? compare2(arg0, arg1) :
		 * result; }
		 * 
		 * int order(Function func) { if (func instanceof MonitoredFunction) {
		 * return 0; } else if (func instanceof SharedFunction) { return 1; }
		 * else if (func instanceof ControlledFunction) { return 2; } else if
		 * (func instanceof OutFunction) { return 3; } else if (func instanceof
		 * StaticFunction) { return 4; } else if (func instanceof
		 * DerivedFunction) { return 5; } else { return 6; } }
		 * 
		 * int compare2(Function arg0, Function arg1) { String name0 =
		 * arg0.getName(); String name1 = arg1.getName(); return
		 * name0.compareTo(name1); }
		 * 
		 * });
		 */
		return functions;
	}

//	public String visitDcl(Function function) {
//		StringBuilder str = new StringBuilder();
//		String name = function.getName();
//		Domain domain = function.getDomain();
//		Domain codomain = function.getCodomain();
//		str.append(visitUnknownDcl(function));
//		str.append(print(name + ": "));
//		if (domain != null) {
//			str.append(visit(domain));
//			str.append(print(" -> "));
//		}
//		str.append(visit(codomain));
//		str.append(println());
//		return str.toString();
//	}

//	public String visit(Domain domain) {
//		if (domain instanceof StructuredTd) {
//			return visitUnknown(domain);
//		} else {
//			String name = domain.getName();
//			return print(name);
//		}
//	}

	public String visit(RuleDomain domain) {
		StringBuilder str = new StringBuilder();
		List<Domain> domains = domain.getDomains();
		str.append(print("Rule"));
		str.append(visit(domains));
		return str.toString();
	}

	public String visit(ProductDomain domain) {
		StringBuilder str = new StringBuilder();
		List<Domain> domains = domain.getDomains();
		str.append(print("Prod"));
		str.append(visit(domains));
		return str.toString();
	}

	public String visit(SequenceDomain domain) {
		StringBuilder str = new StringBuilder();
		Domain base = domain.getDomain();
		str.append(print("Sequence("));
		str.append(visit(base));
		str.append(print(")"));
		return str.toString();
	}

	public String visit(PowersetDomain domain) {
		StringBuilder str = new StringBuilder();
		Domain base = domain.getBaseDomain();
		str.append(print("Powerset("));
		str.append(visit(base));
		str.append(print(")"));
		return str.toString();
	}

	public String visit(BagDomain domain) {
		StringBuilder str = new StringBuilder();
		Domain base = domain.getDomain();
		str.append(print("Bag("));
		str.append(visit(base));
		str.append(print(")"));
		return str.toString();
	}

	public String visit(MapDomain domain) {
		StringBuilder str = new StringBuilder();
		Domain source = domain.getSourceDomain();
		Domain target = domain.getTargetDomain();
		str.append(print("Map("));
		str.append(visit(source));
		str.append(print(", "));
		str.append(visit(target));
		str.append(print(")"));
		return str.toString();
	}

	/**
	 * visit the domain list, if it is empty do nothing
	 * 
	 * @param domains
	 */
	public String visit(List<Domain> domains) {
		StringBuilder sb = new StringBuilder();
		if (domains != null && domains.size() > 0) {
			sb.append(print("("));
			boolean first = true;
			for (int i = 1; i < domains.size(); i++) {
				Domain domain = domains.get(i);
				if (!first)
					sb.append(print(", "));
				else
					first = false;
				sb.append(visit(domain));
			}
			sb.append(print(")"));
		}
		return sb.toString();
	}

	public String visitDcl(StaticFunction function) {
		return print("static ");
	}

	public String visitDcl(OutFunction function) {
		return print("out ");
	}

	public String visitDcl(MonitoredFunction function) {
		return print("monitored ");
	}

	public String visitDcl(SharedFunction function) {
		return print("shared ");
	}

	public String visitDcl(ControlledFunction function) {
		return print("controlled ");
	}

	public String visitDcl(DerivedFunction function) {
		return print("derived ");
	}

//	public String visitDomains(Collection<Domain> doms) {
//		StringBuilder str = new StringBuilder();
//		if (doms != null) {
//			Domain[] domains = sort(doms);
//			for (Domain domain : domains) {
//				Asm asm = Defs.getAsm(domain);
//				if (asm == model) {
//					str.append(visitDcl(domain));
//				}
//			}
//		}
//		return str.toString();
//	}

	private Domain[] sortDomains(Collection<Domain> doms) {
		Domain[] domains = doms.toArray(new Domain[0]);
		/*
		 * Arrays.sort(domains, new Comparator<Domain>() {
		 * 
		 * @Override public int compare(Domain arg0, Domain arg1) { String name0
		 * = arg0.getName(); String name1 = arg1.getName(); if (arg0 instanceof
		 * AbstractTd) { if (arg1 instanceof AbstractTd) { return
		 * name0.compareTo(name1); } return -1; } else if (arg0 instanceof
		 * ConcreteDomain) { if (arg1 instanceof AbstractTd) { return 1; } if
		 * (arg1 instanceof ConcreteDomain) { return name0.compareTo(name1); }
		 * return -1; } else if (arg0 instanceof EnumTd) { if (arg1 instanceof
		 * AbstractTd || arg1 instanceof ConcreteDomain) { return 1; } return
		 * name0.compareTo(name1); } else { return name0.compareTo(name1); } }
		 * 
		 * });
		 */
		return domains;
	}

//	public String visitDcl(Domain domain) {
//		if (domain instanceof AbstractTd || domain instanceof ConcreteDomain
//				|| domain instanceof EnumTd) {
//			if (!(domain instanceof AgentDomain || domain instanceof ReserveDomain)) {
//				return visitUnknownDcl(domain);
//			}
//		}
//	}

	public String visitDcl(AbstractTd domain) {
		String name = domain.getName();
		return println("abstract domain " + name);
	}

//	public void visitDcl(ConcreteDomain domain) {
//		String name = domain.getName();
//		TypeDomain typeDomain = domain.getTypeDomain();
//		String dyn = "";
//		if(domain.getIsDynamic())dyn=print("dynamic ");
//		return dyn+print("domain " + name + " subsetof ")+
//		+ visit(typeDomain) + println();
//	}

	public String visitDcl(EnumTd enumTd) {
		String name = enumTd.getName();
		Collection<EnumElement> elements = enumTd.getElement();
		return print("enum domain " + name + " = ")
			+ visitDcl(elements);
	}

	public String visitDcl(Collection<EnumElement> enums) {
		StringBuilder str = new StringBuilder();
		if (enums != null) {
			//print("{");
			str.append(print("{"));
			EnumElement[] elements = enums.toArray(new EnumElement[0]);
			EnumElement enumElement = elements[0];
			String name = enumElement.getSymbol();
			//print(name);
			str.append(print(name));
			for (int i = 1; i < elements.length; i++) {
				enumElement = elements[i];
				name = enumElement.getSymbol();
				//print(" | " + name);
				str.append(print(" | " + name));
				
			}
			//println("}");
			str.append(println("}"));
			
		}
		return str.toString();
	}

	protected String print(String msg) {
		if (expand) {
			expandWhites();
			expand = false;
		}
		//sb.append(msg);
		return msg;
	}

	protected String println(String msg) {
		//print(msg);
		//println();
		return print(msg) + println();
	}

	String println() {
		//sb.append("\n");
		expand = true;
		return "\n";
	}

	protected void indent() {
		indentation++;
	}

	protected void unIndent() {
		indentation--;
	}

	String expandWhites() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indentation; i++) {
			sb.append(tabWidth);
		}
		return sb.toString();
	}
	
	

}

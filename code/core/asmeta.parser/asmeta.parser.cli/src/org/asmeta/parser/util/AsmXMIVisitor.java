package org.asmeta.parser.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.DynamicFunction;
import asmeta.definitions.Function;
import asmeta.definitions.Invariant;
import asmeta.definitions.Property;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
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
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;

public class AsmXMIVisitor extends ReflectiveVisitor<Void> {

	// String tabWidth = "    ";
	// int indentation = 0;
	// PrintWriter out;
	Asm model;
	TermXMIVisitor tp;
	RuleXMIVisitor rp;

	// boolean expand = true;

	/*
	 * public void close() { out.close(); } public AsmXMIVisitor(String
	 * fileName) throws FileNotFoundException { this(new File(fileName)); }
	 * 
	 * public AsmXMIVisitor(File file) throws FileNotFoundException { out = new
	 * PrintWriter(file); }
	 * 
	 * public AsmXMIVisitor(PrintWriter writer) { out = writer; }
	 */

	public void visitUnknown(Object object) {
		invokeMethod(object);
	}

	public void visitUnknownDcl(Object object) {
		invokeMethod(object, "visitDcl");
	}

	public void visit(Asm asm, EList<EObject> resourceList) {
		model = asm;
		tp = new TermXMIVisitor(resourceList);
		rp = new RuleXMIVisitor(resourceList);
		resourceList.add(asm);
		// First, add to the resource the ASM machine (so add model elements by
		// containment reference)
		// Then, visit and add to the resource some modeling elements with no
		// containment references
		// Header header = asm.getHeaderSection();
		// visit(header, resourceList);
		Body body = asm.getBodySection();
		visit(body, resourceList);
		MacroDeclaration main = asm.getMainrule();
		if (main != null)
			visitMain(main, resourceList);
		Initialization init = asm.getDefaultInitialState();
		// visitDefault(init,resourceList);
		if (init != null)
			visit(init, resourceList);
		model = null;
	}

	public void visit(Header header, EList<EObject> resourceList) {
		Collection<ImportClause> importClause = header.getImportClause();
		ExportClause exportClause = header.getExportClause();
		Signature signature = header.getSignature();
		// visit(importClause);
		// visit(exportClause);
		// println();
		// visit(signature);

		// does nothing!
	}

	public void visit(Body body, EList<EObject> resourceList) {

		// resourceList.add(body); //NO! The relation is of containment!

		Collection<DomainDefinition> domains = body.getDomainDefinition();
		Collection<FunctionDefinition> funcs = body.getFunctionDefinition();
		Collection<RuleDeclaration> rules = body.getRuleDeclaration();
		List<Property> properties = body.getProperty();
		Collection<Invariant> invariants = new ArrayList<Invariant>();
		for (Property p : properties) {
			if (p instanceof Invariant) {
				invariants.add((Invariant) p);
			}
		}

		/*
		 * println("definitions:"); println(); indent();
		 */
		visitDomDefs(domains);
		// println();
		visitFunDefs(funcs, resourceList);
		// println();
		visitRuleDefs(rules, resourceList);
		// unIndent();
		visitInvariantsDefs(invariants);

	}

	public void visitFunDefs(Collection<FunctionDefinition> funcs,
			EList<EObject> resourceList) {
		if (funcs != null) {
			for (FunctionDefinition def : funcs) {
				Function func = def.getDefinedFunction();
				Asm asm = Defs.getAsm(func);
				if (asm == model) {
					visitDef(def, resourceList);
				}
			}
		}
	}

	public void visitDef(FunctionDefinition def, EList<EObject> resourceList) {
		// String name = def.getDefinedFunction().getName();

		// Add to the resource the list of variable terms
		List<VariableTerm> vars = def.getVariable();
		resourceList.addAll(vars);

		// Visit the body
		tp.visit(def.getBody());

		// String termStr = tp.visit(term);
		// print("function " + name);
		// visitDclVars(vars, "(", ")",resourceList);
		// println(" = " + termStr);
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
		// String name = def.getDefinedDomain().getName();
		Term term = def.getBody();
		tp.visit(term);
		// String termStr = tp.visit(term);
		// println("domain " + name + " = " + termStr);
	}

	public void visitRuleDefs(Collection<RuleDeclaration> defs,
			EList<EObject> resourceList) {
		if (defs != null) {
			RuleDeclaration[] rules = sort(defs);
			for (RuleDeclaration rule : rules) {
				Asm asm = Defs.getAsm(rule);
				if (asm == model || model.getMainrule() == rule) {
					visitDef(rule, resourceList);
				}
			}
		}
	}

	public RuleDeclaration[] sort(Collection<RuleDeclaration> ruleDcls) {
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

	public void visitDef(RuleDeclaration dcl, EList<EObject> resourceList) {
		// if (dcl == model.getMainrule()) {
		// visitMain();
		// } else

		/*
		 * if (dcl instanceof MacroDeclaration) { print("macro "); } else {
		 * print("turbo "); }
		 */

		// String name = dcl.getName();

		List<VariableTerm> vars = dcl.getVariable();
		resourceList.addAll(vars);

		Rule rule = dcl.getRuleBody();
		// print("rule " + name);
		// visitDclVars(vars, "(", ")");
		// println(" =");
		// indent();

		resourceList.add(rule);
		rp.visit(rule);

		// println();
		// unIndent();
	}

	public void visitInvariantsDefs(Collection<Invariant> defs) {
		if (defs != null) {
			for (Invariant def : defs) {
				visitDef(def);
			}
		}
	}

	public void visitDef(Invariant def) {
		// String name = def.getName();
		Term term = def.getBody();
		tp.visit(term);
		// String termStr = tp.visit(term);
		// println("invariant " + name);
	}

	public void visitMain(MacroDeclaration main, EList<EObject> resourceList) {
		// print("main ");
		visitDef(main, resourceList);
	}

	public void visit(Initialization init, EList<EObject> resourceList) {
		if (init != null) {
			// TODO init.getDomainInitialization()
			// String name = init.getName();
			// println("init " + name + ":");
			// indent();
			Collection<FunctionInitialization> funcs = init
					.getFunctionInitialization();
			if (funcs != null)
				visitFuncInits(funcs, resourceList);
			// unIndent();
		}
	}

	public void visitDefault(Initialization init, EList<EObject> resourceList) {
		if (init != null) {
			// print("default ");
			visit(init, resourceList);
		}
	}

	public void visitFuncInits(Collection<FunctionInitialization> funcs,
			EList<EObject> resourceList) {
		if (funcs != null && !funcs.isEmpty()) {
			for (FunctionInitialization init : funcs) {
				DynamicFunction func = init.getInitializedFunction();
				if (func != null) {
					Asm asm = Defs.getAsm(func);
					if (asm == model)
						visitInit(init, resourceList);
				}
			}
		}
	}

	public void visitInit(FunctionInitialization init,
			EList<EObject> resourceList) {
		// String name = init.getInitializedFunction().getName();
		// List<VariableTerm> vars = init.getVariable();
		Term term = init.getBody();
		resourceList.add(term);
		tp.visit(term);

		// String termStr = tp.visit(term);
		// print("function " + name);
		// visitDclVars(vars, "(", ")");
		// println(" = " + termStr);
	}

	/*
	 * public void visit(Collection<ImportClause> imports) { if (imports !=
	 * null) { for (ImportClause importClause : imports) { //String name =
	 * importClause.getModuleName(); //println("import " + name); } } }
	 * 
	 * public void visit(ExportClause exportClause) { // TODO }
	 */

	private Function[] sortFunctions(Collection<Function> funcs) {
		Function[] functions = funcs.toArray(new Function[0]);

		return functions;
	}

	/**
	 * visit the domain list, if it is empty do nothing
	 * 
	 * @param domains
	 */

	private Domain[] sortDomains(Collection<Domain> doms) {
		Domain[] domains = doms.toArray(new Domain[0]);

		return domains;
	}

}

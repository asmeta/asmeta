package org.asmeta.flattener;

import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.flattener.args.RemoveArgumentsRuleFlattener;
import org.asmeta.flattener.args.RemoveArgumentsTermFlattener;
import org.asmeta.flattener.rule.AsmetaFlattener;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.AsmPrinter;
import org.asmeta.simulator.wrapper.RuleFactory;

import asmeta.definitions.InvarConstraint;
import asmeta.definitions.Property;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.TemporalProperty;
import asmeta.structure.Asm;
import asmeta.structure.FunctionDefinition;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.Rule;

public class RemoveArgumentsFlattener implements AsmetaFlattener {

	private static final Logger logger = Logger.getLogger(RemoveArgumentsFlattener.class);
	private RuleFactory ruleFact;
	private Asm asm;

	public RemoveArgumentsFlattener(Asm asm) {
		this.asm = asm;
		ruleFact = new RuleFactory();
	}

	@Override
	public Asm flattenASM() {
		for (FunctionDefinition fd : asm.getBodySection().getFunctionDefinition()) {
			logger.debug("flattening definition " + fd.getDefinedFunction().getName());
			RemoveArgumentsTermFlattener tf = new RemoveArgumentsTermFlattener(ruleFact);
			Term newFunctionBody = tf.visit(fd.getBody());
			LinkedHashMap<VariableTerm, Term> map = tf.getMapForLet();
			if (map.size() > 0) {
				fd.setBody(tf.buildLetTerm(map, newFunctionBody));
			}
		}
		RemoveArgumentsRuleFlattener rf = new RemoveArgumentsRuleFlattener(ruleFact);
		for (RuleDeclaration rd : asm.getBodySection().getRuleDeclaration()) {
			logger.debug(rd.getName());
			Rule newRuleBody = rf.visit(rd.getRuleBody());
			rd.setRuleBody(newRuleBody);
		}

		// this is meant to flatten properties, but it has some problems
		//flattenProperties(asm.getBodySection().getProperty());
		flattenProperties(asm.getBodySection().getInvariantConstraint());
		return asm;
	}

	private void flattenProperties(List<?> properties) {
		RemoveArgumentsTermFlattener.REUSE_VAR = false;
		for (Object p : properties) {
			// there is no common sueprclass abstarct enough
			// to unite the twp
			if (p instanceof TemporalProperty tp) {
				logger.debug("modifying also the property " + p);
				RemoveArgumentsTermFlattener tf = new RemoveArgumentsTermFlattener(ruleFact);
				Term newBody = tf.visit(tp.getBody());
				LinkedHashMap<VariableTerm, Term> map = tf.getMapForLet();
				if (map.size() > 0) {
					tp.setBody(tf.buildLetTerm(map, newBody));
				}
			}
			if (p instanceof InvarConstraint inv) {
				logger.debug("modifying also the invariant " + p);
				RemoveArgumentsTermFlattener tf = new RemoveArgumentsTermFlattener(ruleFact);
				Term newBody = tf.visit(inv.getBody());
				LinkedHashMap<VariableTerm, Term> map = tf.getMapForLet();
				if (map.size() > 0) {
					inv.setBody(tf.buildLetTerm(map, newBody));
				}
			}
		}
		RemoveArgumentsTermFlattener.REUSE_VAR = true;
	}

	public static void main(String[] args) throws Exception {
		// Asm asm = ASMParser.setUpReadAsm(new
		// File("../../../../asm_examples/examples/GilbreathCardTrick/GilbreathCardTrickFasterShuffleForAsmetaSMV.asm")).getMain();
		Asm asm = ASMParser.setUpReadAsm(new File("./examples/derivedLocArgs.asm")).getMain();
		Logger.getRootLogger().removeAllAppenders();
		Logger.getRootLogger().addAppender(new ConsoleAppender(new SimpleLayout()));
		Logger.getLogger(RemoveArgumentsFlattener.class).setLevel(Level.DEBUG);
		Logger.getLogger(RemoveArgumentsRuleFlattener.class).setLevel(Level.DEBUG);
		Logger.getLogger(RemoveArgumentsTermFlattener.class).setLevel(Level.DEBUG);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.FATAL);
		RemoveArgumentsFlattener f = new RemoveArgumentsFlattener(asm);
		f.flattenASM();
		AsmPrinter p = new AsmPrinter(new PrintWriter(System.out));
		p.visit(asm);
		p.close();
	}

	@Override
	public String getCode() {
		return "AR";
	}
}
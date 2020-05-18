package org.asmeta.visualizer.graphViewer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asmeta.parser.util.TermPrinter;

import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;

public class AdvancedTermPrinter extends TermPrinter {
	public Map<VariableTerm, Term> mapVarTerm;

	public AdvancedTermPrinter(boolean showAsmName) {
		super(showAsmName);
		mapVarTerm = new HashMap<VariableTerm, Term>();
	}

	@Override
	public String visit(FunctionTerm function) {
		List<Term> args = null;
		if(function.getArguments() != null) {
			args = function.getArguments().getTerms();
		}
		switch(function.getFunction().getName()) {
			case "and":
				return this.visit(args.get(0)) + " and " + this.visit(args.get(1));
			case "or":
				return this.visit(args.get(0)) + " or " + this.visit(args.get(1));
			case "eq":
				return this.visit(args.get(0)) + " = " + this.visit(args.get(1));
			case "neq":
				return this.visit(args.get(0)) + " = " + this.visit(args.get(1));
			case "le":
				return this.visit(args.get(0)) + " <= " + this.visit(args.get(1));
			case "ge":
				return this.visit(args.get(0)) + " >= " + this.visit(args.get(1));
			case "lt":
				return this.visit(args.get(0)) + " < " + this.visit(args.get(1));
			case "gt":
				return this.visit(args.get(0)) + " > " + this.visit(args.get(1));
			case "plus":
				if(args.size() == 2) {
					return this.visit(args.get(0)) + " + " + this.visit(args.get(1));
				}
			case "minus":
				if(args.size() == 2) {
					return this.visit(args.get(0)) + " - " + this.visit(args.get(1));
				}
			case "mult":
				return this.visit(args.get(0)) + " * " + this.visit(args.get(1));
			default:
				return super.visit(function);
		}
	}

	 @Override
	public String visit(VariableTerm variable) {
		if(mapVarTerm.containsKey(variable)) {
			 return visit(mapVarTerm.get(variable)); 
		}
		else {
			return super.visit(variable);
		}
    }
}

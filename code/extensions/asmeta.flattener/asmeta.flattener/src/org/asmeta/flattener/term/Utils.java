package org.asmeta.flattener.term;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asmeta.parser.util.AsmPrinter;
import org.asmeta.parser.util.AsmetaTermPrinter;

import asmeta.definitions.Function;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.Rule;

public class Utils {

	private static String printEl(Object obj) {
		StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter(sw);
		AsmPrinter ap = new AsmPrinter(writer);
		ap.visit(obj);
		return sw.toString();
	}

	public static String print(Rule rule) {
		return printEl(rule);
	}

	public static String print(Asm asm) {
		return printEl(asm);
	}

	public static String print(Term term) {
		AsmetaTermPrinter tp = AsmetaTermPrinter.getAsmetaTermPrinter(false);
		return tp.visit(term);
	}

	public static ArrayList<Term> propagateEq(ArrayList<Term> list) {
		ArrayList<Term> modifiedList = list;
		int indexSub = -1;
		while (true) {
			Map<String, Term> map = new HashMap<>();
			for (int i = 0; i < modifiedList.size(); i++) {
				if(i <= indexSub) {
					continue;
				}
				Term l = modifiedList.get(i);
				if (l instanceof FunctionTerm) {
					FunctionTerm functionTerm = (FunctionTerm) l;
					Function func = functionTerm.getFunction();
					if (func.getName().equals("eq")) {
						List<Term> terms = functionTerm.getArguments().getTerms();
						Term firstArg = terms.get(0);
						Term secondArg = terms.get(1);
						if(firstArg instanceof ConstantTerm && !(secondArg instanceof ConstantTerm)) {
							indexSub = i;
							map.put(Utils.print(secondArg), firstArg);
							break;
						}
						else if(secondArg instanceof ConstantTerm && !(firstArg instanceof ConstantTerm)) {
							indexSub = i;
							map.put(Utils.print(firstArg), secondArg);
							break;
						}
					}
				}
			}
			if(map.size() == 1) {
				String key = map.keySet().iterator().next();
				//System.err.println(key + " " + map.get(key));
				//System.err.println(indexSub);
				ReplaceTerm rt = new ReplaceTerm(key, map.get(key));
				ArrayList<Term> newList = new ArrayList<>();
				for(Term m: modifiedList) {
					newList.add(rt.visit(m));
				}
				modifiedList = newList;
			}
			else {
				break;
			}
		}
		return modifiedList;
	}
}

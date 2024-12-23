package org.asmeta.asm2java

import asmeta.definitions.domains.CharDomain
import asmeta.definitions.domains.Domain
import asmeta.definitions.domains.IntegerDomain
import asmeta.definitions.domains.NaturalDomain
import asmeta.definitions.domains.RealDomain
import asmeta.definitions.domains.StringDomain
import asmeta.structure.Asm
import asmeta.terms.basicterms.VariableTerm
import org.eclipse.emf.common.util.EList
import asmeta.definitions.domains.BooleanDomain

class Util {

	int counter

	Object c

	int i

	new() {
	}

	def static getElemsSetName(String domainName) {
		return domainName + "_elems"
	}

	def static getExtendSetName(String domainName) {
		return domainName + "_extend"
	}

	def String adaptRuleParam(EList<VariableTerm> variables, Asm res) {
		var StringBuffer paramDef = new StringBuffer
		paramDef.append("");
		for (var i = 0; i < variables.size; i++) {
			paramDef.append('''«new ToString(res).visit(variables.get(i).domain)» «variables.get(i).name», ''')
		}
		return paramDef.substring(0, paramDef.length - 2)
	}

	def String parseFunction(String s) {
		if (s.equals("and")) {
			return "&";
		} else if (s.equals("or")) {
			return "|";
		} else if (s.equals("not")) {
			return "!";
		} else if (s.equals("plus")) {
			return "+";
		} else if (s.equals("minus")) {
			return "-";
		} else if (s.equals("mult")) {
			return "*";
		} else if (s.equals("div")) {
			return "/";
		} else if (s.equals("gt")) {
			return ">";
		} else if (s.equals("ge")) {
			return ">=";
		} else if (s.equals("lt")) {
			return "<";
		} else if (s.equals("le")) {
			return "<=";
		} else if (s.equals("eq")) {
			return "=";
		} else if (s.equals("neq")) {
			return "!=";
		} else if (s.equals("pwr")) {
			return "^";
		} else {
			return s;
		}
	}

	def String equals(String left, String right) {
		return setPars(left + " == " + right);
	}

	def Boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	def String notEquals(String left, String right) {
		return setPars(left + " != " + right);
	}

	def String setPars(String expr) {
		if (hasFirstLastPars(expr)) {
			return expr;
		} else {

			return "(" + expr + ")";
		}
	}

	def boolean hasFirstLastPars(String str) {
		if (str.startsWith("(") && str.endsWith(")")) {
			counter = 1; // Starting parenthesis
			for (i = 1; i < str.length() - 1; i++) {
				c = str.charAt(i);
				if (c == '(') {
					counter++; // A new starting parenthesis is found
				} else if (c == ')') {
					counter--; // A closing parenthesis is found
				}
				if (counter == 0) { // Initial parenthesis is closed
					return false; // There is no correspondence between initial and closing parenthesis
				}
			}
			counter--; // Removing final parenthesis
			// If the counter is 0, it means that the initial and final parenthesis are coupled
			return counter == 0;
		} else {
			return false;
		}
	}

	// Return true if domain is one of those listed in the condition
	def boolean isNotNumerable(Domain domain) {
		if (domain instanceof StringDomain || domain instanceof CharDomain || domain instanceof IntegerDomain ||
			domain instanceof RealDomain || domain instanceof NaturalDomain || domain instanceof BooleanDomain)
			return true
		else
			return false
	}

}

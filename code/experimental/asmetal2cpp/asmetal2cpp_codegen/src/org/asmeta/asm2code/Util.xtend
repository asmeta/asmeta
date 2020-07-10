package org.asmeta.asm2code

import asmeta.definitions.domains.CharDomain
import asmeta.definitions.domains.Domain
import asmeta.definitions.domains.IntegerDomain
import asmeta.definitions.domains.NaturalDomain
import asmeta.definitions.domains.RealDomain
import asmeta.definitions.domains.StringDomain
import asmeta.structure.Asm
import asmeta.terms.basicterms.VariableTerm
import org.eclipse.emf.common.util.EList

public class Util {

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

/* 	def static getAsmClassName(Asm asm) {
		return asm.name.toFirstUpper
	}*/

	def String adaptRuleParam(EList<VariableTerm> variables, Asm res) {
		var StringBuffer paramDef = new StringBuffer
		paramDef.append("");
		for (var i = 0; i < variables.size; i++) {
			//paramDef.
			//	append('''«new ToString(res).visit(variables.get(i).domain)» «updateVarName(variables.get(i).name)», ''')
			paramDef.
				append('''«new ToString(res,true).visit(variables.get(i).domain)» «variables.get(i).name», ''')
		}
		return paramDef.substring(0, paramDef.length - 2)
	}

	
	/*def String updateVarName(String name) {
		if (name.startsWith("$"))
			return "_" + name.substring(1)
		else
			return name
	}*/

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
		}
		/* TODO --> translate properties into asserts
		 *   else if (s.equals("implies")) {
		 * 	return "->";
		 * } else if (s.equals("iff")) {
		 * 	return "<->";
		 * } else if (s.equals("eg") || s.equals("ex") || s.equals("ef") || s.equals("ag") || s.equals("ax") ||
		 * 	s.equals("af")) {
		 * 	return s.toUpperCase();
		 * } else if (s.equals("e") || s.equals("a")) {
		 * 	return "U";
		 * } else if (s.equals("x") || s.equals("g") || s.equals("f") || s.equals("u") || s.equals("v") || s.equals("y") ||
		 * 	s.equals("z") || s.equals("h") || s.equals("o") || s.equals("s") || s.equals("t")) {
		 * 	return s.toUpperCase();
		 }*/
		else if (s.equals("pwr")) {
			return "^";
		} else {
			return s;
		}
	}

	/**
	 * Esegue l'operatore di uguaglianza. Se non bisogna semplificare ritorna
	 * semplicemente una stringa che rappresenta l'uguaglianza. Se bisogna
	 * semplificare prova a fare delle semplificazioni.
	 * 
	 * @param left
	 *            the left operand
	 * @param right
	 *            the right operand
	 * 
	 * @return the string
	 */
	def String equals(String left, String right) {
		// Se le due stringhe sono uguali, qualsiasi cosa siano (termini,
		// numeri, enum, boolean) ritorna true.
			return setPars(left + " == " + right);
	}

	/**
	 * Checks if str is a number.
	 * 
	 * @param str
	 *            a string
	 * 
	 * @return true if str is a number, false otherwise.
	 */
	def Boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/** TODO: DELETE FOR COVERAGE def boolean isBoolean(String str) {
		return str.equals("false") || str.equals("true");
	}*/

	/**
	 * Not equals.
	 * 
	 * @param left
	 *            the left
	 * @param right
	 *            the right
	 * 
	 * @return the string
	 */
	def String notEquals(String left, String right) {
		// Se le due stringhe sono uguali, qualsiasi cosa siano (termini,
		// numeri, enum, boolean) ritorna true.
		return setPars(left + " != " + right);
	}
	
	
	/**
	 * Sets the parentheses.
	 * 
	 * @param expr
	 *            the expr
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	def String setPars(String expr) {
		if (hasFirstLastPars(expr)) {
			// System.err.println(expr);
			return expr;
		} else {
			// System.err.println("(" + expr + ")");
			return "(" + expr + ")";
		}
	}

	/**
	 * Checks if the string starts and ends with a couple of parentheses
	 * 
	 * @param str
	 *            the string to check
	 * @return true if the string starts and ends with a couple of parentheses
	 */
	def boolean hasFirstLastPars(String str) {
		// condizione necessaria e' che ci sia una parentesi all'inizio e una
		// alla fine
		if (str.startsWith("(") && str.endsWith(")")) {
			counter = 1; // aggiungo la parentesi iniziale
			for (i = 1; i < str.length() - 1; i++) {
				c = str.charAt(i);
				if (c == '(') {
					counter++; // trovata una parentesi aperta
				} else if (c == ')') {
					counter--; // trovata una parentesi chiusa
				}
				if (counter == 0) { // si e' chiusa la parentesi iniziale
					return false; // la parentesi iniziale non e' in coppia con quella finale
				}
			}
			counter--; // rimuovo la parentesi finale
			// se il contatore vale 0 vuol dire che la parentesi iniziale e' accoppiata
			// con quella finale
			return counter == 0;
		} else {
			return false;
		}
	}
	
	
	//return true if domain is one of those listed in the condition
	def boolean isNotNumerable(Domain domain) {
		if (domain instanceof StringDomain || domain instanceof CharDomain || domain instanceof IntegerDomain ||
			domain instanceof RealDomain || domain instanceof NaturalDomain)
			return true
		else
			return false
	}
	
}

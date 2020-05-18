package org.asmeta.toyices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

import asmeta.definitions.domains.Domain;
import asmeta.structure.FunctionDefinition;
import yices.YicesLibrary;

public class YicesModel {
	//private static Logger logger = LogManager.getLogger(YicesModel.class.getName());
	Pointer ctx;
	static YicesLibrary yices;
	public Set<String[]> contrFuncsDeclarations;
	Map<String, String[]> enumValues;
	Set<String> allEnumAndAgentsValues;
	Map<String, Integer[]> intValues;
	Map<Domain, Integer[]> domainIntValues;
	Map<String, String> intDomainsDeclaration;
	public List<FunctionDefinition> derivedFunctionsDef;
	public Set<String[]> monDerFuncsDeclarations;
	Map<String, Integer[]> setsValues;
	Map<String, List<String>> agentValues;
	public Map<String, String> domainUndefValue;

	static {
		System.setProperty("jna.library.path",".");
		yices = (YicesLibrary) Native.loadLibrary("yices", YicesLibrary.class);
	}

	public YicesModel() {
		ctx = yices.yices_mk_context();
		setsValues = new HashMap<String, Integer[]>();
		agentValues = new HashMap<String, List<String>>();
	}

	public String tuple(String[] operands) {
		StringBuilder sb = new StringBuilder("");
		for(int i = 0; i < operands.length - 1; i++) {
			sb.append(operands[i] + " ");
		}
		sb.append(operands[operands.length - 1]);
		return sb.toString();
	}

	public String tuple(Collection<String> operands) {
		StringBuilder sb = new StringBuilder("");
		Iterator<String> it = operands.iterator();
		for(int i = 0; i < operands.size() - 1; i++) {
			sb.append(it.next() + " ");
		}
		sb.append(it.next());
		return sb.toString();
	}

	public String and(Collection<String> operands) {
		if(operands.contains("false")) {
			return "false";
		}
		LinkedHashSet<String> ops = new LinkedHashSet<String>();
		for(String o: operands) {
			if(!o.equals("true")) {
				ops.add(o);
			}
		}
		if(ops.size() == 0) {
			return "true";
		}
		else if(ops.size() > 1) {
			return "(and " + tuple(ops) + ")";
		}
		else {
			assert ops.size() == 1; 
			return ops.iterator().next();
		}
	}

	/*public String and(String[] operands) {
		return "(and " + tuple(operands) + ")";
	}*/

	public String and(String firstOperand, String secondOperand) {
		List<String> operands = new ArrayList<String>();
		operands.add(firstOperand);
		operands.add(secondOperand);
		return and(operands);
	}
	
	public String or(String[] operands) {
		ArrayList<String> list = new ArrayList<String>();
		for(String operand: operands) {
			list.add(operand);
		}
		return or(list);
	}

	public String or(Collection<String> operands) {
		if(operands.contains("true")) {
			return "true";
		}
		LinkedHashSet<String> ops = new LinkedHashSet<String>();
		for(String o: operands) {
			if(!o.equals("false")) {
				ops.add(o);
			}
		}
		if(ops.size() == 0) {
			return "false";
		}
		else if(ops.size() > 1) {
			//we add the following check, since it can happen in
			//the guards of unchanged functions (when a location is updated in any case)
			//if it contains "a \/ !a" it is true 
			for(String o1: ops) {
				for(String o2: ops) {
					if(o1.equals(not(o2)) || o2.equals(not(o1))) {
						return "true";
					}
				}
			}
			return "(or " + tuple(ops) + ")";
		}
		else {
			assert ops.size() == 1; 
			return ops.iterator().next();
		}
	}

	public String or(String firstOperand, String secondOperand) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(firstOperand);
		list.add(secondOperand);
		return or(list);
	}

	public String implies(String antecedent, String consequent) {
		//return or(not(antecedent), consequent);
		if(antecedent.equals("true")) {
			return consequent;
		}
		else if(antecedent.equals("false") || antecedent.equals(consequent) || consequent.equals("true")) {
			return "true";
		}
		else {
			return "(=> " + antecedent + " " + consequent + ")";
		}
	}

	public String not(String operand) {
		if(operand.equals("true")) {
			return "false";
		}
		else if(operand.equals("false")) {
			return "true";
		}
		else {
			return "(not " + operand +")";
		}
	}

	private int[] parseIntegers(String leftOperand, String rightOperand) {
		return new int[]{Integer.parseInt(leftOperand), Integer.parseInt(rightOperand)};
	}

	public String eq(String leftOperand, String rightOperand) {
		if(leftOperand.equals(rightOperand)) {
			return "true";
		}
		else if(leftOperand.equals("true")) {
			return rightOperand;
		}
		else if(rightOperand.equals("true")) {
			return leftOperand;
		}
		else if(leftOperand.equals("false")) {
			return not(rightOperand);
		}
		
		else if(rightOperand.equals("false")) {
			return not(leftOperand);
		}
		else {
			try {
				int[] asInt = parseIntegers(leftOperand, rightOperand);
				return (asInt[0] == asInt[1])?"true":"false";
			}
			catch(Exception e) {}
			if(allEnumAndAgentsValues.contains(leftOperand) && allEnumAndAgentsValues.contains(rightOperand)) {
				return "false";
			}
			return "(= " + leftOperand + " " + rightOperand + ")";
		}
	}

	public String neq(String leftOperand, String rightOperand) {
		if(leftOperand.equals(rightOperand)) {
			return "false";
		}
		else if(leftOperand.equals("true")) {
			return not(rightOperand);
		}
		else if(leftOperand.equals("false")) {
			return rightOperand;
		}
		else if(rightOperand.equals("true")) {
			return not(leftOperand);
		}
		else if(rightOperand.equals("false")) {
			return leftOperand;
		}
		else {
			try {
				int[] asInt = parseIntegers(leftOperand, rightOperand);
				return (asInt[0] != asInt[1])?"true":"false";
			}
			catch(Exception e) {}
			if(allEnumAndAgentsValues.contains(leftOperand) && allEnumAndAgentsValues.contains(rightOperand)) {
				return "true";
			}
			return "(/= " + leftOperand + " " + rightOperand + ")";
		}
	}

	public String ge(String leftOperand, String rightOperand) {
		try {
			int[] asInt = parseIntegers(leftOperand, rightOperand);
			return (asInt[0] >= asInt[1])?"true":"false";
		}
		catch(Exception e) {}
		return "(>= " + leftOperand + " " + rightOperand + ")";
	}

	public String gt(String leftOperand, String rightOperand) {
		try {
			int[] asInt = parseIntegers(leftOperand, rightOperand);
			return (asInt[0] > asInt[1])?"true":"false";
		}
		catch(Exception e) {}
		return "(> " + leftOperand + " " + rightOperand + ")";
	}

	public String le(String leftOperand, String rightOperand) {
		try {
			int[] asInt = parseIntegers(leftOperand, rightOperand);
			return (asInt[0] <= asInt[1])?"true":"false";
		}
		catch(Exception e) {}
		return "(<= " + leftOperand + " " + rightOperand + ")";
	}

	public String lt(String leftOperand, String rightOperand) {
		try {
			int[] asInt = parseIntegers(leftOperand, rightOperand);
			return (asInt[0] < asInt[1])?"true":"false";
		}
		catch(Exception e) {}
		return "(< " + leftOperand + " " + rightOperand + ")";
	}

	public String ifThenElse(String cond, String thenBranch, String elseBranch) {
		if(cond.equals("true")) {
			return thenBranch;
		}
		else if(cond.equals("false")) {
			return elseBranch;
		}
		else {
			//return and(implies(cond, thenBranch), implies(not(cond), elseBranch));
			return "(if " + cond + " " + thenBranch + " " + elseBranch + ")";
		}
	}

	public String ifThen(String cond, String thenBranch) {
		return ifThenElse(cond, thenBranch, "true");
	}

	public String forall(List<String> vars, List<String> domains, String body) {
		String sb = getBodyForallExists(vars, domains);
		return forall(sb, body);
	}

	private String getBodyForallExists(List<String> vars, List<String> domains) {
		int numVars = vars.size();
		assert numVars == domains.size();
		StringBuilder sb = new StringBuilder("(");
		for(int i = 0; i < numVars - 1; i++) {
			sb.append(vars.get(i)).append("::").append(domains.get(i)).append(" ");
		}
		sb.append(vars.get(numVars - 1)).append("::").append(domains.get(numVars - 1)).append(")");
		return sb.toString();
	}

	public String exists(List<String> vars, List<String> domains, String body) {
		String s = getBodyForallExists(vars, domains);
		return exists(s, body);
	}

	public String forall(String varsDoms, String body) {
		return new StringBuilder("(forall ").append(varsDoms).append(" ").append(body).append(")").toString();
	}

	public String exists(String varsDoms, String body) {
		return new StringBuilder("(exists ").append(varsDoms).append(" ").append(body).append(")").toString();
	}

	public String getEnumValue(String domain, int i) {
		return enumValues.get(domain)[i];
	}

	public String plus(String augend, String addend) {
		try {
			int[] asInt = parseIntegers(augend, addend);
			return String.valueOf(asInt[0] + asInt[1]);
		}
		catch(NumberFormatException n) {}
		if(augend.equals("0")) {
			return addend;
		}
		else if(addend.equals("0")) {
			return augend;
		}
		return "(+ " + augend + " " + addend + ")";
	}

	public String minus(String minuend, String subtrahend) {
		try {
			int[] asInt = parseIntegers(minuend, subtrahend);
			return String.valueOf(asInt[0] - asInt[1]);
		}
		catch(NumberFormatException n) {
		}
		if(subtrahend.equals("0")) {
			return minuend;
		}
		else if(minuend.equals("0")) {
			return unaryMinus(subtrahend);
		}
		return "(- " + minuend + " " + subtrahend + ")";
	}

	public String mult(String multiplicand, String multiplier) {
		try {
			int[] asInt = parseIntegers(multiplicand, multiplier);
			return String.valueOf(asInt[0] * asInt[1]);
		}
		catch(NumberFormatException n) {
		}
		if(multiplicand.equals("0") || multiplier.equals("0")) {
			return "0";
		}
		else if(multiplicand.equals("1")) {
			return multiplier;
		}
		else if(multiplier.equals("1")) {
			return multiplicand;
		}
		return "(* " + multiplicand + " " + multiplier + ")";
	}

	public String intDiv(String dividend, String divisor) {
		try {
			int[] asInt = parseIntegers(dividend, divisor);
			return String.valueOf(asInt[0] / asInt[1]);
		}
		catch(NumberFormatException n) {
			try {
				return String.valueOf(Double.valueOf(dividend)/Double.valueOf(divisor));
			}
			catch(NumberFormatException n2) {
			}
		}
		return "(div " + dividend + " " + divisor + ")";
	}

	public String mod(String dividend, String divisor) {
		try {
			return String.valueOf(Integer.valueOf(dividend)%Integer.valueOf(divisor));
		}
		catch(NumberFormatException n) {
		}
		return "(mod " + dividend + " " + divisor + ")";
	}

	public String unaryMinus(String value) {
		if(value.startsWith("-")) {
			return value.substring(1);
		}
		return "-" + value;
	}

	public String xor(ArrayList<String> operands) {
		ArrayList<String> orOperands = new ArrayList<String>();
		for(int i = 0; i < operands.size(); i++) {
			ArrayList<String> andOperands = new ArrayList<String>();
			andOperands.add(operands.get(i));
			for(int j = 0; j < operands.size(); j++) {
				if(j != i) {
					andOperands.add(not(operands.get(j)));
				}
			}
			orOperands.add(and(andOperands));
		}
		return or(orOperands);
	}

	public ArrayList<String[]> getValuesCombinationsFromDoms(List<Domain> domains) {
		ArrayList<String[]> valuesCombinations = new ArrayList<String[]>();
		Utils.combineValues(domains, 0, valuesCombinations, new Stack<String>(), this);
		return valuesCombinations;
	}

	public ArrayList<String[]> getValuesCombinationsFromDomsWithUndef(List<Domain> domains) {
		ArrayList<String[]> valuesCombinations = new ArrayList<String[]>();
		Utils.combineValuesWithUndef(domains, 0, valuesCombinations, new Stack<String>(), this);
		return valuesCombinations;
	}
}
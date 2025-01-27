package org.asmeta.toyices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.asmeta.parser.Defs;

import asmeta.definitions.Function;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.TypeDomain;

public class Utils {
	public final static String currentStateSymbol = "§";
	public final static String nextStateSymbol = "#";

	public static List<Domain> splitDomains(Domain domain) {
		if(domain instanceof ProductDomain) {
			return ((ProductDomain)domain).getDomains();
		}
		List<Domain> d = new ArrayList<Domain>();
		d.add(domain);
		return d;
	}

	public static String parseDomainName(Domain domain) {
		String domainName = domain.getName();
		if(domainName.equals("Boolean")) {
			return "bool";
		}
		else if(domain instanceof ConcreteDomain) {
			return "int";
		}
		else if(domainName.equals("Integer")) {
			return "int";
		}
		return domainName;
	}

	public static String parseDomainNameOrDef(Domain domain, YicesModel yicesModel) {
		String domainName = domain.getName();
		if(domainName.equals("Boolean")) {
			return "bool";
		}
		else if(domain instanceof ConcreteDomain) {
			if(((ConcreteDomain) domain).getTypeDomain() instanceof IntegerDomain) {
				return yicesModel.intDomainsDeclaration.get(domain.getName());
			}
		}
		else if(domainName.equals("Integer")) {
			return "int";
		}
		return domainName;
	}

	public static String getFuncName(Function function) {
		return function.getName();
	}

	public static String location(String funcName, int stateNumber, String[] args) {
		return location(funcName, String.valueOf(stateNumber), args);
	}

	public static String location(String funcName, String stateNumber, String[] args) {
		List<String> listArgs = new ArrayList<String>();
		for(String arg: args) {
			listArgs.add(arg);
		}
		return location(funcName + stateNumber, listArgs);
	}

	public static String location(String funcName, String[] args) {
		List<String> listArgs = new ArrayList<String>();
		for(String arg: args) {
			listArgs.add(arg);
		}
		return location(funcName, listArgs);
	}
	
	public static String location(String funcName, List<String> args) {
		int arity = args.size();
		if(arity == 0) {
			return funcName;
		}
		else if (arity == 1) {
			return "(" + funcName + " " + args.get(0) + ")";
		}
		else {
			//n-ary functions with n > 1
			StringBuilder sb = new StringBuilder("(" + funcName + " ");
			if(Utils.nAryFunctionsAsTuples) {
				//using tuples
				sb.append("(mk-tuple");
				for(String arg: args) {
					sb.append(" ").append(arg);
				}
				sb.append(")");
			}
			else {
				//using curryfication
				sb.append(args.get(0));
				for(int i = 1; i < args.size(); i++) {
					sb.append(" ").append(args.get(i));
				}
			}
			sb.append(")");
			return sb.toString();
		}
	}

	public static void combineValues(List<Domain> domains, int index, ArrayList<String[]> result, Stack<String> tupla,
			YicesModel yicesModel) {
		assert domains.size() > index : "domains.size() = " + domains.size() + " index = " + index;
		Domain domain = domains.get(index);
		List<String> values = new ArrayList<String>();
		if (domain instanceof BooleanDomain) {
			values.add("false");
			values.add("true");
		} else {
			String domainName = domain.getName();
			if (domain instanceof EnumTd) {
				Map<String, String[]> enumValues = yicesModel.enumValues;
				for (String v : enumValues.get(domainName)) {
					values.add(v);
				}
			} else if (domain instanceof ConcreteDomain) {
				TypeDomain typeDomain = ((ConcreteDomain) domain).getTypeDomain();
				if (typeDomain instanceof IntegerDomain) {
					Map<String, Integer[]> intValues = yicesModel.intValues;
					assert intValues.get(domainName) != null : "We do not have the values of " + domainName
							+ "\navailable concrete domains: " + yicesModel.domainIntValues;
					for (Integer v : intValues.get(domainName)) {
						values.add(String.valueOf(v));
					}
				} else if (typeDomain instanceof AgentDomain) {
					Map<String, List<String>> agentValues = yicesModel.agentValues;
					values.addAll(agentValues.get(domainName));
				}
			} else if (domain instanceof AbstractTd) {
				// no value to combine
			} else if (domain instanceof RealDomain) {
				// no value to combine
			} else {
				System.err.println(domain.getClass().getSimpleName());
				throw new Error(domain.getName() + " not supported");
			}
		}
		for(String value: values) {
			tupla.push(value);
			if(domains.size() == index + 1){
				String[] temp = new String[tupla.size()];
				for(int i = 0; i < temp.length; i++) {
					temp[i] = tupla.get(i);
				}
				result.add(temp);
			}
			else {
				combineValues(domains, index + 1, result, tupla, yicesModel);
			}
			tupla.pop();
		}
	}

	public static void combineValuesWithUndef(List<Domain> domains, int index, ArrayList<String[]> result, Stack<String> tupla, YicesModel yicesModel) {
		assert domains.size() > index: "domains.size() = " + domains.size() + " index = " + index;
		Domain domain = domains.get(index);
		List<String> values = new ArrayList<String>();
		if(domain instanceof BooleanDomain) { 
			values.add("false");
			values.add("true");
		}
		else {
			String domainName = domain.getName();
			values.add(yicesModel.domainUndefValue.get(domainName));
			if(domain instanceof EnumTd) {
				Map<String, String[]> enumValues = yicesModel.enumValues;
				for(String v: enumValues.get(domainName)) {
					values.add(v);
				}
			}
			else if(domain instanceof ConcreteDomain) {
				TypeDomain typeDomain = ((ConcreteDomain) domain).getTypeDomain();
				if(typeDomain instanceof IntegerDomain) {
					Map<String, Integer[]> intValues = yicesModel.intValues;
					assert intValues.get(domainName) != null: "We do not have the values of " + domainName + "\navailable concrete domains: " + yicesModel.domainIntValues;
					for(Integer v: intValues.get(domainName)) {
						values.add(String.valueOf(v));
					}
				}
				else if(typeDomain instanceof AgentDomain) {
					Map<String, List<String>> agentValues = yicesModel.agentValues;
					values.addAll(agentValues.get(domainName));
				}
			}
			else {
				System.err.println(domain.getClass().getSimpleName());
				throw new Error(domain.getName() + " not supported");
			}
		}
		for(String value: values) {
			tupla.push(value);
			if(domains.size() == index + 1){
				String[] temp = new String[tupla.size()];
				for(int i = 0; i < temp.length; i++) {
					temp[i] = tupla.get(i);
				}
				result.add(temp);
			}
			else {
				combineValuesWithUndef(domains, index + 1, result, tupla, yicesModel);
			}
			tupla.pop();
		}
	}

	public static ArrayList<String> getLocations(Function func, YicesModel yicesModel) {
		ArrayList<String> locations = new ArrayList<String>();
		String funcName = func.getName() + currentStateSymbol;
		if(func.getArity() > 0) {
			ArrayList<String[]> values = new ArrayList<String[]>();
			Utils.combineValues(Utils.splitDomains(func.getDomain()), 0, values, new Stack<String>(), yicesModel);
			for(String[] v: values) {
				StringBuilder sb = new StringBuilder("(" + funcName);
				for(String a: v) {
					sb.append(" " + a);
				}
				sb.append(")");
				locations.add(sb.toString());
			}
		}
		else {
			locations.add(funcName);
		}
		return locations;
	}

	public static boolean isAgentConstant(Function function) {
		Domain codomain = function.getCodomain();
		return Defs.isStatic(function) && function.getArity() == 0 && (codomain instanceof AgentDomain || (codomain instanceof ConcreteDomain && ((ConcreteDomain)codomain).getTypeDomain() instanceof AgentDomain)); 
	}

	/**
	 * It renames functions by adding the function arity to the name.
	 * E.g.,
	 * f: Integer becomes f0
	 * f: Integer -> Integer becomes f1
	 * ...
	 * 
	 * @param functions a collection of functions
	 * @param renameAlways it says whether the functions must always be renamed (also if there is only one function
	 *                     with that name), or they must be renamed only if they are in overloading
	 */
	public static void renameOverloadFunctions(Collection<Function> functions, boolean renameAlways) {
		Map<String, Integer> funcNameCounter = new HashMap<String, Integer>();
		ArrayList<Function> consideredFunctions = new ArrayList<Function>();
		//Functions having the same name (in overload) must be renamed,
		//We suppose that no functions with the same name have also the
		//same arity.
		for(Function func: functions) {
			if(!isAgentConstant(func)) {
				consideredFunctions.add(func);
				String funcName = func.getName();
				if(!funcNameCounter.containsKey(funcName)) {
					funcNameCounter.put(funcName, 1);
				}
				else {
					funcNameCounter.put(funcName, funcNameCounter.get(funcName) + 1);
				}
			}
		}
		for(Function func: consideredFunctions) {
			String funcName = getFuncName(func);
			if(renameAlways || funcNameCounter.get(funcName) > 1) {
				//If a function is in overload (based on the arity)
				//we rename it adding to the function name its arity.
				func.setName(funcName + func.getArity());
			}
		}
	}

	public static String replaceCurrentNextSymbol(String command, int currentState) {
		command = command.replaceAll(Utils.currentStateSymbol, String.valueOf(currentState));
		command = command.replaceAll(Utils.nextStateSymbol, String.valueOf(currentState + 1));
		return command;
	}

	public static boolean nAryFunctionsAsTuples = false;//if false we use curryfication
	
	public static String getDomainNameForFunctionDomain(Domain domain, Map<String, String> domainWithoutUndef, YicesModel yicesModel) {
		if(domainWithoutUndef.containsKey(domain.getName())) {
			return domainWithoutUndef.get(domain.getName());
		}
		else {
			return parseDomainNameOrDef(domain, yicesModel);
		}
	}
}
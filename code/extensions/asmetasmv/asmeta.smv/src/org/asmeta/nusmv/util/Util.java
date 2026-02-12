package org.asmeta.nusmv.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.asmeta.parser.util.Defs;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.Function;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.TypeDomain;
import asmeta.structure.Asm;
import asmeta.structure.Header;
import asmeta.structure.Signature;

public class Util {
	
	private static final String LTL_LIBRARY_NAME = "LTLLibrary";
	private static final String CTL_LIBRARY_NAME = "CTLLibrary";
	private static final String STANDARD_LIBRARY_NAME = "StandardLibrary";
	
	public static final String trueString = "TRUE";
	public static final String falseString = "FALSE";
	public final static String notUsedMess = " has not been exported in NuSMV since it is never used.";
	private static Boolean isAsynchr;
	private static String path;
	private static String mainAsmName;
	private static String dir;
	static boolean disableCounterExampleComputation;
	/**
	 * Gets the name of the Asm module to which the function belongs.
	 * 
	 * @param function
	 *            a function
	 * @return the name of the Asm module to which the function belongs
	 */
	static String getAsmName(Function function) {
		return getAsm(function).getName();
	}

	/**
	 * Gets the Asm module to which the function belongs.
	 * 
	 * @param function
	 *            a function
	 * 
	 * @return the Asm module to which the function belongs
	 */
	static Asm getAsm(Function function) {
		return function.getSignature().getHeaderSection().getAsm();
	}

	/**
	 * Gets the name of the Asm module to which the domain belongs.
	 * 
	 * @param domain
	 *            a domain
	 * 
	 * @return the name of the Asm module to which the domain belongs
	 */
	static String getAsmName(Domain domain) {
		Asm asm = getAsm(domain);
		if (asm != null) {
			return asm.getName();
		}
		else {
			return getMainAsmName();
		}
	}

	/**
	 * Gets the Asm module to which the domain belongs.
	 * 
	 * @param domain
	 *            a domain
	 * 
	 * @return the Asm module to which the domain belongs
	 */
	static Asm getAsm(Domain domain) {
		Asm asm = null;
		Signature signature = domain.getSignature();
		if(signature != null) {
			Header header = signature.getHeaderSection();
			if(header != null) {
				asm = header.getAsm();
			}
		}
		return asm;
	}

	/**
	 * Belongs to main asm.
	 * 
	 * @param domain
	 *            a domain
	 * 
	 * @return true, if successful
	 */
	static boolean belongsToMainAsm(Domain domain) {
		return getAsmName(domain).equals(getMainAsmName());
	}

	static boolean notBelongsToMainAsm(Domain domain) {
		return !belongsToMainAsm(domain);
	}

	static boolean belongsToMainAsm(Function function) {
		return getAsmName(function).equals(getMainAsmName());
	}

	/**
	 * Not belongs to main asm.
	 * 
	 * @param function
	 *            a function
	 * 
	 * @return true, if successful
	 */
	static boolean notBelongsToMainAsm(Function function) {
		return !belongsToMainAsm(function);
	}

	/**
	 * Gets the domain name.
	 * 
	 * @param domain
	 *            a domain
	 * 
	 * @return the domain name
	 */
	public static String getDomainName(Domain domain) {
		String domainName = domain.getName();
		if (notBelongsToMainAsm(domain)
				&& !getAsmName(domain).equals(STANDARD_LIBRARY_NAME)) {
			domainName = Util.getAsmName(domain) + "_" + domainName;
		}
		return domainName;
	}

	/**
	 * Gets the function name.
	 * 
	 * @param function
	 *            a function
	 * 
	 * @return the function name
	 */
	public static String getFunctionName(Function function) {
		String functionName = function.getName();
		String asmName = getAsmName(function);
		if (notBelongsToMainAsm(function) && !asmName.equals(STANDARD_LIBRARY_NAME)
				&& !asmName.equals(CTL_LIBRARY_NAME)
				&& !asmName.equals(LTL_LIBRARY_NAME)) {
			functionName = asmName + "_" + functionName;
		}
		return functionName;
	}

	/**
	 * Parsename.
	 * 
	 * @param s
	 *            the s
	 * 
	 * @return the string
	 * 
	 * @throws AsmNotSupportedException
	 *             the asm not supported exception
	 */
	public static String parsename(String s) throws AsmNotSupportedException {
		if (s.equals("and")) {
			return "&";
		}
		else if (s.equals("or")) {
			return "|";
		}
		else if (s.equals("not")) {
			return "!";
		}
		else if (s.equals("plus")) {
			return "+";
		}
		else if (s.equals("minus")) {
			return "-";
		}
		else if (s.equals("mult")) {
			return "*";
		}
		else if (s.equals("div")) {
			return "/";
		}
		else if (s.equals("gt")) {
			return ">";
		}
		else if (s.equals("ge")) {
			return ">=";
		}
		else if (s.equals("lt")) {
			return "<";
		}
		else if (s.equals("le")) {
			return "<=";
		}
		else if (s.equals("eq")) {
			return "=";
		}
		else if (s.equals("neq")) {
			return "!=";
		}
		else if (s.equals("implies")) {
			return "->";
		}
		else if (s.equals("iff")) {
			return "<->";
		}
		else if (s.equals("eg") || s.equals("ex") || s.equals("ef")
				|| s.equals("ag") || s.equals("ax") || s.equals("af")) {
			return s.toUpperCase();
		}
		else if (s.equals("eu") || s.equals("au")) {
			return "U";
		}
		else if (s.equals("x") || s.equals("g") || s.equals("f")
				|| s.equals("u") || s.equals("v") || s.equals("y")
				|| s.equals("z") || s.equals("h") || s.equals("o")
				|| s.equals("s") || s.equals("t")) {
			return s.toUpperCase();
		}
		else if (s.equals("pwr")) {
			throw new AsmNotSupportedException("La funzione " + s
					+ " non e' supportata.");
		}
		else {
			return s;
		}
	}

	/**
	 * Merge conditions.
	 * 
	 * @param conditions
	 *            the conditions
	 * @param operator
	 *            the operator
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	static String mergeConditions(Collection<String> conditions, String operator) {
		StringBuilder result = new StringBuilder();
		assert conditions.size() > 0: "The collection of conditions should not be empty.";
		Iterator<String> condsIt = conditions.iterator();
		result.append(condsIt.next());
		while(condsIt.hasNext()) {
			result.append(" " + operator + " " + condsIt.next());
		}
		if(conditions.size() > 1) {
			return setPars(result.toString());
		}
		else {
			return result.toString();
		}
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
	public static String setPars(String expr) {
		if(hasFirstLastPars(expr)) {
			//System.err.println(expr);
			return expr;
		}
		else {
			//System.err.println("(" + expr + ")");
			return "(" + expr + ")";
		}
	}

	/**
	 * Checks if is integer concrete domain.
	 * 
	 * @param domain
	 *            the domain
	 * 
	 * @return true, if is integer concrete domain
	 */
	static boolean isIntegerConcreteDomain(Domain domain) {
		if (Defs.isConcreteDomain(domain)) {
			TypeDomain td = ((ConcreteDomain) domain).getTypeDomain();
			return allowedTypeDomain(td);
		}
		return false;
	}

	/**
	 * Checks if is agent domain.
	 * 
	 * @param domain
	 *            the domain
	 * 
	 * @return true, if is agent domain
	 */
	public static boolean isAgentDomain(Domain domain) {
		String className = domain.getClass().getSimpleName();
		if (className.equals("AgentDomainImpl")
				|| className.equals("AgentDomain")) {
			return true;
		}
		else if (Defs.isConcreteDomain(domain)) {
			className = ((ConcreteDomain) domain).getTypeDomain().getClass()
					.getSimpleName();
			return className.equals("AgentDomainImpl")
					|| className.equals("AgentDomain");
		}
		else {
			return false;
		}
	}

	/**
	 * Allowed type domain.
	 * 
	 * @param domain
	 *            the domain
	 * 
	 * @return true, if successful
	 */
	protected static boolean allowedTypeDomain(TypeDomain domain) {
		String className = domain.getName();
		return className.equals("Integer") || className.equals("Natural")
				|| className.equals("Agent");
		// subsetof Real non � ammesso
		// non mettendolo viene sollevata un'eccezione quando nel modello si inserisce ad esempio
		// domain Num subsetof Real
 		// return className.equals("Real") || className.equals("Integer") || className.equals("Natural")
 		//		|| className.equals("Agent");
	}

	/**
	 * It checks whether the type domain is allowed.
	 * If not, it raises an exception.
	 * 
	 * @param concrDom
	 *            the concr dom
	 * 
	 * @throws AsmNotSupportedException
	 *             the asm not supported exception
	 */
	public static void checkTypeDomain(ConcreteDomain concrDom)
			throws AsmNotSupportedException {
		TypeDomain typeDom = concrDom.getTypeDomain();
		if (!Util.allowedTypeDomain(typeDom)) {
			throw new AsmNotSupportedException("The type domain of "
					+ concrDom.getName() + " cannot be "
					+ typeDom.getName() + ". Only Integer and Natural are allowed.");
		}
	}


//SILVIA
	/**
	 * Not allowed LET type domain.
	 * 
	 * @param className
	 *            the domain name
	 * 
	 * @return true, if successful
	 */
	protected static boolean notAllowedTypeDomain(String className) {
		return className.equals("Real") || className.equals("Integer") || className.equals("Natural")
				|| className.equals("Agent");
	}
	
//SILVIA	
	/**
	 * It checks whether the LET type domain is allowed.
	 * If not, it raises an exception.
	 * 
	 * @param domainName
	 *            the dom of function in LET
	 * 
	 * @throws AsmNotSupportedException
	 *             the asm not supported exception
	 */
	public static void checkRuleDomain(String domainName) throws AsmNotSupportedException{
		if (Util.notAllowedTypeDomain(domainName)){
			throw new AsmNotSupportedException("The type domain of cannot be " + domainName + ". Only finite domains are allowed.");
		}
	}
	
	
	/**
	 * Allowed domain.
	 * 
	 * @param domain the domain
	 * 
	 * @return true, if successful
	 */
	static boolean allowedDomain(Domain domain) {
		return Defs.isEnumDomain(domain) || Defs.isConcreteDomain(domain) ||
				Defs.isBooleanDomain(domain) || Defs.isAbstractDomain(domain);
	}

	/**
	 * Check domain.
	 * 
	 * @param domain
	 *            the domain
	 * 
	 * @throws AsmNotSupportedException
	 *             the asm not supported exception
	 */
	public static void checkDomain(Domain domain) throws AsmNotSupportedException {
		if (!allowedDomain(domain)) {
			throw new AsmNotSupportedException("Il dominio " + domain.getName()
					+ " di tipo " + domain.getClass().getSimpleName()
					+ " non puo' essere esportato in NuSMV.");
		}
	}

	/**
	 * Checks if the location name is permitted. All the temporal operators
	 * names are not available.
	 * 
	 * @param locationName
	 *            the location name
	 * 
	 * @throws AsmNotSupportedException
	 *             the asm not supported exception
	 */
	public static void checkLocationName(String locationName)
			throws AsmNotSupportedException {
		if (locationName.equals("eg") || locationName.equals("ex")
				|| locationName.equals("ef") || locationName.equals("ag")
				|| locationName.equals("ax") || locationName.equals("af")
				|| locationName.equals("eu") || locationName.equals("au")
				|| locationName.equals("x") || locationName.equals("g")
				|| locationName.equals("f") || locationName.equals("u")
				|| locationName.equals("v") || locationName.equals("y")
				|| locationName.equals("z") || locationName.equals("h")
				|| locationName.equals("o") || locationName.equals("s")
				|| locationName.equals("t")) {
			throw new AsmNotSupportedException("\"" + locationName
					+ "\" cannot be used as a function name because it's a"
					+ " keyword.");
		}
	}

	/**
	 * Check update.
	 * 
	 * @param updateMap
	 *            the update map
	 * @param location
	 *            the location
	 * @param cond
	 *            the cond
	 * @param value
	 *            the value
	 * 
	 * @throws AsmNotSupportedException
	 *             the asm not supported exception
	 */
	static void checkUpdate(Map<String, Map<String, String>> updateMap,
			String location, String cond, String value)
			throws AsmNotSupportedException {
		if (!isConsistentUpdate(updateMap, location, cond, value)) {
			throw new AsmNotSupportedException("Update inconsistente della "
					+ "location " + location + " sotto la condizione " + cond
					+ "\nSi vuole aggiornarla contemporaneamente ai valori "
					+ updateMap.get(location).get(cond) + " e " + value + ".");
		}
	}

	/**
	 * Checks if is consistent update.
	 * 
	 * @param updateMap
	 *            the update map
	 * @param location
	 *            the location
	 * @param cond
	 *            the cond
	 * @param value
	 *            the value
	 * 
	 * @return true, if is consistent update
	 */
	static boolean isConsistentUpdate(
			Map<String, Map<String, String>> updateMap, String location,
			String cond, String value) {
		Map<String, String> map = updateMap.get(location);
		return map.containsKey(cond) && !map.get(cond).equals(value);
	}

	/**
	 * Checks if the string starts and ends with a couple of parentheses
	 * 
	 * @param str
	 *            the string to check
	 * @return true if the string starts and ends with a couple of parentheses
	 */
	static boolean hasFirstLastPars(String str) {
		//condizione necessaria e' che ci sia una parentesi all'inizio e una
		//alla fine
		if (str.startsWith("(") && str.endsWith(")")) {
			int counter = 1;// aggiungo la parentesi iniziale
			char c;
			for (int i = 1; i < str.length() - 1; i++) {
				c = str.charAt(i);
				if (c == '(') {
					counter++;// trovata una parentesi aperta
				}
				else if (c == ')') {
					counter--;// trovata una parentesi chiusa
				}
				if (counter == 0) {// si e' chiusa la parentesi iniziale
					return false;// la parentesi iniziale non e' in coppia con quella finale
				}
			}
			counter--;// rimuovo la parentesi finale
			//se il contatore vale 0 vuol dire che la parentesi iniziale e' accoppiata
			//con quella finale
			return counter == 0;
		}
		else {
			return false;
		}
	}

	/**
	 * Not.
	 * 
	 * @param cond
	 *            the cond
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static String not(String cond) {
		if (AsmetaSMVOptions.simplify) {
			if (cond.equals(trueString)) {
				return falseString;
			}
			else if (cond.equals(falseString)) {
				return trueString;
			}
			//se e' cond = !(fooCond) ritorna fooCond
			//e' giusto eliminare le parentesi? Credo di si'.
			else if (cond.charAt(0) == '!' && hasFirstLastPars(cond.substring(1))) {
				return removePars(cond.substring(1));
			}
		}
		return "!" + Util.setPars(cond);
	}

	static String removePars(String str) {
		return str.substring(1, str.length() - 1);
	}

	/**
	 * Not.
	 * 
	 * @param conds the conds
	 * 
	 * @return the list< string>
	 * 
	 * @throws Exception
	 *             the exception
	 */
	static List<String> not(List<String> conds){
		List<String> tempConds = new ArrayList<String>();
		for (String cond : conds) {
			tempConds.add(not(cond));
		}
		return tempConds;
	}

	/**
	 * And.
	 * 
	 * @param str1 the str1
	 * @param str2 the str2
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static String and(String str1, String str2) {
		List<String> list = new ArrayList<String>();
		list.add(str1);
		list.add(str2);
		return and(list);
	}

	/**
	 * And.
	 * 
	 * @param conds
	 *            the conds
	 * 
	 * @return the string
	 * 
	 */
	public static String and(Collection<String> conds) {
		if (AsmetaSMVOptions.simplify) {
			//un false rende false tutto l'and
			if (conds.contains(falseString)) {
				return falseString;
			}
			Set<String> set = new HashSet<String>(conds);// rimuovo i duplicati
			set.remove(trueString);
			if (set.size() == 0) {
				return trueString;//vuol dire che erano tutti true
			}
			else {
				for(String cond: set) {
					//cond and not(cond) = false
					if (set.contains(Util.not(cond))) {
						return falseString;
					}
				}
				return mergeConditions(set, "&");
			}
		}
		return mergeConditions(conds, "&");
	}

	/**
	 * @param str1 the first operand
	 * @param str2 the second operand
	 * @return the string representation of the or over the operands
	 * @throws Exception
	 */
	public static String or(String str1, String str2) {
		List<String> list = new ArrayList<String>();
		list.add(str1);
		list.add(str2);
		return or(list);
	}

	/**
	 * @param conds
	 * @return
	 * @throws Exception
	 */
	public static String or(Collection<String> conds) {
		if (AsmetaSMVOptions.simplify) {
			// se un elemento dell'or e' true, allora l'or e' true
			if (conds.contains(trueString)) {
				return trueString;
			}
			// le condizioni false non concorrono alla valutazione del valore
			// di verita' dell'or. Le posso eliminare.
			Set<String> set = new HashSet<String>(conds);// rimuovo i duplicati
			set.remove(falseString);
			// Se non ci sono piu' elementi nella lista vuol dire che tutti
			// gli elementi erano false; quindi ritorno false.
			if (set.size() == 0) {
				return falseString;
			}
			for (String cond : set) {
				if (set.contains(Util.not(cond))) {
					// cond or not(cond) = true
					return trueString;
				}
			}
			return mergeConditions(set, "|");
		}
		return mergeConditions(conds, "|");
	}

	/**
	 * Not and. Negazione di un and di condizioni; per DeMorgan l'or dei negati
	 * 
	 * @param conds
	 *            the conds
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static String notAnd(List<String> conds){
		List<String> notConds = not(conds);
		return or(notConds);
	}

	/**
	 * Not or. Negazione di un or di condizioni; per DeMorgan l'and dei negati
	 * 
	 * @param conds
	 *            the conds
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	static String notOr(List<String> conds) throws Exception {
		List<String> notConds = not(conds);
		return and(notConds);
	}

	/**
	 * Xor.
	 * 
	 * @param str1
	 *            the str1
	 * @param str2
	 *            the str2
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static String xor(String str1, String str2) throws Exception {
		List<String> list = new ArrayList<String>();
		list.add(str1);
		list.add(str2);
		return xor(list);
	}

	/**
	 * Xor.
	 * 
	 * @param conds
	 *            the conds
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static String xor(List<String> conds) throws Exception {
		List<String> condTemp = new ArrayList<String>();
		int numTrue = 0;
		int numFalse = 0;
		boolean tempRes = false;//risultato temporaneo
		for (String cond : conds) {
			if (cond.equals(trueString)) {
				numTrue++;
				tempRes = (tempRes)? false: true;
			}
			else if(cond.equals(falseString)) {
				numFalse++;
				tempRes = (tempRes)? true: false;
			}
			else {
				condTemp.add(cond);
			}
		}
		int numConds = condTemp.size();//numero di condizioni diverse da true e false
		//se la lista contiene solo true e false, si puo' ritornare direttamente il risultato
		if (numConds == 0) {
			return (tempRes)? trueString: falseString;
		}
		else if (numTrue > 0 || numFalse > 0) {
			//se c'e' almeno un true o un false, e c'e' una sola condizione cond diversa
			//da true e false, si puo' identificare il risultato in funzione di cond.
			if(numConds == 1) {
				//true xor cond = not(cond)
				//false xor cond = cond
				if(tempRes) {
					return not(condTemp.get(0));
				}
				else {
					return condTemp.get(0);
				}
			}
			else {
				//se c'e' piu' di una condizione diversa da true e da false,
				//allora elimino la valutazione di tutti i true e i false (tempRes),
				//mettendolo in xor con il primo elemento della lista.
				//Se tempRes = true, allora (true xor firstCond = not(firstCond)).
				//Se tempRes = false, allora (false xor firstCond = firstCond).
				//Quindi si puo' ignorare tempRes e ritornare la lista senza modificarla.
				if(tempRes) {
					String firstCond = condTemp.get(0);
					condTemp.remove(0);
					condTemp.add(0, not(firstCond));
				}
			}
		}
		return mergeConditions(condTemp, "xor");
	}

	/**
	 * left -> right
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public static String implies(String left, String right) {
		if (AsmetaSMVOptions.simplify) {
			//se sono uguali e' comunque vero
			//false -> false
			//true -> true
			//cond -> cond
			if(left.equals(right)) {
				return trueString;
			}
			else {
				// Se "left" e' false, l'implicazione logica e' true
				// indipendentemente dal valore di "right".
				// Se "right" e' true, l'implicazione logica e' vera
				// indipendentemente dal valore di "left".
				//false -> cond
				//cond -> true
				//false -> true
				if (left.equals(falseString) || right.equals(trueString)) {
					return trueString;
				}
				// Se "left" e' true e "right" e' false, l'implicazione logica e' false
				//true -> false
				else if (left.equals(trueString) && right.equals(falseString)) {
					return falseString;
				}
				//cond -> false = not(cond)
				else if(right.equals(falseString)) {
					return not(left);
				}
				//true -> cond = cond
				else if(left.equals(trueString)) {
					return right;
				}
			}
		}
		return Util.setPars(left + " -> " + right);
	}

	public static String iff(String left, String right) {
		if (AsmetaSMVOptions.simplify) {
			//Se sono uguali vuol dire che sono lo stesso termine o che sono
			//tutti e due true o tutti e due false. In tutti questi tre casi
			//il risultato dell'iff e' true.
			// true <-> true
			// false <-> false
			// cond <-> cond
			if(left.equals(right)) {
				return trueString;
			}
			else if (left.equals(falseString)) {
				//false <-> true 
				if(right.equals(trueString)) {
					return falseString;
				}
				//false <-> cond = not(cond)
				else {
					return Util.not(right);
				}
			}
			else if (left.equals(trueString)) {
				//true <-> false 
				if(right.equals(falseString)) {
					return falseString;
				}
				//true <-> cond = cond
				else {
					return right;
				}
			}
			// cond <-> false = not(cond)
			else if (right.equals(falseString)) {
				return Util.not(left);
			}
			// cond <-> true = cond
			else if (right.equals(trueString)) {
				return left;
			}
		}
		return Util.setPars(left + " <-> " + right);
	}

	/**
	 * Removes the.
	 * 
	 * @param conds
	 *            the conds
	 * @param condToRemove
	 *            the cond to remove
	 * 
	 * @return the list< string>
	 */
	static List<String> remove(Collection<String> conds, String condToRemove) {
		List<String> tempConds = new ArrayList<String>();
		for (String cond: conds) {
			if (!cond.equals(condToRemove)) {
				tempConds.add(cond);
			}
		}
		return tempConds;
	}

	/**
	 * Checks if str is a number.
	 * 
	 * @param str
	 *            a string
	 * 
	 * @return true if str is a number, false otherwise.
	 */
	public static Boolean isNumber(String str) {
		// Cattiva programmazione!
		// Prova a fare il parsing del numero.
		// Se viene sollevata un'eccezione vuol dire che non e' un numero.
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Checks if the string represents an enum.
	 * 
	 * @param str
	 *            a string
	 * 
	 * @return true if the string str is an enum, false otherwise.
	 */
	static Boolean isEnum(String str) {
		// E' un enum se str e' gia' in maiuscolo. Prima, pero', bisogna
		// controllare che non sia un numero o che non cominci con $, perche'
		// qualsiasi numero ed un nome di variabile tipo $A sono tutti maiscoli
		// ma non sono enum e non devono entrare nel secondo ramo dell'if.
		if (isNumber(str) || str.startsWith("$")) {
			return false;
		}
		else {
			return str.toUpperCase().equals(str);
		}
	}

	private static boolean isBoolean(String str) {
		return str.equals(falseString) || str.equals(trueString);
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
	public static String equals(String left, String right) {
		if (AsmetaSMVOptions.simplify) {
			// Se le due stringhe sono uguali, qualsiasi cosa siano (termini,
			// numeri, enum, boolean) ritorna true.
			if (left.equals(right)) {
				return trueString;
			}
			// Se non sono uguali, ma sono due numeri, due enum o due boolean, allora vuol
			// dire che sono diversi e allora ritorna false.
			else if ((isNumber(left) && isNumber(right))
					|| (isEnum(left) && isEnum(right))
					|| (isBoolean(left) && isBoolean(right))) {
				return falseString;
			}
			else if(isBoolean(left)) {
				if(left.equals(trueString)) {
					return right;
				}
				else {
					return not(right);
				}
			}
			else if(isBoolean(right)) {
				if(right.equals(trueString)) {
					return left;
				}
				else {
					return not(left);
				}
			}
		}
		return setPars(left + " = " + right);
	}

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
	public static String notEquals(String left, String right) {
		if (AsmetaSMVOptions.simplify) {
			// Se le due stringhe sono uguali, qualsiasi cosa siano (termini,
			// numeri, enum, boolean) ritorna false.
			if (left.equals(right)) {
				return falseString;
			}
			// Se non sono uguali, ma sono due numeri o due enum, allora vuol
			// dire che sono diversi e allora ritorna true.
			else if ((isNumber(left) && isNumber(right))
					|| (isEnum(left) && isEnum(right))
					|| (isBoolean(left) && isBoolean(right))) {
				return trueString;
			}
			else if(isBoolean(left)) {
				if(left.equals(falseString)) {
					return right;
				}
				else {
					return not(right);
				}
			}
			else if(isBoolean(right)) {
				if(right.equals(falseString)) {
					return left;
				}
				else {
					return not(left);
				}
			}
		}
		return setPars(left + " != " + right);
	}

	public static String getLocationName(Function function, Value[] values) {
		StringBuilder locationName = new StringBuilder();
		locationName.append(Util.getFunctionName(function));
		for(Value value: values) {
			locationName.append("_" + value.toString().toUpperCase());
		}
		return locationName.toString();
	}

	/**
	 * Rappresenta un insieme di valori come un insieme.
	 * 
	 * @param values
	 *            un insieme di valori
	 * @return una stringa che rappresenta i valori come un insieme
	 */
	static String asSet(List<String> values) {
		StringBuilder set = new StringBuilder();
		if (values.size() > 0) {
			set.append("{");
			int i = 0;
			for (; i < values.size() - 1; i++) {
				set.append(values.get(i) + ", ");
			}
			set.append(values.get(i) + "}");
		}
		return set.toString();
	}

	public static String asSet(Set<String> values) {
		return asSet(new ArrayList<String>(values));
	}

	/**
	 * Crea un nuovo nome di file sostituendo l'estensione di nameFile con
	 * newExtension.
	 * 
	 * @param fileName
	 *            Nome del file di partenza. Deve essere dotato anche
	 *            dell'estensione.
	 * @param newExtension
	 *            Nuova estensione del file.
	 * @return
	 */
	public static String replaceExtension(String fileName, String newExtension) {
		return fileName.substring(0, fileName.lastIndexOf(".")).concat(
				"." + newExtension);
	}

	/**
	 * Utilizzata in smv4val per rimuovere i next delle funzioni monitorate
	 * dalle condizioni e dai valori. Ora non dovrebbe piu' essere necessario.
	 * 
	 * @param input
	 *            Stringa in input.
	 * @return La stringa input da cui sono stati rimossi gli operatori next.
	 */
	/*
	 * public static String removeNext(String input) { // se la stringa non
	 * contiene l'operatore next si puo' ritornare la // stringa fornita in
	 * input. // Si suppone che il next si possa presentare solo nelle forme
	 * " next(" // e "(next(". Ci sono altri casi? if (input.indexOf(" next(")
	 * == -1 // non c'e' un next preceduto da uno // spazio &&
	 * input.indexOf("(next(") == -1 // non c'e' un next preceduto // da una
	 * parentesi && input.indexOf("next(") != 0// non c'e' un next in prima //
	 * posizione ) return input; StringBuilder sb = new StringBuilder();
	 * List<Integer> parIndex = new ArrayList<Integer>(); List<Integer>
	 * openParIndex = new ArrayList<Integer>(); List<Integer> closeParIndex =
	 * new ArrayList<Integer>(); List<Integer> openNextParIndex = new
	 * ArrayList<Integer>(); List<Integer> closeNextParIndex = new
	 * ArrayList<Integer>(); int index = -1; // ciclo per ottenere una lista
	 * delle parentesi aperte do { // Cerca da una posizione successiva
	 * all'ultima parentesi valutata index = input.indexOf("(", index + 1); if
	 * (index > -1) { openParIndex.add(index);// lista delle parentesi aperte
	 * parIndex.add(index);// lista di tutte le parentesi } } while (index >
	 * -1);// fino a quando ci sono parentesi index = -1; // ciclo per ottenere
	 * una lista delle parentesi chiuse do { index = input.indexOf(")", index +
	 * 1); if (index > -1) { closeParIndex.add(index);// lista delle parentesi
	 * chiuse parIndex.add(index);// lista di tutte le parentesi } } while
	 * (index > -1); // La lista di tutte le parentesi deve essere ordinata per
	 * avere le // parentesi nell'ordine in cui appaiono nella stringa.
	 * Collections.sort(parIndex); index = -5; int candidateIndex1,
	 * candidateIndex2, candidateIndex3; // Ciclo per ottenere le parentesi
	 * aperte dell'operatore next. do { candidateIndex1 = input.indexOf("next(",
	 * index + 5); if (candidateIndex1 == 0) index = candidateIndex1; else {
	 * candidateIndex2 = input.indexOf(" next(", index + 5); candidateIndex3 =
	 * input.indexOf("(next(", index + 5); if (candidateIndex2 == -1 &&
	 * candidateIndex3 == -1) index = -1; else if (candidateIndex2 == -1) index
	 * = candidateIndex3 + 1; else if (candidateIndex3 == -1) index =
	 * candidateIndex2 + 1; else index = Math.min(candidateIndex2,
	 * candidateIndex3) + 1; } // La ricerca della prossima parentesi aperta di
	 * una next puo' // cominciare 5 posizioni dopo l'inizio del next corrente.
	 * // index = input.indexOf("next(", index + 5);
	 * 
	 * if (index > -1) { // la parentesi si trova 4 posizioni dopo la stringa
	 * cercata openNextParIndex.add(index + 4); } } while (index > -1); int
	 * counter, indexInList, curParIndex; // Cicla tutte le parentesi aperte dei
	 * next per trovare le parentesi // chiuse corrispondenti. for (Integer i :
	 * openNextParIndex) { // counter e' il contatore delle parentesi. Le
	 * parentesi aperte lo // incrementano, quelle chiuse lo decrementano.
	 * Quando counter vale // zero vuol dire che e' stata trovata la parentesi
	 * chiusa della // next corrente. counter = 1;// la prima parentesi aperta
	 * indexInList = parIndex.indexOf(i);// indice nella lista di tutte le //
	 * parentesi curParIndex = 0; // Ciclo sulle parentesi aperte e chiuse
	 * successive alla aperta che // mi interessa e mi fermo quando trovo la sua
	 * parentesi di // chiusura. for (int j = indexInList + 1; counter > 0; j++)
	 * { curParIndex = parIndex.get(j); if (openParIndex.contains(curParIndex))
	 * counter++;// una nuova parentesi aperta else if
	 * (closeParIndex.contains(curParIndex)) counter--;// si e' chiusa una
	 * parentesi } // Aggiungo alla lista delle parentesi chiuse dei next la
	 * parentesi // chiusa trovata. // Si assume che sia sempre possibile.
	 * Questo dovrebbe essere // garantito // dal precendete controllo del
	 * parser sul fatto che le parentesi // siano corrette.
	 * closeNextParIndex.add(curParIndex); } // Ora bisogna cancellare i next
	 * con le relative parentesi aperte e // chiuse. // Si mette in uno
	 * stringBuffer solo le porzioni di testo valide. int initText = 0;// da
	 * dove iniziare a copiare il testo. int endText;// dove finire di copiare
	 * il testo index = -1;// indice della lista // ciclo sulle parentesi aperte
	 * dei next for (int i : openNextParIndex) { index++;// indice corrente
	 * della lista endText = i - 4;// bisogna copiare fino a prima di "next" if
	 * (initText < endText) sb.append(input.substring(initText, endText));
	 * initText = i + 1;// indice del primo carattere dell'argomento del // next
	 * endText = closeNextParIndex.get(index);// parentesi finale del next
	 * sb.append(input.substring(initText, endText)); // nel prossimo giro il
	 * testo da copiare inizia dopo la corrente // parentesi chiusa. initText =
	 * endText + 1; } // alla fine devo copiare il testo che avanza dopo la
	 * parentesi chiusa // dell'ultimo next endText = input.length(); if
	 * (initText <= endText) sb.append(input.substring(initText, endText)); //
	 * System.out.println(sb.toString()); return sb.toString(); }
	 */

	static String printFormatted(String string) {
		StringBuilder sb = new StringBuilder();
		int lastIndex = string.length();
		int rowLength = 80;
		int i = 0;
		String substring;
		int lastSpaceIndex = -1;
		int lastSubStringIndex;
		int returnIndex;
		do {
			returnIndex = string.indexOf("\n", i);
			if (returnIndex > -1 && returnIndex < rowLength) {
				sb.append(string.substring(i, returnIndex + 1));
				i = returnIndex + 1;
				continue;
			}

			// Se ci sono rowLength caratteri disponibili li leggo, altrimenti
			// leggo solo fino alla fine della stringa.
			lastSubStringIndex = Math.min(i + rowLength, lastIndex);
			// System.out.println("lastSubStringIndex "+lastSubStringIndex);
			substring = string.substring(i, lastSubStringIndex);
			// System.out.println("substring "+substring);
			// returnIndex = substring.indexOf("\n");
			// if(returnIndex > -1){
			// substring.indexOf("\n");
			// sb.append(substring.substring(0, i + returnIndex));
			// i = i + returnIndex + 1;
			// }
			// else{
			if (lastSubStringIndex == lastIndex) {
				sb.append(string.substring(i, lastIndex));
				i = lastIndex + 1;
			} else {
				lastSpaceIndex = substring.lastIndexOf(" ");
				if (substring.lastIndexOf(" ") > -1) {
					lastSpaceIndex = lastSpaceIndex + i;
					// System.out.println("lastSpaceIndex "+lastSpaceIndex);
					sb.append(string.substring(i, lastSpaceIndex) + "\n");
					// System.out.println("substrin2 "+string.substring(i,
					// lastSpaceIndex));
					i = lastSpaceIndex + 1;
				} else {
					sb.append(string.substring(i, lastIndex));
					i = lastIndex + 1;
				}
			}
			// }
		} while (i <= lastIndex);
		// System.out.print("*"+sb.toString()+"*");
		return sb.toString();
	}


	public static String getPath() {
		return path;
	}

	public static void setPath(String path) {
		Util.path = path;
	}

	public static String getDir() {
		return dir;
	}

	public static void setDir(String dir) {
		Util.dir = dir;
	}

	public static String getMainAsmName() {
		return mainAsmName;
	}

	public static void setMainAsmName(String mainAsmName) {
		Util.mainAsmName = mainAsmName;
	}

	public static Boolean getIsAsynchr() {
		return isAsynchr;
	}

	public static void setIsAsynchr(Boolean isAsynchr) {
		Util.isAsynchr = isAsynchr;
	}
}

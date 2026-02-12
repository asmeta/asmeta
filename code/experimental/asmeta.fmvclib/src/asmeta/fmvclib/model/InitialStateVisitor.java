package asmeta.fmvclib.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import org.asmeta.parser.util.Defs;
import org.asmeta.parser.util.ImportFlattener;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.Value;
import org.mariuszgromada.math.mxparser.Expression;

import asmeta.definitions.DynamicFunction;
import asmeta.definitions.Function;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Initialization;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;

@SuppressWarnings("rawtypes")
public class InitialStateVisitor extends org.asmeta.parser.util.ReflectiveVisitor {

	public Set<Domain> domains;
	public List<Function> functions;
	private List<DomainDefinition> domainDefinition;
	private List<DomainInitialization> domainInitialization;
	public Map<String, Set<String>> contrFuncLocations, derFuncLocations, statFuncLocations, sharedFuncLocations;
	public SortedSet<String> contrLocations;
	RuleVisitor rv;
	protected SortedSet<String> derived;
	protected SortedSet<String> sharedLocations;
	SortedMap<String, Map<String, String>> derivedMap;
	public SortedMap<String, String> initMap;
	protected SortedMap<String, String> varsDecl;
	SortedMap<String, String> chooseVarsDecl;
	/**
	 * It contains the conditions found during the visit of the ASM.
	 */
	private Stack<String> conditions;
	public Map<String, String> locationDomain, functionDomain;

	protected Map<String, List<Value[]>> domainValues;
	private List<String> enumDomain;
	public Environment env;
	public TermVisitor tp;
	private Map<String, String> undefValue;

	public Map<String, Set<String>> usedStatDerInDer, usedContrMonInDer;

	public InitialStateVisitor() {
		env = new Environment();
		tp = new TermVisitor(env, this);
		chooseVarsDecl = new TreeMap<String, String>();
		setConditions(new Stack<String>());
		rv = new RuleVisitor(this);
		initMap = new TreeMap<String, String>();
	}

	/**
	 * Visit.
	 * 
	 * @param setTerm the set term
	 * 
	 * @return the sorted set< string>
	 */
	public SortedSet<String> visit(SetTerm setTerm) {
		SortedSet<String> set = new TreeSet<String>();
		for (Term term : setTerm.getTerm()) {
			// out.println(term + " " + tp.visit(term));
			set.add(tp.visit(term));
		}
		return set;
	}

	public SortedSet<String> visit(EnumTd domain) {
		SortedSet<String> set = new TreeSet<String>();
		for (EnumElement el : domain.getElement()) {
			set.add(el.getSymbol());
		}
		return set;
	}

	/**
	 * Visita l'inizializzazione di default.
	 * 
	 * @param init the init
	 */
	protected void visitDefault(Initialization init) {
		String undef;
		initMap = new TreeMap<String, String>();
		if (init != null) {
			visitFuncInits(init.getFunctionInitialization());
		}
		// le locazioni che non sono state inizializzate in modo esplicito
		// e che hanno l'undef per NuSMV sono inizializzate ad undef.
		for (String location : contrLocations) {
			if (!initMap.containsKey(location)) {
				undef = getUndefValue().get(locationDomain.get(location));
				if (undef != null) {
					initMap.put(location, undef);
				}
			}
		}
	}

	/**
	 * Visit a collection of FunctionInitialization
	 * 
	 * @param funcs the collection of FunctionInitialization
	 */
	private void visitFuncInits(Collection<FunctionInitialization> funcs) {
		if (funcs != null) {
			for (FunctionInitialization init : funcs) {
				visitInit(init);
			}
		}
	}

	/**
	 * Visit the initialization of functions.
	 * 
	 * @param init the FunctionInitialization section
	 */
	public void visitInit(FunctionInitialization init) {
		ImportFlattener importFlattener = new ImportFlattener(((Initialization) init.eContainer()).getAsm(),
				AsmetaFMVCModel.ASM_PATH);
		importFlattener.visit();
		domains = importFlattener.getDomains();
		functions = importFlattener.getFunctions();
		setDomainDefinition(importFlattener.getDomainDefinitions());
		setDomainInitialization(importFlattener.getDomainInitializations());
		visitDomains();
		visitFunctions();
		List<VariableTerm> vars = init.getVariable();
		DynamicFunction func = init.getInitializedFunction();
		List<Location> locations = getLocations(func);
		Term term = init.getBody();
		String locStr, termStr;

		for (Location loc : locations) {
			env.setVarsValues(vars, loc.getElements());
			locStr = visit(loc);
			termStr = tp.visit(term);
			// per l'inizializzazione esplicita ad undef
			if (termStr == null || termStr.equals("undef")) {
				String undef = getUndefValue().get(locationDomain.get(locStr));
				if (undef != null) {
					termStr = undef;
				}
			} else if (new Expression(termStr).checkSyntax()) {
				termStr = String.valueOf((int) (new Expression(termStr).calculate()));
			}

			if (termStr != null) {
				termStr = termStr.trim().replace(")", "").replace("(", "").replace(" ", "");
				int conditionsCount = termStr.split("\\?").length;
				for (int i = 0; i < conditionsCount; i++) {
					if (termStr.contains("=") && termStr.contains("?") && termStr.contains(":")) {
						// Conditional assignment

						String condition = termStr.split("\\?")[0];
						if (condition.split("=")[0].equals(condition.split("=")[1])) {
							// True part
							String assignment = termStr.split("\\?")[1];
							termStr = assignment.split(":")[0];
							break;
						} else {
							// False part, visit the remaining part
							termStr = termStr.substring(termStr.split(":")[0].length() + 1);
						}
					}
				}
				initMap.put(locStr, termStr);
			}
		}
	}

	/**
	 * Gets all the locations of a function.
	 * 
	 * @param func the func
	 * 
	 * @return the list of locations of the function
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Location> getLocations(Function func) {
		Domain domain = func.getDomain();
		ArrayList<Location> locations = new ArrayList<Location>();
		if (domain == null) {
			locations.add(new Location(func, new Value[0]));
		} else {
			for (Value[] v : (List<Value[]>) asValueList(domain)) {
				locations.add(new Location(func, v));
			}
		}
		return locations;
	}

	/**
	 * Visit of a location.
	 * 
	 * @param location a location
	 * 
	 * @return the string that represents the location
	 */
	private String visit(Location location) {
		StringBuilder s = new StringBuilder();
		Function f = location.getSignature();
		s.append(Util.getFunctionName(f));
		Value[] values = location.getElements();
		for (Value value : values) {
			s.append("_" + value.toString().toUpperCase());
		}
		return s.toString();
	}

	/**
	 * Visit.
	 * 
	 * @param pD the p d
	 * 
	 * @return the string
	 */
	String visit(PowersetDomain pD) {
		return (String) visit(pD.getBaseDomain());
	}

	/**
	 * As value list.
	 * 
	 * @param d the d
	 * 
	 * @return the object
	 */
	public Object asValueList(Domain d) {
		return invokeMethod(d, "asValueList");
	}

	public List<Value[]> asValueList(ProductDomain domain) {
		List<Domain> domains = domain.getDomains();
		ArrayList<Value[]> result = new ArrayList<Value[]>();
		combineValues(domains, 0, result, new Stack<Value>());
		return result;
	}

	/**
	 * Combine values.
	 * 
	 * @param domains the domains
	 * @param index   the index
	 * @param result  the result
	 * @param tupla   the tupla
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public void combineValues(List<Domain> domains, int index, ArrayList<Value[]> result, Stack<Value> tupla) {
		Domain domain = domains.get(index);
		List<Value[]> values = (ArrayList<Value[]>) asValueList(domain);
		for (Value[] value : values) {
			for (Value v : value) {
				tupla.push(v);
			}
			if (domains.size() == index + 1) {
				Value[] temp = new Value[tupla.size()];
				for (int i = 0; i < temp.length; i++) {
					temp[i] = tupla.get(i);
				}
				result.add(temp);
			} else {
				combineValues(domains, index + 1, result, tupla);
			}
			for (Value v : value) {
				tupla.pop();
			}
		}
	}

	protected void visitFunctions() {
		String locName, codomainName;
		Domain codomain;
		contrLocations = new TreeSet<String>();
		sharedLocations = new TreeSet<String>();
		contrFuncLocations = new TreeMap<String, Set<String>>();
		sharedFuncLocations = new TreeMap<String, Set<String>>();
		derFuncLocations = new TreeMap<String, Set<String>>();
		statFuncLocations = new TreeMap<String, Set<String>>();
		locationDomain = new HashMap<String, String>();
		functionDomain = new HashMap<String, String>();
		varsDecl = new TreeMap<String, String>();
		derived = new TreeSet<String>();

		SortedSet<String> locationSet = null;
		for (Function func : functions) {
			codomain = func.getCodomain();
			codomainName = Util.getDomainName(codomain);
			locationSet = new TreeSet<String>();
			String functionName = Util.getFunctionName(func);
			if (Defs.isControlled(func) || Defs.isOut(func)) {
				contrFuncLocations.put(functionName, locationSet);
			} else if (Defs.isShared(func)) {
				sharedFuncLocations.put(functionName, locationSet);
			} else if (Defs.isDerived(func)) {
				derFuncLocations.put(functionName, locationSet);
			} else if (Defs.isStatic(func)) {
				statFuncLocations.put(functionName, locationSet);
			}

			functionDomain.put(functionName, codomainName);
			for (Location location : getLocations(func)) {
				locName = visit(location);
				Util.checkLocationName(locName);
				locationSet.add(locName);
				locationDomain.put(locName, codomainName);// associa ad un nome di locazione il suo dominio
				if (!Defs.isDerived(func) && !Defs.isStatic(func)) {
					if (Defs.isControlled(func) || Defs.isOut(func)) {
						contrLocations.add(locName);
					} else if (Defs.isShared(func)) {
						sharedLocations.add(locName);
					}
				} else {
					if (!Defs.isAbstractConst(func) && !Defs.isAgentConst(func)) {
						env.usedDerLoc.add(locName);
						derived.add(locName);
					}
				}
			}
		}
	}

	/**
	 * It visits the domains.
	 * 
	 * @throws AsmNotSupportedException the asm not supported exception
	 */
	@SuppressWarnings("unchecked")
	protected void visitDomains() throws RuntimeException {
		List<Value[]> values = new ArrayList<Value[]>();
		SortedSet<String> set = new TreeSet<String>();
		SortedSet<String> setWithUndef;
		Iterator<String> iterator;
		String domName, str, funcName, undefValueStr;
		int first, last, number;
		Value[] value = new BooleanValue[1];
		setUndefValue(new HashMap<String, String>());
		domainValues = new HashMap<String, List<Value[]>>();
		setEnumDomain(new ArrayList<String>());

		// Boolean domain
		set.add("FALSE");
		set.add("TRUE");
		value[0] = BooleanValue.TRUE;
		values.add(value.clone());
		value[0] = BooleanValue.FALSE;
		values.add(value.clone());
		domainValues.put("Boolean", values);

		set = new TreeSet<String>();
		values = new ArrayList<Value[]>();

		Map<ConcreteDomain, Term> concrDoms = new HashMap<ConcreteDomain, Term>();
		// Definition of concrete domains
		for (DomainDefinition domDef : getDomainDefinition()) {
			concrDoms.put(domDef.getDefinedDomain(), domDef.getBody());
		}
		// Initialization of concrete domains
		for (DomainInitialization domInit : getDomainInitialization()) {
			concrDoms.put(domInit.getInitializedDomain(), domInit.getBody());
		}
		ConcreteDomain concreteDomain;
		for (Entry<ConcreteDomain, Term> concrDomsEntrySet : concrDoms.entrySet()) {
			concreteDomain = concrDomsEntrySet.getKey();
			if (!concreteDomain.getTypeDomain().getName().equals("Agent")) {
				Util.checkTypeDomain(concreteDomain);
				domName = Util.getDomainName(concreteDomain);
				set = (SortedSet<String>) visit(concrDomsEntrySet.getValue());
				iterator = set.iterator();
				values = new ArrayList<Value[]>();
				str = iterator.next();

				value = new IntegerValue[1];
				value[0] = new IntegerValue(str);
				values.add(value.clone());
				first = last = Integer.parseInt(str);
				while (iterator.hasNext()) {
					str = iterator.next();
					value[0] = new IntegerValue(str);
					values.add(value.clone());
					number = Integer.parseInt(str);
					if (number < first) {
						first = number;
					} else if (number > last) {
						last = number;
					}
				}
				domainValues.put(domName, values);

				// undef representation
				undefValueStr = "-2147483647";
				getUndefValue().put(domName, undefValueStr);
				setWithUndef = new TreeSet<String>(set);
				setWithUndef.add(undefValueStr);
			}
		}
		// tutti i domini dichiarati nel modello ASM
		for (Domain domain : domains) {
			Util.checkDomain(domain);// it checks whether the domain is supported
			domName = Util.getDomainName(domain);
			if (Defs.isEnumDomain(domain)) {
				getEnumDomain().add(domName);// la lista contiene i nomi dei domini enumerativi
				set = (SortedSet<String>) visit(domain);
				// undef representation
				undefValueStr = domName.toUpperCase() + "_UNDEF";
				getUndefValue().put(domName, undefValueStr);
				setWithUndef = new TreeSet<String>(set);
				setWithUndef.add(undefValueStr);

				values = new ArrayList<Value[]>();
				value = new EnumValue[1];
				iterator = set.iterator();
				str = iterator.next();
				value[0] = new EnumValue(str);
				values.add(value.clone());
				while (iterator.hasNext()) {
					str = iterator.next();
					value[0] = new EnumValue(str);
					values.add(value.clone());
				}
				domainValues.put(domName, values);
			} else if (Defs.isAbstractDomain(domain)) {
				set = new TreeSet<String>();
				values = new ArrayList<Value[]>();
				for (Function func : functions) {
					if (Util.getDomainName(func.getCodomain()).equals(domName)) {
						if (func.getArity() == 0 && Defs.isAbstractConst(func)) {
							funcName = Util.getFunctionName(func);
							set.add(funcName.toUpperCase());
							value = new EnumValue[1];
							value[0] = new EnumValue(funcName.toUpperCase());
							values.add(value);
						} else {
							// throw new AsmNotSupportedException("Gli agenti possono solo essere
							// variabili.");
						}
					}
				}
				// note that also empty domains are added
				// abstractDomains.add(domName);
				domainValues.put(domName, values);

				// undef representation
				undefValueStr = domName.toUpperCase() + "_UNDEF";
				getUndefValue().put(domName, undefValueStr);
				setWithUndef = new TreeSet<String>(set);
				setWithUndef.add(undefValueStr);
			}
		}
	}

	public List<Value[]> asValueList(BooleanDomain domain) {
		return domainValues.get("Boolean");
	}

	public List<Value[]> asValueList(ConcreteDomain domain) {
		String domainName = Util.getDomainName(domain);
		return domainValues.get(domainName);
	}

	public List<Value[]> asValueList(AgentDomain domain) {
		return domainValues.get("Agent");
	}

	public List<Value[]> asValueList(EnumTd domain) {
		return domainValues.get(Util.getDomainName(domain));
	}

	public List<Value[]> asValueList(AbstractTd domain) {
		return domainValues.get(Util.getDomainName(domain));
	}

	public Map<String, String> getUndefValue() {
		return undefValue;
	}

	public void setUndefValue(Map<String, String> undefValue) {
		this.undefValue = undefValue;
	}

	public List<DomainDefinition> getDomainDefinition() {
		return domainDefinition;
	}

	public void setDomainDefinition(List<DomainDefinition> domainDefinition) {
		this.domainDefinition = domainDefinition;
	}

	public List<DomainInitialization> getDomainInitialization() {
		return domainInitialization;
	}

	public void setDomainInitialization(List<DomainInitialization> domainInitialization) {
		this.domainInitialization = domainInitialization;
	}

	public Stack<String> getConditions() {
		return conditions;
	}

	public void setConditions(Stack<String> conditions) {
		this.conditions = conditions;
	}

	public List<String> getEnumDomain() {
		return enumDomain;
	}

	public void setEnumDomain(List<String> enumDomain) {
		this.enumDomain = enumDomain;
	}
}

package org.asmeta.nusmv;

import static java.lang.System.out;
import static org.asmeta.nusmv.util.Util.checkDomain;
import static org.asmeta.nusmv.util.Util.checkTypeDomain;
import static org.asmeta.nusmv.util.Util.falseString;
import static org.asmeta.nusmv.util.Util.getDir;
import static org.asmeta.nusmv.util.Util.getDomainName;
import static org.asmeta.nusmv.util.Util.getFunctionName;
import static org.asmeta.nusmv.util.Util.isAgentDomain;
import static org.asmeta.nusmv.util.Util.notUsedMess;
import static org.asmeta.nusmv.util.Util.setPars;
import static org.asmeta.nusmv.util.Util.trueString;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

import org.asmeta.flattener.AsmetaMultipleFlattener;
import org.asmeta.flattener.FlattenerSetting;
import org.asmeta.flattener.RemoveArgumentsFlattener;
import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.asmeta.flattener.rule.AsmetaFlattener;
import org.asmeta.flattener.rule.CaseRuleFlattener;
import org.asmeta.flattener.rule.ForallRuleFlattener;
import org.asmeta.flattener.rule.LetRuleFlattener;
import org.asmeta.flattener.rule.MacroCallRuleFlattener;
import org.asmeta.nusmv.util.AsmNotSupportedException;
import org.asmeta.nusmv.util.Util;
import org.asmeta.parser.Defs;
import org.asmeta.parser.util.ImportFlattener;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.CompassionConstraint;
import asmeta.definitions.CtlSpec;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.FairnessConstraint;
import asmeta.definitions.Function;
import asmeta.definitions.InvarConstraint;
import asmeta.definitions.Invariant;
import asmeta.definitions.JusticeConstraint;
import asmeta.definitions.LtlSpec;
import asmeta.definitions.Property;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.TemporalProperty;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.impl.ConcreteDomainImpl;
import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
import asmeta.structure.DomainDefinition;
import asmeta.structure.DomainInitialization;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Initialization;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;

/**
 * The Class MapVisitor.
 */
public class MapVisitor extends org.asmeta.parser.util.ReflectiveVisitor {

	// list of flatteners
	public static Class<? extends AsmetaFlattener>[] ALL_SMV_FLATTENERS = new Class[] { MacroCallRuleFlattener.class,
			ForallRuleFlattener.class, RemoveArgumentsFlattener.class, LetRuleFlattener.class, CaseRuleFlattener.class,
			RemoveNestingFlattener.class };

	private Map<Location, String> locationNameToNusmvVariableName;
	RuleVisitor rv;
	public Map<Rule, List<String>> ruleCond;
	List<String> allProp;
	public Map<String, List<String>> locationReachabilityConds;

	List<Boolean> nuSmvPropsResults;
	private Map<String, Boolean> mapPropResult;
	private List<String> agentsDomains;// list of agent domains names
	Map<String, String> agentDomain;// given an agent name, it gives its domain
	private List<String> constants;// constants contain agents' names

	public Set<Domain> domains;
	public List<Function> functions;
	private List<DomainDefinition> domainDefinition;
	private List<DomainInitialization> domainInitialization;
	public Map<String, Set<String>> contrFuncLocations, derFuncLocations, statFuncLocations, monFuncLocations,
			sharedFuncLocations;
	public SortedSet<String> monLocations, contrLocations;
	protected SortedSet<String> derived;

	protected SortedSet<String> sharedLocations;
	private Map<Location, String> locationName;
	SortedMap<String, Map<String, String>> derivedMap;
	public UpdateMap updateMap;
	public SortedMap<String, String> initMap;
	protected SortedMap<String, String> varsDecl;
	SortedMap<String, String> chooseVarsDecl;
	/**
	 * It contains the conditions found during the visit of the ASM.
	 */
	private Stack<String> conditions;
	public Map<String, String> locationDomain, functionDomain;
	Map<String, String> invars;
	/**
	 * It associates to the AsmetaL domain name the representation as NuSMV type
	 * (without the undef value). It is required by those domains that do not
	 * provide the undef value (as the Boolean domain) and in the choose rules
	 * (i.e., when we choose over a domain, we cannot choose the undef value).
	 */
	protected Map<String, String> domainSmv;
	/**
	 * It associates the AsmetaL domain name with the representation as NuSMV type
	 * (without the undef value). It is required by those domains that do not
	 * provide the undef value (as the Boolean domain) and in the choose rules
	 * (i.e., when we choose over a domain, we cannot choose the undef value).
	 */
	protected Map<String, String> domainSmvWithUndef;
	/**
	 * It associates each domain name with a set containing its values.
	 */
	public Map<String, SortedSet<String>> domainSet;
	// TODO: I don't know why list of "arrays" of values are needed.
	// Are they really needed? Shouldn't a list of values enough?
	protected Map<String, List<Value[]>> domainValues;
	private List<String> enumDomain;
	public Environment env;
	public TermVisitor tp;
	public Map<String, String> nusmvNameToLocation;
	// private int seqCounter;
	public ArrayList<String> ctlList, ltlList;
	public ArrayList<String> ctlListNames, ltlListNames;
	public ArrayList<String> invariantInAgCTL;
	private ArrayList<String> justiceConstraintsList;
	private ArrayList<String[]> compassionConstraintsList;
	private ArrayList<String> invarConstraintsList;
	private Map<String, String> undefValue;
	DerivedVisitor dv;
	private HashMap<Integer, String> propertiesCounterExample;
	private HashMap<Integer, String> propertiesForPrinting;

	public TreeSet<String> controlledLocationInitialized;
	public Map<ConditionalRule, List<String>> condRuleEvalToTrueThen, condRuleEvalToTrueElse;
	public Map<MacroDeclaration, Integer> macroRuleCalled;
	public Map<ConditionalRule, List<String>> condRuleIsComplete;
	public Map<CaseRule, List<String>> caseRuleIsComplete;
	public Map<String, List<String>> statDerReachabilityConds;
	public Map<String, Set<String>> usedStatDerInDer, usedContrMonInDer;
	public Map<ChooseRule, List<String[]>> chooseRuleSetIsEmpty;
	public Map<ForallRule, List<String>> forallRuleSetIsEmpty;

	protected MapVisitor() {
		env = new Environment();
		tp = new TermVisitor(env, this);
		chooseVarsDecl = new TreeMap<String, String>();
		setConditions(new Stack<String>());
		invars = new TreeMap<String, String>();
		setConstants(new ArrayList<String>());
		ruleCond = new HashMap<Rule, List<String>>();
		setMapPropResult(new HashMap<String, Boolean>());
		setRv(new RuleVisitor(this));

		if (AsmetaSMVOptions.doAsmetaMA) {
			locationReachabilityConds = new HashMap<String, List<String>>();
			controlledLocationInitialized = new TreeSet<String>();
			condRuleEvalToTrueThen = new HashMap<ConditionalRule, List<String>>();
			condRuleEvalToTrueElse = new HashMap<ConditionalRule, List<String>>();
			macroRuleCalled = new HashMap<MacroDeclaration, Integer>();
			condRuleIsComplete = new HashMap<ConditionalRule, List<String>>();
			caseRuleIsComplete = new HashMap<CaseRule, List<String>>();
			statDerReachabilityConds = new HashMap<String, List<String>>();
			chooseRuleSetIsEmpty = new HashMap<ChooseRule, List<String[]>>();
			forallRuleSetIsEmpty = new HashMap<ForallRule, List<String>>();
		}
	}

	/**
	 * It checks if domName is an enumerative domain.
	 * 
	 * @param domName the domain name
	 * 
	 * @return true if domName is an enumerative domain
	 */
	private boolean isEnumDomain(String domName) {
		return getEnumDomain().contains(domName);
	}

	/**
	 * Controlla se bisogna aggiungere il controllo sull'appartenenza al dominio.
	 * 
	 * @param domName the domain name
	 * 
	 * @return true, if successful
	 */
	protected boolean needCheckOnDomain(String domName) {
		boolean checkCond = (isEnumDomain(domName) || domName.equals("Boolean") || domName.equals("boolean")
				|| !AsmetaSMVOptions.isCheckConcrete());
		if (AsmetaSMVOptions.isUseNuXmvTime() || AsmetaSMVOptions.isUseNuXmv())
			return !(checkCond || domName.equals("Integer") || domName.equals("integer") || domName.equals("Real")
					|| domName.equals("real"));
		else
			return !checkCond;
	}

	/**
	 * It generates the NuSMV file.
	 * 
	 * @param smvFileName the name of the NuSMV file
	 * @throws Exception
	 */
	public void printSmv(String smvFileName) throws Exception {
		File smvFile = new File(smvFileName);
		PrintWriter smv = null;
		try {
			smv = new PrintWriter(smvFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		printSmv(smvFileName, smv);
		if (!AsmetaSMVOptions.keepNuSMVfile) {
			// System.out.println("destroy");
			smvFile.deleteOnExit();// it should be "delete" (but not here). "delete" here is too early
		}
	}

	/**
	 * It generates the NuSMV file.
	 * 
	 * @param smvFileName the name of the NuSMV file
	 * @throws Exception
	 */
	public void printSmv(String smvFileName, PrintWriter smv) throws Exception {
		printMainModule(smvFileName, smv);
		smv.close();
	}

	void printMainModule(String smvFileName, PrintWriter smv) {
		smv.println("--file " + smvFileName);
		smv.println("-- options: flatten? " + AsmetaSMVOptions.FLATTEN);
		if (AsmetaSMVOptions.isUseNuXmvTime() )
			smv.println("@TIME_DOMAIN continuous");
		smv.println("MODULE main");
		smv.println("\tVAR");
		for (String var : varsDecl.keySet()) {
			// only variables that are actually used are defined in the NuSMV model
			if (env.usedLoc.contains(var)) { 
				//Silvia 10/05/2021 -> automatically set clock type
				if (AsmetaSMVOptions.isUseNuXmvTime()  && var.equalsIgnoreCase("TimeLibrarySimple_mCurrTimeSecs"))
					smv.print("\t\t" + var + ": " + "clock" + "; --");
				else
					smv.print("\t\t" + var + ": " + varsDecl.get(var) + "; --");
				if (contrLocations.contains(var)) {
					smv.println("controlled");
				} else if (monLocations.contains(var)) {
					smv.println("monitored");
				} else if (sharedLocations.contains(var)) {
					smv.println("shared");
				}
			}
		}

		if (chooseVarsDecl.size() > 0) {
			smv.println("\t\t--declaration of choose variables");
			for (String var : chooseVarsDecl.keySet()) {
				smv.println("\t\t" + var + ": " + chooseVarsDecl.get(var) + ";");
			}
		}
		Set<String> updatesConditions;
		Map<String, String> variableUpdates;
		String variableDomain, domName;
		if (env.usedDerLoc.size() > 0) {
			smv.println("\tDEFINE");
			for (String var : derived) {
				// if the occurrence of a derived location can be substituted with its
				// definition body,
				// we do not define the derived function and we substitute each occurrence of
				// the location
				// with its body
				if (AsmetaSMVOptions.simplify) {
					if (AsmetaSMVOptions.simplifyDerived) {
						if (env.inLineFunctions.containsKey(var)) {
							continue;
						}
					}
				}

				if (!env.usedDerLoc.contains(var)) {
					continue;
				}
				domName = locationDomain.get(var);
				if (domainSmvWithUndef.containsKey(domName)) {
					variableDomain = domainSmvWithUndef.get(domName);
				} else {
					variableDomain = domainSmv.get(domName);
				}
				variableUpdates = derivedMap.get(var);
				if (variableUpdates == null) {
					throw new AsmNotSupportedException("Derived function " + var + " is not defined in the ASM.");
				}
				updatesConditions = variableUpdates.keySet();
				smv.print("\t\t" + var + " :=");
				if (updatesConditions.size() == 1 && updatesConditions.contains(trueString)) {
					smv.println(" " + variableUpdates.get(updatesConditions.iterator().next()) + ";");
				} else {
					smv.println();
					smv.println("\t\t\tcase");
					for (String cond : updatesConditions) {
						smv.print("\t\t\t\t");
						if (!cond.equals(trueString)) {
							smv.print(cond);
							if (needCheckOnDomain(domName)) {
								smv.print(" & " + variableUpdates.get(cond) + " in " + variableDomain);
							}
						}
						// da copertura con emma, qui non entra mai
						else {
							if (needCheckOnDomain(domName)) {
								smv.print(variableUpdates.get(cond) + " in " + variableDomain);
							} else {
								smv.println(trueString);
							}
						}
						smv.println(": " + variableUpdates.get(cond) + ";");
					}
					if (domName.equals("Boolean")) {
						smv.println("\t\t\t\t" + trueString + ": " + falseString + ";");
					} else {
						String undefValue = this.getUndefValue().get(domName);
						assert undefValue != null : domName + " does not provide a representation for the undef value.";
						smv.println("\t\t\t\t" + trueString + ": " + undefValue + ";");
					}
					smv.println("\t\t\tesac;");
				}
			}
		}
		if (getConstants().size() > 0) {
			smv.print("\tCONSTANTS ");
			int i = 0;
			for (; i < getConstants().size() - 1; i++) {
				smv.print(getConstants().get(i) + ", ");
			}
			smv.println(getConstants().get(i) + ";");
		}

		// if there is at least an initialization or an update, we must
		// add the ASSIGN section
		if ((initMap != null && initMap.size() > 0) || updateMap.getSize() > 0) {
			smv.println("\tASSIGN");
			if (AsmetaSMVOptions.isUseNuXmvTime()) //Silvia 10/05/2021: init clock to 0 if usenuxmv with time
				smv.println("\t\tinit(" + "TimeLibrarySimple_mCurrTimeSecs" + ") := " + "0" + ";");
			if (initMap != null) {
				for (String var : initMap.keySet()) {
					if (env.usedLoc.contains(var)) {
						smv.println("\t\tinit(" + var + ") := " + initMap.get(var) + ";");
					}
				}
			}
			for (String var : updateMap.keySet()) {
				// if a variable is never used, we can avoid declaring it
				if (!env.usedLoc.contains(var)) {
					continue;
				}
				domName = locationDomain.get(var);
				// if a domain has a representation for the undef value in NuSMV,
				// we include the undef in the domain definition
				// The only domain that does not provide a representation for the undef
				// value is the Boolean domain
				if (getUndefValue().containsKey(domName)) {
					variableDomain = domainSmvWithUndef.get(domName);
				} else {
					variableDomain = domainSmv.get(domName);
				}
				variableUpdates = updateMap.get(var);// updates of the current variable
				updatesConditions = variableUpdates.keySet();// conditions guarding the updates of the current variable

				// PA: 2013/08/06 EXPERIMENTAL!!!
				// it merges (as disjunction) the conditions that bring to the same
				// value
				// For example,
				// case
				// a: 2;
				// b: 2;
				// c: 3;
				// esac;
				// becomes
				// case
				// a || b: 2;
				// c: 3;
				// esac;
				Map<String, String> valueToCondsOR = new HashMap<String, String>();
				for (String cond : updatesConditions) {
					String value = variableUpdates.get(cond);
					if (!valueToCondsOR.containsKey(value)) {
						valueToCondsOR.put(value, cond);
					} else {
						valueToCondsOR.put(value, Util.or(valueToCondsOR.get(value), cond));
					}
				}
				Map<String, String> newMap = new HashMap<String, String>();
				for (String value : valueToCondsOR.keySet()) {
					newMap.put(valueToCondsOR.get(value), value);
					// System.out.println(valueToCondsOR.get(value)+"\t"+value);
				}
				variableUpdates = newMap;
				updatesConditions = newMap.keySet();
				// PA: 2013/08/06 EXPERIMENTAL END!!!

				if (sharedLocations.contains(var) && updatesConditions.size() == 0) {// da copertura con emma: partially
																						// covered
					continue;// da copertura con emma: non entra mai
				}
				smv.print("\t\tnext(" + var + ") :=");
				// if the update map for the variable is empty,
				// the variable keeps its value (according to the ASM semantics)
				if (updatesConditions.size() == 0) {
					smv.println(" " + var + ";");
				}
				// if the update map for the variable has only the condition TRUE
				// and the variable is not integer (and, therefore, it does not require a
				// check on the updating term), we can directly update the variable
				else if (!needCheckOnDomain(domName) && updatesConditions.size() == 1
						&& updatesConditions.contains(trueString)) {
					smv.println(" " + variableUpdates.get(updatesConditions.iterator().next()) + ";");
				}
				// otherwise there is a list of conditions in the update map
				// or the variable is integer and, therefore, requires a check on the updating
				// term
				else {
					smv.println();
					smv.println("\t\t\tcase");
					for (String cond : updatesConditions) {
						smv.print("\t\t\t\t");
						if (!cond.equals(trueString)) {
							smv.print(setPars(cond));
							if (needCheckOnDomain(domName)) {
								smv.print(" & " + variableUpdates.get(cond) + " in " + variableDomain);
							}
						} else {
							if (needCheckOnDomain(domName)) {
								smv.print(variableUpdates.get(cond) + " in " + variableDomain);
							} else {
								smv.print(trueString);
							}
						}
						smv.println(": " + variableUpdates.get(cond) + ";");
					}
					smv.print("\t\t\t\t" + trueString + ": ");
					if (sharedLocations.contains(var)) {
						smv.print(varsDecl.get(var).equals("boolean") ? "{TRUE, FALSE}" : varsDecl.get(var));
						smv.println(";");
					} else {
						smv.println(var + ";");
					}
					smv.println("\t\t\tesac;");
				}
			}
		}

		// INVAR section: it contains the definitions of the variables used
		// to model the choose rules
		for (String choose : invars.keySet()) {
			if (!invars.get(choose).equals(trueString)) {
				smv.println("\tINVAR\t" + invars.get(choose) + ";");
			}
		}

		// TODO: fairness on choose
		if (chooseVarsDecl.size() > 0) {
			// smv.println("\t\t--fairness for choose rules");
			for (String chooseVar : chooseVarsDecl.keySet()) {
				// System.out.println(chooseVarsDecl.get(chooseVar));
			}
		}

		printSmvPropertiesAndConstraints(smv);
	}

	protected void printSmvPropertiesAndConstraints(PrintWriter smv) {
		if (invarConstraintsList.size() > 0) {
			smv.println("--INVAR constraints");
			for (String invarConstr : invarConstraintsList) {
				smv.println("INVAR " + invarConstr + ";");
			}
		}
		if (justiceConstraintsList.size() > 0) {
			smv.println("--JUSTICE constraints");
			for (String justiceConstr : justiceConstraintsList) {
				smv.println("JUSTICE " + justiceConstr + ";");
			}
		}
		if (compassionConstraintsList.size() > 0) {
			smv.println("--COMPASSION constraints");
			for (String[] compassionConstr : compassionConstraintsList) {
				smv.println("COMPASSION (" + compassionConstr[0] + ", " + compassionConstr[1] + ");");
			}
		}
		allProp = new ArrayList<String>();
		String name;
		Iterator<String> names;
		if (ltlList.size() > 0) {
			names = ltlListNames.iterator();
			smv.println("--LTL properties");
			int ltlCounter = 0;
			for (String property : ltlList) {
				smv.print("LTLSPEC");
				name = names.next();
				if (!name.equals("")) {
					smv.print(" NAME " + name + (ltlCounter++) + " :=");
				} else {
					smv.print(" NAME ltl" + (ltlCounter++) + " :=");
				}
				smv.println(" " + property + ";");
				allProp.add(property);
			}
		}
		if (ctlList.size() > 0) {
			// System.out.println(ctlListNames);
			names = ctlListNames.iterator();
			smv.println("--CTL properties");
			int ctlCounter = 0;
			for (String property : ctlList) {
				smv.print("CTLSPEC");
				// if(ctlListNames.size() > 0) {
				name = names.next();
				if (!name.equals("")) {
					smv.print(" NAME " + name + (ctlCounter++) + " :=");
				} else {
					smv.print(" NAME ctl" + (ctlCounter++) + " :=");
				}
				// }
				smv.println(" " + property + ";");
				allProp.add(property);
			}
		}
		if (invariantInAgCTL.size() > 0) {
			smv.println("--AsmetaL invariants");
			for (String property : invariantInAgCTL) {
				smv.println("CTLSPEC\t" + property + ";");
				allProp.add(property);
			}
		}
	}

	/**
	 * It visits an ASM module.
	 * 
	 * @param asm the asm
	 * 
	 * @throws Exception the exception
	 */
	void visit(Asm asm) throws Exception {
		if (AsmetaSMVOptions.FLATTEN) {
			asm = new RemoveArgumentsFlattener(asm).flattenASM();
			FlattenerSetting.simplify = true;
			String name = asm.getName();
			asm = AsmetaMultipleFlattener.flatten(asm, ALL_SMV_FLATTENERS);
			asm.setName(name);
			// System.out.println(AsmetaMultipleFlattener.printASM(asm));

			/*
			 * File tempFile = File.createTempFile("tmp", ".asm"); String printASM =
			 * AsmetaMultipleFlattener.printASM(asm); printASM = printASM.replaceFirst(name,
			 * tempFile.toPath().getFileName().toString().replace(".asm", ""));
			 * System.out.println(printASM);
			 * Files.write(Paths.get(tempFile.getAbsolutePath()),
			 * printASM.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
			 * StandardOpenOption.TRUNCATE_EXISTING); asm =
			 * ASMParser.setUpReadAsm(tempFile).getMain(); asm.setName(name);
			 */
		}
		ImportFlattener importFlattener = new ImportFlattener(asm, getDir());
		importFlattener.visit();
		domains = importFlattener.getDomains();
		functions = importFlattener.getFunctions();
		setDomainDefinition(importFlattener.getDomainDefinitions());
		setDomainInitialization(importFlattener.getDomainInitializations());
		Initialization defaultInit = asm.getDefaultInitialState();
		if (defaultInit != null) {
			getDomainInitialization().addAll(defaultInit.getDomainInitialization());
		}
		visitDomains();
		visitFunctions();
		visitDerivedFunctions();
		visitDefault(defaultInit);
		// AsmetaMA
		if (AsmetaSMVOptions.doAsmetaMA) {
			// Before visiting the ASM starting from the main rule,
			// we initialize the counter of the visited macro call rules.
			for (RuleDeclaration r : asm.getBodySection().getRuleDeclaration()) {
				if (r instanceof MacroDeclaration) {
					macroRuleCalled.put((MacroDeclaration) r, 0);
				}
			}
		}
		if (asm.getMainrule() != null) {
			env.inMainRule = true;
			// at the beginning, there are no conditions on updates
			// each update contains at least the guard "TRUE"
			getConditions().push(trueString);
			Rule mainRuleBody = asm.getMainrule().getRuleBody();
			getRv().visit(mainRuleBody);// visit starting from the main rule
			getConditions().pop();// we remove the condition "TRUE" added before visiting the main rule
			env.inMainRule = false;
		}
		visitProperties(asm.getBodySection().getProperty(), asm.getBodySection().getFairnessConstraint(),
				asm.getBodySection().getInvariantConstraint());
	}

	/**
	 * It visits all the temporal properties, the fairness constraints, and the
	 * invariant constraints.
	 * 
	 * @param properties           temporal properties
	 * @param fairnessConstraints  fairness constraints
	 * @param invariantConstraints invariant constraints
	 */
	protected void visitProperties(List<Property> properties, List<FairnessConstraint> fairnessConstraints,
			List<InvarConstraint> invariantConstraints) {
		List<Invariant> invariants = new ArrayList<Invariant>();
		List<CtlSpec> ctlSpecs = new ArrayList<CtlSpec>();
		List<LtlSpec> ltlSpecs = new ArrayList<LtlSpec>();
		for (Property property : properties) {
			if (property instanceof Invariant) {
				invariants.add((Invariant) property);
			} else if (property instanceof CtlSpec) {
				ctlSpecs.add((CtlSpec) property);
			} else if (property instanceof LtlSpec) {
				ltlSpecs.add((LtlSpec) property);
			}
		}
		visitInvariants(invariants);
		ctlList = new ArrayList<String>();
		ctlListNames = new ArrayList<String>();
		visitTemporalSpecs(ctlSpecs, ctlList, ctlListNames);
		ltlList = new ArrayList<String>();
		ltlListNames = new ArrayList<String>();
		visitTemporalSpecs(ltlSpecs, ltlList, ltlListNames);

		List<JusticeConstraint> justiceConstraints = new ArrayList<JusticeConstraint>();
		List<CompassionConstraint> compassionConstraints = new ArrayList<CompassionConstraint>();
		for (FairnessConstraint fairnessConstraint : fairnessConstraints) {
			if (fairnessConstraint instanceof JusticeConstraint) {
				justiceConstraints.add((JusticeConstraint) fairnessConstraint);
			} else if (fairnessConstraint instanceof CompassionConstraint) {
				compassionConstraints.add((CompassionConstraint) fairnessConstraint);
			}
		}
		visitJusticeConstraints(justiceConstraints);
		visitCompassionConstraints(compassionConstraints);
		visitInvarConstraints(invariantConstraints);
	}

	// PA 2013/08/14
	private void visitInLineFunctions() throws Exception {
		env.inDerivedVisitor = true;
		String locName;
		Map<String, String> map;
		env.inLineFunctions = new HashMap<String, String>();
		for (Location location : locationName.keySet()) {
			locName = locationName.get(location);
			if (derived.contains(locName)) {
				if (!DoesTermContainConds.INSTANCE.visit(location)) {
					// TODO bisogna rimuovere il try
					// ora alcune funzioni possono contenere altre funzioni
					try {
						dv = new DerivedVisitor(env, getRv(), this.getUndefValue());
						map = dv.visit(location);
						if (map.size() == 1) {
							Entry<String, String> entrySet = map.entrySet().iterator().next();
							assert entrySet.getKey().equals(Util.trueString)
									: entrySet.getKey() + "\t" + entrySet.getValue();
							env.inLineFunctions.put(locName, entrySet.getValue());
						}
					} catch (Exception e) {

					}
				}
			}
		}
		env.inDerivedVisitor = false;
	}

	/**
	 * It visits derived functions.
	 * 
	 * @throws Exception the exception
	 */
	protected void visitDerivedFunctions() throws Exception {
		if (AsmetaSMVOptions.simplify) {
			visitInLineFunctions();
		}

		env.inDerivedVisitor = true;
		String locName;
		Map<String, String> map;
		derivedMap = new TreeMap<String, Map<String, String>>();
		usedStatDerInDer = new HashMap<String, Set<String>>();
		usedContrMonInDer = new HashMap<String, Set<String>>();
		for (Location location : locationName.keySet()) {
			locName = locationName.get(location);
			if (AsmetaSMVOptions.simplify) {
				if (AsmetaSMVOptions.simplifyDerived) {
					if (env.inLineFunctions.containsKey(locName)) {
						continue;
					}
				}
			}
			dv = new DerivedVisitor(env, getRv(), this.getUndefValue());
			if (derived.contains(locName)) {
				map = dv.visit(location);
				if (map.size() > 0) {
					usedStatDerInDer.put(locName, dv.usedStatDer);
					usedContrMonInDer.put(locName, dv.usedContrMon);
					derivedMap.put(locName, new TreeMap<String, String>(map));
				}
			}
		}
		env.inDerivedVisitor = false;
	}

	/**
	 * It visits the domains.
	 * 
	 * @throws AsmNotSupportedException the asm not supported exception
	 */
	protected void visitDomains() throws AsmNotSupportedException {
		List<Value[]> values = new ArrayList<Value[]>();
		SortedSet<String> set = new TreeSet<String>();
		SortedSet<String> setWithUndef;
		Iterator<String> iterator;
		String domName, str, funcName, undefValueStr;
		int first, last, number;
		Value[] value = new BooleanValue[1];
		domainSmv = new HashMap<String, String>();
		domainSmvWithUndef = new HashMap<String, String>();
		setUndefValue(new HashMap<String, String>());
		domainSet = new HashMap<String, SortedSet<String>>();
		domainValues = new HashMap<String, List<Value[]>>();
		setAgentsDomains(new ArrayList<String>());
		// agents = new ArrayList<String>();
		// abstractDomains = new ArrayList<String>();
		setEnumDomain(new ArrayList<String>());

		// Boolean domain
		set.add("FALSE");
		set.add("TRUE");
		value[0] = BooleanValue.TRUE;
		values.add(value.clone());
		value[0] = BooleanValue.FALSE;
		values.add(value.clone());
		domainSmv.put("Boolean", "boolean");// associa al nome AsmetaL il nome NuSMV
		domainSet.put("Boolean", set);// associa al dominio AsmetaL un insieme con tutti i valori NuSMV
		domainValues.put("Boolean", values);

		// Silvia: 03/05/2021: allow integer and real domains translation if nuXmv
		if (AsmetaSMVOptions.isUseNuXmvTime() || AsmetaSMVOptions.isUseNuXmv() ) {
			domainSmv.put("Real", "real");
			domainSmv.put("Integer", "integer");
		}

		set = new TreeSet<String>();
		values = new ArrayList<Value[]>();
		for (Function func : functions) {
			if (getDomainName(func.getCodomain()).equals("Agent")) {
				if (Defs.isAgentConst(func)) {
					funcName = getFunctionName(func);
					set.add(funcName.toUpperCase());
					value = new EnumValue[1];
					value[0] = new EnumValue(funcName.toUpperCase());
					values.add(value);
					// agents.add(funcName);
					getConstants().add(funcName.toUpperCase());
				} else {
					// throw new AsmNotSupportedException("Gli agenti possono solo essere
					// variabili.");
				}
			}
		}
		if (values.size() > 0) {
			domainSet.put("Agent", set);
			domainValues.put("Agent", values);
			getAgentsDomains().add("Agent");
			domainSmv.put("Agent", Util.asSet(set));
		}

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
				checkTypeDomain(concreteDomain);
				domName = getDomainName(concreteDomain);
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
				domainSet.put(domName, set);
				String result;
				// if distance (plus 1) between the minimum value and the maximum value is
				// as the size of the set, it means that all the values between "first"
				// and "last" are present and, therefore, we can define the domain
				// with the interval notation
				if (last - first + 1 == set.size()) {
					result = first + ".." + last;
				}
				// otherwise we must define the domain listing all its values
				else {
					result = Util.asSet(set);
				}
				domainSmv.put(domName, result);
				domainValues.put(domName, values);

				// undef representation
				undefValueStr = "-2147483647";
				getUndefValue().put(domName, undefValueStr);
				setWithUndef = new TreeSet<String>(set);
				setWithUndef.add(undefValueStr);
				domainSmvWithUndef.put(domName, Util.asSet(setWithUndef));
			}
		}
		// tutti i domini dichiarati nel modello ASM
		for (Domain domain : domains) {
			checkDomain(domain);// it checks whether the domain is supported
			domName = getDomainName(domain);
			if (Defs.isEnumDomain(domain)) {
				getEnumDomain().add(domName);// la lista contiene i nomi dei domini enumerativi
				set = (SortedSet<String>) visit(domain);
				domainSet.put(domName, set);
				domainSmv.put(domName, Util.asSet(set));
				// undef representation
				undefValueStr = domName.toUpperCase() + "_UNDEF";
				getUndefValue().put(domName, undefValueStr);
				setWithUndef = new TreeSet<String>(set);
				setWithUndef.add(undefValueStr);
				domainSmvWithUndef.put(domName, Util.asSet(setWithUndef));

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

				// We need to add enumerative values to the CONSTANTS section, because
				// in the ASM model the enumerative values could be used
				// independently (without being in a function codomain);
				// for example: AA != BB
				getConstants().add(undefValueStr);
				getConstants().addAll(set);
			} else if (isAgentDomain(domain)) {
				set = new TreeSet<String>();
				values = new ArrayList<Value[]>();
				for (Function func : functions) {
					if (getDomainName(func.getCodomain()).equals(domName)) {
						if (func.getArity() == 0) {
							funcName = getFunctionName(func);
							set.add(funcName.toUpperCase());
							value = new EnumValue[1];
							value[0] = new EnumValue(funcName.toUpperCase());
							values.add(value);
							// agents.add(funcName);//not needed TODO: remove in future
							getConstants().add(funcName.toUpperCase());
						} else {
							// throw new AsmNotSupportedException("Gli agenti possono solo essere
							// variabili.");
						}
					}
				}
				getAgentsDomains().add(domName);
				domainSet.put(domName, set);
				domainValues.put(domName, values);
				domainSmv.put(domName, Util.asSet(set));
				// undef representation
				undefValueStr = domName.toUpperCase() + "_UNDEF";
				getUndefValue().put(domName, undefValueStr);
				setWithUndef = new TreeSet<String>(set);
				setWithUndef.add(undefValueStr);
				domainSmvWithUndef.put(domName, Util.asSet(setWithUndef));
				domainValues.put(domName, values);

				// We need to add enumerative values to the CONSTANTS section, because
				// in the ASM model the enumerative values could be used
				// independently (without being in a function codomain);
				// for example: AA != BB
				getConstants().add(undefValueStr);
				getConstants().addAll(set);
			} else if (Defs.isAbstractDomain(domain)) {
				set = new TreeSet<String>();
				values = new ArrayList<Value[]>();
				for (Function func : functions) {
					if (getDomainName(func.getCodomain()).equals(domName)) {
						if (func.getArity() == 0 && Defs.isAbstractConst(func)) {
							funcName = getFunctionName(func);
							set.add(funcName.toUpperCase());
							value = new EnumValue[1];
							value[0] = new EnumValue(funcName.toUpperCase());
							values.add(value);
							getConstants().add(funcName.toUpperCase());
						} else {
							// throw new AsmNotSupportedException("Gli agenti possono solo essere
							// variabili.");
						}
					}
				}
				// note that also empty domains are added
				// abstractDomains.add(domName);
				domainSet.put(domName, set);
				domainValues.put(domName, values);

				// undef representation
				undefValueStr = domName.toUpperCase() + "_UNDEF";
				getUndefValue().put(domName, undefValueStr);
				setWithUndef = new TreeSet<String>(set);
				setWithUndef.add(undefValueStr);
				domainSmvWithUndef.put(domName, Util.asSet(setWithUndef));
				getConstants().add(undefValueStr);
				domainSmv.put(domName, Util.asSet(set));
			} else if (Defs.isConcreteDomain(domain)) {
				if (domainSet.get(domName) == null) {
					String mustBe;
					if (((ConcreteDomainImpl) domain).getIsDynamic()) {
						mustBe = "initialized.";
					} else {
						mustBe = "defined.";
					}
					throw new AsmNotSupportedException("Domain " + domName + " must be " + mustBe);
				}
			}
		}
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
	 * It visits the invariants. Invariants become properties in the form AG().
	 * 
	 * @param invariants the ASM invariants
	 */

	private void visitInvariants(List<Invariant> invariants) {
		String property;
		ctlList = new ArrayList<String>();
		ltlList = new ArrayList<String>();
		invariantInAgCTL = new ArrayList<String>();
		if (invariants != null) {
			for (Invariant invariant : invariants) {
				property = tp.visit(invariant.getBody());
				// AsmetaL invariants "inv" become CTL properties "AG(inv)"
				invariantInAgCTL.add("AG(" + property + ")");
			}
		}
	}

	private void visitTemporalSpecs(List<? extends TemporalProperty> specs, ArrayList<String> propertyList,
			ArrayList<String> names) {
		String name;
		if (specs != null) {
			for (TemporalProperty spec : specs) {
				propertyList.add(tp.visit(spec.getBody()));
				name = spec.getName();
				names.add(name != null ? name : "");
			}
		}
	}

	private void visitJusticeConstraints(List<JusticeConstraint> justiceConstraints) {
		justiceConstraintsList = new ArrayList<String>();
		if (justiceConstraints != null) {
			for (JusticeConstraint justiceConstraint : justiceConstraints) {
				justiceConstraintsList.add(tp.visit(justiceConstraint.getBody()));
			}
		}
	}

	private void visitCompassionConstraints(List<CompassionConstraint> compassionConstraints) {
		compassionConstraintsList = new ArrayList<String[]>();
		if (compassionConstraints != null) {
			for (CompassionConstraint compassionConstraint : compassionConstraints) {
				compassionConstraintsList.add(
						new String[] { tp.visit(compassionConstraint.getP()), tp.visit(compassionConstraint.getQ()) });
			}
		}
	}

	private void visitInvarConstraints(List<InvarConstraint> invarConstraints) {
		invarConstraintsList = new ArrayList<String>();
		if (invarConstraints != null) {
			for (InvarConstraint invarConstraint : invarConstraints) {
				invarConstraintsList.add(tp.visit(invarConstraint.getBody()));
			}
		}
	}

	/**
	 * It visits the functions of the signature.
	 * 
	 * @throws AsmNotSupportedException
	 */
	protected void visitFunctions() throws AsmNotSupportedException {
		String locName, codomainDefinition, codomainName;
		Domain codomain;
		locationName = new HashMap<Location, String>();
		monLocations = new TreeSet<String>();
		contrLocations = new TreeSet<String>();
		sharedLocations = new TreeSet<String>();
		contrFuncLocations = new TreeMap<String, Set<String>>();
		sharedFuncLocations = new TreeMap<String, Set<String>>();
		monFuncLocations = new TreeMap<String, Set<String>>();
		derFuncLocations = new TreeMap<String, Set<String>>();
		statFuncLocations = new TreeMap<String, Set<String>>();
		locationDomain = new HashMap<String, String>();
		functionDomain = new HashMap<String, String>();
		varsDecl = new TreeMap<String, String>();
		updateMap = new UpdateMap();
		nusmvNameToLocation = new HashMap<String, String>();
		agentDomain = new HashMap<String, String>();
		derived = new TreeSet<String>();
		locationNameToNusmvVariableName = new HashMap<Location, String>();

		
		
		SortedSet<String> locationSet = null;
		for (Function func : functions) {
			codomain = func.getCodomain();
			codomainName = getDomainName(codomain);
			locationSet = new TreeSet<String>();
			String functionName = getFunctionName(func);
			if (getAgentsDomains().contains(codomainName)) {
				functionName = functionName.toUpperCase();
			}
			if (Defs.isControlled(func) || Defs.isOut(func)) {
				contrFuncLocations.put(functionName, locationSet);
			} else if (Defs.isShared(func)) {
				sharedFuncLocations.put(functionName, locationSet);
			} else if (Defs.isMonitored(func)) {
				monFuncLocations.put(functionName, locationSet);
			} else if (Defs.isDerived(func)) {
				derFuncLocations.put(functionName, locationSet);
			} else if (Defs.isStatic(func)) {
				statFuncLocations.put(functionName, locationSet);
			}

			// some domains permit to model the undef value
			if ((Defs.isEnumDomain(codomain) || Defs.isConcreteDomain(codomain) || Defs.isAbstractDomain(codomain))
					&& (Defs.isControlled(func) || Defs.isOut(func))) {
				codomainDefinition = domainSmvWithUndef.get(codomainName);
			} else {
				codomainDefinition = domainSmv.get(codomainName);
			}
			if (codomainDefinition == null) {
				throw new AsmNotSupportedException("Domain " + codomainName + " not supported.");
			}
			functionDomain.put(functionName, codomainName);
			for (Location location : getLocations(func)) {
				locName = visit(location);
				if (getAgentsDomains().contains(codomainName) && func.getArity() == 0) {
					locName = locName.toUpperCase();
					// System.err.println(locName);
				}
				Util.checkLocationName(locName);
				locationSet.add(locName);
				locationDomain.put(locName, codomainName);// associa ad un nome di locazione il suo dominio
				locationName.put(location, locName);// associa ad una locazione il suo nome
				nusmvNameToLocation.put(locName, location.toString());
				locationNameToNusmvVariableName.put(location, locName);
				if (getAgentsDomains().contains(codomainName)) {
					agentDomain.put(locName.toUpperCase(), codomainName);
				}
				if (!Defs.isDerived(func) && !Defs.isStatic(func)) {
					varsDecl.put(locName, codomainDefinition);
					if (Defs.isControlled(func) || Defs.isOut(func)) {
						updateMap.put(locName);
						contrLocations.add(locName);
					} else if (Defs.isMonitored(func)) {
						monLocations.add(locName);
					} else if (Defs.isShared(func)) {
						sharedLocations.add(locName);
						updateMap.put(locName);
					} else {
						throw new AsmNotSupportedException(
								"Type " + func.getClass().getSimpleName() + " not supported.");
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
	 * Visita l'inizializzazione di default.
	 * 
	 * @param init the init
	 */
	protected void visitDefault(Initialization init) {
		String undef;
		initMap = new TreeMap<String, String>();
		if (init != null) {
			visitFuncInits(init.getFunctionInitialization());
			List<AgentInitialization> agentInit = init.getAgentInitialization();
			if (agentInit != null) {
				visitAgents(agentInit);
			}
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
	 * Visita le inizializzazioni degli agenti.
	 * 
	 * @param agentInit l'inizializzazione degli agenti
	 */
	private void visitAgents(List<AgentInitialization> agentInit) {
		for (AgentInitialization ag : agentInit) {
			env.agentRule.put(getDomainName(ag.getDomain()), ag.getProgram());
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
	private void visitInit(FunctionInitialization init) {
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
			}

			if (termStr != null) {
				// env.usedLocation.add(locStr);
				initMap.put(locStr, termStr);

				// AsmetaMA: segnala che la locazione locStr viene inizializzata
				if (AsmetaSMVOptions.doAsmetaMA) {
					controlledLocationInitialized.add(locStr);
				}
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
		/*
		 * if(notBelongsToMainAsm(f)) s.append(getAsmName(f) + "_");
		 * s.append(f.getName());
		 */
		s.append(getFunctionName(f));
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
	
	public List<Value[]> asValueList(BooleanDomain domain) {
		return domainValues.get("Boolean");
	}

	public List<Value[]> asValueList(ConcreteDomain domain) throws AsmNotSupportedException {
		return domainValues.get(getDomainName(domain));
	}

	public List<Value[]> asValueList(AgentDomain domain) throws AsmNotSupportedException {
		return domainValues.get("Agent");
	}

	public List<Value[]> asValueList(EnumTd domain) throws AsmNotSupportedException {
		return domainValues.get(getDomainName(domain));
	}

	public List<Value[]> asValueList(AbstractTd domain) throws AsmNotSupportedException {
		// System.out.println(domainValues.get(getDomainName(domain)));
		return domainValues.get(getDomainName(domain));
	}

	/**
	 * Cerca nell'output dell'esecuzione del modello NuSMV i risultati della
	 * verifica delle singole proprieta'. I risultati vengono memorizzati in
	 * nusmvPropertiesresults. L'indice del risultato nella lista
	 * nusmvPropertiesresults e' lo stesso memorizzato nella mappe delle
	 * metaproprieta' verso gli indici. L'assunzione che viene fatta (che non e' mai
	 * stata smentita dalle esecuzioni fino ad ora) e' che l'ordine della proprieta'
	 * nel modello NuSMV e' lo stesso nell'output dell'esecuzione del modello.
	 * 
	 * @param outputRunNuSMV l'output dell'esecuzione del modello NuSMV
	 * 
	 */
	public void getPropertiesResults(String outputRunNuSMV) {
		nuSmvPropsResults = new ArrayList<Boolean>();
		setMapPropResult(new HashMap<String, Boolean>());
		this.propertiesCounterExample = new HashMap<Integer, String>();
		this.propertiesForPrinting = new HashMap<Integer, String>();
		int indexResult = outputRunNuSMV.indexOf("\n") + 1;
		String[] results = outputRunNuSMV.substring(indexResult).split("\\-\\- specification ");
		for (int i = 1; i < results.length; i++) {
			Integer index = new Integer(i - 1);
			String[] lines = results[i].split("\n");
			boolean result;
			String[] strRes = lines[0].split("  is ");
			String prop = strRes[0];
			getPropertiesForPrinting().put(index, prop);
			if (strRes[1].equals("true")) {
				result = true;
			} else {
				assert strRes[1].equals("false") : strRes[1];
				result = false;
				if (AsmetaSMVOptions.isPrintCounterExample()) {
					String counterExample = results[i].substring(results[i].indexOf("-> State:"));
					propertiesCounterExample.put(index, counterExample);
				}
			}
			nuSmvPropsResults.add(result);

			// it is not working anymore with NuSMV 2.6 (the order of properties is not
			// preserved in the results)
			// String key = allProp.get(i - 1);

			String key = getKey(prop);// we need to look for the correct property

			// System.out.println(prop);
			// System.out.println(key + "\n");
			getMapPropResult().put(key, result);
		}
	}

	private String getKey(String prop) {
		String charToRem = "[() ]";
		String simplProp = prop.replaceAll(charToRem, "");
		String key = null;
		for (String k : allProp) {
			String simplKey = k.replaceAll(charToRem, "");
			if (simplKey.equals(simplProp)) {
				if (key == null) {
					key = k;
				} else {
					if (!key.equals(k)) {
						throw new Error("Not able to retrieve a unique result for " + prop
								+ "These keys are conflicting:\n" + key + "\n" + k);
					}
				}
			}
		}
		if (key == null) {
			throw new Error("Not able to retrieve the result.");
		}
		return key;
	}

	/**
	 * It prints the locations that have not been exported in NuSMV since the are
	 * not used in the AsmetaL model.
	 */
	private void printUnusedLocations() {
		for (String location : varsDecl.keySet()) {
			if (!env.usedLoc.contains(location)) {
				out.println("Location " + location + Util.notUsedMess);
			}
		}
		for (String location : derived) {
			if (!env.usedDerLoc.contains(location)) {
				out.println("Derived location " + location + notUsedMess);
			}
		}
	}

	/**
	 * Genera un file di ordinamento delle variabili di NuSMV. Il metodo per ora non
	 * viene mai usato perche' l'ordinamento scelto e' poco efficiente: ora l'ordine
	 * delle variabili e' l'ordine con cui si incontrano le funzioni in AsmetaL.
	 * Bisogna pensare a quale potrebbe essere un buon metodo. Ad esempio si
	 * potrebbero calcolare le dipendenze tra funzioni (l'aggiornamento di una
	 * dipende dal valore di un'altra, etc...). Per ora e' meglio eseguire il
	 * modello NuSMV usando i metodi di ordinamento preimpostati (ad esempio con
	 * l'opzione "-dynamic").
	 * 
	 * @param orderFileName nome del file di ordinamento
	 * @throws AsmNotSupportedException
	 */
	private void printOrderFile(String orderFileName) throws AsmNotSupportedException {
		File orderFile = new File(orderFileName);
		PrintWriter order = null;
		try {
			order = new PrintWriter(orderFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (String str : env.orderVars) {
			order.println(str);
		}
		order.close();
	}

	public Map<String, String> getUndefValue() {
		return undefValue;
	}

	public void setUndefValue(Map<String, String> undefValue) {
		this.undefValue = undefValue;
	}

	public Map<String, Boolean> getMapPropResult() {
		return mapPropResult;
	}

	public void setMapPropResult(Map<String, Boolean> mapPropResult) {
		this.mapPropResult = mapPropResult;
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

	public RuleVisitor getRv() {
		return rv;
	}

	public void setRv(RuleVisitor rv) {
		this.rv = rv;
	}

	public List<String> getAgentsDomains() {
		return agentsDomains;
	}

	public void setAgentsDomains(List<String> agentsDomains) {
		this.agentsDomains = agentsDomains;
	}

	public List<String> getEnumDomain() {
		return enumDomain;
	}

	public void setEnumDomain(List<String> enumDomain) {
		this.enumDomain = enumDomain;
	}

	public List<String> getConstants() {
		return constants;
	}

	public void setConstants(List<String> constants) {
		this.constants = constants;
	}

	public HashMap<Integer, String> getPropertiesCounterExample() {
		return propertiesCounterExample;
	}

	public HashMap<Integer, String> getPropertiesForPrinting() {
		return propertiesForPrinting;
	}

	public List<Boolean> getNuSmvPropsResults() {
		return nuSmvPropsResults;
	}

	// TODO: Check if the following commented code can be removed.
	// The following code is never used and I do not remember its purpose.
	/**
	 * Visit main rule all mons.
	 * 
	 * @param mainRuleBody the main rule body
	 */
	/*
	 * private void visitMainRuleAllMons(Rule mainRuleBody) {
	 * List<Map<List<String>,Map<String,String>>> monsCombinations = new
	 * ArrayList<Map<List<String>, Map<String, String>>>(); combineMons(monsConds(),
	 * 0, new Stack<String>(), new HashMap<String,String>(),monsCombinations);
	 * for(Map<List<String>,Map<String, String>> comb: monsCombinations){
	 * for(Entry<List<String>, Map<String, String>> combEntrySet: comb.entrySet()) {
	 * updateCondition(conditions, combEntrySet.getKey());
	 * env.monValues.putAll(combEntrySet.getValue()); visit(mainRuleBody);
	 * restoreCondition(conditions, combEntrySet.getKey()); } } }
	 */

	/**
	 * Combine mons.
	 * 
	 * @param input  the input
	 * @param index  the index
	 * @param conds  the conds
	 * @param values the values
	 * @param result the result
	 */
	/*
	 * private void combineMons(Map<String,Map<String, String>> input, int index,
	 * Stack<String> conds, Map<String, String> values,
	 * List<Map<List<String>,Map<String, String>>> result) { String mon =
	 * (String)input.keySet().toArray()[index]; Map<String, String> monMap =
	 * input.get(mon); for(Entry<String, String> cond: monMap.entrySet()) {
	 * conds.push(cond.getKey()); values.put(mon, cond.getKey()); if(input.size() ==
	 * index + 1) { Map<List<String>, Map<String, String>> temp = new
	 * HashMap<List<String>, Map<String, String>>(); temp.put((List<String>)
	 * conds.clone(), new HashMap<String,String>(values)); result.add(temp); } else
	 * { combineMons(input, index+1, conds, values,result); } conds.pop(); } }
	 */

	// monitored in mappa condizione-valore
	/*
	 * private Map<String,Map<String, String>> monsConds(){ Map<String,Map<String,
	 * String>> mons = new HashMap<String,Map<String, String>>(); for(String
	 * monitored: monLocations) { mons.put(monitored, monConds(monitored)); } return
	 * mons; }
	 */

	// associa alla condizione il valore assunto dalla monitorata
	/*
	 * private Map<String, String> monConds(String monitored) { Map<String, String>
	 * values = new HashMap<String, String>(); String domName =
	 * locationDomain.get(monitored); if(domName.equals("boolean")) {
	 * values.put(monitored, trueString); values.put("!"+monitored, falseString); }
	 * else { for(String value: domainSet.get(domName)) { values.put(monitored + "="
	 * + value,value); } } return values; }
	 */
}

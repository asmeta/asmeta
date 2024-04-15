package org.asmeta.toyices;

import static org.asmeta.toyices.Utils.getFuncName;
import static org.asmeta.toyices.Utils.isAgentConstant;
import static org.asmeta.toyices.Utils.nextStateSymbol;
import static org.asmeta.toyices.Utils.parseDomainNameOrDef;
import static org.asmeta.toyices.Utils.renameOverloadFunctions;
import static org.asmeta.toyices.Utils.replaceCurrentNextSymbol;
import static org.asmeta.toyices.Utils.splitDomains;
import static org.asmeta.toyices.Utils.currentStateSymbol;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Category;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.Defs;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.value.Value;

import com.sun.jna.Pointer;

import asmeta.definitions.Function;
import asmeta.definitions.Invariant;
import asmeta.definitions.Property;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
import asmeta.structure.FunctionDefinition;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.Initialization;
import asmeta.structure.Signature;
import asmeta.transitionrules.basictransitionrules.Rule;
import yices.YicesLibrary;

public class SMTbasedASMsimulator {
	private static Logger logger = LogManager.getLogger(SMTbasedASMsimulator.class.getName());
	public RuleVisitor rv;
	public Asm asm;
	public YicesModel yicesModel;
	protected YicesLibrary yices;
	protected Pointer ctx;
	public int stateNumber;
	private Map<String, Function> mapFuncNameFunc;
	public ArrayList<String> derivedInYices;
	public String rules;
	public String invariants;
	public ArrayList<String> unchangedFunctions;
	public ArrayList<String> chooseVarsDeclarations;
	//If false, the i-th derived functions are printed before the i-th step.
	//If true, the i-th derived functions are printed at the end of step (i-1): this is useful for runtime monitoring 
	private boolean printDerivedAfter;
	private boolean checkSatisfiability;
	public Signature signature;
	public Initialization defaultInitState;
	public Map<String, Rule> agentRules;
	public Map<String, String> domainWithoutUndef;

	public static void setLogger() {
		ConsoleAppender console = new ConsoleAppender(new PatternLayout("%m\n"));
		Logger.getRootLogger().addAppender(console);
		LogManager.getLogger("org.asmeta").setLevel(Level.OFF);
		LogManager.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		LogManager.getLogger(SMTbasedASMsimulator.class).setLevel(Level.INFO);
	}

	public void shutdownLogger() {
		Logger.getRootLogger();
		Category.shutdown();
		LogManager.getLogger("org.asmeta").setLevel(Level.OFF);
		LogManager.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		LogManager.getLogger(SMTbasedASMsimulator.class).setLevel(Level.OFF);
	}

	public SMTbasedASMsimulator(Asm asm, YicesModel yicesModel) {
		this.asm = asm;
		this.yicesModel = yicesModel;
		yices = YicesModel.yices;
		ctx = yicesModel.ctx;
		stateNumber = 0;
		checkSatisfiability = true;
		//void yices_set_arith_only	(int flag)
	 	//Inform yices that only the arithmetic theory is going to be used.
		//This flag usually improves performance (default = false).
		//yicesModel.yices.yices_set_arith_only(1);
		signature = asm.getHeaderSection().getSignature();
		defaultInitState = asm.getDefaultInitialState();
		renameOverloadFunctions(signature.getFunction(), false);
	}

	public SMTbasedASMsimulator(String asmFile) throws Exception {
		this(ASMParser.setUpReadAsm(new File(asmFile)).getMain(), new YicesModel());
	}

	public SMTbasedASMsimulator(Asm asm) throws Exception {
		this(asm, new YicesModel());
	}

	public SMTbasedASMsimulator(String asmFile, boolean printDerivedAfter, boolean checkSatisfiability) throws Exception {
		this(asmFile);
		this.printDerivedAfter = printDerivedAfter;
		this.checkSatisfiability = checkSatisfiability;
	}

	public boolean visitASM() throws Exception {
		visitDomains(signature.getDomain());
		visitFunctions(signature.getFunction());
		yicesModel.derivedFunctionsDef = asm.getBodySection().getFunctionDefinition();
		getDerivedFunctionsInYices();
		if(defaultInitState != null) {
			visitInitState(defaultInitState);
		}
		getRulesInYices();
		assertDerivedDefinitions();//assert the derived functions in the initial state
		String satisfiability = "sat";
		if(checkSatisfiability) {
			satisfiability = check();
		}
		return satisfiability.equals("sat");
	}

	public void visitDomains(Collection<Domain> domains) {
		domainWithoutUndef = new HashMap<String, String>();
		yicesModel.enumValues = new HashMap<String, String[]>();
		yicesModel.allEnumAndAgentsValues = new HashSet<String>();
		yicesModel.intValues = new HashMap<String, Integer[]>();
		yicesModel.domainIntValues = new HashMap<Domain, Integer[]>();
		yicesModel.intDomainsDeclaration = new HashMap<String, String>();
		yicesModel.domainUndefValue = new HashMap<String, String>();
		//As undef value for integers, we use the integer minimum value.
		//The model, therefore, cannot use that value. 
		yicesModel.domainUndefValue.put("Integer", String.valueOf(Integer.MIN_VALUE));
		
		DomainToYicesType domainToType = new DomainToYicesType(yicesModel, domainWithoutUndef, signature);
		for(Domain domain: domains) {
			String yices_commands = domainToType.visit(domain);
			int result = parseCommand(yices_commands);
			assert result == 1: "Command\n" + yices_commands + "\nexited with error.";
		}
	}

	/**
	 * @param domain
	 */
	public void visitFunctions(Collection<Function> functions) {
		yicesModel.contrFuncsDeclarations = new HashSet<String[]>();
		yicesModel.monDerFuncsDeclarations = new HashSet<String[]>();
		mapFuncNameFunc = new HashMap<String, Function>();
		logger.info(";; functions - state 0");
		ArrayList<Function> consideredFunctions = new ArrayList<Function>();
		for(Function func: functions) {
			if(!isAgentConstant(func)) {
				consideredFunctions.add(func);
			}
		}
		for(Function func: consideredFunctions) {
			Domain codom = func.getCodomain();
			String codomName = parseDomainNameOrDef(codom, yicesModel);
			if(codom instanceof ConcreteDomain) {
				codomName = yicesModel.intDomainsDeclaration.get(codom.getName());
			}
			String funcName = getFuncName(func);
			String funcNameCS = funcName + 0;
			String command = "(define " + funcNameCS + "::";
			mapFuncNameFunc.put(funcName, func);
			String type;
			if(func.getArity() > 0) {
				StringBuilder sb = new StringBuilder("(-> ");
				Domain domain = func.getDomain();
				if(func.getArity() == 1) {
					sb.append(getDomainNameForFunctionDomain(domain));
				}
				else {
					if(Utils.nAryFunctionsAsTuples) {
						sb.append("(tuple ");//using tuples
					}
					List<Domain> doms = splitDomains(domain);
					for(int i = 0; i < doms.size() - 1; i++) {
						sb.append(getDomainNameForFunctionDomain(doms.get(i))).append(" ");
					}
					sb.append(getDomainNameForFunctionDomain(doms.get(doms.size() - 1)));
					if(Utils.nAryFunctionsAsTuples) {
						sb.append(")");//using tuples
					}
				}
				sb.append(" ").append(codomName).append(")");
				type = sb.toString();
			}
			else {
				type = codomName;
			}
			command = command + type + ")";
			parseCommand(command);
			if(Defs.isControlled(func)) {
				yicesModel.contrFuncsDeclarations.add(new String[]{"(define " + funcName + nextStateSymbol, "::" + type + ")"});
			}
			else {
				yicesModel.monDerFuncsDeclarations.add(new String[]{"(define " + funcName + currentStateSymbol, "::" + type + ")"});
			}
		}
	}

	private String getDomainNameForFunctionDomain(Domain domain) {
		if(domainWithoutUndef.containsKey(domain.getName())) {
			return domainWithoutUndef.get(domain.getName());
		}
		else {
			return parseDomainNameOrDef(domain, yicesModel);
		}
	}

	public void visitInitState(Initialization initialization) {
		TermVisitor tv = new TermVisitor(yicesModel);
		List<FunctionInitialization> functionInitialization = initialization.getFunctionInitialization();
		int size = functionInitialization.size();
		Set<String> initializedFunctions = new HashSet<String>();
		if(size > 0) {
			logger.info(";; initial state");
			for(int i = 0; i < size; i++) {
				FunctionInitialization funcInit = functionInitialization.get(i);
				String funcName = getFuncName(funcInit.getInitializedFunction());
				initializedFunctions.add(funcName);//before adding the 0
				funcName = funcName + "0";
				String initStateFunc = tv.visitFunctionDefinition(funcName, funcInit.getVariable(), funcInit.getBody());
				initStateFunc = initStateFunc.replaceAll(currentStateSymbol, "0");
				parseAndAssertCommand(initStateFunc);
			}
		}
		for(Function func: signature.getFunction()) {
			if(Defs.isControlled(func) && !initializedFunctions.contains(func.getName())) {
				String funcName = getFuncName(func) + "0";
				String codomainName = func.getCodomain().getName();
				assert yicesModel.domainUndefValue.containsKey(codomainName): codomainName + " does not have the undef value."; 
				String undefName = yicesModel.domainUndefValue.get(codomainName);
				if(func.getArity() == 0) {
					parseAndAssertCommand(yicesModel.eq(funcName, undefName));
				}
				else {
					parseAndAssertCommand(tv.forallEqConst(funcName, undefName, splitDomains(func.getDomain())));
				}
			}
		}
		agentRules = new HashMap<String, Rule>();
		for(AgentInitialization agentInit: initialization.getAgentInitialization()) {
			String agentName = agentInit.getDomain().getName();
			Rule agentRule = agentInit.getProgram().getCalledMacro().getRuleBody();
			agentRules.put(agentName, agentRule);
		}
	}

	public void assertDerivedDefinitions(String snStr) {
		if(derivedInYices.size() > 0) {
			logger.info(";; derived functions definitions - state " + snStr);
			for(String derived: derivedInYices) {
				String currentDerived = derived.replaceAll(currentStateSymbol, snStr);
				assert !currentDerived.contains(nextStateSymbol);
				parseAndAssertCommand(currentDerived);
			}
		}
	}

	public void assertDerivedDefinitions() {
		assertDerivedDefinitions(String.valueOf(stateNumber));
	}

	public void getDerivedFunctionsInYices() {
		TermVisitor tv = new TermVisitor(yicesModel);
		derivedInYices = new ArrayList<String>();
		for(FunctionDefinition funcDef: yicesModel.derivedFunctionsDef) {
			Function func = funcDef.getDefinedFunction();
			String funcName = getFuncName(func) + currentStateSymbol;
			String funcDefYices = tv.visitFunctionDefinition(funcName, funcDef.getVariable(), funcDef.getBody());
			derivedInYices.add(funcDefYices);
		}
	}

	public boolean assertRules() throws Exception {
		logger.info(";; functions state " + (stateNumber + 1));
		if(!printDerivedAfter) {
			if(stateNumber > 0) {
				for(String[] functionDecl: yicesModel.monDerFuncsDeclarations) {
					assert functionDecl.length == 2;
					String command = functionDecl[0].replaceAll(currentStateSymbol, String.valueOf(stateNumber)) + functionDecl[1];
					parseCommand(command);
				}
				assertDerivedDefinitions();
			}
		}
		logger.info(";; new controlled functions for next state - step " + stateNumber);
		for(String[] functionDecl: yicesModel.contrFuncsDeclarations) {
			assert functionDecl.length == 2;
			String command = functionDecl[0].replaceAll(nextStateSymbol, String.valueOf(stateNumber + 1)) + functionDecl[1];
			parseCommand(command);
		}
		logger.info(";; logic variables - step " + stateNumber);
		for(String cvDecl: chooseVarsDeclarations) {
			String command = cvDecl.replaceAll(currentStateSymbol, String.valueOf(stateNumber));
			parseCommand(command);
		}
		logger.info(";; rules state " + stateNumber);
		String rulesCommand = replaceCurrentNextSymbol(rules, stateNumber);
		parseAndAssertCommand(rulesCommand);

		logger.info(";; unchanged functions - state " + stateNumber);
		for(String unchangedFunction: unchangedFunctions) {
			parseAndAssertCommand(replaceCurrentNextSymbol(unchangedFunction, stateNumber));
		}
		String satisfiability = "sat";
		if(checkSatisfiability) {
			satisfiability = check();;
		}
		stateNumber++;
		if(printDerivedAfter) {
			for(String[] functionDecl: yicesModel.monDerFuncsDeclarations) {
				assert functionDecl.length == 2;
				String command = functionDecl[0].replaceAll(currentStateSymbol, String.valueOf(stateNumber)) + functionDecl[1];
				parseCommand(command);
			}
			assertDerivedDefinitions();
		}
		return satisfiability.equals("sat");
	}

	public Set<String> chooseVars = new HashSet<String>(); 

	public void getRulesInYices() throws Exception {
		assert agentRules != null;
		assert domainWithoutUndef != null;
		rv = new RuleVisitor(yicesModel, agentRules, domainWithoutUndef);
		rules = rv.visit(asm.getMainrule().getRuleBody());
		chooseVarsDeclarations = new ArrayList<String>();
		for(String[] decl: rv.chooseVarDeclarations) {
			chooseVars.add(decl[0]);
			chooseVarsDeclarations.add("(define " + decl[0] + "::" + decl[1] + ")");
		}

		unchangedFunctions = new ArrayList<String>();
		
		if(RuleVisitor.chooseWithVariables) {
			for(String location: rv.updateConds.keySet()) {
				String oldLoc = location.replaceFirst(nextStateSymbol, currentStateSymbol);
				String newLoc = location;
				String eq = yicesModel.eq(oldLoc, newLoc);
				String notConds = yicesModel.not(yicesModel.or(rv.updateConds.get(location)));
				//String command = yicesModel.ifThen(notConds, eq);
				String command = yicesModel.implies(notConds, eq);
				if(!command.equals("true")) {
					//System.err.println(command);
					unchangedFunctions.add(command);
				}
			}
		}
		else {
			for(String location: rv.locationUpdates.keySet()) {
				String oldLoc = location.replaceFirst(nextStateSymbol, currentStateSymbol);
				String newLoc = location;
				String eq = yicesModel.eq(oldLoc, newLoc);
				String notConds = yicesModel.not(yicesModel.or(rv.locationUpdates.get(location)));
				//String command = yicesModel.ifThen(notConds, eq);
				String command = yicesModel.implies(notConds, eq);
				if(!command.equals("true")) {
					//System.err.println(command);
					unchangedFunctions.add(command);
				}
			}
		}
	}

	public void getInvariantsInYices() {
		getInvariantsInYices("inv_");
	}

	public void getInvariantsInYices(String filterName) {
		TermVisitor tv = new TermVisitor(yicesModel);
		Set<String> invs = new HashSet<String>();
		for(Property prop: asm.getBodySection().getProperty()) {
			if(prop instanceof Invariant) {
				Invariant invariant = (Invariant)prop;
				if(invariant.getName().startsWith(filterName)) {
					invs.add(tv.visit(invariant.getBody()));
				}
			}
		}
		if(invs.size() > 0) {
			invariants = yicesModel.and(invs);
		}
	}

	public String check() {
		logger.info("(check)");
		int checkResult = yices.yices_check(ctx);
		String result;
		switch(checkResult) {
			case YicesLibrary.lbool.l_true:
				result = "sat";
				break;
			case YicesLibrary.lbool.l_false:
				result = "unsat";
				break;
			case YicesLibrary.lbool.l_undef:
				result = "unknown";
				break;
			default:
				throw new Error("Code " + checkResult + " not expected.");
		}
		logger.info(";; " + result);
		return result;
	}

	public String getValue(String func, String dom) {
		Pointer model = yices.yices_get_model(ctx);
		Pointer var = yices.yices_get_var_decl_from_name(ctx, func);
		if(yicesModel.enumValues.keySet().contains(dom)) {
			int index = yices.yices_get_scalar_value(model, var);
			String[] domainValues = yicesModel.enumValues.get(dom);
			if(index < domainValues.length) {
				return domainValues[index];
			}
			else {
				return yicesModel.domainUndefValue.get(dom);
			}
		}
		else if(dom.equals("bool")) {
			int value = yices.yices_get_value(model, var);
			switch(value) {
				case YicesLibrary.lbool.l_true:
					return "true";
				case YicesLibrary.lbool.l_false:
					return "false";
				default:
					return "unknown";
			}
		}
		else if(dom.equals("int")) {
			return "TODO interi";
		}
		return "unknown";
	}

	public void assertEq(String loc, String value) {
		String command = yicesModel.eq(loc, value);
		parseAndAssertCommand(command);
	}

	public int parseCommand(String command) {
		logger.info(command);
		return yices.yices_parse_command(ctx, command);
	}

	public void parseAndAssertCommand(String command) {
		//System.err.println(command);
		logger.info("(assert " + command + ")");
		yices.yices_assert(ctx, yices.yices_parse_expression(ctx, command));
	}

	//TODO: formatter for yices
	private String formatCommand(String command) {
		StringBuilder sb = new StringBuilder();
		int numOfTabs = 0;
		for(char c: command.toCharArray()) {
			if(c == '(') {
				numOfTabs++;
				sb.append("\n");
				for(int i = 0; i < numOfTabs; i++) {
					sb.append("\t");
				}
			}
			else if(c == ')') {
				numOfTabs--;
			}
			sb.append(c);
			
		}
		return sb.toString();
	}

	public void parseAndAssertCommand(String comment, String command) {
		logger.info(";; " + comment);
		parseAndAssertCommand(command);
	}

	public void setLocationValue(Location loc, String value) {
		String command = yicesModel.eq(locationName(loc), value);
		parseAndAssertCommand(command);
	}

	public void setLocationValue(Location loc, int state, String value) {
		String command = yicesModel.eq(locationName(loc, state), value);
		parseAndAssertCommand(command);
	}

	public String locationName(Location location) {
		return locationName(location, currentStateSymbol);
	}

	public String locationName(Location location, int state) {
		return locationName(location, String.valueOf(state));
	}

	public String locationName(Location location, String stateId) {
		Function func = location.getSignature();
		String funcName = getFuncName(func);
		funcName = funcName + stateId;
		if(func.getArity() == 0) {
			return funcName;
		}
		else {
			String locationYices = "(" + funcName + " " + parseArgs(location.getElements()) + ")";
			return locationYices;
		}
	}

	public String parseArgs(Value[] args) {
		if(args.length == 1) {
			return args[0].toString();
		}
		else {
			//n-ary functions with n > 1
			StringBuilder sb = new StringBuilder();
			if(Utils.nAryFunctionsAsTuples) {
				//using tuples
				sb.append("(mk-tuple");
				for(Value arg: args) {
					sb.append(" ").append(arg.toString());
				}
				sb.append(")");
			}
			else {
				//using curryfication
				sb.append(args[0].toString());
				for(int i = 1; i < args.length; i++) {
					sb.append(" ").append(args[i].toString());
				}
			}
			return sb.toString();
		}
	}

	public void applyState(List<String[]> state) {
		for(String[] varValue: state) {
			assert varValue.length == 2;
			String command = yicesModel.eq(varValue[0], varValue[1]);
			parseAndAssertCommand(command);
		}
	}

	public static void main(String[] args) throws Exception {
		//String asm = "models/simpleModel.asm";
		//String asm = "models/simpleModel2.asm";
		//String asm = "models/simpleNonDetModel.asm";
		//String asm = "models/functionOverload.asm";
		//String asm = "models/forall.asm";
		//String asm = "models/forall2.asm";
		//String asm = "models/forallForall.asm";
		//String asm = "models/choose5.asm";
		//String asm = "models/chooseOtherwise.asm";
		//String asm = "models/funcs.asm";
		//String asm = "models/caseRule.asm";
		//String asm = "models/caseRule2.asm";
		//String asm = "models/caseRule3.asm";
		//String asm = "models/choose7.asm";
		//String asm = "models/choose8.asm";
		//String asm = "models/macroCallRule.asm";
		//String asm = "models/letRule.asm";
		//String asm = "models/mon.asm";
		//String asm = "models/caseTerm.asm";
		//String asm = "models/caseTerm2.asm";
		//String asm = "models/caseTerm3.asm";
		//String asm = "models/caseTerm4.asm";
		//String asm = "models/derived.asm";
		//String asm = "models/Tictactoe.asm";
		//String asm = "models/ticTacToe_forMonitoring.asm";
		//String asm = "models/ticTacToe_forMonitoringSimple.asm";
		//String asm = "models/monArg.asm";
		//String asm = "models/monArg2.asm";
		//String asm = "models/board.asm";
		//String asm = "models/RushHour.asm";
		//String asm = "models/RushHour2.asm";
		//String asm = "models/RushHourSimple.asm";
		//String asm = "models/RushHourSimple2.asm";
		//String asm = "models/Tank.asm";
		//String asm = "models/nAryFunc.asm";
		//String asm = "models/agents.asm";
		//String asm = "models/initUndef.asm";
		//String asm = "models/nAryFuncUndef.asm";
		//String asm = "models/initUndef.asm";
		//String asm = "../../../../asm_examples/examples/stereoacuity/certifierGround.asm";
		//String asm = "../../../../asm_examples/examples/stereoacuity/certifierRaff1.asm";
		//String asm = "../../../../asm_examples/examples/stereoacuity/certifierRaff2.asm";
		//String asm = "../../../../asm_examples/examples/stereoacuity/certifierRaff3.asm";
		//String asm = "../../../../asm_examples/examples/stereoacuity/certifierRaff4.asm";
		//String asm = "../../../../asm_examples/examples/stereoacuity/certifierRaff5.asm";
		//String asm = "../../../../asm_examples/examples/examples/landingGearSystem/LGS_GM.asm";
		//String asm = "../../../../asm_examples/examples/examples/landingGearSystem/LGS_EV.asm";
		//String asm = "../../../../asm_examples/examples/examples/landingGearSystem/LGS_3L.asm";
		//String asm = "../../../../../asm_cloud/Project_ClientMiddlewareCloud_Asmeta/modelChecking/ClientDisplayOutputNoMessageArrived.asm";
		//String asm = "../../../../../asm_cloud/Project_ClientMiddlewareCloud_Asmeta/modelChecking/ClientDisplayOutput.asm";
		//String asm = "../../../../../asm_cloud/Project_ClientMiddlewareCloud_Asmeta/modelChecking/ClientDisplayOutputWithCookie.asm";
		//String asm = "../../../../../asm_cloud/Project_ClientMiddlewareCloud_Asmeta/modelChecking/ClientDisplayOutputWithCookieTags.asm";
		//String asm = "../../../../../asm_cloud/Project_ClientMiddlewareCloud_Asmeta/modelChecking/ServerReceiveRequest.asm";
		
		//String asm = "models/mape/simpleASMmape.asm";
		//String asm = "models/mape/simpleASMmape1.asm";
		//String asm = "models/mape/simpleASMmape2.asm";
		String asm = "models/Tank.asm";
		SMTbasedASMsimulator.setLogger();
		SMTbasedASMsimulator visitor = new SMTbasedASMsimulator(asm);
		visitor.visitASM();
		visitor.assertRules();
		//visitor.assertRules();
		//visitor.assertRules();
		/*visitor.assertRules();
		visitor.assertRules();
		visitor.assertRules();
		visitor.assertRules();
		visitor.assertRules();
		visitor.assertRules();
		visitor.assertRules();*/
	}
}
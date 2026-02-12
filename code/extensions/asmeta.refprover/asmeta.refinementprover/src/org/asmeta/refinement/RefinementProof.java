package org.asmeta.refinement;

import static org.asmeta.toyices.Utils.currentStateSymbol;
import static org.asmeta.toyices.Utils.getFuncName;
import static org.asmeta.toyices.Utils.nextStateSymbol;
import static org.asmeta.toyices.Utils.renameOverloadFunctions;
import static org.asmeta.toyices.Utils.replaceCurrentNextSymbol;
import static org.asmeta.toyices.Utils.splitDomains;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import org.asmeta.parser.util.Defs;
import org.asmeta.toyices.SMTbasedASMsimulator;
import org.asmeta.toyices.TermVisitor;
import org.asmeta.toyices.Utils;
import org.asmeta.toyices.YicesModel;

import asmeta.definitions.Function;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.TypeDomain;
import asmeta.structure.AgentInitialization;
import asmeta.structure.Asm;
import asmeta.structure.FunctionInitialization;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.Rule;

/**
 * This version avoids to duplicate variables when not
 * necessary. For example, linked functions are not duplicated.
 * However, some functions need to be duplicated, e.g.,
 * derived functions (since they may provide different
 * computation mechanisms). 
 *
 */
public class RefinementProof {
	private static final String UNDEF = "UNDEF";
	private static final String ABS_NAME = "ABSTRACT";
	private static final boolean derivedFunctionsAssertedSeparately = true;//se mettiamo le derivate con la regola, quando facciamo lambda expression per l'exists non le vincoliamo
	public static boolean addMonitoredInAbstract = true;
	private static boolean asLambdaExpressionWithExists = false;//it does not always work (it often returns "unknown")
	private static boolean asExpressionWithExistsWithoutLambda = false;//it does not work
	public static boolean asLambdaExpression = true;
	private static Logger logger = LogManager.getLogger(RefinementProof.class.getName());
	private Asm abstractAsm;
	private Asm refinedAsm;
	private Set<String> conformantFunctionsNames;
	private Set<Function> allFunctions;
	private Set<Function> monitoredAbstractFunctions;
	private Set<Function> derivedAbstractFunctions;
	private Set<Domain> allDomains;
	private String abstractAsmName, refinedAsmName;
	ConsoleAppender console;
	private HashSet<Function> controlledAbstractFunctionNotInConformance;
	private HashSet<Function> onlyAbstractFunctions;

	private void setLogger() {
		console = new ConsoleAppender(new PatternLayout("%m\n"));
		Logger.getRootLogger().addAppender(console);
		LogManager.getLogger("org.asmeta").setLevel(Level.OFF);
		LogManager.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		LogManager.getLogger(SMTbasedASMsimulator.class).setLevel(Level.INFO);
		LogManager.getLogger(RefinementProof.class).setLevel(Level.INFO);
	}

	private void shutdownLogger() {
		Logger.getRootLogger();
		Category.shutdown();
		LogManager.getLogger("org.asmeta").setLevel(Level.OFF);
		LogManager.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		LogManager.getLogger(SMTbasedASMsimulator.class).setLevel(Level.OFF);
		LogManager.getLogger(RefinementProof.class).setLevel(Level.OFF);
	}

	void shutdownConsole() {
		//Logger.getRootLogger().removeAppender(console);
		shutdownLogger();
	}

	public RefinementProof(String abstractAsm, String refinedAsm) throws Exception {
		this.abstractAsmName = abstractAsm;
		this.refinedAsmName = refinedAsm;
		initializeAsms();
	}

	private void initializeAsms() throws Exception {
		LogManager.getLogger(RefinementProof.class).setLevel(Level.INFO);
		setLogger();
		refinedAsm = ASMParser.setUpReadAsm(new File(refinedAsmName)).getMain();
		abstractAsm = ASMParser.setUpReadAsm(new File(abstractAsmName)).getMain();
		//we rename all the functions by adding their arity to the function name
		renameOverloadFunctions(refinedAsm.getHeaderSection().getSignature().getFunction(), true);
		renameOverloadFunctions(abstractAsm.getHeaderSection().getSignature().getFunction(), true);

		//choose variables of the abstract machine must be renamed so that they
		//are declared.
		//RenameChooseVariables r = new RenameChooseVariables("abs_");
		//r.visit(abstractAsm.getMainrule().getRuleBody());

		//we take all the names of the refined functions
		List<Function> refinedFunctions = refinedAsm.getHeaderSection().getSignature().getFunction();
		Set<String> refinedFunctionsNames = new HashSet<String>();
		Map<String, Domain> functionCodomain = new HashMap<String, Domain>();
		Map<String, Function> mapRefinedFunctionameFunction = new HashMap<String, Function>();
		for(Function func: refinedFunctions) {
			refinedFunctionsNames.add(func.getName());
			functionCodomain.put(func.getName(), func.getCodomain());
			mapRefinedFunctionameFunction.put(func.getName(), func);
		}

		conformantFunctionsNames = new HashSet<String>();
		onlyAbstractFunctions = new HashSet<Function>();
		monitoredAbstractFunctions = new HashSet<Function>();
		derivedAbstractFunctions = new HashSet<Function>();
		for(Function abstractFunction: abstractAsm.getHeaderSection().getSignature().getFunction()) {
			//Derived and static (but not abstract constant) functions need to be renamed since
			//they can provide different computation mechanisms in the two machines
			if(Defs.isDerived(abstractFunction) ||
				(Defs.isStatic(abstractFunction) && !Defs.isAbstractConst(abstractFunction))
					|| (Defs.isMonitored(abstractFunction) && addMonitoredInAbstract &&
							!isInitialized(abstractFunction, abstractAsm)
							)
					) {
				abstractFunction.setName(abstractFunction.getName() + ABS_NAME);
				if(Defs.isMonitored(abstractFunction)) {
					monitoredAbstractFunctions.add(abstractFunction);
				}
				else if(Defs.isDerived(abstractFunction)) {
					//System.err.println(abstractFunction.getName());
					derivedAbstractFunctions.add(abstractFunction);
				}
			}
			String funcName = abstractFunction.getName();
			//if both the abstract machine and the refined machine contain the same function name,
			//this function must be checked for conformance
			if(refinedFunctionsNames.contains(funcName)) {
				//monitored and abstract constants cannot be checked for conformance
				if((!addMonitoredInAbstract && Defs.isMonitored(abstractFunction)) && !Defs.isAbstractConst(abstractFunction)) {
					conformantFunctionsNames.add(funcName);
					Domain refinedDomain = functionCodomain.get(funcName);
					Domain abstractDomain = abstractFunction.getCodomain();
					if(!domainsAreEqual(refinedDomain, abstractDomain)) {
						mapRefinedFunctionameFunction.get(funcName).setCodomain(mergeDomains(refinedDomain, abstractDomain));
						//the abstract domain must be renamed so that it can be added to the context
						abstractDomain.setName(abstractDomain.getName() + ABS_NAME);
					}
				}
			}
			else {
				//we track functions that only belong to the abstract machine
				//in order to add them later in the context
				//System.err.println(abstractFunction);
				onlyAbstractFunctions.add(abstractFunction);
			}
		}
		allFunctions = new HashSet<Function>(refinedFunctions);
		allFunctions.addAll(onlyAbstractFunctions);
		List<Domain> refinedDomains = refinedAsm.getHeaderSection().getSignature().getDomain();
		Set<String> refinedDomainsNames = new HashSet<String>();
		for(Domain refinedDomain: refinedDomains) {
			refinedDomainsNames.add(refinedDomain.getName());
		}
		allDomains = new HashSet<Domain>(refinedDomains);
		for(Domain abstractDomain: abstractAsm.getHeaderSection().getSignature().getDomain()) {
			if(!refinedDomainsNames.contains(abstractDomain.getName())) {
				allDomains.add(abstractDomain);
			}
		}
		refinedAsm.getBodySection().getFunctionDefinition().addAll(abstractAsm.getBodySection().getFunctionDefinition());
		
		controlledAbstractFunctionNotInConformance = new HashSet<Function>();
		for(Function func: abstractAsm.getHeaderSection().getSignature().getFunction()) {
			if(Defs.isControlled(func) && !conformantFunctionsNames.contains(func.getName())) {
				controlledAbstractFunctionNotInConformance.add(func);
			}
		}
	}

	private boolean isInitialized(Function abstractFunction, Asm asm) {
		String funcName = abstractFunction.getName();
		for(FunctionInitialization fi: asm.getDefaultInitialState().getFunctionInitialization()) {
			if(fi.getInitializedFunction().getName().equals(funcName)) {
				return true;
			}
		}
		return false;
	}

	/*private void initializeAsms(Set<String> functionsToCheck) throws Exception {
		LogManager.getLogger(RefinementProof.class).setLevel(Level.INFO);
		setLogger();
		refinedAsm = ASMParser.setUpReadAsm(new File(refinedAsmName)).getMain();
		abstractAsm = ASMParser.setUpReadAsm(new File(abstractAsmName)).getMain();
		//we rename all the functions by adding their arity to the function name
		renameOverloadFunctions(refinedAsm.getHeaderSection().getSignature().getFunction(), true);
		renameOverloadFunctions(abstractAsm.getHeaderSection().getSignature().getFunction(), true);

		//we take all the names of the refined functions
		List<Function> refinedFunctions = refinedAsm.getHeaderSection().getSignature().getFunction();
		Set<String> refinedFunctionsNames = new HashSet<String>();
		Map<String, Domain> functionCodomain = new HashMap<String, Domain>();
		Map<String, Function> mapRefinedFunctionameFunction = new HashMap<String, Function>();
		for(Function func: refinedFunctions) {
			refinedFunctionsNames.add(func.getName());
			functionCodomain.put(func.getName(), func.getCodomain());
			mapRefinedFunctionameFunction.put(func.getName(), func);
		}
		
		
		conformantFunctionsNames = functionsToCheck;
		onlyAbstractFunctions = new HashSet<Function>();
		monitoredAbstractFunctions = new HashSet<Function>();
		derivedAbstractFunctions = new HashSet<Function>();
		for(Function abstractFunction: abstractAsm.getHeaderSection().getSignature().getFunction()) {
			//Derived and static (but not abstract constant) functions need to be renamed since
			//they can provide different computation mechanisms in the two machines
			if(Defs.isDerived(abstractFunction) ||
				(Defs.isStatic(abstractFunction) && !Defs.isAbstractConst(abstractFunction)) ||
					Defs.isMonitored(abstractFunction)) {
				abstractFunction.setName(abstractFunction.getName() + ABS_NAME);
				if(Defs.isMonitored(abstractFunction)) {
					monitoredAbstractFunctions.add(abstractFunction);
				}
				else if(Defs.isDerived(abstractFunction)) {
					//System.err.println(abstractFunction.getName());
					derivedAbstractFunctions.add(abstractFunction);
				}
			}
			String funcName = abstractFunction.getName();
			//if both the abstract machine and the refined machine contain the same function name,
			//this function must be checked for conformance
			if(refinedFunctionsNames.contains(funcName)) {
				//monitored and abstract constants cannot be checked for conformance
				if(!Defs.isMonitored(abstractFunction) && !Defs.isAbstractConst(abstractFunction)) {
					
					if(!conformantFunctionsNames.contains(funcName)) {
						abstractFunction.setName(abstractFunction.getName() + ABS_NAME);
						conformantFunctionsNames.add(funcName);
						Domain refinedDomain = functionCodomain.get(funcName);
						Domain abstractDomain = abstractFunction.getCodomain();
						if(!domainsAreEqual(refinedDomain, abstractDomain)) {
							abstractDomain.setName(abstractDomain.getName() + ABS_NAME);
						}
						onlyAbstractFunctions.add(abstractFunction);
					}
				}
			}
			else {
				onlyAbstractFunctions.add(abstractFunction);
			}
		}
		allFunctions = new HashSet<Function>(refinedFunctions);
		allFunctions.addAll(onlyAbstractFunctions);
		List<Domain> refinedDomains = refinedAsm.getHeaderSection().getSignature().getDomain();
		Set<String> refinedDomainsNames = new HashSet<String>();
		for(Domain refinedDomain: refinedDomains) {
			refinedDomainsNames.add(refinedDomain.getName());
		}
		allDomains = new HashSet<Domain>(refinedDomains);
		for(Domain abstractDomain: abstractAsm.getHeaderSection().getSignature().getDomain()) {
			if(!refinedDomainsNames.contains(abstractDomain.getName())) {
				allDomains.add(abstractDomain);
			}
		}
		refinedAsm.getBodySection().getFunctionDefinition().addAll(abstractAsm.getBodySection().getFunctionDefinition());
		
		controlledAbstractFunctionNotInConformance = new HashSet<Function>();
		for(Function func: abstractAsm.getHeaderSection().getSignature().getFunction()) {
			if(Defs.isControlled(func) && !conformantFunctionsNames.contains(func.getName())) {
				controlledAbstractFunctionNotInConformance.add(func);
			}
		}
	}*/

	public RefinementProof(String abstractAsm, String refinedAsm, Set<String> functionsToCheck) throws Exception {
		this(abstractAsm, refinedAsm);
		conformantFunctionsNames = functionsToCheck;
		/*this.abstractAsmName = abstractAsm;
		this.refinedAsmName = refinedAsm;
		initializeAsms(functionsToCheck);*/
	}

	public boolean[] buildProof() throws Exception {
		boolean[] result = new boolean[2];
		try {
			result[0] = initStateProof();//result init state
			System.out.println(";; Initial states are" + (result[0]?"":" not") + " conformant.\n");		
			result[1] = genericStepProof();//result generic step
			System.out.println(";; Generic step is" + (result[1]?"":" not") + " conformant.");
		}
		finally {
			shutdownLogger();
		}
		return result;
	}

	/**
	 * It checks whether the initials states of the abstract and the refined machines
	 * are conformant.
	 * |= refinedInitialState => abstractInitialState
	 * 
	 * @return a boolean saying whether the abstract and the refined initial states are conformant
	 * @throws Exception
	 */
	private boolean initStateProof() throws Exception {
		SMTbasedASMsimulator visitorAbstractAsm = new SMTbasedASMsimulator(abstractAsm);
		//only the refined visitor is used to check satisfiability (and, therefore, to prove conformance)
		SMTbasedASMsimulator visitorRefinedAsm = new SMTbasedASMsimulator(refinedAsm, visitorAbstractAsm.yicesModel);
		logger.info(";;Proof of refinement from " + abstractAsm.getName() + " to " + refinedAsm.getName());
		visitorRefinedAsm.visitDomains(allDomains);//"allDomains" contains all the domains defined in the two machines

		Set<Function> monFuncsInInit = new LinkedHashSet<Function>();
		for(FunctionInitialization fi: visitorAbstractAsm.asm.getDefaultInitialState().getFunctionInitialization()) {
			monFuncsInInit.addAll(GetMonitoredFunctionsUsedInTerm.INSTANCE.visit(fi.getBody()));
		}
		for(Function deriveAbstractFunction: derivedAbstractFunctions) {
			monFuncsInInit.addAll(GetMonitoredFunctionsUsedInTerm.INSTANCE.visit(deriveAbstractFunction.getDefinition().getBody()));
		}

		//visitorRefinedAsm.visitFunctions(allFunctions);//"allFunctions" contains all the functions defined in the two machines
		//not adding the monitored functions may create problems with the derived functions
		HashSet<Function> necessaryFunctions = new HashSet<Function>(allFunctions);
		//necessaryFunctions.removeAll(monitoredAbstractFunctions);//abstract monitored functions do no have to be added
		//necessaryFunctions.removeAll(derivedAbstractFunctions);//do derived functions need to be added?
		//necessaryFunctions.removeAll(onlyAbstractFunctions);
		visitorRefinedAsm.visitFunctions(necessaryFunctions);

		//we retrieve all the derived function definitions
		visitorRefinedAsm.yicesModel.derivedFunctionsDef = visitorRefinedAsm.asm.getBodySection().getFunctionDefinition();
		visitorRefinedAsm.getDerivedFunctionsInYices();//it translates the derived functions (it also contains abstract derived functions)

		String refinedInitState = getInitialStateRepresentation(visitorRefinedAsm);
		if(derivedFunctionsAssertedSeparately) {
			visitorRefinedAsm.assertDerivedDefinitions();//assert the derived functions in the initial state
		}
		else {
			ArrayList<String> ops = new ArrayList<String>();
			ops.add(refinedInitState);
			//System.err.println(refinedInitState);
			for(String der: visitorRefinedAsm.derivedInYices) {
				if(!der.contains(ABS_NAME)) {
					//System.err.println(der);
					ops.add(der.replaceAll(currentStateSymbol, "0"));
				}
			}
			refinedInitState = visitorRefinedAsm.yicesModel.and(ops);
		}

		ArrayList<String> ops = new ArrayList<String>();
		ops.add(getInitialStateRepresentation(visitorAbstractAsm));
		for(String der: visitorRefinedAsm.derivedInYices) {
			if(der.contains(ABS_NAME)) {
				ops.add(der.replaceAll(currentStateSymbol, "0"));
			}
		}
		String originalAbstractInitState = visitorAbstractAsm.yicesModel.and(ops);
		String abstractInitState = originalAbstractInitState;




		//we should take all the monitored functions because now we are not able to get all the needed monitored functions (there are problems with monitored in derived functions: see derivedInUpdateAbstract.asm) TODO: fix it
		//if(monitoredAbstractFunctions.size() > 0) {
		if(monFuncsInInit.size() > 0) {
		//if(monFuncsInInit.size() > 0 || onlyAbstractFunctions.size() > 0) {
			ArrayList<String> variables = new ArrayList<String>();
			ArrayList<Domain> domains = new ArrayList<Domain>();
			for(Function monFunction: monFuncsInInit) {
				Domain codomain = monFunction.getCodomain();
				for(String monLocation: Utils.getLocations(monFunction, visitorAbstractAsm.yicesModel)) {
					variables.add(monLocation);
					domains.add(codomain);
				}
			}
			
			for(Function contrFunc: onlyAbstractFunctions) {
				Domain codomain = contrFunc.getCodomain();
				for(String contrLoc: Utils.getLocations(contrFunc, visitorAbstractAsm.yicesModel)) {
					variables.add(contrLoc);
					domains.add(codomain);
				}
			}
			
			
			if(asLambdaExpression) {
				abstractInitState = assertLambdaExpression("abstractInit", visitorRefinedAsm, abstractInitState, variables, domains);
			}

			ArrayList<String[]> valuesCombinations = visitorAbstractAsm.yicesModel.getValuesCombinationsFromDoms(domains);
			//ArrayList<String[]> valuesCombinations = visitorAbstractAsm.yicesModel.getValuesCombinationsFromDomsWithUndef(domains);
			ArrayList<String> abstractInitStates = new ArrayList<String>();
			for(String[] values: valuesCombinations) {
				if(!asLambdaExpression) {
					//explicit representation of the initial state
					String modifiedAbstractInitState = abstractInitState;
					for(int i = 0; i < variables.size(); i++) {
						modifiedAbstractInitState = modifiedAbstractInitState.replaceAll(variables.get(i).replaceAll(currentStateSymbol, String.valueOf(0)), values[i]);
					}
					abstractInitStates.add(modifiedAbstractInitState);
				}
				else {
					//with lambda expression for initial state
					abstractInitStates.add(getLambdaExpressionInstance("abstractInit", variables, values));
				}
			}
			if(asLambdaExpression && asLambdaExpressionWithExists) {
				//abstractInitState = getLambdaExpressionExists("abstractInit", variables, domains, visitorAbstractAsm.yicesModel);
				abstractInitState = getExpressionExists(originalAbstractInitState, variables, domains, visitorAbstractAsm.yicesModel);
			}
			else {
				abstractInitState = visitorAbstractAsm.yicesModel.or(abstractInitStates);
			}
		}

		//String proof = visitorRefinedAsm.yicesModel.not(visitorRefinedAsm.yicesModel.implies(refinedInitState, abstractInitState));
		logger.info(";; Initial state proof");
		visitorRefinedAsm.parseCommand("(define refinedInitState::bool " + refinedInitState + ")");
		visitorRefinedAsm.parseCommand("(define existsAbstractInitState::bool " + abstractInitState + ")");
		
		String consequent = "existsAbstractInitState";
		//for handling invariants
		visitorRefinedAsm.getInvariantsInYices();
		if(visitorRefinedAsm.invariants != null) {
			visitorRefinedAsm.parseCommand("(define invInitState::bool " + visitorRefinedAsm.invariants.replaceAll(currentStateSymbol, "0") + ")");
			consequent = visitorRefinedAsm.yicesModel.and("invInitState", consequent);			
		}
		
		String proof = visitorRefinedAsm.yicesModel.not(visitorRefinedAsm.yicesModel.implies("refinedInitState", consequent));
		visitorRefinedAsm.parseAndAssertCommand(proof);
		logger.info("(set-evidence! true)");
		String result = visitorRefinedAsm.check();
		assert result.equals("sat") || result.equals("unsat"): "Unexpected result: " + result;
		return result.equals("unsat");
	}

	private String getLambdaExpressionInstance(String lambdaExpressionName, ArrayList<String> variables, String[] values) {
		StringBuilder lambdaInstance = new StringBuilder("(");
		lambdaInstance.append(lambdaExpressionName);
		for(int i = 0; i < variables.size(); i++) {
			lambdaInstance.append(" ").append(values[i]);
		}
		lambdaInstance.append(")");
		return lambdaInstance.toString();
	}

	private String getLambdaExpressionExists(String lambdaExpressionName, ArrayList<String> variables, ArrayList<Domain> domains, YicesModel yicesModel) {
		StringBuilder lambdaExpressionExists = new StringBuilder("(exists (");
		int i = 0;
		for(; i < variables.size() - 1; i++) {
			lambdaExpressionExists.append(getLambdaVariableName(variables.get(i))).append("::").append(Utils.parseDomainNameOrDef(domains.get(i), yicesModel)).append(" ");
		}
		lambdaExpressionExists.append(getLambdaVariableName(variables.get(i))).append("::").append(Utils.parseDomainNameOrDef(domains.get(i), yicesModel));
		lambdaExpressionExists.append(") (").append(lambdaExpressionName);
		for(int j = 0; j < variables.size(); j++) {
			lambdaExpressionExists.append(" ").append(getLambdaVariableName(variables.get(j)));
		}
		lambdaExpressionExists.append("))");
		return lambdaExpressionExists.toString();
	}

	private String getExpressionExists(String expression, ArrayList<String> variables, ArrayList<Domain> domains, YicesModel yicesModel) {
		StringBuilder lambdaExpressionExists = new StringBuilder("(exists (");
		int i = 0;
		for(; i < variables.size() - 1; i++) {
			lambdaExpressionExists.append(parseVariableName(variables.get(i))).append("::").append(Utils.parseDomainNameOrDef(domains.get(i), yicesModel)).append(" ");
		}
		lambdaExpressionExists.append(parseVariableName(variables.get(i))).append("::").append(Utils.parseDomainNameOrDef(domains.get(i), yicesModel));
		lambdaExpressionExists.append(") ").append(expression);
		lambdaExpressionExists.append(")");
		return lambdaExpressionExists.toString();
	}

	/**
	 * It returns the yices representation of the ASM linked by visitor
	 * @param visitor a visitor for an ASM model 
	 * @return
	 */
	private String getInitialStateRepresentation(SMTbasedASMsimulator visitor) {
		List<String> operands = new ArrayList<String>();
		Set<String> initializedFunctions = new HashSet<String>();
		TermVisitor tv = new TermVisitor(visitor.yicesModel);
		if(visitor.defaultInitState != null) {
			List<FunctionInitialization> functionInitialization = visitor.defaultInitState.getFunctionInitialization();
			int size = functionInitialization.size();
			if(size > 0) {
				for(FunctionInitialization funcInit: functionInitialization) {
					String funcName = getFuncName(funcInit.getInitializedFunction());
					//initializedFunctions contains the names of functions that are explicitly
					//initialised
					initializedFunctions.add(funcName);
					funcName = funcName + "0";
					String initStateFunc = tv.visitFunctionDefinition(funcName, funcInit.getVariable(), funcInit.getBody());
					initStateFunc = initStateFunc.replaceAll(currentStateSymbol, "0");
					operands.add(initStateFunc);
				}
			}
		}
		for(Function func: visitor.signature.getFunction()) {
			//if a function is not explicitly initialised, it must initialised with
			//the undef value
			if(Defs.isControlled(func) &&
					!initializedFunctions.contains(func.getName())) {
				String funcName = getFuncName(func) + "0";
				assert visitor.yicesModel.domainUndefValue.get(func.getCodomain().getName()) != null: func.getCodomain().getName() + " has no undef value\nfunction " + funcName;
				String undefName = visitor.yicesModel.domainUndefValue.get(func.getCodomain().getName());
				if(func.getArity() == 0) {
					operands.add(visitor.yicesModel.eq(funcName, undefName));
				}
				else {
					//if the arity is greater than 0, all the locations of the function must be initialised 
					operands.add(tv.forallEqConst(funcName, undefName, splitDomains(func.getDomain())));
				}
			}
		}
		return visitor.yicesModel.and(operands);
	}

	/**
	 * It checks whether the generic steps of the abstract and of the refined
	 * machines are conformant.
	 * |= refinedStep => (abstractStep \/ currentRefinedState = nextRefinedState)
	 * 
	 * @return a boolean saying whether the abstract and the refined generic steps are conformant
	 * @throws Exception
	 */
	private boolean genericStepProof() throws Exception {
		logger.info("");
		logger.info("(reset)");
		logger.info("(set-evidence! false)");
		SMTbasedASMsimulator visitorAbstractAsm = new SMTbasedASMsimulator(abstractAsm);
		//only the refined visitor is used to check satisfiability (and, therefore, to prove conformance)
		SMTbasedASMsimulator visitorRefinedAsm = new SMTbasedASMsimulator(refinedAsm, visitorAbstractAsm.yicesModel);
		visitorRefinedAsm.defaultInitState = null;//the initial state must be removed (since any generic step must be checked)
		visitorRefinedAsm.visitDomains(allDomains);//"allDomains" contains all the domains defined in the two machines

		//visitorRefinedAsm.visitFunctions(allFunctions);//"allFunctions" contains all the functions defined in the two machines
		Set<Function> necessaryFunctions = new HashSet<Function>(allFunctions);
		//necessaryFunctions.removeAll(monitoredAbstractFunctions);
		//necessaryFunctions.removeAll(onlyAbstractFunctions);
		visitorRefinedAsm.visitFunctions(necessaryFunctions);

		getAgentsRules(visitorRefinedAsm);
		visitorRefinedAsm.yicesModel.derivedFunctionsDef = visitorRefinedAsm.asm.getBodySection().getFunctionDefinition();
		visitorRefinedAsm.getDerivedFunctionsInYices();
		//visitorRefinedAsm.assertDerivedDefinitions();//assert the derived functions in the initial state

		getAgentsRules(visitorAbstractAsm);
		visitorRefinedAsm.getRulesInYices();
		visitorAbstractAsm.domainWithoutUndef = visitorRefinedAsm.domainWithoutUndef;
		visitorAbstractAsm.getRulesInYices();

		for(String[] functionDecl: visitorRefinedAsm.yicesModel.contrFuncsDeclarations) {
			assert functionDecl.length == 2;
			String command = functionDecl[0].replaceAll(nextStateSymbol, String.valueOf(1)) + functionDecl[1];
			visitorRefinedAsm.parseCommand(command);
		}
		for(String cvDecl: visitorRefinedAsm.chooseVarsDeclarations) {
			String command = cvDecl.replaceAll(currentStateSymbol, String.valueOf(0));
			visitorRefinedAsm.parseCommand(command);
		}
		visitorRefinedAsm.stateNumber++;
		for(String[] functionDecl: visitorRefinedAsm.yicesModel.monDerFuncsDeclarations) {
			assert functionDecl.length == 2;
			String command = functionDecl[0].replaceAll(currentStateSymbol, String.valueOf(1)) + functionDecl[1];
			visitorRefinedAsm.parseCommand(command);
		}

		//visitorRefinedAsm.assertDerivedDefinitions();
		//List<String> refinedRulesAndOperands = new ArrayList<String>(); 
		//String refinedRulesCommand = replaceCurrentNextSymbol(visitorRefinedAsm.rules, 0);
		//refinedRulesAndOperands.add(refinedRulesCommand);
		List<String> refinedRulesAndOperands = new ArrayList<String>(); 
		String refinedRulesCommand = replaceCurrentNextSymbol(visitorRefinedAsm.rules, 0);
		refinedRulesAndOperands.add(refinedRulesCommand);
		if(derivedFunctionsAssertedSeparately) {
			visitorRefinedAsm.assertDerivedDefinitions("0");
			visitorRefinedAsm.assertDerivedDefinitions("1");
		}
		else {
			for(String der: visitorRefinedAsm.derivedInYices) {
				if(!der.contains(ABS_NAME)) {
					refinedRulesAndOperands.add(der.replaceAll(currentStateSymbol, "0"));
				}
			}
		}
		for(String unchangedFunction: visitorRefinedAsm.unchangedFunctions) {
			refinedRulesAndOperands.add(replaceCurrentNextSymbol(unchangedFunction, 0));
		}
		String refinedStep = visitorRefinedAsm.yicesModel.and(refinedRulesAndOperands);
		

		//computation of the abstract step
		String abstractRules = visitorAbstractAsm.rules;
		abstractRules = replaceCurrentNextSymbol(abstractRules, 0);
		List<String> abstractStepElements = new ArrayList<String>();
		abstractStepElements.add(abstractRules);
		if(!derivedFunctionsAssertedSeparately) {
			for(String der: visitorRefinedAsm.derivedInYices) {
				if(der.contains(ABS_NAME)) {
					abstractStepElements.add(der.replaceAll(currentStateSymbol, "0"));
				}
			}
		}
		for(String unchangedFunction: visitorAbstractAsm.unchangedFunctions) {
			abstractStepElements.add(replaceCurrentNextSymbol(unchangedFunction, 0));
		}
		String originalAbstractStep = visitorRefinedAsm.yicesModel.and(abstractStepElements);
		String abstractStep = originalAbstractStep;
		String lambdaBody = null;

		//it does not work, since we are not able to visit the "program" function. TODO
		//Set<Function> usedAbstractMonitoredFuncs = GetMonitoredFunctionsUsedInTerm.INSTANCE.visit(visitorAbstractAsm.asm.getMainrule().getRuleBody());
		Set<Function> usedAbstractMonitoredFuncs = monitoredAbstractFunctions;
		//if(visitorAbstractAsm.rv.chooseVariablesDomains.size() > 0 || monitoredAbstractFunctions.size() > 0) {
		if(visitorAbstractAsm.rv.chooseVariablesDomains.size() > 0 || usedAbstractMonitoredFuncs.size() > 0) {
		//if(visitorAbstractAsm.rv.chooseVariablesDomains.size() > 0 || onlyAbstractFunctions.size() > 0) {
			ArrayList<String> variables = new ArrayList<String>();
			ArrayList<Domain> domains = new ArrayList<Domain>();
			for(String chooseVar: visitorAbstractAsm.rv.chooseVariablesDomains.keySet()) {
				variables.add(chooseVar);
				domains.add(visitorAbstractAsm.rv.chooseVariablesDomains.get(chooseVar));
			}
			//for(Function monFunction: monitoredAbstractFunctions) {
			//for(Function monFunction: usedAbstractMonitoredFuncs) {
			for(Function monFunction: onlyAbstractFunctions) {
				for(String monLocation: Utils.getLocations(monFunction, visitorAbstractAsm.yicesModel)) {
					variables.add(monLocation);
					domains.add(monFunction.getCodomain());
				}
			}
			if(asLambdaExpression) {
				abstractStep = assertLambdaExpression("abstractStep", visitorRefinedAsm, abstractStep, variables, domains);
				if(asExpressionWithExistsWithoutLambda) {
					lambdaBody = abstractStep;
				}
			}
			
			ArrayList<String[]> valuesCombinations = visitorAbstractAsm.yicesModel.getValuesCombinationsFromDoms(domains);
			//ArrayList<String[]> valuesCombinations = visitorAbstractAsm.yicesModel.getValuesCombinationsFromDomsWithUndef(domains);
			ArrayList<String> abstractSteps = new ArrayList<String>();
			if(!asLambdaExpression) {
				for(String[] values: valuesCombinations) {
					int i = 0;
					String modifiedAbstractStep = abstractStep;
					for(String var: variables) {
						var = var.replaceAll(currentStateSymbol, String.valueOf(0)).replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
						modifiedAbstractStep = modifiedAbstractStep.replaceAll(var, values[i]);
						i++;
					}
					abstractSteps.add(modifiedAbstractStep);
				}
			}
			else {
				//with lambda expression for the abstract step
				for(String[] values: valuesCombinations) {
					abstractSteps.add(getLambdaExpressionInstance("abstractStep", variables, values));
				}
			}
			if(asLambdaExpression && asLambdaExpressionWithExists) {
				if(asExpressionWithExistsWithoutLambda) {
					//actually, this version does not use the lambda expression
					abstractStep = getExpressionExists(lambdaBody, variables, domains, visitorAbstractAsm.yicesModel);
				}
				else {
					abstractStep = getLambdaExpressionExists("abstractStep", variables, domains, visitorAbstractAsm.yicesModel);
				}
			}
			else {
				abstractStep = visitorAbstractAsm.yicesModel.or(abstractSteps);
			}
		}
		List<String> eqs = new ArrayList<String>();
		for(String confFunc: conformantFunctionsNames) {
			eqs.add(visitorRefinedAsm.yicesModel.eq(confFunc + "0", confFunc + "1"));
		}
		String eqState = visitorRefinedAsm.yicesModel.and(eqs);


		//Building of the generic step proof
		
		//first version: the refined step is asserted before the proof
		/*visitorRefinedAsm.parseAndAssertCommand("refined step", refinedStep);
		String proof = visitorRefinedAsm.yicesModel.not(visitorRefinedAsm.yicesModel.or(abstractStep, eqState));
		visitorRefinedAsm.parseAndAssertCommand("Generic step proof", proof);*/

		//second version: the refined step is asserted in the proof
		//note that the two versions are equivalent (in the first one we do no check steps that are not possible in the refined machine) 
		//String proof = visitorRefinedAsm.yicesModel.not(visitorRefinedAsm.yicesModel.implies(refinedStep, visitorRefinedAsm.yicesModel.or(abstractStep, eqState)));
		logger.info(";; Generic step proof");
		visitorRefinedAsm.parseCommand("(define refinedStep::bool " + refinedStep + ")");
		visitorRefinedAsm.parseCommand("(define existsAbstractStep::bool " + abstractStep + ")");
		visitorRefinedAsm.parseCommand("(define stutteringState::bool " + eqState + ")");
		
		
		String antecedent = "refinedStep";
		String consequent = visitorRefinedAsm.yicesModel.or("existsAbstractStep", "stutteringState");
		//for handling invariants
		visitorRefinedAsm.getInvariantsInYices("inv_invForRef");
		if(visitorRefinedAsm.invariants != null) {
			visitorRefinedAsm.parseCommand("(define invCurrState::bool " + visitorRefinedAsm.invariants.replaceAll(currentStateSymbol, "0") + ")");
			antecedent = visitorRefinedAsm.yicesModel.and("invCurrState", antecedent);
			visitorRefinedAsm.parseCommand("(define invNextState::bool " + visitorRefinedAsm.invariants.replaceAll(currentStateSymbol, "1") + ")");
			consequent = visitorRefinedAsm.yicesModel.and("invNextState", consequent);			
		}
		
		
		String proof = visitorRefinedAsm.yicesModel.not(visitorRefinedAsm.yicesModel.implies(antecedent, consequent));
		visitorRefinedAsm.parseAndAssertCommand(proof);
		logger.info("(set-evidence! true)");
		String result = visitorRefinedAsm.check();
		assert result.equals("sat") || result.equals("unsat"): "Unexpected result: " + result;
		if(result.equals("sat")) {
			//TODO print the model
		}
		return result.equals("unsat");
	}

	private String assertLambdaExpression(String lambdaName, SMTbasedASMsimulator asmVisitor, String lambdaBody, ArrayList<String> variables, ArrayList<Domain> domains) {
		StringBuilder sb = new StringBuilder("(define ");
		sb.append(lambdaName).append("::(->");
		for(Domain d: domains) {
			sb.append(" " + Utils.parseDomainNameOrDef(d, asmVisitor.yicesModel));
			//sb.append(" " + asmVisitor.rv.getDomainNameForFunctionDomain(d));
			//sb.append(" " + Utils.getDomainNameForFunctionDomain(d, asmVisitor.domainWithoutUndef, asmVisitor.yicesModel));
		}
		sb.append(" bool) (lambda (");
		int i = 0;
		for(;i < variables.size() - 1; i++) {
			//note that if a location is not fully specified in the rule (e.g., (f t) being t a generic term)
			//the substitution will not be applied
			//The problem is mainly due to the arguments of monitored functions
			String var = getLambdaVariableName(variables.get(i));
			sb.append(var + "::" + Utils.parseDomainNameOrDef(domains.get(i), asmVisitor.yicesModel)).append(" ");
			//sb.append(var + "::" + asmVisitor.rv.getDomainNameForFunctionDomain(domains.get(i))).append(" ");
			String varRegex = variables.get(i).replaceAll(Utils.currentStateSymbol, "0").replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)").replaceAll("\\_", "\\\\_");
			lambdaBody = lambdaBody.replaceAll(varRegex, var);
		}
		String var = getLambdaVariableName(variables.get(i));
		sb.append(var + "::" + Utils.parseDomainNameOrDef(domains.get(i), asmVisitor.yicesModel));
		//sb.append(var + "::" + asmVisitor.rv.getDomainNameForFunctionDomain(domains.get(i)));
		String varRegex = variables.get(i).replaceAll(Utils.currentStateSymbol, "0").replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)").replaceAll("\\_", "\\\\_");
		lambdaBody = lambdaBody.replaceAll(varRegex, var);
		sb.append(") " + lambdaBody + "))");
		String lambdaDefinition = sb.toString();
		asmVisitor.parseCommand(lambdaDefinition);
		return lambdaBody;
	}

	private String getLambdaVariableName(String var) {
		return parseVariableName(var) + "Lambda";
	}

	private String parseVariableName(String var) {
		return var.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(Utils.currentStateSymbol, "").replaceAll(" ", "");
	}

	private void getAgentsRules(SMTbasedASMsimulator visitorAsm) {
		visitorAsm.agentRules = new HashMap<String, Rule>();
		for(AgentInitialization agentInit: visitorAsm.asm.getDefaultInitialState().getAgentInitialization()) {
			String agentName = agentInit.getDomain().getName();
			Rule agentRule = agentInit.getProgram().getCalledMacro().getRuleBody();
			visitorAsm.agentRules.put(agentName, agentRule);
		}
	}

	private Domain mergeDomains(Domain a, Domain b) {
		assert a != null;
		assert b != null;
		assert a.getClass() == b.getClass();
		if(a instanceof BooleanDomain || a instanceof IntegerDomain) {
			return a;
		}
		Set<String> aValues = getDomainValues(a);
		Set<String> bValues = getDomainValues(b);
		if(aValues.containsAll(bValues)) {
			return a;
		}
		else if(bValues.containsAll(aValues)) {
			//System.err.println(bValues);
			b.setName(a.getName());
			return b;
		}
		else {
			throw new Error("TODO: The two domains must be merged! " + a.getName() + ", " + b.getName());
		}
	}

	private boolean domainsAreEqual(Domain a, Domain b) {
		assert a != null;
		assert b != null;
		if(a instanceof BooleanDomain || a instanceof IntegerDomain) {
			return true;
		}
		Set<String> aValues = getDomainValues(a);
		Set<String> bValues = getDomainValues(b);
		if(aValues.containsAll(bValues) && bValues.containsAll(aValues)) {
			return true;
		}
		return false;
	}

	public Set<String> getDomainValues(Domain domain) {
		if(domain instanceof EnumTd) {
			String domainName = domain.getName();
			List<EnumElement> elementTerms = ((EnumTd) domain).getElement();
			Set<String> enumValues = new HashSet<String>();
			String symbol;
			for (int i = 0; i < elementTerms.size(); i++) {
				symbol = elementTerms.get(i).getSymbol();
				enumValues.add(symbol);
			}
			enumValues.add(domainName + UNDEF);
			return enumValues;
		} else if (domain instanceof ConcreteDomain) {
			ConcreteDomain concrDom = (ConcreteDomain) domain;
			TypeDomain typeDomain = concrDom.getTypeDomain();
			if (typeDomain instanceof AgentDomain) {
				throw new Error("Not supported yet");
			} else {
				Term defDomainBody = concrDom.getDefinition().getBody();
				if (defDomainBody instanceof SetTerm) {
					SetTerm set = (SetTerm) defDomainBody;
					Set<String> setValues = new HashSet<String>();
					for (Integer v : TermVisitor.getSetValues(set)) {
						setValues.add(String.valueOf(v));
					}
					setValues.add(String.valueOf(Integer.MIN_VALUE));
					return setValues;
				} else {
					throw new Error();
				}
			}
		}
		else {
			throw new Error("Domain " + domain.getName() + " not supported yet.");
		}
	}

	public static void main(String[] args) throws Exception {
		//RefinementProof proof = new RefinementProof("refinement/increment.asm", "refinement/increment_ref1.asm");
		//RefinementProof proof = new RefinementProof("refinement/increment.asm", "refinement/increment_ref2.asm");
		//RefinementProof proof = new RefinementProof("../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_GM.asm", "../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_EV.asm");
		Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"doors0", "gears0"}));
		//RefinementProof proof = new RefinementProof("../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_GM.asm", "../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_EV.asm", functionsToCheck);
		//RefinementProof proof = new RefinementProof("../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_EV.asm", "../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_SE.asm", functionsToCheck);
		//RefinementProof proof = new RefinementProof("../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_SE.asm", "../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_3L.asm", functionsToCheck);
		RefinementProof proof = new RefinementProof("../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_3L.asm", "../../../Asmeta/asm_examples/examples/landingGearSystem/LGS_HM.asm", functionsToCheck);

		//Set<String> functionsToCheck = new HashSet<String>(Arrays.asList(new String[]{"state"}));
		//RefinementProof proof = new RefinementProof("refinement/cloudRoxana/ClientDisplayOutput.asm", "refinement/cloudRoxana/ClientDisplayOutputWithCookie.asm", functionsToCheck);
		proof.buildProof();
	}
}
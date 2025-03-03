package org.asmeta.modeladvisor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.asmeta.modeladvisor.metaproperties.CaseRuleIsComplete;
import org.asmeta.modeladvisor.metaproperties.Checker;
import org.asmeta.modeladvisor.metaproperties.ChooseRuleIsEmpty;
import org.asmeta.modeladvisor.metaproperties.CondRuleEvalToTrue;
import org.asmeta.modeladvisor.metaproperties.CondRuleIsComplete;
import org.asmeta.modeladvisor.metaproperties.ContrLocCouldBeStatic;
import org.asmeta.modeladvisor.metaproperties.ContrLocIsUpdated;
import org.asmeta.modeladvisor.metaproperties.ContrLocTakesEveryValue;
import org.asmeta.modeladvisor.metaproperties.DomainAllUsed;
import org.asmeta.modeladvisor.metaproperties.ForallRuleIsEmpty;
import org.asmeta.modeladvisor.metaproperties.InconsistentUpdate;
import org.asmeta.modeladvisor.metaproperties.LocationCouldBeRemoved;
import org.asmeta.modeladvisor.metaproperties.MacroCallRuleIsReached;
import org.asmeta.modeladvisor.metaproperties.MacroRuleCalled;
import org.asmeta.modeladvisor.metaproperties.RuleIsReached;
import org.asmeta.modeladvisor.metaproperties.StatDerIsUsed;
import org.asmeta.modeladvisor.metaproperties.TrivialUpdate;
import org.asmeta.modeladvisor.texpr.Expression;
import org.asmeta.nusmv.Environment;
import org.asmeta.nusmv.MapVisitor;
import org.asmeta.nusmv.main.AsmetaSMV;
import org.asmeta.nusmv.util.AsmetaSMVOptions;

/**
 * La classe AsmetaMA. Contiene tutti i metodi per la costruzione e
 * l'interpretazione delle metaproprieta' di smv4val.
 */
public class AsmetaMA {
	
	static Logger log = Logger.getLogger(AsmetaMA.class);
	
	
	// some simplification could create problems to the model reviewer (for example,
	// the substitution of a derived location with its value (inLineFunctions))
	public static boolean USE_ASMETASMV_SIMPL = false;
	// TODO: substitute with a List of Checkers
	public RuleIsReached ruleIsReached;
	ContrLocIsUpdated contrLocIsUpdated;
	InconsistentUpdate inconUpd;
	ContrLocCouldBeStatic contrLocStatic;
	LocationCouldBeRemoved locCouldBeRem;
	CondRuleEvalToTrue condRuleEval;
	MacroCallRuleIsReached mcrIsReached;
	MacroRuleCalled macroRuleCalled;
	DomainAllUsed domainAllUsed;
	ContrLocTakesEveryValue contrLocTakes;
	CondRuleIsComplete condRuleIsCompl;
	CaseRuleIsComplete caseRuleIsCompl;
	StatDerIsUsed statDerIsUsed;
	ChooseRuleIsEmpty chooseRuleIsEmpty;
	ForallRuleIsEmpty forallRuleIsEmpty;
	TrivialUpdate trivialUpdate;
	public Map<Checker, Set<Expression>> nuSmvProperties;
	public static boolean LOG_COUNTEREXAMPLES = false;

	// indicate the metaproperty to be checked
	static public enum ExecCheck {
		// MP1
		execInconsistentUpdates,
		// other MPs
		execMacroCallRuleIsReached, execContrLocTakesEveryValue, execMacroRuleCalled,
		execContrLocCouldBeStatic, execNoTrivialUpdate, execDomainAllUsed, execChooseRuleEmpty, execRuleIsReached,
		execForallRuleEmpty, execCondRuleIsComplete, execCaseRuleIsComplete, execContrLocIsUpdated, execStatDerIsUsed,
		execCondRuleEvalToTrue, execLocationCouldBeRemoved
	}
	// the check to be performed
	private Map<ExecCheck, Boolean> execCheck = new HashMap<AsmetaMA.ExecCheck, Boolean>();

	// activate or deactivate a check
	public void activateExecCheck(ExecCheck s, boolean b) {
		execCheck.put(s, b);
	}

	public MapVisitor mv;
	private Environment env;
	private String asmFile;
	private Set<String> funcNamesForMP1;// names of the functions that must be checked by MP1. If null, all functions
										// must be checked

	public static AsmetaMA buildAsmetaMA(String asmFile) throws Exception {
		return buildAsmetaMA(Paths.get(asmFile));
	}

	public static AsmetaMA buildAsmetaMA(Path asmFile) throws Exception {
		AsmetaMA asmetaMA = null;
		String forMp1FilePath = asmFile.toString();
		forMp1FilePath = forMp1FilePath.substring(0, forMp1FilePath.length() - 4);
		forMp1FilePath = forMp1FilePath + ".forMP1";
		// System.out.println(forMp1FilePath);
		if (new File(forMp1FilePath).exists()) {
			Set<String> funcsForMP1 = new HashSet<>();
			for (String func : Files.readAllLines(Paths.get(forMp1FilePath))) {
				funcsForMP1.add(func);
			}
			System.out.println("checking MP1 only for " + funcsForMP1);
			asmetaMA = new AsmetaMA(asmFile.toString(), funcsForMP1);
		} else {
			asmetaMA = new AsmetaMA(asmFile.toString());
		}
		return asmetaMA;
	}

	private AsmetaMA(String asmFile) throws Exception {
		this.asmFile = asmFile;
		nuSmvProperties = new HashMap<Checker, Set<Expression>>();

		// checks whether a rule is executed.
		// Too generic: it refers to ALL the rules
//		execCheck.put(ExecCheck.execRuleIsReached,false);
//		execCheck.put(ExecCheck.execContrLocIsUpdated,false);
//		execCheck.put(ExecCheck.execInconsistentUpdates,false);
//		execCheck.put(ExecCheck.execContrLocCouldBeStatic,false);
//		execCheck.put(ExecCheck.execLocationCouldBeRemoved,false);
//		execCheck.put(ExecCheck.execNoTrivialUpdate,false);
//		execCheck.put(ExecCheck.execCondRuleEvalToTrue,false);
//		execCheck.put(ExecCheck.execMacroCallRuleIsReached,false);
//		execCheck.put(ExecCheck.execMacroRuleCalled = false;
//		execCheck.put(ExecCheck.execDomainAllUsed = false;
//		execCheck.put(ExecCheck.execContrLocTakesEveryValue = false;
//		execCheck.put(ExecCheck.execCondRuleIsComplete = false;
//		execCheck.put(ExecCheck.execCaseRuleIsComplete = false;
//		execCheck.put(ExecCheck.execStatDerIsUsed = false;
//		execCheck.put(ExecCheck.execChooseRuleEmpty = false;
//		execCheck.put(ExecCheck.execForallRuleEmpty = false;
		// initially no check is enables
		for (ExecCheck c : ExecCheck.values()) {
			execCheck.put(c, false);
		}
		AsmetaSMVOptions.doAsmetaMA = true;
	}

	private AsmetaMA(String asmFile, Set<String> funcNamesForMP1) throws Exception {
		this(asmFile);
		this.funcNamesForMP1 = funcNamesForMP1;
	}

	// run the check of all the desired meta properties and return if there is a
	// violation or not
	public Map<String, Boolean> runCheck() throws Exception {
		AsmetaSMV asmetaSMV = loadAsmetaSMV();
		setCheckers();
		execCheck(asmetaSMV);
		return readResults(asmetaSMV);
	}

	public void execCheck(AsmetaSMV asmetaSMV) throws Exception {
		// we don't want to check other CTL/LTL properties
		asmetaSMV.mv.ctlList.clear();
		asmetaSMV.mv.ltlList.clear();
		asmetaSMV.mv.invariantList.clear();
		// add now the properties for asmetaMA
		Set<String> translatedAllProperties = new HashSet<>();
		System.err.println(nuSmvProperties.entrySet());
		for (Entry<Checker, Set<Expression>> entry : nuSmvProperties.entrySet()) {
			Set<Expression> properties = entry.getValue();
			if (!properties.isEmpty()) {
				Set<String> translatedProperties = translate(properties);
				log.debug("adding: " + translatedProperties);
				System.err.println("adding: " + translatedProperties);
				translatedAllProperties.addAll(translatedProperties);
				// System.out.println(entry.getKey().getClass().getSimpleName() + " "
				// +translatedProperties);
			} else {
				log.debug("no property to add for " + entry.getKey());
			}
		}
		
		if (!translatedAllProperties.isEmpty()) {
			asmetaSMV.addCtlProperties(translatedAllProperties);
			asmetaSMV.createTempNuSMVfile();
			log.debug("executing " + asmetaSMV.getSmvFileName());
			asmetaSMV.executeNuSMV();
		}
	}

	public void setCheckers() {
		if (execCheck.get(ExecCheck.execRuleIsReached)) {
			ruleIsReached = new RuleIsReached(mv.ruleCond);
			nuSmvProperties.put(ruleIsReached, ruleIsReached.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execContrLocIsUpdated)) {
			contrLocIsUpdated = new ContrLocIsUpdated(mv.contrLocations, mv.updateMap, mv.nusmvNameToLocation);
			nuSmvProperties.put(contrLocIsUpdated, contrLocIsUpdated.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execInconsistentUpdates)) {
			inconUpd = new InconsistentUpdate(mv.updateMap, mv.nusmvNameToLocation, funcNamesForMP1);
			nuSmvProperties.put(inconUpd, inconUpd.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execContrLocCouldBeStatic)) {
			contrLocStatic = new ContrLocCouldBeStatic(mv.env.usedLoc, mv.initMap, mv.contrFuncLocations);
			nuSmvProperties.put(contrLocStatic, contrLocStatic.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execLocationCouldBeRemoved)) {
			locCouldBeRem = new LocationCouldBeRemoved(mv);
			nuSmvProperties.put(locCouldBeRem, locCouldBeRem.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execNoTrivialUpdate)) {
			trivialUpdate = new TrivialUpdate(mv.updateMap, mv.nusmvNameToLocation);
			nuSmvProperties.put(trivialUpdate, trivialUpdate.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execCondRuleEvalToTrue)) {
			condRuleEval = new CondRuleEvalToTrue(mv.condRuleEvalToTrueThen, mv.condRuleEvalToTrueElse, env);
			nuSmvProperties.put(condRuleEval, condRuleEval.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execMacroCallRuleIsReached)) {
			mcrIsReached = new MacroCallRuleIsReached(mv.ruleCond);
			nuSmvProperties.put(mcrIsReached, mcrIsReached.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execMacroRuleCalled)) {
			macroRuleCalled = new MacroRuleCalled(mv.macroRuleCalled);
		}
		if (execCheck.get(ExecCheck.execDomainAllUsed)) {
			domainAllUsed = new DomainAllUsed(mv);
			nuSmvProperties.put(domainAllUsed, domainAllUsed.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execContrLocTakesEveryValue)) {
			contrLocTakes = new ContrLocTakesEveryValue(mv);
			nuSmvProperties.put(contrLocTakes, contrLocTakes.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execCondRuleIsComplete)) {
			condRuleIsCompl = new CondRuleIsComplete(mv.condRuleIsComplete, env);
			nuSmvProperties.put(condRuleIsCompl, condRuleIsCompl.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execCaseRuleIsComplete)) {
			caseRuleIsCompl = new CaseRuleIsComplete(mv.caseRuleIsComplete, env);
			nuSmvProperties.put(caseRuleIsCompl, caseRuleIsCompl.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execStatDerIsUsed)) {
			statDerIsUsed = new StatDerIsUsed(mv.derFuncLocations, mv.statFuncLocations, mv.nusmvNameToLocation,
					mv.statDerReachabilityConds, mv.usedStatDerInDer);
			nuSmvProperties.put(statDerIsUsed, statDerIsUsed.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execChooseRuleEmpty)) {
			chooseRuleIsEmpty = new ChooseRuleIsEmpty(mv.chooseRuleSetIsEmpty, env);
			nuSmvProperties.put(chooseRuleIsEmpty, chooseRuleIsEmpty.createNuSmvProperties());
		}
		if (execCheck.get(ExecCheck.execForallRuleEmpty)) {
			forallRuleIsEmpty = new ForallRuleIsEmpty(mv.forallRuleSetIsEmpty, env);
			nuSmvProperties.put(forallRuleIsEmpty, forallRuleIsEmpty.createNuSmvProperties());
		}
	}

	private Map<String, Boolean> readResults(AsmetaSMV asmetaSMV) throws Exception, IOException {

		if (LOG_COUNTEREXAMPLES) {
			HashMap<Integer, String> cexs = asmetaSMV.getPropertiesCounterExample();
			Path asmPath = Paths.get(asmFile);
			String asmName = asmPath.getFileName().toString();
			File fileCexs = new File(
					asmPath.getParent() + "/" + asmName.substring(0, asmName.length() - 4) + "_cexs.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileCexs));
			for (Entry<Integer, String> indexProp : asmetaSMV.getPropertiesForPrinting().entrySet()) {
				Integer index = indexProp.getKey();
				if (cexs.containsKey(index)) {
					bw.write(indexProp.getValue());
					bw.newLine();
					bw.write(cexs.get(index));
					bw.newLine();
				}
			}
			bw.close();
		}
		Map<String, Boolean> globalResult = new HashMap<>();
		// MP1: No inconsistent update is ever performed
		if (execCheck.get(ExecCheck.execInconsistentUpdates)) {
			check(asmetaSMV, inconUpd, globalResult);
		}
		// MP2: Every conditional rule must be complete
		if (execCheck.get(ExecCheck.execCondRuleIsComplete)) {
			check(asmetaSMV, condRuleIsCompl, globalResult);
		}
		// MP2: Every case rule without otherwise must be complete
		if (execCheck.get(ExecCheck.execCaseRuleIsComplete)) {
			check(asmetaSMV, caseRuleIsCompl, globalResult);
		}
		// MP3: Choose rule is always/sometimes/never not empty
		if (execCheck.get(ExecCheck.execChooseRuleEmpty)) {
			check(asmetaSMV, chooseRuleIsEmpty, globalResult);
		}
		// MP3: Forall rule is always/sometimes/never not empty
		if (execCheck.get(ExecCheck.execForallRuleEmpty)) {
			check(asmetaSMV, forallRuleIsEmpty, globalResult);
		}
		// MP3: Conditional rule eval to true
		if (execCheck.get(ExecCheck.execCondRuleEvalToTrue)) {
			check(asmetaSMV, condRuleEval, globalResult);
		}
		// MP4: No assignment is always trivial
		if (execCheck.get(ExecCheck.execNoTrivialUpdate)) {
			check(asmetaSMV, trivialUpdate, globalResult);
		}
		// MP5: For every domain element e, there exists a location which has value e
		if (execCheck.get(ExecCheck.execDomainAllUsed)) {
			check(asmetaSMV, domainAllUsed, globalResult);
		}
		// MP6: Every controlled location can take any value in its codomain
		if (execCheck.get(ExecCheck.execContrLocTakesEveryValue)) {
			check(asmetaSMV, contrLocTakes, globalResult);
		}
		// MP7: a location could be removed
		if (execCheck.get(ExecCheck.execLocationCouldBeRemoved)) {
			check(asmetaSMV, locCouldBeRem, globalResult);
		}
		// MP7: a controlled location is never updated
		if (execCheck.get(ExecCheck.execContrLocIsUpdated)) {
			check(asmetaSMV, contrLocIsUpdated, globalResult);
		}
		// MP7: a controlled location could be static
		if (execCheck.get(ExecCheck.execContrLocCouldBeStatic)) {
			check(asmetaSMV, contrLocStatic, globalResult);
		}

		// should the following evaluations be executed?
		if (execCheck.get(ExecCheck.execRuleIsReached)) {
			check(asmetaSMV, ruleIsReached, globalResult);
		}
		if (execCheck.get(ExecCheck.execMacroCallRuleIsReached)) {
			check(asmetaSMV, mcrIsReached, globalResult);
		}
		if (execCheck.get(ExecCheck.execMacroRuleCalled)) {
			macroRuleCalled.evaluation();
		}
		if (execCheck.get(ExecCheck.execStatDerIsUsed)) {
			check(asmetaSMV, statDerIsUsed, globalResult);
		}
		return globalResult;
	}

	public AsmetaSMV loadAsmetaSMV() throws Exception {
		AsmetaSMV asmetaSMV = null;
		asmetaSMV = new AsmetaSMV(asmFile);
		AsmetaSMVOptions.simplify = USE_ASMETASMV_SIMPL;
		AsmetaSMVOptions.setPrintCounterExample(true);
		AsmetaSMVOptions.keepNuSMVfile = true;
		asmetaSMV.translation();
		mv = asmetaSMV.mv;
		env = asmetaSMV.mv.env;
		return asmetaSMV;
	}

	// check and update the results
	private void check(AsmetaSMV asmetaSMV, Checker checker, Map<String, Boolean> partialResult) {
		Set<Expression> properties = nuSmvProperties.get(checker);
		Set<String> translatedProperties = translate(properties);
		//System.out.println(translatedProperties);
		Map<String, Boolean> results = asmetaSMV.mv.getResults(translatedProperties);
		checker.evaluation(results);
		checker.printResults();
		partialResult.putAll(results);
	}

	// given a set of temp properties, builds the set of their CTL translation
	public Set<String> translate(Set<? extends Expression> properties) {
		Set<String> result = new HashSet<String>();
		for (Expression t : properties) {
			result.add(t.getSMV());
		}
		return result;
	}

	public void setAllMetapropertiesExecution() {
		setMetapropertiesExecution(true, true, true, true, true, true, true);
	}

	public void setMetapropertiesExecution(boolean execMp1, boolean execMp2, boolean execMp3, boolean execMp4,
			boolean execMp5, boolean execMp6, boolean execMp7) {
		activateExecCheck(ExecCheck.execInconsistentUpdates, execMp1);
		activateExecCheck(ExecCheck.execCondRuleIsComplete, execMp2);
		activateExecCheck(ExecCheck.execCaseRuleIsComplete, execMp2);
		activateExecCheck(ExecCheck.execChooseRuleEmpty, execMp3);
		activateExecCheck(ExecCheck.execForallRuleEmpty, execMp3);
		activateExecCheck(ExecCheck.execCondRuleEvalToTrue, execMp3);
		activateExecCheck(ExecCheck.execNoTrivialUpdate, execMp4);
		activateExecCheck(ExecCheck.execDomainAllUsed, execMp5);
		activateExecCheck(ExecCheck.execContrLocTakesEveryValue, execMp6);
		activateExecCheck(ExecCheck.execContrLocCouldBeStatic, execMp7);
		activateExecCheck(ExecCheck.execLocationCouldBeRemoved, execMp7);
		activateExecCheck(ExecCheck.execContrLocIsUpdated, execMp7);
	}
}
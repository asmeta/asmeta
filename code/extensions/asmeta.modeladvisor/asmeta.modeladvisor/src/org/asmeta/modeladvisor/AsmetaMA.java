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
import org.asmeta.nusmv.AsmetaSMV;
import org.asmeta.nusmv.AsmetaSMVOptions;
import org.asmeta.nusmv.Environment;
import org.asmeta.nusmv.MapVisitor;

/**
 * La classe AsmetaMA. Contiene tutti i metodi per la costruzione e
 * l'interpretazione delle metaproprieta' di smv4val.
 */
public class AsmetaMA {
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

	// indicate whether a metaproperty must be checked
	// TODO: substitute with a List of Booleans
	boolean execMacroCallRuleIsReached, execInconsistentUpdates, execContrLocTakesEveryValue;
	public boolean execMacroRuleCalled;
	boolean execContrLocCouldBeStatic;
	boolean execNoTrivialUpdate;
	boolean execDomainAllUsed;
	boolean execChooseRuleEmpty;
	public boolean execRuleIsReached;
	boolean execForallRuleEmpty;
	boolean execCondRuleIsComplete;
	boolean execCaseRuleIsComplete;
	boolean execContrLocIsUpdated;
	boolean execStatDerIsUsed;
	boolean execCondRuleEvalToTrue;
	boolean execLocationCouldBeRemoved;

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
		forMp1FilePath = forMp1FilePath +  ".forMP1";
		//System.out.println(forMp1FilePath);
		if(new File(forMp1FilePath).exists()) {
			Set<String> funcsForMP1 = new HashSet<>();
			for(String func: Files.readAllLines(Paths.get(forMp1FilePath))) {
				funcsForMP1.add(func);
			}
			System.out.println("checking MP1 only for " + funcsForMP1);
			asmetaMA = new AsmetaMA(asmFile.toString(), funcsForMP1);
		}
		else {
			asmetaMA = new AsmetaMA(asmFile.toString());
		}
		return asmetaMA;
	}

	private AsmetaMA(String asmFile) throws Exception {
		this.asmFile = asmFile;
		nuSmvProperties = new HashMap<Checker, Set<Expression>>();

		// checks whether a rule is executed.
		// Too generic: it refers to ALL the rules
		execRuleIsReached = false;

		execContrLocIsUpdated = false;
		execInconsistentUpdates = false;
		execContrLocCouldBeStatic = false;
		execLocationCouldBeRemoved = false;
		execNoTrivialUpdate = false;
		execCondRuleEvalToTrue = false;
		execMacroCallRuleIsReached = false;
		execMacroRuleCalled = false;
		execDomainAllUsed = false;
		execContrLocTakesEveryValue = false;
		execCondRuleIsComplete = false;
		execCaseRuleIsComplete = false;
		execStatDerIsUsed = false;
		execChooseRuleEmpty = false;
		execForallRuleEmpty = false;
		AsmetaSMVOptions.doAsmetaMA = true;
	}

	private AsmetaMA(String asmFile, Set<String> funcNamesForMP1) throws Exception {
		this(asmFile);
		this.funcNamesForMP1 = funcNamesForMP1;
	}

	public void runCheck() throws Exception {
		AsmetaSMV asmetaSMV = loadAsmetaSMV();
		setCheckers();
		execCheck(asmetaSMV);
		readResults(asmetaSMV);
	}

	public void execCheck(AsmetaSMV asmetaSMV) throws Exception {
		// we don't want to check other CTL/LTL properties
		asmetaSMV.mv.ctlList.clear();
		asmetaSMV.mv.ltlList.clear();
		asmetaSMV.mv.ctlListNames.clear();
		asmetaSMV.mv.ltlListNames.clear();

		for (Entry<Checker, Set<Expression>> entry : nuSmvProperties.entrySet()) {
			// System.err.println("adding: " + translate(properties));
			Set<Expression> properties = entry.getValue();
			if (properties.size() > 0) {
				Set<String> translatedProperties = translate(properties);
				// System.out.println(entry.getKey().getClass().getSimpleName() + " "
				// +translatedProperties);
				asmetaSMV.addCtlProperties(translatedProperties);
			}
		}

		asmetaSMV.createTempNuSMVfile();
		asmetaSMV.executeNuSMV();
	}

	public void setCheckers() {
		if (execRuleIsReached) {
			ruleIsReached = new RuleIsReached(mv.ruleCond);
			nuSmvProperties.put(ruleIsReached, ruleIsReached.createNuSmvProperties());
		}
		if (execContrLocIsUpdated) {
			contrLocIsUpdated = new ContrLocIsUpdated(mv.contrLocations, mv.updateMap, mv.nusmvNameToLocation);
			nuSmvProperties.put(contrLocIsUpdated, contrLocIsUpdated.createNuSmvProperties());
		}
		if (execInconsistentUpdates) {
			inconUpd = new InconsistentUpdate(mv.updateMap, mv.nusmvNameToLocation, funcNamesForMP1);
			nuSmvProperties.put(inconUpd, inconUpd.createNuSmvProperties());
		}
		if (execContrLocCouldBeStatic) {
			contrLocStatic = new ContrLocCouldBeStatic(mv.env.usedLoc, mv.initMap, mv.contrFuncLocations);
			nuSmvProperties.put(contrLocStatic, contrLocStatic.createNuSmvProperties());
		}
		if (execLocationCouldBeRemoved) {
			locCouldBeRem = new LocationCouldBeRemoved(mv);
			nuSmvProperties.put(locCouldBeRem, locCouldBeRem.createNuSmvProperties());
		}
		if (execNoTrivialUpdate) {
			trivialUpdate = new TrivialUpdate(mv.updateMap, mv.nusmvNameToLocation);
			nuSmvProperties.put(trivialUpdate, trivialUpdate.createNuSmvProperties());
		}
		if (execCondRuleEvalToTrue) {
			condRuleEval = new CondRuleEvalToTrue(mv.condRuleEvalToTrueThen, mv.condRuleEvalToTrueElse, env);
			nuSmvProperties.put(condRuleEval, condRuleEval.createNuSmvProperties());
		}
		if (execMacroCallRuleIsReached) {
			mcrIsReached = new MacroCallRuleIsReached(mv.ruleCond);
			nuSmvProperties.put(mcrIsReached, mcrIsReached.createNuSmvProperties());
		}
		if (execMacroRuleCalled) {
			macroRuleCalled = new MacroRuleCalled(mv.macroRuleCalled);
		}
		if (execDomainAllUsed) {
			domainAllUsed = new DomainAllUsed(mv);
			nuSmvProperties.put(domainAllUsed, domainAllUsed.createNuSmvProperties());
		}
		if (execContrLocTakesEveryValue) {
			contrLocTakes = new ContrLocTakesEveryValue(mv);
			nuSmvProperties.put(contrLocTakes, contrLocTakes.createNuSmvProperties());
		}
		if (execCondRuleIsComplete) {
			condRuleIsCompl = new CondRuleIsComplete(mv.condRuleIsComplete, env);
			nuSmvProperties.put(condRuleIsCompl, condRuleIsCompl.createNuSmvProperties());
		}
		if (execCaseRuleIsComplete) {
			caseRuleIsCompl = new CaseRuleIsComplete(mv.caseRuleIsComplete, env);
			nuSmvProperties.put(caseRuleIsCompl, caseRuleIsCompl.createNuSmvProperties());
		}
		if (execStatDerIsUsed) {
			statDerIsUsed = new StatDerIsUsed(mv.derFuncLocations, mv.statFuncLocations, mv.nusmvNameToLocation,
					mv.statDerReachabilityConds, mv.usedStatDerInDer);
			nuSmvProperties.put(statDerIsUsed, statDerIsUsed.createNuSmvProperties());
		}
		if (execChooseRuleEmpty) {
			chooseRuleIsEmpty = new ChooseRuleIsEmpty(mv.chooseRuleSetIsEmpty, env);
			nuSmvProperties.put(chooseRuleIsEmpty, chooseRuleIsEmpty.createNuSmvProperties());
		}
		if (execForallRuleEmpty) {
			forallRuleIsEmpty = new ForallRuleIsEmpty(mv.forallRuleSetIsEmpty, env);
			nuSmvProperties.put(forallRuleIsEmpty, forallRuleIsEmpty.createNuSmvProperties());
		}
	}

	private void readResults(AsmetaSMV asmetaSMV) throws Exception, IOException {

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

		// MP1: No inconsistent update is ever performed
		if (execInconsistentUpdates) {
			check(asmetaSMV, inconUpd);
		}
		// MP2: Every conditional rule must be complete
		if (execCondRuleIsComplete) {
			check(asmetaSMV, condRuleIsCompl);
		}
		// MP2: Every case rule without otherwise must be complete
		if (execCaseRuleIsComplete) {
			check(asmetaSMV, caseRuleIsCompl);
		}
		// MP3: Choose rule is always/sometimes/never not empty
		if (execChooseRuleEmpty) {
			check(asmetaSMV, chooseRuleIsEmpty);
		}
		// MP3: Forall rule is always/sometimes/never not empty
		if (execForallRuleEmpty) {
			check(asmetaSMV, forallRuleIsEmpty);
		}
		// MP3: Conditional rule eval to true
		if (execCondRuleEvalToTrue) {
			check(asmetaSMV, condRuleEval);
		}
		// MP4: No assignment is always trivial
		if (execNoTrivialUpdate) {
			check(asmetaSMV, trivialUpdate);
		}
		// MP5: For every domain element e, there exists a location which has value e
		if (execDomainAllUsed) {
			check(asmetaSMV, domainAllUsed);
		}
		// MP6: Every controlled location can take any value in its codomain
		if (execContrLocTakesEveryValue) {
			check(asmetaSMV, contrLocTakes);
		}
		// MP7: a location could be removed
		if (execLocationCouldBeRemoved) {
			check(asmetaSMV, locCouldBeRem);
		}
		// MP7: a controlled location is never updated
		if (execContrLocIsUpdated) {
			check(asmetaSMV, contrLocIsUpdated);
		}
		// MP7: a controlled location could be static
		if (execContrLocCouldBeStatic) {
			check(asmetaSMV, contrLocStatic);
		}

		// should the following evaluations be executed?
		if (execRuleIsReached) {
			check(asmetaSMV, ruleIsReached);
		}
		if (execMacroCallRuleIsReached) {
			check(asmetaSMV, mcrIsReached);
		}
		if (execMacroRuleCalled) {
			macroRuleCalled.evaluation();
		}
		if (execStatDerIsUsed) {
			check(asmetaSMV, statDerIsUsed);
		}
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

	private void check(AsmetaSMV asmetaSMV, Checker checker) {
		Set<Expression> properties = nuSmvProperties.get(checker);
		Set<String> translatedProperties = translate(properties);
		// System.out.println(translatedProperties);
		checker.evaluation(results(asmetaSMV, translatedProperties));
		checker.printResults();
	}

	public Map<String, Boolean> results(AsmetaSMV asmetaSMV, Set<String> translatedProps) {
		return asmetaSMV.getResults(translatedProps);
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
		this.execInconsistentUpdates = execMp1;
		this.execCondRuleIsComplete = execMp2;
		this.execCaseRuleIsComplete = execMp2;
		this.execChooseRuleEmpty = execMp3;
		this.execForallRuleEmpty = execMp3;
		this.execCondRuleEvalToTrue = execMp3;
		this.execNoTrivialUpdate = execMp4;
		this.execDomainAllUsed = execMp5;
		this.execContrLocTakesEveryValue = execMp6;
		this.execContrLocCouldBeStatic = execMp7;
		this.execLocationCouldBeRemoved = execMp7;
		this.execContrLocIsUpdated = execMp7;
	}
}
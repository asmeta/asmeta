package org.asmeta.xt.validator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.asmeta.avallaxt.avallaXt.Invariant;
import org.asmeta.avallaxt.avallaXt.Scenario;
import org.asmeta.avallaxt.avallaXt.Set;
import org.asmeta.avallaxt.validation.ScenarioUtility;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.Defs;
import org.asmeta.parser.util.AsmPrinter;
import org.asmeta.simulator.util.MonitoredFinder;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Injector;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.SharedFunction;
import asmeta.definitions.TemporalProperty;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.structure.Asm;
import asmeta.structure.FunctionInitialization;
import asmeta.structure.ImportClause;
import asmeta.structure.Initialization;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;

/**
 * AsmPrinter that takes avalla script and produces an Asemta Spec
 * 
 * @author garganti
 */
public class AsmetaFromAvallaBuilder extends AsmPrinter {
	private static final Logger logger = Logger.getLogger(AsmetaFromAvallaBuilder.class);

	/** The scenario. */
	Scenario scenario;

	/** The new main. */
	String newMain;

	/** The old main name. */
	String oldMainName;

	/** The parent path. */
	String scenarioDirectoryPath;

	/** The asm. */
	Asm asm;

	String tempAsmPath;
	String tempAsmName;

	String modelPath;
	String modelPathDir;

	/**
	 * The invariants specified in the model.
	 */
	private Collection<asmeta.definitions.Invariant> modelInvariants;

	private File modelFile;

	private ArrayList<Set> monitoredInitState;// PA: 2017/12/29

	private List<ArrayList<Set>> allMonitored;// PA: 2017/12/29

	/**
	 * Instantiates a new builder.
	 * 
	 * @param scenarioPath
	 *            the scenario path
	 * @param tempAsmPath
	 *            the complete name (including the path and the asm file) where to
	 *            save the temporary asm
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public AsmetaFromAvallaBuilder(String scenarioPath, String tempAsmPath) throws Exception {		
		super(tempAsmPath);
		scenarioDirectoryPath = new File(scenarioPath).getAbsoluteFile().getParent();
		assert tempAsmPath.endsWith(".asm");
		this.tempAsmPath = tempAsmPath;
		tempAsmName = new File(tempAsmPath).getName();
		tempAsmName = tempAsmName.substring(0, tempAsmName.length() - 4);
		buildScript(scenarioPath);
	}

	public AsmetaFromAvallaBuilder(String scenarioPath) throws Exception {
		this(scenarioPath, Files.createTempFile("__tempAsmetaV", ".asm").toString());
	}

	/**
	 * Builds the script.
	 * 
	 * @param scenarioPath
	 *            the scenario path
	 * 
	 * @throws Exception
	 *             the exception
	 */
	private void buildScript(String scenarioPath) throws Exception {
		// read the spec from file
		Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
		rs.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = rs.getResource(URI.createFileURI(scenarioPath), true);
		resource.load(rs.getLoadOptions());
		scenario = (Scenario) resource.getContents().get(0);
		// get the specification loaded by the script
		modelPath = ScenarioUtility.getAsmPath(scenario);
		logger.debug("modelPath " + modelPath);
		modelFile = new File(modelPath);
		logger.debug("modelFile " + modelFile);
		logger.debug("modelFile " + modelFile.exists());
		AsmCollection pack = ASMParser.setUpReadAsm(modelFile);
		asm = pack.getMain();
		MacroDeclaration mainrule = asm.getMainrule();
		// TODO, or just add an empty main rule?
		if (mainrule == null) throw new RuntimeException("an asm without main cannot be validated by scenarios");
		oldMainName = mainrule.getName();
		// questo sbaglia
		// modelPathDir = modelPath.substring(0,
		// modelPath.lastIndexOf(File.separatorChar));
		modelPathDir = Paths.get(modelPath).getParent().toString();
	}

	/**
	 * Save.
	 */
	public void save() {
		StatementToStringBuffer stb = new StatementToStringBuffer(scenario, oldMainName, scenarioDirectoryPath);
		stb.parseCommands();
		monitoredInitState = stb.monitoredInitState;// PA: 2017/12/29
		allMonitored = stb.allMonitored;// PA: 2017/12/29
		List<String> statements = stb.statements;
		newMain = buildNewMain(statements).toString();
		visit(asm);
		close();
	}

	/**
	 * Builds the new main.
	 * 
	 * @param statements
	 */
	StringBuilder buildNewMain(List<String> statements) {
		StringBuilder buff = new StringBuilder();
		// make switch statement
		buff.append("switch step__\n");
		for (int i = 0; i < statements.size(); i++) {
			String stm = statements.get(i);
			buff.append("\t\t\tcase " + i + ":\n");
			buff.append("\t\t\t\t" + stm);
		}
		buff.append("\t\tendswitch");
		return buff;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.asmeta.parser.util.AsmPrinter#visitDefault(asmeta.structure.
	 * Initialization)
	 */
	@Override
	public void visitDefault(Initialization init) {
		if (init == null) {
			println("// added by validator");
			println("default init s0__:");
			indent();
			println("// added by validator");
			println("function step__ = 0");
			unIndent();
		} else {
			super.visitDefault(init);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.asmeta.parser.util.AsmPrinter#sort(java.util.Collection)
	 */
	// @Override //PA: commentato il 13 set 10 per eliminare l'errore: "The
	// method sort(Collection<RuleDeclaration>) of type AsmetaFromAvallaBuilder must override or
	// implement a supertype method AsmetaFromAvallaBuilder.java
	// /validator/src/org/asmeta/validator/asm line 198 Java Problem"
	public RuleDeclaration[] sort(Collection<RuleDeclaration> ruleDcls) {
		RuleDeclaration[] tmp = TopologicalSort.sort(ruleDcls);
		return tmp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.asmeta.parser.util.AsmPrinter#getAsmName()
	 */
	@Override
	public String getAsmName() {
		return tempAsmName;
	}

	/**
	 * add the path.
	 * 
	 * @param imports
	 *            the imports
	 */
	@Override
	public void visit(Collection<ImportClause> imports) {
		if (imports != null) {
			for (ImportClause importClause : imports) {
				String name = importClause.getModuleName();
				// name is relative to the load spec
				//
				String importedName = (modelPathDir == null ? "" : (modelPathDir + File.separatorChar)) + name;
				// replace \ with \\ so when printed it will be preinted correctly (with \\)
				// TODO tha path should be OS independent
				importedName = importedName.replace("\\", "\\\\");
				// if the name contains spaces, add the double quotes
				if (importedName.contains(" ")) {
					assert !importedName.contains("\"");
					importedName = "\"" + importedName + "\"";
				}
				// it can be smething like: 
				println("import " + importedName);
			}
		}
	}

	// PA 2017/12/29
	@Override
	protected void visitInvariantBody(asmeta.definitions.Invariant invariant) {
		MonitoredFinder mf = new MonitoredFinder();
		Term body = invariant.getBody();
		boolean isMonitoredInvariant = mf.visit(body);
		if (isMonitoredInvariant && allMonitored.get(allMonitored.size() - 1).isEmpty()) {
			// TODO: set the correct step number
			print("step__ >= " + (allMonitored.size() - 1) + " or ");
		}
		super.visitInvariantBody(invariant);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.asmeta.parser.util.AsmPrinter#visitMain(asmeta.transitionrules.
	 * basictransitionrules.MacroDeclaration)
	 */
	@Override
	public void visitMain(MacroDeclaration main) {
		if (modelInvariants != null) {
			println("//model invariants");
			super.visitInvariants(modelInvariants);
		}
		List<Invariant> invs = scenario.getInvariants();
		if (invs != null && invs.size() > 0) {
			println("//scenario invariants");
			for (Invariant inv : invs) {
				println("invariant inv_" + inv.getName() + " over Boolean: " + inv.getExpression());
			}
		}
		println("// new main added by validator");
		println("main rule r_main__ =");
		indent();
		println(newMain);
	}

	@Override
	protected void visitInvariants(Collection<asmeta.definitions.Invariant> invariants) {
		// the invariants must be moved, after the old main rule
		modelInvariants = invariants;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.asmeta.parser.util.AsmPrinter#visitFunctions(java.util.Collection)
	 */
	@Override
	public void visitFunctions(Collection<Function> funcs) {
		println("// added by validator");
		println("controlled step__: Integer");
		super.visitFunctions(funcs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.asmeta.parser.util.AsmPrinter#visitDcl(asmeta.definitions.
	 * MonitoredFunction)
	 */
	@Override
	public void visitDcl(MonitoredFunction function) {
		println("// converted to controlled by validator");
		print("controlled ");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.asmeta.parser.util.AsmPrinter#visitDcl(asmeta.definitions.
	 * SharedFunction )
	 */
	@Override
	public void visitDcl(SharedFunction function) {
		println("// converted to controlled by validator");
		print("controlled ");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.asmeta.parser.util.AsmPrinter#visitFuncInits(java.util.Collection)
	 */
	@Override
	public void visitFuncInits(Collection<FunctionInitialization> funcs) {
		println("// added by validator");
		println("function step__ = 0");

		// PA 2017/12/29
		// build the map for n-ary function
		// function insideCall($x in D) = at({1 -> "eee"}, $x)
		// function insideCall($x in D, $y in D2) = at({(1,2) -> "eee"}, ($x,$y))
		Map<String, Map<String, String>> monitoredInit = new HashMap<>();
		for (Set set : monitoredInitState) {
			// take the function name
			String location = set.getLocation();
			int ido = location.indexOf('(');
			// PUT in the map???
			if (ido >= 0) {
				// a n-ray function
				String name = location.substring(0, ido);
				String args = location.substring(ido); // including the (
				Map<String, String> map = monitoredInit.get(name);
				if (map == null) {
					map = new HashMap<>();
					monitoredInit.put(name, map);
				}
				map.put(args, set.getValue());
			} else {
				println("function " + location + " = " + set.getValue());
			}
		}
		// get the functions
		EList<Function> functions = asm.getHeaderSection().getSignature().getFunction();		
		// get also the imported functions
		asm.getHeaderSection().getImportClause().stream()
			.map(x -> x.getImportedFunction()).forEach(functions::addAll);		
		// print the map
		for (Entry<String, Map<String, String>> e : monitoredInit.entrySet()) {
			String funcName = e.getKey();
			// there exists a function with that name
			assert functions.stream().anyMatch(t -> t.getName().equals(funcName))
				: "function " + funcName + " not found";
			// get the signature			 
			Function func = functions.stream().filter(x -> x.getName().equals(funcName)).findFirst().get();
			// get the domains
			Domain domain = func.getDomain();
			List<String> domainNames = new ArrayList<String>();
			if (domain instanceof ProductDomain) {
				domainNames.addAll(((ProductDomain) domain).getDomains().stream().map(x -> x.getName())
						.collect(Collectors.toList()));
			} else {
				domainNames.add(domain.getName());
			}
			// now print variables 
			print("function " + funcName + "(");
			String varNames = "";
			for (int i = 0; i < domainNames.size(); i++) {
				String dn = domainNames.get(i);
				String varname = (i!=0 ? "," : "") + "$x" + i;
				print( varname + " in " + dn);
				varNames += varname;				
			}
			print(") = at({");
			// now the map !!!
			Map<String, String> values = e.getValue();
			boolean first = true;
			for(Entry<String,String>   val: values.entrySet()) {
				if (! first) { 
					print(",");
				}
				first = false;
				// something like (5,3) -> 6
				print(val.getKey() + "->" + val.getValue());
			}
			/// close the map
			// (5,5) -> 4}, ($r,$c))
			println("},(" + varNames + "))");
		}
		// add the init for the ASM
		super.visitFuncInits(funcs);
	}

	@Override
	protected void visitTemporalProperties(Collection<? extends TemporalProperty> properties) {
	}

	@Override
	public void visitRuleDefs(Collection<RuleDeclaration> defs) {
		if (defs != null) {
			RuleDeclaration[] rules = sortRuleDeclarations(defs);
			for (RuleDeclaration rule : rules) {
				Asm asm = Defs.getAsm(rule);
				if (asm == model) {
					visitDef(rule);
				}
			}
		}
	}	
	@Override	
	public void visitInit(FunctionInitialization init) {
		// collect all the location set by the init state
		java.util.Set<String> allLocations = monitoredInitState.stream().map(x -> x.getLocation()).collect(Collectors.toSet());
		// check if this function is already defined in the initial state of the scenario
		if (allLocations.contains(init.getInitializedFunction().getName())) return;
		super.visitInit(init);
	}

}

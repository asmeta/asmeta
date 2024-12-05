package org.asmeta.xt.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.asmeta.avallaxt.avalla.Command;
import org.asmeta.avallaxt.avalla.Invariant;
import org.asmeta.avallaxt.avalla.Pick;
import org.asmeta.avallaxt.avalla.Set;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.Defs;
import org.asmeta.parser.Utility;
import org.asmeta.parser.util.AsmPrinter;
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;
import org.asmeta.simulator.RuleSubstitution;
import org.asmeta.simulator.TermAssignment;
import org.asmeta.simulator.util.MonitoredFinder;
import org.asmeta.simulator.util.StandardLibrary;
import org.asmeta.simulator.wrapper.RuleFactory;

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
import asmeta.terms.basicterms.BasictermsFactory;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;

public class AsmetaPrinterForAvalla extends AsmPrinter {

	private static final String STEP = "step__";

	public static final String R_MAIN = "r_main__";

	public static final String IS_PICKED = "is_picked_";
	public static final String VAL_PICKED = "val_picked_";

	// ASMs already translated (to avoid over translation
	// asm path (absolute) of the original asm -> where (path) it has been
	// translated
	private HashMap<Path, Path> translatedFiles = new HashMap<>();

	// TODO just one should be enough
	private String tempAsmName;
	File tempAsmPath;

	// path of the original asm (useful to get the path and other stuff, like name
	// and so on)
	Path asmPath;

	private AsmetaFromAvallaBuilder builder;

	private static Logger LOG = Logger.getLogger(AsmetaPrinterForAvalla.class);

	/**
	 * Instantiates a new asmeta printer for avalla.
	 *
	 * @param tempAsmPath the temp asm path where to save the new ASM (build from
	 *                    avalla)
	 * @param asmPathDir  the path of the original model asm is stored
	 * @param builder     the builder
	 * @throws FileNotFoundException the file not found exception
	 */
	public AsmetaPrinterForAvalla(File tempAsmPath, Path asmPath, AsmetaFromAvallaBuilder builder)
			throws FileNotFoundException {
		super(tempAsmPath);
		assert tempAsmPath.getName().endsWith(ASMParser.ASM_EXTENSION);
		this.tempAsmPath = tempAsmPath;
		tempAsmName = tempAsmPath.getName();
		tempAsmName = tempAsmName.substring(0, tempAsmName.length() - 4);
		assert asmPath.toString().endsWith(ASMParser.ASM_EXTENSION);
		this.asmPath = asmPath;
		this.builder = builder;
	}

	@Override
	public void visit(Asm asm) {
		// add a comment - careful, since the "u" cannot be escaped even in the
		// comments,
		// if there is a directory staring with it gets an error
		String filename = asmPath.normalize().toUri().toString();
		println("// translation of the asm (for avalla) " + filename);
		super.visit(asm);
	}

	@Override
	public void visit(ChooseRule chooseRule) {
		List<VariableTerm> vars = chooseRule.getVariable();
		// Pick for choose rules with multiple variables not yet supported
		if (vars.size() != 1) {
			super.visit(chooseRule);
			return;
		}
		VariableTerm var = vars.get(0);
		Term range = chooseRule.getRanges().get(0); //
		Term guardTerm = chooseRule.getGuard();
		Rule doRule = chooseRule.getDoRule();
		String isPicked = IS_PICKED + var.getName().substring(1) + "_" + super.currentRuleDeclaration.getName();
		String valPicked = VAL_PICKED + var.getName().substring(1) + "_" + super.currentRuleDeclaration.getName();
		// if the variable is never picked in the avalla, print the choose rule
		if (getPickFromVariable(var, super.currentRuleDeclaration.getName(), this.builder.allPickRules) == null) {
			super.visit(chooseRule);
		} else {
			// substitute, where necessary, the variables starting with $ with the
			// correspondent val_picked_X controlled function
			TermAssignment assignment = new TermAssignment();
			VariableTerm newTerm = BasictermsFactory.eINSTANCE.createVariableTerm();
			newTerm.setName(valPicked);
			assignment.put(vars, Collections.singleton(newTerm));
			RuleSubstitution substitution = new RuleSubstitution(assignment, new RuleFactory());
			Rule newDoRule = substitution.visit(doRule);
			Term newGuardTerm = substitution.visit(guardTerm);
			String guardString = super.tp.visit(newGuardTerm);
			// print
			println("if not " + isPicked + " then");
			indent();
			super.visit(chooseRule);
			unIndent();
			println("else ");
			// to check if the picked value is in the term used as domain (e.g. {10:20})
			if (!(range instanceof DomainTerm)) {
				indent();
				println("if " + "contains(" + super.tp.visit(range) + ", " + valPicked + ")" + " then");
			}
			indent();
			println("if " + guardString + " then");
			indent();
			visit(newDoRule);
			unIndent();
			println("else");
			indent();
			println("seq");
			indent();
			println("result := print(\"CHECK FAILED: the value cannot be chosen as the guard evaluates to false\")");
			println("step__ := -2"); // -2 so plus 1 is still < 0
			unIndent();
			println("endseq");
			unIndent();
			println("endif");
			unIndent();
			// to when the picked value is not in the term used as domain
			if (!(range instanceof DomainTerm)) {
				println("else");
				indent();
				println("seq");
				indent();
				println("result := print(\"CHECK FAILED: the value is not in the domain\")");
				println("step__ := -2"); // -2 so plus 1 is still < 0
				unIndent();
				println("endseq");
				unIndent();
				println("endif");
				unIndent();
			}
			println("endif");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.asmeta.parser.util.AsmPrinter#visitDefault(asmeta.structure.
	 * Initialization)
	 */
	@Override
	public void visitDefault(Initialization init) {
		if (model.getMainrule() == null) {
			println("// this ASM has no main, FunctionInitialization ignored");
			return;
		}
		// i fit shas a main run a default init state must be added
		// for step or for functions set in the initial part of the scenario
		println("// added by validator (Initialization)");
		String initName = init == null ? "s0__" : init.getName();
		println("default init " + initName + ":");
		indent();
		// if the init is defined, proceed as usual (see super.visitDefault)
		if (init != null)
			visitFuntionsAgents(init);
		else
			// otherwise add the functions (monitored -> controlled)
			visitFuncInits(Collections.EMPTY_SET);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.asmeta.parser.util.AsmPrinter#sort(java.util.Collection)
	 */
	// @Override //PA: commentato il 13 set 10 per eliminare l'errore: "The
	// method sort(Collection<RuleDeclaration>) of type AsmetaFromAvallaBuilder must
	// override or
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
	 * @param imports the imports
	 */
	@Override
	public void visit(Collection<ImportClause> imports) {
		if (imports != null) {
			for (ImportClause importClause : imports) {
				// get the name of the file to import. name is relative to the load spec
				String name = importClause.getModuleName();
				// now build the path
				// the asm to be imported
				String asmDirbase = asmPath.getParent() == null ? null : asmPath.getParent().toString();
				File importFile = Utility.importFile(asmDirbase, importClause);
				Path importedAsmPath = importFile.toPath().normalize();
				assert Files.exists(importedAsmPath)
						: " path (imported ASM) " + importedAsmPath.toString() + " does not exist";
				if (StandardLibrary.isAStandardLibrary(name)) {
					printImport(importedAsmPath);
				} else {
					// convert the file to a new file with monitored -> controlled
					// change also the path - in the same directory
					try {
						// search for the file which is included
						LOG.debug("Looking for imported ASM path" + importedAsmPath);
						Path importedFile;
						// Check whether the file has been already processed
						if (translatedFiles.containsKey(importedAsmPath)) {
							importedFile = translatedFiles.get(importedAsmPath);
							LOG.debug(importedAsmPath + " in include is already translated in " + importedFile);
							assert Files.exists(importedFile) : "File not found";
						} else {
							// get the name form the file, not from the ASM which must be read after
							String fileName = importedAsmPath.getFileName().toString();
							assert fileName.endsWith(ASMParser.ASM_EXTENSION);
							String asmName = fileName.substring(0, fileName.length() - 4);
							// build the temp asm file and store in the table
							// in the same directory
							File folder = tempAsmPath.getParentFile();
							assert folder.exists() && folder.isDirectory();
							importedFile = File.createTempFile("_" + asmName + "_", ASMParser.ASM_EXTENSION,
									tempAsmPath.getParentFile()).toPath();
							LOG.debug(importedAsmPath + " to be translated into " + importedFile);
							translatedFiles.put(importedAsmPath, importedFile);
							AsmetaPrinterForAvalla newprinter = new AsmetaImportedPrinterForAvalla(
									importedFile.toFile(), importedAsmPath, builder);
							newprinter.translatedFiles = this.translatedFiles;
							// call recursively
							// import the ASM
							AsmCollection pack = ASMParser.setUpReadAsm(importedAsmPath.toFile());
							// now visit this imported asm
							newprinter.visit(pack.getMain());
							newprinter.tempAsmName = importedFile.getFileName().toString();
							newprinter.close();
						}
						// add the new import
						printImport(importedFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	// the complete path of the file to import - this file must exist (in case add
	// .asm)
	// the print will transform it to a relative path to the temporary ASM written
	// without the .asm
	private void printImport(Path importedAsm) {
		String importedName = printImport(tempAsmPath.toString(), importedAsm);
		println("import " + importedName);
	}

	/**
	 * return the string to be used in the import.
	 *
	 * @param tempAsmPath the temp asm
	 * @param importedAsm the imported asm
	 * @return the string to be used to
	 */
	static String printImport(String tempAsmPath, Path importedAsm) {
		assert importedAsm.toFile().exists() : "imported file with path " + importedAsm + " does not exists";
		assert importedAsm.toFile().getName().endsWith(ASMParser.ASM_EXTENSION);
		// convert to a relative path with the current file
		assert new File(tempAsmPath).exists();
		Path tempAsmPathParent = new File(tempAsmPath).getParentFile().toPath();
		assert tempAsmPathParent.toFile().exists() && tempAsmPathParent.toFile().isDirectory();
		// if they share the parent use just the name of the imported Asm
		String importedName;
		if (importedAsm.getParent().equals(tempAsmPathParent)) {
			// use just the name
			importedName = importedAsm.toFile().getName();
		} else {
			// use absolute path
			importedName = importedAsm.toAbsolutePath().normalize().toString();
		}
		// remove extension
		importedName = importedName.substring(0, importedName.length() - 4);
		// replace \ with \\ so when printed it will be printed correctly (with \\)
		// TODO the path should be OS independent
		importedName = importedName.replace("\\", "\\\\");
		// if the name contains spaces, add the double quotes
		if (importedName.contains(" ") || importedName.contains("(") || importedName.contains(")")) {
			assert !importedName.contains("\"");
			importedName = "\"" + importedName + "\"";
		}
		LOG.debug("temp Asm Path  " + tempAsmPath);
		LOG.debug("imported Asm   " + importedAsm);
		LOG.debug("imported as -->" + importedName);
		return importedName;
	}

	// PA 2017/12/29
	@Override
	protected void visitInvariantBody(asmeta.definitions.Invariant invariant) {
		MonitoredFinder mf = new MonitoredFinder();
		Term body = invariant.getBody();
		boolean isMonitoredInvariant = mf.visit(body);
		if (isMonitoredInvariant && this.builder.allMonitored.get(this.builder.allMonitored.size() - 1).isEmpty()) {
			// TODO: set the correct step number
			print("step__ >= " + (this.builder.allMonitored.size() - 1) + " or ");
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
		if (this.builder.modelInvariants != null) {
			println("//model invariants");
			super.visitInvariants(this.builder.modelInvariants);
		}
		List<Invariant> invs = this.builder.scenario.getInvariants();
		if (invs != null && invs.size() > 0) {
			println("//scenario invariants");
			for (Invariant inv : invs) {
				println("invariant inv_" + inv.getName() + " over Boolean: " + inv.getExpression());
			}
		}
		println("// new main added by validator");
		println("main rule " + R_MAIN + " =");
		indent();
		println(this.builder.newMain);
	}

	/** print a rule declaration (not main rule) **/
	@Override
	protected void visitDef(RuleDeclaration dcl) {
		if (model == null || model.getMainrule() != dcl) {
			super.visitDef(dcl);
		} else {
			// it is a main rule : remove as main and set it again
			model.setMainrule(null);
			super.visitDef(dcl);
			model.setMainrule((MacroDeclaration) dcl);
		}
	}

	@Override
	protected void visitInvariants(Collection<asmeta.definitions.Invariant> invariants) {
		// the invariants must be moved, after the old main rule
		this.builder.modelInvariants = invariants;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.asmeta.parser.util.AsmPrinter#visitFunctions(java.util.Collection)
	 */
	@Override
	public void visitFunctions(Collection<Function> funcs) {
		// add the main if necessary (it has the main rule)
		if (model.getMainrule() != null) {
			println("// added by validator");
			println("controlled " + STEP + ": Integer");
		}
		visitDeclaredFunctions(funcs);
		// add the controlled functions relative to choose variables
		if (!this.builder.allPickRules.isEmpty()) {
			println("// added by validator to implement determinism in choose rule");
			for (Entry<ChooseRule, String> cr : this.builder.allChooseRules.entrySet()) {
				// only choose rules with ONE variable are supported in pick
				VariableTerm variable = cr.getKey().getVariable().get(0);
				String varName = variable.getName().substring(1) + "_" + cr.getValue();
				if (getPickFromVariable(variable, cr.getValue(), this.builder.allPickRules) != null) {
					println("controlled " + IS_PICKED + varName + ": Boolean");
					println("controlled " + VAL_PICKED + varName + ": " + variable.getDomain().getName());
				}
			}
		}
	}

	protected void visitDeclaredFunctions(Collection<Function> funcs) {
		super.visitFunctions(funcs);
	}

	@Override
	public void visitDcl(Function function) {
		String name = function.getName();
		Domain domain = function.getDomain();
		Domain codomain = function.getCodomain();
		// 26/04/2021 -> Silvia: do not convert monitored to controlled if simulation
		// time is set to auto increment or use java time
		if ((Environment.timeMngt == TimeMngt.auto_increment || Environment.timeMngt == TimeMngt.use_java_time)
				&& Environment.monTimeFunctions.containsKey(name)) {
			println("// Auto_increment time: keep function monitored");
			print("monitored ");
		} else
			visitUnknownDcl(function);
		print(name + ": ");
		if (domain != null) {
			visit(domain);
			print(" -> ");
		}
		visit(codomain);
		println();
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
		if (model.getMainrule() == null) {
			println("// this ASM has no main, FunctionInitialization ignored");
			return;
		}
		println("// added by validator (visitFuncInits)");
		println("function step__ = 0");
		// get all the functions
		List<Function> functions = new ArrayList<>();
		functions.addAll(builder.asm.getHeaderSection().getSignature().getFunction());
		// get also ALL the imported functions indirectly
		// in any case these must be printed in this ASM if set in the scenario
		// recursively
		this.builder.asm.getHeaderSection().getImportClause().stream().map(x1 -> x1.getImportedFunction())
				.forEach(functions::addAll);
		//
		// PA 2017/12/29
		// build the map for n-ary function
		// function insideCall($x in D) = at({1 -> "eee"}, $x)
		// function insideCall($x in D, $y in D2) = at({(1,2) -> "eee"}, ($x,$y))
		Map<Function, Map<String, String>> monitoredInit = new HashMap<>();
		for (Command c : this.builder.monitoredInitState) {
			// Only for Set, Pick results in unary functions
			if (c instanceof Set) {
				Set set = (Set) c;
				// For monitored from Set
				// take the function name
				String funcName;
				String location = set.getLocation();
				int ido = location.indexOf('(');
				if (ido >= 0) {
					// a n-ray function
					funcName = location.substring(0, ido);
				} else {
					funcName = location;
				}
				// there exists a function with that name - it can false because
				LOG.debug("function " + funcName
						+ (functions.stream().anyMatch(t -> t.getName().equals(funcName)) ? " found" : " not found"));
				// get the signature if there is one
				Optional<Function> func = functions.stream().filter(x -> x.getName().equals(funcName)).findFirst();
				if (!func.isPresent())
					continue;
				// only if the the function is declared in this asm
				/*
				 * //AG 5/2021: questo adesso lo ignoro, non guardo le ASM, potrei settare
				 * qualcosa che importo // if (Defs.getAsm(func.get())!= model) { if (!
				 * Defs.getAsm(func.get()).getName().equals(model.getName())) { continue; }
				 */
				// PUT in the map???
				if (ido >= 0) {
					// a n-ray function
					String args = location.substring(ido); // including the (
					Map<String, String> map = monitoredInit.get(func.get());
					if (map == null) {
						map = new HashMap<>();
						monitoredInit.put(func.get(), map);
					}
					map.put(args, set.getValue());
				} else {
					println("function " + funcName + " = " + set.getValue());
				}
			}
		}
		// print the map for functions A -> B
		for (Entry<Function, Map<String, String>> e : monitoredInit.entrySet()) {
			Function func = e.getKey();
			// get the domains
			Domain domain = func.getDomain();
			List<String> domainNames = new ArrayList<>();
			if (domain instanceof ProductDomain) {
				domainNames.addAll(((ProductDomain) domain).getDomains().stream().map(x -> x.getName())
						.collect(Collectors.toList()));
			} else {
				domainNames.add(domain.getName());
			}
			// now print variables
			print("function " + func.getName() + "(");
			String varNames = "";
			for (int i = 0; i < domainNames.size(); i++) {
				String dn = domainNames.get(i);
				String varname = (i != 0 ? "," : "") + "$x" + i;
				print(varname + " in " + dn);
				varNames += varname;
			}
			print(") = at({");
			// now the map !!!
			Map<String, String> values = e.getValue();
			boolean first = true;
			for (Entry<String, String> val : values.entrySet()) {
				if (!first) {
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
		List<Pick> allInitPick = this.builder.monitoredInitState.stream().filter(x -> x instanceof Pick)
				.map(x -> ((Pick) x)).collect(Collectors.toList());
		if (!this.builder.allPickRules.isEmpty()) {
			println("// initialize is_picked_X and val_picked_X functions");
			for (Entry<ChooseRule, String> cr : this.builder.allChooseRules.entrySet()) {
				// only choose rules with ONE variable are supported in pick
				VariableTerm variable = cr.getKey().getVariable().get(0);
				String varName = variable.getName().substring(1) + "_" + cr.getValue();
				String lastPickedValue = getPickFromVariable(variable, cr.getValue(), this.builder.allPickRules);
				String lastInitPickedValue = getPickFromVariable(variable, cr.getValue(), allInitPick);
				if (lastInitPickedValue != null) {
					println("function " + IS_PICKED + varName + " = true");
					println("function " + VAL_PICKED + varName + " = " + lastInitPickedValue);
				} else if (lastPickedValue != null) {
					println("function " + IS_PICKED + varName + " = false");
				}
			}
		}
		// add the init for the ASM
		super.visitFuncInits(funcs);
	}

	/**
	 * Given a VariableTerm, the name of the RuleDeclaration in which it is used,
	 * and a list of Pick rules, search and return the value of the last appearance of
	 * a Pick rule in the list that picks a value for that variable term.
	 * 
	 * @param variable            the variable term to search in the list of pick
	 *                            rules
	 * @param RuleDeclarationName the name of the rule declaration in which the
	 *                            variable term is used
	 * @param pickList            the list of picks where to search the variable
	 *                            name
	 * @return the value of the last Pick that picks a value for the variable term,
	 *         null if not present
	 */
	private String getPickFromVariable(VariableTerm variable, String RuleDeclarationName, List<Pick> pickList) {
		// reversed to get the last, so if pickList is a list of init
		// where the same variable is inserted picked multiple times,
		// only the last pick is considered
		for (Pick pick : pickList.reversed())
			if (pick.getVar().equals(variable.getName()) && (pick.getRule() == null
					|| pick.getRule().equals(RuleDeclarationName)))
				return pick.getValue();
		return null;
	}

	@Override
	protected void visitTemporalProperties(Collection<? extends TemporalProperty> properties) {
		// in avalla the temporal properties (*TL) are skipped
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
		java.util.Set<String> allLocations = this.builder.monitoredInitState.stream().filter(x -> x instanceof Set)
				.map(x -> ((Set) x).getLocation()).collect(Collectors.toSet());
		// check if this function is already defined in the initial state of the
		// scenario
		if (allLocations.contains(init.getInitializedFunction().getName())) {
			println("// " + init.getInitializedFunction().getName()
					+ " is initialized also in the initial state - it will ignored");
			print("// ");
		}
		super.visitInit(init);
	}
}
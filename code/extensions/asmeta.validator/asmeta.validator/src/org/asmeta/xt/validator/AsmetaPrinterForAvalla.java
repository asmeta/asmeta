package org.asmeta.xt.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.asmeta.avallaxt.avallaXt.Invariant;
import org.asmeta.avallaxt.avallaXt.Set;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.Defs;
import org.asmeta.parser.util.AsmPrinter;
import org.asmeta.simulator.util.MonitoredFinder;
import org.asmeta.simulator.util.StandardLibrary;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.util.internal.Log;

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
import org.asmeta.simulator.Environment;
import org.asmeta.simulator.Environment.TimeMngt;

public class AsmetaPrinterForAvalla extends AsmPrinter {
	
	
	// ASMs already translated (to avoid over translation
	// asm path (absolute) of the original asm -> where (path) it has been translated
	private HashMap<Path,Path> translatedFiles = new HashMap<>();

	// TODO just one should be enough
	private String tempAsmName;
	File tempAsmPath;

	// path of the original asm (useful to get the path and other stuff, like name and so on)
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
		assert tempAsmPath.getName().endsWith(".asm");
		this.tempAsmPath = tempAsmPath;
		tempAsmName = tempAsmPath.getName();
		tempAsmName = tempAsmName.substring(0, tempAsmName.length() - 4);
		assert asmPath.toString().endsWith(".asm");
		this.asmPath = asmPath;
		this.builder = builder;
	}

	private AsmetaPrinterForAvalla(File tempAsmPath, Path asmPath, AsmetaFromAvallaBuilder builder, HashMap<Path,Path> fileNames) throws FileNotFoundException {
		this(tempAsmPath,asmPath,builder);
		this.translatedFiles = fileNames;
	}

	
	
	
	public void visit(Asm asm) {
		// add a comment
		println("// translation of the asm (for avalla) " + asmPath.normalize().toString());
		super.visit(asm);
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
		println("default init "+initName+":");
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
				Path importedAsmPath = asmPath.getParent().resolve(name + ".asm").toAbsolutePath().normalize();
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
							LOG.debug(importedAsmPath + " in include is already translated in "  + importedFile);
							assert Files.exists(importedFile) : "File not found";
						} else {
							// get the name form the file, not from the ASM which must be read after
							String fileName = importedAsmPath.getFileName().toString();
							assert fileName.endsWith(".asm");
							String asmName = fileName.substring(0, fileName.length()-4);
							// build the temp asm file and store in the table
							// in the same directory 
							File folder = tempAsmPath.getParentFile();
							assert folder.exists() && folder.isDirectory();
							importedFile = File.createTempFile("_" + asmName +"_", ".asm", tempAsmPath.getParentFile()).toPath();
							LOG.debug(importedAsmPath + " to be translated into "  + importedFile);
							translatedFiles.put(importedAsmPath, importedFile);
							// call recursively
							AsmetaPrinterForAvalla newprinter = new AsmetaPrinterForAvalla(importedFile.toFile(),
									importedAsmPath, builder, this.translatedFiles);
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
		String importedName = printImport(tempAsmPath.toString(),importedAsm);
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
		assert importedAsm.toFile().getName().endsWith(".asm");
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
			importedName =  importedAsm.toAbsolutePath().normalize().toString();
		}
		// remove extension
		importedName = importedName.substring(0, importedName.length() - 4);		
		// replace \ with \\ so when printed it will be printed correctly (with \\)
		// TODO the path should be OS independent
		importedName = importedName.replace("\\", "\\\\");
		// if the name contains spaces, add the double quotes
		if (importedName.contains(" ")) {
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
		println("main rule r_main__ =");
		indent();
		println(this.builder.newMain);
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
		if (model.getMainrule() != null) {
			println("// added by validator");
			println("controlled step__: Integer");
		}
		super.visitFunctions(funcs);
	}

	@Override
	public void visitDcl(Function function) {
		String name = function.getName();
		Domain domain = function.getDomain();
		Domain codomain = function.getCodomain();
		//26/04/2021 -> Silvia: do not convert monitored to controlled if simulation time is set to auto increment or use java time
		if ((Environment.timeMngt == TimeMngt.auto_increment || Environment.timeMngt == TimeMngt.use_java_time) && Environment.monTimeFunctions.containsKey(name)) {
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
		this.builder.asm.getHeaderSection().getImportClause().stream().map(x1 -> x1.getImportedFunction()).forEach(functions::addAll);
		//
		// PA 2017/12/29
		// build the map for n-ary function
		// function insideCall($x in D) = at({1 -> "eee"}, $x)
		// function insideCall($x in D, $y in D2) = at({(1,2) -> "eee"}, ($x,$y))
		Map<Function, Map<String, String>> monitoredInit = new HashMap<>();
		for (Set set : this.builder.monitoredInitState) {
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
			LOG.debug("function " + funcName + (functions.stream().anyMatch(t -> t.getName().equals(funcName)) ? " found" :  " not found"));
			// get the signature if there is one
			Optional<Function> func = functions.stream().filter(x -> x.getName().equals(funcName)).findFirst();
			if (func.isEmpty()) continue;
			// only if the the function is declared in this asm
			/*//AG 5/2021: questo adesso lo ignoro, non guardo le ASM, potrei settare qualcosa che importo
			// if (Defs.getAsm(func.get())!= model) {
			if (! Defs.getAsm(func.get()).getName().equals(model.getName())) {
				continue;			
			}*/
			// PUT in the map???
			if (ido >= 0) {
				// a n-ray function
				String args = location.substring(ido); // including the (
				Map<String, String> map = monitoredInit.get(func);
				if (map == null) {
					map = new HashMap<>();
					monitoredInit.put(func.get(), map);
				}
				map.put(args, set.getValue());
			} else {
				println("function " + funcName + " = " + set.getValue());
			}
		}
		// print the map for functions A -> B
		for (Entry<Function, Map<String, String>> e : monitoredInit.entrySet()) {
			Function func = e.getKey();
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
		java.util.Set<String> allLocations = this.builder.monitoredInitState.stream().map(x -> x.getLocation())
				.collect(Collectors.toSet());
		// check if this function is already defined in the initial state of the
		// scenario
		if (allLocations.contains(init.getInitializedFunction().getName())) {
			println("// " + init.getInitializedFunction().getName() + " is initialized also in the initial state - it will ignored");
			print("// ");
		}
		super.visitInit(init);
	}
}
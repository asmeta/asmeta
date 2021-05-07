package org.asmeta.xt.validator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.asmeta.avallaxt.AvallaStandaloneSetup;
import org.asmeta.avallaxt.avallaXt.Scenario;
import org.asmeta.avallaxt.avallaXt.Set;
import org.asmeta.avallaxt.validation.ScenarioUtility;
import org.asmeta.parser.ASMParser;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Injector;

import asmeta.AsmCollection;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;

/**
 * AsmPrinter that takes avalla script and produces an Asmeta Spec
 * 
 * @author garganti
 */
public class AsmetaFromAvallaBuilder {

	private static final Logger logger = Logger.getLogger(AsmetaFromAvallaBuilder.class);

	/** The scenario. */
	Scenario scenario;

	/** The new main. */
	String newMain;

	/** The old main name. */
	String oldMainName;

	/** The parent path. */
	String scenarioDirectoryPath;

	Path modelPath;// path of the model

	/**
	 * The invariants specified in the model.
	 */
	Collection<asmeta.definitions.Invariant> modelInvariants;


	ArrayList<Set> monitoredInitState;// PA: 2017/12/29

	List<ArrayList<Set>> allMonitored;// PA: 2017/12/29

	private AsmetaPrinterForAvalla asmetaPrinterforAvalla;
	
	// for the scenario itself (the AsmPrinter has another model)
	Asm asm;

	
	/**
	 * Instantiates a new asmeta from avalla  in a temporary file
	 *
	 * @param scenarioPath the scenario path
	 * @throws Exception the exception
	 */
	public AsmetaFromAvallaBuilder(String scenarioPath) throws Exception {
		this(scenarioPath, Files.createTempDirectory("asms_foravalla").toFile());
	}

	
	/**
	 * Instantiates a new builder.
	 * 
	 * @param scenarioPath the scenario path
	 * @param tempAsmPath  the complete name (including the path and the asm file)
	 *                     where to save the temporary asm
	 * 
	 * @throws Exception the exception
	 */
	public AsmetaFromAvallaBuilder(String scenarioPath, File tempAsmPathDir) throws Exception {
		//
		assert Paths.get(scenarioPath).toFile().exists();
		assert tempAsmPathDir.exists() && tempAsmPathDir.isDirectory();
		//
		scenarioDirectoryPath = new File(scenarioPath).getAbsoluteFile().getParent();
		// read the spec from file
		Injector injector = new AvallaStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
		rs.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = rs.getResource(URI.createFileURI(scenarioPath), true);
		resource.load(rs.getLoadOptions());
		scenario = (Scenario) resource.getContents().get(0);
		// get the specification loaded by the script
		modelPath = ScenarioUtility.getAsmPath(scenario);
		assert Files.exists(modelPath);
		logger.debug("build the asm from scenario " + modelPath);
		File modelFile = modelPath.toFile();
		AsmCollection pack = ASMParser.setUpReadAsm(modelFile);
		asm = pack.getMain();
		MacroDeclaration mainrule = asm.getMainrule();
		// TODO, or just add an empty main rule?
		if (mainrule == null)
			throw new RuntimeException("an asm without main cannot be validated by scenarios");
		oldMainName = mainrule.getName();
		// create a temp file in the directory
		File tempAsmPath = File.createTempFile("__tempAsmetaV", ".asm", tempAsmPathDir);
		//
		asmetaPrinterforAvalla = new AsmetaPrinterForAvalla(tempAsmPath,modelPath, this);
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
		asmetaPrinterforAvalla.visit(asm);
		asmetaPrinterforAvalla.close();
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

	/** return the path where the asm has been saved */
	public File getTempAsmPath() {
		return asmetaPrinterforAvalla.tempAsmPath;
	}

}

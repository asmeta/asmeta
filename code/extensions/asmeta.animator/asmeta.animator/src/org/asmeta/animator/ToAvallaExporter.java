package org.asmeta.animator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.asmeta.parser.AsmetaParserUtility;
import org.eclipse.swt.widgets.TableItem;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.definitions.domains.StringDomain;
import asmeta.structure.Asm;

public class ToAvallaExporter {

	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	//df.setTimeZone(TimeZone.getTimeZone("UTC"));
	
	private PrintWriter out;
	private File scenarioFile;
	private AsmCollection asm;
	
	ToAvallaExporter(File specFolder, AsmCollection asm) throws FileNotFoundException{	
		this.asm = asm;
		// create the file
		String fileName = "scenario_from_animation_";
		int i = 0;
		do {
			scenarioFile = new File(specFolder.toString() + File.separator + fileName + i++ + ".avalla");
		} while (scenarioFile.exists());
		out = new PrintWriter(scenarioFile);
	}
	
	String exportToAvalla(TableItem[] states_down, 
			TableItem[] functions_down, TableItem[] states_up, TableItem[] functions_up, int colCount){
		print("//// starting scenario");
		print("//// created from the animator on " + df.format(new Date()));
		print("scenario " + "SCENARIO_NAME");
		String asmname = asm.getMain().getName();
		print("load " + asmname + AsmetaParserUtility.ASM_EXTENSION);
		// get the states in items
		for (int column = 0; column < colCount; column++) {
			// all the controlled and then monitored
			String[] functionTypes = { VisualizationSimulation.CONTROLLED, VisualizationSimulation.MONITORED };
			for (String functionT : functionTypes) {
				addStateToAvalla(states_down, functions_down, column, functionT);
				addStateToAvalla(states_up, functions_up, column, functionT);
			}
			// new step
			if (column < colCount - 1)
				print("step");
		}
		out.close();
		return scenarioFile.getAbsolutePath();
	}

	/**
	 * @param states_down
	 * @param functions_down
	 * @param column
	 * @param functionT
	 */
	private void addStateToAvalla(TableItem[] states_down, TableItem[] functions_down, int column, String functionT) {
		for (int i = 0; i < states_down.length; i++) {
			// get the value of i-th state
			String text = states_down[i].getText(column);
			if (text.length() > 0) {
				// get function name
				TableItem left = functions_down[i];
				String functionName = left.getText(2);
				// function type (C for controlled and so on)
				String functionType = left.getText(1);
				if (!functionType.equals(functionT))
					continue;
				// if the type is a string add the quotes
				// get the functions of this ASM
				Collection<Function> functions = new ArrayList<>();
				for (Asm a : this.asm) {
					functions.addAll(a.getHeaderSection().getSignature().getFunction());
				}
				// add the quotes AG 04-2022
				Function function = org.asmeta.parser.AsmetaParserUtility.search_funcName(functions, functionName);
				if (function != null && function.getCodomain() instanceof StringDomain) {
					text = "\"" + text + "\"";
				}
				// print
				if (functionType.equals(VisualizationSimulation.MONITORED))
					print("set " + functionName + " := " + text + ";");
				else
					print("check " + functionName + " = " + text + ";");
			}
		}
	}
	
	private void print(String msg) {
		out.println(msg);
	}
}

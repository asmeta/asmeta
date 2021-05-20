package org.asmeta.runtime_commander;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.asmeta.runtime_container.SimulationContainer;
import org.asmeta.simulationUI.CompositionContainer;
import org.asmeta.simulationUI.CompositionSizeOutOfBoundException;
import org.asmeta.simulationUI.CompositionType;
import org.asmeta.simulationUI.EmptyCompositionListException;

/**
 * @author Federico Rebucini
 * Command line interface main class
 */
public class Commander {
	private static final int DEFAULT_VALUE = -10;	// default value to check if user has inserted integer values
	private static final String CONFIG_FILE_PATH = "src/org/asmeta/runtime_commander/.config"; 
	public static boolean debugMode = true;		// default value for the debug mode is false
	public static CommanderOutput out = new CommanderOutput(CommanderStatus.FAILURE, "Nothing initialized");
	public static SimulationContainer containerInstance;
	public static CompositionContainer compContainer;
	private static String defaultModelDir;
	private static boolean initConfigRequired = true;
	
	// Argument patterns
	private static final Pattern N_PATTERN = Pattern.compile("(-n\\s+\\d+)");
	private static final Pattern MODELPATH_PATTERN = Pattern.compile("(-modelpath\\s+\"[^\"\\n]+\")");
	private static final Pattern ID_PATTERN = Pattern.compile("(-id\\s+\\d+)");
	private static final Pattern MAX_PATTERN = Pattern.compile("(-max\\s+\\d+)");
	private static final Pattern TIMEOUT_PATTERN = Pattern.compile("(-t\\s+\\d+)");
	private static final Pattern LOCATION_VALUE_PATTERN = Pattern.compile("(-locationvalue\\s+\\{[^\\n\\}]+})");
	private static final Pattern INV_PATTERN = Pattern.compile("(-inv\\s+\"[^\"\\n]+\")");
	private static final Pattern INV_OLD_PATTERN = Pattern.compile("(-invold\\s+\"[^\"\\n]+\")");
	private static final Pattern CONFIG_DEFAULT_MODELS_DIR_PATTERN = Pattern.compile("(-dir\\s+\"[^\"\\\\\\n]+\")");
								
	//support function to parse numbers 
	private static int parseNumber(Matcher m, String mod) {
		int number = DEFAULT_VALUE;
		if (m.find()) {
			try {
				number=Integer.parseInt(m.group(1).substring(m.group(1).indexOf(' ')+1).trim());
			}catch (NumberFormatException e) {
				if (debugMode)
					System.out.println("NumberFormatException while parsing " + mod);
			}
			//System.out.println(m.group(1)); 
			if (debugMode)
				System.out.println("found " + mod + " parameter with value: " + number);
		}else {
			if (debugMode)
				System.out.println(mod + " parameter not found.");
		}
			
		return number;
	}
	
	//support function to parse strings 
	private static String parseText(Matcher m, String mod) {
		String str = null;
		if (m.find()) {
			str=m.group(1).substring(m.group(1).indexOf('"')+1,m.group(1).length()-1);
			//System.out.println(m.group(1)); 
			if (debugMode)
				System.out.println("found "+mod+" parameter with value: "+str);
		}else
			if (debugMode)
				System.out.println(mod+" parameter not found.");
		return str;
	}
	
	private static Map<String, String> parseLocationValue(Matcher m) {
		Map<String, String> locationvaluep = null;
		if(m.find()) {
			//System.out.println(m.group(1));
			locationvaluep = new HashMap<String, String>();
			Scanner scan = new Scanner(m.group(1).substring(m.group(1).indexOf('{')+1,m.group(1).length()-1));
			scan.useDelimiter(",");
			int count=0;
			while (scan.hasNext()) {
				count++;
				String[] supp=scan.next().split("=");
				if (supp.length>2) {
					out = new CommanderOutput(CommanderStatus.FAILURE, "wrong input format found inside locationvalue subparameter: "+String.join("=", supp));
				}
				else if (supp.length==2)
					locationvaluep.put(supp[0].trim(),supp[1].trim());
				else {
					out = new CommanderOutput(CommanderStatus.FAILURE, "nothing found inside locationvalue subparameter number "+count);
				}
			}
			scan.close();
		} else {
			if (debugMode)
				System.out.println("locationvalue parameter not found.");
		}
		
		return locationvaluep;
	}
	
	 /**
	 * Reads the operation and executes it.
	 *
	 * @param crt SimulationContainer instance
	 * @param input string with the command and parameter to parse
	 * @return object representing the result of the command executed
	 */
	public static CommanderOutput parseInput(SimulationContainer crt, String input) {
		containerInstance = crt;
		String command = "";
		if(initConfigRequired) {
			initializeConfiguration();
		}
		
		if (debugMode)
			System.out.println("Debug mode on, parsing \"" + input + "\"");

		Scanner scan = new Scanner(input);
		if (scan.hasNext()) {
			//parsing main command
			command = scan.next();
			//System.out.println("_" + command + "_");
			scan.close();
			switch (command.toUpperCase()) {
				case "INIT": 				 cmdInit(input); 				 break;
				case "STARTEXECUTION": 		 cmdStartExecution(input); 		 break;
				case "STOPEXECUTION": 		 cmdStopExecution(input); 		 break;
				case "RUNSTEP": 			 cmdRunStep(input);				 break;
				case "RUNSTEPTIMEOUT": 		 cmdRunStepTimeout(input);		 break;
				case "RUNUNTILEMPTY":		 cmdRunUntilEmpty(input);		 break;
				case "RUNUNTILEMPTYTIMEOUT": cmdRunUntilEmptyTimeout(input); break;
				case "VIEWLISTINVARIANT": 	 cmdViewListInvariant(input);	 break;
				case "ADDINVARIANT": 		 cmdAddInvariant(input);		 break;
				case "REMOVEINVARIANT": 	 cmdRemoveInvariant(input); 	 break;
				case "UPDATEINVARIANT": 	 cmdUpdateInvariant(input); 	 break;
				case "GETLOADEDIDS": 		 cmdGetLoadedIDs(); 			 break;
				case "HELP": 				 cmdHelp(); 					 break;
				case "AUTOSETUP":			 cmdAutoSetup(input);			 break;
				case "CONFIG":				 cmdConfig(input);				 break;
				default:
					out = new CommanderOutput(CommanderStatus.FAILURE, "Function \"" + command + "\" is not a correct command!");
			}
		} else 
			out = new CommanderOutput(CommanderStatus.FAILURE, "No function found!");
		
		return out;
	}
	
	private static void initializeConfiguration() {
		try {
			File configFile = new File(CONFIG_FILE_PATH);
			Scanner reader = new Scanner(configFile);
			while(reader.hasNextLine()) {
				String data = reader.nextLine();
				if(data.startsWith("defaultModelDir=")) {
					cmdConfig("-dir " + data.substring(16));
					initConfigRequired = false;
				} 
			}
			reader.close();
		} catch(Exception e) {
			System.err.println("Error while reading the configuration file!");
		}
	}

	private static void cmdConfig(String argument) {
		Matcher matcher = CONFIG_DEFAULT_MODELS_DIR_PATTERN.matcher(argument);
		String defaultDirp = parseText(matcher, "config-dir");
		
		if(defaultDirp != null) {
			File defaultDir = new File(defaultDirp);
			if(!defaultDir.exists()) {
				out = new CommanderOutput(CommanderStatus.FAILURE, defaultDirp + " does not exist!");
			} else if(!defaultDir.isDirectory()) {
				out = new CommanderOutput(CommanderStatus.FAILURE, defaultDirp + " is not a directory!");
			} else {
				defaultModelDir = defaultDirp;
				System.out.println("Default models directory: " + defaultModelDir);
				setProperty("defaultModelDir", "\"" + defaultModelDir + "\"");
				out = new CommanderOutput(CommanderStatus.FAILURE, "");
			}
		} else {
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'dir' !");
		}
	}
	
	// syntax:	autosetup model1.asm model2.asm ... 	-> init all + start all
	//			autosetup model1.asm | model2.asm | ... -> init all + compose (PIPE) in order
	//			autosetup model1.asm <|> model2.asm		-> init all (must be only 2) + compose (BID_PIPE)
	// model1.asm, model2.asm, ... have to be in the defaultModelDir folder.
	private static void cmdAutoSetup(String argument) {
		String msg = "";
		ArrayList<String> params = new ArrayList<>();
		//System.out.println(argument);
		String[] tokens;
		if(argument.trim().contains("|")) {
			if(argument.trim().contains("<|>")) { // BID_PIPE
				tokens = argument.split("\\s*<\\|>\\s*");
				if(tokens.length != 2) {
					out = new CommanderOutput(CommanderStatus.FAILURE, "Bidirectional pipe composition requires only two models!");
					return;
				}
				tokens = argument.split("\\s*<\\|>\\s*");
				for(String token: tokens) {
					if(token.toUpperCase().contains("AUTOSETUP")) {
						token = token.substring(9, token.length()).trim();
					}
					if(!token.toUpperCase().equals("AUTOSETUP")) {
						if(token.contains(".asm")) {
							params.add(token);
						} else {
							out = new CommanderOutput(CommanderStatus.FAILURE, "Invalid model extension!");
							return;
						}
					}
				}
				cmdInit("-n " + Integer.toString(params.size()));
				for(String param: params) {
					cmdStartExecution("-modelpath \"" + defaultModelDir + "/" + param + "\"");
				}
				compContainer = new CompositionContainer(containerInstance, CompositionType.BID_PIPE);
				for(int i = 0; i < params.size() - 1; i++) {
					compContainer.addComposition(i + 1, i + 2);
				}
			} else { // PIPE
				tokens = argument.split("\\s*\\|\\s*");
				for(String token: tokens) {
					if(token.toUpperCase().contains("AUTOSETUP")) {
						token = token.substring(9, token.length()).trim();
					}
					if(!token.toUpperCase().equals("AUTOSETUP")) {
						if(token.contains(".asm")) {
							params.add(token);
						} else {
							out = new CommanderOutput(CommanderStatus.FAILURE, "Invalid model extension!");
							return;
						}
					}
				}
				cmdInit("-n " + Integer.toString(params.size()));
				for(String param: params) {
					cmdStartExecution("-modelpath \"" + defaultModelDir + "/" + param + "\"");
				}
				compContainer = new CompositionContainer(containerInstance, CompositionType.PIPE);
				for(int i = 0; i < params.size() - 1; i++) {
					compContainer.addComposition(i + 1, i + 2);
				}
			}
		} else { // No composition
			tokens = argument.split(" ");
			for(String token: tokens) {
				if(!token.toUpperCase().equals("AUTOSETUP")) {
					if(token.contains(".asm")) {
						params.add(token);
					} else {
						out = new CommanderOutput(CommanderStatus.FAILURE, "Invalid model extension!");
						return;
					}
				}
			}
			cmdInit("-n " + Integer.toString(params.size()));
			for(String param: params) {
				cmdStartExecution("-modelpath \"" + defaultModelDir + "/" + param + "\"");
			}
		}
		System.out.println(params.toString());
		System.out.println("Autosetup successful, type 'help' for more commands!\nLoaded simulations:");
		cmdGetLoadedIDs();
	}
	
	private static void cmdInit(String argument) {
		//parsing N
		Matcher matcher = N_PATTERN.matcher(argument);
		int np = parseNumber(matcher, "n");
		
		if (np != DEFAULT_VALUE)
			out = new CommanderOutput(CommanderStatus.INSTANCES, containerInstance.init(np));
		else
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'n' !");
	}
	
	private static void cmdStartExecution(String argument) {
		//parsing MODELPATH
		Matcher matcher = MODELPATH_PATTERN.matcher(argument);
		String modelpathp = parseText(matcher, "modelpath");
		
		if (modelpathp != null)
			out = new CommanderOutput(CommanderStatus.SIM_ID, containerInstance.startExecution(modelpathp));
		else 
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'modelpath' !");
	}

	private static void cmdStopExecution(String argument) {
		//parsing ID
		Matcher matcher = ID_PATTERN.matcher(argument);
		int idp = parseNumber(matcher, "id");
		
		if (idp!=DEFAULT_VALUE)
			out=new CommanderOutput(CommanderStatus.STOP, containerInstance.stopExecution(idp));
		else 
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'id' !");
	}
	
	private static void cmdRunStep(String argument) {
		//parsing ID
		Matcher matcher = ID_PATTERN.matcher(argument);
		int idp = parseNumber(matcher, "id");
		
		//parsing LOCATIONVALUE
		matcher = LOCATION_VALUE_PATTERN.matcher(argument);
		Map<String, String> locationvaluep = parseLocationValue(matcher);
		
		if (idp != DEFAULT_VALUE)
			if (locationvaluep != null) {
				out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runStep(idp, locationvaluep));
				if(compContainer != null) {
					try {
						compContainer.runStep(out.getRunOutput(), false);
						out = new CommanderOutput(CommanderStatus.FAILURE, "");
					} catch (EmptyCompositionListException e) {
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, empty composition list!");
					} catch (CompositionSizeOutOfBoundException e) {
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, composition list out of bounds!");
					} catch (CommanderException e) {
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, commander error!");
					}
				}
			}
			else
				out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runStep(idp));
		else 
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'id' !");
	}
	
	private static void cmdRunStepTimeout(String argument) {
		//parsing ID
		Matcher matcher = ID_PATTERN.matcher(argument);
		int idp = parseNumber(matcher, "id");
		
		//parsing TIMEOUT
		matcher = TIMEOUT_PATTERN.matcher(argument);
		int timeoutp = parseNumber(matcher, "timeout");
		
		//parsing LOCATIONVALUE
		matcher = LOCATION_VALUE_PATTERN.matcher(argument);
		Map<String, String> locationvaluep = parseLocationValue(matcher);
		
		if (idp != DEFAULT_VALUE && timeoutp != DEFAULT_VALUE)
			if (locationvaluep != null)
				out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runStepTimeout(idp, locationvaluep, timeoutp));
			else
				out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runStepTimeout(idp, timeoutp));
		else { 
			if (idp == DEFAULT_VALUE) 
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'id' !");
			if (timeoutp == DEFAULT_VALUE) 
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'timeout' !");
		}
	}
	
	private static void cmdRunUntilEmpty(String argument) {
		//parsing ID
		Matcher matcher = ID_PATTERN.matcher(argument);
		int idp = parseNumber(matcher, "id");
		
		//parsing MAX
		matcher = MAX_PATTERN.matcher(argument);
		int maxp = parseNumber(matcher, "max");
		
		//parsing LOCATIONVALUE
		matcher = LOCATION_VALUE_PATTERN.matcher(argument);
		Map<String, String> locationvaluep = parseLocationValue(matcher);
		
		if (idp != DEFAULT_VALUE) {
			if (maxp != DEFAULT_VALUE)
				if (locationvaluep != null)
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmpty(idp, locationvaluep, maxp));
				else
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmpty(idp, maxp));
			else
				if (locationvaluep != null)
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmpty(idp, locationvaluep));
				else
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmpty(idp));
		} else {
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'id' !");
		}
	}
	
	private static void cmdRunUntilEmptyTimeout(String argument) {
		//parsing ID
		Matcher matcher = ID_PATTERN.matcher(argument);
		int idp = parseNumber(matcher, "id");
		
		//parsing MAX
		matcher = MAX_PATTERN.matcher(argument);
		int maxp = parseNumber(matcher, "max");
		
		//parsing TIMEOUT
		matcher = TIMEOUT_PATTERN.matcher(argument);
		int timeoutp = parseNumber(matcher, "timeout");
		
		//parsing LOCATIONVALUE
		matcher = LOCATION_VALUE_PATTERN.matcher(argument);
		Map<String, String> locationvaluep = parseLocationValue(matcher);
		
		if (idp != DEFAULT_VALUE && timeoutp != DEFAULT_VALUE)
			if (maxp != DEFAULT_VALUE)
				if (locationvaluep != null)
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmptyTimeout(idp, locationvaluep, maxp, timeoutp));
				else
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmptyTimeout(idp, maxp, timeoutp));
			else
				if (locationvaluep != null)
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmptyTimeout(idp, locationvaluep, timeoutp));
				else
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmptyTimeout(idp, timeoutp));
		else { 
			if (idp == DEFAULT_VALUE)
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'id' !");
			if (timeoutp == DEFAULT_VALUE) 
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'timeout' !");
		}
	}
	
	private static void cmdViewListInvariant(String argument) {
		//parsing ID
		Matcher matcher = ID_PATTERN.matcher(argument);
		int idp = parseNumber(matcher, "id");
		
		if (idp != DEFAULT_VALUE)
			out = new CommanderOutput(CommanderStatus.VIEWINV, containerInstance.viewListInvariant(idp));
		else 
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'id' !");
	}
	
	private static void cmdAddInvariant(String argument) {
		//parsing ID
		Matcher matcher = ID_PATTERN.matcher(argument);
		int idp = parseNumber(matcher, "id");
		
		//parsing INVARIANT
		matcher = INV_PATTERN.matcher(argument);
		String invariantp = parseText(matcher, "invariant");
		
		if (idp != DEFAULT_VALUE && invariantp != null) 
			out = new CommanderOutput(CommanderStatus.BOOLRES, containerInstance.addInvariant(idp, invariantp) > 0);
		else if (invariantp != null)
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'id' !");
		else if (idp != DEFAULT_VALUE)
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'invariant' !");
		else
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing multiple required parameters!");
	}
	
	private static void cmdRemoveInvariant(String argument) {
		//parsing ID
		Matcher matcher = ID_PATTERN.matcher(argument);
		int idp = parseNumber(matcher, "id");
		
		//parsing INVARIANT
		matcher = INV_PATTERN.matcher(argument);
		String invariantp = parseText(matcher, "invariant");
		
		if (idp != DEFAULT_VALUE && invariantp != null) 
			out = new CommanderOutput(CommanderStatus.BOOLRES, containerInstance.removeInvariant(idp, invariantp));
		else if (invariantp != null)
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'id' !");
		else if (idp != DEFAULT_VALUE)
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'invariant' !");
		else
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing multiple required parameters!");
	}
	
	private static void cmdUpdateInvariant(String argument) {
		//parsing ID
		Matcher matcher = ID_PATTERN.matcher(argument);
		int idp = parseNumber(matcher, "id");
		
		//parsing INVARIANT (INV)
		matcher = INV_PATTERN.matcher(argument);
		String invariantp = parseText(matcher, "invariant");
		
		//parsing INVARIANT2 (INV_OLD)
		matcher = INV_OLD_PATTERN.matcher(argument);
		String invariant2p = parseText(matcher, "invariant2");
		
		if (idp != DEFAULT_VALUE && invariantp != null && invariantp != null) 
			out = new CommanderOutput(CommanderStatus.BOOLRES, containerInstance.updateInvariant(idp, invariantp, invariant2p)>0);
		else if (invariantp != null && invariant2p != null)
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'id' !");
		else if (idp != DEFAULT_VALUE)
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'invariant' !");
		else
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing multiple required parameters!");
	}
	
	private static void cmdGetLoadedIDs() {
		Map<Integer, String> loadedIDs = containerInstance.getLoadedIDs();
		for(int i = 0; i < loadedIDs.size(); i++) {
			System.out.println("ID: " + loadedIDs.keySet().toArray()[i] + " | Model path: " + loadedIDs.get(i + 1));
		}
		out = new CommanderOutput(CommanderStatus.FAILURE, "");
	}
	
	private static void cmdHelp() {
		//INIT
		System.out.println("INIT\t\t\tInitializes the maximum simulation instances.");
		System.out.println("\t\t\t\tParameter: -n <simulations number>");
		
		//STARTEXECUTION
		System.out.println("STARTEXECUTION\t\tStarts a model simulation execution.");
		System.out.println("\t\t\t\tParameter: -modelpath <model file path>");
		
		//STOPEXECUTION
		System.out.println("STOPEXECUTION\t\tStops a model simulation execution.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		
		//RUNSTEP
		System.out.println("RUNSTEP\t\t\tExecutes one step on a model simulation.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		System.out.println("\t\t\t\tParameter: [-locationvalue <Hash map with monitored variables and their value>]");
		
		//RUNSTEPTIMEOUT
		System.out.println("RUNSTEPTIMEOUT\t\tExecutes one step on a model simulation with a given timeout.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		System.out.println("\t\t\t\tParameter: [-locationvalue <Hash map with monitored variables and their value>]");
		System.out.println("\t\t\t\tParameter: -t <timeout in milliseconds>");
		
		//RUNUNTILEMPTY
		System.out.println("RUNUNTILEMPTY\t\tExecutes the simulation until the main rule is empty.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		System.out.println("\t\t\t\tParameter: [-locationvalue <Hash map with monitored variables and their value>]");
		System.out.println("\t\t\t\tParameter: [-max <max steps to take>]");
		
		//RUNUNTILEMPTYTIMEOUT
		System.out.println("RUNUNTILEMPTYTIMEOUT\tExecutes the simulation until the main rule is empty with a given timeout.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		System.out.println("\t\t\t\tParameter: [-locationvalue <Hash map with monitored variables and their value>]");
		System.out.println("\t\t\t\tParameter: [-max <max steps to take>]");
		System.out.println("\t\t\t\tParameter: -t <timeout in milliseconds>");
		
		//VIEWLISTINVARIANT
		System.out.println("VIEWLISTINVARIANT\tShows a list with all the variables and invariants in the model.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		
		//ADDINVARIANT
		System.out.println("ADDINVARIANT\t\tAdds another invariant to the model.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		System.out.println("\t\t\t\tParameter: -inv <new invariant>");
		
		//REMOVEINVARIANT
		System.out.println("REMOVEINVARIANT\t\tRemoves the given invariant from the model.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		System.out.println("\t\t\t\tParameter: -inv <removed invariant>");
		
		//UPDATEINVARIANT
		System.out.println("UPDATEINVARIANT\t\tUpdates the given invariant from the model.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		System.out.println("\t\t\t\tParameter: -inv <updated invariant>");
		System.out.println("\t\t\t\tParameter: -invold <old invariant>");
		
		//GETLOADEDIDS
		System.out.println("GETLOADEDIDS\t\tShows all the running simulations' ID.");
		System.out.println("\nParameter examples:\t-n 5");
		System.out.println("\t\t\t-id 1");
		System.out.println("\t\t\t-modelpath \"C:/Users/...\"");
		System.out.println("\t\t\t-locationvalue {monitored1=value1,monitored2=value2,...}");
		System.out.println("\t\t\t-t 1000");
		System.out.println("\t\t\t-max 50");
		System.out.println("\t\t\t-inv \"invariant inv_name over ...\"");
		
		out = new CommanderOutput(CommanderStatus.FAILURE, "");
	}
	
	/**
	 * Change a property value in the '.properties' file.
	 * @param propertyName: name of the property in '.properties' file.
	 * @param value: new property value.
	 */
	public static void setProperty(String propertyName, String value) {
		try {
			File propertiesFile = new File(CONFIG_FILE_PATH);
			ArrayList<String> fileContent = new ArrayList<>();
			String line = "";
			BufferedReader reader = new BufferedReader(new FileReader(propertiesFile));
			while((line = reader.readLine()) != null) {
				fileContent.add(line);
			}
			FileWriter writer = new FileWriter(propertiesFile);
			for(String property: fileContent) {
				property = property.replaceAll(("^" + propertyName + "=.+?$"), (propertyName + "=" + value));
				writer.write(property);
				writer.write(System.lineSeparator());
			}
			reader.close();
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

package org.asmeta.runtime_commander;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.asmeta.parser.ASMParser;
import org.asmeta.runtime_container.Esit;
import org.asmeta.runtime_container.SimulationContainer;
import org.asmeta.simulationUI.CompositionContainer;
import org.asmeta.simulationUI.CompositionSizeOutOfBoundException;
import org.asmeta.simulationUI.CompositionType;
import org.asmeta.simulationUI.EmptyCompositionListException;

import asmeta.AsmCollection;
import asmeta.definitions.Function;
import asmeta.structure.Initialization;
import asmeta.terms.basicterms.impl.BooleanTermImpl;

/**
 * @author Federico Rebucini
 * Command line interface main class
 */
public class Commander {
	private static final int DEFAULT_VALUE = -10;																// Default value to check if user has inserted integer values
	private static final String CONFIG_FILE_PATH = "src/org/asmeta/runtime_commander/.config"; 					// Configuration file path
	public static boolean debugMode = false;																	// Default value for the debug mode is false
	public static CommanderOutput out = new CommanderOutput(CommanderStatus.FAILURE, "Nothing initialized");	// CommanderOutput initialization
	public static SimulationContainer containerInstance;														// SimulationContainer declaration 
	public static CompositionContainer compContainer;															// CompositionContainer declaration
	private static String defaultModelDir;																		// defaultModelDir config file property
	private static boolean initConfigRequired = true;															// Configuration required on startup flag
	public static String lastInput = null;																		// Last executed input
	public static String prompt;																				// Command line prompt text
	
	// Argument regular expressions
	private static final String RUNSTEP_REGEX = "\\s*RUNSTEP\\(\\s*([^,]+)\\s*,?\\s*(\\{(\\p{Alnum}+\\s*=\\s*\\p{Alnum}+,?)*\\})?\\)";
	
	// Argument regex patterns
	private static final Pattern N_PATTERN = Pattern.compile("(-n\\s+\\d+)");
	private static final Pattern MODELPATH_PATTERN = Pattern.compile("(-modelpath\\s+\"[^\"\\n]+\")");
	private static final Pattern ID_PATTERN = Pattern.compile("(-id\\s+\\d+)");
	private static final Pattern MAX_PATTERN = Pattern.compile("(-max\\s+\\d+)");
	private static final Pattern TIMEOUT_PATTERN = Pattern.compile("(-t\\s+\\d+)");
	private static final Pattern LOCATION_VALUE_PATTERN = Pattern.compile("(-locationvalue\\s+\\{[^\\n\\}]+})");
	private static final Pattern INV_PATTERN = Pattern.compile("(-inv\\s+\"[^\"\\n]+\")");
	private static final Pattern INV_OLD_PATTERN = Pattern.compile("(-invold\\s+\"[^\"\\n]+\")");
	private static final Pattern CONFIG_DEFAULT_MODELS_DIR_PATTERN = Pattern.compile("(-dir\\s+\"[^\"\\\\\\n]+\")");
	private static final Pattern CONFIG_PROMPT_PATTERN = Pattern.compile("(-prompt\\s+\"[^\"\\n]+\")");
	private static final Pattern IF_THEN_PATTERN = Pattern.compile("\\s*IF\\s+(\\p{Alnum}+)\\s+THEN\\s+(\\p{Alnum}+.asm|" + RUNSTEP_REGEX + ")");
	private static final Pattern IF_THEN_ELSE_PATTERN = Pattern.compile("\\s*IF\\s+(\\p{Alnum}+)\\s+THEN\\s+(\\p{Alnum}+.asm|" + RUNSTEP_REGEX + ")\\s+ELSE\\s+(\\p{Alnum}+.asm|" + RUNSTEP_REGEX + ")");
	private static final Pattern WHILE_DO_PATTERN = Pattern.compile("\\s*WHILE\\s+(\\p{Alnum}+)\\s+DO\\s+(" + RUNSTEP_REGEX + ")");
	
	
	
	// Support function to parse numbers 
	private static int parseNumber(Matcher m, String mod) {
		int number = DEFAULT_VALUE;
		if (m.find()) {
			try {
				number=Integer.parseInt(m.group(1).substring(m.group(1).indexOf(' ')+1).trim());
			}catch (NumberFormatException e) {
				if (debugMode)
					System.out.println("NumberFormatException while parsing " + mod);
			}
			if (debugMode)
				System.out.println("found " + mod + " parameter with value: " + number);
		}else {
			if (debugMode)
				System.out.println(mod + " parameter not found.");
		}
			
		return number;
	}
	
	// Support function to parse strings 
	private static String parseText(Matcher m, String mod) {
		String str = null;
		if (m.find()) {
			str=m.group(1).substring(m.group(1).indexOf('"')+1,m.group(1).length()-1);
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
			locationvaluep = new HashMap<String, String>();
			Scanner scan = new Scanner(m.group(1).substring(m.group(1).indexOf('{')+1,m.group(1).length()-1));
			scan.useDelimiter(",");
			int count=0;
			while (scan.hasNext()) {
				count++;
				String[] supp=scan.next().split("=");
				if (supp.length>2) {
					out = new CommanderOutput(CommanderStatus.FAILURE, "Wrong input format found inside 'locationvalue' subparameter: "+String.join("=", supp));
				}
				else if (supp.length==2)
					locationvaluep.put(supp[0].trim(),supp[1].trim());
				else {
					out = new CommanderOutput(CommanderStatus.FAILURE, "Nothing found inside 'locationvalue' subparameter number "+count);
				}
			}
			scan.close();
		} else {
			if (debugMode)
				System.out.println("'locationvalue' parameter not found!");
		}
		return locationvaluep;
	}
	
	private static int checkParentheses(String line) {
		int parenthesesCounter = 0;
		String symbols[] = line.split("\\s+");
		
		for(int i = 0; i < symbols.length; i++) {
			if(symbols[i].contains("(")) {
				parenthesesCounter++;
				symbols[i] = symbols[i].replaceFirst("\\(", "");
				i--;
			}
			if(symbols[i].contains(")")) {
				parenthesesCounter--;
				symbols[i] = symbols[i].replaceFirst("\\)", "");
				i--;
			}
			
		}
		return parenthesesCounter;
	}
	
	private static boolean validateModels(String line) { // TODO: check files in defaultModelDir
		boolean valid = true;
		String symbols[] = line.split("\\s+");
		for(String symbol: symbols) {
			if(!symbol.toLowerCase().equals("autosetup") && !symbol.equals("|") && !symbol.equals("||") && !symbol.equals("<|>")) {
				if(!symbol.contains(".asm")) {
					valid = false;
				}
			}
		}
		return valid;
	}
	
	private static int charCount(String word, char c) {
		if(word == null) {
			return -1;
		}
		int counter = 0;
		for(int i = 0; i < word.length(); i++) {
			if(word.charAt(i) == c) {
				counter++;
			}
		}
		return counter;
	}
	
	// Pretty print List<String[]> -> String[] dimension is 2
	public static void printList(List<String[]> list) {
		for(int i = 0; i < list.size(); i++) {
			if(i == 0) {
				System.out.print("[");
			}
			if(i == list.size() - 1) {
				System.out.print("[" + list.get(i)[0] + ", " + list.get(i)[1] + "]]\n");
			} else {
				System.out.print("[" + list.get(i)[0] + ", " + list.get(i)[1] + "], ");
			}			
		}
	}
	
	private static List<String[]> clearList(List<String[]> list) {
		if(list == null) {
			return null;
		}
		for(int i = 0; i < list.size(); i++) {
			list.get(i)[0] = list.get(i)[0].replaceAll("\\(", "");
			list.get(i)[0] = list.get(i)[0].replaceAll("\\)", "");
		}
		return list;
	}
	
	// Support function to parse complex commands. White spaces are mandatory.
	// Example: S1 | (S2 || S3) | S4
	//			(S1 <|> S2) | S3
	//			S1 | ((S2 | S3) || S4) | S5
	private static String parseComplex(String argument) {
		if(!argument.contains("(") && !argument.contains(")")) {
			if(argument.contains(" | ")) {
				if(argument.contains(" || ") || argument.contains(" <|> ")) {
					out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, operands precedence must be declared using parentheses!");
					return null;
				}
				return argument;
			} else if(argument.contains(" || ")) {
				if(argument.contains(" <|> ")) {
					out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, operands precedence must be declared using parentheses!");
					return null;
				}
				return argument;
			}
		}
		int parenthesesCounter = checkParentheses(argument);
		if(parenthesesCounter != 0) {
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command: invalid syntax (check parentheses)!");
			return null;
		}
		/*if(!validateModels(argument)) {
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid model extension!");
			return null;
		}*/
		
		// symbolsPriority structure: [["S1", "0"], ["(S2", "1"]]... -> [[symbol1, priority1], [symbol2, priority2], ..., [symbolN, priorityN]]
		argument = argument.substring(10); // remove "autosetup "
		String[] symbols = argument.split("\\s+");
		List<String[]> symbolsPriority = new ArrayList<>();
		int currentPriority = 0;
		for(int i = 0; i < symbols.length; i++) {
			currentPriority += charCount(symbols[i], '(');
			String[] symbolPriority = {symbols[i], String.valueOf(currentPriority)};
			symbolsPriority.add(symbolPriority);
			currentPriority -= charCount(symbols[i], ')');			
		}
		
		//printList(symbolsPriority);
		symbolsPriority = clearList(symbolsPriority);
		
		try {
			CommanderTreeNode<String> commanderTree = CommanderTreeNode.buildTree(symbolsPriority);
			commanderTree.printTree();
		} catch (Exception e) {
			out = new CommanderOutput(CommanderStatus.FAILURE, e.getMessage());
			return null;
		}
		
		return argument;
	}

	/**
	 * Reads the operation and executes it.
	 *
	 * @param crt: SimulationContainer instance
	 * @param input: string with the command and parameter to parse
	 * @return a CommanderOutput instance representing the result of the command executed
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
			if(!command.toUpperCase().equals("RR") && !command.toUpperCase().equals("IF") && !command.toUpperCase().equals("WHILE")) {
				lastInput = input;
			}
			switch (command.toUpperCase()) {
				case "INIT": 				 cmdInit(input); 				 break;
				case "START": 		 		 cmdStart(input); 		 		 break;
				case "STOP": 		 		 cmdStop(input); 		 		 break;
				case "RUNSTEP": 			 cmdRunStep(input);				 break;
				case "RUNSTEPTIMEOUT": 		 cmdRunStepTimeout(input);		 break;
				case "RUNUNTILEMPTY":		 cmdRunUntilEmpty(input);		 break;
				case "RUNUNTILEMPTYTIMEOUT": cmdRunUntilEmptyTimeout(input); break;
				case "VIEWINV": 	 		 cmdViewInvariants(input);	 	 break;
				case "ADDINV": 		 		 cmdAddInvariant(input);		 break;
				case "REMOVEINV": 	 		 cmdRemoveInvariant(input); 	 break;
				case "UPDATEINV": 	 		 cmdUpdateInvariant(input); 	 break;
				case "IDS": 		 		 cmdGetLoadedIDs(); 			 break;
				case "HELP": 				 cmdHelp(); 					 break;
				case "AUTOSETUP":			 cmdAutoSetup(input);			 break;
				case "CONFIG":				 cmdConfig(input);				 break;
				case "RR":					 cmdRerun();					 break;
				case "OPEN":				 cmdOpen(input);				 break;
				case "IF":					 cmdIf(input);					 break;
				case "WHILE":				 cmdWhileDo(input);				 break;
				default:
					if(command.toUpperCase().startsWith("RUNCOND(")) {
						cmdRunCond(input);
					} else if(command.toUpperCase().startsWith("RUNSTEP(")) {
						cmdRunStep(input);
					} else if(command.toUpperCase().startsWith("WHILE(")) {
						cmdWhile(input);
					} else if(command.toUpperCase().startsWith("PIPE(")) {
						cmdPipe(input);
					} else if(command.toUpperCase().startsWith("BID(")) {
						cmdBid(input);
					} else if(command.toUpperCase().startsWith("PAR(")) {
						cmdPar(input);
					} else {
						out = new CommanderOutput(CommanderStatus.FAILURE, "Function \"" + command + "\" is not a correct command!");
					}
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
				} else if(data.startsWith("prompt=")) {
					cmdConfig("-prompt " + data.substring(7));
					initConfigRequired = false;
				} else {
					cmdConfig("-prompt " + "\"> \"");
					initConfigRequired = false;
				}
			}
			reader.close();
		} catch(Exception e) {
			System.err.println("Error while reading the configuration file!");
		}
	}
	
	// Syntax: pipe(file1.asm, file2.asm, ..., fineN.asm) <-> autosetup file1.asm | file2.asm | ... | fileN.asm
	// file1.asm, file2.asm, ..., fileN.asm MUST be in the defaultModelDir
	private static void cmdPipe(String argument) {
		StringBuffer parsedCommand = new StringBuffer();
		boolean end = false;
		 
		parsedCommand.append("autosetup ");
		for(String token: argument.substring(5).split(",")) {
			if(token.contains(")")) {
				token = token.replace(")", "");
				end = true;
			}
			if(token.contains(".asm")) {
				if(!end) {
					parsedCommand.append(token + "|");
				} else {
					parsedCommand.append(token);
				}
			}
		}
		if(debugMode)
			System.out.println(parsedCommand);
		
		cmdAutoSetup(parsedCommand.toString());
	}
	
	// Syntax: bid(file1.asm, file2.asm) <-> autosetup file1.asm <|> file2.asm
	// file1.asm, file2.asm MUST be in the defaultModelDir
	private static void cmdBid(String argument) {
		StringBuffer parsedCommand = new StringBuffer();
		boolean end = false;
		
		parsedCommand.append("autosetup ");
		for(String token: argument.substring(4).split(",")) {
			if(argument.substring(4).split(",").length != 2) {
				out = new CommanderOutput(CommanderStatus.FAILURE, "Bidirectional pipe composition requires only two models!");
				return;
			}
			if(token.contains(")")) {
				token = token.replace(")", "");
				end = true;
			}
			if(token.contains(".asm")) {
				if(!end) {
					parsedCommand.append(token + "<|>");
				} else {
					parsedCommand.append(token);
				}
			}
		}
		if(debugMode)
			System.out.println(parsedCommand);
	
		cmdAutoSetup(parsedCommand.toString());
	}
	
	// Syntax: par(file1.asm, file2.asm, ..., fineN.asm) <-> autosetup file1.asm || file2.asm || ... || fileN.asm
	// file1.asm, file2.asm, ..., fileN.asm MUST be in the defaultModelDir
	private static void cmdPar(String argument) {
		StringBuffer parsedCommand = new StringBuffer();
		boolean end = false;
		
		parsedCommand.append("autosetup ");
		for(String token: argument.substring(4).split(",")) {
			if(token.contains(")")) {
				token = token.replace(")", "");
				end = true;
			}
			if(token.contains(".asm")) {
				if(!end) {
					parsedCommand.append(token + "||");
				} else {
					parsedCommand.append(token);
				}
			}
		}
		if(debugMode)
			System.out.println(parsedCommand);
		
		cmdAutoSetup(parsedCommand.toString());
	}
	
	// Syntax: WHILE(cond, RUNSTEP(S)) 		 -> WHILE cond DO RUNSTEP(S)
	//		   WHILE(cond, RUNSTEP(S, {...}) -> WHILE cond DO RUNSTEP(S, {...})
	private static void cmdWhile(String argument) {
		argument = argument.trim(); // remove spaces after ")"
		argument = argument.substring(6, argument.length() - 1);
		argument = argument.trim(); // remove spaces inside (...)
		argument = argument.replace(" ", "");
		
		String[] tokens = argument.split(",");
		if(tokens.length == 2) {
			if(tokens[1].contains(".asm") && tokens[1].toUpperCase().contains("RUNSTEP(")) {
				cmdWhileDo("WHILE " + tokens[0] + " DO " + tokens[1]);
			} else {
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid model extension!");
			}
		} else if(tokens.length == 3){
			if(tokens[1].contains(".asm") && tokens[1].toUpperCase().contains("RUNSTEP(")) {
				cmdWhileDo("WHILE " + tokens[0] + " DO " + tokens[1] + "," + tokens[2]);
			} else {
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid model extension!");
			}
		} else { 	
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid argument structure!");
		}
	}
	
	// Syntax: WHILE cond DO runstep(S) 		("cond" must be a boolean condition in the asmeta model S (file .asm))
	//		   WHILE cond DO runstep(S, {...})
	private static void cmdWhileDo(String argument) {
		argument = argument.replace("while", "WHILE");
		argument = argument.replace("do", "DO");
		argument = argument.replace("runstep", "RUNSTEP");
		
		Matcher whileMatcher = WHILE_DO_PATTERN.matcher(argument);
		String cond = null;
		String functionOnS = null;
		if(whileMatcher.find()) {
			// group(0) is the entire expression
			// group(1) is "cond" (the condition) -> must be a boolean term in S
			// group(2) is "RUNSTEP(...)"
			// group(3) is the model S
			if(whileMatcher.groupCount() < 2 || whileMatcher.groupCount() > 5) {
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid argument structure!");
				return;
			}
			cond = whileMatcher.group(1);
			functionOnS = whileMatcher.group(2);
			try {
				do {
					cmdIf("IF " + cond + " THEN " + functionOnS);
				} while(out.getStatus() == CommanderStatus.RUNOUTPUT && out.getRunOutput().getEsit() == Esit.SAFE);
			} catch (CommanderException e) {
				System.err.println(e.getMessage());
			}
		} else {
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid syntax!");
		}
	}
	
	// Syntax: runcond(cond, S1)     -> IF cond THEN S1
	// 		   runcond(cond, S1, S2) -> IF cond THEN S1 ELSE S2
	private static void cmdRunCond(String argument) {
		argument = argument.trim(); // remove spaces after ")"
		argument = argument.substring(4, argument.length() - 1);
		argument = argument.trim(); // remove spaces inside (...)
		argument = argument.replace(" ", "");
		
		String[] tokens = argument.split(",");
		if(tokens.length == 2) {
			if(tokens[1].contains(".asm")) {
				cmdIf("IF " + tokens[0] + " THEN " + tokens[1]);
			} else {
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid model extension!");
			}
		} else if(tokens.length == 3) {
			if(tokens[1].contains(".asm") && tokens[2].contains(".asm")) {
				cmdIf("IF " + tokens[0] + " THEN " + tokens[1] + " ELSE " + tokens[2]);
			} else {
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid model extension!");
			}
		} else {
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid argument structure!");
		}
	}
	
	private static void cmdIf(String argument) {
		argument = argument.replace("if", "IF");
		argument = argument.replace("else", "ELSE");
		argument = argument.replace("then", "THEN");
		argument = argument.replace("runstep", "RUNSTEP");
		
		Matcher ifThenMatcher = IF_THEN_PATTERN.matcher(argument);
		Matcher ifThenElseMatcher = IF_THEN_ELSE_PATTERN.matcher(argument);
		ArrayList<String> groups = new ArrayList<>();
		
		String cond = null;
		String functionOnS1 = null;
		String modelS1 = null;
		String functionOnS2 = null;
		String modelS2 = null;
		if(ifThenMatcher.find()) {
			if(debugMode)
				System.out.println("Match OK!");
			for(int i = 0; i <= ifThenMatcher.groupCount(); i++) {
				if(ifThenMatcher.group(i) != null) {
					groups.add(ifThenMatcher.group(i));
				}
			}
			if(groups.size() < 3 || groups.size() > 6) {
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid argument structure!");
				return;
			}
			if(ifThenElseMatcher.find()) {
				// group(0) is the entire expression
				// group(1) is "cond" (the condition) -> must be a boolean term in S1
				// group(2) is "S1"
				// group(3) is "S2"
				groups.removeAll(groups);
				for(int i = 0; i <= ifThenElseMatcher.groupCount(); i++) {
					if(ifThenElseMatcher.group(i) != null) {
						if(i > 1) {
							if(ifThenElseMatcher.group(i).contains(".asm")) {
								groups.add(ifThenElseMatcher.group(i));
							}
						} else {
							groups.add(ifThenElseMatcher.group(i));
						}
					}
				}
				if(debugMode)
					System.out.println(groups.toString());
				if(groups.size() < 4 || groups.size() > 10) {
					out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid argument structure!");
					return;
				}
				
				if(groups.size() == 4) {
					cond = groups.get(1);
					modelS1 = groups.get(2);
					modelS2 = groups.get(3);
				} else if(groups.size() == 5){
					cond = groups.get(1);
					functionOnS1 = groups.get(2);
					modelS1 = groups.get(3);
					modelS2 = groups.get(4);
				} else if(groups.size() == 6) {
					cond = groups.get(1);
					functionOnS1 = groups.get(2);
					modelS1 = groups.get(3);
					functionOnS2 = groups.get(4);
					modelS2 = groups.get(5);
				}
				if(!modelS1.contains(".asm") || !modelS2.contains(".asm")) {
					out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid model extension!");
					return;
				}
				try {
					boolean condValue = false;
					if((containerInstance != null && !containerInstance.getLoadedIDs().containsValue(defaultModelDir + "/" + modelS1)) ||
						out.getStatus() != CommanderStatus.RUNOUTPUT) {
						File modelS1File = new File(defaultModelDir + "/" + modelS1); // get the initial value of "cond" from the default initial state of "S1"
						if(modelS1File.exists() && modelS1File.isFile()) {
							AsmCollection asm = ASMParser.setUpReadAsm(modelS1File);
							for(int i = 0; i < asm.getMain().getHeaderSection().getSignature().getFunction().size(); i++) {
								Function f = asm.getMain().getHeaderSection().getSignature().getFunction().get(i);
								if(f.getName().equals(cond) && f.getCodomain().getName().equalsIgnoreCase("boolean")) {
									for(int j = 0; j < asm.getMain().getDefaultInitialState().getFunctionInitialization().size(); j++) {
										Initialization g = asm.getMain().getDefaultInitialState().getFunctionInitialization().get(j).getInitialState();
										if(g.getFunctionInitialization().get(j).getInitializedFunction().getName().equals(cond)) {
											condValue = Boolean.parseBoolean(((BooleanTermImpl) g.getFunctionInitialization().get(j).getBody()).getSymbol());
										}
									}
								}
							}
							if(condValue) {
								if(functionOnS1 != null && functionOnS1.toUpperCase().contains("RUNSTEP")) {
									cmdRunStep(functionOnS1);
								} else {
									cmdAutoSetup("autosetup " + modelS1);
								}
							} else {
								if(functionOnS2 != null && functionOnS2.toUpperCase().contains("RUNSTEP")) {
									cmdRunStep(functionOnS2);
								} else {
									cmdAutoSetup("autosetup " + modelS2);
								}
							}
						}
					} else if(out.getStatus() == CommanderStatus.RUNOUTPUT && out.getRunOutput().getEsit() == Esit.SAFE) {
						// get the boolean term "cond" from "S1" -> it has to be a controlled boolean 0-ary function
						Map <String, String> controlled = out.getRunOutput().getControlledvalues();
						if(controlled.containsKey(cond)) {
							if(controlled.get(cond).equalsIgnoreCase("true")) { // "cond" value is TRUE
								condValue = Boolean.parseBoolean(controlled.get(cond));
								assertTrue(condValue);
								if(functionOnS1 != null && functionOnS1.toUpperCase().contains("RUNSTEP")) {
									cmdRunStep(functionOnS1);
								} else {
									cmdAutoSetup("autosetup " + modelS1);
								}
							} else if(controlled.get(cond).equalsIgnoreCase("false")) { // "cond" value is FALSE
								condValue = Boolean.parseBoolean(controlled.get(cond));
								assertFalse(condValue);
								if(functionOnS2 != null && functionOnS2.toUpperCase().contains("RUNSTEP")) {
									cmdRunStep(functionOnS2);
								} else {
									cmdAutoSetup("autosetup " + modelS2);
								}
							} else {
								out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, controlled condition " + cond + " is not valid!");
								return;
							}
						} else {
							out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, controlled condition " + cond + " not found!");
							return;
						}
					} else {
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, try to autosetup the model first!");
					}
				} catch(AssertionError e) {
					out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, controlled condition " + cond + " is not valid!");
				} catch (CommanderException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// group(0) is the entire expression
				// group(1) is "cond" (the condition) -> must be a boolean term in S1
				// group(2) is "S1"
				if(groups.size() == 3) {
					cond = groups.get(1);
					modelS1 = groups.get(2);
				} else {
					cond = groups.get(1);
					functionOnS1 = groups.get(2);
					modelS1 = groups.get(3);
				}
				
				if(!modelS1.contains(".asm")) {
					out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid model extension!");
					return;
				}
				try {
					boolean condValue = false;
					if((containerInstance != null && !containerInstance.getLoadedIDs().containsValue(defaultModelDir + "/" + modelS1)) ||
						out.getStatus() != CommanderStatus.RUNOUTPUT) {
						File modelS1File = new File(defaultModelDir + "/" + modelS1); // get the initial value of "cond" from the default initial state of "S1"
						if(modelS1File.exists() && modelS1File.isFile()) {
							AsmCollection asm = ASMParser.setUpReadAsm(modelS1File);
							for(int i = 0; i < asm.getMain().getHeaderSection().getSignature().getFunction().size(); i++) {
								Function f = asm.getMain().getHeaderSection().getSignature().getFunction().get(i);
								if(f.getName().equals(cond) && f.getCodomain().getName().equalsIgnoreCase("boolean")) {
									for(int j = 0; j < asm.getMain().getDefaultInitialState().getFunctionInitialization().size(); j++) {
										Initialization g = asm.getMain().getDefaultInitialState().getFunctionInitialization().get(j).getInitialState();
										if(g.getFunctionInitialization().get(j).getInitializedFunction().getName().equals(cond)) {
											condValue = Boolean.parseBoolean(((BooleanTermImpl) g.getFunctionInitialization().get(j).getBody()).getSymbol());
										}
									}
								}
							}
							if(condValue) {
								if(functionOnS1 != null && functionOnS1.toUpperCase().contains("RUNSTEP")) {
									cmdRunStep(functionOnS1);
								} else {
									cmdAutoSetup("autosetup " + modelS1);
								}
							} else {
								out = new CommanderOutput(CommanderStatus.FAILURE, "Controlled condition: " + cond + " is false\n" + modelS1 + " cannot be executed!");
							}
						}
					} else if(out.getStatus() == CommanderStatus.RUNOUTPUT && out.getRunOutput().getEsit() == Esit.SAFE) {
						// get the boolean term "cond" from "S1" -> it has to be a controlled boolean 0-ary function
						Map <String, String> controlled = out.getRunOutput().getControlledvalues();
						if(controlled.containsKey(cond)) {
							if(controlled.get(cond).equalsIgnoreCase("true")) { // "cond" value is TRUE
								condValue = Boolean.parseBoolean(controlled.get(cond));
								assertTrue(condValue);
								if(functionOnS1.toUpperCase().contains("RUNSTEP")) {
									cmdRunStep(functionOnS1);
								} else {
									cmdAutoSetup("autosetup " + modelS1);
								}
							} else if(controlled.get(cond).equalsIgnoreCase("false")) { // "cond" value is FALSE
								condValue = Boolean.parseBoolean(controlled.get(cond));
								assertFalse(condValue);
								out = new CommanderOutput(CommanderStatus.FAILURE, "Controlled condition: " + cond + " is false\n" + modelS1 + " cannot be executed!");
							} else {
								out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, controlled condition " + cond + " is not valid!");
								return;
							}
						} else {
							out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, controlled condition " + cond + " not found!");
							return;
						}
					} else {
						// TODO: UNSAFE case?
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, try to autosetup the model first!");
					}
				} catch(AssertionError e) {
					out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, controlled condition " + cond + " is not valid!");
				} catch (CommanderException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid syntax!");
		}
	}
	
	private static void cmdOpen(String argument) {
		String[] tokens;
		ArrayList<String> fileContent = new ArrayList<>();
		if(argument.contains("/")) { // file path
			tokens = argument.split("\\s+");
			if(tokens.length > 2) {
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid arguments!");
				return;
			}
			for(String token: tokens) {
				token = token.replace("\"", "");
				if(!token.toUpperCase().equals("OPEN") && token.toLowerCase().contains(".asmsh")) {
					File runFile = new File(token);
					if(runFile.exists() && runFile.isFile()) {
						try {
							String line = "";
							BufferedReader reader;
							reader = new BufferedReader(new FileReader(runFile));
							while((line = reader.readLine()) != null) {
								fileContent.add(line);
							}
							reader.close();
						} catch(Exception e) {
							out = new CommanderOutput(CommanderStatus.FAILURE, "Error while reading the file!");
							return;
						}
					}
				}
			}
			SimulationContainer containerInstance = new SimulationContainer();
			for(String command: fileContent) {
				parseInput(containerInstance, command);
			}
		} else { // file already in defaultModelDir
			if(argument.toUpperCase().contains("OPEN ")) {
				argument = argument.toUpperCase().replace("OPEN ", "");
			}
			argument = defaultModelDir + "/" + argument.trim();
			cmdOpen(argument);
		}
		out = new CommanderOutput(CommanderStatus.SUCCESS);
	}
	
	private static void cmdRerun() {
		if(lastInput != null && containerInstance != null) {
			parseInput(containerInstance, lastInput);
		}
	}

	private static void cmdConfig(String argument) {
		Matcher matcher = CONFIG_DEFAULT_MODELS_DIR_PATTERN.matcher(argument);
		String defaultDirp = parseText(matcher, "config-dir");
		matcher = CONFIG_PROMPT_PATTERN.matcher(argument);
		String promptp = parseText(matcher, "config-prompt");
		
		if(defaultDirp != null) {
			File defaultDir = new File(defaultDirp);
			if(!defaultDir.exists()) {
				out = new CommanderOutput(CommanderStatus.FAILURE, defaultDirp + " does not exist!");
				return;
			} else if(!defaultDir.isDirectory()) {
				out = new CommanderOutput(CommanderStatus.FAILURE, defaultDirp + " is not a directory!");
				return;
			} else {
				defaultModelDir = defaultDirp;
				System.out.println("Default models directory: " + defaultModelDir);
				setProperty("defaultModelDir", "\"" + defaultModelDir + "\"");
				out = new CommanderOutput(CommanderStatus.SUCCESS);
			}
		} else if(promptp != null) {
			prompt = promptp;
			setProperty("prompt", "\"" + prompt + "\"");
			out = new CommanderOutput(CommanderStatus.BOOLRES, true);
		} else {
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing parameter 'dir' or 'prompt' !");
		}
	}
	
	/* Syntax:	autosetup model1.asm model2.asm ... 	-> init all + start all
				autosetup model1.asm | model2.asm | ... -> init all + compose (PIPE) in order
				autosetup model1.asm <|> model2.asm		-> init all (must be only 2) + compose (BID_PIPE) in order
				autosetup model1.asm || model2.asm ...	-> init all + compose (PARALLEL) in order
	   model1.asm, model2.asm, ... have to be in the defaultModelDir folder. */
	private static void cmdAutoSetup(String argument) {
		argument = parseComplex(argument);
		if(argument == null) {
			return;
		}
		ArrayList<String> params = new ArrayList<>();
		String[] tokens;
		if(argument.trim().contains("|")) {
			if(argument.trim().contains("<|>") && !argument.trim().contains("||")) { // BID_PIPE
				tokens = argument.split("\\s*<\\|>\\s*");
				if(tokens.length != 2) {
					out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, bidirectional pipe composition requires only two models!");
					return;
				}
				for(String token: tokens) {
					if(token.toUpperCase().contains("AUTOSETUP")) {
						token = token.substring(9, token.length()).trim();
					}
					if(!token.toUpperCase().equals("AUTOSETUP")) {
						if(token.contains(".asm")) {
							params.add(token);
						} else {
							out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid model extension!");
							return;
						}
					}
				}
				cmdInit("-n " + Integer.toString(params.size()));
				for(String param: params) {
					cmdStart("-modelpath \"" + defaultModelDir + "/" + param + "\"");
				}
				compContainer = new CompositionContainer(containerInstance, CompositionType.BID_PIPE);
				for(int i = 0; i < params.size() - 1; i++) {
					compContainer.addComposition(i + 1, i + 2);
				}
			} else if(!argument.trim().contains("<|>") && argument.trim().contains("||")) { // PARALLEL
				tokens = argument.split("\\s*\\|\\|\\s*");
				for(String token: tokens) {
					if(token.toUpperCase().contains("AUTOSETUP")) {
						token = token.substring(9, token.length()).trim();
					}
					if(!token.toUpperCase().equals("AUTOSETUP")) {
						if(token.contains(".asm")) {
							params.add(token);
						} else {
							out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid model extension!");
							return;
						}
					}
				}
				cmdInit("-n " + Integer.toString(params.size()));
				for(String param: params) {
					cmdStart("-modelpath \"" + defaultModelDir + "/" + param + "\"");
				}
				compContainer = new CompositionContainer(containerInstance, CompositionType.PARALLEL);
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
							out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid model extension!");
							return;
						}
					}
				}
				cmdInit("-n " + Integer.toString(params.size()));
				for(String param: params) {
					cmdStart("-modelpath \"" + defaultModelDir + "/" + param + "\"");
				}
				compContainer = new CompositionContainer(containerInstance, CompositionType.PIPE);
				for(int i = 0; i < params.size() - 1; i++) {
					compContainer.addComposition(i + 1, i + 2);
				}
			}
		} else { // No composition
			tokens = argument.split("\\s+");
			if(tokens.length <= 1) {
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid arguments!");
				return;
			}
			for(String token: tokens) {
				if(!token.toUpperCase().equals("AUTOSETUP")) {
					if(token.contains(".asm")) {
						params.add(token);
					} else {
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid model extension!");
						return;
					}
				}
			}
			cmdInit("-n " + Integer.toString(params.size()));
			for(String param: params) {
				cmdStart("-modelpath \"" + defaultModelDir + "/" + param + "\"");
			}
		}
		
		System.out.println("Autosetup finished, type 'help' for more commands!\nLoaded simulations:");
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
	
	private static void cmdStart(String argument) {
		//parsing MODELPATH
		Matcher matcher = MODELPATH_PATTERN.matcher(argument);
		String modelpathp = parseText(matcher, "modelpath");
		
		if (modelpathp != null)
			out = new CommanderOutput(CommanderStatus.SIM_ID, containerInstance.startExecution(modelpathp));
		else 
			out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'modelpath' !");
	}

	private static void cmdStop(String argument) {
		//parsing ID
		Matcher matcher = ID_PATTERN.matcher(argument);
		int idp = parseNumber(matcher, "id");
		
		if (idp != DEFAULT_VALUE)
			out = new CommanderOutput(CommanderStatus.STOP, containerInstance.stopExecution(idp));
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
		
		if(idp != DEFAULT_VALUE) {
			if(locationvaluep != null) {
				out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runStep(idp, locationvaluep));
				if(compContainer != null) {
					try {
						compContainer.runStep(out.getRunOutput(), false);
						out = new CommanderOutput(CommanderStatus.SUCCESS);
					} catch (EmptyCompositionListException e) {
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, empty composition list!");
					} catch (CompositionSizeOutOfBoundException e) {
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, composition list out of bounds!");
					} catch (CommanderException e) {
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, commander error!");
					}
				}
			} else {
				out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runStep(idp));
				if(compContainer != null) {
					try {
						compContainer.runStep(out.getRunOutput(), false);
						out = new CommanderOutput(CommanderStatus.SUCCESS);
					} catch (EmptyCompositionListException e) {
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, empty composition list!");
					} catch (CompositionSizeOutOfBoundException e) {
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, composition list out of bounds!");
					} catch (CommanderException e) {
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, commander error!");
					}
				}
			}
		} else { // Alternative syntaxes examples: RUNSTEP(1, {x=FOUR,y=TWO}) 	  <-> RUNSTEP -id 1 -locationvalue {x=FOUR,y=TWO}
				 // 							   RUNSTEP(Square.asm, {x=FOUR}) <-> RUNSTEP -id <id of Square.asm> -locationvalue {x=FOUR}
			argument = argument.replace("runstep", "RUNSTEP");
			Matcher exprMatcher = Pattern.compile(RUNSTEP_REGEX).matcher(argument);
			if(exprMatcher.find()) {
				String firstParam = exprMatcher.group(1);
				firstParam = firstParam.trim();
				if(exprMatcher.groupCount() == 1) {
					// In the example: RUNSTEP(1) <-> RUNSTEP -id 1 or RUNSTEP(Square.asm, {x=FOUR}) <-> RUNSTEP -id <id of Square.asm> -locationvalue {x=FOUR}
					// group(0) -> the entire expression
					// group(1) -> the model id -> 1
					if(firstParam.contains(".asm")) {
						if(containerInstance != null && containerInstance.getLoadedIDs().containsValue(defaultModelDir + "/" + firstParam)) {
							for(int id: containerInstance.getLoadedIDs().keySet()) {
								if(containerInstance.getLoadedIDs().get(id).equals(defaultModelDir + "/" + firstParam)) {
									cmdRunStep("runstep -id " + id);
								}
							}
						} else {
							out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, try to autosetup the model: '" + firstParam + "' first!");
						}
					} else {
						cmdRunStep("runstep -id " + firstParam);
					}
				} else if(exprMatcher.groupCount() == 3) {
					// In the example: RUNSTEP(1, {x=FOUR,y=TWO}) <-> RUNSTEP -id 1 -locationvalue {x=FOUR,y=TWO}
					// group(0) -> the entire expression
					// group(1) -> the model id -> 1
					// group(2) -> the whole location-value set -> {x=FOUR,y=TWO}
					// group(3) -> the last expression of the location-value set -> y=TWO
					if(firstParam.contains(".asm")) {
						if(containerInstance != null && containerInstance.getLoadedIDs().containsValue(defaultModelDir + "/" + firstParam)) {
							for(int id: containerInstance.getLoadedIDs().keySet()) {
								if(containerInstance.getLoadedIDs().get(id).equals(defaultModelDir + "/" + firstParam)) {
									cmdRunStep("runstep -id " + id + " -locationvalue " + exprMatcher.group(2));
								}
							}
						} else {
							out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, try to autosetup the model: '" + firstParam + "' first!");
						}
					} else {
						cmdRunStep("runstep -id " + firstParam + " -locationvalue " + exprMatcher.group(2));
					}
				} else {
					out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid syntax!");
					return;
				}
			} else {
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, invalid syntax!");
			}
		}
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
			if (maxp != DEFAULT_VALUE) {
				if (locationvaluep != null)
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmpty(idp, locationvaluep, maxp));
				else
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmpty(idp, maxp));
			} else {
				if (locationvaluep != null)
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmpty(idp, locationvaluep));
				else
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmpty(idp));
			}
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
		
		if (idp != DEFAULT_VALUE && timeoutp != DEFAULT_VALUE) {
			if (maxp != DEFAULT_VALUE) {
				if (locationvaluep != null)
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmptyTimeout(idp, locationvaluep, maxp, timeoutp));
				else
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmptyTimeout(idp, maxp, timeoutp));
			} else {
				if (locationvaluep != null)
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmptyTimeout(idp, locationvaluep, timeoutp));
				else
					out = new CommanderOutput(CommanderStatus.RUNOUTPUT, containerInstance.runUntilEmptyTimeout(idp, timeoutp));
			}
		} else { 
			if (idp == DEFAULT_VALUE)
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'id' !");
			if (timeoutp == DEFAULT_VALUE) 
				out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter 'timeout' !");
		}
	}
	
	private static void cmdViewInvariants(String argument) {
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
		System.out.println();
		out = new CommanderOutput(CommanderStatus.SUCCESS);
	}
	
	private static void cmdHelp() {
		//INIT
		System.out.println("INIT\t\t\tInitializes the maximum simulation instances.");
		System.out.println("\t\t\t\tParameter: -n <number of simulations>");
		
		//START
		System.out.println("START\t\t\tStarts a model simulation execution.");
		System.out.println("\t\t\t\tParameter: -modelpath <model file path>");
		
		//STOP
		System.out.println("STOP\t\t\tStops a model simulation execution.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		
		//RUNSTEP
		System.out.println("RUNSTEP\t\t\tExecutes one step on a model simulation.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		System.out.println("\t\t\t\tParameter: [-locationvalue <Hash map with monitored variables and their value>]");
		System.out.println("\t\t\t\tAlternative syntax: RUNSTEP(<simulation ID>[, <locationvalue>])");
		System.out.println("\t\t\t\tAlternative syntax: RUNSTEP(<asm model in the default model directory>[, <locationvalue>])");
		
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
		
		//VIEWINV
		System.out.println("VIEWINV\t\t\tShows a list with all the variables and invariants in the model.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		
		//ADDINVARIANT
		System.out.println("ADDINV\t\t\tAdds another invariant to the model.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		System.out.println("\t\t\t\tParameter: -inv <new invariant>");
		
		//REMOVEINVARIANT
		System.out.println("REMOVEINV\t\tRemoves the given invariant from the model.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		System.out.println("\t\t\t\tParameter: -inv <removed invariant>");
		
		//UPDATEINVARIANT
		System.out.println("UPDATEINV\t\tUpdates the given invariant from the model.");
		System.out.println("\t\t\t\tParameter: -id <simulation ID>");
		System.out.println("\t\t\t\tParameter: -inv <updated invariant>");
		System.out.println("\t\t\t\tParameter: -invold <old invariant>");
		
		//CONFIG
		System.out.println("CONFIG\t\t\tManage SimShell and Commander configuration file.");
		System.out.println("\t\t\t\tParameter: -dir <default asmeta models directory>");
		System.out.println("\t\t\t\tParameter: -prompt <custom command line prompt>");
				
		//AUTOSETUP
		System.out.println("AUTOSETUP\t\tAuto-setup single or multiple asmeta models in the default models directory.");
		System.out.println("\t\t\t\tArguments: <model1> [<model2> ...]\t\t\t-> No model composition");
		System.out.println("\t\t\t\tArguments: <model1> | <model2> [| <model3> ...]\t\t-> Unidirectional cascade pipe composition");
		System.out.println("\t\t\t\tArguments: <model1> <|> <model2>\t\t\t-> (Partial) Bidirectional pipe composition");
		System.out.println("\t\t\t\tArguments: <model1> || <model2> [|| <model3> ...]\t-> (Coupled) For-join composition");
		
		//OPEN
		System.out.println("OPEN\t\t\tRun a sequence of commands specified in an asmeta shell (.asmsh) file.");
		System.out.println("\t\t\t\tArgument: <asmsh file absolute path>");
		System.out.println("\t\t\t\tArgument: <asmsh file name> (when the file is already in the default models directory)");
		
		//IF - THEN / IF - THEN - ELSE
		System.out.println("IF-THEN-[ELSE]\t\tConditional selection of models in the default model directory.");
		System.out.println("\t\t\t\tSyntax: IF <condition in model1> THEN <model1>");
		System.out.println("\t\t\t\tSyntax: IF <condition in model1> THEN <model1> ELSE <model2>");
		
		//RUNCOND
		System.out.println("RUNCOND\t\t\tConditional selection of models in the default model directory.");
		System.out.println("\t\t\t\tSyntax: RUNCOND(<condition in model1>, <model1>) equivalent to:\n\t\t\t\t\tIF <condition in model1> THEN <model1>");
		System.out.println("\t\t\t\tSyntax: RUNCOND(<condition in model1>, <model1>, <model2>) equivalent to:\n\t\t\t\t\tIF <condition in model1> THEN <model1> ELSE <model2>");
		
		//WHILE - DO
		System.out.println("WHILE - DO\t\tIterative execution of a model in the default model directory.");
		System.out.println("\t\t\t\tSyntax: WHILE <condition in model1> DO RUNSTEP(<model1>[, <locationvalue>])");
		
		//WHILE
		System.out.println("WHILE\t\t\tIterative execution of a model in the default model directory.");
		System.out.println("\t\t\t\tSyntax: WHILE(<condition in model1>, RUNSTEP(<model1>[, <locationvalue>])) equivalent to:\n\t\t\t\t\tWHILE <condition in model1> DO RUNSTEP(<model1>[, <locationvalue>])");
		
		//PIPE
		System.out.println("PIPE\t\t\tSetup models in the default models directory for a unidirectional cascade pipe composition.");
		System.out.println("\t\t\t\tSyntax: PIPE(<model1>, <model2>[, <model3> ...]) equivalent to:\n\t\t\t\t\tAUTOSETUP <model1> | <model2> [| <model3> ...]");
		
		//BID
		System.out.println("PIPE\t\t\tSetup two models in the default models directory for a bidirectional pipe composition.");
		System.out.println("\t\t\t\tSyntax: BID(<model1>, <model2>) equivalent to:\n\t\t\t\t\tAUTOSETUP <model1> <|> <model2>");
				
		//PAR
		System.out.println("PAR\t\t\tSetup models in the default models directory for a (coupled) for-join execution.");
		System.out.println("\t\t\t\tSyntax: PAR(<model1>, <model2>[, <model3> ...]) equivalent to:\n\t\t\t\t\tAUTOSETUP <model1> || <model2> [|| <model3> ...]");
		
		//RERUN
		System.out.println("RR\t\t\tRe-run the previous command.");
		
		//GETLOADEDIDS
		System.out.println("GETLOADEDIDS\t\tShow all the running simulations' IDs.");
		
		//HELP
		System.out.println("HELP\t\t\tShow this help menu.");
				
		//Examples
		System.out.println("\nParameter examples:\t-n 5");
		System.out.println("\t\t\t-id 1");
		System.out.println("\t\t\t-modelpath \"C:/Users/...\"");
		System.out.println("\t\t\t-locationvalue {monitored1=value1,monitored2=value2,...}");
		System.out.println("\t\t\t-t 1000");
		System.out.println("\t\t\t-max 50");
		System.out.println("\t\t\t-inv \"invariant inv_name over ...\"");
		System.out.println("\t\t\t-dir \"C:/Users/...\"");
		System.out.println("\t\t\t-prompt \"> \"");
		
		out = new CommanderOutput(CommanderStatus.SUCCESS);
	}
	
	/**
	 * Change a property value in the '.config' file.
	 * @param propertyName: name of the property in '.config' file.
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

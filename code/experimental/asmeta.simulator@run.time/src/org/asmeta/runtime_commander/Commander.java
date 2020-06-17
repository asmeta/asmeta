package org.asmeta.runtime_commander;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.asmeta.runtime_container.SimulationContainer;

public class Commander {

	static int DEFVALUE=-10;	//default value to check if user has inserted integer values
								
	//support function to parse numbers 
	private static int parseNumber(Matcher m, boolean debugMode, String mod) {
		int number=DEFVALUE;
		if (m.find())	{
			try {
				number=Integer.parseInt(m.group(1).substring(m.group(1).indexOf(' ')+1).trim());
			}catch (NumberFormatException e) {
				if (debugMode)
					System.out.println("NumberFormatException while parsing "+mod);
			}
			//System.out.println(m.group(1)); 
			if (debugMode)
				System.out.println("found "+mod+" parameter with value: "+number);
		}else
			if (debugMode)
				System.out.println(mod+" parameter not found.");
		return number;
	}
	
	//support function to parse strings 
	private static String parseText(Matcher m, boolean debugMode, String mod) {
		String str=null;
		if (m.find())	{
			str=m.group(1).substring(m.group(1).indexOf('"')+1,m.group(1).length()-1);
			//System.out.println(m.group(1)); 
			if (debugMode)
				System.out.println("found "+mod+" parameter with value: "+str);
		}else
			if (debugMode)
				System.out.println(mod+" parameter not found.");
		return str;
	}
	
	public void parseInput(SimulationContainer crt, String input) {	//using default no debug mode
		parseInput(crt, input, false);
	}
	
	 /**
	 * Reads the operation and executes it.
	 *
	 * @param crt containerRT instance
	 * @param input string with the command and parameter to parse
	 * @param debugMode shows more information on the console
	 * @return object representing the result of the command executed
	 */
	public static CommanderOutput parseInput(SimulationContainer crt, String input, boolean debugMode) {
		Pattern p;
		Matcher m;
		String f="";
		boolean parseok=true;
		CommanderOutput out = new CommanderOutput(CommanderStatus.FAILURE, "Nothing initialized");
		//function parameters
		int idp=DEFVALUE;	
		int np=DEFVALUE;
		int maxp=DEFVALUE;
		int timeoutp=DEFVALUE;
		String modelpathp=null;
		Map<String, String> locationvaluep=null;
		String invariantp=null;
		String invariant2p=null;
		//
		
		//reset parameters
		//timeoutp=np=maxp=idp=DEFVALUE;
		//modelpathp=null;
		//locationvaluep=null;
		//
		
		//input = String.join(" ", args);
		
		if (debugMode)
			System.out.println("Debug mode on, parsing \""+input+"\"");

		Scanner scan = new Scanner (input);
		if (scan.hasNext())
		{
			f=scan.next();
			scan.close();
			//parsing ID
			p = Pattern.compile("(-id\\s+\\d+)");
			m = p.matcher(input);
			idp=parseNumber(m,debugMode,"id");
			//parsing N (for the init command)
			p = Pattern.compile("(-n\\s+\\d+)");
			m = p.matcher(input);
			np=parseNumber(m,debugMode,"n");
			//parsing MAX
			p = Pattern.compile("(-max\\s+\\d+)");
			m = p.matcher(input);
			maxp=parseNumber(m,debugMode,"max");
			//parsing TIMEOUT
			p = Pattern.compile("(-t\\s+\\d+)");
			m = p.matcher(input);
			timeoutp=parseNumber(m,debugMode,"timeout"); 
			//parsing MODELPATH
			p = Pattern.compile("(-modelpath\\s+\"[^\"\\n]+\")");
			m = p.matcher(input);
			modelpathp = parseText(m, debugMode, "modelpath");
			//parsing LOCATIONVALUE 	//(-locationvalue\s+\\{([^\n\\}]+,)*[^\n}]+})
			p = Pattern.compile("(-locationvalue\\s+\\{[^\\n\\}]+})");
			m = p.matcher(input);
			if (m.find())	{
				//System.out.println(m.group(1)); 
				locationvaluep=new HashMap<String, String>();
				scan = new Scanner(m.group(1).substring(m.group(1).indexOf('{')+1,m.group(1).length()-1));
				scan.useDelimiter(",");
				int count=0;
				while (scan.hasNext()) {
					//System.out.println(scan.next().trim());
					count++;
					String[] supp=scan.next().split("=");
					if (supp.length>2) {
						out = new CommanderOutput(CommanderStatus.FAILURE, "wrong input format found inside locationvalue subparameter: "+String.join("=", supp));
						//System.out.println("wrong input format found inside locationvalue subparameter: "+String.join("=", supp));
						parseok=false;	//nothing executes if something is wrong inside the input data
					}
					else if (supp.length==2)
						locationvaluep.put(supp[0].trim(),supp[1].trim());
					else {
						out = new CommanderOutput(CommanderStatus.FAILURE, "nothing found inside locationvalue subparameter number "+count);
						//System.out.println("nothing found inside locationvalue subparameter number "+count);
						parseok=false;	//nothing executes if something is wrong inside the input data
					}
				}
				
				if (debugMode)
					System.out.println("found locationvalue parameter with value: "+locationvaluep);
			}else
				if (debugMode)
					System.out.println("locationvalue parameter not found.");
			//parsing INVARIANT1
			p = Pattern.compile("(-inv\\s+\"[^\"\\n]+\")");
			m = p.matcher(input);
			invariantp = parseText(m, debugMode, "invariant");
			//parsing INVARIANT2
			p = Pattern.compile("(-inv2\\s+\"[^\"\\n]+\")");
			m = p.matcher(input);
			invariant2p = parseText(m, debugMode, "invariant2");
			scan.close();
			
			//
			
			//test parameters
			if (debugMode)
			{
				System.out.println("\nParameters value list:");
				System.out.println("\tID: "+idp);
				System.out.println("\tMAX: "+maxp);
				System.out.println("\tTIMEOUT: "+timeoutp);
				System.out.println("\tN: "+np);
				System.out.println("\tMODELPATH: \""+modelpathp+"\"");
				System.out.println("\tLOCATIONVALUE: "+locationvaluep);
				System.out.println("\tINVARIANT: \""+invariantp+"\"");
				System.out.println("\tINVARIANT2: \""+invariant2p+"\"\n");
			}
			//
			
			//executing function
			if (parseok) {
				switch (f.toUpperCase()) {
				case "INIT":
					if (np!=DEFVALUE)
						//System.out.println("init ok");
						out=new CommanderOutput(CommanderStatus.INSTANCES, crt.init(np));
					else
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter n ");
						//System.out.println("Couldn't launch command, required parameter n missing.");
				break;
				case "STARTEXECUTION":
					if (modelpathp!=null)
						//System.out.println("startexec ok");
						out=new CommanderOutput(CommanderStatus.SIM_ID, crt.startExecution(modelpathp));
					else 
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter modelpath");
						//System.out.println("Couldn't launch command, required parameter modelpath missing.");
				break;
				case "STOPEXECUTION":
					if (idp!=DEFVALUE)
						//System.out.println("stopexec ok");
						out=new CommanderOutput(CommanderStatus.STOP, crt.stopExecution(idp));
					else 
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter id");
						//System.out.println("Couldn't launch command, required parameter id missing.");
				break;
				case "RUNSTEP":
					if (idp!=DEFVALUE)
						if (locationvaluep!=null)
							//System.out.println("runstep ok long");
							out=new CommanderOutput(CommanderStatus.RUNOUTPUT, crt.runStep(idp, locationvaluep));
						else
							//System.out.println("runstep ok short");
							out=new CommanderOutput(CommanderStatus.RUNOUTPUT, crt.runStep(idp));
					else 
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter id");
						//System.out.println("Couldn't launch command, required parameter id missing.");
				break;
				case "RUNSTEPTIMEOUT":
					if (idp!=DEFVALUE && timeoutp!=DEFVALUE)
						if (locationvaluep!=null)
							//System.out.println("runstep ok long to");
							out=new CommanderOutput(CommanderStatus.RUNOUTPUT, crt.runStepTimeout(idp, locationvaluep, timeoutp));
						else
							//System.out.println("runstep ok short to");
							out=new CommanderOutput(CommanderStatus.RUNOUTPUT, crt.runStepTimeout(idp, timeoutp));
					else { 
						if (idp==DEFVALUE) 
							out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter id");
							//System.out.println("Couldn't launch command, required parameter id missing.");
						if (timeoutp==DEFVALUE) 
							out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter timeout");
							//System.out.println("Couldn't launch command, required parameter timeout missing.");
					}
				break;
				case "RUNUNTILEMPTY":
					if (idp!=DEFVALUE)
						if (maxp!=DEFVALUE)
							if (locationvaluep!=null)
								//System.out.println("rununtilempty ok 3");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, crt.runUntilEmpty(idp, locationvaluep, maxp));
							else
								//System.out.println("rununtilempty ok 1");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, crt.runUntilEmpty(idp, maxp));
								//System.out.println("Couldn't launch command, no combination with given parameters found.");
						else
							if (locationvaluep!=null)
								//System.out.println("rununtilempty ok 2");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, crt.runUntilEmpty(idp, locationvaluep));
							else
								//System.out.println("rununtilempty ok 4");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, crt.runUntilEmpty(idp));
						//else 
							//out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, no combination with given parameters found");
							//System.out.println("Couldn't launch command, no combination with given parameters found.");
					else 
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter id");
						//System.out.println("Couldn't launch command, required parameter id missing.");
				break;
				case "RUNUNTILEMPTYTIMEOUT":
					if (idp!=DEFVALUE && timeoutp!=DEFVALUE)
						if (maxp!=DEFVALUE)
							if (locationvaluep!=null)
								//System.out.println("rununtilemptytimeout ok 3");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, crt.runUntilEmptyTimeout(idp, locationvaluep, maxp, timeoutp));
							else
								//System.out.println("rununtilemptytimeout ok 1");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, crt.runUntilEmptyTimeout(idp, maxp, timeoutp));
							//else 
								//out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, no combination with given parameters found");
								//System.out.println("Couldn't launch command, no combination with given parameters found.");
						else
							if (locationvaluep!=null)
								//System.out.println("rununtilemptytimeout ok 2");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, crt.runUntilEmptyTimeout(idp, locationvaluep, timeoutp));
							else
								//System.out.println("rununtilemptytimeout ok 4");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, crt.runUntilEmptyTimeout(idp, timeoutp));
						//else 
							//out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, no combination with given parameters found");
							//System.out.println("Couldn't launch command, no combination with given parameters found.");
					else { 
						if (idp==DEFVALUE)
							out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter id");
							//System.out.println("Couldn't launch command, required parameter id missing.");
						if (timeoutp==DEFVALUE) 
							out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter timeout");
							//System.out.println("Couldn't launch command, required parameter timeout missing.");
					}
					break;
				case "VIEWLISTINVARIANT":
					if (idp!=DEFVALUE) {
						out=new CommanderOutput(CommanderStatus.VIEWINV, crt.viewListInvariant(idp));
					}else 
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter id");
						//System.out.println("Couldn't launch command, required parameter id missing.");
					break;
				case "ADDINVARIANT":
					if (idp!=DEFVALUE && invariantp!=null) 
						out=new CommanderOutput(CommanderStatus.BOOLRES, crt.addInvariant(idp, invariantp)>0);
					else if (invariantp!=null)
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter id");
						//System.out.println("Couldn't launch command, required parameter id missing.");
					else if (idp!=DEFVALUE)
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter invariant");
						//System.out.println("Couldn't launch command, required parameter invariant missing.");
					else
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing multiple required parameters");
						//System.out.println("Couldn't launch command, multiple required parameters missing.");
					break;
				case "REMOVEINVARIANT":
					if (idp!=DEFVALUE && invariantp!=null) 
						out=new CommanderOutput(CommanderStatus.BOOLRES, crt.removeInvariant(idp, invariantp));
					else if (invariantp!=null)
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter id");
						//System.out.println("Couldn't launch command, required parameter id missing.");
					else if (idp!=DEFVALUE)
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter invariant");
						//System.out.println("Couldn't launch command, required parameter invariant missing.");
					else
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing multiple required parameters");
						//System.out.println("Couldn't launch command, multiple required parameters missing.");
					break;
				case "UPDATEINVARIANT":
					if (idp!=DEFVALUE && invariantp!=null && invariantp!=null) 
						out=new CommanderOutput(CommanderStatus.BOOLRES, crt.updateInvariant(idp, invariantp, invariant2p)>0);
					else if (invariantp!=null && invariant2p!=null)
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter id");
						//System.out.println("Couldn't launch command, required parameter id missing.");
					else if (idp!=DEFVALUE)
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing required parameter invariant");
						//System.out.println("Couldn't launch command, required parameter invariant missing.");
					else
						out = new CommanderOutput(CommanderStatus.FAILURE, "Couldn't launch command, missing multiple required parameters");
						//System.out.println("Couldn't launch command, multiple required parameters missing.");
					break;
				case "GETLOADEDIDS":
					out = new CommanderOutput(CommanderStatus.LOADED_IDS, crt.getLoadedIDs());
					break;
				case "HELP":
					CommanderHelp.help();
					out = new CommanderOutput(CommanderStatus.FAILURE, "");
					break;
				default:
					out = new CommanderOutput(CommanderStatus.FAILURE, "Function \""+f+"\" is not a correct command");
					//System.out.println("Function \""+f+"\" is not a correct command.");
				}
			}
		}
		else 
			out = new CommanderOutput(CommanderStatus.FAILURE, "No function found");
			//System.out.println("No function found");
		return out;
	}
}

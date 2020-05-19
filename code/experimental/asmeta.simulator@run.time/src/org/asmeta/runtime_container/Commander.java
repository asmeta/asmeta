package org.asmeta.runtime_container;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commander {

	static int DEFVALUE=-10;	//default value to check if user has inserted integer values
								
	
	private static int parseNumber(Matcher m, boolean debugMode,String mod) {
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
	
	public void parseInput(ContainerRT crt, String input) {	//using default no debug mode
		parseInput(crt, input, false);
	}
	
	public static CommanderOutput parseInput(ContainerRT crt, String input, boolean debugMode) {
		Pattern p;
		Matcher m;
		String f="";
		boolean parseok=true;
		CommanderOutput out = new CommanderOutput(CommanderStatus.FAILURE, 0, null);
		//function parameters
		int idp=DEFVALUE;	
		int np=DEFVALUE;
		int maxp=DEFVALUE;
		int timeoutp=DEFVALUE;
		String modelpathp=null;
		Map<String, String> locationvaluep=null;
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
			//parsing ID
			p = Pattern.compile("(-id\\s+\\d+)");
			m = p.matcher(input);
			idp=parseNumber(m,debugMode,"id");
			//parsing N (for init)
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
			if (m.find())	{
				modelpathp=m.group(1).substring(m.group(1).indexOf('"')+1,m.group(1).length()-1);
				//System.out.println(m.group(1)); 
				if (debugMode)
					System.out.println("found modelpath parameter with value: "+modelpathp);
			}else
				if (debugMode)
					System.out.println("modelpath parameter not found.");
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
						System.out.println("wrong input format found inside locationvalue subparameter: "+String.join("=", supp));
						parseok=false;	//nothing executes if something is wrong inside the input data
					}
					else if (supp.length==2)
						locationvaluep.put(supp[0].trim(),supp[1].trim());
					else {
						System.out.println("nothing found inside locationvalue subparameter number "+count);
						parseok=false;	//nothing executes if something is wrong inside the input data
					}
				}
				
				if (debugMode)
					System.out.println("found locationvalue parameter with value: "+locationvaluep);
			}else
				if (debugMode)
					System.out.println("locationvalue parameter not found.");
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
				System.out.println("\tLOCATIONVALUE: "+locationvaluep+"\n");
			}
			//
			
			//executing function
			if (parseok) {
				switch (f) {
				case "init":
					if (np!=DEFVALUE)
						//System.out.println("init ok");
						out=new CommanderOutput(CommanderStatus.INSTANCES, crt.init(np), null);
					else
						System.out.println("Couldn't launch command, required parameter n missing.");
				break;
				case "startexecution":
					if (modelpathp!=null)
						//System.out.println("startexec ok");
						out=new CommanderOutput(CommanderStatus.SIM_ID, crt.startExecution(modelpathp), null);
					else 
						System.out.println("Couldn't launch command, required parameter modelpath missing.");
				break;
				case "stopexecution":
					if (idp!=DEFVALUE)
						//System.out.println("stopexec ok");
						out=new CommanderOutput(CommanderStatus.STOP, crt.stopExecution(idp), null);
					else 
						System.out.println("Couldn't launch command, required parameter id missing.");
				break;
				case "runstep":
					if (idp!=DEFVALUE)
						if (locationvaluep!=null && modelpathp!=null)
							//System.out.println("runstep ok long");
							out=new CommanderOutput(CommanderStatus.RUNOUTPUT, 0, crt.runStep(idp, locationvaluep, modelpathp));
						else
							//System.out.println("runstep ok short");
							out=new CommanderOutput(CommanderStatus.RUNOUTPUT, 0, crt.runStep(idp));
					else 
						System.out.println("Couldn't launch command, required parameter id missing.");
				break;
				case "runsteptimeout":
					if (idp!=DEFVALUE && timeoutp!=DEFVALUE)
						if (locationvaluep!=null && modelpathp!=null)
							//System.out.println("runstep ok long to");
							out=new CommanderOutput(CommanderStatus.RUNOUTPUT, 0, crt.runStepTimeout(idp, locationvaluep, modelpathp,timeoutp));
						else
							//System.out.println("runstep ok short to");
							out=new CommanderOutput(CommanderStatus.RUNOUTPUT, 0, crt.runStepTimeout(idp,timeoutp));
					else { 
						if (idp==DEFVALUE) 
							System.out.println("Couldn't launch command, required parameter id missing.");
						if (timeoutp==DEFVALUE) 
							System.out.println("Couldn't launch command, required parameter timeout missing.");
					}
				break;
				case "rununtilempty":
					if (idp!=DEFVALUE)
						if (maxp!=DEFVALUE)
							if (modelpathp!=null && locationvaluep!=null)
								//System.out.println("rununtilempty ok 3");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, 0, crt.runUntilEmpty(idp, locationvaluep, modelpathp, maxp));
							else if (modelpathp==null && locationvaluep==null)
								//System.out.println("rununtilempty ok 1");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, 0, crt.runUntilEmpty(idp, maxp));
							else
								System.out.println("Couldn't launch command, no combination with given parameters found.");
						else if (modelpathp!=null)
							if (locationvaluep!=null)
								//System.out.println("rununtilempty ok 2");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, 0, crt.runUntilEmpty(idp, locationvaluep, modelpathp));
							else
								//System.out.println("rununtilempty ok 4");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, 0, crt.runUntilEmpty(idp, modelpathp));
						else 
							System.out.println("Couldn't launch command, no combination with given parameters found.");
					else 
						System.out.println("Couldn't launch command, required parameter id missing.");
				break;
				case "rununtilemptytimeout":
					if (idp!=DEFVALUE && timeoutp!=DEFVALUE)
						if (maxp!=DEFVALUE)
							if (modelpathp!=null && locationvaluep!=null)
								//System.out.println("rununtilemptytimeout ok 3");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, 0, crt.runUntilEmptyTimeout(idp, locationvaluep, modelpathp, maxp, timeoutp));
							else if (modelpathp==null && locationvaluep==null)
								//System.out.println("rununtilemptytimeout ok 1");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, 0, crt.runUntilEmptyTimeout(idp, maxp, timeoutp));
							else 
								System.out.println("Couldn't launch command, no combination with given parameters found.");
						else if (modelpathp!=null)
							if (locationvaluep!=null)
								//System.out.println("rununtilemptytimeout ok 2");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, 0, crt.runUntilEmptyTimeout(idp, locationvaluep, modelpathp, timeoutp));
							else
								//System.out.println("rununtilemptytimeout ok 4");
								out=new CommanderOutput(CommanderStatus.RUNOUTPUT, 0, crt.runUntilEmptyTimeout(idp, modelpathp, timeoutp));
						else 
							System.out.println("Couldn't launch command, no combination with given parameters found.");
					else { 
						if (idp==DEFVALUE)
							System.out.println("Couldn't launch command, required parameter id missing.");
						if (timeoutp==DEFVALUE) 
							System.out.println("Couldn't launch command, required parameter timeout missing.");
					}
					break;
				default:
					System.out.println("Function \""+f+"\" is not a correct command.");
				}
			}
		}
		else 
			System.out.println("No function found");
		return out;
	}
}

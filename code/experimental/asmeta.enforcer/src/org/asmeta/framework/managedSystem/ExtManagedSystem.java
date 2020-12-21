package org.asmeta.framework.managedSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.Socket;

import org.asmeta.framework.auxiliary.Utility;


//The managed system is a local process but external to the JVM where the enforcer runs.
//Communication is carried out by a local pipe using the ProcessBuilder class, which allows a 
//Java program to specify a process that is native to the operating system.
//Communication between the JVM and the external process occurs through the 
//InputStream and OutputStream of the external process.

public class ExtManagedSystem extends ManagedSystem{

	/** Communication handles*/
    //private Socket socket;		
    //private PrintWriter out;			
    //private BufferedReader in;
	private BufferedReader outFromS;
	private BufferedWriter inToS;
	
	 private ProcessBuilder pb;
    /** Shut down string*/
    private String SHUT_DOWN_STR = "###";
    
    /**
     * Constructor: create a new client instance as a local application
     * 	(by default, the subprocess reads input from a pipe)
     *  
     */
	//public ExtManagedSystem(String host, int port) {
    public ExtManagedSystem() {
     try {
			/*socket	= new Socket(host, port);
    	    out 	= new PrintWriter(socket.getOutputStream(),true);
			in		= new BufferedReader(new InputStreamReader(socket.getInputStream()));*/
			
			//Managed system started as a local application
    	     /* 
			 * By default, the subprocess reads input from a pipe. 
			 * 
			 * */
    	   String command = Utility.getProperty("SYSTEM_CMD"); //strings containing the program to be invoked and its arguments, if any
    	   String arg = Utility.getProperty("SYSTEM_ARG");
    	   String syspath = Utility.getProperty("SYSTEM_PATH");
    	   pb = new ProcessBuilder();
    	   pb.redirectErrorStream(true);
    	   //System.out.println(pb.directory());
    	   pb.command(command,"-cp",syspath,arg);
    	   Process p = pb.start();
    	   //we get the input stream from the standard output of the process.
		   outFromS = new BufferedReader(new InputStreamReader(p.getInputStream()));
		   
		   //we get the output stream from the standard input of the process.
		   inToS = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
		  } 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}		
    }
	
	/* public PrintWriter getPrintWriter(){
		return this.out;
	}
	
	
	public BufferedReader getBufferedReader(){
		return this.in;
	}
	*/
	
	public String read() throws IOException{
		return outFromS.readLine();
		
	}
	
	
	public String write(String outputStr) throws IOException{
		//inToS.println(outputStr);
		inToS.write(outputStr);
		inToS.flush();
		//return outputStr;
		return read();
	}
	
	public boolean shutDown(){
		String inputStr;
		try {
			inputStr = write(SHUT_DOWN_STR);
			System.out.println(inputStr);
			if (inputStr.equals(SHUT_DOWN_STR))
				return true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Probe getProbe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Effector getEffector() {
		// TODO Auto-generated method stub
		return null;
	}
}

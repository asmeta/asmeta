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

public abstract class ExtManagedSystem extends ManagedSystem{

	/** Communication handles*/
    //private Socket socket;		
    //private PrintWriter out;			
    //private BufferedReader in;
	private BufferedReader in;
	private BufferedWriter out;
	
	 private ProcessBuilder pb;
    /** Shut down string*/
    private String SHUT_DOWN_STR = "###";
    
    /**
     * Constructor: create a new client instance as a local application
     * 	(by default, the subprocess reads input from a pipe)
     *  
     */
	//public Client(String host, int port) {
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
    	   String command = Utility.getProperty("SYSTEM_CMD"); //a string array containing the program to start and its arguments
    	   pb = new ProcessBuilder(command);
    	   Process p = pb.start();
		   in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		   out = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
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
		return in.readLine();
	}
	
	
	public String write(String outputStr) throws IOException{
		//out.println(outputStr);
		out.write(outputStr);
		out.flush();
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
}

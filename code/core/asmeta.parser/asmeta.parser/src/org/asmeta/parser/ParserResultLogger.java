package org.asmeta.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/** when the parser encounter an error or a warning or whatever, uses this logger
 * which keeps track for example of the errors and line numbers
 * 
 * @author garganti
 * 
 * Log4j by default can log messages with five priority levels.

   1. Use debug to write debugging messages which should not be printed when the application is 
   		in production.
   2. Use info for messages similar to the "verbose" mode of many applications.
   3. Use warn for warning messages which are logged to some log but the application is able to 
   		carry on without a problem.
   4. Use error for application error messages which are also logged to some log but, still, 
   	the application can hobble along. Such as when some administrator-supplied configuration 
   	parameter is incorrect and you fall back to using some hard-coded default value.
   	A ParseException is an Error
   5. Use fatal for critical messages, after logging of which the application quits abnormally.
   Other exception are fatal 
 *
 * FOR THE PARSER
 *  1. debug: info for debugging (entering and exiting methods)
 *  2. info: like verbose mode (reading file, etc.)
 *  3. warn: like now warn++
 *  4. err: like now errors, parse Exception
 *  5. fatal: other exceptions
 */
public class ParserResultLogger { // DO NOT EXTEND LOGGER !!! USE COMPOSITION TO CHECK extends Logger{
	
	private Logger log;
	
	public ParserResultLogger() {
		// get the logger for the parser
		log = Logger.getLogger("org.asmeta.parser");
		//log.setLevel(Level.OFF);
		// if there are no appenders, a warning will be printed
//		if (!log.getAllAppenders().hasMoreElements())
//			log.addAppender(new ConsoleAppender(new SimpleLayout()));
	}
	

	/** last Parse Exception generated before exiting*/
	public ParseException lastParseError = null;
	
	/** list of warnings */
	public List<String> warnings = new ArrayList<String>();
	
	public int getWarnNum(){return warnings.size();}

	/** list of errors */
	public List<String> errors = new ArrayList<String>();

	public int getErrorNum(){return errors.size();}
	
	// fatal
	public void fatal(Object o){		
		log.fatal(o);
	}
	
	
	/** logs a ParseException: print the messages and keep track of the last ParseExeception
	 * ERRORS
	 * */
	public void logPE(ParseException pe){		
		logErr(pe.getMessage());
		// genera eccezione se cerca di capire qual e' il token
		// perche' pe.currentToken == null		
		if (pe.currentToken !=null) logErr("Line "+ pe.currentToken.beginLine + " column "+ pe.currentToken.beginColumn);
		// add to the errors
		lastParseError = pe;
	}
	
	/** logs some errors */
	public void logErr(String s){	
		errors.add(s);
		log.error(s);
	}
	
	/** logs some errors + info about the line and so on*/
	public void logErr(String s, Token current){		
		log.error(s);
		log.error("at line" + current.beginLine);
	}
	// DEBUG
	public void logDebug(String s){
		log.debug("LOGGGER " + log.getName() + " " + log.getLevel());
		log.debug(s);
	}

	// INFO
	public void logInfo(String s){
		log.info(s);
	}

	// WARNING
	
	public void logWarning(String s){
		warnings.add(s);
		log.warn(s);
	}
	
	// add appender	
	public void addAppender(Appender arg0){
		log.addAppender(arg0);
	}

	
	// reset 
	public void resetForParsing(File f){
		clear();
	}

	/**
	 * 
	 */
	public void clear() {
		lastParseError = null;
		warnings.clear();
		errors.clear();
	}
	
	public void setLevel(Level level) {
		log.setLevel(level);
	}
	
}

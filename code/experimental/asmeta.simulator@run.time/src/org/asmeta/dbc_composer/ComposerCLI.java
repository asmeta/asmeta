package org.asmeta.dbc_composer;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComposerCLI {
//-Interpreta `setup <alias> as <composition>` anche se la composizione è complessa.
//-Interpreta `run(<alias>, {param})` e chiama `eval(true,param)` sulla `Composition`.

	public static void main(String[] args) {
		        if (args.length == 0) {
		            System.err.println("Usage: java ConfigParserApp <config_file.asmsh>");
		            return;
		        }

		        String filePath = args[0];
		        Map<String, String> aliases = new HashMap<>(); //Associa alias all'espressione stringa corrispondente
		        Map<String, Composition> compositions = new HashMap<>(); //Associa alias a oggetto Composition

		        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
		            String line;
		            Pattern setupPattern = Pattern.compile("setup\\s+(\\w+)\\s+as\\s+(.+)");
		            Pattern runPattern = Pattern.compile("run\\((\\w+),\\s*\\{(.+)}\\)");

		            while ((line = reader.readLine()) != null) {
		                line = line.trim();

		                // Gestione SETUP
		                Matcher setupMatcher = setupPattern.matcher(line);
		                if (setupMatcher.matches()) {
		                    String alias = setupMatcher.group(1);
		                    String compositionExpr = setupMatcher.group(2).replaceAll("\\\\", "").trim();

		                    aliases.put(alias,compositionExpr);
		                    String replacedExpr = replaceAliasWithValues(compositionExpr, aliases);
		                    System.out.println("Parsing setup for alias: " + alias + " -> " + compositionExpr);	
		                    System.out.println("Parsing setup for alias: " + alias + " -> " + replacedExpr);
		                    Parser parser = null;
							try {
								parser = new Parser(replacedExpr,false);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							
							}
		                    Composition comp = parser.toComposition();
		                    System.out.println("Composition setup for alias: "+ alias + " -> "+comp.toString());
		                    compositions.put(alias, comp);
		                    System.out.println(compositions.toString());
		                    continue;
							
		                }

		                // Gestione RUN
		                Matcher runMatcher = runPattern.matcher(line);
		                if (runMatcher.matches()) {
		                    String alias = runMatcher.group(1);
		                    String parameters = runMatcher.group(2).trim();
		                    //Prepare input: transform strings into a map(string,string)
		                    Map<String,String> mon = new HashMap<>();
		                    String[] pairs = parameters.split(";");
		                    for (String pair:pairs) {
		                    	String[] keyValue = pair.split("=");
		                    	if (keyValue.length==2) {
		                    		mon.put(keyValue[0],keyValue[1]);
		                    	}
		                    }

		                    System.out.print("Running composition: " + alias);
		                    System.out.println(" with input: " + mon);
		                  
		                    Composition comp = compositions.get(alias);
		                  
		                    if (comp == null) {
		                        System.err.println("Composition not found for alias: " + alias);
		                        continue;
		                    }

		                    
		                    try {
		                        System.out.println(comp.toString());
		                    	comp.eval(true,mon); // true abilita il DbC checker; TODO eval con parametri per le funz. monitorate libere
		                        System.out.println(comp.toString());
		                    } catch (CompositionException e) {
		                        System.err.println("Error in composition " + alias + ": " + e.getMessage());
		                        System.out.println(comp.toString());
		                    }
		                }
		            }

		        } catch (IOException e) {
		            System.err.println("Error reading file: " + e.getMessage());
		        }
		    }

private static String replaceAliasWithValues(String compositionExpr, Map<String, String> alias) {

	// Pattern per identificare parole (chiavi) nella stringa
	        Pattern pattern = Pattern.compile("\\b\\w+\\b");
	        Matcher matcher = pattern.matcher(compositionExpr);

	        StringBuffer result = new StringBuffer();

	        while (matcher.find()) {
	            String key = matcher.group();
	            String value = alias.getOrDefault(key, key); // Se la chiave non esiste, lascia invariato
	            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
	        }
	        matcher.appendTail(result);

	        return result.toString();

}
		}
		
package org.asmeta.dbc_composer;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ComposerCLI {
//-Interpreta `setup <alias> as <composition>` anche se la composizione è complessa.
//-Interpreta `run(<alias>, {parametri})` e chiama `eval(true)` sulla `Composition`.

	public static void main(String[] args) {
		        if (args.length == 0) {
		            System.err.println("Usage: java ConfigParserApp <config_file.asmsh>");
		            return;
		        }

		        String filePath = args[0];
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

		                    System.out.println("Parsing setup for alias: " + alias);
		                    Parser parser = null;
							try {
								parser = new Parser(compositionExpr);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                    Composition comp = parser.toComposition();
		                    compositions.put(alias, comp);
		                    continue;
		                }

		                // Gestione RUN
		                Matcher runMatcher = runPattern.matcher(line);
		                if (runMatcher.matches()) {
		                    String alias = runMatcher.group(1);
		                    String parameters = runMatcher.group(2).trim();

		                    Composition comp = compositions.get(alias);
		                    if (comp == null) {
		                        System.err.println("Composition not found for alias: " + alias);
		                        continue;
		                    }

		                    System.out.println("Running composition: " + alias);
		                    try {
		                        comp.eval(true); // true abilita il DbC checker; TODO eval con parametri per le funz. monitorate libere
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
		}
		
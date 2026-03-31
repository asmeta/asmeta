package org.asmeta.xt.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.parser.ASMParser;
import org.asmeta.xt.asmetal.Asm;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Throwables;

import asmeta.AsmCollection;

public class ParseAndValidateResult{

	List<String> errors;
	ArrayList<Asm> asm;
	
	public ParseAndValidateResult(List<EObject> asm_list, List<INode> parserIssues, List<Issue> valIssues, boolean verifyWithOldParser, String path) {
		errors = new ArrayList<String>();
		// add parser errors 
		parserIssues.forEach(x -> errors.add(x.toString()));
		// add validation errors
		valIssues.forEach(issue -> errors.add(issue.getSeverity() + " " + issue.getMessage() + " (Line " + issue.getLineNumber() + ")"));
		asm = new ArrayList<Asm>();
		asm_list.forEach(x -> asm.add((Asm) x));
		
		// If the XText parser and validator does not produce any error, check with the old JavaCC parser too
		if (errors.isEmpty() && verifyWithOldParser) {
			for (Object m : asm_list) {
				Asm model = (Asm) m;
				
				try {
					AsmCollection x = ASMParser.setUpReadAsm(new File(path));
				}catch (org.asmeta.parser.ParseException e) { 
					errors.add("Error in token " + e.currentToken);
				}catch (Exception e) {
					errors.add(Throwables.getStackTraceAsString(e));
				} catch (Error e) {
					errors.add(Throwables.getStackTraceAsString(e));
				}
			}
		}
	}

	// together grammar and validation errors
	public List<String> getErrors() {
		return errors;
	}	
	
	public List<Asm> getAsm() {
		return asm;
	}
	
}

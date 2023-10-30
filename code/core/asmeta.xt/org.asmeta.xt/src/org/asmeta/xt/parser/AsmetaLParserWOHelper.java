package org.asmeta.xt.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.asmeta.parser.ASMParser;
import org.asmeta.xt.AsmetaLStandaloneSetup;
import org.asmeta.xt.asmetal.Asm;
import org.asmeta.xt.asmetal.ImportClause;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Injector;

public class AsmetaLParserWOHelper {
	
	public HashSet<String> importedAsm = new HashSet<String>(); 
	
	/**
	 * parse and return the ASM + errors (validation and parser).
	 *
	 * @param path the path
	 * @return the parses the and validate result
	 * @throws IOException            Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException  the file not found exception
	 * @throws OperationCanceledError the operation canceled error
	 */
	public ParseAndValidateResult parseAndValidateFile(String path, boolean verifyWithOldParser)
			throws IOException, FileNotFoundException, OperationCanceledError {
		Injector injector = new AsmetaLStandaloneSetup().createInjectorAndDoEMFRegistration();
		ResourceSet rs = injector.getInstance(ResourceSet.class);
		Resource resource = rs.getResource(URI.createFileURI(path), true);
		resource.load(rs.getLoadOptions());
		// parse the resource
		IParser parser = injector.getInstance(IParser.class);
		IParseResult pResult = parser.parse(new FileReader(path));
		List<EObject> asm_list = StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(resource.getAllContents(), Spliterator.ORDERED), false)
				.filter(x -> x instanceof Asm).collect(Collectors.toList());
		// TODO elimina questi messaggi
		if (!pResult.hasSyntaxErrors())
			System.out.println("parser ok");
		else {
			System.out.println("parser errors ");
			for (INode issue : pResult.getSyntaxErrors()) {
				System.out.println(issue.getText() + " (Line " + issue.getStartLine() + "): "
						+ issue.getSyntaxErrorMessage().getMessage());
			}
			List<INode> issues = new ArrayList<INode>();
			pResult.getSyntaxErrors().forEach(x -> issues.add(x));
			return new ParseAndValidateResult(asm_list, issues, Collections.EMPTY_LIST, verifyWithOldParser, path);
		}
		
		if (asm_list.size()>0) {
		
			importedAsm.add(((Asm)asm_list.get(0)).getName());
			
			// Check if also the imported ASMs are correct
			for (ImportClause i : ((Asm)asm_list.get(0)).getHeaderSection().getImportClause()) {
					
				String newPath = path.replace("\\", "/");
				newPath = newPath.substring(0, newPath.lastIndexOf("/")) + "/";
					
				if (!importedAsm.contains(newPath + i.getModuleName() + ASMParser.ASM_EXTENSION)) {	
				
					importedAsm.add(newPath + i.getModuleName() + ASMParser.ASM_EXTENSION);
					
					System.out.println("Checking imported ASMs");
					
					ParseAndValidateResult otherAsmResults = new AsmetaLParserWOHelper().parseAndValidateFile(newPath + i.getModuleName() + ASMParser.ASM_EXTENSION, verifyWithOldParser);
					if (otherAsmResults.errors.size()>0)
						return otherAsmResults;		
				}
			}	
			
		}
		
		// now apply the validation rules
		IResourceValidator validator = injector.getInstance(IResourceValidator.class);
		List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
		// TODO elimina questi messaggi
		if (issues.isEmpty())
			System.out.println("validazione ok");
		else {
			System.out.println("errori di validazione");
			for (Issue issue : issues) {
				System.out.println(issue.getMessage());
			}
		}
		return new ParseAndValidateResult(asm_list, Collections.EMPTY_LIST, issues, verifyWithOldParser, path);
	}

}

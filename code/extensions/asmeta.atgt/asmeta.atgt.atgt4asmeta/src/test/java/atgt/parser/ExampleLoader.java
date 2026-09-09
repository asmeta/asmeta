/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;

import atgt.parser.asmeta.AsmetaLLoader;
import atgt.parser.asmgofer.AsmGoferParser;
import atgt.parser.asmgofer.ParseException;
import atgt.specification.ASMSpecification;

/**
 * utility class to load an example it keeps also the memory for the examples, in
 * case it is already been loaded.
 */
public class ExampleLoader {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ExampleLoader.class);


	@BeforeAll
	static void activateLogger(){
		logger.setLevel(Level.DEBUG);
	}

	/** The specs loaded. */
	private static Map<String, ASMSpecification> specsLoaded = new HashMap<String, ASMSpecification>();

	private static String dir = "/atgt_examples/";

	/**
	 * Gets the spec.
	 * 
	 * @param spec
	 *            the spec
	 * 
	 * @return the spec
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException 
	 */
	public static ASMSpecification getSpec(String spec)
			throws ParseException, IOException {
		ASMSpecification s = specsLoaded.get(spec);
		if (s == null) {
			s = loadSpec(spec);
			specsLoaded.put(spec, s);
		}
		assert s != null : "spec " + spec + " is null";
		return s;
	}

	/**
	 * Load spec.
	 * 
	 * @param spec
	 *            the spec
	 * 
	 * @return the aSM specification
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException 
	 */
	private static ASMSpecification loadSpec(String spec){
		File specFile;
		try {
			specFile = getFileSpec(spec);
			// if it is not contained in the example dir try the direct name
			if (specFile ==null)
				specFile = new File(spec);
			assertTrue(specFile.exists(),spec + " not found nor in examples dir, nor in the current dir");
			if (spec.endsWith(".gs"))
				return loadAsmGoferSpec(specFile);
			if (spec.endsWith(".asm"))
				return loadAsmetalSpec(specFile);
			fail("not gs and not asm");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (tgtlib.specification.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Load asmetal spec.
	 * 
	 * @param specFile
	 *            the spec file
	 * 
	 * @return the aSM specification
	 * @throws tgtlib.specification.ParseException 
	 */
	private static ASMSpecification loadAsmetalSpec(File specFile) throws tgtlib.specification.ParseException {
		AsmetaLLoader xmipar = new AsmetaLLoader();
		ASMSpecification SP = xmipar.read(specFile);
		return SP;
	}

	/**
	 * Load asm gofer spec.
	 * 
	 * @param specFile
	 *            the spec file
	 * 
	 * @return the aSM specification
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws ParseException
	 *             the parse exception
	 */
	private static ASMSpecification loadAsmGoferSpec(File specFile)
			throws FileNotFoundException, ParseException {
		AsmGoferParser parser = new AsmGoferParser(new FileReader(specFile));
		// Elimina la specifica precedente.
		parser.resetSpecification();
		// Esegue il parsing del testo
		parser.parseSpec();
		// return the spec
		return parser.getSpecification();

	}

	
	/** given a spec with a name, returns the file with the right path
	 * (in the example directory) or in the combinatorial subdir
	 * @throws IOException 
	 * @throws IOException 
	 */
	static File getFileSpec(String s) throws IOException{
		// try the direct name first
		File spec = new File(s);
		if (spec.exists()) return spec;
		// in the example dir
		String baseDir;
		URL thisClassDir = ExampleLoader.class.getResource(".");
		// thisClassDir can be null (for example if a Jar)
		if (thisClassDir!=null){
			assertNotNull(thisClassDir);
			// FROM IDE (from another project too
			logger.debug("project dir = " + thisClassDir.getPath());
			String projectDirS = thisClassDir.getPath() + ".."+File.separator + ".."+File.separator + "..";
			File projectDir = new File(projectDirS);
			logger.debug("project dir = " + projectDir.getCanonicalPath());
			assertTrue(projectDir.exists());
			baseDir = projectDir.getAbsolutePath();
		} else { 
			// take the current dir
			baseDir = new File(".").getAbsolutePath();
		}
		//return new File(baseDir + "//source//atgt_examples//" + s);		
		spec = new File(baseDir + dir + s);
		if (spec.exists()) return spec;
		// try some subdir
		spec = new File(baseDir + dir + "combinatorial/" + s);
		assert spec.exists();
		return spec;
	}
}

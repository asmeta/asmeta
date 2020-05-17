package org.asmeta.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.parser.util.AsmPrinter;
import org.junit.BeforeClass;

import asmeta.AsmCollection;

/**
 * TODO use logger instead of Sysout
 * @author garganti
 *
 */
public class AsmParserTest {
	private static final boolean CHECK_EQUALITY = false;

	@BeforeClass
	public static void setUpLogger() {
		Logger log = Logger.getLogger("org.asmeta.parser");
		if (!log.getAllAppenders().hasMoreElements())
		log.addAppender(new ConsoleAppender(new SimpleLayout()));
		log.setLevel(Level.ALL);
		Logger.getLogger(Utility.class).setLevel(Level.ALL);
	}

	public static final String FILE_BASE = "../../../../asm_examples/";

	protected void testDir(String dir) {
		Collection<File> res = testSpecInSubFolder(dir);
		assertTrue(res.size() + " " + res.toString(), res.isEmpty());
	}

	/**
	 * recursive test test all the specs in the subdirectory of asm_examples
	 * called dirname if it contains a directory, all the specs in it will be
	 * tested. DO not stop if one fails.
	 * */
	private Collection<File> testSpecInSubFolder(String dirname) {
		Collection<File> failedSpec = new ArrayList<File>();
		File dir = new File(FILE_BASE + dirname);
		assertTrue("example dir " + dir.getAbsolutePath()
				+ " does not exist, current dir: "
				+ new File(".").getAbsolutePath(), dir.isDirectory());
		// read all the specs
		for (File f : dir.listFiles(new ASMFileFilter())) {
			AsmCollection testOneSpec = testOneSpec(f, false, false);
			if (testOneSpec == null){
				 failedSpec.add(f);
				 System.err.println(" failed " + f);
			}	
		}
		// test recursively dirs
		for (File f : dir.listFiles(new ASMDirFilter())) {
			if (f.isDirectory())
				failedSpec.addAll(testSpecInSubFolder(dirname + "/"
						+ f.getName()));
		}
		return failedSpec;

	}

	/**
	 * Test one spec. as file name, fail if not corrected
	 *
	 * @param spec the spec
	 * @return the asm collection
	 */
	protected AsmCollection testOneSpec(String spec) {
		File f = new File(FILE_BASE + spec);
		assertTrue("file " + f.getAbsolutePath()
				+ " does not exist, current dir: "
				+ new File(".").getAbsolutePath(), f.exists());
		ASMFileFilter filter = new ASMFileFilter();
		assertTrue("not a valid asm", (filter.accept(f)));
		AsmCollection x = testOneSpec(f, true, false);
		return x;
	}

	/**
	 * restituisce la collezione di ASM lette, null se non va bene.
	 *
	 * @param spec the specification to parse
	 * @param failOnError if error, fail
	 * @param visitParsedSpec visits the parse spec to check if the visitor works
	 * @return the asm collection
	 */
	private AsmCollection testOneSpec(File spec, boolean failOnError, boolean visitParsedSpec) {
		try {
			AsmCollection x = ASMParser.setUpReadAsm(spec);
			if (failOnError) assertNotNull(x);
			// reparse and reprint to STDOUT
			// TODO use logger instead
			if (visitParsedSpec) {
				StringPrintWriter writer = new StringPrintWriter();
				writer.append("// visiting spec " + spec + "\n");
				AsmPrinter printer = new AsmPrinter(writer);
				try {
					printer.visit(x.getMain());
				} catch (Exception e) {
					e.printStackTrace();
					fail("errors when visiting the spec "+ spec);
				} finally{
					System.out.println(writer.getString());					
				}
				// check equality
				if (CHECK_EQUALITY){
					checkEq(writer.getString(),spec);
				} 
			}
			return x;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("in file " + spec.getPath());
			return null;
		} catch (Error e) {
			e.printStackTrace();
			System.err.println("in file " + spec.getPath());
			return null;
		}
	}
	/** check the content of the spec as String as a File
	 *  TODO implement the comparison
	 * @param specS
	 * @param specF
	 */
	private void checkEq(String specS, File specF) {
		try {
			BufferedReader sReader = new BufferedReader(new StringReader(specS));
			BufferedReader fReader = new BufferedReader(new FileReader(specF));
			StreamTokenizer sst = new StreamTokenizer(sReader);
			StreamTokenizer fst = new StreamTokenizer(fReader);	
			for(;;){
				int nTokS = sst.nextToken();
				if ( nTokS != fst.nextToken() ||
						(nTokS == StreamTokenizer.TT_WORD && ! sst.sval.equals(fst.sval))||
						(nTokS == StreamTokenizer.TT_NUMBER && sst.nval != sst.nval)){
					System.err.println(sst.toString() + " in visited differs from " + fst.toString() + " in file");
					fail();
				}
				if (nTokS == StreamTokenizer.TT_EOF) return;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}

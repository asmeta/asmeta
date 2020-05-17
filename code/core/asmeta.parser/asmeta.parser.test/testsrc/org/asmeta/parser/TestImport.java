package org.asmeta.parser;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import asmeta.AsmCollection;

@RunWith(Parameterized.class)
public class TestImport {

	private File root;
	private File importedAsm;

	
	public TestImport(String dir) throws IOException, Exception {
		// make a temp module to be imported
		new File(dir).mkdirs();
		root = new File(dir);
		// 1. build imported asm
		importedAsm = buildtempAsm(root, "imported",null);
    }
	
	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
                 { "temp"}, { "temp with space" }});
    }


	@After
	public void deleteFiles() throws Exception {
		deleteFilesIn(root);
		root.delete();
	}

	private void deleteFilesIn(File file) {
		// delete content of temp
		String[]entries = file.list();
		for(String s: entries){
		    File currentFile = new File(file.getPath(),s);
		    if (currentFile.isDirectory())
		    	deleteFilesIn(currentFile);
		    else
		    	currentFile.delete();
		}
		file.delete();
	}
	
	@Test
	public void testImportRelative() throws Exception {
		// A. in the same directory with relative path without ASM
		String asmname = importedAsm.getName();
		asmname = asmname.substring(0, asmname.lastIndexOf('.'));		
		buildtempAsm(root, "importingRel", asmname);		
	}
	@Test
	public void testImportAbsolute() throws Exception {
		// B. in the same directory with absolute path
		buildtempAsm(root, "importingAbs", importedAsm.getAbsolutePath());		
	}

	@Test
	public void testDoubleImportRelativeSubdir() throws Exception {		
		String subdir = "subdir";
		Path sub = Paths.get(root.getPath()+File.separator+ subdir);
		if (!sub.toFile().exists()) Files.createDirectory(sub);
		Path subsub = Paths.get(root.getPath()+File.separator+ subdir+File.separator+ subdir);
		if (!subsub.toFile().exists()) Files.createDirectory(subsub);
		// 1. build imported asm in subdir 2
		importedAsm = buildtempAsm(subsub.toFile(), "imported2",null);
		// 2. build imported/importing asm in subdir 1
		File bothAsm = buildtempAsm(sub.toFile(), "bothRel", subdir + "/" + importedAsm.getName());		
		// 3. in the root directory with relative path using .
		buildtempAsm(root, "bothRel", subdir + "/" + bothAsm.getName());		
	}

	@Test
	public void testImportRelativeSubdir() throws Exception {		
		String subdir = "subdir";
		Path sub = Paths.get(root.getPath()+File.separator+ subdir);
		if (!sub.toFile().exists()) Files.createDirectory(sub);
		// 1. build imported asm in subdir
		importedAsm = buildtempAsm(sub.toFile(), "imported2",null);
		// B1. in the root directory with absolute path simple
		buildtempAsm(root, "importingRel", subdir + "/" + importedAsm.getName());		
		// B2. in the root directory with absolute path using .
		buildtempAsm(root, "importingRel", "./" + subdir + "/" + importedAsm.getName());		
		// B3. in the root directory with absolute path using \
		buildtempAsm(root, "importingRel", subdir + File.separator + importedAsm.getName());		
	}
	
	
	
	private static File buildtempAsm(File root, String asmnameprefix, String importedAsm) throws IOException, Exception {
		File builtAsm = File.createTempFile(asmnameprefix, ".asm", root);
		String asmname = builtAsm.getName();
		asmname = asmname.substring(0, asmname.lastIndexOf('.'));
		// write something into it
		FileWriter fw = new FileWriter(builtAsm);
		fw.write("asm " + asmname + "\n");
		// escape with "
		if (importedAsm!= null) {
			if (importedAsm.contains(" ")) 
			importedAsm = "\""+ importedAsm + "\"";  
			fw.write("import " + importedAsm + "\n");
		}
		fw.write("signature:\n");
		fw.write("definitions:\n");
		fw.close();
		// parse
		AsmCollection x = ASMParser.setUpReadAsm(builtAsm);
		assertNotNull(x);
		return builtAsm;
	}

	
	
	
}

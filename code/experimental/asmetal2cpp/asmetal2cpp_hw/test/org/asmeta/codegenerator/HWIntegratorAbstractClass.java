package org.asmeta.codegenerator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.asmeta.asm2code.main.AsmToCGenerator;
import org.asmeta.codegenerator.arduino.ArduinoVersion;
import org.asmeta.codegenerator.configuration.HWConfiguration;
import org.asmeta.parser.ASMParser;
import org.eclipse.xtext.junit4.util.ParseHelper;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.inject.Inject;

import asmeta.AsmCollection;
import asmeta.structure.Asm;

public abstract class HWIntegratorAbstractClass {

	/** region attributes **/
	@Inject
	private ParseHelper<asmeta.structure.Asm> parseHelper;
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
	
	/** endregion **/
	
	/** region methods **/
	
	/**
	 * method for generating json configuration file with base skeleton
	 * 
	 * @param uasmFilePath  path to asm model
	 * @param export  enables json export to file
	 * @throws IOException
	 * @throws Exception
	 */
	void generateJsonConfiguration(String uasmFilePath, boolean export) throws IOException, Exception {
		File uasmFile= new File(uasmFilePath);
		
		if(!uasmFile.exists())
			throw new FileNotFoundException("File "+uasmFilePath+" not found");
		
		try {		
			//final Asm model = parseAsm(uasmFilePath);
			//final Asm model = ASMParser.setUpReadAsm(uasmFile).getMain();
			AsmCollection model = ASMParser.setUpReadAsm(uasmFile);
			//Resource res = model.eResource();
			JsonGenerator jsonGen = new JsonGenerator(ArduinoVersion.UNO);
			//jsonGen.getFolderPath(model,uasmFile.getPath());
			jsonGen.getFolderPath(model.getMain(),uasmFile.getPath());
			//System.out.println(jsonGen.compile(model,true));
			//Check if the U2C file has to be exported
			if (export) {
				String filePath = uasmFile.getPath().replace(".asm", ".u2c");
				String separator = (filePath.indexOf("\\") >= 0) ? "\\" : "/";
				int index = filePath.lastIndexOf(separator) + 1;
				filePath = filePath.substring(0, index) + dateFormat.format(new Date()) + "_" + filePath.substring(index, filePath.length());
				System.out.println(filePath);
				//jsonGen.generate(model, filePath, folder);
				jsonGen.generate(model, filePath, true);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * method that generates the HW integration library that connects Arduino hardware to software
	 * 
	 * @param asmFilePath			path to asm model
	 * @param jsonFilePath			path to json configuration
	 * @throws IOException
	 * @throws Exception
	 */
	public void generateHWIntegrationLibrary(String asmFilePath) throws IOException, Exception {
		File asmFile = new File(asmFilePath);
		File hwFile = new File(asmFile.getParent() + "/" + asmFile.getName().replace(AsmToCGenerator.Ext, HWIntegrationGenerator.Ext));
		File u2cFile = getLastU2CFile(asmFile.getParentFile());
		
		assertTrue(asmFile.exists() && u2cFile.exists());
		
		Gson gson = new Gson();
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(u2cFile));
			HWConfiguration config = gson.fromJson(reader, HWConfiguration.class);
			
			assertTrue("Configuration file not correct", config.isValid());
			
			HWIntegrationGenerator hwGen = new HWIntegrationGenerator(config);
			AsmCollection model = ASMParser.setUpReadAsm(asmFile);
			hwGen.generate(model, hwFile.getPath());
			
			assertTrue(hwFile.exists());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		}
	};
	
	public void generateInoProject(String asmFilePath) throws IOException, Exception{
		File asmFile = new File(asmFilePath);
		File u2cFile = getLastU2CFile(asmFile.getParentFile());
		File inoFile = new File(asmFile.getParent() + "/" + asmFile.getName().replace(AsmToCGenerator.Ext, InoGenerator.Ext));
		
		assertTrue(asmFile.exists() && u2cFile.exists());
		
		Gson gson = new Gson();
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(u2cFile));
			HWConfiguration config = gson.fromJson(reader, HWConfiguration.class);
			final Asm model = ASMParser.setUpReadAsm(asmFile).getMain();
			InoGenerator inoGen = new InoGenerator(config);
			inoGen.generate(model, inoFile.getPath());
			
			assertTrue(inoFile.exists());
		}catch(Exception e) {
			e.printStackTrace();
			fail();
			return;
		}
		
		createProject(u2cFile, inoFile);
	}
	
	protected File getLastU2CFile(File dir) throws ParseException
	{
		File[] files = dir.listFiles();
		File u2cFile = null;
		Date mostRecent = null;
		
		for (File file : files)
		{
			if (file.getName().endsWith(".u2c"))
			{
				String[] nameParts = file.getName().split("_");
				
				Date d = dateFormat.parse(nameParts[0] + "_" + nameParts[1]);
				
				if (u2cFile == null
				|| d.compareTo(mostRecent) > 0)
				{
					mostRecent = d;
					u2cFile = file;
				}
			}
		}
		
		return u2cFile;
	}
	
	protected void createProject(File u2cFile, File inoFile) throws IOException
	{
		String projectName = FilenameUtils.removeExtension(inoFile.getName());
		File projectDir = new File(inoFile.getParentFile().getAbsolutePath() + "/" + projectName);
				
		// re-create folder
		projectDir.createNewFile();
		
		Path sIno = inoFile.toPath();
		Path dIno = Paths.get(projectDir.getAbsolutePath() + "/" + projectName + ".ino");

		Path sCpp = Paths.get(inoFile.getParentFile().getAbsolutePath() + "/" + projectName + ".cpp");
		Path dCpp = Paths.get(projectDir.getAbsolutePath() + "/" + projectName + ".cpp");

		Path sH = Paths.get(inoFile.getParentFile().getAbsolutePath() + "/" + projectName + ".h");
		Path dH = Paths.get(projectDir.getAbsolutePath() + "/" + projectName + ".h");

		Path sHWCpp = Paths.get(inoFile.getParentFile().getAbsolutePath() + "/" + projectName + "_hw.cpp");
		Path dHWCpp = Paths.get(projectDir.getAbsolutePath() + "/" + projectName + "_hw.cpp");

		Path sU2C = u2cFile.toPath();
		Path dU2C = Paths.get(projectDir.getAbsolutePath() + "/" + FilenameUtils.removeExtension(u2cFile.getName()) + ".u2c");

		
		Paths.get(projectDir.getAbsolutePath() + "/" + projectName + ".h");
		Paths.get(projectDir.getAbsolutePath() + "/" + projectName + "_hw.cpp");
		
		// copy files to new project folder
		Files.copy(sIno, dIno, StandardCopyOption.REPLACE_EXISTING);
		Files.copy(sCpp, dCpp, StandardCopyOption.REPLACE_EXISTING);
		Files.copy(sH, dH, StandardCopyOption.REPLACE_EXISTING);
		Files.copy(sHWCpp, dHWCpp, StandardCopyOption.REPLACE_EXISTING);
		Files.copy(sU2C, dU2C, StandardCopyOption.REPLACE_EXISTING);
	}
	
	/** endregion **/
}

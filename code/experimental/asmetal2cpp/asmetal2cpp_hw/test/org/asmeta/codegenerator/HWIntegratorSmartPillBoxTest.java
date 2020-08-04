package org.asmeta.codegenerator;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class HWIntegratorSmartPillBoxTest extends HWIntegratorAbstractClass {

	private Boolean creatProjectFolderFlag = true;
	
	/** json generation **/
	
	//@Test
	public void Level0GenerateJsonTest() throws Exception, IOException
	{
		String asmPath = "..//..//..//..//asm_examples//PillBox//Level0//pillbox_0.asm";
		generateJsonConfiguration(asmPath, true);
	}
	
	//@Test
	public void Level1GenerateJsonTest() throws Exception, IOException
	{
		String asmPath = "..//..//..//..//asm_examples//PillBox//Level1//pillbox_1.asm";
		generateJsonConfiguration(asmPath, true);
	}
	
	//@Test
	public void Level2GenerateJsonTest() throws Exception, IOException
	{
		String asmPath = "..//..//..//..//asm_examples//PillBox//Level2//pillbox_2.asm";
		generateJsonConfiguration(asmPath, true);
	}
	
	//@Test
	public void Level3GenerateJsonTest() throws Exception, IOException
	{
		String asmPath = "..//..//..//..//asm_examples//PillBox//Level3//pillbox_FULL.asm";
		generateJsonConfiguration(asmPath, true);
	}
	
	/** HW library generation **/
	
	//@Test
	public void Level0GenerateHWIntegrationTest() throws Exception, IOException
	{
		String asmPath = "..//..//..//..//asm_examples//PillBox//Level0//pillbox_0.asm";
		generateHWIntegrationLibrary(asmPath);
	}
	
	//@Test
	public void Level1GenerateHWIntegrationTest() throws Exception, IOException
	{
		String asmPath = "..//..//..//..//asm_examples//PillBox//Level1//pillbox_1.asm";
		generateHWIntegrationLibrary(asmPath);
	}
	
	//@Test
	public void Level2GenerateHWIntegrationTest() throws Exception, IOException
	{
		String asmPath = "..//..//..//..//asm_examples//PillBox//Level2//pillbox_2.asm";
		generateHWIntegrationLibrary(asmPath);
	}
	
	//@Test
	public void Level3GenerateHWIntegrationTest() throws Exception, IOException
	{
		String asmPath = "..//..//..//..//asm_examples//PillBox//Level3//pillbox_FULL.asm";
		generateHWIntegrationLibrary(asmPath);
	}
	
	/** ino file generation **/

	//@Test
	public void Level0GenerateInoFileTest() throws Exception, IOException
	{
		String asmPath = "..//..//..//..//asm_examples//PillBox//Level0//pillbox_0.asm";
		generateInoProject(asmPath, creatProjectFolderFlag);
	}
	
	//@Test
	public void Level1GenerateInoFileTest() throws Exception, IOException
	{
		String asmPath = "..//..//..//..//asm_examples//PillBox//Level1//pillbox_1.asm";
		generateInoProject(asmPath, creatProjectFolderFlag);
	}
	
	//@Test
	public void Level2GenerateInoFileTest() throws Exception, IOException
	{
		String asmPath = "..//..//..//..//asm_examples//PillBox//Level2//pillbox_2.asm";
		generateInoProject(asmPath, creatProjectFolderFlag);
	}
	
	//@Test
	public void Level3GenerateInoFileTest() throws Exception, IOException
	{
		String asmPath = "..//..//..//..//asm_examples//PillBox//Level3//pillbox_FULL.asm";
		generateInoProject(asmPath, creatProjectFolderFlag);
	}
	
	/** other tests **/
	
	@Test
	public void CreateProjectFolderTest() throws Exception, IOException
	{
		File inoFile = new File("..//..//..//..//asm_examples//PillBox//Level0//pillbox_0.ino");
		File u2cFile = getLastU2CFile(inoFile.getParentFile());
		
		if (inoFile.exists() && u2cFile.exists())
		{
			createProject(u2cFile, inoFile);
		}
	}
}

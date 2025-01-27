package org.asmeta.codegenerator;

import static org.junit.Assert.fail;

import org.asmeta.flattener.*;

import java.io.IOException;

import org.junit.Test;

import asmeta.structure.Asm;

public class JsonGeneratorCarSystem extends HWIntegratorAbstractClass{
	
	@Test
	public void CarSystem000JsonTest() throws Exception, IOException{
		String asmspec = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem000/CarSystem000CommonDomains.asm";
		generateJsonConfiguration(asmspec, true);
		//asmspec = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem000/CarSystem000CommonFunctions.asm";
		//generateJsonConfiguration(asmspec, true);
	}

	@Test
	public void CarSystem001JsonTest() throws Exception, IOException{
		String asmspec = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001main.asm";
		generateJsonConfiguration(asmspec, true);
	}
	
	@Test
	public void CarSystem002JsonTest() throws Exception, IOException{
		String asmspec = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002main.asm";
		generateJsonConfiguration(asmspec, true);
	}
	
	@Test
	public void CarSystem003JsonTest() throws Exception, IOException{
		String asmspec = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem003/CarSystem003main.asm";
		generateJsonConfiguration(asmspec, true);
	}
	
	@Test
	public void CarSystem004JsonTest() throws Exception, IOException{
		String asmspec = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004main.asm";
		generateJsonConfiguration(asmspec, true);
	}
	
	@Test
	public void CarSystem007JsonTest() throws Exception, IOException{
		String asmspec = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem007/CarSystem007main.asm";
		generateJsonConfiguration(asmspec, true);
	}
	
	
	@Test
	public void CarSystem009JsonTest() throws Exception, IOException{
		String asmspec = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem009/CarSystem009main.asm";
		generateJsonConfiguration(asmspec, true);
	}
	@Test
	public void CarSystem001FolderJson() throws Exception, IOException {
		String asmspec = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001main.asm";
		generateJsonConfiguration(asmspec, true);
	}
	
	@Test
	public void CarSystem002FolderJson() throws Exception, IOException {
		String asmspec = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002main.asm";
		generateJsonConfiguration(asmspec, true);
	}
	
	@Test
	public void CarSystem004FolderJson() throws Exception, IOException {
		String asmspec = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004main.asm";
		generateJsonConfiguration(asmspec, true);
	}
	
	@Test
	public void CarSystem005FolderJson() throws Exception, IOException {
		String asmspec = "../asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem005/CarSystem005main.asm";
		generateJsonConfiguration(asmspec, true);
	}
	
	


}

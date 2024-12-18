package org.asmeta.asm2code.main;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.asm2code.main.CppGenerator;
import org.asmeta.asm2code.main.HeaderGenerator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.asm2code.compiler.CompileResult;
import org.asmeta.asm2code.compiler.CppCompiler;
import org.asmeta.parser.ASMParser;
import org.junit.Test;
import asmeta.AsmCollection;

public class GeneratorCompilerCarSystem extends GeneratorCompilerTest{

	@Test
	public void testCarSystem001Blink() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001Blink.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	
	@Test
	public void testCarSystem001main() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001main.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001HW.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	
	@Test
	public void testCarSystem003HighBeam() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem003/CarSystem003HighBeam.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
   
	@Test
	public void testCarSystem004HighBeam() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004HighBeam.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
  
	@Test
	public void testCarSystem007AdaptiveCruiseC() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem007/CarSystem007AdaptiveCruiseC.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	
	@Test
	public void testCarSystem001Functions() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001Functions.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	
	@Test
	public void testCarSystem003main() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem003/CarSystem003main.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	
	@Test
	public void testCarSystem000All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem000/CarSystem000CommonDomains.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem000/CarSystem000CommonFunctions.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	@Test
	public void testCarSystem001All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001Domains.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001Functions.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001Blink.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001HW.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001main.asm";
		if (!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	
	@Test
	public void testCarSytem002All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002Domains.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002Functions.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002EmergencyBrakeLights.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002LowBeam.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002Cornering.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002main.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	
	@Test
	public void testCarSystem003All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem003/CarSystem003Domains.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem003/CarSystem003HighBeam.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem003/CarSystem003main.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	
	@Test
	public void testCarSystem004All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004Domains.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004Functions.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004Blink.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004EmergencyBrakeLights.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004HighBeam.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004LowBeam.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004Cornering.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004HW.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004main.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	
	@Test
	public void testCarSystem005All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem005/CarSystem005Domains.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem005/CarSystem005Functions.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem005/CarSystem005DesiredSpeedCruiseC.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem005/CarSystem005main.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	
	@Test
	public void testCarSystem006All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem006/CarSystem006Functions.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem006/CarSystem006DesiredSpeedCruiseC.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem006/CarSystem006SpeedLimitTrafficDet.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem006/CarSystem006main.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	
	
	@Test
	public void testCarSystem007All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem007/CarSystem007EmergencyBrakeSpeed.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem007/CarSystem007AdaptiveCruiseC.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem007/CarSystem007main.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	
	
	@Test
	public void testCarSystem008All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem008/CarSystem008main.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
	
	@Test
	public void testCarSystem009All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem009/CarSystem009EmergencyBrakeLights.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem009/CarSystem009main.asm";
		if(!AsmetaL2CppGeneratorMain.translate(asmspec,testOptions, false).success)
			fail();
	}
		

//CARSYSTEM001:
	
	@Test
	public void testCarSystem001Domainscpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem001Domains.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem001";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
	@Test
	public void testCarSystem001Functionscpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem001Functions.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem001";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
	@Test
	public void testCarSystem001Blinkcpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem001Blink.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem001";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
	@Test
	public void testCarSystem001HWcpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem001HW.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem001";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
	@Test
	public void testCarSystem001maincpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem001main.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem001";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}

//CARSYSTEM002
	@Test
	public void testCarSystem002Domainscpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem002Domains.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem002";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
	@Test
	public void testCarSystem002Functionscpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem002Functions.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem002";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
	@Test
	public void testCarSystem002LowBeamcpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem002LowBeam.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem002";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
	@Test
	public void testCarSystem002Corneringcpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem002Cornering.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem002";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
	@Test
	public void testCarSystem002maincpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem002main.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem002";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
	@Test
	public void testCarSystem002Prova() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "Prova.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem002";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
//CARSYSTEM003
	@Test
	public void testCarSystem003HighBeamcpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem003HighBeam.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem003";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
	@Test
	public void testCarSystem003maincpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem003main.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem003";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
//CARSYSTEM004
	@Test
	public void testCarSystem004Blinkcpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem004Blink.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem004";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
	@Test
	public void testCarSystem004HighBeamcpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem004HighBeam.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem004";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
	@Test
	public void testCarSystem004maincpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem004main.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem004";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
//CARSYSTEM005
	@Test
	public void testCarSystem005Domainscpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem005Domains.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem005";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
	@Test
	public void testCarSystem005maincpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem005main.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem005";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
//CARSYSTEM006
	

	
//CARSYSTEM007
	
	@Test
	public void testCarSystem007AdaptiveCruiseCcpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem007AdaptiveCruiseC.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem007";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}

//CARSYSTEM008
	@Test
	public void testCarSystem008maincpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem008main.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem008";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
	
//CARSYSTEM009
	public void testCarSystem009EmergencyBrakeLightscpp() {
		Logger.getLogger(CppCompiler.class).setLevel(Level.ALL);
		String name = "CarSystem009EmergencyBrakeLights.cpp";
		String dir = "examples/ABZ2020/CarSystemModule/CarSystem009";
		if(!CppCompiler.compile(name, dir, true, true, true).success)
			fail();
	}
}

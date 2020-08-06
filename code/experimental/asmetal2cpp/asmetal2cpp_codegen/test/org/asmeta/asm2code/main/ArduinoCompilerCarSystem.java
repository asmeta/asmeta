package org.asmeta.asm2code.main;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ArduinoCompilerCarSystem extends GeneratorCompilerTest2 {
	
	TranslatorOptions arduinoOptions = new TranslatorOptions(true,true,true,true);

	@Test
	public void testCarSystem000All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem000/CarSystem000CommonDomains.asm";
		if (!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem000/CarSystem000CommonFunctions.asm";
		if (!test(asmspec,arduinoOptions).success)
			fail();
	}
	@Test
	public void testCarSystem001All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001Domains.asm";
		if (!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001Functions.asm";
		if (!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001Blink.asm";
		if (!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001HW.asm";
		if (!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001main.asm";
		if (!test(asmspec,arduinoOptions).success)
			fail();
	}
	
	@Test
	public void testCarSytem002All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002Domains.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002Functions.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002EmergencyBrakeLights.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002LowBeam.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002Cornering.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem002/CarSystem002main.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
	}
	
	@Test
	public void testCarSystem003All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem003/CarSystem003Domains.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem003/CarSystem003HighBeam.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem003/CarSystem003main.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
	}
	
	@Test
	public void testCarSystem004All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004Domains.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004Functions.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004Blink.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004EmergencyBrakeLights.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004HighBeam.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004LowBeam.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004Cornering.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004HW.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem004/CarSystem004main.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
	}
	
	@Test
	public void testCarSystem005All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem005/CarSystem005Domains.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem005/CarSystem005Functions.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem005/CarSystem005DesiredSpeedCruiseC.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem005/CarSystem005main.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
	}
	
	@Test
	public void testCarSystem006All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem006/CarSystem006Functions.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem006/CarSystem006DesiredSpeedCruiseC.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem006/CarSystem006SpeedLimitTrafficDet.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem006/CarSystem006main.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
	}
	
	
	@Test
	public void testCarSystem007All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem007/CarSystem007EmergencyBrakeSpeed.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem007/CarSystem007AdaptiveCruiseC.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem007/CarSystem007main.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
	}
	
	
	@Test
	public void testCarSystem008All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem008/CarSystem008main.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
	}
	
	@Test
	public void testCarSystem009All() throws IOException, Exception{
		String asmspec = "examples/ABZ2020/CarSystemModule/CarSystem009/CarSystem009EmergencyBrakeLights.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
		asmspec = "examples/ABZ2020/CarSystemModule/CarSystem009/CarSystem009main.asm";
		if(!test(asmspec,arduinoOptions).success)
			fail();
	}

}

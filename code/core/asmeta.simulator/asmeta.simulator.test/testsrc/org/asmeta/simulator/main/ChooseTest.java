/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator.main;

import org.asmeta.simulator.UpdateSet;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChooseTest {
	
	@BeforeClass
	public static void setUpLogger(){
		//AsmParserTest.setUpLogger();
		//
//		Logger log = Logger.getLogger("org.asmeta.simulator");
//		if (!log.getAllAppenders().hasMoreElements())
//			log.addAppender(new ConsoleAppender(new SimpleLayout()));
//		log.setLevel(Level.ALL);
	}
	
	static final String chooseExample = "chooseProblem.asm"; 
	
	@Test
	public void testNonDetermistic() throws Exception{
		Simulator sim = Util.getSimulatorForTestSpec("test/simulator/"+chooseExample);
		sim.setShuffleFlag(true);
		for(int i = 1; i < 10; i++){
			UpdateSet updateSet = sim.doOneStep();	
			System.out.println(updateSet);
			// TODO check that it can change
		}
	} 
	
	@Test
	public void testDetermistic() throws Exception{
		Simulator sim = Util.getSimulatorForTestSpec("test/simulator/"+chooseExample);
		sim.setShuffleFlag(false);
		for(int i = 1; i < 10; i++){
			UpdateSet updateSet = sim.doOneStep();	
			System.out.println(updateSet);
			// TODO check that it is alwsys the same
		}
	} 

}

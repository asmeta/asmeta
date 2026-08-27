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
package atgt.combinatorial;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import atgt.generator.AsmMonitoredDataExtractor;
import atgt.parser.ExampleLoader;
import atgt.parser.asmgofer.ASMParserTest;
import atgt.parser.asmgofer.ParseException;
import atgt.specification.ASMSpecification;
import extgt.coverage.combinatorial.MonitoredData;

// TODO: Auto-generated Javadoc
/**
 * test the extration of moniotred data.
 */
public class MonitoredDataExtractorTest {

	/**
	 * Test analyze cc.
	 */
	@Test
	public void testAnalyzeCC() {
		ASMSpecification SP = ASMParserTest.getCruiseControlNoAxiom();
		AsmMonitoredDataExtractor mde = AsmMonitoredDataExtractor.getMonitoredDataExtractor();
		MonitoredData data = mde.analyze(SP);
		System.out.println(data);
		assertTrue(data.toString(),data.toString().contains("ignited=[true, false]"));
		assertTrue(data.toString().contains(
				"cruiseEvent=[Activate, Deactivate, Resume, NONE]"));
		assertTrue(data.toString().contains("engRun=[true, false]"));
		assertTrue(data.toString().contains("brake=[true, false]"));
		assertTrue(data.toString().contains("tooFast=[true, false]"));
	}
	
	
	@Test
	public void testSISgs() throws ParseException, IOException{
		ASMSpecification SIS = ExampleLoader.getSpec("sis.gs");
		AsmMonitoredDataExtractor mde = AsmMonitoredDataExtractor.getMonitoredDataExtractor();
		MonitoredData data = mde.analyze(SIS);
		// integers variables are ingored !!!
		assertEquals("[reset, block]", data.getVars().toString());
		
		
	}

}

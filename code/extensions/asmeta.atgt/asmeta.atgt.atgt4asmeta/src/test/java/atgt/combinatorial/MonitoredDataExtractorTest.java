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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import atgt.generator.AsmMonitoredDataExtractor;

import org.junit.jupiter.api.Test;
import atgt.parser.ExampleLoader;
import atgt.parser.asmgofer.ASMParserTest;
import atgt.specification.ASMSpecification;
import extgt.coverage.combinatorial.MonitoredData;

// TODO: Auto-generated Javadoc
/**
 * test the extration of moniotred data.
 */
class MonitoredDataExtractorTest {

	/**
	 * Test analyze cc.
	 */
	@Test void analyzeCC() {
		ASMSpecification SP = ASMParserTest.getCruiseControlNoAxiom();
		AsmMonitoredDataExtractor mde = AsmMonitoredDataExtractor.getMonitoredDataExtractor();
		MonitoredData data = mde.analyze(SP);
		System.out.println(data);
		assertTrue(data.toString().contains("ignited=[true, false]"),data.toString());
		assertTrue(data.toString().contains(
				"cruiseEvent=[Activate, Deactivate, Resume, NONE]"));
		assertTrue(data.toString().contains("engRun=[true, false]"));
		assertTrue(data.toString().contains("brake=[true, false]"));
		assertTrue(data.toString().contains("tooFast=[true, false]"));
	}


	@Test void sISgs() throws Exception{
		ASMSpecification SIS = ExampleLoader.getSpec("sis.gs");
		AsmMonitoredDataExtractor mde = AsmMonitoredDataExtractor.getMonitoredDataExtractor();
		MonitoredData data = mde.analyze(SIS);
		// integers variables are ingored !!!
		assertEquals("[reset, block]", data.getVars().toString());
		
		
	}

}

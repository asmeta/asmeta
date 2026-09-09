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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import atgt.coverage.TestCondition;

import org.junit.jupiter.api.Test;
import atgt.generator.MonitoredDataToSAL;
import atgt.parser.asmeta.AsmMLoaderTest;
import atgt.parser.asmgofer.ASMParserTest;
import atgt.specification.ASMSpecification;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.util.SimpleCmdExecutor;

// TODO: Auto-generated Javadoc
/**
 * test the translation to SAL of some specs.
 */
public class ToSALCombinatorialTest {

	/**
	 * cruise control without axioms.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test void cCToSAL() throws Exception {
		ASMSpecification SP = ASMParserTest.getCruiseControlNoAxiom();
		TypedInitExpression cruiseEvent = SP.getVariable("cruiseEvent");
		assertNotNull(cruiseEvent);
		EnumConst resume = ((EnumType)cruiseEvent.getType()).getEnumConst("Resume");
		assertNotNull(resume);
		Expression exp = new EqualsExpression(cruiseEvent.getIdExpression(), resume);
		TestCondition tc = new TestCondition("cincative", exp);
		testWFSpecInitNext("cruisecontrol", tc, SP);
	}

	/**
	 * cruise control with axioms.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test void ccwaToSAL() throws Exception {
		ASMSpecification SP = AsmMLoaderTest.cc_asmWithAxioms();
		TypedInitExpression lever = SP.getVariable("lever");
		assertNotNull(lever);
		EnumConst resume = ((EnumType)lever.getType()).getEnumConst("RESUME");
		assertNotNull(resume);
		Expression exp = new EqualsExpression(lever.getIdExpression(),resume);
		TestCondition tc = new TestCondition("cincative", exp);
		testWFSpecInitNext("cruisecontrolAx", tc, SP);
	}

	/**
	 * get the translation for BBS con assiomi.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test void bbsToSAL() throws Exception {
		ASMSpecification SP = AsmMLoaderTest.BasicBillingSystem();
		TypedInitExpression billing = SP.getVariable("billing");
		assertNotNull(billing);
		EnumConst collect = ((EnumType)billing.getType()).getEnumConst("COLLECT");
		assertNotNull(collect);
		Expression exp = new EqualsExpression(billing.getIdExpression(),collect);
		TestCondition tc = new TestCondition("bllingColl", exp);
		testWFSpecInitNext("BasicBillingSystem", tc, SP);
	}

	/**
	 * cruise control with axioms.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test void sisAb() throws Exception {
		ASMSpecification SP = AsmMLoaderTest.sis_asmAbstract();		
		Expression exp = makeEqualsExpression(SP,"waterpressure","GT_PERMIT");
		TestCondition tc = new TestCondition("cincative", exp);
		testWFSpecInitNext("cruisecontrolAx", tc, SP);
	}

	public static EqualsExpression makeEqualsExpression(ASMSpecification SP, String var,
			String val) {
		TypedInitExpression v = SP.getVariable(var);
		assertNotNull(v);
		EnumConst e = ((EnumType)v.getType()).getEnumConst(val);
		assertNotNull(e);
		return  new EqualsExpression(v.getIdExpression(),e);
	}

	/**
	 * *************************************************************************
	 * test well foermsess of a sal spec.
	 * 
	 * @param specName
	 *            the spec name
	 * @param tc
	 *            the tc
	 * @param SP
	 *            the sP
	 * 
	 * @throws Exception
	 *             the exception
	 */
	private void testWFSpecInitNext(String specName, TestCondition tc,
			ASMSpecification SP) throws Exception {
		// set the option
		atgt.preferences.ATGToolPreferences.ConsiderInitNext.setChecked(
				true);
		testWFSpec(specName, tc, SP);
		atgt.preferences.ATGToolPreferences.ConsiderInitNext.setChecked(
				false);
		testWFSpec(specName, tc, SP);
	}

	// DA AGGIUNGERE IL METODO PER CONTROLLARE SE IL FILE VA BENE DA
	// scrtgtool.translator.srisal.ToSRISALTranslatorTest
	/**
	 * Test wf spec.
	 * 
	 * @param specName
	 *            the spec name
	 * @param tc
	 *            the tc
	 * @param SP
	 *            the sP
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws FileNotFoundException
	 *             the file not found exception
	 */
	private void testWFSpec(String specName, TestCondition tc,
			ASMSpecification SP) throws IOException, FileNotFoundException {
		// write to a temp File
		File f = File.createTempFile(specName, ".sal");
		System.out.println(f.getAbsolutePath());
		String name = f.getName();
		name = name.substring(0, name.length() - 4);
		// transalte
		MonitoredDataToSAL trans = MonitoredDataToSAL.SINGLETON;
		trans.setContextName(name);
		trans.setTestCondition(tc);
		StringBuffer trasla = trans.analyze(SP);
		FileWriter fw = new FileWriter(f);
		fw.append(trasla);
		fw.close();
		String[] commandLine = { "sal-wfc", f.getAbsolutePath() };
		SimpleCmdExecutor.CMD.runCommand(f.getParentFile(), true, true,
				commandLine);
		BufferedReader sb = new BufferedReader(new FileReader(
				SimpleCmdExecutor.CMD.getOutput()));
		String line = sb.readLine();
		assertEquals("Ok.", line);
		while ((line = sb.readLine()) != null) {
			System.out.println(line);
		}
	}

}

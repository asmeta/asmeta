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
package atgt.parser.asmeta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import atgt.parser.ExampleLoader;
import atgt.parser.ParseSpecsAsmm;
import atgt.parser.asmgofer.ParseException;
import atgt.specification.ASMSpecification;
import atgt.specification.constraints.NextStateConstraint;
import atgt.specification.constraints.OneInputAssumption;
import atgt.specification.location.Variable;
import atgt.translator.ToAsmmVisitor;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.specification.Axiom;
import tgtlib.util.IterableEnumeration;

/**
 * test the parser for ASM files !!!.
 * 
 * @author garganti
 */
public class AsmMLoaderTest {

	/** Test of readAsmM method, of class atgt.parser.xmi.AsmMLoader. */

	private static ASMSpecification SISSpecification = null;

	/**
	 * return SIS SPEC.
	 * 
	 * @return the ASM specification
	 */
	public static ASMSpecification SISSpecification() {
		if (SISSpecification == null) {
			try {
				SISSpecification = loadSpec(atgt.parser.ParseSpecsAsmm.SIS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return SISSpecification;
	}

	/** The T p4. */
	private static ASMSpecification TP4 = null;

	/**
	 * return threepowerfour.asm
	 * 
	 * @return the ASM specification
	 */
	public static ASMSpecification TP4() {
		if (TP4 == null)
			return TP4 = loadSpec(atgt.parser.ParseSpecsAsmm.TP4);
		else
			return TP4;
	}

	/** The BBS. */
	private static ASMSpecification BBS = null;

	/**
	 * returns the bbs spec from an asm file with axioms.
	 * 
	 * @return the ASM specification
	 */
	public static ASMSpecification BasicBillingSystem() {
		if (BBS == null)
			return BBS = loadSpec(atgt.parser.ParseSpecsAsmm.BasicBillingSystem);
		else
			return BBS;
	}

	/** The BB s_ noax. */
	private static ASMSpecification BBS_NOAX = null;

	/**
	 * returns the bbs spec from an asm file without axioms.
	 * 
	 * @return the ASM specification
	 */
	public static ASMSpecification BBSNoAxioms() {
		if (BBS_NOAX == null) {
			BBS_NOAX = loadSpec(atgt.parser.ParseSpecsAsmm.BasicBillingSystem);
			BBS_NOAX.getAxiom().clear();
		}
		return BBS_NOAX;
	}

	/** The T p2_3_4_4. */
	private static ASMSpecification TP2_3_4_4;

	/**
	 * returns the T3_3_4444 spec from an asm file.
	 * 
	 * @return the ASM specification
	 */
	public static ASMSpecification TP2_3_4_4() {
		if (TP2_3_4_4 == null)
			return TP2_3_4_4 = loadSpec(atgt.parser.ParseSpecsAsmm.TP2_3_4_4);
		else
			return TP2_3_4_4;
	}

	/** The cc_asm. */
	private static ASMSpecification cc_asm;

	/**
	 * returns the cruise control - with initial value axiom AND with axioms
	 * over the next state per quello senza assiomi prendi il gofer.
	 * 
	 * @return the ASM specification
	 */
	public static ASMSpecification cc_asmWithAxioms() {
		if (cc_asm == null) {
			cc_asm = loadSpec(atgt.parser.ParseSpecsAsmm.CC_ASM);
			assertNotNull(cc_asm);
			// ADD THE CONSTRAINTS for lever
			Variable leverVar = null;
			for (Variable v : new IterableEnumeration<Variable>(cc_asm.allVariables())) {
				if (v.getName().equals("lever")) {
					leverVar = v;
					break;
				}
			}
			assertNotNull(leverVar);
			EnumConst act = ((EnumType) leverVar.getType()).getEnumConst("ACTIVATE");
			EnumConst dea = ((EnumType) leverVar.getType()).getEnumConst("DEACTIVATE");
			EnumConst res = ((EnumType) leverVar.getType()).getEnumConst("RESUME");
			assertNotNull(act);
			assertNotNull(dea);
			assertNotNull(res);
			// set the axioms
			// DEACTIVATE => next DE or ACT
			IdExpression[] deaP = { dea, act };
			cc_asm.addAxiom(new NextStateConstraint("a1", leverVar, dea, deaP));
			// ACTIVATE ==> DEACT of RES or ACT
			IdExpression[] ActP = { dea, act, res };
			cc_asm.addAxiom(new NextStateConstraint("a2", leverVar, act, ActP));
			// RES ==> ACT OR RES
			IdExpression[] resP = { act, res };
			cc_asm.addAxiom(new NextStateConstraint("a3", leverVar, res, resP));
		}
		return cc_asm;
	}

	/** The sis_asm_2. */
	private static ASMSpecification sis_asm_2;

	/**
	 * returns the SIS in versione astratta, senza WP, cioè con WP diviso in tre
	 * campi, con gli assiomi e con OIA.
	 * 
	 * @return the ASM specification
	 */
	public static ASMSpecification sis_asmAbstract() {
		if (sis_asm_2 == null) {
			sis_asm_2 = loadSpec(atgt.parser.ParseSpecsAsmm.SIS_ABSTRACT);
			assertNotNull(sis_asm_2);
			// ADD THE CONSTRAINTS for waterpressure
			Variable wpVar = null;
			for (Variable v : new IterableEnumeration<Variable>(sis_asm_2.allVariables())) {
				if (v.getName().equals("waterpressure")) {
					wpVar = v;
					break;
				}
			}
			assertNotNull(wpVar);
			EnumConst low = ((EnumType) wpVar.getType()).getEnumConst("LT_LOW");
			EnumConst per = ((EnumType) wpVar.getType()).getEnumConst("BT_LOWPERMIT");
			EnumConst hig = ((EnumType) wpVar.getType()).getEnumConst("GT_PERMIT");
			assertNotNull(low);
			assertNotNull(per);
			assertNotNull(hig);
			// set the axioms
			// LT_LOW => next BT_LOWPERMIT or LT_LOW
			sis_asm_2.addAxiom(NextStateConstraint.possibleChangeIn("a1", wpVar, low, per));
			// BT_LOWPERMIT => next BT_LOWPERMIT or LT_LOW OR GT_PERMIT
			IdExpression[] ActP = { low, hig };
			sis_asm_2.addAxiom(NextStateConstraint.possibleChangeIn("a2", wpVar, per, ActP));
			// GT_PERMIT => next BT
			sis_asm_2.addAxiom(NextStateConstraint.possibleChangeIn("a3", wpVar, hig, per));
			// ADD THE OIA
			sis_asm_2.addAxiom(OneInputAssumption.OIA);

		}
		return sis_asm_2;
	}

	/**
	 * Test read asm mcc.
	 */
	@Test
	public void testReadAsmMCC() {
		ASMSpecification cc = cc_asmWithAxioms();
		test(cc);
		System.out.println(cc.getAxiom().iterator().next().toString());
		assertEquals(5, cc.getAxiom().size());
		assertTrue(cc.getVariable("mode").isControlled());
		assertTrue(cc.getVariable("lever").isMonitored());
	}

	/**
	 * Test read asm msis.
	 */
	@Test
	public void testReadAsmMSIS() {
		test(SISSpecification());
	}

	/**
	 * Test read t334444.
	 */
	@Test
	public void testReadT334444() {
		test(TP2_3_4_4());
	}

	
	@Test
	public void testCruiseControl() throws ParseException, IOException {
		Logger.getLogger(AsmetaLLoader.class).setLevel(Level.ALL);
		test(ExampleLoader.getSpec("cruiseControl.asm"));
	}

	/**
	 * Test read asm mbbs.
	 */
	@Test
	public void testReadAsmMBBS() {
		ASMSpecification bbs = BasicBillingSystem();
		test(bbs);
		assertTrue(bbs.getAxiom().size() > 0);
	}

	/**
	 * Test read asm mbb s_ noax.
	 */
	@Test
	public void testReadAsmMBBS_NOAX() {
		ASMSpecification bbs = BBSNoAxioms();
		test(bbs);
		assertTrue(bbs.getAxiom().size() == 0);
	}

	/**
	 * Test read si s_ wa.
	 */
	@Test
	public void testReadSIS_WA() {
		ASMSpecification sis2 = sis_asmAbstract();
		assertNotNull(sis2);
		test(sis2);
		System.out.println(sis2.getAxiom().iterator().next().toString());
		assertEquals(4, sis2.getAxiom().size());
	}

	/**
	 * controlla che legga in modo corretto le enumconst degli assiomi
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * 
	 */
	@Test
	public void testEnumConstReader() throws ParseException, IOException {
		Logger.getLogger(AsmetaLLoader.class).setLevel(Level.ALL);
		ASMSpecification s = ExampleLoader.getSpec("combinatorial/CCA1.asm");
		// check that the type is correct
		assertNotNull(((ElementsType) (s.getTypeFor("D3"))).allElements().get(0).getType());

		// / controlla anche gli assiomi
		Collection<Axiom> axioms = s.getAxiom();
		assertNotNull(axioms);
		assertTrue(axioms.size() > 0);
		Expression ax = axioms.iterator().next().getBody();
		assertTrue(ax instanceof NotExpression);
		Expression andE = ((NotExpression) ax).getOperand();
		assertTrue(andE instanceof AndExpression);
		Expression eqE = ((AndExpression) andE).getFirstOperand();
		Expression enumExpr = ((EqualsExpression) eqE).getSecondOperand();
		assertTrue("" + enumExpr.getClass() + " " + enumExpr, enumExpr instanceof EnumConst);
		EnumConst ec = (EnumConst) enumExpr;
		assertNotNull(ec.getType());

	}

	@Test
	public void testPlus() throws ParseException, IOException {
		Logger.getLogger(AsmetaLLoader.class).setLevel(Level.ALL);
		ASMSpecification s = ExampleLoader.getSpec("plus.asm");	
	}

	@Test
	public void testPunto() throws ParseException, IOException {
		Logger.getLogger(AsmetaLLoader.class).setLevel(Level.ALL);
		ASMSpecification s = ExampleLoader.getSpec("punto2DintParamsForSpin.asm");
		assertNotNull(s);		
	}

	/**
	 * Gets the tCAS.
	 * 
	 * @return the tCAS
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException
	 */
	@Test
	public void getTCAS() throws ParseException, IOException {
		ASMSpecification res = ExampleLoader.getSpec("combinatorial/TCAS.asm");
		Assert.assertNotNull(res);
	}

	/**
	 * Test.
	 * 
	 * @param spec
	 *            the spec
	 */
	private void test(ASMSpecification spec) {
		assertNotNull(spec);
		ToAsmmVisitor visit = new ToAsmmVisitor();
		System.out.println(visit.analyze(spec).toString());

	}

	// / load the specification from a file f
	/**
	 * Load spec.
	 * 
	 * @param f
	 *            the f
	 * 
	 * @return the aSM specification
	 */
	public static ASMSpecification loadSpec(File f) {
		assert f.exists() : " file " + f + " not found";
		AsmetaLLoader xmipar = new AsmetaLLoader();
		ASMSpecification SP;
		try {
			SP = xmipar.read(f);
			return SP;
		} catch (tgtlib.specification.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
			return null;
		}
	}
	
	
	@Test
	public void testStereoacuity() {
		File stereoacuity = ParseSpecsAsmm.getFileSpec("stereoacuity/certifier3CHECK_6.asm");
		ASMSpecification derS = AsmMLoaderTest.loadSpec(stereoacuity);
		assertNotNull(derS);
		Enumeration<Variable> allVariables = derS.allVariables();
		System.out.println(allVariables.nextElement());
		System.out.println(allVariables.nextElement());
		System.out.println(allVariables.nextElement());
			//System.out.println(cc.getAxiom().iterator().next().toString());
		//assertEquals(5, cc.getAxiom().size());
		//assertTrue(cc.getVariable("mode").isControlled());
		//assertTrue(cc.getVariable("lever").isMonitored());
	}
	
	
	@Test
	public void testMVM() {
		 Logger.getLogger(AsmetaLLoader.class).setLevel(Level.ALL);
		File stereoacuity = new File("D:\\AgHome\\progettidaSVNGIT\\asmeta\\mvm-asmeta\\asm_models\\VentilatoreASM_NewTime\\Ventilatore4SimpleTimeLtdYFun.asm");
		ASMSpecification derS = AsmMLoaderTest.loadSpec(stereoacuity);
		assertNotNull(derS);
		Enumeration<Variable> allVariables = derS.allVariables();
		System.out.println(allVariables.nextElement());
		System.out.println(allVariables.nextElement());
		System.out.println(allVariables.nextElement());
			//System.out.println(cc.getAxiom().iterator().next().toString());
		//assertEquals(5, cc.getAxiom().size());
		//assertTrue(cc.getVariable("mode").isControlled());
		//assertTrue(cc.getVariable("lever").isMonitored());
	}

}


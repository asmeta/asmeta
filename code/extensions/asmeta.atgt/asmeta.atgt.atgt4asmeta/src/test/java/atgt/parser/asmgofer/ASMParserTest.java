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
package atgt.parser.asmgofer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import atgt.parser.ExampleLoader;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Variable;
import atgt.specification.type.DummyType;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.Type;

/**
 * The Class ASMParserTest.
 */
public class ASMParserTest {

	/** The ccna. */
	static ASMSpecification ccna = null;

	/** The Constant ccName. */
	static final String ccName = "CruiseControl";

	@BeforeAll
	static void setUpLogger(){
		Logger.getLogger(AsmGoferParser.class).setLevel(Level.ALL);
	}

	/**
	 * Test get specification.
	 */
	@Test void ccnaSpecification() {
		ASMSpecification cc = getCruiseControlNoAxiom();
		assertNotNull(cc);
		assertEquals(ccName, cc.name);
		for(Variable var:cc.getVariables()){
			String name = var.getName();
			assertSame(cc.getVariable(name), var);
			assertFalse(var.getType() instanceof DummyType, var.getName());
		}
	}

	/**
	 * Gets the cruise control.
	 * 
	 * @return the cruise control (WITHOUT AXIOMS) !!! quella con gli assiomi
	 */
	public static ASMSpecification getCruiseControlNoAxiom() {
		if (ccna != null)
			return ccna;
		File CruiseControl = atgt.parser.ParseGoferFiles.CruiseControl;
		System.err.println(CruiseControl.getAbsolutePath());
		try {			
			AsmGoferParser parser = new AsmGoferParser(new FileReader(CruiseControl));
			// Elimina la specifica precedente.
			parser.resetSpecification();
			// Esegue il parsing del testo
			parser.parseSpec();
			ccna = parser.getSpecification();
			ccna.name = ccName;
			// TODO ADD ALSO THE AXIOM THAT IF THE ENG RUNNING IS INGNITED
			// QUALCOSA DI SIMILE .... controllare
			
			return ccna;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Test SIS
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@Test void sis() throws Exception {
		ASMSpecification res = ExampleLoader.getSpec("sis.gs");
		assertNotNull(res);
		assertEquals("waterPressure", res.allVariables().nextElement().toString());
		Variable WP = res.getVariable("waterPressure");
		assertTrue(WP.isMonitored());
		Type WPT = WP.getType();
		assertEquals("Int", WPT.getName());
		assertInstanceOf(BoundType.class, WPT);
		// gofer parser fails to set the name !!!
		//assertEquals("SIS",res.name);
		for(Variable var:res.getVariables()){
			String name = var.getName();
			assertSame(res.getVariable(name), var);
			assertFalse(var.getType() instanceof DummyType, var.getName());
		}
		
	}

}
